package bunnythekenzo.client.mixin;

import java.util.Map;
import net.minecraft.client.render.LayeringTransform;
import net.minecraft.client.render.OutputTarget;
import net.minecraft.client.render.RenderSetup;
import net.minecraft.client.render.RenderSetup.OutlineMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RenderSetup.class)
public interface RenderSetupAccessor {
  @Accessor("textures")
  Map getTextures();

  @Accessor("useLightmap")
  boolean getUseLightmap();

  @Accessor("useOverlay")
  boolean getUseOverlay();

  @Accessor("layeringTransform")
  LayeringTransform getLayeringTransform();

  @Accessor("outputTarget")
  OutputTarget getOutputTarget();

  @Accessor("outlineMode")
  OutlineMode getOutlineMode();
}
