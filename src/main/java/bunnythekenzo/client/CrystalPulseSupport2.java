package bunnythekenzo.client;

import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.util.math.Vec3d;

public final class CrystalPulseSupport2 {
  public final EndCrystalEntity endCrystalEntity;
  public final double value;
  public Vec3d vec3d;
  public Vec3d vec3d2;
  public double value2 = Double.POSITIVE_INFINITY;
  public boolean enabled;

  public CrystalPulseSupport2(EndCrystalEntity endCrystalEntity2, double value3) {
    this.endCrystalEntity = endCrystalEntity2;
    this.value = value3;
    this.vec3d = this.vec3d2 = endCrystalEntity2.getEntityPos();
  }
}
