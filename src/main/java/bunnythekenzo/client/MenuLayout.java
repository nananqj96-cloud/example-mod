package bunnythekenzo.client;

public final class MenuLayout {
  public static final float amount = 920.0F;
  public final float amount2;
  public final float amount3;
  public final float amount4;
  public final float amount5;
  public final float amount6;
  public final float amount7;
  public final float amount8;
  public final float amount9;
  public final float amount10;
  public final float amount11;
  public final float amount12;
  public final float amount13;
  public final float amount14;
  public final float amount15;
  public final float amount16;
  public final float amount17;

  public MenuLayout(
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      float floatValue7) {
    this(
        floatValue,
        floatValue2,
        floatValue3,
        floatValue4,
        floatValue5,
        floatValue6,
        floatValue7,
        0.0F,
        0.0F);
  }

  public MenuLayout(
      float floatValue,
      float floatValue2,
      float amount42,
      float floatValue3,
      float floatValue4,
      float amount52,
      float amount62,
      float amount72,
      float amount82) {
    this.amount2 = floatValue - amount72;
    this.amount3 = floatValue2 - amount82;
    this.amount4 = amount42;
    this.amount7 = amount72;
    this.amount8 = amount82;
    this.amount12 = 0.0F;
    this.amount5 = amount52;
    this.amount6 = amount62;
    float floatValue5 = (float) Math.sin(floatValue3);
    float amount92 = (float) Math.cos(floatValue3);
    float floatValue6 = (float) Math.sin(floatValue4);
    float amount132 = (float) Math.cos(floatValue4);
    this.amount9 = amount92;
    this.amount10 = floatValue5 * floatValue6;
    this.amount11 = floatValue5 * amount132;
    this.amount13 = amount132;
    this.amount14 = -floatValue6;
    this.amount15 = -floatValue5;
    this.amount16 = amount92 * floatValue6;
    this.amount17 = amount92 * amount132;
  }

  public MenuLayout getMenuLayout(
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      float floatValue7) {
    return new MenuLayout(
        this,
        floatValue,
        floatValue2,
        floatValue3,
        floatValue4,
        floatValue5,
        floatValue6,
        floatValue7);
  }

  public MenuLayout(
      MenuLayout menuLayout,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float amount52,
      float amount62) {
    this.amount2 = menuLayout.amount2;
    this.amount3 = menuLayout.amount3;
    this.amount5 = amount52;
    this.amount6 = amount62;
    float floatValue6 = floatValue - menuLayout.amount5;
    float floatValue7 = floatValue2 - menuLayout.amount6;
    this.amount7 =
        menuLayout.amount7
            + menuLayout.amount9 * floatValue6
            + menuLayout.amount10 * floatValue7
            + menuLayout.amount11 * floatValue3;
    this.amount8 =
        menuLayout.amount8
            + menuLayout.amount12 * floatValue6
            + menuLayout.amount13 * floatValue7
            + menuLayout.amount14 * floatValue3;
    this.amount4 =
        menuLayout.amount4
            + menuLayout.amount15 * floatValue6
            + menuLayout.amount16 * floatValue7
            + menuLayout.amount17 * floatValue3;
    float floatValue8 = (float) Math.sin(floatValue4);
    float floatValue9 = (float) Math.cos(floatValue4);
    float floatValue10 = (float) Math.sin(floatValue5);
    float floatValue11 = (float) Math.cos(floatValue5);
    this.amount9 = menuLayout.amount9 * floatValue9 - menuLayout.amount11 * floatValue8;
    this.amount12 = menuLayout.amount12 * floatValue9 - menuLayout.amount14 * floatValue8;
    this.amount15 = menuLayout.amount15 * floatValue9 - menuLayout.amount17 * floatValue8;
    this.amount10 =
        menuLayout.amount9 * floatValue8 * floatValue10
            + menuLayout.amount10 * floatValue11
            + menuLayout.amount11 * floatValue9 * floatValue10;
    this.amount13 =
        menuLayout.amount12 * floatValue8 * floatValue10
            + menuLayout.amount13 * floatValue11
            + menuLayout.amount14 * floatValue9 * floatValue10;
    this.amount16 =
        menuLayout.amount15 * floatValue8 * floatValue10
            + menuLayout.amount16 * floatValue11
            + menuLayout.amount17 * floatValue9 * floatValue10;
    this.amount11 =
        menuLayout.amount9 * floatValue8 * floatValue11
            - menuLayout.amount10 * floatValue10
            + menuLayout.amount11 * floatValue9 * floatValue11;
    this.amount14 =
        menuLayout.amount12 * floatValue8 * floatValue11
            - menuLayout.amount13 * floatValue10
            + menuLayout.amount14 * floatValue9 * floatValue11;
    this.amount17 =
        menuLayout.amount15 * floatValue8 * floatValue11
            - menuLayout.amount16 * floatValue10
            + menuLayout.amount17 * floatValue9 * floatValue11;
  }

  public ClientData3 getClientData3(float floatValue, float floatValue2, float floatValue3) {
    float floatValue4 = floatValue - this.amount5;
    float floatValue5 = floatValue2 - this.amount6;
    float floatValue6 =
        920.0F
            / (920.0F
                - this.amount4
                - this.amount15 * floatValue4
                - this.amount16 * floatValue5
                - this.amount17 * floatValue3);
    return new ClientData3(
        this.amount2
            + (this.amount7
                    + this.amount9 * floatValue4
                    + this.amount10 * floatValue5
                    + this.amount11 * floatValue3)
                * floatValue6,
        this.amount3
            + (this.amount8
                    + this.amount12 * floatValue4
                    + this.amount13 * floatValue5
                    + this.amount14 * floatValue3)
                * floatValue6);
  }

  public ClientData3 getClientData3(float floatValue, float floatValue2) {
    return this.getClientData32(floatValue, floatValue2, 0.0F);
  }

  public ClientData3 getClientData32(float floatValue, float floatValue2, float floatValue3) {
    float floatValue4 = floatValue - this.amount2;
    float floatValue5 = floatValue2 - this.amount3;
    float floatValue6 = 920.0F * this.amount9 + floatValue4 * this.amount15;
    float floatValue7 = 920.0F * this.amount10 + floatValue4 * this.amount16;
    float floatValue8 = 920.0F * this.amount12 + floatValue5 * this.amount15;
    float floatValue9 = 920.0F * this.amount13 + floatValue5 * this.amount16;
    float floatValue10 = floatValue6 * floatValue9 - floatValue7 * floatValue8;
    if (Math.abs(floatValue10) < 1.0E-4F) {
      return new ClientData3(Float.NaN, Float.NaN);
    }

    float floatValue11 =
        floatValue4 * (920.0F - this.amount4 - this.amount17 * floatValue3)
            - 920.0F * (this.amount7 + this.amount11 * floatValue3);
    float floatValue12 =
        floatValue5 * (920.0F - this.amount4 - this.amount17 * floatValue3)
            - 920.0F * (this.amount8 + this.amount14 * floatValue3);
    return new ClientData3(
        (floatValue11 * floatValue9 - floatValue7 * floatValue12) / floatValue10 + this.amount5,
        (floatValue6 * floatValue12 - floatValue11 * floatValue8) / floatValue10 + this.amount6);
  }
}
