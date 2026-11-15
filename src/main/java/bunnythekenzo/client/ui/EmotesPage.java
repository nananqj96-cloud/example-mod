package bunnythekenzo.client.ui;

import java.util.ArrayList;
import java.util.List;
import bunnythekenzo.client.AnimatedTextRows;
import bunnythekenzo.client.AnimationIntensitySupport;
import bunnythekenzo.client.ArgbColors;
import bunnythekenzo.client.ClientAudio;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.ColorPalette;
import bunnythekenzo.client.EmoteCatalog;
import bunnythekenzo.client.EmoteDefinition;
import bunnythekenzo.client.StaggeredTransition;
import bunnythekenzo.client.render.FontManager;
import bunnythekenzo.client.setting.SettingWidget;

public final class EmotesPage implements MenuPage {
  public static final float amount = 146.0F;
  public static final float amount2 = 61.0F;
  public static final float amount3 = 282.0F;
  public static final float amount4 = 42.0F;
  public final EmoteCatalog emoteCatalog = ClientInitializer.getEmoteCatalog();
  public final AnimatedTextRows animatedTextRows =
      new AnimatedTextRows(this.emoteCatalog.getList().size());
  public final List list = new ArrayList();
  public final AnimationIntensitySupport animationIntensitySupport =
      new AnimationIntensitySupport();
  public final StaggeredTransition staggeredTransition = new StaggeredTransition(28.0F, 360.0F);
  public int count;
  public int count2 = -1;
  public SettingWidget settingWidget;
  public float amount5;
  public float amount6;
  public float amount7;
  public float amount8;
  public float amount9;
  public float amount10;
  public float amount11;
  public float amount12;
  public float amount13;
  public float amount14;
  public String string;

  public EmotesPage() {
    this.emoteCatalog
        .getCollectionData()
        .list()
        .forEach(
            settingEntry -> {
              this.list.add(
                  SettingWidget.getSettingWidget(
                      (bunnythekenzo.client.setting.SettingEntry) (settingEntry)));
            });
  }

  @Override
  public void update() {
    this.staggeredTransition.apply(this.emoteCatalog.getList().size() + this.list.size() + 2);
  }

  @Override
  public void update2() {
    this.staggeredTransition.apply2(this.emoteCatalog.getList().size() + this.list.size() + 2);
  }

  @Override
  public void render(
      FontManager fontManager,
      float amount52,
      float amount62,
      float amount72,
      float amount82,
      float floatValue,
      float floatValue2) {
    this.amount5 = amount52;
    this.amount6 = amount62;
    this.amount7 = amount72;
    this.amount8 = amount82;
    this.string = null;
    ColorPalette instance = ColorPalette.getInstance();
    this.amount9 = amount52 + 226.0F;
    this.amount10 = amount62 + amount82 / 2.0F - 5.0F;
    float value = this.staggeredTransition.computeFloat(0);
    this.count2 =
        this.animatedTextRows.computeInt(
            fontManager,
            this.emoteCatalog.getList(),
            this.amount9,
            this.amount10,
            61.0F,
            146.0F,
            floatValue,
            floatValue2,
            this.count,
            value);
    fontManager.computeFloat2(
        "YOUR EMOTES", this.amount9, amount62 + 24.0F, 9.5F, instance.computeInt18());
    this.amount11 = amount52 + amount72 - 282.0F - 24.0F;
    this.amount12 = amount62 + 19.0F;
    this.render(
        fontManager,
        instance,
        floatValue,
        floatValue2,
        this.staggeredTransition.computeFloat(this.emoteCatalog.getList().size()));
    this.render(fontManager, floatValue, floatValue2);
    this.render2(
        fontManager,
        instance,
        floatValue,
        floatValue2,
        this.staggeredTransition.computeFloat(
            this.emoteCatalog.getList().size() + this.list.size() + 1));
  }

  public void render(
      FontManager fontManager,
      ColorPalette colorPalette,
      float floatValue,
      float floatValue2,
      float floatValue3) {
    EmoteDefinition emoteDefinition = (EmoteDefinition) this.emoteCatalog.getList().get(this.count);
    fontManager.update2();
    fontManager.apply3(floatValue3);
    fontManager.apply((1.0F - floatValue3) * 18.0F, 0.0F);
    fontManager.render(
        this.amount11,
        this.amount12,
        282.0F,
        83.0F,
        9.0F,
        colorPalette.computeInt12(),
        colorPalette.computeInt10(),
        true);
    fontManager.render(
        this.amount11, this.amount12, 282.0F, 83.0F, 9.0F, 1.0F, colorPalette.computeInt15());
    fontManager.render(
        this.amount11, this.amount12 + 14.0F, 3.0F, 55.0F, 1.5F, colorPalette.computeInt());
    fontManager.computeFloat(
        emoteDefinition.string(),
        this.amount11 + 16.0F,
        this.amount12 + 22.0F,
        13.5F,
        colorPalette.computeInt16(),
        251.0F);
    fontManager.computeFloat(
        emoteDefinition.string2() + "  ·  " + emoteDefinition.getString(),
        this.amount11 + 16.0F,
        this.amount12 + 42.0F,
        11.0F,
        colorPalette.computeInt());
    fontManager.computeFloat(
        emoteDefinition.string3(),
        this.amount11 + 16.0F,
        this.amount12 + 63.0F,
        9.5F,
        colorPalette.computeInt18(),
        251.0F);
    fontManager.update3();
    if (floatValue >= this.amount11
        && floatValue <= this.amount11 + 282.0F
        && floatValue2 >= this.amount12
        && floatValue2 <= this.amount12 + 83.0F) {
      this.string = emoteDefinition.string3();
    }
  }

  public void render(FontManager fontManager, float floatValue, float floatValue2) {
    float floatValue3 = this.amount12 + 96.0F;

    for (int index = 0; index < this.list.size(); index++) {
      SettingWidget settingWidget2 = (SettingWidget) this.list.get(index);
      settingWidget2.render(
          fontManager,
          this.amount11,
          floatValue3,
          282.0F,
          floatValue,
          floatValue2,
          this.staggeredTransition.computeFloat(this.emoteCatalog.getList().size() + 1 + index),
          1.0F);
      if (settingWidget2.test4(floatValue, floatValue2)) {
        this.string = settingWidget2.getSettingEntry().getDescription();
      }

      floatValue3 += settingWidget2.computeFloat() + 4.0F;
    }

    this.amount14 = floatValue3 + 6.0F;
    this.amount13 = this.amount11;
  }

  public void render2(
      FontManager fontManager,
      ColorPalette colorPalette,
      float floatValue,
      float floatValue2,
      float floatValue3) {
    boolean flag = ((EmoteDefinition) this.emoteCatalog.getList().get(this.count)).test();
    boolean flag2 =
        floatValue >= this.amount13
            && floatValue <= this.amount13 + 282.0F
            && floatValue2 >= this.amount14
            && floatValue2 <= this.amount14 + 42.0F;
    float value = this.animationIntensitySupport.computeFloat(flag2 && !flag);
    fontManager.update2();
    fontManager.apply3(floatValue3 * (flag ? 0.5F : 1.0F));
    fontManager.render2(
        this.amount13 + 141.0F,
        this.amount14 + 21.0F,
        this.animationIntensitySupport.computeFloat(0.012F));
    fontManager.render(
        this.amount13,
        this.amount14,
        282.0F,
        42.0F,
        9.0F,
        ArgbColors.withOpacity(colorPalette.computeInt(), 0.13F + value * 0.12F));
    fontManager.render(
        this.amount13,
        this.amount14,
        282.0F,
        42.0F,
        9.0F,
        1.0F,
        ArgbColors.withOpacity(colorPalette.computeInt(), 0.45F + value * 0.4F));
    float floatValue4 = this.amount13 + 141.0F;
    float floatValue5 = this.amount14 + 21.0F;
    if (!flag) {
      fontManager.render3(
          floatValue4 - 45.0F,
          floatValue5 - 5.0F,
          floatValue4 - 45.0F,
          floatValue5 + 5.0F,
          floatValue4 - 36.0F,
          floatValue5,
          colorPalette.computeInt());
    }

    fontManager.computeFloat2(
        flag ? "Blank emote" : "Play emote",
        floatValue4 + (flag ? 0 : 7),
        floatValue5,
        13.5F,
        colorPalette.computeInt16());
    fontManager.update3();
    if (flag2) {
      this.string =
          flag
              ? "No animation or music added yet"
              : "Play the selected emote and switch to the game";
    }
  }

  @Override
  public boolean test(float floatValue, float floatValue2, int intValue) {
    if (intValue == 0 && this.count2 >= 0) {
      this.count = this.count2;
      ClientAudio.update5();
      return true;
    }

    for (SettingWidget settingWidget2 :
        (java.lang.Iterable<SettingWidget>) (java.lang.Iterable<?>) (this.list)) {
      if (settingWidget2.test(floatValue, floatValue2, intValue)) {
        this.settingWidget = settingWidget2;
        return true;
      }
    }

    if (intValue != 0
        || !(floatValue >= this.amount13)
        || !(floatValue <= this.amount13 + 282.0F)
        || !(floatValue2 >= this.amount14)
        || !(floatValue2 <= this.amount14 + 42.0F)) {
      return this.test(floatValue, floatValue2);
    }

    if (((EmoteDefinition) this.emoteCatalog.getList().get(this.count)).test()) {
      return true;
    }

    this.animationIntensitySupport.apply(true);
    ClientAudio.update5();
    this.emoteCatalog.apply(this.count);
    return true;
  }

  @Override
  public void apply(float floatValue, float floatValue2) {
    if (this.settingWidget != null) {
      this.settingWidget.apply(floatValue, floatValue2);
    }
  }

  @Override
  public void update5() {
    if (this.settingWidget != null) {
      this.settingWidget.update5();
    }

    this.settingWidget = null;
    this.animationIntensitySupport.apply(false);
  }

  @Override
  public boolean test2(int intValue) {
    for (SettingWidget settingWidget2 :
        (java.lang.Iterable<SettingWidget>) (java.lang.Iterable<?>) (this.list)) {
      if (settingWidget2.test2(intValue)) {
        return true;
      }
    }

    return false;
  }

  @Override
  public String getString() {
    return this.string;
  }

  public boolean test(float floatValue, float floatValue2) {
    return floatValue >= this.amount5
        && floatValue <= this.amount5 + this.amount7
        && floatValue2 >= this.amount6
        && floatValue2 <= this.amount6 + this.amount8;
  }
}
