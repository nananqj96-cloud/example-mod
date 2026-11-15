package bunnythekenzo.client.gametest;

import net.minecraft.client.render.VertexConsumer;

public final class HoppinStandingBeforeTestSupport14 implements VertexConsumer {
  public float amount;
  public int count;

  @Override
  public VertexConsumer vertex(float floatValue, float floatValue2, float floatValue3) {
    ProtectionPerformanceTest.ensureInitialized();
    OrangeJusticeStandingBeforeTest.apply(
        Float.isFinite(floatValue) && Float.isFinite(floatValue2) && Float.isFinite(floatValue3),
        "finite vertices");
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
    ProtectionPerformanceTest.ensureInitialized();
    OrangeJusticeStandingBeforeTest.apply(
        Float.isFinite(floatValue) && Float.isFinite(floatValue2), "finite UVs");
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
