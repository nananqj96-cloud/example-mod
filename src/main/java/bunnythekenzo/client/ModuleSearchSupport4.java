package bunnythekenzo.client;

import bunnythekenzo.client.ui.ModulesPage;

public class ModuleSearchSupport4 implements ModuleSearchContract {
  public final ModulesPage modulesPage;

  public ModuleSearchSupport4(ModulesPage modulesPage2) {
    this.modulesPage = modulesPage2;
  }

  @Override
  public void apply(GuiModuleEntry guiModuleEntry) {
    this.modulesPage.apply2(guiModuleEntry);
  }

  @Override
  public void apply(GuiModuleEntry guiModuleEntry, boolean flag) {
    this.modulesPage.apply(guiModuleEntry, flag);
  }

  @Override
  public void update() {
    this.modulesPage.leaveVault();
  }
}
