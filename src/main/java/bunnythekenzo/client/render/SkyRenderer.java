package bunnythekenzo.client.render;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.minecraft.block.enums.CameraSubmersionType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.world.dimension.DimensionType.Skybox;
import org.joml.Vector3fc;
import org.joml.Vector4f;
import bunnythekenzo.client.ArgbColors;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.SkyColorPalette;
import bunnythekenzo.client.SkySupport;
import bunnythekenzo.client.SkySupport10;
import bunnythekenzo.client.SkySupport11;
import bunnythekenzo.client.SkySupport13;
import bunnythekenzo.client.SkySupport14;
import bunnythekenzo.client.SkySupport17;
import bunnythekenzo.client.SkySupport18;
import bunnythekenzo.client.SkySupport2;
import bunnythekenzo.client.SkySupport21;
import bunnythekenzo.client.SkySupport22;
import bunnythekenzo.client.SkySupport25;
import bunnythekenzo.client.SkySupport3;
import bunnythekenzo.client.SkySupport5;
import bunnythekenzo.client.SkySupport6;
import bunnythekenzo.client.SkySupport7;
import bunnythekenzo.client.SkySupport8;
import bunnythekenzo.client.module.SkyModule;

public final class SkyRenderer {
  public static final float amount = (float) (Math.PI * 2);
  public static final float amount2 = 0.0052359877F;
  public static boolean enabled;
  public static boolean enabled2;
  public static float amount3;
  public static float amount4;
  public static double value;
  public static long timestamp;
  public static final float[] amountArray = new float[3];
  public static final float[] amountArray2 = new float[3];

  public static void register() {
    if (!enabled2) {
      enabled2 = true;
      SkyPipelines.update();
      WorldRenderEvents.END_MAIN.register(SkyRenderer::apply);
      ClientPlayConnectionEvents.JOIN.register(
          (clientPlayNetworkHandler, packetSender, minecraftClient) -> {
            SkySupport11.update();
          });
      ClientPlayConnectionEvents.DISCONNECT.register(
          (clientPlayNetworkHandler2, minecraftClient2) -> {
            SkySupport11.update();
          });
    }
  }

  public static void apply(float floatValue) {
    float floatValue2 = (float) Math.cos(0.14F);
    amountArray2[0] = floatValue2 * (float) Math.cos(floatValue);
    amountArray2[1] = (float) Math.sin(0.14F);
    amountArray2[2] = floatValue2 * (float) Math.sin(floatValue);
  }

  public static float[] getAmountArray() {
    return SkySupport17.test(amountArray) ? getAmountArray(amountArray) : null;
  }

  public static float[] getAmountArray2() {
    if (!SkySupport8.test(amountArray)) {
      return null;
    }

    SkySupport6.render(amountArray, (float) Math.cos(amount4), (float) Math.sin(amount4));
    return getAmountArray(amountArray);
  }

  public static void update() {
    SkySupport8.update();
  }

  public static float[] getAmountArray3() {
    SkySupport5.apply(amountArray);
    return getAmountArray(amountArray);
  }

  public static float[] getAmountArray4() {
    SkySupport3.apply(amountArray);
    return getAmountArray(amountArray);
  }

  public static float[] getAmountArray5() {
    SkySupport25.apply(amountArray);
    return getAmountArray(amountArray);
  }

  public static float[] getAmountArray6() {
    SkySupport21.apply(amountArray);
    return getAmountArray(amountArray);
  }

  public static float[] getAmountArray7() {
    SkySupport14.apply(amountArray);
    return getAmountArray(amountArray);
  }

  public static float[] getAmountArray8() {
    return SkySupport2.test(amountArray) ? getAmountArray(amountArray) : null;
  }

  public static float[] getAmountArray(float[] floatValues) {
    float floatValue = (float) Math.toDegrees(-Math.asin(Math.clamp(floatValues[1], -1.0F, 1.0F)));
    float floatValue2 = (float) Math.toDegrees(Math.atan2(-floatValues[0], floatValues[2]));
    return new float[] {floatValue2, floatValue};
  }

  public static void update2() {
    SkySupport17.update3();
  }

  public static void update3() {
    SkySupport17.update();
  }

  public static void update4() {
    SkySupport17.update2();
  }

  public static void update5() {
    SkySupport17.update4();
  }

  public static float[] getAmountArray9() {
    return SkySupport17.getAmountArray();
  }

  public static void update6() {
    SkySupport2.update();
  }

  public static boolean test() {
    if (!enabled && SkyPipelines.test() && SkyModule.test()) {
      ClientWorld world = MinecraftClient.getInstance().world;
      return world != null && world.getDimension().skybox() == Skybox.OVERWORLD;
    } else {
      return false;
    }
  }

  public static void update7() {
    try {
      MinecraftClient instance = MinecraftClient.getInstance();
      ClientWorld world = instance.world;
      if (world == null) {
        return;
      }

      Vector3fc horizontalPlane = instance.gameRenderer.getCamera().getHorizontalPlane();
      SkySupport7.render(horizontalPlane.x(), horizontalPlane.y(), horizontalPlane.z());
      float floatValue = computeFloat();
      float tickProgress = instance.getRenderTickCounter().getTickProgress(false);
      float floatValue2 = world.getRainGradient(tickProgress) * 0.85F;
      Immediate immediate = SkyPipelines.getImmediate();
      SkySupport.apply(immediate);
      if (SkyModule.test9()) {
        SkySupport18.render(immediate, amount4, floatValue2);
      }

      if (SkyModule.test10()) {
        SkySupport10.render(immediate, amount4, amount3, floatValue2);
      }

      if (SkyModule.test7()) {
        SkySupport13.render(immediate, amount4, floatValue2);
      }

      if (SkyModule.test5()) {
        SkySupport22.render(immediate, amount4, amount3, floatValue2);
      }

      apply(amount4);
      if (SkyModule.test13()) {
        SkySupport5.render(immediate, value, amountArray2, floatValue2);
      }

      if (SkyModule.test14()) {
        SkySupport3.render(immediate, value, amountArray2, floatValue2);
      }

      if (SkyModule.test15()) {
        SkySupport25.render(immediate, value, amountArray2, floatValue2);
      }

      if (SkyModule.test17()) {
        SkySupport21.render(immediate, value, amountArray2, floatValue2);
      }

      if (SkyModule.test18()) {
        SkySupport14.render(immediate, value, amountArray2, floatValue2);
      }

      if (SkyModule.test16()) {
        SkySupport8.render(immediate, floatValue, amount4, floatValue2);
      }

      if (SkyModule.test11()) {
        SkySupport2.render(immediate, floatValue, SkyModule.computeFloat10(), floatValue2);
      }

      if (SkyModule.test12()) {
        SkySupport17.render(immediate, floatValue * SkyModule.computeFloat11(), floatValue2);
      }
    } catch (Throwable failure) {
      apply("sky", failure);
    }
  }

  public static float computeFloat() {
    long timestamp2 = System.nanoTime();
    float floatValue =
        timestamp == 0L ? 0.0F : (float) Math.min((timestamp2 - timestamp) / 1.0E9, 0.1);
    timestamp = timestamp2;
    if (MinecraftClient.getInstance().isPaused()) {
      floatValue = 0.0F;
    }

    value = value + floatValue * SkyModule.computeFloat11();
    amount3 += floatValue;
    if (amount3 > 3600.0F) {
      amount3 -= 3600.0F;
    }

    amount4 = amount4 + 0.0052359877F * SkyModule.computeFloat11() * floatValue;
    if (amount4 > (float) (Math.PI * 2)) {
      amount4 -= (float) (Math.PI * 2);
    }

    return floatValue;
  }

  public static void apply(WorldRenderContext worldRenderContext) {
    if (!enabled && SkyPipelines.test() && SkyModule.test4()) {
      MinecraftClient instance = MinecraftClient.getInstance();
      ClientWorld world = instance.world;
      if (world != null) {
        try {
          Camera camera = instance.gameRenderer.getCamera();
          if (!camera.isReady()) {
            return;
          }

          SkySupport11.apply(SkyPipelines.getImmediate2(), camera, world);
        } catch (Throwable failure) {
          apply("motes", failure);
        }
      }
    }
  }

  public static void apply(Vector4f vector4f, Camera camera) {
    if (test() && camera.getSubmersionType() == CameraSubmersionType.NONE) {
      float float4 = SkyModule.computeFloat4();
      if (!(float4 <= 0.001F)) {
        int value2 = SkyColorPalette.computeInt(0.045F);
        vector4f.x = vector4f.x + (ArgbColors.red(value2) / 255.0F - vector4f.x) * float4;
        vector4f.y = vector4f.y + (ArgbColors.green(value2) / 255.0F - vector4f.y) * float4;
        vector4f.z = vector4f.z + (ArgbColors.blue(value2) / 255.0F - vector4f.z) * float4;
      }
    }
  }

  public static void apply(String text, Throwable throwable) {
    enabled = true;
    ClientInitializer.logger.error(
        "bunnythekenzo Sky ({}) crashed; disabling it for this session", text, throwable);
  }
}
