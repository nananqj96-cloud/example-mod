package bunnythekenzo.client;

import java.util.EnumMap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.PlayerInput;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import bunnythekenzo.client.mixin.InputAccessor;
import bunnythekenzo.client.module.FreecamModule;

public final class EquipmentPreview {
  public final FakeLootDropModule fakeLootDropModule;
  public final EnumMap enumMap = new EnumMap<>(EquipmentSlot.class);
  public ClientPlayerEntity clientPlayerEntity;
  public ClientWorld clientWorld;
  public Vec3d vec3d;
  public Vec3d vec3d2;
  public float amount;
  public float amount2;
  public boolean enabled;
  public boolean enabled2;
  public int count = -100;
  public int count2;

  public EquipmentPreview(FakeLootDropModule fakeLootDropModule2) {
    this.fakeLootDropModule = fakeLootDropModule2;
  }

  public boolean test(int count3) {
    MinecraftClient instance = MinecraftClient.getInstance();
    if (this.fakeLootDropModule.isEnabled()
        && instance.player != null
        && count3 >= 0
        && count3 < 36) {
      this.apply(instance);
      ItemStack stack = instance.player.getInventory().getStack(count3);
      if (!this.fakeLootDropModule.test(stack)) {
        return false;
      }

      ItemStack itemStack = this.fakeLootDropModule.getItemStack(stack);
      EquippableComponent equippableComponent = itemStack.get(DataComponentTypes.EQUIPPABLE);
      if (equippableComponent != null
          && test(equippableComponent.slot())
          && equippableComponent.allows(instance.player.getType())) {
        EquipmentPreviewData equipmentPreviewData =
            (EquipmentPreviewData) this.enumMap.get(equippableComponent.slot());
        if (equipmentPreviewData != null
            && equipmentPreviewData.count() == count3
            && equipmentPreviewData._v() == itemStack.getItem()) {
          this.enumMap.remove(equippableComponent.slot());
          this.update2();
          this.apply("Local equipment removed");
        } else {
          this.enumMap.put(
              equippableComponent.slot(),
              new EquipmentPreviewData(count3, stack.copyWithCount(1), itemStack.getItem()));
          this.apply(
              itemStack.isOf(Items.ELYTRA)
                  ? "Local Elytra equipped - double Space to preview flight, Shift to return"
                  : "Local "
                      + itemStack.getName().getString()
                      + " equipped - click its armor slot to remove");
        }

        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  public boolean test() {
    MinecraftClient instance = MinecraftClient.getInstance();
    if (this.test3()) {
      return true;
    } else if (instance.crosshairTarget != null
        && instance.crosshairTarget.getType() != Type.MISS) {
      return false;
    } else if (this.enabled2) {
      return true;
    } else if (instance.player != null
        && this.test(instance.player.getInventory().getSelectedSlot())) {
      this.enabled2 = true;
      return true;
    } else {
      return false;
    }
  }

  public ItemStack getItemStack(EquipmentSlot equipmentSlot) {
    EquipmentPreviewData equipmentPreviewData =
        (EquipmentPreviewData) this.enumMap.get(equipmentSlot);
    return equipmentPreviewData == null
        ? ItemStack.EMPTY
        : new ItemStack(equipmentPreviewData._v());
  }

  public EquipmentSlot getEquipmentSlot(Slot slot) {
    MinecraftClient instance = MinecraftClient.getInstance();
    if (slot != null
        && instance.player != null
        && instance.player.currentScreenHandler == instance.player.playerScreenHandler
        && slot.inventory == instance.player.getInventory()) {
      return switch (slot.getIndex()) {
        case 36 -> EquipmentSlot.FEET;
        case 37 -> EquipmentSlot.LEGS;
        case 38 -> EquipmentSlot.CHEST;
        case 39 -> EquipmentSlot.HEAD;
        default -> null;
      };
    } else {
      return null;
    }
  }

  public ItemStack getItemStack(Slot slot) {
    EquipmentSlot equipmentSlot = this.getEquipmentSlot(slot);
    return equipmentSlot == null ? ItemStack.EMPTY : this.getItemStack(equipmentSlot);
  }

  public ItemStack getItemStack2(Slot slot) {
    EquipmentSlot equipmentSlot = this.getEquipmentSlot(slot);
    EquipmentPreviewData equipmentPreviewData =
        equipmentSlot == null ? null : (EquipmentPreviewData) this.enumMap.get(equipmentSlot);
    return equipmentPreviewData == null ? ItemStack.EMPTY : equipmentPreviewData.itemStack();
  }

  public boolean test(Slot slot, SlotActionType slotActionType) {
    if (slot == null) {
      return false;
    } else {
      EquipmentSlot equipmentSlot = this.getEquipmentSlot(slot);
      if (equipmentSlot != null && this.enumMap.containsKey(equipmentSlot)) {
        this.enumMap.remove(equipmentSlot);
        this.update2();
        return true;
      } else {
        MinecraftClient instance = MinecraftClient.getInstance();
        return slotActionType == SlotActionType.QUICK_MOVE
            && instance.player != null
            && slot.inventory == instance.player.getInventory()
            && this.test(slot.getIndex());
      }
    }
  }

  public static boolean test(EquipmentSlot equipmentSlot) {
    return equipmentSlot == EquipmentSlot.HEAD
        || equipmentSlot == EquipmentSlot.CHEST
        || equipmentSlot == EquipmentSlot.LEGS
        || equipmentSlot == EquipmentSlot.FEET;
  }

  public void apply(MinecraftClient minecraftClient) {
    if (this.clientPlayerEntity != minecraftClient.player
        || this.clientWorld != minecraftClient.world) {
      this.update3();
      this.clientPlayerEntity = minecraftClient.player;
      this.clientWorld = minecraftClient.world;
    }
  }

  public void tick() {
    MinecraftClient instance = MinecraftClient.getInstance();
    this.apply(instance);
    this.count2++;
    if (!instance.options.useKey.isPressed()) {
      this.enabled2 = false;
    }

    if (this.clientPlayerEntity != null && this.clientPlayerEntity.isAlive()) {
      this.enumMap
          .entrySet()
          .removeIf(
              value -> {
                EquipmentPreviewData equipmentPreviewData =
                    (EquipmentPreviewData) ((java.util.Map.Entry) (value)).getValue();
                ItemStack stack =
                    this.clientPlayerEntity.getInventory().getStack(equipmentPreviewData.count());
                return !ItemStack.areItemsAndComponentsEqual(
                        stack, equipmentPreviewData.itemStack())
                    || this.fakeLootDropModule.itemReplacementsSetting.getItem(stack.getItem())
                        != equipmentPreviewData._v();
              });
      if (!this.getItemStack(EquipmentSlot.CHEST).isOf(Items.ELYTRA)
          || instance.currentScreen != null
          || FreecamModule.getInstance() != null && FreecamModule.getInstance().test()) {
        this.update2();
      }

      if (this.test3()) {
        if (!this.test(this.vec3d)) {
          this.update2();
        } else {
          this.vec3d2 = this.vec3d;
          if (instance.options.sneakKey.isPressed()) {
            this.update2();
          } else {
            double doubleValue =
                (instance.options.forwardKey.isPressed() ? 1 : 0)
                    - (instance.options.backKey.isPressed() ? 1 : 0);
            double doubleValue2 =
                (instance.options.rightKey.isPressed() ? 1 : 0)
                    - (instance.options.leftKey.isPressed() ? 1 : 0);
            Vec3d vec3d3 = Vec3d.fromPolar(this.amount2, this.amount);
            Vec3d vec3d4 = Vec3d.fromPolar(0.0F, this.amount + 90.0F);
            Vec3d vec3d5 =
                vec3d3
                    .multiply(doubleValue == 0.0 ? 0.65 : doubleValue)
                    .add(vec3d4.multiply(doubleValue2 * 0.6))
                    .add(0.0, instance.options.jumpKey.isPressed() ? 0.5 : -0.025, 0.0);
            double doubleValue3 = instance.options.sprintKey.isPressed() ? 1.4 : 0.7;
            Vec3d vec3d6 = this.vec3d.add(vec3d5.multiply(doubleValue3));
            if (this.test(vec3d6)) {
              this.vec3d = vec3d6;
            }
          }
        }
      }
    } else {
      this.update3();
    }
  }

  public boolean test(Vec3d vec3d3) {
    if (this.clientPlayerEntity != null && this.clientWorld != null) {
      int intValue =
          Math.max(8, (MinecraftClient.getInstance().options.getClampedViewDistance() - 2) * 16);
      Vec3d vec3d4 = vec3d3.subtract(this.clientPlayerEntity.getEntityPos());
      if (!(vec3d4.horizontalLengthSquared() > (double) intValue * intValue)
          && !(vec3d3.y < this.clientWorld.getBottomY())
          && !(vec3d3.y >= this.clientWorld.getTopYInclusive())) {
        Vec3d vec3d5 = this.vec3d == null ? this.clientPlayerEntity.getEntityPos() : this.vec3d;
        int intValue2 = Math.max(1, (int) Math.ceil(vec3d5.distanceTo(vec3d3)));

        for (int index = 1; index <= intValue2; index++) {
          BlockPos blockPos = BlockPos.ofFloored(vec3d5.lerp(vec3d3, (double) index / intValue2));
          if (!this.clientWorld.isChunkLoaded(blockPos.getX() >> 4, blockPos.getZ() >> 4)) {
            return false;
          }
        }

        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  public void update() {
    MinecraftClient instance = MinecraftClient.getInstance();
    if (this.fakeLootDropModule.isEnabled()
        && instance.player != null
        && instance.currentScreen == null) {
      boolean pressed = instance.options.jumpKey.isPressed();
      if (pressed && !this.enabled && this.getItemStack(EquipmentSlot.CHEST).isOf(Items.ELYTRA)) {
        if (this.count2 - this.count <= 7) {
          if (this.test3()) {
            this.update2();
          } else {
            this.test2();
          }

          this.count = -100;
        } else {
          this.count = this.count2;
        }
      }

      this.enabled = pressed;
      if (this.test3()) {
        InputAccessor inputAccessor = (InputAccessor) (Object) (instance.player.input);
        inputAccessor._p0(PlayerInput.DEFAULT);
        inputAccessor._p1(Vec2f.ZERO);
      }
    }
  }

  public boolean test2() {
    MinecraftClient instance = MinecraftClient.getInstance();
    if (this.fakeLootDropModule.isEnabled()
        && instance.player != null
        && instance.currentScreen == null
        && this.getItemStack(EquipmentSlot.CHEST).isOf(Items.ELYTRA)
        && (FreecamModule.getInstance() == null || !FreecamModule.getInstance().test())) {
      this.vec3d = this.vec3d2 = instance.player.getEntityPos();
      this.amount = instance.player.getYaw();
      this.amount2 = instance.player.getPitch();
      this.apply("Local flight preview - Shift to return");
      return true;
    } else {
      return false;
    }
  }

  public void update2() {
    this.vec3d = this.vec3d2 = null;
  }

  public boolean test3() {
    return this.fakeLootDropModule.isEnabled() && this.vec3d != null;
  }

  public Vec3d getVec3d(float floatValue) {
    return this.vec3d2.lerp(this.vec3d, floatValue);
  }

  public float getAmount() {
    return this.amount;
  }

  public float getAmount2() {
    return this.amount2;
  }

  public void apply(double doubleValue, double doubleValue2) {
    this.amount += (float) doubleValue * 0.15F;
    this.amount2 = MathHelper.clamp(this.amount2 + (float) doubleValue2 * 0.15F, -90.0F, 90.0F);
  }

  public void update3() {
    this.enumMap.clear();
    this.update2();
    this.clientPlayerEntity = null;
    this.clientWorld = null;
    this.enabled = false;
    this.enabled2 = false;
    this.count = -100;
  }

  public void apply(String text) {
    ClientPlayerEntity player = MinecraftClient.getInstance().player;
    if (player != null) {
      player.sendMessage(Text.literal(text), true);
    }
  }
}
