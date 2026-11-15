package bunnythekenzo.client;

import java.util.UUID;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;
import bunnythekenzo.client.module.StaffListModule;

public final class AntiVanishSupport2 {
  public final UUID uUID;
  public final String string;
  public final boolean enabled;
  public final String string2;
  public final AntiVanishData4 antiVanishData4;
  public final int count;
  public final long timestamp = System.currentTimeMillis();
  public boolean enabled2;
  public boolean enabled3;
  public static final String string3 = "vanished";

  public AntiVanishSupport2(
      UUID uUID2,
      String string4,
      boolean enabled4,
      String string22,
      AntiVanishData4 antiVanishData42,
      int count2) {
    this.uUID = uUID2;
    this.string = string4;
    this.enabled = enabled4;
    this.string2 = string22;
    this.antiVanishData4 = antiVanishData42;
    this.count = count2;
  }

  public Text getText() {
    return this.enabled
        ? StaffListModule.getChatMessageBuilder(
                ClientChat.getChatMessageBuilder(), GameMode.SPECTATOR.getId())
            .getText()
        : ClientChat.getChatMessageBuilder().getChatMessageBuilder6(string3).getText();
  }
}
