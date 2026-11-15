package bunnythekenzo.client;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import bunnythekenzo.client.module.ModuleCategory;
import bunnythekenzo.client.setting.ChoiceSettingView;
import bunnythekenzo.client.setting.NumberSettingView;
import bunnythekenzo.client.setting.SettingEntry;
import bunnythekenzo.client.setting.ToggleSettingView;
import bunnythekenzo.client.ui.ScreenSupport2;

public final class PearlEscapeAutomation {

  private static final long MIN_LANDING_WAIT_NANOS = 1000000000L;

  public static final float amount = 12.0F;
  public static final ChoiceSettingView triggerControl =
      new ChoiceSettingView(
          "Trigger",
          "Keybind runs once on demand; Random Y also triggers while falling",
          0,
          new String[] {"Keybind", "Random Y"});
  public static final ChoiceSettingView methodControl =
      new ChoiceSettingView(
          "Method",
          "Action to run when you press the bound key or cross the trigger level",
          0,
          new String[] {"Relog", "Render Distance", "Pearl + RTP"});
  public static final NumberSettingView lowestYControl =
      (NumberSettingView)
          new NumberSettingView(
                  "Lowest Y",
                  "Lowest possible randomized trigger level",
                  -16.0F,
                  -64.0F,
                  0.0F,
                  1.0F,
                  "")
              .visibleWhen(
                  () -> {
                    return triggerControl.getSelectedValue().equals("Random Y");
                  });
  public static final NumberSettingView highestYControl =
      (NumberSettingView)
          new NumberSettingView(
                  "Highest Y",
                  "Highest possible randomized trigger level",
                  0.0F,
                  -64.0F,
                  0.0F,
                  1.0F,
                  "")
              .visibleWhen(
                  () -> {
                    return triggerControl.getSelectedValue().equals("Random Y");
                  });
  public static final ToggleSettingView chatFeedbackControl =
      new ToggleSettingView(
          "Chat Feedback", "Shows the selected Y level and action status in chat", true);
  public static final GuiModuleEntry guiModuleEntry =
      new GuiModuleEntry(
          "Relog Method",
          "Bind a key and enable to run the selected recovery method once per press",
          new CollectionData[] {
            new CollectionData(
                "General",
                new SettingEntry[] {
                  triggerControl, methodControl, lowestYControl, highestYControl
                }),
            new CollectionData("Feedback", new SettingEntry[] {chatFeedbackControl})
          });
  public static PearlEscapeState pearlEscapeState = PearlEscapeState.IDLE;
  public static int count6;
  public static long timestamp3;
  public static boolean enabled;
  public static boolean enabled2;
  public static boolean enabled3;
  public static int count7;
  public static int count8 = -1;
  public static ServerInfo serverInfo;
  public static ServerAddress serverAddress;
  public static Screen screen;
  public static Screen screen2;
  public static Screen screen3;
  public static boolean enabled4;
  public static Hand hand;
  public static int count9 = -1;
  public static float amount2;
  public static boolean enabled5;
  public static double value;
  public static double value2;
  public static double value3;
  public static final Set set = new HashSet();
  public static UUID uUID;
  public static ClientWorld clientWorld;
  public static ClientPlayerEntity clientPlayerEntity;
  public static RegistryKey registryKey;
  public static RegistryKey registryKey2;
  public static int count10;
  public static int count11;
  public static long timestamp4;
  public static boolean enabled6;
  public static boolean enabled7;
  public static ClientInitializer[] clientInitializerArray;

  public static GuiModuleEntry getGuiModuleEntry() {
    return guiModuleEntry;
  }

  public static void init() {
    ModuleCategory.ensureInitialized();
    if (!enabled7) {
      enabled7 = true;
      PlayerModelHooks.register(
          PearlEscapeAutomation::apply,
          PearlEscapeAutomation::apply,
          entity -> {
            ModuleCategory.ensureInitialized();
            if (test3() && entity.getUuid().equals(uUID)) {
              timestamp4 = System.nanoTime();
            }
          },
          PearlEscapeAutomation::apply12,
          PearlEscapeAutomation::apply14,
          PearlEscapeAutomation::update2);
    }
  }

  public static boolean test(int intValue) {
    ModuleCategory.ensureInitialized();
    if (guiModuleEntry.test()
        && guiModuleEntry.test3()
        && guiModuleEntry.computeInt() == intValue) {
      MinecraftClient instance = MinecraftClient.getInstance();
      if (instance.currentScreen == null
          && instance.player != null
          && instance.world != null
          && instance.interactionManager != null
          && instance.player.isAlive()) {
        if (pearlEscapeState == PearlEscapeState.IDLE) {
          count7 = (int) Math.floor(instance.player.getY());
          apply3(instance);
        }

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
    if (!guiModuleEntry.test()) {
      if (enabled || pearlEscapeState != PearlEscapeState.IDLE) {
        apply15(minecraftClient2);
      }

      enabled = false;
      enabled3 = false;
    } else {
      boolean flag = !enabled;
      enabled = true;
      boolean enabled22 = triggerControl.getSelectedValue().equals("Random Y");
      boolean flag2 = enabled22 != enabled2;
      enabled2 = enabled22;
      if (!enabled22 || flag2) {
        enabled3 = false;
      }

      if (pearlEscapeState != PearlEscapeState.RELOG_WAIT_CONNECT
          && pearlEscapeState != PearlEscapeState.RELOG_WAIT_JOIN) {
        ClientPlayerEntity player = minecraftClient2.player;
        if (player != null
            && minecraftClient2.world != null
            && minecraftClient2.interactionManager != null) {
          if (!player.isAlive()) {
            if (pearlEscapeState != PearlEscapeState.IDLE) {
              apply2(minecraftClient2, "Recovery cancelled because the player died.");
            }

            enabled3 = false;
          } else {
            if ((flag || flag2)
                && pearlEscapeState == PearlEscapeState.IDLE
                && enabled22
                && Math.floor(player.getY()) > computeInt2()) {
              apply(player);
            }

            if (pearlEscapeState != PearlEscapeState.IDLE) {
              if (pearlEscapeState != PearlEscapeState.PEARL_PREPARE
                      && pearlEscapeState != PearlEscapeState.PEARL_AIMED
                  || minecraftClient2.world == clientWorld && player == clientPlayerEntity) {
                if ((pearlEscapeState == PearlEscapeState.PEARL_PREPARE
                        || pearlEscapeState == PearlEscapeState.PEARL_AIMED)
                    && minecraftClient2.currentScreen != null) {
                  apply2(minecraftClient2, "Pearl throw cancelled because a menu was opened.");
                } else if (++count6 > 600) {
                  apply2(minecraftClient2, "The selected recovery method timed out.");
                } else {
                  apply2(minecraftClient2);
                }
              } else {
                apply2(
                    minecraftClient2, "Pearl + RTP cancelled because the player changed worlds.");
              }
            } else if (enabled22 && minecraftClient2.currentScreen == null) {
              if (!enabled3) {
                if (Math.floor(player.getY()) > computeInt2()) {
                  apply(player);
                }
              } else {
                if (Math.floor(player.getY()) <= count7) {
                  apply3(minecraftClient2);
                }
              }
            }
          }
        }
      } else {
        apply7(minecraftClient2);
      }
    }
  }

  public static void apply2(MinecraftClient minecraftClient) {
    ModuleCategory.ensureInitialized();
    switch (pearlEscapeState.ordinal()) {
      case 2:
        {
          PearlEscapeAutomation.apply6(minecraftClient);
          if (System.nanoTime() - timestamp3 < 1000000000L) {
            return;
          }
          PearlEscapeAutomation.apply(PearlEscapeState.RELOG_WAIT_CONNECT);
          enabled4 = true;
          try {
            minecraftClient.disconnect(ClientWorld.QUITTING_MULTIPLAYER_TEXT);
            break;
          } finally {
            timestamp3 = System.nanoTime();
            enabled4 = false;
          }
        }
      case 1:
        {
          int n;
          if (count6 < 20 || System.nanoTime() - timestamp3 < 1000000000L) {
            return;
          }
          SimpleOption<Integer> simpleOption = minecraftClient.options.getViewDistance();
          Object callbacks = simpleOption.getCallbacks();
          if (callbacks instanceof SimpleOption.ValidatingIntSliderCallbacks) {
            SimpleOption.ValidatingIntSliderCallbacks validatingIntSliderCallbacks =
                (SimpleOption.ValidatingIntSliderCallbacks) callbacks;
            n = validatingIntSliderCallbacks.maxInclusive();
          } else {
            n = 32;
          }
          int n2 = n;
          simpleOption.setValue(n2);
          minecraftClient.options.write();
          PearlEscapeAutomation.apply(
              minecraftClient,
              "Render distance changed from minimum to "
                  + String.valueOf(simpleOption.getValue())
                  + " chunks.");
          break;
        }
      case 5:
        {
          PearlEscapeAutomation.apply9(minecraftClient);
          break;
        }
      case 6:
        {
          PearlEscapeAutomation.apply10(minecraftClient);
          break;
        }
      case 7:
      case 8:
      case 9:
        {
          PearlEscapeAutomation.apply11(minecraftClient);
          break;
        }
    }
  }

  public static void apply3(MinecraftClient minecraftClient2) {
    ModuleCategory.ensureInitialized();
    if (pearlEscapeState == PearlEscapeState.IDLE) {
      enabled3 = false;
      ClientInitializer.logger.info(
          "Relog Method starting: method={}, trigger={}, y={}",
          methodControl.getSelectedValue(),
          triggerControl.getSelectedValue(),
          minecraftClient2.player.getY());
      switch (methodControl.getSelectedValue()) {
        case "Relog":
          apply5(minecraftClient2);
          break;
        case "Render Distance":
          apply4(minecraftClient2);
          break;
        case "Pearl + RTP":
          apply8(minecraftClient2);
          break;
        default:
          apply2(minecraftClient2, "Select a recovery method first.");
      }
    }
  }

  public static void apply4(MinecraftClient minecraftClient2) {
    count8 = minecraftClient2.options.getViewDistance().getValue();
    minecraftClient2.options.getViewDistance().setValue(2);
    minecraftClient2.options.sendClientSettings();
    apply(PearlEscapeState.RENDER_DISTANCE_LOW);
    apply(
        minecraftClient2.player,
        "Set render distance to minimum at Y " + count7 + ".",
        Formatting.LIGHT_PURPLE);
  }

  public static void apply5(MinecraftClient minecraftClient2) {
    ModuleCategory.ensureInitialized();

    serverInfo = minecraftClient2.getCurrentServerEntry();
    if (serverInfo != null && serverInfo.address != null && !serverInfo.address.isBlank()) {
      serverAddress = ServerAddress.parse(serverInfo.address);
      screen = new MultiplayerScreen(new TitleScreen());
      apply(
          minecraftClient2.player,
          "Pausing actions for one second before relog at Y " + count7 + "…",
          Formatting.LIGHT_PURPLE);
      minecraftClient2.interactionManager.cancelBlockBreaking();
      if (minecraftClient2.player.isUsingItem()) {
        minecraftClient2.interactionManager.stopUsingItem(minecraftClient2.player);
      }

      screen3 = minecraftClient2.currentScreen;
      screen2 = new GameMenuScreen(true);
      apply(PearlEscapeState.RELOG_PAUSE);
      apply6(minecraftClient2);
      minecraftClient2.setScreen(screen2);
    } else {
      apply2(minecraftClient2, "Relog is only available on a multiplayer server.");
    }
  }

  public static boolean test() {
    ModuleCategory.ensureInitialized();
    return guiModuleEntry.test()
        && (pearlEscapeState == PearlEscapeState.RELOG_PAUSE
            || pearlEscapeState == PearlEscapeState.RELOG_WAIT_CONNECT
            || pearlEscapeState == PearlEscapeState.RELOG_WAIT_JOIN);
  }

  public static boolean test2() {
    ModuleCategory.ensureInitialized();
    return test()
        || guiModuleEntry.test()
            && (pearlEscapeState == PearlEscapeState.PEARL_PREPARE
                || pearlEscapeState == PearlEscapeState.PEARL_AIMED
                || test3() && enabled5);
  }

  public static void apply6(MinecraftClient minecraftClient2) {
    ModuleCategory.ensureInitialized();
    if (test()) {
      KeyBinding.unpressAll();
      if (minecraftClient2.player != null) {
        PlayerInputReset.apply(minecraftClient2.player.input);
      }
    }
  }

  public static void apply7(MinecraftClient minecraftClient2) {
    ModuleCategory.ensureInitialized();
    if (!enabled4) {
      if (++count6 > 600) {
        apply2(minecraftClient2, "Automatic relog timed out.");
      } else if (pearlEscapeState == PearlEscapeState.RELOG_WAIT_CONNECT
          && System.nanoTime() - timestamp3 >= 1000000000L) {
        if (serverInfo != null && serverAddress != null && screen != null) {
          apply(PearlEscapeState.RELOG_WAIT_JOIN);
          ScreenSupport2.apply(screen, minecraftClient2, serverAddress, serverInfo);
        } else {
          apply2(minecraftClient2, "The previous server is no longer available.");
        }
      }
    }
  }

  public static void apply8(MinecraftClient minecraftClient2) {
    ClientPlayerEntity player = minecraftClient2.player;
    ModuleCategory.ensureInitialized();

    clientPlayerEntity = player;
    clientWorld = minecraftClient2.world;
    PlayerInventory inventory = player.getInventory();
    count9 = inventory.getSelectedSlot();
    hand = null;
    if (player.getMainHandStack().isOf(Items.ENDER_PEARL)) {
      hand = Hand.MAIN_HAND;
    } else if (player.getOffHandStack().isOf(Items.ENDER_PEARL)) {
      hand = Hand.OFF_HAND;
    } else {
      for (int index = 0; index < PlayerInventory.getHotbarSize(); index++) {
        if (inventory.getStack(index).isOf(Items.ENDER_PEARL)) {
          inventory.setSelectedSlot(index);
          hand = Hand.MAIN_HAND;
          break;
        }
      }
    }

    if (hand == null) {
      apply2(minecraftClient2, "Put an ender pearl in your hotbar or offhand first.");
    } else if (player.getItemCooldownManager().isCoolingDown(player.getStackInHand(hand))) {
      apply2(minecraftClient2, "The ender pearl is still on cooldown.");
    } else {
      amount2 = player.getPitch();
      enabled5 = true;
      value = player.getX();
      value2 = player.getY();
      value3 = player.getZ();
      set.clear();

      for (Entity entity : minecraftClient2.world.getEntities()) {
        if (entity instanceof EnderPearlEntity) {
          set.add(entity.getUuid());
        }
      }

      uUID = null;
      clientWorld = minecraftClient2.world;
      registryKey = minecraftClient2.world.getRegistryKey();
      timestamp4 = 0L;
      enabled6 = false;
      minecraftClient2.interactionManager.cancelBlockBreaking();
      if (player.isUsingItem()) {
        minecraftClient2.interactionManager.stopUsingItem(player);
      }

      apply(PearlEscapeState.PEARL_PREPARE);
      apply(player, "Preparing Pearl + RTP at Y " + count7 + ".", Formatting.LIGHT_PURPLE);
    }
  }

  public static void apply9(MinecraftClient minecraftClient2) {
    ModuleCategory.ensureInitialized();
    if (count6 >= 1) {
      ClientPlayerEntity player = minecraftClient2.player;
      if (!player.getStackInHand(hand).isOf(Items.ENDER_PEARL)) {
        apply2(minecraftClient2, "The selected pearl is no longer available.");
      } else {
        if (test(player, computeFloat())) {
          apply(PearlEscapeState.PEARL_AIMED);
        }
      }
    }
  }

  public static void apply10(MinecraftClient minecraftClient2) {
    ModuleCategory.ensureInitialized();
    if (count6 >= 1) {
      ClientPlayerEntity player = minecraftClient2.player;
      if (!player.getStackInHand(hand).isOf(Items.ENDER_PEARL)) {
        apply2(minecraftClient2, "The selected pearl is no longer available.");
      } else if (player.getItemCooldownManager().isCoolingDown(player.getStackInHand(hand))) {
        apply2(minecraftClient2, "The ender pearl is still on cooldown.");
      } else if (player.getPitch() != computeFloat()) {
        apply(PearlEscapeState.PEARL_PREPARE);
      } else {
        value = player.getX();
        value2 = player.getY();
        value3 = player.getZ();
        apply(PearlEscapeState.PEARL_WAIT_RTP);
        ActionResult actionResult = minecraftClient2.interactionManager.interactItem(player, hand);
        if (!actionResult.isAccepted()) {
          apply2(minecraftClient2, "Minecraft did not accept the pearl throw.");
        } else {
          player.networkHandler.sendChatCommand("rtp");
          player.swingHand(hand);
          apply(
              player,
              "Pearl thrown upwards; /rtp sent. Waiting for the new location and chunks…",
              Formatting.LIGHT_PURPLE);
        }
      }
    }
  }

  public static void apply11(MinecraftClient minecraftClient2) {
    ModuleCategory.ensureInitialized();

    apply13(minecraftClient2);
    apply12(minecraftClient2);
    if (pearlEscapeState == PearlEscapeState.PEARL_WAIT_LANDING) {
      if (enabled6 && !enabled5) {
        apply(minecraftClient2, "Pearl landed after RTP. Relock complete.");
      }
    } else if (pearlEscapeState != PearlEscapeState.PEARL_WAIT_CHUNKS) {
      if (timestamp4 != 0L
          && test(minecraftClient2)
          && System.nanoTime() - timestamp4 > 3000000000L) {
        apply2(minecraftClient2, "The pearl disappeared before RTP moved you to another location.");
      } else if (uUID == null) {
        for (Entity entity : minecraftClient2.world.getEntities()) {
          apply(entity);
          if (uUID != null) {
            return;
          }
        }

        if (count6 > 60 && test(minecraftClient2)) {
          apply2(minecraftClient2, "Could not confirm that the pearl spawned.");
        }
      }
    }
  }

  public static void update() {
    ModuleCategory.ensureInitialized();
    if (test3()) {
      MinecraftClient instance = MinecraftClient.getInstance();
      if (instance.player != null && instance.world != null) {
        boolean flag = test(instance);
        if (pearlEscapeState == PearlEscapeState.PEARL_WAIT_RTP && !flag) {
          registryKey2 = instance.world.getRegistryKey();
          count10 = instance.player.getChunkPos().x;
          count11 = instance.player.getChunkPos().z;
          apply(PearlEscapeState.PEARL_WAIT_CHUNKS);
          apply12(instance);
        } else if (pearlEscapeState == PearlEscapeState.PEARL_WAIT_LANDING && flag) {
          enabled6 = true;
        } else if (pearlEscapeState == PearlEscapeState.PEARL_WAIT_CHUNKS && flag) {
          apply2(instance, "The pearl returned before the RTP destination chunks loaded.");
        }
      }
    }
  }

  public static void apply12(MinecraftClient minecraftClient2) {
    ModuleCategory.ensureInitialized();
    if (pearlEscapeState == PearlEscapeState.PEARL_WAIT_CHUNKS
        && minecraftClient2.world != null
        && minecraftClient2.player != null
        && minecraftClient2.world.getRegistryKey().equals(registryKey2)
        && !test(minecraftClient2)
        && minecraftClient2.world.getChunkManager().isChunkLoaded(count10, count11)) {
      apply(PearlEscapeState.PEARL_WAIT_LANDING);
      apply(
          minecraftClient2.player,
          "RTP destination loaded. Waiting for the pearl to land…",
          Formatting.LIGHT_PURPLE);
    }
  }

  public static boolean test(MinecraftClient minecraftClient2) {
    ModuleCategory.ensureInitialized();

    double doubleValue = minecraftClient2.player.getX() - value;
    double doubleValue2 = minecraftClient2.player.getZ() - value3;
    return minecraftClient2.world.getRegistryKey().equals(registryKey)
        && doubleValue * doubleValue + doubleValue2 * doubleValue2 <= computeDouble();
  }

  public static float computeFloat() {
    return Float.intBitsToFloat(-1028390912);
  }

  public static double computeDouble() {
    int intValue = Integer.parseInt("20", 16);
    return (double) intValue * intValue;
  }

  public static boolean test3() {
    ModuleCategory.ensureInitialized();
    return pearlEscapeState == PearlEscapeState.PEARL_WAIT_RTP
        || pearlEscapeState == PearlEscapeState.PEARL_WAIT_CHUNKS
        || pearlEscapeState == PearlEscapeState.PEARL_WAIT_LANDING;
  }

  public static boolean test(ClientPlayerEntity clientPlayerEntity2, float floatValue) {
    ModuleCategory.ensureInitialized();

    float floatValue2 = Math.abs(floatValue - clientPlayerEntity2.getPitch());
    clientPlayerEntity2.setPitch(
        MathHelper.stepTowards(
            clientPlayerEntity2.getPitch(),
            floatValue,
            MathHelper.clamp(floatValue2 * 0.35F, 1.0F, 12.0F)));
    return clientPlayerEntity2.getPitch() == floatValue;
  }

  public static void apply13(MinecraftClient minecraftClient2) {
    ModuleCategory.ensureInitialized();

    ClientPlayerEntity player = minecraftClient2.player;
    if (player != clientPlayerEntity) {
      enabled5 = false;
      count9 = -1;
    } else {
      apply2(player);
      if (pearlEscapeState == PearlEscapeState.PEARL_WAIT_LANDING) {
        if (enabled5 && test(player, amount2)) {
          enabled5 = false;
        }
      }
    }
  }

  public static void apply(Entity entity) {
    ModuleCategory.ensureInitialized();
    if (test3()
        && uUID == null
        && entity.getEntityWorld() == clientWorld
        && entity instanceof EnderPearlEntity enderPearlEntity
        && !set.contains(enderPearlEntity.getUuid())) {
      ClientPlayerEntity player = MinecraftClient.getInstance().player;
      if (player != null) {
        Entity owner = enderPearlEntity.getOwner();
        if (owner != null
            ? owner.getUuid().equals(player.getUuid())
            : !(enderPearlEntity.squaredDistanceTo(value, value2, value3) > 25.0)) {
          uUID = enderPearlEntity.getUuid();
        }
      }
    }
  }

  public static void apply(ClientPlayerEntity clientPlayerEntity2) {
    int intValue = computeInt();
    int intValue2 = computeInt2();
    count7 = ThreadLocalRandom.current().nextInt(intValue, intValue2 + 1);
    enabled3 = true;
    int intValue3 = intValue2;
    int intValue4 = intValue;
    int intValue5 = count7;
    apply(
        clientPlayerEntity2,
        "Armed at randomized Y " + intValue5 + " (range " + intValue4 + " to " + intValue3 + ").",
        Formatting.GRAY);
  }

  public static int computeInt() {
    return Math.min(Math.round(lowestYControl.getValue()), Math.round(highestYControl.getValue()));
  }

  public static int computeInt2() {
    return Math.max(Math.round(lowestYControl.getValue()), Math.round(highestYControl.getValue()));
  }

  public static void apply14(MinecraftClient minecraftClient2) {
    ModuleCategory.ensureInitialized();
    if (pearlEscapeState == PearlEscapeState.RELOG_WAIT_JOIN) {
      apply(minecraftClient2, "Relog complete.");
    } else {
      enabled3 = false;
      enabled = false;
    }
  }

  public static void update2() {
    ModuleCategory.ensureInitialized();
    if (pearlEscapeState != PearlEscapeState.RELOG_WAIT_CONNECT
        && pearlEscapeState != PearlEscapeState.RELOG_WAIT_JOIN) {
      if (pearlEscapeState != PearlEscapeState.IDLE) {
        ClientInitializer.logger.warn(
            "Connection closed during Relog Method stage {}", pearlEscapeState);
      }

      apply15(MinecraftClient.getInstance());
      enabled3 = false;
    }
  }

  public static void apply(MinecraftClient minecraftClient2, String text) {
    apply(minecraftClient2.player, text, Formatting.GREEN);
    update3();
    enabled3 = false;
  }

  public static void apply2(MinecraftClient minecraftClient2, String text) {
    ClientInitializer.logger.warn("Relog Method failed: {}", text);
    apply(minecraftClient2.player, text, Formatting.RED);
    apply15(minecraftClient2);
    enabled3 = false;
  }

  public static void apply15(MinecraftClient minecraftClient2) {
    ModuleCategory.ensureInitialized();
    if (screen2 != null && minecraftClient2.currentScreen == screen2) {
      minecraftClient2.setScreen(screen3);
    }

    if (pearlEscapeState == PearlEscapeState.RENDER_DISTANCE_LOW) {
      minecraftClient2.options.getViewDistance().setValue(count8);
      minecraftClient2.options.write();
    }

    apply16(minecraftClient2);
    update3();
  }

  public static void apply16(MinecraftClient minecraftClient2) {
    ModuleCategory.ensureInitialized();

    ClientPlayerEntity player = minecraftClient2.player;
    if (player != null && player == clientPlayerEntity) {
      if (enabled5) {
        player.setPitch(amount2);
        enabled5 = false;
      }

      apply2(player);
    }
  }

  public static void apply2(ClientPlayerEntity clientPlayerEntity2) {
    ModuleCategory.ensureInitialized();
    if (count9 >= 0 && clientPlayerEntity2.getInventory().getSelectedSlot() != count9) {
      clientPlayerEntity2.getInventory().setSelectedSlot(count9);
    }

    count9 = -1;
  }

  public static void update3() {
    pearlEscapeState = PearlEscapeState.IDLE;
    count6 = 0;
    timestamp3 = 0L;
    count8 = -1;
    serverInfo = null;
    serverAddress = null;
    screen = null;
    screen2 = null;
    screen3 = null;
    enabled4 = false;
    hand = null;
    count9 = -1;
    enabled5 = false;
    set.clear();
    uUID = null;
    clientWorld = null;
    clientPlayerEntity = null;
    registryKey = null;
    registryKey2 = null;
    count10 = 0;
    count11 = 0;
    timestamp4 = 0L;
    enabled6 = false;
  }

  public static void apply(PearlEscapeState pearlEscapeState2) {
    pearlEscapeState = pearlEscapeState2;
    count6 = 0;
    timestamp3 = System.nanoTime();
  }

  public static void apply(
      ClientPlayerEntity clientPlayerEntity2, String text, Formatting formatting) {
    if (clientPlayerEntity2 != null && chatFeedbackControl.getValue()) {
      clientPlayerEntity2.sendMessage(
          Text.literal("[Relog Method] ")
              .formatted(Formatting.LIGHT_PURPLE)
              .append(Text.literal(text).formatted(formatting)),
          false);
    }
  }

  static {
    ClientInitializer[] clientInitializerArray2 = new ClientInitializer[3];
    setClientInitializerArray(clientInitializerArray2);
  }

  public static void setClientInitializerArray(ClientInitializer[] clientInitializerArray2) {
    clientInitializerArray = clientInitializerArray2;
  }

  public static ClientInitializer[] getClientInitializerArray() {
    return clientInitializerArray;
  }
}
