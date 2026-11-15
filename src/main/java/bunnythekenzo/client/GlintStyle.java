package bunnythekenzo.client;

import java.util.Arrays;

public enum GlintStyle {
  DEFAULT("Default", "Classic foil in your own colour", -9418308, -1788673),
  GALAXY("Galaxy", "Drifting nebulae and twinkling stars", -11980115, -750613),
  ENDER("Ender", "Violet ripples with turquoise sparks", -9165892, -11075616),
  VOID("Void", "Dark purple waves and spectral seams", -14281656, -5478419),
  TOXIC("Toxic", "Pulsing pools of acid green", -15440333, -3408052),
  AMETHYST("Amethyst", "Shifting crystalline facets", -10141004, -806401),
  CYBER("Cyber", "Neon circuits and travelling signals", -15574388, -42535),
  SOLAR("Solar", "Golden flares across a molten surface", -4703465, -5494),
  PRISMATIC("Prismatic", "Flowing ribbons of the rainbow", -12415745, -34599),
  ABYSS("Abyss", "Deep ocean currents and luminous crests", -15582344, -10949150),
  AURORA("Aurora", "Undulating curtains of mint and violet", -9285430, -9699390),
  INFERNO("Inferno", "Rising flame tongues and glowing embers", -6547926, -18873);

  public final String label;
  public final String description;
  public final int dark;
  public final int bright;

  GlintStyle(String label2, String description2, int dark2, int bright2) {
    this.label = label2;
    this.description = description2;
    this.dark = dark2;
    this.bright = bright2;
  }

  public static GlintStyle byName(String text) {
    return Arrays.stream(values())
        .filter(
            glintStyle -> {
              return glintStyle.label.equals(text);
            })
        .findFirst()
        .orElse(DEFAULT);
  }

  public static String[] names() {
    return Arrays.stream(values())
        .map(
            glintStyle -> {
              return glintStyle.label;
            })
        .toArray(String[]::new);
  }
}
