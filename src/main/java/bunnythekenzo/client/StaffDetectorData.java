package bunnythekenzo.client;

import java.util.List;
import java.util.Set;

public record StaffDetectorData(
    String string, Set set, List list, String string2, boolean enabled, boolean enabled2) {

  public StaffDetectorData(
      String string, Set set, List list, String string2, boolean enabled, boolean enabled2) {
    this.string = string;
    this.set = set;
    ClientData.ensureInitialized();

    this.list = list;
    this.string2 = string2;
    this.enabled = enabled;
    this.enabled2 = enabled2;
  }
}
