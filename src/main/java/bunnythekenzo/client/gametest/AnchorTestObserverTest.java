package bunnythekenzo.client.gametest;

import com.google.gson.JsonObject;
import io.netty.channel.Channel;
import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;
import net.minecraft.block.Blocks;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.GuiModuleEntry;
import bunnythekenzo.client.mixin.ClientPlayerInteractionManagerAccessor;
import bunnythekenzo.client.mixin.MinecraftClientAccessor;
import bunnythekenzo.client.module.AnchorMacroModule;
import bunnythekenzo.client.setting.Setting;

public final class AnchorTestObserverTest {
  public static final BlockPos blockPos = new BlockPos(0, 181, 0);

  public static void run(
      ClientGameTestContext clientGameTestContext,
      TestSingleplayerContext testSingleplayerContext) {
    ProtectionPerformanceTest.ensureInitialized();

    JsonObject onClient =
        clientGameTestContext.computeOnClient(
            minecraftClient -> {
              return ClientInitializer.getClientConfig().getJsonObject();
            });
    CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();
    CopyOnWriteArrayList copyOnWriteArrayList2 = new CopyOnWriteArrayList();
    CopyOnWriteArrayList copyOnWriteArrayList3 = new CopyOnWriteArrayList();
    Channel[] channelArray = new Channel[1];

    try {
      clientGameTestContext.runOnClient(
          minecraftClient2 -> {
            ClientInitializer.getModuleManager()
                .getList()
                .forEach(
                    (java.util.function.Consumer<bunnythekenzo.client.module.Module>)
                        (module -> {
                          module.setEnabled(false);
                        }));
            minecraftClient2.setScreen(null);
            minecraftClient2.options.pauseOnLostFocus = false;
            checkSettings();
            apply(minecraftClient2);
          });

      for (int index = 0; index < 6; index++) {
        boolean flag = index % 3 == 2;
        boolean flag2 = index % 3 == 1;
        boolean flag3 = index >= 3;
        copyOnWriteArrayList.clear();
        copyOnWriteArrayList2.clear();
        copyOnWriteArrayList3.clear();
        testSingleplayerContext.getServer().runCommand("gamemode creative @a");
        testSingleplayerContext
            .getServer()
            .runOnServer(
                minecraftServer -> {
                  ServerWorld overworld = minecraftServer.getOverworld();
                  ProtectionPerformanceTest.ensureInitialized();

                  for (int index2 = -4; index2 <= 4; index2++) {
                    for (int index3 = -4; index3 <= 5; index3++) {
                      overworld.setBlockState(
                          new BlockPos(index2, 180, index3), Blocks.BEDROCK.getDefaultState());

                      for (int index4 = 181; index4 <= 184; index4++) {
                        overworld.setBlockState(
                            new BlockPos(index2, index4, index3), Blocks.AIR.getDefaultState());
                      }
                    }
                  }

                  ServerPlayerEntity first =
                      minecraftServer.getPlayerManager().getPlayerList().getFirst();
                  first.getInventory().clear();
                  if (!flag) {
                    first
                        .getInventory()
                        .setStack(flag2 ? 40 : 0, new ItemStack(Items.TOTEM_OF_UNDYING));
                  }

                  first.getInventory().setStack(1, new ItemStack(Items.GLOWSTONE, 16));
                  first.getInventory().setStack(2, new ItemStack(Items.RESPAWN_ANCHOR, 16));
                  first.playerScreenHandler.syncState();
                });
        testSingleplayerContext.getServer().runCommand("tp @a 0.5 181 3.5 180 27.6");
        clientGameTestContext.waitTicks(25);
        clientGameTestContext.runOnClient(
            minecraftClient3 -> {
              AnchorMacroModule anchorMacroModule =
                  ClientInitializer.getModuleManager().anchorMacroModule;
              ProtectionPerformanceTest.ensureInitialized();

              anchorMacroModule
                  .getSettings()
                  .forEach(
                      (referenceArgument13_0) ->
                          ((bunnythekenzo.client.setting.Setting) referenceArgument13_0)
                              .resetToDefault());
              anchorMacroModule.disallowWithoutTotemSetting.setValue(!flag);
              if (flag3) {
                anchorMacroModule.switchBackDelaySetting.setNumber(Double.valueOf(0.0));
              }

              anchorMacroModule.setEnabled(true);
              minecraftClient3.player.getInventory().setSelectedSlot(2);
              ((ClientPlayerInteractionManagerAccessor)
                      (Object) (minecraftClient3.interactionManager))
                  .invokeSyncSelectedSlot();
              channelArray[0] =
                  getChannel(
                      minecraftClient3,
                      copyOnWriteArrayList,
                      copyOnWriteArrayList2,
                      copyOnWriteArrayList3);
              apply(minecraftClient3, 181.05);
              ((MinecraftClientAccessor) (Object) (minecraftClient3)).setItemUseCooldown(0);
              minecraftClient3.options.useKey.setPressed(true);
            });
        clientGameTestContext.waitTicks(2);
        clientGameTestContext.runOnClient(
            minecraftClient4 -> {
              minecraftClient4.options.useKey.setPressed(false);
            });
        clientGameTestContext.waitTicks(25);
        clientGameTestContext.runOnClient(
            minecraftClient5 -> {
              ProtectionPerformanceTest.ensureInitialized();

              minecraftClient5.options.useKey.setPressed(false);
              apply(
                  !minecraftClient5.world.getBlockState(blockPos).isOf(Blocks.RESPAWN_ANCHOR),
                  "client receives real anchor removal");
              apply(
                  minecraftClient5.player.getInventory().getSelectedSlot() == 2,
                  "real cycle restores original slot");
              ClientInitializer.getModuleManager().anchorMacroModule.setEnabled(false);
            });
        testSingleplayerContext
            .getServer()
            .runOnServer(
                minecraftServer2 -> {
                  ProtectionPerformanceTest.ensureInitialized();
                  apply(
                      !minecraftServer2
                          .getOverworld()
                          .getBlockState(blockPos)
                          .isOf(Blocks.RESPAWN_ANCHOR),
                      "server actually exploded the anchor");
                  apply(
                      minecraftServer2
                              .getPlayerManager()
                              .getPlayerList()
                              .getFirst()
                              .getInventory()
                              .getSelectedSlot()
                          == 2,
                      "server receives switch-back");
                });
        apply(
            copyOnWriteArrayList.equals(
                List.of("use:2", "slot:1", "use:1", "slot:0", "use:0", "slot:2")),
            "one placement, one charge, one detonation, no duplicate slot/use packets: "
                + copyOnWriteArrayList);
        apply(
            copyOnWriteArrayList2.size() == 3
                && (Integer) copyOnWriteArrayList2.get(0) < (Integer) copyOnWriteArrayList2.get(1)
                && (Integer) copyOnWriteArrayList2.get(1) < (Integer) copyOnWriteArrayList2.get(2),
            "vanilla prediction sequences strictly increase: " + copyOnWriteArrayList2);
        apply(
            copyOnWriteArrayList3.isEmpty(),
            "slot/use/movement packet order: " + copyOnWriteArrayList3);
        ClientInitializer.logger.info(
            "Anchor Macro real server cycle verified: withoutTotem={}, offhandTotem={},"
                + " zeroSwitchBack={}, packets={}, orderingErrors={}",
            flag,
            flag2,
            flag3,
            copyOnWriteArrayList,
            copyOnWriteArrayList3);
        clientGameTestContext.runOnClient(
            minecraftClient6 -> {
              channelArray[0].pipeline().remove("anchor-test-observer");
              channelArray[0] = null;
            });
      }

      ClientInitializer.logger.info(
          "Anchor Macro verified: slot changes never follow use in the same network tick; actions"
              + " precede movement; zero-delay and default switch-back, totem checks, fallback,"
              + " cancellation and real server explosions passed");
    } finally {
      clientGameTestContext.runOnClient(
          minecraftClient7 -> {
            ProtectionPerformanceTest.ensureInitialized();

            minecraftClient7.options.useKey.setPressed(false);
            ClientInitializer.getModuleManager().anchorMacroModule.setEnabled(false);
            if (channelArray[0] != null
                && channelArray[0].pipeline().get("anchor-test-observer") != null) {
              channelArray[0].pipeline().remove("anchor-test-observer");
            }

            ClientInitializer.getClientConfig().apply(onClient);
          });
    }
  }

  public static void checkSettings() {
    ProtectionPerformanceTest.ensureInitialized();

    AnchorMacroModule anchorMacroModule = ClientInitializer.getModuleManager().anchorMacroModule;
    apply(
        anchorMacroModule.getSettings().size() == 7,
        "existing six settings plus the no-totem option");
    apply(
        (Boolean) anchorMacroModule.disallowWithoutTotemSetting.getValue(),
        "existing totem requirement remains the default");
    apply(
        anchorMacroModule.getSettings().stream()
            .anyMatch(
                value2 -> {
                  return ((bunnythekenzo.client.setting.Setting) (value2))
                      .getName()
                      .equals("Disallow Without Totem");
                }),
        "correct setting name exposed in the menu");
    apply(
        anchorMacroModule.getSettings().stream()
            .noneMatch(
                value3 -> {
                  return ((bunnythekenzo.client.setting.Setting) (value3))
                      .getName()
                      .equals("Allow Without Totem");
                }),
        "old setting removed");
    List<bunnythekenzo.client.CollectionData> list = GuiModuleEntry.of(anchorMacroModule).getList();
    apply(
        ((bunnythekenzo.client.CollectionData)
                    (list.stream()
                        .filter(
                            collectionData -> {
                              return ((bunnythekenzo.client.CollectionData) (collectionData))
                                  .string()
                                  .equals("General");
                            })
                        .findFirst()
                        .orElseThrow()))
                .list()
                .size()
            == 7,
        "all seven behavior settings are in General");
    apply(
        list.stream()
            .filter(
                collectionData2 -> {
                  return ((bunnythekenzo.client.CollectionData) (collectionData2))
                      .string()
                      .equals("Visuals");
                })
            .findFirst()
            .orElseThrow()
            .test(),
        "Glowstone Delay is not visual");

    for (Setting setting :
        (java.lang.Iterable<Setting>) (java.lang.Iterable<?>) (anchorMacroModule.getSettings())) {
      apply(!setting.getDescription().isBlank(), "setting describes its behavior");
    }

    anchorMacroModule.autoSwitchBackSetting.setValue(false);
    apply(
        !anchorMacroModule.switchBackDelaySetting.isVisible(),
        "dependent delay hidden when switch-back disabled");
    anchorMacroModule
        .getSettings()
        .forEach(
            (referenceArgument14_0) ->
                ((bunnythekenzo.client.setting.Setting) referenceArgument14_0).resetToDefault());
  }

  public static void apply(MinecraftClient minecraftClient) {
    AnchorMacroModule anchorMacroModule = ClientInitializer.getModuleManager().anchorMacroModule;
    ClientPlayerInteractionManager interactionManager = minecraftClient.interactionManager;
    ProtectionPerformanceTest.ensureInitialized();

    Vec3d entityPos = minecraftClient.player.getEntityPos();
    HoppinStandingBeforeTestSupport15 interactionManager2 =
        new HoppinStandingBeforeTestSupport15(minecraftClient);
    minecraftClient.interactionManager = interactionManager2;

    try {
      apply(minecraftClient, interactionManager2, 0);
      apply2(minecraftClient);
      apply(
          interactionManager2.list.equals(List.of(1)),
          "zero delay charges exactly once on the next combat tick");
      apply(
          minecraftClient.player.getInventory().getSelectedSlot() == 1,
          "glowstone remains selected until the charge tick ends");
      ((MinecraftClientAccessor) (Object) (minecraftClient)).invokeDoItemUse();
      apply(interactionManager2.list.size() == 1, "duplicate use input cannot add an extra charge");
      anchorMacroModule.onTick();
      apply(
          interactionManager2.list.equals(List.of(1, 0)), "zero delay detonates on following tick");
      minecraftClient.world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 3);
      anchorMacroModule.onTick();
      apply(
          minecraftClient.player.getInventory().getSelectedSlot() == 0,
          "default switch-back waits two ticks");
      anchorMacroModule.onTick();
      apply(
          minecraftClient.player.getInventory().getSelectedSlot() == 2,
          "switch-back restores previous slot");
      apply(minecraftClient, interactionManager2, 0);
      anchorMacroModule.switchDelaySetting.setNumber(Double.valueOf(3.0));
      anchorMacroModule.glowstoneDelaySetting.setNumber(Double.valueOf(4.0));
      anchorMacroModule.explodeDelaySetting.setNumber(Double.valueOf(5.0));
      anchorMacroModule.switchBackDelaySetting.setNumber(Double.valueOf(6.0));
      apply2(minecraftClient);
      apply(
          interactionManager2.list.isEmpty()
              && minecraftClient.player.getInventory().getSelectedSlot() == 2,
          "first tick respects switch delay");

      for (int index = 2; index <= 21; index++) {
        anchorMacroModule.onTick();
        if (index < 3) {
          apply(
              minecraftClient.player.getInventory().getSelectedSlot() == 2,
              "switch waits full delay");
        }

        if (index >= 3 && index < 10) {
          apply(
              minecraftClient.player.getInventory().getSelectedSlot() == 1,
              "glowstone selected during its phase");
        }

        apply(
            interactionManager2.list.size() == (index < 7 ? 0 : (index < 15 ? 1 : 2)),
            "exact charge/explode delays at tick " + index);
        if (index == 15) {
          minecraftClient.world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 3);
        }

        if (index >= 10 && index < 21) {
          apply(
              minecraftClient.player.getInventory().getSelectedSlot() == 0,
              "totem held through detonation delay");
        }
      }

      apply(
          minecraftClient.player.getInventory().getSelectedSlot() == 2,
          "switch-back uses its own delay");
      apply(minecraftClient, interactionManager2, 3);
      minecraftClient.player.getInventory().setStack(1, ItemStack.EMPTY);
      minecraftClient.player.getInventory().setStack(8, new ItemStack(Items.TOTEM_OF_UNDYING));
      anchorMacroModule.totemSlotSetting.setNumber(Double.valueOf(9.0));
      anchorMacroModule.autoSwitchBackSetting.setValue(false);
      apply2(minecraftClient);
      apply(
          interactionManager2.list.equals(List.of(8)),
          "charged anchor skips glowstone and uses slot nine");
      minecraftClient.world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 3);
      apply(anchorMacroModule, 4);
      anchorMacroModule.setEnabled(false);
      apply(
          minecraftClient.player.getInventory().getSelectedSlot() == 8,
          "switch-back off retains chosen slot even on disable");
      apply(minecraftClient, interactionManager2);
      apply2(minecraftClient, interactionManager2);
      apply3(minecraftClient, interactionManager2);

      for (int index2 = 0; index2 < 3; index2++) {
        apply(minecraftClient, interactionManager2, 0);
        if (index2 < 2) {
          minecraftClient.player.getInventory().setStack(index2, ItemStack.EMPTY);
        } else {
          minecraftClient.player.getInventory().setStack(40, new ItemStack(Items.GLOWSTONE));
        }

        minecraftClient.options.useKey.setPressed(true);
        apply2(minecraftClient);
        apply(anchorMacroModule, 8);
        apply(
            interactionManager2.list.isEmpty()
                && minecraftClient.player.getInventory().getSelectedSlot() == 2,
            "missing totem/fuel or offhand fuel prevents sequence");
      }

      for (int index3 = 0; index3 < 5; index3++) {
        apply(minecraftClient, interactionManager2, 0);
        anchorMacroModule.glowstoneDelaySetting.setNumber(Double.valueOf(8.0));
        apply2(minecraftClient);
        apply(
            minecraftClient.player.getInventory().getSelectedSlot() == 1,
            "cancel fixture switched to glowstone");
        switch (index3) {
          case 0:
            minecraftClient.player.setYaw(0.0F);
            break;
          case 1:
            minecraftClient.player.getInventory().setSelectedSlot(7);
            break;
          case 2:
            minecraftClient.setScreen(new InventoryScreen(minecraftClient.player));
            break;
          case 3:
            anchorMacroModule.setEnabled(false);
            break;
          case 4:
            minecraftClient.player.setPosition(0.5, 181.0, 10.5);
        }

        apply(anchorMacroModule, 12);
        apply(interactionManager2.list.isEmpty(), "cancellation prevents delayed charge " + index3);
        apply(
            minecraftClient.player.getInventory().getSelectedSlot() == (index3 == 1 ? 7 : 2),
            "cancellation respects manual slot " + index3);
      }

      apply(minecraftClient, interactionManager2, 0);
      minecraftClient.world.setBlockState(
          new BlockPos(0, 182, 2), Blocks.STONE.getDefaultState(), 3);
      apply(!anchorMacroModule.test(), "solid obstruction blocks eye ray");
      apply(interactionManager2.list.isEmpty(), "no use through obstruction");
      minecraftClient.world.setBlockState(new BlockPos(0, 182, 2), Blocks.AIR.getDefaultState(), 3);
      apply(minecraftClient, interactionManager2, 1);
      minecraftClient.options.useKey.setPressed(true);
      apply2(minecraftClient);
      apply(anchorMacroModule, 65);
      apply(
          interactionManager2.list.size() == 1,
          "unremoved/rejected anchor times out without repeated detonation while held");
      apply(minecraftClient.player.getInventory().getSelectedSlot() == 2, "timeout restores slot");
    } finally {
      anchorMacroModule.setEnabled(false);
      anchorMacroModule
          .getSettings()
          .forEach(
              (referenceArgument15_0) ->
                  ((bunnythekenzo.client.setting.Setting) referenceArgument15_0).resetToDefault());
      minecraftClient.options.useKey.setPressed(false);
      minecraftClient.setScreen(null);
      minecraftClient.interactionManager = interactionManager;
      minecraftClient.player.setPosition(entityPos);
    }
  }

  public static void apply(
      MinecraftClient minecraftClient,
      HoppinStandingBeforeTestSupport15 hoppinStandingBeforeTestSupport15) {
    AnchorMacroModule anchorMacroModule = ClientInitializer.getModuleManager().anchorMacroModule;
    boolean[] flags = new boolean[] {true, false};
    ProtectionPerformanceTest.ensureInitialized();

    for (boolean flag : flags) {
      apply(minecraftClient, hoppinStandingBeforeTestSupport15, 0);
      anchorMacroModule.disallowWithoutTotemSetting.setValue(false);
      minecraftClient
          .player
          .getInventory()
          .setStack(0, flag ? ItemStack.EMPTY : new ItemStack(Items.DIAMOND_SWORD));
      minecraftClient.options.useKey.setPressed(true);

      for (int index = 0; index < 3; index++) {
        anchorMacroModule.onTick();
        apply(
            hoppinStandingBeforeTestSupport15.list.equals(List.of(1)),
            "no-totem cycle charges once: " + index);
        anchorMacroModule.onTick();
        apply(
            hoppinStandingBeforeTestSupport15.list.equals(List.of(1, 0)),
            "empty hand / other item detonates: " + index);
        minecraftClient.world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 3);
        apply(anchorMacroModule, 2);
        apply(
            minecraftClient.player.getInventory().getSelectedSlot() == 2,
            "no-totem cycle restores original slot");
        minecraftClient.world.setBlockState(blockPos, Blocks.RESPAWN_ANCHOR.getDefaultState(), 3);
        apply(minecraftClient, 181.5);
        hoppinStandingBeforeTestSupport15.list.clear();
      }
    }

    apply(minecraftClient, hoppinStandingBeforeTestSupport15, 0);
    anchorMacroModule.disallowWithoutTotemSetting.setValue(false);
    minecraftClient.player.getInventory().setStack(0, new ItemStack(Items.GLOWSTONE, 16));
    apply2(minecraftClient);
    anchorMacroModule.onTick();
    apply(
        hoppinStandingBeforeTestSupport15.list.equals(List.of(0, 2)),
        "glowstone in preferred slot falls back to a different hotbar slot");
    apply(
        minecraftClient.world.getBlockState(blockPos).get(RespawnAnchorBlock.CHARGES) == 1,
        "fallback detonates instead of adding a second charge");
    apply(minecraftClient, hoppinStandingBeforeTestSupport15, 0);
    anchorMacroModule.disallowWithoutTotemSetting.setValue(false);
    anchorMacroModule.explodeDelaySetting.setNumber(Double.valueOf(4.0));
    apply2(minecraftClient);
    minecraftClient.player.getInventory().setStack(0, ItemStack.EMPTY);
    apply(anchorMacroModule, 5);
    apply(
        hoppinStandingBeforeTestSupport15.list.equals(List.of(1, 0)),
        "losing the last totem mid-cycle still detonates");
    apply(minecraftClient, hoppinStandingBeforeTestSupport15, 1);
    anchorMacroModule.disallowWithoutTotemSetting.setValue(false);
    minecraftClient.player.getInventory().setStack(0, ItemStack.EMPTY);
    minecraftClient.player.getInventory().setStack(1, ItemStack.EMPTY);
    apply2(minecraftClient);
    apply(
        hoppinStandingBeforeTestSupport15.list.equals(List.of(0)),
        "precharged anchor needs neither totem nor glowstone");
    apply(minecraftClient, hoppinStandingBeforeTestSupport15, 0);
    anchorMacroModule.disallowWithoutTotemSetting.setValue(false);
    minecraftClient.player.getInventory().setStack(0, ItemStack.EMPTY);
    minecraftClient.player.getInventory().setStack(1, ItemStack.EMPTY);
    apply2(minecraftClient);
    apply(
        hoppinStandingBeforeTestSupport15.list.isEmpty(),
        "uncharged anchor still needs actual fuel");
    apply(minecraftClient, hoppinStandingBeforeTestSupport15, 1);
    anchorMacroModule.disallowWithoutTotemSetting.setValue(false);

    for (int index2 = 0; index2 < 9; index2++) {
      minecraftClient.player.getInventory().setStack(index2, new ItemStack(Items.GLOWSTONE, 16));
    }

    apply2(minecraftClient);
    apply(anchorMacroModule, 3);
    apply(
        hoppinStandingBeforeTestSupport15.list.isEmpty(),
        "all-glowstone hotbar cannot accidentally add charges");
  }

  public static void apply2(
      MinecraftClient minecraftClient,
      HoppinStandingBeforeTestSupport15 hoppinStandingBeforeTestSupport15) {
    ProtectionPerformanceTest.ensureInitialized();

    AnchorMacroModule anchorMacroModule = ClientInitializer.getModuleManager().anchorMacroModule;

    for (int intValue : new int[] {0, 8, 40}) {
      apply(minecraftClient, hoppinStandingBeforeTestSupport15, 0);
      minecraftClient.player.getInventory().setStack(0, ItemStack.EMPTY);
      minecraftClient
          .player
          .getInventory()
          .setStack(intValue, new ItemStack(Items.TOTEM_OF_UNDYING));
      apply2(minecraftClient);
      anchorMacroModule.onTick();
      apply(
          hoppinStandingBeforeTestSupport15.list.equals(List.of(1, intValue == 40 ? 0 : intValue)),
          "hotbar/offhand totem allows sequence and is held for detonation: " + intValue);
      apply(
          minecraftClient.player.getMainHandStack().isOf(Items.TOTEM_OF_UNDYING)
              || minecraftClient.player.getOffHandStack().isOf(Items.TOTEM_OF_UNDYING),
          "detonation has a totem in hand");
    }

    for (int intValue2 : new int[] {9, 35}) {
      for (int intValue3 : new int[] {0, 1}) {
        apply(minecraftClient, hoppinStandingBeforeTestSupport15, intValue3);
        minecraftClient.player.getInventory().setStack(0, ItemStack.EMPTY);
        minecraftClient
            .player
            .getInventory()
            .setStack(intValue2, new ItemStack(Items.TOTEM_OF_UNDYING));
        minecraftClient.options.useKey.setPressed(true);
        apply2(minecraftClient);
        apply(anchorMacroModule, 6);
        apply(
            hoppinStandingBeforeTestSupport15.list.isEmpty(),
            "main-inventory-only totem cannot protect a sequence: " + intValue2);
        apply(
            minecraftClient.player.getInventory().getSelectedSlot() == 2,
            "blocked sequence leaves original slot selected");
      }
    }

    for (int intValue4 : new int[] {0, 40}) {
      for (boolean flag : new boolean[] {false, true}) {
        apply(minecraftClient, hoppinStandingBeforeTestSupport15, 0);
        minecraftClient.player.getInventory().setStack(0, ItemStack.EMPTY);
        minecraftClient
            .player
            .getInventory()
            .setStack(intValue4, new ItemStack(Items.TOTEM_OF_UNDYING));
        minecraftClient.player.getInventory().setStack(35, new ItemStack(Items.TOTEM_OF_UNDYING));
        if (flag) {
          anchorMacroModule.explodeDelaySetting.setNumber(Double.valueOf(5.0));
        } else {
          anchorMacroModule.glowstoneDelaySetting.setNumber(Double.valueOf(5.0));
        }

        minecraftClient.options.useKey.setPressed(true);
        apply2(minecraftClient);
        minecraftClient.player.getInventory().setStack(intValue4, ItemStack.EMPTY);
        apply(anchorMacroModule, 12);
        apply(
            hoppinStandingBeforeTestSupport15.list.size() == (flag ? 1 : 0),
            "losing last hotbar/offhand totem stops pending charge/detonation despite inventory"
                + " spare");
        apply(
            minecraftClient.player.getInventory().getSelectedSlot() == 2,
            "last-totem cancellation restores original slot");
      }
    }

    apply(minecraftClient, hoppinStandingBeforeTestSupport15, 0);
    anchorMacroModule.explodeDelaySetting.setNumber(Double.valueOf(4.0));
    apply2(minecraftClient);
    minecraftClient
        .player
        .getInventory()
        .setStack(35, minecraftClient.player.getInventory().getStack(0));
    minecraftClient.player.getInventory().setStack(0, ItemStack.EMPTY);
    apply(anchorMacroModule, 5);
    apply(
        hoppinStandingBeforeTestSupport15.list.equals(List.of(1)),
        "moving last hotbar totem to main inventory cancels detonation");
    apply(minecraftClient, hoppinStandingBeforeTestSupport15, 0);
    anchorMacroModule.explodeDelaySetting.setNumber(Double.valueOf(4.0));
    apply2(minecraftClient);
    minecraftClient
        .player
        .getInventory()
        .setStack(40, minecraftClient.player.getInventory().getStack(0));
    minecraftClient.player.getInventory().setStack(0, ItemStack.EMPTY);
    apply(anchorMacroModule, 5);
    apply(
        hoppinStandingBeforeTestSupport15.list.equals(List.of(1, 0)),
        "moving hotbar totem to offhand preserves protection");

    for (boolean flag2 : new boolean[] {false, true}) {
      apply(minecraftClient, hoppinStandingBeforeTestSupport15, 0);
      anchorMacroModule.explodeDelaySetting.setNumber(Double.valueOf(3.0));
      if (flag2) {
        minecraftClient
            .player
            .getInventory()
            .setStack(40, minecraftClient.player.getInventory().getStack(0));
        minecraftClient.player.getInventory().setStack(0, ItemStack.EMPTY);
      }

      apply2(minecraftClient);
      anchorMacroModule.onTick();
      minecraftClient.player.getInventory().setStack(8, new ItemStack(Items.TOTEM_OF_UNDYING));
      minecraftClient.player.getInventory().setStack(flag2 ? 40 : 0, ItemStack.EMPTY);
      apply(anchorMacroModule, 10);
      apply(
          hoppinStandingBeforeTestSupport15.list.equals(List.of(1, 8)),
          "replacement hotbar totem is selected after previous protection disappears");
      apply(
          minecraftClient.player.getMainHandStack().isOf(Items.TOTEM_OF_UNDYING),
          "replacement totem held when detonating");
    }

    apply(minecraftClient, hoppinStandingBeforeTestSupport15, 1);
    minecraftClient.player.getInventory().setStack(0, ItemStack.EMPTY);
    apply2(minecraftClient);
    apply(anchorMacroModule, 4);
    apply(
        hoppinStandingBeforeTestSupport15.list.isEmpty(),
        "precharged anchor still requires a totem when option is on");
    apply(minecraftClient, hoppinStandingBeforeTestSupport15, 0);
    minecraftClient.player.getInventory().setStack(0, ItemStack.EMPTY);
    minecraftClient.player.getInventory().setStack(35, new ItemStack(Items.TOTEM_OF_UNDYING));
    minecraftClient.world.setBlockState(blockPos, Blocks.STONE.getDefaultState(), 3);
    apply(minecraftClient, 181.5);
    apply2(minecraftClient);
    apply(
        hoppinStandingBeforeTestSupport15.list.isEmpty(),
        "held anchor cannot place another block after the last totem is gone");
  }

  public static void apply(
      MinecraftClient minecraftClient,
      HoppinStandingBeforeTestSupport15 hoppinStandingBeforeTestSupport15,
      int intValue) {
    AnchorMacroModule anchorMacroModule = ClientInitializer.getModuleManager().anchorMacroModule;
    anchorMacroModule.setEnabled(false);
    anchorMacroModule.update4();
    anchorMacroModule
        .getSettings()
        .forEach(
            (referenceArgument16_0) ->
                ((bunnythekenzo.client.setting.Setting) referenceArgument16_0).resetToDefault());
    minecraftClient.setScreen(null);
    minecraftClient.options.useKey.setPressed(false);
    minecraftClient.options.attackKey.setPressed(false);
    minecraftClient.options.sneakKey.setPressed(false);
    minecraftClient.player.setSneaking(false);
    minecraftClient.player.setPosition(0.5, 181.0, 3.5);
    minecraftClient.player.setVelocity(Vec3d.ZERO);
    minecraftClient.player.getInventory().clear();
    minecraftClient.player.getInventory().setStack(0, new ItemStack(Items.TOTEM_OF_UNDYING));
    minecraftClient.player.getInventory().setStack(1, new ItemStack(Items.GLOWSTONE, 16));
    minecraftClient.player.getInventory().setStack(2, new ItemStack(Items.RESPAWN_ANCHOR, 16));
    minecraftClient.player.getInventory().setSelectedSlot(2);
    minecraftClient.world.setBlockState(
        blockPos,
        Blocks.RESPAWN_ANCHOR.getDefaultState().with(RespawnAnchorBlock.CHARGES, intValue),
        3);
    apply(minecraftClient, 181.5);
    hoppinStandingBeforeTestSupport15.list.clear();
    anchorMacroModule.setEnabled(true);
  }

  public static void apply(MinecraftClient minecraftClient, double doubleValue) {
    minecraftClient.player.setYaw(180.0F);
    minecraftClient.player.setPitch(
        (float) Math.toDegrees(Math.atan2(minecraftClient.player.getEyeY() - doubleValue, 3.0)));
    minecraftClient.crosshairTarget =
        minecraftClient.player.raycast(
            minecraftClient.player.getBlockInteractionRange(), 1.0F, false);
  }

  public static void apply2(MinecraftClient minecraftClient) {
    int selectedSlot = minecraftClient.player.getInventory().getSelectedSlot();
    ProtectionPerformanceTest.ensureInitialized();

    ((MinecraftClientAccessor) (Object) (minecraftClient)).invokeDoItemUse();
    apply(
        minecraftClient.player.getInventory().getSelectedSlot() == selectedSlot,
        "input callback never switches slots");
    ClientInitializer.getModuleManager().anchorMacroModule.onTick();
  }

  public static void apply(AnchorMacroModule anchorMacroModule, int intValue) {
    ProtectionPerformanceTest.ensureInitialized();

    for (int index = 0; index < intValue; index++) {
      anchorMacroModule.onTick();
    }
  }

  public static void apply3(
      MinecraftClient minecraftClient,
      HoppinStandingBeforeTestSupport15 hoppinStandingBeforeTestSupport15) {
    AnchorMacroModule anchorMacroModule = ClientInitializer.getModuleManager().anchorMacroModule;
    ProtectionPerformanceTest.ensureInitialized();

    for (boolean flag : new boolean[] {false, true}) {
      apply(minecraftClient, hoppinStandingBeforeTestSupport15, 0);
      apply2(minecraftClient);
      apply(
          minecraftClient.player.getInventory().getSelectedSlot() == 1,
          "disable fixture just charged");
      anchorMacroModule.setEnabled(false);
      apply(
          minecraftClient.player.getInventory().getSelectedSlot() == 1,
          "disable cannot switch immediately after use");
      if (flag) {
        minecraftClient.player.getInventory().setSelectedSlot(7);
      }

      ClientInitializer.getModuleManager().update3();
      apply(
          minecraftClient.player.getInventory().getSelectedSlot() == (flag ? 7 : 2),
          "disabled module restores at next combat tick and respects manual slot");
      apply(
          hoppinStandingBeforeTestSupport15.list.equals(List.of(1)),
          "disable never detonates the charged anchor");
    }
  }

  public static Channel getChannel(
      MinecraftClient minecraftClient, List list, List list2, List list3) {
    try {
      Field declaredField =
          bunnythekenzo.client.runtime.MappedReflection.field(
              ClientConnection.class, "channel", "field_11651", "Lio/netty/channel/Channel;");
      declaredField.setAccessible(true);
      Channel channel =
          (Channel) declaredField.get(minecraftClient.getNetworkHandler().getConnection());
      channel
          .pipeline()
          .addBefore(
              "packet_handler",
              "anchor-test-observer",
              new AnchorPacketOrderObserver(list3, list, list2));
      return channel;
    } catch (ReflectiveOperationException failure) {
      throw new AssertionError(failure);
    }
  }

  public static void apply(boolean flag, String text) {
    if (!flag) {
      throw new AssertionError("Anchor Macro: " + text);
    }
  }
}
