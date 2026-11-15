package bunnythekenzo.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.sound.SoundEvent;
import bunnythekenzo.client.setting.KeybindSettingView;
import bunnythekenzo.client.setting.NumberSettingView;
import bunnythekenzo.client.setting.SettingCodec;
import bunnythekenzo.client.setting.SettingEntry;
import bunnythekenzo.client.setting.ToggleSettingView;
import bunnythekenzo.client.ui.EmoteWheelScreen;

public final class EmoteCatalog {
  public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
  public static final float amount = 0.24F;
  public static final float amount2 = 0.34F;
  public final List list =
      List.of(
          new EmoteDefinition(
              "Take the L",
              "Dance",
              "Forehead L, hand on hip and alternating kicks to the original music",
              150.0F,
              25267L,
              ClientSounds.soundEvent32,
              false,
              EmoteAnimation.TAKE_THE_L),
          new EmoteDefinition(
              "Hoppin dih",
              "Seated hop",
              "Low seated hops, forward kicks and a spinning finish while you walk and jump",
              146.0F,
              15045L,
              ClientSounds.soundEvent35,
              true,
              EmoteAnimation.HOPPIN_DIH),
          new EmoteDefinition(
              "Wizox ban this guy",
              "Dance",
              "An original routine: ban hits, quick footwork, freezes, body rolls and a spinning"
                  + " finale",
              136.6F,
              32000L,
              ClientSounds.soundEvent30,
              false,
              EmoteAnimation.WIZOX_BAN),
          new EmoteDefinition(
              "Farex pull my pearl",
              "Pearl pull",
              "Pearl pulls, heel-toe steps, double hits, freezes and a spinning finish",
              128.4F,
              13200L,
              ClientSounds.soundEvent28,
              false,
              EmoteAnimation.FAREX_PULL_MY_PEARL),
          new EmoteDefinition(
              "Twerking",
              "Dance",
              "Half-turns, alternating knees and low hip pulses with the original music",
              145.0F,
              14371L,
              ClientSounds.soundEvent33,
              false,
              EmoteAnimation.TWERKING),
          new EmoteDefinition(
              "Jerking off",
              "Air gesture",
              "Slow-to-fast arm motion, suspenseful music and a white paper-confetti finale",
              78.0F,
              12000L,
              ClientSounds.soundEvent37,
              false,
              EmoteAnimation.JERKING_OFF),
          new EmoteDefinition(
              "Orange justice",
              "Dance",
              "Crossed arm swings, overhead hits and deep side steps with the original music",
              121.6F,
              29433L,
              ClientSounds.soundEvent31,
              false,
              EmoteAnimation.ORANGE_JUSTICE),
          new EmoteDefinition(
              "Passinho do jamal",
              "Dance",
              "Cross-body arm swings, double knee lifts and the original song",
              170.0F,
              11433L,
              ClientSounds.soundEvent34,
              false,
              EmoteAnimation.PASSINHO_DO_JAMAL),
          new EmoteDefinition(
              "Lush life",
              "Dance",
              "The original Lush life routine: side steps, arm sweeps, hip accents and a full turn",
              98.0F,
              14419L,
              ClientSounds.soundEvent36,
              false,
              EmoteAnimation.LUSH_LIFE),
          new EmoteDefinition(
              "Drdonutt wholesome moment",
              "Shuffle",
              "A mobile shuffle that keeps dancing while you walk and jump",
              124.0F,
              38900L,
              ClientSounds.soundEvent29,
              true,
              EmoteAnimation.SHUFFLE));
  public final KeybindSettingView wheelKeyControl =
      new KeybindSettingView(
          "Wheel key", "Hold this key in-game, point at an emote, then release", 66);
  public final NumberSettingView musicVolumeControl =
      new NumberSettingView(
          "Music volume",
          "Volume of the song played with an emote",
          75.0F,
          0.0F,
          100.0F,
          5.0F,
          "%");
  public final ToggleSettingView automaticF5Control =
      new ToggleSettingView(
          "Automatic F5", "Switches to third-person back view while an emote plays", true);
  public final ToggleSettingView cancelOnActionControl =
      new ToggleSettingView(
          "Cancel on action", "Stops on movement or jumping, except for mobile emotes", true);
  public final CollectionData collectionData =
      new CollectionData(
          "Wheel settings",
          new SettingEntry[] {
            this.wheelKeyControl,
            this.musicVolumeControl,
            this.automaticF5Control,
            this.cancelOnActionControl
          });
  public int count = -1;
  public long timestamp;
  public long timestamp2;
  public EmoteSound emoteSound;
  public Perspective perspective;
  public boolean enabled;
  public boolean enabled2;
  public final EmoteConfettiSystem emoteConfettiSystem = new EmoteConfettiSystem();

  public EmoteConfettiSystem getEmoteConfettiSystem() {
    return this.emoteConfettiSystem;
  }

  public EmoteCatalog() {
    this.update3();
  }

  public List getList() {
    return this.list;
  }

  public CollectionData getCollectionData() {
    return this.collectionData;
  }

  public int getCount() {
    return this.count;
  }

  public boolean test() {
    return this.count >= 0;
  }

  public boolean test(int intValue) {
    ClientPlayerEntity player = MinecraftClient.getInstance().player;
    return this.test()
        && player != null
        && player.getId() == intValue
        && ((EmoteDefinition) this.list.get(this.count)).emoteAnimation()
            == EmoteAnimation.JERKING_OFF;
  }

  public double computeDouble() {
    if (!this.test()) {
      return 0.0;
    }

    long timestamp22 = System.nanoTime();
    if (MinecraftClient.getInstance().isPaused()) {
      if (this.timestamp2 == 0L) {
        this.timestamp2 = timestamp22;
      }

      timestamp22 = this.timestamp2;
    } else if (this.timestamp2 != 0L) {
      this.timestamp = this.timestamp + (timestamp22 - this.timestamp2);
      this.timestamp2 = 0L;
    }

    return (timestamp22 - this.timestamp) / 1.0E9;
  }

  public boolean test2(int intValue) {
    MinecraftClient instance = MinecraftClient.getInstance();
    if (this.wheelKeyControl.test3()
        && this.wheelKeyControl.computeInt() == intValue
        && instance.currentScreen == null
        && instance.world != null
        && instance.player != null) {
      if (this.test()) {
        this.update();
      }

      instance.setScreen(new EmoteWheelScreen(this, intValue));
      return true;
    } else {
      return false;
    }
  }

  public void apply(int count2) {
    if (count2 >= 0 && count2 < this.list.size()) {
      if (!((EmoteDefinition) this.list.get(count2)).test()) {
        MinecraftClient instance = MinecraftClient.getInstance();
        if (instance.world != null && instance.player != null) {
          if (((EmoteDefinition) this.list.get(count2)).emoteAnimation()
              == EmoteAnimation.ORANGE_JUSTICE) {
            OrangeJusticeAnimation.update();
          }

          this.update();
          if (instance.currentScreen != null) {
            instance.setScreen(null);
          }

          this.count = count2;
          this.timestamp = System.nanoTime();
          if (this.automaticF5Control.getValue()) {
            this.perspective = instance.options.getPerspective();
            if (this.perspective != Perspective.THIRD_PERSON_BACK) {
              instance.options.setPerspective(Perspective.THIRD_PERSON_BACK);
              this.enabled = true;
            }
          }

          SoundEvent soundEvent = ((EmoteDefinition) this.list.get(count2)).soundEvent();
          if (soundEvent != null) {
            this.emoteSound =
                new EmoteSound(soundEvent, this.musicVolumeControl.getValue() / 100.0F);
            instance.getSoundManager().play(this.emoteSound);
          }
        }
      }
    }
  }

  public void update() {
    this.apply(false);
  }

  public void apply(boolean flag) {
    MinecraftClient instance = MinecraftClient.getInstance();
    if (!flag) {
      this.emoteConfettiSystem.update();
    }

    this.enabled2 = false;
    if (this.emoteSound != null && instance.getSoundManager() != null) {
      this.emoteSound.update();
      instance.getSoundManager().stop(this.emoteSound);
    }

    this.emoteSound = null;
    this.count = -1;
    this.timestamp = 0L;
    this.timestamp2 = 0L;
    if (this.enabled
        && this.perspective != null
        && instance.options.getPerspective() == Perspective.THIRD_PERSON_BACK) {
      instance.options.setPerspective(this.perspective);
    }

    this.enabled = false;
    this.perspective = null;
  }

  public void update2() {
    MinecraftClient instance = MinecraftClient.getInstance();
    this.emoteConfettiSystem.tick(instance);
    if (this.test()) {
      if (instance.world != null && instance.player != null && instance.player.isAlive()) {
        EmoteDefinition emoteDefinition = (EmoteDefinition) this.list.get(this.count);
        double value = this.computeDouble();
        if (!instance.isPaused()
            && !this.enabled2
            && emoteDefinition.emoteAnimation() == EmoteAnimation.JERKING_OFF
            && value >= 11.2F) {
          this.emoteConfettiSystem.apply(instance);
          this.enabled2 = true;
        }

        if (value * 1000.0 >= emoteDefinition.timestamp()) {
          this.apply(true);
        } else {
          boolean flag =
              instance.options.forwardKey.isPressed()
                  || instance.options.backKey.isPressed()
                  || instance.options.leftKey.isPressed()
                  || instance.options.rightKey.isPressed();
          if (this.cancelOnActionControl.getValue()
              && (flag && !emoteDefinition.enabled()
                  || instance.options.jumpKey.isPressed() && !emoteDefinition.enabled()
                  || instance.options.sneakKey.isPressed()
                  || instance.options.attackKey.isPressed()
                  || instance.options.useKey.isPressed()
                  || instance.player.hurtTime > 0
                  || instance.player.hasVehicle())) {
            this.update();
          }
        }
      } else {
        this.update();
      }
    }
  }

  public void apply(
      BipedEntityModel bipedEntityModel, PlayerEntityRenderState playerEntityRenderState) {
    MinecraftClient instance = MinecraftClient.getInstance();
    if (this.test()
        && instance.player != null
        && playerEntityRenderState.id == instance.player.getId()) {
      EmoteDefinition emoteDefinition = (EmoteDefinition) this.list.get(this.count);
      float floatValue = (float) this.computeDouble();
      float floatValue2 = (float) emoteDefinition.timestamp() / 1000.0F - floatValue;
      boolean flag =
          emoteDefinition.emoteAnimation() == EmoteAnimation.LUSH_LIFE
              || emoteDefinition.emoteAnimation() == EmoteAnimation.TWERKING;
      float floatValue3 =
          emoteDefinition.emoteAnimation() == EmoteAnimation.HOPPIN_DIH
              ? 0.08F
              : (flag ? 0.1F : 0.24F);
      float floatValue4 = computeFloat(Math.clamp(floatValue / floatValue3, 0.0F, 1.0F));
      float floatValue5 =
          computeFloat(Math.clamp(floatValue2 / (flag ? 0.08F : 0.34F), 0.0F, 1.0F));
      EmotePoseAnimator.render(
          bipedEntityModel,
          emoteDefinition.emoteAnimation(),
          floatValue,
          emoteDefinition.amount(),
          Math.min(floatValue4, floatValue5));
    }
  }

  public static float computeFloat(float floatValue) {
    return floatValue * floatValue * (3.0F - 2.0F * floatValue);
  }

  public static int computeInt(
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      int intValue) {
    float floatValue7 = floatValue - floatValue3;
    float floatValue8 = floatValue2 - floatValue4;
    float floatValue9 = floatValue7 * floatValue7 + floatValue8 * floatValue8;
    if (!(floatValue9 < floatValue5 * floatValue5) && !(floatValue9 > floatValue6 * floatValue6)) {
      double doubleValue = (Math.PI * 2) / intValue;
      double doubleValue2 = Math.atan2(floatValue8, floatValue7) + (Math.PI / 2);
      if (doubleValue2 < 0.0) {
        doubleValue2 += Math.PI * 2;
      }

      return (int) Math.floor((doubleValue2 + doubleValue / 2.0) / doubleValue) % intValue;
    } else {
      return -1;
    }
  }

  public static Path getPath() {
    return FabricLoader.getInstance()
        .getConfigDir()
        .resolve("bunnythekenzo")
        .resolve("emotes.json");
  }

  public void update3() {
    Path path = getPath();
    if (Files.exists(path)) {
      try (BufferedReader bufferedReader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
        JsonObject jsonObject = gson.fromJson(bufferedReader, JsonObject.class);
        if (jsonObject != null) {
          SettingCodec.apply(jsonObject, this.collectionData.list());
        }
      } catch (Exception failure) {
        ClientInitializer.logger.warn("Could not read {}; using emote defaults", path, failure);
      }
    }
  }

  public void update4() {
    Path path = getPath();

    try {
      Files.createDirectories(path.getParent());

      try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
        gson.toJson(SettingCodec.getJsonObject(this.collectionData.list()), bufferedWriter);
      }
    } catch (IOException failure) {
      ClientInitializer.logger.error("Could not write {}", path, failure);
    }
  }
}
