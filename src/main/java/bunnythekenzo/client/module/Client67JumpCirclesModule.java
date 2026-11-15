package bunnythekenzo.client.module;

import java.util.ArrayDeque;
import java.util.Deque;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.util.math.random.Random;
import bunnythekenzo.client.Client67JumpCirclesSupport;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.FakeLootDropModule;
import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.ColorSetting;
import bunnythekenzo.client.setting.NumberSetting;

public class Client67JumpCirclesModule extends Module {
  public final NumberSetting sizeSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Size", "Decal scale (1.0 = one block wide)", 1.0, 0.5, 3.0, 0.1, "x"));
  public final ColorSetting colorSetting =
      (ColorSetting)
          this.registerSetting(
              new ColorSetting("Color", "Decal color; follows the theme until edited", -38476)
                  .getColorSetting());
  public final NumberSetting lifetimeSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Lifetime", "How long a 67 stays visible", 1.5, 0.5, 4.0, 0.1, "s"));
  public final BooleanSetting rainbowSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Rainbow", "Cycle the color through the rainbow", false));
  public final BooleanSetting shockwaveSetting =
      (BooleanSetting)
          this.registerSetting(new BooleanSetting("Shockwave", "Expanding ring on spawn", true));
  public final BooleanSetting particlesSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Particles", "Drifting dust motes on jump", true));
  public final NumberSetting maxSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Max", "Max simultaneous decals (oldest drops first)", 10.0, 1.0, 30.0, 1.0));
  public final Deque deque = new ArrayDeque();
  public int count;
  public static ClientInitializer[] clientInitializerArray;

  public Client67JumpCirclesModule() {
    super(
        "67JumpCircles",
        "Stamps a glowing 67 on the ground when you jump",
        ModuleCategory.getInstance());
  }

  public Deque getDeque() {
    return this.deque;
  }

  @Override
  public void onDisable() {
    this.update4();
  }

  public void update4() {
    this.deque.clear();
  }

  public int computeInt() {
    return this.colorSetting.getInteger() & 16777215;
  }

  public void apply(ClientPlayerEntity clientPlayerEntity) {
    FakeLootDropModule.ensureInitialized();
    if (this.isEnabled()) {
      double x = clientPlayerEntity.getX();
      double y = clientPlayerEntity.getY();
      double z = clientPlayerEntity.getZ();
      float floatValue = 0.01F + this.count % 8 * 0.001F;
      this.count++;
      this.deque.addLast(
          new Client67JumpCirclesSupport(
              x, y, z, clientPlayerEntity.getYaw(), floatValue, System.nanoTime()));
      int intValue = Math.max(1, this.maxSetting.intValue());

      while (this.deque.size() > intValue) {
        this.deque.removeFirst();
      }

      if ((Boolean) this.particlesSetting.getValue()) {
        this.apply2(clientPlayerEntity);
      }
    }
  }

  public void apply2(ClientPlayerEntity clientPlayerEntity) {
    FakeLootDropModule.ensureInitialized();

    Random random = clientPlayerEntity.getRandom();
    int value2 = this.computeInt();

    for (int index = 0; index < 10; index++) {
      double doubleValue = random.nextDouble() * Math.PI * 2.0;
      double doubleValue2 = 0.15 + random.nextDouble() * 0.45;
      clientPlayerEntity
          .getEntityWorld()
          .addParticleClient(
              new DustParticleEffect(value2, 0.9F),
              clientPlayerEntity.getX() + Math.cos(doubleValue) * doubleValue2,
              clientPlayerEntity.getY() + 0.05,
              clientPlayerEntity.getZ() + Math.sin(doubleValue) * doubleValue2,
              0.0,
              0.6 + random.nextDouble() * 0.4,
              0.0);
    }
  }

  public static void setClientInitializerArray(ClientInitializer[] clientInitializerArray2) {
    clientInitializerArray = clientInitializerArray2;
  }

  public static ClientInitializer[] getClientInitializerArray() {
    return clientInitializerArray;
  }

  static {
    setClientInitializerArray(null);
  }
}
