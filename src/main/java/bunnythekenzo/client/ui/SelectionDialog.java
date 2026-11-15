package bunnythekenzo.client.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import bunnythekenzo.client.AnimationIntensity;
import bunnythekenzo.client.AnimationIntensitySupport;
import bunnythekenzo.client.ArgbColors;
import bunnythekenzo.client.ClientAudio;
import bunnythekenzo.client.ColorPalette;
import bunnythekenzo.client.Easing;
import bunnythekenzo.client.GuiModal;
import bunnythekenzo.client.NanoVgImageCache;
import bunnythekenzo.client.SmoothScrollState;
import bunnythekenzo.client.TweenValue;
import bunnythekenzo.client.render.FontManager;
import bunnythekenzo.client.setting.ColorSetting;

public final class SelectionDialog implements GuiModal {
  public static final float amount = 620.0F;
  public static final float amount2 = 424.0F;
  public static final float amount3 = 16.0F;
  public static final float amount4 = 52.0F;
  public static final float amount5 = 16.0F;
  public static final float amount6 = 32.0F;
  public static final float amount7 = 128.0F;
  public static final float amount8 = 22.0F;
  public final float amount9;
  public final float amount10;
  public static final float amount11 = 22.0F;
  public static final float amount12 = 260.0F;
  public static final float amount13 = 200.0F;
  public static final float amount14 = 10.0F;
  public static final float amount15 = 96.0F;
  public static final float amount16 = 11.0F;
  public static final float amount17 = 169.0F;
  public final GalleryModel galleryModel;
  public final TweenValue tweenValue = new TweenValue(260.0F, 0.0F, Easing.OUT_EXPO);
  public final SmoothScrollState smoothScrollState = new SmoothScrollState();
  public final AnimationIntensitySupport animationIntensitySupport =
      new AnimationIntensitySupport(AnimationIntensity.SUBTLE);
  public final AnimationIntensitySupport animationIntensitySupport2 =
      new AnimationIntensitySupport();
  public final AnimationIntensitySupport animationIntensitySupport3 =
      new AnimationIntensitySupport(AnimationIntensity.SUBTLE);
  public final StringBuilder stringBuilder = new StringBuilder();
  public boolean enabled = true;
  public boolean enabled2;
  public boolean enabled3;
  public List list = List.of();
  public String string;
  public boolean enabled4;
  public float amount18;
  public float amount19;
  public float amount20;
  public float amount21;
  public float amount22;
  public float amount23;
  public int count = 1;
  public ColorSetting colorSetting;
  public String string2;
  public float amount24;
  public float amount25;
  public float amount26;
  public float amount27;
  public float amount28;
  public boolean enabled5;
  public boolean enabled6;

  public SelectionDialog(GalleryModel galleryModel2) {
    this.galleryModel = galleryModel2;
    this.amount9 = galleryModel2.computeFloat();
    this.amount10 = galleryModel2.computeFloat2();
    this.tweenValue.animateTo(1.0F);
    ClientAudio.update();
  }

  public GalleryModel getGalleryModel() {
    return this.galleryModel;
  }

  @Override
  public void onClosed() {
    this.galleryModel.update();
  }

  public void closeAnimated() {
    if (!this.enabled3) {
      this.enabled3 = true;
      this.colorSetting = null;
      this.tweenValue.animateTo(0.0F);
      ClientAudio.update2();
    }
  }

  @Override
  public boolean isCloseComplete() {
    return this.enabled3 && this.tweenValue.getValue() <= 0.01F;
  }

  public float computeFloat() {
    return this.enabled3
        ? Easing.IN_QUAD.computeFloat(this.tweenValue.getValue())
        : this.tweenValue.getValue();
  }

  public void updateLayout(float floatValue, float floatValue2) {
    this.amount18 = Math.round((floatValue - 620.0F) / 2.0F);
    this.amount19 = Math.round((floatValue2 - 424.0F) / 2.0F - floatValue2 * 0.015F);
    this.amount20 = this.amount18 + 16.0F;
    this.amount21 = this.amount19 + 52.0F + 16.0F + 32.0F + 16.0F;
    this.amount22 = 588.0F;
    this.amount23 = this.amount19 + 424.0F - 22.0F - this.amount21;
    this.count = Math.max(1, (int) (this.amount22 / this.amount9));
  }

  public void refreshFilteredItems() {
    String string3 = this.stringBuilder.toString().trim().toLowerCase(Locale.ROOT);
    if (!string3.equals(this.string) || this.enabled2 != this.enabled4) {
      this.string = string3;
      this.enabled4 = this.enabled2;
      ArrayList list2 = new ArrayList();

      for (GalleryItem galleryItem :
          (java.lang.Iterable<GalleryItem>)
              (java.lang.Iterable<?>) (this.galleryModel.getList2())) {
        if ((!this.enabled2 || galleryItem.test3())
            && (string3.isEmpty() || galleryItem.test(string3))) {
          list2.add(galleryItem);
        }
      }

      this.list = list2;
      this.smoothScrollState.update2();
    }
  }

  public int computeInt() {
    return (this.list.size() + this.count - 1) / this.count;
  }

  public float computeFloat(int intValue) {
    return this.amount20
        + intValue % this.count * this.amount9
        + (this.amount22 - this.count * this.amount9) / 2.0F;
  }

  public float computeFloat2(int intValue) {
    return this.amount21
        + intValue / this.count * this.amount10
        - this.smoothScrollState.computeFloat();
  }

  @Override
  public void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    float value = this.computeFloat();
    if (!(value <= 0.02F)) {
      this.updateLayout(floatValue3, floatValue4);
      this.refreshFilteredItems();
      this.smoothScrollState.apply(this.computeInt() * this.amount10, this.amount23);
      ColorPalette instance = ColorPalette.getInstance();
      float floatValue5 = 0.96F + 0.04F * value;
      fontManager.render(
          0.0F,
          0.0F,
          floatValue3,
          floatValue4,
          0.0F,
          ArgbColors.withOpacity(-16317174, 0.72F * value));
      fontManager.update2();
      fontManager.apply3(value);
      fontManager.render2(this.amount18 + 310.0F, this.amount19 + 212.0F, floatValue5);
      this.apply(fontManager, instance);
      this.render(fontManager, instance, floatValue, floatValue2);
      this.render3(fontManager, instance, floatValue, floatValue2);
      this.render4(fontManager, instance, floatValue, floatValue2);
      if (this.colorSetting != null) {
        this.render5(fontManager, instance, floatValue, floatValue2);
      }

      fontManager.update3();
    }
  }

  public void apply(FontManager fontManager, ColorPalette colorPalette) {
    fontManager.render(
        this.amount18,
        this.amount19,
        620.0F,
        424.0F,
        16.0F,
        34.0F,
        12.0F,
        ArgbColors.withOpacity(-16777216, 0.6F));
    fontManager.render(
        this.amount18,
        this.amount19,
        620.0F,
        424.0F,
        16.0F,
        colorPalette.computeInt3(),
        colorPalette.computeInt4(),
        true);
    fontManager.render(
        this.amount18, this.amount19, 620.0F, 424.0F, 16.0F, 1.0F, colorPalette.computeInt14());
    fontManager.render(
        this.amount20 - 4.0F,
        this.amount21 - 4.0F,
        this.amount22 + 8.0F,
        this.amount23 + 8.0F,
        9.0F,
        ArgbColors.withOpacity(-16119792, 0.55F));
    fontManager.render(
        this.amount20 - 4.0F,
        this.amount21 - 4.0F,
        this.amount22 + 8.0F,
        this.amount23 + 8.0F,
        9.0F,
        1.0F,
        colorPalette.computeInt13());
  }

  public void render(
      FontManager fontManager, ColorPalette colorPalette, float floatValue, float floatValue2) {
    fontManager.update2();
    fontManager.render(this.amount18, this.amount19, 620.0F, 52.0F);
    fontManager.render(
        this.amount18,
        this.amount19,
        620.0F,
        52.0F,
        16.0F,
        16.0F,
        0.0F,
        0.0F,
        colorPalette.computeInt5(),
        colorPalette.computeInt6());
    fontManager.update3();
    fontManager.render(
        this.amount18,
        this.amount19 + 52.0F - 1.0F,
        620.0F,
        1.0F,
        0.0F,
        colorPalette.computeInt13());
    float floatValue3 = this.amount19 + 26.0F;
    float floatValue4 = this.amount18 + 16.0F + 2.0F;
    floatValue4 +=
        fontManager.computeFloat(
            this.galleryModel.getString(),
            floatValue4,
            floatValue3,
            18.0F,
            colorPalette.computeInt16());
    String string22 = this.galleryModel.getString2();
    float floatValue5 = fontManager.computeFloat(string22, 9.5F) + 14.0F;
    fontManager.render(
        floatValue4 + 10.0F,
        floatValue3 - 8.0F,
        floatValue5,
        16.0F,
        8.0F,
        colorPalette.computeInt(0.16F));
    fontManager.render(
        floatValue4 + 10.0F,
        floatValue3 - 8.0F,
        floatValue5,
        16.0F,
        8.0F,
        1.0F,
        colorPalette.computeInt(0.34F));
    fontManager.computeFloat2(
        string22,
        floatValue4 + 10.0F + floatValue5 / 2.0F,
        floatValue3,
        9.5F,
        colorPalette.computeInt());
    this.render2(fontManager, colorPalette, floatValue, floatValue2);
  }

  public void render2(
      FontManager fontManager, ColorPalette colorPalette, float floatValue, float floatValue2) {
    float floatValue3 = this.amount18 + 620.0F - 24.0F;
    float floatValue4 = this.amount19 + 26.0F;
    float value = this.animationIntensitySupport.computeFloat(this.test(floatValue, floatValue2));
    if (value > 0.01F) {
      fontManager.render2(
          floatValue3, floatValue4, 12.0F, ArgbColors.withOpacity(-1, 0.07F * value));
    }

    fontManager.update2();
    fontManager.apply(floatValue3, floatValue4);
    fontManager.apply2(value * 0.2F);
    fontManager.render5(
        -5.0F,
        -5.0F,
        10.0F,
        1.5F,
        ArgbColors.lerp(colorPalette.computeInt18(), colorPalette.computeInt16(), value));
    fontManager.update3();
  }

  public void render3(
      FontManager fontManager, ColorPalette colorPalette, float floatValue, float floatValue2) {
    float floatValue3 = this.amount19 + 52.0F + 16.0F;
    float value = this.animationIntensitySupport3.computeFloat(this.test2(floatValue, floatValue2));
    fontManager.render(
        this.amount18 + 16.0F, floatValue3, 452.0F, 32.0F, 8.0F, colorPalette.computeInt10());
    fontManager.render(
        this.amount18 + 16.0F,
        floatValue3,
        452.0F,
        32.0F,
        8.0F,
        1.0F,
        this.enabled
            ? colorPalette.computeInt15()
            : ArgbColors.lerp(
                colorPalette.computeInt14(), colorPalette.computeInt15(), value * 0.5F));
    float floatValue4 = this.amount18 + 16.0F + 12.0F;
    float floatValue5 = floatValue3 + 16.0F;
    fontManager.render2(
        floatValue4 + 4.0F, floatValue5 - 1.0F, 4.0F, 1.4F, colorPalette.computeInt18());
    fontManager.render2(
        floatValue4 + 7.0F,
        floatValue5 + 2.0F,
        floatValue4 + 10.0F,
        floatValue5 + 5.0F,
        1.4F,
        colorPalette.computeInt18());
    floatValue4 += 18.0F;
    String text = this.stringBuilder.toString();
    if (text.isEmpty()) {
      fontManager.computeFloat(
          "CrackedByDexter", floatValue4, floatValue5, 13.5F, colorPalette.computeInt19());
    } else {
      floatValue4 +=
          fontManager.computeFloat(
              text, floatValue4, floatValue5, 13.5F, colorPalette.computeInt16(), 406.0F);
    }

    if (this.enabled && System.currentTimeMillis() / 530L % 2L == 0L) {
      fontManager.render(
          floatValue4 + 1.0F, floatValue5 - 7.0F, 1.2F, 14.0F, 0.0F, colorPalette.computeInt());
    }

    this.render(
        fontManager,
        colorPalette,
        this.amount18 + 620.0F - 16.0F - 128.0F,
        floatValue3,
        floatValue,
        floatValue2);
  }

  public void render(
      FontManager fontManager,
      ColorPalette colorPalette,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    float value =
        this.animationIntensitySupport2.computeFloat(this.test3(floatValue3, floatValue4));
    int intValue =
        this.enabled2
            ? colorPalette.computeInt12()
            : ArgbColors.lerp(colorPalette.computeInt10(), colorPalette.computeInt11(), value);
    fontManager.render(floatValue, floatValue2, 128.0F, 32.0F, 8.0F, intValue);
    fontManager.render(
        floatValue,
        floatValue2,
        128.0F,
        32.0F,
        8.0F,
        1.0F,
        this.enabled2 ? colorPalette.computeInt15() : colorPalette.computeInt14());
    float floatValue5 = floatValue + 11.0F;
    float floatValue6 = floatValue2 + 9.5F;
    fontManager.render(
        floatValue5,
        floatValue6,
        13.0F,
        13.0F,
        4.0F,
        this.enabled2 ? colorPalette.computeInt() : ArgbColors.withOpacity(-16777216, 0.35F));
    if (this.enabled2) {
      fontManager.render4(floatValue5, floatValue6, 13.0F, 1.7F, -15593956);
    } else {
      fontManager.render(
          floatValue5, floatValue6, 13.0F, 13.0F, 4.0F, 1.0F, colorPalette.computeInt14());
    }

    fontManager.computeFloat(
        this.galleryModel.getString3(),
        floatValue5 + 13.0F + 8.0F,
        floatValue2 + 16.0F,
        11.0F,
        this.enabled2 ? colorPalette.computeInt16() : colorPalette.computeInt17());
  }

  public void render4(
      FontManager fontManager, ColorPalette colorPalette, float floatValue, float floatValue2) {
    fontManager.update2();
    fontManager.render(this.amount20, this.amount21, this.amount22, this.amount23);
    if (this.list.isEmpty()) {
      fontManager.computeFloat2(
          this.stringBuilder.isEmpty() && this.enabled2 ? "Nothing chosen yet" : "No matches",
          this.amount20 + this.amount22 / 2.0F,
          this.amount21 + this.amount23 / 2.0F - 6.0F,
          13.5F,
          colorPalette.computeInt18());
      fontManager.computeFloat2(
          this.enabled2 ? "Clear the filter to browse everything" : "Try a shorter search",
          this.amount20 + this.amount22 / 2.0F,
          this.amount21 + this.amount23 / 2.0F + 12.0F,
          11.0F,
          colorPalette.computeInt19());
      fontManager.update3();
    } else {
      int intValue =
          Math.max(0, (int) (this.smoothScrollState.computeFloat() / this.amount10) * this.count);
      int intValue2 =
          Math.min(
              this.list.size(),
              intValue + this.count * ((int) (this.amount23 / this.amount10) + 2));

      for (int index = intValue; index < intValue2; index++) {
        GalleryItem galleryItem = (GalleryItem) this.list.get(index);
        float value = this.computeFloat(index);
        float float2 = this.computeFloat2(index);
        boolean flag =
            floatValue >= value
                && floatValue <= value + this.amount9
                && floatValue2 >= float2
                && floatValue2 <= float2 + this.amount10
                && floatValue2 >= this.amount21
                && floatValue2 <= this.amount21 + this.amount23;
        if (flag) {
          fontManager.render(
              value + 2.0F,
              float2 + 2.0F,
              this.amount9 - 4.0F,
              this.amount10 - 4.0F,
              8.0F,
              ArgbColors.withOpacity(colorPalette.computeInt11(), 0.55F));
        }

        this.render(fontManager, colorPalette, galleryItem, value, float2, flag);
        if (galleryItem.test()) {
          int value2 = galleryItem.computeInt();
          boolean flag2 = galleryItem.test2();
          fontManager.render(
              value + 2.0F,
              float2 + 2.0F,
              this.amount9 - 4.0F,
              this.amount10 - 4.0F,
              8.0F,
              flag2 ? 1.6F : 1.0F,
              ArgbColors.withOpacity(value2, flag2 ? 0.95F : 0.34F));
          fontManager.render(
              value + 10.0F,
              float2 + this.amount10 - 7.0F,
              this.amount9 - 20.0F,
              2.5F,
              1.25F,
              ArgbColors.withOpacity(value2, flag2 ? 1.0F : 0.34F));
        } else if (flag) {
          fontManager.render(
              value + 2.0F,
              float2 + 2.0F,
              this.amount9 - 4.0F,
              this.amount10 - 4.0F,
              8.0F,
              1.0F,
              colorPalette.computeInt14());
        }
      }

      fontManager.update3();
      this.smoothScrollState.update();
      this.smoothScrollState.render(
          fontManager, this.amount20 + this.amount22 + 2.0F, this.amount21, this.amount23);
      GalleryItem galleryItem2 = this.getGalleryItem(floatValue, floatValue2);
      float floatValue3 = this.amount19 + 424.0F - 22.0F;
      if (this.colorSetting == null) {
        if (galleryItem2 != null) {
          fontManager.computeFloat(
              galleryItem2.getString(),
              this.amount18 + 16.0F + 2.0F,
              floatValue3 + 11.0F - 1.0F,
              11.0F,
              colorPalette.computeInt17(),
              443.0F);
          fontManager.computeFloat3(
              this.galleryModel.getString4(),
              this.amount18 + 620.0F - 16.0F,
              floatValue3 + 11.0F - 1.0F,
              9.5F,
              colorPalette.computeInt19());
        } else {
          fontManager.computeFloat(
              this.list.size() + (this.list.size() == 1 ? " result" : " results"),
              this.amount18 + 16.0F + 2.0F,
              floatValue3 + 11.0F - 1.0F,
              9.5F,
              colorPalette.computeInt19());
        }
      }
    }
  }

  public void render(
      FontManager fontManager,
      ColorPalette colorPalette,
      GalleryItem galleryItem,
      float floatValue,
      float floatValue2,
      boolean flag) {
    if (this.galleryModel.test()) {
      galleryItem.test(
          fontManager,
          floatValue + 9.0F,
          floatValue2 + 8.0F,
          this.amount9 - 18.0F,
          this.amount10 - 36.0F);
      fontManager.computeFloat2(
          galleryItem.getString2(),
          floatValue + this.amount9 / 2.0F,
          floatValue2 + this.amount10 - 17.0F,
          11.0F,
          galleryItem.test3() ? colorPalette.computeInt() : colorPalette.computeInt16());
    } else {
      float floatValue3 = floatValue + (this.amount9 - 22.0F) / 2.0F;
      float floatValue4 = floatValue2 + (this.amount10 - 22.0F) / 2.0F - 2.0F;
      int int3 = NanoVgImageCache.computeInt3(galleryItem.getIdentifier());
      if (int3 > 0) {
        fontManager.render(int3, floatValue3, floatValue4, 22.0F, 22.0F, -1);
      } else {
        String text = getString(galleryItem.getString2());
        fontManager.render(
            floatValue3, floatValue4, 22.0F, 22.0F, 4.0F, ArgbColors.withOpacity(-16777216, 0.3F));
        fontManager.render(
            floatValue3, floatValue4, 22.0F, 22.0F, 4.0F, 1.0F, colorPalette.computeInt13());
        fontManager.computeFloat2(
            text,
            floatValue3 + 11.0F,
            floatValue4 + 11.0F,
            9.5F,
            flag ? colorPalette.computeInt17() : colorPalette.computeInt18());
      }
    }
  }

  public static String getString(String text) {
    StringBuilder builder = new StringBuilder(2);

    for (String text2 : text.split(" +")) {
      if (!text2.isEmpty() && builder.length() != 2) {
        builder.append(Character.toUpperCase(text2.charAt(0)));
      }
    }

    return builder.isEmpty() ? "?" : builder.toString();
  }

  public void render5(
      FontManager fontManager, ColorPalette colorPalette, float floatValue, float floatValue2) {
    fontManager.render(
        this.amount24,
        this.amount25,
        200.0F,
        169.0F,
        12.0F,
        20.0F,
        7.0F,
        ArgbColors.withOpacity(-16777216, 0.55F));
    fontManager.render(
        this.amount24,
        this.amount25,
        200.0F,
        169.0F,
        12.0F,
        colorPalette.computeInt5(),
        colorPalette.computeInt6(),
        true);
    fontManager.render(
        this.amount24, this.amount25, 200.0F, 169.0F, 12.0F, 1.0F, colorPalette.computeInt14());
    float floatValue3 = this.amount24 + 10.0F;
    float floatValue4 = this.amount25 + 10.0F + 18.0F;
    fontManager.computeFloat(
        this.string2,
        floatValue3,
        this.amount25 + 10.0F + 4.0F,
        11.0F,
        colorPalette.computeInt16(),
        146.0F);
    fontManager.render(
        this.amount24 + 200.0F - 10.0F - 26.0F,
        this.amount25 + 10.0F - 3.0F,
        26.0F,
        14.0F,
        4.0F,
        ArgbColors.fromHsv(this.amount26, this.amount27, this.amount28));
    fontManager.render(
        floatValue3,
        floatValue4,
        180.0F,
        96.0F,
        8.0F,
        ArgbColors.fromHsv(this.amount26, 1.0F, 1.0F));
    fontManager.render(
        floatValue3, floatValue4, 180.0F, 96.0F, 8.0F, -1, ArgbColors.withOpacity(-1, 0.0F), false);
    fontManager.render(
        floatValue3,
        floatValue4,
        180.0F,
        96.0F,
        8.0F,
        ArgbColors.withOpacity(-16777216, 0.0F),
        -16777216,
        true);
    fontManager.render(
        floatValue3, floatValue4, 180.0F, 96.0F, 8.0F, 1.0F, ArgbColors.withOpacity(-1, 0.12F));
    float floatValue5 = floatValue3 + this.amount27 * 180.0F;
    float floatValue6 = floatValue4 + (1.0F - this.amount28) * 96.0F;
    fontManager.render2(
        floatValue5, floatValue6, 6.5F, 3.0F, ArgbColors.withOpacity(-16777216, 0.35F));
    fontManager.render2(floatValue5, floatValue6, 6.0F, 2.0F, -1);
    float floatValue7 = floatValue4 + 96.0F + 10.0F;
    int[] intValues = new int[] {-65536, -256, -16711936, -16711681, -16776961, -65281, -65536};

    for (int index = 0; index < 6; index++) {
      float floatValue8 = floatValue3 + 30.0F * index;
      float floatValue9 = 30.0F + (index < 5 ? 0.75F : 0.0F);
      fontManager.render(
          floatValue8,
          floatValue7,
          floatValue9,
          11.0F,
          index == 0 ? 5.5F : 0.0F,
          index == 5 ? 5.5F : 0.0F,
          index == 5 ? 5.5F : 0.0F,
          index == 0 ? 5.5F : 0.0F,
          intValues[index],
          intValues[index + 1],
          false);
    }

    fontManager.render(
        floatValue3, floatValue7, 180.0F, 11.0F, 5.5F, 1.0F, ArgbColors.withOpacity(-1, 0.14F));
    float floatValue10 = floatValue3 + this.amount26 / 360.0F * 180.0F;
    fontManager.render2(
        floatValue10, floatValue7 + 5.5F, 8.0F, ArgbColors.withOpacity(-16777216, 0.35F));
    fontManager.render2(floatValue10, floatValue7 + 5.5F, 7.0F, -1);
    fontManager.render2(
        floatValue10, floatValue7 + 5.5F, 4.0F, ArgbColors.fromHsv(this.amount26, 1.0F, 1.0F));
    fontManager.computeFloat2(
        "Click a cell or press Escape to close",
        this.amount24 + 100.0F,
        floatValue7 + 11.0F + 13.0F,
        9.5F,
        colorPalette.computeInt19());
  }

  @Override
  public boolean acceptsInput() {
    return !this.enabled3;
  }

  @Override
  public boolean mouseClicked(float floatValue, float floatValue2, int intValue) {
    if (this.enabled3) {
      return true;
    }

    if (this.colorSetting != null) {
      if (test(floatValue, floatValue2, this.amount24, this.amount25, 200.0F, 169.0F)) {
        this.apply3(floatValue, floatValue2);
        return true;
      }

      this.colorSetting = null;
    }

    if (this.test(floatValue, floatValue2)) {
      this.closeAnimated();
      return true;
    }

    if (this.test2(floatValue, floatValue2)) {
      this.enabled = true;
      ClientAudio.update5();
      return true;
    }

    if (this.test3(floatValue, floatValue2)) {
      this.enabled2 = !this.enabled2;
      this.enabled = false;
      ClientAudio.apply(this.enabled2);
      return true;
    }

    GalleryItem galleryItem = this.getGalleryItem(floatValue, floatValue2);
    if (galleryItem == null) {
      if (!test(floatValue, floatValue2, this.amount18, this.amount19, 620.0F, 424.0F)) {
        this.closeAnimated();
      } else {
        this.enabled = false;
      }

      return true;
    } else {
      this.enabled = false;
      if (intValue == 1 && galleryItem.getColorSetting() != null) {
        this.render(galleryItem, floatValue, floatValue2);
      } else {
        galleryItem.update();
        if (this.enabled2) {
          this.string = null;
        }

        ClientAudio.apply(galleryItem.test2());
      }

      return true;
    }
  }

  public void render(GalleryItem galleryItem, float floatValue, float floatValue2) {
    ColorSetting colorSetting2 = galleryItem.getColorSetting();
    if (colorSetting2 != null) {
      this.colorSetting = colorSetting2;
      this.string2 = galleryItem.getString2();
      float[] amountArray = ArgbColors.toHsv(colorSetting2.getInteger());
      this.amount26 = amountArray[0];
      this.amount27 = amountArray[1];
      this.amount28 = amountArray[2];
      this.amount24 =
          Math.clamp(
              floatValue + 12.0F, this.amount18 + 6.0F, this.amount18 + 620.0F - 200.0F - 6.0F);
      this.amount25 =
          Math.clamp(
              floatValue2 - 84.5F,
              this.amount19 + 52.0F + 6.0F,
              this.amount19 + 424.0F - 169.0F - 6.0F);
      if (this.enabled2) {
        this.string = null;
      }

      ClientAudio.update5();
    }
  }

  public void apply3(float floatValue, float floatValue2) {
    float floatValue3 = this.amount25 + 10.0F + 18.0F;
    float floatValue4 = floatValue3 + 96.0F + 10.0F;
    if (floatValue2 >= floatValue3 - 4.0F && floatValue2 <= floatValue3 + 96.0F + 4.0F) {
      this.enabled5 = true;
      this.apply4(floatValue, floatValue2);
    } else if (floatValue2 >= floatValue4 - 5.0F && floatValue2 <= floatValue4 + 11.0F + 5.0F) {
      this.enabled6 = true;
      this.apply4(floatValue, floatValue2);
    }
  }

  @Override
  public void mouseDragged(float floatValue, float floatValue2) {
    if (this.colorSetting != null && (this.enabled5 || this.enabled6)) {
      this.apply4(floatValue, floatValue2);
    }
  }

  public void apply4(float floatValue, float floatValue2) {
    float floatValue3 = this.amount24 + 10.0F;
    float floatValue4 = this.amount25 + 10.0F + 18.0F;
    if (this.enabled5) {
      this.amount27 = Math.clamp((floatValue - floatValue3) / 180.0F, 0.0F, 1.0F);
      this.amount28 = 1.0F - Math.clamp((floatValue2 - floatValue4) / 96.0F, 0.0F, 1.0F);
    } else if (this.enabled6) {
      this.amount26 = Math.clamp((floatValue - floatValue3) / 180.0F, 0.0F, 1.0F) * 360.0F;
    }

    this.colorSetting.apply(
        Integer.valueOf(ArgbColors.fromHsv(this.amount26, this.amount27, this.amount28)));
  }

  @Override
  public void mouseReleased() {
    this.enabled5 = false;
    this.enabled6 = false;
  }

  @Override
  public boolean mouseScrolled(float floatValue, float floatValue2, double doubleValue) {
    if (this.colorSetting != null
        && test(floatValue, floatValue2, this.amount24, this.amount25, 200.0F, 169.0F)) {
      return true;
    }

    this.smoothScrollState.test(doubleValue);
    return true;
  }

  @Override
  public boolean keyPressed(int intValue) {
    if (this.enabled3) {
      return true;
    }

    switch (intValue) {
      case 256:
        if (this.colorSetting != null) {
          this.colorSetting = null;
        } else {
          this.closeAnimated();
        }
        break;
      case 257:
      case 335:
        this.enabled = false;
        break;
      case 259:
        if (this.enabled && !this.stringBuilder.isEmpty()) {
          this.stringBuilder.deleteCharAt(this.stringBuilder.length() - 1);
        }
        break;
      default:
        return true;
    }

    return true;
  }

  public void setSearchText(String text) {
    this.stringBuilder.setLength(0);
    this.stringBuilder.append(text);
    this.string = null;
  }

  public void setSelectedOnly(boolean enabled22) {
    this.enabled2 = enabled22;
    this.string = null;
  }

  public int getVisibleItemCount() {
    this.refreshFilteredItems();
    return this.list.size();
  }

  public boolean test3() {
    this.refreshFilteredItems();

    for (GalleryItem galleryItem :
        (java.lang.Iterable<GalleryItem>) (java.lang.Iterable<?>) (this.list)) {
      if (galleryItem.getColorSetting() != null) {
        this.render(galleryItem, this.amount18 + 310.0F, this.amount19 + 212.0F);
        return true;
      }
    }

    return false;
  }

  @Override
  public boolean charTyped(int intValue) {
    if (this.enabled3) {
      return false;
    }

    if (!this.enabled) {
      this.enabled = true;
    }

    if (this.stringBuilder.length() < 48 && intValue >= 32) {
      this.stringBuilder.appendCodePoint(intValue);
    }

    return true;
  }

  public static boolean test(
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6) {
    return floatValue >= floatValue3
        && floatValue <= floatValue3 + floatValue5
        && floatValue2 >= floatValue4
        && floatValue2 <= floatValue4 + floatValue6;
  }

  public boolean test(float floatValue, float floatValue2) {
    float floatValue3 = this.amount18 + 620.0F - 24.0F;
    float floatValue4 = this.amount19 + 26.0F;
    float floatValue5 = floatValue - floatValue3;
    float floatValue6 = floatValue2 - floatValue4;
    return floatValue5 * floatValue5 + floatValue6 * floatValue6 <= 225.0F;
  }

  public boolean test2(float floatValue, float floatValue2) {
    return test(
        floatValue,
        floatValue2,
        this.amount18 + 16.0F,
        this.amount19 + 52.0F + 16.0F,
        452.0F,
        32.0F);
  }

  public boolean test3(float floatValue, float floatValue2) {
    return test(
        floatValue,
        floatValue2,
        this.amount18 + 620.0F - 16.0F - 128.0F,
        this.amount19 + 52.0F + 16.0F,
        128.0F,
        32.0F);
  }

  public GalleryItem getGalleryItem(float floatValue, float floatValue2) {
    if (!test(
        floatValue, floatValue2, this.amount20, this.amount21, this.amount22, this.amount23)) {
      return null;
    } else {
      int intValue =
          (int)
              Math.floor(
                  (floatValue - this.amount20 - (this.amount22 - this.count * this.amount9) / 2.0F)
                      / this.amount9);
      if (intValue >= 0 && intValue < this.count) {
        int intValue2 =
            (int)
                ((floatValue2 - this.amount21 + this.smoothScrollState.computeFloat())
                    / this.amount10);
        int intValue3 = intValue2 * this.count + intValue;
        return intValue3 >= 0 && intValue3 < this.list.size()
            ? (GalleryItem) this.list.get(intValue3)
            : null;
      } else {
        return null;
      }
    }
  }
}
