package bunnythekenzo.client;

import net.minecraft.client.model.ModelPart;

public final class EmoteBodyPartsSupport {
  public final ModelPart modelPart;
  public final ModelPart modelPart2;
  public final ModelPart modelPart3;
  public final int count;
  public boolean enabled;

  public EmoteBodyPartsSupport(
      ModelPart modelPart4, ModelPart modelPart22, ModelPart modelPart32, int count2) {
    this.modelPart = modelPart4;
    this.modelPart2 = modelPart22;
    this.modelPart3 = modelPart32;
    this.count = count2;
  }
}
