package bunnythekenzo.client.setting;

import java.util.List;

public final class ItemReplacementSettingView extends SettingView {
  public final ItemReplacementSetting itemReplacementSetting;

  public ItemReplacementSettingView(ItemReplacementSetting itemReplacementSetting2) {
    super(itemReplacementSetting2.getName(), itemReplacementSetting2.getDescription());
    this.itemReplacementSetting = itemReplacementSetting2;
  }

  @Override
  public boolean isBound() {
    return true;
  }

  public ItemReplacementSetting getItemReplacementSetting() {
    return this.itemReplacementSetting;
  }

  public String getString3() {
    return this.itemReplacementSetting.computeInt()
        + " active / "
        + ((List) this.itemReplacementSetting.getValue()).size()
        + " rules";
  }
}
