package bunnythekenzo.client.mixin;

import java.util.function.Supplier;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(targets = "net/minecraft/client/render/RenderSetup$TextureSpec")
public interface Class12247Class12338Accessor {
  @Accessor("location")
  Identifier getLocation();

  @Accessor("sampler")
  Supplier getSampler();
}
