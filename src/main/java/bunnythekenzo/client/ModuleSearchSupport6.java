package bunnythekenzo.client;

public final class ModuleSearchSupport6 {
  public ModuleSearchData2 moduleSearchData2;
  public MenuLayout menuLayout;

  public ClientData3 getClientData3(float floatValue, float floatValue2) {
    return this.getClientData3(floatValue, floatValue2, true);
  }

  public ClientData3 getClientData32(float floatValue, float floatValue2) {
    return this.getClientData3(floatValue, floatValue2, false);
  }

  public ClientData3 getClientData3(float floatValue, float floatValue2, boolean flag) {
    if (this.moduleSearchData2 != null
        && Float.isFinite(floatValue)
        && Float.isFinite(floatValue2)) {
      ClientData3 clientData3 = this.menuLayout.getClientData3(floatValue, floatValue2, 0.0F);
      ClientData3 clientData32 =
          this.moduleSearchData2
              .getMenuLayout(this.menuLayout)
              .getClientData3(clientData3.amount(), clientData3.amount2());
      ModuleSearchData3 moduleSearchData3 =
          this.moduleSearchData2.getModuleSearchData3(
              clientData32.amount(), clientData32.amount2(), 0.0F, 0.0F, 0.0F);
      return flag
              && !this.moduleSearchData2
                  .moduleSearchData4()
                  .test(moduleSearchData3.amount(), moduleSearchData3.amount2())
          ? new ClientData3(Float.NaN, Float.NaN)
          : new ClientData3(
              this.moduleSearchData2.amount() + clientData32.amount(),
              this.moduleSearchData2.amount2() + clientData32.amount2());
    } else {
      return new ClientData3(Float.NaN, Float.NaN);
    }
  }

  public ClientData3 getClientData33(float floatValue, float floatValue2) {
    return this.moduleSearchData2 == null
        ? new ClientData3(Float.NaN, Float.NaN)
        : this.moduleSearchData2
            .getMenuLayout(this.menuLayout)
            .getClientData3(floatValue, floatValue2, 0.0F);
  }

  public boolean test(float floatValue, float floatValue2) {
    return this.moduleSearchData2 != null
        && this.moduleSearchData2.moduleSearchData4().test(floatValue, floatValue2)
        && floatValue
            >= this.moduleSearchData2.amount()
                + this.moduleSearchData2.amount4()
                - this.moduleSearchData2.amount6() * 0.5F
        && floatValue
            <= this.moduleSearchData2.amount()
                + this.moduleSearchData2.amount4()
                - this.moduleSearchData2.amount6() * 0.5F
                + this.moduleSearchData2.amount3()
        && floatValue2
            >= this.moduleSearchData2.amount2()
                + this.moduleSearchData2.amount5()
                - this.moduleSearchData2.amount6() * 0.57F
        && floatValue2
            <= this.moduleSearchData2.amount2()
                + this.moduleSearchData2.amount5()
                - this.moduleSearchData2.amount6() * 0.57F
                + this.moduleSearchData2.l_();
  }

  public float computeFloat() {
    return this.moduleSearchData2 == null
        ? Float.NEGATIVE_INFINITY
        : this.moduleSearchData2.amount6();
  }

  public void update() {
    this.moduleSearchData2 = null;
  }
}
