package bunnythekenzo.client.module;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry.Reference;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import bunnythekenzo.client.AutoToolData;
import bunnythekenzo.client.FakeLootDropModule;
import bunnythekenzo.client.InventoryActions;
import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.ChoiceSetting;
import bunnythekenzo.client.setting.NumberSetting;

public final class AutoToolModule extends Module {
  public final ChoiceSetting prioritySetting =
      (ChoiceSetting)
          this.registerSetting(
              new ChoiceSetting(
                  "Priority",
                  "How the best suitable tool is chosen.",
                  "Fastest",
                  new String[] {"Fastest", "Fortune", "Silk Touch", "Durability"}));
  public final BooleanSetting requireCorrectToolSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Require Correct Tool",
                  "Never select a tool that would lose a block's drops.",
                  true));
  public final NumberSetting switchDelaySetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Switch Delay",
                  "Ticks spent mining the same block before switching.",
                  0.0,
                  0.0,
                  10.0,
                  1.0,
                  " ticks"));
  public final BooleanSetting switchBackSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Switch Back", "Restore the slot held before mining.", true));
  public final NumberSetting switchBackDelaySetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Switch Back Delay",
                  "Ticks to wait after mining stops before restoring the slot.",
                  2.0,
                  0.0,
                  20.0,
                  1.0,
                  " ticks"));
  public final BooleanSetting antiBreakSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Anti Break", "Skip tools at or below the durability threshold.", true));
  public final NumberSetting minDurabilitySetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Min Durability",
                  "Lowest remaining durability an automatic switch may use.",
                  5.0,
                  1.0,
                  50.0,
                  1.0,
                  "%"));
  public final BooleanSetting useSwordsSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Use Swords", "Allow swords when they are fastest, such as for cobwebs.", false));
  public final BooleanSetting whileSneakingSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "While Sneaking", "Allow automatic switching while sneak is held.", true));
  public int count = -1;
  public int count2 = -1;
  public int count3;
  public int count4;
  public BlockPos blockPos;

  public AutoToolModule() {
    super(
        "Auto Tool",
        "Selects the best hotbar tool with vanilla packet ordering",
        ModuleCategory.MISC);
    this.switchBackDelaySetting.setVisibilityCondition(this.switchBackSetting::getValue);
    this.minDurabilitySetting.setVisibilityCondition(this.antiBreakSetting::getValue);
  }

  @Override
  public boolean usesCombatTick() {
    return true;
  }

  @Override
  public void onEnable() {
    this.update5();
  }

  @Override
  public void onDisable() {
    this.apply(MinecraftClient.getInstance(), true);
    this.update5();
  }

  @Override
  public void onTick() {
    FakeLootDropModule.ensureInitialized();

    MinecraftClient instance = MinecraftClient.getInstance();
    if (!this.test(instance)) {
      this.apply(instance);
    } else {
      BlockHitResult blockHitResult = (BlockHitResult) instance.crosshairTarget;
      if (!blockHitResult.getBlockPos().equals(this.blockPos)) {
        this.blockPos = blockHitResult.getBlockPos().toImmutable();
        this.count3 = 0;
      } else {
        this.count3++;
      }

      this.count4 = 0;
      if (this.count3 >= this.switchDelaySetting.intValue()) {
        BlockState blockState = instance.world.getBlockState(blockHitResult.getBlockPos());
        if (blockState.isAir()) {
          this.apply(instance);
        } else {
          int selectedSlot = instance.player.getInventory().getSelectedSlot();
          if (this.count >= 0 && selectedSlot != this.count2) {
            this.update4();
          }

          int value2 = this.computeInt(instance, blockState, selectedSlot);
          if (value2 >= 0 && value2 != selectedSlot) {
            if (this.count < 0) {
              this.count = selectedSlot;
            }

            InventoryActions.apply(value2);
            this.count2 = value2;
          }
        }
      }
    }
  }

  public boolean test(MinecraftClient minecraftClient) {
    FakeLootDropModule.ensureInitialized();
    return minecraftClient.player != null
        && minecraftClient.world != null
        && minecraftClient.interactionManager != null
        && minecraftClient.currentScreen == null
        && !minecraftClient.player.isSpectator()
        && minecraftClient.options.attackKey.isPressed()
        && ((Boolean) this.whileSneakingSetting.getValue() || !minecraftClient.player.isSneaking())
        && minecraftClient.crosshairTarget instanceof BlockHitResult
        && minecraftClient.crosshairTarget.getType() == Type.BLOCK;
  }

  public int computeInt(MinecraftClient minecraftClient, BlockState blockState, int intValue) {
    FakeLootDropModule.ensureInitialized();
    boolean flag =
        (Boolean) this.requireCorrectToolSetting.getValue() && blockState.isToolRequired();
    int intValue2 = -1;
    AutoToolData autoToolData = null;

    for (int index = 0; index < 9; index++) {
      int intValue3 = Math.floorMod(intValue + index, 9);
      ItemStack stack = minecraftClient.player.getInventory().getStack(intValue3);
      AutoToolData autoToolData2 = this.getAutoToolData(minecraftClient, stack, blockState, flag);
      if (autoToolData2 != null
          && (autoToolData == null
              || autoToolData2.test(autoToolData, (String) this.prioritySetting.getValue()))) {
        autoToolData = autoToolData2;
        intValue2 = intValue3;
      }
    }

    return intValue2;
  }

  public AutoToolData getAutoToolData(
      MinecraftClient minecraftClient, ItemStack itemStack, BlockState blockState, boolean flag) {
    FakeLootDropModule.ensureInitialized();
    if (!itemStack.isEmpty()
        && ((Boolean) this.useSwordsSetting.getValue() || !itemStack.isIn(ItemTags.SWORDS))) {
      boolean suitableFor = itemStack.isSuitableFor(blockState);
      if (flag && !suitableFor) {
        return null;
      }

      float miningSpeedMultiplier = itemStack.getMiningSpeedMultiplier(blockState);
      if (miningSpeedMultiplier <= 1.0F && !suitableFor) {
        return null;
      }

      double value = computeDouble(itemStack);
      if ((Boolean) this.antiBreakSetting.getValue()
          && itemStack.isDamageable()
          && value <= (Double) this.minDurabilitySetting.getValue()) {
        return null;
      }

      int intValue = computeInt(minecraftClient, itemStack, Enchantments.EFFICIENCY);
      if (miningSpeedMultiplier > 1.0F && intValue > 0) {
        miningSpeedMultiplier += intValue * intValue + 1.0F;
      }

      return new AutoToolData(
          miningSpeedMultiplier,
          suitableFor,
          computeInt(minecraftClient, itemStack, Enchantments.FORTUNE),
          computeInt(minecraftClient, itemStack, Enchantments.SILK_TOUCH),
          value);
    } else {
      return null;
    }
  }

  public static int computeInt(
      MinecraftClient minecraftClient, ItemStack itemStack, RegistryKey registryKey) {
    try {
      Reference orThrow =
          minecraftClient
              .world
              .getRegistryManager()
              .getOrThrow(RegistryKeys.ENCHANTMENT)
              .getOrThrow(registryKey);
      return EnchantmentHelper.getLevel(orThrow, itemStack);
    } catch (RuntimeException failure) {
      return 0;
    }
  }

  public static double computeDouble(ItemStack itemStack) {
    FakeLootDropModule.ensureInitialized();
    return itemStack.isDamageable() && itemStack.getMaxDamage() > 0
        ? 100.0 * (itemStack.getMaxDamage() - itemStack.getDamage()) / itemStack.getMaxDamage()
        : 100.0;
  }

  public void apply(MinecraftClient minecraftClient) {
    FakeLootDropModule.ensureInitialized();
    this.blockPos = null;
    this.count3 = 0;
    if (this.count >= 0) {
      if (!(Boolean) this.switchBackSetting.getValue()) {
        this.update4();
      } else {
        if (++this.count4 > this.switchBackDelaySetting.intValue()) {
          this.apply(minecraftClient, false);
        }
      }
    }
  }

  public void apply(MinecraftClient minecraftClient, boolean flag) {
    FakeLootDropModule.ensureInitialized();
    if (this.count >= 0
        && minecraftClient.player != null
        && minecraftClient.player.getInventory().getSelectedSlot() == this.count2) {
      InventoryActions.apply(this.count);
    }

    this.update4();
    if (flag) {
      this.blockPos = null;
      this.count3 = 0;
    }
  }

  public void update4() {
    this.count = -1;
    this.count2 = -1;
    this.count4 = 0;
  }

  public void update5() {
    this.update4();
    this.blockPos = null;
    this.count3 = 0;
  }
}
