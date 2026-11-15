package bunnythekenzo.client.mixin;

import java.util.List;
import java.util.Map;
import net.minecraft.client.model.ModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ModelPart.class)
public interface ModelPartAccessor {
  @Accessor("cuboids")
  List getCuboids();

  @Accessor("children")
  Map getChildren();

  @Mutable
  @Accessor("children")
  void setChildren(Map map);
}
