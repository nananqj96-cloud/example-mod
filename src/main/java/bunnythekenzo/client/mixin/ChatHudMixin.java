package bunnythekenzo.client.mixin;

import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.module.ModuleManager;

@Mixin(ChatHud.class)
public class ChatHudMixin {
  @ModifyVariable(
      method =
          "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V",
      at = @At("HEAD"),
      argsOnly = true,
      ordinal = 0)
  public Text getText(Text text) {
    ModuleManager moduleManager = ClientInitializer.getModuleManager();
    if (moduleManager == null) {
      return text;
    } else {
      return moduleManager.nameProtectModule != null && moduleManager.nameProtectModule.isEnabled()
          ? moduleManager.nameProtectModule.getText(text)
          : text;
    }
  }
}
