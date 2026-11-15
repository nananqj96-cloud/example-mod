package bunnythekenzo.client;

import java.util.Arrays;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public final class LumaSupport {
  public Vec3d vec3d;
  public Vec3d vec3d2;
  public float amount;
  public float amount2;
  public final Vec3d[] vec3dArray = new Vec3d[34];
  public int count;
  public int count2;

  public void apply(Vec3d vec3d22) {
    this.vec3d = this.vec3d2 = vec3d22;
    this.amount = this.amount2 = 0.0F;
    this.count = this.count2 = 0;
    Arrays.fill(this.vec3dArray, null);
    this.apply2(vec3d22);
  }

  public void apply2(Vec3d vec3d3) {
    this.count = (this.count + 1) % 34;
    this.vec3dArray[this.count] = vec3d3;
    this.count2 = Math.min(34, this.count2 + 1);
  }

  public void update() {
    this.vec3d = this.vec3d2 = null;
    Arrays.fill(this.vec3dArray, null);
    this.count = this.count2 = 0;
    this.amount = this.amount2 = 0.0F;
  }

  public Vec3d getVec3d(float floatValue) {
    FakeLootDropModule.ensureInitialized();
    return this.vec3d == null ? null : this.vec3d2.lerp(this.vec3d, floatValue);
  }

  public float computeFloat(float floatValue) {
    return MathHelper.lerp(floatValue, this.amount2, this.amount);
  }

  public int getCount2() {
    return this.count2;
  }

  public Vec3d getVec3d(int intValue) {
    return this.vec3dArray[Math.floorMod(this.count - intValue, 34)];
  }
}
