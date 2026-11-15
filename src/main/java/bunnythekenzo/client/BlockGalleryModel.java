package bunnythekenzo.client;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntSupplier;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import bunnythekenzo.client.setting.BlockListSetting;
import bunnythekenzo.client.ui.GalleryModel;

public final class BlockGalleryModel implements GalleryModel {
  public static List list;
  public final BlockListSetting blockListSetting;
  public final IntSupplier intSupplier;
  public final String string;
  public List list2;

  public BlockGalleryModel(
      BlockListSetting blockListSetting2, IntSupplier intSupplier2, String string2) {
    this.blockListSetting = blockListSetting2;
    this.intSupplier = intSupplier2;
    this.string = string2;
  }

  public static List getList() {
    if (list == null) {
      ArrayList list3 = new ArrayList();

      for (Block block : Registries.BLOCK) {
        if (block != Blocks.AIR && block != Blocks.CAVE_AIR && block != Blocks.VOID_AIR) {
          list3.add(block);
        }
      }

      list = list3;
    }

    return list;
  }

  @Override
  public String getString() {
    return this.string;
  }

  @Override
  public long computeLong() {
    return this.blockListSetting.enabledCount();
  }

  @Override
  public List getList2() {
    if (this.list2 == null) {
      ArrayList list22 = new ArrayList(getList().size());

      for (Block block : (java.lang.Iterable<Block>) (java.lang.Iterable<?>) (getList())) {
        list22.add(new BlockGalleryItem(this, block));
      }

      this.list2 = list22;
    }

    return this.list2;
  }
}
