package bunnythekenzo.client;

public enum AnchorMacroState {
  IDLE,
  SELECT_GLOWSTONE,
  CHARGE,
  SELECT_TOTEM,
  DETONATE,
  WAIT_REMOVAL,
  RESTORE;

  static {
    String[] textArray =
        new String[] {
          "DETONATE",
          "WAIT_REMOVAL",
          "IDLE",
          "SELECT_GLOWSTONE",
          "SELECT_TOTEM",
          "CHARGE",
          "RESTORE"
        };
  }
}
