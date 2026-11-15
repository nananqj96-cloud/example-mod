package bunnythekenzo.client;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.entity.player.PlayerSkinType;
import net.minecraft.entity.player.SkinTextures;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.system.MemoryStack;
import bunnythekenzo.client.render.FontManager;
import bunnythekenzo.client.render.LogoRenderer;

public final class ProfileArtworkSupport {
  public final List list = new ArrayList(80);
  public final LogoRenderer logoRenderer = new LogoRenderer();
  public final LocalSkinProvider localSkinProvider = new LocalSkinProvider();
  public float amount = -0.38F;
  public float amount2 = this.amount;
  public float amount3 = 0.12F;
  public long timestamp = System.nanoTime();
  public float amount4;
  public boolean enabled;

  public void apply(float amount42) {
    this.enabled = true;
    this.amount4 = amount42;
  }

  public void apply2(float amount42) {
    if (this.enabled) {
      this.amount = this.amount + (amount42 - this.amount4) * 0.012F;
      this.amount4 = amount42;
    }
  }

  public void update() {
    this.enabled = false;
  }

  public void apply3(float floatValue) {
    this.amount += floatValue;
  }

  public void update2() {
    this.amount = -0.38F;
  }

  public void apply(FontManager fontManager, boolean flag) {
    float floatValue =
        AnimationPreferences.isEnabled()
            ? (float) (System.nanoTime() % 600000000000L) / 1.0E9F
            : 0.0F;
    float floatValue2 =
        this.amount
            + (flag && AnimationPreferences.isEnabled() && !this.enabled
                ? (float) Math.sin(floatValue * 0.6F) * 0.08F
                : 0.0F);
    this.logoRenderer.render(fontManager, 600.0F, floatValue2, 0.15F, 32.0F, 1.0F);
  }

  public void update3() {
    this.update();
    this.logoRenderer.close();
  }

  public void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      ProfileArtwork profileArtwork,
      boolean flag,
      float floatValue5,
      float floatValue6) {
    boolean flag2 = profileArtwork == ProfileArtwork.CRYSTAL;
    boolean flag3 = profileArtwork == ProfileArtwork.BUNNY;
    long timestamp2 = System.nanoTime();
    float floatValue7 = Math.min(0.05F, (float) (timestamp2 - this.timestamp) / 1.0E9F);
    this.timestamp = timestamp2;
    if (AnimationPreferences.isEnabled() && flag && !this.enabled && !flag3) {
      this.amount = (this.amount + floatValue7 * (flag2 ? 0.25F : 0.65F)) % (float) (Math.PI * 2);
    }

    float floatValue8 =
        AnimationPreferences.isEnabled() ? (float) (timestamp2 % 600000000000L) / 1.0E9F : 0.0F;
    this.amount2 =
        this.amount
            + (flag3 && flag && AnimationPreferences.isEnabled() && !this.enabled
                ? (float) Math.sin(floatValue8 * 0.6F) * 0.18F
                : 0.0F);
    ColorPalette instance = ColorPalette.getInstance();
    float floatValue9 = floatValue + floatValue3 / 2.0F;
    float floatValue10 =
        floatValue2 + floatValue4 * (profileArtwork == ProfileArtwork.SKIN ? 0.9F : 0.83F);
    fontManager.update2();
    fontManager.render(floatValue, floatValue2, floatValue3, floatValue4);
    if (flag3) {
      float floatValue11 = floatValue2 + floatValue4 * 0.46F;
      fontManager.update2();
      fontManager.apply(floatValue9, floatValue11 + floatValue3 * 0.245F);
      fontManager.apply2(1.0F, 0.12F);
      fontManager.render3(0.0F, 0.0F, floatValue3 * 0.18F, floatValue3 * 0.22F, -1610612736);
      fontManager.update3();
      this.logoRenderer.render(fontManager, floatValue9, floatValue11, floatValue3 * 1.08F);
      fontManager.update3();
    } else {
      fontManager.render3(
          floatValue9,
          floatValue2 + floatValue4 * 0.44F,
          24.0F,
          floatValue3 * 0.48F,
          instance.computeInt(0.12F));
      fontManager.update2();
      fontManager.apply(floatValue9, floatValue10);
      fontManager.apply2(1.0F, 0.28F);
      fontManager.render3(0.0F, 7.0F, floatValue3 * 0.2F, 28.0F, -1342177280);
      fontManager.render2(0.0F, 0.0F, floatValue3 * 0.34F, -14673626);
      fontManager.render2(0.0F, 0.0F, floatValue3 * 0.34F, 1.2F, instance.computeInt(0.28F));
      fontManager.render2(0.0F, 0.0F, floatValue3 * 0.29F, 0.8F, 318767103);
      fontManager.update3();
      this.list.clear();
      this.amount3 = flag3 ? 0.23F : (flag2 ? 0.27F : 0.12F);
      if (flag2) {
        this.apply(floatValue8, instance);
      } else {
        SkinTextures skinTextures = this.localSkinProvider.getSkinTextures();
        int value = TextureImageCache.computeInt(skinTextures.body().texturePath());
        boolean flag4 = skinTextures.model() == PlayerSkinType.SLIM;
        float floatValue12 = flag4 ? 3.0F : 4.0F;
        this.render(-4.0F, -16.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, 0.0F, value, 0.0F, 0.0F);
        this.render(-4.0F, -8.0F, -2.0F, 8.0F, 12.0F, 4.0F, 16.0F, 16.0F, value, 0.0F, 0.0F);
        this.render(-4.0F, 4.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, 16.0F, value, 0.0F, 0.0F);
        this.render(0.0F, 4.0F, -2.0F, 4.0F, 12.0F, 4.0F, 16.0F, 48.0F, value, 0.0F, 0.0F);
        float floatValue13 =
            AnimationPreferences.isEnabled() ? (float) Math.sin(floatValue8 * 1.7F) * 0.022F : 0.0F;
        this.render(
            -4.0F - floatValue12,
            -8.0F,
            -2.0F,
            floatValue12,
            12.0F,
            4.0F,
            40.0F,
            16.0F,
            value,
            -0.06F + floatValue13,
            -4.0F);
        this.render(
            4.0F,
            -8.0F,
            -2.0F,
            floatValue12,
            12.0F,
            4.0F,
            32.0F,
            48.0F,
            value,
            0.06F - floatValue13,
            4.0F);
        this.render(-4.0F, -16.0F, -4.0F, 8.0F, 8.0F, 8.0F, 32.0F, 0.0F, value, 0.0F, 0.0F, 0.45F);
        this.render(-4.0F, -8.0F, -2.0F, 8.0F, 12.0F, 4.0F, 16.0F, 32.0F, value, 0.0F, 0.0F, 0.22F);
        this.render(-4.0F, 4.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, 32.0F, value, 0.0F, 0.0F, 0.2F);
        this.render(0.0F, 4.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, 48.0F, value, 0.0F, 0.0F, 0.2F);
        this.render(
            -4.0F - floatValue12,
            -8.0F,
            -2.0F,
            floatValue12,
            12.0F,
            4.0F,
            40.0F,
            32.0F,
            value,
            -0.06F + floatValue13,
            -4.0F,
            0.22F);
        this.render(
            4.0F,
            -8.0F,
            -2.0F,
            floatValue12,
            12.0F,
            4.0F,
            48.0F,
            48.0F,
            value,
            0.06F - floatValue13,
            4.0F,
            0.22F);
      }

      this.list.sort(Comparator.comparingDouble(ProfileArtworkData2::computeFloat));
      float floatValue14 =
          flag3
              ? Math.min(floatValue3 / 38.0F, floatValue4 / 36.0F)
              : (flag2
                  ? Math.min(floatValue3 / 30.0F, floatValue4 / 43.0F)
                  : Math.min(floatValue3 / 26.0F, floatValue4 / 37.0F));
      float floatValue15 =
          floatValue10
              - (flag3 ? 12 : 16) * floatValue14
              - (!flag2 && !flag3 ? 0.0F : (float) Math.sin(floatValue8 * 1.1F) * 4.0F);

      for (ProfileArtworkData2 profileArtworkData2 :
          (java.lang.Iterable<ProfileArtworkData2>) (java.lang.Iterable<?>) (this.list)) {
        render(fontManager, profileArtworkData2, floatValue9, floatValue15, floatValue14);
      }

      fontManager.update3();
    }
  }

  public ProfileArtworkData getProfileArtworkData(
      float floatValue, float floatValue2, float floatValue3) {
    float amount5 =
        floatValue * (float) Math.cos(this.amount2) + floatValue3 * (float) Math.sin(this.amount2);
    float floatValue4 =
        -floatValue * (float) Math.sin(this.amount2) + floatValue3 * (float) Math.cos(this.amount2);
    return new ProfileArtworkData(
        amount5,
        floatValue2 * (float) Math.cos(this.amount3) + floatValue4 * (float) Math.sin(this.amount3),
        -floatValue2 * (float) Math.sin(this.amount3)
            + floatValue4 * (float) Math.cos(this.amount3));
  }

  public void render(
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      float floatValue7,
      float floatValue8,
      int intValue,
      float floatValue9,
      float floatValue10) {
    this.render(
        floatValue,
        floatValue2,
        floatValue3,
        floatValue4,
        floatValue5,
        floatValue6,
        floatValue7,
        floatValue8,
        intValue,
        floatValue9,
        floatValue10,
        0.0F);
  }

  public void render(
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      float floatValue7,
      float floatValue8,
      int intValue,
      float floatValue9,
      float floatValue10,
      float floatValue11) {
    float floatValue12 = floatValue - floatValue11;
    float floatValue13 = floatValue + floatValue4 + floatValue11;
    float floatValue14 = floatValue2 - floatValue11;
    float floatValue15 = floatValue2 + floatValue5 + floatValue11;
    float floatValue16 = floatValue3 - floatValue11;
    float floatValue17 = floatValue3 + floatValue6 + floatValue11;
    ProfileArtworkData[] profileArtworkDataArray = new ProfileArtworkData[8];
    float[][] floatValuesArray =
        new float[][] {
          {floatValue12, floatValue14, floatValue17},
          {floatValue13, floatValue14, floatValue17},
          {floatValue13, floatValue15, floatValue17},
          {floatValue12, floatValue15, floatValue17},
          {floatValue12, floatValue14, floatValue16},
          {floatValue13, floatValue14, floatValue16},
          {floatValue13, floatValue15, floatValue16},
          {floatValue12, floatValue15, floatValue16}
        };

    for (int index = 0; index < 8; index++) {
      float floatValue18 = floatValuesArray[index][0] - floatValue10;
      float floatValue19 = floatValuesArray[index][1] - floatValue2;
      profileArtworkDataArray[index] =
          this.getProfileArtworkData(
              floatValue10
                  + floatValue18 * (float) Math.cos(floatValue9)
                  - floatValue19 * (float) Math.sin(floatValue9),
              floatValue2
                  + floatValue18 * (float) Math.sin(floatValue9)
                  + floatValue19 * (float) Math.cos(floatValue9),
              floatValuesArray[index][2]);
    }

    this.render(
        profileArtworkDataArray,
        new int[] {0, 1, 2, 3},
        floatValue7 + floatValue6,
        floatValue8 + floatValue6,
        floatValue4,
        floatValue5,
        intValue);
    this.render(
        profileArtworkDataArray,
        new int[] {5, 4, 7, 6},
        floatValue7 + floatValue6 + floatValue4 + floatValue6,
        floatValue8 + floatValue6,
        floatValue4,
        floatValue5,
        intValue);
    this.render(
        profileArtworkDataArray,
        new int[] {4, 0, 3, 7},
        floatValue7,
        floatValue8 + floatValue6,
        floatValue6,
        floatValue5,
        intValue);
    this.render(
        profileArtworkDataArray,
        new int[] {1, 5, 6, 2},
        floatValue7 + floatValue6 + floatValue4,
        floatValue8 + floatValue6,
        floatValue6,
        floatValue5,
        intValue);
    this.render(
        profileArtworkDataArray,
        new int[] {4, 5, 1, 0},
        floatValue7 + floatValue6,
        floatValue8,
        floatValue4,
        floatValue6,
        intValue);
    this.render(
        profileArtworkDataArray,
        new int[] {3, 2, 6, 7},
        floatValue7 + floatValue6 + floatValue4,
        floatValue8,
        floatValue4,
        floatValue6,
        intValue);
  }

  public void render(
      ProfileArtworkData[] profileArtworkDataArray,
      int[] intValues,
      float amount5,
      float amount22,
      float amount32,
      float amount42,
      int count) {
    ProfileArtworkData[] profileArtworkDataArray2 = new ProfileArtworkData[intValues.length];

    for (int index = 0; index < intValues.length; index++) {
      profileArtworkDataArray2[index] = profileArtworkDataArray[intValues[index]];
    }

    float floatValue = profileArtworkDataArray2[1].amount() - profileArtworkDataArray2[0].amount();
    float floatValue2 =
        profileArtworkDataArray2[1].amount2() - profileArtworkDataArray2[0].amount2();
    float floatValue3 =
        profileArtworkDataArray2[1].amount3() - profileArtworkDataArray2[0].amount3();
    float floatValue4 = profileArtworkDataArray2[2].amount() - profileArtworkDataArray2[0].amount();
    float floatValue5 =
        profileArtworkDataArray2[2].amount2() - profileArtworkDataArray2[0].amount2();
    float floatValue6 =
        profileArtworkDataArray2[2].amount3() - profileArtworkDataArray2[0].amount3();
    float floatValue7 = floatValue2 * floatValue6 - floatValue3 * floatValue5;
    float floatValue8 = floatValue3 * floatValue4 - floatValue * floatValue6;
    float floatValue9 = floatValue * floatValue5 - floatValue2 * floatValue4;
    if (!(floatValue9 <= 0.001F)) {
      float floatValue10 =
          (float)
              Math.sqrt(
                  floatValue7 * floatValue7
                      + floatValue8 * floatValue8
                      + floatValue9 * floatValue9);
      float floatValue11 =
          0.58F
              + 0.42F
                  * Math.max(
                      0.0F,
                      (-0.35F * floatValue7 - 0.55F * floatValue8 + 0.76F * floatValue9)
                          / floatValue10);
      int intValue = Math.round(floatValue11 * 255.0F);
      this.list.add(
          new ProfileArtworkData2(
              profileArtworkDataArray2,
              amount5,
              amount22,
              amount32,
              amount42,
              count,
              0xFF000000 | intValue << 16 | intValue << 8 | intValue));
    }
  }

  public void apply(float floatValue, ColorPalette colorPalette) {
    ProfileArtworkData[] profileArtworkDataArray = new ProfileArtworkData[18];
    profileArtworkDataArray[0] = this.getProfileArtworkData(0.0F, -15.0F, 0.0F);
    profileArtworkDataArray[17] = this.getProfileArtworkData(0.0F, 13.0F, 0.0F);

    for (int index = 0; index < 8; index++) {
      float floatValue2 = index * (float) Math.PI / 4.0F;
      profileArtworkDataArray[1 + index] =
          this.getProfileArtworkData(
              (float) Math.cos(floatValue2) * 10.0F, -2.0F, (float) Math.sin(floatValue2) * 10.0F);
      profileArtworkDataArray[9 + index] =
          this.getProfileArtworkData(
              (float) Math.cos(floatValue2) * 9.8F, -0.8F, (float) Math.sin(floatValue2) * 9.8F);
    }

    for (int index2 = 0; index2 < 8; index2++) {
      int intValue = (index2 + 1) % 8;
      this.apply(
          profileArtworkDataArray, new int[] {0, 1 + index2, 1 + intValue}, colorPalette, false);
      this.apply(
          profileArtworkDataArray,
          new int[] {1 + index2, 9 + index2, 9 + intValue, 1 + intValue},
          colorPalette,
          true);
      this.apply(
          profileArtworkDataArray, new int[] {17, 9 + intValue, 9 + index2}, colorPalette, false);
    }

    float floatValue3 = floatValue * 0.45F;

    for (int index3 = 0; index3 < 2; index3++) {
      float floatValue4 = floatValue3 + index3 * (float) Math.PI;
      float floatValue5 = (float) Math.cos(floatValue4) * 14.0F;
      float floatValue6 = (float) Math.sin(floatValue4) * 12.0F;
      int size = this.list.size();
      this.render(
          floatValue5 - 1.2F,
          1 + index3 * 3,
          floatValue6 - 1.2F,
          2.4F,
          2.4F,
          2.4F,
          0.0F,
          0.0F,
          -1,
          0.0F,
          0.0F);

      for (int index4 = size; index4 < this.list.size(); index4++) {
        ProfileArtworkData2 profileArtworkData2 = (ProfileArtworkData2) this.list.get(index4);
        int value =
            ArgbColors.lerp(
                colorPalette.getCount(),
                -3076,
                (profileArtworkData2.count2() & 0xFF) / 255.0F * 0.75F);
        this.list.set(
            index4,
            new ProfileArtworkData2(
                profileArtworkData2.profileArtworkDataArray(), 0.0F, 0.0F, 1.0F, 1.0F, -1, value));
      }
    }
  }

  public void apply(
      ProfileArtworkData[] profileArtworkDataArray,
      int[] intValues,
      ColorPalette colorPalette,
      boolean flag) {
    int size = this.list.size();
    this.render(profileArtworkDataArray, intValues, 0.0F, 0.0F, 1.0F, 1.0F, -1);
    if (this.list.size() != size) {
      ProfileArtworkData2 profileArtworkData2 = (ProfileArtworkData2) this.list.removeLast();
      float floatValue = (profileArtworkData2.count2() & 0xFF) / 255.0F;
      float floatValue2 = (float) Math.pow(Math.max(0.0F, (floatValue - 0.65F) / 0.35F), 8.0);
      int value =
          ArgbColors.lerp(-13361095, colorPalette.computeInt(), (floatValue - 0.4F) * 1.35F);
      value = ArgbColors.lerp(value, -3588, flag ? 0.48F : floatValue2 * 0.55F);
      this.list.add(
          new ProfileArtworkData2(
              profileArtworkData2.profileArtworkDataArray(), 0.0F, 0.0F, 1.0F, 1.0F, -1, value));
    }
  }

  public static void render(
      FontManager fontManager,
      ProfileArtworkData2 profileArtworkData2,
      float floatValue,
      float floatValue2,
      float floatValue3) {
    ProfileArtworkData[] profileArtworkDataArray = profileArtworkData2.profileArtworkDataArray();
    if (profileArtworkData2.count() > 0) {
      fontManager.update2();
      long timestamp2 = fontManager.getTimestamp();
      NanoVG.nvgTransform(
          timestamp2,
          (profileArtworkDataArray[1].amount() - profileArtworkDataArray[0].amount())
              * floatValue3
              / profileArtworkData2.amount3(),
          (profileArtworkDataArray[1].amount2() - profileArtworkDataArray[0].amount2())
              * floatValue3
              / profileArtworkData2.amount3(),
          (profileArtworkDataArray[3].amount() - profileArtworkDataArray[0].amount())
              * floatValue3
              / profileArtworkData2.amount4(),
          (profileArtworkDataArray[3].amount2() - profileArtworkDataArray[0].amount2())
              * floatValue3
              / profileArtworkData2.amount4(),
          floatValue + profileArtworkDataArray[0].amount() * floatValue3,
          floatValue2 + profileArtworkDataArray[0].amount2() * floatValue3);

      try (MemoryStack stack = MemoryStack.stackPush()) {
        NVGPaint __result = NVGPaint.malloc(stack);
        NanoVG.nvgImagePattern(
            timestamp2,
            -profileArtworkData2.amount(),
            -profileArtworkData2.amount2(),
            64.0F,
            64.0F,
            0.0F,
            profileArtworkData2.count(),
            1.0F,
            __result);
        float value = (profileArtworkData2.count2() & 0xFF) / 255.0F;
        __result.innerColor().r(value).g(value).b(value).a(1.0F);
        __result.outerColor().r(value).g(value).b(value).a(1.0F);
        NanoVG.nvgBeginPath(timestamp2);
        NanoVG.nvgRect(
            timestamp2, 0.0F, 0.0F, profileArtworkData2.amount3(), profileArtworkData2.amount4());
        NanoVG.nvgFillPaint(timestamp2, __result);
        NanoVG.nvgFill(timestamp2);
      }

      fontManager.update3();
    } else {
      for (int index = 1; index < profileArtworkDataArray.length - 1; index++) {
        fontManager.render3(
            floatValue + profileArtworkDataArray[0].amount() * floatValue3,
            floatValue2 + profileArtworkDataArray[0].amount2() * floatValue3,
            floatValue + profileArtworkDataArray[index].amount() * floatValue3,
            floatValue2 + profileArtworkDataArray[index].amount2() * floatValue3,
            floatValue + profileArtworkDataArray[index + 1].amount() * floatValue3,
            floatValue2 + profileArtworkDataArray[index + 1].amount2() * floatValue3,
            profileArtworkData2.count2());
      }

      for (int index2 = 0; index2 < profileArtworkDataArray.length; index2++) {
        ProfileArtworkData profileArtworkData = profileArtworkDataArray[index2];
        ProfileArtworkData profileArtworkData3 =
            profileArtworkDataArray[(index2 + 1) % profileArtworkDataArray.length];
        fontManager.render2(
            floatValue + profileArtworkData.amount() * floatValue3,
            floatValue2 + profileArtworkData.amount2() * floatValue3,
            floatValue + profileArtworkData3.amount() * floatValue3,
            floatValue2 + profileArtworkData3.amount2() * floatValue3,
            0.65F,
            637534207);
      }
    }
  }
}
