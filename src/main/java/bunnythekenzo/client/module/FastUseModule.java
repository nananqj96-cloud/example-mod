package bunnythekenzo.client.module;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import bunnythekenzo.client.FakeLootDropModule;
import bunnythekenzo.client.mixin.MinecraftClientAccessor;
import bunnythekenzo.client.setting.ChoiceSetting;

public class FastUseModule extends Module {
  public final ChoiceSetting itemsSetting =
      (ChoiceSetting)
          this.registerSetting(
              new ChoiceSetting(
                  "Items",
                  "What to speed up",
                  "All",
                  new String[] {"All", "Pearls", "XP Bottles"}));

  public FastUseModule() {
    super("FastUse", "Removes item use cooldowns", ModuleCategory.MISC);
  }

  @Override
  public void onTick() {
    MinecraftClient instance = MinecraftClient.getInstance();
    if (instance.player != null) {
      if (this.test(instance)) {
        ((MinecraftClientAccessor) (Object) (instance)).setItemUseCooldown(0);
      }
    }
  }

  public boolean test(MinecraftClient minecraftClient) {
    FakeLootDropModule.ensureInitialized();
    if (this.itemsSetting.isSelected("All")) {
      return true;
    }

    Item item =
        this.itemsSetting.isSelected("Pearls") ? Items.ENDER_PEARL : Items.EXPERIENCE_BOTTLE;
    return minecraftClient.player.getMainHandStack().isOf(item)
        || minecraftClient.player.getOffHandStack().isOf(item);
  }
}
