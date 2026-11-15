package bunnythekenzo.client.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockBreakingProgressS2CPacket;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ChunkDataS2CPacket;
import net.minecraft.network.packet.s2c.play.ChunkDeltaUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.CommandSuggestionsS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityTrackerUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.ParticleS2CPacket;
import net.minecraft.network.packet.s2c.play.PlaySoundFromEntityS2CPacket;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRemoveS2CPacket;
import net.minecraft.network.packet.s2c.play.UnloadChunkS2CPacket;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.collection.WeightedPool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.PearlEscapeAutomation;
import bunnythekenzo.client.ServerTickRate;
import bunnythekenzo.client.module.BlockEntityESPModule;
import bunnythekenzo.client.module.ClusterESPModule;
import bunnythekenzo.client.module.ModuleManager;
import bunnythekenzo.client.module.SpawnerProtectModule;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
  private static void apply(Packet packet) {
    ModuleManager moduleManager = ClientInitializer.getModuleManager();
    if (moduleManager != null
        && moduleManager.antiVanishModule != null
        && moduleManager.antiVanishModule.isEnabled()) {
      moduleManager.antiVanishModule.test(packet);
    }
  }

  @Inject(method = "onPlayerRemove", at = @At("HEAD"))
  public void apply(PlayerRemoveS2CPacket playerRemoveS2CPacket, CallbackInfo callbackInfo) {
    apply(playerRemoveS2CPacket);
  }

  @Inject(method = "onPlayerList", at = @At("HEAD"))
  public void apply(PlayerListS2CPacket playerListS2CPacket, CallbackInfo callbackInfo) {
    apply(playerListS2CPacket);
  }

  @Inject(method = "onGameMessage", at = @At("HEAD"))
  public void apply(GameMessageS2CPacket gameMessageS2CPacket, CallbackInfo callbackInfo) {
    apply(gameMessageS2CPacket);
  }

  @Inject(method = "onCommandSuggestions", at = @At("HEAD"), cancellable = true)
  public void apply(
      CommandSuggestionsS2CPacket commandSuggestionsS2CPacket, CallbackInfo callbackInfo) {
    ModuleManager moduleManager = ClientInitializer.getModuleManager();
    if (moduleManager != null
        && moduleManager.antiVanishModule != null
        && moduleManager.antiVanishModule.isEnabled()
        && moduleManager.antiVanishModule.test(commandSuggestionsS2CPacket)) {
      callbackInfo.cancel();
    }
  }

  @Inject(method = "onEntityTrackerUpdate", at = @At("HEAD"))
  public void apply(
      EntityTrackerUpdateS2CPacket entityTrackerUpdateS2CPacket, CallbackInfo callbackInfo) {
    apply(entityTrackerUpdateS2CPacket);
  }

  @Inject(method = "onExplosion", at = @At("HEAD"))
  public void apply(ExplosionS2CPacket explosionS2CPacket, CallbackInfo callbackInfo) {
    apply(explosionS2CPacket);
  }

  @Inject(method = "onPlaySoundFromEntity", at = @At("HEAD"))
  public void apply(
      PlaySoundFromEntityS2CPacket playSoundFromEntityS2CPacket, CallbackInfo callbackInfo) {
    apply(playSoundFromEntityS2CPacket);
  }

  @Inject(method = "onParticle", at = @At("HEAD"))
  public void apply(ParticleS2CPacket particleS2CPacket, CallbackInfo callbackInfo) {
    apply(particleS2CPacket);
  }

  @Inject(method = "onPlaySound", at = @At("HEAD"))
  public void apply(PlaySoundS2CPacket playSoundS2CPacket, CallbackInfo callbackInfo) {
    apply(playSoundS2CPacket);
  }

  @Inject(
      method = "onExplosion",
      at =
          @At(
              value = "INVOKE",
              target =
                  "Lnet/minecraft/client/world/ClientWorld;addParticleClient(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V"))
  public void apply(
      ExplosionS2CPacket explosionS2CPacket,
      CallbackInfo callbackInfo,
      @Share("crystalClean") LocalBooleanRef localBooleanRef) {
    ModuleManager moduleManager = ClientInitializer.getModuleManager();
    if (moduleManager != null) {
      boolean flag = moduleManager.crystalPulseModule.test(explosionS2CPacket.center());
      localBooleanRef.set(
          flag
              && (Boolean) moduleManager.crystalPulseModule.cleanExplosionsSetting.getValue()
              && !moduleManager.freecamModule.test()
              && MinecraftClient.getInstance().player != null
              && !MinecraftClient.getInstance().player.isSpectator());
    }
  }

  @WrapWithCondition(
      method = "onExplosion",
      at =
          @At(
              value = "INVOKE",
              target =
                  "Lnet/minecraft/client/world/ClientWorld;addParticleClient(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V"))
  public boolean test(
      ClientWorld clientWorld,
      ParticleEffect particleEffect,
      double doubleValue,
      double doubleValue2,
      double doubleValue3,
      double doubleValue4,
      double doubleValue5,
      double doubleValue6,
      @Share("crystalClean") LocalBooleanRef localBooleanRef) {
    return !localBooleanRef.get();
  }

  @WrapWithCondition(
      method = "onExplosion",
      at =
          @At(
              value = "INVOKE",
              target =
                  "Lnet/minecraft/client/world/ClientWorld;addBlockParticleEffects(Lnet/minecraft/util/math/Vec3d;FILnet/minecraft/util/collection/WeightedPool;)V"))
  public boolean test(
      ClientWorld clientWorld,
      Vec3d vec3d,
      float floatValue,
      int intValue,
      WeightedPool weightedPool,
      @Share("crystalClean") LocalBooleanRef localBooleanRef) {
    return !localBooleanRef.get();
  }

  @Inject(method = "onPlayerPositionLook", at = @At("TAIL"))
  public void apply(
      PlayerPositionLookS2CPacket playerPositionLookS2CPacket, CallbackInfo callbackInfo) {
    PearlEscapeAutomation.update();
  }

  @Inject(method = "onWorldTimeUpdate", at = @At("HEAD"))
  public void apply(WorldTimeUpdateS2CPacket worldTimeUpdateS2CPacket, CallbackInfo callbackInfo) {
    ServerTickRate.recordPacket();
  }

  @Inject(method = "sendChatCommand", at = @At("HEAD"), cancellable = true)
  public void apply(String text, CallbackInfo callbackInfo) {
    ModuleManager moduleManager = ClientInitializer.getModuleManager();
    if (moduleManager != null) {
      try {
        if (moduleManager.fakePayModule != null && moduleManager.fakePayModule.test(text)) {
          callbackInfo.cancel();
          return;
        }

        if (moduleManager.fakeStatsModule != null && moduleManager.fakeStatsModule.test(text)) {
          callbackInfo.cancel();
        }
      } catch (Exception failure) {
      }
    }
  }

  @Inject(method = "onChunkData", at = @At("TAIL"))
  public void apply(ChunkDataS2CPacket chunkDataS2CPacket, CallbackInfo callbackInfo) {
    ClusterESPModule clusterESPModule = getClusterESPModule();
    if (clusterESPModule != null && clusterESPModule.isEnabled()) {
      clusterESPModule.apply(chunkDataS2CPacket.getChunkX(), chunkDataS2CPacket.getChunkZ());
    }

    BlockEntityESPModule blockEntityESPModule = getBlockEntityESPModule();
    if (blockEntityESPModule != null
        && blockEntityESPModule.isEnabled()
        && blockEntityESPModule.test()) {
      try {
        chunkDataS2CPacket
            .getChunkData()
            .getBlockEntities(chunkDataS2CPacket.getChunkX(), chunkDataS2CPacket.getChunkZ())
            .accept(
                (referenceArgument57_0, referenceArgument57_1, referenceArgument57_2) ->
                    ClientPlayNetworkHandlerMixin.handleChunkBlockEntity(
                        blockEntityESPModule,
                        (net.minecraft.util.math.BlockPos) referenceArgument57_0,
                        (net.minecraft.block.entity.BlockEntityType) referenceArgument57_1,
                        (net.minecraft.nbt.NbtCompound) referenceArgument57_2));
      } catch (Exception failure) {
      }
    }
  }

  @Inject(method = "onBlockEntityUpdate", at = @At("TAIL"))
  public void apply(
      BlockEntityUpdateS2CPacket blockEntityUpdateS2CPacket, CallbackInfo callbackInfo) {
    BlockEntityESPModule blockEntityESPModule = getBlockEntityESPModule();
    if (blockEntityESPModule != null
        && blockEntityESPModule.isEnabled()
        && blockEntityESPModule.test4()) {
      try {
        blockEntityESPModule.apply(
            blockEntityUpdateS2CPacket.getPos(), blockEntityUpdateS2CPacket.getBlockEntityType());
      } catch (Exception failure) {
      }
    }
  }

  private static BlockEntityESPModule getBlockEntityESPModule() {
    ModuleManager moduleManager = ClientInitializer.getModuleManager();
    return moduleManager != null ? moduleManager.blockEntityESPModule : null;
  }

  private static SpawnerProtectModule getSpawnerProtectModule() {
    ModuleManager moduleManager = ClientInitializer.getModuleManager();
    return moduleManager != null ? moduleManager.spawnerProtectModule : null;
  }

  private static ClusterESPModule getClusterESPModule() {
    ModuleManager moduleManager = ClientInitializer.getModuleManager();
    return moduleManager != null ? moduleManager.clusterESPModule : null;
  }

  @Inject(method = "onUnloadChunk", at = @At("TAIL"))
  public void apply(UnloadChunkS2CPacket unloadChunkS2CPacket, CallbackInfo callbackInfo) {
    ClusterESPModule clusterESPModule = getClusterESPModule();
    if (clusterESPModule != null) {
      clusterESPModule.apply2(unloadChunkS2CPacket.pos().x, unloadChunkS2CPacket.pos().z);
    }
  }

  @Inject(method = "onBlockBreakingProgress", at = @At("HEAD"))
  public void apply(
      BlockBreakingProgressS2CPacket blockBreakingProgressS2CPacket, CallbackInfo callbackInfo) {
    SpawnerProtectModule spawnerProtectModule = getSpawnerProtectModule();
    if (spawnerProtectModule != null
        && spawnerProtectModule.isEnabled()
        && MinecraftClient.getInstance().isOnThread()) {
      try {
        spawnerProtectModule.apply(
            blockBreakingProgressS2CPacket.getEntityId(), blockBreakingProgressS2CPacket.getPos());
      } catch (Exception failure) {
      }
    }
  }

  @Inject(method = "onBlockUpdate", at = @At("HEAD"))
  public void apply(BlockUpdateS2CPacket blockUpdateS2CPacket, CallbackInfo callbackInfo) {
    if (MinecraftClient.getInstance().isOnThread()) {
      ClusterESPModule clusterESPModule = getClusterESPModule();
      if (clusterESPModule != null && clusterESPModule.isEnabled()) {
        clusterESPModule.apply(blockUpdateS2CPacket.getPos());
      }

      SpawnerProtectModule spawnerProtectModule = getSpawnerProtectModule();
      if (spawnerProtectModule != null
          && spawnerProtectModule.isEnabled()
          && spawnerProtectModule.test()) {
        try {
          spawnerProtectModule.apply(
              blockUpdateS2CPacket.getPos(), blockUpdateS2CPacket.getState(), false);
        } catch (Exception failure) {
        }
      }
    }
  }

  @Inject(method = "onChunkDeltaUpdate", at = @At("HEAD"))
  public void apply(
      ChunkDeltaUpdateS2CPacket chunkDeltaUpdateS2CPacket, CallbackInfo callbackInfo) {
    if (MinecraftClient.getInstance().isOnThread()) {
      ClusterESPModule clusterESPModule = getClusterESPModule();
      SpawnerProtectModule spawnerProtectModule = getSpawnerProtectModule();
      boolean flag = clusterESPModule != null && clusterESPModule.isEnabled();
      boolean flag2 =
          spawnerProtectModule != null
              && spawnerProtectModule.isEnabled()
              && spawnerProtectModule.test();
      if (flag || flag2) {
        try {
          boolean[] flags = new boolean[] {false};
          chunkDeltaUpdateS2CPacket.visitUpdates(
              (java.util.function.BiConsumer<
                      net.minecraft.util.math.BlockPos, net.minecraft.block.BlockState>)
                  ((referenceArgument58_0, referenceArgument58_1) ->
                      ClientPlayNetworkHandlerMixin.handleSectionBlockUpdate(
                          flag,
                          flags,
                          clusterESPModule,
                          flag2,
                          spawnerProtectModule,
                          (net.minecraft.util.math.BlockPos) referenceArgument58_0,
                          (net.minecraft.block.BlockState) referenceArgument58_1)));
        } catch (Exception failure) {
        }
      }
    }
  }

  private static void handleSectionBlockUpdate(
      boolean flag,
      boolean[] flags,
      ClusterESPModule clusterESPModule,
      boolean flag2,
      SpawnerProtectModule spawnerProtectModule,
      BlockPos blockPos,
      BlockState blockState) {
    if (flag && !flags[0]) {
      clusterESPModule.apply(blockPos);
      flags[0] = true;
    }

    if (flag2) {
      spawnerProtectModule.apply(blockPos, blockState, true);
    }
  }

  private static void handleChunkBlockEntity(
      BlockEntityESPModule blockEntityESPModule,
      BlockPos blockPos,
      BlockEntityType blockEntityType,
      NbtCompound nbtCompound) {
    blockEntityESPModule.apply(blockPos, blockEntityType);
  }
}
