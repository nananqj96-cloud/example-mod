package bunnythekenzo.client;

public enum SkyParticleStyle {
  MOTES("mote", 0.14F, 0.2F, 1.0F, 0.0F),
  SPARKS("spark", 0.05F, 0.32F, 0.85F, 0.0F),
  PETALS("petal", -0.55F, 0.45F, 2.4F, 1.7F);

  private final String sprite;
  private final float drift;
  private final float sway;
  private final float scale;
  private final float spin;

  SkyParticleStyle(String sprite2, float drift2, float sway2, float scale2, float spin2) {
    this.sprite = sprite2;
    this.drift = drift2;
    this.sway = sway2;
    this.scale = scale2;
    this.spin = spin2;
  }

  public String getSprite() {
    return this.sprite;
  }

  public float getDrift() {
    return this.drift;
  }

  public float getSway() {
    return this.sway;
  }

  public float getScale() {
    return this.scale;
  }

  public float getSpin() {
    return this.spin;
  }

  public static SkyParticleStyle getSkyParticleStyle(int intValue) {
    SkyParticleStyle[] skyParticleStyleArray = values();
    return skyParticleStyleArray[Math.clamp(intValue, 0, skyParticleStyleArray.length - 1)];
  }
}
