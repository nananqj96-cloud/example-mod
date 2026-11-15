package bunnythekenzo.client.module;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import bunnythekenzo.client.FakeLootDropModule;
import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.ChoiceSetting;
import bunnythekenzo.client.setting.KeybindSetting;
import bunnythekenzo.client.setting.StringSetting;

public class ChatMacroModule extends Module {

  public final ChoiceSetting slotSetting;
  public final BooleanSetting sendInstantlySetting;
  public final StringSetting[] choiceSettingSupport4Array;
  public final KeybindSetting[] keybindSettingArray;

  public ChatMacroModule() {
    super("ChatMacro", "Bindable chat command macros.", ModuleCategory.getInstance());

    FakeLootDropModule.ensureInitialized();

    this.slotSetting =
        (ChoiceSetting)
            this.registerSetting(
                new ChoiceSetting(
                    "Slot", "Macro slot to edit.", "1", new String[] {"1", "2", "3"}));
    this.sendInstantlySetting =
        (BooleanSetting)
            this.registerSetting(
                new BooleanSetting(
                    "Send Instantly", "Skip the chat preview and send immediately.", true));
    this.choiceSettingSupport4Array = new StringSetting[3];
    this.keybindSettingArray = new KeybindSetting[3];

    for (int index = 0; index < 3; index++) {
      String text = Integer.toString(index + 1);
      this.choiceSettingSupport4Array[index] =
          (StringSetting)
              this.registerSetting(
                  new StringSetting(
                      "Message " + text,
                      "Message or /command for slot " + text,
                      "",
                      256,
                      "/say hi"));
      this.keybindSettingArray[index] =
          (KeybindSetting)
              this.registerSetting(
                  new KeybindSetting("Key " + text, "Key that runs slot " + text, -1));
      this.choiceSettingSupport4Array[index].setVisibilityCondition(
          () -> {
            return this.slotSetting.isSelected(text);
          });
      this.keybindSettingArray[index].setVisibilityCondition(
          () -> {
            return this.slotSetting.isSelected(text);
          });
    }
  }

  @Override
  public boolean onKeyPress(int intValue) {
    FakeLootDropModule.ensureInitialized();

    boolean flag = false;

    for (int index = 0; index < 3; index++) {
      if (this.keybindSettingArray[index].matches(intValue)) {
        this.apply((String) this.choiceSettingSupport4Array[index].getValue());
        flag = true;
      }
    }

    return flag;
  }

  public void apply(String text) {
    FakeLootDropModule.ensureInitialized();
    if (text != null && !text.isBlank()) {
      MinecraftClient instance = MinecraftClient.getInstance();
      if (instance.player != null && instance.player.networkHandler != null) {
        if (!(Boolean) this.sendInstantlySetting.getValue()) {
          instance.setScreen(new ChatScreen(text, false));
        } else {
          if (text.startsWith("/")) {
            instance.player.networkHandler.sendChatCommand(text.substring(1));
          } else {
            instance.player.networkHandler.sendChatMessage(text);
          }
        }
      }
    }
  }
}
