package bunnythekenzo.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.render.Camera;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector4f;
import bunnythekenzo.client.render.OverlayRenderer;

public final class FramebufferSupport {
  public static final Matrix4f matrix4f = new Matrix4f();
  public static final Matrix4f matrix4f2 = new Matrix4f();
  public static final Matrix4f matrix4f3 = new Matrix4f();
  public static final Vector4f vector4f = new Vector4f();
  public static Vec3d vec3d = Vec3d.ZERO;
  public static int count;
  public static int count2;
  public static float amount;
  public static boolean enabled;

  public static void render(Matrix4f matrix4f4, Matrix4f matrix4f5, float amount2) {
    MinecraftClient instance = MinecraftClient.getInstance();
    Camera camera = instance.gameRenderer.getCamera();
    if (camera != null && matrix4f4 != null && matrix4f5 != null) {
      matrix4f2.set(matrix4f4);
      matrix4f3.set(matrix4f5);
      matrix4f.set(matrix4f3).mul(matrix4f2);
      vec3d = camera.getCameraPos();
      Framebuffer framebuffer = instance.getFramebuffer();
      count = framebuffer.textureWidth;
      count2 = framebuffer.textureHeight;
      amount = amount2;
      enabled = true;
    } else {
      enabled = false;
    }
  }

  public static void update() {
    enabled = false;
  }

  public static boolean isEnabled() {
    return enabled;
  }

  public static float getAmount() {
    return amount;
  }

  public static Vec3d getVec3d() {
    return vec3d;
  }

  public static Matrix4fc getMatrix4f2() {
    return matrix4f2;
  }

  public static Matrix4fc getMatrix4f3() {
    return matrix4f3;
  }

  public static float computeFloat() {
    return matrix4f3.m00();
  }

  public static float computeFloat2() {
    return matrix4f3.m11();
  }

  public static float[] getAmountArray(
      double doubleValue, double doubleValue2, double doubleValue3) {
    float[] floatValues = getAmountArray2(doubleValue, doubleValue2, doubleValue3);
    if (floatValues == null) {
      return null;
    }

    float value = OverlayRenderer.computeFloat();
    return new float[] {floatValues[0] / value, floatValues[1] / value};
  }

  public static float[] getAmountArray2(
      double doubleValue, double doubleValue2, double doubleValue3) {
    if (!enabled) {
      return null;
    }

    vector4f.set(
        (float) (doubleValue - vec3d.x),
        (float) (doubleValue2 - vec3d.y),
        (float) (doubleValue3 - vec3d.z),
        1.0F);
    matrix4f.transform(vector4f);
    if (vector4f.w <= 1.0E-4F) {
      return null;
    }

    float floatValue = vector4f.x / vector4f.w;
    float floatValue2 = vector4f.y / vector4f.w;
    float floatValue3 = (floatValue * 0.5F + 0.5F) * count;
    float floatValue4 = (1.0F - (floatValue2 * 0.5F + 0.5F)) * count2;
    return new float[] {floatValue3, floatValue4};
  }
}
