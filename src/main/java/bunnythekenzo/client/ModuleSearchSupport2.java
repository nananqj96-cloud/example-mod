package bunnythekenzo.client;

import java.util.function.Consumer;
import bunnythekenzo.client.render.FontManager;
import bunnythekenzo.client.ui.MenuTab;

public final class ModuleSearchSupport2 {
  public static final float amount = 22.0F;
  public static final float amount2 = 4.0F;
  public static final float amount3 = 13.0F;
  public final MenuTab[] menuTabArray = MenuTab.values();
  public final AnimationIntensitySupport[] animationIntensitySupportArray =
      new AnimationIntensitySupport[MenuTab.values().length];
  public final ModuleSearchSupport6[] moduleSearchSupport6Array =
      new ModuleSearchSupport6[MenuTab.values().length];
  public final DampedSpring dampedSpring = new DampedSpring(300.0F, 26.0F, 0.0F);
  public final DampedSpring dampedSpring2 = new DampedSpring(340.0F, 34.0F, 0.0F);
  public boolean enabled;
  public final float[] amountArray = new float[MenuTab.values().length];
  public final float[] amountArray2 = new float[MenuTab.values().length];
  public float amount4;
  public float amount5;
  public float amount6;
  public MenuTab menuTab = MenuTab.MODULES;

  public ModuleSearchSupport2() {
    for (int index = 0; index < this.animationIntensitySupportArray.length; index++) {
      this.animationIntensitySupportArray[index] =
          new AnimationIntensitySupport(AnimationIntensity.SUBTLE);
      this.moduleSearchSupport6Array[index] = new ModuleSearchSupport6();
    }
  }

  public MenuTab getMenuTab() {
    return this.menuTab;
  }

  public void render(
      FontManager fontManager,
      float amount42,
      float amount52,
      float amount62,
      float floatValue,
      float floatValue2) {
    this.amount4 = amount42;
    this.amount5 = amount52;
    this.amount6 = amount62;
    ColorPalette instance = ColorPalette.getInstance();
    float floatValue3 = amount42;

    for (int index = 0; index < this.menuTabArray.length; index++) {
      this.amountArray2[index] =
          fontManager.computeFloat(this.menuTabArray[index].getLabel(), 13.0F) + 22.0F;
      this.amountArray[index] = floatValue3;
      floatValue3 += this.amountArray2[index] + 4.0F;
    }

    int ordinal = this.menuTab.ordinal();
    float floatValue4 =
        this.amountArray[ordinal] - this.amount4 + this.amountArray2[ordinal] / 2.0F;
    if (!this.enabled) {
      this.dampedSpring.snapTo(floatValue4);
      this.dampedSpring2.snapTo(this.amountArray2[ordinal]);
      this.enabled = true;
    } else {
      this.dampedSpring.setTarget(floatValue4);
      this.dampedSpring2.setTarget(this.amountArray2[ordinal]);
    }

    float value = this.dampedSpring.advance();
    float value2 = this.dampedSpring2.advance();
    float floatValue5 = Math.clamp(Math.abs(this.dampedSpring.getVelocity()) * 0.05F, 0.0F, 14.0F);
    float floatValue6 = this.amount4 + value - (value2 + floatValue5) / 2.0F;
    float floatValue7 = value2 + floatValue5;
    fontManager.render(
        floatValue6 + 11.0F,
        amount52 + amount62 - 3.0F,
        floatValue7 - 22.0F,
        2.0F,
        1.0F,
        instance.computeInt());

    for (int index2 = 0; index2 < this.menuTabArray.length; index2++) {
      boolean flag = index2 == ordinal;
      float value3 =
          this.animationIntensitySupportArray[index2].computeFloat(
              this.test(index2, floatValue, floatValue2)
                  || this.animationIntensitySupportArray[index2].computeFloat() > 0.05F
                      && this.moduleSearchSupport6Array[index2].test(floatValue, floatValue2));
      int intValue =
          flag
              ? instance.computeInt16()
              : ArgbColors.lerp(instance.computeInt18(), instance.computeInt17(), value3);
      float floatValue8 = this.amountArray[index2] + 2.0F;
      float floatValue9 = amount52 + 11.0F;
      float floatValue10 = this.amountArray2[index2] - 4.0F;
      float floatValue11 = amount62 - 24.0F;
      int index3 = index2;
      float floatValue12 = AnimationPreferences.isEnabled() ? value3 * 12.0F : 0.0F;
      ModuleSearchSupport7.render(
          fontManager,
          this.moduleSearchSupport6Array[index2],
          floatValue8,
          floatValue9,
          floatValue10,
          floatValue11,
          2.0F + floatValue12,
          0.0F,
          AnimationPreferences.isEnabled() ? -value3 * 0.018F : 0.0F,
          6.0F,
          3.0F,
          value3,
          () -> {
            fontManager.render(
                floatValue8,
                floatValue9,
                floatValue10,
                floatValue11,
                6.0F,
                flag
                    ? instance.computeInt12()
                    : ArgbColors.lerp(instance.computeInt5(), instance.computeInt11(), value3),
                instance.computeInt6(),
                true);
            fontManager.render(
                floatValue8 + 0.5F,
                floatValue9 + 0.5F,
                floatValue10 - 1.0F,
                floatValue11 - 1.0F,
                5.5F,
                0.8F,
                flag
                    ? instance.computeInt(0.45F)
                    : ArgbColors.withOpacity(-1, 0.14F + value3 * 0.15F),
                184549375,
                true);
            fontManager.computeFloat2(
                this.menuTabArray[index3].getLabel(),
                floatValue8 + floatValue10 / 2.0F,
                amount52 + amount62 / 2.0F - 1.0F,
                13.0F,
                intValue);
          });
    }
  }

  public boolean test(int index, float floatValue, float floatValue2) {
    ClientData3 clientData3 =
        this.moduleSearchSupport6Array[index].getClientData3(floatValue, floatValue2);
    floatValue = clientData3.amount();
    floatValue2 = clientData3.amount2();
    return floatValue >= this.amountArray[index]
        && floatValue <= this.amountArray[index] + this.amountArray2[index]
        && floatValue2 >= this.amount5
        && floatValue2 <= this.amount5 + this.amount6;
  }

  public boolean test(float floatValue, float floatValue2, Consumer consumer) {
    for (int index = 0; index < this.menuTabArray.length; index++) {
      if (this.test(index, floatValue, floatValue2)) {
        this.apply(this.menuTabArray[index], consumer);
        return true;
      }
    }

    return false;
  }

  public ClientData3 getClientData3(MenuTab menuTab2) {
    int ordinal = menuTab2.ordinal();
    return this.moduleSearchSupport6Array[ordinal].getClientData33(
        (this.amountArray2[ordinal] - 4.0F) / 2.0F, (this.amount6 - 24.0F) / 2.0F);
  }

  public void apply(MenuTab menuTab2, Consumer consumer) {
    if (menuTab2 != null && menuTab2 != this.menuTab) {
      ClientAudio.apply((float) menuTab2.ordinal() / Math.max(1, this.menuTabArray.length - 1));
      this.menuTab = menuTab2;
      consumer.accept(this.menuTab);
    }
  }
}
