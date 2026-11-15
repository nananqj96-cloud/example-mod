package bunnythekenzo.client;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.texture.Sprite;

public final class VertexConsumerSupport8 implements VertexConsumer {
  public final VertexConsumer vertexConsumer;
  public final Sprite sprite;
  public int count;
  public int count2;

  public VertexConsumerSupport8(VertexConsumer vertexConsumer2, Sprite sprite2) {
    this.vertexConsumer = vertexConsumer2;
    this.sprite = sprite2;
  }

  @Override
  public VertexConsumer vertex(float floatValue, float floatValue2, float floatValue3) {
    this.vertexConsumer.vertex(floatValue, floatValue2, floatValue3);
    return this;
  }

  @Override
  public VertexConsumer color(int intValue, int intValue2, int intValue3, int intValue4) {
    this.vertexConsumer.color(intValue, intValue2, intValue3, intValue4);
    return this;
  }

  @Override
  public VertexConsumer color(int intValue) {
    this.vertexConsumer.color(intValue);
    return this;
  }

  @Override
  public VertexConsumer texture(float floatValue, float floatValue2) {
    this.vertexConsumer.texture(floatValue, floatValue2);
    this.count =
        Math.round(
            Math.clamp(
                    (floatValue - this.sprite.getMinU())
                        / (this.sprite.getMaxU() - this.sprite.getMinU()),
                    0.0F,
                    1.0F)
                * 32767.0F);
    this.count2 =
        Math.round(
            Math.clamp(
                    (floatValue2 - this.sprite.getMinV())
                        / (this.sprite.getMaxV() - this.sprite.getMinV()),
                    0.0F,
                    1.0F)
                * 32767.0F);
    return this;
  }

  @Override
  public VertexConsumer overlay(int intValue, int intValue2) {
    this.vertexConsumer.overlay(this.count, this.count2);
    return this;
  }

  @Override
  public VertexConsumer light(int intValue, int intValue2) {
    this.vertexConsumer.light(intValue, intValue2);
    return this;
  }

  @Override
  public VertexConsumer normal(float floatValue, float floatValue2, float floatValue3) {
    this.vertexConsumer.normal(floatValue, floatValue2, floatValue3);
    return this;
  }

  @Override
  public VertexConsumer lineWidth(float floatValue) {
    this.vertexConsumer.lineWidth(floatValue);
    return this;
  }
}
