package bunnythekenzo.client.ui;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import bunnythekenzo.client.AnimationIntensitySupport;
import bunnythekenzo.client.ArgbColors;
import bunnythekenzo.client.ClientAudio;
import bunnythekenzo.client.ColorPalette;
import bunnythekenzo.client.ConfigSummary;
import bunnythekenzo.client.JsonSupport2;
import bunnythekenzo.client.SmoothScrollState;
import bunnythekenzo.client.StaggeredTransition;
import bunnythekenzo.client.render.FontManager;

public final class ConfigsPage implements MenuPage {
  public static final float amount = 520.0F;
  public static final float amount2 = 62.0F;
  public static final float amount3 = 8.0F;
  public static final float amount4 = 52.0F;
  public static final float amount5 = 6.0F;
  public static final String[] stringArray = new String[] {"Load", "Update", "Export", "Delete"};
  public static final float amount6 = 24.0F;
  public static final float amount7 = 46.0F;
  public static final int count = -1551236;
  public final Map map = new IdentityHashMap();
  public final StaggeredTransition staggeredTransition = new StaggeredTransition(28.0F, 380.0F);
  public final SmoothScrollState smoothScrollState = new SmoothScrollState();
  public final AnimationIntensitySupport animationIntensitySupport =
      new AnimationIntensitySupport();
  public final AnimationIntensitySupport animationIntensitySupport2 =
      new AnimationIntensitySupport();
  public float amount8;
  public float amount9;
  public float amount10;
  public float amount11;
  public float amount12;
  public float amount13;
  public float amount14;
  public float amount15;
  public float amount16;
  public float amount17;
  public ConfigSummary configSummary;
  public ConfigSummary configSummary2;
  public String string;

  public static List getList() {
    return JsonSupport2.getList4();
  }

  public ConfigsPageSupport view(ConfigSummary configSummary3) {
    return (bunnythekenzo.client.ui.ConfigsPageSupport)
        (this.map.computeIfAbsent(
            configSummary3,
            value -> {
              return new ConfigsPageSupport();
            }));
  }

  @Override
  public void update() {
    this.staggeredTransition.apply(Math.max(1, getList().size()));
  }

  @Override
  public void update2() {
    this.staggeredTransition.apply2(Math.max(1, getList().size()));
  }

  @Override
  public void render(
      FontManager fontManager,
      float amount112,
      float amount122,
      float amount132,
      float amount142,
      float floatValue,
      float floatValue2) {
    this.amount11 = amount112;
    this.amount12 = amount122;
    this.amount13 = amount132;
    this.amount14 = amount142;
    this.string = null;
    List list = getList();
    float floatValue3 = amount112 + (amount132 - 520.0F) / 2.0F;
    fontManager.update2();
    fontManager.render(amount112, amount122, amount132, amount142);
    if (list.isEmpty()) {
      this.render2(
          fontManager, amount112, amount122, amount132, amount142, floatValue, floatValue2);
    } else {
      float floatValue4 = 82.0F + list.size() * 70.0F;
      this.smoothScrollState.apply(floatValue4, amount142);
      float floatValue5 = amount122 + 22.0F - this.smoothScrollState.computeFloat();
      this.render(fontManager, floatValue3, floatValue5, 520.0F, floatValue, floatValue2);
      float floatValue6 = floatValue5 + 46.0F + 14.0F;

      for (int index = 0; index < list.size(); index++) {
        float floatValue7 = floatValue6 + index * 70.0F;
        this.renderSlot(
            fontManager,
            (ConfigSummary) list.get(index),
            floatValue3,
            floatValue7,
            this.staggeredTransition.computeFloat(index),
            SmoothScrollState.computeFloat(
                floatValue7, floatValue7 + 62.0F, amount122, amount122 + amount142),
            floatValue,
            floatValue2);
      }
    }

    fontManager.update3();
    if (floatValue >= amount112
        && floatValue <= amount112 + amount132
        && floatValue2 >= amount122
        && floatValue2 <= amount122 + amount142) {
      this.smoothScrollState.update();
    }

    this.smoothScrollState.render(fontManager, floatValue3 + 520.0F + 16.0F, amount122, amount142);
  }

  public void render2(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6) {
    ColorPalette instance = ColorPalette.getInstance();
    float floatValue7 = floatValue + floatValue3 / 2.0F;
    float floatValue8 = floatValue2 + floatValue4 / 2.0F - 46.0F;
    fontManager.render(
        floatValue7 - 16.0F, floatValue8, 32.0F, 1.0F, 0.5F, ArgbColors.withOpacity(-1, 0.12F));
    fontManager.computeFloat2(
        "No configs yet", floatValue7, floatValue8 + 22.0F, 13.5F, instance.computeInt18());
    fontManager.computeFloat2(
        "Save your current setup, or import one you were sent",
        floatValue7,
        floatValue8 + 40.0F,
        11.0F,
        instance.computeInt19());
    this.render(
        fontManager, floatValue7 - 190.0F, floatValue8 + 62.0F, 380.0F, floatValue5, floatValue6);
  }

  public void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5) {
    float floatValue6 = (floatValue3 - 10.0F) / 2.0F;
    this.render3(fontManager, floatValue, floatValue2, floatValue6, floatValue4, floatValue5);
    this.render2(
        fontManager,
        floatValue + floatValue6 + 10.0F,
        floatValue2,
        floatValue6,
        floatValue4,
        floatValue5);
  }

  public void render2(
      FontManager fontManager,
      float amount82,
      float amount92,
      float amount102,
      float floatValue,
      float floatValue2) {
    ColorPalette instance = ColorPalette.getInstance();
    this.amount8 = amount82;
    this.amount9 = amount92;
    this.amount10 = amount102;
    boolean enabled = ConfigFileDialogs.isEnabled();
    float value =
        this.animationIntensitySupport2.computeFloat(
            !enabled
                && floatValue >= amount82
                && floatValue <= amount82 + amount102
                && floatValue2 >= amount92
                && floatValue2 <= amount92 + 46.0F);
    fontManager.render(
        amount82,
        amount92,
        amount102,
        46.0F,
        9.0F,
        ArgbColors.withOpacity(-1, 0.03F + 0.04F * value));
    fontManager.render(
        amount82,
        amount92,
        amount102,
        46.0F,
        9.0F,
        1.0F,
        ArgbColors.withOpacity(-1, 0.1F + 0.14F * value));
    String text = enabled ? "Choosing a file…" : "Import";
    float floatValue3 = amount82 + amount102 / 2.0F;
    float floatValue4 = amount92 + 23.0F;
    int intValue =
        enabled
            ? instance.computeInt19()
            : ArgbColors.lerp(instance.computeInt18(), instance.computeInt16(), value);
    if (!enabled) {
      float floatValue5 = floatValue3 - fontManager.computeFloat(text, 13.5F) / 2.0F - 15.0F;
      fontManager.render(floatValue5 - 0.75F, floatValue4 - 6.0F, 1.5F, 7.0F, 0.75F, intValue);
      fontManager.render(floatValue5, floatValue4 + 2.5F, 3.5F, 1.5F, intValue, true);
      fontManager.render(floatValue5 - 5.0F, floatValue4 + 5.5F, 10.0F, 1.5F, 0.75F, intValue);
    }

    fontManager.computeFloat(
        text,
        floatValue3 - fontManager.computeFloat(text, 13.5F) / 2.0F + (enabled ? 0 : 5),
        floatValue4,
        13.5F,
        intValue);
  }

  public void render3(
      FontManager fontManager,
      float amount152,
      float amount162,
      float amount172,
      float floatValue,
      float floatValue2) {
    ColorPalette instance = ColorPalette.getInstance();
    this.amount15 = amount152;
    this.amount16 = amount162;
    this.amount17 = amount172;
    float value =
        this.animationIntensitySupport.computeFloat(
            floatValue >= amount152
                && floatValue <= amount152 + amount172
                && floatValue2 >= amount162
                && floatValue2 <= amount162 + 46.0F);
    fontManager.render(
        amount152,
        amount162,
        amount172,
        46.0F,
        9.0F,
        ArgbColors.withOpacity(instance.computeInt(), 0.09F + 0.09F * value));
    fontManager.render(
        amount152,
        amount162,
        amount172,
        46.0F,
        9.0F,
        1.0F,
        ArgbColors.withOpacity(instance.computeInt(), 0.28F + 0.34F * value));
    float floatValue3 = amount152 + amount172 / 2.0F;
    float floatValue4 = amount162 + 23.0F;
    float value2 = fontManager.computeFloat("Add config", 13.5F);
    float floatValue5 = floatValue3 - value2 / 2.0F - 13.0F;
    int value3 = ArgbColors.lerp(instance.computeInt(), -1, value * 0.4F);
    fontManager.render(floatValue5 - 5.0F, floatValue4 - 0.75F, 10.0F, 1.5F, 0.75F, value3);
    fontManager.render(floatValue5 - 0.75F, floatValue4 - 5.0F, 1.5F, 10.0F, 0.75F, value3);
    fontManager.computeFloat(
        "Add config", floatValue3 - value2 / 2.0F + 5.0F, floatValue4, 13.5F, value3);
  }

  public void renderSlot(
      FontManager fontManager,
      ConfigSummary configSummary3,
      float amount18,
      float amount22,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    ConfigsPageSupport configsPageSupport = this.view(configSummary3);
    configsPageSupport.amount = amount18;
    configsPageSupport.amount2 = amount22;
    if (floatValue * floatValue2 <= 0.004F) {
      configsPageSupport.animationIntensitySupport.update();
    } else {
      ColorPalette instance = ColorPalette.getInstance();
      int value = this.computeInt(configSummary3, floatValue3, floatValue4);
      float value2 =
          configsPageSupport.animationIntensitySupport.computeFloat(
              this.test(configSummary3, floatValue3, floatValue4) && value < 0);
      configsPageSupport.dampedSpring.setTarget(configSummary3 == this.configSummary ? 1.0F : 0.0F);
      float floatValue5 = Math.clamp(configsPageSupport.dampedSpring.advance(), 0.0F, 1.0F);
      fontManager.update2();
      fontManager.apply3(floatValue * floatValue2);
      fontManager.apply((1.0F - floatValue) * 18.0F, 0.0F);
      fontManager.apply(0.0F, -value2 * 1.5F);
      fontManager.render(
          amount18,
          amount22,
          520.0F,
          62.0F,
          9.0F,
          8.0F + value2 * 4.0F,
          2.0F,
          ArgbColors.withOpacity(-16777216, 0.22F + value2 * 0.16F));
      int value3 = ArgbColors.lerp(instance.computeInt10(), instance.computeInt11(), value2);
      int value4 = ArgbColors.lerp(value3, instance.computeInt12(), floatValue5 * 0.8F);
      fontManager.render(
          amount18,
          amount22,
          520.0F,
          62.0F,
          9.0F,
          ArgbColors.lighten(value4, 0.04F),
          value4,
          1.15F);
      if (!configsPageSupport.clickRippleEffects.test()) {
        fontManager.update2();
        fontManager.render(amount18, amount22, 520.0F, 62.0F);
        configsPageSupport.clickRippleEffects.apply(
            value5 -> {
              fontManager.render2(
                  ((bunnythekenzo.client.CollectionData5) (value5)).amount(),
                  ((bunnythekenzo.client.CollectionData5) (value5)).amount2(),
                  ((bunnythekenzo.client.CollectionData5) (value5)).amount3(),
                  ArgbColors.withOpacity(
                      instance.computeInt(),
                      0.11F * ((bunnythekenzo.client.CollectionData5) (value5)).amount4()));
            });
        fontManager.update3();
      }

      fontManager.render(
          amount18,
          amount22,
          520.0F,
          62.0F,
          9.0F,
          1.0F,
          ArgbColors.lerp(
              ArgbColors.withOpacity(-1, 0.07F + 0.05F * value2),
              instance.computeInt(0.75F),
              floatValue5));
      if (floatValue5 > 0.01F) {
        float floatValue6 = 36.0F * floatValue5;
        fontManager.render(
            amount18 + 1.5F,
            amount22 + 31.0F - floatValue6 / 2.0F,
            2.5F,
            floatValue6,
            1.25F,
            ArgbColors.withOpacity(instance.computeInt(), floatValue5));
      }

      int intValue =
          configSummary3.isEnabled()
              ? ArgbColors.lerp(
                  instance.computeInt17(), instance.computeInt16(), Math.max(value2, floatValue5))
              : instance.computeInt18();
      fontManager.computeFloat(
          configSummary3.getString(),
          amount18 + 16.0F,
          amount22 + 23.0F,
          13.5F,
          ArgbColors.lerp(intValue, instance.computeInt(), floatValue5 * 0.7F));
      fontManager.computeFloat(
          configSummary3.getString2(),
          amount18 + 16.0F,
          amount22 + 41.0F,
          11.0F,
          configSummary3.isEnabled() ? instance.computeInt18() : instance.computeInt19());
      float floatValue7 = Math.max(value2, value >= 0 ? 1.0F : 0.0F);
      if (floatValue7 > 0.01F) {
        fontManager.update2();
        fontManager.apply3(floatValue7);
        float floatValue8 = amount22 + 19.0F;
        boolean flag = configSummary3 == this.configSummary2;

        for (int index = 0; index < stringArray.length; index++) {
          boolean flag2 = index == stringArray.length - 1;
          String text = flag2 && flag ? "Sure?" : stringArray[index];

          int intValue2 =
              switch (index) {
                case 0 -> instance.computeInt();
                case 3 -> -1551236;
                default -> instance.computeInt16();
              };
          boolean flag3 = index == 1 || configSummary3.isEnabled();
          this.render(
              fontManager,
              instance,
              computeFloat(amount18, index),
              floatValue8,
              text,
              configsPageSupport.animationIntensitySupportArray[index],
              value == index || flag2 && flag,
              flag3,
              intValue2);
        }

        fontManager.update3();
      } else {
        for (AnimationIntensitySupport animationIntensitySupport3 :
            configsPageSupport.animationIntensitySupportArray) {
          animationIntensitySupport3.update();
        }
      }

      fontManager.update3();
      if (this.test(configSummary3, floatValue3, floatValue4)) {
        this.string =
            configSummary3.isEnabled()
                ? "Load it, update it to match the client, export it, or delete it"
                : "Update this config to match the client as it is now";
      }
    }
  }

  public void render(
      FontManager fontManager,
      ColorPalette colorPalette,
      float floatValue,
      float floatValue2,
      String text,
      AnimationIntensitySupport animationIntensitySupport3,
      boolean flag,
      boolean flag2,
      int intValue) {
    float value = animationIntensitySupport3.computeFloat(flag && flag2);
    if (value > 0.01F) {
      fontManager.render(
          floatValue,
          floatValue2,
          52.0F,
          24.0F,
          12.0F,
          ArgbColors.withOpacity(intValue, 0.16F * value));
    }

    fontManager.render(
        floatValue,
        floatValue2,
        52.0F,
        24.0F,
        12.0F,
        1.0F,
        flag2
            ? ArgbColors.lerp(
                ArgbColors.withOpacity(-1, 0.12F), ArgbColors.withOpacity(intValue, 0.8F), value)
            : ArgbColors.withOpacity(-1, 0.06F));
    fontManager.computeFloat2(
        text,
        floatValue + 26.0F,
        floatValue2 + 12.0F,
        11.0F,
        flag2
            ? ArgbColors.lerp(colorPalette.computeInt18(), intValue, value)
            : colorPalette.computeInt19());
  }

  public boolean test(ConfigSummary configSummary3, float floatValue, float floatValue2) {
    ConfigsPageSupport configsPageSupport = this.view(configSummary3);
    return floatValue >= configsPageSupport.amount
        && floatValue <= configsPageSupport.amount + 520.0F
        && floatValue2 >= configsPageSupport.amount2
        && floatValue2 <= configsPageSupport.amount2 + 62.0F;
  }

  public static float computeFloat(float floatValue, int intValue) {
    float floatValue2 = floatValue + 520.0F - 16.0F - 52.0F;
    return floatValue2 - 58.0F * (stringArray.length - 1 - intValue);
  }

  public int computeInt(ConfigSummary configSummary3, float floatValue, float floatValue2) {
    ConfigsPageSupport configsPageSupport = this.view(configSummary3);
    float floatValue3 = configsPageSupport.amount2 + 19.0F;
    if (!(floatValue2 < floatValue3) && !(floatValue2 > floatValue3 + 24.0F)) {
      for (int index = 0; index < stringArray.length; index++) {
        float floatValue4 = computeFloat(configsPageSupport.amount, index);
        if (floatValue >= floatValue4 && floatValue <= floatValue4 + 52.0F) {
          return index;
        }
      }

      return -1;
    } else {
      return -1;
    }
  }

  @Override
  public boolean test(float floatValue, float floatValue2, int intValue) {
    if (floatValue >= this.amount15
        && floatValue <= this.amount15 + this.amount17
        && floatValue2 >= this.amount16
        && floatValue2 <= this.amount16 + 46.0F) {
      ConfigSummary configSummary3 = JsonSupport2.getConfigSummary();
      configSummary3.update();
      if (this.configSummary == null) {
        this.configSummary = configSummary3;
      }

      this.staggeredTransition.apply(getList().size());
      JsonSupport2.update6();
      ClientAudio.update6();
      return true;
    } else {
      if (floatValue >= this.amount8
          && floatValue <= this.amount8 + this.amount10
          && floatValue2 >= this.amount9
          && floatValue2 <= this.amount9 + 46.0F) {
        ConfigFileDialogs.importConfig(
            value -> {
              JsonSupport2.getConfigSummary((bunnythekenzo.client.ConfigSummary) (value));
              JsonSupport2.update6();
              this.staggeredTransition.apply(getList().size());
              ClientAudio.update6();
            });
        ClientAudio.update5();
        return true;
      }

      for (ConfigSummary configSummary4 :
          (java.lang.Iterable<ConfigSummary>) (java.lang.Iterable<?>) (List.copyOf(getList()))) {
        if (this.test(configSummary4, floatValue, floatValue2)) {
          int value2 = this.computeInt(configSummary4, floatValue, floatValue2);
          switch (value2) {
            case 0:
              this.apply(configSummary4);
              break;
            case 1:
              configSummary4.update();
              JsonSupport2.update6();
              ClientAudio.apply(true);
              break;
            case 2:
              if (configSummary4.isEnabled()) {
                ConfigFileDialogs.exportConfig(configSummary4, ClientAudio::update6);
                ClientAudio.update5();
              }
              break;
            case 3:
              this.apply2(configSummary4);
              break;
            default:
              this.view(configSummary4).clickRippleEffects.render(floatValue, floatValue2, 312.0F);
              this.apply(configSummary4);
          }

          if (value2 != 3) {
            this.configSummary2 = null;
          }

          this.view(configSummary4).animationIntensitySupport.apply(true);
          return true;
        }
      }

      this.configSummary2 = null;
      return this.test(floatValue, floatValue2);
    }
  }

  public void apply(ConfigSummary configSummary3) {
    if (!configSummary3.isEnabled()) {
      ClientAudio.update5();
    } else {
      configSummary3.update2();
      if (this.configSummary == configSummary3) {
        ClientAudio.update5();
      } else {
        this.configSummary = configSummary3;
        ClientAudio.update6();
      }
    }
  }

  public void apply2(ConfigSummary configSummary22) {
    if (this.configSummary2 != configSummary22) {
      this.configSummary2 = configSummary22;
      ClientAudio.update5();
    } else {
      this.configSummary2 = null;
      this.map.remove(configSummary22);
      if (this.configSummary == configSummary22) {
        this.configSummary = null;
      }

      JsonSupport2.apply(configSummary22);
      JsonSupport2.update6();
      ClientAudio.apply(false);
    }
  }

  @Override
  public void update5() {
    for (ConfigsPageSupport configsPageSupport :
        (java.lang.Iterable<ConfigsPageSupport>) (java.lang.Iterable<?>) (this.map.values())) {
      configsPageSupport.animationIntensitySupport.apply(false);
    }
  }

  @Override
  public boolean test(float floatValue, float floatValue2, double doubleValue) {
    if (!this.test(floatValue, floatValue2)) {
      return false;
    }

    this.smoothScrollState.test(doubleValue);
    return true;
  }

  @Override
  public boolean test() {
    if (this.configSummary2 == null) {
      return false;
    }

    this.configSummary2 = null;
    return true;
  }

  @Override
  public String getString() {
    return this.string;
  }

  public boolean test(float floatValue, float floatValue2) {
    return floatValue >= this.amount11
        && floatValue <= this.amount11 + this.amount13
        && floatValue2 >= this.amount12
        && floatValue2 <= this.amount12 + this.amount14;
  }
}
