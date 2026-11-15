package bunnythekenzo.client.module;

import bunnythekenzo.client.BlockEntityScanner;
import bunnythekenzo.client.setting.BlockListSetting;
import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.ChoiceSetting;
import bunnythekenzo.client.setting.NumberSetting;

public class BlockESPModule extends Module {
  public final BlockListSetting targetBlocksSetting =
      (BlockListSetting)
          this.registerSetting(
              new BlockListSetting("Target Blocks", "Pick which blocks to highlight"));
  public final ChoiceSetting modeSetting =
      (ChoiceSetting)
          this.registerSetting(
              new ChoiceSetting(
                  "Mode", "Zerio box style", "Outline", new String[] {"Outline", "Full"}));
  public final BooleanSetting tracersSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Tracers", "Zerio screen-space tracers to nearby blocks", false));
  public final NumberSetting highlightAlphaSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting("Highlight Alpha", "Box opacity", 255.0, 0.0, 255.0, 5.0));
  public final NumberSetting rangeExtraChunksSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Range Extra Chunks",
                  "Extra scan radius beyond render distance",
                  1.0,
                  0.0,
                  4.0,
                  1.0));
  public final NumberSetting maxYLevelSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Max Y Level",
                  "Only scan blocks at or below this Y level",
                  319.0,
                  -64.0,
                  319.0,
                  1.0));

  public BlockESPModule() {
    super("BlockESP", "Highlights chosen blocks through walls", ModuleCategory.RENDER);
    this.targetBlocksSetting.update3();
  }

  @Override
  public void onTick() {
    BlockEntityScanner.scan(this);
  }

  @Override
  public void onDisable() {
    BlockEntityScanner.update();
  }
}
