package bunnythekenzo.client.module;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.block.AbstractBlock.AbstractBlockState;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.WorldChunk;
import bunnythekenzo.client.BlockEntityESPData;
import bunnythekenzo.client.FakeLootDropModule;
import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.ChoiceSetting;
import bunnythekenzo.client.setting.ColorSetting;
import bunnythekenzo.client.setting.EntityTypeSetting;
import bunnythekenzo.client.setting.NumberSetting;

public class BlockEntityESPModule extends Module {

  public final EntityTypeSetting blockEntitiesSetting =
      (EntityTypeSetting)
          this.registerSetting(
              new EntityTypeSetting("Block Entities", "Which block-entity types to highlight"));
  public final ChoiceSetting modeSetting =
      (ChoiceSetting)
          this.registerSetting(
              new ChoiceSetting(
                  "Mode",
                  "Box style — hollow outline or translucent fill",
                  "Full",
                  new String[] {"Full", "Outline"}));
  public final NumberSetting rangeSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Range", "Max distance a block entity is highlighted", 128.0, 16.0, 512.0, 8.0));
  public final NumberSetting maxYLevelSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Max Y Level",
                  "Only scan block entities at or below this Y level",
                  319.0,
                  -64.0,
                  319.0,
                  1.0));
  public final NumberSetting highlightAlphaSetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting("Highlight Alpha", "Box opacity (0-255)", 180.0, 0.0, 255.0, 1.0));
  public final BooleanSetting tracersSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Tracers", "Thin lines from the crosshair to nearby block entities", false));
  public final BooleanSetting showGhostsSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Show Ghosts",
                  "Keep entries the server sent but that are gone client-side",
                  true));
  public final ColorSetting ghostTintSetting =
      (ColorSetting)
          this.registerSetting(
              new ColorSetting("Ghost Tint", "Color blended into ghost entries", -922791856));
  public final BooleanSetting chunkPacketsSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Chunk Packets", "Read block entities from chunk-data packets", true));
  public final BooleanSetting beUpdatePacketsSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "BE Update Packets",
                  "Read block entities from block-entity update packets",
                  true));
  public final BooleanSetting worldRescanSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "World Rescan", "Also snapshot already-loaded chunks when enabled", true));
  public final Map map = new HashMap();
  public final Map map2 = new ConcurrentHashMap();
  public int count2 = Integer.MIN_VALUE;
  public static int[] countArray;

  public static final long timestamp = Long.MAX_VALUE;

  public BlockEntityESPModule() {
    super("BlockEntityESP", "Highlights block entities from raw packets", ModuleCategory.RENDER);
    this.ghostTintSetting.setVisibilityCondition(this.showGhostsSetting::getValue);
    this.apply("minecraft:chest", "Chest", Items.CHEST, -22016, true);
    this.apply("minecraft:trapped_chest", "Trapped Chest", Items.TRAPPED_CHEST, -65536, true);
    this.apply("minecraft:ender_chest", "Ender Chest", Items.ENDER_CHEST, -8912641, true);
    this.apply("minecraft:shulker_box", "Shulker Box", Items.SHULKER_BOX, -47873, true);
    this.apply("minecraft:barrel", "Barrel", Items.BARREL, -7842560, true);
    this.apply("minecraft:mob_spawner", "Spawner", Items.SPAWNER, -16711936, true);
    this.apply("minecraft:hopper", "Hopper", Items.HOPPER, -7829368, false);
    this.apply("minecraft:furnace", "Furnace", Items.FURNACE, -7566196, false);
    this.apply("minecraft:blast_furnace", "minecraft:furnace");
    this.apply("minecraft:smoker", "minecraft:furnace");
    this.apply("minecraft:dispenser", "Dispenser", Items.DISPENSER, -10066330, false);
    this.apply("minecraft:dropper", "minecraft:dispenser");
    this.apply("minecraft:brewing_stand", "Brewing Stand", Items.BREWING_STAND, -3372801, false);
    this.apply("minecraft:beehive", "Beehive", Items.BEEHIVE, -13312, false);
    this.apply(
        "minecraft:enchanting_table", "Enchanting Table", Items.ENCHANTING_TABLE, -7864065, false);
    this.apply("minecraft:sign", "Sign", Items.OAK_SIGN, -3355444, false);
    this.apply("minecraft:hanging_sign", "minecraft:sign");
    this.apply("minecraft:bed", "Bed", Items.RED_BED, -30584, false);
    this.apply("minecraft:skull", "Skull", Items.SKELETON_SKULL, -2236963, false);
    this.apply("minecraft:banner", "Banner", Items.WHITE_BANNER, -1118482, false);
    this.apply("minecraft:crafter", "Crafter", Items.CRAFTER, -12276993, false);
    this.apply("minecraft:vault", "Vault", Items.VAULT, -10496, false);
    this.apply("minecraft:trial_spawner", "Trial Spawner", Items.TRIAL_SPAWNER, -16711766, false);
    this.apply("other", "Other", Items.BEDROCK, -5592406, true);
  }

  public void apply(String text, String text2, Item item, int intValue, boolean flag) {
    this.blockEntitiesSetting.getEntityFilterEntry(text, text2, item, flag, intValue);
  }

  public void apply(String text, String text2) {
    this.map.put(text, text2);
  }

  public String getString(String text) {
    FakeLootDropModule.ensureInitialized();
    if (this.blockEntitiesSetting.getEntityFilterEntry(text) != null) {
      return text;
    }

    String text2 = (String) this.map.get(text);
    if (text2 != null) {
      return text2;
    }

    int intValue = text.indexOf(58);
    if (intValue >= 0) {
      String text3 = text.substring(intValue + 1);
      String text4 = "minecraft:" + text3;
      if (this.blockEntitiesSetting.getEntityFilterEntry(text4) != null) {
        return text4;
      }

      if (this.map.containsKey(text4)) {
        return (String) this.map.get(text4);
      }
    }

    return "other";
  }

  public boolean test() {
    return (Boolean) this.chunkPacketsSetting.getValue();
  }

  public boolean test4() {
    return (Boolean) this.beUpdatePacketsSetting.getValue();
  }

  public void apply(BlockPos blockPos, BlockEntityType blockEntityType) {
    FakeLootDropModule.ensureInitialized();
    if (blockPos != null
        && blockEntityType != null
        && blockPos.getY() <= this.maxYLevelSetting.intValue()) {
      Identifier id = Registries.BLOCK_ENTITY_TYPE.getId(blockEntityType);
      String string = this.getString(id != null ? id.toString() : String.valueOf(blockEntityType));
      this.map2.put(
          blockPos.asLong(),
          new BlockEntityESPData(blockPos.toImmutable(), string, System.currentTimeMillis()));
      if (this.map2.size() > 8192) {
        this.update4();
      }
    }
  }

  public void update4() {
    FakeLootDropModule.ensureInitialized();

    long longValue = timestamp;
    Long longValue2 = null;

    for (Entry entry : (java.lang.Iterable<Entry>) (java.lang.Iterable<?>) (this.map2.entrySet())) {
      if (((BlockEntityESPData) entry.getValue()).timestamp() < longValue) {
        longValue = ((BlockEntityESPData) entry.getValue()).timestamp();
        longValue2 = (Long) entry.getKey();
      }
    }

    if (longValue2 != null) {
      this.map2.remove(longValue2);
    }
  }

  public Collection getCollection() {
    return this.map2.values();
  }

  public void update5() {
    this.map2.clear();
  }

  public int computeInt() {
    return this.map2.size();
  }

  @Override
  public void onEnable() {
    this.map2.clear();
    FakeLootDropModule.ensureInitialized();

    this.count2 = this.maxYLevelSetting.intValue();
    if ((Boolean) this.worldRescanSetting.getValue()) {
      this.update8();
    }
  }

  @Override
  public void onDisable() {
    this.map2.clear();
    this.count2 = Integer.MIN_VALUE;
  }

  @Override
  public void onTick() {
    FakeLootDropModule.ensureInitialized();

    int value2 = this.maxYLevelSetting.intValue();
    if (value2 != this.count2) {
      this.count2 = value2;
      this.map2
          .entrySet()
          .removeIf(
              value3 -> {
                FakeLootDropModule.ensureInitialized();
                return ((BlockEntityESPData) ((java.util.Map.Entry) (value3)).getValue())
                        .blockPos()
                        .getY()
                    > value2;
              });
      if ((Boolean) this.worldRescanSetting.getValue()) {
        this.update8();
      }
    }
  }

  public void update8() {
    FakeLootDropModule.ensureInitialized();

    MinecraftClient instance = MinecraftClient.getInstance();
    ClientWorld world = instance.world;
    ClientPlayerEntity player = instance.player;
    if (world != null && player != null) {
      int intValue =
          Math.min(32, (int) Math.ceil((Double) this.rangeSetting.getValue() / 16.0) + 2);
      int x = player.getChunkPos().x;
      int z = player.getChunkPos().z;
      Mutable mutable = new Mutable();

      for (int index = x - intValue; index <= x + intValue; index++) {
        for (int index2 = z - intValue; index2 <= z + intValue; index2++) {
          if (world.getChunkManager().isChunkLoaded(index, index2)) {
            WorldChunk chunk = world.getChunk(index, index2);
            ChunkSection[] sectionArray = chunk.getSectionArray();
            int bottomSectionCoord = chunk.getBottomSectionCoord();
            int startX = chunk.getPos().getStartX();
            int startZ = chunk.getPos().getStartZ();

            for (int index3 = 0; index3 < sectionArray.length; index3++) {
              ChunkSection chunkSection = sectionArray[index3];
              int intValue2 = bottomSectionCoord + index3 << 4;
              if (intValue2 <= this.maxYLevelSetting.intValue()
                  && !chunkSection.isEmpty()
                  && chunkSection.hasAny(AbstractBlockState::hasBlockEntity)) {
                int intValue3 = Math.min(15, this.maxYLevelSetting.intValue() - intValue2);

                for (int index4 = 0; index4 <= intValue3; index4++) {
                  for (int index5 = 0; index5 < 16; index5++) {
                    for (int index6 = 0; index6 < 16; index6++) {
                      BlockState blockState = chunkSection.getBlockState(index6, index4, index5);
                      if (blockState.hasBlockEntity()
                          && blockState.getBlock()
                              instanceof BlockEntityProvider blockEntityProvider) {
                        mutable.set(startX + index6, intValue2 + index4, startZ + index5);

                        try {
                          BlockEntity blockEntity =
                              blockEntityProvider.createBlockEntity(
                                  mutable.toImmutable(), blockState);
                          if (blockEntity != null) {
                            this.apply(mutable, blockEntity.getType());
                          }
                        } catch (Exception failure) {
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
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
