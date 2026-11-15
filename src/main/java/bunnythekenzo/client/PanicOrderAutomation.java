package bunnythekenzo.client;

import java.util.Locale;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import bunnythekenzo.client.module.ModuleCategory;
import bunnythekenzo.client.setting.SettingEntry;
import bunnythekenzo.client.setting.StringSettingView;
import bunnythekenzo.client.setting.ToggleSettingView;

public final class PanicOrderAutomation {

  public static final StringSettingView playerControl =
      new StringSettingView(
          "Player", "Player who should receive the panic order", "", "Player name", 32);
  public static final StringSettingView itemControl =
      new StringSettingView("Item", "Item to fulfil, for example elytra", "elytra", "Item id", 64);
  public static final ToggleSettingView chatFeedbackControl =
      new ToggleSettingView("Chat Feedback", "Shows Panic Order progress and errors in chat", true);
  public static final GuiModuleEntry guiModuleEntry =
      new GuiModuleEntry(
          "Panic Order",
          "Fulfils an order from one key press",
          new CollectionData[] {
            new CollectionData("Order", new SettingEntry[] {playerControl, itemControl}),
            new CollectionData("Feedback", new SettingEntry[] {chatFeedbackControl})
          });
  public static PanicOrderState panicOrderState = PanicOrderState.IDLE;
  public static int count3;
  public static int count4;
  public static String string;
  public static ScreenHandler screenHandler;
  public static Screen screen;
  public static ScreenHandler screenHandler2;
  public static String string2;
  public static Screen screen2;
  public static ScreenHandler screenHandler3;
  public static int count5;
  public static int count6;
  public static Screen screen3;
  public static ScreenHandler screenHandler4;
  public static boolean enabled;
  public static boolean enabled2;

  public static GuiModuleEntry getGuiModuleEntry() {
    return guiModuleEntry;
  }

  public static void update() {
    ModuleCategory.ensureInitialized();
    if (!enabled) {
      enabled = true;
      ClientTickEvents.END_CLIENT_TICK.register(PanicOrderAutomation::apply2);
    }
  }

  public static boolean test(int intValue) {
    ModuleCategory.ensureInitialized();
    if (guiModuleEntry.test()
        && guiModuleEntry.test3()
        && guiModuleEntry.computeInt() == intValue) {
      MinecraftClient instance = MinecraftClient.getInstance();
      if (instance.currentScreen == null && instance.player != null && instance.world != null) {
        apply(instance);
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  public static void apply(MinecraftClient minecraftClient2) {
    ModuleCategory.ensureInitialized();
    if (panicOrderState != PanicOrderState.IDLE) {
      apply(minecraftClient2.player, "Panic Order is already running.", Formatting.YELLOW);
    } else {
      String text = playerControl.getValue().trim();
      String string3 = getString(itemControl.getValue());
      if (!isSingleCommandArgument(text)) {
        apply(minecraftClient2.player, "Set a valid player name first.", Formatting.RED);
      } else if (!isSafeItemName(string3)) {
        apply(minecraftClient2.player, "Set a valid item name first.", Formatting.RED);
      } else if (!test(minecraftClient2.player.getInventory(), string3)) {
        apply(
            minecraftClient2.player,
            "You do not have " + string3 + " in your inventory.",
            Formatting.RED);
      } else {
        string = string3;
        screenHandler = minecraftClient2.player.currentScreenHandler;
        screen = null;
        screenHandler2 = null;
        screen2 = null;
        screenHandler3 = null;
        screen3 = null;
        screenHandler4 = null;
        apply(PanicOrderState.WAIT_ORDER_MENU);
        minecraftClient2.player.networkHandler.sendChatCommand("orders " + text + " " + string3);
        apply(
            minecraftClient2.player,
            "Running /orders " + text + " " + string3 + "…",
            Formatting.LIGHT_PURPLE);
      }
    }
  }

  public static boolean isSingleCommandArgument(String text) {
    ModuleCategory.ensureInitialized();
    return !text.isBlank()
        && text.length() <= 64
        && text.chars()
            .noneMatch(
                intValue -> {
                  ModuleCategory.ensureInitialized();
                  return Character.isWhitespace(intValue) || intValue == 47;
                });
  }

  public static boolean isSafeItemName(String text) {
    ModuleCategory.ensureInitialized();
    return !text.isBlank()
        && text.length() <= 64
        && text.chars()
            .noneMatch(
                intValue -> {
                  ModuleCategory.ensureInitialized();
                  return intValue == 47 || Character.isISOControl(intValue);
                });
  }

  public static String getString(String text) {
    ModuleCategory.ensureInitialized();
    return text == null ? "" : text.trim().replaceAll("\\s+", " ");
  }

  public static void apply2(MinecraftClient minecraftClient) {
    ModuleCategory.ensureInitialized();
    if (PearlEscapeAutomation.test2()) {
      return;
    }
    if (panicOrderState == PanicOrderState.IDLE) {
      return;
    }
    if (minecraftClient.player == null
        || minecraftClient.world == null
        || minecraftClient.interactionManager == null) {
      PanicOrderAutomation.apply(
          minecraftClient, "Panic Order stopped because you left the server.");
      return;
    }
    if (++count3 > 240) {
      PanicOrderAutomation.apply(minecraftClient, PanicOrderAutomation.getString());
      return;
    }
    switch (panicOrderState.ordinal()) {
      case 1:
        {
          PanicOrderAutomation.apply3(minecraftClient);
          break;
        }
      case 2:
        {
          PanicOrderAutomation.apply4(minecraftClient);
          break;
        }
      case 3:
        {
          PanicOrderAutomation.apply5(minecraftClient);
          break;
        }
      case 4:
        {
          PanicOrderAutomation.apply6(minecraftClient);
          break;
        }
      case 5:
        {
          PanicOrderAutomation.apply7(minecraftClient);
          break;
        }
    }
  }

  public static void apply3(MinecraftClient minecraftClient2) {
    ModuleCategory.ensureInitialized();

    PanicOrderAutomationData panicOrderAutomationData =
        getPanicOrderAutomationData(minecraftClient2);
    if (panicOrderAutomationData != null
        && panicOrderAutomationData.screenHandler() != screenHandler) {
      if (panicOrderAutomationData.screen() != screen
          || panicOrderAutomationData.screenHandler() != screenHandler2) {
        screen = panicOrderAutomationData.screen();
        screenHandler2 = panicOrderAutomationData.screenHandler();
        count4 = 0;
      } else if (++count4 >= 2) {
        if (screenHandler2.slots.isEmpty()) {
          apply(minecraftClient2, "The orders GUI has no first slot.");
        } else {
          apply(minecraftClient2, screenHandler2, 0, SlotActionType.PICKUP);
          string2 = getString(screen, screenHandler2);
          apply(PanicOrderState.WAIT_ITEM_MENU);
        }
      }
    }
  }

  public static void apply4(MinecraftClient minecraftClient2) {
    ModuleCategory.ensureInitialized();

    PanicOrderAutomationData panicOrderAutomationData =
        getPanicOrderAutomationData(minecraftClient2);
    if (panicOrderAutomationData != null) {
      boolean flag =
          panicOrderAutomationData.screen() != screen
              || panicOrderAutomationData.screenHandler() != screenHandler2
              || !getString(
                      panicOrderAutomationData.screen(), panicOrderAutomationData.screenHandler())
                  .equals(string2);
      if (flag) {
        if (panicOrderAutomationData.screen() != screen2
            || panicOrderAutomationData.screenHandler() != screenHandler3) {
          screen2 = panicOrderAutomationData.screen();
          screenHandler3 = panicOrderAutomationData.screenHandler();
          count4 = 0;
        } else if (++count4 >= 2) {
          int intValue = computeInt(minecraftClient2.player, screenHandler3, string);
          if (intValue < 0) {
            apply(minecraftClient2, "Could not find " + string + " in the open GUI inventory.");
          } else {
            count5 = computeInt(minecraftClient2.player.getInventory(), string);
            count6 = computeInt2(minecraftClient2.player, screenHandler3, string);
            apply(minecraftClient2, screenHandler3, intValue, SlotActionType.QUICK_MOVE);
            apply(PanicOrderState.WAIT_TRANSFER);
          }
        }
      }
    }
  }

  public static void apply5(MinecraftClient minecraftClient2) {
    ModuleCategory.ensureInitialized();

    PanicOrderAutomationData panicOrderAutomationData =
        getPanicOrderAutomationData(minecraftClient2);
    if (panicOrderAutomationData != null
        && panicOrderAutomationData.screenHandler() == screenHandler3) {
      if (count3 >= 2) {
        int intValue = computeInt(minecraftClient2.player.getInventory(), string);
        int intValue2 = computeInt2(minecraftClient2.player, screenHandler3, string);
        if (intValue < count5 || intValue2 > count6) {
          screen2.close();
          apply(PanicOrderState.WAIT_CONFIRM);
        }
      }
    }
  }

  public static void apply6(MinecraftClient minecraftClient2) {
    ModuleCategory.ensureInitialized();

    PanicOrderAutomationData panicOrderAutomationData =
        getPanicOrderAutomationData(minecraftClient2);
    if (panicOrderAutomationData != null
        && panicOrderAutomationData.screen() != screen2
        && panicOrderAutomationData.screenHandler() != screenHandler3) {
      if (panicOrderAutomationData.screen() != screen3
          || panicOrderAutomationData.screenHandler() != screenHandler4) {
        screen3 = panicOrderAutomationData.screen();
        screenHandler4 = panicOrderAutomationData.screenHandler();
        count4 = 0;
      } else if (++count4 >= 2) {
        if (screenHandler4.slots.size() <= 15) {
          apply(minecraftClient2, "The confirm GUI has no slot 15.");
        } else {
          apply(minecraftClient2, screenHandler4, 15, SlotActionType.PICKUP);
          apply(PanicOrderState.WAIT_FINISH);
        }
      }
    }
  }

  public static void apply7(MinecraftClient minecraftClient2) {
    ModuleCategory.ensureInitialized();
    if (count3 >= 2) {
      apply(minecraftClient2.player, "Panic Order confirmed.", Formatting.GREEN);
      update2();
    }
  }

  public static PanicOrderAutomationData getPanicOrderAutomationData(
      MinecraftClient minecraftClient2) {
    ModuleCategory.ensureInitialized();

    return minecraftClient2.currentScreen instanceof HandledScreen screen4
        ? new PanicOrderAutomationData(screen4, screen4.getScreenHandler())
        : null;
  }

  public static void apply(
      MinecraftClient minecraftClient2,
      ScreenHandler screenHandler5,
      int intValue,
      SlotActionType slotActionType) {
    minecraftClient2.interactionManager.clickSlot(
        screenHandler5.syncId, intValue, 0, slotActionType, minecraftClient2.player);
  }

  public static int computeInt(
      ClientPlayerEntity clientPlayerEntity, ScreenHandler screenHandler5, String text) {
    ModuleCategory.ensureInitialized();

    PlayerInventory inventory = clientPlayerEntity.getInventory();

    for (int index = 0; index < screenHandler5.slots.size(); index++) {
      Slot slot = screenHandler5.slots.get(index);
      if (slot.inventory == inventory && test(slot.getStack(), text)) {
        return index;
      }
    }

    return -1;
  }

  public static boolean test(PlayerInventory playerInventory, String text) {
    ModuleCategory.ensureInitialized();
    return computeInt(playerInventory, text) > 0;
  }

  public static int computeInt(PlayerInventory playerInventory, String text) {
    ModuleCategory.ensureInitialized();

    int intValue = 0;

    for (int index = 0; index < playerInventory.size(); index++) {
      ItemStack stack = playerInventory.getStack(index);
      if (test(stack, text)) {
        intValue += stack.getCount();
      }
    }

    return intValue;
  }

  public static int computeInt2(
      ClientPlayerEntity clientPlayerEntity, ScreenHandler screenHandler5, String text) {
    ModuleCategory.ensureInitialized();

    int intValue = 0;

    for (Slot slot : screenHandler5.slots) {
      if (slot.inventory != clientPlayerEntity.getInventory() && test(slot.getStack(), text)) {
        intValue += slot.getStack().getCount();
      }
    }

    return intValue;
  }

  public static boolean test(ItemStack itemStack, String text) {
    ModuleCategory.ensureInitialized();
    if (itemStack.isEmpty()) {
      return false;
    }

    String text2 = text.trim().toLowerCase(Locale.ROOT);
    Identifier id = Registries.ITEM.getId(itemStack.getItem());
    return id == null || !id.toString().equals(text2) && !id.getPath().equals(text2)
        ? itemStack.getName().getString().equalsIgnoreCase(text.trim())
        : true;
  }

  public static String getString(Screen screen4, ScreenHandler screenHandler5) {
    StringBuilder builder =
        new StringBuilder(screen4.getTitle().getString())
            .append('|')
            .append(screenHandler5.slots.size())
            .append('|');
    ModuleCategory.ensureInitialized();

    int intValue = Math.min(screenHandler5.slots.size(), 27);

    for (int index = 0; index < intValue; index++) {
      ItemStack stack = screenHandler5.slots.get(index).getStack();
      if (stack.isEmpty()) {
        builder.append(';');
      } else {
        builder
            .append(Registries.ITEM.getId(stack.getItem()))
            .append(':')
            .append(stack.getCount())
            .append(':')
            .append(stack.getName().getString())
            .append(';');
      }
    }

    ItemStack cursorStack = screenHandler5.getCursorStack();
    if (!cursorStack.isEmpty()) {
      builder
          .append("held=")
          .append(Registries.ITEM.getId(cursorStack.getItem()))
          .append(':')
          .append(cursorStack.getCount());
    }

    return builder.toString();
  }

  public static String getString() {
    return switch (panicOrderState.ordinal()) {
      default -> throw new MatchException(null, null);
      case 1 -> "Timed out waiting for the orders GUI.";
      case 2 -> "Timed out waiting for the item GUI.";
      case 3 -> "The item was not accepted by the order GUI.";
      case 4 -> "Timed out waiting for the confirm GUI.";
      case 0, 5 -> "Panic Order timed out.";
    };
  }

  public static void apply(MinecraftClient minecraftClient2, String text) {
    ClientInitializer.logger.warn("Panic Order failed: {}", text);
    apply(minecraftClient2.player, text, Formatting.RED);
    update2();
  }

  public static void apply(
      ClientPlayerEntity clientPlayerEntity, String text, Formatting formatting) {
    if (clientPlayerEntity != null && chatFeedbackControl.getValue()) {
      clientPlayerEntity.sendMessage(
          Text.literal("[Panic Order] ")
              .formatted(Formatting.LIGHT_PURPLE)
              .append(Text.literal(text).formatted(formatting)),
          false);
    }
  }

  public static void apply(PanicOrderState panicOrderState2) {
    panicOrderState = panicOrderState2;
    count3 = 0;
    count4 = 0;
  }

  public static void update2() {
    panicOrderState = PanicOrderState.IDLE;
    count3 = 0;
    count4 = 0;
    string = null;
    screenHandler = null;
    screen = null;
    screenHandler2 = null;
    string2 = null;
    screen2 = null;
    screenHandler3 = null;
    screen3 = null;
    screenHandler4 = null;
  }

  static {
    setEnabled2(false);
    guiModuleEntry.apply(true);
  }

  public static void setEnabled2(boolean enabled22) {
    enabled2 = enabled22;
  }

  public static boolean isEnabled2() {
    return enabled2;
  }

  public static boolean test() {
    boolean flag = isEnabled2();
    return !flag;
  }
}
