package bunnythekenzo.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;

public final class WorldChangeTracker {
  public Object object;
  public String string = "";

  public boolean test(MinecraftClient minecraftClient) {
    ChatMessageBuilder.getCountArray();
    ClientWorld world = minecraftClient.world;
    String string2 =
        minecraftClient.getCurrentServerEntry() == null
            ? ""
            : minecraftClient.getCurrentServerEntry().address;
    boolean flag = this.object != null && (this.object != world || !this.string.equals(string2));
    this.object = world;
    this.string = string2;
    return flag;
  }

  public void update() {
    this.object = null;
    this.string = "";
  }
}
