package bunnythekenzo.client;

import net.minecraft.sound.SoundEvent;

public record ClientSoundsData(SoundEvent[] soundEventArray, SoundEvent[] soundEventArray2) {

  public SoundEvent getSoundEvent(int intValue) {
    return this.soundEventArray[Math.floorMod(intValue, this.soundEventArray.length)];
  }

  public SoundEvent getSoundEvent2(int intValue) {
    return this.soundEventArray2[Math.floorMod(intValue, this.soundEventArray2.length)];
  }
}
