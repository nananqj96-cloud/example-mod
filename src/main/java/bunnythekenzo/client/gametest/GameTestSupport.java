package bunnythekenzo.client.gametest;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyInput;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil.Key;
import net.minecraft.client.util.InputUtil.Type;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.GuiModuleEntry;
import bunnythekenzo.client.MovementOverrides;
import bunnythekenzo.client.mixin.KeyBindingAccessor;
import bunnythekenzo.client.mixin.LivingEntityAccessor;
import bunnythekenzo.client.mixin.MinecraftClientAccessor;
import bunnythekenzo.client.setting.ChoiceSettingView;
import bunnythekenzo.client.setting.NumberSettingView;
import bunnythekenzo.client.setting.SettingEntry;

public final class GameTestSupport {
  public static final Method method =
      getMethod(
          bunnythekenzo.client.MovementOverrides.class,
          "tick",
          new Class[] {net.minecraft.client.MinecraftClient.class});
  public static final Method method2 =
      getMethod(MovementOverrides.class, "resetRuntime", new Class[] {MinecraftClient.class});
  public static final Method method3 =
      bunnythekenzo.client.runtime.MappedReflection.requireMethod(
          Keyboard.class,
          "onKey",
          "method_1466",
          "(JILnet/minecraft/class_11908;)V",
          long.class,
          int.class,
          KeyInput.class);
  public static final double value = 3.0E-4;

  public static void runInWorld(ClientGameTestContext clientGameTestContext) {
    clientGameTestContext.runOnClient(
        minecraftClient -> {
          Input input = minecraftClient.player.input;
          GameMode currentGameMode = minecraftClient.interactionManager.getCurrentGameMode();

          try {
            minecraftClient.interactionManager.setGameMode(GameMode.SURVIVAL);
            minecraftClient.player.setPosition(
                minecraftClient.player.getX(), 220.0, minecraftClient.player.getZ());
            minecraftClient.player.input = new KeyboardInput(minecraftClient.options);
            apply2(minecraftClient);
            apply(minecraftClient);
            apply3(minecraftClient);
            apply4(minecraftClient);
            apply5(minecraftClient);
            apply6(minecraftClient);
            apply7(minecraftClient);
            apply8(minecraftClient);
          } finally {
            apply9(minecraftClient);
            minecraftClient.player.input = input;
            minecraftClient.interactionManager.setGameMode(currentGameMode);
          }
        });
    ClientInitializer.logger.info(
        "Anti-cheat movement verified: 1016 direction cases against vanilla, opposite keys, jump"
            + " edges, flight restoration, boat ownership, rebound movement keys and use"
            + " cooldowns");
  }

  public static void apply(MinecraftClient minecraftClient) {
    float[] floatValues = new float[] {0.0F, 90.0F, 180.0F, -90.0F};
    int length = floatValues.length;
    ProtectionPerformanceTest.ensureInitialized();

    for (int index = 0; index < length; index++) {
      float floatValue = floatValues[index];

      for (int intValue : new int[] {4, 8}) {
        apply9(minecraftClient);
        minecraftClient.player.setYaw(floatValue);
        apply(minecraftClient, intValue, false, false);
        Vec3d vec3d = getVec3d(minecraftClient);
        Vec3d entityPos = minecraftClient.player.getEntityPos();
        MovementOverrides.guiModuleEntry.apply(true);
        apply10(minecraftClient);

        for (int index2 = 0; index2 < 5; index2++) {
          minecraftClient.player.tickMovement();
          apply10(minecraftClient);
        }

        Vec3d vec3d2 =
            minecraftClient.player.getEntityPos().subtract(entityPos).multiply(1.0, 0.0, 1.0);
        apply(
            vec3d2.dotProduct(vec3d) > 1.0,
            "Flight actually moves "
                + (intValue == 4 ? "left" : "right")
                + " at yaw="
                + floatValue);
        apply(
            vec3d2.normalize().dotProduct(vec3d) > 0.999,
            "Flight stays aligned with vanilla steering over multiple frames");
      }
    }
  }

  public static void apply2(MinecraftClient minecraftClient) {
    var list =
        List.of(
            MovementOverrides.guiModuleEntry,
            MovementOverrides.guiModuleEntry2,
            MovementOverrides.guiModuleEntry13,
            MovementOverrides.guiModuleEntry4,
            MovementOverrides.guiModuleEntry11,
            MovementOverrides.guiModuleEntry12,
            MovementOverrides.guiModuleEntry19);
    ProtectionPerformanceTest.ensureInitialized();
    boolean flag;
    float[] floatValues = new float[] {0.0F, 45.0F, 90.0F, 135.0F, 180.0F, -135.0F, -90.0F, -45.0F};

    for (GuiModuleEntry guiModuleEntry :
        (java.lang.Iterable<GuiModuleEntry>) (java.lang.Iterable<?>) (list)) {
      apply9(minecraftClient);
      BoatEntity boatEntity = null;
      if (guiModuleEntry == MovementOverrides.guiModuleEntry2) {
        boatEntity = boat(minecraftClient);
        apply(minecraftClient.player.startRiding(boatEntity, true, true), "mount test boat");
      }

      if (guiModuleEntry == MovementOverrides.guiModuleEntry11) {
        minecraftClient.player.startGliding();
      }

      if (guiModuleEntry == MovementOverrides.guiModuleEntry12
          || guiModuleEntry == MovementOverrides.guiModuleEntry19) {
        apply(minecraftClient, true);
      }

      guiModuleEntry.apply(true);
      double doubleValue = computeDouble(guiModuleEntry);
      Entity entity = boatEntity == null ? minecraftClient.player : boatEntity;

      for (float floatValue : floatValues) {
        minecraftClient.player.setYaw(floatValue);

        for (int index = 0; index < 16; index++) {
          apply(minecraftClient, index, false, false);
          Vec3d vec3d = getVec3d(minecraftClient).multiply(doubleValue);
          if (guiModuleEntry == MovementOverrides.guiModuleEntry11 && index == 0) {
            apply(minecraftClient, 1, false, false);
            vec3d = getVec3d(minecraftClient).multiply(doubleValue);
            apply(minecraftClient, 0, false, false);
          }

          minecraftClient.player.setVelocity(Vec3d.ZERO);
          entity.setVelocity(Vec3d.ZERO);
          apply10(minecraftClient);
          Vec3d velocity = entity.getVelocity();
          flag = test(velocity.x, vec3d.x) && test(velocity.z, vec3d.z);
          String string = guiModuleEntry.getString();
          String text = String.valueOf(vec3d);
          String text2 = String.valueOf(velocity);
          String text3 = text;
          int intValue = index;
          float floatValue2 = floatValue;
          apply(
              flag,
              string
                  + " yaw="
                  + floatValue2
                  + " keys="
                  + intValue
                  + " expected="
                  + text3
                  + " actual="
                  + text2);
          apply(
              test(velocity.horizontalLength(), vec3d.horizontalLength()),
              guiModuleEntry.getString() + " diagonal speed stays constant");
        }
      }
    }

    apply9(minecraftClient);
    MovementOverrides.guiModuleEntry8.apply(true);

    for (float floatValue3 : floatValues) {
      minecraftClient.player.setYaw(floatValue3);

      for (int index2 = 1; index2 < 16; index2++) {
        apply(minecraftClient, index2, false, false);
        Vec3d vec3d2 =
            getVec3d(minecraftClient)
                .multiply(
                    getNumberSettingView(MovementOverrides.guiModuleEntry8, "Horizontal Boost")
                        .getValue());
        minecraftClient.player.jump();
        apply10(minecraftClient);
        Vec3d velocity2 = minecraftClient.player.getVelocity();
        apply(
            test(velocity2.x, vec3d2.x) && test(velocity2.z, vec3d2.z),
            "Long Jump matches vanilla direction");
      }
    }
  }

  public static void apply3(MinecraftClient minecraftClient) {
    ProtectionPerformanceTest.ensureInitialized();

    for (GuiModuleEntry guiModuleEntry :
        List.of(
            MovementOverrides.guiModuleEntry,
            MovementOverrides.guiModuleEntry13,
            MovementOverrides.guiModuleEntry2,
            MovementOverrides.guiModuleEntry11)) {
      apply9(minecraftClient);
      Entity player = minecraftClient.player;
      if (guiModuleEntry == MovementOverrides.guiModuleEntry2) {
        player = boat(minecraftClient);
        minecraftClient.player.startRiding(player, true, true);
      }

      if (guiModuleEntry == MovementOverrides.guiModuleEntry11) {
        minecraftClient.player.startGliding();
      }

      guiModuleEntry.apply(true);
      double value3 = getNumberSettingView(guiModuleEntry, "Vertical Speed").getValue();

      for (int index = 0; index < 4; index++) {
        apply(minecraftClient, 0, (index & 1) != 0, (index & 2) != 0);
        if (guiModuleEntry == MovementOverrides.guiModuleEntry2) {
          minecraftClient.options.sneakKey.setPressed(false);
          minecraftClient.options.sprintKey.setPressed((index & 2) != 0);
          minecraftClient.player.input.tick();
        }

        player.setVelocity(Vec3d.ZERO);
        apply10(minecraftClient);
        double doubleValue = index == 1 ? value3 : (index == 2 ? -value3 : 0.0);
        apply(
            test(player.getVelocity().y, doubleValue),
            guiModuleEntry.getString() + " vertical keys=" + index);
      }
    }
  }

  public static void apply4(MinecraftClient minecraftClient) {
    ProtectionPerformanceTest.ensureInitialized();

    for (GuiModuleEntry guiModuleEntry :
        List.of(MovementOverrides.guiModuleEntry8, MovementOverrides.guiModuleEntry9)) {
      apply9(minecraftClient);
      guiModuleEntry.apply(true);
      apply(minecraftClient, 4, true, false);
      minecraftClient.player.jump();
      minecraftClient.player.setOnGround(false);
      apply10(minecraftClient);
      apply(
          test(
              minecraftClient.player.getVelocity().y,
              getNumberSettingView(guiModuleEntry, "Jump Height").getValue()),
          guiModuleEntry.getString() + " boosts a real ground jump after leaving the ground");
      apply(minecraftClient, 4, false, false);
      apply10(minecraftClient);
      minecraftClient.player.setVelocity(0.0, 0.2, 0.0);
      apply(minecraftClient, 4, true, false);
      apply10(minecraftClient);
      apply(
          test(minecraftClient.player.getVelocity().y, 0.2),
          guiModuleEntry.getString() + " cannot retrigger while rising");
      MovementOverrides.guiModuleEntry10.apply(true);
      apply(minecraftClient, 4, false, false);
      apply10(minecraftClient);
      apply(minecraftClient, 4, true, false);
      apply10(minecraftClient);
      apply(
          test(
              minecraftClient.player.getVelocity().y,
              getNumberSettingView(MovementOverrides.guiModuleEntry10, "Jump Height").getValue()),
          "Air Jump still works alongside " + guiModuleEntry.getString());
      minecraftClient.player.setVelocity(0.0, -0.2, 0.0);
      apply10(minecraftClient);
      apply(
          test(minecraftClient.player.getVelocity().y, -0.2),
          "holding jump cannot repeatedly air-jump");
    }
  }

  public static void apply5(MinecraftClient minecraftClient) {
    ProtectionPerformanceTest.ensureInitialized();

    for (boolean allowFlying : new boolean[] {false, true}) {
      for (int intValue : new int[] {0, 1}) {
        apply9(minecraftClient);
        PlayerAbilities abilities = minecraftClient.player.getAbilities();
        abilities.allowFlying = allowFlying;
        abilities.flying = allowFlying;
        abilities.setFlySpeed(0.073F);
        getChoiceSettingView().selectIndex(intValue);
        MovementOverrides.guiModuleEntry.apply(true);
        apply10(minecraftClient);
        getChoiceSettingView().selectIndex(1 - intValue);
        apply10(minecraftClient);
        MovementOverrides.guiModuleEntry.apply(false);
        apply10(minecraftClient);
        apply(
            abilities.allowFlying == allowFlying
                && abilities.flying == allowFlying
                && test(abilities.getFlySpeed(), 0.073),
            "Flight restores original abilities across mode switches");
      }
    }

    apply9(minecraftClient);
    getChoiceSettingView().selectIndex(1);
    MovementOverrides.guiModuleEntry.apply(true);
    apply10(minecraftClient);
    MovementOverrides.guiModuleEntry13.apply(true);
    apply10(minecraftClient);
    apply(
        !minecraftClient.player.getAbilities().flying && minecraftClient.player.noClip,
        "Phase takes movement control from Flight");
    minecraftClient.player.noClip = false;
    minecraftClient.player.tickMovement();
    apply(
        minecraftClient.player.noClip,
        "Phase is restored before vanilla movement, after the per-tick collision reset");
    apply(method2, null, new Object[] {minecraftClient});
    apply(
        !minecraftClient.player.noClip && !minecraftClient.player.getAbilities().flying,
        "reset restores collision and flight state");
    apply(
        Arrays.stream(MovementOverrides.guiModules()).noneMatch(GuiModuleEntry::test),
        "reset disables every anti-cheat module");
  }

  public static void apply6(MinecraftClient minecraftClient) {
    ProtectionPerformanceTest.ensureInitialized();

    apply9(minecraftClient);
    BoatEntity boatEntity = boat(minecraftClient);
    PigEntity pigEntity = new PigEntity(EntityType.PIG, minecraftClient.world);
    pigEntity.startRiding(boatEntity, true, true);
    minecraftClient.player.startRiding(boatEntity, true, true);
    apply(
        boatEntity.getControllingPassenger() != minecraftClient.player,
        "player occupies passenger seat");
    MovementOverrides.guiModuleEntry2.apply(true);
    apply(minecraftClient, 4, true, false);
    apply10(minecraftClient);
    apply(
        !boatEntity.hasNoGravity() && boatEntity.getVelocity().equals(Vec3d.ZERO),
        "passenger cannot steer someone else's boat");
    minecraftClient.player.stopRiding();
    pigEntity.stopRiding();
    minecraftClient.player.startRiding(boatEntity, true, true);
    apply10(minecraftClient);
    apply(boatEntity.hasNoGravity(), "driver can enable boat flight");
    minecraftClient.player.stopRiding();
    apply10(minecraftClient);
    apply(!boatEntity.hasNoGravity(), "dismount restores boat gravity");
    boatEntity.setNoGravity(true);
    minecraftClient.player.startRiding(boatEntity, true, true);
    apply10(minecraftClient);
    MovementOverrides.guiModuleEntry2.apply(false);
    apply10(minecraftClient);
    apply(boatEntity.hasNoGravity(), "pre-existing boat gravity setting is preserved");
    apply9(minecraftClient);
    minecraftClient.player.startRiding(pigEntity, true, true);
    MovementOverrides.guiModuleEntry4.apply(true);
    MovementOverrides.guiModuleEntry.apply(true);
    apply(minecraftClient, 4, true, false);
    apply10(minecraftClient);
    apply(
        minecraftClient.player.getVelocity().equals(Vec3d.ZERO),
        "player movement modules leave ridden vehicles alone");
  }

  public static void apply7(MinecraftClient minecraftClient) {
    apply9(minecraftClient);
    ProtectionPerformanceTest.ensureInitialized();

    for (KeyBinding keyBinding : getKeyBindingArray(minecraftClient)) {
      Key key = ((KeyBindingAccessor) (Object) (keyBinding)).getBoundKey();
      MovementOverrides.guiModuleEntry.apply(key.getCode());
      apply(
          !MovementOverrides.test(key.getCode()) && minecraftClient.currentScreen == null,
          "movement key cannot open module confirmation: " + key);
      MovementOverrides.guiModuleEntry.apply(true);
      apply(
          !MovementOverrides.test(key.getCode()) && MovementOverrides.guiModuleEntry.test(),
          "movement key cannot disable Flight: " + key);
      MovementOverrides.guiModuleEntry.apply(false);
    }

    Key key2 = ((KeyBindingAccessor) (Object) (minecraftClient.options.leftKey)).getBoundKey();

    try {
      minecraftClient.options.leftKey.setBoundKey(Type.KEYSYM.createFromCode(74));
      KeyBinding.updateKeysByCode();
      MovementOverrides.guiModuleEntry.apply(74);
      MovementOverrides.guiModuleEntry.apply(true);
      minecraftClient.player.setYaw(0.0F);
      apply(
          method3,
          minecraftClient.keyboard,
          new Object[] {minecraftClient.getWindow().getHandle(), 1, new KeyInput(74, 0, 0)});
      minecraftClient.player.input.tick();
      apply10(minecraftClient);
      apply(
          minecraftClient.player.input.playerInput.left()
              && minecraftClient.player.getVelocity().x > 0.0
              && minecraftClient.currentScreen == null
              && MovementOverrides.guiModuleEntry.test(),
          "rebound left key travels through keyboard dispatch and flies left");
    } finally {
      apply(
          method3,
          minecraftClient.keyboard,
          new Object[] {minecraftClient.getWindow().getHandle(), 0, new KeyInput(74, 0, 0)});
      minecraftClient.options.leftKey.setBoundKey(key2);
      KeyBinding.updateKeysByCode();
    }
  }

  public static void apply8(MinecraftClient minecraftClient) {
    ProtectionPerformanceTest.ensureInitialized();

    apply9(minecraftClient);
    MovementOverrides.guiModuleEntry17.apply(true);
    MinecraftClientAccessor minecraftClientAccessor =
        (MinecraftClientAccessor) (Object) (minecraftClient);

    for (int index = 0; index <= 4; index++) {
      getNumberSettingView(MovementOverrides.guiModuleEntry17, "Cooldown").setValue(index);
      minecraftClientAccessor.setItemUseCooldown(4);
      apply10(minecraftClient);
      apply(
          minecraftClientAccessor.getItemUseCooldown() == index, "Fast Use lowers active cooldown");

      for (int index2 = index; index2 >= 0; index2--) {
        minecraftClientAccessor.setItemUseCooldown(index2);
        apply10(minecraftClient);
        apply(
            minecraftClientAccessor.getItemUseCooldown() == index2,
            "Fast Use lets countdown reach zero");
      }
    }

    MovementOverrides.guiModuleEntry18.apply(true);
    getNumberSettingView(MovementOverrides.guiModuleEntry18, "Finish At").setValue(8.0F);
    ItemStack mainHandStack = minecraftClient.player.getMainHandStack();

    try {
      minecraftClient.player.setStackInHand(Hand.MAIN_HAND, new ItemStack(Items.HONEY_BOTTLE));
      minecraftClient.player.setCurrentHand(Hand.MAIN_HAND);
      apply10(minecraftClient);
      apply(minecraftClient.player.getItemUseTimeLeft() <= 8, "Fast Eat accelerates item use");
      ((LivingEntityAccessor) (Object) (minecraftClient.player)).setItemUseTimeLeft(2);
      apply10(minecraftClient);
      apply(
          minecraftClient.player.getItemUseTimeLeft() == 2,
          "Fast Eat never extends an almost-finished item");
    } finally {
      minecraftClient.player.clearActiveItem();
      minecraftClient.player.setStackInHand(Hand.MAIN_HAND, mainHandStack);
    }
  }

  public static void apply9(MinecraftClient minecraftClient) {
    ProtectionPerformanceTest.ensureInitialized();

    for (GuiModuleEntry guiModuleEntry : MovementOverrides.guiModules()) {
      guiModuleEntry.apply(false);
      guiModuleEntry.apply(-1);
    }

    apply10(minecraftClient);
    minecraftClient.player.stopRiding();
    minecraftClient.player.stopGliding();
    minecraftClient.player.setOnGround(false);
    minecraftClient.player.horizontalCollision = false;
    minecraftClient.player.noClip = false;
    apply(minecraftClient, false);
    minecraftClient.player.getAbilities().allowFlying = false;
    minecraftClient.player.getAbilities().flying = false;
    minecraftClient.player.getAbilities().setFlySpeed(0.05F);
    minecraftClient.player.setVelocity(Vec3d.ZERO);
    getChoiceSettingView().selectIndex(0);
    apply(minecraftClient, 0, false, false);
    apply10(minecraftClient);
  }

  public static void apply(
      MinecraftClient minecraftClient, int intValue, boolean flag, boolean flag2) {
    ProtectionPerformanceTest.ensureInitialized();
    minecraftClient.options.forwardKey.setPressed((intValue & 1) != 0);
    minecraftClient.options.backKey.setPressed((intValue & 2) != 0);
    minecraftClient.options.leftKey.setPressed((intValue & 4) != 0);
    minecraftClient.options.rightKey.setPressed((intValue & 8) != 0);
    minecraftClient.options.jumpKey.setPressed(flag);
    minecraftClient.options.sneakKey.setPressed(flag2);
    minecraftClient.options.sprintKey.setPressed(false);
    minecraftClient.player.input.tick();
  }

  public static Vec3d getVec3d(MinecraftClient minecraftClient) {
    Vec2f movementInput = minecraftClient.player.input.getMovementInput();
    minecraftClient.player.setVelocity(Vec3d.ZERO);
    minecraftClient.player.updateVelocity(1.0F, new Vec3d(movementInput.x, 0.0, movementInput.y));
    return minecraftClient.player.getVelocity();
  }

  public static BoatEntity boat(MinecraftClient minecraftClient) {
    BoatEntity boatEntity =
        new BoatEntity(
            EntityType.OAK_BOAT,
            minecraftClient.world,
            () -> {
              return Items.OAK_BOAT;
            });
    boatEntity.setPosition(minecraftClient.player.getEntityPos());
    return boatEntity;
  }

  public static void apply(MinecraftClient minecraftClient, boolean flag) {
    try {
      Field declaredField =
          bunnythekenzo.client.runtime.MappedReflection.field(
              Entity.class, "touchingWater", "field_5957", "Z");
      declaredField.setAccessible(true);
      declaredField.setBoolean(minecraftClient.player, flag);
    } catch (ReflectiveOperationException failure) {
      throw new AssertionError(failure);
    }
  }

  public static KeyBinding[] getKeyBindingArray(MinecraftClient minecraftClient) {
    GameOptions options = minecraftClient.options;
    return new KeyBinding[] {
      options.forwardKey,
      options.backKey,
      options.leftKey,
      options.rightKey,
      options.jumpKey,
      options.sneakKey,
      options.sprintKey
    };
  }

  public static double computeDouble(GuiModuleEntry guiModuleEntry) {
    ProtectionPerformanceTest.ensureInitialized();
    String text =
        guiModuleEntry == MovementOverrides.guiModuleEntry4
            ? "Speed"
            : (guiModuleEntry == MovementOverrides.guiModuleEntry12
                ? "Surface Speed"
                : (guiModuleEntry == MovementOverrides.guiModuleEntry19
                    ? "Water Speed"
                    : "Horizontal Speed"));
    return getNumberSettingView(guiModuleEntry, text).getValue();
  }

  public static NumberSettingView getNumberSettingView(GuiModuleEntry guiModuleEntry, String text) {
    return (NumberSettingView) setting(guiModuleEntry, text);
  }

  public static ChoiceSettingView getChoiceSettingView() {
    return (ChoiceSettingView) setting(MovementOverrides.guiModuleEntry, "Mode");
  }

  public static SettingEntry setting(GuiModuleEntry guiModuleEntry, String text) {
    return (bunnythekenzo.client.setting.SettingEntry)
        (guiModuleEntry.getList().stream()
            .flatMap(
                collectionData -> {
                  return ((bunnythekenzo.client.CollectionData) (collectionData)).list().stream();
                })
            .filter(
                settingEntry -> {
                  return ((bunnythekenzo.client.setting.SettingEntry) (settingEntry))
                      .getName()
                      .equals(text);
                })
            .findFirst()
            .orElseThrow());
  }

  public static void apply10(MinecraftClient minecraftClient) {
    apply(method, null, new Object[] {minecraftClient});
  }

  public static Method getMethod(Class type, String text, Class[] typeArray) {
    try {
      Method declaredMethod = type.getDeclaredMethod(text, typeArray);
      declaredMethod.setAccessible(true);
      return declaredMethod;
    } catch (ReflectiveOperationException failure) {
      throw new AssertionError(failure);
    }
  }

  public static void apply(Method method4, Object value2, Object[] valueArray) {
    try {
      method4.invoke(value2, valueArray);
    } catch (ReflectiveOperationException failure) {
      throw new AssertionError(method4.getName(), failure);
    }
  }

  public static boolean test(double doubleValue, double doubleValue2) {
    ProtectionPerformanceTest.ensureInitialized();
    return Math.abs(doubleValue - doubleValue2) < 3.0E-4;
  }

  public static void apply(boolean flag, String text) {
    if (!flag) {
      throw new AssertionError("Anti-cheat movement: " + text);
    }
  }
}
