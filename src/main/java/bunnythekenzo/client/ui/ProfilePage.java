package bunnythekenzo.client.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import net.minecraft.client.MinecraftClient;
import bunnythekenzo.client.ClientAudio;
import bunnythekenzo.client.ColorPalette;
import bunnythekenzo.client.GuiDrawing;
import bunnythekenzo.client.ProfileArtwork;
import bunnythekenzo.client.ProfileArtworkSupport;
import bunnythekenzo.client.ProfileDefaults;
import bunnythekenzo.client.ProfileNames;
import bunnythekenzo.client.ProfileStorage;
import bunnythekenzo.client.render.FontManager;

public final class ProfilePage implements MenuPage {
  public static final float amount = 24.0F;
  public static final float amount2 = 70.0F;
  public static final float amount3 = 276.0F;
  public static final float amount4 = 12.0F;
  public final ProfileStorage profileStorage = ProfileStorage.getProfileStorage();
  public final ProfileNames profileNames;
  public final ProfileArtworkSupport profileArtworkSupport;
  public float amount5;
  public float amount6;
  public float amount7;
  public float amount8;
  public String string;
  public String string2;
  public boolean enabled;

  public ProfilePage() {
    this.profileNames = new ProfileNames(this.profileStorage.getProfileDefaults().string2);
    this.profileArtworkSupport = new ProfileArtworkSupport();
    this.string = "3–24 letters, numbers, _ or -";
  }

  @Override
  public void apply(FontManager fontManager) {
    if (this.profileStorage.getProfileDefaults().enabled5) {
      this.profileArtworkSupport.apply(
          fontManager, this.profileStorage.getProfileDefaults().enabled3);
    }
  }

  @Override
  public void update6() {
    this.profileArtworkSupport.update3();
  }

  @Override
  public void render(
      FontManager fontManager,
      float amount52,
      float amount62,
      float amount72,
      float floatValue,
      float floatValue2,
      float floatValue3) {
    this.amount5 = amount52;
    this.amount6 = amount62;
    this.amount7 = amount72;
    this.amount8 = floatValue - 70.0F - 24.0F;
    ColorPalette instance = ColorPalette.getInstance();
    ProfileDefaults profileDefaults = this.profileStorage.getProfileDefaults();
    LocalDate localDate = LocalDate.now();
    List list = profileDefaults.getList(localDate);
    this.string2 = null;
    fontManager.computeFloat(
        "Your space.", amount52 + 24.0F, amount62 + 27.0F, 24.0F, instance.computeInt16());
    fontManager.computeFloat(
        "A little more you.",
        amount52 + 24.0F + 1.0F,
        amount62 + 49.0F,
        11.0F,
        instance.computeInt17());
    GuiDrawing.render(
        fontManager,
        "Replay welcome",
        this.computeFloat4(),
        amount62 + 20.0F,
        126.0F,
        28.0F,
        floatValue2,
        floatValue3,
        false,
        true);
    float floatValue4 = amount62 + 70.0F;
    GuiDrawing.render(fontManager, amount52 + 24.0F, floatValue4, 276.0F, this.amount8);
    fontManager.computeFloat(
        "YOUR SKIN", amount52 + 24.0F + 18.0F, floatValue4 + 22.0F, 9.5F, instance.computeInt17());
    fontManager.render2(
        amount52 + 24.0F + 18.0F,
        floatValue4 + 36.0F,
        amount52 + 24.0F + 276.0F - 18.0F,
        floatValue4 + 36.0F,
        1.0F,
        instance.computeInt13());
    this.profileArtworkSupport.render(
        fontManager,
        this.computeFloat6(),
        this.computeFloat7(),
        this.computeFloat8(),
        this.computeFloat9(),
        ProfileArtwork.SKIN,
        true,
        floatValue2,
        floatValue3);
    float float2 = this.computeFloat2();
    float float3 = this.computeFloat3();
    GuiDrawing.render(fontManager, float2, floatValue4, float3, 94.0F);
    fontManager.computeFloat(
        "PROFILE NAME", float2 + 16.0F, floatValue4 + 17.0F, 9.0F, instance.computeInt17());
    String text =
        LocalDate.parse(profileDefaults.string3)
            .format(DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH));
    fontManager.computeFloat3(
        "Member since " + text,
        float2 + float3 - 16.0F,
        floatValue4 + 17.0F,
        9.0F,
        instance.computeInt18());
    this.profileNames.render(
        fontManager, float2 + 16.0F, floatValue4 + 33.0F, float3 - 108.0F, 34.0F);
    GuiDrawing.render(
        fontManager,
        "Save",
        this.computeFloat5(),
        floatValue4 + 33.0F,
        64.0F,
        34.0F,
        floatValue2,
        floatValue3,
        true,
        this.profileNames.test());
    fontManager.computeFloat(
        this.string,
        float2 + 16.0F,
        floatValue4 + 81.0F,
        9.5F,
        this.enabled ? instance.computeInt22() : instance.computeInt18());
    float floatValue5 = floatValue4 + 94.0F + 12.0F;
    float floatValue6 = this.amount8 - 190.0F;
    this.render(
        fontManager,
        float2,
        floatValue5,
        float3,
        floatValue6,
        floatValue2,
        floatValue3,
        localDate,
        list);
    this.insights(fontManager, float2, floatValue5 + floatValue6 + 12.0F, float3, localDate, list);
  }

  public void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      LocalDate localDate,
      List list) {
    ColorPalette instance = ColorPalette.getInstance();
    GuiDrawing.render(fontManager, floatValue, floatValue2, floatValue3, floatValue4);
    long longValue =
        list.stream()
            .mapToLong(
                (referenceArgument79_0) -> ((java.lang.Long) referenceArgument79_0).longValue())
            .sum();
    long longValue2 =
        Math.max(
            60000L,
            list.stream()
                .mapToLong(
                    (referenceArgument80_0) -> ((java.lang.Long) referenceArgument80_0).longValue())
                .max()
                .orElse(0L));
    fontManager.computeFloat(
        "Your week", floatValue + 16.0F, floatValue2 + 22.0F, 15.0F, instance.computeInt16());
    fontManager.computeFloat3(
        ProfileDefaults.getString(longValue) + " played",
        floatValue + floatValue3 - 16.0F,
        floatValue2 + 22.0F,
        10.0F,
        instance.computeInt17());
    float floatValue7 = (floatValue3 - 32.0F) / 7.0F;
    float floatValue8 = floatValue2 + floatValue4 - 26.0F;
    float floatValue9 = floatValue4 - 70.0F;
    fontManager.render2(
        floatValue + 16.0F,
        floatValue8 - floatValue9 / 2.0F,
        floatValue + floatValue3 - 16.0F,
        floatValue8 - floatValue9 / 2.0F,
        1.0F,
        134217727);
    fontManager.render2(
        floatValue + 16.0F,
        floatValue8,
        floatValue + floatValue3 - 16.0F,
        floatValue8,
        1.0F,
        instance.computeInt13());

    for (int index = 0; index < 7; index++) {
      float floatValue10 = floatValue + 16.0F + index * floatValue7;
      float floatValue11 = floatValue7 - 14.0F;
      float floatValue12 =
          (Long) list.get(index) == 0L
              ? 2.0F
              : Math.max(
                  3.0F,
                  (float) ((Long) list.get(index)).longValue() / (float) longValue2 * floatValue9);
      boolean flag =
          GuiDrawing.test(
              floatValue5,
              floatValue6,
              floatValue10,
              floatValue2 + 40.0F,
              floatValue7,
              floatValue4 - 44.0F);
      if (index == 6 || flag) {
        fontManager.render(
            floatValue10 + 1.0F,
            floatValue2 + 40.0F,
            floatValue7 - 2.0F,
            floatValue4 - 45.0F,
            6.0F,
            instance.computeInt(flag ? 0.09F : 0.04F));
      }

      fontManager.render(
          floatValue10 + 7.0F,
          floatValue8 - floatValue12,
          floatValue11,
          floatValue12,
          3.0F,
          instance.computeInt(index != 6 && !flag ? 0.48F : 0.9F),
          instance.computeInt(0.17F),
          true);
      fontManager.computeFloat2(
          localDate
              .minusDays(6 - index)
              .getDayOfWeek()
              .getDisplayName(TextStyle.SHORT, Locale.ENGLISH),
          floatValue10 + floatValue7 / 2.0F,
          floatValue8 + 14.0F,
          9.0F,
          index == 6 ? instance.computeInt() : instance.computeInt17());
      if (flag) {
        this.string2 =
            localDate.minusDays(6 - index)
                + " · "
                + ProfileDefaults.getString((Long) list.get(index))
                + " played";
      }
    }

    if (longValue == 0L) {
      fontManager.computeFloat2(
          "Your next adventure starts here.",
          floatValue + floatValue3 / 2.0F,
          floatValue2 + 58.0F,
          10.0F,
          instance.computeInt17());
    }
  }

  public void insights(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      LocalDate localDate,
      List list) {
    ColorPalette instance = ColorPalette.getInstance();
    float floatValue4 = (floatValue3 - 12.0F) / 2.0F;
    GuiDrawing.render(fontManager, floatValue, floatValue2, floatValue4, 72.0F);
    fontManager.computeFloat(
        "ACTIVE DAYS", floatValue + 16.0F, floatValue2 + 17.0F, 9.0F, instance.computeInt17());
    long longValue =
        list.stream()
            .filter(
                (java.util.function.Predicate<java.lang.Long>)
                    (longValue2 -> {
                      return longValue2 > 0L;
                    }))
            .count();
    fontManager.computeFloat(
        longValue + " / 7",
        floatValue + 16.0F,
        floatValue2 + 43.0F,
        22.0F,
        instance.computeInt16());

    for (int index = 0; index < 7; index++) {
      float floatValue5 = floatValue + floatValue4 - 81.0F + index * 10;
      fontManager.render2(
          floatValue5,
          floatValue2 + 45.0F,
          3.0F,
          (Long) list.get(index) > 0L ? instance.computeInt() : 419430399);
      if (index == 6) {
        fontManager.render2(
            floatValue5, floatValue2 + 45.0F, 5.0F, 0.8F, instance.computeInt(0.4F));
      }
    }

    float floatValue6 = floatValue + floatValue4 + 12.0F;
    GuiDrawing.render(fontManager, floatValue6, floatValue2, floatValue4, 72.0F);
    fontManager.computeFloat(
        "MOST ACTIVE DAY", floatValue6 + 16.0F, floatValue2 + 17.0F, 9.0F, instance.computeInt17());
    int intValue = -1;

    for (int index2 = 0; index2 < 7; index2++) {
      if ((Long) list.get(index2) > 0L
          && (intValue < 0 || (Long) list.get(index2) >= (Long) list.get(intValue))) {
        intValue = index2;
      }
    }

    String text =
        intValue < 0
            ? "Still to come"
            : localDate
                .minusDays(6 - intValue)
                .getDayOfWeek()
                .getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    fontManager.computeFloat(
        text, floatValue6 + 16.0F, floatValue2 + 40.0F, 18.0F, instance.computeInt16());
    fontManager.computeFloat(
        intValue < 0
            ? "Make this week yours"
            : ProfileDefaults.getString((Long) list.get(intValue)) + " played this week",
        floatValue6 + 16.0F,
        floatValue2 + 58.0F,
        9.0F,
        instance.computeInt18());
  }

  public float computeFloat2() {
    return this.amount5 + 24.0F + 276.0F + 12.0F;
  }

  public float computeFloat3() {
    return this.amount7 - 48.0F - 276.0F - 12.0F;
  }

  public float computeFloat4() {
    return this.amount5 + this.amount7 - 24.0F - 126.0F;
  }

  public float computeFloat5() {
    return this.computeFloat2() + this.computeFloat3() - 80.0F;
  }

  public float computeFloat6() {
    return this.amount5 + 24.0F + 12.0F;
  }

  public float computeFloat7() {
    return this.amount6 + 70.0F + 44.0F;
  }

  public float computeFloat8() {
    return 252.0F;
  }

  public float computeFloat9() {
    return this.amount8 - 54.0F;
  }

  public void update3() {
    if (!this.profileNames.test()) {
      this.enabled = true;
      this.string = "Use 3–24 letters, numbers, _ or -.";
    } else {
      this.enabled = !this.profileStorage.test(this.profileNames.getString(), false);
      this.string =
          this.enabled
              ? "Could not save. Check your config folder and retry."
              : "Saved. Make yourself at home.";
      if (!this.enabled) {
        this.profileNames.setEnabled(false);
        ClientAudio.update5();
      }
    }
  }

  @Override
  public boolean test(float floatValue, float floatValue2, int intValue) {
    if (intValue != 0) {
      return false;
    } else {
      this.profileNames.test(floatValue, floatValue2);
      if (GuiDrawing.test(
          floatValue, floatValue2, this.computeFloat4(), this.amount6 + 20.0F, 126.0F, 28.0F)) {
        MinecraftClient instance = MinecraftClient.getInstance();
        instance.setScreen(new WelcomeScreen(instance.currentScreen));
        return true;
      } else if (GuiDrawing.test(
          floatValue,
          floatValue2,
          this.computeFloat5(),
          this.amount6 + 70.0F + 33.0F,
          64.0F,
          34.0F)) {
        this.update3();
        return true;
      } else if (GuiDrawing.test(
          floatValue,
          floatValue2,
          this.computeFloat6(),
          this.computeFloat7(),
          this.computeFloat8(),
          this.computeFloat9())) {
        this.profileArtworkSupport.apply(floatValue);
        return true;
      } else {
        return false;
      }
    }
  }

  @Override
  public void apply(float floatValue, float floatValue2) {
    this.profileArtworkSupport.apply2(floatValue);
  }

  @Override
  public void update5() {
    this.profileArtworkSupport.update();
  }

  @Override
  public void update() {
    this.profileNames.apply(this.profileStorage.getProfileDefaults().string2);
    this.profileNames.setEnabled(false);
  }

  @Override
  public void update2() {
    this.profileNames.setEnabled(false);
    this.profileArtworkSupport.update();
  }

  @Override
  public boolean test(int intValue) {
    return this.profileNames.test(intValue);
  }

  @Override
  public boolean test2(int intValue) {
    if (intValue == 258) {
      this.profileNames.setEnabled(!this.profileNames.isEnabled());
      return true;
    }

    if (!this.profileNames.isEnabled() || intValue != 257 && intValue != 335) {
      if (this.profileNames.test2(intValue)) {
        return true;
      }

      if (intValue != 263 && intValue != 262) {
        return false;
      }

      this.profileArtworkSupport.apply3(intValue == 263 ? -0.2F : 0.2F);
      return true;
    } else {
      this.update3();
      return true;
    }
  }

  @Override
  public String getString() {
    return this.string2;
  }
}
