package bunnythekenzo.client.gametest;

import net.minecraft.client.render.VertexConsumer;

public final class HoppinStandingBeforeTestSupport13 implements VertexConsumer {
  public float amount;
  public int count;
  public static final String string = "finite rendered vertex";

  @Override
  public VertexConsumer vertex(float floatValue, float floatValue2, float floatValue3) {
    ProtectionPerformanceTest.ensureInitialized();
    HoppinStandingBeforeTest.apply(
        Float.isFinite(floatValue) && Float.isFinite(floatValue2) && Float.isFinite(floatValue3),
        string);
    this.amount = Math.max(this.amount, floatValue2);
    this.count++;
    return this;
  }

  @Override
  public VertexConsumer color(int intValue, int intValue2, int intValue3, int intValue4) {
    return this;
  }

  @Override
  public VertexConsumer color(int intValue) {
    return this;
  }

  @Override
  public VertexConsumer texture(float floatValue, float floatValue2) {
    return this;
  }

  @Override
  public VertexConsumer overlay(int intValue, int intValue2) {
    return this;
  }

  @Override
  public VertexConsumer light(int intValue, int intValue2) {
    return this;
  }

  @Override
  public VertexConsumer normal(float floatValue, float floatValue2, float floatValue3) {
    return this;
  }

  @Override
  public VertexConsumer lineWidth(float floatValue) {
    return this;
  }
}
