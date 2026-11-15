package bunnythekenzo.client.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.module.ModuleManager;
import bunnythekenzo.client.module.NameTagsModule;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {
  @Inject(method = "renderLabelIfPresent", at = @At("HEAD"), cancellable = true)
  public void apply(
      EntityRenderState entityRenderState,
      MatrixStack matrixStack,
      OrderedRenderCommandQueue orderedRenderCommandQueue,
      CameraRenderState cameraRenderState,
      CallbackInfo callbackInfo) {
    ModuleManager moduleManager = ClientInitializer.getModuleManager();
    if (moduleManager != null && entityRenderState.displayName != null) {
      NameTagsModule nameTagsModule = moduleManager.nameTagsModule;
      boolean flag = nameTagsModule != null && nameTagsModule.isEnabled();
      if (flag) {
        String string = entityRenderState.displayName.getString();
        if (test(string)) {
          if ((Boolean) nameTagsModule.hideOwnTagSetting.getValue()
              || (Boolean) nameTagsModule.playersSetting.getValue()
                  && (Boolean) nameTagsModule.selfSetting.getValue()) {
            callbackInfo.cancel();
            return;
          }
        } else if (test2(string)) {
          if ((Boolean) nameTagsModule.playersSetting.getValue()
              || (Boolean) nameTagsModule.hidePlayerTagsSetting.getValue()) {
            callbackInfo.cancel();
            return;
          }
        } else if ((Boolean) nameTagsModule.hideOtherTagsSetting.getValue()) {
          callbackInfo.cancel();
          return;
        }
      }

      if (moduleManager.nameProtectModule != null && moduleManager.nameProtectModule.isEnabled()) {
        String string2 =
            moduleManager.nameProtectModule.getString2(entityRenderState.displayName.getString());
        if (string2 != null) {
          entityRenderState.displayName = Text.literal(string2);
        }
      }
    }
  }

  private static boolean test(String text) {
    if (text != null && !text.isEmpty()) {
      ClientPlayerEntity player = MinecraftClient.getInstance().player;
      if (player == null) {
        return false;
      }

      String text2 = player.getGameProfile().name();
      return text2 != null && !text2.isEmpty() && text.contains(text2);
    } else {
      return false;
    }
  }

  private static boolean test2(String text) {
    if (text != null && !text.isEmpty()) {
      MinecraftClient instance = MinecraftClient.getInstance();
      if (instance.getNetworkHandler() == null) {
        return false;
      }

      for (PlayerListEntry playerListEntry : instance.getNetworkHandler().getPlayerList()) {
        GameProfile profile = playerListEntry.getProfile();
        String text2 = profile == null ? null : profile.name();
        if (text2 != null && !text2.isEmpty() && text.contains(text2)) {
          return true;
        }
      }

      return false;
    } else {
      return false;
    }
  }
}
