package bunnythekenzo.client.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import bunnythekenzo.client.setting.ItemSetting;

public final class SelectionGallery implements GalleryModel {
  public static List list;
  public final String string;
  public final Supplier supplier;
  public final Consumer consumer;
  public List list2;

  public SelectionGallery(ItemSetting itemSetting) {
    this(
        itemSetting.getName(),
        itemSetting::getValue,
        (referenceArgument81_0) ->
            itemSetting.apply((net.minecraft.item.Item) referenceArgument81_0));
  }

  public SelectionGallery(String string2, Supplier supplier2, Consumer consumer2) {
    this.string = string2;
    this.supplier = supplier2;
    this.consumer = consumer2;
  }

  public static List getList() {
    if (list == null) {
      ArrayList arrayList = new ArrayList();

      for (Item item : Registries.ITEM) {
        if (item != Items.AIR) {
          arrayList.add(item);
        }
      }

      list = List.copyOf(arrayList);
    }

    return list;
  }

  @Override
  public String getString() {
    return this.string;
  }

  @Override
  public String getString2() {
    return this.computeLong() + " selected";
  }

  @Override
  public long computeLong() {
    return this.supplier.get() == null ? 0L : 1L;
  }

  @Override
  public String getString3() {
    return "Current only";
  }

  @Override
  public String getString4() {
    return "Click to select";
  }

  @Override
  public List getList2() {
    if (this.list2 == null) {
      ArrayList arrayList = new ArrayList(getList().size());

      for (Item item : (java.lang.Iterable<Item>) (java.lang.Iterable<?>) (getList())) {
        arrayList.add(new SelectionGallerySupport(this, item));
      }

      this.list2 = List.copyOf(arrayList);
    }

    return this.list2;
  }
}
