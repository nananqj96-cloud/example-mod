package bunnythekenzo.client;

import java.util.List;
import java.util.UUID;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil.Key;
import net.minecraft.client.util.InputUtil.Type;
import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.AbstractBoatEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.consume.UseAction;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.OnGroundOnly;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.PlayerInput;
import net.minecraft.util.math.Vec3d;
import bunnythekenzo.client.mixin.ClientPlayerInteractionManagerAccessor;
import bunnythekenzo.client.mixin.KeyBindingAccessor;
import bunnythekenzo.client.mixin.LivingEntityAccessor;
import bunnythekenzo.client.mixin.MinecraftClientAccessor;
import bunnythekenzo.client.module.ModuleCategory;
import bunnythekenzo.client.setting.ChoiceSettingView;
import bunnythekenzo.client.setting.NumberSettingView;
import bunnythekenzo.client.setting.SettingEntry;
import bunnythekenzo.client.ui.PanelScreen;

public final class MovementOverrides {
  public static final ChoiceSettingView choiceSettingView =
      getChoiceSettingView(
          "Mode",
          "Velocity directly controls motion; Abilities uses Minecraft flight.",
          0,
          new String[] {"Velocity", "Abilities"});
  public static final NumberSettingView numberSettingView;
  public static final NumberSettingView numberSettingView2;
  public static final NumberSettingView numberSettingView3;
  public static final NumberSettingView numberSettingView4;
  public static final NumberSettingView numberSettingView5;
  public static final NumberSettingView numberSettingView6;
  public static final NumberSettingView numberSettingView7;
  public static final NumberSettingView numberSettingView8;
  public static final NumberSettingView numberSettingView9;
  public static final NumberSettingView numberSettingView10;
  public static final NumberSettingView numberSettingView11;
  public static final NumberSettingView numberSettingView12;
  public static final NumberSettingView numberSettingView13;
  public static final NumberSettingView numberSettingView14;
  public static final NumberSettingView numberSettingView15;
  public static final NumberSettingView numberSettingView16;
  public static final NumberSettingView numberSettingView17;
  public static final NumberSettingView numberSettingView18;
  public static final NumberSettingView numberSettingView19;
  public static final NumberSettingView numberSettingView20;
  public static final NumberSettingView numberSettingView21;
  public static final NumberSettingView numberSettingView22;
  public static final NumberSettingView numberSettingView23;
  public static final NumberSettingView numberSettingView24;
  public static final GuiModuleEntry guiModuleEntry;
  public static final GuiModuleEntry guiModuleEntry2;
  public static final GuiModuleEntry guiModuleEntry3;
  public static final GuiModuleEntry guiModuleEntry4;
  public static final GuiModuleEntry guiModuleEntry5;
  public static final GuiModuleEntry guiModuleEntry6;
  public static final GuiModuleEntry guiModuleEntry7;
  public static final GuiModuleEntry guiModuleEntry8;
  public static final GuiModuleEntry guiModuleEntry9;
  public static final GuiModuleEntry guiModuleEntry10;
  public static final GuiModuleEntry guiModuleEntry11;
  public static final GuiModuleEntry guiModuleEntry12;
  public static final GuiModuleEntry guiModuleEntry13;
  public static final GuiModuleEntry guiModuleEntry14;
  public static final GuiModuleEntry guiModuleEntry15;
  public static final GuiModuleEntry guiModuleEntry16;
  public static final GuiModuleEntry guiModuleEntry17;
  public static final GuiModuleEntry guiModuleEntry18;
  public static final GuiModuleEntry guiModuleEntry19;
  public static final List list;
  public static boolean enabled;
  public static ClientPlayerEntity clientPlayerEntity;
  public static boolean enabled2;
  public static ClientPlayerEntity clientPlayerEntity2;
  public static int count;
  public static boolean enabled3;
  public static boolean enabled4;
  public static boolean enabled5;
  public static float amount;
  public static boolean enabled6;
  public static Entity entity;
  public static boolean enabled7;
  public static int[] countArray;

  public static GuiModuleEntry[] guiModules() {
    return (bunnythekenzo.client.GuiModuleEntry[]) (list.toArray(GuiModuleEntry[]::new));
  }

  public static void init() {
    ModuleCategory.ensureInitialized();
    if (!enabled) {
      enabled = true;
      ClientTickEvents.END_CLIENT_TICK.register(MovementOverrides::tick);
      ClientPlayConnectionEvents.DISCONNECT.register(
          (clientPlayNetworkHandler, minecraftClient) -> {
            resetRuntime(minecraftClient);
          });
    }
  }

  public static boolean test(int intValue) {
    ModuleCategory.ensureInitialized();

    MinecraftClient instance = MinecraftClient.getInstance();
    if (instance.player != null && instance.world != null && instance.currentScreen == null) {
      if (intValue != 83 && !test(instance, intValue)) {
        for (GuiModuleEntry guiModuleEntry20 :
            (java.lang.Iterable<GuiModuleEntry>) (java.lang.Iterable<?>) (list)) {
          if (guiModuleEntry20.test3() && guiModuleEntry20.computeInt() == intValue) {
            if (guiModuleEntry20.test()) {
              guiModuleEntry20.apply(false);
              ClientAudio.apply(false);
              return true;
            }

            PanelScreen panelScreen = new PanelScreen(instance.currentScreen);
            panelScreen.apply(guiModuleEntry20);
            instance.setScreen(panelScreen);
            return true;
          }
        }

        return false;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  public static boolean test(MinecraftClient minecraftClient, int intValue) {
    ModuleCategory.ensureInitialized();

    GameOptions options = minecraftClient.options;

    for (KeyBinding keyBinding :
        new KeyBinding[] {
          options.forwardKey,
          options.backKey,
          options.leftKey,
          options.rightKey,
          options.jumpKey,
          options.sneakKey,
          options.sprintKey
        }) {
      Key key = ((KeyBindingAccessor) (Object) (keyBinding)).getBoundKey();
      if (key.getCategory() == Type.KEYSYM && key.getCode() == intValue) {
        return true;
      }
    }

    return false;
  }

  public static void setClientPlayerEntity2(ClientPlayerEntity clientPlayerEntity22) {
    clientPlayerEntity2 = clientPlayerEntity22;
  }

  public static void tick(MinecraftClient minecraftClient) {
    ClientPlayerEntity clientPlayerEntity3 = clientPlayerEntity2;
    ModuleCategory.ensureInitialized();
    clientPlayerEntity2 = null;
    if (!PearlEscapeAutomation.test()) {
      ClientPlayerEntity player = minecraftClient.player;
      if (player != null && minecraftClient.world != null) {
        if (clientPlayerEntity != player) {
          apply(clientPlayerEntity);
          clientPlayerEntity = player;
          enabled2 = false;
        }

        PlayerInput playerInput = player.input.playerInput;
        boolean enabled22 = playerInput.jump();
        boolean flag = enabled22 && !enabled2;
        if (count > 0) {
          count--;
        }

        apply(player, playerInput);
        apply2(player, playerInput);
        apply3(player, playerInput);
        apply4(player);
        boolean flag2 =
            guiModuleEntry.test()
                || guiModuleEntry13.test()
                || player.hasVehicle()
                || player.isSpectator();
        if (!flag2) {
          if (guiModuleEntry11.test() && player.isGliding()) {
            apply4(player, playerInput);
          } else {
            apply5(player, playerInput);
            apply(player, flag, clientPlayerEntity3 == player);
          }
        }

        updateUseTiming(minecraftClient, player);
        apply3(minecraftClient);
        enabled2 = enabled22;
      } else {
        resetRuntime(minecraftClient);
      }
    }
  }

  public static void resetRuntime(MinecraftClient minecraftClient) {
    ModuleCategory.ensureInitialized();
    apply(clientPlayerEntity != null ? clientPlayerEntity : minecraftClient.player);
    clientPlayerEntity = null;
    enabled2 = false;
    clientPlayerEntity2 = null;
    count = 0;
    update();

    for (GuiModuleEntry guiModuleEntry20 :
        (java.lang.Iterable<GuiModuleEntry>) (java.lang.Iterable<?>) (list)) {
      guiModuleEntry20.apply(false);
    }
  }

  public static void apply(ClientPlayerEntity clientPlayerEntity3) {
    ModuleCategory.ensureInitialized();
    if (clientPlayerEntity3 == null) {
      enabled3 = false;
      enabled6 = false;
    } else {
      apply2(clientPlayerEntity3);
      if (enabled6 && !clientPlayerEntity3.isSpectator()) {
        clientPlayerEntity3.noClip = false;
      }

      enabled6 = false;
    }
  }

  public static void apply(ClientPlayerEntity clientPlayerEntity3, PlayerInput playerInput) {
    ModuleCategory.ensureInitialized();
    if (guiModuleEntry.test()
        && !guiModuleEntry13.test()
        && !clientPlayerEntity3.hasVehicle()
        && !clientPlayerEntity3.isSpectator()) {
      if (!enabled3) {
        enabled4 = clientPlayerEntity3.getAbilities().allowFlying;
        enabled5 = clientPlayerEntity3.getAbilities().flying;
        amount = clientPlayerEntity3.getAbilities().getFlySpeed();
        enabled3 = true;
      }

      if (choiceSettingView.getSelectedIndex() == 1) {
        clientPlayerEntity3.getAbilities().allowFlying = true;
        clientPlayerEntity3.getAbilities().flying = true;
        clientPlayerEntity3
            .getAbilities()
            .setFlySpeed(Math.max(0.01F, numberSettingView.getValue() * 0.1F));
      } else {
        clientPlayerEntity3.getAbilities().allowFlying = enabled4;
        clientPlayerEntity3.getAbilities().setFlySpeed(amount);
        Vec3d vec3d = getVec3d(clientPlayerEntity3, numberSettingView.getValue(), false);
        double doubleValue = computeDouble(playerInput, numberSettingView2.getValue(), 0.0);
        clientPlayerEntity3.getAbilities().flying = false;
        clientPlayerEntity3.setVelocity(vec3d.x, doubleValue, vec3d.z);
        clientPlayerEntity3.fallDistance = 0.0;
      }
    } else {
      apply2(clientPlayerEntity3);
    }
  }

  public static void apply2(ClientPlayerEntity clientPlayerEntity3) {
    ModuleCategory.ensureInitialized();
    if (enabled3 && clientPlayerEntity3 != null) {
      clientPlayerEntity3.getAbilities().allowFlying = enabled4;
      clientPlayerEntity3.getAbilities().flying = enabled5;
      clientPlayerEntity3.getAbilities().setFlySpeed(amount);
      clientPlayerEntity3.sendAbilitiesUpdate();
      enabled3 = false;
    }
  }

  public static void apply2(ClientPlayerEntity clientPlayerEntity3, PlayerInput playerInput) {
    ModuleCategory.ensureInitialized();

    Entity vehicle = clientPlayerEntity3.getVehicle();
    if (guiModuleEntry2.test()
        && vehicle instanceof AbstractBoatEntity
        && vehicle.getControllingPassenger() == clientPlayerEntity3) {
      if (entity != vehicle) {
        update();
        entity = vehicle;
        enabled7 = vehicle.hasNoGravity();
      }

      vehicle.setNoGravity(true);
      Vec3d vec3d = getVec3d(clientPlayerEntity3, numberSettingView3.getValue(), false);
      double doubleValue =
          computeDouble(
              playerInput.jump(),
              playerInput.sprint(),
              numberSettingView4.getValue(),
              numberSettingView5.getValue());
      vehicle.setVelocity(vec3d.x, doubleValue, vec3d.z);
      vehicle.fallDistance = 0.0;
    } else {
      update();
    }
  }

  public static void update() {
    ModuleCategory.ensureInitialized();
    if (entity != null && !entity.isRemoved()) {
      entity.setNoGravity(enabled7);
    }

    entity = null;
    enabled7 = false;
  }

  public static void apply3(ClientPlayerEntity clientPlayerEntity3, PlayerInput playerInput) {
    ModuleCategory.ensureInitialized();
    if (guiModuleEntry13.test()
        && !clientPlayerEntity3.hasVehicle()
        && !clientPlayerEntity3.isSpectator()) {
      clientPlayerEntity3.noClip = true;
      enabled6 = true;
      Vec3d vec3d = getVec3d(clientPlayerEntity3, numberSettingView17.getValue(), false);
      double doubleValue = computeDouble(playerInput, numberSettingView18.getValue(), 0.0);
      clientPlayerEntity3.setVelocity(vec3d.x, doubleValue, vec3d.z);
      clientPlayerEntity3.fallDistance = 0.0;
    } else {
      if (enabled6 && !clientPlayerEntity3.isSpectator()) {
        clientPlayerEntity3.noClip = false;
      }

      enabled6 = false;
    }
  }

  public static void apply3(ClientPlayerEntity clientPlayerEntity3) {
    ModuleCategory.ensureInitialized();
    if (guiModuleEntry13.test()
        && !clientPlayerEntity3.hasVehicle()
        && !clientPlayerEntity3.isSpectator()
        && !PearlEscapeAutomation.test()) {
      clientPlayerEntity3.noClip = true;
      enabled6 = true;
    }
  }

  public static void apply4(ClientPlayerEntity clientPlayerEntity3) {
    ModuleCategory.ensureInitialized();
    if (guiModuleEntry3.test()
        && !clientPlayerEntity3.getAbilities().creativeMode
        && !clientPlayerEntity3.isGliding()) {
      if (!(clientPlayerEntity3.fallDistance < numberSettingView6.getValue())
          && !(clientPlayerEntity3.getVelocity().y >= 0.0)) {
        clientPlayerEntity3.networkHandler.sendPacket(
            new OnGroundOnly(true, clientPlayerEntity3.horizontalCollision));
        clientPlayerEntity3.fallDistance = 0.0;
      }
    }
  }

  public static void apply4(ClientPlayerEntity clientPlayerEntity3, PlayerInput playerInput) {
    Vec3d vec3d = getVec3d(clientPlayerEntity3, numberSettingView14.getValue(), true);
    double doubleValue =
        computeDouble(
            playerInput, numberSettingView15.getValue(), clientPlayerEntity3.getVelocity().y);
    clientPlayerEntity3.setVelocity(vec3d.x, doubleValue, vec3d.z);
  }

  public static void apply5(ClientPlayerEntity clientPlayerEntity3, PlayerInput playerInput) {
    ModuleCategory.ensureInitialized();

    boolean flag = test(playerInput);
    boolean flag2 = clientPlayerEntity3.isTouchingWater() || clientPlayerEntity3.isInLava();
    if (guiModuleEntry16.test() && clientPlayerEntity3.isClimbing()) {
      double doubleValue =
          playerInput.sneak() ? -numberSettingView21.getValue() : numberSettingView21.getValue();
      clientPlayerEntity3.setVelocity(
          clientPlayerEntity3.getVelocity().x, doubleValue, clientPlayerEntity3.getVelocity().z);
    } else if (guiModuleEntry15.test() && clientPlayerEntity3.horizontalCollision && flag) {
      clientPlayerEntity3.setVelocity(
          clientPlayerEntity3.getVelocity().x,
          numberSettingView20.getValue(),
          clientPlayerEntity3.getVelocity().z);
    }

    if (guiModuleEntry12.test() && flag2) {
      Vec3d vec3d = getVec3d(clientPlayerEntity3, numberSettingView16.getValue(), false);
      double doubleValue2 =
          playerInput.sneak() ? -0.18 : Math.max(0.08, clientPlayerEntity3.getVelocity().y);
      clientPlayerEntity3.setVelocity(vec3d.x, doubleValue2, vec3d.z);
      clientPlayerEntity3.fallDistance = 0.0;
    } else if (guiModuleEntry19.test() && flag2 && flag) {
      Vec3d vec3d2 = getVec3d(clientPlayerEntity3, numberSettingView24.getValue(), false);
      clientPlayerEntity3.setVelocity(vec3d2.x, clientPlayerEntity3.getVelocity().y, vec3d2.z);
    } else if (guiModuleEntry4.test()
        && !flag2
        && !clientPlayerEntity3.isClimbing()
        && !clientPlayerEntity3.isGliding()) {
      Vec3d vec3d3 = getVec3d(clientPlayerEntity3, numberSettingView7.getValue(), false);
      clientPlayerEntity3.setVelocity(vec3d3.x, clientPlayerEntity3.getVelocity().y, vec3d3.z);
    }

    if (guiModuleEntry14.test()
        && count == 0
        && flag
        && clientPlayerEntity3.isOnGround()
        && clientPlayerEntity3.horizontalCollision) {
      clientPlayerEntity3.setPosition(
          clientPlayerEntity3.getX(),
          clientPlayerEntity3.getY() + numberSettingView19.getValue(),
          clientPlayerEntity3.getZ());
      clientPlayerEntity3.setOnGround(false);
      count = 4;
    }
  }

  public static void apply(ClientPlayerEntity clientPlayerEntity3, boolean flag, boolean flag2) {
    ModuleCategory.ensureInitialized();
    if (flag || flag2) {
      Vec3d velocity = clientPlayerEntity3.getVelocity();
      if (guiModuleEntry8.test() && flag2) {
        Vec3d vec3d = getVec3d(clientPlayerEntity3, numberSettingView10.getValue(), true);
        clientPlayerEntity3.setVelocity(vec3d.x, numberSettingView11.getValue(), vec3d.z);
      } else if (guiModuleEntry9.test() && flag2) {
        clientPlayerEntity3.setVelocity(
            velocity.x, Math.max(velocity.y, numberSettingView12.getValue()), velocity.z);
      } else {
        if (guiModuleEntry10.test() && flag && !flag2 && !clientPlayerEntity3.isOnGround()) {
          clientPlayerEntity3.setVelocity(velocity.x, numberSettingView13.getValue(), velocity.z);
        }
      }
    }
  }

  public static void updateUseTiming(
      MinecraftClient minecraftClient, ClientPlayerEntity clientPlayerEntity3) {
    ModuleCategory.ensureInitialized();
    if (guiModuleEntry17.test()) {
      MinecraftClientAccessor minecraftClientAccessor =
          (MinecraftClientAccessor) (Object) (minecraftClient);
      minecraftClientAccessor.setItemUseCooldown(
          Math.min(
              minecraftClientAccessor.getItemUseCooldown(),
              Math.round(numberSettingView22.getValue())));
    }

    if (guiModuleEntry18.test() && clientPlayerEntity3.isUsingItem()) {
      ItemStack activeItem = clientPlayerEntity3.getActiveItem();
      UseAction useAction = activeItem.getUseAction();
      if (useAction == UseAction.EAT || useAction == UseAction.DRINK) {
        int intValue = Math.round(numberSettingView23.getValue());
        if (clientPlayerEntity3.getItemUseTimeLeft() > intValue) {
          ((LivingEntityAccessor) (Object) (clientPlayerEntity3)).setItemUseTimeLeft(intValue);
        }

        if (minecraftClient.getServer() != null) {
          IntegratedServer server = minecraftClient.getServer();
          UUID uuid = clientPlayerEntity3.getUuid();
          server.execute(
              () -> {
                ModuleCategory.ensureInitialized();

                ServerPlayerEntity player = server.getPlayerManager().getPlayer(uuid);
                if (player != null && player.isUsingItem()) {
                  UseAction useAction2 = player.getActiveItem().getUseAction();
                  if ((useAction2 == UseAction.EAT || useAction2 == UseAction.DRINK)
                      && player.getItemUseTimeLeft() > intValue) {
                    ((LivingEntityAccessor) (Object) (player)).setItemUseTimeLeft(intValue);
                  }
                }
              });
        }
      }
    }
  }

  public static void apply3(MinecraftClient minecraftClient) {
    ModuleCategory.ensureInitialized();
    if (minecraftClient.interactionManager != null) {
      ClientPlayerInteractionManagerAccessor clientPlayerInteractionManagerAccessor =
          (ClientPlayerInteractionManagerAccessor) (Object) (minecraftClient.interactionManager);
      float floatValue = clientPlayerInteractionManagerAccessor.getCurrentBreakingProgress();
      if (!(floatValue <= 0.0F)) {
        if (guiModuleEntry7.test()) {
          clientPlayerInteractionManagerAccessor.setCurrentBreakingProgress(
              Math.min(floatValue, numberSettingView9.getValue()));
        } else if (guiModuleEntry6.test()) {
          clientPlayerInteractionManagerAccessor.setCurrentBreakingProgress(1.0F);
        } else if (guiModuleEntry5.test()) {
          clientPlayerInteractionManagerAccessor.setCurrentBreakingProgress(
              Math.min(1.0F, floatValue + numberSettingView8.getValue()));
        }
      }
    }
  }

  public static Vec3d getVec3d(
      ClientPlayerEntity clientPlayerEntity3, double doubleValue, boolean flag) {
    ModuleCategory.ensureInitialized();

    PlayerInput playerInput = clientPlayerEntity3.input.playerInput;
    double doubleValue2 = (playerInput.forward() ? 1 : 0) - (playerInput.backward() ? 1 : 0);
    double doubleValue3 = (playerInput.left() ? 1 : 0) - (playerInput.right() ? 1 : 0);
    if (doubleValue2 == 0.0 && doubleValue3 == 0.0) {
      if (!flag
          || playerInput.forward()
          || playerInput.backward()
          || playerInput.left()
          || playerInput.right()) {
        return Vec3d.ZERO;
      }

      doubleValue2 = 1.0;
    }

    double doubleValue4 = Math.toRadians(clientPlayerEntity3.getYaw());
    double doubleValue5 =
        -Math.sin(doubleValue4) * doubleValue2 + Math.cos(doubleValue4) * doubleValue3;
    double doubleValue6 =
        Math.cos(doubleValue4) * doubleValue2 + Math.sin(doubleValue4) * doubleValue3;
    double doubleValue7 = Math.hypot(doubleValue5, doubleValue6);
    return doubleValue7 == 0.0
        ? Vec3d.ZERO
        : new Vec3d(
            doubleValue5 / doubleValue7 * doubleValue,
            0.0,
            doubleValue6 / doubleValue7 * doubleValue);
  }

  public static boolean test(PlayerInput playerInput) {
    ModuleCategory.ensureInitialized();
    return playerInput.forward() != playerInput.backward()
        || playerInput.left() != playerInput.right();
  }

  public static double computeDouble(
      PlayerInput playerInput, double doubleValue, double doubleValue2) {
    return computeDouble(playerInput.jump(), playerInput.sneak(), doubleValue, doubleValue2);
  }

  public static double computeDouble(
      boolean flag, boolean flag2, double doubleValue, double doubleValue2) {
    ModuleCategory.ensureInitialized();
    return !flag && !flag2 ? doubleValue2 : ((flag ? 1 : 0) - (flag2 ? 1 : 0)) * doubleValue;
  }

  public static GuiModuleEntry getGuiModuleEntry(
      String text, String text2, CollectionData[] collectionDataArray) {
    return new GuiModuleEntry(text, text2, collectionDataArray);
  }

  public static CollectionData getCollectionData(String text, SettingEntry[] settingEntryArray) {
    return new CollectionData(text, settingEntryArray);
  }

  public static NumberSettingView getNumberSettingView(
      String text,
      String text2,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      String text3) {
    return new NumberSettingView(
        text, text2, floatValue, floatValue2, floatValue3, floatValue4, text3);
  }

  public static ChoiceSettingView getChoiceSettingView(
      String text, String text2, int intValue, String[] textArray) {
    return new ChoiceSettingView(text, text2, intValue, textArray);
  }

  static {
    setCountArray(new int[3]);
    String[] textArray =
        new String[] {
          "Cooldown",
          "Horizontal flight speed in blocks per tick.",
          "Instant Break",
          "Velocity directly controls motion; Abilities uses Minecraft flight.",
          "Horizontal movement speed in blocks per tick.",
          "Guard",
          "Horizontal Speed",
          " ticks",
          "Horizontal speed while moving across liquid.",
          "Height moved upward when colliding with an obstacle.",
          "Vertical velocity applied with the long jump.",
          "Vertical ladder and vine speed.",
          "Completes food use when this many client ticks remain.",
          "Caps your own mining progress to prevent instant completion",
          "Horizontal no-clip speed.",
          "Speed",
          "Vertical no-clip speed.",
          "Jump Height",
          "Horizontal speed while submerged.",
          "Jump Height",
          "Elytra ascent and descent speed.",
          "Horizontal velocity applied when a jump begins.",
          "Overrides horizontal movement while submerged",
          "Vertical Speed",
          " blocks",
          "Completes active block breaking immediately",
          "Fall Threshold",
          "Phase",
          "Step",
          "Horizontal Boost",
          "Directly controls a ridden boat in the air",
          "Movement",
          "Velocity",
          "Direct, unrestricted aerial movement",
          " b/t",
          "Vertical velocity applied to an air jump.",
          "Step Height",
          "Reduces repeated item-use delay",
          "Timing",
          "Adds horizontal velocity to the start of a jump",
          "Caps local mining progress to stop an instant-break completion.",
          "Disables local collision and enables no-clip movement",
          "Vertical flight speed in blocks per tick.",
          "Mining",
          "Progress Bonus",
          "Directly controls velocity while fall-flying",
          "Timing",
          "Extra block-breaking progress added every client tick.",
          "Ticks between repeated item-use actions.",
          "Water Speed",
          "Movement",
          " blocks",
          "Idle Glide",
          "High Jump",
          "Climb Speed",
          "Fast Use",
          "Vertical velocity applied to a ground jump.",
          "No Fall",
          "Moves over obstacles taller than the vanilla step height",
          "Fast Climb",
          "Speed Mine",
          "Reports a grounded state before fall damage",
          "Horizontal Speed",
          "Fast Eat",
          "Speed",
          "Flight",
          "Boat speed in blocks per tick.",
          "Long Jump",
          "Climb Speed",
          "Air Jump",
          "Boat Fly",
          "Elytra Fly",
          "Keeps the player moving at the liquid surface",
          "Anti Instant Break",
          "Overrides ladder and vine climb velocity",
          "Accelerates active block-breaking progress",
          "Mode",
          " ticks",
          "Horizontal elytra speed in blocks per tick.",
          "Abilities",
          "Spider",
          "Overrides horizontal player movement speed",
          "Climbs upward while pushing against a wall",
          "Allows a fresh jump while already airborne",
          "Raises the vertical velocity of a ground jump",
          "Progress Cap",
          "Finishes food use early; guaranteed in singleplayer, server-dependent online",
          "Finish At",
          "Vertical speed against a wall.",
          "Water Speed",
          "/tick",
          " b/t",
          "Surface Speed",
          "Jump rises; Sprint descends. Sneak dismounts normally.",
          "Packet",
          "Jesus",
          "Vertical Speed",
          "Vertical motion while neither Jump nor Sprint is held.",
          "Fall distance before an on-ground movement packet is sent."
        };

    numberSettingView =
        getNumberSettingView(textArray[62], textArray[1], 0.55F, 0.1F, 2.0F, 0.05F, textArray[91]);

    numberSettingView2 =
        getNumberSettingView(textArray[96], textArray[42], 0.42F, 0.1F, 1.5F, 0.05F, textArray[34]);
    numberSettingView3 =
        getNumberSettingView(textArray[6], textArray[66], 1.1F, 0.1F, 4.0F, 0.1F, textArray[34]);
    numberSettingView4 =
        getNumberSettingView(textArray[23], textArray[93], 0.6F, 0.1F, 2.0F, 0.05F, textArray[34]);
    numberSettingView5 =
        getNumberSettingView(textArray[52], textArray[97], 0.0F, -0.5F, 0.5F, 0.05F, textArray[34]);
    numberSettingView6 =
        getNumberSettingView(textArray[26], textArray[98], 2.5F, 0.5F, 10.0F, 0.5F, textArray[51]);
    numberSettingView7 =
        getNumberSettingView(textArray[64], textArray[4], 0.42F, 0.1F, 1.5F, 0.05F, textArray[34]);
    numberSettingView8 =
        getNumberSettingView(textArray[44], textArray[47], 0.1F, 0.01F, 0.5F, 0.01F, textArray[90]);
    numberSettingView9 =
        getNumberSettingView(textArray[85], textArray[40], 0.72F, 0.1F, 0.95F, 0.01F, "");
    numberSettingView10 =
        getNumberSettingView(textArray[29], textArray[21], 1.25F, 0.3F, 4.0F, 0.05F, textArray[34]);
    numberSettingView11 =
        getNumberSettingView(textArray[17], textArray[10], 0.55F, 0.2F, 1.5F, 0.05F, textArray[34]);
    numberSettingView12 =
        getNumberSettingView(
            textArray[19], textArray[56], 0.85F, 0.42F, 2.5F, 0.05F, textArray[34]);
    numberSettingView13 =
        getNumberSettingView(textArray[19], textArray[35], 0.55F, 0.2F, 1.5F, 0.05F, textArray[34]);
    numberSettingView14 =
        getNumberSettingView(textArray[6], textArray[78], 1.2F, 0.2F, 4.0F, 0.1F, textArray[34]);
    numberSettingView15 =
        getNumberSettingView(textArray[23], textArray[20], 0.6F, 0.1F, 2.0F, 0.05F, textArray[34]);
    numberSettingView16 =
        getNumberSettingView(textArray[92], textArray[8], 0.38F, 0.1F, 1.5F, 0.05F, textArray[34]);
    numberSettingView17 =
        getNumberSettingView(textArray[6], textArray[14], 0.45F, 0.1F, 1.5F, 0.05F, textArray[34]);
    numberSettingView18 =
        getNumberSettingView(textArray[23], textArray[16], 0.35F, 0.1F, 1.5F, 0.05F, textArray[34]);
    numberSettingView19 =
        getNumberSettingView(textArray[36], textArray[9], 1.0F, 0.5F, 4.0F, 0.5F, textArray[24]);
    numberSettingView20 =
        getNumberSettingView(
            textArray[54], textArray[88], 0.28F, 0.05F, 1.0F, 0.05F, textArray[34]);
    numberSettingView21 =
        getNumberSettingView(textArray[68], textArray[11], 0.45F, 0.1F, 1.5F, 0.05F, textArray[34]);
    numberSettingView22 =
        getNumberSettingView(textArray[0], textArray[48], 0.0F, 0.0F, 4.0F, 1.0F, textArray[7]);
    numberSettingView23 =
        getNumberSettingView(textArray[87], textArray[12], 1.0F, 1.0F, 8.0F, 1.0F, textArray[77]);
    numberSettingView24 =
        getNumberSettingView(textArray[49], textArray[18], 0.5F, 0.1F, 1.5F, 0.05F, textArray[34]);
    guiModuleEntry =
        getGuiModuleEntry(
            textArray[65],
            textArray[33],
            new CollectionData[] {
              getCollectionData(
                  textArray[31],
                  new SettingEntry[] {choiceSettingView, numberSettingView, numberSettingView2})
            });
    guiModuleEntry2 =
        getGuiModuleEntry(
            textArray[70],
            textArray[30],
            new CollectionData[] {
              getCollectionData(
                  textArray[50],
                  new SettingEntry[] {numberSettingView3, numberSettingView4, numberSettingView5})
            });
    guiModuleEntry3 =
        getGuiModuleEntry(
            textArray[57],
            textArray[61],
            new CollectionData[] {
              getCollectionData(textArray[94], new SettingEntry[] {numberSettingView6})
            });
    guiModuleEntry4 =
        getGuiModuleEntry(
            textArray[15],
            textArray[81],
            new CollectionData[] {
              getCollectionData(textArray[50], new SettingEntry[] {numberSettingView7})
            });
    guiModuleEntry5 =
        getGuiModuleEntry(
            textArray[60],
            textArray[75],
            new CollectionData[] {
              getCollectionData(textArray[43], new SettingEntry[] {numberSettingView8})
            });
    guiModuleEntry6 = getGuiModuleEntry(textArray[2], textArray[25], new CollectionData[0]);
    guiModuleEntry7 =
        getGuiModuleEntry(
            textArray[73],
            textArray[13],
            new CollectionData[] {
              getCollectionData(textArray[5], new SettingEntry[] {numberSettingView9})
            });
    guiModuleEntry8 =
        getGuiModuleEntry(
            textArray[67],
            textArray[39],
            new CollectionData[] {
              getCollectionData(
                  textArray[50], new SettingEntry[] {numberSettingView10, numberSettingView11})
            });
    guiModuleEntry9 =
        getGuiModuleEntry(
            textArray[53],
            textArray[84],
            new CollectionData[] {
              getCollectionData(textArray[50], new SettingEntry[] {numberSettingView12})
            });
    guiModuleEntry10 =
        getGuiModuleEntry(
            textArray[69],
            textArray[83],
            new CollectionData[] {
              getCollectionData(textArray[50], new SettingEntry[] {numberSettingView13})
            });
    guiModuleEntry11 =
        getGuiModuleEntry(
            textArray[71],
            textArray[45],
            new CollectionData[] {
              getCollectionData(
                  textArray[50], new SettingEntry[] {numberSettingView14, numberSettingView15})
            });
    guiModuleEntry12 =
        getGuiModuleEntry(
            textArray[95],
            textArray[72],
            new CollectionData[] {
              getCollectionData(textArray[50], new SettingEntry[] {numberSettingView16})
            });
    guiModuleEntry13 =
        getGuiModuleEntry(
            textArray[27],
            textArray[41],
            new CollectionData[] {
              getCollectionData(
                  textArray[50], new SettingEntry[] {numberSettingView17, numberSettingView18})
            });
    guiModuleEntry14 =
        getGuiModuleEntry(
            textArray[28],
            textArray[58],
            new CollectionData[] {
              getCollectionData(textArray[50], new SettingEntry[] {numberSettingView19})
            });
    guiModuleEntry15 =
        getGuiModuleEntry(
            textArray[80],
            textArray[82],
            new CollectionData[] {
              getCollectionData(textArray[50], new SettingEntry[] {numberSettingView20})
            });
    guiModuleEntry16 =
        getGuiModuleEntry(
            textArray[59],
            textArray[74],
            new CollectionData[] {
              getCollectionData(textArray[50], new SettingEntry[] {numberSettingView21})
            });
    guiModuleEntry17 =
        getGuiModuleEntry(
            textArray[55],
            textArray[37],
            new CollectionData[] {
              getCollectionData(textArray[46], new SettingEntry[] {numberSettingView22})
            });
    guiModuleEntry18 =
        getGuiModuleEntry(
            textArray[63],
            textArray[86],
            new CollectionData[] {
              getCollectionData(textArray[38], new SettingEntry[] {numberSettingView23})
            });
    guiModuleEntry19 =
        getGuiModuleEntry(
            textArray[89],
            textArray[22],
            new CollectionData[] {
              getCollectionData(textArray[50], new SettingEntry[] {numberSettingView24})
            });
    list =
        List.of(
            guiModuleEntry,
            guiModuleEntry2,
            guiModuleEntry3,
            guiModuleEntry4,
            guiModuleEntry5,
            guiModuleEntry6,
            guiModuleEntry7,
            guiModuleEntry8,
            guiModuleEntry9,
            guiModuleEntry10,
            guiModuleEntry11,
            guiModuleEntry12,
            guiModuleEntry13,
            guiModuleEntry14,
            guiModuleEntry15,
            guiModuleEntry16,
            guiModuleEntry17,
            guiModuleEntry18,
            guiModuleEntry19);
  }

  public static void setCountArray(int[] countArray2) {
    countArray = countArray2;
  }

  public static int[] getCountArray() {
    return countArray;
  }
}
