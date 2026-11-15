package bunnythekenzo.client;

public enum AntiVanishState {
  VANISH,
  INVISIBLE,
  PARTICLE,
  SOUND;

  static {
    String[] textArray = new String[] {"PARTICLE", "SOUND", "VANISH", "INVISIBLE"};
  }
}
