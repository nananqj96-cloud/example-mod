package bunnythekenzo.client.module;

import java.util.ArrayDeque;
import java.util.Deque;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import bunnythekenzo.client.ArgbColors;
import bunnythekenzo.client.FakeLootDropModule;
import bunnythekenzo.client.HitParticlesSupport;
import bunnythekenzo.client.HitParticlesSupport2;
import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.ChoiceSetting;
import bunnythekenzo.client.setting.ColorSetting;
import bunnythekenzo.client.setting.NumberSetting;

public class HitParticlesModule extends Module {
  public static final int count = 0;
  public static final int count2 = 1;

  public final ChoiceSetting styleSetting =
      (ChoiceSetting)
          this.registerSetting(
              new ChoiceSetting(
                  "Style",
                  "Burst style",
                  "Sparks",
                  new String[] {"Sparks", "Hearts", "Lightning", "67"}));
  public final NumberSetting amountSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting("Amount", "Particles spawned per hit", 14.0, 4.0, 40.0, 1.0));
  public final NumberSetting sizeSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting("Size", "Particle scale", 1.0, 0.3, 3.0, 0.1, "x"));
  public final NumberSetting lifetimeSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting("Lifetime", "How long the burst lingers", 0.7, 0.3, 2.0, 0.1, "s"));
  public final NumberSetting spreadSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting("Spread", "How far particles fly out", 1.0, 0.3, 2.5, 0.1, "x"));
  public final ColorSetting colorSetting =
      (ColorSetting)
          this.registerSetting(
              new ColorSetting("Color", "Particle tint; follows the theme until edited", -49508)
                  .getColorSetting());
  public final BooleanSetting rainbowSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Rainbow", "Cycle each burst through the rainbow", false));
  public final NumberSetting glowSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting("Glow", "Soft outer halo intensity", 65.0, 0.0, 100.0, 5.0, "%"));
  public final BooleanSetting shockwaveSetting =
      (BooleanSetting)
          this.registerSetting(new BooleanSetting("Shockwave", "Expanding ring on each hit", true));
  public final Deque deque = new ArrayDeque();
  public final Deque deque2 = new ArrayDeque();

  public static String string;

  public static final long timestamp = 3500L;

  public HitParticlesModule() {
    super("HitParticles", "Themed particle bursts when you hit an entity", ModuleCategory.VISUALS);
  }

  public Deque getDeque() {
    return this.deque;
  }

  public Deque getDeque2() {
    return this.deque2;
  }

  @Override
  public void onDisable() {
    this.update4();
  }

  public void update4() {
    this.deque.clear();
    this.deque2.clear();
  }

  public int computeInt() {
    FakeLootDropModule.ensureInitialized();
    if ((Boolean) this.rainbowSetting.getValue()) {
      float floatValue = (float) (System.currentTimeMillis() % timestamp) / 3500.0F * 360.0F;
      return ArgbColors.fromHsv(floatValue, 0.85F, 1.0F) & 16777215;
    } else {
      return this.colorSetting.getInteger() & 16777215;
    }
  }

  public void apply(Entity entity) {
    FakeLootDropModule.ensureInitialized();
    if (this.isEnabled() && entity != null) {
      double x = entity.getX();
      double doubleValue = entity.getY() + entity.getHeight() * 0.6;
      double z = entity.getZ();
      this.render(x, doubleValue, z);
    }
  }

  public void render(double doubleValue, double doubleValue2, double doubleValue3) {
    FakeLootDropModule.ensureInitialized();

    MinecraftClient instance = MinecraftClient.getInstance();
    if (instance.world != null) {
      Random random = instance.world.random;
      int int2 = this.computeInt2();
      int value2 = this.computeInt();
      int value3 = this.amountSetting.intValue();
      float value4 = this.lifetimeSetting.floatValue();
      float value5 = this.spreadSetting.floatValue();
      float value6 = this.sizeSetting.floatValue();
      long longValue = System.nanoTime();

      for (int index = 0; index < value3; index++) {
        this.render(
            random,
            int2,
            value2,
            doubleValue,
            doubleValue2,
            doubleValue3,
            value4,
            value5,
            value6,
            longValue);
      }

      if ((Boolean) this.shockwaveSetting.getValue()) {
        this.deque2.addLast(
            new HitParticlesSupport(
                doubleValue, doubleValue2, doubleValue3, value2, int2, longValue));

        while (this.deque2.size() > 24) {
          this.deque2.removeFirst();
        }
      }

      while (this.deque.size() > 600) {
        this.deque.removeFirst();
      }
    }
  }

  public void render(
      Random random,
      int intValue,
      int intValue2,
      double doubleValue,
      double doubleValue2,
      double doubleValue3,
      float floatValue,
      float floatValue2,
      float floatValue3,
      long longValue) {
    FakeLootDropModule.ensureInitialized();

    double doubleValue4 = random.nextDouble() * Math.PI * 2.0;
    double doubleValue5 = 2.0 * random.nextDouble() - 1.0;
    double doubleValue6 = Math.sqrt(Math.max(0.0, 1.0 - doubleValue5 * doubleValue5));
    float floatValue4 = (float) (doubleValue6 * Math.cos(doubleValue4));
    float floatValue5 = (float) doubleValue5;
    float floatValue6 = (float) (doubleValue6 * Math.sin(doubleValue4));
    float floatValue7 = floatValue * (0.75F + random.nextFloat() * 0.25F);
    float floatValue8 = floatValue3 * (0.7F + random.nextFloat() * 0.6F);
    float floatValue9;
    float floatValue10;
    switch (intValue) {
      case 1:
        floatValue4 *= 0.5F;
        floatValue6 *= 0.5F;
        floatValue5 = 0.4F + Math.abs(floatValue5) * 0.5F;
        floatValue9 = (1.6F + random.nextFloat() * 1.0F) * floatValue2;
        floatValue10 = 1.2F;
        floatValue7 = floatValue * (1.0F + random.nextFloat() * 0.3F);
        break;
      case 2:
        floatValue5 *= 0.25F;
        float floatValue11 =
            MathHelper.sqrt(
                Math.max(1.0E-4F, floatValue4 * floatValue4 + floatValue6 * floatValue6));
        floatValue4 /= floatValue11;
        floatValue6 /= floatValue11;
        floatValue9 = (5.0F + random.nextFloat() * 3.0F) * floatValue2;
        floatValue10 = 0.6F;
        floatValue7 = floatValue * (0.45F + random.nextFloat() * 0.25F);
        break;
      case 3:
        floatValue5 = 0.12F + floatValue5 * 0.35F;
        floatValue9 = (1.9F + random.nextFloat() * 1.4F) * floatValue2;
        floatValue10 = 1.3F;
        floatValue7 = floatValue * (1.0F + random.nextFloat() * 0.25F);
        break;
      default:
        floatValue5 = floatValue5 * 0.7F + 0.3F;
        floatValue9 = (4.0F + random.nextFloat() * 3.5F) * floatValue2;
        floatValue10 = 6.5F;
    }

    float floatValue12 = floatValue4 * floatValue9;
    float floatValue13 = floatValue5 * floatValue9;
    float floatValue14 = floatValue6 * floatValue9;
    float floatValue15 = random.nextFloat() * (float) (Math.PI * 2);
    float floatValue16 = (random.nextFloat() - 0.5F) * 8.0F;
    this.deque.addLast(
        new HitParticlesSupport2(
            doubleValue,
            doubleValue2,
            doubleValue3,
            floatValue12,
            floatValue13,
            floatValue14,
            intValue2,
            intValue,
            floatValue8,
            floatValue15,
            floatValue16,
            floatValue10,
            floatValue7,
            longValue));
  }

  public int computeInt2() {
    FakeLootDropModule.ensureInitialized();
    if (this.styleSetting.isSelected("Hearts")) {
      return 1;
    } else if (this.styleSetting.isSelected("Lightning")) {
      return 2;
    } else {
      return this.styleSetting.isSelected("67") ? 3 : 0;
    }
  }

  public float computeFloat() {
    return this.glowSetting.floatValue() / 100.0F;
  }

  public static void setString(String string2) {
    string = string2;
  }

  public static String getString() {
    return string;
  }

  static {
    setString("DWZcs");
  }
}
