package bunnythekenzo.client;

import java.util.UUID;
import net.minecraft.util.math.Vec3d;

public record AntiVanishData7(
    AntiVanishState2 antiVanishState2,
    UUID uUID,
    int count,
    double value,
    double b_,
    double value2,
    String string) {

  public static AntiVanishData7 getAntiVanishData7(UUID uUID2) {
    return new AntiVanishData7(AntiVanishState2.TAB_REMOVE, uUID2, -1, 0.0, 0.0, 0.0, "");
  }

  public static AntiVanishData7 getAntiVanishData72(UUID uUID2) {
    return new AntiVanishData7(AntiVanishState2.TAB_HIDE, uUID2, -1, 0.0, 0.0, 0.0, "");
  }

  public static AntiVanishData7 getAntiVanishData7(UUID uUID2, String string2) {
    return new AntiVanishData7(AntiVanishState2.GAMEMODE, uUID2, -1, 0.0, 0.0, 0.0, string2);
  }

  public static AntiVanishData7 getAntiVanishData7(String string2) {
    return new AntiVanishData7(AntiVanishState2.PLAYER_LEFT, null, -1, 0.0, 0.0, 0.0, string2);
  }

  public static AntiVanishData7 getAntiVanishData72(String string2) {
    return new AntiVanishData7(AntiVanishState2.SYSTEM_CHAT, null, -1, 0.0, 0.0, 0.0, string2);
  }

  public static AntiVanishData7 getAntiVanishData7(int count2) {
    return new AntiVanishData7(AntiVanishState2.ENTITY_METADATA, null, count2, 0.0, 0.0, 0.0, "");
  }

  public static AntiVanishData7 getAntiVanishData7(Vec3d vec3d, float floatValue) {
    return new AntiVanishData7(
        AntiVanishState2.EXPLOSION,
        null,
        -1,
        vec3d.x,
        vec3d.y,
        vec3d.z,
        Float.toString(floatValue));
  }

  public static AntiVanishData7 getAntiVanishData7(int count2, String string2) {
    return new AntiVanishData7(AntiVanishState2.ENTITY_SOUND, null, count2, 0.0, 0.0, 0.0, string2);
  }

  public static AntiVanishData7 getAntiVanishData7(
      AntiVanishState2 antiVanishState22,
      double value3,
      double b_2,
      double value22,
      String string2) {
    return new AntiVanishData7(antiVanishState22, null, -1, value3, b_2, value22, string2);
  }

  public Vec3d getVec3d() {
    return new Vec3d(this.value, this.b_, this.value2);
  }
}
