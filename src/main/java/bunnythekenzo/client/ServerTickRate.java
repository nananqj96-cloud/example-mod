package bunnythekenzo.client;

public final class ServerTickRate {
  public static long lastPacketNanos = -1L;
  public static float smoothedTicksPerSecond = 20.0F;

  public static void recordPacket() {
    long timestamp2 = System.nanoTime();
    if (lastPacketNanos > 0L) {
      float floatValue = (float) (timestamp2 - lastPacketNanos) / 1.0E9F;
      if (floatValue > 0.05F) {
        float floatValue2 = Math.clamp(20.0F / floatValue, 0.0F, 20.0F);
        smoothedTicksPerSecond = smoothedTicksPerSecond * 0.7F + floatValue2 * 0.3F;
      }
    }

    lastPacketNanos = timestamp2;
  }

  public static void reset() {
    lastPacketNanos = -1L;
    smoothedTicksPerSecond = 20.0F;
  }

  public static float getTicksPerSecond() {
    return smoothedTicksPerSecond;
  }
}
