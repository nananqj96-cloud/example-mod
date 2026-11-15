package bunnythekenzo.client.gametest;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.entity.Entity.RemovalReason;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.GuiModuleEntry;
import bunnythekenzo.client.JsonSupport2;
import bunnythekenzo.client.module.AimAssistModule;
import bunnythekenzo.client.module.FreeLookModule;
import bunnythekenzo.client.module.FreecamModule;
import bunnythekenzo.client.module.Module;
import bunnythekenzo.client.ui.PanelScreen;

public final class HoppinStandingBeforeTestSupport6 {
  public static void run(ClientGameTestContext clientGameTestContext) {
    clientGameTestContext.runOnClient(
        minecraftClient -> {
          ProtectionPerformanceTest.ensureInitialized();

          JsonObject jsonObject = ClientInitializer.getClientConfig().getJsonObject();
          Vec3d entityPos = minecraftClient.player.getEntityPos();
          float yaw = minecraftClient.player.getYaw();
          float pitch = minecraftClient.player.getPitch();
          double value2 = minecraftClient.options.getMouseSensitivity().getValue();
          boolean value3 = minecraftClient.options.getInvertMouseX().getValue();
          boolean value4 = minecraftClient.options.getInvertMouseY().getValue();
          boolean smoothCameraEnabled = minecraftClient.options.smoothCameraEnabled;
          boolean pressed = minecraftClient.options.attackKey.isPressed();
          OtherClientPlayerEntity otherClientPlayerEntity = null;
          OtherClientPlayerEntity otherClientPlayerEntity2 = null;
          AimAssistModule aimAssistModule = ClientInitializer.getModuleManager().aimAssistModule;
          boolean flag = false  ;

          try {
            flag = true;
            JsonSupport2.getList();

            for (Module module :
                (java.lang.Iterable<Module>)
                    (java.lang.Iterable<?>) (ClientInitializer.getModuleManager().getList())) {
              module.setEnabled(false);
            }

            aimAssistModule
                .getSettings()
                .forEach(
                    (java.util.function.Consumer<bunnythekenzo.client.setting.Setting>)
                        (setting -> {
                          setting.resetToDefault();
                        }));
            minecraftClient.setScreen(null);
            minecraftClient.options.getMouseSensitivity().setValue(0.5);
            minecraftClient.options.getInvertMouseX().setValue(false);
            minecraftClient.options.getInvertMouseY().setValue(false);
            minecraftClient.options.smoothCameraEnabled = false;
            minecraftClient.options.attackKey.setPressed(false);
            minecraftClient.player.setPosition(
                Math.floor(entityPos.x) + 0.5, 200.0, Math.floor(entityPos.z) + 0.5);
            apply(minecraftClient);
            otherClientPlayerEntity = getOtherClientPlayerEntity(minecraftClient, -67201, 1.0, 3.0);
            checkSettings(aimAssistModule);
            apply(
                aimAssistModule.getValueArray(0.016666666666666666, 0.0, 0.0) == null,
                "disabled module is idle");
            aimAssistModule.setEnabled(true);
            double[] doubleValues =
                getValueArray(
                    aimAssistModule.getValueArray(0.016666666666666666, 0.0, 0.0),
                    "default player acquisition");
            apply(
                doubleValues[0] < 0.0 && doubleValues[1] > 0.0,
                "body aim corrects yaw and pitch toward target");
            apply(
                minecraftClient.player.getYaw() == 0.0F
                    && minecraftClient.player.getPitch() == 0.0F,
                "compute does not directly rotate player");
            aimAssistModule.verticalSetting.setValue(false);
            apply(
                getValueArray(
                        aimAssistModule.getValueArray(0.016666666666666666, 0.0, 0.0),
                        "horizontal aim")[1]
                    == 0.0,
                "vertical toggle");
            aimAssistModule.verticalSetting.setValue(true);
            aimAssistModule.rangeSetting.setNumber(Double.valueOf(1.0));
            apply(
                aimAssistModule.getValueArray(0.016666666666666666, 0.0, 0.0) == null,
                "range filter");
            aimAssistModule.rangeSetting.resetToDefault();
            aimAssistModule.fovSetting.setNumber(Double.valueOf(10.0));
            apply(
                aimAssistModule.getValueArray(0.016666666666666666, 0.0, 0.0) == null,
                "FOV filter");
            aimAssistModule.fovSetting.resetToDefault();
            aimAssistModule.playersSetting.setValue(false);
            apply(
                aimAssistModule.getValueArray(0.016666666666666666, 0.0, 0.0) == null,
                "player filter");
            aimAssistModule.playersSetting.setValue(true);
            otherClientPlayerEntity.setInvisible(true);
            apply(
                aimAssistModule.getValueArray(0.016666666666666666, 0.0, 0.0) == null,
                "invisible target excluded");
            aimAssistModule.invisiblesSetting.setValue(true);
            getValueArray(
                aimAssistModule.getValueArray(0.016666666666666666, 0.0, 0.0),
                "invisible target enabled");
            otherClientPlayerEntity.setInvisible(false);
            aimAssistModule.invisiblesSetting.resetToDefault();
            aimAssistModule.alwaysActiveSetting.setValue(false);
            apply(
                aimAssistModule.getValueArray(0.016666666666666666, 0.0, 0.0) == null,
                "attack-only idle");
            minecraftClient.options.attackKey.setPressed(true);
            getValueArray(
                aimAssistModule.getValueArray(0.016666666666666666, 0.0, 0.0),
                "attack-only engaged");

            minecraftClient.options.attackKey.setPressed(false);

            aimAssistModule.alwaysActiveSetting.resetToDefault();

            for (String text : List.of("Head", "Body", "Feet", "Nearest")) {
              aimAssistModule.targetSetting.setValue(text);
              getValueArray(
                  aimAssistModule.getValueArray(0.016666666666666666, 0.0, 0.0),
                  "target part " + text);
            }

            aimAssistModule.targetSetting.resetToDefault();
            otherClientPlayerEntity2 =
                getOtherClientPlayerEntity(minecraftClient, -67202, -1.0, 1.5);
            apply(
                getValueArray(
                        aimAssistModule.getValueArray(0.016666666666666666, 0.0, 0.0),
                        "sticky target")[0]
                    < 0.0,
                "sticky retains first target despite nearer player");
            aimAssistModule.stickyTargetSetting.setValue(false);
            apply(
                getValueArray(
                        aimAssistModule.getValueArray(0.016666666666666666, 0.0, 0.0),
                        "nearest target")[0]
                    > 0.0,
                "nonsticky selects nearer player");
            aimAssistModule.stickyTargetSetting.setValue(true);
            otherClientPlayerEntity2.setHealth(0.0F);
            apply(
                getValueArray(
                        aimAssistModule.getValueArray(0.016666666666666666, 0.0, 0.0),
                        "dead target replacement")[0]
                    < 0.0,
                "dead sticky target is discarded");
            otherClientPlayerEntity2.setHealth(20.0F);
            aimAssistModule.setEnabled(false);
            aimAssistModule.setEnabled(true);
            apply(
                getValueArray(
                        aimAssistModule.getValueArray(0.016666666666666666, 0.0, 0.0),
                        "reenabled target")[0]
                    > 0.0,
                "disable resets sticky target");
            minecraftClient.world.removeEntity(
                otherClientPlayerEntity2.getId(), RemovalReason.DISCARDED);
            otherClientPlayerEntity2 = null;
            apply(minecraftClient, aimAssistModule, otherClientPlayerEntity);
            minecraftClient.setScreen(new InventoryScreen(minecraftClient.player));
            apply(
                aimAssistModule.getValueArray(0.016666666666666666, 0.0, 0.0) == null,
                "inventory pauses assist");
            minecraftClient.setScreen(null);
            getValueArray(
                aimAssistModule.getValueArray(0.016666666666666666, 0.0, 0.0),
                "assist resumes after inventory");
            apply(aimAssistModule.getValueArray(0.0, 0.0, 0.0) == null, "zero frame time");
            apply(
                aimAssistModule.getValueArray(Double.NaN, 0.0, 0.0) == null, "invalid frame time");
            apply(
                aimAssistModule.getValueArray(0.016666666666666666, Double.POSITIVE_INFINITY, 0.0)
                    == null,
                "invalid mouse input");
            doubleValues =
                getValueArray(
                    aimAssistModule.getValueArray(0.016666666666666666, 0.0, 0.0), "normal pull");
            double[] valueArray = aimAssistModule.getValueArray(0.016666666666666666, 10000.0, 0.0);
            apply(
                valueArray == null || Math.abs(valueArray[0]) < Math.abs(doubleValues[0]),
                "fast user movement reduces pull");
            apply(minecraftClient, aimAssistModule);
            FreeLookModule freeLookModule = ClientInitializer.getModuleManager().freeLookModule;
            freeLookModule.modeSetting.setValue("Camera");
            freeLookModule.setEnabled(true);
            apply(freeLookModule.test5(), "FreeLook camera mode active");
            apply(
                aimAssistModule.getValueArray(0.016666666666666666, 0.0, 0.0) == null,
                "FreeLook camera is not steered by assist");
            freeLookModule.setEnabled(false);
            FreecamModule freecamModule = ClientInitializer.getModuleManager().freecamModule;
            freecamModule.setEnabled(true);
            freecamModule.onTick();
            apply(freecamModule.test(), "Freecam active");
            apply(
                aimAssistModule.getValueArray(0.016666666666666666, 0.0, 0.0) == null,
                "detached camera is not steered by assist");
            freecamModule.setEnabled(false);
            PanelScreen panelScreen = new PanelScreen();
            minecraftClient.setScreen(panelScreen);
            apply(panelScreen.test("AimAssist"), "AimAssist settings reachable in panel");
            ClientInitializer.logger.info(
                "AimAssist tests passed: settings, targeting, filters, sticky reset, mouse"
                    + " pipeline, inversion and camera guards");
            flag = false;
          } finally {
            if (flag) {
              aimAssistModule.setEnabled(false);
              ClientInitializer.getModuleManager().freecamModule.setEnabled(false);
              ClientInitializer.getModuleManager().freeLookModule.setEnabled(false);
              if (otherClientPlayerEntity != null) {
                minecraftClient.world.removeEntity(
                    otherClientPlayerEntity.getId(), RemovalReason.DISCARDED);
              }

              if (otherClientPlayerEntity2 != null) {
                minecraftClient.world.removeEntity(
                    otherClientPlayerEntity2.getId(), RemovalReason.DISCARDED);
              }

              minecraftClient.setScreen(null);
              minecraftClient.player.setPosition(entityPos);
              minecraftClient.player.setYaw(yaw);
              minecraftClient.player.setPitch(pitch);
              minecraftClient.options.getMouseSensitivity().setValue(value2);
              minecraftClient.options.getInvertMouseX().setValue(value3);
              minecraftClient.options.getInvertMouseY().setValue(value4);
              minecraftClient.options.smoothCameraEnabled = smoothCameraEnabled;
              minecraftClient.options.attackKey.setPressed(pressed);
              ClientInitializer.getClientConfig().apply(jsonObject);
            }
          }

          aimAssistModule.setEnabled(false);
          ClientInitializer.getModuleManager().freecamModule.setEnabled(false);
          ClientInitializer.getModuleManager().freeLookModule.setEnabled(false);
          if (otherClientPlayerEntity != null) {
            minecraftClient.world.removeEntity(
                otherClientPlayerEntity.getId(), RemovalReason.DISCARDED);
          }

          minecraftClient.setScreen(null);
          minecraftClient.player.setPosition(entityPos);
          minecraftClient.player.setYaw(yaw);
          minecraftClient.player.setPitch(pitch);
          minecraftClient.options.getMouseSensitivity().setValue(value2);
          minecraftClient.options.getInvertMouseX().setValue(value3);
          minecraftClient.options.getInvertMouseY().setValue(value4);
          minecraftClient.options.smoothCameraEnabled = smoothCameraEnabled;
          minecraftClient.options.attackKey.setPressed(pressed);
          ClientInitializer.getClientConfig().apply(jsonObject);
        });
  }

  public static void checkSettings(AimAssistModule aimAssistModule) {
    ProtectionPerformanceTest.ensureInitialized();

    apply(
        aimAssistModule.getSettings().stream()
            .map(
                value2 -> {
                  return ((bunnythekenzo.client.setting.Setting) (value2)).getName();
                })
            .toList()
            .equals(
                List.of(
                    "Range",
                    "Speed",
                    "Smoothness",
                    "Target",
                    "FOV",
                    "Vertical",
                    "Players",
                    "Hostiles",
                    "Passive",
                    "Invisibles",
                    "Wall Check",
                    "Always Active",
                    "Sticky Target")),
        "all original settings retained");
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
                    value3 -> {
                      return ((bunnythekenzo.client.GuiModuleEntry) (value3)).getModule()
                          == aimAssistModule;
                    })
                .findFirst()
                .orElseThrow());
    apply(
        guiModuleEntry.getList().stream()
                .mapToInt(
                    (java.util.function.ToIntFunction<bunnythekenzo.client.CollectionData>)
                        (collectionData -> {
                          return ((java.util.List) (collectionData.list())).size();
                        }))
                .sum()
            == 13,
        "all original GUI settings bound");
  }

  public static void apply(
      MinecraftClient minecraftClient,
      AimAssistModule aimAssistModule,
      OtherClientPlayerEntity otherClientPlayerEntity) {
    Vec3d entityPos = otherClientPlayerEntity.getEntityPos();
    ProtectionPerformanceTest.ensureInitialized();

    otherClientPlayerEntity.setPosition(
        minecraftClient.player.getX(),
        minecraftClient.player.getY(),
        minecraftClient.player.getZ() + 3.0);
    BlockPos blockPos =
        BlockPos.ofFloored(
            minecraftClient.player.getX(),
            minecraftClient.player.getEyeY(),
            minecraftClient.player.getZ() + 1.0);
    BlockState blockState = minecraftClient.world.getBlockState(blockPos);

    try {
      minecraftClient.world.setBlockState(blockPos, Blocks.STONE.getDefaultState(), 3);
      aimAssistModule.wallCheckSetting.setValue(true);
      apply(
          aimAssistModule.getValueArray(0.016666666666666666, 0.0, 0.0) == null,
          "wall check rejects blocked target");
      aimAssistModule.wallCheckSetting.setValue(false);
      getValueArray(
          aimAssistModule.getValueArray(0.016666666666666666, 0.0, 0.0),
          "wall check can be disabled");
    } finally {
      minecraftClient.world.setBlockState(blockPos, blockState, 3);
      otherClientPlayerEntity.setPosition(entityPos);
    }
  }

  public static void apply(MinecraftClient minecraftClient, AimAssistModule aimAssistModule) {
    apply(minecraftClient);
    ProtectionPerformanceTest.ensureInitialized();

    aimAssistModule.setEnabled(false);
    render(minecraftClient, 0.0, 0.0);
    apply(
        minecraftClient.player.getYaw() == 0.0F && minecraftClient.player.getPitch() == 0.0F,
        "disabled mouse hook is idle");
    render(minecraftClient, 12.0, 0.0);
    apply(minecraftClient.player.getYaw() > 0.0F, "real mouse movement still works while disabled");
    apply(minecraftClient);
    aimAssistModule.setEnabled(true);
    aimAssistModule.verticalSetting.setValue(false);
    render(minecraftClient, 0.0, 0.0);
    apply(
        minecraftClient.player.getYaw() < 0.0F && minecraftClient.player.getPitch() == 0.0F,
        "real turnPlayer hook applies horizontal assist");
    apply(minecraftClient);
    aimAssistModule.verticalSetting.setValue(true);
    minecraftClient.options.getInvertMouseX().setValue(true);
    minecraftClient.options.getInvertMouseY().setValue(true);
    render(minecraftClient, 0.0, 0.0);
    apply(
        minecraftClient.player.getYaw() < 0.0F && minecraftClient.player.getPitch() > 0.0F,
        "inverted mouse still aims toward target");
    minecraftClient.options.getInvertMouseX().setValue(false);
    minecraftClient.options.getInvertMouseY().setValue(false);
    apply(minecraftClient);
  }

  public static void render(
      MinecraftClient minecraftClient, double doubleValue, double doubleValue2) {
    try {
      Field declaredField =
          bunnythekenzo.client.runtime.MappedReflection.field(
              Mouse.class, "cursorDeltaX", "field_1789", "D");
      Field declaredField2 =
          bunnythekenzo.client.runtime.MappedReflection.field(
              Mouse.class, "cursorDeltaY", "field_1787", "D");
      Method declaredMethod =
          bunnythekenzo.client.runtime.MappedReflection.method(
              Mouse.class, "updateMouse", "method_1606", "(D)V", double.class);
      declaredField.setAccessible(true);
      declaredField2.setAccessible(true);
      declaredMethod.setAccessible(true);
      double value = declaredField.getDouble(minecraftClient.mouse);
      double value2 = declaredField2.getDouble(minecraftClient.mouse);

      try {
        declaredField.setDouble(minecraftClient.mouse, doubleValue);
        declaredField2.setDouble(minecraftClient.mouse, doubleValue2);
        declaredMethod.invoke(minecraftClient.mouse, 0.016666666666666666);
      } finally {
        declaredField.setDouble(minecraftClient.mouse, value);
        declaredField2.setDouble(minecraftClient.mouse, value2);
      }
    } catch (ReflectiveOperationException failure) {
      throw new AssertionError("Could not exercise vanilla mouse handler", failure);
    }
  }

  public static OtherClientPlayerEntity getOtherClientPlayerEntity(
      MinecraftClient minecraftClient, int intValue, double doubleValue, double doubleValue2) {
    OtherClientPlayerEntity otherClientPlayerEntity =
        new OtherClientPlayerEntity(
            minecraftClient.world, new GameProfile(new UUID(0L, -intValue), "AimTest" + -intValue));
    otherClientPlayerEntity.setId(intValue);
    otherClientPlayerEntity.setPosition(
        minecraftClient.player.getX() + doubleValue,
        minecraftClient.player.getY(),
        minecraftClient.player.getZ() + doubleValue2);
    otherClientPlayerEntity.resetPosition();
    minecraftClient.world.addEntity(otherClientPlayerEntity);
    return otherClientPlayerEntity;
  }

  public static void apply(MinecraftClient minecraftClient) {
    minecraftClient.player.setYaw(0.0F);
    minecraftClient.player.setPitch(0.0F);
  }

  public static double[] getValueArray(double[] doubleValues, String text) {
    ProtectionPerformanceTest.ensureInitialized();
    apply(doubleValues != null && doubleValues.length == 2, text);
    apply(
        Double.isFinite(doubleValues[0])
            && Double.isFinite(doubleValues[1])
            && doubleValues[0] == Math.rint(doubleValues[0])
            && doubleValues[1] == Math.rint(doubleValues[1]),
        text + " uses whole pixels");
    return doubleValues;
  }

  public static void apply(boolean flag, String text) {
    if (!flag) {
      throw new AssertionError("AimAssist: " + text);
    }
  }
}
