package bunnythekenzo.client.ui;

import java.util.List;
import java.util.function.Consumer;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import bunnythekenzo.client.ArgbColors;
import bunnythekenzo.client.ClientAudio;
import bunnythekenzo.client.ColorPalette;
import bunnythekenzo.client.GuiDialogManager;
import bunnythekenzo.client.GuiModal;
import bunnythekenzo.client.NanoVgImageCache;
import bunnythekenzo.client.SmoothScrollState;
import bunnythekenzo.client.render.FontManager;
import bunnythekenzo.client.setting.ItemReplacementRule;
import bunnythekenzo.client.setting.ItemReplacementSetting;

public final class ItemReplacementDialog implements GuiModal {
  public static final float amount = 620.0F;
  public static final float amount2 = 424.0F;
  public static final float amount3 = 54.0F;
  public static final float amount4 = 112.0F;
  public static final float amount5 = 242.0F;
  public final ItemReplacementSetting itemReplacementSetting;
  public final SmoothScrollState smoothScrollState = new SmoothScrollState();
  public boolean enabled;
  public float amount6;
  public float amount7;
  public float amount8;

  public ItemReplacementDialog(ItemReplacementSetting itemReplacementSetting2) {
    this.itemReplacementSetting = itemReplacementSetting2;
    ClientAudio.update();
  }

  @Override
  public void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    this.amount6 = Math.round((floatValue3 - 620.0F) / 2.0F);
    this.amount7 = Math.round((floatValue4 - 424.0F) / 2.0F - floatValue4 * 0.015F);
    ColorPalette instance = ColorPalette.getInstance();
    fontManager.render(
        0.0F, 0.0F, floatValue3, floatValue4, 0.0F, ArgbColors.withOpacity(-16317174, 0.72F));
    fontManager.render(
        this.amount6,
        this.amount7,
        620.0F,
        424.0F,
        16.0F,
        34.0F,
        12.0F,
        ArgbColors.withOpacity(-16777216, 0.6F));
    fontManager.render(
        this.amount6,
        this.amount7,
        620.0F,
        424.0F,
        16.0F,
        instance.computeInt3(),
        instance.computeInt4(),
        true);
    fontManager.render(
        this.amount6, this.amount7, 620.0F, 424.0F, 16.0F, 1.0F, instance.computeInt14());
    fontManager.computeFloat(
        "Fake Loot Drop",
        this.amount6 + 20.0F,
        this.amount7 + 28.0F,
        18.0F,
        instance.computeInt16());
    fontManager.computeFloat(
        this.itemReplacementSetting.computeInt() + " active",
        this.amount6 + 175.0F,
        this.amount7 + 28.0F,
        11.0F,
        instance.computeInt());
    render(
        fontManager,
        "Done",
        this.amount6 + 620.0F - 76.0F,
        this.amount7 + 14.0F,
        56.0F,
        28.0F,
        floatValue,
        floatValue2);
    fontManager.computeFloat(
        "Choose a real item and the fake item to show in its place.",
        this.amount6 + 20.0F,
        this.amount7 + 65.0F,
        13.5F,
        instance.computeInt17());
    fontManager.computeFloat(
        "REAL ITEM", this.amount6 + 60.0F, this.amount7 + 96.0F, 9.5F, instance.computeInt18());
    fontManager.computeFloat(
        "FAKE ITEM", this.amount6 + 312.0F, this.amount7 + 96.0F, 9.5F, instance.computeInt18());
    this.smoothScrollState.apply(
        ((List) this.itemReplacementSetting.getValue()).size() * 54.0F, 242.0F);
    this.amount8 = this.smoothScrollState.computeFloat();
    fontManager.update2();
    fontManager.render(this.amount6 + 16.0F, this.amount7 + 112.0F, 588.0F, 242.0F);
    if (((List) this.itemReplacementSetting.getValue()).isEmpty()) {
      fontManager.computeFloat2(
          "No replacements yet",
          this.amount6 + 310.0F,
          this.amount7 + 205.0F,
          18.0F,
          instance.computeInt17());
      fontManager.computeFloat2(
          "Add a replacement to choose your first pair of items.",
          this.amount6 + 310.0F,
          this.amount7 + 232.0F,
          11.0F,
          instance.computeInt18());
    }

    for (int index = 0; index < ((List) this.itemReplacementSetting.getValue()).size(); index++) {
      ItemReplacementRule choiceSettingData =
          (ItemReplacementRule) ((List) this.itemReplacementSetting.getValue()).get(index);
      float floatValue5 = this.amount7 + 112.0F + index * 54.0F - this.amount8;
      if (!(floatValue5 + 54.0F <= this.amount7 + 112.0F)
          && !(floatValue5 >= this.amount7 + 112.0F + 242.0F)) {
        fontManager.render(
            this.amount6 + 18.0F, floatValue5 + 2.0F, 584.0F, 48.0F, 8.0F, instance.computeInt10());
        fontManager.render(
            this.amount6 + 29.0F,
            floatValue5 + 17.0F,
            16.0F,
            16.0F,
            4.0F,
            choiceSettingData.enabled() ? instance.computeInt() : instance.computeInt4());
        if (choiceSettingData.enabled()) {
          fontManager.render4(
              this.amount6 + 29.0F, floatValue5 + 17.0F, 16.0F, 1.6F, instance.computeInt4());
        } else {
          fontManager.render(
              this.amount6 + 29.0F,
              floatValue5 + 17.0F,
              16.0F,
              16.0F,
              4.0F,
              1.0F,
              instance.computeInt14());
        }

        this.render(
            fontManager,
            choiceSettingData.item(),
            this.amount6 + 56.0F,
            floatValue5 + 8.0F,
            218.0F,
            choiceSettingData.enabled(),
            floatValue,
            floatValue2);
        fontManager.render2(
            this.amount6 + 283.0F,
            floatValue5 + 25.0F,
            this.amount6 + 297.0F,
            floatValue5 + 25.0F,
            1.4F,
            instance.computeInt18());
        fontManager.render2(
            this.amount6 + 292.0F,
            floatValue5 + 20.0F,
            this.amount6 + 297.0F,
            floatValue5 + 25.0F,
            1.4F,
            instance.computeInt18());
        fontManager.render2(
            this.amount6 + 292.0F,
            floatValue5 + 30.0F,
            this.amount6 + 297.0F,
            floatValue5 + 25.0F,
            1.4F,
            instance.computeInt18());
        this.render(
            fontManager,
            choiceSettingData.item2(),
            this.amount6 + 308.0F,
            floatValue5 + 8.0F,
            242.0F,
            choiceSettingData.enabled(),
            floatValue,
            floatValue2);
        render(
            fontManager,
            "x",
            this.amount6 + 560.0F,
            floatValue5 + 10.0F,
            28.0F,
            30.0F,
            floatValue,
            floatValue2);
      }
    }

    fontManager.update3();
    this.smoothScrollState.render(
        fontManager, this.amount6 + 620.0F - 16.0F, this.amount7 + 112.0F, 242.0F);
    render(
        fontManager,
        "+ Add replacement",
        this.amount6 + 20.0F,
        this.amount7 + 370.0F,
        156.0F,
        32.0F,
        floatValue,
        floatValue2);
    fontManager.computeFloat3(
        "Click an item to open the picker",
        this.amount6 + 620.0F - 20.0F,
        this.amount7 + 386.0F,
        11.0F,
        instance.computeInt18());
  }

  public void render(
      FontManager fontManager,
      Item item,
      float floatValue,
      float floatValue2,
      float floatValue3,
      boolean flag,
      float floatValue4,
      float floatValue5) {
    ColorPalette instance = ColorPalette.getInstance();
    boolean flag2 = test(floatValue4, floatValue5, floatValue, floatValue2, floatValue3, 34.0F);
    fontManager.render(
        floatValue,
        floatValue2,
        floatValue3,
        34.0F,
        6.0F,
        flag2 ? instance.computeInt11() : instance.computeInt4());
    fontManager.render(
        floatValue,
        floatValue2,
        floatValue3,
        34.0F,
        6.0F,
        1.0F,
        flag2 ? instance.computeInt15() : instance.computeInt14());
    int int3 = NanoVgImageCache.computeInt3(Registries.ITEM.getId(item));
    if (int3 != 0) {
      fontManager.render(
          int3, floatValue + 7.0F, floatValue2 + 6.0F, 22.0F, 22.0F, flag ? -1 : 1728053247);
    }

    fontManager.computeFloat(
        item.getName().getString(),
        floatValue + 37.0F,
        floatValue2 + 17.0F,
        11.0F,
        flag ? instance.computeInt16() : instance.computeInt18(),
        floatValue3 - 45.0F);
  }

  public static void render(
      FontManager fontManager,
      String text,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6) {
    ColorPalette instance = ColorPalette.getInstance();
    boolean flag =
        test(floatValue5, floatValue6, floatValue, floatValue2, floatValue3, floatValue4);
    fontManager.render(
        floatValue,
        floatValue2,
        floatValue3,
        floatValue4,
        7.0F,
        flag ? instance.computeInt12() : instance.computeInt10());
    fontManager.render(
        floatValue,
        floatValue2,
        floatValue3,
        floatValue4,
        7.0F,
        1.0F,
        flag ? instance.computeInt15() : instance.computeInt14());
    fontManager.computeFloat2(
        text,
        floatValue + floatValue3 / 2.0F,
        floatValue2 + floatValue4 / 2.0F,
        11.0F,
        flag ? instance.computeInt() : instance.computeInt17());
  }

  public void addReplacement() {
    this.pick(
        "Choose real item",
        null,
        value -> {
          this.pick(
              "Choose fake item",
              null,
              value2 -> {
                this.itemReplacementSetting.put(
                    (net.minecraft.item.Item) (value), (net.minecraft.item.Item) (value2));
              });
        });
  }

  public void pick(String text, Item item, Consumer consumer) {
    Item[] itemArray = new Item[] {null};
    GuiDialogManager.open(
        new SelectionGallery(
            text,
            () -> {
              return item;
            },
            value -> {
              itemArray[0] = (net.minecraft.item.Item) (value);
              GuiDialogManager.getSelectionDialog().closeAnimated();
            }),
        () -> {
          if (itemArray[0] != null) {
            consumer.accept(itemArray[0]);
          }
        });
  }

  @Override
  public boolean mouseClicked(float floatValue, float floatValue2, int intValue) {
    if (intValue != 0) {
      return true;
    }

    if (!test(floatValue, floatValue2, this.amount6, this.amount7, 620.0F, 424.0F)
        || test(
            floatValue,
            floatValue2,
            this.amount6 + 620.0F - 76.0F,
            this.amount7 + 14.0F,
            56.0F,
            28.0F)) {
      this.closeAnimated();
    } else if (test(
        floatValue, floatValue2, this.amount6 + 20.0F, this.amount7 + 370.0F, 156.0F, 32.0F)) {
      this.addReplacement();
    } else if (test(
        floatValue, floatValue2, this.amount6 + 18.0F, this.amount7 + 112.0F, 584.0F, 242.0F)) {
      int intValue2 = (int) ((floatValue2 - this.amount7 - 112.0F + this.amount8) / 54.0F);
      if (intValue2 < 0 || intValue2 >= ((List) this.itemReplacementSetting.getValue()).size()) {
        return true;
      }

      ItemReplacementRule choiceSettingData =
          (ItemReplacementRule) ((List) this.itemReplacementSetting.getValue()).get(intValue2);
      float floatValue3 = this.amount7 + 112.0F + intValue2 * 54.0F - this.amount8;
      if (test(floatValue, floatValue2, this.amount6 + 25.0F, floatValue3 + 10.0F, 24.0F, 30.0F)) {
        this.itemReplacementSetting.update(
            choiceSettingData,
            new ItemReplacementRule(
                choiceSettingData.item(), choiceSettingData.item2(), !choiceSettingData.enabled()));
      } else if (test(
          floatValue, floatValue2, this.amount6 + 56.0F, floatValue3 + 8.0F, 218.0F, 34.0F)) {
        this.pick(
            "Choose real item",
            choiceSettingData.item(),
            value -> {
              this.itemReplacementSetting.update(
                  choiceSettingData,
                  new ItemReplacementRule(
                      (net.minecraft.item.Item) (value),
                      choiceSettingData.item2(),
                      choiceSettingData.enabled()));
            });
      } else if (test(
          floatValue, floatValue2, this.amount6 + 308.0F, floatValue3 + 8.0F, 242.0F, 34.0F)) {
        this.pick(
            "Choose fake item",
            choiceSettingData.item2(),
            value2 -> {
              this.itemReplacementSetting.update(
                  choiceSettingData,
                  new ItemReplacementRule(
                      choiceSettingData.item(),
                      (net.minecraft.item.Item) (value2),
                      choiceSettingData.enabled()));
            });
      } else if (test(
          floatValue, floatValue2, this.amount6 + 560.0F, floatValue3 + 10.0F, 28.0F, 30.0F)) {
        this.itemReplacementSetting.apply(choiceSettingData);
      }
    }

    return true;
  }

  public void closeAnimated() {
    if (!this.enabled) {
      this.enabled = true;
      ClientAudio.update2();
    }
  }

  @Override
  public boolean isCloseComplete() {
    return this.enabled;
  }

  @Override
  public boolean acceptsInput() {
    return true;
  }

  @Override
  public boolean mouseScrolled(float floatValue, float floatValue2, double doubleValue) {
    this.smoothScrollState.test(doubleValue);
    return true;
  }

  @Override
  public boolean keyPressed(int intValue) {
    if (intValue == 256) {
      this.closeAnimated();
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
}
