package bunnythekenzo.client.gametest;

import com.google.gson.JsonObject;
import java.util.Locale;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.render.state.OutlineRenderState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import bunnythekenzo.client.AnimationPreferences;
import bunnythekenzo.client.BlockOutlineTransition;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.GuiModuleEntry;
import bunnythekenzo.client.module.CustomBlockOutlineModule;
import bunnythekenzo.client.render.BlockSelectorRenderer;
import bunnythekenzo.client.ui.PanelScreen;

public final class BlockSelectorTest {
  public static final BlockPos blockPos = new BlockPos(0, 181, 0);

  public static void run(
      ClientGameTestContext clientGameTestContext,
      TestSingleplayerContext testSingleplayerContext) {
    ProtectionPerformanceTest.ensureInitialized();

    JsonObject onClient =
        clientGameTestContext.computeOnClient(
            minecraftClient -> {
              return ClientInitializer.getClientConfig().getJsonObject();
            });
    boolean enabled = AnimationPreferences.isEnabled();

    try {
      clientGameTestContext.runOnClient(
          minecraftClient2 -> {
            ClientInitializer.getModuleManager()
                .getList()
                .forEach(
                    (java.util.function.Consumer<bunnythekenzo.client.module.Module>)
                        (module -> {
                          module.setEnabled(false);
                        }));
            ClientInitializer.getModuleManager().hUDModule.notificationsSetting.setValue(false);
            CustomBlockOutlineModule customBlockOutlineModule =
                ClientInitializer.getModuleManager().customBlockOutlineModule;
            customBlockOutlineModule
                .getSettings()
                .forEach(
                    (referenceArgument21_0) ->
                        ((bunnythekenzo.client.setting.Setting) referenceArgument21_0).resetToDefault());
            customBlockOutlineModule.colorSetting.apply(Integer.valueOf(-5339649));
            customBlockOutlineModule.accentColorSetting.apply(Integer.valueOf(-9374739));
            customBlockOutlineModule.setEnabled(true);
            AnimationPreferences.setEnabled(true);
            verifyState(customBlockOutlineModule);
            minecraftClient2.options.getMaxFps().setValue(60);
            minecraftClient2.options.getFov().setValue(45);
            minecraftClient2.options.hudHidden = false;
            minecraftClient2.options.setPerspective(Perspective.FIRST_PERSON);
            ((net.minecraft.client.MinecraftClient) (minecraftClient2)).setScreen(null);
          });
      testSingleplayerContext.getServer().runCommand("gamemode creative @a");
      testSingleplayerContext
          .getServer()
          .runOnServer(
              minecraftServer -> {
                ProtectionPerformanceTest.ensureInitialized();

                ServerWorld overworld = minecraftServer.getOverworld();
                overworld.setTimeOfDay(6000L);

                for (int index = -9; index <= 9; index++) {
                  for (int index2 = -9; index2 <= 9; index2++) {
                    overworld.setBlockState(
                        new BlockPos(index, 180, index2), Blocks.DEEPSLATE_TILES.getDefaultState());

                    for (int index3 = 181; index3 < 187; index3++) {
                      overworld.setBlockState(
                          new BlockPos(index, index3, index2), Blocks.AIR.getDefaultState());
                    }
                  }
                }

                overworld.setBlockState(blockPos, Blocks.SMOOTH_STONE.getDefaultState());
                minecraftServer
                    .getPlayerManager()
                    .getPlayerList()
                    .getFirst()
                    .setPosition(2.1, 181.0, -2.4);
              });
      clientGameTestContext.runOnClient(
          minecraftClient3 -> {
            minecraftClient3.player.setPosition(2.1, 181.0, -2.4);
            minecraftClient3.player.setVelocity(Vec3d.ZERO);
            apply(minecraftClient3, Vec3d.ofCenter(blockPos));
          });
      clientGameTestContext.waitTicks(35);
      testSingleplayerContext.getClientWorld().waitForChunksRender();
      clientGameTestContext.waitTicks(45);
      clientGameTestContext.runOnClient(
          minecraftClient4 -> {
            ProtectionPerformanceTest.ensureInitialized();

            minecraftClient4.inGameHud.getChatHud().clear(true);
            apply(BlockSelectorRenderer.getCount() > 0, "custom GPU passes ran");
            apply(
                ClientInitializer.getModuleManager()
                            .customBlockOutlineModule
                            .blockOutlineTransition
                            .getAnimatedBlockOutline()
                        != null
                    && ClientInitializer.getModuleManager()
                        .customBlockOutlineModule
                        .blockOutlineTransition
                        .getAnimatedBlockOutline()
                        .blockPos
                        .equals(blockPos),
                "live raycast selected the display block");
          });

      for (String text : new String[] {"Prism", "Orbit", "Focus"}) {
        clientGameTestContext.runOnClient(
            minecraftClient5 -> {
              ClientInitializer.getModuleManager()
                  .customBlockOutlineModule
                  .styleSetting
                  .setValue(text);
            });
        clientGameTestContext.waitTicks(12);
        clientGameTestContext.takeScreenshot("block-selector-" + text.toLowerCase(Locale.ROOT));
      }

      clientGameTestContext.runOnClient(
          minecraftClient6 -> {
            ClientInitializer.getModuleManager()
                .customBlockOutlineModule
                .styleSetting
                .setValue("Prism");
          });
      shape(
          clientGameTestContext,
          testSingleplayerContext,
          Blocks.STONE_SLAB.getDefaultState(),
          "slab",
          0.25);
      clientGameTestContext.runOnClient(
          minecraftClient7 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                ClientInitializer.getModuleManager()
                        .customBlockOutlineModule
                        .blockOutlineTransition
                        .getAnimatedBlockOutline()
                        .getBox()
                        .getLengthY()
                    == 0.5,
                "slab uses a half-height sculpture");
          });
      shape(
          clientGameTestContext,
          testSingleplayerContext,
          Blocks.STONE_BRICK_STAIRS.getDefaultState(),
          "stairs",
          0.45);
      shape(
          clientGameTestContext,
          testSingleplayerContext,
          Blocks.GLASS.getDefaultState(),
          "glass",
          0.5);
      shape(
          clientGameTestContext,
          testSingleplayerContext,
          Blocks.GLASS_PANE.getDefaultState(),
          "pane",
          0.5);
      shape(
          clientGameTestContext,
          testSingleplayerContext,
          Blocks.OAK_FENCE.getDefaultState(),
          "fence",
          0.5);
      shape(
          clientGameTestContext,
          testSingleplayerContext,
          Blocks.SMOOTH_STONE.getDefaultState(),
          "prism-restored",
          0.5);
      clientGameTestContext.runOnClient(
          minecraftClient8 -> {
            CustomBlockOutlineModule customBlockOutlineModule2 =
                ClientInitializer.getModuleManager().customBlockOutlineModule;
            customBlockOutlineModule2.colorSetting.apply(Integer.valueOf(-23475));
            customBlockOutlineModule2.accentColorSetting.apply(Integer.valueOf(-36450));
          });
      clientGameTestContext.waitTicks(8);
      clientGameTestContext.takeScreenshot("block-selector-sunset");
      clientGameTestContext.runOnClient(
          minecraftClient9 -> {
            ProtectionPerformanceTest.ensureInitialized();

            CustomBlockOutlineModule customBlockOutlineModule3 =
                ClientInitializer.getModuleManager().customBlockOutlineModule;
            customBlockOutlineModule3.rainbowSetting.setValue(true);
            customBlockOutlineModule3.animationSetting.setValue("Gradient Flow");
            apply(
                !customBlockOutlineModule3.colorSetting.isVisible()
                    && !customBlockOutlineModule3.accentColorSetting.isVisible(),
                "rainbow hides unused custom swatches");
          });
      clientGameTestContext.waitTicks(8);
      clientGameTestContext.takeScreenshot("block-selector-rainbow");
      clientGameTestContext.runOnClient(
          minecraftClient10 -> {
            CustomBlockOutlineModule customBlockOutlineModule4 =
                ClientInitializer.getModuleManager().customBlockOutlineModule;
            customBlockOutlineModule4.rainbowSetting.setValue(false);
            customBlockOutlineModule4.animationSetting.setValue("Static");
            customBlockOutlineModule4.thicknessSetting.setNumber(Double.valueOf(6.0));
            customBlockOutlineModule4.detailScaleSetting.setNumber(Double.valueOf(150.0));
            customBlockOutlineModule4.glowSetting.setNumber(Double.valueOf(100.0));
          });
      clientGameTestContext.waitTicks(5);
      double onClient2 =
          clientGameTestContext.computeOnClient(
              minecraftClient11 -> {
                return ClientInitializer.getModuleManager()
                    .customBlockOutlineModule
                    .blockOutlineTransition
                    .getValue();
              });
      clientGameTestContext.waitTicks(5);
      clientGameTestContext.runOnClient(
          minecraftClient12 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                ClientInitializer.getModuleManager()
                        .customBlockOutlineModule
                        .blockOutlineTransition
                        .getValue()
                    == onClient2,
                "Static freezes live animation");
          });
      clientGameTestContext.takeScreenshot("block-selector-max-details");
      clientGameTestContext.runOnClient(
          minecraftClient13 -> {
            CustomBlockOutlineModule customBlockOutlineModule5 =
                ClientInitializer.getModuleManager().customBlockOutlineModule;
            customBlockOutlineModule5
                .getSettings()
                .forEach(
                    (referenceArgument22_0) ->
                        ((bunnythekenzo.client.setting.Setting) referenceArgument22_0).resetToDefault());
            minecraftClient13.player.setPitch(-85.0F);
            minecraftClient13.player.resetPosition();
          });
      clientGameTestContext.waitTicks(15);
      clientGameTestContext.runOnClient(
          minecraftClient14 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                ClientInitializer.getModuleManager()
                            .customBlockOutlineModule
                            .blockOutlineTransition
                            .getAnimatedBlockOutline()
                        == null
                    && ClientInitializer.getModuleManager()
                            .customBlockOutlineModule
                            .blockOutlineTransition
                            .getOutlineDirectionSwitchTable()
                        == null,
                "looking at sky clears the faded sculpture");
          });
      clientGameTestContext.takeScreenshot("block-selector-sky-cleared");
      clientGameTestContext.runOnClient(
          minecraftClient15 -> {
            apply(minecraftClient15, Vec3d.ofCenter(blockPos));
          });
      clientGameTestContext.waitTicks(10);
      clientGameTestContext.runOnClient(
          minecraftClient16 -> {
            minecraftClient16.options.hudHidden = true;
          });
      clientGameTestContext.waitTicks(5);
      clientGameTestContext.runOnClient(
          minecraftClient17 -> {
            apply(
                ClientInitializer.getModuleManager()
                        .customBlockOutlineModule
                        .blockOutlineTransition
                        .getAnimatedBlockOutline()
                    == null,
                "F1 clears the selector");
          });
      clientGameTestContext.runOnClient(
          minecraftClient18 -> {
            minecraftClient18.options.hudHidden = false;
            ClientInitializer.getModuleManager().customBlockOutlineModule.setEnabled(false);
            ClientInitializer.logger.info("Block selector disabled baseline begins");
          });
      clientGameTestContext.waitTicks(5);
      int onClient3 =
          clientGameTestContext.computeOnClient(
              minecraftClient19 -> {
                return BlockSelectorRenderer.getCount();
              });
      clientGameTestContext.waitTicks(5);
      clientGameTestContext.runOnClient(
          minecraftClient20 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                BlockSelectorRenderer.getCount() == onClient3,
                "disabled module submits no geometry");
          });
      clientGameTestContext.takeScreenshot("block-selector-vanilla-disabled");
      clientGameTestContext.runOnClient(
          minecraftClient21 -> {
            ClientInitializer.logger.info("Block selector disabled baseline ends");
            ClientInitializer.getModuleManager().customBlockOutlineModule.setEnabled(true);
            PanelScreen panelScreen = new PanelScreen();
            minecraftClient21.setScreen(panelScreen);
            apply(panelScreen.test("CustomBlockOutline"), "settings open from Visuals");
          });
      clientGameTestContext.waitTicks(12);
      clientGameTestContext.takeScreenshot("block-selector-settings");
      ClientInitializer.logger.info(
          "Block selector verified: three native 3D styles, shape alignment, translucent blocks,"
              + " colors, static motion, target fades, F1, disable and GUI");
    } finally {
      clientGameTestContext.runOnClient(
          minecraftClient22 -> {
            minecraftClient22.setScreen(null);
            minecraftClient22.options.hudHidden = false;
            ClientInitializer.getClientConfig().apply(onClient);
            AnimationPreferences.setEnabled(enabled);
          });
    }
  }

  public static void shape(
      ClientGameTestContext clientGameTestContext,
      TestSingleplayerContext testSingleplayerContext,
      BlockState blockState,
      String text,
      double doubleValue) {
    testSingleplayerContext
        .getServer()
        .runOnServer(
            minecraftServer -> {
              minecraftServer.getOverworld().setBlockState(blockPos, blockState);
            });
    clientGameTestContext.runOnClient(
        minecraftClient -> {
          apply(minecraftClient, new Vec3d(0.5, blockPos.getY() + doubleValue, 0.5));
        });
    clientGameTestContext.waitTicks(12);
    testSingleplayerContext.getClientWorld().waitForChunksRender();
    clientGameTestContext.runOnClient(
        minecraftClient2 -> {
          ProtectionPerformanceTest.ensureInitialized();
          apply(
              ClientInitializer.getModuleManager()
                          .customBlockOutlineModule
                          .blockOutlineTransition
                          .getAnimatedBlockOutline()
                      != null
                  && ClientInitializer.getModuleManager()
                      .customBlockOutlineModule
                      .blockOutlineTransition
                      .getAnimatedBlockOutline()
                      .blockPos
                      .equals(blockPos),
              text + " remains selectable");
        });
    clientGameTestContext.takeScreenshot("block-selector-" + text);
  }

  public static void apply(MinecraftClient minecraftClient, Vec3d vec3d) {
    Vec3d vec3d2 = vec3d.subtract(minecraftClient.player.getEyePos());
    minecraftClient.player.setYaw((float) Math.toDegrees(Math.atan2(-vec3d2.x, vec3d2.z)));
    minecraftClient.player.setPitch(
        (float) (-Math.toDegrees(Math.atan2(vec3d2.y, Math.hypot(vec3d2.x, vec3d2.z)))));
    minecraftClient.player.resetPosition();
  }

  public static void verifyState(CustomBlockOutlineModule customBlockOutlineModule) {
    ProtectionPerformanceTest.ensureInitialized();

    BlockOutlineTransition blockOutlineTransition = new BlockOutlineTransition();
    Object value2 = new Object();
    OutlineRenderState outlineRenderState =
        new OutlineRenderState(BlockPos.ORIGIN, false, false, VoxelShapes.fullCube());
    long longValue = 1000000000L;
    blockOutlineTransition.apply(
        outlineRenderState, null, value2, 1000000000L, customBlockOutlineModule);

    for (int index = 0; index < 5; index++) {
      blockOutlineTransition.apply(
          outlineRenderState, null, value2, longValue += 50000000L, customBlockOutlineModule);
    }

    apply(blockOutlineTransition.getAnimatedBlockOutline().getValue() == 1.0, "entrance settles");
    OutlineRenderState outlineRenderState2 =
        new OutlineRenderState(BlockPos.ORIGIN.east(), false, false, VoxelShapes.fullCube());
    long longValue2;
    blockOutlineTransition.apply(
        outlineRenderState2,
        null,
        value2,
        longValue2 = longValue + 16000000L,
        customBlockOutlineModule);
    apply(
        blockOutlineTransition.getAnimatedBlockOutline().blockPos.equals(outlineRenderState2.pos())
            && blockOutlineTransition
                .getOutlineDirectionSwitchTable()
                .blockPos
                .equals(outlineRenderState.pos()),
        "transition anchors both real targets");
    blockOutlineTransition.apply(
        outlineRenderState,
        null,
        value2,
        longValue = longValue2 + 16000000L,
        customBlockOutlineModule);
    apply(
        blockOutlineTransition.getAnimatedBlockOutline().getValue() > 0.8,
        "rapid reacquisition reuses the visible target");

    for (int index2 = 0; index2 < 5; index2++) {
      blockOutlineTransition.apply(
          null, null, value2, longValue += 50000000L, customBlockOutlineModule);
    }

    apply(
        blockOutlineTransition.getAnimatedBlockOutline() == null
            && blockOutlineTransition.getOutlineDirectionSwitchTable() == null,
        "empty target fades completely");
    VoxelShape voxelShape =
        VoxelShapes.union(
            VoxelShapes.cuboid(0.0, 0.0, 0.0, 1.0, 0.5, 1.0),
            VoxelShapes.cuboid(0.0, 0.5, 0.5, 1.0, 1.0, 1.0));
    OutlineRenderState outlineRenderState3 =
        new OutlineRenderState(BlockPos.ORIGIN, false, false, voxelShape);
    long longValue3;
    blockOutlineTransition.apply(
        outlineRenderState3,
        new BlockHitResult(new Vec3d(0.5, 0.5, 0.25), Direction.UP, BlockPos.ORIGIN, false),
        value2,
        longValue3 = longValue + 50000000L,
        customBlockOutlineModule);
    apply(
        blockOutlineTransition.getAnimatedBlockOutline().getBox2().maxY == 0.5,
        "stair reticle lands on the inset tread");

    for (Direction direction : Direction.values()) {
      Vec3d vec3d =
          new Vec3d(
              0.5 + direction.getOffsetX() * 0.5,
              0.5 + direction.getOffsetY() * 0.5,
              0.5 + direction.getOffsetZ() * 0.5);
      blockOutlineTransition.apply(
          outlineRenderState,
          new BlockHitResult(vec3d, direction, BlockPos.ORIGIN, false),
          value2,
          longValue3 += 50000000L,
          customBlockOutlineModule);
      apply(
          blockOutlineTransition.getAnimatedBlockOutline().getDirection() == direction,
          "all six face orientations");
    }

    customBlockOutlineModule.animationSpeedSetting.setNumber(Double.valueOf(0.0));
    double value3 = blockOutlineTransition.getValue();
    blockOutlineTransition.apply(
        outlineRenderState,
        null,
        value2,
        longValue = longValue3 + 50000000L,
        customBlockOutlineModule);
    apply(blockOutlineTransition.getValue() == value3, "zero speed freezes the clock");
    customBlockOutlineModule.animationSpeedSetting.resetToDefault();
    AnimationPreferences.setEnabled(false);
    long longValue4;
    blockOutlineTransition.apply(
        outlineRenderState,
        null,
        value2,
        longValue4 = longValue + 50000000L,
        customBlockOutlineModule);
    apply(blockOutlineTransition.getValue() == value3, "global reduced motion is respected");
    AnimationPreferences.setEnabled(true);
    Object value4 = new Object();

    blockOutlineTransition.apply(
        null, null, value4, longValue4 + 50000000L, customBlockOutlineModule);
    apply(
        blockOutlineTransition.getAnimatedBlockOutline() == null
            && blockOutlineTransition.getOutlineDirectionSwitchTable() == null,
        "world changes discard old anchors");
    GuiModuleEntry guiModuleEntry = GuiModuleEntry.of(customBlockOutlineModule);
    apply(
        guiModuleEntry.getList().size() == 3
            && guiModuleEntry.getList().stream()
                    .mapToInt(
                        collectionData -> {
                          return ((bunnythekenzo.client.CollectionData) (collectionData)).list().size();
                        })
                    .sum()
                == customBlockOutlineModule.getSettings().size(),
        "every control reaches the three settings tabs");
    JsonObject jsonObject = ClientInitializer.getClientConfig().getJsonObject();
    customBlockOutlineModule.styleSetting.setValue("Focus");
    customBlockOutlineModule.accentColorSetting.apply(Integer.valueOf(-22016));
    customBlockOutlineModule.animationSpeedSetting.setNumber(Double.valueOf(2.0));
    JsonObject jsonObject2 = ClientInitializer.getClientConfig().getJsonObject();
    ClientInitializer.getClientConfig().apply(jsonObject);
    ClientInitializer.getClientConfig().apply(jsonObject2);
    apply(
        customBlockOutlineModule.styleSetting.isSelected("Focus")
            && customBlockOutlineModule.accentColorSetting.getInteger() == -22016
            && (Double) customBlockOutlineModule.animationSpeedSetting.getValue() == 2.0,
        "style, custom color and speed survive config restore");
    ClientInitializer.getClientConfig().apply(jsonObject);
    customBlockOutlineModule.blockOutlineTransition.update();
  }

  public static void apply(boolean flag, String text) {
    if (!flag) {
      throw new AssertionError("Block selector: " + text);
    }
  }
}
