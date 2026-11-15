package bunnythekenzo.client.gametest;

import com.google.gson.JsonObject;
import java.lang.reflect.Field;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.Entity.RemovalReason;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.GuiModuleEntry;
import bunnythekenzo.client.JsonSupport2;
import bunnythekenzo.client.module.TriggerbotModule;
import bunnythekenzo.client.ui.PanelScreen;

public final class TriggerbotSettingsTest {
  public static void run(
      ClientGameTestContext clientGameTestContext,
      TestSingleplayerContext testSingleplayerContext) {
    JsonObject onClient =
        clientGameTestContext.computeOnClient(
            minecraftClient -> {
              return ClientInitializer.getClientConfig().getJsonObject();
            });

    try {
      clientGameTestContext.runOnClient(
          minecraftClient2 -> {
            JsonSupport2.getList();
            ClientInitializer.getModuleManager()
                .getList()
                .forEach(
                    (java.util.function.Consumer<bunnythekenzo.client.module.Module>)
                        (module -> {
                          module.setEnabled(false);
                        }));
            checkSettings();
            apply(minecraftClient2);
          });
      testSingleplayerContext.getServer().runCommand("gamemode survival @a");
      testSingleplayerContext.getServer().runCommand("fill -3 119 -3 3 119 5 minecraft:stone");
      testSingleplayerContext.getServer().runCommand("fill -3 120 -3 3 124 5 minecraft:air");
      testSingleplayerContext.getServer().runCommand("tp @a 0.5 120 0.5 0 12");
      testSingleplayerContext
          .getServer()
          .runCommand(
              "summon minecraft:cow 0.5 120 3 {NoAI:1b,NoGravity:1b,Tags:[\"triggerbot_test\"]}");
      clientGameTestContext.waitTicks(30);
      clientGameTestContext.runOnClient(
          minecraftClient3 -> {
            TriggerbotModule triggerbotModule =
                ClientInitializer.getModuleManager().triggerbotModule;
            triggerbotModule
                .getSettings()
                .forEach(
                    value -> {
                      ((bunnythekenzo.client.setting.Setting) (value)).resetToDefault();
                    });
            triggerbotModule.passiveMobsSetting.setValue(true);
            minecraftClient3.setScreen(null);
            minecraftClient3.attackCooldown = 0;
            triggerbotModule.setEnabled(true);
          });
      clientGameTestContext.waitTicks(30);
      clientGameTestContext.runOnClient(
          minecraftClient4 -> {
            ClientInitializer.getModuleManager().triggerbotModule.setEnabled(false);
          });
      testSingleplayerContext
          .getServer()
          .runOnServer(
              minecraftServer -> {
                ProtectionPerformanceTest.ensureInitialized();

                var iterator = minecraftServer.getOverworld().iterateEntities().iterator();
                boolean flag = false;

                while (iterator.hasNext()) {
                  Entity entity = (Entity) iterator.next();
                  if (entity.getCommandTags().contains("triggerbot_test")
                      && entity instanceof LivingEntity livingEntity) {
                    flag |= livingEntity.getHealth() < livingEntity.getMaxHealth();
                  }
                }

                apply(flag, "server accepts an actual crosshair attack");
              });
      clientGameTestContext.getInput().resizeWindow(1280, 720);
      clientGameTestContext.runOnClient(
          minecraftClient5 -> {
            PanelScreen panelScreen = new PanelScreen();
            minecraftClient5.setScreen(panelScreen);
            apply(panelScreen.test("Triggerbot"), "settings panel opens");
          });
      clientGameTestContext.waitTicks(10);
      clientGameTestContext.takeScreenshot("triggerbot-settings");
      ClientInitializer.logger.info(
          "Triggerbot tests passed: timing, guards, ray cast, config and server damage");
    } finally {
      clientGameTestContext.runOnClient(
          minecraftClient6 -> {
            ClientInitializer.getModuleManager().triggerbotModule.setEnabled(false);
            minecraftClient6.setScreen(null);
            ClientInitializer.getClientConfig().apply(onClient);
          });
    }
  }

  public static void checkSettings() {
    ProtectionPerformanceTest.ensureInitialized();

    TriggerbotModule triggerbotModule = ClientInitializer.getModuleManager().triggerbotModule;
    triggerbotModule
        .getSettings()
        .forEach(
            value2 -> {
              ((bunnythekenzo.client.setting.Setting) (value2)).resetToDefault();
            });
    apply(triggerbotModule.usesCombatTick(), "attacks dispatched before movement");
    apply(
        triggerbotModule.getSettings().stream()
            .allMatch(
                value3 -> {
                  ProtectionPerformanceTest.ensureInitialized();
                  return !((bunnythekenzo.client.setting.Setting) (value3)).getDescription().isBlank();
                }),
        "all settings explained");
    apply(
        GuiModuleEntry.of(triggerbotModule).getList().stream()
                .mapToInt(
                    collectionData -> {
                      return ((bunnythekenzo.client.CollectionData) (collectionData)).list().size();
                    })
                .sum()
            == triggerbotModule.getSettings().size(),
        "every setting reaches GUI");
    triggerbotModule.timingSetting.setValue("CPS");
    triggerbotModule.minCpsSetting.setNumber(Double.valueOf(20.0));
    triggerbotModule.maxCpsSetting.setNumber(Double.valueOf(20.0));
    triggerbotModule.respectCooldownSetting.setValue(false);
    triggerbotModule.reactionDelaySetting.setNumber(Double.valueOf(3.0));
    triggerbotModule.onlyItemSetting.setValue(true);
    triggerbotModule.itemFilterSetting.setValue("Sword or Axe");
    JsonObject jsonObject = ClientInitializer.getClientConfig().getJsonObject();
    triggerbotModule
        .getSettings()
        .forEach(
            value4 -> {
              ((bunnythekenzo.client.setting.Setting) (value4)).resetToDefault();
            });
    ClientInitializer.getClientConfig().apply(jsonObject);
    apply(
        triggerbotModule.timingSetting.isSelected("CPS")
            && triggerbotModule.minCpsSetting.intValue() == 20
            && triggerbotModule.maxCpsSetting.intValue() == 20
            && !(Boolean) triggerbotModule.respectCooldownSetting.getValue()
            && triggerbotModule.reactionDelaySetting.intValue() == 3
            && (Boolean) triggerbotModule.onlyItemSetting.getValue()
            && triggerbotModule.itemFilterSetting.isSelected("Sword or Axe"),
        "settings survive config round trip");
    apply(
        triggerbotModule.minCpsSetting.isVisible()
            && !triggerbotModule.minDelaySetting.isVisible()
            && !triggerbotModule.minCooldownSetting.isVisible(),
        "dependent settings visibility");
    triggerbotModule
        .getSettings()
        .forEach(
            value5 -> {
              ((bunnythekenzo.client.setting.Setting) (value5)).resetToDefault();
            });
  }

  public static void apply(MinecraftClient minecraftClient) {
    ProtectionPerformanceTest.ensureInitialized();

    TriggerbotModule triggerbotModule = new TriggerbotModule();
    ClientPlayerInteractionManager interactionManager = minecraftClient.interactionManager;
    HoppinStandingBeforeTestSupport5 interactionManager2 =
        new HoppinStandingBeforeTestSupport5(minecraftClient);
    Vec3d entityPos = minecraftClient.player.getEntityPos();
    float yaw = minecraftClient.player.getYaw();
    float pitch = minecraftClient.player.getPitch();
    int age = minecraftClient.player.age;
    ItemStack itemStack = minecraftClient.player.getMainHandStack().copy();
    HoppinStandingBeforeTestSupport12 hoppinStandingBeforeTestSupport12 =
        new HoppinStandingBeforeTestSupport12(minecraftClient);
    hoppinStandingBeforeTestSupport12.setId(-67890);
    hoppinStandingBeforeTestSupport12.refreshPositionAndAngles(0.5, 120.0, 3.0, 180.0F, 0.0F);
    hoppinStandingBeforeTestSupport12.setNoGravity(true);
    hoppinStandingBeforeTestSupport12.resetPosition();
    minecraftClient.world.addEntity(hoppinStandingBeforeTestSupport12);

    try {
      minecraftClient.interactionManager = interactionManager2;
      interactionManager2.setGameMode(GameMode.SURVIVAL);
      minecraftClient.setScreen(null);
      minecraftClient.player.refreshPositionAndAngles(0.5, 120.0, 0.5, 0.0F, 0.0F);
      minecraftClient.player.resetPosition();
      minecraftClient.player.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
      minecraftClient.attackCooldown = 0;
      reset(triggerbotModule, interactionManager2);
      apply(minecraftClient, triggerbotModule, 20);
      apply(interactionManager2.count == 20, "20 CPS produces exactly one attack each tick");
      triggerbotModule.onTick();
      apply(interactionManager2.count == 20, "duplicate tick cannot duplicate attacks");
      apply(
          minecraftClient.player.getYaw() == 0.0F
              && minecraftClient.player.getPitch() == 0.0F
              && minecraftClient.player.getEntityPos().equals(new Vec3d(0.5, 120.0, 0.5)),
          "attacks preserve position and rotation");
      reset(triggerbotModule, interactionManager2);
      triggerbotModule.minCpsSetting.setNumber(Double.valueOf(12.0));
      triggerbotModule.maxCpsSetting.setNumber(Double.valueOf(12.0));
      apply(minecraftClient, triggerbotModule, 100);
      apply(interactionManager2.count == 60, "fractional CPS timing averages 12, not 10");
      reset(triggerbotModule, interactionManager2);
      triggerbotModule.timingSetting.setValue("Delay");
      triggerbotModule.minDelaySetting.setNumber(Double.valueOf(4.0));
      triggerbotModule.maxDelaySetting.setNumber(Double.valueOf(4.0));
      apply(minecraftClient, triggerbotModule, 20);
      apply(interactionManager2.count == 5, "four-tick intervals without off-by-one");
      reset(triggerbotModule, interactionManager2);
      triggerbotModule.timingSetting.setValue("Delay");
      triggerbotModule.minDelaySetting.setNumber(Double.valueOf(0.0));
      triggerbotModule.maxDelaySetting.setNumber(Double.valueOf(0.0));
      apply(minecraftClient, triggerbotModule, 20);
      apply(interactionManager2.count == 20, "zero delay stays bounded to one attack per tick");
      reset(triggerbotModule, interactionManager2);
      triggerbotModule.minCpsSetting.setNumber(Double.valueOf(20.0));
      triggerbotModule.maxCpsSetting.setNumber(Double.valueOf(8.0));
      apply(minecraftClient, triggerbotModule, 100);
      apply(
          interactionManager2.count >= 39 && interactionManager2.count <= 100,
          "reversed CPS bounds are normalized");
      reset(triggerbotModule, interactionManager2);
      triggerbotModule.reactionDelaySetting.setNumber(Double.valueOf(3.0));
      apply(minecraftClient, triggerbotModule, 3);
      apply(interactionManager2.count == 0, "reaction delay waits three full ticks");
      apply(minecraftClient, triggerbotModule, 1);
      apply(interactionManager2.count == 1, "reaction delay then attacks");
      minecraftClient.player.setYaw(90.0F);
      apply(minecraftClient, triggerbotModule, 10);
      apply(interactionManager2.count == 1, "stale rendered target is not attacked after turning");
      minecraftClient.player.setYaw(0.0F);
      apply(minecraftClient, triggerbotModule, 3);
      apply(interactionManager2.count == 1, "target loss restarts reaction delay");
      apply(minecraftClient, triggerbotModule, 1);
      apply(interactionManager2.count == 2, "reacquisition attacks once without catch-up burst");
      reset(triggerbotModule, interactionManager2);
      triggerbotModule.rangeSetting.setNumber(Double.valueOf(1.0));
      apply(minecraftClient, triggerbotModule, 2);
      apply(interactionManager2.count == 0, "range is measured to crosshair hit");
      triggerbotModule.rangeSetting.resetToDefault();
      BlockPos blockPos = new BlockPos(0, 121, 2);
      BlockState blockState = minecraftClient.world.getBlockState(blockPos);
      minecraftClient.world.setBlockState(blockPos, Blocks.STONE.getDefaultState(), 3);
      apply(minecraftClient, triggerbotModule, 2);
      apply(interactionManager2.count == 0, "wall occludes attacks");
      minecraftClient.world.setBlockState(blockPos, blockState, 3);
      hoppinStandingBeforeTestSupport12.setInvisible(true);
      apply(minecraftClient, triggerbotModule, 2);
      apply(interactionManager2.count == 0, "invisible targets skipped");
      hoppinStandingBeforeTestSupport12.setInvisible(false);
      triggerbotModule.playersSetting.setValue(false);
      apply(minecraftClient, triggerbotModule, 2);
      apply(interactionManager2.count == 0, "player filter works");
      triggerbotModule.playersSetting.setValue(true);
      triggerbotModule.onlyItemSetting.setValue(true);
      apply(minecraftClient, triggerbotModule, 2);
      apply(interactionManager2.count == 0, "sword filter rejects empty hand");
      minecraftClient.player.setStackInHand(Hand.MAIN_HAND, new ItemStack(Items.DIAMOND_SWORD));
      apply(minecraftClient, triggerbotModule, 1);
      apply(interactionManager2.count == 1, "sword filter accepts vanilla sword tag");
      reset(triggerbotModule, interactionManager2);
      minecraftClient.player.resetTicksSince();
      triggerbotModule.respectCooldownSetting.setValue(true);
      apply(minecraftClient, triggerbotModule, 5);
      apply(interactionManager2.count == 0, "cooldown prevents early attacks even at 20 CPS");
      triggerbotModule.respectCooldownSetting.setValue(false);
      triggerbotModule.onlyCritSetting.setValue(true);
      minecraftClient.player.setOnGround(true);
      apply(minecraftClient, triggerbotModule, 2);
      apply(interactionManager2.count == 0, "Only Crit skips grounded and uncharged attacks");
      apply2(minecraftClient);
      minecraftClient.player.setOnGround(false);
      minecraftClient.player.fallDistance = 0.5;
      minecraftClient.player.setSprinting(false);
      apply(minecraftClient, triggerbotModule, 1);
      apply(interactionManager2.count == 1, "Only Crit accepts a charged natural falling attack");
      minecraftClient.player.setSprinting(true);
      apply(minecraftClient, triggerbotModule, 2);
      apply(interactionManager2.count == 1, "sprinting is not a critical attack");
      minecraftClient.player.setSprinting(false);
      minecraftClient.player.setOnGround(true);
      minecraftClient.player.fallDistance = 0.0;
      interactionManager2.count = 0;
      triggerbotModule.onlyCritSetting.setValue(false);
      triggerbotModule.checkShieldSetting.setValue(true);
      hoppinStandingBeforeTestSupport12.setStackInHand(Hand.OFF_HAND, new ItemStack(Items.SHIELD));
      hoppinStandingBeforeTestSupport12.update();

      for (int index = 0; index < 8; index++) {
        hoppinStandingBeforeTestSupport12.tick();
      }

      apply(hoppinStandingBeforeTestSupport12.isBlocking(), "shield fixture is actively blocking");
      apply(minecraftClient, triggerbotModule, 2);
      apply(interactionManager2.count == 0, "Check Shield skips blocking target");
      hoppinStandingBeforeTestSupport12.update2();
      apply(minecraftClient, triggerbotModule, 1);
      apply(interactionManager2.count == 1, "lowered shield allows attack");
      interactionManager2.count = 0;
      triggerbotModule.requireAttackKeySetting.setValue(true);
      minecraftClient.options.attackKey.setPressed(false);
      apply(minecraftClient, triggerbotModule, 2);
      apply(interactionManager2.count == 0, "attack-key requirement");
      triggerbotModule.requireAttackKeySetting.setValue(false);
      minecraftClient.setScreen(new InventoryScreen(minecraftClient.player));
      apply(minecraftClient, triggerbotModule, 2);
      apply(interactionManager2.count == 0, "inventory screen blocks attacks");
      minecraftClient.setScreen(null);
      minecraftClient.attackCooldown = 0;
      minecraftClient.player.setStackInHand(Hand.MAIN_HAND, new ItemStack(Items.SHIELD));
      minecraftClient.player.setCurrentHand(Hand.MAIN_HAND);
      apply(minecraftClient, triggerbotModule, 2);
      apply(interactionManager2.count == 0, "item use blocks attacks");
      minecraftClient.player.clearActiveItem();
      minecraftClient.attackCooldown = 4;
      apply(minecraftClient, triggerbotModule, 2);
      apply(
          interactionManager2.count == 0 && minecraftClient.attackCooldown == 4,
          "vanilla miss cooldown is preserved");
      minecraftClient.attackCooldown = 0;
      interactionManager2.setGameMode(GameMode.SPECTATOR);
      apply(minecraftClient, triggerbotModule, 2);
      apply(interactionManager2.count == 0, "spectator cannot attack");
      interactionManager2.setGameMode(GameMode.SURVIVAL);
      triggerbotModule.setEnabled(false);
      apply(minecraftClient, triggerbotModule, 2);
      apply(interactionManager2.count == 0, "disabled module cannot attack");
    } finally {
      triggerbotModule.setEnabled(false);
      minecraftClient.player.clearActiveItem();
      minecraftClient.player.setStackInHand(Hand.MAIN_HAND, itemStack);
      minecraftClient.player.refreshPositionAndAngles(
          entityPos.x, entityPos.y, entityPos.z, yaw, pitch);
      minecraftClient.player.resetPosition();
      minecraftClient.player.age = age;
      minecraftClient.world.removeEntity(
          hoppinStandingBeforeTestSupport12.getId(), RemovalReason.DISCARDED);
      minecraftClient.interactionManager = interactionManager;
      minecraftClient.setScreen(null);
    }
  }

  public static void reset(
      TriggerbotModule triggerbotModule,
      HoppinStandingBeforeTestSupport5 hoppinStandingBeforeTestSupport5) {
    triggerbotModule.setEnabled(false);
    triggerbotModule
        .getSettings()
        .forEach(
            value -> {
              ((bunnythekenzo.client.setting.Setting) (value)).resetToDefault();
            });
    triggerbotModule.timingSetting.setValue("CPS");
    triggerbotModule.minCpsSetting.setNumber(Double.valueOf(20.0));
    triggerbotModule.maxCpsSetting.setNumber(Double.valueOf(20.0));
    triggerbotModule.respectCooldownSetting.setValue(false);
    hoppinStandingBeforeTestSupport5.count = 0;
    triggerbotModule.setEnabled(true);
  }

  public static void apply(
      MinecraftClient minecraftClient, TriggerbotModule triggerbotModule, int intValue) {
    ProtectionPerformanceTest.ensureInitialized();

    for (int index = 0; index < intValue; index++) {
      minecraftClient.player.age++;
      triggerbotModule.onTick();
    }
  }

  public static void apply2(MinecraftClient minecraftClient) {
    try {
      Field declaredField =
          bunnythekenzo.client.runtime.MappedReflection.field(
              LivingEntity.class, "ticksSinceLastAttack", "field_6273", "I");
      declaredField.setAccessible(true);
      declaredField.setInt(minecraftClient.player, 100);
    } catch (ReflectiveOperationException failure) {
      throw new AssertionError(failure);
    }
  }

  public static void apply(boolean flag, String text) {
    if (!flag) {
      throw new AssertionError("Triggerbot: " + text);
    }
  }
}
