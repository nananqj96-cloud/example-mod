package bunnythekenzo.client.setting;

public final class GlintStyleSettingView extends SettingView {
  public final GlintStyleSetting glintStyleSetting;

  public GlintStyleSettingView(GlintStyleSetting glintStyleSetting2) {
    super(glintStyleSetting2.getName(), glintStyleSetting2.getDescription());
    this.glintStyleSetting = glintStyleSetting2;
  }

  @Override
  public boolean isBound() {
    return true;
  }

  public GlintStyleSetting getGlintStyleSetting() {
    return this.glintStyleSetting;
  }

  public String getString3() {
    return (String) this.glintStyleSetting.getValue();
  }
}
