package bunnythekenzo.client.module;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.PlayerInput;
import net.minecraft.world.GameMode;
import org.lwjgl.glfw.GLFW;
import bunnythekenzo.client.FakeLootDropModule;
import bunnythekenzo.client.mixin.ClientPlayerEntityAccessor;
import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.NumberSetting;

public class AutoTotemModule extends Module {
  public final NumberSetting delaySetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Delay",
                  "Ticks to wait before filling the offhand while inventory is open. 0 = next"
                      + " eligible tick; 20 ticks = 1 second.",
                  4.0,
                  0.0,
                  40.0,
                  1.0,
                  " ticks"));
  public final BooleanSetting forceTotemSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Force Totem",
                  "Replace an occupied offhand too. The previous item moves into the totem's source"
                      + " slot.",
                  false));
  public final BooleanSetting hotbarTotemSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Hotbar Totem",
                  "Also refill the chosen hotbar slot. Offhand has priority; off disables hotbar"
                      + " refills.",
                  false));
  public final NumberSetting hotbarSlotSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Hotbar Slot",
                  "Hotbar slot to refill (1-9). Its previous item moves into the totem's source"
                      + " slot.",
                  1.0,
                  1.0,
                  9.0,
                  1.0));
  public final NumberSetting hotbarDelaySetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Hotbar Delay",
                  "Ticks to wait before a hotbar refill, after any offhand work. 0 = next eligible"
                      + " tick; 20 ticks = 1 second.",
                  4.0,
                  0.0,
                  40.0,
                  1.0,
                  " ticks"));
  public InventoryScreen inventoryScreen;
  public ClientPlayerEntity clientPlayerEntity;
  public int count = -1;
  public int count2;

  public AutoTotemModule() {
    super(
        "Auto Totem",
        "Refills offhand and optional hotbar totems only while your inventory is open",
        ModuleCategory.COMBAT);
  }

  @Override
  public boolean usesCombatTick() {
    return false;
  }

  @Override
  public void onEnable() {
    this.update5();
  }

  @Override
  public void onDisable() {
    this.update5();
  }

  @Override
  public void onTick() {
    FakeLootDropModule.ensureInitialized();

    MinecraftClient instance = MinecraftClient.getInstance();
    if (!this.test(instance)) {
      this.update5();
    } else {
      InventoryScreen inventoryScreen2 = (InventoryScreen) instance.currentScreen;
      if (this.inventoryScreen != inventoryScreen2 || this.clientPlayerEntity != instance.player) {
        this.update5();
        this.inventoryScreen = inventoryScreen2;
        this.clientPlayerEntity = instance.player;
      }

      PlayerScreenHandler playerScreenHandler = instance.player.playerScreenHandler;
      int count3 = -1;
      int intValue = -1;
      int value2 = this.delaySetting.intValue();
      ItemStack offHandStack = instance.player.getOffHandStack();
      if (!offHandStack.isOf(Items.TOTEM_OF_UNDYING)
          && (offHandStack.isEmpty() || (Boolean) this.forceTotemSetting.getValue())) {
        intValue = computeInt(playerScreenHandler, instance.player, -1);
        if (intValue >= 0) {
          count3 = 40;
        }
      }

      if (count3 < 0 && (Boolean) this.hotbarTotemSetting.getValue()) {
        int intValue2 = this.hotbarSlotSetting.intValue() - 1;
        if (!instance.player.getInventory().getStack(intValue2).isOf(Items.TOTEM_OF_UNDYING)) {
          intValue = computeInt(playerScreenHandler, instance.player, 36 + intValue2);
          if (intValue >= 0) {
            count3 = intValue2;
            value2 = this.hotbarDelaySetting.intValue();
          }
        }
      }

      if (count3 < 0) {
        this.update4();
      } else {
        if (this.count != count3) {
          this.count = count3;
          this.count2 = 0;
        }

        if (this.count2++ >= value2) {
          instance.interactionManager.clickSlot(
              playerScreenHandler.syncId, intValue, count3, SlotActionType.SWAP, instance.player);
          this.update4();
        }
      }
    }
  }

  public boolean test(MinecraftClient minecraftClient) {
    FakeLootDropModule.ensureInitialized();
    if (this.isEnabled()
        && minecraftClient.player != null
        && minecraftClient.world != null
        && minecraftClient.interactionManager != null
        && minecraftClient.getNetworkHandler() != null
        && minecraftClient.getNetworkHandler().isLoaded()
        && !minecraftClient.isPaused()
        && minecraftClient.player.isAlive()
        && !minecraftClient.player.isSpectator()
        && !minecraftClient.player.isCreative()
        && !minecraftClient.interactionManager.isFlyingLocked()
        && minecraftClient.interactionManager.getCurrentGameMode() != GameMode.CREATIVE
        && !minecraftClient.player.isUsingItem()
        && minecraftClient.currentScreen instanceof InventoryScreen inventoryScreen2
        && minecraftClient.player.currentScreenHandler == minecraftClient.player.playerScreenHandler
        && inventoryScreen2.getScreenHandler() == minecraftClient.player.playerScreenHandler
        && minecraftClient.player.playerScreenHandler.getCursorStack().isEmpty()) {
      ClientPlayerEntityAccessor clientPlayerEntityAccessor =
          (ClientPlayerEntityAccessor) (Object) (minecraftClient.player);
      if (minecraftClient.player.input.playerInput.equals(PlayerInput.DEFAULT)
          && clientPlayerEntityAccessor.getLastPlayerInput().equals(PlayerInput.DEFAULT)
          && !minecraftClient.player.isSprinting()
          && !clientPlayerEntityAccessor.getLastSprinting()
          && !minecraftClient.player.isSneaking()) {
        long handle = minecraftClient.getWindow().getHandle();
        return GLFW.glfwGetMouseButton(handle, 0) != 1 && GLFW.glfwGetMouseButton(handle, 1) != 1;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  public static int computeInt(
      PlayerScreenHandler playerScreenHandler,
      ClientPlayerEntity clientPlayerEntity2,
      int intValue) {
    FakeLootDropModule.ensureInitialized();

    for (int index = 9; index < 45; index++) {
      if (index != intValue
          && playerScreenHandler.getSlot(index).getStack().isOf(Items.TOTEM_OF_UNDYING)
          && playerScreenHandler.getSlot(index).canTakeItems(clientPlayerEntity2)) {
        return index;
      }
    }

    return -1;
  }

  public void update4() {
    this.count = -1;
    this.count2 = 0;
  }

  public void update5() {
    this.inventoryScreen = null;
    this.clientPlayerEntity = null;
    this.update4();
  }
}
