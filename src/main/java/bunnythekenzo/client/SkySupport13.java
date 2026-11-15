package bunnythekenzo.client;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import bunnythekenzo.client.module.SkyModule;
import bunnythekenzo.client.render.SkyPipelines;

public final class SkySupport13 {
  public static final float amount = -0.3F;
  public static final float amount2 = -0.16F;

  public static void render(Immediate immediate, float floatValue, float floatValue2) {
    float floatValue3 = (1.0F - floatValue2) * (0.35F + 0.65F * SkyModule.computeFloat2());
    if (!(floatValue3 <= 0.01F)) {
      int value = SkyColorPalette.computeInt();
      VertexConsumer buffer = immediate.getBuffer(SkyPipelines.getRenderLayer2());
      render(buffer, floatValue, -0.3F, 0.46F, 0.2F * floatValue3, value);
      render(buffer, floatValue, -0.16F, 0.19F, 0.34F * floatValue3, value);
      immediate.draw();
    }
  }

  public static void render(
      VertexConsumer vertexConsumer,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      int intValue) {
    float floatValue5 = (float) Math.sin(floatValue2);
    float floatValue6 = (float) Math.cos(floatValue2);
    float floatValue7 = floatValue6 * (float) Math.cos(floatValue);
    float floatValue8 = floatValue6 * (float) Math.sin(floatValue);
    SkySupport6.render(
        vertexConsumer,
        floatValue7,
        floatValue5,
        floatValue8,
        100.0F * floatValue3,
        intValue,
        floatValue4);
  }
}
