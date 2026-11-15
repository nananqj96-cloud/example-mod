package bunnythekenzo.client.hud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import bunnythekenzo.client.ArgbColors;
import bunnythekenzo.client.ColorPalette;
import bunnythekenzo.client.HudElementContract;
import bunnythekenzo.client.NanoVgImageCache;
import bunnythekenzo.client.ThemeManager;
import bunnythekenzo.client.WorldSignalTypeData;
import bunnythekenzo.client.module.HUDModule;
import bunnythekenzo.client.module.SusChunkFinderModule;
import bunnythekenzo.client.render.FontManager;

public class RadarHud extends HudElement {
  public static final float amount = 110.0F;
  public final HUDModule hUDModule;
  public final SusChunkFinderModule susChunkFinderModule;
  public final ThemeManager themeManager;

  public RadarHud(
      HUDModule hUDModule2,
      SusChunkFinderModule susChunkFinderModule2,
      ThemeManager themeManager2,
      HudElementContract hudElementContract) {
    super(
        "radar", "Radar", "Nearby players on a circular sweep", 0.006F, 0.45F, hudElementContract);
    this.hUDModule = hUDModule2;
    this.susChunkFinderModule = susChunkFinderModule2;
    this.themeManager = themeManager2;
  }

  @Override
  public float computeFloat(FontManager fontManager) {
    return 110.0F;
  }

  @Override
  public float computeFloat2(FontManager fontManager) {
    return 110.0F;
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
      float floatValue5 = floatValue3 / 2.0F;
      float floatValue6 = floatValue + floatValue5;
      float floatValue7 = floatValue2 + floatValue5;
      fontManager.render2(
          floatValue6, floatValue7, floatValue5, ArgbColors.withOpacity(-15856621, 0.55F));
      fontManager.render2(
          floatValue6,
          floatValue7,
          floatValue5 * 0.5F,
          1.0F,
          ArgbColors.withOpacity(colorPalette.getCount(), 0.22F));
      int value = ArgbColors.withOpacity(colorPalette.getCount(), 0.18F);
      fontManager.render2(
          floatValue6 - floatValue5,
          floatValue7,
          floatValue6 + floatValue5,
          floatValue7,
          1.0F,
          value);
      fontManager.render2(
          floatValue6,
          floatValue7 - floatValue5,
          floatValue6,
          floatValue7 + floatValue5,
          1.0F,
          value);
      float yaw = player.getYaw();
      float floatValue8 = (float) Math.toRadians(yaw + 90.0F);
      String[] textArray = new String[] {"N", "E", "S", "W"};
      float[][] floatValuesArray =
          new float[][] {{0.0F, -1.0F}, {1.0F, 0.0F}, {0.0F, 1.0F}, {-1.0F, 0.0F}};

      for (int index = 0; index < 4; index++) {
        float floatValue9 =
            computeFloat(floatValuesArray[index][0], floatValuesArray[index][1], floatValue8);
        float floatValue10 =
            computeFloat2(floatValuesArray[index][0], floatValuesArray[index][1], floatValue8);
        float floatValue11 = floatValue6 + floatValue9 * (floatValue5 - 9.0F);
        float floatValue12 = floatValue7 + floatValue10 * (floatValue5 - 9.0F);
        boolean flag = index == 0;
        fontManager.computeFloat(
            textArray[index],
            floatValue11 - fontManager.computeFloat(textArray[index], 11.0F) / 2.0F,
            floatValue12,
            11.0F,
            flag ? colorPalette.computeInt() : colorPalette.computeInt18());
      }

      float value2 = this.hUDModule.radarRangeSetting.floatValue();
      if (this.susChunkFinderModule.isEnabled()
          && (Boolean) this.susChunkFinderModule.showOnRadarSetting.getValue()) {
        int value3 = this.susChunkFinderModule.suspiciousChunkScanner.computeInt();

        for (WorldSignalTypeData worldSignalTypeData :
            (java.lang.Iterable<WorldSignalTypeData>)
                (java.lang.Iterable<?>)
                    (this.susChunkFinderModule.suspiciousChunkScanner.getList3())) {
          this.render(
              fontManager,
              colorPalette,
              floatValue6,
              floatValue7,
              floatValue5,
              value2,
              floatValue8,
              (float) (worldSignalTypeData.value() - player.getX()),
              (float) (worldSignalTypeData.value2() - player.getZ()),
              computeFloat(worldSignalTypeData.value4(), value3));
        }
      }

      fontManager.render3(
          floatValue6,
          floatValue7,
          3.0F,
          4.0F,
          ArgbColors.withOpacity(colorPalette.getCount(), 0.6F));
      fontManager.render2(floatValue6, floatValue7, 3.0F, colorPalette.computeInt());
      float floatValue13 = Math.max(10.0F, floatValue5 * 0.17F);

      for (AbstractClientPlayerEntity abstractClientPlayerEntity :
          MinecraftClient.getInstance().world.getPlayers()) {
        if (abstractClientPlayerEntity != player) {
          float floatValue14 = (float) (abstractClientPlayerEntity.getX() - player.getX());
          float floatValue15 = (float) (abstractClientPlayerEntity.getZ() - player.getZ());
          float floatValue16 =
              (float) Math.sqrt(floatValue14 * floatValue14 + floatValue15 * floatValue15);
          if (!(floatValue16 > value2)) {
            float floatValue17 = floatValue16 / value2;
            float floatValue18 =
                floatValue6
                    + computeFloat(floatValue14, floatValue15, floatValue8)
                        / Math.max(floatValue16, 0.001F)
                        * floatValue17
                        * (floatValue5 - floatValue13 / 2.0F - 3.0F);
            float floatValue19 =
                floatValue7
                    + computeFloat2(floatValue14, floatValue15, floatValue8)
                        / Math.max(floatValue16, 0.001F)
                        * floatValue17
                        * (floatValue5 - floatValue13 / 2.0F - 3.0F);
            if ((Boolean) this.hUDModule.radarHeadsSetting.getValue()) {
              int intValue =
                  NanoVgImageCache.wrapGlTexture(
                      abstractClientPlayerEntity.getSkin().body().texturePath(), 64, 64);
              if (intValue > 0) {
                float floatValue20 = floatValue18 - floatValue13 / 2.0F;
                float floatValue21 = floatValue19 - floatValue13 / 2.0F;
                NanoVgImageCache.render(
                    fontManager,
                    intValue,
                    64.0F,
                    64.0F,
                    8.0F,
                    8.0F,
                    16.0F,
                    16.0F,
                    floatValue20,
                    floatValue21,
                    floatValue13,
                    floatValue13,
                    1.0F);
                NanoVgImageCache.render(
                    fontManager,
                    intValue,
                    64.0F,
                    64.0F,
                    40.0F,
                    8.0F,
                    48.0F,
                    16.0F,
                    floatValue20,
                    floatValue21,
                    floatValue13,
                    floatValue13,
                    1.0F);
                fontManager.render(
                    floatValue20 - 1.0F,
                    floatValue21 - 1.0F,
                    floatValue13 + 2.0F,
                    floatValue13 + 2.0F,
                    3.0F,
                    1.0F,
                    ArgbColors.withOpacity(-1, 0.35F));
                continue;
              }
            }

            fontManager.render3(
                floatValue18, floatValue19, 2.5F, 3.0F, ArgbColors.withOpacity(-1, 0.4F));
            fontManager.render2(floatValue18, floatValue19, 2.5F, -1);
          }
        }
      }
    }
  }

  public static float computeFloat(double doubleValue, int intValue) {
    return intValue <= 0
        ? 1.0F
        : Math.clamp((float) ((doubleValue - intValue) / (intValue * 2.0)), 0.0F, 1.0F);
  }

  public void render(
      FontManager fontManager,
      ColorPalette colorPalette,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      float floatValue7,
      float floatValue8) {
    float floatValue9 = (float) Math.sqrt(floatValue6 * floatValue6 + floatValue7 * floatValue7);
    if (!(floatValue9 < 0.5F)) {
      boolean flag = floatValue9 > floatValue4;
      float floatValue10 = Math.min(floatValue9 / floatValue4, 1.0F);
      float floatValue11 = flag ? 6.0F : 9.0F;
      float floatValue12 =
          floatValue
              + computeFloat(floatValue6, floatValue7, floatValue5)
                  / floatValue9
                  * floatValue10
                  * (floatValue3 - floatValue11);
      float floatValue13 =
          floatValue2
              + computeFloat2(floatValue6, floatValue7, floatValue5)
                  / floatValue9
                  * floatValue10
                  * (floatValue3 - floatValue11);
      double doubleValue = System.nanoTime() % 1000000000000L / 1.0E9;
      float floatValue14 = (float) (0.5 + 0.5 * Math.sin(doubleValue * Math.PI * 2.0 / 1.8));
      float floatValue15 = (flag ? 2.4F : 3.0F + 1.5F * floatValue8) * (1.0F + 0.1F * floatValue14);
      float floatValue16 = (0.72F + 0.28F * floatValue8) * (0.88F + 0.12F * floatValue14);
      int count = colorPalette.getCount();
      fontManager.render2(
          floatValue12 - floatValue15,
          floatValue13 - floatValue15,
          floatValue15 * 2.0F,
          floatValue15 * 2.0F,
          floatValue15 * 0.6F,
          3.5F + 2.5F * floatValue14,
          ArgbColors.withOpacity(count, (0.3F + 0.25F * floatValue8) * floatValue16));
      fontManager.render(
          floatValue12 - floatValue15,
          floatValue13 - floatValue15,
          floatValue15 * 2.0F,
          floatValue15 * 2.0F,
          floatValue15 * 0.6F,
          ArgbColors.withOpacity(count, floatValue16));
      fontManager.render(
          floatValue12 - floatValue15,
          floatValue13 - floatValue15,
          floatValue15 * 2.0F,
          floatValue15 * 2.0F,
          floatValue15 * 0.6F,
          1.0F,
          ArgbColors.withOpacity(colorPalette.computeInt(), 0.55F * floatValue16));
      if (flag && floatValue9 <= floatValue4 * 2.5F) {
        String text = (int) floatValue9 + "m";
        float value = fontManager.computeFloat(text, 8.5F);
        float floatValue17 = floatValue12 + (floatValue - floatValue12) * 0.24F;
        float floatValue18 = floatValue13 + (floatValue2 - floatValue13) * 0.24F;
        fontManager.render(
            floatValue17 - value / 2.0F - 3.0F,
            floatValue18 - 5.5F,
            value + 6.0F,
            11.0F,
            5.5F,
            ArgbColors.withOpacity(-15856621, 0.72F));
        fontManager.computeFloat(
            text,
            floatValue17 - value / 2.0F,
            floatValue18,
            8.5F,
            ArgbColors.withOpacity(colorPalette.computeInt(), 0.95F));
      }
    }
  }

  public static float computeFloat(float floatValue, float floatValue2, float floatValue3) {
    return (float) (-floatValue * Math.sin(floatValue3) + floatValue2 * Math.cos(floatValue3));
  }

  public static float computeFloat2(float floatValue, float floatValue2, float floatValue3) {
    return (float) (-(floatValue * Math.cos(floatValue3) + floatValue2 * Math.sin(floatValue3)));
  }
}
