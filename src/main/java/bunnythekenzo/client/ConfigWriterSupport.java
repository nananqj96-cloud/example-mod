package bunnythekenzo.client;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ConfigWriterSupport {
  public static boolean test(Object value) {
    return Files.exists((Path) value);
  }

  public static String getString(Object value) throws IOException {
    return Files.readString((Path) value);
  }

  public static void apply(Object value, String text) throws IOException {
    Path path = (Path) value;
    Files.createDirectories(path.getParent());
    Files.writeString(path, text);
  }
}
