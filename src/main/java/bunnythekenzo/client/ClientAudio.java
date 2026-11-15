package bunnythekenzo.client;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundEvent;
import bunnythekenzo.client.setting.SoundSettings;

public final class ClientAudio {
  public static float amount = 0.6F;
  public static boolean enabled = true;
  public static boolean enabled2 = true;
  public static boolean enabled3 = true;
  public static SoundSettings soundSettings = new SoundSettings();
  public static long timestamp;
  public static long timestamp2;
  public static long timestamp3;
  public static SoundInstance soundInstance;

  public static void apply(SoundSettings soundSettings2) {
    if (soundSettings2 != null) {
      soundSettings = soundSettings2;
    }
  }

  public static void render(SoundEvent soundEvent, float floatValue, float floatValue2) {
    if (!(amount <= 0.01F)) {
      MinecraftClient instance = MinecraftClient.getInstance();
      if (instance.getSoundManager() != null) {
        instance
            .getSoundManager()
            .play(PositionedSoundInstance.ui(soundEvent, floatValue, floatValue2 * amount));
      }
    }
  }

  public static void update() {
    if (enabled) {
      render(ClientSounds.soundEvent, 1.0F, 0.85F);
    }
  }

  public static void update2() {
    if (enabled) {
      render(ClientSounds.soundEvent2, 1.0F, 0.75F);
    }
  }

  public static void update3() {
    if (enabled2) {
      long timestamp4 = System.nanoTime();
      if (timestamp4 - timestamp >= 40000000L) {
        timestamp = timestamp4;
        render(
            ClientSounds.soundEvent3,
            0.94F + ThreadLocalRandom.current().nextFloat() * 0.12F,
            0.45F);
      }
    }
  }

  public static void update4() {
    if (enabled2) {
      long timestamp4 = System.nanoTime();
      if (timestamp4 - timestamp >= 40000000L) {
        timestamp = timestamp4;
        render(ClientSounds.soundEvent3, 1.18F, 0.3F);
      }
    }
  }

  public static void update5() {
    if (enabled3) {
      render(ClientSounds.soundEvent7, 1.0F, 0.6F);
    }
  }

  public static void apply(int intValue) {
    if (enabled3) {
      render(ClientSounds.soundEvent7, 1.0F + intValue * 0.11F, 0.7F);
    }
  }

  public static void apply(float floatValue) {
    if (enabled3) {
      render(ClientSounds.soundEvent8, 0.92F + Math.clamp(floatValue, 0.0F, 1.0F) * 0.3F, 0.5F);
    }
  }

  public static void update6() {
    if (enabled3) {
      render(ClientSounds.soundEvent7, 1.16F, 0.55F);
    }
  }

  public static void update7() {
    if (enabled3) {
      render(ClientSounds.soundEvent7, 0.84F, 0.45F);
    }
  }

  public static void apply(boolean flag) {
    if (enabled3) {
      render(flag ? ClientSounds.soundEvent4 : ClientSounds.soundEvent5, 1.0F, 0.75F);
    }
  }

  public static void apply2(float floatValue) {
    if (enabled3) {
      long timestamp22 = System.nanoTime();
      if (timestamp22 - timestamp2 >= 55000000L) {
        timestamp2 = timestamp22;
        render(ClientSounds.soundEvent6, 0.85F + Math.clamp(floatValue, 0.0F, 1.0F) * 0.55F, 0.45F);
      }
    }
  }

  public static void apply2(boolean flag) {
    if ((Boolean) soundSettings.notificationsSetting.getValue()) {
      render(flag ? ClientSounds.soundEvent9 : ClientSounds.soundEvent10, 1.0F, 0.7F);
    }
  }

  public static void update8() {
    int value = GuiPreferences.getGuiPreferences().startupSoundControl.getSelectedIndex();
    if (value != 2 && ProfileStorage.getProfileStorage().getProfileDefaults().enabled2) {
      if (value == 0) {
        getSoundInstance();
      } else {
        ArrayList arrayList = new ArrayList();
        if ((Boolean) soundSettings.sound67Setting.getValue()) {
          arrayList.add(ClientSounds.soundEvent26);
        }

        if ((Boolean) soundSettings.sound67SadSongSetting.getValue()) {
          arrayList.add(ClientSounds.soundEvent23);
        }

        if ((Boolean) soundSettings.sound67SongSetting.getValue()) {
          arrayList.add(ClientSounds.soundEvent24);
        }

        if ((Boolean) soundSettings.sound67TikiPhonkSetting.getValue()) {
          arrayList.add(ClientSounds.soundEvent25);
        }

        if (!arrayList.isEmpty()) {
          if (!(amount <= 0.01F)) {
            apply(soundInstance);
            soundInstance =
                PositionedSoundInstance.ui(
                    (SoundEvent)
                        arrayList.get(ThreadLocalRandom.current().nextInt(arrayList.size())),
                    1.0F,
                    0.9F * amount);
            MinecraftClient.getInstance().getSoundManager().play(soundInstance);
          }
        }
      }
    }
  }

  public static SoundInstance getSoundInstance() {
    if (enabled
        && !(amount <= 0.01F)
        && GuiPreferences.getGuiPreferences().startupSoundControl.getSelectedIndex() != 2) {
      MinecraftClient instance = MinecraftClient.getInstance();
      apply(soundInstance);
      instance.getMusicTracker().stop();
      PositionedSoundInstance soundInstance2 =
          PositionedSoundInstance.ui(ClientSounds.soundEvent27, 1.0F, amount * 0.65F);
      instance.getSoundManager().play(soundInstance2);
      soundInstance = soundInstance2;
      return soundInstance2;
    } else {
      return null;
    }
  }

  public static void apply(SoundInstance soundInstance2) {
    if (soundInstance2 != null) {
      MinecraftClient.getInstance().getSoundManager().stop(soundInstance2);
    }

    if (soundInstance == soundInstance2) {
      soundInstance = null;
    }
  }

  public static void render2(SoundEvent soundEvent, float floatValue, float floatValue2) {
    if (soundEvent != null) {
      long timestamp32 = System.nanoTime();
      if (timestamp32 - timestamp3 >= 12000000L) {
        timestamp3 = timestamp32;
        render(soundEvent, floatValue, floatValue2);
      }
    }
  }

  public static void update9() {
    if (enabled3) {
      long timestamp22 = System.nanoTime();
      if (timestamp22 - timestamp2 >= 220000000L) {
        timestamp2 = timestamp22;
        render(ClientSounds.soundEvent6, 0.55F, 0.25F);
      }
    }
  }
}
