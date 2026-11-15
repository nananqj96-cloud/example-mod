package bunnythekenzo.client.module;

import java.util.HashSet;
import java.util.Set;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import bunnythekenzo.client.StorageScanner;
import bunnythekenzo.client.StorageType;
import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.ChoiceSetting;
import bunnythekenzo.client.setting.EntityTypeSetting;
import bunnythekenzo.client.setting.NumberSetting;

public class StorageESPModule extends Module {
  public final ChoiceSetting modeSetting =
      (ChoiceSetting)
          this.registerSetting(
              new ChoiceSetting(
                  "Mode",
                  "Box style — hollow outline or translucent fill",
                  "Outline",
                  new String[] {"Outline", "Full"}));
  public final NumberSetting rangeSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Range", "Max distance a container is highlighted", 128.0, 16.0, 256.0, 8.0));
  public final NumberSetting maxYLevelSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Max Y Level",
                  "Only scan containers at or below this Y level",
                  319.0,
                  -64.0,
                  319.0,
                  1.0));
  public final NumberSetting highlightAlphaSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting("Highlight Alpha", "Box opacity (0-255)", 200.0, 0.0, 255.0, 1.0));
  public final BooleanSetting tracersSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Tracers", "Thin lines from the crosshair to nearby containers", false));
  public final EntityTypeSetting containersSetting =
      (EntityTypeSetting)
          this.registerSetting(
              new EntityTypeSetting("Containers", "Which container types to highlight"));
  public final BooleanSetting hideOpenedSetting;
  public final Set set = new HashSet();

  public StorageESPModule() {
    super("StorageESP", "Highlights chests, barrels, shulkers", ModuleCategory.RENDER);

    for (StorageType storageType : StorageType.values()) {
      this.containersSetting.getEntityFilterEntry(
          storageType.string,
          storageType.label,
          storageType.icon,
          storageType.defaultEnabled,
          storageType.defaultColor);
    }

    this.hideOpenedSetting =
        (BooleanSetting)
            this.registerSetting(
                new BooleanSetting(
                    "Hide Opened", "Don't highlight containers you've already opened", false));
  }

  public boolean test(StorageType storageType) {
    return this.containersSetting.test(storageType.string);
  }

  public int computeInt(StorageType storageType) {
    return this.containersSetting.computeInt(storageType.string);
  }

  public boolean test() {
    return (Boolean) this.hideOpenedSetting.getValue();
  }

  public boolean test(int intValue, int intValue2, int intValue3) {
    return !this.set.isEmpty()
        && this.set.contains(BlockPos.asLong(intValue, intValue2, intValue3));
  }

  public void apply(BlockPos blockPos) {
    if (blockPos != null) {
      MinecraftClient instance = MinecraftClient.getInstance();
      if (instance.world != null) {
        this.set.add(blockPos.asLong());
        BlockEntity blockEntity = instance.world.getBlockEntity(blockPos);
        if (blockEntity instanceof ChestBlockEntity) {
          BlockState blockState = instance.world.getBlockState(blockPos);
          if (blockState.getBlock() instanceof ChestBlock) {
            ChestType chestType = blockState.get(ChestBlock.CHEST_TYPE);
            if (chestType != ChestType.SINGLE) {
              Direction direction = blockState.get(ChestBlock.FACING);
              BlockPos blockPos2 =
                  blockPos.offset(
                      chestType == ChestType.LEFT
                          ? direction.rotateYClockwise()
                          : direction.rotateYCounterclockwise());
              this.set.add(blockPos2.asLong());
            }
          }
        }
      }
    }
  }

  @Override
  public void onEnable() {
    this.set.clear();
    StorageScanner.update();
  }

  @Override
  public void onTick() {
    StorageScanner.scan(this);
  }

  @Override
  public void onDisable() {
    StorageScanner.update();
  }
}
