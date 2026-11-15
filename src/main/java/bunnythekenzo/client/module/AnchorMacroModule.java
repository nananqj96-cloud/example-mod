package bunnythekenzo.client.module;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResult.Success;
import net.minecraft.util.ActionResult.SwingSource;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.attribute.EnvironmentAttributes;
import bunnythekenzo.client.AnchorMacroData;
import bunnythekenzo.client.AnchorMacroState;
import bunnythekenzo.client.FakeLootDropModule;
import bunnythekenzo.client.PearlEscapeAutomation;
import bunnythekenzo.client.mixin.ClientPlayerInteractionManagerAccessor;
import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.NumberSetting;

public class AnchorMacroModule extends Module {
  public final NumberSetting switchDelaySetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Switch Delay",
                  "Ticks before selecting glowstone or the detonation slot. After charging, slot"
                      + " changes wait at least one tick.",
                  0.0,
                  0.0,
                  20.0,
                  1.0,
                  " ticks"));
  public final NumberSetting glowstoneDelaySetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Glowstone Delay",
                  "Ticks after selecting glowstone before charging the anchor.",
                  0.0,
                  0.0,
                  20.0,
                  1.0,
                  " ticks"));
  public final NumberSetting explodeDelaySetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Explode Delay",
                  "Ticks after selecting the detonation slot before detonating. At most one block"
                      + " use per tick.",
                  0.0,
                  0.0,
                  20.0,
                  1.0,
                  " ticks"));
  public final NumberSetting totemSlotSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Totem Slot",
                  "Preferred hotbar slot (1-9) for detonation. With the totem check on, selects a"
                      + " totem unless your offhand already holds one.",
                  1.0,
                  1.0,
                  9.0,
                  1.0));
  public final BooleanSetting disallowWithoutTotemSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Disallow Without Totem",
                  "Stop when neither your hotbar nor offhand has a totem. Totems in the main"
                      + " inventory do not count. Off allows continuing without a totem.",
                  true));
  public final BooleanSetting autoSwitchBackSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Auto Switch Back",
                  "Restore your original hotbar slot after the sequence, unless you manually"
                      + " changed slots. Disabling restores next tick.",
                  true));
  public final NumberSetting switchBackDelaySetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Switch Back Delay",
                  "Minimum ticks after detonation before restoring your slot; also waits for the"
                      + " anchor to disappear.",
                  2.0,
                  0.0,
                  20.0,
                  1.0,
                  " ticks"));

  public AnchorMacroState anchorMacroState = AnchorMacroState.IDLE;
  public ClientPlayerEntity clientPlayerEntity;
  public ClientWorld clientWorld;
  public BlockPos blockPos;
  public BlockPos blockPos2;
  public int count2 = -1;
  public int count3 = -1;
  public int count4;
  public int count5;
  public int count6;
  public int count7 = -1;
  public int count8;
  public AnchorMacroData anchorMacroData;
  public static String string;

  public AnchorMacroModule() {
    super(
        "Anchor Macro",
        "Right-click a placed anchor to charge and detonate it, with an optional hotbar/offhand"
            + " totem check",
        ModuleCategory.COMBAT);
    this.switchBackDelaySetting.setVisibilityCondition(this.autoSwitchBackSetting::getValue);
  }

  @Override
  public void onEnable() {
    this.update5();
    this.blockPos2 = null;
    this.count5 = 0;
    this.count7 = -1;
  }

  @Override
  public void onDisable() {
    this.apply3(MinecraftClient.getInstance());
    this.update5();
    this.blockPos2 = null;
  }

  @Override
  public void onTick() {
    FakeLootDropModule.ensureInitialized();

    MinecraftClient instance = MinecraftClient.getInstance();
    this.count5++;
    this.update4();
    if (this.isEnabled()) {
      if (this.clientPlayerEntity != null
          && (this.clientPlayerEntity != instance.player || this.clientWorld != instance.world)) {
        this.update5();
        this.blockPos2 = null;
      }

      if (!instance.options.useKey.isPressed() && this.anchorMacroState == AnchorMacroState.IDLE) {
        this.blockPos2 = null;
      }

      if (!test2(instance)) {
        this.apply2(instance);
        this.update5();
      } else if (this.anchorMacroState != AnchorMacroState.IDLE
          && instance.player.getInventory().getSelectedSlot() != this.count3) {
        this.update5();
      } else {
        if (this.anchorMacroState == AnchorMacroState.IDLE && instance.options.useKey.isPressed()) {
          this.test(instance);
        }

        this.apply(instance);
      }
    }
  }

  public boolean test() {
    FakeLootDropModule.ensureInitialized();

    MinecraftClient instance = MinecraftClient.getInstance();
    if (!this.isEnabled() || !test2(instance)) {
      return false;
    } else if (this.anchorMacroState != AnchorMacroState.IDLE) {
      return true;
    } else {
      return this.test3(instance)
              && instance.player.getMainHandStack().isOf(Items.RESPAWN_ANCHOR)
              && getBlockHitResult(instance) == null
          ? true
          : this.test(instance);
    }
  }

  public boolean test(MinecraftClient minecraftClient2) {
    FakeLootDropModule.ensureInitialized();

    BlockHitResult blockHitResult2 = getBlockHitResult(minecraftClient2);
    if (blockHitResult2 == null) {
      this.blockPos2 = null;
      return false;
    } else {
      BlockPos blockPos3 = blockHitResult2.getBlockPos();
      if (blockPos3.equals(this.blockPos2)) {
        return true;
      } else {
        this.blockPos2 = blockPos3.toImmutable();
        if (minecraftClient2
            .world
            .getEnvironmentAttributes()
            .getAttributeValue(EnvironmentAttributes.RESPAWN_ANCHOR_WORKS_GAMEPLAY, blockPos3)) {
          apply(minecraftClient2, "Anchors set spawn here; the macro cannot detonate them.");
          return true;
        } else if (this.test3(minecraftClient2)) {
          apply(
              minecraftClient2,
              "No totem in your hotbar or offhand. Disallow Without Totem stopped this sequence.");
          return true;
        } else {
          this.count4 = this.computeInt4(minecraftClient2);
          if (this.count4 < 0) {
            apply(
                minecraftClient2,
                "Keep an empty slot or a non-glowstone item in your hotbar to detonate.");
            return true;
          } else if (minecraftClient2.player.getOffHandStack().isOf(Items.GLOWSTONE)) {
            apply(minecraftClient2, "Move glowstone out of your offhand before detonating.");
            return true;
          } else {
            boolean flag =
                minecraftClient2.world.getBlockState(blockPos3).get(RespawnAnchorBlock.CHARGES) > 0;
            if (!flag && computeInt2(minecraftClient2) < 0) {
              apply(minecraftClient2, "Put glowstone in your hotbar to charge the anchor.");
              return true;
            } else {
              this.clientPlayerEntity = minecraftClient2.player;
              this.clientWorld = minecraftClient2.world;
              this.blockPos = blockPos3.toImmutable();
              this.count2 = this.count3 = minecraftClient2.player.getInventory().getSelectedSlot();
              this.apply(
                  flag ? AnchorMacroState.SELECT_TOTEM : AnchorMacroState.SELECT_GLOWSTONE,
                  this.switchDelaySetting.intValue());
              return true;
            }
          }
        }
      }
    }
  }

  public void apply(MinecraftClient minecraftClient) {
    FakeLootDropModule.ensureInitialized();
    block6:
    for (int i = 0; i < 6 && this.anchorMacroState != AnchorMacroState.IDLE; ++i) {
      if (this.anchorMacroState == AnchorMacroState.WAIT_REMOVAL) {
        if (minecraftClient.world.getBlockState(this.blockPos).isOf(Blocks.RESPAWN_ANCHOR)) {
          if (this.count5 < this.count8) {
            return;
          }
          AnchorMacroModule.apply(
              minecraftClient, "The anchor did not disappear; release use before retrying.");
        } else {
          this.blockPos2 = null;
        }
        this.anchorMacroState = AnchorMacroState.RESTORE;
      }
      if (this.anchorMacroState == AnchorMacroState.RESTORE) {
        if (((Boolean) this.autoSwitchBackSetting.getValue()).booleanValue()
            && this.count5 < this.count6) {
          return;
        }
        this.apply2(minecraftClient);
        this.update5();
        return;
      }
      BlockHitResult blockHitResult = AnchorMacroModule.getBlockHitResult(minecraftClient);
      if (blockHitResult == null
          || !this.blockPos.equals(blockHitResult.getBlockPos())
          || minecraftClient
              .world
              .getEnvironmentAttributes()
              .getAttributeValue(EnvironmentAttributes.RESPAWN_ANCHOR_WORKS_GAMEPLAY, this.blockPos)
              .booleanValue()
          || this.test3(minecraftClient)
          || !this.test(minecraftClient, this.count4)
          || minecraftClient.player.getOffHandStack().isOf(Items.GLOWSTONE)) {
        this.apply4(minecraftClient);
        return;
      }
      if (this.count5 < this.count6) {
        return;
      }
      switch (this.anchorMacroState.ordinal()) {
        case 1:
          {
            if (this.computeInt(minecraftClient) > 0) {
              this.apply(AnchorMacroState.SELECT_TOTEM, 0);
              continue block6;
            }
            int n = AnchorMacroModule.computeInt2(minecraftClient);
            if (n < 0) {
              this.apply4(minecraftClient);
              return;
            }
            this.apply(minecraftClient, n);
            this.apply(AnchorMacroState.CHARGE, this.glowstoneDelaySetting.intValue());
            continue block6;
          }
        case 2:
          {
            if (this.computeInt(minecraftClient) == 0) {
              if (!minecraftClient.player.getMainHandStack().isOf(Items.GLOWSTONE)) {
                this.apply4(minecraftClient);
                return;
              }
              if (this.count7 == this.count5) {
                return;
              }
              if (!this.test(minecraftClient, blockHitResult)) {
                this.apply4(minecraftClient);
                return;
              }
            }
            this.apply(
                AnchorMacroState.SELECT_TOTEM, Math.max(1, this.switchDelaySetting.intValue()));
            return;
          }
        case 3:
          {
            if (this.computeInt(minecraftClient) <= 0) {
              this.apply4(minecraftClient);
              return;
            }
            this.count4 = this.computeInt4(minecraftClient);
            if (this.count4 < 0) {
              this.apply4(minecraftClient);
              return;
            }
            this.apply(minecraftClient, this.count4);
            this.apply(AnchorMacroState.DETONATE, this.explodeDelaySetting.intValue());
            continue block6;
          }
        case 4:
          {
            if (this.computeInt(minecraftClient) <= 0
                || !this.test(
                    minecraftClient, minecraftClient.player.getInventory().getSelectedSlot())) {
              this.apply4(minecraftClient);
              return;
            }
            if (((Boolean) this.disallowWithoutTotemSetting.getValue()).booleanValue()
                && !minecraftClient.player.getOffHandStack().isOf(Items.TOTEM_OF_UNDYING)
                && !minecraftClient.player.getMainHandStack().isOf(Items.TOTEM_OF_UNDYING)) {
              this.apply(AnchorMacroState.SELECT_TOTEM, this.switchDelaySetting.intValue());
              return;
            }
            if (this.count7 == this.count5) {
              return;
            }
            if (!this.test(minecraftClient, blockHitResult)) {
              this.apply4(minecraftClient);
              return;
            }
            this.apply(
                AnchorMacroState.WAIT_REMOVAL,
                (Boolean) this.autoSwitchBackSetting.getValue() != false
                    ? this.switchBackDelaySetting.intValue()
                    : 0);
            this.count8 = this.count5 + 40;
            return;
          }
        default:
          {
            return;
          }
      }
    }
  }

  public boolean test(MinecraftClient minecraftClient2, BlockHitResult blockHitResult2) {
    FakeLootDropModule.ensureInitialized();
    if (minecraftClient2
        .player
        .getItemCooldownManager()
        .isCoolingDown(minecraftClient2.player.getMainHandStack())) {
      return false;
    }

    this.count7 = this.count5;
    ActionResult actionResult =
        minecraftClient2.interactionManager.interactBlock(
            minecraftClient2.player, Hand.MAIN_HAND, blockHitResult2);
    if (actionResult instanceof Success success && success.swingSource() == SwingSource.CLIENT) {
      minecraftClient2.player.swingHand(Hand.MAIN_HAND);
    }

    return actionResult.isAccepted();
  }

  public static boolean test2(MinecraftClient minecraftClient2) {
    FakeLootDropModule.ensureInitialized();
    return minecraftClient2.player != null
        && minecraftClient2.world != null
        && minecraftClient2.interactionManager != null
        && minecraftClient2.getNetworkHandler() != null
        && minecraftClient2.currentScreen == null
        && !minecraftClient2.isPaused()
        && minecraftClient2.player.isAlive()
        && !minecraftClient2.player.isSpectator()
        && !minecraftClient2.player.isUsingItem()
        && !minecraftClient2.player.isSneaking()
        && !minecraftClient2.options.sneakKey.isPressed()
        && !minecraftClient2.options.attackKey.isPressed()
        && !minecraftClient2.interactionManager.isBreakingBlock()
        && minecraftClient2.player.currentScreenHandler
            == minecraftClient2.player.playerScreenHandler
        && !PearlEscapeAutomation.test2()
        && (FreecamModule.getInstance() == null || !FreecamModule.getInstance().test());
  }

  public static BlockHitResult getBlockHitResult(MinecraftClient minecraftClient2) {
    FakeLootDropModule.ensureInitialized();

    HitResult hitResult =
        minecraftClient2.player.raycast(
            minecraftClient2.player.getBlockInteractionRange(), 1.0F, false);
    return hitResult instanceof BlockHitResult blockHitResult2
            && hitResult.getType() == Type.BLOCK
            && minecraftClient2.world.getWorldBorder().contains(blockHitResult2.getBlockPos())
            && minecraftClient2
                .world
                .getBlockState(blockHitResult2.getBlockPos())
                .isOf(Blocks.RESPAWN_ANCHOR)
            && (minecraftClient2.crosshairTarget == null
                || minecraftClient2.crosshairTarget.getType() != Type.ENTITY)
        ? blockHitResult2
        : null;
  }

  public int computeInt(MinecraftClient minecraftClient2) {
    FakeLootDropModule.ensureInitialized();

    BlockState blockState = minecraftClient2.world.getBlockState(this.blockPos);
    return blockState.isOf(Blocks.RESPAWN_ANCHOR) ? blockState.get(RespawnAnchorBlock.CHARGES) : -1;
  }

  public static int computeInt2(MinecraftClient minecraftClient2) {
    FakeLootDropModule.ensureInitialized();

    int selectedSlot = minecraftClient2.player.getInventory().getSelectedSlot();

    for (int index = 0; index < 9; index++) {
      int intValue = (selectedSlot + index) % 9;
      if (minecraftClient2.player.getInventory().getStack(intValue).isOf(Items.GLOWSTONE)) {
        return intValue;
      }
    }

    return -1;
  }

  public boolean test(MinecraftClient minecraftClient2, int intValue) {
    FakeLootDropModule.ensureInitialized();
    return intValue >= 0 && intValue < 9
        ? !minecraftClient2.player.getInventory().getStack(intValue).isOf(Items.GLOWSTONE)
        : false;
  }

  public boolean test3(MinecraftClient minecraftClient2) {
    FakeLootDropModule.ensureInitialized();
    return (Boolean) this.disallowWithoutTotemSetting.getValue()
        && !minecraftClient2.player.getOffHandStack().isOf(Items.TOTEM_OF_UNDYING)
        && this.computeInt3(minecraftClient2) < 0;
  }

  public int computeInt3(MinecraftClient minecraftClient2) {
    FakeLootDropModule.ensureInitialized();

    int intValue = this.totemSlotSetting.intValue() - 1;
    if (minecraftClient2.player.getInventory().getStack(intValue).isOf(Items.TOTEM_OF_UNDYING)) {
      return intValue;
    }

    for (int index = 0; index < 9; index++) {
      if (minecraftClient2.player.getInventory().getStack(index).isOf(Items.TOTEM_OF_UNDYING)) {
        return index;
      }
    }

    return -1;
  }

  public int computeInt4(MinecraftClient minecraftClient2) {
    FakeLootDropModule.ensureInitialized();
    if ((Boolean) this.disallowWithoutTotemSetting.getValue()
        && !minecraftClient2.player.getOffHandStack().isOf(Items.TOTEM_OF_UNDYING)) {
      return this.computeInt3(minecraftClient2);
    }

    int intValue = this.totemSlotSetting.intValue() - 1;
    if (this.test(minecraftClient2, intValue)) {
      return intValue;
    }

    int selectedSlot = minecraftClient2.player.getInventory().getSelectedSlot();

    for (int index = 0; index < 9; index++) {
      int intValue2 = (selectedSlot + index) % 9;
      if (this.test(minecraftClient2, intValue2)) {
        return intValue2;
      }
    }

    return -1;
  }

  public void apply(MinecraftClient minecraftClient2, int count32) {
    minecraftClient2.player.getInventory().setSelectedSlot(count32);
    ((ClientPlayerInteractionManagerAccessor) (Object) (minecraftClient2.interactionManager))
        .invokeSyncSelectedSlot();
    this.count3 = count32;
  }

  public void apply2(MinecraftClient minecraftClient2) {
    FakeLootDropModule.ensureInitialized();
    if (this.test4(minecraftClient2)) {
      if (this.count7 == this.count5) {
        this.apply3(minecraftClient2);
      } else {
        this.apply(minecraftClient2, this.count2);
      }
    }
  }

  public boolean test4(MinecraftClient minecraftClient2) {
    FakeLootDropModule.ensureInitialized();
    return (Boolean) this.autoSwitchBackSetting.getValue()
        && this.clientPlayerEntity != null
        && this.clientPlayerEntity == minecraftClient2.player
        && this.clientWorld == minecraftClient2.world
        && minecraftClient2.interactionManager != null
        && minecraftClient2.getNetworkHandler() != null
        && minecraftClient2.player.isAlive()
        && !PearlEscapeAutomation.test2()
        && this.count2 >= 0
        && minecraftClient2.player.getInventory().getSelectedSlot() == this.count3;
  }

  public void apply3(MinecraftClient minecraftClient2) {
    FakeLootDropModule.ensureInitialized();
    if (this.test4(minecraftClient2)) {
      this.anchorMacroData =
          new AnchorMacroData(this.clientPlayerEntity, this.clientWorld, this.count3, this.count2);
    }
  }

  public void update4() {
    FakeLootDropModule.ensureInitialized();
    if (this.anchorMacroData != null) {
      MinecraftClient instance = MinecraftClient.getInstance();
      AnchorMacroData anchorMacroData2 = this.anchorMacroData;
      this.anchorMacroData = null;
      if ((Boolean) this.autoSwitchBackSetting.getValue()
          && instance.player == anchorMacroData2.clientPlayerEntity()
          && instance.world == anchorMacroData2.clientWorld()
          && instance.interactionManager != null
          && instance.getNetworkHandler() != null
          && instance.player.isAlive()
          && !PearlEscapeAutomation.test2()
          && instance.player.getInventory().getSelectedSlot() == anchorMacroData2.count()) {
        instance.player.getInventory().setSelectedSlot(anchorMacroData2.count2());
        ((ClientPlayerInteractionManagerAccessor) (Object) (instance.interactionManager))
            .invokeSyncSelectedSlot();
      }
    }
  }

  public void apply4(MinecraftClient minecraftClient2) {
    FakeLootDropModule.ensureInitialized();
    this.apply(
        AnchorMacroState.RESTORE,
        (java.lang.Boolean) (this.autoSwitchBackSetting.getValue())
            ? this.switchBackDelaySetting.intValue()
            : 0);
    if (this.count5 >= this.count6) {
      this.apply2(minecraftClient2);
      this.update5();
    }
  }

  public void apply(AnchorMacroState anchorMacroState2, int intValue) {
    this.anchorMacroState = anchorMacroState2;
    this.count6 = this.count5 + intValue;
  }

  public void update5() {
    this.anchorMacroState = AnchorMacroState.IDLE;
    this.clientPlayerEntity = null;
    this.clientWorld = null;
    this.blockPos = null;
    this.count2 = this.count3 = -1;
  }

  public static void apply(MinecraftClient minecraftClient2, String text) {
    minecraftClient2.player.sendMessage(Text.literal("Anchor Macro: " + text), true);
  }

  public static void setString(String string2) {
    string = string2;
  }

  public static String getString() {
    return string;
  }

  static {
    setString("b5g0cb");
  }
}
