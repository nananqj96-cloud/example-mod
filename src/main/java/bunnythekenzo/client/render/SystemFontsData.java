package bunnythekenzo.client.render;

import java.nio.file.Path;

public record SystemFontsData(String string, String string2, String string3, Path path) {

  public boolean test() {
    return this.string3 != null;
  }
}
