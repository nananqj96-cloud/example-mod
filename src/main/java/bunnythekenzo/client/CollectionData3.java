package bunnythekenzo.client;

import java.util.List;

public record CollectionData3(
    String string, String string2, List<GuiModuleEntry> list, boolean enabled) {

  public CollectionData3(String string3, String string22, List list2) {
    this(string3, string22, list2, false);
  }

  public CollectionData3(String string3, String string22, GuiModuleEntry[] lookupSupport12Array) {
    this(string3, string22, List.of(lookupSupport12Array), false);
  }

  public static CollectionData3 getCollectionData3(
      String string3, String string22, GuiModuleEntry[] lookupSupport12Array) {
    return new CollectionData3(string3, string22, List.of(lookupSupport12Array), true);
  }
}
