package bunnythekenzo.client.hud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import bunnythekenzo.client.ArgbColors;
import bunnythekenzo.client.ColorPalette;
import bunnythekenzo.client.HudElementContract;
import bunnythekenzo.client.NanoVgImageCache;
import bunnythekenzo.client.ThemeManager;
import bunnythekenzo.client.render.FontManager;

public class ArmorHud extends HudElement {
  public static final EquipmentSlot[] equipmentSlotArray =
      new EquipmentSlot[] {
        EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET
      };
  public static final float amount = 22.0F;
  public static final float amount2 = 7.0F;
  public static final float amount3 = 6.0F;
  public static final float amount4 = 3.0F;
  public final ThemeManager themeManager;

  public ArmorHud(ThemeManager themeManager2, HudElementContract hudElementContract) {
    super(
        "armor", "Armour", "Equipped armour with durability bars", 0.5F, 0.8F, hudElementContract);
    this.themeManager = themeManager2;
  }

  @Override
  public float computeFloat(FontManager fontManager) {
    return 14.0F + equipmentSlotArray.length * 22.0F + (equipmentSlotArray.length - 1) * 6.0F;
  }

  @Override
  public float computeFloat2(FontManager fontManager) {
    return 38.0F;
  }

  @Override
  public void render(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    ColorPalette colorPalette = this.themeManager.getColorPalette();
    ClientPlayerEntity player = MinecraftClient.getInstance().player;
    if (player != null) {
      fontManager.render(
          floatValue,
          floatValue2,
          floatValue3,
          floatValue4,
          9.0F,
          colorPalette.computeInt7(),
          colorPalette.computeInt8(),
          true);
      float floatValue5 = floatValue + 7.0F;

      for (EquipmentSlot equipmentSlot : equipmentSlotArray) {
        ItemStack equippedStack = player.getEquippedStack(equipmentSlot);
        float floatValue6 = floatValue2 + 4.0F;
        if (equippedStack.isEmpty()) {
          fontManager.render(
              floatValue5,
              floatValue6,
              22.0F,
              22.0F,
              5.0F,
              1.0F,
              ArgbColors.withOpacity(colorPalette.computeInt19(), 0.5F));
        } else {
          Identifier id = Registries.ITEM.getId(equippedStack.getItem());
          int value =
              NanoVgImageCache.computeInt(
                  Identifier.of(id.getNamespace(), "textures/item/" + id.getPath() + ".png"));
          if (value > 0) {
            fontManager.render(
                value,
                floatValue5,
                floatValue6,
                22.0F,
                22.0F,
                floatValue5,
                floatValue6,
                22.0F,
                22.0F,
                1.0F);
          } else {
            fontManager.render(
                floatValue5,
                floatValue6,
                22.0F,
                22.0F,
                5.0F,
                ArgbColors.withOpacity(colorPalette.getCount(), 0.3F));
          }

          if (equippedStack.isDamageable()) {
            float floatValue7 =
                1.0F - (float) equippedStack.getDamage() / equippedStack.getMaxDamage();
            int value2 = ArgbColors.lerp(-1684147, -11671924, floatValue7);
            float floatValue8 = floatValue6 + 22.0F + 3.0F;
            fontManager.render(
                floatValue5,
                floatValue8,
                22.0F,
                3.0F,
                1.5F,
                ArgbColors.withOpacity(-16777216, 0.45F));
            fontManager.render(
                floatValue5, floatValue8, Math.max(3.0F, 22.0F * floatValue7), 3.0F, 1.5F, value2);
          }
        }

        floatValue5 += 28.0F;
      }
    }
  }
}
