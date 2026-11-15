package bunnythekenzo.client;

import bunnythekenzo.client.hud.HudWidget;

public record WorldSignal(
    HudWidget hudWidget,
    float amount,
    float amount2,
    float amount3,
    float amount4,
    boolean enabled) {

  public boolean test(float floatValue, float floatValue2) {
    return floatValue >= this.amount
        && floatValue <= this.amount + this.amount3
        && floatValue2 >= this.amount2
        && floatValue2 <= this.amount2 + this.amount4;
  }

  public float computeFloat(float floatValue) {
    float floatValue2 = this.amount3 / this.hudWidget.getAmount8();
    float floatValue3 = this.hudWidget.getAmount8() / 2.0F;
    return ((floatValue - Math.round(this.amount)) / floatValue2 - floatValue3)
            / this.computeFloat()
        + floatValue3;
  }

  public float computeFloat2(float floatValue) {
    float floatValue2 = this.amount4 / this.hudWidget.getAmount9();
    float floatValue3 = this.hudWidget.getAmount9() / 2.0F;
    return ((floatValue - Math.round(this.amount2)) / floatValue2 - floatValue3)
            / this.computeFloat()
        + floatValue3;
  }

  public float computeFloat() {
    float float2 = this.hudWidget.computeFloat2();
    return float2 < 0.999F ? 0.94F + 0.06F * float2 : 1.0F;
  }
}
