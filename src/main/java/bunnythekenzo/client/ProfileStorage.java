package bunnythekenzo.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;

public final class ProfileStorage {
  public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
  public static ProfileStorage profileStorage;
  public final Path path;
  public ProfileDefaults profileDefaults = new ProfileDefaults();
  public long timestamp;
  public long timestamp2;
  public long timestamp3;
  public Object object;
  public boolean enabled;
  public boolean enabled2;
  public static String string;
  public static final long timestamp4 = -2776464308370930712L;

  public static ProfileStorage getProfileStorage() {
    ProfileDefaults.ensureInitialized();
    if (profileStorage == null) {
      profileStorage = new ProfileStorage();
    }

    return profileStorage;
  }

  public ProfileStorage() {
    ProfileDefaults.ensureInitialized();

    this.timestamp = System.nanoTime();
    this.timestamp2 = this.timestamp;
    this.path =
        FabricLoader.getInstance()
            .getConfigDir()
            .resolve("bunnythekenzo")
            .resolve("profiles")
            .resolve(MinecraftClient.getInstance().getSession().getUuidOrNull() + ".json");
    if (Files.exists(this.path)) {
      try (BufferedReader bufferedReader = Files.newBufferedReader(this.path)) {
        ProfileDefaults profileDefaults2 = gson.fromJson(bufferedReader, ProfileDefaults.class);
        if (profileDefaults2 != null) {
          this.profileDefaults = profileDefaults2;
        }
      } catch (Exception failure) {
        ClientInitializer.logger.warn("Could not read local profile {}", this.path, failure);

        try {
          Files.copy(
              this.path,
              this.path.resolveSibling(
                  this.path.getFileName() + ".corrupt-" + System.currentTimeMillis()));
        } catch (IOException failure2) {
          ClientInitializer.logger.warn("Could not back up profile", failure2);
        }
      }
    }

    this.profileDefaults.normalize();
  }

  public ProfileDefaults getProfileDefaults() {
    return this.profileDefaults;
  }

  public long getTimestamp3() {
    return this.timestamp3;
  }

  public String getString() {
    ProfileDefaults.ensureInitialized();
    return this.profileDefaults.string2.isEmpty() ? "your name" : this.profileDefaults.string2;
  }

  public boolean test() {
    boolean enabled3 = this.profileDefaults.enabled;
    ProfileDefaults.ensureInitialized();

    this.profileDefaults.enabled = true;
    if (this.test2()) {
      return true;
    }

    this.profileDefaults.enabled = enabled3;
    return false;
  }

  public boolean test(String string2, boolean flag) {
    ProfileDefaults.ensureInitialized();
    if (!ProfileDefaults.test(string2)) {
      return false;
    }

    String string22 = this.profileDefaults.string2;
    boolean enabled3 = this.profileDefaults.enabled;
    this.profileDefaults.string2 = string2;
    this.profileDefaults.enabled |= flag;
    if (this.test2()) {
      return true;
    }

    this.profileDefaults.string2 = string22;
    this.profileDefaults.enabled = enabled3;
    return false;
  }

  public void update() {
    this.enabled2 = true;
  }

  public void apply(MinecraftClient minecraftClient) {
    ProfileDefaults.ensureInitialized();

    long timestamp5 = System.nanoTime();
    boolean enabled3 =
        minecraftClient.world != null
            && minecraftClient.player != null
            && !minecraftClient.isPaused();
    ClientPlayNetworkHandler object2 =
        minecraftClient.world == null ? null : minecraftClient.getNetworkHandler();
    if (object2 != this.object) {
      this.object = object2;
      this.timestamp3 = 0L;
      this.enabled = false;
      if (this.object != null) {
        this.profileDefaults.count++;
        this.enabled2 = true;
      }
    }

    if (enabled3 && this.enabled) {
      long longValue = Math.clamp((timestamp5 - this.timestamp) / 1000000L, 0, 1000);
      this.profileDefaults.apply(longValue, LocalDate.now());
      this.timestamp3 += longValue;
      this.enabled2 = true;
    }

    this.enabled = enabled3;
    this.timestamp = timestamp5;
    if (this.enabled2 && timestamp5 - this.timestamp2 >= 30000000000L) {
      this.test2();
    }
  }

  public boolean test2() {
    this.timestamp2 = System.nanoTime();
    Path path2 = this.path.resolveSibling(this.path.getFileName() + ".tmp");

    try {
      Files.createDirectories(this.path.getParent());
      Files.writeString(path2, gson.toJson(this.profileDefaults));

      try {
        Files.move(
            path2, this.path, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
      } catch (AtomicMoveNotSupportedException failure) {
        Files.move(path2, this.path, StandardCopyOption.REPLACE_EXISTING);
      }

      this.enabled2 = false;
      return true;
    } catch (IOException failure2) {
      this.enabled2 = true;
      ClientInitializer.logger.error("Could not save local profile {}", this.path, failure2);
      return false;
    }
  }

  static {
    setString("hzbFRb");
  }

  public static void setString(String string2) {
    string = string2;
  }

  public static String getString2() {
    return string;
  }
}
