package bunnythekenzo.client;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

public enum StorageType {
  CHEST("chest", "Chest", Items.CHEST, -22016, true),
  TRAPPED("trapped", "Trapped Chest", Items.TRAPPED_CHEST, -65536, true),
  ENDER("ender", "Ender Chest", Items.ENDER_CHEST, -8912641, true),
  SHULKER("shulker", "Shulker Box", Items.SHULKER_BOX, -47873, true),
  BARREL("barrel", "Barrel", Items.BARREL, -7842560, true),
  SPAWNER("spawner", "Spawner", Items.SPAWNER, -16711936, true),
  HOPPER("hopper", "Hopper", Items.HOPPER, -7829368, false),
  FURNACE("furnace", "Furnace", Items.FURNACE, -7566196, false);

  public final String string;
  public final String label;
  public final Item icon;
  public final int defaultColor;
  public final boolean defaultEnabled;

  StorageType(
      String string2, String label2, Item icon2, int defaultColor2, boolean defaultEnabled2) {
    this.string = string2;
    this.label = label2;
    this.icon = icon2;
    this.defaultColor = defaultColor2;
    this.defaultEnabled = defaultEnabled2;
  }
}
