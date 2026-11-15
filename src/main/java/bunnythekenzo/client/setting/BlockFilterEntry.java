package bunnythekenzo.client.setting;

import net.minecraft.block.Block;
import net.minecraft.util.Identifier;

public final class BlockFilterEntry {
  public final Identifier identifier;
  public final Block block;
  public final BooleanSetting enabledSetting;
  public final ColorSetting colorSetting;

  public BlockFilterEntry(Identifier identifier2, Block block2, boolean flag, int intValue) {
    this.identifier = identifier2;
    this.block = block2;
    this.enabledSetting = new BooleanSetting("Enabled", "Highlight this block", flag);
    this.colorSetting =
        new ColorSetting(BlockListSetting.getString(block2), "Highlight color", intValue);
  }

  public Identifier getIdentifier() {
    return this.identifier;
  }

  public Block getBlock() {
    return this.block;
  }

  public String getString() {
    return this.colorSetting.getName();
  }
}
