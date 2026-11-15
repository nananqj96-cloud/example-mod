package bunnythekenzo.client;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;

public interface EmoteModelAccess {
  EmoteModelGeometry getEmoteModelGeometry();

  void applyArmPose(Arm arm, MatrixStack matrixStack);
}
