package bunnythekenzo.client.module;

import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.hit.EntityHitResult;
import bunnythekenzo.client.FakeLootDropModule;
import bunnythekenzo.client.mixin.MinecraftClientAccessor;
import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.ChoiceSetting;
import bunnythekenzo.client.setting.NumberSetting;

public class TriggerbotModule extends Module {
  public final ChoiceSetting timingSetting =
      (ChoiceSetting)
          this.registerSetting(
              new ChoiceSetting(
                  "Timing",
                  "Cooldown attacks when charged; CPS sets speed; Delay uses tick intervals.",
                  "Cooldown",
                  new String[] {"Cooldown", "CPS", "Delay"}));
  public final NumberSetting minCpsSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Min CPS",
                  "Minimum attack rate. Actual speed is limited by ticks and enabled checks.",
                  8.0,
                  1.0,
                  20.0,
                  1.0,
                  " CPS"));
  public final NumberSetting maxCpsSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Max CPS",
                  "Maximum attack rate; 20 allows one attack per tick at normal tick speed.",
                  12.0,
                  1.0,
                  20.0,
                  1.0,
                  " CPS"));
  public final NumberSetting minDelaySetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Min Delay",
                  "Minimum ticks between attacks in Delay mode. Zero attacks each tick.",
                  9.0,
                  0.0,
                  20.0,
                  1.0,
                  " ticks"));
  public final NumberSetting maxDelaySetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Max Delay",
                  "Maximum ticks between attacks in Delay mode.",
                  11.0,
                  0.0,
                  20.0,
                  1.0,
                  " ticks"));
  public final BooleanSetting respectCooldownSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Respect Cooldown",
                  "Also wait for weapon charge in CPS and Delay modes. Off allows weaker rapid"
                      + " hits.",
                  true));
  public final NumberSetting minCooldownSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Min Cooldown",
                  "Required weapon charge before attacking.",
                  100.0,
                  0.0,
                  100.0,
                  1.0,
                  "%"));
  public final NumberSetting reactionDelaySetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Reaction Delay",
                  "Ticks to keep aiming at a new target before attacking. Zero reacts immediately.",
                  0.0,
                  0.0,
                  20.0,
                  1.0,
                  " ticks"));
  public final NumberSetting rangeSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Range",
                  "Maximum crosshair hit distance, also limited by vanilla reach.",
                  3.0,
                  1.0,
                  3.0,
                  0.1,
                  " blocks"));
  public final BooleanSetting playersSetting =
      (BooleanSetting) this.registerSetting(new BooleanSetting("Players", "Attack players.", true));
  public final BooleanSetting hostileMobsSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Hostile Mobs", "Attack monster-category mobs.", true));
  public final BooleanSetting passiveMobsSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Passive Mobs", "Attack other mobs, including animals and villagers.", false));
  public final BooleanSetting ignoreInvisibleSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Ignore Invisible", "Skip invisible targets.", true));
  public final BooleanSetting ignoreTeammatesSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Ignore Teammates", "Skip scoreboard teammates and allied entities.", true));
  public final BooleanSetting onlyItemSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Only Item", "Require the selected type of item in your main hand.", false));
  public final ChoiceSetting itemFilterSetting =
      (ChoiceSetting)
          this.registerSetting(
              new ChoiceSetting(
                  "Item Filter",
                  "Allowed main-hand item when Only Item is enabled.",
                  "Sword",
                  new String[] {"Sword", "Axe", "Hand", "Sword or Axe"}));
  public final BooleanSetting onlyCritSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Only Crit",
                  "Wait for a charged, natural falling critical. Sprinting and grounded attacks are"
                      + " skipped.",
                  false));
  public final BooleanSetting checkShieldSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Check Shield", "Skip targets actively blocking with a shield.", false));
  public final BooleanSetting requireAttackKeySetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Require Attack Key", "Only trigger while your attack key is held.", false));
  public ClientPlayerEntity clientPlayerEntity;
  public LivingEntity livingEntity;
  public int count = Integer.MIN_VALUE;
  public int count2;
  public double value;

  public TriggerbotModule() {
    super(
        "Triggerbot",
        "Automatically attacks valid targets under your crosshair",
        ModuleCategory.COMBAT);
    this.minCpsSetting.setVisibilityCondition(
        () -> {
          return this.timingSetting.isSelected("CPS");
        });
    this.maxCpsSetting.setVisibilityCondition(
        () -> {
          return this.timingSetting.isSelected("CPS");
        });
    this.minDelaySetting.setVisibilityCondition(
        () -> {
          return this.timingSetting.isSelected("Delay");
        });
    this.maxDelaySetting.setVisibilityCondition(
        () -> {
          return this.timingSetting.isSelected("Delay");
        });
    this.respectCooldownSetting.setVisibilityCondition(
        () -> {
          FakeLootDropModule.ensureInitialized();
          return !this.timingSetting.isSelected("Cooldown");
        });
    this.minCooldownSetting.setVisibilityCondition(
        () -> {
          FakeLootDropModule.ensureInitialized();
          return this.timingSetting.isSelected("Cooldown")
              || (Boolean) this.respectCooldownSetting.getValue();
        });
    this.itemFilterSetting.setVisibilityCondition(this.onlyItemSetting::getValue);
  }

  @Override
  public void onEnable() {
    this.update4();
  }

  @Override
  public void onDisable() {
    this.update4();
  }

  public void update4() {
    this.clientPlayerEntity = null;
    this.count = Integer.MIN_VALUE;
    this.livingEntity = null;
    this.count2 = 0;
    this.value = 0.0;
  }

  @Override
  public void onTick() {
    FakeLootDropModule.ensureInitialized();

    MinecraftClient instance = MinecraftClient.getInstance();
    if (this.isEnabled()
        && instance.player != null
        && instance.world != null
        && instance.interactionManager != null) {
      if (this.clientPlayerEntity != instance.player
          || this.count != instance.player.age && this.count != instance.player.age - 1) {
        this.update4();
      }

      if (this.count != instance.player.age) {
        this.clientPlayerEntity = instance.player;
        this.count = instance.player.age;
        this.value = Math.max(-1.0, this.value - 1.0);
        if (!this.test(instance)) {
          this.update5();
        } else {
          instance.gameRenderer.updateCrosshairTarget(1.0F);
          if (instance.crosshairTarget instanceof EntityHitResult entityHitResult
              && entityHitResult.getEntity() instanceof LivingEntity livingEntity2
              && this.test(instance, entityHitResult, livingEntity2)) {
            if (this.livingEntity != livingEntity2) {
              this.livingEntity = livingEntity2;
              this.count2 = 0;
              this.value = Math.max(0.0, this.value);
            } else {
              this.count2++;
            }

            if (this.count2 < this.reactionDelaySetting.intValue()
                || !this.test(instance.player, livingEntity2)) {
              this.value = Math.max(0.0, this.value);
            } else if (!(this.value > 1.0E-6)) {
              ((MinecraftClientAccessor) (Object) (instance)).invokeDoAttack();
              this.value = this.value + this.computeDouble();
            }
          } else {
            this.update5();
          }
        }
      }
    } else {
      this.update4();
    }
  }

  public void update5() {
    this.livingEntity = null;
    this.count2 = 0;
    this.value = Math.max(0.0, this.value);
  }

  public boolean test(MinecraftClient minecraftClient) {
    FakeLootDropModule.ensureInitialized();

    ClientPlayerEntity player = minecraftClient.player;
    return minecraftClient.currentScreen == null
        && minecraftClient.getOverlay() == null
        && !minecraftClient.isPaused()
        && minecraftClient.getCameraEntity() == player
        && (FreecamModule.getInstance() == null || !FreecamModule.getInstance().test())
        && player.isAlive()
        && !player.isSpectator()
        && !minecraftClient.interactionManager.isFlyingLocked()
        && !player.isSleeping()
        && !player.isUsingItem()
        && !player.isRiding()
        && !minecraftClient.interactionManager.isBreakingBlock()
        && minecraftClient.attackCooldown <= 0
        && (!(Boolean) this.requireAttackKeySetting.getValue()
            || minecraftClient.options.attackKey.isPressed())
        && this.test(player);
  }

  public boolean test(ClientPlayerEntity clientPlayerEntity2) {
    FakeLootDropModule.ensureInitialized();
    if (!(Boolean) this.onlyItemSetting.getValue()) {
      return true;
    }

    ItemStack mainHandStack = clientPlayerEntity2.getMainHandStack();

    return switch ((String) this.itemFilterSetting.getValue()) {
      case "Sword" -> mainHandStack.isIn(ItemTags.SWORDS);
      case "Axe" -> mainHandStack.isIn(ItemTags.AXES);
      case "Hand" -> mainHandStack.isEmpty();
      case "Sword or Axe" ->
          mainHandStack.isIn(ItemTags.SWORDS) || mainHandStack.isIn(ItemTags.AXES);
      default -> false;
    };
  }

  public boolean test(
      MinecraftClient minecraftClient,
      EntityHitResult entityHitResult,
      LivingEntity livingEntity2) {
    FakeLootDropModule.ensureInitialized();
    if (livingEntity2 == minecraftClient.player
        || !livingEntity2.isAlive()
        || livingEntity2.isRemoved()
        || !livingEntity2.isAttackable()
        || livingEntity2.isSpectator()
        || livingEntity2 instanceof ArmorStandEntity
        || livingEntity2.getEntityWorld() != minecraftClient.world) {
      return false;
    }

    if ((Boolean) this.ignoreInvisibleSetting.getValue() && livingEntity2.isInvisible()) {
      return false;
    }

    if ((Boolean) this.ignoreTeammatesSetting.getValue()
        && minecraftClient.player.isTeammate(livingEntity2)) {
      return false;
    }

    boolean flag =
        livingEntity2 instanceof PlayerEntity
            ? (Boolean) this.playersSetting.getValue()
            : livingEntity2 instanceof MobEntity
                    && livingEntity2.getType().getSpawnGroup() == SpawnGroup.MONSTER
                ? (Boolean) this.hostileMobsSetting.getValue()
                : (Boolean) this.passiveMobsSetting.getValue();
    double doubleValue =
        Math.min(
            (Double) this.rangeSetting.getValue(),
            minecraftClient.player.getEntityInteractionRange());
    return flag
        && minecraftClient.player.getEyePos().squaredDistanceTo(entityHitResult.getPos())
            <= doubleValue * doubleValue
        && minecraftClient.player.canSee(livingEntity2);
  }

  public boolean test(ClientPlayerEntity clientPlayerEntity2, LivingEntity livingEntity2) {
    FakeLootDropModule.ensureInitialized();

    float attackCooldownProgress = clientPlayerEntity2.getAttackCooldownProgress(0.0F);
    if ((this.timingSetting.isSelected("Cooldown")
            || (Boolean) this.respectCooldownSetting.getValue())
        && attackCooldownProgress < (Double) this.minCooldownSetting.getValue() / 100.0) {
      return false;
    } else {
      return this.checkShieldSetting.getValue() && livingEntity2.isBlocking()
          ? false
          : !(Boolean) this.onlyCritSetting.getValue()
              || attackCooldownProgress > 0.9F
                  && clientPlayerEntity2.fallDistance > 0.0
                  && !clientPlayerEntity2.isOnGround()
                  && !clientPlayerEntity2.isClimbing()
                  && !clientPlayerEntity2.isTouchingWater()
                  && !clientPlayerEntity2.hasBlindnessEffect()
                  && !clientPlayerEntity2.hasVehicle()
                  && !clientPlayerEntity2.isSprinting();
    }
  }

  public double computeDouble() {
    FakeLootDropModule.ensureInitialized();
    if (this.timingSetting.isSelected("CPS")) {
      double doubleValue =
          Math.min((Double) this.minCpsSetting.getValue(), (Double) this.maxCpsSetting.getValue());
      double doubleValue2 =
          Math.max((Double) this.minCpsSetting.getValue(), (Double) this.maxCpsSetting.getValue());
      return 20.0
          / (doubleValue == doubleValue2
              ? doubleValue
              : ThreadLocalRandom.current().nextDouble(doubleValue, doubleValue2));
    } else if (this.timingSetting.isSelected("Delay")) {
      int intValue = Math.min(this.minDelaySetting.intValue(), this.maxDelaySetting.intValue());
      int intValue2 = Math.max(this.minDelaySetting.intValue(), this.maxDelaySetting.intValue());
      return Math.max(1, ThreadLocalRandom.current().nextInt(intValue, intValue2 + 1));
    } else {
      return 1.0;
    }
  }
}
