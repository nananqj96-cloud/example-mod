package bunnythekenzo.client;

public final class DiagnosticTokenGenerator {
  public static final long processSalt =
      mix64(
          System.nanoTime()
              ^ (long) System.identityHashCode(DiagnosticTokenGenerator.class) << 32
              ^ System.identityHashCode(new Object())
              ^ Runtime.getRuntime().availableProcessors());
  public static String[] stringArray;

  public static String tokenFor(int intValue) {
    return Long.toUnsignedString(mix64(processSalt ^ Integer.toUnsignedLong(intValue)), 36);
  }

  public static long mix64(long longValue) {
    longValue ^= longValue >>> 30;
    longValue *= -4658895280553007687L;
    longValue ^= longValue >>> 27;
    longValue *= -7723592293110705685L;
    return longValue ^ longValue >>> 31;
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
