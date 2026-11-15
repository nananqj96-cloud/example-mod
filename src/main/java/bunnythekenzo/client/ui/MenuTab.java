package bunnythekenzo.client.ui;

public enum MenuTab {
  MODULES("Modules"),
  EMOTES("Emotes"),
  CONFIGS("Configs"),
  PROFILE("Profile"),
  SETTINGS("Settings"),
  HUD_EDITOR("HUD");

  private final String label;

  MenuTab(String label2) {
    this.label = label2;
  }

  public String getLabel() {
    return this.label;
  }
}
