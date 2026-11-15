package bunnythekenzo.client;

public enum AnimationCurve {
  LINEAR {
    @Override
    public float computeFloat(float floatValue) {
      return floatValue;
    }
  },
  EASE_OUT_CUBIC {
    @Override
    public float computeFloat(float floatValue) {
      float floatValue2 = 1.0F - floatValue;
      return 1.0F - floatValue2 * floatValue2 * floatValue2;
    }
  },
  EASE_IN_OUT_QUAD {
    @Override
    public float computeFloat(float floatValue) {
      return floatValue < 0.5F
          ? 2.0F * floatValue * floatValue
          : 1.0F - (float) Math.pow(-2.0F * floatValue + 2.0F, 2.0) / 2.0F;
    }
  };

  public abstract float computeFloat(float floatValue);
}
