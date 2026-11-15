package bunnythekenzo.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;

public final class EmoteConfettiParticle {
  public Vec3d vec3d;
  public Vec3d vec3d2;
  public final float amount;
  public final float amount2;
  public final float amount3;
  public float amount4;
  public float amount5;
  public float amount6;
  public float amount7;
  public int count;
  public int count2;
  public final int count3;
  public Vec3d vec3d3;
  public float amount8;
  public float amount9;
  public boolean enabled;

  public EmoteConfettiParticle(
      Vec3d vec3d22,
      Vec3d vec3d32,
      float amount10,
      float amount22,
      float amount32,
      float amount82,
      int count32) {
    this.vec3d = this.vec3d2 = vec3d22;
    this.vec3d3 = vec3d32;
    this.amount = amount10;
    this.amount2 = amount22;
    this.amount3 = amount32;
    this.amount8 = amount82;
    this.count3 = count32;
    this.amount4 = this.amount5 = amount32;
    this.amount6 = this.amount7 = amount32 * 0.6F;
  }

  public void apply(MinecraftClient minecraftClient) {
    this.vec3d = this.vec3d2;
    this.amount4 = this.amount5;
    this.amount6 = this.amount7;
    this.count++;
    if (this.enabled) {
      this.amount7 = this.amount7 + (this.amount9 - this.amount7) * 0.35F;
      this.amount8 *= 0.6F;
      this.amount5 = this.amount5 + this.amount8;
    } else {
      this.vec3d3 =
          this.vec3d3
              .multiply(0.97, 0.985, 0.97)
              .add(
                  Math.sin(this.count * 0.45 + this.amount3) * 0.002,
                  -0.024,
                  Math.cos(this.count * 0.37 + this.amount3) * 0.002);
      Vec3d vec3d22 = this.vec3d2.add(this.vec3d3);
      BlockHitResult blockHitResult =
          minecraftClient.world.raycast(
              new RaycastContext(
                  this.vec3d2,
                  vec3d22,
                  ShapeType.COLLIDER,
                  FluidHandling.NONE,
                  minecraftClient.player));
      if (blockHitResult.getType() == Type.BLOCK) {
        Vec3d vec3d4 = Vec3d.of(blockHitResult.getSide().getVector());
        this.vec3d2 = blockHitResult.getPos().add(vec3d4.multiply(0.015));
        double doubleValue = this.vec3d3.dotProduct(vec3d4);
        this.vec3d3 = this.vec3d3.subtract(vec3d4.multiply(1.42 * doubleValue)).multiply(0.68);
        this.amount8 *= -0.62F;
        this.count2++;
        if (vec3d4.y > 0.5 && (Math.abs(doubleValue) < 0.06 || this.count2 >= 4)) {
          this.enabled = true;
          this.vec3d3 = Vec3d.ZERO;
          this.amount9 =
              (float)
                  ((Math.PI / 2) + Math.rint((this.amount7 - (Math.PI / 2)) / Math.PI) * Math.PI);
        }
      } else {
        this.vec3d2 = vec3d22;
      }

      if (!this.enabled) {
        this.amount5 = this.amount5 + this.amount8;
        this.amount7 =
            this.amount7 + (0.16F + 0.07F * (float) Math.sin(this.count * 0.31 + this.amount3));
      }
    }
  }

  public float computeFloat(float floatValue) {
    float floatValue2 = (this.count3 - this.count - floatValue) / 20.0F;
    float floatValue3 = Math.clamp(floatValue2, 0.0F, 1.0F);
    return floatValue3 * floatValue3 * (3.0F - 2.0F * floatValue3);
  }
}
