package bunnythekenzo.client;

import java.util.function.IntPredicate;

public final class DynamicLinkageFixture {
  public static ClientInitializer[] clientInitializerArray;
  public static final String string = "entry=";

  public static IntPredicate predicate(int intValue) {
    return intValue2 -> {
      return intValue2 > intValue;
    };
  }

  public static String getString(int intValue) {
    return string + intValue;
  }

  public static void setClientInitializerArray(ClientInitializer[] clientInitializerArray2) {
    clientInitializerArray = clientInitializerArray2;
  }

  public static ClientInitializer[] getClientInitializerArray() {
    return clientInitializerArray;
  }

  static {
    setClientInitializerArray(null);
  }
}
