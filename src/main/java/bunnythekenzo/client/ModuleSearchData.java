package bunnythekenzo.client;

public record ModuleSearchData(float amount, float amount2, float amount3, float amount4) {

  public ModuleSearchData3 getModuleSearchData3(float floatValue, float amount32) {
    return new ModuleSearchData3(
        this.amount + this.amount3 * floatValue,
        this.amount2 + this.amount4 * floatValue,
        amount32,
        0.0F,
        0.0F);
  }
}
