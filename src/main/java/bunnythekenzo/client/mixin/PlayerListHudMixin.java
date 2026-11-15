package bunnythekenzo.client.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.module.ModuleManager;

@Mixin(PlayerListHud.class)
public class PlayerListHudMixin {
  @ModifyReturnValue(method = "getPlayerName", at = @At("RETURN"))
  public Text getText(Text text, PlayerListEntry playerListEntry) {
    ModuleManager moduleManager = ClientInitializer.getModuleManager();
    if (moduleManager == null) {
      return text;
    }

    GameProfile profile = playerListEntry.getProfile();
    String text2 = profile == null ? null : profile.name();
    if (moduleManager.nameProtectModule != null
        && moduleManager.nameProtectModule.isEnabled()
        && text2 != null
        && !text2.isEmpty()) {
      String string2 = moduleManager.nameProtectModule.getString2(text2);
      if (string2 != null) {
        return Text.literal(string2);
      }
    }

    return text;
  }
}
