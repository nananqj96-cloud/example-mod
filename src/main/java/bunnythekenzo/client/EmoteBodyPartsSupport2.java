package bunnythekenzo.client;

import java.util.Set;
import net.minecraft.client.model.ModelPart.Cuboid;
import net.minecraft.client.model.ModelPart.Quad;
import net.minecraft.client.model.ModelPart.Vertex;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack.Entry;
import org.joml.Vector3f;

public final class EmoteBodyPartsSupport2 extends Cuboid {
  public EmoteBodyPartsSupport2(
      int intValue,
      int intValue2,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      float floatValue7,
      float floatValue8,
      float floatValue9,
      boolean flag,
      float floatValue10,
      float floatValue11,
      Set set) {
    super(
        intValue,
        intValue2,
        floatValue,
        floatValue2,
        floatValue3,
        floatValue4,
        floatValue5,
        floatValue6,
        floatValue7,
        floatValue8,
        floatValue9,
        flag,
        floatValue10,
        floatValue11,
        set);
  }

  @Override
  public void renderCuboid(
      Entry entry, VertexConsumer vertexConsumer, int intValue, int intValue2, int intValue3) {
    Vector3f vector3f = new Vector3f();

    for (Quad quad : this.sides) {
      if (quad != null) {
        entry.transformNormal(quad.direction(), vector3f);
        float x = vector3f.x;
        float y = vector3f.y;
        float z = vector3f.z;

        for (Vertex vertex : quad.vertices()) {
          entry
              .getPositionMatrix()
              .transformPosition(vertex.worldX(), vertex.worldY(), vertex.worldZ(), vector3f);
          vertexConsumer.vertex(
              vector3f.x,
              vector3f.y,
              vector3f.z,
              intValue3,
              vertex.u(),
              vertex.v(),
              intValue2,
              intValue,
              x,
              y,
              z);
        }
      }
    }
  }
}
