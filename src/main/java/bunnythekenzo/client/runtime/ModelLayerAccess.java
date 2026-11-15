package bunnythekenzo.client.runtime;

import java.lang.invoke.MethodHandles;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;

public final class ModelLayerAccess {
  public static final EntityModelLayer PLAYER_SLIM = slimPlayer();

  private ModelLayerAccess() {}

  private static EntityModelLayer slimPlayer() {
    try {
      java.lang.invoke.MethodHandle getter;
      try {
        getter =
            MethodHandles.publicLookup()
                .findStaticGetter(EntityModelLayers.class, "field_27581", EntityModelLayer.class);
      } catch (NoSuchFieldException developmentNamespace) {
        getter =
            MethodHandles.publicLookup()
                .findStaticGetter(EntityModelLayers.class, "PLAYER_SLIM", EntityModelLayer.class);
      }
      return (EntityModelLayer) getter.invokeExact();
    } catch (Throwable failure) {
      throw new ExceptionInInitializerError(failure);
    }
  }
}
