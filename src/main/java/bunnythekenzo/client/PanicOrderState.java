package bunnythekenzo.client;

public enum PanicOrderState {
  IDLE,
  WAIT_ORDER_MENU,
  WAIT_ITEM_MENU,
  WAIT_TRANSFER,
  WAIT_CONFIRM,
  WAIT_FINISH;

  private static final long timestamp = -8710277295006285818L;

  static {
    String[] textArray =
        new String[] {
          "WAIT_ITEM_MENU",
          "WAIT_TRANSFER",
          "WAIT_ORDER_MENU",
          "WAIT_FINISH",
          "WAIT_CONFIRM",
          "IDLE"
        };
  }
}
