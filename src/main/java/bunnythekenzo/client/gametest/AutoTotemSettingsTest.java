package bunnythekenzo.client.gametest;

import com.google.gson.JsonObject;
import io.netty.channel.Channel;
import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.PlayerInput;
import net.minecraft.world.GameMode;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.GuiModuleEntry;
import bunnythekenzo.client.JsonSupport2;
import bunnythekenzo.client.module.AutoTotemModule;
import bunnythekenzo.client.module.Module;
import bunnythekenzo.client.ui.PanelScreen;

public final class AutoTotemSettingsTest {
  public static void run(
      ClientGameTestContext clientGameTestContext,
      TestSingleplayerContext testSingleplayerContext) {
    JsonObject onClient =
        clientGameTestContext.computeOnClient(
            minecraftClient -> {
              return ClientInitializer.getClientConfig().getJsonObject();
            });
    CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();
    HoppinStandingBeforeTestSupport21 hoppinStandingBeforeTestSupport21 =
        new HoppinStandingBeforeTestSupport21(copyOnWriteArrayList);
    Channel[] channelArray = new Channel[1];

    try {
      clientGameTestContext.runOnClient(
          minecraftClient2 -> {
            ProtectionPerformanceTest.ensureInitialized();

            JsonSupport2.getList();

            for (Module module :
                (java.lang.Iterable<Module>)
                    (java.lang.Iterable<?>) (ClientInitializer.getModuleManager().getList())) {
              module.setEnabled(false);
            }

            checkSettings();
            checkMenuBehavior(minecraftClient2);
            channelArray[0] = getChannel(minecraftClient2, hoppinStandingBeforeTestSupport21);
          });
      testSingleplayerContext.getServer().runCommand("gamemode survival @a");
      testSingleplayerContext
          .getServer()
          .runOnServer(
              minecraftServer -> {
                ProtectionPerformanceTest.ensureInitialized();

                ServerPlayerEntity first =
                    minecraftServer.getPlayerManager().getPlayerList().getFirst();
                first.getInventory().clear();

                for (int index = 0; index < 36; index++) {
                  first.getInventory().setStack(index, new ItemStack(Items.STONE, 64));
                }

                first.getInventory().setStack(9, new ItemStack(Items.TOTEM_OF_UNDYING));
                first.getInventory().setStack(10, new ItemStack(Items.TOTEM_OF_UNDYING));
                first.getInventory().setStack(40, new ItemStack(Items.SHIELD));
                first.playerScreenHandler.syncState();
              });
      clientGameTestContext.waitTicks(20);
      clientGameTestContext.runOnClient(
          minecraftClient3 -> {
            AutoTotemModule autoTotemModule = ClientInitializer.getModuleManager().autoTotemModule;
            autoTotemModule.delaySetting.setNumber(Double.valueOf(2.0));
            autoTotemModule.hotbarDelaySetting.setNumber(Double.valueOf(5.0));
            autoTotemModule.forceTotemSetting.setValue(true);
            autoTotemModule.hotbarTotemSetting.setValue(true);
            autoTotemModule.hotbarSlotSetting.setNumber(Double.valueOf(9.0));
            autoTotemModule.setEnabled(true);
            minecraftClient3.setScreen(null);
          });
      clientGameTestContext.waitTicks(10);
      clientGameTestContext.runOnClient(
          minecraftClient4 -> {
            apply(copyOnWriteArrayList.isEmpty(), "closed inventory sends no clicks");
            apply(
                minecraftClient4.player.getOffHandStack().isOf(Items.SHIELD),
                "closed inventory keeps shield");
            minecraftClient4.setScreen(new InventoryScreen(minecraftClient4.player));
          });
      clientGameTestContext.waitTicks(25);
      clientGameTestContext.runOnClient(
          minecraftClient5 -> {
            apply(
                minecraftClient5.player.getOffHandStack().isOf(Items.TOTEM_OF_UNDYING),
                "client sees offhand refill");
            ProtectionPerformanceTest.ensureInitialized();

            apply(
                minecraftClient5.player.getInventory().getStack(8).isOf(Items.TOTEM_OF_UNDYING),
                "client sees slot 9 refill");
            apply(
                minecraftClient5.player.playerScreenHandler.getCursorStack().isEmpty(),
                "client cursor stays empty");
            apply(
                copyOnWriteArrayList.size() == 2,
                "exactly two real container clicks, no repeated full-slot swaps: "
                    + copyOnWriteArrayList.size());
            ClickSlotC2SPacket clickSlotC2SPacket =
                (ClickSlotC2SPacket) copyOnWriteArrayList.get(0);
            ClickSlotC2SPacket clickSlotC2SPacket2 =
                (ClickSlotC2SPacket) copyOnWriteArrayList.get(1);
            apply(
                clickSlotC2SPacket.actionType() == SlotActionType.SWAP
                    && clickSlotC2SPacket.button() == 40
                    && clickSlotC2SPacket.slot() == 9,
                "offhand uses vanilla container F swap first");
            apply(
                clickSlotC2SPacket2.actionType() == SlotActionType.SWAP
                    && clickSlotC2SPacket2.button() == 8
                    && clickSlotC2SPacket2.slot() == 10,
                "hotbar uses vanilla number-key swap second");
            apply(
                clickSlotC2SPacket.syncId() == 0 && clickSlotC2SPacket2.syncId() == 0,
                "player menu IDs");
          });
      testSingleplayerContext
          .getServer()
          .runOnServer(
              minecraftServer2 -> {
                ProtectionPerformanceTest.ensureInitialized();

                ServerPlayerEntity first2 =
                    minecraftServer2.getPlayerManager().getPlayerList().getFirst();
                apply(
                    first2.getOffHandStack().isOf(Items.TOTEM_OF_UNDYING),
                    "server accepted offhand refill");
                apply(
                    first2.getInventory().getStack(8).isOf(Items.TOTEM_OF_UNDYING),
                    "server accepted hotbar refill");
                apply(
                    first2.getInventory().getStack(9).isOf(Items.SHIELD),
                    "server preserved shield in full inventory");
                apply(
                    first2.getInventory().getStack(10).isOf(Items.STONE)
                        && first2.getInventory().getStack(10).getCount() == 64,
                    "server preserved full hotbar stack");
                apply(first2.playerScreenHandler.getCursorStack().isEmpty(), "server cursor empty");
              });
      testSingleplayerContext
          .getServer()
          .runOnServer(
              minecraftServer3 -> {
                ServerPlayerEntity first3 =
                    minecraftServer3.getPlayerManager().getPlayerList().getFirst();
                first3.getInventory().setStack(40, ItemStack.EMPTY);
                first3.getInventory().setStack(11, new ItemStack(Items.TOTEM_OF_UNDYING));
                first3.playerScreenHandler.sendContentUpdates();
              });
      clientGameTestContext.waitTicks(20);
      testSingleplayerContext
          .getServer()
          .runOnServer(
              minecraftServer4 -> {
                apply(
                    minecraftServer4
                        .getPlayerManager()
                        .getPlayerList()
                        .getFirst()
                        .getOffHandStack()
                        .isOf(Items.TOTEM_OF_UNDYING),
                    "server-synced consumption triggers refill");
              });
      clientGameTestContext.runOnClient(
          minecraftClient6 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(copyOnWriteArrayList.size() == 3, "one additional refill after consumption");
          });
      checkOpeningPacketOrder(
          clientGameTestContext, testSingleplayerContext, hoppinStandingBeforeTestSupport21);
      clientGameTestContext.runOnClient(
          minecraftClient7 -> {
            minecraftClient7.currentScreen.close();
            PanelScreen panelScreen = new PanelScreen();
            minecraftClient7.setScreen(panelScreen);
            apply(panelScreen.test("Auto Totem"), "Auto Totem settings reachable in combat menu");
          });
      clientGameTestContext.waitTicks(20);
      clientGameTestContext.takeScreenshot("auto-totem-settings");
      ClientInitializer.logger.info(
          "Auto Totem tests passed: settings, delays, guards, vanilla swaps, server synchronization"
              + " and opening while walking/sprinting/sneaking");
    } finally {
      clientGameTestContext.runOnClient(
          minecraftClient8 -> {
            ProtectionPerformanceTest.ensureInitialized();

            ClientInitializer.getModuleManager().autoTotemModule.setEnabled(false);
            if (channelArray[0] != null) {
              channelArray[0].pipeline().remove("auto-totem-test");
            }

            minecraftClient8.setScreen(null);
            ClientInitializer.getClientConfig().apply(onClient);
          });
    }
  }

  public static void checkOpeningPacketOrder(
      ClientGameTestContext clientGameTestContext,
      TestSingleplayerContext testSingleplayerContext,
      HoppinStandingBeforeTestSupport21 hoppinStandingBeforeTestSupport21) {
    ProtectionPerformanceTest.ensureInitialized();

    for (String text : List.of("walking", "sprinting", "sneaking")) {
      clientGameTestContext.runOnClient(
          minecraftClient -> {
            ClientInitializer.getModuleManager().autoTotemModule.setEnabled(false);
            minecraftClient.currentScreen.close();
          });
      testSingleplayerContext
          .getServer()
          .runOnServer(
              minecraftServer -> {
                ServerPlayerEntity first =
                    minecraftServer.getPlayerManager().getPlayerList().getFirst();
                first.getInventory().clear();
                first.getInventory().setStack(9, new ItemStack(Items.TOTEM_OF_UNDYING));
                first.playerScreenHandler.syncState();
              });
      clientGameTestContext.waitTicks(5);
      clientGameTestContext.runOnClient(
          minecraftClient2 -> {
            minecraftClient2.options.forwardKey.setPressed(true);
            minecraftClient2.options.sprintKey.setPressed(text.equals("sprinting"));
            minecraftClient2.options.sneakKey.setPressed(text.equals("sneaking"));
          });
      clientGameTestContext.waitTicks(5);
      clientGameTestContext.runOnClient(
          minecraftClient3 -> {
            ProtectionPerformanceTest.ensureInitialized();

            apply(
                hoppinStandingBeforeTestSupport21.playerInput.forward(),
                "vanilla transmitted forward input before " + text + " test");
            if (text.equals("sprinting")) {
              apply(
                  hoppinStandingBeforeTestSupport21.enabled, "vanilla transmitted START_SPRINTING");
            }

            if (text.equals("sneaking")) {
              apply(
                  hoppinStandingBeforeTestSupport21.playerInput.sneak(),
                  "vanilla transmitted sneaking input");
            }

            AutoTotemModule autoTotemModule = ClientInitializer.getModuleManager().autoTotemModule;
            autoTotemModule.delaySetting.setNumber(Double.valueOf(0.0));
            autoTotemModule.hotbarDelaySetting.setNumber(Double.valueOf(0.0));
            autoTotemModule.hotbarTotemSetting.setValue(false);
            autoTotemModule.setEnabled(true);
            minecraftClient3.setScreen(new InventoryScreen(minecraftClient3.player));
          });
      clientGameTestContext.waitTicks(8);
      clientGameTestContext.runOnClient(
          minecraftClient4 -> {
            apply(
                minecraftClient4.player.getOffHandStack().isOf(Items.TOTEM_OF_UNDYING),
                text + " opening refills promptly at zero delay");
            apply(
                hoppinStandingBeforeTestSupport21.list2.isEmpty(),
                "inventory packets follow vanilla input/sprint release: "
                    + hoppinStandingBeforeTestSupport21.list2);
          });
      testSingleplayerContext
          .getServer()
          .runOnServer(
              minecraftServer2 -> {
                apply(
                    minecraftServer2
                        .getPlayerManager()
                        .getPlayerList()
                        .getFirst()
                        .getOffHandStack()
                        .isOf(Items.TOTEM_OF_UNDYING),
                    "server accepted refill after " + text);
              });
    }
  }

  public static void checkSettings() {
    AutoTotemModule autoTotemModule = ClientInitializer.getModuleManager().autoTotemModule;
    ProtectionPerformanceTest.ensureInitialized();

    apply(
        autoTotemModule.getSettings().stream()
            .map(
                value2 -> {
                  return ((bunnythekenzo.client.setting.Setting) (value2)).getName();
                })
            .toList()
            .equals(List.of("Delay", "Force Totem", "Hotbar Totem", "Hotbar Slot", "Hotbar Delay")),
        "all original settings retained");
    apply(
        autoTotemModule.getSettings().stream()
            .allMatch(
                value3 -> {
                  ProtectionPerformanceTest.ensureInitialized();
                  return !((bunnythekenzo.client.setting.Setting) (value3)).getDescription().isBlank();
                }),
        "every setting explained");
    apply(
        !autoTotemModule.usesCombatTick(),
        "inventory work runs after vanilla input synchronization");
    GuiModuleEntry guiModuleEntry =
        (bunnythekenzo.client.GuiModuleEntry)
            (JsonSupport2.getList().stream()
                .flatMap(
                    (java.util.function.Function<
                            bunnythekenzo.client.CollectionData3, java.util.stream.Stream<?>>)
                        (collectionData3 -> {
                          return ((java.util.List) (collectionData3.list())).stream();
                        }))
                .filter(
                    value4 -> {
                      return ((bunnythekenzo.client.GuiModuleEntry) (value4)).getModule()
                          == autoTotemModule;
                    })
                .findFirst()
                .orElseThrow());
    apply(
        guiModuleEntry.getList().stream()
                .mapToInt(
                    (java.util.function.ToIntFunction<bunnythekenzo.client.CollectionData>)
                        (collectionData -> {
                          return ((java.util.List) (collectionData.list())).size();
                        }))
                .sum()
            == 5,
        "all five GUI rows bound");
    autoTotemModule.delaySetting.setNumber(Double.valueOf(0.0));
    autoTotemModule.hotbarDelaySetting.setNumber(Double.valueOf(17.0));
    autoTotemModule.hotbarSlotSetting.setNumber(Double.valueOf(9.0));
    autoTotemModule.hotbarTotemSetting.setValue(true);
    autoTotemModule.forceTotemSetting.setValue(true);
    JsonObject jsonObject = ClientInitializer.getClientConfig().getJsonObject();
    autoTotemModule
        .getSettings()
        .forEach(
            value5 -> {
              ((bunnythekenzo.client.setting.Setting) (value5)).resetToDefault();
            });
    ClientInitializer.getClientConfig().apply(jsonObject);
    apply(
        autoTotemModule.delaySetting.intValue() == 0
            && autoTotemModule.hotbarDelaySetting.intValue() == 17
            && autoTotemModule.hotbarSlotSetting.intValue() == 9
            && (Boolean) autoTotemModule.hotbarTotemSetting.getValue()
            && (Boolean) autoTotemModule.forceTotemSetting.getValue(),
        "all settings survive config round trip");
  }

  public static void checkMenuBehavior(MinecraftClient minecraftClient) {
    ClientPlayerInteractionManager interactionManager = minecraftClient.interactionManager;
    ProtectionPerformanceTest.ensureInitialized();

    var list =
        minecraftClient.player.playerScreenHandler.slots.stream()
            .map(
                slot -> {
                  return slot.getStack().copy();
                })
            .toList();
    int selectedSlot = minecraftClient.player.getInventory().getSelectedSlot();
    HoppinStandingBeforeTestSupport17 interactionManager2 =
        new HoppinStandingBeforeTestSupport17(minecraftClient);
    AutoTotemModule autoTotemModule = new AutoTotemModule();

    try {
      minecraftClient.interactionManager = interactionManager2;
      interactionManager2.setGameMode(GameMode.SURVIVAL);
      reset(minecraftClient, autoTotemModule, interactionManager2);
      apply(minecraftClient, 9, Items.TOTEM_OF_UNDYING);
      autoTotemModule.delaySetting.setNumber(Double.valueOf(4.0));
      apply(autoTotemModule, 4);
      apply(interactionManager2.list.isEmpty(), "four full ticks of opening delay");
      apply(autoTotemModule, 1);
      apply(interactionManager2.list.equals(List.of("9:40")), "offhand fills after delay");
      apply(autoTotemModule, 50);
      autoTotemModule.forceTotemSetting.setValue(true);
      apply(autoTotemModule, 50);
      apply(interactionManager2.list.size() == 1, "Force Totem does not churn a filled offhand");
      reset(minecraftClient, autoTotemModule, interactionManager2);
      apply(minecraftClient, 9, Items.TOTEM_OF_UNDYING);
      apply(minecraftClient, 40, Items.SHIELD);
      apply(autoTotemModule, 5);
      apply(interactionManager2.list.isEmpty(), "Force Totem off preserves occupied offhand");
      autoTotemModule.forceTotemSetting.setValue(true);
      apply(autoTotemModule, 1);
      apply(
          minecraftClient.player.getInventory().getStack(9).isOf(Items.SHIELD),
          "force swaps shield back into source");
      reset(minecraftClient, autoTotemModule, interactionManager2);
      apply(minecraftClient, 9, Items.TOTEM_OF_UNDYING);
      apply(minecraftClient, 10, Items.TOTEM_OF_UNDYING);
      apply(minecraftClient, 8, Items.STONE);
      autoTotemModule.hotbarTotemSetting.setValue(true);
      autoTotemModule.hotbarSlotSetting.setNumber(Double.valueOf(9.0));
      autoTotemModule.hotbarDelaySetting.setNumber(Double.valueOf(3.0));
      apply(autoTotemModule, 1);
      apply(
          interactionManager2.list.equals(List.of("9:40")),
          "offhand wins and only one click per tick");
      apply(autoTotemModule, 3);
      apply(interactionManager2.list.size() == 1, "independent hotbar delay");
      apply(autoTotemModule, 1);
      apply(interactionManager2.list.equals(List.of("9:40", "10:8")), "hotbar slot 9 mapping");
      apply(
          minecraftClient.player.getInventory().getStack(10).isOf(Items.STONE),
          "hotbar item preserved");
      apply(
          minecraftClient.player.getInventory().getSelectedSlot() == selectedSlot,
          "selected hotbar slot untouched");
      reset(minecraftClient, autoTotemModule, interactionManager2);
      apply(minecraftClient, 8, Items.TOTEM_OF_UNDYING);
      autoTotemModule.hotbarTotemSetting.setValue(true);
      apply(autoTotemModule, 5);
      apply(
          interactionManager2.list.equals(List.of("44:40")),
          "last hotbar totem goes to offhand without ping-pong");
      reset(minecraftClient, autoTotemModule, interactionManager2);
      apply(minecraftClient, 40, Items.TOTEM_OF_UNDYING);
      apply(minecraftClient, 8, Items.TOTEM_OF_UNDYING);
      apply(autoTotemModule, 5);
      apply(interactionManager2.list.isEmpty(), "Hotbar Totem off leaves hotbar arrangement alone");
      autoTotemModule.hotbarTotemSetting.setValue(true);
      apply(autoTotemModule, 1);
      apply(
          interactionManager2.list.equals(List.of("44:0")),
          "hotbar-to-hotbar refill and slot 1 mapping");
      reset(minecraftClient, autoTotemModule, interactionManager2);
      apply(minecraftClient, 9, Items.TOTEM_OF_UNDYING);
      autoTotemModule.delaySetting.setNumber(Double.valueOf(2.0));
      apply(autoTotemModule, 2);
      minecraftClient.setScreen(new InventoryScreen(minecraftClient.player));
      apply(autoTotemModule, 2);
      apply(
          interactionManager2.list.isEmpty(),
          "reopened screen resets delay even without a closed tick");
      autoTotemModule.setEnabled(false);
      apply(autoTotemModule, 5);
      apply(interactionManager2.list.isEmpty(), "disabled module never acts");
      autoTotemModule.setEnabled(true);
      apply(autoTotemModule, 2);
      apply(interactionManager2.list.isEmpty(), "re-enable starts fresh delay");
      apply(minecraftClient, 9, Items.STONE);
      apply(minecraftClient, 35, Items.TOTEM_OF_UNDYING);
      apply(autoTotemModule, 1);
      apply(
          interactionManager2.list.equals(List.of("35:40")),
          "source re-evaluated after manual change");
      reset(minecraftClient, autoTotemModule, interactionManager2);
      apply(minecraftClient, 9, Items.TOTEM_OF_UNDYING);
      minecraftClient.player.playerScreenHandler.setCursorStack(new ItemStack(Items.DIAMOND, 7));
      apply(autoTotemModule, 5);
      apply(
          interactionManager2.list.isEmpty()
              && minecraftClient.player.playerScreenHandler.getCursorStack().getCount() == 7,
          "manual cursor stack remains untouched");

      minecraftClient.player.playerScreenHandler.setCursorStack(ItemStack.EMPTY);

      autoTotemModule.delaySetting.setNumber(Double.valueOf(2.0));
      apply(autoTotemModule, 2);
      apply(interactionManager2.list.isEmpty(), "cursor pause resets delay");
      minecraftClient.setScreen(null);
      apply(autoTotemModule, 5);
      minecraftClient.setScreen(new ChatScreen("", false));
      apply(autoTotemModule, 5);
      GenericContainerScreenHandler generic9x3 =
          GenericContainerScreenHandler.createGeneric9x3(7, minecraftClient.player.getInventory());
      minecraftClient.player.currentScreenHandler = generic9x3;
      minecraftClient.setScreen(
          new GenericContainerScreen(
              generic9x3, minecraftClient.player.getInventory(), Text.literal("Test chest")));
      apply(autoTotemModule, 5);
      minecraftClient.setScreen(new InventoryScreen(minecraftClient.player));
      apply(autoTotemModule, 5);
      apply(
          interactionManager2.list.isEmpty(), "closed, chat, chest and mismatched menu never act");
      minecraftClient.player.currentScreenHandler = minecraftClient.player.playerScreenHandler;
      interactionManager2.setGameMode(GameMode.CREATIVE);
      apply(autoTotemModule, 5);
      apply(interactionManager2.list.isEmpty(), "creative never acts, including mode transition");
      interactionManager2.setGameMode(GameMode.SPECTATOR);
      apply(autoTotemModule, 5);
      apply(interactionManager2.list.isEmpty(), "spectator never acts, including mode transition");
      interactionManager2.setGameMode(GameMode.SURVIVAL);
      apply(minecraftClient, autoTotemModule, interactionManager2);
      reset(minecraftClient, autoTotemModule, interactionManager2);
      minecraftClient
          .player
          .playerScreenHandler
          .getSlot(1)
          .setStackNoCallbacks(new ItemStack(Items.TOTEM_OF_UNDYING));
      apply(autoTotemModule, 10);
      apply(interactionManager2.list.isEmpty(), "crafting grid is never a source");
      minecraftClient.player.playerScreenHandler.getSlot(1).setStackNoCallbacks(ItemStack.EMPTY);
      apply(autoTotemModule, 10);
      apply(minecraftClient, 9, Items.TOTEM_OF_UNDYING);
      autoTotemModule.delaySetting.setNumber(Double.valueOf(2.0));
      apply(autoTotemModule, 2);
      apply(interactionManager2.list.isEmpty(), "no delay accumulated with no totems");
      apply(autoTotemModule, 1);
      apply(interactionManager2.list.size() == 1, "newly supplied totem refills after fresh delay");
    } finally {
      autoTotemModule.setEnabled(false);
      minecraftClient.player.currentScreenHandler = minecraftClient.player.playerScreenHandler;
      minecraftClient.player.playerScreenHandler.setCursorStack(ItemStack.EMPTY);

      for (int index = 0; index < list.size(); index++) {
        minecraftClient
            .player
            .playerScreenHandler
            .getSlot(index)
            .setStackNoCallbacks((ItemStack) list.get(index));
      }

      minecraftClient.player.getInventory().setSelectedSlot(selectedSlot);
      interactionManager2.setGameMode(interactionManager.getCurrentGameMode());
      minecraftClient.interactionManager = interactionManager;
      minecraftClient.setScreen(null);
    }
  }

  public static void apply(
      MinecraftClient minecraftClient,
      AutoTotemModule autoTotemModule,
      HoppinStandingBeforeTestSupport17 hoppinStandingBeforeTestSupport17) {
    ProtectionPerformanceTest.ensureInitialized();

    PlayerInput playerInput = minecraftClient.player.input.playerInput;

    try {
      for (int index = 0; index < 7; index++) {
        reset(minecraftClient, autoTotemModule, hoppinStandingBeforeTestSupport17);
        apply(minecraftClient, 9, Items.TOTEM_OF_UNDYING);
        minecraftClient.player.input.playerInput =
            new PlayerInput(
                index == 0, index == 1, index == 2, index == 3, index == 4, index == 5, index == 6);
        apply(autoTotemModule, 3);
        apply(
            hoppinStandingBeforeTestSupport17.list.isEmpty(), "waits on active input bit " + index);
        minecraftClient.player.input.playerInput = PlayerInput.DEFAULT;
      }

      reset(minecraftClient, autoTotemModule, hoppinStandingBeforeTestSupport17);
      apply(minecraftClient, 9, Items.TOTEM_OF_UNDYING);
      Field declaredField =
          bunnythekenzo.client.runtime.MappedReflection.field(
              ClientPlayerEntity.class,
              "lastPlayerInput",
              "field_54156",
              "Lnet/minecraft/class_10185;");
      Field declaredField2 =
          bunnythekenzo.client.runtime.MappedReflection.field(
              ClientPlayerEntity.class, "lastSprinting", "field_3919", "Z");
      declaredField.setAccessible(true);
      declaredField2.setAccessible(true);
      Object value2 = declaredField.get(minecraftClient.player);
      boolean value3 = declaredField2.getBoolean(minecraftClient.player);

      try {
        declaredField.set(
            minecraftClient.player,
            new PlayerInput(true, false, false, false, false, false, false));
        apply(autoTotemModule, 3);
        apply(
            hoppinStandingBeforeTestSupport17.list.isEmpty(),
            "local input reset cannot bypass pending server input update");
        declaredField.set(minecraftClient.player, PlayerInput.DEFAULT);
        minecraftClient.player.setSprinting(true);
        apply(autoTotemModule, 3);
        apply(
            hoppinStandingBeforeTestSupport17.list.isEmpty(),
            "waits while local sprint remains active");
        minecraftClient.player.setSprinting(false);
        declaredField2.setBoolean(minecraftClient.player, true);
        apply(autoTotemModule, 3);
        apply(
            hoppinStandingBeforeTestSupport17.list.isEmpty(),
            "waits until vanilla has sent STOP_SPRINTING");
        declaredField2.setBoolean(minecraftClient.player, false);
        declaredField.set(
            minecraftClient.player,
            new PlayerInput(false, false, false, false, false, true, false));
        apply(autoTotemModule, 3);
        apply(
            hoppinStandingBeforeTestSupport17.list.isEmpty(),
            "waits until vanilla has transmitted neutral sneaking input");
        declaredField.set(minecraftClient.player, PlayerInput.DEFAULT);
        apply(autoTotemModule, 1);
        apply(
            hoppinStandingBeforeTestSupport17.list.equals(List.of("9:40")),
            "zero delay refills as soon as actual and transmitted input are neutral");
      } finally {
        declaredField.set(minecraftClient.player, value2);
        declaredField2.setBoolean(minecraftClient.player, value3);
      }
    } catch (ReflectiveOperationException failure) {
      throw new AssertionError(failure);
    } finally {
      minecraftClient.player.input.playerInput = playerInput;
      minecraftClient.player.setSprinting(false);
    }
  }

  public static void reset(
      MinecraftClient minecraftClient,
      AutoTotemModule autoTotemModule,
      HoppinStandingBeforeTestSupport17 hoppinStandingBeforeTestSupport17) {
    autoTotemModule.setEnabled(false);
    autoTotemModule
        .getSettings()
        .forEach(
            value -> {
              ((bunnythekenzo.client.setting.Setting) (value)).resetToDefault();
            });
    autoTotemModule.delaySetting.setNumber(Double.valueOf(0.0));
    autoTotemModule.hotbarDelaySetting.setNumber(Double.valueOf(0.0));
    minecraftClient.player.playerScreenHandler.setCursorStack(ItemStack.EMPTY);
    minecraftClient.player.getInventory().clear();
    minecraftClient.setScreen(new InventoryScreen(minecraftClient.player));
    hoppinStandingBeforeTestSupport17.list.clear();
    autoTotemModule.setEnabled(true);
  }

  public static void apply(MinecraftClient minecraftClient, int intValue, Item item) {
    minecraftClient.player.getInventory().setStack(intValue, new ItemStack(item));
  }

  public static void apply(AutoTotemModule autoTotemModule, int intValue) {
    ProtectionPerformanceTest.ensureInitialized();

    for (int index = 0; index < intValue; index++) {
      autoTotemModule.onTick();
    }
  }

  public static Channel getChannel(
      MinecraftClient minecraftClient,
      HoppinStandingBeforeTestSupport21 hoppinStandingBeforeTestSupport21) {
    try {
      Field declaredField =
          bunnythekenzo.client.runtime.MappedReflection.field(
              ClientConnection.class, "channel", "field_11651", "Lio/netty/channel/Channel;");
      declaredField.setAccessible(true);
      Channel channel =
          (Channel) declaredField.get(minecraftClient.getNetworkHandler().getConnection());
      channel
          .pipeline()
          .addBefore("packet_handler", "auto-totem-test", hoppinStandingBeforeTestSupport21);
      return channel;
    } catch (ReflectiveOperationException failure) {
      throw new AssertionError(failure);
    }
  }

  public static void apply(boolean flag, String text) {
    if (!flag) {
      throw new AssertionError("Auto Totem: " + text);
    }
  }
}
