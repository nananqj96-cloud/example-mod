package bunnythekenzo.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.PlayerLikeEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import bunnythekenzo.client.EquipmentPreview;
import bunnythekenzo.client.FakeLootDropModule;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin2 {
  @Inject(
      method =
          "updateRenderState(Lnet/minecraft/entity/PlayerLikeEntity;Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;F)V",
      at = @At("TAIL"))
  public void render(
      PlayerLikeEntity playerLikeEntity,
      PlayerEntityRenderState playerEntityRenderState,
      float floatValue,
      CallbackInfo callbackInfo) {
    FakeLootDropModule fakeLootDropModule = FakeLootDropModule.getFakeLootDropModule();
    if (fakeLootDropModule != null
        && fakeLootDropModule.isEnabled()
        && playerLikeEntity == MinecraftClient.getInstance().player) {
      EquipmentPreview equipmentPreview = fakeLootDropModule.equipmentPreview;
      if (!equipmentPreview.getItemStack(EquipmentSlot.HEAD).isEmpty()) {
        playerEntityRenderState.equippedHeadStack =
            equipmentPreview.getItemStack(EquipmentSlot.HEAD);
        playerEntityRenderState.spyglassState.clear();
        playerEntityRenderState.headItemRenderState.clear();
        playerEntityRenderState.wearingSkullType = null;
      }

      if (!equipmentPreview.getItemStack(EquipmentSlot.CHEST).isEmpty()) {
        playerEntityRenderState.equippedChestStack =
            equipmentPreview.getItemStack(EquipmentSlot.CHEST);
      }

      if (!equipmentPreview.getItemStack(EquipmentSlot.LEGS).isEmpty()) {
        playerEntityRenderState.equippedLegsStack =
            equipmentPreview.getItemStack(EquipmentSlot.LEGS);
      }

      if (!equipmentPreview.getItemStack(EquipmentSlot.FEET).isEmpty()) {
        playerEntityRenderState.equippedFeetStack =
            equipmentPreview.getItemStack(EquipmentSlot.FEET);
      }

      if (equipmentPreview.test3() && MinecraftClient.getInstance().currentScreen == null) {
        Vec3d vec3d = equipmentPreview.getVec3d(floatValue);
        playerEntityRenderState.x = vec3d.x;
        playerEntityRenderState.y = vec3d.y;
        playerEntityRenderState.z = vec3d.z;
        playerEntityRenderState.isGliding = true;
        playerEntityRenderState.isInSneakingPose = false;
        playerEntityRenderState.isSwimming = false;
        playerEntityRenderState.glidingTicks = 20.0F;
        playerEntityRenderState.pitch = equipmentPreview.getAmount2();
        playerEntityRenderState.bodyYaw = equipmentPreview.getAmount();
        playerEntityRenderState.relativeHeadYaw = 0.0F;
        playerEntityRenderState.limbAmplitudeInverse = 1.0F;
        playerEntityRenderState.leftWingPitch = (float) Math.toRadians(20.0);
        playerEntityRenderState.leftWingYaw = 0.0F;
        playerEntityRenderState.leftWingRoll = (float) Math.toRadians(-70.0);
        playerEntityRenderState.applyFlyingRotation = false;
        playerEntityRenderState.shadowPieces.clear();
      }
    }
  }
}
