package bunnythekenzo.client.ui;

import java.util.Objects;
import bunnythekenzo.client.AnimationIntensity;
import bunnythekenzo.client.AnimationIntensitySupport;
import bunnythekenzo.client.ArgbColors;
import bunnythekenzo.client.BlockGalleryModel;
import bunnythekenzo.client.ClientAudio;
import bunnythekenzo.client.ClientData3;
import bunnythekenzo.client.ColorPalette;
import bunnythekenzo.client.Easing;
import bunnythekenzo.client.EntityGalleryModel;
import bunnythekenzo.client.GuiDialogManager;
import bunnythekenzo.client.KeyNames;
import bunnythekenzo.client.TweenValue;
import bunnythekenzo.client.render.FontManager;
import bunnythekenzo.client.setting.BlockListSettingView;
import bunnythekenzo.client.setting.ChoiceSettingView;
import bunnythekenzo.client.setting.ColorSettingView;
import bunnythekenzo.client.setting.EntityTypeSettingView;
import bunnythekenzo.client.setting.GlintStyleSettingView;
import bunnythekenzo.client.setting.ItemReplacementSettingView;
import bunnythekenzo.client.setting.ItemSettingView;
import bunnythekenzo.client.setting.KeybindSettingView;
import bunnythekenzo.client.setting.NumberSettingView;
import bunnythekenzo.client.setting.SettingEntry;
import bunnythekenzo.client.setting.StringSettingView;
import bunnythekenzo.client.setting.ToggleSettingView;

public final class MenuPanelSupport5 {
  public static final float amount = 13.0F;
  public static final float amount2 = 12.0F;
  public final SettingEntry settingEntry;
  public final String string;
  public final AnimationIntensitySupport animationIntensitySupport =
      new AnimationIntensitySupport(AnimationIntensity.SUBTLE);
  public float amount3;
  public boolean enabled;
  public final TweenValue tweenValue;
  public final TweenValue tweenValue2 = new TweenValue(180.0F, 0.0F, Easing.OUT_QUART);

  public MenuPanelSupport5(SettingEntry settingEntry3, String string2) {
    this.settingEntry = settingEntry3;
    this.string = string2;
    float floatValue =
        settingEntry3 instanceof ToggleSettingView toggleSettingView2
            ? (toggleSettingView2.getValue() ? 1 : 0)
            : (settingEntry3 instanceof NumberSettingView numberSettingView2
                ? numberSettingView2.getFraction()
                : 0.0F);
    this.tweenValue = new TweenValue(180.0F, floatValue, Easing.OUT_QUART);
  }

  public float computeFloat() {
    return this.settingEntry instanceof ToggleSettingView
        ? 44.0F
        : (this.settingEntry instanceof NumberSettingView ? 74.0F : 68.0F);
  }

  public void render(FontManager fontManager, float f, boolean bl, boolean bl2, boolean bl3) {
    ColorPalette colorPalette = ColorPalette.getInstance();
    float f2 = this.animationIntensitySupport.computeFloat(bl);
    fontManager.render(
        6.0f,
        this.amount3,
        f - 12.0f,
        this.computeFloat(),
        6.0f,
        ArgbColors.lerp(colorPalette.computeInt10(), colorPalette.computeInt11(), f2));
    this.tweenValue2.animateTo(bl2 ? 1.0f : 0.0f);
    fontManager.render(
        6.0f,
        this.amount3,
        f - 12.0f,
        this.computeFloat(),
        6.0f,
        1.0f,
        ArgbColors.lerp(
            colorPalette.computeInt14(),
            colorPalette.computeInt15(),
            Math.max(f2 * 0.5f, this.tweenValue2.getValue())));
    float f3 = f - (float) (this.settingEntry instanceof ToggleSettingView ? 72 : 24);
    this.render(fontManager, f3, colorPalette.computeInt16());
    SettingEntry settingEntry = this.settingEntry;
    Objects.requireNonNull(settingEntry);
    SettingEntry settingEntry2 = settingEntry;
    switch (bunnythekenzo.client.runtime.TypeDispatch.select(
        new Class<?>[] {ToggleSettingView.class, NumberSettingView.class, ColorSettingView.class},
        (Object) settingEntry2,
        0)) {
      case 0:
        {
          ToggleSettingView toggleSettingView = (ToggleSettingView) settingEntry2;
          this.tweenValue.animateTo(toggleSettingView.getValue() ? 1.0f : 0.0f);
          MenuPanelSupport.render(
              fontManager,
              f - 48.0f,
              this.amount3 + 22.0f,
              this.tweenValue.getValue(),
              colorPalette.computeInt());
          break;
        }
      case 1:
        {
          NumberSettingView numberSettingView = (NumberSettingView) settingEntry2;
          fontManager.computeFloat3(
              numberSettingView.getFormattedValue(),
              f - 12.0f,
              this.amount3 + 47.0f,
              12.0f,
              colorPalette.computeInt());
          if (bl3) {
            this.tweenValue.snapTo(numberSettingView.getFraction());
          } else {
            this.tweenValue.animateTo(numberSettingView.getFraction());
          }
          float f4 = 16.0f + (f - 32.0f) * Math.clamp(this.tweenValue.getValue(), 0.0f, 1.0f);
          fontManager.render(16.0f, this.amount3 + 61.5f, f - 32.0f, 5.0f, 2.5f, 0x50FFFFFF);
          fontManager.render(
              16.0f,
              this.amount3 + 61.5f,
              Math.max(2.5f, f4 - 16.0f),
              5.0f,
              2.5f,
              colorPalette.computeInt());
          fontManager.render2(
              f4,
              this.amount3 + 64.0f,
              9.0f,
              colorPalette.computeInt(0.2f * Math.max(f2, this.tweenValue2.getValue())));
          fontManager.render2(
              f4, this.amount3 + 64.0f, 5.5f + 0.8f * f2, colorPalette.computeInt16());
          break;
        }
      case 2:
        {
          ColorSettingView toggleSettingViewSupport2 = (ColorSettingView) settingEntry2;
          fontManager.computeFloat(
              "#" + ArgbColors.toHex(toggleSettingViewSupport2.computeInt()),
              12.0f,
              this.amount3 + 51.0f,
              12.0f,
              colorPalette.computeInt17());
          fontManager.render(
              f - 48.0f,
              this.amount3 + 39.0f,
              36.0f,
              24.0f,
              5.0f,
              toggleSettingViewSupport2.computeInt());
          fontManager.render(
              f - 48.0f,
              this.amount3 + 39.0f,
              36.0f,
              24.0f,
              5.0f,
              1.0f,
              colorPalette.computeInt17());
          break;
        }
      default:
        {
          fontManager.render(
              12.0f, this.amount3 + 38.0f, f - 24.0f, 26.0f, 5.0f, colorPalette.computeInt6());
          fontManager.render(
              12.0f,
              this.amount3 + 38.0f,
              f - 24.0f,
              26.0f,
              5.0f,
              1.0f,
              this.enabled ? colorPalette.computeInt() : colorPalette.computeInt14());
          Object object = this.getString();
          if (this.settingEntry instanceof StringSettingView && this.enabled) {
            object = (String) object + "|";
          }
          fontManager.computeFloat(
              (String) object,
              19.0f,
              this.amount3 + 51.0f,
              12.0f,
              this.enabled ? colorPalette.computeInt() : colorPalette.computeInt16(),
              f - (float) (this.settingEntry instanceof ChoiceSettingView ? 53 : 38));
          if (!(this.settingEntry instanceof ChoiceSettingView)) break;
          fontManager.render(
              f - 23.0f, this.amount3 + 51.0f, 6.0f, 1.4f, colorPalette.computeInt17(), true);
        }
    }
  }

  public void render(FontManager fontManager2, float floatValue, int intValue) {
    String string2 = this.settingEntry.getName();
    if (fontManager2.computeFloat(string2, 13.0F) <= floatValue) {
      fontManager2.computeFloat(string2, 12.0F, this.amount3 + 22.0F, 13.0F, intValue);
    } else {
      int intValue2 = string2.lastIndexOf(32);

      while (intValue2 > 0
          && fontManager2.computeFloat(string2.substring(0, intValue2), 13.0F) > floatValue) {
        intValue2 = string2.lastIndexOf(32, intValue2 - 1);
      }

      if (intValue2 <= 0) {
        fontManager2.computeFloat(
            string2, 12.0F, this.amount3 + 22.0F, 13.0F, intValue, floatValue);
      } else {
        fontManager2.computeFloat(
            string2.substring(0, intValue2), 12.0F, this.amount3 + 14.0F, 13.0F, intValue);
        fontManager2.computeFloat(
            string2.substring(intValue2 + 1),
            12.0F,
            this.amount3 + 29.0F,
            13.0F,
            intValue,
            floatValue);
      }
    }
  }

  public String getString() {
    SettingEntry settingEntry = this.settingEntry;
    Objects.requireNonNull(settingEntry);
    SettingEntry settingEntry2 = settingEntry;
    return switch (bunnythekenzo.client.runtime.TypeDispatch.select(
        new Class<?>[] {
          StringSettingView.class,
          KeybindSettingView.class,
          ChoiceSettingView.class,
          BlockListSettingView.class,
          EntityTypeSettingView.class,
          ItemSettingView.class,
          GlintStyleSettingView.class,
          ItemReplacementSettingView.class
        },
        (Object) settingEntry2,
        0)) {
      case 0 -> {
        StringSettingView toggleSettingViewSupport4 = (StringSettingView) settingEntry2;
        if (toggleSettingViewSupport4.getValue().isEmpty()) {
          yield toggleSettingViewSupport4.getPlaceholder();
        }
        yield toggleSettingViewSupport4.getValue();
      }
      case 1 -> {
        KeybindSettingView toggleSettingViewSupport = (KeybindSettingView) settingEntry2;
        if (this.enabled) {
          yield "Press key\u2026";
        }
        yield KeyNames.getString(toggleSettingViewSupport.computeInt());
      }
      case 2 -> {
        ChoiceSettingView choiceSettingView2 = (ChoiceSettingView) settingEntry2;
        yield choiceSettingView2.getSelectedValue();
      }
      case 3 -> {
        BlockListSettingView toggleSettingViewSupport82 = (BlockListSettingView) settingEntry2;
        yield toggleSettingViewSupport82.getString3() + " \u00b7 Choose";
      }
      case 4 -> {
        EntityTypeSettingView toggleSettingViewSupport32 = (EntityTypeSettingView) settingEntry2;
        yield toggleSettingViewSupport32.getString3() + " \u00b7 Choose";
      }
      case 5 -> {
        ItemSettingView toggleSettingViewSupport62 = (ItemSettingView) settingEntry2;
        yield toggleSettingViewSupport62.getString3();
      }
      case 6 -> {
        GlintStyleSettingView toggleSettingViewSupport72 = (GlintStyleSettingView) settingEntry2;
        yield toggleSettingViewSupport72.getString3();
      }
      case 7 -> {
        ItemReplacementSettingView toggleSettingViewSupport52 =
            (ItemReplacementSettingView) settingEntry2;
        yield toggleSettingViewSupport52.getString3() + " \u00b7 Manage";
      }
      default -> "";
    };
  }

  public void apply(int n, MenuPanelSupport4 menuPanelSupport4, ClientData3 clientData3) {
    SettingEntry settingEntry = this.settingEntry;
    Objects.requireNonNull(settingEntry);
    SettingEntry settingEntry2 = settingEntry;
    switch (bunnythekenzo.client.runtime.TypeDispatch.select(
        new Class<?>[] {
          ToggleSettingView.class,
          ChoiceSettingView.class,
          ColorSettingView.class,
          StringSettingView.class,
          KeybindSettingView.class,
          BlockListSettingView.class,
          EntityTypeSettingView.class,
          ItemSettingView.class,
          GlintStyleSettingView.class,
          ItemReplacementSettingView.class,
          NumberSettingView.class
        },
        (Object) settingEntry2,
        0)) {
      default:
        {
          throw new MatchException(null, null);
        }
      case 0:
        {
          ToggleSettingView toggleSettingView = (ToggleSettingView) settingEntry2;
          ClientAudio.apply(toggleSettingView.toggle());
          return;
        }
      case 1:
        {
          ChoiceSettingView choiceSettingView = (ChoiceSettingView) settingEntry2;
          choiceSettingView.selectIndex(
              Math.floorMod(
                  choiceSettingView.getSelectedIndex() + n, choiceSettingView.getChoices().size()));
          break;
        }
      case 2:
        {
          ColorSettingView toggleSettingViewSupport2 = (ColorSettingView) settingEntry2;
          menuPanelSupport4.open(toggleSettingViewSupport2, clientData3);
          return;
        }
      case 3:
        {
          this.enabled = true;
          break;
        }
      case 4:
        {
          this.enabled = !this.enabled;
          break;
        }
      case 5:
        {
          BlockListSettingView toggleSettingViewSupport8 = (BlockListSettingView) settingEntry2;
          GuiDialogManager.open(
              new BlockGalleryModel(
                  toggleSettingViewSupport8.getBlockListSetting(),
                  toggleSettingViewSupport8.getIntSupplier(),
                  toggleSettingViewSupport8.getName()));
          break;
        }
      case 6:
        {
          EntityTypeSettingView toggleSettingViewSupport3 = (EntityTypeSettingView) settingEntry2;
          GuiDialogManager.open(
              new EntityGalleryModel(toggleSettingViewSupport3.getEntityTypeSetting()));
          break;
        }
      case 7:
        {
          ItemSettingView toggleSettingViewSupport6 = (ItemSettingView) settingEntry2;
          GuiDialogManager.open(new SelectionGallery(toggleSettingViewSupport6.getItemSetting()));
          break;
        }
      case 8:
        {
          GlintStyleSettingView toggleSettingViewSupport7 = (GlintStyleSettingView) settingEntry2;
          GuiDialogManager.open(new GlintGallery(toggleSettingViewSupport7.getGlintStyleSetting()));
          break;
        }
      case 9:
        {
          ItemReplacementSettingView toggleSettingViewSupport5 =
              (ItemReplacementSettingView) settingEntry2;
          GuiDialogManager.open(toggleSettingViewSupport5.getItemReplacementSetting());
          break;
        }
      case 10:
        {
          return;
        }
    }
    ClientAudio.update5();
  }

  public void apply(float floatValue, float floatValue2) {
    if (this.settingEntry instanceof NumberSettingView numberSettingView2) {
      float value = numberSettingView2.getValue();
      numberSettingView2.setFraction((floatValue - 16.0F) / (floatValue2 - 32.0F));
      if (value != numberSettingView2.getValue()) {
        ClientAudio.apply2(numberSettingView2.getFraction());
      }
    }
  }

  public boolean test(int intValue, boolean flag) {
    if (this.settingEntry instanceof NumberSettingView numberSettingView2) {
      numberSettingView2.setValue(
          numberSettingView2.getValue()
              + intValue * numberSettingView2.getStep() * (flag ? 10 : 1));
      ClientAudio.apply2(numberSettingView2.getFraction());
      return true;
    } else if (this.settingEntry instanceof ChoiceSettingView choiceSettingView2) {
      choiceSettingView2.selectIndex(
          Math.floorMod(
              choiceSettingView2.getSelectedIndex() + intValue,
              choiceSettingView2.getChoices().size()));
      ClientAudio.update5();
      return true;
    } else {
      return false;
    }
  }

  public boolean test(int intValue) {
    if (!this.enabled) {
      return false;
    }

    if (this.settingEntry instanceof KeybindSettingView toggleSettingViewSupport) {
      toggleSettingViewSupport.apply(
          intValue != 256 && intValue != 261 && intValue != 259 ? intValue : -1);
      this.enabled = false;
      ClientAudio.update5();
    } else if (this.settingEntry instanceof StringSettingView toggleSettingViewSupport4) {
      switch (intValue) {
        case 256:
        case 257:
        case 335:
          this.enabled = false;
          break;
        case 259:
          String string3 = toggleSettingViewSupport4.getValue();
          if (!string3.isEmpty()) {
            toggleSettingViewSupport4.setValue(
                string3.substring(0, string3.offsetByCodePoints(string3.length(), -1)));
          }
          break;
        case 261:
          toggleSettingViewSupport4.setValue("");
      }
    }

    return true;
  }

  public boolean test2(int intValue) {
    if (this.enabled
        && intValue != 0
        && this.settingEntry instanceof KeybindSettingView toggleSettingViewSupport) {
      toggleSettingViewSupport.apply(intValue);
      this.enabled = false;
      ClientAudio.update5();
      return true;
    } else {
      return false;
    }
  }

  public boolean test3(int intValue) {
    if (this.enabled && this.settingEntry instanceof StringSettingView toggleSettingViewSupport4) {
      if (Character.isValidCodePoint(intValue) && !Character.isISOControl(intValue)) {
        toggleSettingViewSupport4.setValue(
            toggleSettingViewSupport4.getValue() + new String(Character.toChars(intValue)));
      }

      return true;
    } else {
      return false;
    }
  }

  public void update() {
    this.enabled = false;
    this.animationIntensitySupport.update();
  }
}
