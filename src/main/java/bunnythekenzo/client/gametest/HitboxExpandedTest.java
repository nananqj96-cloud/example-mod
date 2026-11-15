package bunnythekenzo.client.gametest;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.client.option.Perspective;
import net.minecraft.entity.Entity;
import net.minecraft.entity.Entity.RemovalReason;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.GuiModuleEntry;
import bunnythekenzo.client.mixin.MinecraftClientAccessor;
import bunnythekenzo.client.module.HitBoxModule;
import bunnythekenzo.client.ui.PanelScreen;

public final class HitboxExpandedTest {
  public static void run(
      ClientGameTestContext clientGameTestContext,
      TestSingleplayerContext testSingleplayerContext) {
    JsonObject onClient =
        clientGameTestContext.computeOnClient(
            minecraftClient -> {
              return ClientInitializer.getClientConfig().getJsonObject();
            });
    OtherClientPlayerEntity[] otherClientPlayerEntityArray = new OtherClientPlayerEntity[2];

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
            ClientInitializer.getModuleManager()
                .hitBoxModule
                .getSettings()
                .forEach(
                    (referenceArgument43_0) ->
                        ((bunnythekenzo.client.setting.Setting) referenceArgument43_0).resetToDefault());
            ClientInitializer.getModuleManager().hUDModule.notificationsSetting.setValue(false);
            minecraftClient2.options.getMaxFps().setValue(60);
            minecraftClient2.options.setPerspective(Perspective.FIRST_PERSON);
            minecraftClient2.setScreen(null);
          });
      testSingleplayerContext.getServer().runCommand("gamemode survival @a");
      testSingleplayerContext
          .getServer()
          .runOnServer(
              minecraftServer -> {
                ServerWorld overworld = minecraftServer.getOverworld();
                ProtectionPerformanceTest.ensureInitialized();

                for (int index = -6; index <= 6; index++) {
                  for (int index2 = -6; index2 <= 8; index2++) {
                    overworld.setBlockState(
                        new BlockPos(index, 180, index2), Blocks.SMOOTH_STONE.getDefaultState());

                    for (int index3 = 181; index3 < 187; index3++) {
                      overworld.setBlockState(
                          new BlockPos(index, index3, index2), Blocks.AIR.getDefaultState());
                    }
                  }
                }

                minecraftServer
                    .getPlayerManager()
                    .getPlayerList()
                    .getFirst()
                    .requestTeleport(0.5, 181.0, 0.5);
              });
      clientGameTestContext.waitTicks(25);
      clientGameTestContext.runOnClient(
          minecraftClient3 -> {
            apply(minecraftClient3);
            otherClientPlayerEntityArray[0] =
                getOtherClientPlayerEntity(minecraftClient3, -67801, 0.65, 2.5);
            otherClientPlayerEntityArray[1] =
                getOtherClientPlayerEntity(minecraftClient3, -67802, 4.0, 2.0);
            checkPicking(
                minecraftClient3, otherClientPlayerEntityArray[0], otherClientPlayerEntityArray[1]);
          });
      clientGameTestContext.waitTicks(10);
      clientGameTestContext.takeScreenshot("hitbox-expanded");
      clientGameTestContext.runOnClient(
          minecraftClient4 -> {
            PanelScreen panelScreen = new PanelScreen();
            minecraftClient4.setScreen(panelScreen);
            apply(panelScreen.test("HitBox"), "settings panel opens");
          });
      clientGameTestContext.waitTicks(8);
      clientGameTestContext.takeScreenshot("hitbox-settings");
      clientGameTestContext.runOnClient(
          minecraftClient5 -> {
            ProtectionPerformanceTest.ensureInitialized();

            minecraftClient5.setScreen(null);

            for (OtherClientPlayerEntity otherClientPlayerEntity : otherClientPlayerEntityArray) {
              minecraftClient5.world.removeEntity(
                  otherClientPlayerEntity.getId(), RemovalReason.DISCARDED);
            }

            ClientInitializer.getModuleManager().hitBoxModule.mobsSetting.setValue(true);
            apply(minecraftClient5);
          });
      testSingleplayerContext
          .getServer()
          .runCommand(
              "summon minecraft:villager 1.15 181 3"
                  + " {NoAI:1b,NoGravity:1b,Silent:1b,Tags:[hitbox_test]}");
      clientGameTestContext.waitTicks(20);
      clientGameTestContext.runOnClient(
          minecraftClient6 -> {
            ProtectionPerformanceTest.ensureInitialized();

            HitBoxModule hitBoxModule = ClientInitializer.getModuleManager().hitBoxModule;
            hitBoxModule.setEnabled(false);
            apply(
                getEntity(minecraftClient6) == null,
                "real server target is outside the vanilla ray");
            hitBoxModule.setEnabled(true);
            Entity entity = getEntity(minecraftClient6);
            apply(entity instanceof LivingEntity, "expanded ray selects real server mob");
            hitBoxModule.mobsSetting.setValue(false);
            apply(getEntity(minecraftClient6) == null, "mob filter disables mob expansion");
            hitBoxModule.mobsSetting.setValue(true);
            getEntity(minecraftClient6);
            ((MinecraftClientAccessor) (Object) (minecraftClient6)).invokeDoAttack();
          });
      clientGameTestContext.waitTicks(10);
      testSingleplayerContext
          .getServer()
          .runOnServer(
              minecraftServer2 -> {
                ProtectionPerformanceTest.ensureInitialized();

                var entitiesByClass =
                    minecraftServer2
                        .getOverworld()
                        .getEntitiesByClass(
                            LivingEntity.class,
                            new Box(-4.0, 180.0, -4.0, 5.0, 185.0, 6.0),
                            livingEntity -> {
                              return livingEntity.getCommandTags().contains("hitbox_test");
                            });
                apply(
                    entitiesByClass.size() == 1
                        && ((LivingEntity) entitiesByClass.getFirst()).getHealth()
                            < ((LivingEntity) entitiesByClass.getFirst()).getMaxHealth(),
                    "vanilla server received the manual attack and applied damage");
                apply(
                    ((LivingEntity) entitiesByClass.getFirst()).getBoundingBox().getLengthX() < 0.7,
                    "server hitbox stays original");
              });
      ClientInitializer.logger.info(
          "HitBox verified: live picking, filters, occlusion, range, poses, config, cleanup,"
              + " rendering and server damage");
    } finally {
      clientGameTestContext.runOnClient(
          minecraftClient7 -> {
            ProtectionPerformanceTest.ensureInitialized();

            ClientInitializer.getModuleManager().hitBoxModule.setEnabled(false);
            if (minecraftClient7.world != null) {
              for (OtherClientPlayerEntity otherClientPlayerEntity2 :
                  otherClientPlayerEntityArray) {
                if (otherClientPlayerEntity2 != null && !otherClientPlayerEntity2.isRemoved()) {
                  minecraftClient7.world.removeEntity(
                      otherClientPlayerEntity2.getId(), RemovalReason.DISCARDED);
                }
              }
            }

            minecraftClient7.setScreen(null);
            ClientInitializer.getClientConfig().apply(onClient);
          });
    }
  }

  public static void checkPicking(
      MinecraftClient minecraftClient,
      OtherClientPlayerEntity otherClientPlayerEntity,
      OtherClientPlayerEntity otherClientPlayerEntity2) {
    ProtectionPerformanceTest.ensureInitialized();

    HitBoxModule hitBoxModule = ClientInitializer.getModuleManager().hitBoxModule;
    Box boundingBox = otherClientPlayerEntity.getBoundingBox();
    apply(getEntity(minecraftClient) == null, "disabled module preserves vanilla miss");
    hitBoxModule.setEnabled(true);
    apply(
        getEntity(minecraftClient) == otherClientPlayerEntity,
        "live mixin selects expanded horizontal box");

    for (int index = 0; index < 100; index++) {
      getEntity(minecraftClient);
    }

    apply(
        otherClientPlayerEntity.getBoundingBox().equals(boundingBox),
        "repeated picking never mutates physics bounds");
    hitBoxModule.setEnabled(false);
    apply(minecraftClient.targetedEntity == null, "disable immediately clears expanded selection");
    hitBoxModule.setEnabled(true);
    hitBoxModule.expandSetting.setNumber(Double.valueOf(0.0));
    apply(getEntity(minecraftClient) == null, "zero expansion preserves vanilla behavior");
    hitBoxModule.expandSetting.resetToDefault();
    hitBoxModule.enableRenderSetting.setValue(false);
    apply(
        getEntity(minecraftClient) == otherClientPlayerEntity
            && !hitBoxModule.colorSetting.isVisible(),
        "render toggle is independent of picking");
    hitBoxModule.enableRenderSetting.setValue(true);
    hitBoxModule.playersSetting.setValue(false);
    apply(getEntity(minecraftClient) == null, "player filter takes effect immediately");
    hitBoxModule.playersSetting.setValue(true);
    otherClientPlayerEntity.setInvisible(true);
    apply(getEntity(minecraftClient) == null, "invisible players excluded by default");
    hitBoxModule.invisibleSetting.setValue(true);
    apply(
        getEntity(minecraftClient) == otherClientPlayerEntity,
        "invisible option enables expansion");
    otherClientPlayerEntity.setInvisible(false);
    hitBoxModule.invisibleSetting.resetToDefault();
    Scoreboard scoreboard = minecraftClient.world.getScoreboard();
    Team team = scoreboard.addTeam("hitbox_test");
    scoreboard.addScoreHolderToTeam(minecraftClient.player.getNameForScoreboard(), team);
    scoreboard.addScoreHolderToTeam(otherClientPlayerEntity.getNameForScoreboard(), team);

    try {
      apply(getEntity(minecraftClient) == null, "teammates excluded by default");
      hitBoxModule.teammatesSetting.setValue(true);
      apply(
          getEntity(minecraftClient) == otherClientPlayerEntity,
          "teammates setting enables expansion");
    } finally {
      scoreboard.removeTeam(team);
      hitBoxModule.teammatesSetting.resetToDefault();
    }

    render(minecraftClient, otherClientPlayerEntity2, 0.65, 1.5);
    apply(getEntity(minecraftClient) == otherClientPlayerEntity2, "nearest expanded target wins");
    render(minecraftClient, otherClientPlayerEntity2, 4.0, 2.0);
    ArmorStandEntity armorStandEntity =
        new ArmorStandEntity(
            minecraftClient.world,
            minecraftClient.player.getX(),
            minecraftClient.player.getY(),
            minecraftClient.player.getZ() + 1.0);
    armorStandEntity.setId(-67803);
    minecraftClient.world.addEntity(armorStandEntity);

    try {
      apply(
          getEntity(minecraftClient) == armorStandEntity,
          "unexpanded foreground entity still blocks the target");
    } finally {
      minecraftClient.world.removeEntity(armorStandEntity.getId(), RemovalReason.DISCARDED);
    }

    BlockPos blockPos =
        BlockPos.ofFloored(
            minecraftClient.player.getX(),
            minecraftClient.player.getEyeY(),
            minecraftClient.player.getZ() + 1.0);
    BlockState blockState = minecraftClient.world.getBlockState(blockPos);

    try {
      minecraftClient.world.setBlockState(blockPos, Blocks.STONE.getDefaultState(), 3);
      apply(getEntity(minecraftClient) == null, "wall occludes expanded target");
    } finally {
      minecraftClient.world.setBlockState(blockPos, blockState, 3);
    }

    render(minecraftClient, otherClientPlayerEntity, 0.65, 3.6);
    apply(
        getEntity(minecraftClient) == null,
        "expansion cannot pull a target outside real interaction range into reach");
    hitBoxModule.expandSetting.setNumber(Double.valueOf(2.0));
    render(minecraftClient, otherClientPlayerEntity, 2.1, 1.9);
    apply(
        getEntity(minecraftClient) == otherClientPlayerEntity,
        "maximum expansion widens candidate search beyond vanilla broad phase");
    hitBoxModule.expandSetting.resetToDefault();
    render(minecraftClient, otherClientPlayerEntity, 0.65, 2.5);
    otherClientPlayerEntity.setPose(EntityPose.SWIMMING);
    otherClientPlayerEntity.calculateDimensions();
    Box boundingBox2 = otherClientPlayerEntity.getBoundingBox();
    apply(getEntity(minecraftClient) == null, "horizontal expansion respects reduced pose height");
    hitBoxModule.verticalExpandSetting.setNumber(Double.valueOf(1.2));
    apply(
        getEntity(minecraftClient) == otherClientPlayerEntity
            && otherClientPlayerEntity.getBoundingBox().equals(boundingBox2),
        "vertical expansion follows pose without changing physics");
    otherClientPlayerEntity.setPose(EntityPose.STANDING);
    otherClientPlayerEntity.calculateDimensions();
    hitBoxModule.verticalExpandSetting.resetToDefault();
    otherClientPlayerEntity.setHealth(0.0F);
    apply(getEntity(minecraftClient) == null, "dead targets are not expanded");
    otherClientPlayerEntity.setHealth(20.0F);
    minecraftClient.setCameraEntity(otherClientPlayerEntity2);
    apply(!hitBoxModule.test(minecraftClient), "detached camera disables expansion");
    minecraftClient.setCameraEntity(minecraftClient.player);
    JsonObject jsonObject = ClientInitializer.getClientConfig().getJsonObject();
    hitBoxModule.expandSetting.setNumber(Double.valueOf(1.75));
    hitBoxModule.verticalExpandSetting.setNumber(Double.valueOf(0.75));
    ClientInitializer.getClientConfig().apply(jsonObject);
    apply(
        (Double) hitBoxModule.expandSetting.getValue() == 0.5
            && (Double) hitBoxModule.verticalExpandSetting.getValue() == 0.0,
        "config restores both dimensions");
    GuiModuleEntry guiModuleEntry = GuiModuleEntry.of(hitBoxModule);
    apply(
        guiModuleEntry.getList().stream()
                .mapToInt(
                    collectionData -> {
                      return ((bunnythekenzo.client.CollectionData) (collectionData)).list().size();
                    })
                .sum()
            == hitBoxModule.getSettings().size(),
        "all settings have GUI controls");
    hitBoxModule.showOriginalSetting.setValue(true);
    apply(minecraftClient);
    apply(
        getEntity(minecraftClient) == otherClientPlayerEntity, "selection works after restoration");
  }

  public static OtherClientPlayerEntity getOtherClientPlayerEntity(
      MinecraftClient minecraftClient, int intValue, double doubleValue, double doubleValue2) {
    OtherClientPlayerEntity otherClientPlayerEntity =
        new OtherClientPlayerEntity(
            minecraftClient.world, new GameProfile(UUID.randomUUID(), "Hitbox " + -intValue));
    otherClientPlayerEntity.setId(intValue);
    render(minecraftClient, otherClientPlayerEntity, doubleValue, doubleValue2);
    otherClientPlayerEntity.setNoGravity(true);
    minecraftClient.world.addEntity(otherClientPlayerEntity);
    return otherClientPlayerEntity;
  }

  public static void render(
      MinecraftClient minecraftClient, Entity entity, double doubleValue, double doubleValue2) {
    entity.refreshPositionAndAngles(
        minecraftClient.player.getX() + doubleValue,
        minecraftClient.player.getY(),
        minecraftClient.player.getZ() + doubleValue2,
        180.0F,
        0.0F);
    entity.resetPosition();
  }

  public static void apply(MinecraftClient minecraftClient) {
    minecraftClient.player.setVelocity(Vec3d.ZERO);
    minecraftClient.player.setYaw(0.0F);
    minecraftClient.player.setPitch(0.0F);
    minecraftClient.player.resetPosition();
  }

  public static Entity getEntity(MinecraftClient minecraftClient) {
    minecraftClient.gameRenderer.updateCrosshairTarget(1.0F);
    return minecraftClient.targetedEntity;
  }

  public static void apply(boolean flag, String text) {
    if (!flag) {
      throw new AssertionError("HitBox: " + text);
    }
  }
}
