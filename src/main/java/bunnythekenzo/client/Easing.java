package bunnythekenzo.client;

public enum Easing {
  LINEAR {
    @Override
    public float computeFloat(float floatValue) {
      return floatValue;
    }
  },
  IN_QUAD {
    @Override
    public float computeFloat(float floatValue) {
      return floatValue * floatValue;
    }
  },
  IN_CUBIC {
    @Override
    public float computeFloat(float floatValue) {
      return floatValue * floatValue * floatValue;
    }
  },
  OUT_QUART {
    @Override
    public float computeFloat(float floatValue) {
      float floatValue2 = 1.0F - floatValue;
      return 1.0F - floatValue2 * floatValue2 * floatValue2 * floatValue2;
    }
  },
  IN_QUINT {
    @Override
    public float computeFloat(float floatValue) {
      return Easing.computeFloat(floatValue, 5);
    }
  },
  OUT_EXPO {
    @Override
    public float computeFloat(float floatValue) {
      return floatValue >= 1.0F ? 1.0F : 1.0F - (float) Math.pow(2.0, -10.0F * floatValue);
    }
  },
  IN_BACK {
    @Override
    public float computeFloat(float floatValue) {
      return 2.70158F * floatValue * floatValue * floatValue - 1.70158F * floatValue * floatValue;
    }
  },
  OUT_BACK {
    @Override
    public float computeFloat(float floatValue) {
      float floatValue2 = floatValue - 1.0F;
      return 1.0F
          + 2.70158F * Easing.computeFloat3(floatValue2)
          + 1.70158F * Easing.computeFloat2(floatValue2);
    }
  };

  private static final float amount = 1.70158F;
  private static final float amount2 = 2.70158F;

  public abstract float computeFloat(float floatValue);

  private static float computeFloat2(float floatValue) {
    return floatValue * floatValue;
  }

  private static float computeFloat3(float floatValue) {
    return floatValue * floatValue * floatValue;
  }

  private static float computeFloat(float floatValue, int intValue) {
    float floatValue2 = 1.0F;

    for (int index = 0; index < intValue; index++) {
      floatValue2 *= floatValue;
    }

    return floatValue2;
  }
}
