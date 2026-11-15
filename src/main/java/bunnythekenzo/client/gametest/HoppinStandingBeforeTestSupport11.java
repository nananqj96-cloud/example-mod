package bunnythekenzo.client.gametest;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.UUID;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil.Key;
import net.minecraft.client.util.InputUtil.Type;
import net.minecraft.entity.Entity.RemovalReason;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import bunnythekenzo.client.AimRotationController;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.GuiModuleEntry;
import bunnythekenzo.client.JsonSupport2;
import bunnythekenzo.client.mixin.KeyBindingAccessor;
import bunnythekenzo.client.module.AimAssistModule;
import bunnythekenzo.client.module.AutoCrystalModule;
import bunnythekenzo.client.module.Module;

public final class HoppinStandingBeforeTestSupport11 {
  public static final BlockPos blockPos = new BlockPos(0, 199, 3);
  public static final BlockPos blockPos2 = blockPos.up();
  public static final double value = 0.016666666666666666;
  public static final Method method =
      getMethod(AutoCrystalModule.class, "queueRotation", new Class[] {BlockPos.class});
  public static final Method method2 =
      bunnythekenzo.client.runtime.MappedReflection.requireMethod(
          Mouse.class, "updateMouse", "method_1606", "(D)V", double.class);
  public static final Field field = getField(AutoCrystalModule.class, "rotationPoint");
  public static final Field field2 =
      bunnythekenzo.client.runtime.MappedReflection.requireField(
          Mouse.class, "cursorDeltaX", "field_1789", "D");
  public static final Field field3 =
      bunnythekenzo.client.runtime.MappedReflection.requireField(
          Mouse.class, "cursorDeltaY", "field_1787", "D");
  public static final long timestamp = 67293L;

  public static void run(ClientGameTestContext clientGameTestContext) {
    clientGameTestContext.runOnClient(
        minecraftClient -> {
          JsonObject jsonObject = ClientInitializer.getClientConfig().getJsonObject();
          Vec3d entityPos = minecraftClient.player.getEntityPos();
          float yaw = minecraftClient.player.getYaw();
          float pitch = minecraftClient.player.getPitch();
          double value2 = minecraftClient.options.getMouseSensitivity().getValue();
          ProtectionPerformanceTest.ensureInitialized();
          boolean flag;
          boolean value3 = minecraftClient.options.getInvertMouseX().getValue();
          boolean value4 = minecraftClient.options.getInvertMouseY().getValue();
          boolean smoothCameraEnabled = minecraftClient.options.smoothCameraEnabled;
          Key key = ((KeyBindingAccessor) (Object) (minecraftClient.options.useKey)).getBoundKey();
          BlockState blockState = minecraftClient.world.getBlockState(blockPos);
          BlockState blockState2 = minecraftClient.world.getBlockState(blockPos2);
          ItemStack[] itemStackArray = new ItemStack[9];

          for (int index = 0; index < 9; index++) {
            itemStackArray[index] = minecraftClient.player.getInventory().getStack(index).copy();
          }

          int selectedSlot = minecraftClient.player.getInventory().getSelectedSlot();
          AutoCrystalModule autoCrystalModule =
              ClientInitializer.getModuleManager().autoCrystalModule;
          OtherClientPlayerEntity otherClientPlayerEntity = null;
          boolean flag2 = false  ;

          try {
            flag2 = true;

            for (Module module :
                (java.lang.Iterable<Module>)
                    (java.lang.Iterable<?>) (ClientInitializer.getModuleManager().getList())) {
              module.setEnabled(false);
            }

            autoCrystalModule
                .getSettings()
                .forEach(
                    (java.util.function.Consumer<bunnythekenzo.client.setting.Setting>)
                        (setting -> {
                          setting.resetToDefault();
                        }));
            minecraftClient.setScreen(null);
            minecraftClient.onWindowFocusChanged(true);
            minecraftClient.options.getMouseSensitivity().setValue(0.5);
            minecraftClient.options.getInvertMouseX().setValue(false);
            minecraftClient.options.getInvertMouseY().setValue(false);
            minecraftClient.options.smoothCameraEnabled = false;
            minecraftClient.options.useKey.setBoundKey(Type.MOUSE.createFromCode(1));
            KeyBinding.updateKeysByCode();
            minecraftClient.options.useKey.setPressed(true);
            minecraftClient.player.setPosition(0.5, 200.25, 0.5);

            for (EndCrystalEntity endCrystalEntity :
                minecraftClient.world.getNonSpectatingEntities(
                    EndCrystalEntity.class, new Box(blockPos2).expand(2.0))) {
              minecraftClient.world.removeEntity(endCrystalEntity.getId(), RemovalReason.DISCARDED);
            }

            minecraftClient.world.setBlockState(blockPos, Blocks.STONE.getDefaultState(), 3);
            minecraftClient.world.setBlockState(blockPos2, Blocks.AIR.getDefaultState(), 3);
            minecraftClient.player.getInventory().setStack(0, new ItemStack(Items.DIAMOND_SWORD));
            minecraftClient.player.getInventory().setStack(1, new ItemStack(Items.END_CRYSTAL, 64));
            minecraftClient.player.getInventory().setStack(2, new ItemStack(Items.OBSIDIAN, 64));
            minecraftClient.player.getInventory().setSelectedSlot(0);
            otherClientPlayerEntity =
                new OtherClientPlayerEntity(
                    minecraftClient.world,
                    new GameProfile(new UUID(0L, timestamp), "CrystalAimTest"));
            otherClientPlayerEntity.setId(-67293);
            otherClientPlayerEntity.setPosition(1.5, 200.25, 3.5);
            otherClientPlayerEntity.resetPosition();
            minecraftClient.world.addEntity(otherClientPlayerEntity);
            autoCrystalModule.setEnabled(true);
            apply(minecraftClient, Vec3d.ofBottomCenter(blockPos2));

            try {
              bunnythekenzo.client.runtime.MappedReflection.requireMethod(
                      ClientPlayerEntity.class, "sendMovementPackets", "method_3136", "()V")
                  .invoke(minecraftClient.player);
            } catch (ReflectiveOperationException failure) {
              throw new AssertionError(failure);
            }

            float yaw2 = minecraftClient.player.getYaw();
            float pitch2 = minecraftClient.player.getPitch();
            autoCrystalModule.onTick();
            apply(
                minecraftClient.world.getBlockState(blockPos2).isOf(Blocks.OBSIDIAN)
                    && autoCrystalModule.test(),
                "placing obsidian starts mouse assist");
            apply(
                yaw2 == minecraftClient.player.getYaw()
                    && pitch2 == minecraftClient.player.getPitch(),
                "placement never snaps player rotation");
            autoCrystalModule.onTick();
            apply(
                minecraftClient.player.getMainHandStack().isOf(Items.OBSIDIAN),
                "side-face aim waits for the mouse pull before placing a crystal");
            GuiModuleEntry guiModuleEntry =
                (bunnythekenzo.client.GuiModuleEntry)
                    (JsonSupport2.getList().stream()
                        .flatMap(
                            (java.util.function.Function<
                                    bunnythekenzo.client.CollectionData3, java.util.stream.Stream<?>>)
                                (collectionData3 -> {
                                  return ((java.util.List) (collectionData3.list())).stream();
                                }))
                        .filter(
                            value5 -> {
                              return ((bunnythekenzo.client.GuiModuleEntry) (value5)).getModule()
                                  == autoCrystalModule;
                            })
                        .findFirst()
                        .orElseThrow());
            apply(
                guiModuleEntry.getList().stream()
                        .mapToInt(
                            (java.util.function.ToIntFunction<bunnythekenzo.client.CollectionData>)
                                (collectionData -> {
                                  return collectionData.list().size();
                                }))
                        .sum()
                    == 13,
                "original and new settings are exposed");
            autoCrystalModule.rotationSpeedSetting.setNumber(Double.valueOf(8.5));
            autoCrystalModule.aimVariationSetting.setNumber(Double.valueOf(0.3));
            autoCrystalModule.targetRangeSetting.setNumber(Double.valueOf(7.5));
            autoCrystalModule.damageCheckSetting.setValue(true);
            autoCrystalModule.showStatusSetting.setValue(true);
            autoCrystalModule.requireTargetSetting.setValue(false);
            JsonElement jsonElement = autoCrystalModule.rotationSpeedSetting.toJson();
            JsonElement jsonElement2 = autoCrystalModule.aimVariationSetting.toJson();
            JsonElement jsonElement3 = autoCrystalModule.targetRangeSetting.toJson();
            JsonElement jsonElement4 = autoCrystalModule.damageCheckSetting.toJson();
            JsonElement jsonElement5 = autoCrystalModule.showStatusSetting.toJson();
            JsonElement jsonElement6 = autoCrystalModule.requireTargetSetting.toJson();
            autoCrystalModule.rotationSpeedSetting.resetToDefault();
            autoCrystalModule.aimVariationSetting.resetToDefault();
            autoCrystalModule.targetRangeSetting.resetToDefault();
            autoCrystalModule.damageCheckSetting.resetToDefault();
            autoCrystalModule.showStatusSetting.resetToDefault();
            autoCrystalModule.requireTargetSetting.resetToDefault();
            apply(
                (Boolean) autoCrystalModule.requireTargetSetting.getValue(),
                "target requirement keeps the existing default");
            apply(
                !(Boolean) autoCrystalModule.damageCheckSetting.getValue()
                    && !(Boolean) autoCrystalModule.showStatusSetting.getValue(),
                "server compatibility and quiet status defaults");
            autoCrystalModule.rotationSpeedSetting.fromJson(jsonElement);
            autoCrystalModule.aimVariationSetting.fromJson(jsonElement2);
            autoCrystalModule.targetRangeSetting.fromJson(jsonElement3);
            autoCrystalModule.damageCheckSetting.fromJson(jsonElement4);
            autoCrystalModule.showStatusSetting.fromJson(jsonElement5);
            autoCrystalModule.requireTargetSetting.fromJson(jsonElement6);
            apply(
                !(Boolean) autoCrystalModule.requireTargetSetting.getValue(),
                "optional target setting survives serialization");
            autoCrystalModule.requireTargetSetting.resetToDefault();
            apply(
                (Boolean) autoCrystalModule.damageCheckSetting.getValue()
                    && (Boolean) autoCrystalModule.showStatusSetting.getValue(),
                "target and status settings survive serialization");
            autoCrystalModule.damageCheckSetting.resetToDefault();
            autoCrystalModule.showStatusSetting.resetToDefault();
            apply(
                (Double) autoCrystalModule.rotationSpeedSetting.getValue() == 8.5
                    && (Double) autoCrystalModule.aimVariationSetting.getValue() == 0.3
                    && (Double) autoCrystalModule.targetRangeSetting.getValue() == 7.5,
                "new settings survive serialization");
            HashSet hashSet = new HashSet();

            for (int index2 = 0; index2 < 32; index2++) {
              apply(autoCrystalModule);
              Vec3d vec3d = getVec3d(autoCrystalModule);
              hashSet.add(vec3d);
              apply(
                  vec3d.y == 201.0
                      && vec3d.x >= 0.2
                      && vec3d.x <= 0.8
                      && vec3d.z >= 3.2
                      && vec3d.z <= 3.8,
                  "varied aim stays safely inside the top face: " + vec3d);
            }

            apply(hashSet.size() > 1, "new bases receive varying target points");
            Vec3d vec3d2 = getVec3d(autoCrystalModule);

            for (int index3 = 0; index3 < 4; index3++) {
              autoCrystalModule.getValueArray(0.016666666666666666, 0.0, 0.0);
            }

            apply(
                vec3d2.equals(getVec3d(autoCrystalModule)),
                "target does not jitter between frames");
            autoCrystalModule.aimVariationSetting.setNumber(Double.valueOf(0.0));
            apply(autoCrystalModule);
            apply(
                getVec3d(autoCrystalModule).equals(Vec3d.ofBottomCenter(blockPos2.up())),
                "zero variation uses the centre");
            double doubleValue =
                computeDouble(minecraftClient, autoCrystalModule, 1.0, 12, 0.016666666666666666);
            double doubleValue2 =
                computeDouble(minecraftClient, autoCrystalModule, 10.0, 12, 0.016666666666666666);
            flag = doubleValue2 < doubleValue * 0.5;
            double doubleValue3 = doubleValue2;
            double doubleValue4 = doubleValue;
            apply(
                flag,
                "speed changes small-angle easing: slow="
                    + doubleValue4
                    + ", fast="
                    + doubleValue3);
            double doubleValue5 =
                computeDouble(minecraftClient, autoCrystalModule, 4.0, 6, 0.03333333333333333);
            double doubleValue6 =
                computeDouble(minecraftClient, autoCrystalModule, 4.0, 29, 0.006944444444444444);
            flag = Math.abs(doubleValue5 - doubleValue6) < 1.2;
            double doubleValue7 = doubleValue6;
            double doubleValue8 = doubleValue5;
            apply(
                flag,
                "similar travel at different frame rates: " + doubleValue8 + ", " + doubleValue7);

            for (boolean flag3 : new boolean[] {false, true}) {
              minecraftClient.options.getInvertMouseX().setValue(flag3);
              minecraftClient.options.getInvertMouseY().setValue(flag3);
              autoCrystalModule.aimVariationSetting.setNumber(Double.valueOf(0.2));
              apply(minecraftClient, Vec3d.ofBottomCenter(blockPos2));
              apply(autoCrystalModule);
              double doubleValue9 = computeDouble(minecraftClient, autoCrystalModule);
              float yaw3 = minecraftClient.player.getYaw();
              float pitch3 = minecraftClient.player.getPitch();
              apply(minecraftClient, 0.016666666666666666);
              double doubleValue10 = computeDouble(minecraftClient, autoCrystalModule);
              apply(
                  doubleValue10 < doubleValue9 && doubleValue10 > doubleValue9 * 0.4,
                  "mouse hook eases toward target without snapping, inverted=" + flag3);
              apply(
                  test(minecraftClient.player.getYaw() - yaw3)
                      && test(minecraftClient.player.getPitch() - pitch3),
                  "correction uses vanilla whole-pixel sensitivity steps");
            }

            minecraftClient.options.getInvertMouseX().setValue(false);
            minecraftClient.options.getInvertMouseY().setValue(false);
            autoCrystalModule.aimVariationSetting.setNumber(Double.valueOf(0.0));
            apply(minecraftClient, Vec3d.ofBottomCenter(blockPos2));
            apply(autoCrystalModule);
            double doubleValue11 = computeDouble(minecraftClient, autoCrystalModule);
            apply(minecraftClient, 5.0);
            apply(
                computeDouble(minecraftClient, autoCrystalModule) > doubleValue11 * 0.3,
                "a long frame cannot snap directly to the goal");
            AimAssistModule aimAssistModule = ClientInitializer.getModuleManager().aimAssistModule;
            aimAssistModule
                .getSettings()
                .forEach(
                    (java.util.function.Consumer<bunnythekenzo.client.setting.Setting>)
                        (setting2 -> {
                          setting2.resetToDefault();
                        }));
            aimAssistModule.setEnabled(true);
            apply(minecraftClient, Vec3d.ofBottomCenter(blockPos2));
            apply(autoCrystalModule);
            apply(minecraftClient, 0.016666666666666666);
            apply(
                Math.abs(minecraftClient.player.getYaw()) < 0.01,
                "entity AimAssist does not compete with the obsidian pull");
            minecraftClient.options.useKey.setPressed(false);
            apply(minecraftClient, 0.016666666666666666);
            apply(
                !autoCrystalModule.test() && minecraftClient.player.getYaw() < -0.1,
                "release immediately gives the mouse back to AimAssist");
            aimAssistModule.setEnabled(false);
            minecraftClient.options.useKey.setPressed(true);
            apply(minecraftClient, Vec3d.ofBottomCenter(blockPos2));
            apply(autoCrystalModule);
            minecraftClient.player.setYaw(90.0F);
            apply(
                autoCrystalModule.getValueArray(0.016666666666666666, 0.0, 0.0) == null
                    && !autoCrystalModule.test(),
                "manual look-away cancels the pull");
            apply(minecraftClient, Vec3d.ofBottomCenter(blockPos2));
            apply(autoCrystalModule);
            minecraftClient.setScreen(new GameMenuScreen(false));
            apply(
                autoCrystalModule.getValueArray(0.016666666666666666, 0.0, 0.0) == null
                    && !autoCrystalModule.test(),
                "menus cancel pending motion");
            minecraftClient.setScreen(null);
            minecraftClient.options.useKey.setPressed(true);
            apply(autoCrystalModule);
            minecraftClient.onWindowFocusChanged(false);
            apply(
                autoCrystalModule.getValueArray(0.016666666666666666, 0.0, 0.0) == null
                    && !autoCrystalModule.test(),
                "focus loss cancels pending motion");
            minecraftClient.onWindowFocusChanged(true);
            apply(autoCrystalModule);
            apply(
                autoCrystalModule.getValueArray(Double.NaN, 0.0, 0.0) == null
                    && !autoCrystalModule.test(),
                "invalid frame times clear residual motion");
            apply(autoCrystalModule);
            autoCrystalModule.setEnabled(false);
            apply(
                !autoCrystalModule.test()
                    && autoCrystalModule.getValueArray(0.016666666666666666, 0.0, 0.0) == null,
                "disable clears motion and target");
            ClientInitializer.logger.info(
                "Auto Crystal rotation verified: no snap, shared mouse easing, speed, frame rates,"
                    + " varied stable points, inversion, whole pixels, AimAssist priority and"
                    + " cancellation");
            flag2 = false;
          } finally {
            if (flag2) {
              autoCrystalModule.setEnabled(false);
              ClientInitializer.getModuleManager().aimAssistModule.setEnabled(false);
              minecraftClient.options.useKey.setPressed(false);
              if (otherClientPlayerEntity != null) {
                minecraftClient.world.removeEntity(
                    otherClientPlayerEntity.getId(), RemovalReason.DISCARDED);
              }

              minecraftClient.world.setBlockState(blockPos, blockState, 3);
              minecraftClient.world.setBlockState(blockPos2, blockState2, 3);

              for (int index4 = 0; index4 < 9; index4++) {
                minecraftClient.player.getInventory().setStack(index4, itemStackArray[index4]);
              }

              minecraftClient.player.getInventory().setSelectedSlot(selectedSlot);
              minecraftClient.player.setPosition(entityPos);
              minecraftClient.player.setYaw(yaw);
              minecraftClient.player.setPitch(pitch);
              minecraftClient.options.getMouseSensitivity().setValue(value2);
              minecraftClient.options.getInvertMouseX().setValue(value3);
              minecraftClient.options.getInvertMouseY().setValue(value4);
              minecraftClient.options.smoothCameraEnabled = smoothCameraEnabled;
              minecraftClient.options.useKey.setBoundKey(key);
              KeyBinding.updateKeysByCode();
              minecraftClient.setScreen(null);
              ClientInitializer.getClientConfig().apply(jsonObject);
            }
          }

          autoCrystalModule.setEnabled(false);
          ClientInitializer.getModuleManager().aimAssistModule.setEnabled(false);
          minecraftClient.options.useKey.setPressed(false);
          minecraftClient.world.removeEntity(
              otherClientPlayerEntity.getId(), RemovalReason.DISCARDED);
          minecraftClient.world.setBlockState(blockPos, blockState, 3);
          minecraftClient.world.setBlockState(blockPos2, blockState2, 3);

          for (int index5 = 0; index5 < 9; index5++) {
            minecraftClient.player.getInventory().setStack(index5, itemStackArray[index5]);
          }

          minecraftClient.player.getInventory().setSelectedSlot(selectedSlot);
          minecraftClient.player.setPosition(entityPos);
          minecraftClient.player.setYaw(yaw);
          minecraftClient.player.setPitch(pitch);
          minecraftClient.options.getMouseSensitivity().setValue(value2);
          minecraftClient.options.getInvertMouseX().setValue(value3);
          minecraftClient.options.getInvertMouseY().setValue(value4);
          minecraftClient.options.smoothCameraEnabled = smoothCameraEnabled;
          minecraftClient.options.useKey.setBoundKey(key);
          KeyBinding.updateKeysByCode();
          minecraftClient.setScreen(null);
          ClientInitializer.getClientConfig().apply(jsonObject);
        });
  }

  public static double computeDouble(
      MinecraftClient minecraftClient,
      AutoCrystalModule autoCrystalModule,
      double doubleValue,
      int intValue,
      double doubleValue2) {
    autoCrystalModule.rotationSpeedSetting.setNumber(Double.valueOf(doubleValue));
    ProtectionPerformanceTest.ensureInitialized();

    apply(minecraftClient, Vec3d.ofBottomCenter(blockPos2));
    apply(autoCrystalModule);

    for (int index = 0; index < intValue; index++) {
      apply(minecraftClient, doubleValue2);
    }

    return computeDouble(minecraftClient, autoCrystalModule);
  }

  public static boolean test(double doubleValue) {
    ProtectionPerformanceTest.ensureInitialized();
    return Math.abs(doubleValue / 0.15 - Math.rint(doubleValue / 0.15)) < 1.0E-4;
  }

  public static double computeDouble(
      MinecraftClient minecraftClient, AutoCrystalModule autoCrystalModule) {
    double[] valueArray =
        AimRotationController.getValueArray(minecraftClient, getVec3d(autoCrystalModule), true);
    return Math.hypot(valueArray[0], valueArray[1]);
  }

  public static Vec3d getVec3d(AutoCrystalModule autoCrystalModule) {
    try {
      return (Vec3d) field.get(autoCrystalModule);
    } catch (ReflectiveOperationException failure) {
      throw new AssertionError(failure);
    }
  }

  public static void apply(AutoCrystalModule autoCrystalModule) {
    try {
      method.invoke(autoCrystalModule, blockPos2);
    } catch (ReflectiveOperationException failure) {
      throw new AssertionError(failure);
    }
  }

  public static void apply(MinecraftClient minecraftClient, double doubleValue) {
    try {
      double value2 = field2.getDouble(minecraftClient.mouse);
      double value3 = field3.getDouble(minecraftClient.mouse);

      try {
        field2.setDouble(minecraftClient.mouse, 0.0);
        field3.setDouble(minecraftClient.mouse, 0.0);
        method2.invoke(minecraftClient.mouse, doubleValue);
      } finally {
        field2.setDouble(minecraftClient.mouse, value2);
        field3.setDouble(minecraftClient.mouse, value3);
      }
    } catch (ReflectiveOperationException failure) {
      throw new AssertionError(failure);
    }
  }

  public static void apply(MinecraftClient minecraftClient, Vec3d vec3d) {
    Vec3d vec3d2 = vec3d.subtract(minecraftClient.player.getEyePos());
    minecraftClient.player.setYaw((float) Math.toDegrees(Math.atan2(-vec3d2.x, vec3d2.z)));
    minecraftClient.player.setPitch(
        (float) (-Math.toDegrees(Math.atan2(vec3d2.y, vec3d2.horizontalLength()))));
  }

  public static Method getMethod(Class type, String text, Class[] typeArray) {
    try {
      Method declaredMethod = type.getDeclaredMethod(text, typeArray);
      declaredMethod.setAccessible(true);
      return declaredMethod;
    } catch (ReflectiveOperationException failure) {
      throw new ExceptionInInitializerError(failure);
    }
  }

  public static Field getField(Class type, String text) {
    try {
      Field declaredField = type.getDeclaredField(text);
      declaredField.setAccessible(true);
      return declaredField;
    } catch (ReflectiveOperationException failure) {
      throw new ExceptionInInitializerError(failure);
    }
  }

  public static void apply(boolean flag, String text) {
    if (!flag) {
      throw new AssertionError(text);
    }
  }
}
