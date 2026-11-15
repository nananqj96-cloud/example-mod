package bunnythekenzo.client;

import bunnythekenzo.client.module.Module;

public class HudElementSupport2 implements HudElementContract {
  public final Module module;

  public HudElementSupport2(Module module2) {
    this.module = module2;
  }

  @Override
  public boolean test() {
    return this.module.isEnabled();
  }

  @Override
  public void apply(boolean flag) {
    this.module.setEnabled(flag);
  }

  @Override
  public void update() {
    this.module.setEnabled(false);
  }
}
