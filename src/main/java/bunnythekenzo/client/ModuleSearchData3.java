package bunnythekenzo.client;

public record ModuleSearchData3(
    float amount, float amount2, float amount3, float amount4, float amount5) {

  public ModuleSearchData3 getModuleSearchData3(
      ModuleSearchData3 moduleSearchData3, float floatValue) {
    return new ModuleSearchData3(
        this.amount + (moduleSearchData3.amount - this.amount) * floatValue,
        this.amount2 + (moduleSearchData3.amount2 - this.amount2) * floatValue,
        this.amount3 + (moduleSearchData3.amount3 - this.amount3) * floatValue,
        this.amount4 + (moduleSearchData3.amount4 - this.amount4) * floatValue,
        this.amount5 + (moduleSearchData3.amount5 - this.amount5) * floatValue);
  }
}
