package bunnythekenzo.client.setting;

import java.util.function.IntSupplier;

public final class BlockListSettingView extends SettingView {
  public final BlockListSetting blockListSetting;
  public final IntSupplier intSupplier;

  public BlockListSettingView(BlockListSetting blockListSetting2, IntSupplier intSupplier2) {
    super(blockListSetting2.getName(), blockListSetting2.getDescription());
    this.blockListSetting = blockListSetting2;
    this.intSupplier = intSupplier2;
  }

  @Override
  public boolean isBound() {
    return true;
  }

  public BlockListSetting getBlockListSetting() {
    return this.blockListSetting;
  }

  public IntSupplier getIntSupplier() {
    return this.intSupplier;
  }

  public String getString3() {
    long longValue = this.blockListSetting.enabledCount();
    int value = this.blockListSetting.computeInt();
    return value == 0
        ? "None chosen"
        : longValue + " of " + value + (value == 1 ? " block" : " blocks");
  }
}
