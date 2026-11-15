package bunnythekenzo.client.module;

import bunnythekenzo.client.DeepscanNotifications;
import bunnythekenzo.client.DeepscanWorker;

public final class DeepscanChunkFinderModule extends Module {
  public final DeepscanWorker deepscanWorker = new DeepscanWorker();

  public DeepscanChunkFinderModule() {
    super("Deepscan Chunk Finder", "It detects signals", ModuleCategory.DONUTSMP);
  }

  @Override
  public void onEnable() {
    this.deepscanWorker.update();
    DeepscanNotifications.update();
  }

  @Override
  public void onDisable() {
    this.deepscanWorker.update();
    DeepscanNotifications.update2();
  }

  @Override
  public void onTick() {
    this.deepscanWorker.tick();
  }
}
