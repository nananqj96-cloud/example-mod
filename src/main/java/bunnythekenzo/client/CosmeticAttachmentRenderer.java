package bunnythekenzo.client;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerSkinType;
import net.minecraft.entity.player.SkinTextures;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import bunnythekenzo.client.module.CosmeticsModule;

public final class CosmeticAttachmentRenderer {
  public static final Identifier identifier = getIdentifier("quartz_block_side");
  public static final Identifier identifier2 = getIdentifier("gold_block");
  public static final Identifier identifier3 = getIdentifier("amethyst_block");
  public static final Identifier identifier4 = getIdentifier("obsidian");
  public static final Identifier identifier5 = getIdentifier("copper_block");
  public static final Identifier identifier6 = getIdentifier("magma");
  public static final Identifier identifier7 = getIdentifier("diamond_block");
  public static final Identifier identifier8 = getIdentifier("iron_block");
  public static final float[] amountArray =
      new float[] {9.0F, 12.0F, 15.0F, 18.0F, 19.0F, 18.0F, 16.0F, 13.0F, 10.0F};

  public static Identifier getIdentifier(String text) {
    return Identifier.ofVanilla("textures/block/" + text + ".png");
  }

  public static void render(
      VertexConsumerSupport6 vertexConsumerSupport6,
      MatrixStack matrixStack,
      CosmeticsModule cosmeticsModule,
      SkinTextures skinTextures,
      float floatValue,
      float floatValue2,
      boolean flag) {
    matrixStack.scale(0.0625F, 0.0625F, 0.0625F);
    matrixStack.translate(0.0F, 2.0F, 3.15F);
    float floatValue3 = cosmeticsModule.wingScaleSetting.floatValue() / 100.0F;
    matrixStack.scale(floatValue3, floatValue3, floatValue3);
    ClientData5 clientData5 =
        getClientData5((String) cosmeticsModule.wingStyleSetting.getValue(), skinTextures);
    vertexConsumerSupport6.render(
        matrixStack, clientData5.identifier2(), -2.8F, -0.6F, 0.0F, 5.6F, 4.4F, 1.5F, -1);
    vertexConsumerSupport6.render(
        matrixStack, clientData5.identifier3(), -1.2F, 0.3F, 1.5F, 2.4F, 2.4F, 0.8F, -1);
    float floatValue4 =
        cosmeticsModule.motionSetting.isSelected("Still")
            ? 0.0F
            : cosmeticsModule.wingbeatSetting.floatValue() / 100.0F;
    float floatValue5 = MathHelper.sin(floatValue * 2.7F);

    for (int intValue : new int[] {-1, 1}) {
      matrixStack.push();
      matrixStack.translate(intValue * 2.3F, 0.8F, 1.0F);
      matrixStack.scale(intValue, 1.0F, 1.0F);
      matrixStack.multiply(
          RotationAxis.POSITIVE_Y.rotation(
              -0.22F
                  - floatValue2 * 0.24F
                  + floatValue5 * (0.12F + floatValue2 * 0.3F) * floatValue4));
      matrixStack.multiply(
          RotationAxis.POSITIVE_Z.rotation(
              -0.12F
                  - floatValue2 * 0.12F
                  + MathHelper.sin(floatValue * 2.7F - 0.65F) * 0.07F * floatValue4));
      render(
          vertexConsumerSupport6,
          matrixStack,
          clientData5,
          floatValue,
          floatValue4,
          floatValue2,
          (Boolean) cosmeticsModule.wingJewelsSetting.getValue(),
          flag);
      matrixStack.pop();
    }
  }

  public static void render(
      VertexConsumerSupport6 vertexConsumerSupport6,
      MatrixStack matrixStack,
      ClientData5 clientData5,
      float floatValue,
      float floatValue2,
      float floatValue3,
      boolean flag,
      boolean flag2) {
    render(
        vertexConsumerSupport6,
        matrixStack,
        clientData5.identifier2(),
        0.0F,
        1.0F,
        8.0F,
        -7.0F,
        2.8F,
        2.6F);
    render(
        vertexConsumerSupport6,
        matrixStack,
        clientData5.identifier(),
        1.0F,
        1.0F,
        8.0F,
        -6.0F,
        3.8F,
        2.1F);
    matrixStack.push();
    matrixStack.translate(8.0F, -7.0F, 0.15F);
    matrixStack.multiply(
        RotationAxis.POSITIVE_Y.rotation(
            MathHelper.sin(floatValue * 2.7F - 0.7F) * 0.12F * floatValue2));
    render(
        vertexConsumerSupport6,
        matrixStack,
        clientData5.identifier2(),
        0.0F,
        0.0F,
        15.0F,
        -5.0F,
        2.1F,
        1.8F);
    matrixStack.pop();

    for (int index = 0; index < amountArray.length; index++) {
      float floatValue4 = 2.4F + index * 2.25F;
      float floatValue5 = index < 3 ? -index * 1.65F : -5.0F - (index - 3) * 0.95F;
      float floatValue6 = -0.06F - index * 0.095F;
      float floatValue7 =
          MathHelper.sin(floatValue * 2.7F - index * 0.22F)
              * (0.025F + index * 0.007F)
              * floatValue2;
      matrixStack.push();
      matrixStack.translate(floatValue4, floatValue5, 0.7F);
      matrixStack.multiply(
          RotationAxis.POSITIVE_Z.rotation(floatValue6 + floatValue7 - floatValue3 * 0.05F));
      matrixStack.multiply(
          RotationAxis.POSITIVE_X.rotation(floatValue7 * 1.5F + (flag2 ? -0.1F : 0.0F)));
      render(vertexConsumerSupport6, matrixStack, clientData5, amountArray[index], index, flag);
      matrixStack.pop();
    }

    for (int index2 = 0; index2 < 7; index2++) {
      matrixStack.push();
      matrixStack.translate(2.0F + index2 * 2.45F, -1.0F - index2 * 1.15F, 2.25F);
      matrixStack.multiply(RotationAxis.POSITIVE_Z.rotation(-0.27F - index2 * 0.055F));
      render(
          vertexConsumerSupport6, matrixStack, clientData5, 5.8F + index2 * 0.24F, index2, false);
      matrixStack.pop();
    }

    if (flag) {
      vertexConsumerSupport6.render(
          matrixStack, clientData5.identifier2(), 5.6F, -7.3F, 2.3F, 4.0F, 3.8F, 1.1F, -1);
      vertexConsumerSupport6.render(
          matrixStack, clientData5.identifier3(), 6.45F, -6.65F, 3.4F, 2.3F, 2.3F, 1.3F, -1);
    }
  }

  public static void render(
      VertexConsumerSupport6 vertexConsumerSupport6,
      MatrixStack matrixStack,
      ClientData5 clientData5,
      float floatValue,
      int intValue,
      boolean flag) {
    int intValue2 =
        clientData5.enabled()
            ? -1
            : (intValue % 3 == 0 ? clientData5.count2() : clientData5.count());
    if (clientData5.enabled()) {
      vertexConsumerSupport6.render(
          matrixStack,
          clientData5.identifier(),
          -1.45F,
          0.0F,
          -0.55F,
          2.9F,
          floatValue - 2.0F,
          1.4F,
          16,
          16,
          8,
          12,
          4);
      vertexConsumerSupport6.render(
          matrixStack,
          clientData5.identifier(),
          -0.95F,
          floatValue - 2.0F,
          -0.4F,
          1.9F,
          2.0F,
          1.1F,
          16,
          16,
          8,
          12,
          4);
    } else {
      vertexConsumerSupport6.render(
          matrixStack,
          clientData5.identifier(),
          -1.45F,
          0.0F,
          -0.55F,
          2.9F,
          floatValue - 2.0F,
          1.4F,
          intValue2);
      vertexConsumerSupport6.render(
          matrixStack,
          clientData5.identifier(),
          -0.95F,
          floatValue - 2.0F,
          -0.4F,
          1.9F,
          1.5F,
          1.1F,
          intValue2);
      vertexConsumerSupport6.render(
          matrixStack,
          clientData5.identifier(),
          -0.45F,
          floatValue - 0.5F,
          -0.25F,
          0.9F,
          1.0F,
          0.8F,
          intValue2);
      vertexConsumerSupport6.render(
          matrixStack,
          clientData5.identifier(),
          -0.24F,
          0.6F,
          0.86F,
          0.48F,
          floatValue - 2.5F,
          0.22F,
          -1);
      if (flag) {
        vertexConsumerSupport6.render(
            matrixStack,
            clientData5.identifier2(),
            -0.98F,
            floatValue - 3.7F,
            -0.59F,
            1.96F,
            1.15F,
            1.48F,
            -1);
      }
    }
  }

  public static void render(
      VertexConsumerSupport6 vertexConsumerSupport6,
      MatrixStack matrixStack,
      Identifier identifier9,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6) {
    float floatValue7 = floatValue3 - floatValue;
    float floatValue8 = floatValue4 - floatValue2;
    matrixStack.push();
    matrixStack.translate(floatValue, floatValue2, 0.0F);
    matrixStack.multiply(
        RotationAxis.POSITIVE_Z.rotation((float) Math.atan2(floatValue8, floatValue7)));
    vertexConsumerSupport6.render(
        matrixStack,
        identifier9,
        0.0F,
        -floatValue5 / 2.0F,
        -floatValue6 / 2.0F,
        (float) Math.hypot(floatValue7, floatValue8),
        floatValue5,
        floatValue6,
        -1);
    matrixStack.pop();
  }

  public static void apply(
      VertexConsumerSupport6 vertexConsumerSupport6,
      MatrixStack matrixStack,
      CosmeticsModule cosmeticsModule) {
    matrixStack.scale(0.0625F, 0.0625F, 0.0625F);
    float floatValue = cosmeticsModule.crownScaleSetting.floatValue() / 100.0F;
    matrixStack.translate(0.0F, -8.1F, 0.0F);
    matrixStack.scale(floatValue, floatValue, floatValue);
    Identifier identifier9 =
        cosmeticsModule.crownStyleSetting.isSelected("Ender")
            ? identifier4
            : (cosmeticsModule.crownStyleSetting.isSelected("Ice") ? identifier8 : identifier2);
    Identifier identifier10 =
        cosmeticsModule.crownStyleSetting.isSelected("Ice") ? identifier7 : identifier3;

    for (int index = 0; index < 4; index++) {
      matrixStack.push();
      matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(index * 90));
      vertexConsumerSupport6.render(
          matrixStack, identifier9, -4.65F, -1.4F, -4.7F, 9.3F, 1.5F, 0.85F, -1);
      vertexConsumerSupport6.render(
          matrixStack, identifier9, -4.65F, -0.15F, -4.9F, 9.3F, 0.5F, 1.2F, -1);

      for (int index2 = -1; index2 <= 1; index2++) {
        float floatValue2 = index2 == 0 ? 2.5F : 1.6F;
        vertexConsumerSupport6.render(
            matrixStack,
            identifier9,
            index2 * 3.35F - 0.7F,
            -1.4F - floatValue2,
            -4.65F,
            1.4F,
            floatValue2,
            0.8F,
            -1);
        vertexConsumerSupport6.render(
            matrixStack,
            identifier9,
            index2 * 3.35F - 0.35F,
            -1.9F - floatValue2,
            -4.55F,
            0.7F,
            0.7F,
            0.6F,
            -1);
      }

      vertexConsumerSupport6.render(
          matrixStack, identifier10, -0.7F, -2.6F, -5.0F, 1.4F, 1.5F, 0.6F, -1);
      matrixStack.pop();
    }
  }

  public static void render(
      VertexConsumerSupport6 vertexConsumerSupport6,
      MatrixStack matrixStack,
      CosmeticsModule cosmeticsModule,
      SkinTextures skinTextures,
      float floatValue,
      float floatValue2) {
    matrixStack.push();
    int intValue = cosmeticsModule.companionSideSetting.isSelected("Right") ? -1 : 1;
    matrixStack.translate(
        intValue * 0.94F, -0.42F + MathHelper.sin(floatValue * 2.5F) * 0.065F, -0.05F);
    matrixStack.multiply(
        RotationAxis.POSITIVE_Y.rotation(
            intValue * -0.22F + MathHelper.sin(floatValue * 1.2F) * 0.18F));
    matrixStack.multiply(
        RotationAxis.POSITIVE_Z.rotation(MathHelper.sin(floatValue * 2.5F - 0.5F) * 0.07F));
    float floatValue3 = 0.3F * cosmeticsModule.companionScaleSetting.floatValue() / 100.0F / 16.0F;
    matrixStack.scale(floatValue3, floatValue3, floatValue3);
    Identifier identifier9 = skinTextures.body().texturePath();
    boolean flag = skinTextures.model() == PlayerSkinType.SLIM;
    vertexConsumerSupport6.render(
        matrixStack, identifier9, -4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0, 0, 8, 8, 8);
    vertexConsumerSupport6.render(
        matrixStack, identifier9, -4.25F, -8.25F, -4.25F, 8.5F, 8.5F, 8.5F, 32, 0, 8, 8, 8);
    vertexConsumerSupport6.render(
        matrixStack, identifier9, -4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 16, 16, 8, 12, 4);
    vertexConsumerSupport6.render(
        matrixStack, identifier9, -4.12F, -0.12F, -2.12F, 8.24F, 12.24F, 4.24F, 16, 32, 8, 12, 4);

    for (int intValue2 : new int[] {-1, 1}) {
      boolean flag2 = intValue2 == 1;
      matrixStack.push();
      matrixStack.translate(intValue2 * 4, 1.0F, 0.0F);
      matrixStack.multiply(
          RotationAxis.POSITIVE_Z.rotation(
              intValue2 * (-0.25F + MathHelper.sin(floatValue * 3.3F) * 0.09F)));
      int intValue3 = flag ? 3 : 4;
      float floatValue4 = flag2 ? 0.0F : -intValue3;
      vertexConsumerSupport6.render(
          matrixStack,
          identifier9,
          floatValue4,
          -1.0F,
          -2.0F,
          intValue3,
          12.0F,
          4.0F,
          flag2 ? 32 : 40,
          flag2 ? 48 : 16,
          intValue3,
          12,
          4);
      vertexConsumerSupport6.render(
          matrixStack,
          identifier9,
          floatValue4 - 0.12F,
          -1.12F,
          -2.12F,
          intValue3 + 0.24F,
          12.24F,
          4.24F,
          flag2 ? 48 : 40,
          flag2 ? 48 : 32,
          intValue3,
          12,
          4);
      matrixStack.pop();
      matrixStack.push();
      matrixStack.translate(intValue2 * 2, 12.0F, 0.0F);
      matrixStack.multiply(
          RotationAxis.POSITIVE_X.rotation(
              0.25F
                  + MathHelper.sin(floatValue * 3.3F + intValue2) * (0.12F + floatValue2 * 0.2F)));
      vertexConsumerSupport6.render(
          matrixStack,
          identifier9,
          -2.0F,
          0.0F,
          -2.0F,
          4.0F,
          12.0F,
          4.0F,
          flag2 ? 16 : 0,
          flag2 ? 48 : 16,
          4,
          12,
          4);
      vertexConsumerSupport6.render(
          matrixStack,
          identifier9,
          -2.12F,
          -0.12F,
          -2.12F,
          4.24F,
          12.24F,
          4.24F,
          0,
          flag2 ? 48 : 32,
          4,
          12,
          4);
      matrixStack.pop();
    }

    for (int intValue4 : new int[] {-1, 1}) {
      matrixStack.push();
      matrixStack.translate(intValue4 * 3, 2.0F, 2.7F);
      matrixStack.scale(intValue4, 1.0F, 1.0F);
      matrixStack.multiply(
          RotationAxis.POSITIVE_Y.rotation(-0.4F + MathHelper.sin(floatValue * 10.0F) * 0.45F));

      for (int index = 0; index < 4; index++) {
        matrixStack.push();
        matrixStack.translate(index * 2.2F, -index * 1.2F, 0.0F);
        matrixStack.multiply(RotationAxis.POSITIVE_Z.rotation(-0.45F));
        vertexConsumerSupport6.render(
            matrixStack, identifier, 0.0F, 0.0F, 0.0F, 2.0F, 6.5F - index * 0.6F, 1.0F, -1);
        matrixStack.pop();
      }

      matrixStack.pop();
    }

    matrixStack.pop();
  }

  public static ClientData5 getClientData5(String text, SkinTextures skinTextures) {
    return switch (text) {
      case "Ender" ->
          new ClientData5(identifier4, identifier3, identifier3, -2042369, -4808480, false);
      case "Phoenix" ->
          new ClientData5(identifier5, identifier2, identifier6, -16009, -8022, false);
      case "Your Skin" ->
          new ClientData5(
              skinTextures.body().texturePath(), identifier8, identifier7, -1, -1, true);
      default -> new ClientData5(identifier, identifier2, identifier7, -1553, -1645330, false);
    };
  }
}
