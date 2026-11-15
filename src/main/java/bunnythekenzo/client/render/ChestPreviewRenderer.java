package bunnythekenzo.client.render;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.client.model.ModelPart.Quad;
import net.minecraft.client.model.ModelPart.Vertex;
import net.minecraft.client.render.block.entity.model.ChestBlockModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.joml.Matrix3f;
import org.joml.Vector3f;
import bunnythekenzo.client.ChestPreviewData;
import bunnythekenzo.client.DiagnosticTokenGenerator;
import bunnythekenzo.client.NanoVgImageCache;

public final class ChestPreviewRenderer {
  public static final Identifier identifier =
      Identifier.ofVanilla("textures/entity/chest/normal.png");
  public final List list = new ArrayList();
  public int count;

  public void prepare(FontManager fontManager) {
    this.count = NanoVgImageCache.computeInt(identifier);
    if (this.count <= 0) {
      throw new IllegalStateException(DiagnosticTokenGenerator.tokenFor(1401));
    }

    if (this.list.isEmpty()) {
      Matrix3f matrix3f = new Matrix3f().rotateX(0.38F).rotateY(-0.55F);
      ChestBlockModel.getSingleTexturedModelData()
          .createModel()
          .forEachCuboid(
              new MatrixStack(),
              (entry, text, intValue, cuboid) -> {
                for (Quad quad : cuboid.sides) {
                  Vector3f vector3f = matrix3f.transform(new Vector3f(quad.direction()));
                  if (!(vector3f.z <= 0.0F)) {
                    float[] amountArray = new float[8];
                    float[] amountArray2 = new float[8];
                    float floatValue = 0.0F;
                    Vertex[] vertexArray = quad.vertices();

                    for (int index = 0; index < 4; index++) {
                      Vertex vertex = vertexArray[index];
                      Vector3f vector3f2 =
                          entry
                              .getPositionMatrix()
                              .transformPosition(
                                  new Vector3f(vertex.worldX(), vertex.worldY(), vertex.worldZ()));
                      matrix3f.transform(vector3f2.sub(0.5F, 0.4375F, 0.5F));
                      amountArray[index * 2] = vector3f2.x;
                      amountArray[index * 2 + 1] = -vector3f2.y;
                      floatValue += vector3f2.z;
                      amountArray2[index * 2] = vertex.u() * 64.0F;
                      amountArray2[index * 2 + 1] = vertex.v() * 64.0F;
                    }

                    float floatValue2 =
                        0.62F
                            + 0.38F
                                * Math.max(
                                    0.0F,
                                    vector3f.dot(new Vector3f(-0.3F, 0.8F, 1.0F).normalize()));
                    this.list.add(
                        new ChestPreviewData(
                            amountArray, amountArray2, floatValue / 4.0F, 1.0F - floatValue2));
                  }
                }
              });
      this.list.sort(Comparator.comparingDouble(ChestPreviewData::amount));
    }
  }

  public void render(
      FontManager fontManager, float floatValue, float floatValue2, float floatValue3) {
    float floatValue4 = floatValue3 * 0.65F;

    for (ChestPreviewData chestPreviewData :
        (java.lang.Iterable<ChestPreviewData>) (java.lang.Iterable<?>) (this.list)) {
      for (int intValue : new int[] {1, 2}) {
        int index = intValue * 2;
        int index2 = (intValue + 1) * 2;
        float floatValue5 = floatValue + chestPreviewData.amountArray()[0] * floatValue4;
        float floatValue6 = floatValue2 + chestPreviewData.amountArray()[1] * floatValue4;
        float floatValue7 = floatValue + chestPreviewData.amountArray()[index] * floatValue4;
        float floatValue8 = floatValue2 + chestPreviewData.amountArray()[index + 1] * floatValue4;
        float floatValue9 = floatValue + chestPreviewData.amountArray()[index2] * floatValue4;
        float floatValue10 = floatValue2 + chestPreviewData.amountArray()[index2 + 1] * floatValue4;
        fontManager.render(
            this.count,
            64.0F,
            64.0F,
            chestPreviewData.amountArray2()[0],
            chestPreviewData.amountArray2()[1],
            chestPreviewData.amountArray2()[index],
            chestPreviewData.amountArray2()[index + 1],
            chestPreviewData.amountArray2()[index2],
            chestPreviewData.amountArray2()[index2 + 1],
            floatValue5,
            floatValue6,
            floatValue7,
            floatValue8,
            floatValue9,
            floatValue10);
        fontManager.render3(
            floatValue5,
            floatValue6,
            floatValue7,
            floatValue8,
            floatValue9,
            floatValue10,
            Math.round(chestPreviewData.amount2() * 255.0F) << 24);
      }
    }
  }
}
