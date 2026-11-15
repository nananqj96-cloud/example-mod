package bunnythekenzo.client.hud;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.Identifier;
import bunnythekenzo.client.ArgbColors;
import bunnythekenzo.client.ColorPalette;
import bunnythekenzo.client.HudElementContract;
import bunnythekenzo.client.NanoVgImageCache;
import bunnythekenzo.client.ThemeManager;
import bunnythekenzo.client.render.FontManager;

public class PotionsHud extends HudElement {
  public static final float amount = 24.0F;
  public static final float amount2 = 18.0F;
  public static final float amount3 = 7.0F;
  public static final float amount4 = 12.5F;
  public final ThemeManager themeManager;

  public PotionsHud(ThemeManager themeManager2, HudElementContract hudElementContract) {
    super(
        "potions",
        "Effects",
        "Active potion effects and their timers",
        0.995F,
        0.6F,
        hudElementContract);
    this.themeManager = themeManager2;
  }

  public List getList() {
    ClientPlayerEntity player = MinecraftClient.getInstance().player;
    if (player == null) {
      return List.of();
    }

    var arrayList = new ArrayList<>(player.getStatusEffects());
    arrayList.sort(Comparator.comparingInt(StatusEffectInstance::getDuration).reversed());
    return arrayList;
  }

  public static String getString(StatusEffectInstance statusEffectInstance) {
    String string = statusEffectInstance.getEffectType().value().getName().getString();
    int amplifier = statusEffectInstance.getAmplifier();
    return amplifier > 0 ? string + " " + (amplifier + 1) : string;
  }

  public static String getString2(StatusEffectInstance statusEffectInstance) {
    if (statusEffectInstance.isInfinite()) {
      return "∞";
    }

    int intValue = statusEffectInstance.getDuration() / 20;
    return String.format("%d:%02d", intValue / 60, intValue % 60);
  }

  @Override
  public float computeFloat(FontManager fontManager) {
    float floatValue = 110.0F;

    for (StatusEffectInstance statusEffectInstance :
        (java.lang.Iterable<StatusEffectInstance>) (java.lang.Iterable<?>) (this.getList())) {
      floatValue =
          Math.max(
              floatValue,
              31.0F
                  + fontManager.computeFloat(getString(statusEffectInstance), 12.5F)
                  + 10.0F
                  + fontManager.computeFloat(getString2(statusEffectInstance), 12.5F)
                  + 7.0F);
    }

    return floatValue;
  }

  @Override
  public float computeFloat2(FontManager fontManager) {
    return Math.max(24.0F, this.getList().size() * 24.0F);
  }

  @Override
  public void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    ColorPalette colorPalette = this.themeManager.getColorPalette();
    List list = this.getList();
    if (!list.isEmpty()) {
      boolean flag = this.test4();
      float floatValue5 = floatValue2;

      for (StatusEffectInstance statusEffectInstance :
          (java.lang.Iterable<StatusEffectInstance>) (java.lang.Iterable<?>) (list)) {
        float value = fontManager.computeFloat(getString(statusEffectInstance), 12.5F);
        float value2 = fontManager.computeFloat(getString2(statusEffectInstance), 12.5F);
        float floatValue6 = 31.0F + value + 10.0F + value2 + 7.0F;
        float floatValue7 = flag ? floatValue + floatValue3 - floatValue6 : floatValue;
        float floatValue8 = floatValue5 + 12.0F;
        fontManager.render(
            floatValue7,
            floatValue5 + 1.0F,
            floatValue6,
            22.0F,
            7.0F,
            ArgbColors.withOpacity(-15462118, 0.78F));
        Identifier identifier =
            statusEffectInstance
                .getEffectType()
                .getKey()
                .map(
                    registryKey -> {
                      return registryKey.getValue();
                    })
                .orElse(null);
        if (identifier != null) {
          int value3 =
              NanoVgImageCache.computeInt(
                  Identifier.of(
                      identifier.getNamespace(),
                      "textures/mob_effect/" + identifier.getPath() + ".png"));
          if (value3 > 0) {
            fontManager.render(
                value3,
                floatValue7 + 7.0F,
                floatValue8 - 9.0F,
                18.0F,
                18.0F,
                floatValue7 + 7.0F,
                floatValue8 - 9.0F,
                18.0F,
                18.0F,
                1.0F);
          }
        }

        fontManager.computeFloat(
            getString(statusEffectInstance),
            floatValue7 + 7.0F + 18.0F + 6.0F,
            floatValue8,
            12.5F,
            colorPalette.computeInt16());
        fontManager.computeFloat2(
            getString2(statusEffectInstance),
            floatValue7 + floatValue6 - 7.0F - value2,
            floatValue8,
            12.5F,
            colorPalette.computeInt(),
            colorPalette.getCount());
        floatValue5 += 24.0F;
      }
    }
  }
}
