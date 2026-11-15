package bunnythekenzo.client.ui;

import bunnythekenzo.client.AnimationIntensity;
import bunnythekenzo.client.AnimationIntensitySupport;
import bunnythekenzo.client.ClickRippleEffects;
import bunnythekenzo.client.DampedSpring;

public final class ConfigsPageSupport {
  public final AnimationIntensitySupport animationIntensitySupport =
      new AnimationIntensitySupport();
  public final AnimationIntensitySupport[] animationIntensitySupportArray =
      new AnimationIntensitySupport[] {
        new AnimationIntensitySupport(AnimationIntensity.SUBTLE),
        new AnimationIntensitySupport(AnimationIntensity.SUBTLE),
        new AnimationIntensitySupport(AnimationIntensity.SUBTLE),
        new AnimationIntensitySupport(AnimationIntensity.SUBTLE)
      };
  public final ClickRippleEffects clickRippleEffects = new ClickRippleEffects();
  public final DampedSpring dampedSpring = DampedSpring.createQuick();
  public float amount;
  public float amount2;
}
