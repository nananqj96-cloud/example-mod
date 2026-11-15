package bunnythekenzo.client;

public enum GambleRiggerState {
  IDLE,
  EXTRACTING,
  EXTRACTED,
  RESTORING;

  static {
    String[] textArray = new String[] {"IDLE", "EXTRACTING", "RESTORING", "EXTRACTED"};
  }
}
