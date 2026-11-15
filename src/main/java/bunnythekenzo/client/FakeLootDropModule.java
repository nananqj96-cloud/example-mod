package bunnythekenzo.client;

import java.util.List;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import bunnythekenzo.client.module.Module;
import bunnythekenzo.client.module.ModuleCategory;
import bunnythekenzo.client.module.ModuleManager;
import bunnythekenzo.client.setting.ItemReplacementSetting;

public final class FakeLootDropModule extends Module {
  public final EquipmentPreview equipmentPreview = new EquipmentPreview(this);
  public final ItemReplacementSetting itemReplacementsSetting =
      (ItemReplacementSetting)
          this.registerSetting(
              new ItemReplacementSetting(
                  "Item Replacements",
                  "Manage real items and the fake items shown in their place"));
  public List list;
  public static String string;

  public FakeLootDropModule() {
    super(
        "Fake Loot Drop",
        "Disguise items, item frames and all placed blocks. Right-click air or Shift-click fake"
            + " armor to wear locally; double Space with Elytra to preview flight, Shift to"
            + " return.",
        ModuleCategory.MISC);
  }

  public boolean test(ItemStack itemStack) {
    return itemStack != null
        && !itemStack.isEmpty()
        && this.itemReplacementsSetting.getItem(itemStack.getItem()) != null;
  }

  public ItemStack getItemStack(ItemStack itemStack) {
    return this.isEnabled() && this.test(itemStack)
        ? new ItemStack(
            this.itemReplacementsSetting.getItem(itemStack.getItem()), itemStack.getCount())
        : itemStack;
  }

  public static ItemStack getItemStack2(ItemStack itemStack) {

    ModuleManager moduleManager = ClientInitializer.getModuleManager();
    return moduleManager != null && moduleManager.fakeLootDropModule != null
        ? moduleManager.fakeLootDropModule.getItemStack(itemStack)
        : itemStack;
  }

  public static FakeLootDropModule getFakeLootDropModule() {

    ModuleManager moduleManager = ClientInitializer.getModuleManager();
    return moduleManager == null ? null : moduleManager.fakeLootDropModule;
  }

  public Item getItem(BlockState blockState) {
    if (this.isEnabled() && !blockState.isAir()) {
      Item item = this.itemReplacementsSetting.getItem(blockState.getBlock().asItem());
      return item != null && !(item instanceof BlockItem) ? item : null;
    } else {
      return null;
    }
  }

  public BlockState getBlockState(BlockState blockState) {
    if (this.isEnabled() && !blockState.isAir()) {
      Item item = this.itemReplacementsSetting.getItem(blockState.getBlock().asItem());
      if (item == null) {
        return blockState;
      } else {
        return item instanceof BlockItem blockItem
            ? blockItem.getBlock().getStateWithProperties(blockState)
            : Blocks.AIR.getDefaultState();
      }
    } else {
      return blockState;
    }
  }

  @Override
  public void onEnable() {
    this.update4();
  }

  @Override
  public void onDisable() {
    this.equipmentPreview.update3();
    this.update4();
  }

  @Override
  public void onTick() {
    if (this.list != this.itemReplacementsSetting.getValue()) {
      this.update4();
    }

    this.equipmentPreview.tick();
    FakeBlockItemRenderer.checkLevel();
  }

  public void update4() {

    this.list = (List) this.itemReplacementsSetting.getValue();
    FakeBlockItemRenderer.update();
    MinecraftClient instance = MinecraftClient.getInstance();
    if (instance.world != null) {
      instance.worldRenderer.reload();
    }
  }

  public static void setString(String string2) {
    string = string2;
  }

  public static String getString() {
    return string;
  }

  static {
    setString(null);
  }

  public static void ensureInitialized() {}
}
