package bunnythekenzo.client;

import net.minecraft.entity.Entity;

@FunctionalInterface
public interface PlayerModelHooksContract {
  void apply(Entity entity);
}
