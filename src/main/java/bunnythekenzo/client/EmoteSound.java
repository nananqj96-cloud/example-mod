package bunnythekenzo.client;

import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundInstance.AttenuationType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;

public final class EmoteSound extends MovingSoundInstance {
  public EmoteSound(SoundEvent soundEvent, float volume) {
    super(soundEvent, SoundCategory.MASTER, SoundInstance.createRandom());
    this.volume = volume;
    this.pitch = 1.0F;
    this.relative = true;
    this.attenuationType = AttenuationType.NONE;
    this.repeat = false;
  }

  public void update() {
    this.setDone();
  }

  @Override
  public boolean canPlay() {
    return !this.isDone();
  }

  @Override
  public void tick() {}
}
