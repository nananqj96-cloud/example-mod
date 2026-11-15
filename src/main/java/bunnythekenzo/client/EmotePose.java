package bunnythekenzo.client;

public final class EmotePose {
  public final float[] amountArray;

  public EmotePose() {
    this.amountArray = new float[26];
    this.getCollectionSupport2(-12.0F, 0.0F, 12.0F, -30.0F);
    this.getEmotePose2(-12.0F, 0.0F, -12.0F, -30.0F);
    this.getCollectionSupport2(5.0F, 0.0F, 0.0F, -5.0F, 0.0F, 0.0F);
    this.getCollectionSupport22(-8.0F, 6.0F, 16.0F, -8.0F, -6.0F, 16.0F);
  }

  public EmotePose(float[] floatValues) {
    this.amountArray = (float[]) floatValues.clone();
  }

  public EmotePose getEmotePose() {
    return new EmotePose(this.amountArray);
  }

  public EmotePose getCollectionSupport2(
      float floatValue, float floatValue2, float floatValue3, float floatValue4) {
    this.render(0, floatValue, floatValue2, floatValue3);
    this.amountArray[18] = floatValue4;
    return this;
  }

  public EmotePose getEmotePose2(
      float floatValue, float floatValue2, float floatValue3, float floatValue4) {
    this.render(3, floatValue, floatValue2, floatValue3);
    this.amountArray[19] = floatValue4;
    return this;
  }

  public EmotePose getCollectionSupport2(
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6) {
    this.render(6, floatValue, floatValue2, floatValue3);
    this.render(9, floatValue4, floatValue5, floatValue6);
    return this;
  }

  public EmotePose getCollectionSupport22(
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6) {
    this.render(12, floatValue, -4.0F, floatValue2);
    this.render(15, floatValue4, 4.0F, floatValue5);
    this.amountArray[20] = floatValue3;
    this.amountArray[21] = floatValue6;
    return this;
  }

  public EmotePose getCollectionSupport2(float floatValue, float floatValue2, float floatValue3) {
    this.render(22, floatValue, floatValue2, floatValue3);
    return this;
  }

  public EmotePose getCollectionSupport2(float floatValue) {
    this.amountArray[25] = floatValue;
    return this;
  }

  public void render(int index, float floatValue, float floatValue2, float floatValue3) {
    this.amountArray[index] = floatValue;
    this.amountArray[index + 1] = floatValue2;
    this.amountArray[index + 2] = floatValue3;
  }
}
