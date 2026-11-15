package bunnythekenzo.client.module;

import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.NumberSetting;

public class NameTagsModule extends Module {
  public final BooleanSetting playersSetting =
      (BooleanSetting)
          this.registerSetting(new BooleanSetting("Players", "Tag players with their IGN", true));
  public final BooleanSetting selfSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Self", "Also tag your own player (best in 3rd person)", false));
  public final BooleanSetting itemsSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Items", "Tag dropped items on the ground", true));
  public final BooleanSetting hidePlayerTagsSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Hide Player Tags",
                  "Remove the vanilla nametag of other players — only the module's shows",
                  true));
  public final BooleanSetting hideOwnTagSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Hide Own Tag",
                  "Remove your own original nametag — only the module's Self tag shows",
                  true));
  public final BooleanSetting hideOtherTagsSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Hide Other Tags",
                  "Also remove vanilla nametags of mobs, armor stands & frames",
                  false));
  public final BooleanSetting armorSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Armor", "Show a player's equipped armor above their tag", true));
  public final BooleanSetting heldItemSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Held Item", "Show what a player is holding above their tag", true));
  public final BooleanSetting healthSetting =
      (BooleanSetting)
          this.registerSetting(new BooleanSetting("Health", "Health bar under player tags", true));
  public final BooleanSetting distanceSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Distance", "Append the distance in blocks", true));
  public final BooleanSetting itemAmountSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Item Amount", "Show the stack size on item tags", true));
  public final NumberSetting scaleSetting =
      (NumberSetting)
          this.registerSetting(new NumberSetting("Scale", "Tag size", 1.0, 0.5, 2.0, 0.1, "x"));
  public final NumberSetting opacitySetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting("Opacity", "Tag transparency", 100.0, 10.0, 100.0, 5.0, "%"));
  public final NumberSetting rangeSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Range", "Only tag within this distance", 64.0, 8.0, 256.0, 4.0, "m"));

  public NameTagsModule() {
    super("NameTags", "HUD-styled nametags for players & items", ModuleCategory.MISC);
  }
}
