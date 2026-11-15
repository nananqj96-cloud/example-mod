package bunnythekenzo.client.gametest;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.List;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.render.chunk.ChunkRendererRegion;
import net.minecraft.client.render.chunk.ChunkRendererRegionBuilder;
import net.minecraft.client.render.entity.state.ItemEntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.EquipmentPreview;
import bunnythekenzo.client.FakeLootDropModule;
import bunnythekenzo.client.GuiDialogManager;
import bunnythekenzo.client.mixin.HandledScreenAccessor;
import bunnythekenzo.client.mixin.MinecraftClientAccessor;
import bunnythekenzo.client.setting.ItemReplacementRule;
import bunnythekenzo.client.setting.ItemReplacementSetting;
import bunnythekenzo.client.ui.GalleryItem;
import bunnythekenzo.client.ui.ItemReplacementDialog;
import bunnythekenzo.client.ui.PanelScreen;

public final class FakeLootRulesTest {

  public static void run(ClientGameTestContext clientGameTestContext) {
    JsonObject onClient =
        clientGameTestContext.computeOnClient(
            minecraftClient -> {
              return ClientInitializer.getClientConfig().getJsonObject();
            });

    try {
      clientGameTestContext.runOnClient(FakeLootRulesTest::apply);
      clientGameTestContext.runOnClient(
          minecraftClient2 -> {
            PanelScreen panelScreen = new PanelScreen();
            minecraftClient2.setScreen(panelScreen);
            apply(panelScreen.test("Fake Loot Drop"), "module settings are reachable");
            GuiDialogManager.open(
                ClientInitializer.getModuleManager().fakeLootDropModule.itemReplacementsSetting);
          });
      clientGameTestContext.waitTicks(20);
      clientGameTestContext.takeScreenshot("fake-loot-rules");
      clientGameTestContext.runOnClient(
          minecraftClient3 -> {
            ((ItemReplacementDialog) GuiDialogManager.getCurrentDialog()).addReplacement();
          });
      clientGameTestContext.waitTicks(10);
      clientGameTestContext.runOnClient(
          minecraftClient4 -> {
            apply(GuiDialogManager.getSelectionDialog() != null, "add opens the existing picker");
            GuiDialogManager.getSelectionDialog().setSearchText("diamond");
          });
      clientGameTestContext.waitTicks(5);
      clientGameTestContext.takeScreenshot("fake-loot-existing-picker");
      clientGameTestContext.runOnClient(
          minecraftClient5 -> {
            select(Items.DIAMOND);
          });
      clientGameTestContext.waitTicks(12);
      clientGameTestContext.runOnClient(
          minecraftClient6 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                GuiDialogManager.getSelectionDialog() != null
                    && GuiDialogManager.getSelectionDialog()
                        .getGalleryModel()
                        .getString()
                        .equals("Choose fake item"),
                "selecting a source opens the target picker");
            select(Items.EMERALD);
          });
      clientGameTestContext.waitTicks(12);
      clientGameTestContext.runOnClient(
          minecraftClient7 -> {
            ProtectionPerformanceTest.ensureInitialized();

            ItemReplacementSetting itemReplacementSetting =
                ClientInitializer.getModuleManager().fakeLootDropModule.itemReplacementsSetting;
            apply(
                GuiDialogManager.getCurrentDialog() instanceof ItemReplacementDialog,
                "selection returns to rule list");
            apply(
                itemReplacementSetting.getItem(Items.DIAMOND) == Items.EMERALD
                    && itemReplacementSetting.getItem(Items.DIRT) == Items.DIAMOND_BLOCK,
                "adding a pair preserves earlier selections");
            ((ItemReplacementDialog) GuiDialogManager.getCurrentDialog()).addReplacement();
            GuiDialogManager.getSelectionDialog().keyPressed(256);
          });
      clientGameTestContext.waitTicks(12);
      clientGameTestContext.runOnClient(
          minecraftClient8 -> {
            apply(
                GuiDialogManager.getCurrentDialog() instanceof ItemReplacementDialog,
                "cancel returns to the manager");
            minecraftClient8.setScreen(null);
            minecraftClient8.player.getInventory().setStack(0, new ItemStack(Items.DIRT, 37));
            minecraftClient8
                .player
                .getInventory()
                .setStack(1, new ItemStack(Items.COBBLESTONE, 12));
            minecraftClient8.player.getInventory().setSelectedSlot(0);
          });
      clientGameTestContext.waitTicks(20);
      clientGameTestContext.takeScreenshot("fake-loot-hand-hotbar-drops");
      clientGameTestContext.runOnClient(
          minecraftClient9 -> {
            ItemStack itemStack = new ItemStack(Items.DIRT, 37);
            itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.literal("Server dirt"));
            itemStack.set(
                DataComponentTypes.LORE, new LoreComponent(List.of(Text.literal("Worth: $0.01"))));
            minecraftClient9.setScreen(
                new ItemPreviewScreen(
                    Text.literal("Fake Loot tooltip test"), itemStack, minecraftClient9));
          });
      clientGameTestContext.waitTicks(5);
      clientGameTestContext.takeScreenshot("fake-loot-hover-without-worth");
      checkPreview(clientGameTestContext);
      ClientInitializer.logger.info(
          "Fake Loot verified: clean tooltips, no drop nametags, placed models, cosmetic armor,"
              + " local flight and picker flow");
    } finally {
      clientGameTestContext.runOnClient(
          minecraftClient10 -> {
            GuiDialogManager.closeAll();
            minecraftClient10.setScreen(null);
            ClientInitializer.getClientConfig().apply(onClient);
          });
    }
  }

  public static void apply(MinecraftClient minecraftClient) {
    FakeLootDropModule fakeLootDropModule = ClientInitializer.getModuleManager().fakeLootDropModule;
    ItemReplacementSetting itemReplacementSetting = fakeLootDropModule.itemReplacementsSetting;
    itemReplacementSetting.apply(
        List.of(
            new ItemReplacementRule(Items.DIRT, Items.DIAMOND_BLOCK, true),
            new ItemReplacementRule(Items.COBBLESTONE, Items.ANCIENT_DEBRIS, true),
            new ItemReplacementRule(Items.DIAMOND_BLOCK, Items.GOLD_BLOCK, true)));
    fakeLootDropModule.setEnabled(true);
    ProtectionPerformanceTest.ensureInitialized();

    ItemStack itemStack = new ItemStack(Items.DIRT, 37);
    itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.literal("Server dirt"));
    itemStack.set(
        DataComponentTypes.LORE, new LoreComponent(List.of(Text.literal("Worth: $0.01"))));
    ItemStack itemStack2 = fakeLootDropModule.getItemStack(itemStack);
    apply(
        itemStack2.isOf(Items.DIAMOND_BLOCK) && itemStack2.getCount() == 37,
        "first rule preserves count");
    apply(
        itemStack2.getName().equals(Items.DIAMOND_BLOCK.getName())
            && itemStack2
                .getOrDefault(DataComponentTypes.LORE, LoreComponent.DEFAULT)
                .lines()
                .isEmpty(),
        "fake has its own name and no worth lore");
    apply(
        itemStack.isOf(Items.DIRT) && itemStack.get(DataComponentTypes.LORE).lines().size() == 1,
        "real stack stays intact");
    apply(
        fakeLootDropModule
            .getItemStack(new ItemStack(Items.COBBLESTONE))
            .isOf(Items.ANCIENT_DEBRIS),
        "second rule is active simultaneously");
    apply(
        fakeLootDropModule.getItemStack(ItemStack.EMPTY) == ItemStack.EMPTY,
        "empty stack passes through");
    ItemStack itemStack3 = new ItemStack(Items.APPLE);
    apply(
        fakeLootDropModule.getItemStack(itemStack3) == itemStack3, "unselected items pass through");

    for (ItemDisplayContext itemDisplayContext :
        new ItemDisplayContext[] {
          ItemDisplayContext.GUI,
          ItemDisplayContext.GROUND,
          ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,
          ItemDisplayContext.FIXED
        }) {
      ItemRenderState itemRenderState =
          getItemRenderState(minecraftClient, itemStack, itemDisplayContext);
      fakeLootDropModule.setEnabled(false);
      ItemRenderState itemRenderState2 =
          getItemRenderState(
              minecraftClient, new ItemStack(Items.DIAMOND_BLOCK), itemDisplayContext);
      fakeLootDropModule.setEnabled(true);
      apply(
          !itemRenderState.isEmpty()
              && itemRenderState.getParticleSprite(Random.create(7L))
                  == itemRenderState2.getParticleSprite(Random.create(7L)),
          itemDisplayContext + " uses the fake model exactly once even with chained rules");
    }

    ItemEntity itemEntity =
        new ItemEntity(
            minecraftClient.world,
            minecraftClient.player.getX(),
            minecraftClient.player.getY() + 0.3,
            minecraftClient.player.getZ() + 3.0,
            itemStack);
    itemEntity.setId(-67101);
    itemEntity.setNoGravity(true);
    itemEntity.setVelocity(Vec3d.ZERO);
    itemEntity.setCustomName(Text.literal("Dirt\nWorth: $0.01"));
    itemEntity.setCustomNameVisible(true);
    itemEntity.resetPosition();
    minecraftClient.world.addEntity(itemEntity);
    ItemEntityRenderState itemEntityRenderState =
        (ItemEntityRenderState)
            minecraftClient.getEntityRenderDispatcher().getAndUpdateRenderState(itemEntity, 1.0F);
    apply(
        itemEntityRenderState.displayName == null && itemEntity.getStack() == itemStack,
        "real dropped name is suppressed without changing entity data");
    BlockPos blockPos = minecraftClient.player.getBlockPos().add(2, 0, 3);
    minecraftClient.world.setBlockState(blockPos, Blocks.COBBLESTONE.getDefaultState(), 3);
    ChunkRendererRegion chunkRendererRegion =
        new ChunkRendererRegionBuilder()
            .build(minecraftClient.world, ChunkSectionPos.from(blockPos).asLong());
    apply(
        chunkRendererRegion != null
            && chunkRendererRegion.getBlockState(blockPos).isOf(Blocks.ANCIENT_DEBRIS),
        "placed block mesh uses fake block");
    apply(
        minecraftClient.world.getBlockState(blockPos).isOf(Blocks.COBBLESTONE),
        "real world block is unchanged");
    itemReplacementSetting.put(Items.COBBLESTONE, Items.CHEST);
    ChunkRendererRegion chunkRendererRegion2 =
        new ChunkRendererRegionBuilder()
            .build(minecraftClient.world, ChunkSectionPos.from(blockPos).asLong());
    apply(
        chunkRendererRegion2.getBlockState(blockPos).isOf(Blocks.CHEST)
            && chunkRendererRegion2.getBlockEntity(blockPos) != null,
        "fake blocks with an entity renderer get a display entity");
    itemReplacementSetting.put(Items.COBBLESTONE, Items.ANCIENT_DEBRIS);
    JsonElement jsonElement = itemReplacementSetting.toJson();
    itemReplacementSetting.apply(List.of());
    itemReplacementSetting.fromJson(jsonElement);
    apply(
        ((List) itemReplacementSetting.getValue()).size() == 3
            && itemReplacementSetting.getItem(Items.DIRT) == Items.DIAMOND_BLOCK,
        "all pairs survive JSON reload");
    ItemReplacementRule choiceSettingData =
        (ItemReplacementRule) ((List) itemReplacementSetting.getValue()).getFirst();
    itemReplacementSetting.update(
        choiceSettingData,
        new ItemReplacementRule(choiceSettingData.item(), choiceSettingData.item2(), false));
    apply(
        fakeLootDropModule.getItemStack(itemStack) == itemStack, "individual rule can be disabled");
    itemReplacementSetting.fromJson(jsonElement);
    ItemReplacementSetting itemReplacementSetting2 = new ItemReplacementSetting("test", "");
    itemReplacementSetting2.fromJson(
        JsonParser.parseString(
            "[{\"source\":\"minecraft:apple\",\"target\":\"minecraft:diamond\"},{\"source\":\"missing:item\"},null]"));
    apply(
        ((List) itemReplacementSetting2.getValue()).size() == 1
            && itemReplacementSetting2.getItem(Items.APPLE) == Items.DIAMOND,
        "malformed rows do not discard valid rows");
    fakeLootDropModule.setEnabled(false);
    apply(
        fakeLootDropModule.getItemStack(itemStack) == itemStack
            && chunkRendererRegion.getBlockState(blockPos).isOf(Blocks.COBBLESTONE),
        "disabling restores real items and block view");
    fakeLootDropModule.setEnabled(true);
    minecraftClient.player.setYaw(0.0F);
    minecraftClient.player.setPitch(15.0F);
  }

  public static ItemRenderState getItemRenderState(
      MinecraftClient minecraftClient, ItemStack itemStack, ItemDisplayContext itemDisplayContext) {
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
    return itemRenderState;
  }

  public static void checkPreview(ClientGameTestContext clientGameTestContext) {
    clientGameTestContext.runOnClient(
        minecraftClient -> {
          minecraftClient.setScreen(null);
          FakeLootDropModule fakeLootDropModule =
              ClientInitializer.getModuleManager().fakeLootDropModule;
          fakeLootDropModule.itemReplacementsSetting.put(Items.SAND, Items.ELYTRA);
          fakeLootDropModule.itemReplacementsSetting.put(Items.GRAVEL, Items.DIAMOND_HELMET);
          fakeLootDropModule.itemReplacementsSetting.put(Items.CLAY, Items.DIAMOND_LEGGINGS);
          ProtectionPerformanceTest.ensureInitialized();

          fakeLootDropModule.itemReplacementsSetting.put(Items.BRICKS, Items.DIAMOND_BOOTS);
          fakeLootDropModule.onTick();
          ItemStack itemStack = new ItemStack(Items.SAND, 16);
          itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.literal("Old sand title"));
          itemStack.set(
              DataComponentTypes.LORE, new LoreComponent(List.of(Text.literal("Worth: $1"))));
          minecraftClient.player.getInventory().setStack(0, itemStack);
          minecraftClient.player.getInventory().setStack(1, new ItemStack(Items.GRAVEL));
          minecraftClient.player.getInventory().setStack(2, new ItemStack(Items.CLAY));
          minecraftClient.player.getInventory().setStack(3, new ItemStack(Items.BRICKS));
          minecraftClient.player.getInventory().setSelectedSlot(0);
          ItemStack itemStack2 = fakeLootDropModule.getItemStack(itemStack);
          var tooltipFromItem = Screen.getTooltipFromItem(minecraftClient, itemStack2);
          apply(
              ((Text) tooltipFromItem.getFirst()).getString().equals("Elytra"),
              "elytra uses its real name");
          apply(
              ((Text) tooltipFromItem.getFirst()).getStyle().getColor().getRgb()
                  == new ItemStack(Items.ELYTRA).getRarity().getFormatting().getColorValue(),
              "elytra tooltip uses vanilla rarity color");
          apply(
              tooltipFromItem.stream()
                  .noneMatch(
                      text -> {
                        ProtectionPerformanceTest.ensureInitialized();
                        return ((net.minecraft.text.Text) (text)).getString().contains("Worth")
                            || ((net.minecraft.text.Text) (text)).getString().contains("sand");
                      }),
              "tooltip has no old name or worth");
          minecraftClient.crosshairTarget =
              BlockHitResult.createMissed(
                  minecraftClient.player.getEyePos(),
                  Direction.UP,
                  minecraftClient.player.getBlockPos().up());
          ((MinecraftClientAccessor) (Object) (minecraftClient)).invokeDoItemUse();
          apply(
              fakeLootDropModule
                  .equipmentPreview
                  .getItemStack(EquipmentSlot.CHEST)
                  .isOf(Items.ELYTRA),
              "real use-key hook equips a cosmetic elytra");

          for (int index = 1; index <= 3; index++) {
            apply(
                fakeLootDropModule.equipmentPreview.test(index),
                "all armor slots can be previewed");
          }

          apply(
              minecraftClient.player.getInventory().getStack(0) == itemStack
                  && itemStack.getCount() == 16
                  && !minecraftClient
                      .player
                      .getEquippedStack(EquipmentSlot.CHEST)
                      .isOf(Items.ELYTRA),
              "equipping changes neither source stack nor real equipment");
          PlayerEntityRenderState playerEntityRenderState =
              (PlayerEntityRenderState)
                  minecraftClient
                      .getEntityRenderDispatcher()
                      .getAndUpdateRenderState(minecraftClient.player, 1.0F);
          apply(
              playerEntityRenderState.equippedChestStack.isOf(Items.ELYTRA)
                  && playerEntityRenderState.equippedHeadStack.isOf(Items.DIAMOND_HELMET)
                  && playerEntityRenderState.equippedLegsStack.isOf(Items.DIAMOND_LEGGINGS)
                  && playerEntityRenderState.equippedFeetStack.isOf(Items.DIAMOND_BOOTS),
              "player renderer displays every cosmetic armor slot");
          apply(
              fakeLootDropModule
                  .equipmentPreview
                  .getItemStack(minecraftClient.player.playerScreenHandler.getSlot(6))
                  .isOf(Items.ELYTRA),
              "inventory chest slot displays cosmetic equipment");
          BlockPos blockPos = minecraftClient.player.getBlockPos().add(0, 1, 3);
          minecraftClient.world.setBlockState(blockPos, Blocks.SAND.getDefaultState(), 3);
          ChunkRendererRegion chunkRendererRegion =
              new ChunkRendererRegionBuilder()
                  .build(minecraftClient.world, ChunkSectionPos.from(blockPos).asLong());
          apply(
              chunkRendererRegion != null && chunkRendererRegion.getBlockState(blockPos).isAir(),
              "non-block target removes the sand mesh");
          apply(
              minecraftClient.world.getBlockState(blockPos).isOf(Blocks.SAND),
              "sand collision and interaction remain real");
          minecraftClient.player.setYaw(0.0F);
          minecraftClient.player.setPitch(0.0F);
          minecraftClient.options.setPerspective(Perspective.THIRD_PERSON_FRONT);
        });
    clientGameTestContext.waitTicks(15);
    clientGameTestContext.takeScreenshot("fake-loot-elytra-equipped");
    clientGameTestContext.runOnClient(
        minecraftClient2 -> {
          ProtectionPerformanceTest.ensureInitialized();

          EquipmentPreview equipmentPreview =
              ClientInitializer.getModuleManager().fakeLootDropModule.equipmentPreview;
          Vec3d entityPos = minecraftClient2.player.getEntityPos();
          apply(equipmentPreview.test2(), "local flight starts with fake elytra equipped");

          for (int index2 = 0; index2 < 8; index2++) {
            equipmentPreview.tick();
          }

          apply(
              equipmentPreview.test3()
                  && equipmentPreview.getVec3d(1.0F).distanceTo(entityPos) > 1.0,
              "preview flies independently");
          apply(
              minecraftClient2.player.getEntityPos().equals(entityPos)
                  && !minecraftClient2.player.isGliding(),
              "real player never enters flight or moves with preview");
          apply(
              !equipmentPreview.test(entityPos.add(10000.0, 0.0, 0.0)),
              "preview rejects leaving loaded render distance");
          PlayerEntityRenderState playerEntityRenderState2 =
              (PlayerEntityRenderState)
                  minecraftClient2
                      .getEntityRenderDispatcher()
                      .getAndUpdateRenderState(minecraftClient2.player, 1.0F);
          apply(
              playerEntityRenderState2.isGliding
                  && playerEntityRenderState2.equippedChestStack.isOf(Items.ELYTRA),
              "render-only avatar has elytra flight pose");
          minecraftClient2.options.forwardKey.setPressed(true);
          equipmentPreview.update();
          apply(
              !minecraftClient2.player.input.playerInput.forward(),
              "flight controls do not become player movement");
          minecraftClient2.options.forwardKey.setPressed(false);
        });
    clientGameTestContext.waitTicks(8);
    clientGameTestContext.takeScreenshot("fake-loot-local-flight");
    clientGameTestContext.runOnClient(
        minecraftClient3 -> {
          FakeLootDropModule fakeLootDropModule2 =
              ClientInitializer.getModuleManager().fakeLootDropModule;
          fakeLootDropModule2.equipmentPreview.update2();
          minecraftClient3.setScreen(new InventoryScreen(minecraftClient3.player));
        });
    clientGameTestContext.waitTicks(8);
    double[] onClient =
        clientGameTestContext.computeOnClient(
            minecraftClient4 -> {
              HandledScreenAccessor handledScreenAccessor =
                  (HandledScreenAccessor) (Object) (minecraftClient4.currentScreen);
              Slot slot = minecraftClient4.player.playerScreenHandler.getSlot(36);
              double scaleFactor = minecraftClient4.getWindow().getScaleFactor();
              return new double[] {
                (handledScreenAccessor.getLeftPos() + slot.x + 8) * scaleFactor,
                (handledScreenAccessor.getTopPos() + slot.y + 8) * scaleFactor
              };
            });
    clientGameTestContext.getInput().setCursorPos(onClient[0], onClient[1]);
    clientGameTestContext.waitTicks(8);
    clientGameTestContext.takeScreenshot("fake-loot-inventory-elytra-tooltip");
    clientGameTestContext.runOnClient(
        minecraftClient5 -> {
          minecraftClient5.setScreen(null);
          ProtectionPerformanceTest.ensureInitialized();

          FakeLootDropModule fakeLootDropModule3 =
              ClientInitializer.getModuleManager().fakeLootDropModule;
          fakeLootDropModule3.itemReplacementsSetting.apply(
              fakeLootDropModule3.itemReplacementsSetting.getValue().stream()
                  .filter(
                      choiceSettingData -> {
                        return ((bunnythekenzo.client.setting.ItemReplacementRule) (choiceSettingData))
                                .item()
                            == Items.SAND;
                      })
                  .findFirst()
                  .orElseThrow());
          fakeLootDropModule3.onTick();
          apply(
              fakeLootDropModule3.equipmentPreview.getItemStack(EquipmentSlot.CHEST).isEmpty()
                  && !fakeLootDropModule3.equipmentPreview.test3(),
              "removing a rule clears associated equipment and flight");
          fakeLootDropModule3.setEnabled(false);
          apply(
              fakeLootDropModule3.equipmentPreview.getItemStack(EquipmentSlot.HEAD).isEmpty(),
              "disabling clears all previews");
          minecraftClient5.options.setPerspective(Perspective.FIRST_PERSON);
        });
  }

  public static void select(Item item) {
    GalleryItem galleryItem =
        (bunnythekenzo.client.ui.GalleryItem)
            (GuiDialogManager.getSelectionDialog().getGalleryModel().getList2().stream()
                .filter(
                    value -> {
                      return ((bunnythekenzo.client.ui.GalleryItem) (value)).getItemStack().isOf(item);
                    })
                .findFirst()
                .orElseThrow());
    galleryItem.update();
  }

  public static void apply(boolean flag, String text) {
    if (!flag) {
      throw new AssertionError("Fake Loot: " + text);
    }
  }
}
