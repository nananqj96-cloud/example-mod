package bunnythekenzo.client;

import java.util.ArrayDeque;
import java.util.Deque;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.Generic3x3ContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import bunnythekenzo.client.module.Module;
import bunnythekenzo.client.module.ModuleCategory;
import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.NumberSetting;

public class GambleRiggerStateSupport extends Module {

  public static final int count3 = 0;
  public static final int count4 = 1;
  public final NumberSetting clickDelaySetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                      "Click Delay",
                      "Ticks between each moved stack. 0 = instant (whole grid in one tick); raise"
                          + " it only if a server flags rapid container clicks.",
                      0.0,
                      0.0,
                      10.0,
                      1.0)
                  .withFormatter(
                      doubleValue -> {
                        FakeLootDropModule.ensureInitialized();
                        return (int) doubleValue <= 0 ? "Instant" : (int) doubleValue + " tick";
                      }));
  public final BooleanSetting keepSlotStaysSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Keep Slot Stays",
                  "Leave the picked slot untouched in the container (someone else refills it). Off:"
                      + " pull it out too and restore leaves it empty.",
                  true));
  public final BooleanSetting chatFeedbackSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Chat Feedback", "Print what the rigger is doing to chat.", true));
  public final MinecraftClient minecraftClient = MinecraftClient.getInstance();
  public GambleRiggerState gambleRiggerState = GambleRiggerState.IDLE;
  public int count5 = -1;
  public int count6 = -1;
  public final ItemStack[] itemStackArray = new ItemStack[9];
  public final int[] countArray = new int[9];
  public final Deque deque = new ArrayDeque();
  public int count7 = 0;
  public static ClientInitializer[] clientInitializerArray;

  public GambleRiggerStateSupport() {
    super(
        "GambleRigger",
        "Rig a dispenser/dropper gamble — a fake slot-panel keypad that pulls every slot but the"
            + " one you keep, then restores the exact layout.",
        ModuleCategory.MISC);
  }

  public GambleRiggerState getGambleRiggerState() {
    return this.gambleRiggerState;
  }

  public int getCount5() {
    return this.count5;
  }

  public int computeInt() {
    return this.deque.size();
  }

  public boolean test() {
    FakeLootDropModule.ensureInitialized();
    return this.gambleRiggerState == GambleRiggerState.EXTRACTING
        || this.gambleRiggerState == GambleRiggerState.RESTORING;
  }

  public boolean test4() {
    FakeLootDropModule.ensureInitialized();
    return this.gambleRiggerState == GambleRiggerState.IDLE && this.test6();
  }

  public boolean test5() {
    FakeLootDropModule.ensureInitialized();
    return this.gambleRiggerState == GambleRiggerState.EXTRACTED && this.test6();
  }

  public void apply(int count52) {
    FakeLootDropModule.ensureInitialized();
    if (this.test4() && count52 >= 0 && count52 < 9) {
      ClientPlayerEntity player = this.minecraftClient.player;
      if (player != null
          && player.currentScreenHandler
              instanceof Generic3x3ContainerScreenHandler generic3x3ContainerScreenHandler) {
        this.count6 = generic3x3ContainerScreenHandler.syncId;

        this.count5 = count52;

        this.deque.clear();
        this.count7 = 0;
        boolean[] flags = new boolean[45];
        int index = 0;

        for (int index2 = 0; index2 < 9; index2++) {
          this.itemStackArray[index2] =
              generic3x3ContainerScreenHandler.getSlot(index2).getStack().copy();
          this.countArray[index2] = -1;
        }

        for (int index3 = 0; index3 < 9; index3++) {
          if ((!(Boolean) this.keepSlotStaysSetting.getValue() || index3 != this.count5)
              && !this.itemStackArray[index3].isEmpty()) {
            int value = this.computeInt(generic3x3ContainerScreenHandler, flags);
            if (value >= 0) {
              flags[value] = true;
              this.countArray[index3] = value;
              this.deque.add(new int[] {0, index3, value});
            } else {
              index++;
              this.deque.add(new int[] {1, index3, 0});
            }
          }
        }

        if (this.deque.isEmpty()) {
          this.gambleRiggerState = GambleRiggerState.EXTRACTED;
          this.apply(
              "§dGambleRigger §7» grid empty — nothing to take, click §fRestore §7when done");
        } else {
          this.gambleRiggerState = GambleRiggerState.EXTRACTING;
          if (index > 0) {
            this.apply(
                "§eGambleRigger §7» your inventory is nearly full — "
                    + index
                    + " stack(s) fall back to shift-click and may not restore exactly");
          }
        }
      }
    }
  }

  public void update4() {
    FakeLootDropModule.ensureInitialized();
    if (this.test5()) {
      ClientPlayerEntity player = this.minecraftClient.player;
      if (player != null
          && player.currentScreenHandler
              instanceof Generic3x3ContainerScreenHandler generic3x3ContainerScreenHandler
          && generic3x3ContainerScreenHandler.syncId == this.count6) {
        this.deque.clear();

        this.count7 = 0;

        boolean[] flags = new boolean[45];
        int index = 0;

        for (int index2 = 0; index2 < 9; index2++) {
          if (index2 != this.count5) {
            ItemStack itemStack = this.itemStackArray[index2];
            if (itemStack != null && !itemStack.isEmpty()) {
              int value =
                  this.computeInt(generic3x3ContainerScreenHandler, itemStack, index2, flags);
              if (value < 0) {
                index++;
              } else {
                flags[value] = true;
                this.deque.add(new int[] {0, value, index2});
              }
            }
          }
        }

        this.gambleRiggerState = GambleRiggerState.RESTORING;
        if (index > 0) {
          this.apply(
              "§eGambleRigger §7» restoring, but §c"
                  + index
                  + " §7stack(s) were not found in your inventory");
        }
      } else {
        this.apply2(true);
      }
    }
  }

  public void update5() {
    FakeLootDropModule.ensureInitialized();
    boolean flag = this.gambleRiggerState != GambleRiggerState.IDLE || this.count5 >= 0;
    this.update10();
    if (flag) {
      this.apply("§7GambleRigger » reset");
    }
  }

  @Override
  public void onTick() {
    this.update8();
  }

  public void update8() {
    FakeLootDropModule.ensureInitialized();
    if (this.deque.isEmpty()) {
      this.update9();
    } else {
      ClientPlayerEntity player = this.minecraftClient.player;
      if (player != null
          && this.minecraftClient.interactionManager != null
          && player.currentScreenHandler instanceof Generic3x3ContainerScreenHandler
          && player.currentScreenHandler.syncId == this.count6) {
        boolean flag = this.clickDelaySetting.intValue() <= 0;

        while (!this.deque.isEmpty()) {
          if (this.count7 > 0) {
            this.count7--;
            return;
          }

          this.apply(player, (int[]) this.deque.poll());
          this.count7 = this.clickDelaySetting.intValue();
          if (!flag) {
            return;
          }
        }

        this.update9();
      } else {
        this.apply2(true);
      }
    }
  }

  public void update9() {
    FakeLootDropModule.ensureInitialized();
    if (this.deque.isEmpty()) {
      if (this.gambleRiggerState == GambleRiggerState.EXTRACTING) {
        this.gambleRiggerState = GambleRiggerState.EXTRACTED;
        this.apply(
            "§aGambleRigger §7» pulled the grid (kept §f#"
                + (this.count5 + 1)
                + "§7) — click §fRestore §7when ready");
      } else if (this.gambleRiggerState == GambleRiggerState.RESTORING) {
        this.apply(
            "§aGambleRigger §7» layout restored — slot §f#" + (this.count5 + 1) + " §7left open");
        this.update10();
      }
    }
  }

  public void apply(PlayerEntity playerEntity, int[] intValues) {
    FakeLootDropModule.ensureInitialized();

    ScreenHandler currentScreenHandler = playerEntity.currentScreenHandler;
    if (intValues[0] == 1) {
      this.apply(playerEntity, intValues[1], 0, SlotActionType.QUICK_MOVE);
    } else {
      this.apply(playerEntity, intValues[1], 0, SlotActionType.PICKUP);
      if (!currentScreenHandler.getCursorStack().isEmpty()) {
        this.apply(playerEntity, intValues[2], 0, SlotActionType.PICKUP);
      }

      if (!currentScreenHandler.getCursorStack().isEmpty()) {
        this.apply(playerEntity, intValues[1], 0, SlotActionType.PICKUP);
      }
    }
  }

  @Override
  public void onDisable() {
    this.apply2(false);
  }

  public boolean test6() {
    FakeLootDropModule.ensureInitialized();

    ClientPlayerEntity player = this.minecraftClient.player;
    return player != null
        && player.currentScreenHandler instanceof Generic3x3ContainerScreenHandler;
  }

  public void apply(
      PlayerEntity playerEntity, int intValue, int intValue2, SlotActionType slotActionType) {
    this.minecraftClient.interactionManager.clickSlot(
        this.count6, intValue, intValue2, slotActionType, playerEntity);
  }

  public int computeInt(ScreenHandler screenHandler, boolean[] flags) {
    FakeLootDropModule.ensureInitialized();

    for (int index = 9; index < 45; index++) {
      if (!flags[index] && screenHandler.getSlot(index).getStack().isEmpty()) {
        return index;
      }
    }

    return -1;
  }

  public int computeInt(
      ScreenHandler screenHandler, ItemStack itemStack, int index, boolean[] flags) {
    FakeLootDropModule.ensureInitialized();

    int index2 = this.countArray[index];
    if (index2 >= 9
        && index2 < 45
        && !flags[index2]
        && ItemStack.areEqual(screenHandler.getSlot(index2).getStack(), itemStack)) {
      return index2;
    }

    for (int index3 = 9; index3 < 45; index3++) {
      if (!flags[index3]
          && ItemStack.areEqual(screenHandler.getSlot(index3).getStack(), itemStack)) {
        return index3;
      }
    }

    for (int index4 = 9; index4 < 45; index4++) {
      if (!flags[index4]) {
        ItemStack stack = screenHandler.getSlot(index4).getStack();
        if (!stack.isEmpty() && ItemStack.areItemsAndComponentsEqual(stack, itemStack)) {
          return index4;
        }
      }
    }

    return -1;
  }

  public void apply2(boolean flag) {
    FakeLootDropModule.ensureInitialized();
    boolean flag2 = this.gambleRiggerState != GambleRiggerState.IDLE;
    this.update10();
    if (flag && flag2) {
      this.apply("§cGambleRigger §7» aborted (the container closed or changed)");
    }
  }

  public void update10() {
    this.gambleRiggerState = GambleRiggerState.IDLE;
    FakeLootDropModule.ensureInitialized();

    this.count5 = -1;
    this.count6 = -1;
    this.deque.clear();
    this.count7 = 0;

    for (int index = 0; index < 9; index++) {
      this.itemStackArray[index] = null;
      this.countArray[index] = -1;
    }
  }

  public void apply(String text) {
    FakeLootDropModule.ensureInitialized();
    if ((Boolean) this.chatFeedbackSetting.getValue()) {
      ClientPlayerEntity player = this.minecraftClient.player;
      if (player != null) {
        player.sendMessage(Text.literal(text), false);
      }
    }
  }

  public static void setClientInitializerArray(ClientInitializer[] clientInitializerArray2) {
    clientInitializerArray = clientInitializerArray2;
  }

  public static ClientInitializer[] getClientInitializerArray() {
    return clientInitializerArray;
  }

  static {
    setClientInitializerArray(new ClientInitializer[4]);
  }
}
