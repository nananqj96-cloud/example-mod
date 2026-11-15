package bunnythekenzo.client.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.PlayerInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ClientPlayerEntity.class)
public interface ClientPlayerEntityAccessor {
  @Accessor("lastPlayerInput")
  PlayerInput getLastPlayerInput();

  @Accessor("lastSprinting")
  boolean getLastSprinting();
}
