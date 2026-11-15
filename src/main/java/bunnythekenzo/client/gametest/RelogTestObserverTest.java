package bunnythekenzo.client.gametest;

import com.google.gson.JsonObject;
import io.netty.channel.Channel;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;
import net.minecraft.block.Blocks;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.input.KeyInput;
import net.minecraft.client.input.MouseInput;
import net.minecraft.client.network.ClientCommonNetworkHandler;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.network.ServerInfo.ServerType;
import net.minecraft.client.option.SimpleOption.ValidatingIntSliderCallbacks;
import net.minecraft.entity.Entity.RemovalReason;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.JsonSupport2;
import bunnythekenzo.client.PearlEscapeAutomation;
import bunnythekenzo.client.mixin.MinecraftClientAccessor;
import bunnythekenzo.client.module.Module;
import bunnythekenzo.client.setting.ChoiceSettingView;
import bunnythekenzo.client.setting.SettingEntry;

public final class RelogTestObserverTest {

  public static void run(ClientGameTestContext clientGameTestContext) {
    ProtectionPerformanceTest.ensureInitialized();

    TestSingleplayerContext testSingleplayerContext = clientGameTestContext.worldBuilder().create();
    testSingleplayerContext.getClientWorld().waitForChunksRender();
    clientGameTestContext.runOnClient(
        minecraftClient -> {
          minecraftClient.options.pauseOnLostFocus = false;
        });
    JsonObject onClient =
        clientGameTestContext.computeOnClient(
            minecraftClient2 -> {
              return ClientInitializer.getClientConfig().getJsonObject();
            });
    JsonObject onClient2 =
        clientGameTestContext.computeOnClient(
            minecraftClient3 -> {
              return JsonSupport2.getJsonObject();
            });
    int onClient3 =
        clientGameTestContext.computeOnClient(
            minecraftClient4 -> {
              return minecraftClient4.options.getViewDistance().getValue();
            });
    AtomicInteger atomicInteger = new AtomicInteger();
    CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();
    CopyOnWriteArrayList copyOnWriteArrayList2 = new CopyOnWriteArrayList();
    CopyOnWriteArrayList copyOnWriteArrayList3 = new CopyOnWriteArrayList();
    CopyOnWriteArrayList copyOnWriteArrayList4 = new CopyOnWriteArrayList();
    AtomicBoolean atomicBoolean = new AtomicBoolean();
    CopyOnWriteArrayList copyOnWriteArrayList5 = new CopyOnWriteArrayList();

    try {
      testSingleplayerContext
          .getServer()
          .runOnServer(
              minecraftServer -> {
                ProtectionPerformanceTest.ensureInitialized();

                minecraftServer
                    .getCommandManager()
                    .getDispatcher()
                    .register(
                        CommandManager.literal("rtp")
                            .executes(
                                commandContext -> {
                                  ProtectionPerformanceTest.ensureInitialized();
                                  if (atomicInteger.get() < 2) {
                                    ServerPlayerEntity playerOrThrow =
                                        commandContext.getSource().getPlayerOrThrow();
                                    var enderPearls = playerOrThrow.getEnderPearls();
                                    boolean flag =
                                        enderPearls.stream()
                                            .anyMatch(
                                                enderPearlEntity -> {
                                                  ProtectionPerformanceTest.ensureInitialized();
                                                  return !((net.minecraft.entity.projectile.thrown
                                                                  .EnderPearlEntity)
                                                              (enderPearlEntity))
                                                          .isRemoved()
                                                      && ((net.minecraft.entity.projectile.thrown
                                                                      .EnderPearlEntity)
                                                                  (enderPearlEntity))
                                                              .getVelocity()
                                                              .y
                                                          > 0.0;
                                                });
                                    String text =
                                        String.valueOf(
                                            enderPearls.stream()
                                                .map(
                                                    enderPearlEntity2 -> {
                                                      return ((net.minecraft.entity.projectile
                                                                  .thrown.EnderPearlEntity)
                                                              (enderPearlEntity2))
                                                          .getVelocity()
                                                          .toString();
                                                    })
                                                .toList());
                                    boolean flag2 =
                                        playerOrThrow.networkHandler.canInteractWithGame();
                                    String text2 = String.valueOf(playerOrThrow.getOffHandStack());
                                    boolean coolingDown =
                                        playerOrThrow
                                            .getItemCooldownManager()
                                            .isCoolingDown(playerOrThrow.getOffHandStack());
                                    String text3 = text2;
                                    boolean flag3 = flag2;
                                    String text4 = text;
                                    apply(
                                        flag,
                                        "RTP arrives while the just-thrown pearl is still rising: "
                                            + text4
                                            + ", loaded="
                                            + flag3
                                            + ", offhand="
                                            + text3
                                            + ", cooldown="
                                            + coolingDown);
                                    playerOrThrow.requestTeleport(1024.5, 120.0, 0.5);
                                  }

                                  return atomicInteger.incrementAndGet();
                                }));

                for (int index = -6; index <= 6; index++) {
                  for (int index2 = -6; index2 <= 6; index2++) {
                    minecraftServer
                        .getOverworld()
                        .setBlockState(
                            new BlockPos(index, 119, index2), Blocks.STONE.getDefaultState());
                    minecraftServer
                        .getOverworld()
                        .setBlockState(
                            new BlockPos(index + 1024, 119, index2),
                            Blocks.STONE.getDefaultState());
                  }
                }

                ServerPlayerEntity first =
                    minecraftServer.getPlayerManager().getPlayerList().getFirst();
                first.getInventory().setStack(0, new ItemStack(Items.DIRT));
                first.getInventory().setStack(1, new ItemStack(Items.ENDER_PEARL, 16));
                first.getInventory().setSelectedSlot(0);
                first.playerScreenHandler.sendContentUpdates();
                minecraftServer.getCommandManager().sendCommandTree(first);
              });
      testSingleplayerContext.getServer().runCommand("tp @a 0.5 120 0.5");
      clientGameTestContext.waitTicks(20);
      clientGameTestContext.runOnClient(
          minecraftClient5 -> {
            ProtectionPerformanceTest.ensureInitialized();

            for (Module module :
                (java.lang.Iterable<Module>)
                    (java.lang.Iterable<?>) (ClientInitializer.getModuleManager().getList())) {
              module.setEnabled(false);
            }

            apply(
                minecraftClient5,
                copyOnWriteArrayList,
                copyOnWriteArrayList2,
                copyOnWriteArrayList3,
                copyOnWriteArrayList5);

            ClientTickEvents.END_CLIENT_TICK.register(
                minecraftClient6 -> {
                  if (atomicBoolean.get() && minecraftClient6.player != null) {
                    copyOnWriteArrayList4.add(minecraftClient6.player.getPitch());
                  }
                });

            select("Trigger", "Keybind");

            select("Method", "Pearl + RTP");
            PearlEscapeAutomation.getGuiModuleEntry().apply(75);
            PearlEscapeAutomation.getGuiModuleEntry().apply(true);
            minecraftClient5.setScreen(null);
            minecraftClient5.player.setPitch(17.0F);
            apply3(minecraftClient5);
            atomicBoolean.set(true);
            apply(minecraftClient5, 1);
            apply(
                getString().equals("PEARL_PREPARE"),
                "keyboard dispatch starts the selected pearl method");
            apply(minecraftClient5, 2);
            apply(getString().equals("PEARL_PREPARE"), "held key does not restart the method");
          });
      clientGameTestContext.waitTicks(5);
      clientGameTestContext.runOnClient(
          minecraftClient7 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                atomicInteger.get() == 0 && copyOnWriteArrayList.isEmpty(),
                "aiming does not throw or send RTP early");
            apply(
                getString().equals("PEARL_PREPARE")
                    && minecraftClient7.player.getPitch() > -90.0F
                    && minecraftClient7.player.getPitch() < 17.0F,
                "upward aim progresses over multiple normal ticks");
          });
      clientGameTestContext.waitFor(
          minecraftClient8 -> {
            ProtectionPerformanceTest.ensureInitialized();
            return getString().equals("PEARL_WAIT_LANDING") && !(Boolean) getObject("enabled5");
          },
          200);
      clientGameTestContext.runOnClient(
          minecraftClient9 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(atomicInteger.get() == 1, "throw sends RTP immediately and exactly once");
            apply(
                minecraftClient9.player.getX() > 1000.0
                    && minecraftClient9.world.getChunkManager().isChunkLoaded(64, 0),
                "RTP relocates the player and loads destination chunks before waiting for landing");
            apply(
                minecraftClient9.player.getInventory().getSelectedSlot() == 0
                    && minecraftClient9.player.getPitch() == 17.0F,
                "throw restores the selected slot and smoothly restores the pitch");
            apply(
                copyOnWriteArrayList5.equals(List.of("use", "rtp")),
                "use-item packet precedes exactly one RTP command");
            atomicBoolean.set(false);
            apply(
                copyOnWriteArrayList3.contains(-90.0F),
                "normal movement packets reach the vertical throw angle");
            apply(copyOnWriteArrayList4);
            apply(!PearlEscapeAutomation.test(), "pearl mode never pauses for relog");
            apply(
                copyOnWriteArrayList.size() == 1 && (Integer) copyOnWriteArrayList.getFirst() > 0,
                "one pearl use packet with a vanilla prediction sequence");
            apply(minecraftClient9, 1);
            apply(
                getString().equals("PEARL_WAIT_LANDING") && copyOnWriteArrayList.size() == 1,
                "another press while running cannot throw a second pearl");
          });
      clientGameTestContext.waitFor(
          minecraftClient10 -> {
            return getString().equals("IDLE");
          },
          300);
      clientGameTestContext.runOnClient(
          minecraftClient11 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                Math.abs(minecraftClient11.player.getX()) < 32.0,
                "pearl landing returns the player to the throw area and completes relock");
          });
      apply(atomicInteger.get() == 1, "landing completes without sending a second RTP");
      clientGameTestContext.waitTicks(10);
      apply(atomicInteger.get() == 1, "completed method does not send RTP again");
      testSingleplayerContext.getClientWorld().waitForChunksRender();
      clientGameTestContext.waitTicks(20);
      testSingleplayerContext
          .getServer()
          .runOnServer(
              minecraftServer2 -> {
                ServerPlayerEntity first2 =
                    minecraftServer2.getPlayerManager().getPlayerList().getFirst();
                first2.getInventory().setStack(1, ItemStack.EMPTY);
                first2.playerScreenHandler.sendContentUpdates();
              });
      clientGameTestContext.waitTicks(3);
      clientGameTestContext.runOnClient(
          minecraftClient12 -> {
            ProtectionPerformanceTest.ensureInitialized();

            apply(minecraftClient12, 1);
            apply(
                getString().equals("IDLE")
                    && minecraftClient12.getNetworkHandler().getConnection().isOpen(),
                "missing pearls cancel without disconnecting");
            apply(copyOnWriteArrayList.size() == 1, "missing pearls send no use packets");
          });
      testSingleplayerContext
          .getServer()
          .runOnServer(
              minecraftServer3 -> {
                ServerPlayerEntity first3 =
                    minecraftServer3.getPlayerManager().getPlayerList().getFirst();
                first3.setStackInHand(Hand.OFF_HAND, new ItemStack(Items.ENDER_PEARL, 16));
                first3.playerScreenHandler.sendContentUpdates();
              });
      clientGameTestContext.waitTicks(3);
      clientGameTestContext.runOnClient(
          minecraftClient13 -> {
            apply(minecraftClient13, 1);
          });
      clientGameTestContext.waitFor(
          minecraftClient14 -> {
            ProtectionPerformanceTest.ensureInitialized();
            return getString().equals("PEARL_WAIT_LANDING") && !(Boolean) getObject("enabled5");
          },
          200);
      clientGameTestContext.runOnClient(
          minecraftClient15 -> {
            ProtectionPerformanceTest.ensureInitialized();

            apply(getString().equals("PEARL_WAIT_LANDING"), "offhand pearl is thrown and tracked");
            apply(
                minecraftClient15.player.getInventory().getSelectedSlot() == 0
                    && minecraftClient15.player.getPitch() == 17.0F,
                "offhand throw preserves the hotbar and restores the view");
          });
      clientGameTestContext.waitFor(
          minecraftClient16 -> {
            return getString().equals("IDLE");
          },
          300);
      apply(
          atomicInteger.get() == 2 && copyOnWriteArrayList.size() == 2,
          "offhand also sends one RTP before landing");
      clientGameTestContext.runOnClient(
          minecraftClient17 -> {
            select("Method", "Render Distance");
            copyOnWriteArrayList2.clear();
            PearlEscapeAutomation.getGuiModuleEntry().apply(3);
            ProtectionPerformanceTest.ensureInitialized();

            apply5(minecraftClient17);
            PearlEscapeAutomation.getGuiModuleEntry().apply(75);
            apply(
                minecraftClient17.options.getViewDistance().getValue() == 2,
                "render distance starts at minimum");
            apply(!PearlEscapeAutomation.test(), "render distance does not pause or relog");
          });
      clientGameTestContext.waitTicks(5);
      clientGameTestContext.runOnClient(
          minecraftClient18 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                minecraftClient18.options.getViewDistance().getValue() == 2
                    && getString().equals("RENDER_DISTANCE_LOW"),
                "minimum distance gets time to apply before raising it");
          });
      clientGameTestContext.waitFor(
          minecraftClient19 -> {
            return getString().equals("IDLE");
          },
          200);
      clientGameTestContext.waitTicks(2);
      clientGameTestContext.runOnClient(
          minecraftClient20 -> {
            ProtectionPerformanceTest.ensureInitialized();

            int intValue =
                ((ValidatingIntSliderCallbacks)
                        minecraftClient20.options.getViewDistance().getCallbacks())
                    .maxInclusive();
            apply(
                minecraftClient20.options.getViewDistance().getValue() == intValue
                    && minecraftClient20.getNetworkHandler().getConnection().isOpen(),
                "render distance ends at maximum while staying connected");
            apply(
                copyOnWriteArrayList2.equals(List.of(2, intValue)),
                "server receives exactly minimum then maximum distance");
            apply(
                copyOnWriteArrayList.size() == 2,
                "render distance sends no item interaction sequences");
            minecraftClient20.options.getViewDistance().setValue(8);
            apply(minecraftClient20, 1);
            PearlEscapeAutomation.getGuiModuleEntry().apply(false);
            apply("apply", minecraftClient20);
            apply(
                minecraftClient20.options.getViewDistance().getValue() == 8,
                "cancelling restores the original distance");

            PearlEscapeAutomation.getGuiModuleEntry().apply(true);

            apply4(minecraftClient20);
            select("Method", "Pearl + RTP");
            apply(minecraftClient20, 1);

            for (int index3 = 0; index3 < 40 && getString().equals("PEARL_PREPARE"); index3++) {
              apply("count6", Integer.valueOf(1));
              apply("apply9", minecraftClient20);
            }

            apply("count6", Integer.valueOf(1));
            apply("apply10", minecraftClient20);
            ArmorStandEntity armorStandEntity =
                new ArmorStandEntity(EntityType.ARMOR_STAND, minecraftClient20.world);
            EnderPearlEntity enderPearlEntity3 =
                new EnderPearlEntity(
                    minecraftClient20.world, armorStandEntity, new ItemStack(Items.ENDER_PEARL));
            enderPearlEntity3.setPosition(minecraftClient20.player.getEntityPos());
            enderPearlEntity3.setId(-67001);
            minecraftClient20.world.addEntity(enderPearlEntity3);
            apply(getObject("uUID") == null, "another player's nearby pearl is ignored");
            minecraftClient20.world.removeEntity(
                enderPearlEntity3.getId(), RemovalReason.DISCARDED);
            EnderPearlEntity enderPearlEntity4 =
                new EnderPearlEntity(
                    minecraftClient20.world,
                    minecraftClient20.player,
                    new ItemStack(Items.ENDER_PEARL));
            enderPearlEntity4.setPosition(minecraftClient20.player.getEntityPos());
            enderPearlEntity4.setId(-67002);
            minecraftClient20.world.addEntity(enderPearlEntity4);
            apply(
                enderPearlEntity4.getUuid().equals(getObject("uUID")),
                "own pearl is tracked at spawn");
            minecraftClient20.world.removeEntity(
                enderPearlEntity4.getId(), RemovalReason.DISCARDED);
            apply("apply11", minecraftClient20);
            apply(
                getString().equals("PEARL_WAIT_RTP"),
                "entity removal alone cannot complete the relock");
            PearlEscapeAutomation.update();
            apply(
                getString().equals("PEARL_WAIT_RTP"),
                "a correction near the throw location is not RTP");
            apply("timestamp4", System.nanoTime() - 4000000000L);
            apply("apply11", minecraftClient20);
            apply(
                getString().equals("IDLE") && !PearlEscapeAutomation.test(),
                "unconfirmed landing cancels without relog");
            apply(minecraftClient20);
          });
      clientGameTestContext.runOnClient(RelogTestObserverTest::apply2);
      clientGameTestContext.waitFor(
          minecraftClient21 -> {
            return minecraftClient21.world == null;
          },
          200);
      apply(
          copyOnWriteArrayList.size() == 3
              && (Integer) copyOnWriteArrayList.get(1) > (Integer) copyOnWriteArrayList.getFirst()
              && (Integer) copyOnWriteArrayList.get(2) > (Integer) copyOnWriteArrayList.get(1),
          "successive pearl uses preserve increasing prediction sequences across render-distance"
              + " changes");
      ClientInitializer.logger.info(
          "Relog methods verified: smooth upward aim and restore, RTP immediately after throw,"
              + " destination chunks before pearl return, unload/correction/timeout guards,"
              + " hotbar/offhand, increasing packet sequences, keybind/Y triggers, render distance,"
              + " cancellation and delayed reconnect");
    } finally {
      atomicBoolean.set(false);
      clientGameTestContext.runOnClient(
          minecraftClient22 -> {
            PearlEscapeAutomation.getGuiModuleEntry().apply(false);
            apply("apply", minecraftClient22);
            minecraftClient22.options.getViewDistance().setValue(onClient3);
            minecraftClient22.options.write();
            ClientInitializer.getClientConfig().apply(onClient);
            JsonSupport2.apply(onClient2);
          });
      if (clientGameTestContext.computeOnClient(
          minecraftClient23 -> {
            return minecraftClient23.world != null;
          })) {
        testSingleplayerContext.close();
      }

      clientGameTestContext.setScreen(TitleScreen::new);
    }
  }

  public static void apply(List list) {
    ProtectionPerformanceTest.ensureInitialized();
    apply(
        list.contains(-90.0F) && list.size() > 10,
        "camera reaches vertical through multiple ticks");

    for (int index = 1; index < list.size(); index++) {
      apply(
          Math.abs((Float) list.get(index) - (Float) list.get(index - 1)) <= 12.01F,
          "aim and restore never snap the camera between client ticks: " + list);
    }
  }

  public static void apply(MinecraftClient minecraftClient) {
    Vec3d entityPos = minecraftClient.player.getEntityPos();
    apply("registryKey", minecraftClient.world.getRegistryKey());
    ProtectionPerformanceTest.ensureInitialized();

    apply("value", entityPos.x);
    apply("value3", entityPos.z);
    apply("PEARL_WAIT_RTP");
    minecraftClient.player.setPosition(32000.5, entityPos.y, 32000.5);
    PearlEscapeAutomation.update();
    apply(
        getString().equals("PEARL_WAIT_CHUNKS"),
        "teleport alone does not imply destination chunks loaded");
    apply("apply11", minecraftClient);
    apply(getString().equals("PEARL_WAIT_CHUNKS"), "unloaded destination continues waiting");
    minecraftClient.player.setPosition(entityPos);
    PearlEscapeAutomation.update();
    apply(getString().equals("IDLE"), "return before destination chunks cannot report success");
    apply("registryKey", minecraftClient.world.getRegistryKey());
    apply("value", entityPos.x);
    apply("value3", entityPos.z);
    apply("PEARL_WAIT_LANDING");
    apply("timestamp4", System.nanoTime() - 10000000000L);
    minecraftClient.player.setPosition(1024.5, entityPos.y, 0.5);
    apply("apply11", minecraftClient);
    apply(
        getString().equals("PEARL_WAIT_LANDING"),
        "pearl unloading after RTP does not fail after three seconds");
    PearlEscapeAutomation.update();
    apply(!(Boolean) getObject("enabled6"), "destination corrections are not pearl landings");
    minecraftClient.player.setPosition(entityPos);
    apply("apply11", minecraftClient);
    apply(
        getString().equals("PEARL_WAIT_LANDING"), "local movement alone cannot confirm a landing");
    PearlEscapeAutomation.update();
    apply("apply11", minecraftClient);
    apply(
        getString().equals("IDLE"),
        "server-confirmed return completes even without a tracked pearl");

    apply("PEARL_WAIT_RTP");
    apply("count6", Integer.valueOf(600));
    apply("apply", minecraftClient);
    apply(
        getString().equals("IDLE"),
        "a rejected RTP eventually cancels without retrying the command");
  }

  public static void apply(String text) {
    apply("pearlEscapeState", Enum.valueOf((Class) getObject("pearlEscapeState").getClass(), text));
  }

  public static void apply2(MinecraftClient minecraftClient) {
    ProtectionPerformanceTest.ensureInitialized();

    try {
      Field declaredField =
          bunnythekenzo.client.runtime.MappedReflection.field(
              ClientCommonNetworkHandler.class,
              "serverInfo",
              "field_45590",
              "Lnet/minecraft/class_642;");
      declaredField.setAccessible(true);
      ClientPlayNetworkHandler networkHandler = minecraftClient.getNetworkHandler();
      Object value = declaredField.get(networkHandler);
      declaredField.set(
          networkHandler, new ServerInfo("Local timing test", "127.0.0.1:9", ServerType.OTHER));

      try {
        PearlEscapeAutomation.getGuiModuleEntry().apply(true);
        select("Method", "Relog");
        minecraftClient.setScreen(null);
        minecraftClient.options.forwardKey.setPressed(true);
        minecraftClient.options.attackKey.setPressed(true);
        apply(minecraftClient, 1);
        apply(
            minecraftClient.currentScreen instanceof GameMenuScreen && PearlEscapeAutomation.test(),
            "relog opens the pause menu before disconnecting");
        apply(
            !minecraftClient.options.forwardKey.isPressed()
                && !minecraftClient.options.attackKey.isPressed(),
            "held inputs are released");
        minecraftClient.setScreen(null);
        ClientInitializer.getModuleManager().autoWalkModule.setEnabled(true);
        ClientInitializer.getModuleManager().update2();
        apply(!minecraftClient.options.forwardKey.isPressed(), "automatic movement also pauses");
        apply(
            !((MinecraftClientAccessor) (Object) (minecraftClient)).invokeDoAttack(),
            "closing menu does not unblock attacks");
        minecraftClient.options.forwardKey.setPressed(true);
        minecraftClient.player.input.tick();
        apply(
            !minecraftClient.player.input.playerInput.forward(),
            "movement remains blocked after closing menu");
        apply("timestamp3", System.nanoTime() - 900000000L);
        apply("apply", minecraftClient);
        apply(
            getString().equals("RELOG_PAUSE") && minecraftClient.getNetworkHandler() != null,
            "client stays connected until a full second of pause has elapsed");
        apply(
            (Long) getObject("MIN_LANDING_WAIT_NANOS") >= 1000000000L,
            "reconnect also allows at least one second for the old session to close");
        PearlEscapeAutomation.getGuiModuleEntry().apply(false);
        apply("apply", minecraftClient);
        apply(
            !PearlEscapeAutomation.test() && getString().equals("IDLE"),
            "disable cancels pending relog");
        ClientInitializer.getModuleManager().update2();
        apply(
            minecraftClient.options.forwardKey.isPressed(),
            "automatic movement resumes after cancellation");
        ClientInitializer.getModuleManager().autoWalkModule.setEnabled(false);
        PearlEscapeAutomation.getGuiModuleEntry().apply(true);
        apply(minecraftClient, 1);
        ClientConnection connection = networkHandler.getConnection();
        apply("timestamp3", System.nanoTime() - 1100000000L);
        apply("apply", minecraftClient);
        apply(!connection.isOpen(), "relog closes the network connection before world teardown");
        apply("timestamp3", System.nanoTime() - 900000000L);
        apply("apply", minecraftClient);
        apply(
            getString().equals("RELOG_WAIT_CONNECT"),
            "reconnect waits a full second after disconnect");
        PearlEscapeAutomation.getGuiModuleEntry().apply(false);
        apply("apply", minecraftClient);
        apply(getString().equals("IDLE"), "pending reconnect can be cancelled without connecting");
      } finally {
        declaredField.set(networkHandler, value);
      }
    } catch (ReflectiveOperationException failure) {
      throw new AssertionError(failure);
    }
  }

  public static void apply3(MinecraftClient minecraftClient) {
    ProtectionPerformanceTest.ensureInitialized();

    double y = minecraftClient.player.getY();
    minecraftClient.player.setPosition(
        minecraftClient.player.getX(), -40.0, minecraftClient.player.getZ());
    apply("apply", minecraftClient);
    apply(
        getString().equals("IDLE") && !(Boolean) getObject("enabled3"),
        "manual mode never starts at low Y");

    minecraftClient.player.setPosition(
        minecraftClient.player.getX(), y, minecraftClient.player.getZ());

    apply(!PearlEscapeAutomation.test(74), "unbound key passes through");

    PearlEscapeAutomation.getGuiModuleEntry().apply(false);

    apply(!PearlEscapeAutomation.test(75), "disabled module ignores the action key");
    PearlEscapeAutomation.getGuiModuleEntry().apply(true);
    minecraftClient.setScreen(new GameMenuScreen(true));
    apply(minecraftClient, 1);
    apply(
        getString().equals("IDLE") && minecraftClient.currentScreen instanceof GameMenuScreen,
        "key cannot trigger from an open menu");
    minecraftClient.setScreen(null);
    JsonObject jsonObject = JsonSupport2.getJsonObject();
    PearlEscapeAutomation.getGuiModuleEntry().apply(-1);

    select("Trigger", "Random Y");

    select("Method", "Relog");
    JsonSupport2.apply(jsonObject);
    apply(
        PearlEscapeAutomation.getGuiModuleEntry().computeInt() == 75
            && ((ChoiceSettingView) getObject("triggerControl"))
                .getSelectedValue()
                .equals("Keybind")
            && ((ChoiceSettingView) getObject("methodControl"))
                .getSelectedValue()
                .equals("Pearl + RTP"),
        "keybind, trigger and selected method survive config round trip");
  }

  public static void apply4(MinecraftClient minecraftClient) {
    double y = minecraftClient.player.getY();
    select("Trigger", "Random Y");
    minecraftClient.player.setPosition(
        minecraftClient.player.getX(), -40.0, minecraftClient.player.getZ());
    apply("apply", minecraftClient);
    apply(getString().equals("IDLE"), "enabling below the range never starts a reconnect loop");
    minecraftClient.player.setPosition(
        minecraftClient.player.getX(), 10.0, minecraftClient.player.getZ());
    apply("apply", minecraftClient);
    apply((Boolean) getObject("enabled3"), "automatic mode arms above its range");
    minecraftClient.player.setPosition(
        minecraftClient.player.getX(), -40.0, minecraftClient.player.getZ());
    apply("apply", minecraftClient);
    apply(
        getString().equals("RENDER_DISTANCE_LOW"), "crossing the Y range runs the selected method");
    PearlEscapeAutomation.getGuiModuleEntry().apply(false);
    apply("apply", minecraftClient);
    PearlEscapeAutomation.getGuiModuleEntry().apply(true);
    apply("apply", minecraftClient);
    apply(
        getString().equals("IDLE"),
        "automatic mode stays disarmed below its range after cancellation");
    minecraftClient.player.setPosition(
        minecraftClient.player.getX(), y, minecraftClient.player.getZ());
    select("Trigger", "Keybind");
  }

  public static void select(String text, String text2) {
    SettingEntry settingEntry =
        (bunnythekenzo.client.setting.SettingEntry)
            (PearlEscapeAutomation.getGuiModuleEntry().getList().stream()
                .flatMap(
                    collectionData -> {
                      return ((bunnythekenzo.client.CollectionData) (collectionData)).list().stream();
                    })
                .filter(
                    settingEntry2 -> {
                      return ((bunnythekenzo.client.setting.SettingEntry) (settingEntry2))
                          .getName()
                          .equals(text);
                    })
                .findFirst()
                .orElseThrow());
    ChoiceSettingView choiceSettingView = (ChoiceSettingView) settingEntry;
    choiceSettingView.selectIndex(choiceSettingView.getChoices().indexOf(text2));
  }

  public static void apply(MinecraftClient minecraftClient, int intValue) {
    ProtectionPerformanceTest.ensureInitialized();

    try {
      Method declaredMethod =
          bunnythekenzo.client.runtime.MappedReflection.method(
              Keyboard.class,
              "onKey",
              "method_1466",
              "(JILnet/minecraft/class_11908;)V",
              long.class,
              int.class,
              KeyInput.class);
      declaredMethod.setAccessible(true);
      declaredMethod.invoke(
          minecraftClient.keyboard,
          minecraftClient.getWindow().getHandle(),
          intValue,
          new KeyInput(75, 0, 0));
      if (intValue == 1) {
        declaredMethod.invoke(
            minecraftClient.keyboard,
            minecraftClient.getWindow().getHandle(),
            0,
            new KeyInput(75, 0, 0));
      }
    } catch (ReflectiveOperationException failure) {
      throw new AssertionError(failure);
    }
  }

  public static void apply(
      MinecraftClient minecraftClient, List list, List list2, List list3, List list4) {
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
              "relog-test-observer",
              new PearlPacketObserver(list, list4, list3, list2));
    } catch (ReflectiveOperationException failure) {
      throw new AssertionError(failure);
    }
  }

  public static void apply5(MinecraftClient minecraftClient) {
    try {
      Method declaredMethod =
          bunnythekenzo.client.runtime.MappedReflection.method(
              Mouse.class,
              "onMouseButton",
              "method_1601",
              "(JLnet/minecraft/class_11910;I)V",
              long.class,
              MouseInput.class,
              int.class);
      declaredMethod.setAccessible(true);
      MouseInput mouseInput = new MouseInput(3, 0);
      declaredMethod.invoke(
          minecraftClient.mouse, minecraftClient.getWindow().getHandle(), mouseInput, 1);
      declaredMethod.invoke(
          minecraftClient.mouse, minecraftClient.getWindow().getHandle(), mouseInput, 0);
    } catch (ReflectiveOperationException failure) {
      throw new AssertionError(failure);
    }
  }

  public static String getString() {
    return getObject("pearlEscapeState").toString();
  }

  public static Object getObject(String text) {
    try {
      Field field = getField(text);
      return field.get(null);
    } catch (ReflectiveOperationException failure) {
      throw new AssertionError(failure);
    }
  }

  public static void apply(String text, Object value) {
    try {
      getField(text).set(null, value);
    } catch (ReflectiveOperationException failure) {
      throw new AssertionError(failure);
    }
  }

  public static Field getField(String text) throws ReflectiveOperationException {
    Field declaredField = PearlEscapeAutomation.class.getDeclaredField(text);
    declaredField.setAccessible(true);
    return declaredField;
  }

  public static void apply(String text, MinecraftClient minecraftClient) {
    try {
      Method declaredMethod =
          PearlEscapeAutomation.class.getDeclaredMethod(text, MinecraftClient.class);
      declaredMethod.setAccessible(true);
      declaredMethod.invoke(null, minecraftClient);
    } catch (ReflectiveOperationException failure) {
      throw new AssertionError(failure);
    }
  }

  public static void apply(boolean flag, String text) {
    if (!flag) {
      throw new AssertionError("Relog methods: " + text);
    }
  }
}
