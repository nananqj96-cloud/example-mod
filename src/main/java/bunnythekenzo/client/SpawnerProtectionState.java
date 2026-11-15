package bunnythekenzo.client;

public enum SpawnerProtectionState {
  WAITING_FOR_STRANGER,
  WORKING,
  GOING_TO_CHEST,
  OPENING_CHEST,
  DEPOSITING_ITEMS,
  FINAL_EXIT,
  BUYING_ECHEST,
  PLACING_ECHEST;

  static {
    String[] textArray =
        new String[] {
          "OPENING_CHEST",
          "DEPOSITING_ITEMS",
          "FINAL_EXIT",
          "WORKING",
          "PLACING_ECHEST",
          "BUYING_ECHEST",
          "WAITING_FOR_STRANGER",
          "GOING_TO_CHEST"
        };
  }
}
