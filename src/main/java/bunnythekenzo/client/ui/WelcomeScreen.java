package bunnythekenzo.client.ui;

import java.util.function.LongSupplier;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.input.CharInput;
import net.minecraft.client.input.KeyInput;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.text.Text;
import bunnythekenzo.client.AnimationPreferences;
import bunnythekenzo.client.ArgbColors;
import bunnythekenzo.client.ClientAudio;
import bunnythekenzo.client.ColorPalette;
import bunnythekenzo.client.GuiDrawing;
import bunnythekenzo.client.GuiPreferences;
import bunnythekenzo.client.ProfileNames;
import bunnythekenzo.client.ProfileStorage;
import bunnythekenzo.client.render.FontManager;
import bunnythekenzo.client.render.LogoRenderer;
import bunnythekenzo.client.render.OverlayRenderer;

public final class WelcomeScreen extends Screen implements ClientScreen {
  public static final float amount = 860.0F;
  public static final float amount2 = 552.0F;
  public static final float amount3 = 7.4F;
  public static final float amount4 = 0.55F;
  public final Screen screen;
  public final ProfileStorage profileStorage = ProfileStorage.getProfileStorage();
  public final ProfileNames profileNames =
      new ProfileNames(this.profileStorage.getProfileDefaults().string2);
  public final LogoRenderer logoRenderer = new LogoRenderer();
  public final LongSupplier longSupplier;
  public long timestamp = -1L;
  public long timestamp2;
  public SoundInstance soundInstance;
  public boolean enabled;
  public boolean enabled2;
  public boolean enabled3;
  public boolean enabled4;
  public boolean enabled5;
  public float amount5;
  public float amount6;
  public float amount7 = 1.0F;
  public float amount8;
  public float amount9;
  public float amount10;
  public float amount11;
  public float amount12;
  public float amount13;
  public float amount14;
  public float amount15;
  public float amount16;
  public boolean enabled6;
  public int count;
  public int count2 = GuiPreferences.getGuiPreferences().guiStyleControl.getSelectedIndex();
  public String string;

  public WelcomeScreen(Screen screen2) {
    this(screen2, System::nanoTime);
  }

  public WelcomeScreen(Screen screen2, LongSupplier longSupplier2) {
    super(Text.literal("Welcome to bunnythekenzo"));
    this.screen = screen2;
    this.longSupplier = longSupplier2;
  }

  @Override
  public void init() {
    this.profileNames.setEnabled(this.enabled5 && this.count == 0);
  }

  @Override
  public boolean shouldPause() {
    return true;
  }

  @Override
  public void renderBackground(
      DrawContext drawContext, int intValue, int intValue2, float floatValue) {}

  @Override
  public void render(DrawContext drawContext, int intValue, int intValue2, float floatValue) {
    drawContext.fill(0, 0, this.width, this.height, -16184560);
  }

  public float computeFloat() {
    if (!this.enabled2 && AnimationPreferences.isEnabled()) {
      return this.timestamp < 0L
          ? 0.0F
          : Math.max(0.0F, (float) (this.longSupplier.getAsLong() - this.timestamp) / 1.0E9F);
    } else {
      return 7.4F;
    }
  }

  public boolean test() {
    return this.computeFloat() >= 7.4F;
  }

  public boolean isEnabled3() {
    return this.enabled3;
  }

  @Override
  public void tick() {
    if (this.enabled3
        && (!AnimationPreferences.isEnabled()
            || (float) (this.longSupplier.getAsLong() - this.timestamp2) / 1.0E9F >= 0.55F)) {
      this.update();
    }
  }

  @Override
  public void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    this.amount7 =
        Math.max(
            0.1F,
            Math.min(
                1.0F, Math.min((floatValue3 - 28.0F) / 860.0F, (floatValue4 - 28.0F) / 552.0F)));
    float value = OverlayRenderer.computeFloat();
    this.amount5 = Math.round((floatValue3 - 860.0F * this.amount7) / 2.0F * value) / value;
    this.amount6 = Math.round((floatValue4 - 552.0F * this.amount7) / 2.0F * value) / value;
    float floatValue5 = Math.min(floatValue3 * 1.58F, floatValue4 * 2.4F);
    if (!this.enabled) {
      this.logoRenderer.render(
          fontManager, floatValue5 * OverlayRenderer.computeFloat(), -0.62F, 0.21F, -24.0F, 1.0F);
      this.enabled = true;
    }

    if (this.timestamp < 0L && this.client.getOverlay() == null) {
      this.timestamp = this.longSupplier.getAsLong();
      if (this.profileStorage.getProfileDefaults().enabled2 && !this.enabled2) {
        this.soundInstance = ClientAudio.getSoundInstance();
      }
    }

    this.amount8 = this.computeFloat();
    if (this.test() && !this.enabled5) {
      this.enabled5 = true;
      this.profileNames.setEnabled(this.count == 0);
    }

    float floatValue6 = computeFloat3(this.amount8, 0.08F, 1.56F);
    float floatValue7 = computeFloat2(this.amount8, 3.45F, 5.65F);
    float floatValue8 = Math.min(floatValue3 * 0.72F, floatValue4 * 1.23F);
    this.amount11 =
        computeFloat(
            computeFloat(floatValue5, floatValue8, floatValue6),
            346.0F * this.amount7,
            floatValue7);
    this.amount9 =
        computeFloat(
            floatValue3 * 0.52F + floatValue3 * 0.08F * (1.0F - floatValue6),
            this.amount5 + 177.0F * this.amount7,
            floatValue7);
    this.amount10 =
        computeFloat(
            floatValue4 * 0.445F + floatValue4 * 0.06F * (1.0F - floatValue6),
            this.amount6 + 246.0F * this.amount7,
            floatValue7);
    if (AnimationPreferences.isEnabled() && this.test() && !this.enabled6) {
      this.amount15 =
          Math.clamp(
                  (floatValue - (this.amount5 + 177.0F * this.amount7)) / (350.0F * this.amount7),
                  -1.0F,
                  1.0F)
              * 0.055F;
      this.amount16 =
          Math.clamp(
                  (floatValue2 - (this.amount6 + 246.0F * this.amount7)) / (350.0F * this.amount7),
                  -1.0F,
                  1.0F)
              * 0.035F;
    } else {
      this.amount15 = this.amount16 = 0.0F;
    }

    float floatValue9 =
        computeFloat(-0.62F, -0.16F, floatValue6)
            + computeFloat2(this.amount8, 1.56F, 3.45F) * 0.08F;
    floatValue9 = computeFloat(floatValue9, -0.25F, floatValue7) + this.amount13 + this.amount15;
    float floatValue10 =
        computeFloat(0.21F, 0.095F, floatValue6) + floatValue7 * 0.045F + this.amount16;
    float floatValue11 = computeFloat(-26.0F, 32.0F, computeFloat2(this.amount8, 0.36F, 3.15F));
    float floatValue12 = computeFloat(0.32F, 1.0F, computeFloat2(this.amount8, 0.0F, 1.06F));
    this.logoRenderer.render(
        fontManager,
        this.amount11 * OverlayRenderer.computeFloat(),
        floatValue9,
        floatValue10,
        floatValue11,
        floatValue12);
    this.amount12 =
        this.enabled3
            ? computeFloat2(
                (float) (this.longSupplier.getAsLong() - this.timestamp2) / 1.0E9F, 0.0F, 0.55F)
            : 0.0F;
  }

  @Override
  public void render2(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    ColorPalette instance = ColorPalette.getInstance();
    float floatValue5 = computeFloat2(this.amount8, 3.45F, 5.65F);
    float floatValue6 = computeFloat2(this.amount8, 0.08F, 1.56F);
    float floatValue7 =
        this.test() && !this.enabled3 ? (floatValue - this.amount5) / this.amount7 : Float.NaN;
    float floatValue8 =
        this.test() && !this.enabled3 ? (floatValue2 - this.amount6) / this.amount7 : Float.NaN;
    fontManager.update2();
    fontManager.apply3(1.0F - this.amount12);
    fontManager.apply(0.0F, -this.amount12 * 18.0F);
    fontManager.setEnabled3(true);
    fontManager.render2(
        0.0F,
        0.0F,
        floatValue3,
        floatValue4,
        floatValue3 * 0.46F,
        floatValue4 * 0.37F,
        0.0F,
        Math.max(floatValue3, floatValue4) * 0.58F,
        ArgbColors.withOpacity(-13419696, 0.24F * floatValue6),
        592656);
    if (floatValue5 < 1.0F) {
      this.render(fontManager, floatValue3, floatValue4, instance);
    }

    if (this.amount8 > 3.45F) {
      fontManager.update2();
      fontManager.apply(this.amount5, this.amount6);
      fontManager.apply(this.amount7);
      float floatValue9 = computeFloat2(this.amount8, 3.45F, 4.45F);
      float floatValue10 = 252.0F * (1.0F - floatValue5);
      float floatValue11 = computeFloat(356.0F, 860.0F, floatValue5);
      float floatValue12 = (1.0F - floatValue5) * 14.0F;
      fontManager.update2();
      fontManager.apply3(floatValue9);
      fontManager.render(
          floatValue10, floatValue12, floatValue11, 552.0F, 16.0F, 32.0F, 12.0F, 2013265920);
      fontManager.render(
          floatValue10, floatValue12, floatValue11, 552.0F, 16.0F, -14999510, -15657699, true);
      fontManager.render(
          floatValue10,
          floatValue12,
          floatValue11,
          552.0F,
          16.0F,
          1.0F,
          1349153927,
          675104344,
          true);
      fontManager.update3();
      fontManager.update2();
      fontManager.render(floatValue10 + 1.0F, floatValue12 + 1.0F, floatValue11 - 2.0F, 550.0F);
      this.render2(fontManager, floatValue7, floatValue8, instance);
      fontManager.update3();
      fontManager.update3();
    }

    fontManager.update2();
    fontManager.apply3(computeFloat2(this.amount8, 0.0F, 0.36F));
    fontManager.update2();
    fontManager.apply(this.amount9, this.amount10 + this.amount11 * 0.245F);
    fontManager.apply2(1.0F, 0.12F);
    fontManager.render3(0.0F, 0.0F, this.amount11 * 0.16F, this.amount11 * 0.21F, -1879048192);
    fontManager.update3();
    this.logoRenderer.render(fontManager, this.amount9, this.amount10, this.amount11);
    fontManager.update3();
    if (!this.test()) {
      this.render(fontManager, floatValue, floatValue2, floatValue3, floatValue4, instance);
    }

    fontManager.update3();
  }

  public void render(
      FontManager fontManager, float floatValue, float floatValue2, ColorPalette colorPalette) {
    float floatValue3 =
        computeFloat2(this.amount8, 0.68F, 1.56F)
            * (1.0F - computeFloat2(this.amount8, 3.15F, 3.8F));
    fontManager.update2();
    fontManager.apply3(floatValue3);
    fontManager.computeFloat2(
        "S I X   S E V E N",
        floatValue / 2.0F,
        floatValue2 * 0.73F + 12.0F * (1.0F - floatValue3),
        20.0F,
        -1446411);
    fontManager.update3();
    float floatValue4 =
        computeFloat2(this.amount8, 1.56F, 2.35F)
            * (1.0F - computeFloat2(this.amount8, 3.15F, 3.8F));
    fontManager.update2();
    fontManager.apply3(floatValue4);
    fontManager.computeFloat2(
        "A new perspective.", floatValue / 2.0F, floatValue2 * 0.73F + 36.0F, 14.0F, -5195318);
    fontManager.render2(
        floatValue / 2.0F - 17.0F,
        floatValue2 * 0.73F + 61.0F,
        floatValue / 2.0F + 17.0F,
        floatValue2 * 0.73F + 61.0F,
        1.0F,
        colorPalette.computeInt(0.7F));
    fontManager.update3();
  }

  public void render2(
      FontManager fontManager, float floatValue, float floatValue2, ColorPalette colorPalette) {
    this.render(fontManager, 5.1F, 5.85F, 0.0F);
    fontManager.computeFloat("bunnythekenzo", 32.0F, 42.0F, 24.0F, colorPalette.computeInt16());
    fontManager.computeFloat("Built around you.", 33.0F, 72.0F, 13.0F, -5195318);
    fontManager.update3();
    this.render(fontManager, 5.2F, 6.25F, 0.0F);
    fontManager.render(32.0F, 376.0F, 32.0F, 3.0F, 1.0F, colorPalette.getCount());
    fontManager.computeFloat("Your client.", 32.0F, 410.0F, 28.0F, -986118);
    fontManager.computeFloat("Your rules.", 32.0F, 444.0F, 28.0F, -986118);
    fontManager.computeFloat("A space that feels like you.", 33.0F, 483.0F, 13.0F, -5195318);
    fontManager.update3();
    this.render(fontManager, 4.5F, 5.65F, 0.0F);
    fontManager.render2(355.0F, 32.0F, 355.0F, 520.0F, 1.0F, 385875967);
    fontManager.update3();
    this.render(fontManager, 5.25F, 6.1F, 16.0F);
    fontManager.computeFloat(
        "WELCOME TO BUNNYTHEKENZO", 391.0F, 53.0F, 12.0F, colorPalette.computeInt());
    fontManager.computeFloat("Make it yours.", 391.0F, 110.0F, 40.0F, colorPalette.computeInt16());
    fontManager.computeFloat("Choose a name. Pick your layout.", 393.0F, 156.0F, 15.0F, -3945511);
    fontManager.computeFloat(
        "Settle in and make yourself at home.", 393.0F, 181.0F, 14.0F, -5852990);
    fontManager.update3();
    this.render(fontManager, 5.65F, 6.5F, 15.0F);
    fontManager.computeFloat("Your name", 393.0F, 236.0F, 14.0F, colorPalette.computeInt16());
    fontManager.computeFloat3("Just for this client", 821.0F, 236.0F, 12.0F, -5852990);
    this.profileNames.render(fontManager, 391.0F, 256.0F, 430.0F, 40.0F, 16.0F);
    fontManager.computeFloat(
        this.string == null ? "3–24 characters · letters, numbers, _ or -" : this.string,
        393.0F,
        313.0F,
        11.5F,
        this.string == null ? -5852990 : colorPalette.computeInt22());
    fontManager.update3();
    this.render(fontManager, 6.0F, 6.8F, 14.0F);
    fontManager.computeFloat("Your layout", 393.0F, 337.0F, 13.0F, colorPalette.computeInt16());
    this.render(fontManager, 0, "Panel", "One workspace", 391.0F, 350.0F, floatValue, floatValue2);
    this.render(
        fontManager, 1, "Dropdown", "Category panels", 613.0F, 350.0F, floatValue, floatValue2);
    fontManager.update3();
    this.render(fontManager, 6.35F, 7.15F, 12.0F);
    this.render(
        fontManager,
        "Continue  →",
        391.0F,
        422.0F,
        430.0F,
        43.0F,
        floatValue,
        floatValue2,
        true,
        this.profileNames.test());
    this.render(
        fontManager,
        "Maybe later",
        391.0F,
        477.0F,
        119.0F,
        29.0F,
        floatValue,
        floatValue2,
        false,
        true);
    this.render(
        fontManager,
        this.profileStorage.getProfileDefaults().enabled2 ? "Sound on" : "Sound off",
        723.0F,
        477.0F,
        98.0F,
        29.0F,
        floatValue,
        floatValue2,
        false,
        true);
    fontManager.update3();
    if (this.test() && this.count > 0 && this.count < 4) {
      float floatValue3 = this.count == 3 ? 721.0F : 389.0F;
      float floatValue4 = this.count == 1 ? 420.0F : 475.0F;
      float floatValue5 = this.count == 1 ? 434.0F : (this.count == 2 ? 123.0F : 102.0F);
      float floatValue6 = this.count == 1 ? 47.0F : 33.0F;
      fontManager.render(
          floatValue3,
          floatValue4,
          floatValue5,
          floatValue6,
          9.0F,
          1.0F,
          colorPalette.computeInt(0.75F));
    }
  }

  public void render(
      FontManager fontManager, float floatValue, float floatValue2, float floatValue3) {
    float floatValue4 = computeFloat3(this.amount8, floatValue, floatValue2);
    fontManager.update2();
    fontManager.apply3(floatValue4);
    fontManager.apply(0.0F, (1.0F - floatValue4) * floatValue3);
  }

  public void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      ColorPalette colorPalette) {
    float floatValue5 = 1.0F - computeFloat2(this.amount8, 6.9F, 7.4F);
    fontManager.update2();
    fontManager.apply3(floatValue5);
    float floatValue6 = Math.max(18.0F, Math.min(floatValue3, floatValue4) * 0.035F);
    fontManager.computeFloat("BUNNYTHEKENZO", floatValue6, floatValue6 + 8.0F, 13.0F, -3287581);
    fontManager.computeFloat3(
        "A NEW PERSPECTIVE", floatValue3 - floatValue6, floatValue6 + 8.0F, 11.0F, -5852990);
    this.render(
        fontManager,
        this.profileStorage.getProfileDefaults().enabled2 ? "Sound on  ·  M" : "Sound off  ·  M",
        floatValue6,
        floatValue4 - floatValue6 - 28.0F,
        122.0F,
        28.0F,
        floatValue,
        floatValue2,
        false,
        true);
    this.render(
        fontManager,
        "Skip intro  →",
        floatValue3 - floatValue6 - 124.0F,
        floatValue4 - floatValue6 - 28.0F,
        124.0F,
        28.0F,
        floatValue,
        floatValue2,
        false,
        true);
    float floatValue7 = Math.clamp(this.amount8 / 7.4F, 0.0F, 1.0F);
    fontManager.render(
        floatValue3 / 2.0F - 40.0F,
        floatValue4 - floatValue6 - 12.0F,
        80.0F,
        1.0F,
        0.0F,
        587202559);
    fontManager.render(
        floatValue3 / 2.0F - 40.0F,
        floatValue4 - floatValue6 - 12.0F,
        80.0F * floatValue7,
        1.0F,
        0.0F,
        colorPalette.computeInt(0.75F));
    fontManager.update3();
  }

  public void render(
      FontManager fontManager,
      int intValue,
      String text,
      String text2,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    ColorPalette instance = ColorPalette.getInstance();
    boolean flag = this.count2 == intValue;
    boolean flag2 =
        GuiDrawing.test(floatValue3, floatValue4, floatValue, floatValue2, 208.0F, 57.0F);
    fontManager.render(
        floatValue,
        floatValue2,
        208.0F,
        57.0F,
        9.0F,
        flag ? -14080459 : (flag2 ? -14406856 : -15196632));
    fontManager.render(
        floatValue,
        floatValue2,
        208.0F,
        57.0F,
        9.0F,
        1.0F,
        !flag && (!this.enabled5 || this.count != 4 + intValue)
            ? (flag2 ? -9734517 : -13024684)
            : instance.computeInt(0.85F));
    if (intValue == 0) {
      fontManager.render(
          floatValue + 14.0F,
          floatValue2 + 17.0F,
          27.0F,
          23.0F,
          4.0F,
          1.2F,
          instance.computeInt17());
      fontManager.render2(
          floatValue + 23.0F,
          floatValue2 + 17.0F,
          floatValue + 23.0F,
          floatValue2 + 40.0F,
          1.0F,
          instance.computeInt18());
    } else {
      for (int index = 0; index < 3; index++) {
        fontManager.render(
            floatValue + 13.0F + index * 10,
            floatValue2 + 15.0F,
            8.0F,
            23 - index * 3,
            2.0F,
            1.0F,
            instance.computeInt17());
      }
    }

    fontManager.computeFloat(
        text, floatValue + 54.0F, floatValue2 + 20.0F, 15.0F, instance.computeInt16());
    fontManager.computeFloat(text2, floatValue + 54.0F, floatValue2 + 40.0F, 11.0F, -4471596);
    if (flag) {
      fontManager.render2(floatValue + 193.0F, floatValue2 + 14.0F, 2.4F, instance.computeInt());
    }
  }

  public void apply(int count22) {
    if (this.count2 != count22) {
      ClientAudio.apply((float) count22);
    }

    this.count2 = count22;
  }

  public void render(
      FontManager fontManager,
      String text,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      boolean flag,
      boolean flag2) {
    ColorPalette instance = ColorPalette.getInstance();
    boolean flag3 =
        flag2
            && GuiDrawing.test(
                floatValue5, floatValue6, floatValue, floatValue2, floatValue3, floatValue4);
    int intValue =
        flag
            ? (flag2 ? (flag3 ? instance.computeInt() : instance.getCount()) : -13617081)
            : (flag3 ? -14077632 : -14998995);
    fontManager.render(floatValue, floatValue2, floatValue3, floatValue4, 8.0F, intValue);
    if (!flag || !flag2) {
      fontManager.render(
          floatValue,
          floatValue2,
          floatValue3,
          floatValue4,
          8.0F,
          1.0F,
          flag3 ? -9734517 : -13024684);
    }

    fontManager.computeFloat2(
        text,
        floatValue + floatValue3 / 2.0F,
        floatValue2 + floatValue4 / 2.0F,
        flag ? 14.0F : 12.0F,
        flag && flag2 ? -15266022 : (flag2 ? -2038031 : -5852990));
  }

  public void apply(boolean enabled42) {
    if (!this.enabled3) {
      if (enabled42 && !this.profileNames.test()) {
        this.string = "Use 3–24 letters, numbers, _ or -.";
      } else {
        boolean flag =
            enabled42
                ? this.profileStorage.test(this.profileNames.getString(), true)
                : this.profileStorage.test();
        if (!flag) {
          this.string = "Could not save your profile. Please try again.";
        } else {
          GuiPreferences.getGuiPreferences().guiStyleControl.selectIndex(this.count2);
          GuiPreferences.getGuiPreferences().update3();
          this.enabled3 = true;
          this.timestamp2 = this.longSupplier.getAsLong();
          this.enabled4 = enabled42;
          this.profileNames.setEnabled(false);
          this.enabled6 = false;
          if (!AnimationPreferences.isEnabled()) {
            this.update();
          }
        }
      }
    }
  }

  public void update() {
    if (this.client.currentScreen == this) {
      if (this.enabled4) {
        if (this.count2 == 1) {
          this.client.setScreen(new DropdownScreen(ScreenSupport4.getScreen2(this.screen)));
        } else if (this.screen instanceof PanelScreen panelScreen) {
          panelScreen.update2();
          this.client.setScreen(panelScreen);
        } else {
          PanelScreen panelScreen2 = new PanelScreen(ScreenSupport4.getScreen2(this.screen));
          panelScreen2.update2();
          this.client.setScreen(panelScreen2);
        }
      } else {
        this.client.setScreen(
            ScreenSupport4.test(this.screen)
                ? ScreenSupport4.getScreen(ScreenSupport4.getScreen2(this.screen))
                : this.screen);
      }
    }
  }

  public void update2() {
    this.enabled2 = true;
    this.enabled5 = true;
    this.profileNames.setEnabled(this.count == 0);
    ClientAudio.apply(this.soundInstance);
    this.soundInstance = null;
  }

  public void update3() {
    this.profileStorage.getProfileDefaults().enabled2 =
        !this.profileStorage.getProfileDefaults().enabled2;
    this.profileStorage.update();
    if (!this.profileStorage.getProfileDefaults().enabled2) {
      ClientAudio.apply(this.soundInstance);
      this.soundInstance = null;
    }
  }

  @Override
  public boolean mouseClicked(Click click, boolean flag) {
    if (click.button() == 0 && !this.enabled3) {
      float value = OverlayRenderer.computeFloat(click.x());
      float value2 = OverlayRenderer.computeFloat(click.y());
      if (!this.test()) {
        float float4 = OverlayRenderer.computeFloat4();
        float float5 = OverlayRenderer.computeFloat5();
        float floatValue = Math.max(18.0F, Math.min(float4, float5) * 0.035F);
        if (GuiDrawing.test(
            value,
            value2,
            float4 - floatValue - 124.0F,
            float5 - floatValue - 28.0F,
            124.0F,
            28.0F)) {
          this.update2();
        } else if (GuiDrawing.test(
            value, value2, floatValue, float5 - floatValue - 28.0F, 122.0F, 28.0F)) {
          this.update3();
        }

        return true;
      } else {
        float amount142 = (value - this.amount5) / this.amount7;
        float floatValue2 = (value2 - this.amount6) / this.amount7;
        if (this.profileNames.test(amount142, floatValue2)) {
          this.count = 0;
        }

        if (GuiDrawing.test(amount142, floatValue2, 391.0F, 422.0F, 430.0F, 43.0F)) {
          this.count = 1;
          this.apply(true);
        } else if (GuiDrawing.test(amount142, floatValue2, 391.0F, 477.0F, 119.0F, 29.0F)) {
          this.count = 2;
          this.apply(false);
        } else if (GuiDrawing.test(amount142, floatValue2, 723.0F, 477.0F, 98.0F, 29.0F)) {
          this.count = 3;
          this.update3();
        } else if (GuiDrawing.test(amount142, floatValue2, 391.0F, 350.0F, 208.0F, 57.0F)) {
          this.count = 4;
          this.profileNames.setEnabled(false);
          this.apply(0);
        } else if (GuiDrawing.test(amount142, floatValue2, 613.0F, 350.0F, 208.0F, 57.0F)) {
          this.count = 5;
          this.profileNames.setEnabled(false);
          this.apply(1);
        } else if (GuiDrawing.test(amount142, floatValue2, 14.0F, 100.0F, 326.0F, 276.0F)) {
          this.enabled6 = true;
          this.amount14 = amount142;
        }

        return true;
      }
    } else {
      return true;
    }
  }

  @Override
  public boolean mouseDragged(Click click, double doubleValue, double doubleValue2) {
    if (this.enabled6 && !this.enabled3) {
      float amount142 = (OverlayRenderer.computeFloat(click.x()) - this.amount5) / this.amount7;
      this.amount13 = this.amount13 + (amount142 - this.amount14) * 0.009F;
      this.amount14 = amount142;
    }

    return true;
  }

  @Override
  public boolean mouseReleased(Click click) {
    this.enabled6 = false;
    return true;
  }

  @Override
  public boolean charTyped(CharInput charInput) {
    if (this.test() && !this.enabled3) {
      this.string = null;
    }

    return !this.test() || this.enabled3 || this.profileNames.test(charInput.codepoint());
  }

  @Override
  public boolean keyPressed(KeyInput keyInput) {
    if (this.enabled3) {
      return true;
    }

    if (!this.test()) {
      if (keyInput.key() == 77) {
        this.update3();
      } else if (keyInput.key() == 256
          || keyInput.key() == 257
          || keyInput.key() == 335
          || keyInput.key() == 32
          || keyInput.key() == 258) {
        this.update2();
      }

      return true;
    } else {
      if (keyInput.key() == 258) {
        this.count = Math.floorMod(this.count + ((keyInput.modifiers() & 1) != 0 ? -1 : 1), 6);
        this.profileNames.setEnabled(this.count == 0);
        return true;
      }

      if (keyInput.key() != 257
          && keyInput.key() != 335
          && (keyInput.key() != 32 || this.count == 0)) {
        if (keyInput.key() == 256) {
          this.apply(false);
          return true;
        } else {
          return this.profileNames.test2(keyInput.key()) || super.keyPressed(keyInput);
        }
      } else {
        if (this.count >= 4) {
          this.apply(this.count - 4);
        } else if (this.count == 3) {
          this.update3();
        } else {
          this.apply(this.count != 2);
        }

        return true;
      }
    }
  }

  @Override
  public void close() {
    if (!this.test()) {
      this.update2();
    } else {
      this.apply(false);
    }
  }

  @Override
  public void removed() {
    ClientAudio.apply(this.soundInstance);
    this.soundInstance = null;
    this.enabled6 = false;
    this.logoRenderer.close();
  }

  public static float computeFloat(float floatValue, float floatValue2, float floatValue3) {
    return floatValue + (floatValue2 - floatValue) * floatValue3;
  }

  public static float computeFloat2(float floatValue, float floatValue2, float floatValue3) {
    float floatValue4 =
        Math.clamp((floatValue - floatValue2) / (floatValue3 - floatValue2), 0.0F, 1.0F);
    return floatValue4
        * floatValue4
        * floatValue4
        * (floatValue4 * (floatValue4 * 6.0F - 15.0F) + 10.0F);
  }

  public static float computeFloat3(float floatValue, float floatValue2, float floatValue3) {
    float floatValue4 =
        Math.clamp((floatValue - floatValue2) / (floatValue3 - floatValue2), 0.0F, 1.0F);
    return 1.0F - (float) Math.pow(1.0F - floatValue4, 4.0);
  }
}
