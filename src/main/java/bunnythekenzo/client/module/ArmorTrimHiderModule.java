package bunnythekenzo.client.module;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.equipment.trim.ArmorTrim;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import org.jetbrains.annotations.Nullable;
import bunnythekenzo.client.FakeLootDropModule;
import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.ChoiceSetting;

public class ArmorTrimHiderModule extends Module {
  public final ChoiceSetting modeSetting =
      (ChoiceSetting)
          this.registerSetting(
              new ChoiceSetting(
                  "Mode",
                  "Hide wipes worn trims; Random gives every piece a random one",
                  "Hide",
                  new String[] {"Hide", "Random"}));
  public final BooleanSetting ownArmorSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Own Armor", "Also affect your own worn armor (F5 / inventory)", true));
  public DynamicRegistryManager dynamicRegistryManager;
  public final List list = new ArrayList();
  public final List list2 = new ArrayList();

  public ArmorTrimHiderModule() {
    super("ArmorTrimHider", "Hides or randomizes worn armor trims", ModuleCategory.MISC);
  }

  public boolean test() {
    return (Boolean) this.ownArmorSetting.getValue();
  }

  @Nullable
  public ArmorTrim getArmorTrim(ItemStack itemStack, @Nullable ArmorTrim armorTrim) {
    FakeLootDropModule.ensureInitialized();
    if (this.modeSetting.isSelected("Random")) {
      ArmorTrim armorTrim2 = this.getArmorTrim(itemStack);
      return armorTrim2 != null ? armorTrim2 : armorTrim;
    } else {
      return null;
    }
  }

  @Nullable
  public ArmorTrim getArmorTrim(ItemStack itemStack) {
    FakeLootDropModule.ensureInitialized();

    MinecraftClient instance = MinecraftClient.getInstance();
    if (instance.world == null) {
      return null;
    }

    DynamicRegistryManager registryManager = instance.world.getRegistryManager();
    if (registryManager != this.dynamicRegistryManager) {
      this.apply(registryManager);
    }

    if (!this.list.isEmpty() && !this.list2.isEmpty()) {
      Random random = new Random(itemStack.getItem().getTranslationKey().hashCode());
      RegistryEntry registryEntry = (RegistryEntry) this.list.get(random.nextInt(this.list.size()));
      RegistryEntry registryEntry2 =
          (RegistryEntry) this.list2.get(random.nextInt(this.list2.size()));
      return new ArmorTrim(registryEntry, registryEntry2);
    } else {
      return null;
    }
  }

  public void apply(DynamicRegistryManager dynamicRegistryManager2) {
    this.dynamicRegistryManager = dynamicRegistryManager2;
    this.list.clear();
    this.list2.clear();

    try {
      dynamicRegistryManager2
          .getOrThrow(RegistryKeys.TRIM_MATERIAL)
          .streamEntries()
          .forEach(this.list::add);
      dynamicRegistryManager2
          .getOrThrow(RegistryKeys.TRIM_PATTERN)
          .streamEntries()
          .forEach(this.list2::add);
    } catch (Exception failure) {
      this.list.clear();
      this.list2.clear();
    }
  }

  @Override
  public void onDisable() {
    this.dynamicRegistryManager = null;
    this.list.clear();
    this.list2.clear();
  }
}
