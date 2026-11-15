package bunnythekenzo.client;

import bunnythekenzo.client.render.FontManager;
import bunnythekenzo.client.ui.MenuInteraction;

public final class ModuleSearchSupport5 {
  public static final float amount = 30.0F;
  public static final float amount2 = 16.0F;
  public static final float amount3 = 16.0F;
  public static final float amount4 = 22.0F;
  public static final float amount5 = 12.0F;
  public static final float amount6 = 10.0F;
  public static final float amount7 = 8.0F;
  public static final float amount8 = 6.0F;
  public final GuiModuleEntry guiModuleEntry;
  public final boolean enabled;
  public final AnimationIntensitySupport animationIntensitySupport =
      new AnimationIntensitySupport();
  public final AnimationIntensitySupport animationIntensitySupport2 =
      new AnimationIntensitySupport(AnimationIntensity.SILENT);
  public final AnimationIntensitySupport animationIntensitySupport3 =
      new AnimationIntensitySupport(AnimationIntensity.SUBTLE);
  public final ClickRippleEffects clickRippleEffects = new ClickRippleEffects();
  public final ToggleSwitchAnimation toggleSwitchAnimation;
  public final DampedSpring dampedSpring = DampedSpring.createQuick();
  public final DampedSpring dampedSpring2 = DampedSpring.createQuick();
  public float amount9;
  public float amount10;
  public float amount11;
  public float amount12;
  public float amount13;
  public String string;
  public boolean enabled2;
  public boolean enabled3;
  public final ModuleSearchSupport6 moduleSearchSupport6 = new ModuleSearchSupport6();
  public final DampedSpring dampedSpring3 = DampedSpring.createDefault();
  public final DampedSpring dampedSpring4 = DampedSpring.createDefault();
  public final DampedSpring dampedSpring5 = DampedSpring.createDefault();
  public float amount14;
  public float amount15;

  public ModuleSearchSupport5(GuiModuleEntry guiModuleEntry2, boolean enabled4) {
    this.guiModuleEntry = guiModuleEntry2;
    this.enabled = enabled4;
    this.toggleSwitchAnimation = new ToggleSwitchAnimation(guiModuleEntry2.test());
    this.dampedSpring.snapTo(guiModuleEntry2.test() ? 1.0F : 0.0F);
  }

  public GuiModuleEntry getGuiModuleEntry() {
    return this.guiModuleEntry;
  }

  public void apply(boolean flag) {
    this.dampedSpring2.setTarget(flag ? 1.0F : 0.0F);
  }

  public void setString(String string2) {
    this.string = string2;
  }

  public void setEnabled2(boolean enabled22) {
    this.enabled2 = enabled22;
  }

  public String getString() {
    boolean flag = this.animationIntensitySupport.computeFloat() > 0.5F;
    return flag && this.amount13 < 0.5F ? this.guiModuleEntry.getString2() : null;
  }

  public void render(
      FontManager fontManager,
      float amount92,
      float amount102,
      float amount112,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    this.amount9 = amount92;
    this.amount10 = amount102;
    this.amount11 = amount112;
    this.amount12 = 42.0F;
    this.enabled3 =
        floatValue3 > 0.98F
            && floatValue4 > 0.15F
            && Float.isFinite(floatValue)
            && Float.isFinite(floatValue2);
    if (floatValue3 * floatValue4 <= 0.004F) {
      this.update3();
      this.amount13 = 0.0F;
      this.amount15 = 0.0F;
    } else {
      ColorPalette instance = ColorPalette.getInstance();
      int intValue = this.enabled ? instance.computeInt23() : instance.computeInt();
      ClientData3 clientData3 = this.moduleSearchSupport6.getClientData3(floatValue, floatValue2);
      float floatValue5 = clientData3.amount();
      float floatValue6 = clientData3.amount2();
      boolean flag =
          this.enabled3
              && (this.test2(floatValue5, floatValue6)
                  || this.dampedSpring5.getTarget() > 0.0F
                      && this.moduleSearchSupport6.test(floatValue, floatValue2));
      boolean bl = flag && this.test3(floatValue5, floatValue6);
      boolean bl2 = flag && this.test4(floatValue5, floatValue6);
      float value = this.animationIntensitySupport.computeFloat(flag && !bl && !bl2);
      this.animationIntensitySupport2.computeFloat(bl);
      float value2 = this.animationIntensitySupport3.computeFloat(bl2);
      float floatValue7 = Math.clamp(this.dampedSpring.advance(), 0.0F, 1.0F);
      float floatValue8 = Math.clamp(this.dampedSpring2.advance(), 0.0F, 1.0F);
      this.dampedSpring5.setTarget(flag ? 1.0F : 0.0F);
      float float2 = this.dampedSpring5.advanceClamped();
      float floatValue9 = Float.isFinite(floatValue5) ? floatValue5 : floatValue;
      float floatValue10 = Float.isFinite(floatValue6) ? floatValue6 : floatValue2;
      this.dampedSpring3.setTarget(
          flag && AnimationPreferences.isEnabled()
              ? Math.clamp((floatValue9 - amount92 - amount112 / 2.0F) / amount112, -0.5F, 0.5F)
                  * 0.055F
              : 0.0F);
      this.dampedSpring4.setTarget(
          flag && AnimationPreferences.isEnabled()
              ? Math.clamp(
                      (floatValue10 - amount102 - this.amount12 / 2.0F) / this.amount12,
                      -0.5F,
                      0.5F)
                  * -0.075F
              : 0.0F);
      fontManager.update2();
      fontManager.apply3(floatValue3 * floatValue4);
      fontManager.apply((1.0F - floatValue3) * 16.0F, (1.0F - floatValue3) * 10.0F);
      float floatValue11 =
          AnimationPreferences.isEnabled()
              ? float2 * 21.0F - this.animationIntensitySupport.computeFloat2() * 7.0F
              : 0.0F;
      ModuleSearchSupport7.render(
          fontManager,
          this.moduleSearchSupport6,
          amount92,
          amount102,
          amount112,
          this.amount12,
          4.0F + floatValue8 * 2.0F + floatValue11 - (1.0F - floatValue3) * 18.0F,
          this.dampedSpring3.advance(),
          -0.012F + this.dampedSpring4.advance() - (1.0F - floatValue3) * 0.08F,
          9.0F,
          5.0F,
          float2,
          () -> {
            fontManager.render(
                amount92,
                amount102,
                amount112,
                this.amount12,
                9.0F,
                PanelColors.computeInt(intValue, floatValue8, float2),
                -15002333,
                true);
            if (!this.clickRippleEffects.test()) {
              fontManager.update2();
              fontManager.render(amount92, amount102, amount112, this.amount12);
              this.clickRippleEffects.apply(
                  (java.util.function.Consumer<bunnythekenzo.client.CollectionData5>)
                      (collectionData5 -> {
                        fontManager.render2(
                            collectionData5.amount(),
                            collectionData5.amount2(),
                            ((bunnythekenzo.client.CollectionData5) (collectionData5)).amount3(),
                            ArgbColors.withOpacity(
                                intValue,
                                0.11F
                                    * ((bunnythekenzo.client.CollectionData5) (collectionData5))
                                        .amount4()));
                      }));
              fontManager.update3();
            }

            int value3 =
                ArgbColors.lerp(
                    ArgbColors.withOpacity(-1, 0.07F + 0.05F * value),
                    ArgbColors.withOpacity(intValue, 0.75F),
                    floatValue8);
            fontManager.render(
                amount92 + 0.5F,
                amount102 + 0.5F,
                amount112 - 1.0F,
                this.amount12 - 1.0F,
                8.5F,
                1.0F,
                ArgbColors.lerp(value3, 1711276031, 0.25F + float2 * 0.25F),
                value3,
                true);
            if (floatValue7 > 0.01F) {
              float floatValue12 = (this.amount12 - 18.0F) * floatValue7;
              fontManager.render(
                  amount92 + 1.5F,
                  amount102 + this.amount12 / 2.0F - floatValue12 / 2.0F,
                  2.5F,
                  floatValue12,
                  1.25F,
                  ArgbColors.withOpacity(intValue, floatValue7));
            }

            float floatValue13 = amount102 + this.amount12 / 2.0F;
            this.render(fontManager, instance, floatValue13, value2);
            float floatValue14 = amount92 + 12.0F;
            float floatValue15 = this.amount14 - 6.0F;
            int value4 =
                ArgbColors.lerp(
                    instance.computeInt17(), instance.computeInt16(), Math.max(value, floatValue8));
            value4 = ArgbColors.lerp(value4, intValue, floatValue7 * 0.8F);
            float value5 =
                fontManager.computeFloat(
                    this.guiModuleEntry.getString(),
                    floatValue14,
                    floatValue13,
                    13.5F,
                    value4,
                    floatValue15 - floatValue14);
            float floatValue16 = floatValue15;
            float floatValue17 = floatValue14 + value5 + 20.0F;
            float floatValue18 = floatValue16 - floatValue17;
            this.amount13 = Math.clamp((floatValue18 - 70.0F) / 50.0F, 0.0F, 1.0F);
            if (this.amount13 > 0.01F) {
              fontManager.update2();
              fontManager.apply3(this.amount13);
              fontManager.computeFloat(
                  this.string != null ? this.string : this.guiModuleEntry.getString2(),
                  floatValue16,
                  floatValue13,
                  11.0F,
                  instance.computeInt19(),
                  floatValue18,
                  4);
              fontManager.update3();
            }

            this.toggleSwitchAnimation.render(
                fontManager,
                amount92 + amount112 - 30.0F - 10.0F,
                amount102 + (this.amount12 - 16.0F) / 2.0F,
                30.0F,
                16.0F,
                Math.max(value, this.animationIntensitySupport2.computeFloat()),
                intValue);
          });
      fontManager.update3();
    }
  }

  public float computeFloat(FontManager fontManager) {
    if (this.enabled2) {
      return Math.max(22.0F, fontManager.computeFloat("Press…", 9.5F) + 12.0F);
    } else {
      return !this.guiModuleEntry.test3()
          ? 22.0F
          : Math.max(
              22.0F,
              fontManager.computeFloat(KeyNames.getString(this.guiModuleEntry.computeInt()), 9.5F)
                  + 12.0F);
    }
  }

  public float computeFloat2(FontManager fontManager) {
    return this.amount9 + this.amount11 - 10.0F - 30.0F - 8.0F - this.computeFloat(fontManager);
  }

  public void render(
      FontManager fontManager, ColorPalette colorPalette, float floatValue, float floatValue2) {
    float value = this.computeFloat(fontManager);
    float float2 = this.computeFloat2(fontManager);
    float floatValue3 = floatValue - 8.0F;
    this.amount14 = float2;
    this.amount15 = value;
    float floatValue4 =
        this.enabled2 ? 0.55F + 0.45F * (float) Math.sin(System.nanoTime() / 1.0E9 * 4.5) : 1.0F;
    int intValue =
        this.enabled2
            ? ArgbColors.withOpacity(colorPalette.computeInt(), 0.2F * floatValue4)
            : ArgbColors.withOpacity(-1, 0.05F + 0.06F * floatValue2);
    int intValue2 =
        this.enabled2
            ? ArgbColors.withOpacity(colorPalette.computeInt(), 0.5F + 0.4F * floatValue4)
            : ArgbColors.withOpacity(-1, 0.1F + 0.18F * floatValue2);
    fontManager.render(float2, floatValue3, value, 16.0F, 5.0F, intValue);
    fontManager.render(float2, floatValue3, value, 16.0F, 5.0F, 1.0F, intValue2);
    if (this.enabled2) {
      fontManager.computeFloat2(
          "Press…", float2 + value / 2.0F, floatValue, 9.5F, colorPalette.computeInt());
    } else if (this.guiModuleEntry.test3()) {
      fontManager.computeFloat2(
          KeyNames.getString(this.guiModuleEntry.computeInt()),
          float2 + value / 2.0F,
          floatValue,
          9.5F,
          ArgbColors.lerp(colorPalette.computeInt18(), colorPalette.computeInt16(), floatValue2));
    } else {
      int value2 =
          ArgbColors.lerp(colorPalette.computeInt19(), colorPalette.computeInt17(), floatValue2);
      fontManager.render(
          float2 + value / 2.0F - 4.5F, floatValue - 4.0F, 9.0F, 8.0F, 2.0F, 1.0F, value2);
      fontManager.render(float2 + value / 2.0F - 2.0F, floatValue - 0.5F, 4.0F, 1.0F, 0.5F, value2);
    }
  }

  public boolean test(float floatValue, float floatValue2) {
    ClientData3 clientData3 = this.moduleSearchSupport6.getClientData3(floatValue, floatValue2);
    return this.enabled3 && this.test2(clientData3.amount(), clientData3.amount2());
  }

  public boolean test2(float floatValue, float floatValue2) {
    return floatValue >= this.amount9
        && floatValue <= this.amount9 + this.amount11
        && floatValue2 >= this.amount10
        && floatValue2 <= this.amount10 + this.amount12;
  }

  public boolean test3(float floatValue, float floatValue2) {
    float floatValue3 = this.amount9 + this.amount11 - 30.0F - 10.0F;
    float floatValue4 = this.amount10 + (this.amount12 - 16.0F) / 2.0F;
    return floatValue >= floatValue3 - 6.0F
        && floatValue <= floatValue3 + 30.0F + 6.0F
        && floatValue2 >= floatValue4 - 8.0F
        && floatValue2 <= floatValue4 + 16.0F + 8.0F;
  }

  public boolean test4(float floatValue, float floatValue2) {
    return this.amount15 > 0.0F
        && floatValue >= this.amount14 - 3.0F
        && floatValue <= this.amount14 + this.amount15 + 3.0F
        && floatValue2 >= this.amount10 + this.amount12 / 2.0F - 8.0F - 6.0F
        && floatValue2 <= this.amount10 + this.amount12 / 2.0F + 8.0F + 6.0F;
  }

  public MenuInteraction getMenuInteraction(float floatValue, float floatValue2) {
    if (!this.enabled3) {
      return MenuInteraction.NONE;
    } else {
      ClientData3 clientData3 = this.moduleSearchSupport6.getClientData3(floatValue, floatValue2);
      floatValue = clientData3.amount();
      floatValue2 = clientData3.amount2();
      if (!this.test2(floatValue, floatValue2)) {
        return MenuInteraction.NONE;
      } else if (this.test4(floatValue, floatValue2)) {
        return MenuInteraction.BIND;
      } else {
        return this.test3(floatValue, floatValue2)
            ? MenuInteraction.TOGGLE
            : MenuInteraction.SELECT;
      }
    }
  }

  public void apply(float floatValue, float floatValue2) {
    this.animationIntensitySupport.apply(true);
    ClientData3 clientData3 = this.moduleSearchSupport6.getClientData3(floatValue, floatValue2);
    this.clickRippleEffects.render(
        clientData3.amount(), clientData3.amount2(), this.amount11 * 0.9F);
  }

  public void update() {
    this.animationIntensitySupport.apply(false);
  }

  public void update2() {
    boolean flag = this.guiModuleEntry.test2();
    this.toggleSwitchAnimation.apply(flag);
    this.dampedSpring.setTarget(flag ? 1.0F : 0.0F);
    ClientAudio.apply(flag);
  }

  public void update3() {
    this.enabled3 = false;
    this.moduleSearchSupport6.update();
    this.dampedSpring5.setTarget(0.0F);
    this.dampedSpring3.setTarget(0.0F);
    this.dampedSpring4.setTarget(0.0F);
    this.animationIntensitySupport.update();
    this.animationIntensitySupport2.update();
    this.animationIntensitySupport3.update();
  }

  public ClientData3 getClientData3(boolean flag) {
    return this.moduleSearchSupport6.getClientData33(
        flag ? this.amount11 - 15.0F - 10.0F : 25.0F, this.amount12 / 2.0F);
  }

  public float computeFloat() {
    return this.moduleSearchSupport6.computeFloat();
  }
}
