package bunnythekenzo.client.ui;

import net.minecraft.client.gui.screen.Screen;
import bunnythekenzo.client.GuiPreferences;

public final class ScreenSupport4 {
  public static boolean test(Screen screen) {
    return screen instanceof PanelScreen || screen instanceof DropdownScreen;
  }

  public static Screen getScreen(Screen screen) {
    return GuiPreferences.getGuiPreferences().guiStyleControl.getSelectedIndex() == 1
        ? new DropdownScreen(screen)
        : new PanelScreen(screen);
  }

  public static Screen getScreen(int intValue, Screen screen) {
    GuiPreferences.getGuiPreferences().guiStyleControl.selectIndex(intValue);
    GuiPreferences.getGuiPreferences().update3();
    return getScreen(screen);
  }

  public static Screen getScreen2(Screen screen) {
    if (screen instanceof PanelScreen panelScreen) {
      return panelScreen.getScreen();
    } else {
      return screen instanceof DropdownScreen dropdownScreen ? dropdownScreen.getScreen() : screen;
    }
  }
}
