package bunnythekenzo.client;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import bunnythekenzo.client.render.SkyPipelines;

public final class SkySupport10 {
  public static final int count = 3;
  public static final float[] amountArray = new float[] {0.62F, 2.94F, 4.78F};
  public static final float[] amountArray2 = new float[] {0.26F, -0.95F, 0.09F};
  public static final float[] amountArray3 = new float[] {0.15F, 0.22F, 0.115F};
  public static final float[] amountArray4 = new float[] {0.1F, 0.62F, 0.35F};
  public static final float[] amountArray5 = new float[] {0.0F, 2.1F, 4.3F};
  public static final float[] amountArray6 = new float[] {0.26F, 0.2F, 0.3F};
  public static final int count2 = -8762400;
  public static final float[] amountArray7 = new float[3];

  public static void render(
      Immediate immediate, float floatValue, float floatValue2, float floatValue3) {
    float floatValue4 = 1.0F - floatValue3;
    if (!(floatValue4 <= 0.01F)) {
      float floatValue5 = (float) Math.cos(floatValue);
      float floatValue6 = (float) Math.sin(floatValue);
      int value = SkyColorPalette.computeInt();
      VertexConsumer buffer = immediate.getBuffer(SkyPipelines.sky("nebula", true));

      for (int index = 0; index < 3; index++) {
        SkySupport18.render(amountArray[index], amountArray2[index], amountArray7);
        SkySupport6.render(amountArray7, floatValue5, floatValue6);
        float floatValue7 =
            1.0F + 0.11F * (float) Math.sin(floatValue2 * 0.105F + amountArray5[index]);
        float floatValue8 =
            0.66F + 0.34F * (float) Math.sin(floatValue2 * 0.071F + amountArray5[index] * 1.7F);
        float floatValue9 = amountArray3[index] * floatValue7 * 100.0F;
        int value2 = ArgbColors.lerp(value, -8762400, amountArray4[index]);
        SkySupport6.render(
            buffer,
            amountArray7[0],
            amountArray7[1],
            amountArray7[2],
            floatValue9,
            floatValue9,
            floatValue2 * 0.012F + amountArray5[index],
            value2,
            amountArray6[index] * floatValue8 * floatValue4);
      }

      immediate.draw();
    }
  }
}
