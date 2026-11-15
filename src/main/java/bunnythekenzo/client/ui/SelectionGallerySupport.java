package bunnythekenzo.client.ui;

import java.util.Locale;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import bunnythekenzo.client.setting.ColorSetting;

public final class SelectionGallerySupport implements GalleryItem {
  public final Item item;
  public final Identifier identifier;
  public final String string;
  public final String string2;
  public ItemStack itemStack;
  public final SelectionGallery selectionGallery;

  public SelectionGallerySupport(SelectionGallery selectionGallery2, Item item2) {
    this.selectionGallery = selectionGallery2;
    this.item = item2;
    this.identifier = Registries.ITEM.getId(item2);
    this.string = item2.getName().getString();
    this.string2 =
        (this.string + " " + (this.identifier == null ? "" : this.identifier.toString()))
            .toLowerCase(Locale.ROOT);
  }

  @Override
  public ItemStack getItemStack() {
    if (this.itemStack == null) {
      this.itemStack = new ItemStack(this.item);
    }

    return this.itemStack;
  }

  @Override
  public Identifier getIdentifier() {
    return this.identifier;
  }

  @Override
  public String getString2() {
    return this.string;
  }

  @Override
  public boolean test(String text) {
    return this.string2.contains(text);
  }

  @Override
  public boolean test() {
    return this.selectionGallery.supplier.get() == this.item;
  }

  @Override
  public boolean test2() {
    return this.selectionGallery.supplier.get() == this.item;
  }

  @Override
  public boolean test3() {
    return this.selectionGallery.supplier.get() == this.item;
  }

  @Override
  public int computeInt() {
    return -49508;
  }

  @Override
  public void update() {
    this.selectionGallery.consumer.accept(this.item);
  }

  @Override
  public ColorSetting getColorSetting() {
    return null;
  }
}
