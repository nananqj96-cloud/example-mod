package bunnythekenzo.client.module;

import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.InputUtil.Key;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResult.Success;
import net.minecraft.util.ActionResult.SwingSource;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;
import bunnythekenzo.client.AimRotationController;
import bunnythekenzo.client.AutoCrystalData;
import bunnythekenzo.client.AutoCrystalData2;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.CrystalTargetStatus;
import bunnythekenzo.client.CrystalTargetStatusSupport;
import bunnythekenzo.client.FakeLootDropModule;
import bunnythekenzo.client.PearlEscapeAutomation;
import bunnythekenzo.client.mixin.ClientPlayerEntityAccessor2;
import bunnythekenzo.client.mixin.ClientPlayerInteractionManagerAccessor;
import bunnythekenzo.client.mixin.KeyBindingAccessor;
import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.KeybindSetting;
import bunnythekenzo.client.setting.NumberSetting;

public class AutoCrystalModule extends Module {
  public final KeybindSetting activateKeySetting =
      (KeybindSetting)
          this.registerSetting(
              new KeybindSetting("Activate Key", "Hold to place + break (default RMB)", 1));
  public final NumberSetting breakDelaySetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Break Delay", "Ticks between crystal breaks", 1.0, 0.0, 20.0, 1.0, "t"));
  public final NumberSetting placeDelaySetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Place Delay", "Ticks between crystal places", 1.0, 0.0, 20.0, 1.0, "t"));
  public final BooleanSetting autoObsidianSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Auto Obsidian",
                  "Lay a base only when the hold starts with a sword or obsidian",
                  true));
  public final NumberSetting obsidianDelaySetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Obsidian Delay", "Ticks between obsidian placements", 2.0, 0.0, 20.0, 1.0, "t"));
  public final NumberSetting breakRangeSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Break Range",
                  "Maximum crystal distance, capped by your vanilla interaction reach",
                  3.0,
                  1.0,
                  6.0,
                  0.5,
                  "m"));
  public final BooleanSetting requireTargetSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Require Target",
                  "Require a nearby player target; turn off to place and break crystals without"
                      + " one",
                  true));
  public final NumberSetting targetRangeSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Target Range",
                  "Require a living player within this distance of you",
                  6.0,
                  1.0,
                  12.0,
                  0.5,
                  "m"));
  public final BooleanSetting damageCheckSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Damage Check",
                  "Also check blast radius, cover and damage immunity; leave off for custom combat"
                      + " rules",
                  false));
  public final BooleanSetting showStatusSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Show Status",
                  "Show why Auto Crystal is waiting while you hold the activate key",
                  false));
  public final BooleanSetting switchBackSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Switch Back", "Return to your original hotbar slot when released", true));
  public final NumberSetting rotationSpeedSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Rotation Speed",
                  "Speed of the smooth mouse pull onto a new obsidian base",
                  4.0,
                  1.0,
                  10.0,
                  0.5));
  public final NumberSetting aimVariationSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Aim Variation",
                  "How far the aim point can vary from the centre of each new base",
                  0.2,
                  0.0,
                  0.35,
                  0.05,
                  "m"));
  public final AimRotationController aimRotationController = new AimRotationController();
  public final Random random = new Random();
  public BlockPos blockPos;
  public Vec3d rotationPoint;
  public ClientPlayerEntity clientPlayerEntity;
  public AutoCrystalData2 autoCrystalData2;
  public int count = -1;
  public int count2 = -1;
  public long timestamp;
  public long timestamp2 = -100L;
  public long timestamp3 = -100L;
  public long timestamp4 = -100L;
  public BlockPos blockPos2;
  public int count3 = -1;
  public boolean enabled;
  public boolean enabled2;

  public String string = "Idle";
  public long timestamp5;
  public String string2;
  public static int count5;

  public AutoCrystalModule() {
    super(
        "Auto Crystal",
        "Hold RMB to auto place + break crystals, laying an obsidian base when needed",
        ModuleCategory.COMBAT);
    this.targetRangeSetting.setVisibilityCondition(this.requireTargetSetting::getValue);
    this.damageCheckSetting.setVisibilityCondition(this.requireTargetSetting::getValue);
  }

  @Override
  public void onEnable() {
    this.update5();
    this.string = "Idle";
    this.timestamp5 = 0L;
    this.string2 = null;
  }

  @Override
  public void onDisable() {
    this.apply2(MinecraftClient.getInstance());
  }

  public void update4() {
    this.autoCrystalData2 = null;
    this.update5();
  }

  public void update5() {
    this.clientPlayerEntity = null;
    this.count = this.count2 = -1;
    this.timestamp = 0L;
    this.timestamp2 = this.timestamp3 = this.timestamp4 = -100L;
    this.blockPos2 = null;
    this.count3 = -1;
    this.enabled = false;
    this.enabled2 = false;
    this.update8();
  }

  @Override
  public void onTick() {
    FakeLootDropModule.ensureInitialized();

    MinecraftClient instance = MinecraftClient.getInstance();
    this.string = "Ready";
    this.apply(instance);
    if ((Boolean) this.showStatusSetting.getValue()
        && this.isEnabled()
        && instance.player != null
        && instance.world != null
        && instance.currentScreen == null
        && instance.isWindowFocused()
        && !instance.isPaused()
        && this.test3(instance)) {
      long longValue = System.nanoTime();
      if (longValue >= this.timestamp5) {
        instance.player.sendMessage(Text.literal("Auto Crystal: " + this.string), true);
        if (!this.string.equals(this.string2)) {
          ClientInitializer.logger.info("Auto Crystal status: {}", this.string);
          this.string2 = this.string;
        }

        this.timestamp5 = longValue + 1000000000L;
      }
    }
  }

  public String getString() {
    return this.string;
  }

  public void apply(MinecraftClient minecraftClient) {
    FakeLootDropModule.ensureInitialized();

    this.update9();
    if (this.isEnabled() && this.test3(minecraftClient)) {
      String string3 = getString(minecraftClient);
      if (string3 != null) {
        this.string = string3;
        this.apply2(minecraftClient);
      } else {
        if (this.clientPlayerEntity != minecraftClient.player) {
          this.update4();
        }

        int selectedSlot = minecraftClient.player.getInventory().getSelectedSlot();
        if (this.count < 0 || selectedSlot != this.count2) {
          this.update4();
          this.clientPlayerEntity = minecraftClient.player;
          this.count = this.count2 = selectedSlot;
          ItemStack mainHandStack = minecraftClient.player.getMainHandStack();
          this.enabled2 = mainHandStack.isIn(ItemTags.SWORDS) || mainHandStack.isOf(Items.OBSIDIAN);
        }

        this.timestamp++;
        this.enabled = false;
        if (!minecraftClient.player.isUsingItem()
            && !minecraftClient.interactionManager.isBreakingBlock()) {
          AutoCrystalData action = this.findAction(minecraftClient);
          if (action == null) {
            this.string = "Aim at a crystal or valid base; check hotbar items";
            this.apply(minecraftClient, true);
          } else {
            CrystalTargetStatus crystalTargetStatus =
                this.getCrystalTargetStatus(minecraftClient, action);
            if (crystalTargetStatus != CrystalTargetStatus.READY) {
              this.string = crystalTargetStatus.getDescription();
              this.apply(minecraftClient, true);
            } else {
              if (this.rotationPoint != null) {
                if (!this.test(minecraftClient)) {
                  this.update8();
                } else if (AimRotationController.test(minecraftClient, this.rotationPoint)) {
                  this.update8();
                } else {
                  this.enabled = true;
                  BlockHitResult blockHitResult =
                      (BlockHitResult)
                          minecraftClient.player.raycast(
                              minecraftClient.player.getBlockInteractionRange(), 1.0F, false);
                  if (blockHitResult.getSide() != Direction.UP) {
                    this.string = "Aiming at the top of the base";
                    return;
                  }
                }
              }

              Vec3d eyePos = minecraftClient.player.getEyePos();
              double doubleValue =
                  Math.min(
                      (Double) this.breakRangeSetting.getValue(),
                      minecraftClient.player.getEntityInteractionRange());
              if (action.endCrystalEntity() != null) {
                EndCrystalEntity endCrystalEntity = action.endCrystalEntity();
                this.enabled = true;
                this.string =
                    eyePos.squaredDistanceTo(action.hitResult().getPos())
                            > doubleValue * doubleValue
                        ? "Crystal outside Break Range or vanilla reach"
                        : (this.count3 == endCrystalEntity.getId()
                            ? "Waiting for server to remove crystal"
                            : "Break delay");
                if (eyePos.squaredDistanceTo(action.hitResult().getPos())
                        <= doubleValue * doubleValue
                    && this.test(this.timestamp2, this.breakDelaySetting)
                    && (this.count3 != endCrystalEntity.getId()
                        || this.timestamp - this.timestamp2 >= 20L)) {
                  minecraftClient.interactionManager.attackEntity(
                      minecraftClient.player, endCrystalEntity);
                  minecraftClient.player.swingHand(Hand.MAIN_HAND);
                  this.timestamp2 = this.timestamp;
                  this.count3 = endCrystalEntity.getId();
                  this.blockPos2 = null;
                  this.string = "Breaking crystal";
                }
              } else {
                BlockHitResult blockHitResult2 = (BlockHitResult) action.hitResult();
                BlockPos blockPos3 = action.blockPos();
                this.enabled = true;
                if (!test2(minecraftClient, blockHitResult2.getBlockPos())) {
                  this.string = "Waiting for your aim to reach the server";
                } else if (!action.enabled()) {
                  this.enabled = true;
                  if (this.blockPos2 != null) {
                    if (this.blockPos2.equals(blockPos3)
                        && this.timestamp - this.timestamp3 < 20L
                        && minecraftClient
                            .world
                            .getNonSpectatingEntities(
                                EndCrystalEntity.class, getBox(this.blockPos2))
                            .isEmpty()) {
                      this.string = "Waiting for server to spawn crystal";
                      return;
                    }

                    this.blockPos2 = null;
                  }

                  if (!this.test(this.timestamp3, this.placeDelaySetting)) {
                    this.string = "Place delay";
                  } else if (!test(minecraftClient, blockPos3)) {
                    this.string = "Crystal space blocked";
                  } else {
                    Hand hand = this.getHand(minecraftClient, Items.END_CRYSTAL);
                    this.string = "No crystals in hotbar or offhand";
                    if (hand != null) {
                      this.timestamp3 = this.timestamp;
                      if (test(minecraftClient, hand, blockHitResult2)) {
                        this.blockPos2 = blockPos3.toImmutable();
                        this.string = "Placing crystal";
                      } else {
                        this.string = "Crystal use refused locally or on cooldown";
                      }
                    }
                  }
                } else {
                  this.enabled = true;
                  if (!this.test(this.timestamp4, this.obsidianDelaySetting)) {
                    this.string = "Obsidian delay";
                  } else {
                    ItemPlacementContext itemPlacementContext =
                        new ItemPlacementContext(
                            minecraftClient.player,
                            Hand.MAIN_HAND,
                            new ItemStack(Items.OBSIDIAN),
                            blockHitResult2);
                    BlockPos blockPos4 = blockPos3;
                    if (itemPlacementContext.canPlace()
                        && minecraftClient.world.getWorldBorder().contains(blockPos4)
                        && !minecraftClient.world.isOutOfHeightLimit(blockPos4)
                        && test(minecraftClient, blockPos4)
                        && minecraftClient.world.canPlace(
                            Blocks.OBSIDIAN.getDefaultState(),
                            blockPos4,
                            ShapeContext.of(minecraftClient.player))) {
                      Hand hand2 = this.getHand(minecraftClient, Items.OBSIDIAN);
                      this.string = "No obsidian in hotbar or offhand";
                      if (hand2 != null) {
                        this.timestamp4 = this.timestamp;
                        if (test(minecraftClient, hand2, blockHitResult2)) {
                          this.string = "Placing obsidian";
                          if (minecraftClient.world.getBlockState(blockPos4).isOf(Blocks.OBSIDIAN)
                              && eyePos.y > blockPos4.getY() + 1) {
                            this.queueRotation(blockPos4);
                          }
                        } else {
                          this.string = "Obsidian use refused locally or on cooldown";
                        }
                      }
                    } else {
                      this.string = "Obsidian base or crystal space blocked";
                    }
                  }
                }
              }
            }
          }
        } else {
          this.string = "Using an item or mining";
          this.update8();
        }
      }
    } else {
      this.string = "Inactive";
      this.apply2(minecraftClient);
    }
  }

  public AutoCrystalData findAction(MinecraftClient minecraftClient) {
    FakeLootDropModule.ensureInitialized();

    Vec3d eyePos = minecraftClient.player.getEyePos();
    double blockInteractionRange = minecraftClient.player.getBlockInteractionRange();
    double doubleValue =
        Math.max(
            blockInteractionRange,
            Math.min(
                (Double) this.breakRangeSetting.getValue(),
                minecraftClient.player.getEntityInteractionRange()));
    Vec3d vec3d2 = minecraftClient.player.getRotationVec(1.0F).multiply(doubleValue);
    HitResult hitResult = minecraftClient.player.raycast(doubleValue, 1.0F, false);
    double doubleValue2 =
        hitResult.getType() == Type.MISS
            ? doubleValue * doubleValue
            : eyePos.squaredDistanceTo(hitResult.getPos());
    EntityHitResult hitResult2 =
        ProjectileUtil.raycast(
            minecraftClient.player,
            eyePos,
            eyePos.add(vec3d2),
            minecraftClient.player.getBoundingBox().stretch(vec3d2).expand(1.0),
            entity -> {
              FakeLootDropModule.ensureInitialized();
              return !entity.isSpectator() && entity.canHit();
            },
            doubleValue2);
    if (hitResult2 == null) {
      if (hitResult instanceof BlockHitResult hitResult3
          && hitResult.getType() == Type.BLOCK
          && !(eyePos.squaredDistanceTo(hitResult3.getPos())
              > blockInteractionRange * blockInteractionRange)
          && minecraftClient.world.getWorldBorder().contains(hitResult3.getBlockPos())
          && test(minecraftClient, Items.END_CRYSTAL)) {
        BlockState blockState = minecraftClient.world.getBlockState(hitResult3.getBlockPos());
        if (blockState.isOf(Blocks.OBSIDIAN) || blockState.isOf(Blocks.BEDROCK)) {
          return new AutoCrystalData(hitResult3, null, hitResult3.getBlockPos(), false);
        } else if ((Boolean) this.autoObsidianSetting.getValue()
            && this.enabled2
            && test(minecraftClient, Items.OBSIDIAN)) {
          ItemPlacementContext itemPlacementContext =
              new ItemPlacementContext(
                  minecraftClient.player,
                  Hand.MAIN_HAND,
                  new ItemStack(Items.OBSIDIAN),
                  hitResult3);
          return new AutoCrystalData(hitResult3, null, itemPlacementContext.getBlockPos(), true);
        } else {
          return null;
        }
      } else {
        return null;
      }
    } else {
      return hitResult2.getEntity() instanceof EndCrystalEntity endCrystalEntity
              && endCrystalEntity.isAlive()
          ? new AutoCrystalData(hitResult2, endCrystalEntity, null, false)
          : null;
    }
  }

  public boolean test(MinecraftClient minecraftClient, AutoCrystalData autoCrystalData) {
    return this.getCrystalTargetStatus(minecraftClient, autoCrystalData)
        == CrystalTargetStatus.READY;
  }

  public CrystalTargetStatus getCrystalTargetStatus(
      MinecraftClient minecraftClient, AutoCrystalData autoCrystalData) {
    FakeLootDropModule.ensureInitialized();
    Vec3d vec3d2 =
        autoCrystalData.endCrystalEntity() != null
            ? autoCrystalData.endCrystalEntity().getEntityPos()
            : Vec3d.ofBottomCenter(autoCrystalData.blockPos().up());
    return this.getCrystalTargetStatus(
        minecraftClient, vec3d2, autoCrystalData.enabled() ? autoCrystalData.blockPos() : null);
  }

  public CrystalTargetStatus getCrystalTargetStatus(
      MinecraftClient minecraftClient, Vec3d vec3d2, BlockPos blockPos3) {
    return (java.lang.Boolean) (this.requireTargetSetting.getValue())
        ? CrystalTargetStatusSupport.getCrystalTargetStatus(
            minecraftClient,
            vec3d2,
            (Double) this.targetRangeSetting.getValue(),
            blockPos3,
            (Boolean) this.damageCheckSetting.getValue())
        : CrystalTargetStatus.READY;
  }

  public void queueRotation(BlockPos blockPos3) {
    this.blockPos = blockPos3.toImmutable();
    double doubleValue = (Double) this.aimVariationSetting.getValue();
    this.rotationPoint =
        Vec3d.ofBottomCenter(blockPos3.up())
            .add(
                (this.random.nextDouble() * 2.0 - 1.0) * doubleValue,
                0.0,
                (this.random.nextDouble() * 2.0 - 1.0) * doubleValue);
    this.aimRotationController.reset();
  }

  public void update8() {
    this.blockPos = null;
    this.rotationPoint = null;
    this.aimRotationController.reset();
  }

  public boolean test() {
    return this.rotationPoint != null;
  }

  public double[] getValueArray(double doubleValue, double doubleValue2, double doubleValue3) {
    FakeLootDropModule.ensureInitialized();

    MinecraftClient instance = MinecraftClient.getInstance();
    if (this.rotationPoint == null) {
      return null;
    } else if (!this.isEnabled()
        || !test2(instance)
        || !this.test3(instance)
        || this.clientPlayerEntity != instance.player
        || instance.player.getInventory().getSelectedSlot() != this.count2
        || instance.player.isUsingItem()
        || instance.interactionManager.isBreakingBlock()
        || !Double.isFinite(doubleValue)
        || doubleValue <= 0.0
        || !Double.isFinite(doubleValue2)
        || !Double.isFinite(doubleValue3)
        || !this.test(instance)) {
      this.update8();
      return null;
    } else if (this.getCrystalTargetStatus(instance, Vec3d.ofBottomCenter(this.blockPos.up()), null)
        != CrystalTargetStatus.READY) {
      this.apply(instance, true);
      return null;
    } else {
      double doubleValue4 = (Double) this.rotationSpeedSetting.getValue();
      return this.aimRotationController.getValueArray(
          instance,
          this.rotationPoint,
          doubleValue4,
          Math.max(1.0, 10.0 - doubleValue4),
          true,
          Math.min(doubleValue, 0.05),
          doubleValue2,
          doubleValue3);
    }
  }

  public boolean test(MinecraftClient minecraftClient) {
    FakeLootDropModule.ensureInitialized();
    if (minecraftClient.world.getBlockState(this.blockPos).isOf(Blocks.OBSIDIAN)
        && !(minecraftClient.player.getEyePos().squaredDistanceTo(this.rotationPoint)
            > minecraftClient.player.getBlockInteractionRange()
                * minecraftClient.player.getBlockInteractionRange())) {
      HitResult hitResult =
          minecraftClient.player.raycast(
              minecraftClient.player.getBlockInteractionRange(), 1.0F, false);
      return hitResult instanceof BlockHitResult blockHitResult
          && hitResult.getType() == Type.BLOCK
          && blockHitResult.getBlockPos().equals(this.blockPos);
    } else {
      return false;
    }
  }

  public boolean test(long longValue, NumberSetting numberSetting8) {
    FakeLootDropModule.ensureInitialized();
    return this.timestamp - longValue >= Math.max(1, numberSetting8.intValue());
  }

  public static Box getBox(BlockPos blockPos3) {
    return new Box(blockPos3.up()).stretch(0.0, 1.0, 0.0);
  }

  public static boolean test(MinecraftClient minecraftClient, BlockPos blockPos3) {
    FakeLootDropModule.ensureInitialized();
    return !minecraftClient.world.isOutOfHeightLimit(blockPos3.up())
        && minecraftClient.world.isAir(blockPos3.up())
        && minecraftClient.world.getOtherEntities(null, getBox(blockPos3)).isEmpty();
  }

  public static boolean test2(MinecraftClient minecraftClient, BlockPos blockPos3) {
    ClientPlayerEntityAccessor2 clientPlayerEntityAccessor2 =
        (ClientPlayerEntityAccessor2) (Object) (minecraftClient.player);
    FakeLootDropModule.ensureInitialized();

    Vec3d vec3d2 =
        new Vec3d(
            clientPlayerEntityAccessor2.getLastXClient(),
            clientPlayerEntityAccessor2.getLastYClient()
                + minecraftClient.player.getStandingEyeHeight(),
            clientPlayerEntityAccessor2.getLastZClient());
    Vec3d vec3d3 =
        vec3d2.add(
            Vec3d.fromPolar(
                    clientPlayerEntityAccessor2.getLastPitchClient(),
                    clientPlayerEntityAccessor2.getLastYawClient())
                .multiply(minecraftClient.player.getBlockInteractionRange()));
    Box box = new Box(blockPos3).expand(1.0E-7);
    return box.contains(vec3d2) || box.raycast(vec3d2, vec3d3).isPresent();
  }

  public static boolean test(MinecraftClient minecraftClient, Item item) {
    FakeLootDropModule.ensureInitialized();
    if (minecraftClient.player.getOffHandStack().isOf(item)) {
      return true;
    }

    for (int index = 0; index < 9; index++) {
      if (minecraftClient.player.getInventory().getStack(index).isOf(item)) {
        return true;
      }
    }

    return false;
  }

  public Hand getHand(MinecraftClient minecraftClient, Item item) {
    FakeLootDropModule.ensureInitialized();
    if (minecraftClient.player.getMainHandStack().isOf(item)) {
      return Hand.MAIN_HAND;
    }

    if (minecraftClient.player.getOffHandStack().isOf(item)) {
      return Hand.OFF_HAND;
    }

    for (int index = 0; index < 9; index++) {
      if (minecraftClient.player.getInventory().getStack(index).isOf(item)) {
        this.apply(minecraftClient, index);
        return Hand.MAIN_HAND;
      }
    }

    return null;
  }

  public void apply(MinecraftClient minecraftClient, int count22) {
    minecraftClient.player.getInventory().setSelectedSlot(count22);
    ((ClientPlayerInteractionManagerAccessor) (Object) (minecraftClient.interactionManager))
        .invokeSyncSelectedSlot();
    this.count2 = count22;
  }

  public static boolean test(
      MinecraftClient minecraftClient, Hand hand, BlockHitResult blockHitResult) {
    FakeLootDropModule.ensureInitialized();
    if (minecraftClient
        .player
        .getItemCooldownManager()
        .isCoolingDown(minecraftClient.player.getStackInHand(hand))) {
      return false;
    }

    ActionResult actionResult =
        minecraftClient.interactionManager.interactBlock(
            minecraftClient.player, hand, blockHitResult);
    if (actionResult instanceof Success success && success.swingSource() == SwingSource.CLIENT) {
      minecraftClient.player.swingHand(hand);
    }

    return actionResult.isAccepted();
  }

  public static boolean test2(MinecraftClient minecraftClient) {
    return getString(minecraftClient) == null;
  }

  public static String getString(MinecraftClient minecraftClient) {
    FakeLootDropModule.ensureInitialized();
    if (minecraftClient.player == null
        || minecraftClient.world == null
        || minecraftClient.interactionManager == null
        || minecraftClient.getNetworkHandler() == null) {
      return "Not connected to a world";
    } else if (minecraftClient.currentScreen != null) {
      return "Menu open";
    } else if (!minecraftClient.isWindowFocused()) {
      return "Minecraft is not focused";
    } else if (minecraftClient.isPaused()) {
      return "Game paused";
    } else if (!minecraftClient.player.isAlive()) {
      return "You are dead";
    } else if (minecraftClient.player.isSpectator()) {
      return "Spectator mode";
    } else if (minecraftClient.getCameraEntity() != minecraftClient.player) {
      return "Camera detached from player";
    } else if (minecraftClient.player.hasVehicle()) {
      return "Riding an entity";
    } else if (PearlEscapeAutomation.test2()) {
      return "Relog Method paused item actions";
    } else {
      FreecamModule instance = FreecamModule.getInstance();
      FreeLookModule instance2 = FreeLookModule.getInstance();
      FakeLootDropModule fakeLootDropModule = FakeLootDropModule.getFakeLootDropModule();
      if (instance != null && instance.test()) {
        return "Freecam active";
      } else if (instance2 != null && instance2.test5()) {
        return "Free Look camera active";
      } else {
        return fakeLootDropModule != null && fakeLootDropModule.equipmentPreview.test3()
            ? "Fake Loot camera active"
            : null;
      }
    }
  }

  public boolean test3(MinecraftClient minecraftClient) {
    FakeLootDropModule.ensureInitialized();

    int intValue = (Integer) this.activateKeySetting.getValue();
    if (!this.activateKeySetting.isBound()) {
      return false;
    } else {
      Key key = ((KeyBindingAccessor) (Object) (minecraftClient.options.useKey)).getBoundKey();
      boolean flag = intValue >= 0 && intValue <= 7;
      if (key.getCode() == intValue
          && key.getCategory()
              == (flag
                  ? net.minecraft.client.util.InputUtil.Type.MOUSE
                  : net.minecraft.client.util.InputUtil.Type.KEYSYM)) {
        return minecraftClient.options.useKey.isPressed();
      } else {
        return flag
            ? GLFW.glfwGetMouseButton(minecraftClient.getWindow().getHandle(), intValue) == 1
            : intValue >= 32
                && intValue <= 348
                && InputUtil.isKeyPressed(minecraftClient.getWindow(), intValue);
      }
    }
  }

  public boolean test4() {
    FakeLootDropModule.ensureInitialized();

    MinecraftClient instance = MinecraftClient.getInstance();
    if (this.test4(instance)) {
      return true;
    }

    if (this.isEnabled()
        && this.enabled
        && this.clientPlayerEntity == instance.player
        && test2(instance)
        && this.test3(instance)
        && instance.player.getInventory().getSelectedSlot() == this.count2) {
      AutoCrystalData action = this.findAction(instance);
      if (action != null && this.test(instance, action)) {
        return true;
      }

      this.apply(instance, true);
      return this.test4(instance);
    } else {
      return false;
    }
  }

  public void apply2(MinecraftClient minecraftClient) {
    this.apply(minecraftClient, (Boolean) this.switchBackSetting.getValue());
  }

  public void apply(MinecraftClient minecraftClient, boolean flag) {
    FakeLootDropModule.ensureInitialized();
    if (flag
        && this.count >= 0
        && this.clientPlayerEntity == minecraftClient.player
        && minecraftClient.player != null
        && minecraftClient.player.isAlive()
        && minecraftClient.interactionManager != null
        && minecraftClient.getNetworkHandler() != null
        && minecraftClient.player.getInventory().getSelectedSlot() == this.count2
        && this.count2 != this.count) {
      this.autoCrystalData2 =
          new AutoCrystalData2(
              minecraftClient.player, minecraftClient.world, this.count2, this.count);
    }

    this.update5();
  }

  public boolean test4(MinecraftClient minecraftClient) {
    FakeLootDropModule.ensureInitialized();
    if (this.autoCrystalData2 == null) {
      return false;
    }

    if (minecraftClient.player == this.autoCrystalData2._d()
        && minecraftClient.world == this.autoCrystalData2.clientWorld()
        && minecraftClient.player != null
        && minecraftClient.player.isAlive()
        && minecraftClient.getNetworkHandler() != null
        && minecraftClient.interactionManager != null
        && minecraftClient.player.getInventory().getSelectedSlot()
            == this.autoCrystalData2.count()) {
      return true;
    }

    this.autoCrystalData2 = null;
    return false;
  }

  public void update9() {
    FakeLootDropModule.ensureInitialized();

    MinecraftClient instance = MinecraftClient.getInstance();
    if (this.test4(instance) && !PearlEscapeAutomation.test2()) {
      int intValue = this.autoCrystalData2.count2();
      this.autoCrystalData2 = null;
      instance.player.getInventory().setSelectedSlot(intValue);
      ((ClientPlayerInteractionManagerAccessor) (Object) (instance.interactionManager))
          .invokeSyncSelectedSlot();
    }
  }

  public static void setCount5(int count52) {
    count5 = count52;
  }

  public static int getCount5() {
    return count5;
  }

  public static int computeInt() {
    int intValue = getCount5();
    return intValue == 0 ? 63 : 0;
  }

  static {
    setCount5(35);
  }
}
