package bunnythekenzo.client.module;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;
import bunnythekenzo.client.ArgbColors;
import bunnythekenzo.client.ColorPalette;
import bunnythekenzo.client.FakeLootDropModule;
import bunnythekenzo.client.TargetESPSupport;
import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.ChoiceSetting;
import bunnythekenzo.client.setting.ColorSetting;
import bunnythekenzo.client.setting.NumberSetting;

public final class TargetESPModule extends Module {
  public final ChoiceSetting styleSetting =
      (ChoiceSetting)
          this.registerSetting(
              new ChoiceSetting(
                  "Style",
                  "3D shape around the targeted player",
                  "Orbit",
                  new String[] {"Orbit", "Helix", "Scan"}));
  public final NumberSetting rangeSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Range",
                  "Maximum targeting distance in blocks; does not change attack reach",
                  6.0,
                  1.0,
                  16.0,
                  0.5,
                  " blocks"));
  public final NumberSetting holdTimeSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Hold Time",
                  "Keep the last target briefly after looking away",
                  0.6,
                  0.0,
                  3.0,
                  0.1,
                  "s"));
  public final NumberSetting fadeTimeSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Fade Time", "How long the effect takes to fade out", 0.35, 0.1, 1.0, 0.05, "s"));
  public final ChoiceSetting colorModeSetting =
      (ChoiceSetting)
          this.registerSetting(
              new ChoiceSetting(
                  "Color Mode",
                  "Use the client theme, two custom colors, target health or a rainbow",
                  "Theme",
                  new String[] {"Theme", "Gradient", "Health", "Rainbow"}));
  public final ColorSetting primaryColorSetting =
      (ColorSetting)
          this.registerSetting(
              new ColorSetting("Primary Color", "Main ring and crystal color", -40008)
                  .getColorSetting());
  public final ColorSetting accentColorSetting =
      (ColorSetting)
          this.registerSetting(
              new ColorSetting(
                      "Accent Color", "Second color flowing through the 3D shape", -7110145)
                  .getColorSetting(true));
  public final NumberSetting scaleSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Scale",
                  "Size of the shape around the player's body",
                  1.0,
                  0.75,
                  1.75,
                  0.05,
                  "x"));
  public final NumberSetting animationSpeedSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Animation Speed",
                  "Rotation and scan speed; zero freezes the shape",
                  1.0,
                  0.0,
                  3.0,
                  0.1,
                  "x"));
  public final NumberSetting opacitySetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Opacity", "Overall effect visibility", 85.0, 10.0, 100.0, 5.0, "%"));
  public final NumberSetting glowSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Glow", "Soft light around the crisp 3D edges", 60.0, 0.0, 100.0, 5.0, "%"));
  public final BooleanSetting pulseSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Pulse", "Gently breathe the shape's size and brightness", true));
  public final BooleanSetting showCrystalsSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Show Crystals", "Small faceted crystals orbit along the rings", true));
  public final BooleanSetting throughWallsSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Through Walls",
                  "Keep an acquired target visible behind blocks during hold and fade",
                  false));
  public final BooleanSetting invisiblePlayersSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Invisible Players", "Allow targeting invisible players", false));
  public ClientWorld clientWorld;
  public TargetESPSupport targetESPSupport;
  public TargetESPSupport targetESPSupport2;
  public long timestamp;
  public double value;
  public static String[] stringArray;

  public TargetESPModule() {
    super(
        "Target ESP",
        "Animated 3D rings around the player under your crosshair",
        ModuleCategory.VISUALS);
    this.primaryColorSetting.setVisibilityCondition(
        () -> {
          return this.colorModeSetting.isSelected("Gradient");
        });
    this.accentColorSetting.setVisibilityCondition(
        () -> {
          return this.colorModeSetting.isSelected("Gradient");
        });
    this.showCrystalsSetting.setVisibilityCondition(
        () -> {
          return this.styleSetting.isSelected("Orbit");
        });
  }

  @Override
  public void onDisable() {
    this.update4();
  }

  @Override
  public void onTick() {
    FakeLootDropModule.ensureInitialized();

    MinecraftClient instance = MinecraftClient.getInstance();
    if (instance.world != this.clientWorld
        || instance.player == null
        || !instance.player.isAlive()) {
      this.update4();
    }
  }

  public void update4() {
    this.clientWorld = null;
    this.targetESPSupport = null;
    this.targetESPSupport2 = null;
    this.timestamp = 0L;
    this.value = 0.0;
  }

  public TargetESPSupport getTargetESPSupport() {
    return this.targetESPSupport;
  }

  public TargetESPSupport getTargetESPSupport2() {
    return this.targetESPSupport2;
  }

  public float computeFloat() {
    return (float) this.value;
  }

  public void render(MinecraftClient minecraftClient, float floatValue, long timestamp2) {
    FakeLootDropModule.ensureInitialized();
    if (this.isEnabled()
        && minecraftClient.world != null
        && minecraftClient.player != null
        && minecraftClient.player.isAlive()) {
      if (this.clientWorld != minecraftClient.world) {
        this.update4();
        this.clientWorld = minecraftClient.world;
      }

      float floatValue2 =
          this.timestamp == 0L
              ? 0.0F
              : Math.clamp((float) (timestamp2 - this.timestamp) / 1.0E9F, 0.0F, 0.1F);
      this.timestamp = timestamp2;
      if (!minecraftClient.isPaused()) {
        this.value = this.value + floatValue2 * this.animationSpeedSetting.floatValue();
        if (this.targetESPSupport != null
            && !this.test(minecraftClient, this.targetESPSupport.playerEntity)) {
          this.targetESPSupport = null;
        }

        if (this.targetESPSupport2 != null
            && !this.test(minecraftClient, this.targetESPSupport2.playerEntity)) {
          this.targetESPSupport2 = null;
        }

        PlayerEntity playerEntity =
            minecraftClient.currentScreen == null ? this.pick(minecraftClient, floatValue) : null;
        if (playerEntity != null
            && (this.targetESPSupport == null
                || this.targetESPSupport.playerEntity != playerEntity)) {
          TargetESPSupport targetESPSupport3 =
              this.targetESPSupport2 != null && this.targetESPSupport2.playerEntity == playerEntity
                  ? this.targetESPSupport2
                  : new TargetESPSupport(playerEntity);
          this.targetESPSupport2 = this.targetESPSupport;
          this.targetESPSupport = targetESPSupport3;
        }

        if (this.targetESPSupport2 != null) {
          this.targetESPSupport2.amount =
              Math.max(
                  0.0F,
                  this.targetESPSupport2.amount - floatValue2 / this.fadeTimeSetting.floatValue());
          if (this.targetESPSupport2.amount == 0.0F) {
            this.targetESPSupport2 = null;
          }
        }

        if (this.targetESPSupport != null) {
          this.targetESPSupport.amount2 =
              playerEntity == this.targetESPSupport.playerEntity
                  ? 0.0F
                  : this.targetESPSupport.amount2 + floatValue2;
          if (playerEntity != this.targetESPSupport.playerEntity
              && !(this.targetESPSupport.amount2 < this.holdTimeSetting.floatValue())) {
            this.targetESPSupport.amount =
                Math.max(
                    0.0F,
                    this.targetESPSupport.amount - floatValue2 / this.fadeTimeSetting.floatValue());
            if (this.targetESPSupport.amount == 0.0F) {
              this.targetESPSupport = null;
            }
          } else {
            this.targetESPSupport.amount =
                Math.min(1.0F, this.targetESPSupport.amount + floatValue2 / 0.18F);
          }
        }
      }
    } else {
      this.update4();
    }
  }

  public boolean test(MinecraftClient minecraftClient, PlayerEntity playerEntity) {
    FakeLootDropModule.ensureInitialized();
    return playerEntity != minecraftClient.player
        && playerEntity != minecraftClient.getCameraEntity()
        && playerEntity.getEntityWorld() == minecraftClient.world
        && !playerEntity.isRemoved()
        && playerEntity.isAlive()
        && !playerEntity.isSpectator()
        && ((Boolean) this.invisiblePlayersSetting.getValue() || !playerEntity.isInvisible())
        && playerEntity.getBoundingBox().squaredMagnitude(minecraftClient.player.getEyePos())
            <= (Double) this.rangeSetting.getValue() * (Double) this.rangeSetting.getValue();
  }

  public PlayerEntity pick(MinecraftClient minecraftClient, float floatValue) {
    FakeLootDropModule.ensureInitialized();

    Entity cameraEntity = minecraftClient.getCameraEntity();
    if (cameraEntity == null) {
      return null;
    }

    Camera camera = minecraftClient.gameRenderer.getCamera();
    Vec3d cameraPos = camera.getCameraPos();
    Vec3d vec3d = new Vec3d(camera.getHorizontalPlane());
    double doubleValue =
        (Double) this.rangeSetting.getValue()
            + cameraPos.distanceTo(minecraftClient.player.getCameraPosVec(floatValue));
    Vec3d vec3d2 = cameraPos.add(vec3d.multiply(doubleValue));
    BlockHitResult blockHitResult =
        minecraftClient.world.raycast(
            new RaycastContext(
                cameraPos, vec3d2, ShapeType.OUTLINE, FluidHandling.NONE, cameraEntity));
    double doubleValue2 =
        blockHitResult.getType() == Type.MISS
            ? doubleValue * doubleValue
            : cameraPos.squaredDistanceTo(blockHitResult.getPos());
    Entity entity = null;

    for (Entity entity2 :
        minecraftClient.world.getOtherEntities(
            cameraEntity,
            new Box(cameraPos, vec3d2).expand(1.0),
            entity3 -> {
              FakeLootDropModule.ensureInitialized();
              return entity3 != minecraftClient.player
                  && !entity3.isSpectator()
                  && entity3.canHit();
            })) {
      Vec3d lerpedPos = entity2.getLerpedPos(floatValue);
      Box box =
          entity2
              .getBoundingBox()
              .offset(lerpedPos.subtract(entity2.getEntityPos()))
              .expand(entity2.getTargetingMargin());
      var optional = box.raycast(cameraPos, vec3d2);
      if (box.contains(cameraPos) || !optional.isEmpty()) {
        double doubleValue3 =
            box.contains(cameraPos)
                ? 0.0
                : cameraPos.squaredDistanceTo((Vec3d) optional.orElseThrow());
        if (doubleValue3 < doubleValue2) {
          doubleValue2 = doubleValue3;
          entity = entity2;
        }
      }
    }

    return entity instanceof PlayerEntity playerEntity && this.test(minecraftClient, playerEntity)
        ? playerEntity
        : null;
  }

  public int computeInt(PlayerEntity playerEntity, float floatValue) {
    FakeLootDropModule.ensureInitialized();

    float floatValue2 =
        0.5F
            + 0.5F
                * MathHelper.sin(floatValue * (float) (Math.PI * 2) + this.computeFloat() * 0.65F);
    if (this.colorModeSetting.isSelected("Theme")) {
      return ArgbColors.lerp(
          ColorPalette.getInstance().getCount(),
          ColorPalette.getInstance().computeInt(),
          floatValue2);
    } else if (this.colorModeSetting.isSelected("Health")) {
      float floatValue3 =
          Math.clamp(
              playerEntity.getHealth() / Math.max(1.0F, playerEntity.getMaxHealth()), 0.0F, 1.0F);
      int intValue =
          floatValue3 < 0.5F
              ? ArgbColors.lerp(-43403, -11651, floatValue3 * 2.0F)
              : ArgbColors.lerp(-11651, -10358854, floatValue3 * 2.0F - 1.0F);
      return ArgbColors.lighten(intValue, floatValue2 * 0.2F);
    } else {
      return this.colorModeSetting.isSelected("Rainbow")
          ? ArgbColors.fromHsv(this.computeFloat() * 35.0F + floatValue * 110.0F, 0.55F, 1.0F)
          : ArgbColors.lerp(
              this.primaryColorSetting.getInteger(),
              this.accentColorSetting.getInteger(),
              floatValue2);
    }
  }

  public static void setStringArray(String[] stringArray2) {
    stringArray = stringArray2;
  }

  public static String[] getStringArray() {
    return stringArray;
  }

  static {
    setStringArray(new String[4]);
  }
}
