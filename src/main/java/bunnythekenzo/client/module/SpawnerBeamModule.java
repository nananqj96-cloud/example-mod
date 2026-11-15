package bunnythekenzo.client.module;

import java.util.List;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.WorldChunk;
import bunnythekenzo.client.ChunkScanOperation;
import bunnythekenzo.client.FakeLootDropModule;
import bunnythekenzo.client.IncrementalChunkScanner;
import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.ChoiceSetting;
import bunnythekenzo.client.setting.ColorSetting;
import bunnythekenzo.client.setting.NumberSetting;

public final class SpawnerBeamModule extends Module {

  public static final ChunkScanOperation chunkScanOperation = SpawnerBeamModule::scanChunk;
  public final ChoiceSetting styleSetting =
      (ChoiceSetting)
          this.registerSetting(
              new ChoiceSetting(
                  "Style",
                  "Shape of the layered light column",
                  "Aurora",
                  new String[] {"Aurora", "Prism", "Beacon"}));
  public final ChoiceSetting colorModeSetting =
      (ChoiceSetting)
          this.registerSetting(
              new ChoiceSetting(
                  "Color Mode",
                  "Single, two-color gradient or animated rainbow",
                  "Gradient",
                  new String[] {"Gradient", "Single", "Rainbow"}));
  public final ColorSetting primaryColorSetting =
      (ColorSetting)
          this.registerSetting(
              new ColorSetting(
                      "Primary Color",
                      "Color at the spawner; follows the theme until edited",
                      -49508)
                  .getColorSetting());
  public final ColorSetting accentColorSetting =
      (ColorSetting)
          this.registerSetting(
              new ColorSetting(
                      "Accent Color", "Glow color; follows the theme until edited", -9508865)
                  .getColorSetting(true));
  public final NumberSetting heightSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Height",
                  "How far the beam reaches above the spawner",
                  384.0,
                  64.0,
                  1024.0,
                  32.0,
                  " blocks"));
  public final NumberSetting beamWidthSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Beam Width",
                  "Diameter of the bright beam core",
                  3.0,
                  0.5,
                  12.0,
                  0.25,
                  " blocks"));
  public final NumberSetting opacitySetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Opacity",
                  "Overall visibility of the light column",
                  78.0,
                  10.0,
                  100.0,
                  1.0,
                  "%"));
  public final NumberSetting glowSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Glow", "Size and strength of the soft outer halo", 70.0, 0.0, 100.0, 5.0, "%"));
  public final BooleanSetting pulseSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Pulse", "Gently breathe the beam width and brightness", true));
  public final NumberSetting animationSpeedSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Animation Speed",
                  "Speed of rotation, pulse and rising energy",
                  0.8,
                  0.0,
                  3.0,
                  0.1,
                  "x"));
  public final BooleanSetting energySpiralSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Energy Spiral", "Wrap two animated energy trails around the beam", true));
  public final BooleanSetting groundGlowSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Ground Glow", "Draw a soft target glow around the spawner", true));
  public final NumberSetting glowRadiusSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Glow Radius",
                  "Radius of the animated ground marker",
                  7.0,
                  2.0,
                  24.0,
                  0.5,
                  " blocks"));
  public final BooleanSetting spawnerBoxSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting("Spawner Box", "Outline the exact spawner block", true));
  public final BooleanSetting tracersSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Tracers", "Draw a thin screen-space line to every found spawner", false));
  public final IncrementalChunkScanner incrementalChunkScanner =
      new IncrementalChunkScanner(24, 65536, 20, 512);

  public SpawnerBeamModule() {
    super(
        "SpawnerBeam",
        "Marks every loaded spawner with a massive animated sky beam",
        ModuleCategory.RENDER);
    this.accentColorSetting.setVisibilityCondition(
        () -> {
          return this.colorModeSetting.isSelected("Gradient");
        });
    this.animationSpeedSetting.setVisibilityCondition(
        () -> {
          FakeLootDropModule.ensureInitialized();
          return (Boolean) this.pulseSetting.getValue()
              || (Boolean) this.energySpiralSetting.getValue()
              || this.colorModeSetting.isSelected("Rainbow");
        });
    this.glowRadiusSetting.setVisibilityCondition(this.groundGlowSetting::getValue);
  }

  @Override
  public void onTick() {
    FakeLootDropModule.ensureInitialized();

    MinecraftClient instance = MinecraftClient.getInstance();
    if (instance.world != null && instance.player != null) {
      int intValue = Math.max(2, instance.options.getViewDistance().getValue());
      this.incrementalChunkScanner.apply(intValue, chunkScanOperation);
    }
  }

  @Override
  public void onDisable() {
    this.update4();
  }

  public void update4() {
    this.incrementalChunkScanner.update2();
  }

  public static int scanChunk(WorldChunk worldChunk, List list, int intValue) {
    FakeLootDropModule.ensureInitialized();
    if (list.size() >= intValue) {
      return 0;
    }

    ChunkSection[] sectionArray = worldChunk.getSectionArray();
    int bottomSectionCoord = worldChunk.getBottomSectionCoord();
    int startX = worldChunk.getPos().getStartX();
    int startZ = worldChunk.getPos().getStartZ();
    short shortValue = 0;

    for (int index = 0; index < sectionArray.length; index++) {
      ChunkSection chunkSection = sectionArray[index];
      if (!chunkSection.isEmpty()
          && chunkSection.hasAny(
              blockState -> {
                return blockState.isOf(Blocks.SPAWNER);
              })) {
        int intValue2 = bottomSectionCoord + index << 4;
        shortValue += 4096;

        for (int index2 = 0; index2 < 16; index2++) {
          for (int index3 = 0; index3 < 16; index3++) {
            for (int index4 = 0; index4 < 16; index4++) {
              if (chunkSection.getBlockState(index4, index2, index3).isOf(Blocks.SPAWNER)) {
                list.add(new BlockPos(startX + index4, intValue2 + index2, startZ + index3));
                if (list.size() >= intValue) {
                  return shortValue;
                }
              }
            }
          }
        }
      }
    }

    return shortValue;
  }
}
