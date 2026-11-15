package bunnythekenzo.client.gametest;

import com.google.gson.JsonObject;
import com.mojang.blaze3d.systems.VertexSorter;
import java.lang.reflect.Method;
import java.util.List;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;
import net.minecraft.client.render.chunk.BlockBufferAllocatorStorage;
import net.minecraft.client.render.chunk.ChunkRendererRegion;
import net.minecraft.client.render.chunk.ChunkRendererRegionBuilder;
import net.minecraft.client.render.chunk.SectionBuilder;
import net.minecraft.client.render.chunk.SectionBuilder.RenderData;
import net.minecraft.client.render.entity.state.ItemFrameEntityRenderState;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.MapIdComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.map.MapState;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.FakeLootDropModule;
import bunnythekenzo.client.mixin.MinecraftClientAccessor;
import bunnythekenzo.client.setting.ItemReplacementRule;

public final class FakeLootWorldBeforeTest {

  public static void run(
      ClientGameTestContext clientGameTestContext,
      TestSingleplayerContext testSingleplayerContext) {
    JsonObject onClient =
        clientGameTestContext.computeOnClient(
            minecraftClient -> {
              return ClientInitializer.getClientConfig().getJsonObject();
            });
    BlockPos onClient2 =
        clientGameTestContext.computeOnClient(
            minecraftClient2 -> {
              return minecraftClient2.player.getBlockPos().add(0, 0, 5);
            });

    try {
      clientGameTestContext.runOnClient(
          minecraftClient3 -> {
            ClientInitializer.getModuleManager().fakeLootDropModule.setEnabled(false);
            minecraftClient3.player.setYaw(0.0F);
            minecraftClient3.player.setPitch(5.0F);
            minecraftClient3.options.setPerspective(Perspective.FIRST_PERSON);
            minecraftClient3.options.hudHidden = true;
          });
      int[] onServer =
          testSingleplayerContext
              .getServer()
              .computeOnServer(
                  minecraftServer -> {
                    ServerWorld overworld = minecraftServer.getOverworld();
                    overworld.setTimeOfDay(6000L);
                    overworld.setBlockState(onClient2.add(-4, 0, 0), Blocks.DIRT.getDefaultState());
                    ProtectionPerformanceTest.ensureInitialized();

                    overworld.setBlockState(
                        onClient2.add(-2, 0, 0), Blocks.COBBLESTONE.getDefaultState());
                    overworld.setBlockState(onClient2, Blocks.SAND.getDefaultState());
                    overworld.setBlockState(
                        onClient2.add(2, 0, 0), Blocks.GRAVEL.getDefaultState());
                    overworld.setBlockState(onClient2.add(4, 0, 0), Blocks.CHEST.getDefaultState());
                    int[] intValues = new int[3];

                    for (int index = 0; index < intValues.length; index++) {
                      BlockPos blockPos = onClient2.add((index - 1) * 3, 1, 2);
                      overworld.setBlockState(blockPos.south(), Blocks.STONE.getDefaultState());
                      ItemFrameEntity itemFrameEntity =
                          new ItemFrameEntity(
                              index == 1 ? EntityType.GLOW_ITEM_FRAME : EntityType.ITEM_FRAME,
                              overworld,
                              blockPos,
                              Direction.NORTH);
                      ItemStack itemStack =
                          new ItemStack(
                              index == 0
                                  ? Items.DIRT
                                  : (index == 1 ? Items.SAND : Items.FILLED_MAP));
                      if (index == 2) {
                        MapIdComponent mapIdComponent = overworld.increaseAndGetMapId();
                        overworld.putMapState(
                            mapIdComponent,
                            MapState.of(
                                onClient2.getX(),
                                onClient2.getZ(),
                                (byte) 0,
                                false,
                                false,
                                overworld.getRegistryKey()));
                        itemStack.set(DataComponentTypes.MAP_ID, mapIdComponent);
                      }

                      itemFrameEntity.setHeldItemStack(itemStack);
                      itemFrameEntity.setRotation(index);
                      overworld.spawnEntity(itemFrameEntity);
                      intValues[index] = itemFrameEntity.getId();
                    }

                    return intValues;
                  });
      clientGameTestContext.waitTicks(30);
      testSingleplayerContext.getClientWorld().waitForChunksRender();
      clientGameTestContext.takeScreenshot("fake-loot-world-before");
      clientGameTestContext.runOnClient(
          minecraftClient4 -> {
            FakeLootDropModule fakeLootDropModule =
                ClientInitializer.getModuleManager().fakeLootDropModule;
            ProtectionPerformanceTest.ensureInitialized();

            fakeLootDropModule.itemReplacementsSetting.apply(
                List.of(
                    new ItemReplacementRule(Items.DIRT, Items.DIAMOND_BLOCK, true),
                    new ItemReplacementRule(Items.GRASS_BLOCK, Items.GILDED_BLACKSTONE, true),
                    new ItemReplacementRule(Items.DIAMOND_BLOCK, Items.GOLD_BLOCK, true),
                    new ItemReplacementRule(Items.COBBLESTONE, Items.CHEST, true),
                    new ItemReplacementRule(Items.SAND, Items.ELYTRA, true),
                    new ItemReplacementRule(Items.GRAVEL, Items.AMETHYST_BLOCK, true),
                    new ItemReplacementRule(Items.CHEST, Items.GOLD_BLOCK, true),
                    new ItemReplacementRule(Items.FILLED_MAP, Items.EMERALD, true)));
            fakeLootDropModule.setEnabled(true);
            ItemStack heldItemStack =
                ((ItemFrameEntity) minecraftClient4.world.getEntityById(onServer[2]))
                    .getHeldItemStack();
            minecraftClient4.world.putClientsideMapState(
                heldItemStack.get(DataComponentTypes.MAP_ID),
                MapState.of((byte) 0, false, minecraftClient4.world.getRegistryKey()));

            for (int index2 = 0; index2 < onServer.length; index2++) {
              apply(minecraftClient4, onServer[index2], index2);
            }

            checkCompiledWorld(minecraftClient4, onClient2);
            apply(minecraftClient4, onClient2);
          });
      clientGameTestContext.waitTicks(10);
      testSingleplayerContext.getClientWorld().waitForChunksRender();
      clientGameTestContext.takeScreenshot("fake-loot-world-existing-blocks-and-frames");
      clientGameTestContext.runOnClient(
          minecraftClient5 -> {
            ClientInitializer.getModuleManager()
                .fakeLootDropModule
                .itemReplacementsSetting
                .put(Items.GRAVEL, Items.EMERALD_BLOCK);
          });
      clientGameTestContext.waitTicks(5);
      testSingleplayerContext.getClientWorld().waitForChunksRender();
      clientGameTestContext.takeScreenshot("fake-loot-world-rule-changed");
      testSingleplayerContext.getServer().runCommand("gamemode creative @a");
      testSingleplayerContext
          .getServer()
          .runOnServer(
              minecraftServer2 -> {
                ServerPlayerEntity first =
                    minecraftServer2.getPlayerManager().getPlayerList().getFirst();
                first.getInventory().setStack(0, new ItemStack(Items.SAND, 16));
                first.getInventory().setSelectedSlot(0);
                first.playerScreenHandler.sendContentUpdates();
              });
      clientGameTestContext.waitTicks(10);
      clientGameTestContext.runOnClient(
          minecraftClient6 -> {
            minecraftClient6.player.getInventory().setSelectedSlot(0);
            apply(
                minecraftClient6.player.getMainHandStack().isOf(Items.SAND),
                "server supplied the real placement item");
            BlockPos blockPos2 = onClient2.add(0, -1, -2);
            minecraftClient6.crosshairTarget =
                new BlockHitResult(
                    Vec3d.ofCenter(blockPos2).add(0.0, 0.5, 0.0), Direction.UP, blockPos2, false);
            ((MinecraftClientAccessor) (Object) (minecraftClient6)).invokeDoItemUse();
            apply(
                ClientInitializer.getModuleManager()
                    .fakeLootDropModule
                    .equipmentPreview
                    .getItemStack(EquipmentSlot.CHEST)
                    .isEmpty(),
                "aimed placement does not equip the fake elytra");
          });
      clientGameTestContext.waitTicks(10);
      testSingleplayerContext
          .getServer()
          .runOnServer(
              minecraftServer3 -> {
                apply(
                    minecraftServer3
                        .getOverworld()
                        .getBlockState(onClient2.add(0, 0, -2))
                        .isOf(Blocks.SAND),
                    "use-key really places the source block on the server");
              });
      testSingleplayerContext.getClientWorld().waitForChunksRender();
      clientGameTestContext.takeScreenshot("fake-loot-world-new-placement");
      int onServer2 =
          testSingleplayerContext
              .getServer()
              .computeOnServer(
                  minecraftServer4 -> {
                    ServerWorld overworld2 = minecraftServer4.getOverworld();
                    BlockPos blockPos3 = onClient2.add(1, 1, -2);
                    overworld2.setBlockState(blockPos3.south(), Blocks.STONE.getDefaultState());
                    ItemFrameEntity itemFrameEntity2 =
                        new ItemFrameEntity(overworld2, blockPos3, Direction.NORTH);
                    overworld2.spawnEntity(itemFrameEntity2);
                    return itemFrameEntity2.getId();
                  });
      clientGameTestContext.waitTicks(10);
      clientGameTestContext.runOnClient(
          minecraftClient7 -> {
            ItemFrameEntity itemFrameEntity3 =
                (ItemFrameEntity) minecraftClient7.world.getEntityById(onServer2);
            minecraftClient7.crosshairTarget = new EntityHitResult(itemFrameEntity3);
            ((MinecraftClientAccessor) (Object) (minecraftClient7)).invokeDoItemUse();
          });
      clientGameTestContext.waitTicks(10);
      testSingleplayerContext
          .getServer()
          .runOnServer(
              minecraftServer5 -> {
                apply(
                    ((ItemFrameEntity) minecraftServer5.getOverworld().getEntityById(onServer2))
                        .getHeldItemStack()
                        .isOf(Items.SAND),
                    "use-key inserts the real item into the frame on the server");
              });
      clientGameTestContext.runOnClient(
          minecraftClient8 -> {
            apply(minecraftClient8, onServer2, 1);
          });
      clientGameTestContext.runOnClient(
          minecraftClient9 -> {
            ClientInitializer.getModuleManager().fakeLootDropModule.setEnabled(false);
          });
      clientGameTestContext.waitTicks(5);
      testSingleplayerContext.getClientWorld().waitForChunksRender();
      clientGameTestContext.runOnClient(
          minecraftClient10 -> {
            ItemFrameEntity itemFrameEntity4 =
                (ItemFrameEntity) minecraftClient10.world.getEntityById(onServer[2]);
            ItemFrameEntityRenderState itemFrameEntityRenderState =
                (ItemFrameEntityRenderState)
                    minecraftClient10
                        .getEntityRenderDispatcher()
                        .getAndUpdateRenderState(itemFrameEntity4, 1.0F);
            apply(
                itemFrameEntityRenderState.mapId != null,
                "disabling restores the original framed map");
            apply(
                minecraftClient10.world.getBlockState(onClient2).isOf(Blocks.SAND),
                "existing world blocks remain real");
          });
      clientGameTestContext.takeScreenshot("fake-loot-world-disabled");
      ClientInitializer.logger.info(
          "Fake Loot world verified: existing terrain, normal/glow/map frames, rule rebuilds, real"
              + " placement and frame insertion, disable restore");
    } finally {
      clientGameTestContext.runOnClient(
          minecraftClient11 -> {
            minecraftClient11.options.hudHidden = false;
            ClientInitializer.getClientConfig().apply(onClient);
          });
    }
  }

  public static void apply(MinecraftClient minecraftClient, int intValue, int intValue2) {
    ProtectionPerformanceTest.ensureInitialized();

    ItemFrameEntity itemFrameEntity =
        (ItemFrameEntity) minecraftClient.world.getEntityById(intValue);
    apply(itemFrameEntity != null, "server frame arrived on the client");
    ItemStack itemStack = itemFrameEntity.getHeldItemStack().copy();
    ItemFrameEntityRenderState itemFrameEntityRenderState =
        (ItemFrameEntityRenderState)
            minecraftClient
                .getEntityRenderDispatcher()
                .getAndUpdateRenderState(itemFrameEntity, 1.0F);
    ItemRenderState itemRenderState = new ItemRenderState();
    ItemStack itemStack2 =
        new ItemStack(
            intValue2 == 0 ? Items.DIAMOND_BLOCK : (intValue2 == 1 ? Items.ELYTRA : Items.EMERALD));
    minecraftClient
        .getItemModelManager()
        .update(
            itemRenderState, itemStack2, ItemDisplayContext.FIXED, minecraftClient.world, null, 0);
    apply(
        !itemFrameEntityRenderState.itemRenderState.isEmpty()
            && itemFrameEntityRenderState.itemRenderState.getParticleSprite(Random.create(7L))
                == itemRenderState.getParticleSprite(Random.create(7L)),
        "frame uses the target model exactly once");
    apply(itemFrameEntityRenderState.mapId == null, "a real map cannot cover up the fake item");
    apply(
        ItemStack.areEqual(itemStack, itemFrameEntity.getHeldItemStack()),
        "frame contents remain unchanged");
    apply(
        itemFrameEntityRenderState.rotation == itemFrameEntity.getRotation(),
        "frame rotation is preserved");
    apply(
        itemFrameEntityRenderState.glow
            == (itemFrameEntity.getType() == EntityType.GLOW_ITEM_FRAME),
        "frame glow is preserved");
  }

  public static void checkCompiledWorld(MinecraftClient minecraftClient, BlockPos blockPos) {
    ProtectionPerformanceTest.ensureInitialized();

    BlockPos blockPos2 = blockPos.add(-2, 0, 0);
    ChunkSectionPos chunkSectionPos = ChunkSectionPos.from(blockPos2);
    ChunkRendererRegion chunkRendererRegion =
        new ChunkRendererRegionBuilder().build(minecraftClient.world, chunkSectionPos.asLong());
    apply(
        chunkRendererRegion.getBlockState(blockPos.add(-4, 0, 0)).isOf(Blocks.DIAMOND_BLOCK),
        "existing dirt uses one replacement");
    apply(
        chunkRendererRegion.getBlockState(blockPos2).isOf(Blocks.CHEST),
        "existing cobblestone uses chest state");
    BlockEntityRenderState blockEntityRenderState = new BlockEntityRenderState();
    BlockEntityRenderState.updateBlockEntityRenderState(
        chunkRendererRegion.getBlockEntity(blockPos2), blockEntityRenderState, null);
    apply(
        LightmapTextureManager.getSkyLightCoordinates(blockEntityRenderState.lightmapCoordinates)
            > 0,
        "fake chest receives daylight instead of the solid source block's dark interior");
    apply(
        chunkRendererRegion.getBlockState(blockPos).isAir(),
        "placed elytra replaces the source block mesh");

    try (BlockBufferAllocatorStorage blockBufferAllocatorStorage =
        new BlockBufferAllocatorStorage()) {
      RenderData renderData =
          new SectionBuilder(
                  minecraftClient.getBlockRenderManager(),
                  minecraftClient.getBlockEntityRenderDispatcher())
              .build(
                  chunkSectionPos,
                  chunkRendererRegion,
                  VertexSorter.BY_DISTANCE,
                  blockBufferAllocatorStorage);

      try {
        apply(!renderData.buffers.isEmpty(), "actual chunk compiler produces terrain geometry");
        apply(
            renderData.blockEntities.stream()
                .anyMatch(
                    blockEntity -> {
                      ProtectionPerformanceTest.ensureInitialized();
                      return blockEntity.getPos().equals(blockPos2)
                          && blockEntity.getCachedState().isOf(Blocks.CHEST);
                    }),
            "actual chunk compiler includes the fake chest renderer");
        apply(
            renderData.blockEntities.stream()
                .noneMatch(
                    blockEntity2 -> {
                      return blockEntity2.getPos().equals(blockPos.add(4, 0, 0));
                    }),
            "the disguised real chest no longer submits its original model");
      } finally {
        renderData.close();
      }
    }
  }

  public static void apply(MinecraftClient minecraftClient, BlockPos blockPos) {
    ProtectionPerformanceTest.ensureInitialized();
    if (FabricLoader.getInstance().isModLoaded("sodium")) {
      try {
        Class type = Class.forName("net.caffeinemc.mods.sodium.client.world.LevelSlice");

        Class type2 =
            Class.forName("net.caffeinemc.mods.sodium.client.world.cloned.ClonedChunkSectionCache");

        Class type3 =
            Class.forName("net.caffeinemc.mods.sodium.client.world.cloned.ChunkRenderContext");
        Object value = type2.getConstructor(World.class).newInstance(minecraftClient.world);
        Object value2 = type.getConstructor(ClientWorld.class).newInstance(minecraftClient.world);
        Object value3 =
            type.getMethod("prepare", World.class, ChunkSectionPos.class, type2)
                .invoke(
                    null,
                    minecraftClient.world,
                    ChunkSectionPos.from(blockPos.add(-2, 0, 0)),
                    value);
        type.getMethod("copyData", type3).invoke(value2, value3);
        Method method = type.getMethod("getBlockState", int.class, int.class, int.class);
        BlockPos blockPos2 = blockPos.add(0, -1, -1);
        apply(
            minecraftClient.world.getBlockState(blockPos2).isOf(Blocks.GRASS_BLOCK),
            "original ground is grass");
        apply(
            ((BlockState)
                    method.invoke(value2, blockPos2.getX(), blockPos2.getY(), blockPos2.getZ()))
                .isOf(Blocks.GILDED_BLACKSTONE),
            "Sodium's terrain source replaces existing grass with gilded blackstone");
        BlockPos blockPos3 = blockPos.add(-4, 0, 0);
        apply(
            ((BlockState)
                    method.invoke(value2, blockPos3.getX(), blockPos3.getY(), blockPos3.getZ()))
                .isOf(Blocks.DIAMOND_BLOCK),
            "Sodium replaces each block exactly once");
        BlockPos blockPos4 = blockPos.add(-2, 0, 0);
        BlockEntity blockEntity =
            (BlockEntity)
                type.getMethod("getBlockEntity", int.class, int.class, int.class)
                    .invoke(value2, blockPos4.getX(), blockPos4.getY(), blockPos4.getZ());
        apply(
            blockEntity != null && blockEntity.getCachedState().isOf(Blocks.CHEST),
            "Sodium includes fake block entities");
        type.getMethod("reset").invoke(value2);
      } catch (ReflectiveOperationException failure) {
        throw new AssertionError("Sodium terrain integration failed", failure);
      }
    }
  }

  public static void apply(boolean flag, String text) {
    if (!flag) {
      throw new AssertionError("Fake Loot world: " + text);
    }
  }
}
