package bunnythekenzo.client.setting;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class BlockListSetting extends Setting<java.util.List<BlockFilterEntry>> {
  public static final int count = -16711736;
  public final Set set = new HashSet();

  public BlockListSetting(String text, String text2) {
    super(text, text2, new ArrayList());
  }

  public List getList() {
    return (List) this.value;
  }

  public int computeInt() {
    return ((List) this.value).size();
  }

  public long enabledCount() {
    return ((List) this.value)
        .stream()
            .filter(
                value -> {
                  return (Boolean)
                      ((bunnythekenzo.client.setting.BlockFilterEntry) (value))
                          .enabledSetting.getValue();
                })
            .count();
  }

  public boolean test(Identifier identifier) {
    return this.set.contains(identifier);
  }

  public BlockFilterEntry getBlockFilterEntry(Block block) {
    if (block == null) {
      return null;
    }

    Identifier id = Registries.BLOCK.getId(block);
    if (id != null && this.set.contains(id)) {
      for (BlockFilterEntry choiceSettingSupport :
          (java.lang.Iterable<BlockFilterEntry>) (java.lang.Iterable<?>) ((List) this.value)) {
        if (choiceSettingSupport.getIdentifier().equals(id)) {
          return choiceSettingSupport;
        }
      }

      return null;
    } else {
      return null;
    }
  }

  public boolean test(Block block) {
    BlockFilterEntry choiceSettingSupport = this.getBlockFilterEntry(block);
    return choiceSettingSupport != null && (Boolean) choiceSettingSupport.enabledSetting.getValue();
  }

  public BlockFilterEntry getBlockFilterEntry(Block block, boolean flag, int intValue) {
    if (block != null && block != Blocks.AIR) {
      Identifier id = Registries.BLOCK.getId(block);
      if (id != null && !this.set.contains(id)) {
        BlockFilterEntry choiceSettingSupport = new BlockFilterEntry(id, block, flag, intValue);
        ((List) this.value).add(choiceSettingSupport);
        this.set.add(id);
        return choiceSettingSupport;
      } else {
        return null;
      }
    } else {
      return null;
    }
  }

  public void apply(BlockFilterEntry choiceSettingSupport) {
    if (((List) this.value).remove(choiceSettingSupport)) {
      this.set.remove(choiceSettingSupport.getIdentifier());
    }
  }

  public void update2() {
    ((List) this.value).clear();
    this.set.clear();
  }

  public List getList(String text, int intValue) {
    String text2 = text == null ? "" : text.trim().toLowerCase(Locale.ROOT);
    ArrayList arrayList = new ArrayList();
    if (!text2.isEmpty() && intValue > 0) {
      for (Block block : Registries.BLOCK) {
        if (block != Blocks.AIR && block != Blocks.CAVE_AIR && block != Blocks.VOID_AIR) {
          Identifier id = Registries.BLOCK.getId(block);
          if (id != null && !this.set.contains(id)) {
            String text3 = id.getPath().toLowerCase(Locale.ROOT);
            String text4 = id.getNamespace().toLowerCase(Locale.ROOT);
            String text5 = getString(block).toLowerCase(Locale.ROOT);
            if (text3.contains(text2) || text4.contains(text2) || text5.contains(text2)) {
              arrayList.add(block);
              if (arrayList.size() >= intValue) {
                break;
              }
            }
          }
        }
      }

      return arrayList;
    } else {
      return arrayList;
    }
  }

  public static String getString(Block block) {
    try {
      return block.getName().getString();
    } catch (Throwable failure) {
      Identifier id = Registries.BLOCK.getId(block);
      return id != null ? id.getPath() : "block";
    }
  }

  public void update3() {
    this.update2();
    this.getBlockFilterEntry(Blocks.DIAMOND_ORE, true, -16711736);
    this.getBlockFilterEntry(Blocks.DEEPSLATE_DIAMOND_ORE, true, -16711736);
    this.getBlockFilterEntry(Blocks.EMERALD_ORE, false, -16711868);
    this.getBlockFilterEntry(Blocks.DEEPSLATE_EMERALD_ORE, false, -16711868);
    this.getBlockFilterEntry(Blocks.ANCIENT_DEBRIS, true, -39356);
    this.getBlockFilterEntry(Blocks.NETHER_GOLD_ORE, false, -10496);
    this.getBlockFilterEntry(Blocks.GOLD_ORE, false, -10496);
    this.getBlockFilterEntry(Blocks.DEEPSLATE_GOLD_ORE, false, -10496);
    this.getBlockFilterEntry(Blocks.IRON_ORE, false, -3618616);
    this.getBlockFilterEntry(Blocks.DEEPSLATE_IRON_ORE, false, -3618616);
    this.getBlockFilterEntry(Blocks.COAL_ORE, false, -12303292);
    this.getBlockFilterEntry(Blocks.DEEPSLATE_COAL_ORE, false, -12303292);
    this.getBlockFilterEntry(Blocks.COPPER_ORE, false, -4689101);
    this.getBlockFilterEntry(Blocks.DEEPSLATE_COPPER_ORE, false, -4689101);
    this.getBlockFilterEntry(Blocks.LAPIS_ORE, false, -12490271);
    this.getBlockFilterEntry(Blocks.DEEPSLATE_LAPIS_ORE, false, -12490271);
    this.getBlockFilterEntry(Blocks.REDSTONE_ORE, false, -65536);
    this.getBlockFilterEntry(Blocks.DEEPSLATE_REDSTONE_ORE, false, -65536);
    this.getBlockFilterEntry(Blocks.SPAWNER, false, -7846657);
    this.getBlockFilterEntry(Blocks.END_PORTAL_FRAME, false, -12255250);
    this.getBlockFilterEntry(Blocks.CHEST, false, -22016);
  }

  @Override
  public JsonElement toJson() {
    JsonArray jsonArray = new JsonArray();

    for (BlockFilterEntry choiceSettingSupport :
        (java.lang.Iterable<BlockFilterEntry>) (java.lang.Iterable<?>) ((List) this.value)) {
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("id", choiceSettingSupport.getIdentifier().toString());
      jsonObject.addProperty("enabled", (Boolean) choiceSettingSupport.enabledSetting.getValue());
      jsonObject.addProperty("color", choiceSettingSupport.colorSetting.getInteger());
      jsonArray.add(jsonObject);
    }

    return jsonArray;
  }

  @Override
  public void fromJson(JsonElement jsonElement) {
    if (jsonElement != null && jsonElement.isJsonArray()) {
      this.update2();
      var iterator = jsonElement.getAsJsonArray().iterator();

      while (true) {
        JsonObject jsonObject;
        Identifier identifier;
        while (true) {
          if (!iterator.hasNext()) {
            return;
          }

          JsonElement jsonElement2 = (JsonElement) iterator.next();
          if (jsonElement2.isJsonObject()) {
            jsonObject = jsonElement2.getAsJsonObject();
            if (jsonObject.has("id")) {
              try {
                identifier = Identifier.of(jsonObject.get("id").getAsString());
                break;
              } catch (Exception failure) {
              }
            }
          }
        }

        Block block = Registries.BLOCK.get(identifier);
        if (block != null && block != Blocks.AIR) {
          boolean flag = !jsonObject.has("enabled") || jsonObject.get("enabled").getAsBoolean();
          int intValue = jsonObject.has("color") ? jsonObject.get("color").getAsInt() : -16711736;
          this.getBlockFilterEntry(block, flag, intValue);
        }
      }
    }
  }
}
