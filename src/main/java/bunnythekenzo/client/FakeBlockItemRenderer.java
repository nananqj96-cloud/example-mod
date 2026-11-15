package bunnythekenzo.client;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.render.state.WorldRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public final class FakeBlockItemRenderer {
  public static final ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
  public static World world;
  public static Frustum frustum;

  public static void setFrustum(Frustum frustum2) {
    frustum = frustum2;
  }

  public static void apply(World world2, BlockPos blockPos, Item item) {
    concurrentHashMap.put(blockPos.toImmutable(), new ItemStackData(world2, item));
  }

  public static void apply(BlockPos blockPos) {
    concurrentHashMap.remove(blockPos);
  }

  public static void update() {
    concurrentHashMap.clear();
    frustum = null;
  }

  public static void checkLevel() {
    ClientWorld world2 = MinecraftClient.getInstance().world;
    if (world != world2) {
      world = world2;
      concurrentHashMap
          .entrySet()
          .removeIf(
              value -> {
                return ((ItemStackData) ((java.util.Map.Entry) (value)).getValue()).world()
                    != world2;
              });
    }
  }

  public static void submit(
      MatrixStack matrixStack,
      WorldRenderState worldRenderState,
      OrderedRenderCommandQueue orderedRenderCommandQueue) {
    MinecraftClient instance = MinecraftClient.getInstance();
    FakeLootDropModule fakeLootDropModule = FakeLootDropModule.getFakeLootDropModule();
    if (fakeLootDropModule != null && fakeLootDropModule.isEnabled() && instance.world != null) {
      Vec3d pos = worldRenderState.cameraRenderState.pos;
      double doubleValue = instance.options.getClampedViewDistance() * 16.0;
      HashMap hashMap = new HashMap();
      concurrentHashMap
          .entrySet()
          .removeIf(
              value -> {
                return ((ItemStackData) ((java.util.Map.Entry) (value)).getValue()).world()
                        != instance.world
                    || !instance.world.isChunkLoaded(
                        ((BlockPos) ((java.util.Map.Entry) (value)).getKey()).getX() >> 4,
                        ((BlockPos) ((java.util.Map.Entry) (value)).getKey()).getZ() >> 4);
              });

      for (Entry entry :
          (java.lang.Iterable<Entry>) (java.lang.Iterable<?>) (concurrentHashMap.entrySet())) {
        BlockPos blockPos = (BlockPos) entry.getKey();
        double squaredDistance = blockPos.getSquaredDistance(pos);
        if (!(squaredDistance > doubleValue * doubleValue)
            && (frustum == null || frustum.isVisible(new Box(blockPos)))) {
          BlockState blockState = instance.world.getBlockState(blockPos);
          Item item = fakeLootDropModule.getItem(blockState);
          if (item != null
              && blockState.getBlock().asItem() == ((ItemStackData) entry.getValue()).item()) {
            Item item2 = ((ItemStackData) entry.getValue()).item();
            ItemRenderState itemRenderState =
                (net.minecraft.client.render.item.ItemRenderState)
                    (hashMap.computeIfAbsent(
                        item2,
                        (java.util.function.Function<
                                net.minecraft.item.Item,
                                net.minecraft.client.render.item.ItemRenderState>)
                            (item3 -> {
                              ItemRenderState itemRenderState2 = new ItemRenderState();
                              instance
                                  .getItemModelManager()
                                  .clearAndUpdate(
                                      itemRenderState2,
                                      new ItemStack(item3),
                                      ItemDisplayContext.FIXED,
                                      instance.world,
                                      null,
                                      0);
                              return itemRenderState2;
                            })));
            matrixStack.push();
            matrixStack.translate(
                blockPos.getX() + 0.5 - pos.x,
                blockPos.getY() + 0.5 - pos.y,
                blockPos.getZ() + 0.5 - pos.z);
            if (!itemRenderState.isSideLit()) {
              matrixStack.multiply(worldRenderState.cameraRenderState.orientation);
            }

            int lightmapCoordinates =
                WorldRenderer.getLightmapCoordinates(instance.world, blockPos.up());
            itemRenderState.render(
                matrixStack,
                orderedRenderCommandQueue,
                lightmapCoordinates,
                OverlayTexture.DEFAULT_UV,
                0);
            matrixStack.pop();
          } else {
            concurrentHashMap.remove(blockPos, entry.getValue());
          }
        }
      }
    }
  }
}
