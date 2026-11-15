package bunnythekenzo.client;

import net.minecraft.sound.SoundEvent;

public record EmoteDefinition(
    String string,
    String string2,
    String string3,
    float amount,
    long timestamp,
    SoundEvent soundEvent,
    boolean enabled,
    EmoteAnimation emoteAnimation) {

  public static EmoteDefinition getEmoteDefinition(String string4) {
    return new EmoteDefinition(
        string4,
        "Blank",
        "No animation or music added yet",
        0.0F,
        0L,
        null,
        false,
        EmoteAnimation.NONE);
  }

  public boolean test() {
    return this.emoteAnimation == EmoteAnimation.NONE;
  }

  public String getString() {
    if (this.test()) {
      return "—";
    }

    long longValue = Math.round(this.timestamp / 1000.0);
    return "%d:%02d".formatted(longValue / 60L, longValue % 60L);
  }
}
