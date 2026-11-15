package bunnythekenzo.client.setting;

public final class ItemSettingView extends SettingView {
  public final ItemSetting itemSetting;

  public ItemSettingView(ItemSetting itemSetting2) {
    super(itemSetting2.getName(), itemSetting2.getDescription());
    this.itemSetting = itemSetting2;
  }

  @Override
  public boolean isBound() {
    return true;
  }

  public ItemSetting getItemSetting() {
    return this.itemSetting;
  }

  public String getString3() {
    return this.itemSetting.getString();
  }
}
