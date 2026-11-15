package bunnythekenzo.client;

import net.minecraft.util.PlayerInput;
import net.minecraft.util.math.Vec2f;
import bunnythekenzo.client.mixin.InputAccessor;

public final class PlayerInputReset {
  public static void apply(Object value) {
    InputAccessor inputAccessor = (InputAccessor) value;
    inputAccessor._p0(PlayerInput.DEFAULT);
    inputAccessor._p1(Vec2f.ZERO);
  }

  public static void apply(Object value, Object value2) {
    ((InputAccessor) value)._p1((Vec2f) value2);
  }
}
