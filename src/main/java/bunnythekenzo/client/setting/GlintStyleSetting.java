package bunnythekenzo.client.setting;

import bunnythekenzo.client.GlintStyle;

public final class GlintStyleSetting extends ChoiceSetting {
  public GlintStyleSetting() {
    super("Style", "Choose an animated glint in the preview gallery", "Galaxy", GlintStyle.names());
  }

  public void apply(String text) {
    if (text != null && this.getChoices().contains(text)) {
      super.setValue(text);
    }
  }

  public GlintStyle getGlintStyle() {
    return GlintStyle.byName((String) this.getValue());
  }

  @Override
  public void setValue(String text) {
    this.apply((String) text);
  }
}
