package bunnythekenzo.client.module;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.FakeLootDropModule;
import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.ChoiceSetting;
import bunnythekenzo.client.setting.KeybindSetting;

public class CoordSnapperModule extends Module {
  public final ChoiceSetting formatSetting =
      (ChoiceSetting)
          this.registerSetting(
              new ChoiceSetting(
                  "Format",
                  "Clipboard format.",
                  "X Y Z",
                  new String[] {"X Y Z", "JSON", "Command"}));
  public final ChoiceSetting targetSetting =
      (ChoiceSetting)
          this.registerSetting(
              new ChoiceSetting(
                  "Target",
                  "Which coordinates to copy.",
                  "Looked-at Block",
                  new String[] {"Looked-at Block", "Player"}));
  public final KeybindSetting copyKeySetting =
      (KeybindSetting)
          this.registerSetting(
              new KeybindSetting("Copy Key", "Press to copy the coordinates.", -1));
  public final BooleanSetting notifySetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Notify", "Show a confirmation when copied.", true));
  public String string = "";

  public CoordSnapperModule() {
    super("CoordSnapper", "Copies looked-at coordinates with one key.", ModuleCategory.MISC);
  }

  public String getString() {
    return this.string;
  }

  @Override
  public boolean onKeyPress(int intValue) {
    FakeLootDropModule.ensureInitialized();
    if (!this.copyKeySetting.matches(intValue)) {
      return false;
    }

    this.update4();
    return true;
  }

  public void update4() {
    FakeLootDropModule.ensureInitialized();
    Object value = null;
    MinecraftClient instance = MinecraftClient.getInstance();
    if (instance.player != null) {
      BlockPos blockPos = this.getBlockPos(instance);
      if (blockPos == null) {
        if ((Boolean) this.notifySetting.getValue()) {
          ClientInitializer.getNotificationsHud().apply("CoordSnapper · no block in view");
        }
      } else {
        String string2 =
            switch ((String) this.formatSetting.getValue()) {
              case "JSON" -> {
                value = blockPos.getX();
                int z = blockPos.getZ();
                int y = blockPos.getY();
                int intValue = (int) value;
                yield "{\"x\": " + intValue + ", \"y\": " + y + ", \"z\": " + z + "}";
              }
              case "Command" -> {
                value = blockPos.getX();
                int z2 = blockPos.getZ();
                int y2 = blockPos.getY();
                int intValue2 = (int) value;
                yield "/tp " + intValue2 + " " + y2 + " " + z2;
              }
              default -> blockPos.getX() + " " + blockPos.getY() + " " + blockPos.getZ();
            };
        instance.keyboard.setClipboard(string2);
        this.string = string2;
        if ((Boolean) this.notifySetting.getValue()) {
          ClientInitializer.getNotificationsHud().apply("Copied · " + string2);
        }
      }
    }
  }

  public BlockPos getBlockPos(MinecraftClient minecraftClient) {
    FakeLootDropModule.ensureInitialized();
    if (this.targetSetting.isSelected("Player")) {
      return minecraftClient.player.getBlockPos();
    }

    HitResult crosshairTarget = minecraftClient.crosshairTarget;
    return crosshairTarget instanceof BlockHitResult blockHitResult
            && crosshairTarget.getType() == Type.BLOCK
        ? blockHitResult.getBlockPos()
        : null;
  }
}
