package bunnythekenzo.client.setting;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public final class ItemSetting extends Setting<net.minecraft.item.Item> {
  public ItemSetting(String text, String text2, Item item) {
    super(text, text2, item != null && item != Items.AIR ? item : Items.STONE);
  }

  public String getString() {
    try {
      return ((Item) this.value).getName().getString();
    } catch (Throwable failure) {
      Identifier id = Registries.ITEM.getId((Item) this.value);
      return id == null ? "Unknown item" : id.getPath();
    }
  }

  public void apply(Item item) {
    if (item != null && item != Items.AIR) {
      super.setValue(item);
    }
  }

  @Override
  public JsonElement toJson() {
    Identifier id = Registries.ITEM.getId((Item) this.value);
    return new JsonPrimitive(id == null ? "minecraft:stone" : id.toString());
  }

  @Override
  public void fromJson(JsonElement jsonElement) {
    if (jsonElement != null && jsonElement.isJsonPrimitive()) {
      try {
        Identifier identifier = Identifier.of(jsonElement.getAsString());
        Item item = Registries.ITEM.get(identifier);
        if (item != Items.AIR) {
          this.apply(item);
        }
      } catch (RuntimeException failure) {
      }
    }
  }

  @Override
  public void setValue(Item item) {
    this.apply((Item) item);
  }
}
