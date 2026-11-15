package bunnythekenzo.client;

import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import bunnythekenzo.client.module.ChunkFinderModule;

public final class ChunkFinderRenderer {
  public static final float amount = 55.0F;
  public static final int count = 190;

  public static void apply(
      Immediate immediate,
      MatrixStack matrixStack,
      Vec3d vec3d,
      ChunkFinderModule chunkFinderModule) {
    if (!chunkFinderModule.getSet3().isEmpty()) {
      int count2 = ClientInitializer.getThemeManager().getColorPalette().getCount();
      int value = ArgbColors.withAlpha(ArgbColors.darken(count2, 0.58F), 190);
      int value2 = ArgbColors.withAlpha(count2, 255);

      for (long longValue :
          (java.lang.Iterable<Long>) (java.lang.Iterable<?>) (chunkFinderModule.getSet3())) {
        double doubleValue = ChunkPos.getPackedX(longValue) * 16.0;
        double doubleValue2 = ChunkPos.getPackedZ(longValue) * 16.0;
        double doubleValue3 = doubleValue + 16.0;
        double doubleValue4 = doubleValue2 + 16.0;
        double value3 =
            VertexConsumerSupport.computeDouble(55.0, doubleValue + 8.0, doubleValue2 + 8.0);
        VertexConsumerSupport.render(
            immediate,
            matrixStack,
            vec3d,
            doubleValue,
            doubleValue2,
            doubleValue3,
            doubleValue4,
            value3,
            value);
        VertexConsumerSupport.render(
            immediate,
            matrixStack,
            vec3d,
            doubleValue,
            doubleValue2,
            doubleValue3,
            doubleValue2,
            value3,
            value2,
            2.0F);
        VertexConsumerSupport.render(
            immediate,
            matrixStack,
            vec3d,
            doubleValue3,
            doubleValue2,
            doubleValue3,
            doubleValue4,
            value3,
            value2,
            2.0F);
        VertexConsumerSupport.render(
            immediate,
            matrixStack,
            vec3d,
            doubleValue3,
            doubleValue4,
            doubleValue,
            doubleValue4,
            value3,
            value2,
            2.0F);
        VertexConsumerSupport.render(
            immediate,
            matrixStack,
            vec3d,
            doubleValue,
            doubleValue4,
            doubleValue,
            doubleValue2,
            value3,
            value2,
            2.0F);
      }

      VertexConsumerSupport.apply(immediate);
    }
  }
}
