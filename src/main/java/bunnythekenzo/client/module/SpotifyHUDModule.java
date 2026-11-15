package bunnythekenzo.client.module;

import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.ChoiceSetting;

public class SpotifyHUDModule extends Module {
  public final ChoiceSetting sourceSetting =
      (ChoiceSetting)
          this.registerSetting(
              new ChoiceSetting(
                  "Source",
                  "Demo shows sample data. Auto starts the local Spotify bridge on Windows.",
                  "Demo",
                  new String[] {"Auto", "Demo"}));
  public final BooleanSetting controlsSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Controls",
                  "Previous / play / next; click with chat (T) or the HUD editor open",
                  true));
  public final BooleanSetting volumeSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Volume",
                  "Drag or scroll to change Spotify's app volume; click the speaker to mute",
                  true));
  public final BooleanSetting hideWhenIdleSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Hide When Idle", "Hide the card when nothing plays", true));

  public SpotifyHUDModule() {
    super("SpotifyHUD", "Now playing — skip and seek from the HUD", ModuleCategory.getInstance());
    this.setEnabled(true);
  }
}
