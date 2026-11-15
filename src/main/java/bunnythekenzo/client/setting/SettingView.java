package bunnythekenzo.client.setting;

import java.util.function.BooleanSupplier;

public abstract sealed class SettingView implements SettingEntry
    permits ItemReplacementSettingView,
        StringSettingView,
        ToggleSettingView,
        NumberSettingView,
        ColorSettingView,
        ChoiceSettingView,
        KeybindSettingView,
        BlockListSettingView,
        EntityTypeSettingView,
        GlintStyleSettingView,
        ItemSettingView {
  public final String name;
  public final String description;
  public BooleanSupplier visibilityCondition;

  public SettingView(String string3, String string22) {
    this.name = string3;
    this.description = string22;
  }

  @Override
  public final String getName() {
    return this.name;
  }

  @Override
  public final String getDescription() {
    return this.description;
  }

  public final SettingView visibleWhen(BooleanSupplier booleanSupplier2) {
    this.visibilityCondition = booleanSupplier2;
    return this;
  }

  @Override
  public final boolean isVisible() {
    return this.visibilityCondition == null || this.visibilityCondition.getAsBoolean();
  }
}
