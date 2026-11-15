package bunnythekenzo.client;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPart.Cuboid;
import net.minecraft.util.math.Direction;
import bunnythekenzo.client.mixin.ModelPartAccessor;

public final class EmoteBending {
  public final ModelPart modelPart;
  public final ModelPart modelPart2;
  public final ModelPart modelPart3;
  public final List list = new ArrayList();
  public final float amount;
  public boolean enabled;
  public boolean enabled2;
  public float amount2;
  public float amount3;
  public ModelPart modelPart4;

  public EmoteBending(ModelPart modelPart5, float amount4) {
    this.modelPart = modelPart5;
    this.amount = amount4;
    ModelPartAccessor modelPartAccessor = (ModelPartAccessor) (Object) (modelPart5);
    LinkedHashMap linkedHashMap = new LinkedHashMap(modelPartAccessor.getChildren());

    for (Entry entry :
        (java.lang.Iterable<Entry>) (java.lang.Iterable<?>) (linkedHashMap.entrySet())) {
      if (((String) entry.getKey()).endsWith("_sleeve")
          || ((String) entry.getKey()).endsWith("_pants")) {
        this.list.add(
            new EmoteBending(
                (ModelPart) entry.getValue(), amount4 - ((ModelPart) entry.getValue()).originY));
      }
    }

    ArrayList arrayList = new ArrayList();
    ArrayList arrayList2 = new ArrayList();

    for (Cuboid cuboid :
        (java.lang.Iterable<Cuboid>) (java.lang.Iterable<?>) (modelPartAccessor.getCuboids())) {
      if (cuboid.sides.length != 6) {
        arrayList.add(cuboid);
      } else {
        Cuboid cuboid2 = EmoteBodyParts.getCuboid(cuboid, amount4, false);
        Cuboid cuboid3 = EmoteBodyParts.getCuboid(cuboid, amount4, true);
        if (cuboid2 != null) {
          arrayList.add(cuboid2);
        }

        if (cuboid3 != null) {
          arrayList2.add(cuboid3);
        }
      }
    }

    this.modelPart2 = new ModelPart(arrayList, Map.of());
    this.modelPart3 = new ModelPart(arrayList2, Map.of());
    this.modelPart3.originY = amount4;
    this.modelPart2.visible = this.modelPart3.visible = false;
    linkedHashMap.put("bunnythekenzo_emote_upper", this.modelPart2);
    linkedHashMap.put("bunnythekenzo_emote_lower", this.modelPart3);
    modelPartAccessor.setChildren(linkedHashMap);
  }

  public void bend(float amount22, float amount32) {
    if (!this.enabled) {
      this.enabled2 = this.modelPart.hidden;
    }

    this.enabled = true;
    this.amount2 = amount22;
    this.amount3 = amount32;
    this.modelPart.hidden = true;
    this.modelPart2.visible = this.modelPart3.visible = !this.enabled2;
    this.modelPart3.originY = this.amount;
    this.modelPart3.pitch = amount22;
    this.modelPart3.roll = amount32;
    this.list.forEach(
        value -> {
          ((bunnythekenzo.client.EmoteBending) (value)).bend(amount22, amount32);
        });
  }

  public void update() {
    if (this.enabled) {
      this.modelPart.hidden = this.enabled2;
    }

    this.enabled = false;
    this.amount2 = this.amount3 = 0.0F;
    this.modelPart2.visible = this.modelPart3.visible = false;
    this.modelPart3.pitch = this.modelPart3.roll = 0.0F;
    if (this.modelPart4 != null) {
      this.modelPart4.visible = false;
    }

    this.list.forEach(
        (referenceArgument5_0) -> ((bunnythekenzo.client.EmoteBending) referenceArgument5_0).update());
  }

  public void apply(boolean visible) {
    if (this.modelPart4 == null) {
      List list2 = ((ModelPartAccessor) (Object) (this.modelPart)).getCuboids();
      if (list2.isEmpty()) {
        return;
      }

      float floatValue = ((Cuboid) list2.getFirst()).maxX - 1.0F;
      this.modelPart4 =
          new ModelPart(
              List.of(
                  EmoteBodyParts.getCuboid(
                      new EmoteBodyPartsSupport2(
                          44,
                          28,
                          -0.5F,
                          0.0F,
                          -0.7F,
                          1.0F,
                          4.0F,
                          1.4F,
                          0.0F,
                          0.0F,
                          0.0F,
                          false,
                          64.0F,
                          64.0F,
                          EnumSet.allOf(Direction.class))),
                  EmoteBodyParts.getCuboid(
                      new EmoteBodyPartsSupport2(
                          44,
                          28,
                          -2.5F,
                          0.0F,
                          -0.7F,
                          3.0F,
                          1.0F,
                          1.4F,
                          0.0F,
                          0.0F,
                          0.0F,
                          false,
                          64.0F,
                          64.0F,
                          EnumSet.allOf(Direction.class)))),
              Map.of());
      this.modelPart4.setOrigin(floatValue, 6.1F, 0.0F);
      this.modelPart4.pitch = 0.786F;
      this.modelPart4.roll = -0.76F;
      ModelPartAccessor modelPartAccessor = (ModelPartAccessor) (Object) (this.modelPart3);
      LinkedHashMap linkedHashMap = new LinkedHashMap(modelPartAccessor.getChildren());
      linkedHashMap.put("bunnythekenzo_l_gesture", this.modelPart4);
      modelPartAccessor.setChildren(linkedHashMap);
    }

    this.modelPart4.visible = visible;
  }
}
