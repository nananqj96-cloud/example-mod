package bunnythekenzo.client.module;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.FakeLootDropModule;
import bunnythekenzo.client.SpawnerProtectionState;
import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.NumberSetting;
import bunnythekenzo.client.setting.StringSetting;

public class SpawnerProtectModule extends Module {
  public final NumberSetting stacksToDepositSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Stacks To Deposit",
                  "How many full stacks of spawners to mine before stashing.",
                  3.0,
                  1.0,
                  30.0,
                  1.0));
  public final NumberSetting triggerRangeSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Trigger Range",
                  "Horizontal distance a stranger triggers the routine.",
                  64.0,
                  16.0,
                  128.0,
                  1.0,
                  "m"));
  public final NumberSetting rotationSpeedSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Rotation Speed",
                  "Degrees per tick the look direction is nudged toward its target.",
                  15.0,
                  1.0,
                  30.0,
                  1.0));
  public final BooleanSetting detectBlockUpdatesSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Detect Block Updates",
                  "Detects distant players by their out-of-render block break/place packets.",
                  true));
  public final StringSetting whitelistSetting =
      (StringSetting)
          this.registerSetting(
              new StringSetting(
                  "Whitelist", "Comma-separated names to ignore.", "", 256, "Steve, Alex"));
  public final NumberSetting breakRangeSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Break Range",
                  "How close a spawner must be before it is broken.",
                  5.5,
                  1.0,
                  8.0,
                  0.5,
                  "m"));
  public final BooleanSetting doubleCheckSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Double Check",
                  "Requires two distant breaks within the window before triggering.",
                  true));
  public final NumberSetting doubleCheckWindowSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Double Check Window",
                  "Time window for the double check.",
                  60.0,
                  1.0,
                  600.0,
                  1.0,
                  "s"));
  public final StringSetting webhookUrlSetting =
      (StringSetting)
          this.registerSetting(
              new StringSetting(
                  "Webhook URL",
                  "Optional Discord webhook for alerts.",
                  "",
                  256,
                  "https://discord.com/api/webhooks/..."));
  public final MinecraftClient minecraftClient = MinecraftClient.getInstance();
  public SpawnerProtectionState spawnerProtectionState =
      SpawnerProtectionState.WAITING_FOR_STRANGER;
  public BlockPos blockPos = null;
  public BlockPos blockPos2 = null;
  public int count = 0;
  public int count2 = 0;
  public int count3 = 0;
  public boolean enabled = false;
  public final Set set = new HashSet();
  public int count4 = 0;
  public long timestamp = 0L;
  public int count5 = 0;
  public int count6 = 0;
  public int count7 = 0;

  public static final long timestamp2 = 1000L;

  public SpawnerProtectModule() {
    super(
        "SpawnerProtect",
        "Auto-salvages your spawners when a stranger approaches, then logs out.",
        ModuleCategory.MISC);
    this.doubleCheckWindowSetting.setVisibilityCondition(this.doubleCheckSetting::getValue);
  }

  @Override
  public void onEnable() {
    this.update4();
  }

  @Override
  public void onDisable() {
    this.update18();
    this.apply2(false);
  }

  public void update4() {
    this.spawnerProtectionState = SpawnerProtectionState.WAITING_FOR_STRANGER;
    this.enabled = false;
    this.count3 = 0;
    this.set.clear();
    this.count4 = 0;
    this.timestamp = 0L;
    this.update5();
  }

  public void update5() {
    this.blockPos2 = null;
    this.blockPos = null;
    this.count = 0;
    this.count2 = 0;
    this.count7 = 0;
  }

  public boolean isEnabled2() {
    return this.enabled;
  }

  public String getString() {
    return this.spawnerProtectionState.name();
  }

  public boolean test() {
    return (Boolean) this.detectBlockUpdatesSetting.getValue();
  }

  public void apply(int intValue, BlockPos blockPos3) {
    FakeLootDropModule.ensureInitialized();

    ClientPlayerEntity player = this.minecraftClient.player;
    ClientWorld world = this.minecraftClient.world;
    if (!this.enabled && player != null && world != null && !this.test4()) {
      if (intValue != player.getId()) {
        Entity entityById = world.getEntityById(intValue);
        if (entityById instanceof PlayerEntity playerEntity2) {
          if (this.test(playerEntity2.getName().getString())) {
            return;
          }
        } else if (entityById == null) {
          for (PlayerEntity playerEntity3 : world.getPlayers()) {
            if (playerEntity3.getEyePos().distanceTo(Vec3d.ofCenter(blockPos3)) < 8.0
                && this.test(playerEntity3.getName().getString())) {
              return;
            }
          }
        }

        double doubleValue = player.getX() - blockPos3.getX();
        double doubleValue2 = player.getZ() - blockPos3.getZ();
        double doubleValue3 = Math.sqrt(doubleValue * doubleValue + doubleValue2 * doubleValue2);
        if (doubleValue3 <= (Double) this.triggerRangeSetting.getValue()) {
          this.enabled = true;
          this.spawnerProtectionState = SpawnerProtectionState.WORKING;
          String text =
              entityById != null ? entityById.getName().getString() : "Invisible/Anti-ESP";
          this.apply(
              "\ud83d\udea8 PACKET DETECT: "
                  + text
                  + " started breaking! (Horizontal: "
                  + (int) doubleValue3
                  + "m)");
          int intValue2 = (int) doubleValue3;
          int y = blockPos3.getY();
          int intValue3 = intValue2;
          String text2 = text;
          this.sendWebhook(
              "\ud83d\udea8 **PACKET DETECT:** `"
                  + text2
                  + "` started breaking! (Horizontal: "
                  + intValue3
                  + "m, Y: "
                  + y
                  + ").");
        }
      }
    }
  }

  public void apply(BlockPos blockPos3, BlockState blockState, boolean flag) {
    FakeLootDropModule.ensureInitialized();

    ClientPlayerEntity player = this.minecraftClient.player;
    ClientWorld world = this.minecraftClient.world;
    if (!this.enabled && player != null && world != null && !this.test4()) {
      for (PlayerEntity playerEntity2 : world.getPlayers()) {
        if (playerEntity2.getEyePos().distanceTo(Vec3d.ofCenter(blockPos3)) < 8.0
            && this.test(playerEntity2.getName().getString())) {
          return;
        }
      }

      if (blockPos3.getY() > -64 && blockState.isAir()) {
        BlockState blockState2 = world.getBlockState(blockPos3);
        if (!blockState2.isAir() && !(blockState2.getBlock() instanceof ShulkerBoxBlock)) {
          double doubleValue = player.getEyePos().distanceTo(Vec3d.ofCenter(blockPos3));
          if (!(doubleValue < 6.0)) {
            double doubleValue2 = player.getX() - blockPos3.getX();
            double doubleValue3 = player.getZ() - blockPos3.getZ();
            double doubleValue4 =
                Math.sqrt(doubleValue2 * doubleValue2 + doubleValue3 * doubleValue3);
            if (!(doubleValue4 > (Double) this.triggerRangeSetting.getValue())) {
              if ((Boolean) this.doubleCheckSetting.getValue()) {
                long timestamp3 = System.currentTimeMillis();
                if (timestamp3 - this.timestamp
                    > this.doubleCheckWindowSetting.intValue() * timestamp2) {
                  this.count4 = 0;
                }

                this.count4++;
                this.timestamp = timestamp3;
                if (this.count4 < 2) {
                  return;
                }
              }

              this.enabled = true;
              this.spawnerProtectionState = SpawnerProtectionState.WORKING;
              String text = flag ? "multi-block break" : "block break";
              this.apply(
                  "\ud83d\udea8 REMOTE DETECT: a block broke nearby! (Horizontal: "
                      + (int) doubleValue4
                      + "m, Y: "
                      + blockPos3.getY()
                      + ")");
              int intValue = (int) doubleValue4;
              int y = blockPos3.getY();
              int intValue2 = intValue;
              String text2 = text;
              this.sendWebhook(
                  "\ud83d\udea8 **REMOTE DETECT ("
                      + text2
                      + "):** an invisible or far-away player broke a block! (Horizontal: "
                      + intValue2
                      + "m, Y: "
                      + y
                      + ")");
            }
          }
        }
      }
    }
  }

  @Override
  public void onTick() {
    double d;
    double d2;
    double d3;
    FakeLootDropModule.ensureInitialized();
    ClientPlayerEntity clientPlayerEntity = this.minecraftClient.player;
    ClientWorld clientWorld = this.minecraftClient.world;
    if (clientPlayerEntity == null || clientWorld == null) {
      return;
    }
    if (this.test4()) {
      if (this.enabled) {
        this.update4();
      }
      return;
    }
    if (!this.enabled) {
      for (PlayerEntity entity : clientWorld.getPlayers()) {
        if (entity == clientPlayerEntity
            || this.test(entity.getName().getString())
            || (d3 =
                    Math.sqrt(
                        (d2 = clientPlayerEntity.getX() - entity.getX()) * d2
                            + (d = clientPlayerEntity.getZ() - entity.getZ()) * d))
                > (Double) this.triggerRangeSetting.getValue()) continue;
        boolean bl = this.set.contains(entity.getId());
        if (!bl && entity.isUsingItem()) {
          bl = true;
          this.set.add(entity.getId());
        }
        if (!bl) continue;
        this.enabled = true;
        this.spawnerProtectionState = SpawnerProtectionState.WORKING;
        this.apply(
            "\u26a0 PLAYER SPOTTED (verified): "
                + entity.getName().getString()
                + " (Horizontal: "
                + (int) d3
                + "m)");
        int n = entity.getBlockY();
        int n2 = (int) d3;
        String string = entity.getName().getString();
        this.sendWebhook(
            "\u26a0 **PLAYER SPOTTED (verified):** `"
                + string
                + "` (Horizontal: "
                + n2
                + "m, Y: "
                + n
                + ")!");
        break;
      }
    }
    if (!this.enabled) {
      for (Entity entity : clientWorld.getEntities()) {
        PlayerEntity playerEntity;
        ProjectileEntity projectileEntity;
        Entity entity2;
        if (entity.getType() != EntityType.ENDER_PEARL
            || entity instanceof ProjectileEntity
                && (entity2 = (projectileEntity = (ProjectileEntity) entity).getOwner())
                    instanceof PlayerEntity
                && this.test((playerEntity = (PlayerEntity) entity2).getName().getString())
            || !((d3 =
                    Math.sqrt(
                        (d2 = clientPlayerEntity.getX() - entity.getX()) * d2
                            + (d = clientPlayerEntity.getZ() - entity.getZ()) * d))
                <= (Double) this.triggerRangeSetting.getValue())) continue;
        this.enabled = true;
        this.spawnerProtectionState = SpawnerProtectionState.WORKING;
        this.apply("\u26a0 ENDER PEARL SPOTTED! (Horizontal: " + (int) d3 + "m)");
        this.sendWebhook(
            "\u26a0 **ENDER PEARL SPOTTED!** Someone threw a pearl. (Horizontal: "
                + (int) d3
                + "m)!");
        break;
      }
    }
    if (this.spawnerProtectionState == SpawnerProtectionState.WAITING_FOR_STRANGER) {
      return;
    }
    if (this.spawnerProtectionState != SpawnerProtectionState.OPENING_CHEST
        && this.spawnerProtectionState != SpawnerProtectionState.DEPOSITING_ITEMS
        && this.spawnerProtectionState != SpawnerProtectionState.BUYING_ECHEST) {
      this.apply2(true);
    }
    this.update8();
    switch (this.spawnerProtectionState.ordinal()) {
      case 1:
        {
          this.update9();
          break;
        }
      case 2:
        {
          this.update11();
          break;
        }
      case 3:
        {
          this.update12();
          break;
        }
      case 4:
        {
          this.update15();
          break;
        }
      case 5:
        {
          this.update16();
          break;
        }
      case 6:
        {
          this.update13();
          break;
        }
      case 7:
        {
          this.update14();
          break;
        }
    }
  }

  public void update8() {
    FakeLootDropModule.ensureInitialized();

    ClientPlayerEntity player = this.minecraftClient.player;
    ClientWorld world = this.minecraftClient.world;
    if (player != null && world != null) {
      Vec3d vec3d = null;
      if (this.spawnerProtectionState == SpawnerProtectionState.WORKING) {
        ItemEntity itemEntity = this.getItemEntity();
        if (itemEntity != null) {
          vec3d = itemEntity.getEntityPos();
        } else {
          if (this.blockPos == null
              || world.getBlockState(this.blockPos).getBlock() != Blocks.SPAWNER
              || player.getBlockPos().getSquaredDistance(this.blockPos) > 256.0) {
            this.blockPos = this.getBlockPos2(Blocks.SPAWNER, 16);
            this.count7 = 0;
          }

          if (this.blockPos != null) {
            vec3d = Vec3d.ofCenter(this.blockPos);
          }
        }
      } else if ((this.spawnerProtectionState == SpawnerProtectionState.GOING_TO_CHEST
              || this.spawnerProtectionState == SpawnerProtectionState.OPENING_CHEST)
          && this.blockPos2 != null) {
        vec3d = Vec3d.ofCenter(this.blockPos2);
      }

      if (vec3d != null) {
        this.apply(vec3d);
      }
    }
  }

  public void apply(Vec3d vec3d) {
    FakeLootDropModule.ensureInitialized();

    ClientPlayerEntity player = this.minecraftClient.player;
    if (player != null) {
      Vec3d eyePos = player.getEyePos();
      double doubleValue = vec3d.x - eyePos.x;
      double doubleValue2 = vec3d.y - eyePos.y;
      double doubleValue3 = vec3d.z - eyePos.z;
      double doubleValue4 = Math.sqrt(doubleValue * doubleValue + doubleValue3 * doubleValue3);
      float floatValue = (float) Math.toDegrees(Math.atan2(-doubleValue, doubleValue3));
      float floatValue2 = (float) (-Math.toDegrees(Math.atan2(doubleValue2, doubleValue4)));
      float value2 = this.rotationSpeedSetting.floatValue();
      player.setYaw(
          player.getYaw()
              + MathHelper.clamp(
                  MathHelper.wrapDegrees(floatValue - player.getYaw()), -value2, value2));
      player.setPitch(
          player.getPitch()
              + MathHelper.clamp(
                  MathHelper.wrapDegrees(floatValue2 - player.getPitch()), -value2, value2));
    }
  }

  public void update9() {
    FakeLootDropModule.ensureInitialized();

    ClientPlayerEntity player = this.minecraftClient.player;
    ClientWorld world = this.minecraftClient.world;
    if (player != null && world != null) {
      if (player.currentScreenHandler != player.playerScreenHandler) {
        player.closeHandledScreen();
      }

      ItemEntity itemEntity = this.getItemEntity();
      if (itemEntity != null) {
        this.count3 = 0;
        this.update17();
        this.minecraftClient.options.forwardKey.setPressed(true);
      } else if (this.computeInt() >= this.stacksToDepositSetting.intValue() * 64) {
        this.update10();
      } else {
        if (this.blockPos == null
            || world.getBlockState(this.blockPos).getBlock() != Blocks.SPAWNER
            || player.getBlockPos().getSquaredDistance(this.blockPos) > 256.0) {
          this.blockPos = this.getBlockPos2(Blocks.SPAWNER, 16);
          this.count7 = 0;
        }

        if (this.blockPos == null) {
          this.update18();
          if (this.count3 < 40) {
            this.count3++;
          } else if (this.computeInt() > 0) {
            this.update10();
          } else {
            this.spawnerProtectionState = SpawnerProtectionState.FINAL_EXIT;
            this.count3 = 0;
          }
        } else {
          this.count3 = 0;
          double doubleValue = player.getEyePos().distanceTo(Vec3d.ofCenter(this.blockPos));
          if (doubleValue <= (Double) this.breakRangeSetting.getValue()) {
            this.minecraftClient.options.forwardKey.setPressed(false);
            this.count7++;
            if (this.count7 > 60) {
              this.minecraftClient.interactionManager.interactBlock(
                  player,
                  Hand.MAIN_HAND,
                  new BlockHitResult(
                      Vec3d.ofCenter(this.blockPos), Direction.UP, this.blockPos, false));
              player.swingHand(Hand.MAIN_HAND);
              this.count7 = 0;
            }

            if (this.count2 <= 0) {
              this.minecraftClient.interactionManager.updateBlockBreakingProgress(
                  this.blockPos, Direction.UP);
              player.swingHand(Hand.MAIN_HAND);
              this.minecraftClient.options.attackKey.setPressed(true);
              this.count2 = 6;
            } else {
              this.count2--;
            }
          } else {
            this.update17();
            this.minecraftClient.options.forwardKey.setPressed(true);
          }
        }
      }
    }
  }

  public void update10() {
    FakeLootDropModule.ensureInitialized();

    this.update18();
    this.blockPos2 = this.getBlockPos(Blocks.ENDER_CHEST, 4);
    if (this.blockPos2 != null) {
      this.spawnerProtectionState = SpawnerProtectionState.GOING_TO_CHEST;
    } else if (this.computeInt2() > 0) {
      this.spawnerProtectionState = SpawnerProtectionState.PLACING_ECHEST;
      this.count6 = 0;
    } else {
      this.spawnerProtectionState = SpawnerProtectionState.BUYING_ECHEST;
      this.count5 = 0;
      this.count6 = 0;
    }
  }

  public void update11() {
    FakeLootDropModule.ensureInitialized();

    ClientPlayerEntity player = this.minecraftClient.player;
    if (player != null) {
      if (this.blockPos2 == null) {
        this.blockPos2 = this.getBlockPos(Blocks.ENDER_CHEST, 4);
      }

      if (this.blockPos2 == null) {
        this.spawnerProtectionState = SpawnerProtectionState.WORKING;
      } else {
        this.minecraftClient.options.forwardKey.setPressed(true);
        if (player.getBlockPos().isWithinDistance(this.blockPos2, 4.0)) {
          this.update18();
          this.spawnerProtectionState = SpawnerProtectionState.OPENING_CHEST;
        }
      }
    }
  }

  public void update12() {
    FakeLootDropModule.ensureInitialized();

    ClientPlayerEntity player = this.minecraftClient.player;
    if (player != null) {
      this.apply2(false);
      if (this.count % 12 == 0 && this.blockPos2 != null) {
        this.minecraftClient.interactionManager.interactBlock(
            player,
            Hand.MAIN_HAND,
            new BlockHitResult(
                Vec3d.ofCenter(this.blockPos2), Direction.UP, this.blockPos2, false));
      }

      this.count++;
      if (player.currentScreenHandler instanceof GenericContainerScreenHandler) {
        this.count = 0;
        this.spawnerProtectionState = SpawnerProtectionState.DEPOSITING_ITEMS;
        this.count3 = 0;
      }
    }
  }

  public void update13() {
    FakeLootDropModule.ensureInitialized();

    ClientPlayerEntity player = this.minecraftClient.player;
    if (player != null) {
      this.count6++;
      if (this.count5 > 0 && this.minecraftClient.currentScreen == null && this.count6 > 60) {
        this.apply2("Shop screen closed, retrying...");
        this.count5 = 0;
        this.count6 = 0;
      } else if (this.count6 >= 30) {
        switch (this.count5) {
          case 0:
            this.apply2("Opening shop: /shop");
            if (this.minecraftClient.getNetworkHandler() != null) {
              this.minecraftClient.getNetworkHandler().sendChatCommand("shop");
            }

            this.count5 = 1;
            this.count6 = 0;
            break;
          case 1:
            if (this.minecraftClient.currentScreen != null && this.getString3().contains("SHOP")) {
              this.apply2("Shop: selecting the End category...");
              this.apply(11, SlotActionType.PICKUP);
              this.count5 = 2;
              this.count6 = 0;
            }
            break;
          case 2:
            if (this.minecraftClient.currentScreen != null && this.getString3().contains("END")) {
              this.apply2("Shop: selecting Ender Chest...");
              this.apply(9, SlotActionType.PICKUP);
              this.count5 = 3;
              this.count6 = 0;
            }
            break;
          case 3:
            if (this.minecraftClient.currentScreen != null
                && this.getString3().contains("ENDER CHEST")) {
              this.apply2("Shop: confirming purchase...");
              this.apply(25, SlotActionType.PICKUP);
              this.count5 = 4;
              this.count6 = 0;
            }
            break;
          case 4:
            if (this.computeInt2() > 0) {
              this.apply2("Ender Chest purchased.");
              player.closeHandledScreen();
              this.spawnerProtectionState = SpawnerProtectionState.PLACING_ECHEST;
              this.count6 = 0;
            } else if (this.count6 > 100) {
              this.apply2("Purchase failed (timeout).");
              player.closeHandledScreen();
              this.count5 = 0;
              this.count6 = 0;
            }
        }
      }
    }
  }

  public void update14() {
    ClientPlayerEntity player = this.minecraftClient.player;
    FakeLootDropModule.ensureInitialized();

    ClientWorld world = this.minecraftClient.world;
    if (player != null && world != null) {
      this.count6++;
      if (this.count6 >= 10) {
        int intValue = -1;

        for (int index = 0; index < 9; index++) {
          if (player.getInventory().getStack(index).getItem() == Blocks.ENDER_CHEST.asItem()) {
            intValue = index;
            break;
          }
        }

        if (intValue == -1) {
          for (int index2 = 9; index2 < 36; index2++) {
            if (player.getInventory().getStack(index2).getItem() == Blocks.ENDER_CHEST.asItem()) {
              this.minecraftClient.interactionManager.clickSlot(
                  player.currentScreenHandler.syncId, index2, 0, SlotActionType.QUICK_MOVE, player);
              this.count6 = 0;
              return;
            }
          }

          this.spawnerProtectionState = SpawnerProtectionState.BUYING_ECHEST;
          this.count5 = 0;
        } else {
          player.getInventory().setSelectedSlot(intValue);
          BlockPos blockPos3 = player.getBlockPos();
          BlockPos blockPos22 = null;

          for (Direction direction : Direction.values()) {
            if (direction != Direction.UP && direction != Direction.DOWN) {
              BlockPos blockPos4 = blockPos3.offset(direction);
              if (world.getBlockState(blockPos4).isReplaceable()) {
                blockPos22 = blockPos4;
                break;
              }
            }
          }

          if (blockPos22 != null) {
            this.minecraftClient.interactionManager.interactBlock(
                player,
                Hand.MAIN_HAND,
                new BlockHitResult(Vec3d.ofCenter(blockPos22), Direction.UP, blockPos22, false));
            player.swingHand(Hand.MAIN_HAND);
            this.blockPos2 = blockPos22;
            this.spawnerProtectionState = SpawnerProtectionState.GOING_TO_CHEST;
          } else {
            this.apply("No spot to place the Ender Chest!");
            this.spawnerProtectionState = SpawnerProtectionState.FINAL_EXIT;
            this.count3 = 0;
          }
        }
      }
    }
  }

  public void update15() {
    FakeLootDropModule.ensureInitialized();

    ClientPlayerEntity player = this.minecraftClient.player;
    if (player != null) {
      if (this.count3 < 15) {
        this.count3++;
      } else if (player.currentScreenHandler
          instanceof GenericContainerScreenHandler genericContainerScreenHandler) {
        int intValue = genericContainerScreenHandler.slots.size() - 36;
        boolean flag = false;

        for (int index = 0; index < intValue; index++) {
          ItemStack stack = genericContainerScreenHandler.getSlot(index).getStack();
          if (stack.isEmpty()
              || stack.getItem() == Blocks.SPAWNER.asItem()
                  && stack.getCount() < stack.getMaxCount()) {
            flag = true;
            break;
          }
        }

        int intValue2 = intValue;

        for (int index2 = 0; index2 < 36; index2++) {
          int intValue3 = intValue2 + index2;
          if (genericContainerScreenHandler.getSlot(intValue3).getStack().getItem()
              == Blocks.SPAWNER.asItem()) {
            if (!flag) {
              this.spawnerProtectionState = SpawnerProtectionState.FINAL_EXIT;
              this.count3 = 0;
              return;
            }

            this.minecraftClient.interactionManager.clickSlot(
                genericContainerScreenHandler.syncId,
                intValue3,
                0,
                SlotActionType.QUICK_MOVE,
                player);
            return;
          }
        }

        player.closeHandledScreen();
        if (this.getBlockPos(Blocks.SPAWNER, 16) != null) {
          this.spawnerProtectionState = SpawnerProtectionState.WORKING;
        } else {
          this.spawnerProtectionState = SpawnerProtectionState.FINAL_EXIT;
          this.count3 = 0;
        }
      }
    }
  }

  public void update16() {
    FakeLootDropModule.ensureInitialized();
    if (this.count3 == 0) {
      this.sendWebhook("\ud83c\udfc1 **DONE.** Waiting for items to save...");
      this.apply2("Task done — waiting 2s for the server to save, then disconnecting...");
    }

    this.count3++;
    if (this.count3 > 40) {
      ClientPlayNetworkHandler networkHandler = this.minecraftClient.getNetworkHandler();
      if (networkHandler != null) {
        networkHandler
            .getConnection()
            .disconnect(Text.literal("[SpawnerProtect] Task complete, safe disconnect."));
      }

      this.update4();
    }
  }

  public void apply2(boolean flag) {
    FakeLootDropModule.ensureInitialized();

    ClientPlayerEntity player = this.minecraftClient.player;
    if (player != null) {
      player.setSneaking(flag);
    }

    this.minecraftClient.options.sneakKey.setPressed(flag);
  }

  public boolean test4() {
    FakeLootDropModule.ensureInitialized();

    ClientPlayerEntity player = this.minecraftClient.player;
    return player != null && Math.abs(player.getX()) < 100.0 && Math.abs(player.getZ()) < 100.0;
  }

  public boolean test(String text) {
    FakeLootDropModule.ensureInitialized();
    if (text != null && !text.isEmpty()) {
      for (String text2 : ((String) this.whitelistSetting.getValue()).split(",")) {
        if (text2.trim().equalsIgnoreCase(text)) {
          return true;
        }
      }

      return false;
    } else {
      return false;
    }
  }

  public String getString3() {
    FakeLootDropModule.ensureInitialized();
    return this.minecraftClient.currentScreen == null
        ? ""
        : this.minecraftClient.currentScreen.getTitle().getString().toUpperCase(Locale.ROOT);
  }

  public void apply(int intValue, SlotActionType slotActionType) {
    FakeLootDropModule.ensureInitialized();

    ClientPlayerEntity player = this.minecraftClient.player;
    if (player != null) {
      this.minecraftClient.interactionManager.clickSlot(
          player.currentScreenHandler.syncId, intValue, 0, slotActionType, player);
    }
  }

  public BlockPos getBlockPos(Block block, int intValue) {
    FakeLootDropModule.ensureInitialized();

    ClientPlayerEntity player = this.minecraftClient.player;
    ClientWorld world = this.minecraftClient.world;
    if (player != null && world != null) {
      BlockPos blockPos3 = player.getBlockPos();
      BlockPos blockPos4 = null;
      double MAX_VALUE = Double.MAX_VALUE;

      for (int index = -intValue; index <= intValue; index++) {
        for (int index2 = -intValue; index2 <= intValue; index2++) {
          for (int index3 = -intValue; index3 <= intValue; index3++) {
            BlockPos blockPos5 = blockPos3.add(index, index2, index3);
            if (world.getBlockState(blockPos5).getBlock() == block) {
              double squaredDistance = blockPos3.getSquaredDistance(blockPos5);
              if (squaredDistance < MAX_VALUE) {
                MAX_VALUE = squaredDistance;
                blockPos4 = blockPos5;
              }
            }
          }
        }
      }

      return blockPos4;
    } else {
      return null;
    }
  }

  public BlockPos getBlockPos2(Block block, int intValue) {
    FakeLootDropModule.ensureInitialized();

    ClientPlayerEntity player = this.minecraftClient.player;
    ClientWorld world = this.minecraftClient.world;
    if (player != null && world != null) {
      BlockPos blockPos3 = player.getBlockPos();
      ArrayList arrayList = new ArrayList();
      double doubleValue = (double) intValue * intValue;

      for (int index = -intValue; index <= intValue; index++) {
        for (int index2 = -intValue; index2 <= intValue; index2++) {
          for (int index3 = -intValue; index3 <= intValue; index3++) {
            BlockPos blockPos4 = blockPos3.add(index, index2, index3);
            if (blockPos3.getSquaredDistance(blockPos4) <= doubleValue
                && world.getBlockState(blockPos4).getBlock() == block) {
              arrayList.add(blockPos4);
            }
          }
        }
      }

      return arrayList.isEmpty()
          ? null
          : (BlockPos) arrayList.get(new Random().nextInt(arrayList.size()));
    } else {
      return null;
    }
  }

  public int computeInt() {
    return this.computeInt(Blocks.SPAWNER.asItem(), 36);
  }

  public int computeInt2() {
    return this.computeInt(Blocks.ENDER_CHEST.asItem(), 45);
  }

  public int computeInt(Item item, int intValue) {
    FakeLootDropModule.ensureInitialized();

    ClientPlayerEntity player = this.minecraftClient.player;
    if (player == null) {
      return 0;
    }

    int intValue2 = 0;

    for (int index = 0; index < intValue; index++) {
      ItemStack stack = player.getInventory().getStack(index);
      if (stack.getItem() == item) {
        intValue2 += stack.getCount();
      }
    }

    return intValue2;
  }

  public ItemEntity getItemEntity() {
    FakeLootDropModule.ensureInitialized();

    ClientPlayerEntity player = this.minecraftClient.player;
    ClientWorld world = this.minecraftClient.world;
    if (player != null && world != null) {
      ItemEntity itemEntity = null;
      double MAX_VALUE = Double.MAX_VALUE;

      for (Entity entity3 : world.getEntities()) {
        if (entity3 instanceof ItemEntity itemEntity2
            && itemEntity2.getStack().getItem() == Blocks.SPAWNER.asItem()) {
          double doubleValue = player.distanceTo(itemEntity2);
          if (doubleValue < 16.0 && doubleValue < MAX_VALUE) {
            MAX_VALUE = doubleValue;
            itemEntity = itemEntity2;
          }
        }
      }

      return itemEntity;
    } else {
      return null;
    }
  }

  public void update17() {
    this.minecraftClient.options.attackKey.setPressed(false);
    this.count2 = 0;
  }

  public void update18() {
    this.update17();
    this.minecraftClient.options.forwardKey.setPressed(false);
  }

  public void apply(String text) {
    FakeLootDropModule.ensureInitialized();

    ClientPlayerEntity player = this.minecraftClient.player;
    if (player != null) {
      player.sendMessage(Text.literal("§c[SpawnerProtect] §f" + text), false);
    }

    try {
      ClientInitializer.getNotificationsHud()
          .apply("SpawnerProtect · " + text.replaceAll("§.", ""));
    } catch (Exception failure) {
    }
  }

  public void apply2(String text) {
    FakeLootDropModule.ensureInitialized();

    ClientPlayerEntity player = this.minecraftClient.player;
    if (player != null) {
      player.sendMessage(Text.literal("§7[SpawnerProtect] " + text), false);
    }
  }

  public void sendWebhook(String text) {
    FakeLootDropModule.ensureInitialized();

    String text2 = ((String) this.webhookUrlSetting.getValue()).trim();
    if (!text2.isEmpty()) {
      String text3 = "{\"content\": \"" + text.replace("\\", "\\\\").replace("\"", "\\\"") + "\"}";
      new Thread(
              () -> {
                FakeLootDropModule.ensureInitialized();

                try {
                  HttpURLConnection httpURLConnection =
                      (HttpURLConnection) URI.create(text2).toURL().openConnection();
                  httpURLConnection.setRequestMethod("POST");
                  httpURLConnection.setRequestProperty("Content-Type", "application/json");
                  httpURLConnection.setDoOutput(true);

                  try (OutputStream outputStream = httpURLConnection.getOutputStream()) {
                    outputStream.write(text3.getBytes(StandardCharsets.UTF_8));
                  }

                  httpURLConnection.getInputStream().close();
                } catch (Exception failure) {
                }
              },
              "SpawnerProtect-Webhook")
          .start();
    }
  }
}
