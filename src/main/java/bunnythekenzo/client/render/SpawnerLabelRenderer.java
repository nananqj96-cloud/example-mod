package bunnythekenzo.client.render;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import org.joml.Matrix3x2fStack;
import bunnythekenzo.client.ArgbColors;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.ColorPalette;
import bunnythekenzo.client.FakeLootDropModule;
import bunnythekenzo.client.FramebufferSupport;
import bunnythekenzo.client.SpawnerLabelData;
import bunnythekenzo.client.module.HUDModule;
import bunnythekenzo.client.module.ModuleManager;
import bunnythekenzo.client.module.NameProtectModule;
import bunnythekenzo.client.module.NameTagsModule;
import bunnythekenzo.client.module.SpawnerNametagsModule;

public final class SpawnerLabelRenderer {
  public static final int count = -22733;
  public static final int count2 = 80;

  public static void apply(FontManager fontManager) {
    if (FramebufferSupport.isEnabled()) {
      ModuleManager moduleManager = ClientInitializer.getModuleManager();
      if (moduleManager != null) {
        MinecraftClient instance = MinecraftClient.getInstance();
        ClientWorld world = instance.world;
        ClientPlayerEntity player = instance.player;
        if (world != null && player != null) {
          NameTagsModule nameTagsModule = moduleManager.nameTagsModule;
          FakeLootDropModule fakeLootDropModule = moduleManager.fakeLootDropModule;
          SpawnerNametagsModule spawnerNametagsModule = moduleManager.spawnerNametagsModule;
          boolean flag = nameTagsModule != null && nameTagsModule.isEnabled();
          boolean flag2 = fakeLootDropModule != null && fakeLootDropModule.isEnabled();
          boolean flag3 =
              spawnerNametagsModule != null
                  && spawnerNametagsModule.isEnabled()
                  && (Boolean) spawnerNametagsModule.nametagSetting.getValue();
          if (flag || flag3) {
            float amount = FramebufferSupport.getAmount();
            ColorPalette colorPalette = ClientInitializer.getThemeManager().getColorPalette();
            HUDModule hUDModule = moduleManager.hUDModule;
            int intValue =
                hUDModule != null && !hUDModule.listThemeSyncSetting.getValue()
                    ? hUDModule.listColorSetting.getInteger()
                    : colorPalette.getCount();
            ArrayList arrayList = new ArrayList();
            if (flag) {
              double doubleValue = (Double) nameTagsModule.rangeSetting.getValue();
              double doubleValue2 = doubleValue * doubleValue;
              float value = nameTagsModule.scaleSetting.floatValue();
              float floatValue =
                  (float) ((Double) nameTagsModule.opacitySetting.getValue() / 100.0);
              if ((Boolean) nameTagsModule.playersSetting.getValue()) {
                NameProtectModule nameProtectModule = moduleManager.nameProtectModule;
                boolean flag4 = nameProtectModule != null && nameProtectModule.isEnabled();
                boolean flag5 =
                    (Boolean) nameTagsModule.selfSetting.getValue()
                        && !instance.options.getPerspective().isFirstPerson();

                for (AbstractClientPlayerEntity abstractClientPlayerEntity : world.getPlayers()) {
                  boolean flag6 = abstractClientPlayerEntity == player;
                  if ((!flag6 || flag5)
                      && !abstractClientPlayerEntity.isSpectator()
                      && abstractClientPlayerEntity.isAlive()) {
                    double doubleValue3 = player.squaredDistanceTo(abstractClientPlayerEntity);
                    if (flag6 || !(doubleValue3 > doubleValue2)) {
                      String text = abstractClientPlayerEntity.getGameProfile().name();
                      if (flag4) {
                        String string2 = nameProtectModule.getString2(text);
                        if (string2 != null) {
                          text = string2;
                        }
                      }

                      float floatValue2 = -1.0F;
                      if ((Boolean) nameTagsModule.healthSetting.getValue()) {
                        float maxHealth = abstractClientPlayerEntity.getMaxHealth();
                        if (maxHealth > 0.0F) {
                          floatValue2 =
                              MathHelper.clamp(
                                  abstractClientPlayerEntity.getHealth() / maxHealth, 0.0F, 1.0F);
                        }
                      }

                      String text2 =
                          !flag6 && nameTagsModule.distanceSetting.getValue()
                              ? (int) Math.sqrt(doubleValue3) + "m"
                              : null;
                      if (!text.isEmpty() || text2 != null || !(floatValue2 < 0.0F)) {
                        arrayList.add(
                            getSpawnerLabelData(
                                abstractClientPlayerEntity,
                                amount,
                                Math.sqrt(doubleValue3),
                                text,
                                text2,
                                intValue,
                                floatValue2,
                                value,
                                floatValue));
                      }
                    }
                  }
                }
              }

              if ((Boolean) nameTagsModule.itemsSetting.getValue()) {
                for (Entity entity : world.getEntities()) {
                  if (entity instanceof ItemEntity itemEntity && itemEntity.isAlive()) {
                    double doubleValue4 = player.squaredDistanceTo(itemEntity);
                    if (!(doubleValue4 > doubleValue2)) {
                      ItemStack stack = itemEntity.getStack();
                      if (!stack.isEmpty() && (!flag2 || !fakeLootDropModule.test(stack))) {
                        String text3 =
                            getString(
                                stack.getCount(),
                                (Boolean) nameTagsModule.itemAmountSetting.getValue(),
                                (java.lang.Boolean) (nameTagsModule.distanceSetting.getValue())
                                    ? (int) Math.sqrt(doubleValue4) + "m"
                                    : null);
                        arrayList.add(
                            getSpawnerLabelData(
                                itemEntity,
                                amount,
                                Math.sqrt(doubleValue4),
                                stack.getName().getString(),
                                text3,
                                intValue,
                                -1.0F,
                                value,
                                floatValue));
                      }
                    }
                  }
                }
              }
            }

            if (flag3) {
              boolean flag7 = (Boolean) spawnerNametagsModule.distanceSetting.getValue();
              float amount3 =
                  (float) ((Double) spawnerNametagsModule.opacitySetting.getValue() / 100.0);

              for (BlockPos blockPos :
                  (java.lang.Iterable<BlockPos>)
                      (java.lang.Iterable<?>)
                          (spawnerNametagsModule.blockPredicateScanner.getList())) {
                double value2 = blockPos.getX() + 0.5;
                double value3 = blockPos.getY() + 1.35;
                double value4 = blockPos.getZ() + 0.5;
                double doubleValue5 =
                    player.squaredDistanceTo(value2, blockPos.getY() + 0.5, value4);
                String string22 = flag7 ? (int) Math.sqrt(doubleValue5) + "m" : null;
                arrayList.add(
                    new SpawnerLabelData(
                        Math.sqrt(doubleValue5),
                        value2,
                        value3,
                        value4,
                        getString(world, blockPos),
                        string22,
                        -22733,
                        -1.0F,
                        1.0F,
                        amount3));
              }
            }

            if (!arrayList.isEmpty()) {
              arrayList.sort(Comparator.comparingDouble(SpawnerLabelData::value));
              int intValue2 = Math.min(arrayList.size(), 80);

              for (int index = intValue2 - 1; index >= 0; index--) {
                SpawnerLabelData spawnerLabelData = (SpawnerLabelData) arrayList.get(index);
                float[] amountArray =
                    FramebufferSupport.getAmountArray(
                        spawnerLabelData.value2(),
                        spawnerLabelData.value3(),
                        spawnerLabelData.value4());
                if (amountArray != null) {
                  render(
                      fontManager, colorPalette, amountArray[0], amountArray[1], spawnerLabelData);
                }
              }
            }
          }
        }
      }
    }
  }

  public static SpawnerLabelData getSpawnerLabelData(
      Entity entity,
      float floatValue,
      double value,
      String string,
      String string2,
      int count3,
      float amount,
      float amount2,
      float amount3) {
    double value2 = MathHelper.lerp(floatValue, entity.lastRenderX, entity.getX());
    double value3 =
        MathHelper.lerp(floatValue, entity.lastRenderY, entity.getY()) + entity.getHeight() + 0.5;
    double value4 = MathHelper.lerp(floatValue, entity.lastRenderZ, entity.getZ());
    return new SpawnerLabelData(
        value, value2, value3, value4, string, string2, count3, amount, amount2, amount3);
  }

  public static String getString(int intValue, boolean flag, String text) {
    StringBuilder builder = new StringBuilder();
    if (flag && intValue > 1) {
      builder.append('x').append(intValue);
    }

    if (text != null) {
      if (builder.length() > 0) {
        builder.append("  ");
      }

      builder.append(text);
    }

    return builder.length() == 0 ? null : builder.toString();
  }

  public static String getString(ClientWorld clientWorld, BlockPos blockPos) {
    try {
      if (clientWorld.getBlockEntity(blockPos)
          instanceof MobSpawnerBlockEntity mobSpawnerBlockEntity) {
        Entity renderedEntity =
            mobSpawnerBlockEntity.getLogic().getRenderedEntity(clientWorld, blockPos);
        return renderedEntity != null
            ? renderedEntity.getType().getName().getString() + " Spawner"
            : "Spawner";
      }
    } catch (Exception failure) {
    }

    return "Spawner";
  }

  public static float computeFloat(float floatValue, boolean flag) {
    float floatValue2 = 12.5F * floatValue + 3.5F * floatValue * 2.0F;
    return floatValue2 + (flag ? 3.0F * floatValue + 3.5F * floatValue : 0.0F);
  }

  public static void apply(DrawContext drawContext) {
    if (FramebufferSupport.isEnabled()) {
      ModuleManager moduleManager = ClientInitializer.getModuleManager();
      if (moduleManager != null) {
        NameTagsModule nameTagsModule = moduleManager.nameTagsModule;
        if (nameTagsModule != null
            && nameTagsModule.isEnabled()
            && (Boolean) nameTagsModule.playersSetting.getValue()) {
          boolean flag = (Boolean) nameTagsModule.armorSetting.getValue();
          boolean flag2 = (Boolean) nameTagsModule.heldItemSetting.getValue();
          if (flag || flag2) {
            MinecraftClient instance = MinecraftClient.getInstance();
            if (instance.currentScreen == null && !instance.options.hudHidden) {
              ClientWorld world = instance.world;
              ClientPlayerEntity player = instance.player;
              if (world != null && player != null) {
                float amount = FramebufferSupport.getAmount();
                double doubleValue = (Double) nameTagsModule.rangeSetting.getValue();
                double doubleValue2 = doubleValue * doubleValue;
                float value = nameTagsModule.scaleSetting.floatValue();
                float floatValue =
                    computeFloat(value, (Boolean) nameTagsModule.healthSetting.getValue());
                float value2 = OverlayRenderer.computeFloat();
                double scaleFactor = instance.getWindow().getScaleFactor();
                boolean flag3 =
                    (Boolean) nameTagsModule.selfSetting.getValue()
                        && !instance.options.getPerspective().isFirstPerson();
                ArrayList arrayList = new ArrayList();

                for (AbstractClientPlayerEntity abstractClientPlayerEntity : world.getPlayers()) {
                  boolean flag4 = abstractClientPlayerEntity == player;
                  if ((!flag4 || flag3)
                      && !abstractClientPlayerEntity.isSpectator()
                      && abstractClientPlayerEntity.isAlive()
                      && (flag4
                          || !(player.squaredDistanceTo(abstractClientPlayerEntity)
                              > doubleValue2))) {
                    arrayList.clear();
                    if (flag) {
                      apply(
                          arrayList,
                          abstractClientPlayerEntity.getEquippedStack(EquipmentSlot.HEAD));
                      apply(
                          arrayList,
                          abstractClientPlayerEntity.getEquippedStack(EquipmentSlot.CHEST));
                      apply(
                          arrayList,
                          abstractClientPlayerEntity.getEquippedStack(EquipmentSlot.LEGS));
                      apply(
                          arrayList,
                          abstractClientPlayerEntity.getEquippedStack(EquipmentSlot.FEET));
                    }

                    if (flag2) {
                      apply(arrayList, abstractClientPlayerEntity.getMainHandStack());
                      apply(
                          arrayList,
                          abstractClientPlayerEntity.getEquippedStack(EquipmentSlot.OFFHAND));
                    }

                    if (!arrayList.isEmpty()) {
                      double doubleValue3 =
                          MathHelper.lerp(
                              amount,
                              abstractClientPlayerEntity.lastRenderX,
                              abstractClientPlayerEntity.getX());
                      double doubleValue4 =
                          MathHelper.lerp(
                                  amount,
                                  abstractClientPlayerEntity.lastRenderY,
                                  abstractClientPlayerEntity.getY())
                              + abstractClientPlayerEntity.getHeight()
                              + 0.5;
                      double doubleValue5 =
                          MathHelper.lerp(
                              amount,
                              abstractClientPlayerEntity.lastRenderZ,
                              abstractClientPlayerEntity.getZ());
                      float[] amountArray2 =
                          FramebufferSupport.getAmountArray2(
                              doubleValue3, doubleValue4, doubleValue5);
                      if (amountArray2 != null) {
                        float floatValue2 = (float) (amountArray2[0] / scaleFactor);
                        float floatValue3 =
                            (float) ((amountArray2[1] - floatValue * value2) / scaleFactor) - 3.0F;
                        float floatValue4 = 11.0F * value;
                        float floatValue5 = floatValue4 + 1.5F;
                        float floatValue6 =
                            arrayList.size() * floatValue4 + (arrayList.size() - 1) * 1.5F;
                        float floatValue7 = floatValue2 - floatValue6 / 2.0F;
                        float floatValue8 = floatValue3 - floatValue4;
                        Matrix3x2fStack matrices = drawContext.getMatrices();

                        for (int index = 0; index < arrayList.size(); index++) {
                          ItemStack itemStack = (ItemStack) arrayList.get(index);
                          matrices.pushMatrix();
                          matrices.translate(floatValue7 + index * floatValue5, floatValue8);
                          matrices.scale(floatValue4 / 16.0F, floatValue4 / 16.0F);
                          drawContext.drawItem(abstractClientPlayerEntity, itemStack, 0, 0, 0);
                          drawContext.drawStackOverlay(instance.textRenderer, itemStack, 0, 0);
                          matrices.popMatrix();
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  public static void apply(List list, ItemStack itemStack) {
    if (itemStack != null && !itemStack.isEmpty()) {
      list.add(itemStack);
    }
  }

  public static void render(
      FontManager fontManager,
      ColorPalette colorPalette,
      float floatValue,
      float floatValue2,
      SpawnerLabelData spawnerLabelData) {
    float floatValue3 = spawnerLabelData.amount2();
    int int2 = ArgbColors.lighten(spawnerLabelData.count(), 0.35F);
    float floatValue4 = 12.5F * floatValue3;
    float floatValue5 = 10.0F * floatValue3;
    float floatValue6 = 6.0F * floatValue3;
    float floatValue7 = 3.5F * floatValue3;
    float floatValue8 = 5.0F * floatValue3;
    boolean flag = spawnerLabelData.amount() >= 0.0F;
    float floatValue9 = 3.0F * floatValue3;
    float value = fontManager.computeFloat(spawnerLabelData.string(), floatValue4);
    float floatValue10 =
        spawnerLabelData.string2() != null
            ? floatValue8 + fontManager.computeFloat(spawnerLabelData.string2(), floatValue5)
            : 0.0F;
    float floatValue11 = value + floatValue10 + floatValue6 * 2.0F;
    float floatValue12 = floatValue4 + floatValue7 * 2.0F;
    float floatValue13 = floatValue12 + (flag ? floatValue9 + floatValue7 : 0.0F);
    float floatValue14 = floatValue - floatValue11 / 2.0F;
    float floatValue15 = floatValue2 - floatValue13;
    float floatValue16 = Math.min(6.0F * floatValue3, floatValue13 / 2.0F);
    boolean flag2 = spawnerLabelData.amount3() < 0.999F;
    if (flag2) {
      fontManager.update2();
      fontManager.apply3(spawnerLabelData.amount3());
    }

    fontManager.render2(
        floatValue14,
        floatValue15,
        floatValue11,
        floatValue13,
        floatValue16,
        4.0F,
        ArgbColors.withOpacity(spawnerLabelData.count(), 0.12F));
    fontManager.render(
        floatValue14,
        floatValue15,
        floatValue11,
        floatValue13,
        floatValue16,
        colorPalette.computeInt7(),
        colorPalette.computeInt8(),
        true);
    float floatValue17 = floatValue15 + floatValue7 + floatValue4 / 2.0F;
    float floatValue18 = floatValue14 + floatValue6;
    floatValue18 +=
        fontManager.computeFloat2(
            spawnerLabelData.string(),
            floatValue18,
            floatValue17,
            floatValue4,
            int2,
            spawnerLabelData.count());
    if (spawnerLabelData.string2() != null) {
      floatValue18 += floatValue8;
      fontManager.computeFloat(
          spawnerLabelData.string2(),
          floatValue18,
          floatValue17,
          floatValue5,
          colorPalette.computeInt18());
    }

    if (flag) {
      float floatValue19 = floatValue15 + floatValue12;
      float floatValue20 = floatValue14 + floatValue6;
      float floatValue21 = floatValue11 - floatValue6 * 2.0F;
      fontManager.render(
          floatValue20,
          floatValue19,
          floatValue21,
          floatValue9,
          floatValue9 / 2.0F,
          ArgbColors.withOpacity(-16777216, 0.55F));
      int value2 = ArgbColors.lerp(-2080450, -11671924, spawnerLabelData.amount());
      fontManager.render(
          floatValue20,
          floatValue19,
          Math.max(floatValue9, floatValue21 * spawnerLabelData.amount()),
          floatValue9,
          floatValue9 / 2.0F,
          value2);
    }

    if (flag2) {
      fontManager.update3();
    }
  }
}
