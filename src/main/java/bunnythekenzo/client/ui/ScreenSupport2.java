package bunnythekenzo.client.ui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.ConnectScreen;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;

public final class ScreenSupport2 {
  public static void apply(Object value, Object value2, Object value3, Object value4) {
    ConnectScreen.connect(
        (Screen) value,
        (MinecraftClient) value2,
        (ServerAddress) value3,
        (ServerInfo) value4,
        false,
        null);
  }
}
