package bunnythekenzo.client;

public enum AntiVanishState2 {
  TAB_REMOVE,
  TAB_HIDE,
  PLAYER_LEFT,
  SYSTEM_CHAT,
  ENTITY_METADATA,
  POSITIONAL_SOUND,
  ENTITY_SOUND,
  PARTICLE,
  EXPLOSION,
  GAMEMODE;

  static {
    String[] textArray =
        new String[] {
          "SYSTEM_CHAT",
          "ENTITY_SOUND",
          "POSITIONAL_SOUND",
          "PLAYER_LEFT",
          "TAB_REMOVE",
          "ENTITY_METADATA",
          "PARTICLE",
          "EXPLOSION",
          "TAB_HIDE",
          "GAMEMODE"
        };
  }
}
