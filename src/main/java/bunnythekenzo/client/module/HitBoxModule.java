package bunnythekenzo.client.module;

import java.util.function.Predicate;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import bunnythekenzo.client.FakeLootDropModule;
import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.ColorSetting;
import bunnythekenzo.client.setting.NumberSetting;

public final class HitBoxModule extends Module {
  public final NumberSetting expandSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Expand",
                  "Extra width on each horizontal side of the selection box",
                  0.5,
                  0.0,
                  2.0,
                  0.05,
                  " blocks"));
  public final BooleanSetting enableRenderSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Enable Render", "Draw the expanded selection boxes", true));
  public final NumberSetting verticalExpandSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Vertical Expand",
                  "Extra height above and below the selection box",
                  0.0,
                  0.0,
                  2.0,
                  0.05,
                  " blocks"));
  public final BooleanSetting playersSetting =
      (BooleanSetting)
          this.registerSetting(new BooleanSetting("Players", "Expand other players", true));
  public final BooleanSetting mobsSetting =
      (BooleanSetting)
          this.registerSetting(new BooleanSetting("Mobs", "Expand living mobs", false));
  public final BooleanSetting invisibleSetting =
      (BooleanSetting)
          this.registerSetting(new BooleanSetting("Invisible", "Include invisible targets", false));
  public final BooleanSetting teammatesSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Teammates", "Include players on your scoreboard team", false));
  public final BooleanSetting showOriginalSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Show Original", "Also draw the original box with a faint white outline", false));
  public final NumberSetting renderRangeSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Render Range",
                  "Maximum display distance; does not change interaction reach",
                  16.0,
                  4.0,
                  64.0,
                  1.0,
                  " blocks"));
  public final NumberSetting lineWidthSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting("Line Width", "Outline thickness", 1.5, 1.0, 4.0, 0.25));
  public final ColorSetting colorSetting =
      (ColorSetting)
          this.registerSetting(
              new ColorSetting("Color", "Expanded box color", -861043201).getColorSetting());
  public final ColorSetting targetColorSetting =
      (ColorSetting)
          this.registerSetting(
              new ColorSetting("Target Color", "Color of the selected entity", -9112146));
  public static String string;

  public HitBoxModule() {
    super(
        "HitBox",
        "Expands client selection boxes. Servers and anticheats can reject expanded hits.",
        ModuleCategory.COMBAT);
    this.showOriginalSetting.setVisibilityCondition(this.enableRenderSetting::getValue);
    this.renderRangeSetting.setVisibilityCondition(this.enableRenderSetting::getValue);
    this.lineWidthSetting.setVisibilityCondition(this.enableRenderSetting::getValue);
    this.colorSetting.setVisibilityCondition(this.enableRenderSetting::getValue);
    this.targetColorSetting.setVisibilityCondition(this.enableRenderSetting::getValue);
    this.teammatesSetting.setVisibilityCondition(this.playersSetting::getValue);
  }

  public boolean test(MinecraftClient minecraftClient) {
    FakeLootDropModule.ensureInitialized();
    if (this.isEnabled()
        && minecraftClient.world != null
        && minecraftClient.player != null
        && minecraftClient.player.isAlive()
        && !minecraftClient.player.isSpectator()
        && minecraftClient.getCameraEntity() == minecraftClient.player) {
      FreecamModule instance = FreecamModule.getInstance();
      FakeLootDropModule fakeLootDropModule = FakeLootDropModule.getFakeLootDropModule();
      return (instance == null || !instance.test())
          && (fakeLootDropModule == null || !fakeLootDropModule.equipmentPreview.test3());
    } else {
      return false;
    }
  }

  public boolean test(MinecraftClient minecraftClient, Entity entity) {
    FakeLootDropModule.ensureInitialized();
    if (minecraftClient.player != null
        && entity != minecraftClient.player
        && entity.getEntityWorld() == minecraftClient.world
        && !entity.isRemoved()
        && entity.isAlive()
        && entity.canHit()
        && !entity.isSpectator()
        && entity.getRootVehicle() != minecraftClient.player.getRootVehicle()
        && ((Boolean) this.invisibleSetting.getValue() || !entity.isInvisible())) {
      return entity instanceof PlayerEntity
          ? (Boolean) this.playersSetting.getValue()
              && ((Boolean) this.teammatesSetting.getValue()
                  || !minecraftClient.player.isTeammate(entity))
          : (Boolean) this.mobsSetting.getValue() && entity instanceof MobEntity;
    } else {
      return false;
    }
  }

  public Box getBox(Entity entity) {
    return entity
        .getBoundingBox()
        .expand(entity.getTargetingMargin())
        .expand(
            (Double) this.expandSetting.getValue(),
            (Double) this.verticalExpandSetting.getValue(),
            (Double) this.expandSetting.getValue());
  }

  public EntityHitResult getEntityHitResult(
      EntityHitResult entityHitResult,
      Entity entity,
      Vec3d vec3d,
      Vec3d vec3d2,
      Box box,
      Predicate predicate,
      double doubleValue) {
    FakeLootDropModule.ensureInitialized();

    MinecraftClient instance = MinecraftClient.getInstance();
    if (this.test(instance)
        && entity == instance.player
        && instance.currentScreen == null
        && ((Double) this.expandSetting.getValue() != 0.0
            || (Double) this.verticalExpandSetting.getValue() != 0.0)) {
      EntityHitResult entityHitResult2 = entityHitResult;
      double doubleValue2 =
          entityHitResult == null
              ? doubleValue
              : Math.min(doubleValue, vec3d.squaredDistanceTo(entityHitResult.getPos()));
      double doubleValue3 =
          instance.player.getEntityInteractionRange() * instance.player.getEntityInteractionRange();
      Box box2 =
          box.expand(
              (Double) this.expandSetting.getValue(),
              (Double) this.verticalExpandSetting.getValue(),
              (Double) this.expandSetting.getValue());

      for (Entity entity2 :
          (java.lang.Iterable<Entity>)
              (java.lang.Iterable<?>) (instance.world.getOtherEntities(entity, box2, predicate))) {
        if (this.test(instance, entity2)
            && !(entity2.getBoundingBox().squaredMagnitude(vec3d) > doubleValue3)
            && instance.player.canSee(entity2)) {
          Box box3 = this.getBox(entity2);
          Vec3d vec3d3 = box3.contains(vec3d) ? vec3d : box3.raycast(vec3d, vec3d2).orElse(null);
          if (vec3d3 != null) {
            double doubleValue4 = vec3d.squaredDistanceTo(vec3d3);
            if (doubleValue4 < doubleValue2) {
              doubleValue2 = doubleValue4;
              entityHitResult2 = new EntityHitResult(entity2, vec3d3);
            }
          }
        }
      }

      return entityHitResult2;
    } else {
      return entityHitResult;
    }
  }

  @Override
  public void onDisable() {
    FakeLootDropModule.ensureInitialized();

    MinecraftClient instance = MinecraftClient.getInstance();
    if (instance.world != null && instance.player != null && instance.gameRenderer != null) {
      instance.gameRenderer.updateCrosshairTarget(1.0F);
    }
  }

  public static void setString(String string2) {
    string = string2;
  }

  public static String getString() {
    return string;
  }

  static {
    setString(null);
  }
}
