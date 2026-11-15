package bunnythekenzo.client;

import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import bunnythekenzo.client.setting.ColorSetting;
import bunnythekenzo.client.setting.EntityFilterEntry;
import bunnythekenzo.client.ui.GalleryItem;

public final class EntityGalleryItem implements GalleryItem {
  public final EntityFilterEntry choiceSettingSupport2;
  public final ItemStack itemStack;

  public EntityGalleryItem(EntityFilterEntry choiceSettingSupport22) {
    this.choiceSettingSupport2 = choiceSettingSupport22;
    this.itemStack = new ItemStack(choiceSettingSupport22.getItem());
  }

  @Override
  public ItemStack getItemStack() {
    return this.itemStack;
  }

  @Override
  public Identifier getIdentifier() {
    return Registries.ITEM.getId(this.choiceSettingSupport2.getItem());
  }

  @Override
  public String getString2() {
    return this.choiceSettingSupport2.getString2();
  }

  @Override
  public boolean test(String text) {
    return this.choiceSettingSupport2.test(text);
  }

  @Override
  public boolean test() {
    return true;
  }

  @Override
  public boolean test2() {
    return (Boolean) this.choiceSettingSupport2.enabledSetting.getValue();
  }

  @Override
  public boolean test3() {
    return (Boolean) this.choiceSettingSupport2.enabledSetting.getValue();
  }

  @Override
  public int computeInt() {
    return this.choiceSettingSupport2.colorSetting.getInteger();
  }

  @Override
  public void update() {
    this.choiceSettingSupport2.enabledSetting.toggle();
  }

  @Override
  public ColorSetting getColorSetting() {
    return this.choiceSettingSupport2.colorSetting;
  }
}
