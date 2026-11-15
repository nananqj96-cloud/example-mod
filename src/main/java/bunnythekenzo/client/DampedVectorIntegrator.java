package bunnythekenzo.client;

import org.joml.Vector3d;

public final class DampedVectorIntegrator {
  public static void render(
      Vector3d vector3d,
      Vector3d vector3d2,
      double doubleValue,
      double doubleValue2,
      double doubleValue3,
      double doubleValue4,
      double doubleValue5) {
    if (Double.isFinite(doubleValue5)
        && !(doubleValue5 <= 0.0)
        && Double.isFinite(doubleValue4)
        && !(doubleValue4 <= 0.0)) {
      double doubleValue6 = Math.exp(-doubleValue4 * doubleValue5);
      double doubleValue7 = -Math.expm1(-doubleValue4 * doubleValue5) / doubleValue4;
      vector3d.add(
          doubleValue * doubleValue5 + (vector3d2.x - doubleValue) * doubleValue7,
          doubleValue2 * doubleValue5 + (vector3d2.y - doubleValue2) * doubleValue7,
          doubleValue3 * doubleValue5 + (vector3d2.z - doubleValue3) * doubleValue7);
      vector3d2.set(
          doubleValue + (vector3d2.x - doubleValue) * doubleValue6,
          doubleValue2 + (vector3d2.y - doubleValue2) * doubleValue6,
          doubleValue3 + (vector3d2.z - doubleValue3) * doubleValue6);
    }
  }
}
