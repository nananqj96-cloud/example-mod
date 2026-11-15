$ErrorActionPreference = 'Stop'
[Console]::InputEncoding = New-Object System.Text.UTF8Encoding($false)
[Console]::OutputEncoding = New-Object System.Text.UTF8Encoding($false)
Add-Type -AssemblyName System.Runtime.WindowsRuntime

$null = [Windows.Media.Control.GlobalSystemMediaTransportControlsSessionManager, Windows.Media.Control, ContentType = WindowsRuntime]

$asTaskGeneric = ([System.WindowsRuntimeSystemExtensions].GetMethods() | Where-Object {
    $_.Name -eq 'AsTask' -and $_.GetParameters().Count -eq 1 -and $_.GetParameters()[0].ParameterType.Name -eq 'IAsyncOperation`1'
})[0]

function Await($WinRtTask, $ResultType) {
    $asTask = $asTaskGeneric.MakeGenericMethod($ResultType)
    $netTask = $asTask.Invoke($null, @($WinRtTask))
    if (-not $netTask.Wait(5000)) { throw 'Spotify operation timed out' }
    $netTask.Result
}

function Get-SpotifySession($mgr) {
    foreach ($s in $mgr.GetSessions()) {
        if ($s.SourceAppUserModelId -like '*spotify*') { return $s }
    }
    return $null
}

$mgr = Await ([Windows.Media.Control.GlobalSystemMediaTransportControlsSessionManager]::RequestAsync()) ([Windows.Media.Control.GlobalSystemMediaTransportControlsSessionManager])
if ($null -eq $mgr) { [Console]::Out.WriteLine('{"fatal":"no session manager"}'); exit 1 }

$artPath = $args[0]
if (-not $artPath) { $artPath = Join-Path $env:TEMP 'bunnythekenzo_spotify_art.img' }

$null = [Windows.Storage.Streams.RandomAccessStreamReference, Windows.Storage.Streams, ContentType = WindowsRuntime]
$asStreamMethod = [System.IO.WindowsRuntimeStreamExtensions].GetMethod('AsStreamForRead',
    [type[]]@([Windows.Storage.Streams.IRandomAccessStream]))

$hasVol = $false
try {
    Add-Type -Language CSharp @'
using System;
using System.Runtime.InteropServices;
using System.Diagnostics;
namespace BunnyTheKenzo {
  [ComImport, Guid("BCDE0395-E52F-467C-8E3D-C4579291692E")]
  public class MMDeviceEnumerator { }
  [Guid("A95664D2-9614-4F35-A746-DE8DB63617E6"), InterfaceType(ComInterfaceType.InterfaceIsIUnknown), ComImport]
  public interface IMMDeviceEnumerator {
    [PreserveSig] int EnumAudioEndpoints(int dataFlow, uint stateMask, out IMMDeviceCollection devices);
    [PreserveSig] int GetDefaultAudioEndpoint(int dataFlow, int role, out IMMDevice ppDevice);
  }
  [Guid("0BD7A1BE-7A1A-44DB-8397-CC5392387B5E"), InterfaceType(ComInterfaceType.InterfaceIsIUnknown), ComImport]
  public interface IMMDeviceCollection {
    [PreserveSig] int GetCount(out uint count);
    [PreserveSig] int Item(uint index, out IMMDevice device);
  }
  [Guid("D666063F-1587-4E43-81F1-B948E807363F"), InterfaceType(ComInterfaceType.InterfaceIsIUnknown), ComImport]
  public interface IMMDevice {
    [PreserveSig] int Activate(ref Guid iid, int dwClsCtx, IntPtr pActivationParams, [MarshalAs(UnmanagedType.IUnknown)] out object ppInterface);
  }
  [Guid("77AA99A0-1BD6-484F-8BC7-2C654C9A9B6F"), InterfaceType(ComInterfaceType.InterfaceIsIUnknown), ComImport]
  public interface IAudioSessionManager2 {
    int f0(); int f1();
    [PreserveSig] int GetSessionEnumerator(out IAudioSessionEnumerator SessionEnum);
  }
  [Guid("E2F5BB11-0570-40CA-ACDD-3AA01277DEE8"), InterfaceType(ComInterfaceType.InterfaceIsIUnknown), ComImport]
  public interface IAudioSessionEnumerator {
    [PreserveSig] int GetCount(out int SessionCount);
    [PreserveSig] int GetSession(int SessionCount, out IAudioSessionControl2 Session);
  }
  [Guid("bfb7ff88-7239-4fc9-8fa2-07c950be9c6d"), InterfaceType(ComInterfaceType.InterfaceIsIUnknown), ComImport]
  public interface IAudioSessionControl2 {
    int f0(); int f1(); int f2(); int f3(); int f4(); int f5(); int f6(); int f7(); int f8(); int f9(); int f10();
    [PreserveSig] int GetProcessId(out uint pRetVal);
    [PreserveSig] int IsSystemSoundsSession();
    int f13();
  }
  [Guid("87CE5498-68D6-44E5-9215-6DA47EF883D8"), InterfaceType(ComInterfaceType.InterfaceIsIUnknown), ComImport]
  public interface ISimpleAudioVolume {
    [PreserveSig] int SetMasterVolume(float fLevel, ref Guid EventContext);
    [PreserveSig] int GetMasterVolume(out float pfLevel);
    [PreserveSig] int SetMute(int bMute, ref Guid EventContext);
    [PreserveSig] int GetMute(out int pbMute);
  }
  public static class SpotVol {
    static bool IsSpotify(uint pid) {
      if (pid == 0) return false;
      try { using (var process = Process.GetProcessById((int)pid)) { return string.Equals(process.ProcessName, "Spotify", StringComparison.OrdinalIgnoreCase); } }
      catch { return false; }
    }
    static void Release(object value) {
      if (value != null && Marshal.IsComObject(value)) Marshal.ReleaseComObject(value);
    }
    // Spotify may be routed to headphones or another output in the app mixer.
    // Enumerate every active render endpoint, not just the system default.
    static int Visit(int? pct) {
      IMMDeviceEnumerator de = null;
      IMMDeviceCollection devices = null;
      int result = -1;
      try {
        de = (IMMDeviceEnumerator)(new MMDeviceEnumerator());
        if (de.EnumAudioEndpoints(0, 1, out devices) < 0 || devices == null) return -1;
        uint count; if (devices.GetCount(out count) < 0) return -1;
        for (uint d = 0; d < count; d++) {
          IMMDevice dev = null;
          object manager = null;
          IAudioSessionEnumerator sessions = null;
          try {
            if (devices.Item(d, out dev) < 0 || dev == null) continue;
            Guid iid = typeof(IAudioSessionManager2).GUID;
            if (dev.Activate(ref iid, 1, IntPtr.Zero, out manager) < 0 || manager == null) continue;
            if (((IAudioSessionManager2)manager).GetSessionEnumerator(out sessions) < 0 || sessions == null) continue;
            int n; if (sessions.GetCount(out n) < 0) continue;
            for (int i = 0; i < n; i++) {
              IAudioSessionControl2 session = null;
              try {
                if (sessions.GetSession(i, out session) < 0 || session == null) continue;
                uint pid; if (session.GetProcessId(out pid) < 0 || !IsSpotify(pid)) continue;
                var volume = (ISimpleAudioVolume)session;
                if (pct.HasValue) {
                  Guid ctx = Guid.Empty;
                  float level = Math.Max(0, Math.Min(100, pct.Value)) / 100f;
                  if (volume.SetMasterVolume(level, ref ctx) >= 0) {
                    if (level > 0) volume.SetMute(0, ref ctx);
                    result = (int)Math.Round(level * 100);
                  }
                } else {
                  float level; int muted;
                  if (volume.GetMasterVolume(out level) >= 0 && volume.GetMute(out muted) >= 0)
                    result = Math.Max(result, muted != 0 ? 0 : (int)Math.Round(level * 100));
                }
              } catch { } finally { Release(session); }
            }
          } catch { } finally { Release(sessions); Release(manager); Release(dev); }
        }
      } catch { } finally { Release(devices); Release(de); }
      return result;
    }
    public static int Get() { return Visit(null); }
    public static bool Set(int pct) { return Visit(pct) >= 0; }
  }
}
'@
    $hasVol = $true
} catch { [Console]::Error.WriteLine("Spotify volume unavailable: " + $_.Exception.Message) }

$lastTrack = ''
$artVersion = 0

$cmdQueue = New-Object 'System.Collections.Concurrent.ConcurrentQueue[string]'
$readerRunspace = [runspacefactory]::CreateRunspace()
$readerRunspace.Open()
$readerRunspace.SessionStateProxy.SetVariable('q', $cmdQueue)
$reader = [powershell]::Create()
$reader.Runspace = $readerRunspace
$null = $reader.AddScript('while ($true) { $l = [Console]::In.ReadLine(); if ($null -eq $l) { $q.Enqueue("__EOF__"); break }; $q.Enqueue($l) }')
$null = $reader.BeginInvoke()

while ($true) {
    try {
    $line = $null
    while ($cmdQueue.TryDequeue([ref]$line)) {
        if ($line -eq '__EOF__') { exit 0 }
        $parts = $line.Trim() -split ' '
        try {
            $s = Get-SpotifySession $mgr
            $ok = $false
            switch ($parts[0].ToUpper()) {
                'NEXT' { if ($s) { $ok = Await ($s.TrySkipNextAsync()) ([bool]) } }
                'PREV' { if ($s) { $ok = Await ($s.TrySkipPreviousAsync()) ([bool]) } }
                'PLAYPAUSE' { if ($s) { $ok = Await ($s.TryTogglePlayPauseAsync()) ([bool]) } }
                'SEEK' {
                    if ($s -and $parts.Count -ge 2) {
                        $ticks = [long]$parts[1] * 10000
                        $ok = Await ($s.TryChangePlaybackPositionAsync($ticks)) ([bool])
                    }
                }
                'VOLUME' {
                    if ($hasVol -and $parts.Count -ge 2) {
                        $ok = [BunnyTheKenzo.SpotVol]::Set([int]$parts[1])
                    }
                }
            }
            if (-not $ok) { [Console]::Error.WriteLine("Spotify rejected command: " + $parts[0]) }
        } catch { [Console]::Error.WriteLine("Spotify command failed: " + $_.Exception.Message) }
    }

    $s = Get-SpotifySession $mgr
    if ($null -eq $s) {
        [Console]::Out.WriteLine('{"active":false}')
    } else {
        $props = Await ($s.TryGetMediaPropertiesAsync()) ([Windows.Media.Control.GlobalSystemMediaTransportControlsSessionMediaProperties])
        if ($props) {
            $tl = $s.GetTimelineProperties()
            $pb = $s.GetPlaybackInfo()
            $playing = $pb.PlaybackStatus -eq [Windows.Media.Control.GlobalSystemMediaTransportControlsSessionPlaybackStatus]::Playing
            $posMs = [long]$tl.Position.TotalMilliseconds
            if ($playing) {
                $posMs += [long]([DateTimeOffset]::UtcNow - $tl.LastUpdatedTime.ToUniversalTime()).TotalMilliseconds
            }
            $track = "$($props.Title)|$($props.Artist)"

            if ($track -ne $lastTrack -and $props.Thumbnail) {
                try {
                    $streamRaw = Await ($props.Thumbnail.OpenReadAsync()) ([Windows.Storage.Streams.IRandomAccessStreamWithContentType])
                    $netStream = $asStreamMethod.Invoke($null, @($streamRaw))
                    $fs = [IO.File]::Create($artPath)
                    $netStream.CopyTo($fs)
                    $fs.Dispose()
                    $netStream.Dispose()
                    $artVersion++
                } catch { }
            }
            $lastTrack = $track

            $vol = -1
            if ($hasVol) { $vol = [BunnyTheKenzo.SpotVol]::Get() }

            $obj = @{
                active = $true
                title = "$($props.Title)"
                artist = "$($props.Artist)"
                posMs = $posMs
                durMs = [long]$tl.EndTime.TotalMilliseconds
                playing = $playing
                canSeek = [bool]$pb.Controls.IsPlaybackPositionEnabled
                artV = $artVersion
                vol = $vol
            }
            [Console]::Out.WriteLine(($obj | ConvertTo-Json -Compress))
        } else { [Console]::Out.WriteLine('{"active":false}') }
    }
    } catch {
        [Console]::Error.WriteLine("Spotify poll failed: " + $_.Exception.Message)
        [Console]::Out.WriteLine('{"active":false}')
    }
    [Console]::Out.Flush()
    Start-Sleep -Milliseconds 600
}
