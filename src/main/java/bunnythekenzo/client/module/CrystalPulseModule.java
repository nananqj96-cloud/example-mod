package bunnythekenzo.client.module;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import bunnythekenzo.client.AnimationPreferences;
import bunnythekenzo.client.CrystalPulseData;
import bunnythekenzo.client.CrystalPulseData2;
import bunnythekenzo.client.CrystalPulseSupport2;
import bunnythekenzo.client.FakeLootDropModule;
import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.ChoiceSetting;
import bunnythekenzo.client.setting.NumberSetting;
import bunnythekenzo.client.setting.Setting;

public final class CrystalPulseModule extends Module {

  public final ChoiceSetting toneSetting =
      (ChoiceSetting)
          this.registerSetting(
              new ChoiceSetting(
                  "Tone",
                  "Curated two-tone light with a pearl-white core",
                  "Opal",
                  new String[] {"Opal", "Amethyst", "Glacier", "Rose", "Ember"}));
  public final ChoiceSetting styleSetting =
      (ChoiceSetting)
          this.registerSetting(
              new ChoiceSetting(
                  "Style",
                  "Halo adds fine arcs; Orbit wraps the crystal; Minimal keeps one clean ring",
                  "Halo",
                  new String[] {"Halo", "Orbit", "Minimal"}));
  public final NumberSetting intensitySetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Intensity",
                  "Brightness of the light without covering your fight",
                  75.0,
                  15.0,
                  100.0,
                  5.0,
                  "%"));
  public final NumberSetting sizeSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Size", "Scale of the crystal's light rings", 100.0, 70.0, 140.0, 5.0, "%"));
  public final BooleanSetting crystalHalosSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Crystal Halos", "Soft rings mark nearby end crystals", true));
  public final BooleanSetting crosshairFocusSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Crosshair Focus", "Gently brighten the crystal under your crosshair", true));
  public final BooleanSetting impactWavesSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Impact Waves", "A short double ripple on a confirmed crystal explosion", true));
  public final BooleanSetting cleanExplosionsSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Clean Explosions",
                  "Replace matched crystal explosion clouds with the light ripple",
                  true));
  public final NumberSetting fadeTimeSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Fade Time", "How quickly each impact dissolves", 0.65, 0.25, 1.2, 0.05, "s"));
  public final NumberSetting waveSizeSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Wave Size",
                  "Visual ripple radius; this is not an explosion damage indicator",
                  2.2,
                  1.0,
                  3.5,
                  0.1,
                  "m"));
  public final BooleanSetting crystalShardsSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Crystal Shards", "A few tiny diamond sparks drift from each impact", true));
  public final NumberSetting renderDistanceSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Render Distance",
                  "Only nearby crystals create effects",
                  24.0,
                  8.0,
                  48.0,
                  2.0,
                  "m"));
  public final Map map = new LinkedHashMap();
  public final List list = new ArrayList();
  public ClientWorld clientWorld;
  public Vec3d vec3d;
  public double value;
  public double value2;
  public double value3;
  public double value4;
  public static int[] countArray;

  public CrystalPulseModule() {
    super(
        "Crystal Pulse",
        "Pearlescent crystal halos & crisp, fading impact waves for Crystal PvP",
        ModuleCategory.VISUALS);
    this.styleSetting.setVisibilityCondition(this.crystalHalosSetting::getValue);
    this.sizeSetting.setVisibilityCondition(this.crystalHalosSetting::getValue);
    this.crosshairFocusSetting.setVisibilityCondition(this.crystalHalosSetting::getValue);
    this.fadeTimeSetting.setVisibilityCondition(this.impactWavesSetting::getValue);
    this.cleanExplosionsSetting.setVisibilityCondition(this.impactWavesSetting::getValue);
    this.waveSizeSetting.setVisibilityCondition(this.impactWavesSetting::getValue);
    this.crystalShardsSetting.setVisibilityCondition(this.impactWavesSetting::getValue);
  }

  public String getString(Setting setting) {
    if (setting == this.impactWavesSetting
        || setting == this.cleanExplosionsSetting
        || setting == this.fadeTimeSetting
        || setting == this.waveSizeSetting
        || setting == this.crystalShardsSetting) {
      return "Impact";
    } else {
      return setting != this.crystalHalosSetting
              && setting != this.crosshairFocusSetting
              && setting != this.renderDistanceSetting
          ? "Appearance"
          : "Visibility";
    }
  }

  public boolean test() {
    FakeLootDropModule.ensureInitialized();

    MinecraftClient instance = MinecraftClient.getInstance();
    if (!this.isEnabled()) {
      return false;
    }

    if (this.clientWorld != instance.world) {
      this.update4();
      this.clientWorld = instance.world;
    }

    if (instance.player != null && this.clientWorld != null) {
      if (this.vec3d != null
          && instance.player.getEntityPos().squaredDistanceTo(this.vec3d) > 64.0) {
        this.update4();
        this.clientWorld = instance.world;
      }

      this.vec3d = instance.player.getEntityPos();
      return true;
    } else {
      return false;
    }
  }

  public void apply(Entity entity) {
    FakeLootDropModule.ensureInitialized();
    if (entity instanceof EndCrystalEntity endCrystalEntity
        && this.test()
        && entity.getEntityWorld() == this.clientWorld) {
      this.apply(endCrystalEntity);
    }
  }

  public void apply2(Entity entity) {
    FakeLootDropModule.ensureInitialized();
    if (entity instanceof EndCrystalEntity endCrystalEntity
        && this.test()
        && entity.getEntityWorld() == this.clientWorld) {
      this.apply(endCrystalEntity);
      CrystalPulseSupport2 crystalPulseSupport2 =
          (CrystalPulseSupport2) this.map.get(endCrystalEntity.getId());
      if (crystalPulseSupport2 != null) {
        crystalPulseSupport2.value2 = this.value;
      }
    }
  }

  public void apply(EndCrystalEntity endCrystalEntity) {
    FakeLootDropModule.ensureInitialized();

    ClientPlayerEntity player = MinecraftClient.getInstance().player;
    if (player != null
        && !(endCrystalEntity.squaredDistanceTo(player)
            > (Double) this.renderDistanceSetting.getValue()
                * (Double) this.renderDistanceSetting.getValue())) {
      CrystalPulseSupport2 crystalPulseSupport2 =
          (CrystalPulseSupport2) this.map.get(endCrystalEntity.getId());
      if (crystalPulseSupport2 == null
          || crystalPulseSupport2.endCrystalEntity != endCrystalEntity) {
        if (this.map.size() >= 64) {
          this.map.remove(this.map.keySet().iterator().next());
        }

        this.map.put(
            endCrystalEntity.getId(), new CrystalPulseSupport2(endCrystalEntity, this.value));
      }
    }
  }

  public boolean test(Vec3d vec3d2) {
    FakeLootDropModule.ensureInitialized();
    if (this.test() && (Boolean) this.impactWavesSetting.getValue()) {
      MinecraftClient instance = MinecraftClient.getInstance();
      if (vec3d2.squaredDistanceTo(instance.player.getEntityPos())
          > (Double) this.renderDistanceSetting.getValue()
              * (Double) this.renderDistanceSetting.getValue()) {
        return false;
      }

      CrystalPulseSupport2 crystalPulseSupport2 = null;

      for (CrystalPulseSupport2 crystalPulseSupport22 :
          (java.lang.Iterable<CrystalPulseSupport2>) (java.lang.Iterable<?>) (this.map.values())) {
        if (!(this.value - crystalPulseSupport22.value2 > 0.3)
            && !(crystalPulseSupport22.vec3d.squaredDistanceTo(vec3d2) > 0.36)
            && (crystalPulseSupport2 == null
                || crystalPulseSupport22.value >= crystalPulseSupport2.value)) {
          crystalPulseSupport2 = crystalPulseSupport22;
        }
      }

      if (crystalPulseSupport2 == null) {
        return false;
      }

      if (crystalPulseSupport2.enabled) {
        return true;
      }

      crystalPulseSupport2.enabled = true;
      if (this.list.size() >= 12) {
        this.list.removeFirst();
      }

      this.list.add(
          new CrystalPulseData(
              vec3d2.add(0.0, 0.055, 0.0),
              this.value,
              (Double) this.fadeTimeSetting.getValue(),
              crystalPulseSupport2.endCrystalEntity.getId()));
      return true;
    } else {
      return false;
    }
  }

  @Override
  public void onTick() {
    FakeLootDropModule.ensureInitialized();
    if (this.test()) {
      MinecraftClient instance = MinecraftClient.getInstance();
      if (!instance.isPaused()) {
        this.value2 = this.value;
        this.value4 = this.value3;
        this.value += 0.05;
        if (AnimationPreferences.isEnabled()) {
          this.value3 += 0.05;
        }

        double doubleValue = (Double) this.renderDistanceSetting.getValue();
        this.clientWorld
            .getNonSpectatingEntities(
                EndCrystalEntity.class, instance.player.getBoundingBox().expand(doubleValue))
            .stream()
            .filter(
                endCrystalEntity -> {
                  FakeLootDropModule.ensureInitialized();
                  return endCrystalEntity.squaredDistanceTo(instance.player)
                      <= doubleValue * doubleValue;
                })
            .sorted(
                Comparator.comparingDouble(
                    endCrystalEntity2 -> {
                      return endCrystalEntity2.squaredDistanceTo(instance.player);
                    }))
            .limit(64L)
            .forEach(this::apply);
        Iterator iterator = this.map.values().iterator();

        while (iterator.hasNext()) {
          CrystalPulseSupport2 crystalPulseSupport2 = (CrystalPulseSupport2) iterator.next();
          crystalPulseSupport2.vec3d2 = crystalPulseSupport2.vec3d;
          crystalPulseSupport2.vec3d = crystalPulseSupport2.endCrystalEntity.getEntityPos();
          if (crystalPulseSupport2.endCrystalEntity.isRemoved()
              && Double.isInfinite(crystalPulseSupport2.value2)) {
            crystalPulseSupport2.value2 = this.value;
          }

          if (this.value - crystalPulseSupport2.value2 > 0.3
              || crystalPulseSupport2.vec3d.squaredDistanceTo(instance.player.getEntityPos())
                  > doubleValue * doubleValue) {
            iterator.remove();
          }
        }

        this.list.removeIf(
            value5 -> {
              FakeLootDropModule.ensureInitialized();
              return this.value - ((bunnythekenzo.client.CrystalPulseData) (value5)).value()
                      > ((bunnythekenzo.client.CrystalPulseData) (value5)).value2()
                  || ((bunnythekenzo.client.CrystalPulseData) (value5))
                          .vec3d()
                          .squaredDistanceTo(instance.player.getEntityPos())
                      > doubleValue * doubleValue;
            });
        if (!(Boolean) this.impactWavesSetting.getValue()) {
          this.list.clear();
        }
      }
    }
  }

  public List halos(float floatValue) {
    FakeLootDropModule.ensureInitialized();
    if (!(Boolean) this.crystalHalosSetting.getValue()) {
      return List.of();
    }

    MinecraftClient instance = MinecraftClient.getInstance();
    return instance.player != null && this.clientWorld == instance.world
        ? this.map.values().stream()
            .filter(
                value5 -> {
                  FakeLootDropModule.ensureInitialized();
                  return !((bunnythekenzo.client.CrystalPulseSupport2) (value5))
                          .endCrystalEntity.isRemoved()
                      && Double.isInfinite(((bunnythekenzo.client.CrystalPulseSupport2) (value5)).value2)
                      && !((bunnythekenzo.client.CrystalPulseSupport2) (value5)).enabled;
                })
            .sorted(
                Comparator.comparingDouble(
                    value6 -> {
                      return ((bunnythekenzo.client.CrystalPulseSupport2) (value6))
                          .vec3d.squaredDistanceTo(instance.player.getEntityPos());
                    }))
            .limit(12L)
            .map(
                value7 -> {
                  return new CrystalPulseData2(
                      ((bunnythekenzo.client.CrystalPulseSupport2) (value7))
                          .vec3d2.lerp(
                              ((bunnythekenzo.client.CrystalPulseSupport2) (value7)).vec3d, floatValue),
                      Math.clamp(
                          (this.computeDouble(floatValue)
                                  - ((bunnythekenzo.client.CrystalPulseSupport2) (value7)).value)
                              / 0.18,
                          0.0,
                          1.0),
                      ((bunnythekenzo.client.CrystalPulseSupport2) (value7)).endCrystalEntity.getId());
                })
            .toList()
        : List.of();
  }

  public double computeDouble(float floatValue) {
    return MathHelper.lerp(floatValue, this.value2, this.value);
  }

  public double computeDouble2(float floatValue) {
    return AnimationPreferences.isEnabled()
        ? MathHelper.lerp(floatValue, this.value4, this.value3)
        : this.value3;
  }

  public List getList2() {
    return List.copyOf(this.list);
  }

  public int computeInt() {
    return this.map.size();
  }

  public boolean test(ClientWorld clientWorld2) {
    return this.clientWorld == clientWorld2;
  }

  @Override
  public void onEnable() {
    this.update4();
    this.onTick();
  }

  @Override
  public void onDisable() {
    this.update4();
  }

  public void update4() {
    this.map.clear();
    this.list.clear();
    this.clientWorld = null;
    this.vec3d = null;
    this.value = this.value2 = this.value3 = this.value4 = 0.0;
  }

  public static void setCountArray(int[] countArray2) {
    countArray = countArray2;
  }

  public static int[] getCountArray() {
    return countArray;
  }

  static {
    setCountArray(null);
  }
}
