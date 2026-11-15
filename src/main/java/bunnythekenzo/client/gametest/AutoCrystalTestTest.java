package bunnythekenzo.client.gametest;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import io.netty.channel.Channel;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil.Key;
import net.minecraft.client.util.InputUtil.Type;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BlockPredicatesComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.Entity.RemovalReason;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.ClientConnection;
import net.minecraft.predicate.BlockPredicate;
import net.minecraft.predicate.component.ComponentsPredicate;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameMode;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.CrystalTargetStatus;
import bunnythekenzo.client.CrystalTargetStatusSupport;
import bunnythekenzo.client.mixin.ClientPlayerInteractionManagerAccessor;
import bunnythekenzo.client.mixin.KeyBindingAccessor;
import bunnythekenzo.client.mixin.MinecraftClientAccessor;
import bunnythekenzo.client.module.AimAssistModule;
import bunnythekenzo.client.module.AutoCrystalModule;
import bunnythekenzo.client.module.Module;

public final class AutoCrystalTestTest {
  public static final BlockPos blockPos = new BlockPos(0, 199, 3);
  public static final Box box = new Box(-8.0, 198.0, -8.0, 8.0, 208.0, 8.0);
  public final List list = new CopyOnWriteArrayList();
  public final AtomicInteger atomicInteger = new AtomicInteger();
  public final AtomicInteger atomicInteger2 = new AtomicInteger();
  public final AtomicReference atomicReference = new AtomicReference<>(Items.AIR);
  public Channel channel;
  public OtherClientPlayerEntity otherClientPlayerEntity;
  public volatile boolean enabled;
  public final List list2 = new CopyOnWriteArrayList();
  public final AtomicReference<Consumer<MinecraftClient>> atomicReference2 = new AtomicReference();

  public static void apply(
      ClientGameTestContext clientGameTestContext,
      TestSingleplayerContext testSingleplayerContext) {
    new AutoCrystalTestTest().verify(clientGameTestContext, testSingleplayerContext);
  }

  public void verify(
      ClientGameTestContext clientGameTestContext,
      TestSingleplayerContext testSingleplayerContext) {
    ProtectionPerformanceTest.ensureInitialized();
    boolean flag;
    JsonObject onClient =
        clientGameTestContext.computeOnClient(
            minecraftClient -> {
              return ClientInitializer.getClientConfig().getJsonObject();
            });
    Key onClient2 =
        clientGameTestContext.computeOnClient(
            minecraftClient2 -> {
              return ((KeyBindingAccessor) (Object) (minecraftClient2.options.useKey))
                  .getBoundKey();
            });

    try {
      clientGameTestContext.runOnClient(
          minecraftClient3 -> {
            ProtectionPerformanceTest.ensureInitialized();

            for (Module module :
                (java.lang.Iterable<Module>)
                    (java.lang.Iterable<?>) (ClientInitializer.getModuleManager().getList())) {
              module.setEnabled(false);
            }

            minecraftClient3.options.useKey.setBoundKey(Type.MOUSE.createFromCode(1));
            KeyBinding.updateKeysByCode();
            this.apply(minecraftClient3);
            ClientTickEvents.START_CLIENT_TICK.register(
                minecraftClient4 -> {
                  ProtectionPerformanceTest.ensureInitialized();
                  if (this.atomicReference2.get() != null
                      && this.channel != null
                      && this.channel.isOpen()) {
                    this.channel.eventLoop().submit(() -> {}).syncUninterruptibly();
                    if (this.count("crystal") > 0L) {
                      ((Consumer) this.atomicReference2.getAndSet(null)).accept(minecraftClient4);
                    }
                  }
                });
            ClientTickEvents.END_CLIENT_TICK.register(
                minecraftClient5 -> {
                  ProtectionPerformanceTest.ensureInitialized();
                  if (this.channel != null && this.channel.isOpen()) {
                    this.channel.eventLoop().submit(() -> {}).syncUninterruptibly();
                  }

                  this.atomicInteger.incrementAndGet();
                });
          });
      this.verifyDetectionAndAllowedActions(clientGameTestContext, testSingleplayerContext);
      this.verifyOptionalTarget(clientGameTestContext, testSingleplayerContext);
      this.verifyBlockedStatus(clientGameTestContext, testSingleplayerContext);
      this.verifyServerTargeting(clientGameTestContext, testSingleplayerContext);
      this.verifyManualPlacement(clientGameTestContext, testSingleplayerContext);
      this.verifyPacketOrder(clientGameTestContext, testSingleplayerContext);

      for (Item item :
          new Item[] {Items.DIAMOND_PICKAXE, Items.APPLE, Items.END_CRYSTAL, Items.AIR}) {
        this.prepare(clientGameTestContext, testSingleplayerContext, item, Blocks.STONE);
        hold(clientGameTestContext);
        clientGameTestContext.waitTicks(10);
        release(clientGameTestContext);
        apply(this.count("obsidian") == 0L, "Auto Obsidian ignores held " + item);
      }

      this.prepare(
          clientGameTestContext, testSingleplayerContext, Items.DIAMOND_SWORD, Blocks.STONE);
      clientGameTestContext.runOnClient(
          minecraftClient6 -> {
            getAutoCrystalModule().autoObsidianSetting.setValue(false);
          });
      hold(clientGameTestContext);
      clientGameTestContext.waitTicks(10);
      release(clientGameTestContext);
      apply(this.count("obsidian") == 0L, "Auto Obsidian toggle is respected");
      this.prepare(
          clientGameTestContext, testSingleplayerContext, Items.DIAMOND_SWORD, Blocks.STONE);
      BlockPos blockPos2 = blockPos.add(2, 0, 0);
      testSingleplayerContext
          .getServer()
          .runOnServer(
              minecraftServer -> {
                minecraftServer
                    .getOverworld()
                    .setBlockState(blockPos2, Blocks.STONE.getDefaultState());
              });
      clientGameTestContext.waitTicks(3);
      clientGameTestContext.runOnClient(
          minecraftClient7 -> {
            getAutoCrystalModule().obsidianDelaySetting.setNumber(Double.valueOf(20.0));
          });
      hold(clientGameTestContext);
      clientGameTestContext.waitTicks(2);
      apply(this.count("obsidian") == 1L, "first base places immediately");
      clientGameTestContext.runOnClient(
          minecraftClient8 -> {
            apply(minecraftClient8, Vec3d.ofBottomCenter(blockPos2.up()));
          });
      clientGameTestContext.waitTicks(4);
      apply(this.count("obsidian") == 1L, "Obsidian Delay also applies when aiming at a new base");
      clientGameTestContext.waitTicks(24);
      release(clientGameTestContext);
      apply(
          this.count("obsidian") >= 2L,
          "second base places when Obsidian Delay expires: " + this.list);
      this.apply("obsidian", 20);
      this.prepare(
          clientGameTestContext, testSingleplayerContext, Items.DIAMOND_SWORD, Blocks.STONE);
      testSingleplayerContext
          .getServer()
          .runOnServer(
              minecraftServer2 -> {
                ServerPlayerEntity first =
                    minecraftServer2.getPlayerManager().getPlayerList().getFirst();
                first.getInventory().setStack(1, ItemStack.EMPTY);
                first.playerScreenHandler.syncState();
              });
      clientGameTestContext.waitTicks(5);
      hold(clientGameTestContext);
      clientGameTestContext.waitTicks(8);
      release(clientGameTestContext);
      apply(
          this.count("obsidian") == 0L && this.count("crystal") == 0L,
          "missing crystals leave the hotbar and base alone");

      for (Item item2 : new Item[] {Items.DIAMOND_SWORD, Items.OBSIDIAN}) {
        this.prepare(clientGameTestContext, testSingleplayerContext, item2, Blocks.STONE);
        clientGameTestContext.runOnClient(
            minecraftClient9 -> {
              getAutoCrystalModule().breakDelaySetting.setNumber(Double.valueOf(4.0));
              getAutoCrystalModule().placeDelaySetting.setNumber(Double.valueOf(6.0));
              getAutoCrystalModule().obsidianDelaySetting.setNumber(Double.valueOf(3.0));
            });
        hold(clientGameTestContext);
        clientGameTestContext.waitTicks(65);
        release(clientGameTestContext);
        apply(
            this.count("obsidian") >= 1L
                && this.count("crystal") >= 2L
                && this.count("attack") >= 2L,
            "held "
                + item2
                + " repeatedly places the base, places crystals and breaks them: "
                + this.list);
        this.apply("crystal", 6);
        this.apply("attack", 4);
        this.apply("obsidian", 3);
        testSingleplayerContext
            .getServer()
            .runOnServer(
                minecraftServer3 -> {
                  apply(
                      minecraftServer3
                          .getOverworld()
                          .getBlockState(blockPos.up())
                          .isOf(Blocks.OBSIDIAN),
                      "server accepted the obsidian base");
                });
        clientGameTestContext.runOnClient(
            minecraftClient10 -> {
              ProtectionPerformanceTest.ensureInitialized();
              apply(
                  minecraftClient10.player.getInventory().getSelectedSlot() == 0,
                  "release restores original slot");
            });
        long size = this.list.size();
        clientGameTestContext.waitTicks(8);
        apply(this.list.size() == size, "release stops all crystal actions");
      }

      for (Block block : new Block[] {Blocks.OBSIDIAN, Blocks.BEDROCK}) {
        this.prepare(clientGameTestContext, testSingleplayerContext, Items.DIAMOND_SWORD, block);
        clientGameTestContext.runOnClient(
            minecraftClient11 -> {
              getAutoCrystalModule().autoObsidianSetting.setValue(false);
              getAutoCrystalModule().breakDelaySetting.setNumber(Double.valueOf(0.0));
              getAutoCrystalModule().placeDelaySetting.setNumber(Double.valueOf(0.0));
              getAutoCrystalModule().switchBackSetting.setValue(false);
            });
        hold(clientGameTestContext);
        clientGameTestContext.waitTicks(35);
        release(clientGameTestContext);
        apply(
            this.count("crystal") >= 2L
                && this.count("attack") >= 2L
                && this.count("obsidian") == 0L,
            "zero-delay cycle works on existing " + block + ": " + this.list);
        this.apply("crystal", 1);
        this.apply("attack", 1);
        clientGameTestContext.runOnClient(
            minecraftClient12 -> {
              ProtectionPerformanceTest.ensureInitialized();
              apply(
                  minecraftClient12.player.getInventory().getSelectedSlot() == 1,
                  "Switch Back off retains crystal slot");
            });
      }

      this.prepare(
          clientGameTestContext, testSingleplayerContext, Items.DIAMOND_SWORD, Blocks.OBSIDIAN);
      clientGameTestContext.runOnClient(
          minecraftClient13 -> {
            getAutoCrystalModule().breakRangeSetting.setNumber(Double.valueOf(1.0));
          });
      hold(clientGameTestContext);
      clientGameTestContext.waitTicks(15);
      apply(
          this.count("crystal") == 1L && this.count("attack") == 0L,
          "small Break Range leaves crystal intact");
      clientGameTestContext.runOnClient(
          minecraftClient14 -> {
            getAutoCrystalModule().breakRangeSetting.setNumber(Double.valueOf(3.0));
          });
      clientGameTestContext.waitTicks(20);
      release(clientGameTestContext);
      apply(this.count("attack") > 0L, "changing Break Range applies during a hold");
      this.prepare(
          clientGameTestContext, testSingleplayerContext, Items.DIAMOND_SWORD, Blocks.OBSIDIAN);
      testSingleplayerContext
          .getServer()
          .runOnServer(
              minecraftServer4 -> {
                ServerPlayerEntity first2 =
                    minecraftServer4.getPlayerManager().getPlayerList().getFirst();
                first2.changeGameMode(GameMode.SURVIVAL);
                first2.requestTeleport(0.5, 200.0, -1.0);
              });
      clientGameTestContext.waitTicks(10);
      clientGameTestContext.runOnClient(
          minecraftClient15 -> {
            apply(minecraftClient15, Vec3d.ofBottomCenter(blockPos.up()));
            getAutoCrystalModule().breakRangeSetting.setNumber(Double.valueOf(6.0));
          });
      testSingleplayerContext
          .getServer()
          .runOnServer(
              minecraftServer5 -> {
                minecraftServer5
                    .getOverworld()
                    .spawnEntity(
                        new EndCrystalEntity(minecraftServer5.getOverworld(), 0.5, 200.0, 3.5));
              });
      clientGameTestContext.waitTicks(10);
      hold(clientGameTestContext);
      clientGameTestContext.waitTicks(12);
      release(clientGameTestContext);
      apply(this.count("attack") == 0L, "Break Range 6 never extends survival reach");
      this.prepare(
          clientGameTestContext, testSingleplayerContext, Items.DIAMOND_SWORD, Blocks.OBSIDIAN);
      testSingleplayerContext
          .getServer()
          .runOnServer(
              minecraftServer6 -> {
                minecraftServer6
                    .getOverworld()
                    .setBlockState(blockPos.up(), Blocks.STONE.getDefaultState());
              });
      clientGameTestContext.waitTicks(8);
      clientGameTestContext.runOnClient(
          minecraftClient16 -> {
            getAutoCrystalModule().autoObsidianSetting.setValue(false);
          });
      hold(clientGameTestContext);
      clientGameTestContext.waitTicks(10);
      release(clientGameTestContext);
      apply(
          this.count("crystal") == 0L && this.count("attack") == 0L,
          "obstructed crystal space is skipped");
      this.prepare(
          clientGameTestContext, testSingleplayerContext, Items.DIAMOND_SWORD, Blocks.OBSIDIAN);
      testSingleplayerContext
          .getServer()
          .runOnServer(
              minecraftServer7 -> {
                ServerPlayerEntity first3 =
                    minecraftServer7.getPlayerManager().getPlayerList().getFirst();
                first3.getInventory().setStack(1, ItemStack.EMPTY);
                first3.setStackInHand(Hand.OFF_HAND, new ItemStack(Items.END_CRYSTAL, 64));
                first3.playerScreenHandler.syncState();
              });
      clientGameTestContext.waitTicks(8);
      clientGameTestContext.runOnClient(
          minecraftClient17 -> {
            apply(
                minecraftClient17.player.getOffHandStack().isOf(Items.END_CRYSTAL),
                "server equipped offhand crystals");
          });
      hold(clientGameTestContext);
      clientGameTestContext.waitTicks(25);
      release(clientGameTestContext);
      flag = this.count("offhand") >= 2L && this.count("attack") >= 2L;
      String text = String.valueOf(this.list);
      String onClient3 =
          clientGameTestContext.computeOnClient(
              minecraftClient18 -> {
                String text2 = String.valueOf(minecraftClient18.player.getEntityPos());
                String text3 = String.valueOf(minecraftClient18.player.raycast(5.0, 1.0F, false));
                String text4 = String.valueOf(minecraftClient18.player.getOffHandStack());
                int size2 =
                    minecraftClient18
                        .world
                        .getNonSpectatingEntities(EndCrystalEntity.class, box)
                        .size();
                String text5 = text4;
                String text6 = text3;
                String text7 = text2;
                return "; pos="
                    + text7
                    + "; aim="
                    + text6
                    + "; hand="
                    + text5
                    + "; crystals="
                    + size2;
              });
      String text8 = text;
      apply(flag, "offhand crystals work without switching the sword: " + text8 + onClient3);
      clientGameTestContext.runOnClient(
          minecraftClient19 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                minecraftClient19.player.getInventory().getSelectedSlot() == 0,
                "offhand preserves selected slot");
          });
      this.prepare(
          clientGameTestContext, testSingleplayerContext, Items.DIAMOND_SWORD, Blocks.OBSIDIAN);
      clientGameTestContext.runOnClient(
          minecraftClient20 -> {
            getAutoCrystalModule().activateKeySetting.setValue(75);
          });
      clientGameTestContext.getInput().holdKey(75);
      clientGameTestContext.waitTicks(15);
      clientGameTestContext.getInput().releaseKey(75);
      clientGameTestContext.waitTicks(3);
      apply(
          this.count("crystal") > 0L && this.count("attack") > 0L,
          "custom keyboard activation works independently of Use Item binding");
      this.prepare(
          clientGameTestContext, testSingleplayerContext, Items.DIAMOND_SWORD, Blocks.OBSIDIAN);
      clientGameTestContext.runOnClient(
          minecraftClient21 -> {
            getAutoCrystalModule().activateKeySetting.setValue(75);
            minecraftClient21.options.useKey.setBoundKey(Type.KEYSYM.createFromCode(75));
            KeyBinding.updateKeysByCode();
          });
      hold(clientGameTestContext);
      clientGameTestContext.waitTicks(15);
      release(clientGameTestContext);
      apply(
          this.count("crystal") > 0L && this.count("attack") > 0L,
          "custom keyboard activation works");
      clientGameTestContext.runOnClient(
          minecraftClient22 -> {
            getAutoCrystalModule().activateKeySetting.setValue(-1);
          });
      this.list.clear();
      hold(clientGameTestContext);
      clientGameTestContext.waitTicks(8);
      release(clientGameTestContext);
      apply(this.count("attack") == 0L, "unbound activation never attacks");
      clientGameTestContext.runOnClient(
          minecraftClient23 -> {
            minecraftClient23.options.useKey.setBoundKey(Type.MOUSE.createFromCode(1));
            KeyBinding.updateKeysByCode();
          });

      for (String text9 : new String[] {"disable", "screen", "focus", "manual"}) {
        this.prepare(
            clientGameTestContext, testSingleplayerContext, Items.DIAMOND_SWORD, Blocks.OBSIDIAN);
        hold(clientGameTestContext);
        clientGameTestContext.waitTicks(8);
        clientGameTestContext.runOnClient(
            minecraftClient24 -> {
              ProtectionPerformanceTest.ensureInitialized();

              switch (text9) {
                case "disable":
                  getAutoCrystalModule().setEnabled(false);
                  break;
                case "screen":
                  minecraftClient24.setScreen(new GameMenuScreen(false));
                  break;
                case "focus":
                  minecraftClient24.onWindowFocusChanged(false);
                  break;
                case "manual":
                  minecraftClient24.player.getInventory().setSelectedSlot(3);
                  minecraftClient24.options.useKey.setPressed(false);
              }
            });
        clientGameTestContext.waitTicks(3);
        clientGameTestContext.runOnClient(
            minecraftClient25 -> {
              ProtectionPerformanceTest.ensureInitialized();
              apply(
                  minecraftClient25.player.getInventory().getSelectedSlot()
                      == (text9.equals("manual") ? 3 : 0),
                  text9 + " restores only the slot owned by the macro");
              apply(!getAutoCrystalModule().test4(), text9 + " releases vanilla input");
              minecraftClient25.options.useKey.setPressed(false);
              minecraftClient25.setScreen(null);
              minecraftClient25.onWindowFocusChanged(true);
            });
      }

      ClientInitializer.logger.info(
          "Auto Crystal verified: manual obsidian without damageable targets, target"
              + " distance/cover/immunity/loss, live server cycles, sword/obsidian gate, all"
              + " delays, zero-delay, range limits, offhand, custom activation, obstruction,"
              + " release/disable/menu/focus/manual-slot cleanup");
    } finally {
      this.enabled = false;
      this.atomicReference2.set(null);
      clientGameTestContext.getInput().releaseKey(75);
      clientGameTestContext.runOnClient(
          minecraftClient26 -> {
            minecraftClient26.options.useKey.setPressed(false);
            ProtectionPerformanceTest.ensureInitialized();

            getAutoCrystalModule().setEnabled(false);
            if (this.otherClientPlayerEntity != null) {
              minecraftClient26.world.removeEntity(
                  this.otherClientPlayerEntity.getId(), RemovalReason.DISCARDED);
            }

            minecraftClient26.options.useKey.setBoundKey(onClient2);
            KeyBinding.updateKeysByCode();
            minecraftClient26.setScreen(null);
            if (this.channel != null && this.channel.pipeline().get("auto-crystal-test") != null) {
              this.channel.pipeline().remove("auto-crystal-test");
            }

            ClientInitializer.getClientConfig().apply(onClient);
          });
    }
  }

  public void verifyDetectionAndAllowedActions(
      ClientGameTestContext clientGameTestContext,
      TestSingleplayerContext testSingleplayerContext) {
    ArrayList arrayList = new ArrayList();
    String[] textArray = new String[] {"covered", "creative"};
    ProtectionPerformanceTest.ensureInitialized();

    for (String text : textArray) {
      this.prepare(
          clientGameTestContext, testSingleplayerContext, Items.DIAMOND_SWORD, Blocks.STONE);
      if (text.equals("covered")) {
        testSingleplayerContext
            .getServer()
            .runOnServer(
                minecraftServer -> {
                  ProtectionPerformanceTest.ensureInitialized();

                  for (int index = 200; index <= 204; index++) {
                    for (int index2 = 1; index2 <= 5; index2++) {
                      minecraftServer
                          .getOverworld()
                          .setBlockState(
                              new BlockPos(2, index, index2), Blocks.BEDROCK.getDefaultState());
                    }
                  }
                });
        clientGameTestContext.waitTicks(4);
      }

      clientGameTestContext.runOnClient(
          minecraftClient -> {
            ProtectionPerformanceTest.ensureInitialized();
            if (text.equals("creative")) {
              this.apply(GameMode.CREATIVE);
            }

            apply(
                minecraftClient.world.getPlayers().size() == 2
                    && minecraftClient.world.getPlayers().contains(this.otherClientPlayerEntity),
                "both modules can find the same player");
            update();
            Vec3d vec3d = Vec3d.ofBottomCenter(blockPos.up(2));
            CrystalTargetStatus crystalTargetStatus =
                CrystalTargetStatusSupport.getCrystalTargetStatus(
                    minecraftClient, vec3d, 6.0, blockPos.up(), false);
            CrystalTargetStatus crystalTargetStatus2 =
                CrystalTargetStatusSupport.getCrystalTargetStatus(
                    minecraftClient, vec3d, 6.0, blockPos.up(), true);
            ClientInitializer.logger.info(
                "Auto Crystal detection comparison: {}, AimAssist=detected, basic={}, strict={}",
                text,
                crystalTargetStatus,
                crystalTargetStatus2);
            if (crystalTargetStatus != CrystalTargetStatus.READY) {
              arrayList.add(
                  text + " rejected the player with Damage Check off: " + crystalTargetStatus);
            }

            CrystalTargetStatus crystalTargetStatus3 =
                text.equals("covered") ? CrystalTargetStatus.COVERED : CrystalTargetStatus.IMMUNE;
            if (crystalTargetStatus2 != crystalTargetStatus3) {
              arrayList.add(
                  text
                      + " strict result should explain damage restriction: "
                      + crystalTargetStatus2);
            }
          });
      this.list2.clear();
      this.enabled = true;
      hold(clientGameTestContext);
      clientGameTestContext.waitTicks(40);
      release(clientGameTestContext);
      if (this.count("obsidian") != 1L || this.count("crystal") < 2L || this.count("attack") < 2L) {
        arrayList.add(text + " did not complete crystal cycles: " + this.list);
      }

      if (this.count("crystal") > 0L || this.count("attack") > 0L) {
        apply(
            this.list2.isEmpty(), text + " detection change preserves packet order: " + this.list2);
      }

      clientGameTestContext.runOnClient(
          minecraftClient2 -> {
            getAutoCrystalModule().damageCheckSetting.setValue(true);
            ProtectionPerformanceTest.ensureInitialized();

            minecraftClient2.options.useKey.setPressed(true);
            getAutoCrystalModule().onTick();
            apply(
                !getAutoCrystalModule().test4() && !getAutoCrystalModule().test(),
                "Damage Check on still releases actions for " + text);
            minecraftClient2.options.useKey.setPressed(false);
            this.apply(GameMode.SPECTATOR);
            apply(
                CrystalTargetStatusSupport.getCrystalTargetStatus(
                        minecraftClient2, Vec3d.ofBottomCenter(blockPos.up()), 6.0, null, false)
                    == CrystalTargetStatus.NO_PLAYER,
                "spectator is not a combat target");
            this.apply(GameMode.SURVIVAL);
            this.otherClientPlayerEntity.setHealth(0.0F);
            apply(
                CrystalTargetStatusSupport.getCrystalTargetStatus(
                        minecraftClient2, Vec3d.ofBottomCenter(blockPos.up()), 6.0, null, false)
                    == CrystalTargetStatus.NO_PLAYER,
                "dead player is not a combat target");
          });
    }

    for (boolean flag : new boolean[] {true, false}) {
      this.prepare(
          clientGameTestContext, testSingleplayerContext, Items.DIAMOND_SWORD, Blocks.OBSIDIAN);
      clientGameTestContext.runOnClient(
          minecraftClient3 -> {
            getAutoCrystalModule().setEnabled(false);
          });
      testSingleplayerContext
          .getServer()
          .runOnServer(
              minecraftServer2 -> {
                ServerPlayerEntity first =
                    minecraftServer2.getPlayerManager().getPlayerList().getFirst();
                ProtectionPerformanceTest.ensureInitialized();

                first.changeGameMode(GameMode.ADVENTURE);
                first.getAbilities().allowFlying = true;
                first.getAbilities().flying = true;
                first.getAbilities().invulnerable = true;
                first
                    .getAttributeInstance(EntityAttributes.EXPLOSION_KNOCKBACK_RESISTANCE)
                    .setBaseValue(1.0);
                first.sendAbilitiesUpdate();
                if (flag) {
                  first
                      .getInventory()
                      .getStack(1)
                      .set(
                          DataComponentTypes.CAN_PLACE_ON,
                          new BlockPredicatesComponent(
                              List.of(
                                  new BlockPredicate(
                                      Optional.of(
                                          RegistryEntryList.of(Blocks.OBSIDIAN.getRegistryEntry())),
                                      Optional.empty(),
                                      Optional.empty(),
                                      ComponentsPredicate.EMPTY))));
                }

                first.playerScreenHandler.syncState();
              });
      clientGameTestContext.waitTicks(8);
      clientGameTestContext.runOnClient(
          minecraftClient4 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                !minecraftClient4.player.getAbilities().allowModifyWorld,
                "server sent Adventure building restriction");
            update();
            minecraftClient4.player.getInventory().setSelectedSlot(1);
            apply(minecraftClient4, Vec3d.ofBottomCenter(blockPos.up()));
            minecraftClient4.crosshairTarget =
                minecraftClient4.player.raycast(
                    minecraftClient4.player.getBlockInteractionRange(), 1.0F, false);
            ((MinecraftClientAccessor) (Object) (minecraftClient4)).invokeDoItemUse();
          });
      clientGameTestContext.waitTicks(6);
      testSingleplayerContext
          .getServer()
          .runOnServer(
              minecraftServer3 -> {
                ProtectionPerformanceTest.ensureInitialized();

                var nonSpectatingEntities =
                    minecraftServer3
                        .getOverworld()
                        .getNonSpectatingEntities(EndCrystalEntity.class, box);
                apply(
                    nonSpectatingEntities.size() == (flag ? 1 : 0),
                    "vanilla manual placement respects the actual item permission");
                nonSpectatingEntities.forEach(Entity::discard);
              });
      clientGameTestContext.runOnClient(
          minecraftClient5 -> {
            minecraftClient5.player.getInventory().setSelectedSlot(0);
            ((ClientPlayerInteractionManagerAccessor)
                    (Object) (minecraftClient5.interactionManager))
                .invokeSyncSelectedSlot();
          });
      clientGameTestContext.waitTicks(4);
      this.list.clear();
      this.list2.clear();
      this.enabled = true;
      clientGameTestContext.runOnClient(
          minecraftClient6 -> {
            getAutoCrystalModule().setEnabled(true);
          });
      hold(clientGameTestContext);
      clientGameTestContext.waitTicks(35);
      String onClient =
          clientGameTestContext.computeOnClient(
              minecraftClient7 -> {
                return "status="
                    + getAutoCrystalModule().getString()
                    + ", pos="
                    + minecraftClient7.player.getEntityPos();
              });
      release(clientGameTestContext);
      ClientInitializer.logger.info(
          "Auto Crystal Adventure comparison: allowedItem={}, manual={}, {}, actions={}",
          flag,
          flag,
          onClient,
          this.list);
      if (flag && (this.count("crystal") < 2L || this.count("attack") < 2L)) {
        arrayList.add(
            "manual allowed-item placement works with mayBuild=false, automation stopped: "
                + this.list);
      }

      if (!flag) {
        apply(
            this.count("attack") == 0L,
            "automation cannot create a crystal with an unpermitted item");
        testSingleplayerContext
            .getServer()
            .runOnServer(
                minecraftServer4 -> {
                  apply(
                      minecraftServer4
                          .getOverworld()
                          .getNonSpectatingEntities(EndCrystalEntity.class, box)
                          .isEmpty(),
                      "vanilla still enforces placement permission");
                  minecraftServer4
                      .getOverworld()
                      .spawnEntity(
                          new EndCrystalEntity(minecraftServer4.getOverworld(), 0.5, 200.0, 3.5));
                });
        clientGameTestContext.waitTicks(5);
        hold(clientGameTestContext);
        clientGameTestContext.waitTicks(12);
        release(clientGameTestContext);
        if (this.count("attack") == 0L) {
          arrayList.add("mayBuild=false blocked attacking an existing crystal");
        } else {
          testSingleplayerContext
              .getServer()
              .runOnServer(
                  minecraftServer5 -> {
                    apply(
                        minecraftServer5
                            .getOverworld()
                            .getNonSpectatingEntities(EndCrystalEntity.class, box)
                            .isEmpty(),
                        "server accepted breaking an existing crystal in Adventure");
                  });
        }
      }

      if (this.count("crystal") > 0L || this.count("attack") > 0L) {
        apply(this.list2.isEmpty(), "Adventure interactions preserve packet order: " + this.list2);
      }
    }

    this.enabled = false;
    apply(arrayList.isEmpty(), "AimAssist/manual-use comparison regressions: " + arrayList);
    ClientInitializer.logger.info(
        "Auto Crystal detection and permission regressions verified: same AimAssist player,"
            + " cover/creative toggle, Adventure manual and automated placement, independent"
            + " crystal attack");
  }

  public static void update() {
    ProtectionPerformanceTest.ensureInitialized();

    AimAssistModule aimAssistModule = new AimAssistModule();
    aimAssistModule.rangeSetting.setNumber(Double.valueOf(6.0));
    aimAssistModule.setEnabled(true);

    try {
      double[] valueArray = aimAssistModule.getValueArray(0.016666666666666666, 0.0, 0.0);
      apply(
          valueArray != null && Math.abs(valueArray[0]) + Math.abs(valueArray[1]) > 0.0,
          "AimAssist detects the only remote player");
    } finally {
      aimAssistModule.setEnabled(false);
    }
  }

  public void apply(GameMode gameMode) {
    try {
      PlayerListEntry playerListEntry =
          new PlayerListEntry(this.otherClientPlayerEntity.getGameProfile(), false);
      Field declaredField =
          bunnythekenzo.client.runtime.MappedReflection.field(
              PlayerListEntry.class, "gameMode", "field_3744", "Lnet/minecraft/class_1934;");
      declaredField.setAccessible(true);
      declaredField.set(playerListEntry, gameMode);
      Field declaredField2 =
          bunnythekenzo.client.runtime.MappedReflection.field(
              AbstractClientPlayerEntity.class,
              "playerListEntry",
              "field_3901",
              "Lnet/minecraft/class_640;");
      declaredField2.setAccessible(true);
      declaredField2.set(this.otherClientPlayerEntity, playerListEntry);
      apply(
          this.otherClientPlayerEntity.getGameMode() == gameMode,
          "test sets real client player game-mode metadata");
    } catch (ReflectiveOperationException failure) {
      throw new AssertionError(failure);
    }
  }

  public void verifyOptionalTarget(
      ClientGameTestContext clientGameTestContext,
      TestSingleplayerContext testSingleplayerContext) {
    Block[] blockArray = new Block[] {Blocks.STONE, Blocks.OBSIDIAN, Blocks.BEDROCK};
    ProtectionPerformanceTest.ensureInitialized();

    for (Block block : blockArray) {
      this.prepare(clientGameTestContext, testSingleplayerContext, Items.DIAMOND_SWORD, block);
      testSingleplayerContext
          .getServer()
          .runOnServer(
              minecraftServer -> {
                minecraftServer.setDifficulty(Difficulty.PEACEFUL, true);
              });
      clientGameTestContext.waitTicks(3);
      clientGameTestContext.runOnClient(
          minecraftClient -> {
            ProtectionPerformanceTest.ensureInitialized();

            minecraftClient.world.removeEntity(
                this.otherClientPlayerEntity.getId(), RemovalReason.DISCARDED);
            apply(minecraftClient.world.getPlayers().size() == 1, "only the local player remains");
            getAutoCrystalModule().requireTargetSetting.setValue(false);
            getAutoCrystalModule().damageCheckSetting.setValue(true);
            getAutoCrystalModule().targetRangeSetting.setNumber(Double.valueOf(1.0));
            getAutoCrystalModule().placeDelaySetting.setNumber(Double.valueOf(6.0));
            getAutoCrystalModule().breakDelaySetting.setNumber(Double.valueOf(4.0));
          });
      this.list2.clear();
      this.enabled = true;
      hold(clientGameTestContext);
      clientGameTestContext.waitTicks(45);
      release(clientGameTestContext);
      apply(
          this.count("crystal") >= 2L && this.count("attack") >= 2L,
          "Require Target off cycles without any opponent on " + block + ": " + this.list);
      apply(
          this.count("obsidian") == (block == Blocks.STONE ? 1 : 0),
          "optional target preserves base placement policy");
      this.apply("crystal", 6);
      this.apply("attack", 4);
      apply(this.list2.isEmpty(), "optional target packet ordering: " + this.list2);
      clientGameTestContext.runOnClient(
          minecraftClient2 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                minecraftClient2.player.getInventory().getSelectedSlot() == 0
                    && !getAutoCrystalModule().test4()
                    && !getAutoCrystalModule().test(),
                "release without a target restores slot and input");
          });
      ClientInitializer.logger.info(
          "Auto Crystal optional target cycle verified: {}, orderingErrors={}", block, this.list2);
    }

    this.enabled = false;

    for (String text : new String[] {"tick", "mouse", "input"}) {
      this.prepare(
          clientGameTestContext, testSingleplayerContext, Items.DIAMOND_SWORD, Blocks.STONE);
      clientGameTestContext.runOnClient(
          minecraftClient3 -> {
            ProtectionPerformanceTest.ensureInitialized();

            minecraftClient3.world.removeEntity(
                this.otherClientPlayerEntity.getId(), RemovalReason.DISCARDED);
            getAutoCrystalModule().requireTargetSetting.setValue(false);
            minecraftClient3.options.useKey.setPressed(true);
            getAutoCrystalModule().onTick();
            apply(
                getAutoCrystalModule().test() && getAutoCrystalModule().test4(),
                "base rotation and input work without a target");
            getAutoCrystalModule().getValueArray(0.016666666666666666, 0.0, 0.0);
            apply(
                getAutoCrystalModule().test(),
                "mouse path does not reintroduce the player target check");
            getAutoCrystalModule().requireTargetSetting.setValue(true);
            switch (text) {
              case "tick":
                getAutoCrystalModule().onTick();
                break;
              case "mouse":
                apply(
                    getAutoCrystalModule().getValueArray(0.016666666666666666, 0.0, 0.0) == null,
                    "requiring a missing target cancels mouse assist");
                break;
              case "input":
                getAutoCrystalModule().test4();
            }

            apply(!getAutoCrystalModule().test(), text + " applies Require Target immediately");
          });
      clientGameTestContext.waitTicks(4);
      clientGameTestContext.runOnClient(
          minecraftClient4 -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                minecraftClient4.player.getInventory().getSelectedSlot() == 0
                    && !getAutoCrystalModule().test4(),
                text + " restores borrowed obsidian and releases input");
          });
      release(clientGameTestContext);
      apply(
          this.count("crystal") == 0L && this.count("attack") == 0L,
          text + " stops follow-up actions when a target is required again");
    }

    this.prepare(
        clientGameTestContext, testSingleplayerContext, Items.DIAMOND_SWORD, Blocks.OBSIDIAN);
    clientGameTestContext.runOnClient(
        minecraftClient5 -> {
          minecraftClient5.world.removeEntity(
              this.otherClientPlayerEntity.getId(), RemovalReason.DISCARDED);
          getAutoCrystalModule().requireTargetSetting.setValue(false);
          getAutoCrystalModule().breakRangeSetting.setNumber(Double.valueOf(1.0));
          getAutoCrystalModule().switchBackSetting.setValue(false);
        });
    hold(clientGameTestContext);
    clientGameTestContext.waitTicks(8);
    apply(
        this.count("crystal") == 1L && this.count("attack") == 0L,
        "optional target still respects Break Range");
    clientGameTestContext.runOnClient(
        minecraftClient6 -> {
          apply(
              minecraftClient6.player.getMainHandStack().isOf(Items.END_CRYSTAL),
              "optional target borrowed the crystal slot");
          getAutoCrystalModule().requireTargetSetting.setValue(true);
          apply(
              getAutoCrystalModule().test4(),
              "reenabling target check defers crystal slot restoration");
        });
    clientGameTestContext.waitTicks(5);
    clientGameTestContext.runOnClient(
        minecraftClient7 -> {
          apply(
              minecraftClient7.player.getMainHandStack().isOf(Items.DIAMOND_SWORD),
              "reenabling target check restores original slot even with Switch Back off");
          getAutoCrystalModule().requireTargetSetting.setValue(false);
          getAutoCrystalModule().breakRangeSetting.setNumber(Double.valueOf(3.0));
        });
    clientGameTestContext.waitTicks(25);
    release(clientGameTestContext);
    apply(
        this.count("crystal") >= 2L && this.count("attack") >= 2L,
        "Require Target can be disabled again during the same hold");
    ClientInitializer.logger.info(
        "Auto Crystal optional target verified: empty world cycles, delays, reach, all three hooks,"
            + " deferred restore and live toggles");
  }

  public void verifyBlockedStatus(
      ClientGameTestContext clientGameTestContext,
      TestSingleplayerContext testSingleplayerContext) {
    ProtectionPerformanceTest.ensureInitialized();

    this.prepare(
        clientGameTestContext, testSingleplayerContext, Items.DIAMOND_SWORD, Blocks.OBSIDIAN);
    clientGameTestContext.runOnClient(
        minecraftClient -> {
          ProtectionPerformanceTest.ensureInitialized();

          Entity cameraEntity = minecraftClient.getCameraEntity();

          try {
            minecraftClient.setCameraEntity(this.otherClientPlayerEntity);
            minecraftClient.options.useKey.setPressed(true);
            getAutoCrystalModule().showStatusSetting.setValue(true);
            minecraftClient.inGameHud.setOverlayMessage(Text.literal("Before diagnostic"), false);
            getAutoCrystalModule().onTick();
            apply(
                getAutoCrystalModule().getString().equals("Camera detached from player"),
                "early guard explains detached camera");
            Field declaredField =
                bunnythekenzo.client.runtime.MappedReflection.field(
                    InGameHud.class, "overlayMessage", "field_2018", "Lnet/minecraft/class_2561;");
            declaredField.setAccessible(true);
            apply(
                ((Text) declaredField.get(minecraftClient.inGameHud))
                    .getString()
                    .equals("Auto Crystal: Camera detached from player"),
                "Show Status is visible even when canAct rejects the action");
            apply(
                !getAutoCrystalModule().test4()
                    && minecraftClient.player.getMainHandStack().isOf(Items.DIAMOND_SWORD),
                "diagnostics do not acquire input or switch items");
            getAutoCrystalModule().showStatusSetting.setValue(false);
            minecraftClient.inGameHud.setOverlayMessage(Text.literal("Manual message"), false);
            getAutoCrystalModule().onTick();
            apply(
                ((Text) declaredField.get(minecraftClient.inGameHud))
                    .getString()
                    .equals("Manual message"),
                "Show Status off leaves the actionbar alone");
          } catch (ReflectiveOperationException failure) {
            throw new AssertionError(failure);
          } finally {
            minecraftClient.setCameraEntity(cameraEntity);
            minecraftClient.options.useKey.setPressed(false);
            getAutoCrystalModule().showStatusSetting.setValue(false);
          }
        });
    apply(
        this.count("crystal") == 0L && this.count("attack") == 0L && this.count("obsidian") == 0L,
        "blocked diagnostics send no crystal actions");
    ClientInitializer.logger.info(
        "Auto Crystal blocked status verified: early guard visible, manual input preserved, quiet"
            + " when disabled");
  }

  public void verifyServerTargeting(
      ClientGameTestContext clientGameTestContext,
      TestSingleplayerContext testSingleplayerContext) {
    ProtectionPerformanceTest.ensureInitialized();

    for (String text : new String[] {"peaceful", "invulnerable", "abilities", "resistance"}) {
      this.prepare(
          clientGameTestContext, testSingleplayerContext, Items.DIAMOND_SWORD, Blocks.STONE);
      if (text.equals("peaceful")) {
        testSingleplayerContext
            .getServer()
            .runOnServer(
                minecraftServer -> {
                  minecraftServer.setDifficulty(Difficulty.PEACEFUL, true);
                });
        clientGameTestContext.waitTicks(4);
      }

      clientGameTestContext.runOnClient(
          minecraftClient -> {
            ProtectionPerformanceTest.ensureInitialized();
            apply(
                !(Boolean) getAutoCrystalModule().damageCheckSetting.getValue(),
                "server-compatible target checks are the default");
            switch (text) {
              case "invulnerable":
                this.otherClientPlayerEntity.setInvulnerable(true);
                break;
              case "abilities":
                this.otherClientPlayerEntity.getAbilities().invulnerable = true;
                break;
              case "resistance":
                this.otherClientPlayerEntity.addStatusEffect(
                    new StatusEffectInstance(StatusEffects.RESISTANCE, 600, 4));
            }

            getAutoCrystalModule().showStatusSetting.setValue(true);
          });
      this.list2.clear();
      this.enabled = true;
      hold(clientGameTestContext);
      clientGameTestContext.waitTicks(40);
      release(clientGameTestContext);
      apply(
          this.count("obsidian") == 1L && this.count("crystal") >= 2L && this.count("attack") >= 2L,
          text + " still permits base placement, mouse rotation and crystal cycles: " + this.list);
      apply(this.list2.isEmpty(), text + " preserves packet ordering: " + this.list2);
      this.enabled = false;
      this.list.clear();
      clientGameTestContext.runOnClient(
          minecraftClient2 -> {
            getAutoCrystalModule().damageCheckSetting.setValue(true);
          });
      hold(clientGameTestContext);
      clientGameTestContext.waitTicks(8);
      apply(
          this.count("obsidian") == 0L && this.count("crystal") == 0L && this.count("attack") == 0L,
          text + " is rejected with Damage Check enabled");
      clientGameTestContext.runOnClient(
          minecraftClient3 -> {
            ProtectionPerformanceTest.ensureInitialized();

            apply(
                getAutoCrystalModule().getString().startsWith("Damage Check:"),
                text + " explains the waiting state");
            apply(
                !getAutoCrystalModule().test4() && !getAutoCrystalModule().test(),
                text + " strict mode releases input");
            getAutoCrystalModule().damageCheckSetting.setValue(false);
          });
      clientGameTestContext.waitTicks(25);
      release(clientGameTestContext);
      apply(
          this.count("crystal") >= 2L && this.count("attack") >= 2L,
          text + " resumes when Damage Check changes during a hold");
      ClientInitializer.logger.info(
          "Auto Crystal server target regression verified: {}, orderingErrors={}",
          text,
          this.list2);
    }

    this.enabled = false;
  }

  public void verifyManualPlacement(
      ClientGameTestContext clientGameTestContext,
      TestSingleplayerContext testSingleplayerContext) {
    ProtectionPerformanceTest.ensureInitialized();

    for (String text :
        new String[] {
          "absent", "far", "range", "covered", "invulnerable", "abilities", "resistance", "dead"
        }) {
      this.prepare(clientGameTestContext, testSingleplayerContext, Items.OBSIDIAN, Blocks.STONE);
      if (text.equals("covered")) {
        testSingleplayerContext
            .getServer()
            .runOnServer(
                minecraftServer -> {
                  ProtectionPerformanceTest.ensureInitialized();

                  for (int index = 200; index <= 204; index++) {
                    for (int index2 = 1; index2 <= 5; index2++) {
                      minecraftServer
                          .getOverworld()
                          .setBlockState(
                              new BlockPos(2, index, index2), Blocks.BEDROCK.getDefaultState());
                    }
                  }
                });
        clientGameTestContext.waitTicks(4);
      }

      clientGameTestContext.runOnClient(
          minecraftClient -> {
            getAutoCrystalModule()
                .damageCheckSetting
                .setValue(
                    List.of("covered", "invulnerable", "abilities", "resistance").contains(text));
            ProtectionPerformanceTest.ensureInitialized();

            switch (text) {
              case "absent":
                minecraftClient.world.removeEntity(
                    this.otherClientPlayerEntity.getId(), RemovalReason.DISCARDED);
                break;
              case "far":
                this.otherClientPlayerEntity.setPosition(9.5, 200.0, 3.5);
                break;
              case "range":
                getAutoCrystalModule().targetRangeSetting.setNumber(Double.valueOf(2.0));
                break;
              case "invulnerable":
                this.otherClientPlayerEntity.setInvulnerable(true);
                break;
              case "abilities":
                this.otherClientPlayerEntity.getAbilities().invulnerable = true;
                break;
              case "resistance":
                this.otherClientPlayerEntity.addStatusEffect(
                    new StatusEffectInstance(StatusEffects.RESISTANCE, 200, 4));
                break;
              case "dead":
                this.otherClientPlayerEntity.setHealth(0.0F);
            }

            minecraftClient.options.useKey.setPressed(true);
            float yaw = minecraftClient.player.getYaw();
            float pitch = minecraftClient.player.getPitch();
            getAutoCrystalModule().onTick();
            apply(
                minecraftClient.world.getBlockState(blockPos.up()).isAir(),
                text + " does not auto-place");
            apply(
                !getAutoCrystalModule().test4() && !getAutoCrystalModule().test(),
                text + " releases use and mouse");
            apply(
                minecraftClient.player.getInventory().getSelectedSlot() == 0
                    && yaw == minecraftClient.player.getYaw()
                    && pitch == minecraftClient.player.getPitch(),
                text + " preserves held obsidian and view");
            minecraftClient.crosshairTarget =
                minecraftClient.player.raycast(
                    minecraftClient.player.getBlockInteractionRange(), 1.0F, false);
            ((MinecraftClientAccessor) (Object) (minecraftClient)).invokeDoItemUse();
            apply(
                minecraftClient.world.getBlockState(blockPos.up()).isOf(Blocks.OBSIDIAN),
                text + " allows vanilla right-click placement");
          });
      clientGameTestContext.waitTicks(6);
      release(clientGameTestContext);
      apply(
          this.count("obsidian") >= 1L && this.count("crystal") == 0L && this.count("attack") == 0L,
          text + " stays manual: " + this.list);
      testSingleplayerContext
          .getServer()
          .runOnServer(
              minecraftServer2 -> {
                apply(
                    minecraftServer2
                        .getOverworld()
                        .getBlockState(blockPos.up())
                        .isOf(Blocks.OBSIDIAN),
                    text + " manual placement accepted by server");
              });
    }

    this.prepare(clientGameTestContext, testSingleplayerContext, Items.DIAMOND_SWORD, Blocks.STONE);
    clientGameTestContext.runOnClient(
        minecraftClient2 -> {
          getAutoCrystalModule().targetRangeSetting.setNumber(Double.valueOf(2.0));
        });
    hold(clientGameTestContext);
    clientGameTestContext.waitTicks(5);
    apply(
        this.count("obsidian") == 0L && this.count("crystal") == 0L,
        "target range stops sword automation");
    clientGameTestContext.runOnClient(
        minecraftClient3 -> {
          getAutoCrystalModule().targetRangeSetting.setNumber(Double.valueOf(6.0));
        });
    clientGameTestContext.waitTicks(35);
    release(clientGameTestContext);
    apply(
        this.count("obsidian") > 0L && this.count("attack") > 0L,
        "target range updates during a hold: " + this.list);

    for (String text2 : new String[] {"tick", "mouse", "input"}) {
      this.prepare(clientGameTestContext, testSingleplayerContext, Items.OBSIDIAN, Blocks.STONE);
      clientGameTestContext.runOnClient(
          minecraftClient4 -> {
            minecraftClient4.options.useKey.setPressed(true);
            getAutoCrystalModule().onTick();
            ProtectionPerformanceTest.ensureInitialized();

            apply(
                getAutoCrystalModule().test(),
                "valid target starts rotation before " + text2 + " loss");
            this.otherClientPlayerEntity.setPosition(15.5, 200.0, 3.5);
            switch (text2) {
              case "tick":
                getAutoCrystalModule().onTick();
                break;
              case "mouse":
                apply(
                    getAutoCrystalModule().getValueArray(0.016666666666666666, 0.0, 0.0) == null,
                    "target loss cancels mouse frame");
                break;
              case "input":
                apply(
                    !getAutoCrystalModule().test4(),
                    "target loss releases input before the next tick");
            }

            apply(
                !getAutoCrystalModule().test() && !getAutoCrystalModule().test4(),
                text2 + " stops on target loss");
            apply(
                minecraftClient4.player.getMainHandStack().isOf(Items.OBSIDIAN),
                text2 + " leaves manual obsidian available");
          });
      clientGameTestContext.waitTicks(6);
      release(clientGameTestContext);
      apply(
          this.count("crystal") == 0L && this.count("attack") == 0L,
          text2 + " loss prevents follow-up crystal actions");
    }

    for (boolean flag : new boolean[] {true, false}) {
      this.prepare(clientGameTestContext, testSingleplayerContext, Items.OBSIDIAN, Blocks.OBSIDIAN);
      clientGameTestContext.runOnClient(
          minecraftClient5 -> {
            getAutoCrystalModule().breakRangeSetting.setNumber(Double.valueOf(1.0));
            getAutoCrystalModule().switchBackSetting.setValue(flag);
          });
      hold(clientGameTestContext);
      clientGameTestContext.waitTicks(8);
      clientGameTestContext.runOnClient(
          minecraftClient6 -> {
            apply(
                minecraftClient6.player.getMainHandStack().isOf(Items.END_CRYSTAL),
                "valid target borrows the crystal slot");
            this.otherClientPlayerEntity.setPosition(15.5, 200.0, 3.5);
            apply(
                getAutoCrystalModule().test4(),
                "target loss holds use until the borrowed slot is restored");
            apply(
                minecraftClient6.player.getMainHandStack().isOf(Items.END_CRYSTAL),
                "input hook defers the slot packet");
          });
      clientGameTestContext.waitTicks(6);
      clientGameTestContext.runOnClient(
          minecraftClient7 -> {
            apply(
                minecraftClient7.player.getMainHandStack().isOf(Items.OBSIDIAN),
                "target loss restores manual obsidian even with Switch Back=" + flag);
          });
      release(clientGameTestContext);
      apply(
          this.count("crystal") == 1L && this.count("attack") == 0L,
          "losing a target cannot leak vanilla crystal placements: " + this.list);
    }

    this.prepare(clientGameTestContext, testSingleplayerContext, Items.DIAMOND_SWORD, Blocks.STONE);
    clientGameTestContext.runOnClient(
        minecraftClient8 -> {
          ProtectionPerformanceTest.ensureInitialized();

          Vec3d vec3d = Vec3d.ofBottomCenter(blockPos.up(2));
          this.otherClientPlayerEntity.setPosition(3.5, 198.5, 3.5);

          for (int index3 = 0; index3 <= 4; index3++) {
            for (int index4 = 2; index4 <= 4; index4++) {
              minecraftClient8.world.setBlockState(
                  new BlockPos(index3, 199, index4), Blocks.AIR.getDefaultState(), 3);
            }
          }

          apply(
              CrystalTargetStatusSupport.test(minecraftClient8, vec3d, 6.0, null),
              "unplaced base would expose a lower player");
          apply(
              !CrystalTargetStatusSupport.test(minecraftClient8, vec3d, 6.0, blockPos.up()),
              "future obsidian cover is checked before placement");
          this.otherClientPlayerEntity.setPosition(-11.4, 200.0, 0.5);
          apply(
              !CrystalTargetStatusSupport.test(minecraftClient8, vec3d, 12.0, null),
              "player range cannot extend the crystal blast radius");
          apply(
              CrystalTargetStatusSupport.getCrystalTargetStatus(
                      minecraftClient8, vec3d, 12.0, null, true)
                  == CrystalTargetStatus.OUT_OF_BLAST_RANGE,
              "Damage Check explains a target outside the blast radius");
          apply(
              CrystalTargetStatusSupport.getCrystalTargetStatus(
                      minecraftClient8, vec3d, 12.0, null, false)
                  == CrystalTargetStatus.READY,
              "Damage Check off detects players by Target Range without predicting blast damage");
        });
  }

  public void verifyPacketOrder(
      ClientGameTestContext clientGameTestContext,
      TestSingleplayerContext testSingleplayerContext) {
    ProtectionPerformanceTest.ensureInitialized();

    for (Block block : new Block[] {Blocks.STONE, Blocks.OBSIDIAN}) {
      this.prepare(clientGameTestContext, testSingleplayerContext, Items.DIAMOND_SWORD, block);
      this.list2.clear();
      this.enabled = true;
      clientGameTestContext.runOnClient(
          minecraftClient -> {
            getAutoCrystalModule().placeDelaySetting.setNumber(Double.valueOf(0.0));
            getAutoCrystalModule().breakDelaySetting.setNumber(Double.valueOf(0.0));
          });
      hold(clientGameTestContext);
      clientGameTestContext.waitTicks(45);
      release(clientGameTestContext);
      apply(
          this.count("crystal") >= 2L && this.count("attack") >= 2L,
          "packet test completes real crystal cycles");
      apply(this.list2.isEmpty(), "cycle packet ordering and both sight lines: " + this.list2);
      ClientInitializer.logger.info(
          "Auto Crystal packet cycle {}: actions={}, orderingErrors={}",
          block,
          this.list.size(),
          this.list2);
    }

    this.prepare(
        clientGameTestContext, testSingleplayerContext, Items.DIAMOND_SWORD, Blocks.OBSIDIAN);
    clientGameTestContext.runOnClient(
        minecraftClient2 -> {
          getAutoCrystalModule().breakRangeSetting.setNumber(Double.valueOf(1.0));
          minecraftClient2.player.setYaw(90.0F);
        });
    clientGameTestContext.waitTicks(3);
    this.list2.clear();
    this.enabled = true;
    clientGameTestContext.runOnClient(
        minecraftClient3 -> {
          apply(minecraftClient3, Vec3d.ofBottomCenter(blockPos.up()));
          minecraftClient3.options.useKey.setPressed(true);
          getAutoCrystalModule().onTick();
          apply(
              minecraftClient3.player.getMainHandStack().isOf(Items.DIAMOND_SWORD),
              "new view waits for vanilla to transmit rotation before selecting crystals");
          apply(getAutoCrystalModule().test4(), "rotation wait blocks a duplicate vanilla click");
        });
    clientGameTestContext.waitTicks(6);
    apply(this.count("crystal") == 1L, "placement resumes after real rotation is sent");
    release(clientGameTestContext);
    apply(this.list2.isEmpty(), "rapid target acquisition: " + this.list2);

    for (String text : new String[] {"disable", "target", "manual", "world", "reenable"}) {
      this.prepare(
          clientGameTestContext, testSingleplayerContext, Items.DIAMOND_SWORD, Blocks.OBSIDIAN);
      clientGameTestContext.runOnClient(
          minecraftClient4 -> {
            getAutoCrystalModule().breakRangeSetting.setNumber(Double.valueOf(1.0));
          });
      this.list2.clear();
      this.enabled = true;
      hold(clientGameTestContext);
      clientGameTestContext.waitTicks(5);
      clientGameTestContext.runOnClient(
          minecraftClient5 -> {
            ProtectionPerformanceTest.ensureInitialized();

            apply(
                minecraftClient5.player.getMainHandStack().isOf(Items.END_CRYSTAL),
                "restore test borrowed crystal slot");
            if (text.equals("target")) {
              this.otherClientPlayerEntity.setPosition(15.5, 200.0, 3.5);
              apply(
                  getAutoCrystalModule().test4(),
                  "target loss queues restoration before releasing input");
            } else {
              getAutoCrystalModule().setEnabled(false);
            }

            apply(
                minecraftClient5.player.getMainHandStack().isOf(Items.END_CRYSTAL),
                "callback sends no immediate slot change");
            apply(
                getAutoCrystalModule().test4(), "pending restoration owns use even while disabled");
            minecraftClient5.options.useKey.setPressed(false);
            if (text.equals("manual")) {
              minecraftClient5.player.getInventory().setSelectedSlot(3);
            }

            if (text.equals("world")) {
              getAutoCrystalModule().update4();
            }

            if (text.equals("reenable")) {
              getAutoCrystalModule().setEnabled(true);
            }
          });
      clientGameTestContext.waitTicks(3);
      clientGameTestContext.runOnClient(
          minecraftClient6 -> {
            ProtectionPerformanceTest.ensureInitialized();
            int intValue = text.equals("manual") ? 3 : (text.equals("world") ? 1 : 0);
            apply(
                minecraftClient6.player.getInventory().getSelectedSlot() == intValue,
                text + " restoration respects session ownership");
            apply(!getAutoCrystalModule().test4(), text + " releases pending input");
          });
      apply(this.list2.isEmpty(), text + " packet ordering: " + this.list2);
    }

    this.prepare(
        clientGameTestContext, testSingleplayerContext, Items.DIAMOND_SWORD, Blocks.OBSIDIAN);
    this.list2.clear();
    this.enabled = true;
    this.atomicReference2.set(
        minecraftClient7 -> {
          getAutoCrystalModule().setEnabled(false);
          minecraftClient7.options.useKey.setPressed(false);
          apply(
              minecraftClient7.player.getMainHandStack().isOf(Items.END_CRYSTAL),
              "disable immediately after use must defer slot change");
          apply(
              getAutoCrystalModule().test4(),
              "disable in placement tick prevents extra vanilla use");
        });
    hold(clientGameTestContext);
    clientGameTestContext.waitTicks(6);
    apply(
        this.atomicReference2.get() == null && this.count("crystal") == 1L,
        "cancelled in the first actual placement tick");
    clientGameTestContext.runOnClient(
        minecraftClient8 -> {
          ProtectionPerformanceTest.ensureInitialized();
          apply(
              minecraftClient8.player.getInventory().getSelectedSlot() == 0,
              "next combat tick restores disabled module");
        });
    apply(this.list2.isEmpty(), "disable immediately after use: " + this.list2);
    this.enabled = false;
    ClientInitializer.logger.info(
        "Auto Crystal packet regression verified: pre/post movement sight lines, zero-delay cycles,"
            + " deferred disable/target restore, manual slot, world clear and re-enable");
  }

  public void prepare(
      ClientGameTestContext clientGameTestContext,
      TestSingleplayerContext testSingleplayerContext,
      Item item,
      Block block) {
    this.enabled = false;
    clientGameTestContext.runOnClient(
        minecraftClient -> {
          minecraftClient.options.useKey.setPressed(false);
          getAutoCrystalModule().setEnabled(false);
          minecraftClient.setScreen(null);
          minecraftClient.onWindowFocusChanged(true);
        });
    testSingleplayerContext
        .getServer()
        .runOnServer(
            minecraftServer -> {
              minecraftServer.setDifficulty(Difficulty.NORMAL, true);
              ServerWorld overworld = minecraftServer.getOverworld();
              ProtectionPerformanceTest.ensureInitialized();

              for (EndCrystalEntity endCrystalEntity :
                  overworld.getNonSpectatingEntities(EndCrystalEntity.class, box)) {
                endCrystalEntity.discard();
              }

              for (int index = -5; index <= 5; index++) {
                for (int index2 = -5; index2 <= 6; index2++) {
                  overworld.setBlockState(
                      new BlockPos(index, 199, index2), Blocks.BEDROCK.getDefaultState());

                  for (int index3 = 200; index3 <= 205; index3++) {
                    overworld.setBlockState(
                        new BlockPos(index, index3, index2), Blocks.AIR.getDefaultState());
                  }
                }
              }

              overworld.setBlockState(blockPos, block.getDefaultState());
              ServerPlayerEntity first =
                  minecraftServer.getPlayerManager().getPlayerList().getFirst();
              first.changeGameMode(GameMode.CREATIVE);
              first
                  .getAttributeInstance(EntityAttributes.EXPLOSION_KNOCKBACK_RESISTANCE)
                  .setBaseValue(0.0);
              first.getAbilities().flying = true;
              first.sendAbilitiesUpdate();
              first.requestTeleport(0.5, 200.25, 0.5);
              first.setVelocity(Vec3d.ZERO);
              first.getInventory().clear();
              first
                  .getInventory()
                  .setStack(
                      0,
                      item == Items.AIR
                          ? ItemStack.EMPTY
                          : new ItemStack(item, item == Items.OBSIDIAN ? 64 : 1));
              first.getInventory().setStack(1, new ItemStack(Items.END_CRYSTAL, 64));
              first.getInventory().setStack(2, new ItemStack(Items.OBSIDIAN, 64));
              first.getInventory().setStack(3, new ItemStack(Items.APPLE, 64));
              first.getInventory().setSelectedSlot(0);
              first.playerScreenHandler.syncState();
            });
    clientGameTestContext.waitTicks(12);
    clientGameTestContext.runOnClient(
        minecraftClient2 -> {
          ProtectionPerformanceTest.ensureInitialized();
          if (this.otherClientPlayerEntity != null) {
            minecraftClient2.world.removeEntity(
                this.otherClientPlayerEntity.getId(), RemovalReason.DISCARDED);
          }

          this.otherClientPlayerEntity =
              new OtherClientPlayerEntity(
                  minecraftClient2.world,
                  new GameProfile(new UUID(0L, 67294L), "CrystalTargetTest"));
          this.otherClientPlayerEntity.setId(-67294);
          this.otherClientPlayerEntity.setPosition(3.5, 200.0, 3.5);
          this.otherClientPlayerEntity.resetPosition();
          minecraftClient2.world.addEntity(this.otherClientPlayerEntity);
          minecraftClient2.player.getAbilities().flying = true;
          minecraftClient2.player.setOnGround(false);
          minecraftClient2.player.setVelocity(Vec3d.ZERO);
          minecraftClient2.player.sendAbilitiesUpdate();
          minecraftClient2.player.getInventory().setSelectedSlot(0);
          this.atomicInteger2.set(0);
          this.atomicReference.set(item);
          apply(minecraftClient2, Vec3d.ofBottomCenter(blockPos.up()));
          AutoCrystalModule autoCrystalModule = getAutoCrystalModule();
          autoCrystalModule.activateKeySetting.setValue(1);
          autoCrystalModule.breakDelaySetting.setNumber(Double.valueOf(1.0));
          autoCrystalModule.placeDelaySetting.setNumber(Double.valueOf(1.0));
          autoCrystalModule.obsidianDelaySetting.setNumber(Double.valueOf(2.0));
          autoCrystalModule.breakRangeSetting.setNumber(Double.valueOf(3.0));
          autoCrystalModule.autoObsidianSetting.setValue(true);
          autoCrystalModule.switchBackSetting.setValue(true);
          autoCrystalModule.rotationSpeedSetting.resetToDefault();
          autoCrystalModule.aimVariationSetting.resetToDefault();
          autoCrystalModule.targetRangeSetting.resetToDefault();
          autoCrystalModule.requireTargetSetting.resetToDefault();
          autoCrystalModule.damageCheckSetting.resetToDefault();
          autoCrystalModule.showStatusSetting.resetToDefault();
          autoCrystalModule.setEnabled(true);
        });
    clientGameTestContext.waitTicks(2);
    this.list.clear();
  }

  public static void apply(MinecraftClient minecraftClient, Vec3d vec3d) {
    Vec3d vec3d2 = vec3d.subtract(minecraftClient.player.getEyePos());
    minecraftClient.player.setYaw((float) Math.toDegrees(Math.atan2(-vec3d2.x, vec3d2.z)));
    minecraftClient.player.setPitch(
        (float) (-Math.toDegrees(Math.atan2(vec3d2.y, vec3d2.horizontalLength()))));
  }

  public static void hold(ClientGameTestContext clientGameTestContext) {
    clientGameTestContext.runOnClient(
        minecraftClient -> {
          minecraftClient.onWindowFocusChanged(true);
          minecraftClient.options.useKey.setPressed(true);
        });
  }

  public static void release(ClientGameTestContext clientGameTestContext) {
    clientGameTestContext.runOnClient(
        minecraftClient -> {
          minecraftClient.options.useKey.setPressed(false);
        });
    clientGameTestContext.waitTicks(3);
  }

  public long count(String text) {
    return this.list.stream()
        .filter(
            value -> {
              return ((bunnythekenzo.client.gametest.HoppinStandingBeforeTestData3) (value))
                  .string()
                  .equals(text);
            })
        .count();
  }

  public void apply(String text, int intValue) {
    ProtectionPerformanceTest.ensureInitialized();

    int intValue2 = -1000;
    int intValue3 = -1;

    for (HoppinStandingBeforeTestData3 hoppinStandingBeforeTestData3 :
        (java.lang.Iterable<HoppinStandingBeforeTestData3>) (java.lang.Iterable<?>) (this.list)) {
      if (hoppinStandingBeforeTestData3.string().equals(text)) {
        apply(
            hoppinStandingBeforeTestData3.count() - intValue2 >= intValue,
            text + " respects delay " + intValue + ": " + this.list);
        if (hoppinStandingBeforeTestData3.count2() >= 0) {
          apply(
              hoppinStandingBeforeTestData3.count2() > intValue3,
              "vanilla placement sequences increase");
        }

        intValue2 = hoppinStandingBeforeTestData3.count();
        intValue3 = hoppinStandingBeforeTestData3.count2();
      }
    }
  }

  public void apply(MinecraftClient minecraftClient) {
    try {
      Field declaredField =
          bunnythekenzo.client.runtime.MappedReflection.field(
              ClientConnection.class, "channel", "field_11651", "Lio/netty/channel/Channel;");
      declaredField.setAccessible(true);
      this.channel =
          (Channel) declaredField.get(minecraftClient.getNetworkHandler().getConnection());
      this.channel
          .pipeline()
          .addBefore("packet_handler", "auto-crystal-test", new CrystalPacketOrderObserver(this));
    } catch (ReflectiveOperationException failure) {
      throw new AssertionError(failure);
    }
  }

  public static AutoCrystalModule getAutoCrystalModule() {
    return ClientInitializer.getModuleManager().autoCrystalModule;
  }

  public static void apply(boolean flag, String text) {
    if (!flag) {
      throw new AssertionError(text);
    }
  }
}
