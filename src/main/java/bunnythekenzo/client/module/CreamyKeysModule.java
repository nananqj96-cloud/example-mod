package bunnythekenzo.client.module;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvent;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.ClientSounds;
import bunnythekenzo.client.ClientSoundsData;
import bunnythekenzo.client.CreamyKeysData;
import bunnythekenzo.client.FakeLootDropModule;
import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.ChoiceSetting;
import bunnythekenzo.client.setting.NumberSetting;

public class CreamyKeysModule extends Module {
  public final ChoiceSetting soundStyleSetting =
      (ChoiceSetting)
          this.registerSetting(
              new ChoiceSetting(
                  "Sound Style",
                  "Choose the keyboard recording",
                  "Creamy",
                  new String[] {"Creamy", "Clacky", "Thocky", "Honey Creamy"}));
  public final BooleanSetting keyboardSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Keyboard", "Play keyboard sounds when pressing and releasing keys", true));
  public final NumberSetting keyboardVolumeSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Keyboard Volume",
                  "Volume of keyboard presses and releases",
                  70.0,
                  0.0,
                  100.0,
                  5.0,
                  "%"));
  public final NumberSetting releaseVolumeSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Release Volume",
                  "How loudly the keyboard returns when you let go",
                  85.0,
                  0.0,
                  100.0,
                  5.0,
                  "%"));
  public final BooleanSetting mouseSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Mouse", "Play distinct left, right and middle mouse clicks", true));
  public final NumberSetting mouseVolumeSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Mouse Volume", "Volume of mouse-button clicks", 65.0, 0.0, 100.0, 5.0, "%"));
  public final BooleanSetting scrollWheelSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Scroll Wheel", "Play a soft notch while scrolling up or down", true));
  public final NumberSetting scrollVolumeSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Scroll Volume", "Volume of scroll-wheel notches", 50.0, 0.0, 100.0, 5.0, "%"));
  public final NumberSetting pitchSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Pitch",
                  "Lower for a deeper board, higher for a brighter board",
                  100.0,
                  88.0,
                  112.0,
                  1.0,
                  "%"));
  public final NumberSetting naturalVariationSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Natural Variation",
                  "Tiny pitch differences without losing each key's identity",
                  2.0,
                  0.0,
                  8.0,
                  1.0,
                  "%"));
  public double value;
  public long timestamp;
  public final Map map = new HashMap();
  public final Map map2 = new HashMap();
  public static boolean enabled;

  public static final long timestamp2 = 9000000L;

  public CreamyKeysModule() {
    super(
        "Creamy Keys",
        "Creamy, Clacky, Thocky and Honey Creamy press and release sounds",
        ModuleCategory.getInstance());
    this.keyboardVolumeSetting.setVisibilityCondition(this.keyboardSetting::getValue);
    this.releaseVolumeSetting.setVisibilityCondition(this.keyboardSetting::getValue);
    this.mouseVolumeSetting.setVisibilityCondition(this.mouseSetting::getValue);
    this.scrollVolumeSetting.setVisibilityCondition(this.scrollWheelSetting::getValue);
  }

  public void apply(int intValue, int intValue2, int intValue3) {
    FakeLootDropModule.ensureInitialized();
    if (this.isEnabled()
        && (Boolean) this.keyboardSetting.getValue()
        && MinecraftClient.getInstance().isWindowFocused()) {
      int intValue4 = intValue == -1 ? -intValue2 - 2 : intValue;
      if (intValue3 == 0) {
        CreamyKeysData creamyKeysData = (CreamyKeysData) this.map.remove(intValue4);
        if (creamyKeysData != null) {
          this.render(
              creamyKeysData.soundEvent(),
              this.keyboardVolumeSetting,
              creamyKeysData.amount(),
              this.releaseVolumeSetting.floatValue() / 100.0F);
        }
      } else if (intValue3 == 1 && !this.map.containsKey(intValue4)) {
        ClientSoundsData clientSoundsData = this.getClientSoundsData();
        int intValue5 = computeInt(intValue == -1 ? intValue4 : intValue);
        float value2 = this.computeFloat(computeFloat(intValue) * computeFloat2(intValue4), true);
        this.map.put(
            intValue4, new CreamyKeysData(clientSoundsData.getSoundEvent2(intValue5), value2));
        this.render(
            clientSoundsData.getSoundEvent(intValue5), this.keyboardVolumeSetting, value2, 1.0F);
      }
    } else {
      this.map.clear();
    }
  }

  public void apply(int intValue, int intValue2) {
    FakeLootDropModule.ensureInitialized();
    if (!this.isEnabled()
        || !(Boolean) this.mouseSetting.getValue()
        || !MinecraftClient.getInstance().isWindowFocused()) {
      this.map2.clear();
    } else if (intValue2 == 0) {
      CreamyKeysData creamyKeysData = (CreamyKeysData) this.map2.remove(intValue);
      if (creamyKeysData != null) {
        this.render(
            creamyKeysData.soundEvent(), this.mouseVolumeSetting, creamyKeysData.amount(), 1.0F);
      }
    } else if (intValue2 == 1 && !this.map2.containsKey(intValue)) {
      SoundEvent soundEvent =
          switch (intValue) {
            case 1 -> ClientSounds.soundEvent16;
            case 2 -> ClientSounds.soundEvent17;
            default -> ClientSounds.soundEvent15;
          };

      SoundEvent soundEvent2 =
          switch (intValue) {
            case 1 -> ClientSounds.soundEvent21;
            case 2 -> ClientSounds.soundEvent22;
            default -> ClientSounds.soundEvent20;
          };
      float value2 = this.computeFloat(computeFloat2(intValue + 700), false);
      this.map2.put(intValue, new CreamyKeysData(soundEvent2, value2));
      this.render(soundEvent, this.mouseVolumeSetting, value2, 1.0F);
    }
  }

  public ClientSoundsData getClientSoundsData() {
    FakeLootDropModule.ensureInitialized();

    return switch ((String) this.soundStyleSetting.getValue()) {
      case "Clacky" -> ClientSounds.clientSoundsData2;
      case "Thocky" -> ClientSounds.clientSoundsData3;
      case "Honey Creamy" -> ClientSounds.clientSoundsData4;
      default -> ClientSounds.clientSoundsData;
    };
  }

  public static int computeInt(int intValue) {
    FakeLootDropModule.ensureInitialized();
    if (intValue >= 65 && intValue <= 90) {
      return intValue - 65;
    } else {
      return intValue >= 48 && intValue <= 57
          ? 26 + intValue - 48
          : Math.floorMod(intValue * 17, 12);
    }
  }

  public static float computeFloat(int intValue) {
    FakeLootDropModule.ensureInitialized();
    if (intValue == 32) {
      return 0.86F;
    } else if (intValue == 257 || intValue == 335) {
      return 0.93F;
    } else if (intValue != 259 && intValue != 261) {
      return test2(intValue) ? 0.97F : 1.0F;
    } else {
      return 0.92F;
    }
  }

  public void apply(double doubleValue) {
    FakeLootDropModule.ensureInitialized();
    if (this.isEnabled()
        && (Boolean) this.scrollWheelSetting.getValue()
        && Double.isFinite(doubleValue)
        && doubleValue != 0.0) {
      this.value += doubleValue;
      if (!(Math.abs(this.value) < 0.75)) {
        boolean flag = this.value > 0.0;
        this.value =
            this.value - Math.copySign(Math.max(1.0, Math.floor(Math.abs(this.value))), this.value);
        long timestamp3 = System.nanoTime();
        if (timestamp3 - this.timestamp >= timestamp2) {
          this.timestamp = timestamp3;
          this.render(
              flag ? ClientSounds.soundEvent18 : ClientSounds.soundEvent19,
              this.scrollVolumeSetting,
              this.computeFloat(1.0F, false),
              1.0F);
        }
      }
    }
  }

  @Override
  public void onDisable() {
    this.map.clear();
    this.map2.clear();
    this.value = 0.0;
    this.timestamp = 0L;
  }

  @Override
  public void onTick() {
    FakeLootDropModule.ensureInitialized();

    boolean windowFocused = MinecraftClient.getInstance().isWindowFocused();
    if (!windowFocused || !(Boolean) this.keyboardSetting.getValue()) {
      this.map.clear();
    }

    if (!windowFocused || !(Boolean) this.mouseSetting.getValue()) {
      this.map2.clear();
    }

    if (!windowFocused || !(Boolean) this.scrollWheelSetting.getValue()) {
      this.value = 0.0;
      this.timestamp = 0L;
    }
  }

  public void render(
      SoundEvent soundEvent, NumberSetting numberSetting7, float floatValue, float floatValue2) {
    FakeLootDropModule.ensureInitialized();
    float floatValue3 =
        ClientInitializer.getSoundSettings() == null
            ? 1.0F
            : ClientInitializer.getSoundSettings().computeFloat();
    float floatValue4 = numberSetting7.floatValue() / 100.0F * floatValue3 * floatValue2;
    if (!(floatValue4 <= 0.001F)) {
      MinecraftClient.getInstance()
          .getSoundManager()
          .play(PositionedSoundInstance.ui(soundEvent, floatValue, floatValue4));
    }
  }

  public float computeFloat(float floatValue, boolean flag) {
    FakeLootDropModule.ensureInitialized();

    float floatValue2 = 1.0F;
    if (flag) {
      float floatValue3 = this.naturalVariationSetting.floatValue() / 100.0F;
      if (floatValue3 > 0.0F) {
        floatValue2 = 1.0F + ThreadLocalRandom.current().nextFloat(-floatValue3, floatValue3);
      }
    }

    return Math.clamp(
        this.pitchSetting.floatValue() / 100.0F * floatValue * floatValue2, 0.5F, 2.0F);
  }

  public static float computeFloat2(int intValue) {
    return 0.982F + Math.floorMod(intValue * 13, 19) * 0.002F;
  }

  public static boolean test2(int intValue) {
    FakeLootDropModule.ensureInitialized();
    return intValue == 340
        || intValue == 344
        || intValue == 341
        || intValue == 345
        || intValue == 342
        || intValue == 346
        || intValue == 343
        || intValue == 347
        || intValue == 280
        || intValue == 258
        || intValue == 256;
  }

  public static void setOpaqueGuard(boolean enabled2) {
    enabled = enabled2;
  }

  public static boolean isEnabled2() {
    return enabled;
  }

  public static boolean test() {
    boolean flag = isEnabled2();
    return !flag;
  }

  static {
    setOpaqueGuard(true);
  }
}
