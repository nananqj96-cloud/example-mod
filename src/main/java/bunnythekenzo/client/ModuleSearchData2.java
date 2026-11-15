package bunnythekenzo.client;

public record ModuleSearchData2(
    float amount,
    float amount2,
    float amount3,
    float l_,
    float amount4,
    float amount5,
    float amount6,
    float amount7,
    float amount8,
    float amount9,
    float amount10,
    float amount11,
    float amount12,
    float amount13,
    float amount14,
    ModuleSearchData4 moduleSearchData4) {

  public MenuLayout getMenuLayout(MenuLayout menuLayout) {
    return menuLayout.getMenuLayout(
        this.amount + this.amount3 / 2.0F + this.amount4,
        this.amount2 + this.l_ / 2.0F + this.amount5,
        this.amount6,
        this.amount7,
        this.amount8,
        this.amount3 / 2.0F,
        this.l_ / 2.0F);
  }

  public ModuleSearchData3 getModuleSearchData3(
      float floatValue, float floatValue2, float floatValue3, float amount42, float amount52) {
    float floatValue4 = floatValue - this.amount3 / 2.0F;
    float floatValue5 = floatValue2 - this.l_ / 2.0F;
    float floatValue6 = (float) Math.cos(this.amount7);
    float floatValue7 = (float) Math.sin(this.amount7);
    float floatValue8 = (float) Math.cos(this.amount8);
    float floatValue9 = (float) Math.sin(this.amount8);
    return new ModuleSearchData3(
        this.amount
            + this.amount3 / 2.0F
            + this.amount4
            + floatValue6 * floatValue4
            + floatValue7 * floatValue9 * floatValue5
            + floatValue7 * floatValue8 * floatValue3,
        this.amount2
            + this.l_ / 2.0F
            + this.amount5
            + floatValue8 * floatValue5
            - floatValue9 * floatValue3,
        this.amount6
            - floatValue7 * floatValue4
            + floatValue6 * floatValue9 * floatValue5
            + floatValue6 * floatValue8 * floatValue3,
        amount42,
        amount52);
  }
}
