package bunnythekenzo.client;

public class RainbowPalette extends ColorPalette {
  public RainbowPalette() {
    super("Rainbow", -40008, false);
  }

  @Override
  public int getCount() {
    double doubleValue = System.nanoTime() % 1000000000000L / 1.0E9;
    return ArgbColors.fromHsv((float) (doubleValue * 36.0 % 360.0), 0.72F, 1.0F);
  }
}
