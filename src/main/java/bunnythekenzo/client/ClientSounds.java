package bunnythekenzo.client;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public final class ClientSounds {
  public static final SoundEvent soundEvent = getSoundEvent("ui.gui_open");
  public static final SoundEvent soundEvent2 = getSoundEvent("ui.gui_close");
  public static final SoundEvent soundEvent3 = getSoundEvent("ui.hover");
  public static final SoundEvent soundEvent4 = getSoundEvent("ui.toggle_on");
  public static final SoundEvent soundEvent5 = getSoundEvent("ui.toggle_off");
  public static final SoundEvent soundEvent6 = getSoundEvent("ui.slider");
  public static final SoundEvent soundEvent7 = getSoundEvent("ui.select");
  public static final SoundEvent soundEvent8 = getSoundEvent("ui.keybind");
  public static final SoundEvent soundEvent9 = getSoundEvent("ui.notify_on");
  public static final SoundEvent soundEvent10 = getSoundEvent("ui.notify_off");
  public static final SoundEvent[] soundEventArray = getSoundEventArray("creamy_keys.key_", 36);
  public static final SoundEvent soundEvent11 = getSoundEvent("creamy_keys.space");
  public static final SoundEvent soundEvent12 = getSoundEvent("creamy_keys.enter");
  public static final SoundEvent soundEvent13 = getSoundEvent("creamy_keys.backspace");
  public static final SoundEvent soundEvent14 = getSoundEvent("creamy_keys.modifier");
  public static final SoundEvent soundEvent15 = getSoundEvent("creamy_keys.mouse_left");
  public static final SoundEvent soundEvent16 = getSoundEvent("creamy_keys.mouse_right");
  public static final SoundEvent soundEvent17 = getSoundEvent("creamy_keys.mouse_middle");
  public static final SoundEvent soundEvent18 = getSoundEvent("creamy_keys.scroll_up");
  public static final SoundEvent soundEvent19 = getSoundEvent("creamy_keys.scroll_down");
  public static final ClientSoundsData clientSoundsData = getClientSoundsData("creamy");
  public static final ClientSoundsData clientSoundsData2 = getClientSoundsData("clacky");
  public static final ClientSoundsData clientSoundsData3 = getClientSoundsData("thocky");
  public static final ClientSoundsData clientSoundsData4 = getClientSoundsData("honey_creamy");
  public static final SoundEvent soundEvent20 = getSoundEvent("creamy_keys.mouse_left_release");
  public static final SoundEvent soundEvent21 = getSoundEvent("creamy_keys.mouse_right_release");
  public static final SoundEvent soundEvent22 = getSoundEvent("creamy_keys.mouse_middle_release");
  public static final SoundEvent soundEvent23 = getSoundEvent("startup.sad");
  public static final SoundEvent soundEvent24 = getSoundEvent("startup.song");
  public static final SoundEvent soundEvent25 = getSoundEvent("startup.tiki");
  public static final SoundEvent soundEvent26 = getSoundEvent("startup.67");
  public static final SoundEvent soundEvent27 = getSoundEvent("startup.welcome");
  public static final SoundEvent soundEvent28 = getSoundEvent("emote.pulse_pop");
  public static final SoundEvent soundEvent29 = getSoundEvent("emote.neon_shuffle");
  public static final SoundEvent soundEvent30 = getSoundEvent("emote.sidewave");
  public static final SoundEvent soundEvent31 = getSoundEvent("emote.orange_justice");
  public static final SoundEvent soundEvent32 = getSoundEvent("emote.take_the_l");
  public static final SoundEvent soundEvent33 = getSoundEvent("emote.twerking");
  public static final SoundEvent soundEvent34 = getSoundEvent("emote.passinho_do_jamal");
  public static final SoundEvent soundEvent35 = getSoundEvent("emote.hoppin_dih");
  public static final SoundEvent soundEvent36 = getSoundEvent("emote.lush_life");
  public static final SoundEvent soundEvent37 = getSoundEvent("emote.confetti_buildup");

  public static ClientSoundsData getClientSoundsData(String text) {
    String text2 = "creamy_keys." + text + ".";
    return new ClientSoundsData(
        getSoundEventArray(text2 + "press_", 12), getSoundEventArray(text2 + "release_", 12));
  }

  public static SoundEvent getSoundEvent(String text) {
    Identifier identifier = Identifier.of("bunnythekenzo", text);
    return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
  }

  public static SoundEvent[] getSoundEventArray(String text, int intValue) {
    SoundEvent[] soundEventArray2 = new SoundEvent[intValue];

    for (int index = 0; index < intValue; index++) {
      soundEventArray2[index] = getSoundEvent(text + String.format("%02d", index + 1));
    }

    return soundEventArray2;
  }

  public static void update() {}
}
