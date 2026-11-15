package bunnythekenzo.client.module;

public enum ModuleCategory {
  COMBAT("Combat", "combat"),
  MISC("Misc", "misc"),
  RENDER("Render", "render"),
  VISUALS("Visuals", "visuals"),
  DONUTSMP("DonutSMP", "render"),
  CLIENT("Client", "client");

  private final String displayName;
  private final String iconId;
  private static String[] stringArray;
  private static final long timestamp = -1482486195043696634L;

  ModuleCategory(String displayName2, String iconId2) {
    this.displayName = displayName2;
    this.iconId = iconId2;
  }

  public static ModuleCategory getInstance() {
    return CLIENT;
  }

  public String getString() {
    return this == CLIENT ? "CLIENT" : this.name();
  }

  public String getDisplayName() {
    return this.displayName;
  }

  public String getIconId() {
    return this.iconId;
  }

  static {
    setStringArray(null);
  }

  public static void setStringArray(String[] stringArray2) {
    stringArray = stringArray2;
  }

  public static String[] getStringArray() {
    return stringArray;
  }

  public static void ensureInitialized() {}
}
