package bunnythekenzo.client;

import java.util.List;
import bunnythekenzo.client.render.FontManager;

public final class AnimatedTextRows {
  public static final float amount = 0.045F;
  public final AnimationIntensitySupport[] animationIntensitySupportArray;

  public AnimatedTextRows(int intValue) {
    this.animationIntensitySupportArray = new AnimationIntensitySupport[intValue];

    for (int index = 0; index < intValue; index++) {
      this.animationIntensitySupportArray[index] =
          new AnimationIntensitySupport(AnimationIntensity.SUBTLE);
    }
  }

  public int computeInt(
      FontManager fontManager,
      List list,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      int intValue,
      float floatValue7) {
    ColorPalette instance = ColorPalette.getInstance();
    int size = list.size();
    int value =
        EmoteCatalog.computeInt(
            floatValue5,
            floatValue6,
            floatValue,
            floatValue2,
            floatValue3,
            floatValue4 + 10.0F,
            size);
    float floatValue8 = (float) ((Math.PI * 2) / size);
    float floatValue9 =
        floatValue3 + (floatValue4 - floatValue3) * Math.clamp(floatValue7, 0.0F, 1.0F);
    fontManager.render3(
        floatValue,
        floatValue2 + 5.0F,
        floatValue9 * 0.58F,
        floatValue9 * 0.52F,
        ArgbColors.withOpacity(-16777216, 0.52F * floatValue7));

    for (int index = 0; index < size; index++) {
      float value2 = this.animationIntensitySupportArray[index].computeFloat(index == value);
      float floatValue10 = index == intValue ? 1.0F : 0.0F;
      float floatValue11 = floatValue9 + value2 * 7.0F + floatValue10 * 2.0F;
      float floatValue12 = (float) ((-Math.PI / 2) + index * floatValue8);
      int value3 =
          ArgbColors.lerp(
              instance.computeInt10(),
              instance.computeInt12(),
              Math.max(floatValue10 * 0.72F, value2 * 0.88F));
      int value4 =
          ArgbColors.lerp(
              instance.computeInt4(),
              instance.computeInt(0.72F),
              Math.max(floatValue10 * 0.42F, value2 * 0.68F));
      fontManager.render(
          floatValue,
          floatValue2,
          floatValue11,
          floatValue12,
          floatValue8 - 0.045F,
          value3,
          value4);
      if (floatValue10 > 0.0F || value2 > 0.02F) {
        float floatValue13 = floatValue11 - 1.0F;
        fontManager.render4(
            floatValue,
            floatValue2,
            floatValue13,
            floatValue12 - floatValue8 / 2.0F + 0.045F,
            floatValue12 + floatValue8 / 2.0F - 0.045F,
            1.5F,
            ArgbColors.withOpacity(
                instance.computeInt(), 0.35F + 0.55F * Math.max(floatValue10, value2)));
      }
    }

    fontManager.render2(floatValue, floatValue2, floatValue3, instance.computeInt3());
    fontManager.render2(
        floatValue,
        floatValue2,
        floatValue3,
        1.2F,
        ArgbColors.lerp(
            instance.computeInt14(), instance.computeInt15(), value >= 0 ? 0.7F : 0.0F));
    fontManager.render2(
        floatValue,
        floatValue2 - 6.0F,
        3.3F,
        value >= 0 ? instance.computeInt() : instance.computeInt18());
    fontManager.render2(
        floatValue + 2.0F,
        floatValue2 - 7.0F,
        floatValue + 2.0F,
        floatValue2 - 17.0F,
        2.0F,
        value >= 0 ? instance.computeInt() : instance.computeInt18());
    fontManager.render2(
        floatValue + 2.0F,
        floatValue2 - 17.0F,
        floatValue + 9.0F,
        floatValue2 - 14.0F,
        2.0F,
        value >= 0 ? instance.computeInt() : instance.computeInt18());
    int intValue2 = value >= 0 ? value : intValue;
    if (intValue2 >= 0 && intValue2 < size) {
      EmoteDefinition emoteDefinition = (EmoteDefinition) list.get(intValue2);
      fontManager.computeFloat2(
          emoteDefinition.string2(),
          floatValue,
          floatValue2 + 7.0F,
          12.5F,
          instance.computeInt16());
      fontManager.computeFloat2(
          emoteDefinition.getString(),
          floatValue,
          floatValue2 + 23.0F,
          9.5F,
          instance.computeInt18());
    } else {
      fontManager.computeFloat2(
          "Choose", floatValue, floatValue2 + 9.0F, 12.5F, instance.computeInt17());
    }

    float floatValue14 = floatValue3 + (floatValue9 - floatValue3) * 0.57F;

    for (int index2 = 0; index2 < size; index2++) {
      float floatValue15 = (float) ((-Math.PI / 2) + index2 * floatValue8);
      float floatValue16 = floatValue + (float) Math.cos(floatValue15) * floatValue14;
      float floatValue17 = floatValue2 + (float) Math.sin(floatValue15) * floatValue14;
      float floatValue18 =
          Math.max(
              index2 == intValue ? 0.75F : 0.0F,
              this.animationIntensitySupportArray[index2].computeFloat());
      int value5 = ArgbColors.lerp(instance.computeInt17(), instance.computeInt16(), floatValue18);
      float floatValue19 = 2.0F * floatValue14 * (float) Math.sin(floatValue8 / 2.0F);
      float floatValue20 =
          Math.min(
              (floatValue9 - floatValue3) * 1.08F,
              (floatValue19 - 14.0F) / Math.max(0.35F, Math.abs((float) Math.sin(floatValue15))));
      fontManager.computeFloat(
          ((EmoteDefinition) list.get(index2)).string(),
          floatValue16,
          floatValue17,
          9.5F,
          value5,
          Math.max(32.0F, floatValue20),
          2);
    }

    return value;
  }
}
