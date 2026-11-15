package bunnythekenzo.client.hud;

import java.util.ArrayList;
import java.util.List;
import bunnythekenzo.client.DampedSpring;
import bunnythekenzo.client.render.FontManager;
import bunnythekenzo.client.setting.SettingEntry;

public abstract class HudWidget {
  public static final float amount = 0.5F;
  public static final float amount2 = 2.5F;
  public final String string;
  public final String string2;
  public final String string3;
  public final float amount3;
  public final float amount4;
  public final boolean enabled;
  public final List list = new ArrayList();
  public boolean enabled2;
  public float amount5;
  public float amount6;
  public float amount7 = 1.0F;
  public final DampedSpring dampedSpring = new DampedSpring(210.0F, 25.0F, 0.0F);
  public float amount8;
  public float amount9;

  public HudWidget(
      String string4,
      String string22,
      String string32,
      boolean enabled3,
      float amount32,
      float amount42) {
    this.string = string4;
    this.string2 = string22;
    this.string3 = string32;
    this.enabled = enabled3;
    this.enabled2 = enabled3;
    this.amount3 = amount32;
    this.amount4 = amount42;
    this.amount5 = amount32;
    this.amount6 = amount42;
  }

  public String getString2() {
    return this.string;
  }

  public String getString22() {
    return this.string2;
  }

  public String getString3() {
    return this.string3;
  }

  public abstract float computeFloat(FontManager fontManager);

  public abstract float computeFloat2(FontManager fontManager);

  public abstract void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4);

  public boolean test3() {
    return true;
  }

  public final boolean test4() {
    return this.amount5 > 0.5F;
  }

  public final boolean test5() {
    return this.amount6 > 0.5F;
  }

  public SettingEntry getSettingEntry(SettingEntry settingEntry) {
    this.list.add(settingEntry);
    return settingEntry;
  }

  public List getList2() {
    return this.list;
  }

  public boolean isEnabled2() {
    return this.enabled2;
  }

  public void setEnabled2(boolean enabled22) {
    this.enabled2 = enabled22;
  }

  public boolean test6() {
    this.enabled2 = !this.enabled2;
    return this.enabled2;
  }

  public float computeFloat2() {
    this.dampedSpring.setTarget(this.isEnabled2() ? 1.0F : 0.0F);
    return Math.clamp(this.dampedSpring.advance(), 0.0F, 1.0F);
  }

  public boolean test7() {
    return this.enabled2 || this.computeFloat2() > 0.01F;
  }

  public float getAmount5() {
    return this.amount5;
  }

  public float getAmount6() {
    return this.amount6;
  }

  public void apply2(float floatValue, float floatValue2) {
    this.amount5 = Math.clamp(floatValue, 0.0F, 1.0F);
    this.amount6 = Math.clamp(floatValue2, 0.0F, 1.0F);
  }

  public float getAmount7() {
    return this.amount7;
  }

  public void apply(float floatValue) {
    this.amount7 = Math.clamp(floatValue, 0.5F, 2.5F);
  }

  public boolean test(float floatValue) {
    float amount72 = this.amount7;
    this.apply(Math.round((this.amount7 + floatValue) * 100.0F) / 100.0F);
    return this.amount7 != amount72;
  }

  public void update2() {
    this.amount5 = this.amount3;
    this.amount6 = this.amount4;
    this.amount7 = 1.0F;
    this.enabled2 = this.enabled;
  }

  public void update3() {
    this.amount5 = this.amount3;
    this.amount6 = this.amount4;
    this.amount7 = 1.0F;
  }

  public void apply3(float amount82, float amount92) {
    this.amount8 = amount82;
    this.amount9 = amount92;
  }

  public float getAmount8() {
    return this.amount8;
  }

  public float getAmount9() {
    return this.amount9;
  }
}
