package bunnythekenzo.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPart.Cuboid;
import net.minecraft.client.model.ModelPart.Quad;
import net.minecraft.client.model.ModelPart.Vertex;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.Direction;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import bunnythekenzo.client.mixin.ModelPartAccessor;

public final class EmoteModelGeometry {
  public final BipedEntityModel bipedEntityModel;
  public final List list = new ArrayList();
  public final Quaternionf[] quaternionfArray = new Quaternionf[10];
  public final Quaternionf quaternionf = new Quaternionf();
  public final Quaternionf quaternionf2 = new Quaternionf();
  public final Quaternionf quaternionf3 = new Quaternionf();
  public final Vector3f vector3f = new Vector3f();
  public final Vector3f vector3f2 = new Vector3f();
  public boolean enabled;

  public EmoteModelGeometry(BipedEntityModel bipedEntityModel2) {
    this.bipedEntityModel = bipedEntityModel2;

    for (int index = 0; index < this.quaternionfArray.length; index++) {
      this.quaternionfArray[index] = new Quaternionf();
    }

    this.render(bipedEntityModel2.rightArm, 4.0F, 2);
    this.render(bipedEntityModel2.leftArm, 4.0F, 4);
    this.render(bipedEntityModel2.rightLeg, 6.0F, 6);
    this.render(bipedEntityModel2.leftLeg, 6.0F, 8);
  }

  public void render(ModelPart modelPart, float floatValue, int intValue) {
    ModelPartAccessor modelPartAccessor = (ModelPartAccessor) (Object) (modelPart);

    for (String text : new String[] {"right_sleeve", "left_sleeve", "right_pants", "left_pants"}) {
      if (modelPart.hasChild(text)) {
        this.render(modelPart.getChild(text), floatValue, intValue);
      }
    }

    if (!modelPartAccessor.getCuboids().isEmpty()) {
      ArrayList arrayList = new ArrayList();
      ArrayList arrayList2 = new ArrayList();

      for (Cuboid cuboid :
          (java.lang.Iterable<Cuboid>) (java.lang.Iterable<?>) (modelPartAccessor.getCuboids())) {
        arrayList.add(getCuboid(cuboid, floatValue, false));
        arrayList2.add(getCuboid(cuboid, floatValue, true));
      }

      ModelPart modelPart2 = new ModelPart(arrayList, new HashMap<>());
      ModelPart modelPart3 = new ModelPart(arrayList2, new HashMap<>());
      modelPart3.setDefaultTransform(ModelTransform.origin(0.0F, floatValue, 0.0F));
      modelPart3.resetTransform();
      modelPart2.visible = modelPart3.visible = false;
      HashMap hashMap = new HashMap(modelPartAccessor.getChildren());
      hashMap.put("bunnythekenzo_upper", modelPart2);
      hashMap.put("bunnythekenzo_lower", modelPart3);
      modelPartAccessor.setChildren(hashMap);
      this.list.add(new EmoteBodyPartsSupport(modelPart, modelPart2, modelPart3, intValue));
    }
  }

  public void update() {
    if (this.enabled) {
      for (EmoteBodyPartsSupport emoteBodyPartsSupport :
          (java.lang.Iterable<EmoteBodyPartsSupport>) (java.lang.Iterable<?>) (this.list)) {
        emoteBodyPartsSupport.modelPart.hidden = emoteBodyPartsSupport.enabled;
        emoteBodyPartsSupport.modelPart2.visible = emoteBodyPartsSupport.modelPart3.visible = false;
      }

      this.enabled = false;
    }
  }

  public void render(
      float[] floatValues, float[] floatValues2, float floatValue, float floatValue2) {
    for (int index = 0; index < this.quaternionfArray.length; index++) {
      int index2 = 3 + index * 4;
      this.quaternionfArray[index].set(
          floatValues[index2],
          floatValues[index2 + 1],
          floatValues[index2 + 2],
          floatValues[index2 + 3]);
      this.quaternionf.set(
          floatValues2[index2],
          floatValues2[index2 + 1],
          floatValues2[index2 + 2],
          floatValues2[index2 + 3]);
      this.quaternionfArray[index].slerp(this.quaternionf, floatValue);
    }

    ModelPart rootPart = this.bipedEntityModel.getRootPart();
    rootPart.originX =
        rootPart.originX + computeFloat(floatValues[0], floatValues2[0], floatValue) * floatValue2;
    rootPart = this.bipedEntityModel.getRootPart();
    rootPart.originY =
        rootPart.originY + computeFloat(floatValues[1], floatValues2[1], floatValue) * floatValue2;
    rootPart = this.bipedEntityModel.getRootPart();
    rootPart.originZ =
        rootPart.originZ + computeFloat(floatValues[2], floatValues2[2], floatValue) * floatValue2;
    this.render(this.bipedEntityModel.body, this.quaternionfArray[0], floatValue2);
    this.vector3f2.set(0.0F, -12.0F, 0.0F).rotate(this.quaternionfArray[0]);
    this.bipedEntityModel.body.originX =
        this.bipedEntityModel.body.originX + this.vector3f2.x * floatValue2;
    this.bipedEntityModel.body.originY =
        this.bipedEntityModel.body.originY + (12.0F + this.vector3f2.y) * floatValue2;
    this.bipedEntityModel.body.originZ =
        this.bipedEntityModel.body.originZ + this.vector3f2.z * floatValue2;
    this.bipedEntityModel.head.originX =
        this.bipedEntityModel.head.originX + this.vector3f2.x * floatValue2;
    this.bipedEntityModel.head.originY =
        this.bipedEntityModel.head.originY + (12.0F + this.vector3f2.y) * floatValue2;
    this.bipedEntityModel.head.originZ =
        this.bipedEntityModel.head.originZ + this.vector3f2.z * floatValue2;
    this.render(this.bipedEntityModel.head, this.quaternionfArray[1], floatValue2);
    this.render(this.bipedEntityModel.rightArm, -5.0F, floatValue2);
    this.render(this.bipedEntityModel.leftArm, 5.0F, floatValue2);
    this.render(this.bipedEntityModel.rightArm, this.quaternionfArray[2], floatValue2);
    this.render(this.bipedEntityModel.leftArm, this.quaternionfArray[4], floatValue2);
    this.render(this.bipedEntityModel.rightLeg, this.quaternionfArray[6], floatValue2);
    this.render(this.bipedEntityModel.leftLeg, this.quaternionfArray[8], floatValue2);

    for (EmoteBodyPartsSupport emoteBodyPartsSupport :
        (java.lang.Iterable<EmoteBodyPartsSupport>) (java.lang.Iterable<?>) (this.list)) {
      if (!this.enabled) {
        emoteBodyPartsSupport.enabled = emoteBodyPartsSupport.modelPart.hidden;
      }

      emoteBodyPartsSupport.modelPart.hidden = true;
      emoteBodyPartsSupport.modelPart2.visible = emoteBodyPartsSupport.modelPart3.visible = true;
      emoteBodyPartsSupport.modelPart3.resetTransform();
      this.quaternionf3
          .set(this.quaternionfArray[emoteBodyPartsSupport.count])
          .conjugate()
          .mul(this.quaternionfArray[emoteBodyPartsSupport.count + 1]);
      this.render(emoteBodyPartsSupport.modelPart3, this.quaternionf3, floatValue2);
    }

    this.enabled = true;
  }

  public void render(ModelPart modelPart, float floatValue, float floatValue2) {
    this.vector3f2.set(floatValue, -10.0F, 0.0F).rotate(this.quaternionfArray[0]);
    modelPart.originX = modelPart.originX + (this.vector3f2.x - floatValue) * floatValue2;
    modelPart.originY = modelPart.originY + (12.0F + this.vector3f2.y - 2.0F) * floatValue2;
    modelPart.originZ = modelPart.originZ + this.vector3f2.z * floatValue2;
  }

  public void render(ModelPart modelPart, Quaternionf quaternionf4, float floatValue) {
    this.quaternionf2
        .rotationZYX(modelPart.roll, modelPart.yaw, modelPart.pitch)
        .slerp(quaternionf4, floatValue);
    this.quaternionf2.getEulerAnglesZYX(this.vector3f);
    modelPart.setAngles(this.vector3f.x, this.vector3f.y, this.vector3f.z);
  }

  public static float computeFloat(float floatValue, float floatValue2, float floatValue3) {
    return floatValue + (floatValue2 - floatValue) * floatValue3;
  }

  public void apply(Arm arm, MatrixStack matrixStack) {
    if (this.enabled) {
      ModelPart modelPart =
          arm == Arm.RIGHT ? this.bipedEntityModel.rightArm : this.bipedEntityModel.leftArm;

      for (EmoteBodyPartsSupport emoteBodyPartsSupport :
          (java.lang.Iterable<EmoteBodyPartsSupport>) (java.lang.Iterable<?>) (this.list)) {
        if (emoteBodyPartsSupport.modelPart == modelPart) {
          matrixStack.translate(0.0F, emoteBodyPartsSupport.modelPart3.originY / 16.0F, 0.0F);
          matrixStack.multiply(
              this.quaternionf2.rotationZYX(
                  emoteBodyPartsSupport.modelPart3.roll,
                  emoteBodyPartsSupport.modelPart3.yaw,
                  emoteBodyPartsSupport.modelPart3.pitch));
          matrixStack.translate(0.0F, -emoteBodyPartsSupport.modelPart3.originY / 16.0F, 0.0F);
          break;
        }
      }
    }
  }

  public static Cuboid getCuboid(Cuboid cuboid, float floatValue, boolean flag) {
    EmoteBodyPartsSupport2 emoteBodyPartsSupport2 =
        new EmoteBodyPartsSupport2(
            0,
            0,
            cuboid.minX,
            flag ? 0.0F : cuboid.minY,
            cuboid.minZ,
            cuboid.maxX - cuboid.minX,
            flag ? cuboid.maxY - floatValue : floatValue - cuboid.minY,
            cuboid.maxZ - cuboid.minZ,
            0.0F,
            0.0F,
            0.0F,
            false,
            64.0F,
            64.0F,
            Set.of(Direction.values()));

    for (int index = 0; index < cuboid.sides.length; index++) {
      Quad quad = cuboid.sides[index];
      if (quad == null) {
        emoteBodyPartsSupport2.sides[index] = null;
      } else {
        Vertex[] vertexArray = quad.vertices();
        Vertex[] vertexArray2 = new Vertex[vertexArray.length];

        for (int index2 = 0; index2 < vertexArray.length; index2++) {
          Vertex vertex = vertexArray[index2];
          float floatValue2 =
              flag ? Math.max(vertex.y(), floatValue) : Math.min(vertex.y(), floatValue);
          float floatValue3 = vertex.u();
          float floatValue4 = vertex.v();
          if (floatValue2 != vertex.y()) {
            for (Vertex vertex2 : vertexArray) {
              if (Math.abs(vertex2.y() - vertex.y()) > 1.0E-4F
                  && Math.abs(vertex2.x() - vertex.x()) < 1.0E-4F
                  && Math.abs(vertex2.z() - vertex.z()) < 1.0E-4F) {
                float floatValue5 = (floatValue2 - vertex.y()) / (vertex2.y() - vertex.y());
                floatValue3 = computeFloat(vertex.u(), vertex2.u(), floatValue5);
                floatValue4 = computeFloat(vertex.v(), vertex2.v(), floatValue5);
                break;
              }
            }
          }

          vertexArray2[index2] =
              new Vertex(
                  vertex.x(),
                  floatValue2 - (flag ? floatValue : 0.0F),
                  vertex.z(),
                  floatValue3,
                  floatValue4);
        }

        emoteBodyPartsSupport2.sides[index] = new Quad(vertexArray2, quad.direction());
      }
    }

    return emoteBodyPartsSupport2;
  }
}
