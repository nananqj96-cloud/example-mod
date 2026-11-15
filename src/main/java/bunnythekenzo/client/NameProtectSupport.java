package bunnythekenzo.client;

import java.util.LinkedHashMap;
import java.util.Map.Entry;
import bunnythekenzo.client.module.NameProtectModule;

public class NameProtectSupport extends LinkedHashMap {
  public final NameProtectModule nameProtectModule;

  public static final long timestamp = -2931501641230188288L;

  public NameProtectSupport(
      NameProtectModule nameProtectModule2, int intValue, float floatValue, boolean flag) {
    super(intValue, floatValue, flag);
    this.nameProtectModule = nameProtectModule2;
  }

  @Override
  public boolean removeEldestEntry(Entry entry) {
    FakeLootDropModule.ensureInitialized();
    return this.size() > 256;
  }
}
