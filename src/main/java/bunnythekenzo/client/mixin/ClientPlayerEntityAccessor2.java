package bunnythekenzo.client.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ClientPlayerEntity.class)
public interface ClientPlayerEntityAccessor2 {
  @Accessor("lastXClient")
  double getLastXClient();

  @Accessor("lastYClient")
  double getLastYClient();

  @Accessor("lastZClient")
  double getLastZClient();

  @Accessor("lastYawClient")
  float getLastYawClient();

  @Accessor("lastPitchClient")
  float getLastPitchClient();
}
