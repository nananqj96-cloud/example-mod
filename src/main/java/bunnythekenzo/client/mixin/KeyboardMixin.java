package bunnythekenzo.client.mixin;

import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.AbstractSignEditScreen;
import net.minecraft.client.gui.screen.ingame.BookEditScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.input.KeyInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.MovementOverrides;
import bunnythekenzo.client.PanicOrderAutomation;
import bunnythekenzo.client.PearlEscapeAutomation;
import bunnythekenzo.client.ui.ScreenSupport4;
import bunnythekenzo.client.ui.WelcomeScreen;

@Mixin(Keyboard.class)
public class KeyboardMixin {
  @Inject(
      method = "onKey(JILnet/minecraft/client/input/KeyInput;)V",
      at = @At("HEAD"),
      cancellable = true)
  public void apply(long longValue, int intValue, KeyInput keyInput, CallbackInfo callbackInfo) {
    MinecraftClient instance = MinecraftClient.getInstance();
    if (longValue == instance.getWindow().getHandle()) {
      if (ClientInitializer.getModuleManager() != null
          && ClientInitializer.getModuleManager().creamyKeysModule != null) {
        ClientInitializer.getModuleManager()
            .creamyKeysModule
            .apply(keyInput.key(), keyInput.scancode(), intValue);
      }

      if (intValue == 0) {
        if (ClientInitializer.getModuleManager() != null
            && ClientInitializer.getModuleManager().test2(keyInput.key())) {
          callbackInfo.cancel();
        }
      } else if (intValue == 1) {
        if (!PearlEscapeAutomation.test()) {
          if (PearlEscapeAutomation.test(keyInput.key())) {
            callbackInfo.cancel();
          } else if (PanicOrderAutomation.test(keyInput.key())) {
            callbackInfo.cancel();
          } else if (MovementOverrides.test(keyInput.key())) {
            callbackInfo.cancel();
          } else if (ClientInitializer.getEmoteCatalog() != null
              && ClientInitializer.getEmoteCatalog().test2(keyInput.key())) {
            callbackInfo.cancel();
          } else if (instance.currentScreen == null && instance.world != null) {
            if (ClientInitializer.getModuleManager() != null
                && ClientInitializer.getModuleManager().test(keyInput.key())) {
              callbackInfo.cancel();
            }
          } else if (keyInput.key() == 344 && instance.currentScreen != null) {
            if (test(instance.currentScreen)) {
              instance.setScreen(ScreenSupport4.getScreen(instance.currentScreen));
              callbackInfo.cancel();
            }
          }
        }
      }
    }
  }

  private static boolean test(Screen screen) {
    return !ScreenSupport4.test(screen)
            && !(screen instanceof WelcomeScreen)
            && !(screen instanceof ChatScreen)
            && !(screen instanceof HandledScreen)
            && !(screen instanceof AbstractSignEditScreen)
            && !(screen instanceof BookEditScreen)
        ? !(screen.getFocused() instanceof TextFieldWidget textFieldWidget
            && textFieldWidget.isFocused())
        : false;
  }
}
