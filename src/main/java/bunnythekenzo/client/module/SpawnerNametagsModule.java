package bunnythekenzo.client.module;

import net.minecraft.block.Blocks;
import bunnythekenzo.client.BlockPredicateScanner;
import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.NumberSetting;

public class SpawnerNametagsModule extends Module {
  public final BooleanSetting nametagSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Nametag", "Floating type + distance label", true));
  public final BooleanSetting boxSetting =
      (BooleanSetting)
          this.registerSetting(new BooleanSetting("Box", "Through-wall box on the spawner", true));
  public final BooleanSetting rangeRingSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Range Ring", "Draw the 16-block activation ring", true));
  public final BooleanSetting distanceSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Distance", "Append the distance in blocks", true));
  public final NumberSetting opacitySetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting("Opacity", "Nametag transparency", 100.0, 10.0, 100.0, 5.0, "%"));
  public final BooleanSetting tracersSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Tracers", "Thin lines from the crosshair to nearby spawners", false));
  public final BlockPredicateScanner blockPredicateScanner =
      new BlockPredicateScanner(
          blockState -> {
            return ((net.minecraft.block.BlockState) blockState).isOf(Blocks.SPAWNER);
          },
          12,
          8,
          400,
          96.0);
  public static int count;

  public SpawnerNametagsModule() {
    super("SpawnerNametags", "Shows spawner type + activation range", ModuleCategory.RENDER);
  }

  @Override
  public void onTick() {
    this.blockPredicateScanner.update2();
  }

  @Override
  public void onDisable() {
    this.blockPredicateScanner.update();
  }

  public static void setCount(int count2) {
    count = count2;
  }

  public static int getCount() {
    return count;
  }

  public static int computeInt() {
    int intValue = getCount();
    return intValue == 0 ? 87 : 0;
  }

  static {
    setCount(0);
  }
}
