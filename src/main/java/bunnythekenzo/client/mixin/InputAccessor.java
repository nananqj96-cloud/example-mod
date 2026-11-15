package bunnythekenzo.client.mixin;

import net.minecraft.client.input.Input;
import net.minecraft.util.PlayerInput;
import net.minecraft.util.math.Vec2f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Input.class)
public interface InputAccessor {
  @Accessor("playerInput")
  void _p0(PlayerInput playerInput);

  @Accessor("movementVector")
  void _p1(Vec2f vec2f);
}
