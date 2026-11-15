package bunnythekenzo.client.hud;

import java.util.function.BooleanSupplier;
import bunnythekenzo.client.HudElementContract;
import bunnythekenzo.client.HudElementSupport;
import bunnythekenzo.client.HudElementSupport2;
import bunnythekenzo.client.module.Module;
import bunnythekenzo.client.setting.BooleanSetting;

public abstract class HudElement extends HudWidget {
  public final HudElementContract hudElementContract;
  public final BooleanSupplier booleanSupplier;

  public HudElement(
      String text,
      String text2,
      String text3,
      float floatValue,
      float floatValue2,
      HudElementContract hudElementContract2) {
    this(text, text2, text3, floatValue, floatValue2, hudElementContract2, null);
  }

  public HudElement(
      String text,
      String text2,
      String text3,
      float floatValue,
      float floatValue2,
      HudElementContract hudElementContract2,
      BooleanSupplier booleanSupplier2) {
    super(
        text,
        text2,
        text3,
        hudElementContract2 == null || hudElementContract2.test(),
        floatValue,
        floatValue2);
    this.hudElementContract = hudElementContract2;
    this.booleanSupplier = booleanSupplier2;
  }

  public static HudElementContract getHudElementContract(BooleanSetting booleanSetting) {
    return new HudElementSupport(booleanSetting);
  }

  public static HudElementContract getHudElementContract(Module module) {
    return new HudElementSupport2(module);
  }

  @Override
  public boolean isEnabled2() {
    return this.hudElementContract != null ? this.hudElementContract.test() : super.isEnabled2();
  }

  @Override
  public void setEnabled2(boolean flag) {
    if (this.hudElementContract != null) {
      this.hudElementContract.apply(flag);
    } else {
      super.setEnabled2(flag);
    }
  }

  @Override
  public boolean test6() {
    this.setEnabled2(!this.isEnabled2());
    return this.isEnabled2();
  }

  @Override
  public void update2() {
    super.update2();
    if (this.hudElementContract != null) {
      this.hudElementContract.update();
    }
  }

  @Override
  public boolean test3() {
    return this.booleanSupplier == null || this.booleanSupplier.getAsBoolean();
  }

  @Deprecated
  public final String getString4() {
    return this.getString2();
  }

  @Deprecated
  public final float computeFloat3() {
    return this.getAmount7();
  }

  @Deprecated
  public final float computeFloat4() {
    return this.getAmount5();
  }

  @Deprecated
  public final float computeFloat5() {
    return this.getAmount6();
  }

  public final boolean test8() {
    return this.isEnabled2() && this.test3();
  }

  public boolean test(float floatValue, float floatValue2) {
    return false;
  }

  public void apply(float floatValue, float floatValue2) {}

  public void update() {}

  public boolean test(float floatValue, float floatValue2, double doubleValue) {
    return false;
  }
}
