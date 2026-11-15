package bunnythekenzo.client;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import net.fabricmc.loader.api.FabricLoader;

public final class SpotifyBridge {
  public final Path path =
      FabricLoader.getInstance().getConfigDir().resolve("bunnythekenzo_art.img");
  public volatile ClientData4 clientData4;
  public Process process;
  public BufferedWriter bufferedWriter;
  public volatile boolean enabled;
  public final AtomicBoolean started = new AtomicBoolean();
  public int count;

  public SpotifyBridge() {
    this.clientData4 = ClientData4.clientData4;
  }

  public ClientData4 getClientData4() {
    return this.clientData4;
  }

  public Path getPath() {
    return this.path;
  }

  public void update() {
    ClientData4.ensureInitialized();
    if (!this.started.compareAndSet(false, true)) {
      return;
    }

    if (!System.getProperty("os.name", "").toLowerCase(Locale.ROOT).contains("win")) {
      ClientInitializer.logger.info("SpotifyHUD bridge disabled (not Windows)");
      return;
    }

    Thread thread = new Thread(this::runBridge, "bunnythekenzo-spotify-bridge");
    thread.setDaemon(true);
    thread.start();
    ClientInitializer.ensureInitialized();
  }

  public synchronized void update2() {
    ClientData4.ensureInitialized();

    this.enabled = true;
    Process process2 = this.process;
    if (process2 != null) {
      process2.destroy();
      try {
        if (!process2.waitFor(2L, TimeUnit.SECONDS)) {
          process2.destroyForcibly();
          process2.waitFor(2L, TimeUnit.SECONDS);
        }
      } catch (InterruptedException interrupted) {
        process2.destroyForcibly();
        Thread.currentThread().interrupt();
        ClientInitializer.logger.debug("Interrupted while stopping Spotify bridge", interrupted);
      }
    }
    try {
      Files.deleteIfExists(
          FabricLoader.getInstance()
              .getConfigDir()
              .resolve("bunnythekenzo_smtc_bridge.ps1"));
    } catch (Exception failure) {
      ClientInitializer.logger.debug("Could not remove Spotify bridge script", failure);
    }
  }

  public void runBridge() {
    ClientData4.ensureInitialized();

    while (!this.enabled && this.count < 4) {
      try {
        Path path2 = this.getPath2();
        ProcessBuilder processBuilder =
            new ProcessBuilder(
                "powershell.exe",
                "-NoProfile",
                "-NonInteractive",
                "-MTA",
                "-WindowStyle",
                "Hidden",
                "-ExecutionPolicy",
                "Bypass",
                "-File",
                path2.toString(),
                this.path.toString());
        processBuilder.redirectErrorStream(false);
        synchronized (this) {
          if (this.enabled) {
            return;
          }

          this.process = processBuilder.start();
          this.bufferedWriter =
              new BufferedWriter(
                  new OutputStreamWriter(this.process.getOutputStream(), StandardCharsets.UTF_8));
          Process process2 = this.process;
          Thread thread =
              new Thread(
                  () -> {
                    this.apply(process2);
                  },
                  "bunnythekenzo-spotify-errors");
          thread.setDaemon(true);
          thread.start();
        }

        String text;
        try (BufferedReader bufferedReader =
            new BufferedReader(
                new InputStreamReader(this.process.getInputStream(), StandardCharsets.UTF_8))) {
          while ((text = bufferedReader.readLine()) != null) {
            this.handleStatusMessage(text.trim());
          }
        }

        this.process.waitFor();
      } catch (Exception failure) {
        ClientInitializer.logger.warn("Spotify bridge died: {}", failure.toString());
      } finally {
        synchronized (this) {
          this.bufferedWriter = null;
          if (this.process != null && this.process.isAlive()) {
            this.process.destroy();
          }

          this.process = null;
        }
      }

      this.clientData4 = ClientData4.clientData4;
      this.count++;
    }
  }

  public void apply(Process process2) {
    ClientData4.ensureInitialized();

    String text;
    try (BufferedReader bufferedReader =
        new BufferedReader(
            new InputStreamReader(process2.getErrorStream(), StandardCharsets.UTF_8))) {
      while ((text = bufferedReader.readLine()) != null) {
        if (!text.isBlank()) {
          ClientInitializer.logger.warn("Spotify bridge: {}", text);
        }
      }
    } catch (Exception failure) {
    }
  }

  public Path getPath2() throws Exception {
    Path configDir = FabricLoader.getInstance().getConfigDir();
    Files.createDirectories(configDir);
    ClientData4.ensureInitialized();

    Path path2 = configDir.resolve("bunnythekenzo_smtc_bridge.ps1");

    try (InputStream resourceAsStream =
        SpotifyBridge.class
            .getClassLoader()
            .getResourceAsStream("assets/bunnythekenzo/spotify/smtc_bridge.ps1")) {
      if (resourceAsStream == null) {
        throw new IllegalStateException(DiagnosticTokenGenerator.tokenFor(1301));
      }

      Files.write(path2, resourceAsStream.readAllBytes());
    }

    return path2;
  }

  public void handleStatusMessage(String text) {
    ClientData4.ensureInitialized();
    if (!text.isEmpty() && text.startsWith("{")) {
      try {
        JsonObject asJsonObject = JsonParser.parseString(text).getAsJsonObject();
        if (asJsonObject.has("error") || asJsonObject.has("fatal")) {
          ClientInitializer.logger.warn("Spotify bridge: {}", text);
          return;
        }

        if (!asJsonObject.has("active") || !asJsonObject.get("active").getAsBoolean()) {
          this.clientData4 = ClientData4.clientData4;
          return;
        }

        this.clientData4 =
            new ClientData4(
                true,
                asJsonObject.get("title").getAsString(),
                asJsonObject.get("artist").getAsString(),
                asJsonObject.get("posMs").getAsLong(),
                asJsonObject.get("durMs").getAsLong(),
                asJsonObject.get("playing").getAsBoolean(),
                asJsonObject.has("canSeek") && asJsonObject.get("canSeek").getAsBoolean(),
                asJsonObject.has("artV") ? asJsonObject.get("artV").getAsInt() : 0,
                asJsonObject.has("vol") ? asJsonObject.get("vol").getAsInt() : -1,
                System.nanoTime());
      } catch (Exception failure) {
      }
    }
  }

  public synchronized boolean test(String text) {
    ClientData4.ensureInitialized();
    if (!this.enabled && this.bufferedWriter != null) {
      try {
        this.bufferedWriter.write(text);
        this.bufferedWriter.newLine();
        this.bufferedWriter.flush();
        return true;
      } catch (Exception failure) {
        ClientInitializer.logger.warn("Spotify bridge command failed: {}", failure.toString());
        return false;
      }
    } else {
      return false;
    }
  }

  public void update3() {
    this.test("NEXT");
  }

  public void update4() {
    this.test("PREV");
  }

  public void update5() {
    this.test("PLAYPAUSE");
  }

  public void apply(long longValue) {
    ClientData4.ensureInitialized();

    long timestamp = Math.clamp(longValue, 0L, Math.max(0L, this.clientData4.timestamp2()));
    if (this.test("SEEK " + timestamp)) {
      ClientData4 clientData42 = this.clientData4;
      if (clientData42.enabled()) {
        this.clientData4 =
            new ClientData4(
                true,
                clientData42.string(),
                clientData42.string2(),
                timestamp,
                clientData42.timestamp2(),
                clientData42.enabled2(),
                clientData42.enabled3(),
                clientData42.count(),
                clientData42.count2(),
                System.nanoTime());
      }
    }
  }

  public void apply(int intValue) {
    ClientData4.ensureInitialized();

    int count2 = Math.clamp(intValue, 0, 100);
    if (this.test("VOLUME " + count2)) {
      ClientData4 clientData42 = this.clientData4;
      if (clientData42.enabled()) {
        this.clientData4 =
            new ClientData4(
                true,
                clientData42.string(),
                clientData42.string2(),
                clientData42.timestamp(),
                clientData42.timestamp2(),
                clientData42.enabled2(),
                clientData42.enabled3(),
                clientData42.count(),
                count2,
                clientData42.timestamp3());
      }
    }
  }
}
