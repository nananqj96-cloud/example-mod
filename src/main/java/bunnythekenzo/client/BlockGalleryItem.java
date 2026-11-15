package bunnythekenzo.client;

import java.util.Locale;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import bunnythekenzo.client.setting.BlockFilterEntry;
import bunnythekenzo.client.setting.BlockListSetting;
import bunnythekenzo.client.setting.ColorSetting;
import bunnythekenzo.client.ui.GalleryItem;

public final class BlockGalleryItem implements GalleryItem {
  public final Block block;
  public final String string;
  public ItemStack itemStack;
  public final BlockGalleryModel blockGalleryModel;

  public BlockGalleryItem(BlockGalleryModel blockGalleryModel2, Block block2) {
    this.blockGalleryModel = blockGalleryModel2;
    this.block = block2;
    Identifier id = Registries.BLOCK.getId(block2);
    String text = id != null ? id.getPath() : "";
    String text2 = id != null ? id.getNamespace() : "";
    this.string =
        (text + " " + text2 + " " + BlockListSetting.getString(block2)).toLowerCase(Locale.ROOT);
  }

  @Override
  public ItemStack getItemStack() {
    if (this.itemStack == null) {
      Item item = this.block.asItem();
      this.itemStack = new ItemStack(item == Items.AIR ? Items.BARRIER : item);
    }

    return this.itemStack;
  }

  @Override
  public Identifier getIdentifier() {
    Identifier id = Registries.BLOCK.getId(this.block);
    return id != null ? id : Registries.ITEM.getId(Items.BARRIER);
  }

  @Override
  public String getString2() {
    return BlockListSetting.getString(this.block);
  }

  @Override
  public boolean test(String text) {
    return this.string.contains(text);
  }

  @Override
  public boolean test() {
    return this.blockGalleryModel.blockListSetting.getBlockFilterEntry(this.block) != null;
  }

  @Override
  public boolean test2() {
    BlockFilterEntry choiceSettingSupport =
        this.blockGalleryModel.blockListSetting.getBlockFilterEntry(this.block);
    return choiceSettingSupport != null && (Boolean) choiceSettingSupport.enabledSetting.getValue();
  }

  @Override
  public boolean test3() {
    return this.blockGalleryModel.blockListSetting.getBlockFilterEntry(this.block) != null;
  }

  @Override
  public int computeInt() {
    BlockFilterEntry choiceSettingSupport =
        this.blockGalleryModel.blockListSetting.getBlockFilterEntry(this.block);
    return choiceSettingSupport != null
        ? choiceSettingSupport.colorSetting.getInteger()
        : this.blockGalleryModel.intSupplier.getAsInt();
  }

  @Override
  public void update() {
    BlockFilterEntry choiceSettingSupport =
        this.blockGalleryModel.blockListSetting.getBlockFilterEntry(this.block);
    if (choiceSettingSupport == null) {
      this.blockGalleryModel.blockListSetting.getBlockFilterEntry(
          this.block, true, this.blockGalleryModel.intSupplier.getAsInt());
    } else {
      choiceSettingSupport.enabledSetting.toggle();
    }
  }

  @Override
  public ColorSetting getColorSetting() {
    BlockFilterEntry choiceSettingSupport =
        this.blockGalleryModel.blockListSetting.getBlockFilterEntry(this.block);
    if (choiceSettingSupport == null) {
      choiceSettingSupport =
          this.blockGalleryModel.blockListSetting.getBlockFilterEntry(
              this.block, true, this.blockGalleryModel.intSupplier.getAsInt());
    }

    return choiceSettingSupport != null ? choiceSettingSupport.colorSetting : null;
  }
}
