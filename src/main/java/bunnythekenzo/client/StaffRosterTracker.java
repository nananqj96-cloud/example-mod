package bunnythekenzo.client;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.scoreboard.Team;
import net.minecraft.world.GameMode;
import bunnythekenzo.client.module.StaffListModule;

public final class StaffRosterTracker {

  public final StaffListModule staffListModule;
  public volatile List list = List.of();
  public Set set = new HashSet();
  public boolean enabled;
  public int count2;
  public static volatile List list2;

  public StaffRosterTracker(StaffListModule staffListModule2) {
    this.staffListModule = staffListModule2;
  }

  public List getList() {
    ClientData.ensureInitialized();

    List list3 = list2;
    return list3 != null ? list3 : this.list;
  }

  public void update() {
    this.set = new HashSet();
    this.enabled = false;
    this.list = List.of();
    this.count2 = 0;
  }

  public void update2() {
    this.update();
  }

  public void update3() {
    ClientData.ensureInitialized();
    if (list2 == null) {
      if (++this.count2 % 10 == 0) {
        this.scan();
      }
    }
  }

  public void scan() {
    ClientData.ensureInitialized();

    MinecraftClient instance = MinecraftClient.getInstance();
    ClientPlayNetworkHandler networkHandler = instance.getNetworkHandler();
    if (networkHandler != null && instance.player != null) {
      StaffDetectorData staffDetectorData = this.staffListModule.getStaffDetectorData();
      UUID uuid = instance.player.getUuid();
      HashSet hashSet = new HashSet();

      for (PlayerListEntry playerListEntry : networkHandler.getListedPlayerListEntries()) {
        hashSet.add(playerListEntry.getProfile().id());
      }

      ArrayList arrayList = new ArrayList();

      for (PlayerListEntry playerListEntry2 : networkHandler.getPlayerList()) {
        UUID uuid2 = playerListEntry2.getProfile().id();
        if (!uuid2.equals(uuid)) {
          String text2 = playerListEntry2.getProfile().name();
          if (text2 != null && !text2.isEmpty()) {
            boolean flag =
                playerListEntry2.getGameMode() == GameMode.SPECTATOR || !hashSet.contains(uuid2);
            if (!flag || staffDetectorData.enabled2()) {
              Team scoreboardTeam = playerListEntry2.getScoreboardTeam();
              ClientData clientData =
                  StaffDetector.getClientData(
                      text2,
                      playerListEntry2.getDisplayName(),
                      scoreboardTeam == null ? null : scoreboardTeam.getPrefix(),
                      scoreboardTeam == null ? null : scoreboardTeam.getSuffix(),
                      scoreboardTeam == null ? null : scoreboardTeam.getName(),
                      flag,
                      playerListEntry2.getLatency(),
                      staffDetectorData);
              if (clientData != null) {
                arrayList.add(clientData);
              }
            }
          }
        }
      }

      arrayList.sort(
          Comparator.comparing(ClientData::enabled)
              .thenComparing(Comparator.comparingInt(ClientData::count3).reversed())
              .thenComparing(
                  clientData2 -> {
                    return clientData2.string().toLowerCase(Locale.ROOT);
                  }));
      HashSet set2 = new HashSet();

      for (ClientData clientData3 :
          (java.lang.Iterable<ClientData>) (java.lang.Iterable<?>) (arrayList)) {
        set2.add(clientData3.string());
      }

      if (this.enabled) {
        for (ClientData clientData4 :
            (java.lang.Iterable<ClientData>) (java.lang.Iterable<?>) (arrayList)) {
          if (!this.set.contains(clientData4.string())) {
            this.staffListModule.apply(clientData4);
          }
        }
      }

      this.set = set2;
      this.enabled = true;
      this.list = List.copyOf(arrayList);
    } else {
      this.list = List.of();
      this.set = new HashSet();
      this.enabled = false;
    }
  }

  public static void apply(List list3) {
    ClientData.ensureInitialized();
    list2 = list3 == null ? null : List.copyOf(list3);
  }

  public static void update4() {
    list2 = null;
  }
}
