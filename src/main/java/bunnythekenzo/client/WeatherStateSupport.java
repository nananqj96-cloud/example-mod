package bunnythekenzo.client;

import bunnythekenzo.client.hud.NotificationsHud;

public class WeatherStateSupport {
  public final String string;
  public final String string2;
  public final WeatherState weatherState;
  public final boolean enabled;
  public final long timestamp;
  public final CurveTween curveTween;

  public WeatherStateSupport(
      String string3, String string22, WeatherState weatherState2, boolean enabled2) {
    super();

    NotificationsHud.getClientInitializerArray();

    this.timestamp = System.nanoTime();
    this.curveTween = new CurveTween(220.0F, 0.0F, AnimationCurve.EASE_OUT_CUBIC);
    this.string = string3;
    this.string2 = string22;
    this.weatherState = weatherState2;
    this.enabled = enabled2;
    this.curveTween.animateTo(1.0F);
    ClientInitializer.ensureInitialized();
  }
}
