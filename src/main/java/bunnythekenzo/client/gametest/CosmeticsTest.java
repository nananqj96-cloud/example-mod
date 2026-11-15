package bunnythekenzo.client.gametest;

import java.lang.reflect.Proxy;
import java.util.LinkedHashMap;
import java.util.Locale;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.model.ElytraEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.Direction;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.GuiModuleEntry;
import bunnythekenzo.client.module.CosmeticsModule;
import bunnythekenzo.client.ui.PanelScreen;

public final class CosmeticsTest {
  public static void run(ClientGameTestContext clientGameTestContext) {
    ProtectionPerformanceTest.ensureInitialized();

    Runnable onClient =
        clientGameTestContext.computeOnClient(
            minecraftClient -> {
              CosmeticsModule cosmeticsModule =
                  ClientInitializer.getModuleManager().cosmeticsModule;
              LinkedHashMap linkedHashMap = new LinkedHashMap();
              cosmeticsModule
                  .getSettings()
                  .forEach(
                      value2 -> {
                        linkedHashMap.put(
                            value2, ((bunnythekenzo.client.setting.Setting) (value2)).toJson());
                      });
              LinkedHashMap linkedHashMap2 = new LinkedHashMap();
              ClientInitializer.getModuleManager()
                  .getList()
                  .forEach(
                      value3 -> {
                        linkedHashMap2.put(
                            value3, ((bunnythekenzo.client.module.Module) (value3)).isEnabled());
                      });
              Perspective perspective = minecraftClient.options.getPerspective();
              boolean hudHidden = minecraftClient.options.hudHidden;
              int value4 = minecraftClient.options.getFov().getValue();
              return () -> {
                minecraftClient.options.sneakKey.setPressed(false);
                minecraftClient.options.forwardKey.setPressed(false);
                minecraftClient.setScreen(null);
                linkedHashMap.forEach(
                    (referenceArgument27_0, referenceArgument27_1) ->
                        ((bunnythekenzo.client.setting.Setting) referenceArgument27_0)
                            .fromJson((com.google.gson.JsonElement) referenceArgument27_1));
                linkedHashMap2.forEach(
                    (referenceArgument28_0, referenceArgument28_1) ->
                        ((bunnythekenzo.client.module.Module) referenceArgument28_0)
                            .setEnabled((java.lang.Boolean) referenceArgument28_1));
                minecraftClient.options.setPerspective(perspective);
                minecraftClient.options.hudHidden = hudHidden;
                minecraftClient.options.getFov().setValue(value4);
              };
            });

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
            CosmeticsModule cosmeticsModule2 = ClientInitializer.getModuleManager().cosmeticsModule;
            cosmeticsModule2
                .getSettings()
                .forEach(
                    (referenceArgument29_0) ->
                        ((bunnythekenzo.client.setting.Setting) referenceArgument29_0).resetToDefault());
            ProtectionPerformanceTest.ensureInitialized();

            cosmeticsModule2.setEnabled(true);
            minecraftClient2.options.hudHidden = true;
            minecraftClient2.options.getFov().setValue(60);
            minecraftClient2.options.setPerspective(Perspective.THIRD_PERSON_BACK);
            minecraftClient2.player.setYaw(0.0F);
            minecraftClient2.player.setPitch(0.0F);
            minecraftClient2.player.bodyYaw = minecraftClient2.player.lastBodyYaw = 0.0F;
            minecraftClient2.player.headYaw = minecraftClient2.player.lastHeadYaw = 0.0F;
            GuiModuleEntry guiModuleEntry = GuiModuleEntry.of(cosmeticsModule2);
            apply(guiModuleEntry.getList().size() == 4, "four wardrobe tabs");
            apply(
                guiModuleEntry.getList().stream()
                        .mapToInt(
                            collectionData -> {
                              return ((bunnythekenzo.client.CollectionData) (collectionData))
                                  .list()
                                  .size();
                            })
                        .sum()
                    == cosmeticsModule2.getSettings().size(),
                "all wardrobe controls reach the menu");
          });
      clientGameTestContext.waitTicks(30);
      checkElytraVisibility(clientGameTestContext);

      for (String text : new String[] {"Seraph", "Ender", "Phoenix", "Your Skin"}) {
        clientGameTestContext.runOnClient(
            minecraftClient3 -> {
              ClientInitializer.getModuleManager().cosmeticsModule.wingStyleSetting.setValue(text);
            });
        clientGameTestContext.waitTicks(12);
        clientGameTestContext.takeScreenshot(
            "cosmetics-" + text.toLowerCase(Locale.ROOT).replace(' ', '-'));
      }

      clientGameTestContext.runOnClient(
          minecraftClient4 -> {
            CosmeticsModule cosmeticsModule3 = ClientInitializer.getModuleManager().cosmeticsModule;
            cosmeticsModule3.wingStyleSetting.setValue("Seraph");
            cosmeticsModule3.crownSetting.setValue(true);
            cosmeticsModule3.miniYouSetting.setValue(true);
          });
      clientGameTestContext.waitTicks(15);
      clientGameTestContext.takeScreenshot("cosmetics-full-back");
      clientGameTestContext.runOnClient(
          minecraftClient5 -> {
            minecraftClient5.options.sneakKey.setPressed(true);
          });
      clientGameTestContext.waitTicks(10);
      clientGameTestContext.runOnClient(
          minecraftClient6 -> {
            apply(
                minecraftClient6.player.isInSneakingPose(),
                "crouching reaches the actual player pose");
          });
      clientGameTestContext.takeScreenshot("cosmetics-crouching");
      clientGameTestContext.runOnClient(
          minecraftClient7 -> {
            minecraftClient7.options.sneakKey.setPressed(false);
            minecraftClient7.options.forwardKey.setPressed(true);
          });
      clientGameTestContext.waitTicks(20);
      clientGameTestContext.runOnClient(
          minecraftClient8 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                ClientInitializer.getModuleManager().cosmeticsModule.computeFloat2(1.0F) > 0.05F,
                "walking unfolds the reactive wing rig");
          });
      clientGameTestContext.takeScreenshot("cosmetics-moving");
      clientGameTestContext.runOnClient(
          minecraftClient9 -> {
            minecraftClient9.options.forwardKey.setPressed(false);
          });
      clientGameTestContext.runOnClient(
          minecraftClient10 -> {
            minecraftClient10.options.setPerspective(Perspective.THIRD_PERSON_FRONT);
          });
      clientGameTestContext.waitTicks(10);
      clientGameTestContext.takeScreenshot("cosmetics-full-front");
      clientGameTestContext.runOnClient(
          minecraftClient11 -> {
            ClientInitializer.getModuleManager()
                .cosmeticsModule
                .animationSpeedSetting
                .setNumber(Double.valueOf(0.0));
          });
      float onClient2 =
          clientGameTestContext.computeOnClient(
              minecraftClient12 -> {
                return ClientInitializer.getModuleManager().cosmeticsModule.computeFloat(1.0F);
              });
      clientGameTestContext.waitTicks(15);
      clientGameTestContext.runOnClient(
          minecraftClient13 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                ClientInitializer.getModuleManager().cosmeticsModule.computeFloat(0.5F)
                    == onClient2,
                "zero speed freezes animation including interpolation");
          });
      clientGameTestContext.takeScreenshot("cosmetics-frozen");
      clientGameTestContext.runOnClient(
          minecraftClient14 -> {
            ClientInitializer.getModuleManager()
                .cosmeticsModule
                .animationSpeedSetting
                .setNumber(Double.valueOf(100.0));
          });
      clientGameTestContext.waitTicks(10);
      clientGameTestContext.runOnClient(
          minecraftClient15 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                ClientInitializer.getModuleManager().cosmeticsModule.computeFloat(1.0F) > onClient2,
                "animation resumes after unfreezing");
          });
      clientGameTestContext.runOnClient(
          minecraftClient16 -> {
            CosmeticsModule cosmeticsModule4 = ClientInitializer.getModuleManager().cosmeticsModule;
            ProtectionPerformanceTest.ensureInitialized();

            cosmeticsModule4.setEnabled(false);
            apply(
                cosmeticsModule4.computeFloat(1.0F) == 0.0F, "disable clears the animation state");
            cosmeticsModule4.setEnabled(true);
            minecraftClient16.options.setPerspective(Perspective.FIRST_PERSON);
          });
      clientGameTestContext.waitTicks(8);
      clientGameTestContext.takeScreenshot("cosmetics-first-person");
      clientGameTestContext.runOnClient(
          minecraftClient17 -> {
            minecraftClient17.options.hudHidden = false;
            PanelScreen panelScreen = new PanelScreen();
            minecraftClient17.setScreen(panelScreen);
            apply(panelScreen.test("Cosmetics"), "wardrobe opens in settings");
          });
      clientGameTestContext.waitTicks(12);
      clientGameTestContext.takeScreenshot("cosmetics-settings");
      ClientInitializer.logger.info(
          "Cosmetics verified: elytra visibility and equipment preservation, four wing styles,"
              + " crown, skin companion, crouching, reactive motion, GUI, freeze/resume and"
              + " cleanup");
    } finally {
      clientGameTestContext.runOnClient(
          minecraftClient18 -> {
            onClient.run();
          });
    }
  }

  public static void checkElytraVisibility(ClientGameTestContext clientGameTestContext) {
    ItemStack onClient =
        clientGameTestContext.computeOnClient(
            minecraftClient -> {
              return minecraftClient.player.getEquippedStack(EquipmentSlot.CHEST).copy();
            });

    try {
      clientGameTestContext.runOnClient(
          minecraftClient2 -> {
            ProtectionPerformanceTest.ensureInitialized();

            CosmeticsModule cosmeticsModule = ClientInitializer.getModuleManager().cosmeticsModule;
            apply(
                cosmeticsModule.hideElytraSetting.isVisible()
                    && cosmeticsModule.getString(cosmeticsModule.hideElytraSetting).equals("Wings"),
                "elytra toggle is available under Wings");
            minecraftClient2.player.equipStack(EquipmentSlot.CHEST, new ItemStack(Items.ELYTRA));
            PlayerEntityRenderState playerEntityRenderState =
                (PlayerEntityRenderState)
                    minecraftClient2
                        .getEntityRenderDispatcher()
                        .getAndUpdateRenderState(minecraftClient2.player, 1.0F);
            apply(
                !(Boolean) cosmeticsModule.hideElytraSetting.getValue(),
                "elytra visibility is unchanged by default");
            requireRender(minecraftClient2, playerEntityRenderState, true, true, "option off");
            cosmeticsModule.hideElytraSetting.setValue(true);
            requireRender(minecraftClient2, playerEntityRenderState, false, true, "option on");
            playerEntityRenderState.isGliding = true;
            requireRender(minecraftClient2, playerEntityRenderState, false, true, "gliding");
            apply(
                playerEntityRenderState.isGliding
                    && playerEntityRenderState.equippedChestStack.isOf(Items.ELYTRA)
                    && minecraftClient2
                        .player
                        .getEquippedStack(EquipmentSlot.CHEST)
                        .isOf(Items.ELYTRA),
                "hiding preserves equipped elytra and flight state");
            playerEntityRenderState.isGliding = false;
            playerEntityRenderState.id++;
            requireRender(minecraftClient2, playerEntityRenderState, true, false, "another player");
            playerEntityRenderState.id = minecraftClient2.player.getId();
            playerEntityRenderState.invisible = true;
            requireRender(
                minecraftClient2, playerEntityRenderState, true, false, "invisible cosmetics");
            playerEntityRenderState.invisible = false;
            playerEntityRenderState.deathTime = 1.0F;
            requireRender(minecraftClient2, playerEntityRenderState, true, false, "death pose");
            playerEntityRenderState.deathTime = 0.0F;
            playerEntityRenderState.sleepingDirection = Direction.NORTH;
            requireRender(minecraftClient2, playerEntityRenderState, true, false, "sleeping");
            playerEntityRenderState.sleepingDirection = null;
            cosmeticsModule.wingsSetting.setValue(false);
            apply(
                !cosmeticsModule.hideElytraSetting.isVisible(),
                "elytra toggle follows Wings visibility");
            requireRender(minecraftClient2, playerEntityRenderState, true, false, "wings off");
            cosmeticsModule.wingsSetting.setValue(true);
            cosmeticsModule.setEnabled(false);
            requireRender(minecraftClient2, playerEntityRenderState, true, false, "cosmetics off");
            cosmeticsModule.setEnabled(true);
            cosmeticsModule.hideElytraSetting.setValue(false);
          });
      clientGameTestContext.waitTicks(8);
      clientGameTestContext.takeScreenshot("cosmetics-elytra-visible");
      clientGameTestContext.runOnClient(
          minecraftClient3 -> {
            ClientInitializer.getModuleManager().cosmeticsModule.hideElytraSetting.setValue(true);
          });
      clientGameTestContext.waitTicks(8);
      clientGameTestContext.takeScreenshot("cosmetics-elytra-hidden");
    } finally {
      clientGameTestContext.runOnClient(
          minecraftClient4 -> {
            minecraftClient4.player.equipStack(EquipmentSlot.CHEST, onClient);
            ClientInitializer.getModuleManager().cosmeticsModule.hideElytraSetting.resetToDefault();
          });
    }
  }

  public static void requireRender(
      MinecraftClient minecraftClient,
      PlayerEntityRenderState playerEntityRenderState,
      boolean flag,
      boolean flag2,
      String text) {
    ProtectionPerformanceTest.ensureInitialized();

    int[] intValues = new int[2];
    OrderedRenderCommandQueue orderedRenderCommandQueue =
        (OrderedRenderCommandQueue)
            Proxy.newProxyInstance(
                OrderedRenderCommandQueue.class.getClassLoader(),
                new Class[] {OrderedRenderCommandQueue.class},
                (value2, method, valueArray) -> {
                  ProtectionPerformanceTest.ensureInitialized();
                  if (method.getName().equals("order")) {
                    return value2;
                  }

                  if (method.getName().equals("submitModel")
                      && valueArray[0] instanceof ElytraEntityModel) {
                    intValues[0]++;
                  }

                  if (method.getName().equals("submitCustomGeometry")) {
                    intValues[1]++;
                  }

                  return null;
                });
    minecraftClient
        .getEntityRenderDispatcher()
        .getPlayerRenderer(minecraftClient.player)
        .render(
            playerEntityRenderState,
            new MatrixStack(),
            orderedRenderCommandQueue,
            new CameraRenderState());
    apply(intValues[0] > 0 == flag, text + ": worn elytra visibility");
    apply(intValues[1] > 0 == flag2, text + ": cosmetic wings visibility");
  }

  public static void apply(boolean flag, String text) {
    if (!flag) {
      throw new AssertionError(text);
    }
  }
}
