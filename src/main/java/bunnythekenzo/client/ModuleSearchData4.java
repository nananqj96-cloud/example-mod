package bunnythekenzo.client;

public record ModuleSearchData4(float amount, float amount2, float amount3, float k_) {

  public boolean test(float floatValue, float floatValue2) {
    return floatValue >= this.amount
        && floatValue2 >= this.amount2
        && floatValue <= this.amount + this.amount3
        && floatValue2 <= this.amount2 + this.k_;
  }
}
