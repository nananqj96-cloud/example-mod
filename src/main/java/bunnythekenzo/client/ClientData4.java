package bunnythekenzo.client;

public record ClientData4(
    boolean enabled,
    String string,
    String string2,
    long timestamp,
    long timestamp2,
    boolean enabled2,
    boolean enabled3,
    int count,
    int count2,
    long timestamp3) {
  public static final ClientData4 clientData4 =
      new ClientData4(false, "", "", 0L, 0L, false, false, 0, -1, 0L);
  public static boolean enabled4;
  public static final long timestamp4 = 1000000L;

  public long computeLong() {
    if (this.enabled2 && this.timestamp2 > 0L) {
      long longValue = (System.nanoTime() - this.timestamp3) / timestamp4;
      return Math.min(this.timestamp + longValue, this.timestamp2);
    } else {
      return this.timestamp;
    }
  }

  static {
    setEnabled4(true);
  }

  public static void setEnabled4(boolean enabled42) {
    enabled4 = enabled42;
  }

  public static boolean isEnabled4() {
    return enabled4;
  }

  public static boolean test() {
    boolean flag = isEnabled4();
    return !flag;
  }

  public static void ensureInitialized() {}
}
