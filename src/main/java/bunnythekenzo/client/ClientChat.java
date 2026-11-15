package bunnythekenzo.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import bunnythekenzo.client.module.Module;

public final class ClientChat {
  public static final String string = "§d[bunnythekenzo] §f";

  public static ChatMessageBuilder getChatMessageBuilder() {
    return new ChatMessageBuilder();
  }

  public static void apply(Module module, ChatMessageBuilder chatMessageBuilder) {
    ChatMessageBuilder.getCountArray();
    MinecraftClient instance = MinecraftClient.getInstance();
    if (instance.player != null) {
      instance.player.sendMessage(Text.literal(string).append(chatMessageBuilder.getText()), false);
    }
  }
}
