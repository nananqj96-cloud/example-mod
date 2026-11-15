package bunnythekenzo.client.gametest;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.render.VertexConsumer;

public final class HoppinStandingBeforeTestSupport4 implements VertexConsumer {
  public final List list = new ArrayList();
  public float amount;
  public float amount2;
  public float amount3;

  @Override
  public VertexConsumer vertex(float amount4, float amount22, float amount32) {
    this.amount = amount4;
    this.amount2 = amount22;
    this.amount3 = amount32;
    return this;
  }

  @Override
  public VertexConsumer texture(float amount4, float amount5) {
    this.list.add(
        new HoppinStandingBeforeTestData(
            this.amount, this.amount2, this.amount3, amount4, amount5));
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
