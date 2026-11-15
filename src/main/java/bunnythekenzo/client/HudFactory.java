package bunnythekenzo.client;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import bunnythekenzo.client.hud.ArmorHud;
import bunnythekenzo.client.hud.ArraylistHud;
import bunnythekenzo.client.hud.HudElement;
import bunnythekenzo.client.hud.KeystrokesHud;
import bunnythekenzo.client.hud.PotionsHud;
import bunnythekenzo.client.hud.RadarHud;
import bunnythekenzo.client.hud.RegionMapHud;
import bunnythekenzo.client.hud.SpotifyHud;
import bunnythekenzo.client.hud.StaffListHud;
import bunnythekenzo.client.hud.WatermarkHud;
import bunnythekenzo.client.module.HUDModule;
import bunnythekenzo.client.module.ModuleManager;

public final class HudFactory {
  public static List create() {
    ModuleManager moduleManager = ClientInitializer.getModuleManager();
    ThemeManager themeManager = ClientInitializer.getThemeManager();
    if (moduleManager != null && themeManager != null) {
      HUDModule hUDModule = moduleManager.hUDModule;
      ArrayList arrayList = new ArrayList();
      arrayList.add(
          new WatermarkHud(
              themeManager, HudElement.getHudElementContract(hUDModule.watermarkSetting)));
      arrayList.add(
          new ArraylistHud(
              moduleManager,
              hUDModule,
              themeManager,
              HudElement.getHudElementContract(hUDModule.arrayListSetting)));
      arrayList.add(
          new TextValueHudElement(
              "fps",
              "FPS",
              "Frames per second",
              themeManager,
              "FPS",
              () -> {
                return Integer.toString(MinecraftClient.getInstance().getCurrentFps());
              },
              0.006F,
              0.985F,
              HudElement.getHudElementContract(hUDModule.fpsSetting)));
      arrayList.add(
          new TextValueHudElement(
              "ping",
              "Ping",
              "Round trip to the server",
              themeManager,
              "Ping",
              HudFactory::getString2,
              0.055F,
              0.985F,
              HudElement.getHudElementContract(hUDModule.pingSetting)));
      arrayList.add(
          new TextValueHudElement(
              "coords",
              "Coordinates",
              "Block position",
              themeManager,
              "XYZ",
              HudFactory::getString,
              0.115F,
              0.985F,
              HudElement.getHudElementContract(hUDModule.coordinatesSetting)));
      arrayList.add(
          new TextValueHudElement(
              "direction",
              "Direction",
              "Which way you are facing",
              themeManager,
              "Facing",
              HudFactory::getString3,
              0.24F,
              0.985F,
              HudElement.getHudElementContract(hUDModule.directionSetting)));
      arrayList.add(
          new TextValueHudElement(
              "tps",
              "TPS",
              "Server tick rate estimate",
              themeManager,
              "TPS",
              () -> {
                return String.format("%.1f", ServerTickRate.getTicksPerSecond());
              },
              0.33F,
              0.985F,
              HudElement.getHudElementContract(hUDModule.tpsSetting)));
      arrayList.add(
          new TextValueHudElement(
              "cps",
              "CPS",
              "Clicks per second, left and right",
              themeManager,
              "CPS",
              () -> {
                return ClickRateTracker.getClicksPerSecond(0)
                    + " | "
                    + ClickRateTracker.getClicksPerSecond(1);
              },
              0.4F,
              0.985F,
              HudElement.getHudElementContract(hUDModule.cpsSetting)));
      arrayList.add(
          new ArmorHud(themeManager, HudElement.getHudElementContract(hUDModule.armorSetting)));
      arrayList.add(
          new PotionsHud(themeManager, HudElement.getHudElementContract(hUDModule.potionsSetting)));
      arrayList.add(
          new KeystrokesHud(
              themeManager, HudElement.getHudElementContract(hUDModule.keystrokesSetting)));
      arrayList.add(
          new RadarHud(
              hUDModule,
              moduleManager.susChunkFinderModule,
              themeManager,
              HudElement.getHudElementContract(hUDModule.radarSetting)));
      arrayList.add(new RegionMapHud(moduleManager.regionMapModule, themeManager));
      arrayList.add(new StaffListHud(moduleManager.staffListModule, themeManager));
      arrayList.add(
          new SpotifyHud(
              moduleManager.spotifyHUDModule, ClientInitializer.getSpotifyBridge(), themeManager));
      arrayList.add(ClientInitializer.getNotificationsHud());
      return List.copyOf(arrayList);
    } else {
      return List.of();
    }
  }

  public static String getString() {
    ClientPlayerEntity player = MinecraftClient.getInstance().player;
    if (player == null) {
      return "0, 0, 0";
    }

    BlockPos blockPos = player.getBlockPos();
    return blockPos.getX() + ", " + blockPos.getY() + ", " + blockPos.getZ();
  }

  public static String getString2() {
    MinecraftClient instance = MinecraftClient.getInstance();
    if (instance.player != null && instance.getNetworkHandler() != null) {
      PlayerListEntry playerListEntry =
          instance.getNetworkHandler().getPlayerListEntry(instance.player.getUuid());
      return playerListEntry == null ? "0ms" : playerListEntry.getLatency() + "ms";
    } else {
      return "0ms";
    }
  }

  public static String getString3() {
    ClientPlayerEntity player = MinecraftClient.getInstance().player;
    if (player == null) {
      return "N";
    }

    Direction horizontalFacing = player.getHorizontalFacing();

    return switch (HudFactorySupport.countArray[horizontalFacing.ordinal()]) {
      case 1 -> "N  -Z";
      case 2 -> "S  +Z";
      case 3 -> "W  -X";
      case 4 -> "E  +X";
      default -> horizontalFacing.getId().toUpperCase();
    };
  }
}
