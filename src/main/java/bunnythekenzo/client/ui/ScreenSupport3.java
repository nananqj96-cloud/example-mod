package bunnythekenzo.client.ui;

import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenMouseEvents;
import net.minecraft.client.gui.screen.ChatScreen;
import bunnythekenzo.client.WorldSignalSupport;
import bunnythekenzo.client.render.FontManager;
import bunnythekenzo.client.render.OverlayRenderer;

public final class ScreenSupport3 {
  public static void register() {
    ScreenEvents.BEFORE_INIT.register(
        (minecraftClient, screen, intValue, intValue2) -> {
          if (screen instanceof ChatScreen) {
            ScreenMouseEvents.allowMouseClick(screen)
                .register(
                    (screen2, click) -> {
                      FontManager instance = FontManager.getInstance();
                      if (instance != null && click.button() == 0 && OverlayRenderer.test()) {
                        boolean flag =
                            WorldSignalSupport.test(
                                instance,
                                0.0F,
                                0.0F,
                                OverlayRenderer.computeFloat4(),
                                OverlayRenderer.computeFloat5(),
                                1.0F,
                                OverlayRenderer.computeFloat(click.x()),
                                OverlayRenderer.computeFloat(click.y()));
                        return !flag;
                      } else {
                        return true;
                      }
                    });
            ScreenMouseEvents.allowMouseRelease(screen)
                .register(
                    (screen3, click2) -> {
                      if (click2.button() == 0 && WorldSignalSupport.test2()) {
                        WorldSignalSupport.update();
                        return false;
                      } else {
                        return true;
                      }
                    });
            ScreenMouseEvents.allowMouseScroll(screen)
                .register(
                    (screen4, doubleValue, doubleValue2, doubleValue3, doubleValue4) -> {
                      FontManager instance2 = FontManager.getInstance();
                      if (instance2 != null && OverlayRenderer.test() && doubleValue4 != 0.0) {
                        boolean flag2 =
                            WorldSignalSupport.test(
                                instance2,
                                0.0F,
                                0.0F,
                                OverlayRenderer.computeFloat4(),
                                OverlayRenderer.computeFloat5(),
                                1.0F,
                                OverlayRenderer.computeFloat(doubleValue),
                                OverlayRenderer.computeFloat(doubleValue2),
                                doubleValue4);
                        return !flag2;
                      } else {
                        return true;
                      }
                    });
            ScreenEvents.remove(screen)
                .register(
                    screen5 -> {
                      if (WorldSignalSupport.test2()) {
                        WorldSignalSupport.update();
                      }
                    });
          }
        });
  }
}
