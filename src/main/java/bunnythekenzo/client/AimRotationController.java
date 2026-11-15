package bunnythekenzo.client;

import java.util.Random;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public final class AimRotationController {
  public double yawVelocity;
  public double pitchVelocity;
  public final Random random = new Random();

  public void reset() {
    this.yawVelocity = this.pitchVelocity = 0.0;
  }

  public static double[] getValueArray(MinecraftClient minecraftClient, Vec3d vec3d, boolean flag) {
    Vec3d vec3d2 = vec3d.subtract(minecraftClient.player.getEyePos());
    float floatValue = (float) Math.toDegrees(Math.atan2(vec3d2.z, vec3d2.x)) - 90.0F;
    float floatValue2 = (float) (-Math.toDegrees(Math.atan2(vec3d2.y, vec3d2.horizontalLength())));
    return new double[] {
      MathHelper.wrapDegrees(floatValue - minecraftClient.player.getYaw()),
      flag ? floatValue2 - minecraftClient.player.getPitch() : 0.0
    };
  }

  public static boolean test(MinecraftClient minecraftClient, Vec3d vec3d) {
    double[] doubleValues = getValueArray(minecraftClient, vec3d, true);
    return Math.abs(doubleValues[0]) < 0.35 && Math.abs(doubleValues[1]) < 0.35;
  }

  public double[] getValueArray(
      MinecraftClient minecraftClient,
      Vec3d vec3d,
      double doubleValue,
      double doubleValue2,
      boolean flag,
      double doubleValue3,
      double doubleValue4,
      double doubleValue5) {
    if (Double.isFinite(doubleValue3)
        && !(doubleValue3 <= 0.0)
        && Double.isFinite(doubleValue4)
        && Double.isFinite(doubleValue5)) {
      double[] doubleValues = getValueArray(minecraftClient, vec3d, flag);
      double doubleValue6 = doubleValues[0];
      double doubleValue7 = doubleValues[1];
      if (Math.abs(doubleValue6) < 0.35 && Math.abs(doubleValue7) < 0.35) {
        return null;
      }

      double doubleValue8 = 1.0 - Math.exp(-(11.0 - doubleValue2) * 3.0 * doubleValue3);
      double doubleValue9 = (40.0 + doubleValue * 80.0) * doubleValue3;
      double doubleValue10 =
          MathHelper.clamp(
              doubleValue6 * doubleValue8 * this.computeDouble(), -doubleValue9, doubleValue9);
      double doubleValue11 =
          MathHelper.clamp(
              doubleValue7 * doubleValue8 * 0.85 * this.computeDouble(),
              -doubleValue9,
              doubleValue9);
      double doubleValue12 = 1.0 / (1.0 + (Math.abs(doubleValue4) + Math.abs(doubleValue5)) * 0.02);
      doubleValue10 *= doubleValue12;
      doubleValue11 *= doubleValue12;
      double doubleValue13 = minecraftClient.options.getMouseSensitivity().getValue() * 0.6 + 0.2;
      double doubleValue14 = doubleValue13 * doubleValue13 * doubleValue13 * 1.2;
      if (doubleValue14 <= 1.0E-6) {
        return null;
      }

      double doubleValue15 = doubleValue10 / doubleValue14;
      double doubleValue16 = doubleValue11 / doubleValue14;
      if (minecraftClient.options.getInvertMouseX().getValue()) {
        doubleValue15 = -doubleValue15;
      }

      if (minecraftClient.options.getInvertMouseY().getValue()) {
        doubleValue16 = -doubleValue16;
      }

      this.yawVelocity += doubleValue15;
      this.pitchVelocity += doubleValue16;
      long longValue = (long) this.yawVelocity;
      long longValue2 = (long) this.pitchVelocity;
      this.yawVelocity -= longValue;
      this.pitchVelocity -= longValue2;
      return longValue == 0L && longValue2 == 0L ? null : new double[] {longValue, longValue2};
    } else {
      this.reset();
      return null;
    }
  }

  public double computeDouble() {
    return 0.9 + this.random.nextDouble() * 0.2;
  }
}
