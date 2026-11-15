package bunnythekenzo.client.gametest;

import com.google.gson.JsonObject;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Locale;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.render.command.OrderedRenderCommandQueueImpl;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.render.item.ItemRenderState.Glint;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.texture.GlTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL33C;
import org.lwjgl.system.MemoryUtil;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.GlintStyle;
import bunnythekenzo.client.GlintSubmissionScope;
import bunnythekenzo.client.GuiDialogManager;
import bunnythekenzo.client.module.CustomGlintModule;
import bunnythekenzo.client.render.AnimatedGlintTexture;
import bunnythekenzo.client.render.GlintPipelines;
import bunnythekenzo.client.setting.GlintStyleSetting;
import bunnythekenzo.client.setting.Setting;
import bunnythekenzo.client.setting.SettingViewFactory;
import bunnythekenzo.client.setting.SettingWidget;
import bunnythekenzo.client.setting.SettingWidgetSupport;
import bunnythekenzo.client.ui.GalleryItem;
import bunnythekenzo.client.ui.GlintGallery;
import bunnythekenzo.client.ui.PanelScreen;
import bunnythekenzo.client.ui.SelectionDialog;

public final class GlintInfernoInHandTest {
  public static void run(ClientGameTestContext clientGameTestContext) {
    ClientInitializer.logger.info("Custom Glint test: preparing GPU and settings checks");
    ProtectionPerformanceTest.ensureInitialized();

    Runnable onClient =
        clientGameTestContext.computeOnClient(
            minecraftClient -> {
              ProtectionPerformanceTest.ensureInitialized();

              CustomGlintModule customGlintModule =
                  ClientInitializer.getModuleManager().customGlintModule;
              LinkedHashMap linkedHashMap = new LinkedHashMap();

              for (Setting setting :
                  (java.lang.Iterable<Setting>)
                      (java.lang.Iterable<?>) (customGlintModule.getSettings())) {
                linkedHashMap.put(setting, setting.toJson());
              }

              LinkedHashMap linkedHashMap2 = new LinkedHashMap();
              ClientInitializer.getModuleManager()
                  .getList()
                  .forEach(
                      value3 -> {
                        linkedHashMap2.put(
                            value3, ((bunnythekenzo.client.module.Module) (value3)).isEnabled());
                      });
              ClientInitializer.getModuleManager()
                  .getList()
                  .forEach(
                      (java.util.function.Consumer<bunnythekenzo.client.module.Module>)
                          (module -> {
                            module.setEnabled(false);
                          }));
              customGlintModule
                  .getSettings()
                  .forEach(
                      (referenceArgument40_0) ->
                          ((bunnythekenzo.client.setting.Setting) referenceArgument40_0)
                              .resetToDefault());
              Perspective perspective = minecraftClient.options.getPerspective();
              int value4 = minecraftClient.options.getFov().getValue();
              AnimatedGlintTexture.tick();
              ItemStack itemStack = new ItemStack(Items.DIAMOND_SWORD);
              itemStack.set(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true);
              minecraftClient.player.getInventory().setStack(0, itemStack);
              minecraftClient.player.getInventory().setSelectedSlot(0);
              apply(
                  SettingWidget.getSettingWidget(
                          SettingViewFactory.getSettingEntry(customGlintModule.glintStyleSetting))
                      instanceof SettingWidgetSupport,
                  "style opens a picker row");
              return () -> {
                GuiDialogManager.closeAll();
                linkedHashMap.forEach(
                    (referenceArgument41_0, referenceArgument41_1) ->
                        ((bunnythekenzo.client.setting.Setting) referenceArgument41_0)
                            .fromJson((com.google.gson.JsonElement) referenceArgument41_1));
                linkedHashMap2.forEach(
                    (referenceArgument42_0, referenceArgument42_1) ->
                        ((bunnythekenzo.client.module.Module) referenceArgument42_0)
                            .setEnabled((java.lang.Boolean) referenceArgument42_1));
                minecraftClient.options.setPerspective(perspective);
                minecraftClient.options.getFov().setValue(value4);
                AnimatedGlintTexture.tick();
                ((net.minecraft.client.MinecraftClient) (minecraftClient)).setScreen(null);
              };
            });

    try {
      byte[][] onClient2 =
          clientGameTestContext.computeOnClient(GlintInfernoInHandTest::getByteValueArray);
      ClientInitializer.logger.info(
          "Custom Glint test: original textures read; checking all styles");

      for (GlintStyle glintStyle : GlintStyle.values()) {
        clientGameTestContext.runOnClient(
            minecraftClient2 -> {
              CustomGlintModule customGlintModule2 =
                  ClientInitializer.getModuleManager().customGlintModule;
              customGlintModule2.glintStyleSetting.apply(glintStyle.label);
              customGlintModule2.strengthSetting.setNumber(Double.valueOf(100.0));
              customGlintModule2.setEnabled(true);
              AnimatedGlintTexture.tick();
            });
        awaitMaterial(clientGameTestContext);
        clientGameTestContext.runOnClient(
            minecraftClient3 -> {
              ProtectionPerformanceTest.ensureInitialized();
              apply(
                  !Arrays.equals(onClient2[0], getByteValueArray2(minecraftClient3)),
                  glintStyle.label + " uploads its own material");
            });
        byte[] onClient3 =
            clientGameTestContext.computeOnClient(GlintInfernoInHandTest::getByteValueArray2);
        clientGameTestContext.waitTicks(12);
        if (glintStyle != GlintStyle.DEFAULT) {
          clientGameTestContext.runOnClient(
              minecraftClient4 -> {
                ProtectionPerformanceTest.ensureInitialized();

                byte[] bytes = getByteValueArray2(minecraftClient4);
                apply(
                    !Arrays.equals(onClient3, bytes),
                    glintStyle.label + " animates the material shared by items and equipment");
              });
        }
      }

      clientGameTestContext.takeScreenshot("glint-inferno-in-hand");
      clientGameTestContext.runOnClient(GlintInfernoInHandTest::apply);
      clientGameTestContext.runOnClient(
          minecraftClient5 -> {
            ProtectionPerformanceTest.ensureInitialized();

            CustomGlintModule customGlintModule3 =
                ClientInitializer.getModuleManager().customGlintModule;
            customGlintModule3.glintStyleSetting.apply("Galaxy");
            customGlintModule3.finishSetting.setValue("Full effect");
            customGlintModule3.applyToSetting.setValue("All items");
            minecraftClient5.player.setYaw(0.0F);
            minecraftClient5.player.setPitch(10.0F);
            Vec3d entityPos = minecraftClient5.player.getEntityPos();

            for (int index = 0; index < 5; index++) {
              ItemEntity itemEntity =
                  new ItemEntity(
                      minecraftClient5.world,
                      entityPos.x + (index - 2) * 0.6,
                      entityPos.y + 0.8,
                      entityPos.z + 2.5,
                      new ItemStack(
                          new Item[] {
                                Items.DIAMOND_SWORD,
                                Items.APPLE,
                                Items.DIAMOND,
                                Items.GRASS_BLOCK,
                                Items.DIAMOND_PICKAXE
                              }
                              [index]));
              itemEntity.setVelocity(0.0, 0.0, 0.0);
              itemEntity.setNoGravity(true);
              itemEntity.setPickupDelayInfinite();
              minecraftClient5.world.addEntity(itemEntity);
            }
          });
      clientGameTestContext.waitTicks(12);
      clientGameTestContext.takeScreenshot("galaxy-dropped-items");
      clientGameTestContext.runOnClient(
          minecraftClient6 -> {
            minecraftClient6.player.equipStack(
                EquipmentSlot.HEAD, new ItemStack(Items.DIAMOND_HELMET));
            minecraftClient6.player.equipStack(
                EquipmentSlot.CHEST, new ItemStack(Items.DIAMOND_CHESTPLATE));
            minecraftClient6.player.equipStack(
                EquipmentSlot.LEGS, new ItemStack(Items.DIAMOND_LEGGINGS));
            minecraftClient6.player.equipStack(
                EquipmentSlot.FEET, new ItemStack(Items.DIAMOND_BOOTS));
            minecraftClient6.options.setPerspective(Perspective.THIRD_PERSON_FRONT);
            minecraftClient6.options.getFov().setValue(40);
            minecraftClient6.player.setPitch(0.0F);
          });
      clientGameTestContext.waitTicks(10);
      clientGameTestContext.takeScreenshot("galaxy-worn-armor");
      clientGameTestContext.runOnClient(
          minecraftClient7 -> {
            minecraftClient7.player.equipStack(EquipmentSlot.CHEST, new ItemStack(Items.ELYTRA));
            minecraftClient7.options.setPerspective(Perspective.THIRD_PERSON_BACK);
          });
      clientGameTestContext.waitTicks(10);
      clientGameTestContext.takeScreenshot("galaxy-elytra");
      clientGameTestContext.runOnClient(
          minecraftClient8 -> {
            minecraftClient8.options.setPerspective(Perspective.FIRST_PERSON);
            minecraftClient8.options.getFov().setValue(70);
          });

      for (String text : new String[] {"Overlay", "Full effect", "Blend"}) {
        clientGameTestContext.runOnClient(
            minecraftClient9 -> {
              CustomGlintModule customGlintModule4 =
                  ClientInitializer.getModuleManager().customGlintModule;
              customGlintModule4.glintStyleSetting.apply("Galaxy");
              customGlintModule4.finishSetting.setValue(text);
              customGlintModule4.applyToSetting.setValue("All items");
              customGlintModule4.textureVisibilitySetting.setNumber(Double.valueOf(45.0));
              minecraftClient9.setScreen(new HoppinStandingBeforeTestSupport19());
            });
        clientGameTestContext.waitTicks(10);
        clientGameTestContext.takeScreenshot(
            "galaxy-" + text.replace(' ', '-').toLowerCase(Locale.ROOT));
      }

      for (GlintStyle glintStyle2 : GlintStyle.values()) {
        clientGameTestContext.runOnClient(
            minecraftClient10 -> {
              CustomGlintModule customGlintModule5 =
                  ClientInitializer.getModuleManager().customGlintModule;
              customGlintModule5.glintStyleSetting.apply(glintStyle2.label);
              customGlintModule5.finishSetting.setValue("Full effect");
            });
        clientGameTestContext.waitTicks(3);
        clientGameTestContext.takeScreenshot(
            "material-" + glintStyle2.name().toLowerCase(Locale.ROOT));
      }

      clientGameTestContext.runOnClient(
          minecraftClient11 -> {
            ClientInitializer.getModuleManager()
                .customGlintModule
                .speedSetting
                .setNumber(Double.valueOf(0.0));
          });
      clientGameTestContext.waitTicks(8);
      awaitMaterial(clientGameTestContext);
      byte[] onClient4 =
          clientGameTestContext.computeOnClient(GlintInfernoInHandTest::getByteValueArray2);
      clientGameTestContext.waitTicks(10);
      clientGameTestContext.runOnClient(
          minecraftClient12 -> {
            apply(
                Arrays.equals(onClient4, getByteValueArray2(minecraftClient12)),
                "zero speed freezes GPU material");
          });
      clientGameTestContext.runOnClient(
          minecraftClient13 -> {
            CustomGlintModule customGlintModule6 =
                ClientInitializer.getModuleManager().customGlintModule;
            ProtectionPerformanceTest.ensureInitialized();

            customGlintModule6.speedSetting.setNumber(Double.valueOf(100.0));
            customGlintModule6.strengthSetting.setNumber(Double.valueOf(0.0));
            AnimatedGlintTexture.tick();
            apply(
                customGlintModule6.computeFloat2() == 0.0F,
                "zero strength disables shader contribution");
            apply(
                Arrays.deepEquals(onClient2, getByteValueArray(minecraftClient13)),
                "custom effects never overwrite resource-pack foil textures");
            customGlintModule6.setEnabled(false);
            AnimatedGlintTexture.tick();
            apply(
                Arrays.deepEquals(onClient2, getByteValueArray(minecraftClient13)),
                "disable restores both original textures exactly");
            JsonObject jsonObject = new JsonObject();
            JsonObject jsonObject2 = new JsonObject();
            JsonObject jsonObject3 = new JsonObject();
            JsonObject jsonObject4 = new JsonObject();
            jsonObject4.addProperty("Style", "Galaxy");
            jsonObject4.addProperty("Strength", 75);
            jsonObject3.add("settings", jsonObject4);
            jsonObject2.add("CustomGlint@MISC", jsonObject3);
            jsonObject.add("modules", jsonObject2);
            ClientInitializer.getClientConfig().apply(jsonObject);
            apply(
                customGlintModule6.glintStyleSetting.isSelected("Galaxy")
                    && (Double) customGlintModule6.strengthSetting.getValue() == 75.0,
                "legacy module config migrates");
            apply(
                !SettingViewFactory.getSettingEntry(customGlintModule6.modeSetting).isVisible()
                    && !SettingViewFactory.getSettingEntry(customGlintModule6.colorSetting)
                        .isVisible()
                    && SettingViewFactory.getSettingEntry(customGlintModule6.speedSetting)
                        .isVisible(),
                "custom styles show only applicable controls");
            customGlintModule6.setEnabled(true);
            PanelScreen panelScreen = new PanelScreen();
            minecraftClient13.setScreen(panelScreen);
            apply(panelScreen.test("Custom Glint"), "module settings reachable by display name");
          });
      clientGameTestContext.waitTicks(14);
      clientGameTestContext.takeScreenshot("glint-settings");
      clientGameTestContext.runOnClient(
          minecraftClient14 -> {
            GlintStyleSetting glintStyleSetting =
                ClientInitializer.getModuleManager().customGlintModule.glintStyleSetting;
            ProtectionPerformanceTest.ensureInitialized();

            GlintGallery glintGallery = new GlintGallery(glintStyleSetting);

            for (GalleryItem galleryItem :
                (java.lang.Iterable<GalleryItem>)
                    (java.lang.Iterable<?>) (glintGallery.getList2())) {
              galleryItem.update();
              galleryItem.update();
              apply(
                  galleryItem.test3()
                      && glintGallery.getList2().stream()
                              .filter(
                                  value9 -> {
                                    return ((bunnythekenzo.client.ui.GalleryItem) (value9)).test3();
                                  })
                              .count()
                          == 1L,
                  "selection stays exclusive and cannot deselect itself");
            }

            glintStyleSetting.apply("Galaxy");
            GuiDialogManager.open(glintGallery);
            SelectionDialog selectionDialog = GuiDialogManager.getSelectionDialog();
            selectionDialog.setSearchText("aurora");
            apply(selectionDialog.getVisibleItemCount() == 1, "search finds a style");
            selectionDialog.setSearchText("no-such-glint");
            apply(selectionDialog.getVisibleItemCount() == 0, "search handles no matches");
            selectionDialog.setSearchText("");
            selectionDialog.setSelectedOnly(true);
            apply(selectionDialog.getVisibleItemCount() == 1, "current-only filter");
            selectionDialog.setSelectedOnly(false);
            apply(
                selectionDialog.getVisibleItemCount() == GlintStyle.values().length,
                "all styles visible");
          });
      clientGameTestContext.waitTicks(14);
      clientGameTestContext.takeScreenshot("glint-gallery");
      clientGameTestContext.waitTicks(20);
      clientGameTestContext.takeScreenshot("glint-gallery-animated");
      clientGameTestContext.runOnClient(
          minecraftClient15 -> {
            GuiDialogManager.getSelectionDialog().keyPressed(256);
          });
      clientGameTestContext.waitTicks(12);
      clientGameTestContext.runOnClient(
          minecraftClient16 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                !GuiDialogManager.hasDialog()
                    && minecraftClient16.currentScreen instanceof PanelScreen,
                "Escape returns to the module settings");
          });
      var onClient5 =
          clientGameTestContext.computeOnClient(
              minecraftClient17 -> {
                return minecraftClient17.reloadResources();
              });
      clientGameTestContext.waitFor(
          minecraftClient18 -> {
            return onClient5.isDone();
          },
          600);
      onClient5.join();
      clientGameTestContext.waitTicks(10);
      clientGameTestContext.runOnClient(
          minecraftClient19 -> {
            ClientInitializer.getModuleManager().customGlintModule.setEnabled(false);
            AnimatedGlintTexture.tick();
            apply(
                Arrays.deepEquals(onClient2, getByteValueArray(minecraftClient19)),
                "resource reload preserves clean restoration");
          });
      clientGameTestContext.runOnClient(
          minecraftClient20 -> {
            AnimatedGlintTexture.update2();
            ClientInitializer.getModuleManager().customGlintModule.setEnabled(true);
            AnimatedGlintTexture.tick();
            AnimatedGlintTexture.update2();
            AnimatedGlintTexture.tick();
          });
      awaitMaterial(clientGameTestContext);
      ClientInitializer.logger.info(
          "Custom Glint verified: 12 animated materials, item scopes, immutable stacks, freeze,"
              + " reload, finishes, settings and gallery");
    } finally {
      clientGameTestContext.runOnClient(
          minecraftClient21 -> {
            onClient.run();
          });
    }
  }

  public static void awaitMaterial(ClientGameTestContext clientGameTestContext) {
    clientGameTestContext.waitFor(
        minecraftClient -> {
          AnimatedGlintTexture.tick();
          return AnimatedGlintTexture.test();
        },
        200);
  }

  public static byte[][] getByteValueArray(MinecraftClient minecraftClient) {
    return new byte[][] {
      getByteValueArray(minecraftClient, ItemRenderer.ITEM_ENCHANTMENT_GLINT),
      getByteValueArray(minecraftClient, ItemRenderer.ENTITY_ENCHANTMENT_GLINT)
    };
  }

  public static byte[] getByteValueArray2(MinecraftClient minecraftClient) {
    return getByteValueArray(minecraftClient, AnimatedGlintTexture.identifier);
  }

  public static void apply(MinecraftClient minecraftClient) {
    ProtectionPerformanceTest.ensureInitialized();

    CustomGlintModule customGlintModule = ClientInitializer.getModuleManager().customGlintModule;
    customGlintModule.applyToSetting.setValue("Enchanted items");
    ItemStack itemStack = new ItemStack(Items.DIAMOND_SWORD);
    ItemStack itemStack2 = itemStack.copy();
    itemStack2.set(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true);

    for (ItemDisplayContext itemDisplayContext :
        new ItemDisplayContext[] {
          ItemDisplayContext.GUI,
          ItemDisplayContext.GROUND,
          ItemDisplayContext.FIXED,
          ItemDisplayContext.FIRST_PERSON_LEFT_HAND,
          ItemDisplayContext.FIRST_PERSON_RIGHT_HAND,
          ItemDisplayContext.THIRD_PERSON_LEFT_HAND,
          ItemDisplayContext.THIRD_PERSON_RIGHT_HAND
        }) {
      apply(
          !customSubmit(minecraftClient, itemStack, itemDisplayContext),
          "plain item stays vanilla in " + itemDisplayContext);
      apply(
          customSubmit(minecraftClient, itemStack2, itemDisplayContext),
          "enchanted item gets custom material in " + itemDisplayContext);
      customGlintModule.applyToSetting.setValue("All items");
      apply(
          customSubmit(minecraftClient, itemStack, itemDisplayContext),
          "all items includes plain item in " + itemDisplayContext);
      customGlintModule.applyToSetting.setValue("Enchanted items");
    }

    customGlintModule.applyToSetting.setValue("All items");
    customGlintModule.droppedItemsSetting.setValue(false);
    apply(
        !customSubmit(minecraftClient, itemStack, ItemDisplayContext.GROUND),
        "dropped items toggle opts out");
    apply(
        customSubmit(minecraftClient, itemStack, ItemDisplayContext.GUI),
        "drop toggle does not affect inventory");
    customGlintModule.droppedItemsSetting.setValue(true);
    customGlintModule.inventoryHotbarSetting.setValue(false);
    apply(
        !customSubmit(minecraftClient, itemStack2, ItemDisplayContext.GUI),
        "inventory toggle preserves vanilla enchanted items");
    customGlintModule.inventoryHotbarSetting.setValue(true);
    customGlintModule.heldItemsSetting.setValue(false);
    apply(
        !customSubmit(minecraftClient, itemStack, ItemDisplayContext.FIRST_PERSON_LEFT_HAND),
        "held toggle covers offhand");
    customGlintModule.heldItemsSetting.setValue(true);
    customGlintModule.itemFramesSetting.setValue(false);
    apply(
        !customSubmit(minecraftClient, itemStack, ItemDisplayContext.FIXED), "item frames opt out");
    customGlintModule.itemFramesSetting.setValue(true);
    customGlintModule.wornArmorSetting.setValue(false);
    apply(!customGlintModule.test(true), "armor opts out");
    customGlintModule.wornArmorSetting.setValue(true);
    apply(customGlintModule.test(false), "all items includes unenchanted armor");
    customGlintModule.setEnabled(false);
    apply(
        !customSubmit(minecraftClient, itemStack2, ItemDisplayContext.GUI),
        "disabled renderer returns to vanilla");
    customGlintModule.setEnabled(true);
    apply(
        !itemStack.hasGlint()
            && itemStack2.hasGlint()
            && itemStack.getComponents().equals(new ItemStack(Items.DIAMOND_SWORD).getComponents()),
        "rendering never mutates item components");
    apply(!GlintSubmissionScope.isActive(), "submission scope restored");
  }

  public static boolean customSubmit(
      MinecraftClient minecraftClient, ItemStack itemStack, ItemDisplayContext itemDisplayContext) {
    ProtectionPerformanceTest.ensureInitialized();

    ItemRenderState itemRenderState = new ItemRenderState();
    minecraftClient
        .getItemModelManager()
        .clearAndUpdate(
            itemRenderState,
            itemStack,
            itemDisplayContext,
            minecraftClient.world,
            minecraftClient.player,
            0);
    OrderedRenderCommandQueueImpl orderedRenderCommandQueueImpl =
        new OrderedRenderCommandQueueImpl();
    itemRenderState.render(new MatrixStack(), orderedRenderCommandQueueImpl, 15728880, 655360, 0);
    var list =
        orderedRenderCommandQueueImpl.getBatchingQueues().values().stream()
            .flatMap(
                batchingRenderCommandQueue -> {
                  return batchingRenderCommandQueue.getItemCommands().stream();
                })
            .toList();
    apply(!list.isEmpty(), "item model submitted geometry for " + itemDisplayContext);
    boolean flag =
        list.stream()
            .anyMatch(
                itemCommand -> {
                  return GlintPipelines.test(
                      ((net.minecraft.client.render.command.OrderedRenderCommandQueueImpl
                                  .ItemCommand)
                              (itemCommand))
                          .renderLayer());
                });
    if (flag) {
      apply(
          list.stream()
              .allMatch(
                  itemCommand2 -> {
                    return ((net.minecraft.client.render.command.OrderedRenderCommandQueueImpl
                                    .ItemCommand)
                                (itemCommand2))
                            .glintType()
                        == Glint.NONE;
                  }),
          "custom material has no duplicate vanilla foil");
    }

    orderedRenderCommandQueueImpl.clear();
    return flag;
  }

  public static byte[] getByteValueArray(MinecraftClient minecraftClient, Identifier identifier) {
    GlTexture glTexture =
        (GlTexture) minecraftClient.getTextureManager().getTexture(identifier).getGlTexture();
    int intValue = GL33C.glGetInteger(32873);
    int intValue2 = GL33C.glGetInteger(35053);
    int intValue3 = GL33C.glGetInteger(3330);
    int intValue4 = GL33C.glGetInteger(3331);
    int intValue5 = GL33C.glGetInteger(3332);
    GL33C.glBindBuffer(35051, 0);
    GL33C.glPixelStorei(3330, 0);
    GL33C.glPixelStorei(3331, 0);
    GL33C.glPixelStorei(3332, 0);
    GL33C.glBindTexture(3553, glTexture.getGlId());
    int intValue6 = GL33C.glGetTexLevelParameteri(3553, 0, 4096);
    int intValue7 = GL33C.glGetTexLevelParameteri(3553, 0, 4097);
    ByteBuffer byteBuffer = MemoryUtil.memAlloc(intValue6 * intValue7 * 4);
    boolean flag = false  ;

    byte[] bytes;
    try {
      flag = true;
      GL33C.glGetTexImage(3553, 0, 6408, 5121, byteBuffer);
      byte[] bytes2 = new byte[byteBuffer.remaining()];
      byteBuffer.get(bytes2);
      bytes = bytes2;
      flag = false;
    } finally {
      if (flag) {

        MemoryUtil.memFree(byteBuffer);

        GL33C.glBindTexture(3553, intValue);

        GL33C.glBindBuffer(35051, intValue2);

        GL33C.glPixelStorei(3330, intValue3);

        GL33C.glPixelStorei(3331, intValue4);

        GL33C.glPixelStorei(3332, intValue5);
      }
    }

    MemoryUtil.memFree(byteBuffer);
    GL33C.glBindTexture(3553, intValue);
    GL33C.glBindBuffer(35051, intValue2);
    GL33C.glPixelStorei(3330, intValue3);
    GL33C.glPixelStorei(3331, intValue4);
    GL33C.glPixelStorei(3332, intValue5);
    return bytes;
  }

  public static void apply(boolean flag, String text) {
    if (!flag) {
      throw new AssertionError("Custom Glint: " + text);
    }
  }
}
