# Security review notes

## Scope and result

This is a best-effort static review of `67clientv2-sourcedbysub5larp.zip` from the linked Drive folder (SHA-256: `92c541dbaee52087da243a15bf17bd79d1d3da1b9455ef9ec58fec41ba061192`). The ZIP passed its CRC/integrity check; its entries contained no absolute paths, `..` traversal paths, encrypted members, or symlinks. The source-wide checks covered Java, build scripts, bundled PowerShell, resource names, and native-library metadata/strings. Unknown code and native binaries were not executed.

No clear active RAT, credential stealer, browser-token collector, auto-start persistence mechanism, or command-and-control path was identified in the reviewed runtime code. This is not an antivirus certification: ClamAV/YARA tooling was not installed in the environment, and static inspection cannot prove a binary is safe.

## Removed during review

The archive included an unused hardware-fingerprinting and encrypted-payload loader chain (`EnvironmentChecks`, `HardwareIdentity`, `NativeHardwareBridge`, `ProtectedResourceKeys`, `ProtectionBootstrap`, `EncryptedClassLoader`, and `PayloadCipher`), an obfuscated JNI wrapper, and two unreferenced Windows DLLs (`AiAuth.dll`, `Aiprotect.dll`). The Java code included anti-debug/VM checks, reads of CPU/volume/MAC identifiers, and logic that would decrypt and load an `inner.bin` JAR. No `inner.bin`/`inner.crc` payload was present, and the chain was not referenced by the declared Fabric entrypoint. The unused code and DLLs were removed rather than carried into the renamed client.

The entrypoint also had two unrelated, inappropriate authentication-themed console prints; those were removed. The Spotify bridge used to launch hidden PowerShell on every Windows startup. Its behavior is now opt-in: the HUD defaults to **Demo**, and the bridge starts once only after the user selects **Auto**. The bridge invokes `powershell.exe` with process-scoped `-ExecutionPolicy Bypass`; it writes the bundled script to the Fabric config directory and removes it on normal client shutdown.

## Remaining network and local-process behavior

- `ItemWorthService` makes a GET request to `https://donutsmpstats.com/api/worth` for item-value data; it sends no account token or player data.
- `SkinProtectModule` queries Mojang's public profile/session endpoints for the username provided in its setting; it does not send Minecraft authentication tokens.
- `SpawnerProtectModule` can POST an alert to a webhook entered by the user. The setting defaults to an empty string, so it sends nothing unless configured.
- When the user opts into Spotify **Auto** on Windows, the bundled PowerShell script reads the local Spotify media session, saves artwork under the Fabric config directory, and accepts local playback-control commands. The script contains no network, registry, scheduled-task, or service commands. The generated `.ps1` file and cover-art cache are stored in the Fabric config directory while the feature is used.

Review the code and dependencies yourself before distributing or running the mod. Gradle will download its configured build dependencies when building.
