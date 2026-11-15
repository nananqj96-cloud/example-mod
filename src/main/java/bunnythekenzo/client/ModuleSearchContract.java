package bunnythekenzo.client;

public interface ModuleSearchContract {
  void apply(GuiModuleEntry guiModuleEntry);

  void apply(GuiModuleEntry guiModuleEntry, boolean flag);

  void update();
}
