package bunnythekenzo.client.gametest;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.UUID;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.client.option.Perspective;
import net.minecraft.entity.Entity.RemovalReason;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.util.math.BlockPos;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.GuiModuleEntry;
import bunnythekenzo.client.module.ModuleCategory;
import bunnythekenzo.client.module.TargetESPModule;
import bunnythekenzo.client.setting.Setting;
import bunnythekenzo.client.ui.PanelScreen;

public final class TargetEspTest {

  public static void run(ClientGameTestContext clientGameTestContext) {
    OtherClientPlayerEntity[] otherClientPlayerEntityArray = new OtherClientPlayerEntity[2];
    ProtectionPerformanceTest.ensureInitialized();

    Runnable onClient =
        clientGameTestContext.computeOnClient(
            minecraftClient -> {
              ProtectionPerformanceTest.ensureInitialized();

              TargetESPModule targetESPModule =
                  ClientInitializer.getModuleManager().targetESPModule;
              apply(
                  targetESPModule != null
                      && targetESPModule.getModuleCategory() == ModuleCategory.VISUALS,
                  "registered under Visuals");
              LinkedHashMap linkedHashMap = new LinkedHashMap();

              for (Setting setting :
                  (java.lang.Iterable<Setting>)
                      (java.lang.Iterable<?>) (targetESPModule.getSettings())) {
                linkedHashMap.put(setting, setting.toJson());
              }

              boolean enabled = targetESPModule.isEnabled();
              Perspective perspective = minecraftClient.options.getPerspective();
              int value3 = minecraftClient.options.getFov().getValue();
              minecraftClient.options.setPerspective(Perspective.FIRST_PERSON);
              minecraftClient.options.getFov().setValue(55);
              minecraftClient.setScreen(null);
              apply(minecraftClient, 0.0F);
              otherClientPlayerEntityArray[0] =
                  getOtherClientPlayerEntity(minecraftClient, -67001, "Target Preview", 4.5);
              otherClientPlayerEntityArray[1] =
                  getOtherClientPlayerEntity(minecraftClient, -67002, "Second Target", 3.0);
              otherClientPlayerEntityArray[1].setPosition(
                  otherClientPlayerEntityArray[1].getX() + 4.0,
                  otherClientPlayerEntityArray[1].getY(),
                  otherClientPlayerEntityArray[1].getZ());
              otherClientPlayerEntityArray[1].resetPosition();
              targetESPModule.setEnabled(true);
              return () -> {
                targetESPModule.setEnabled(false);
                OtherClientPlayerEntity[] otherClientPlayerEntityArray2 =
                    otherClientPlayerEntityArray;
                ProtectionPerformanceTest.ensureInitialized();

                for (OtherClientPlayerEntity otherClientPlayerEntity :
                    otherClientPlayerEntityArray2) {
                  minecraftClient.world.removeEntity(
                      otherClientPlayerEntity.getId(), RemovalReason.DISCARDED);
                }

                linkedHashMap.forEach(
                    (referenceArgument53_0, referenceArgument53_1) ->
                        ((bunnythekenzo.client.setting.Setting) referenceArgument53_0)
                            .fromJson((com.google.gson.JsonElement) referenceArgument53_1));
                targetESPModule.setEnabled(enabled);
                minecraftClient.options.setPerspective(perspective);
                minecraftClient.options.getFov().setValue(value3);
                minecraftClient.setScreen(null);
              };
            });

    try {
      clientGameTestContext.runOnClient(
          minecraftClient2 -> {
            checkBehavior(
                minecraftClient2, otherClientPlayerEntityArray[0], otherClientPlayerEntityArray[1]);
          });

      for (String text : new String[] {"Orbit", "Helix", "Scan"}) {
        clientGameTestContext.runOnClient(
            minecraftClient3 -> {
              TargetESPModule targetESPModule2 =
                  ClientInitializer.getModuleManager().targetESPModule;
              targetESPModule2.update4();
              targetESPModule2.styleSetting.setValue(text);
              targetESPModule2.setEnabled(true);
              apply(minecraftClient3, 0.0F);
            });
        clientGameTestContext.waitTicks(25);
        clientGameTestContext.runOnClient(
            minecraftClient4 -> {
              apply(
                  ClientInitializer.getModuleManager().targetESPModule.getTargetESPSupport()
                      != null,
                  text + " acquires a target from the live render event");
            });
        clientGameTestContext.takeScreenshot("target-esp-" + text.toLowerCase(Locale.ROOT));
      }

      clientGameTestContext.runOnClient(
          minecraftClient5 -> {
            TargetESPModule targetESPModule3 = ClientInitializer.getModuleManager().targetESPModule;
            targetESPModule3.styleSetting.setValue("Orbit");
            targetESPModule3.throughWallsSetting.setValue(true);
            targetESPModule3.colorModeSetting.setValue("Health");
            targetESPModule3.animationSpeedSetting.setNumber(Double.valueOf(0.0));
          });
      clientGameTestContext.waitTicks(10);
      clientGameTestContext.takeScreenshot("target-esp-health-through");
      clientGameTestContext.runOnClient(
          minecraftClient6 -> {
            TargetESPModule targetESPModule4 = ClientInitializer.getModuleManager().targetESPModule;
            targetESPModule4.colorModeSetting.setValue("Gradient");
            targetESPModule4.animationSpeedSetting.setNumber(Double.valueOf(1.0));
            targetESPModule4.throughWallsSetting.setValue(false);
            PanelScreen panelScreen = new PanelScreen();
            minecraftClient6.setScreen(panelScreen);
            apply(panelScreen.test("Target ESP"), "settings pane is reachable");
          });
      clientGameTestContext.waitTicks(10);
      clientGameTestContext.takeScreenshot("target-esp-settings");
      ClientInitializer.logger.info(
          "Target ESP verified: selection, occlusion, range, transitions, cleanup and three render"
              + " styles");
    } finally {
      clientGameTestContext.runOnClient(
          minecraftClient7 -> {
            onClient.run();
          });
    }
  }

  public static void checkBehavior(
      MinecraftClient minecraftClient,
      OtherClientPlayerEntity otherClientPlayerEntity,
      OtherClientPlayerEntity otherClientPlayerEntity2) {
    TargetESPModule targetESPModule = ClientInitializer.getModuleManager().targetESPModule;
    targetESPModule.update4();
    ProtectionPerformanceTest.ensureInitialized();

    long longValue = System.nanoTime();
    targetESPModule.render(minecraftClient, 1.0F, longValue);
    apply(
        targetESPModule.getTargetESPSupport() != null
            && targetESPModule.getTargetESPSupport().playerEntity == otherClientPlayerEntity,
        "crosshair acquisition beyond attack reach");

    for (int index = 0; index < 4; index++) {
      targetESPModule.render(minecraftClient, 1.0F, longValue += 100000000L);
    }

    apply(
        targetESPModule.getTargetESPSupport().computeFloat() > 0.99F,
        "smooth entrance reaches full opacity");
    apply(minecraftClient, 90.0F);
    long longValue2;
    targetESPModule.render(minecraftClient, 1.0F, longValue2 = longValue + 100000000L);
    apply(
        targetESPModule.getTargetESPSupport() != null,
        "hold time keeps the last target when looking away");
    targetESPModule.holdTimeSetting.setNumber(Double.valueOf(0.0));
    targetESPModule.render(minecraftClient, 1.0F, longValue = longValue2 + 100000000L);
    apply(
        targetESPModule.getTargetESPSupport() != null
            && targetESPModule.getTargetESPSupport().computeFloat() > 0.0F
            && targetESPModule.getTargetESPSupport().computeFloat() < 1.0F,
        "looking away fades continuously");

    for (int index2 = 0; index2 < 8; index2++) {
      targetESPModule.render(minecraftClient, 1.0F, longValue += 100000000L);
    }

    apply(
        targetESPModule.getTargetESPSupport() == null
            && targetESPModule.getTargetESPSupport2() == null,
        "fade expires completely");
    targetESPModule.holdTimeSetting.resetToDefault();
    apply(minecraftClient, 0.0F);
    targetESPModule.rangeSetting.setNumber(Double.valueOf(2.0));
    long longValue3;
    targetESPModule.render(minecraftClient, 1.0F, longValue3 = longValue + 100000000L);
    apply(targetESPModule.getTargetESPSupport() == null, "range limit rejects distant players");
    targetESPModule.rangeSetting.resetToDefault();
    otherClientPlayerEntity.setInvisible(true);
    targetESPModule.render(minecraftClient, 1.0F, longValue = longValue3 + 100000000L);
    apply(
        targetESPModule.getTargetESPSupport() == null, "invisible players are excluded by default");
    targetESPModule.invisiblePlayersSetting.setValue(true);
    long longValue4;
    targetESPModule.render(minecraftClient, 1.0F, longValue4 = longValue + 100000000L);
    apply(targetESPModule.getTargetESPSupport() != null, "invisible-player option takes effect");
    otherClientPlayerEntity.setInvisible(false);
    targetESPModule.invisiblePlayersSetting.resetToDefault();
    otherClientPlayerEntity2.setPosition(
        minecraftClient.player.getX(),
        minecraftClient.player.getY(),
        minecraftClient.player.getZ() + 3.0);
    otherClientPlayerEntity2.resetPosition();
    targetESPModule.render(minecraftClient, 1.0F, longValue = longValue4 + 100000000L);
    apply(
        targetESPModule.getTargetESPSupport().playerEntity == otherClientPlayerEntity2
            && targetESPModule.getTargetESPSupport2() != null
            && targetESPModule.getTargetESPSupport2().playerEntity == otherClientPlayerEntity,
        "nearest player wins and old target fades separately");
    otherClientPlayerEntity2.setPosition(
        otherClientPlayerEntity2.getX() + 4.0,
        otherClientPlayerEntity2.getY(),
        otherClientPlayerEntity2.getZ());
    otherClientPlayerEntity2.resetPosition();
    long longValue5;
    targetESPModule.render(minecraftClient, 1.0F, longValue5 = longValue + 100000000L);
    apply(
        targetESPModule.getTargetESPSupport().playerEntity == otherClientPlayerEntity,
        "rapid reacquisition recovers the previous target");
    BlockPos blockPos =
        BlockPos.ofFloored(
            minecraftClient.player.getX(),
            minecraftClient.player.getEyeY(),
            minecraftClient.player.getZ() + 2.0);
    BlockState blockState = minecraftClient.world.getBlockState(blockPos);

    try {
      minecraftClient.world.setBlockState(blockPos, Blocks.STONE.getDefaultState(), 3);
      targetESPModule.update4();
      targetESPModule.throughWallsSetting.setValue(true);
      targetESPModule.render(minecraftClient, 1.0F, longValue = longValue5 + 100000000L);
      apply(
          targetESPModule.getTargetESPSupport() == null,
          "even through-wall rendering cannot acquire through terrain");
    } finally {
      minecraftClient.world.setBlockState(blockPos, blockState, 3);
      targetESPModule.throughWallsSetting.resetToDefault();
    }

    ArmorStandEntity armorStandEntity =
        new ArmorStandEntity(
            minecraftClient.world,
            minecraftClient.player.getX(),
            minecraftClient.player.getY(),
            minecraftClient.player.getZ() + 2.0);
    armorStandEntity.setId(-67003);
    armorStandEntity.resetPosition();
    minecraftClient.world.addEntity(armorStandEntity);

    try {
      targetESPModule.render(minecraftClient, 1.0F, longValue = longValue + 100000000L);
      apply(
          targetESPModule.getTargetESPSupport() == null,
          "non-player entity blocks selection of players behind it");
    } finally {
      minecraftClient.world.removeEntity(armorStandEntity.getId(), RemovalReason.DISCARDED);
    }

    long longValue6;
    targetESPModule.render(minecraftClient, 1.0F, longValue6 = longValue + 100000000L);
    apply(
        targetESPModule.getTargetESPSupport() != null,
        "target is acquired after obstruction disappears");
    targetESPModule.animationSpeedSetting.setNumber(Double.valueOf(0.0));
    float value2 = targetESPModule.computeFloat();
    targetESPModule.render(minecraftClient, 1.0F, longValue = longValue6 + 100000000L);
    apply(targetESPModule.computeFloat() == value2, "zero speed freezes animation");
    targetESPModule.animationSpeedSetting.resetToDefault();
    otherClientPlayerEntity.setHealth(0.0F);
    long longValue7;
    targetESPModule.render(minecraftClient, 1.0F, longValue7 = longValue + 100000000L);
    apply(targetESPModule.getTargetESPSupport() == null, "dead target is discarded immediately");
    otherClientPlayerEntity.setHealth(20.0F);
    targetESPModule.render(minecraftClient, 1.0F, longValue = longValue7 + 100000000L);
    targetESPModule.setEnabled(false);
    apply(
        targetESPModule.getTargetESPSupport() == null
            && targetESPModule.getTargetESPSupport2() == null,
        "disable clears every target");
    targetESPModule.setEnabled(true);

    targetESPModule.render(minecraftClient, 1.0F, longValue + 100000000L);
    targetESPModule.update4();
    apply(
        targetESPModule.getTargetESPSupport() == null
            && targetESPModule.getTargetESPSupport2() == null,
        "world cleanup drops target references");
    GuiModuleEntry guiModuleEntry = GuiModuleEntry.of(targetESPModule);
    apply(
        guiModuleEntry.getModule() == targetESPModule
            && guiModuleEntry.getList().stream()
                    .mapToInt(
                        collectionData -> {
                          return ((bunnythekenzo.client.CollectionData) (collectionData)).list().size();
                        })
                    .sum()
                == targetESPModule.getSettings().size(),
        "every setting is reachable through the live GUI bridge");
    JsonObject jsonObject = ClientInitializer.getClientConfig().getJsonObject();
    targetESPModule.rangeSetting.setNumber(Double.valueOf(2.0));
    targetESPModule.styleSetting.setValue("Scan");
    ClientInitializer.getClientConfig().apply(jsonObject);
    apply(
        (Double) targetESPModule.rangeSetting.getValue() == 6.0
            && targetESPModule.styleSetting.isSelected("Orbit"),
        "config restores range and style");
    targetESPModule.colorModeSetting.setValue("Theme");
    apply(
        !targetESPModule.primaryColorSetting.isVisible()
            && !targetESPModule.accentColorSetting.isVisible(),
        "custom colors hide in theme mode");
    targetESPModule.colorModeSetting.resetToDefault();
    apply(
        targetESPModule.primaryColorSetting.isVisible()
            && targetESPModule.accentColorSetting.isVisible(),
        "gradient exposes both color pickers");
  }

  public static OtherClientPlayerEntity getOtherClientPlayerEntity(
      MinecraftClient minecraftClient, int intValue, String text, double doubleValue) {
    OtherClientPlayerEntity otherClientPlayerEntity =
        new OtherClientPlayerEntity(
            minecraftClient.world,
            new GameProfile(UUID.nameUUIDFromBytes(text.getBytes(StandardCharsets.UTF_8)), text));
    otherClientPlayerEntity.setId(intValue);
    otherClientPlayerEntity.refreshPositionAndAngles(
        minecraftClient.player.getX(),
        minecraftClient.player.getY(),
        minecraftClient.player.getZ() + doubleValue,
        180.0F,
        0.0F);
    otherClientPlayerEntity.resetPosition();
    otherClientPlayerEntity.setNoGravity(true);
    minecraftClient.world.addEntity(otherClientPlayerEntity);
    return otherClientPlayerEntity;
  }

  public static void apply(MinecraftClient minecraftClient, float floatValue) {
    minecraftClient.player.setYaw(floatValue);
    minecraftClient.player.setPitch(0.0F);
    minecraftClient.player.resetPosition();
    minecraftClient
        .gameRenderer
        .getCamera()
        .update(minecraftClient.world, minecraftClient.player, false, false, 1.0F);
  }

  public static void apply(boolean flag, String text) {
    if (!flag) {
      throw new AssertionError("Target ESP: " + text);
    }
  }
}
