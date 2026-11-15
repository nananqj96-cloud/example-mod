package bunnythekenzo.client;

import java.util.Objects;
import bunnythekenzo.client.render.FontManager;

public final class HoverTooltip {
  public static final float amount = 380.0F;
  public static final float amount2 = 260.0F;
  public final TweenValue tweenValue = new TweenValue(150.0F, 0.0F);
  public String string;
  public long timestamp;

  public void apply(String string2) {
    if (string2 != null && string2.isBlank()) {
      string2 = null;
    }

    if (!GuiPreferences.getGuiPreferences().test()) {
      string2 = null;
    }

    if (!Objects.equals(string2, this.string)) {
      this.string = string2;
      this.timestamp = System.nanoTime();
      this.tweenValue.animateTo(0.0F);
    } else if (string2 != null
        && (float) (System.nanoTime() - this.timestamp) / 1000000.0F > 380.0F) {
      this.tweenValue.animateTo(1.0F);
    }
  }

  public void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    float value = this.tweenValue.getValue();
    if (!(value <= 0.01F) && this.string != null) {
      ColorPalette instance = ColorPalette.getInstance();
      float floatValue5 = Math.min(260.0F, fontManager.computeFloat(this.string, 11.0F)) + 20.0F;
      float floatValue6 =
          floatValue + 14.0F + floatValue5 > floatValue3 - 8.0F
              ? floatValue - 14.0F - floatValue5
              : floatValue + 14.0F;
      float floatValue7 =
          Math.min(floatValue2 + 18.0F, floatValue4 - 26.0F - 8.0F) + (1.0F - value) * 5.0F;
      fontManager.update2();
      fontManager.apply3(value);
      fontManager.render(
          floatValue6,
          floatValue7,
          floatValue5,
          26.0F,
          7.0F,
          11.0F,
          3.0F,
          ArgbColors.withOpacity(-16777216, 0.5F));
      fontManager.render(
          floatValue6,
          floatValue7,
          floatValue5,
          26.0F,
          7.0F,
          instance.computeInt5(),
          instance.computeInt6(),
          true);
      fontManager.render(
          floatValue6, floatValue7, floatValue5, 26.0F, 7.0F, 1.0F, instance.computeInt(0.32F));
      fontManager.computeFloat(
          this.string,
          floatValue6 + 10.0F,
          floatValue7 + 13.0F,
          11.0F,
          instance.computeInt17(),
          floatValue5 - 20.0F);
      fontManager.update3();
    }
  }
}
