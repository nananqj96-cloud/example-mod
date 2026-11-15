package bunnythekenzo.client.hud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import bunnythekenzo.client.ArgbColors;
import bunnythekenzo.client.ColorPalette;
import bunnythekenzo.client.ThemeManager;
import bunnythekenzo.client.module.RegionMapModule;
import bunnythekenzo.client.render.FontManager;

public class RegionMapHud extends HudElement {
  public static final float amount = 8.0F;
  public static final float amount2 = 12.0F;
  public static final float amount3 = 108.0F;
  public static final float amount4 = 15.0F;
  public static final float amount5 = 5.0F;
  public static final float amount6 = 8.0F;
  public static final float amount7 = 12.0F;
  public static final int count = 2;
  public final RegionMapModule regionMapModule;
  public final ThemeManager themeManager;

  public RegionMapHud(RegionMapModule regionMapModule2, ThemeManager themeManager2) {
    super(
        "regionMap",
        "Region Map",
        "Terrain minimap of the region around you",
        0.008F,
        0.05F,
        HudElement.getHudElementContract(regionMapModule2));
    this.regionMapModule = regionMapModule2;
    this.themeManager = themeManager2;
  }

  public float computeFloat() {
    return (float) Math.ceil(this.regionMapModule.computeInt2() / 2.0);
  }

  @Override
  public float computeFloat(FontManager fontManager) {
    return 124.0F;
  }

  @Override
  public float computeFloat2(FontManager fontManager) {
    float floatValue = 144.0F;
    if ((Boolean) this.regionMapModule.legendSetting.getValue()) {
      floatValue = 144.0F + (8.0F + this.computeFloat() * 12.0F);
    }

    return floatValue;
  }

  @Override
  public void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    ColorPalette colorPalette = this.themeManager.getColorPalette();
    ClientPlayerEntity player = MinecraftClient.getInstance().player;
    if (player != null) {
      fontManager.render2(
          floatValue,
          floatValue2,
          floatValue3,
          floatValue4,
          13.0F,
          8.0F,
          ArgbColors.withOpacity(-16777216, 0.3F));
      fontManager.render(
          floatValue,
          floatValue2,
          floatValue3,
          floatValue4,
          10.0F,
          colorPalette.computeInt7(),
          colorPalette.computeInt8(),
          true);
      int int3 = this.regionMapModule.computeInt3();
      int int2 = this.regionMapModule.computeInt2(player.getX(), player.getZ());
      this.render(fontManager, colorPalette, floatValue, floatValue2, floatValue3, int3);
      float floatValue5 = floatValue + 8.0F;
      float floatValue6 = floatValue2 + 8.0F + 15.0F + 5.0F;
      this.render(fontManager, colorPalette, player, floatValue5, floatValue6);
      if ((Boolean) this.regionMapModule.legendSetting.getValue()) {
        this.render(fontManager, colorPalette, floatValue5, floatValue6 + 108.0F + 8.0F, int2);
      }
    }
  }

  public void render(
      FontManager fontManager,
      ColorPalette colorPalette,
      float floatValue,
      float floatValue2,
      float floatValue3,
      int intValue) {
    float floatValue4 = floatValue2 + 8.0F + 7.5F;
    fontManager.computeFloat(
        "REGION MAP", floatValue + 8.0F, floatValue4, 9.0F, colorPalette.computeInt16());
    String text = intValue >= 0 ? "#" + intValue : "N/A";
    float value = fontManager.computeFloat(text, 8.5F);
    float floatValue5 = value + 9.0F;
    float floatValue6 = floatValue + floatValue3 - 8.0F - floatValue5;
    float floatValue7 = floatValue4 - 6.25F;
    boolean flag = intValue >= 0;
    fontManager.render(
        floatValue6,
        floatValue7,
        floatValue5,
        12.5F,
        6.0F,
        ArgbColors.withOpacity(colorPalette.getCount(), flag ? 0.18F : 0.1F));
    fontManager.render(
        floatValue6,
        floatValue7,
        floatValue5,
        12.5F,
        6.0F,
        1.0F,
        ArgbColors.withOpacity(colorPalette.getCount(), flag ? 0.45F : 0.2F));
    fontManager.computeFloat(
        text,
        floatValue6 + 4.5F,
        floatValue4,
        8.5F,
        flag ? colorPalette.computeInt() : colorPalette.computeInt18());
  }

  public void render(
      FontManager fontManager,
      ColorPalette colorPalette,
      ClientPlayerEntity clientPlayerEntity,
      float floatValue,
      float floatValue2) {
    int value = this.regionMapModule.computeInt();
    int intValue =
        (int)
            (Math.clamp(this.regionMapModule.opacitySetting.floatValue() / 100.0F, 0.0F, 1.0F)
                * 255.0F);
    fontManager.render(
        floatValue, floatValue2, 108.0F, 108.0F, 4.0F, ArgbColors.withOpacity(-16054000, 0.92F));
    fontManager.update2();
    fontManager.render(floatValue, floatValue2, 108.0F, 108.0F);

    for (int index = 0; index < value; index++) {
      for (int index2 = 0; index2 < value; index2++) {
        int int2 = this.regionMapModule.computeInt2(index * value + index2);
        if (int2 >= 0) {
          int value2 =
              ArgbColors.withAlpha(0xFF000000 | this.regionMapModule.computeInt(int2), intValue);
          float floatValue3 = floatValue + index2 * 12.0F;
          float floatValue4 = floatValue2 + index * 12.0F;
          fontManager.render(floatValue3 + 1.0F, floatValue4 + 1.0F, 10.0F, 10.0F, 1.5F, value2);
        }
      }
    }

    if ((Boolean) this.regionMapModule.gridLinesSetting.getValue()) {
      int value3 = ArgbColors.withOpacity(colorPalette.getCount(), 0.14F);

      for (int index3 = 0; index3 <= value; index3++) {
        float floatValue5 = index3 * 12.0F;
        fontManager.render2(
            floatValue + floatValue5,
            floatValue2,
            floatValue + floatValue5,
            floatValue2 + 108.0F,
            1.0F,
            value3);
        fontManager.render2(
            floatValue,
            floatValue2 + floatValue5,
            floatValue + 108.0F,
            floatValue2 + floatValue5,
            1.0F,
            value3);
      }
    }

    int[] countArray =
        this.regionMapModule.getCountArray(clientPlayerEntity.getX(), clientPlayerEntity.getZ());
    boolean flag =
        countArray[0] >= 0 && countArray[0] < value && countArray[1] >= 0 && countArray[1] < value;
    if (flag) {
      float floatValue6 = floatValue + countArray[0] * 12.0F;
      float floatValue7 = floatValue2 + countArray[1] * 12.0F;
      fontManager.render2(
          floatValue6,
          floatValue7,
          12.0F,
          12.0F,
          2.0F,
          3.0F,
          ArgbColors.withOpacity(colorPalette.getCount(), 0.35F));
      fontManager.render(
          floatValue6 + 0.5F,
          floatValue7 + 0.5F,
          11.0F,
          11.0F,
          2.0F,
          1.2F,
          colorPalette.computeInt());
    }

    if ((Boolean) this.regionMapModule.cellNumbersSetting.getValue()) {
      for (int index4 = 0; index4 < value; index4++) {
        for (int index5 = 0; index5 < value; index5++) {
          int int22 = this.regionMapModule.computeInt2(index4 * value + index5);
          if (int22 >= 0) {
            String text = String.valueOf(this.regionMapModule.computeInt3(index4 * value + index5));
            float floatValue8 = text.length() >= 3 ? 5.5F : 6.5F;
            float value4 = fontManager.computeFloat(text, floatValue8);
            float floatValue9 = floatValue + index5 * 12.0F + (12.0F - value4) / 2.0F;
            float floatValue10 = floatValue2 + index4 * 12.0F + 6.0F;
            fontManager.computeFloat(
                text,
                floatValue9 + 0.5F,
                floatValue10 + 0.5F,
                floatValue8,
                ArgbColors.withOpacity(-16777216, 0.55F));
            fontManager.computeFloat(text, floatValue9, floatValue10, floatValue8, -790280);
          }
        }
      }
    }

    if (flag) {
      double[] valueArray =
          this.regionMapModule.getValueArray(clientPlayerEntity.getX(), clientPlayerEntity.getZ());
      float floatValue11 = floatValue + (float) ((countArray[0] + valueArray[0]) * 12.0);
      float floatValue12 = floatValue2 + (float) ((countArray[1] + valueArray[1]) * 12.0);
      fontManager.render3(
          floatValue11,
          floatValue12,
          2.2F,
          3.5F,
          ArgbColors.withOpacity(colorPalette.getCount(), 0.75F));
      fontManager.update2();
      fontManager.apply(floatValue11, floatValue12);
      fontManager.apply2((float) Math.toRadians(clientPlayerEntity.getYaw() + 180.0F));
      fontManager.render3(
          0.0F, -4.9F, 3.3F, 2.9F, -3.3F, 2.9F, ArgbColors.withOpacity(-16777216, 0.55F));
      fontManager.render3(0.0F, -4.0F, 2.6F, 2.2F, -2.6F, 2.2F, colorPalette.computeInt());
      fontManager.update3();
    }

    fontManager.update3();
    fontManager.render(
        floatValue,
        floatValue2,
        108.0F,
        108.0F,
        4.0F,
        1.0F,
        ArgbColors.withOpacity(colorPalette.getCount(), 0.5F));
  }

  public void render(
      FontManager fontManager,
      ColorPalette colorPalette,
      float floatValue,
      float floatValue2,
      int intValue) {
    for (int index = 0; index < this.regionMapModule.computeInt2(); index++) {
      int intValue2 = index % 2;
      int intValue3 = index / 2;
      float floatValue3 = floatValue + intValue2 * 54.0F;
      float floatValue4 = floatValue2 + intValue3 * 12.0F + 6.0F;
      fontManager.render(
          floatValue3,
          floatValue4 - 2.5F,
          5.0F,
          5.0F,
          1.2F,
          0xFF000000 | this.regionMapModule.computeInt(index));
      boolean flag = index == intValue;
      fontManager.computeFloat(
          this.regionMapModule.getString(index),
          floatValue3 + 8.0F,
          floatValue4,
          7.5F,
          flag ? colorPalette.computeInt() : colorPalette.computeInt18());
    }
  }
}
