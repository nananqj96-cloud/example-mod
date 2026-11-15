package bunnythekenzo.client.setting;

public sealed interface SettingEntry permits SettingView {
  String getName();

  String getDescription();

  default boolean isBound() {
    return false;
  }

  default boolean isVisible() {
    return true;
  }
}
