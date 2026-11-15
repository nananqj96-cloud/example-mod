package bunnythekenzo.client;

public record AutoToolData(float amount, boolean enabled, int count, int count2, double value) {

  public boolean test(AutoToolData autoToolData, String text) {
    FakeLootDropModule.ensureInitialized();

    int intValue =
        switch (text) {
          case "Fortune" -> Integer.compare(this.count, autoToolData.count);
          case "Silk Touch" -> Integer.compare(this.count2, autoToolData.count2);
          case "Durability" -> Double.compare(this.value, autoToolData.value);
          default -> Float.compare(this.amount, autoToolData.amount);
        };
    if (intValue != 0) {
      return intValue > 0;
    }

    if (this.enabled != autoToolData.enabled) {
      return this.enabled;
    }

    int intValue2 = Float.compare(this.amount, autoToolData.amount);
    return intValue2 != 0 ? intValue2 > 0 : Double.compare(this.value, autoToolData.value) > 0;
  }
}
