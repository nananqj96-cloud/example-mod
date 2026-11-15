package bunnythekenzo.client.mixin;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ClientPlayerInteractionManager.class)
public interface ClientPlayerInteractionManagerAccessor {
  @Invoker("syncSelectedSlot")
  void invokeSyncSelectedSlot();

  @Accessor("currentBreakingProgress")
  float getCurrentBreakingProgress();

  @Accessor("currentBreakingProgress")
  void setCurrentBreakingProgress(float floatValue);
}
