package bunnythekenzo.client;

public record ClientData(
    String string, String string2, int count, boolean enabled, int count2, int count3) {
  public static String string3;
  public static final long timestamp = -6956833355681759233L;

  public ClientData(
      String string, String string2, int count, boolean enabled, int count2, int count3) {

    this.string = string;
    this.string2 = string2;
    this.count = count;
    this.enabled = enabled;
    this.count2 = count2;
    this.count3 = count3;
    ClientInitializer.ensureInitialized();
  }

  public boolean test() {
    return (this.count & 16777215) != 0;
  }

  public static void setString3(String string32) {
    string3 = string32;
  }

  public static String getString3() {
    return string3;
  }

  static {
    setString3("g3M3o");
  }

  public static void ensureInitialized() {}
}
