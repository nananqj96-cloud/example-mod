package bunnythekenzo.client.setting;

import java.util.Objects;

public final class SettingViewFactory {
  public static SettingEntry getSettingEntry(Setting setting4) {
    SettingView settingView = build(setting4);
    return settingView == null ? null : settingView.visibleWhen(setting4::isVisible);
  }

  public static SettingView build(Setting setting) {
    Setting setting2 = setting;
    Objects.requireNonNull(setting2);
    Setting setting3 = setting2;
    return switch (bunnythekenzo.client.runtime.TypeDispatch.select(
        new Class<?>[] {
          BooleanSetting.class,
          NumberSetting.class,
          GlintStyleSetting.class,
          ChoiceSetting.class,
          ColorSetting.class,
          KeybindSetting.class,
          StringSetting.class,
          BlockListSetting.class,
          EntityTypeSetting.class,
          ItemSetting.class,
          ItemReplacementSetting.class
        },
        (Object) setting3,
        0)) {
      case 0 -> {
        BooleanSetting booleanSetting = (BooleanSetting) setting3;
        yield new ToggleSettingView(
            booleanSetting.getName(),
            booleanSetting.getDescription(),
            (Boolean) booleanSetting.getValue(),
            booleanSetting::getValue,
            value -> booleanSetting.setValue((Boolean) value));
      }
      case 1 -> {
        NumberSetting numberSetting = (NumberSetting) setting3;
        yield new NumberSettingView(
            numberSetting.getName(),
            numberSetting.getDescription(),
            numberSetting.floatValue(),
            (float) numberSetting.getMinimum(),
            (float) numberSetting.getMaximum(),
            (float) numberSetting.getStep(),
            numberSetting.getUnit(),
            numberSetting::floatValue,
            d -> numberSetting.setNumber((Double) d),
            d -> numberSetting.getFormattedValue());
      }
      case 2 -> {
        GlintStyleSetting glintStyleSetting = (GlintStyleSetting) setting3;
        yield new GlintStyleSettingView(glintStyleSetting);
      }
      case 3 -> {
        ChoiceSetting choiceSetting = (ChoiceSetting) setting3;
        yield new ChoiceSettingView(
            choiceSetting.getName(),
            choiceSetting.getDescription(),
            choiceSetting.getChoices().indexOf(choiceSetting.getValue()),
            choiceSetting.getChoices(),
            choiceSetting::getValue,
            value -> choiceSetting.setValue((String) value));
      }
      case 4 -> {
        ColorSetting colorSetting = (ColorSetting) setting3;
        yield new ColorSettingView(
                colorSetting.getName(),
                colorSetting.getDescription(),
                colorSetting.getInteger(),
                colorSetting::getInteger,
                colorSetting::apply)
            .getColorSettingView(colorSetting::resetToDefault);
      }
      case 5 -> {
        KeybindSetting keybindSetting = (KeybindSetting) setting3;
        yield new KeybindSettingView(
            keybindSetting.getName(),
            keybindSetting.getDescription(),
            (Integer) keybindSetting.getValue(),
            keybindSetting::getValue,
            keybindSetting::setValue);
      }
      case 6 -> {
        StringSetting stringSetting = (StringSetting) setting3;
        yield new StringSettingView(
            stringSetting.getName(),
            stringSetting.getDescription(),
            (String) stringSetting.getValue(),
            stringSetting.getPlaceholder(),
            stringSetting.getMaxLength(),
            stringSetting::getValue,
            value -> stringSetting.setValue((String) value));
      }
      case 7 -> {
        BlockListSetting blockListSetting = (BlockListSetting) setting3;
        yield new BlockListSettingView(blockListSetting, () -> -16711736);
      }
      case 8 -> {
        EntityTypeSetting entityTypeSetting = (EntityTypeSetting) setting3;
        yield new EntityTypeSettingView(entityTypeSetting);
      }
      case 9 -> {
        ItemSetting itemSetting = (ItemSetting) setting3;
        yield new ItemSettingView(itemSetting);
      }
      case 10 -> {
        ItemReplacementSetting itemReplacementSetting = (ItemReplacementSetting) setting3;
        yield new ItemReplacementSettingView(itemReplacementSetting);
      }
      default -> null;
    };
  }
}
