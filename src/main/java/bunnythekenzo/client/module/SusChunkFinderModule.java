package bunnythekenzo.client.module;

import bunnythekenzo.client.LookupSupport3;
import bunnythekenzo.client.SuspiciousChunkScanner;
import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.ChoiceSetting;
import bunnythekenzo.client.setting.NumberSetting;

public class SusChunkFinderModule extends Module {
  public final NumberSetting sensitivitySetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                      "Sensitivity",
                      "Higher = stricter: more weighted evidence before a chunk flags",
                      3.0,
                      1.0,
                      10.0,
                      1.0)
                  .withFormatter(
                      doubleValue -> {
                        return (int) doubleValue + " (" + (int) doubleValue * 5 + ")";
                      }));
  public final BooleanSetting amethystSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Amethyst",
                  "Hidden grown clusters via Zerio-style block-light halo detection",
                  true));
  public final BooleanSetting kelpSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Kelp", "Fully grown / unusually tall kelp columns", true));
  public final BooleanSetting bambooSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Bamboo", "Fully grown, max-height bamboo", true));
  public final BooleanSetting berriesSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Berries", "Sweet berry bushes at max growth stage", true));
  public final BooleanSetting vinesSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Vines", "Vines grown far down from their support", true));
  public final BooleanSetting dripstoneSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Dripstone", "Dripstone spikes longer than natural generation", true));
  public final NumberSetting scanSpeedSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting("Scan Speed", "Chunks scanned per tick", 10.0, 2.0, 24.0, 1.0));
  public final NumberSetting renderYSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Render Y",
                  "Height the flat chunk highlights render at",
                  100.0,
                  -64.0,
                  320.0,
                  1.0));
  public final NumberSetting fillOpacitySetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Fill Opacity", "Fill opacity of the chunk quads", 90.0, 0.0, 255.0, 1.0));
  public final BooleanSetting smartModeSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Smart Mode", "Merge nearby flags into zones with a centroid marker", true));
  public final NumberSetting mergeRadiusSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Merge Radius",
                  "Flags within this many chunks merge into one zone",
                  3.0,
                  1.0,
                  8.0,
                  1.0,
                  " ch"));
  public final BooleanSetting centroidMarkerSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Centroid Marker",
                  "Mark each zone's weighted centre — the likely base spot",
                  true));
  public final BooleanSetting showOnRadarSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Show on Radar",
                  "Pulse sus zones on the Radar; far zones clamp to the edge",
                  true));
  public final ChoiceSetting notificationsSetting =
      (ChoiceSetting)
          this.registerSetting(
              new ChoiceSetting(
                  "Notifications",
                  "Announce each new zone once; Off disables messages and sounds",
                  "Toast",
                  new String[] {"Toast", "Chat", "Off"}));
  public final BooleanSetting notificationSoundSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Notification Sound", "Play a sound when a new zone toast appears", true));
  public final SuspiciousChunkScanner suspiciousChunkScanner = new SuspiciousChunkScanner(this);

  public SusChunkFinderModule() {
    super(
        "SusChunkFinder",
        "Finds long-loaded chunks — bases — via amethyst light & growth",
        ModuleCategory.RENDER);
    this.notificationSoundSetting.setVisibilityCondition(
        () -> {
          return this.notificationsSetting.isSelected("Toast");
        });
  }

  @Override
  public void onTick() {
    this.suspiciousChunkScanner.update2();
  }

  @Override
  public void onDisable() {
    this.suspiciousChunkScanner.update();
    LookupSupport3.update();
  }
}
