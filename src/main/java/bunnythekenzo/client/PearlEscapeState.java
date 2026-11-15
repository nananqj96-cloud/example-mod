package bunnythekenzo.client;

public enum PearlEscapeState {
  IDLE,
  RENDER_DISTANCE_LOW,
  RELOG_PAUSE,
  RELOG_WAIT_CONNECT,
  RELOG_WAIT_JOIN,
  PEARL_PREPARE,
  PEARL_AIMED,
  PEARL_WAIT_RTP,
  PEARL_WAIT_CHUNKS,
  PEARL_WAIT_LANDING;

  static {
    String[] textArray =
        new String[] {
          "PEARL_AIMED",
          "RENDER_DISTANCE_LOW",
          "RELOG_PAUSE",
          "IDLE",
          "PEARL_WAIT_RTP",
          "RELOG_WAIT_CONNECT",
          "RELOG_WAIT_JOIN",
          "PEARL_PREPARE",
          "PEARL_WAIT_CHUNKS",
          "PEARL_WAIT_LANDING"
        };
  }
}
