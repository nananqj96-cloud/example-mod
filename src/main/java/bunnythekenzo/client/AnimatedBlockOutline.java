package bunnythekenzo.client;

import java.util.List;
import net.minecraft.client.render.state.OutlineRenderState;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;

public final class AnimatedBlockOutline {
  public final BlockPos blockPos;
  public VoxelShape voxelShape;
  public List list;
  public Box box;
  public Box box2;
  public Direction direction = Direction.UP;
  public double value;

  public AnimatedBlockOutline(OutlineRenderState outlineRenderState) {
    this.blockPos = outlineRenderState.pos().toImmutable();
    this.apply(outlineRenderState, null);
  }

  public Box getBox() {
    return this.box;
  }

  public Box getBox2() {
    return this.box2;
  }

  public Direction getDirection() {
    return this.direction;
  }

  public double getValue() {
    return this.value;
  }

  public double computeDouble() {
    return 1.0 - Math.pow(1.0 - this.value, 3.0);
  }

  public void apply(OutlineRenderState outlineRenderState, BlockHitResult blockHitResult) {
    if (this.voxelShape != outlineRenderState.shape()) {
      this.voxelShape = outlineRenderState.shape();
      this.list = this.voxelShape.getBoundingBoxes();
      this.box = this.voxelShape.getBoundingBox();
      this.box2 = this.box;
    }

    if (blockHitResult != null && blockHitResult.getBlockPos().equals(this.blockPos)) {
      this.direction = blockHitResult.getSide();
      Vec3d vec3d =
          blockHitResult
              .getPos()
              .subtract(this.blockPos.getX(), this.blockPos.getY(), this.blockPos.getZ());
      double POSITIVE_INFINITY = Double.POSITIVE_INFINITY;

      for (Box box22 : (java.lang.Iterable<Box>) (java.lang.Iterable<?>) (this.list)) {
        double doubleValue = Math.clamp(vec3d.x, box22.minX, box22.maxX);
        double doubleValue2 = Math.clamp(vec3d.y, box22.minY, box22.maxY);
        double doubleValue3 = Math.clamp(vec3d.z, box22.minZ, box22.maxZ);
        switch (OutlineDirectionSwitchTable.countArray[this.direction.ordinal()]) {
          case 1:
            doubleValue2 = box22.minY;
            break;
          case 2:
            doubleValue2 = box22.maxY;
            break;
          case 3:
            doubleValue3 = box22.minZ;
            break;
          case 4:
            doubleValue3 = box22.maxZ;
            break;
          case 5:
            doubleValue = box22.minX;
            break;
          case 6:
            doubleValue = box22.maxX;
        }

        double doubleValue4 = vec3d.squaredDistanceTo(doubleValue, doubleValue2, doubleValue3);
        if (doubleValue4 < POSITIVE_INFINITY) {
          POSITIVE_INFINITY = doubleValue4;
          this.box2 = box22;
        }
      }
    }
  }
}
