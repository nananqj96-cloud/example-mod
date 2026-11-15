package bunnythekenzo.client.render;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import net.fabricmc.loader.api.FabricLoader;
import bunnythekenzo.client.ClientInitializer;

public final class SystemFonts {
  public static final int count = 60;
  public static final String string = "display";
  public static final Set set =
      Set.of(
          "wingdings",
          "wingdings 2",
          "wingdings 3",
          "webdings",
          "symbol",
          "marlett",
          "mt extra",
          "bookshelf symbol 7",
          "holomdl2assets",
          "segoe mdl2 assets",
          "segoe fluent icons",
          "opensymbol",
          "dingbats");
  public static List list;

  public static Path getPath() {
    return FabricLoader.getInstance().getConfigDir().resolve("bunnythekenzo").resolve("fonts");
  }

  public static List getList() {
    if (list == null) {
      list = discover();
    }

    return list;
  }

  public static List getList2() {
    return getList().stream()
        .map(
            (referenceArgument67_0) ->
                ((bunnythekenzo.client.render.SystemFontsData) referenceArgument67_0).string2())
        .toList();
  }

  public static int computeInt() {
    List list2 = getList();

    for (int index = 0; index < list2.size(); index++) {
      if (((SystemFontsData) list2.get(index)).string().equals("display")) {
        return index;
      }
    }

    return 0;
  }

  public static SystemFontsData getSystemFontsData(String text) {
    for (SystemFontsData systemFontsData :
        (java.lang.Iterable<SystemFontsData>) (java.lang.Iterable<?>) (getList())) {
      if (systemFontsData.string().equals(text)) {
        return systemFontsData;
      }
    }

    return (SystemFontsData) getList().get(computeInt());
  }

  public static List discover() {
    ArrayList arrayList = new ArrayList();
    arrayList.add(
        new SystemFontsData(
            "display", "Xuong", "assets/bunnythekenzo/fonts/Xuong-Regular.ttf", null));
    arrayList.add(
        new SystemFontsData(
            "mono", "Monocraft", "assets/bunnythekenzo/fonts/Monocraft.ttf", null));
    LinkedHashMap linkedHashMap = new LinkedHashMap();
    collect(getPath(), "user", linkedHashMap, Integer.MAX_VALUE);

    for (Path path : (java.lang.Iterable<Path>) (java.lang.Iterable<?>) (getList3())) {
      collect(path, "system", linkedHashMap, 60);
    }

    ((java.util.List) (arrayList))
        .addAll(
            linkedHashMap.values().stream()
                .sorted(
                    (value, value2) -> {
                      return ((bunnythekenzo.client.render.SystemFontsData) (value))
                          .string2()
                          .compareToIgnoreCase(
                              ((bunnythekenzo.client.render.SystemFontsData) (value2)).string2());
                    })
                .limit(92L)
                .toList());
    return List.copyOf(arrayList);
  }

  public static List getList3() {
    String text = System.getProperty("os.name", "").toLowerCase(Locale.ROOT);
    String property = System.getProperty("user.home", "");
    ArrayList arrayList = new ArrayList();
    if (text.contains("win")) {
      arrayList.add(Path.of(System.getenv().getOrDefault("WINDIR", "C:\\Windows"), "Fonts"));
      arrayList.add(Path.of(property, "AppData", "Local", "Microsoft", "Windows", "Fonts"));
    } else if (text.contains("mac")) {
      arrayList.add(Path.of("/System/Library/Fonts"));
      arrayList.add(Path.of("/Library/Fonts"));
      arrayList.add(Path.of(property, "Library", "Fonts"));
    } else {
      arrayList.add(Path.of("/usr/share/fonts"));
      arrayList.add(Path.of("/usr/local/share/fonts"));
      arrayList.add(Path.of(property, ".local", "share", "fonts"));
      arrayList.add(Path.of(property, ".fonts"));
    }

    return arrayList;
  }

  public static void collect(Path path, String text, Map map, int intValue) {
    if (Files.isDirectory(path)) {
      try (Stream stream = Files.walk(path, 3)) {
        stream
            .filter(
                value -> {
                  return Files.isRegularFile((java.nio.file.Path) (value));
                })
            .filter(
                (referenceArgument68_0) ->
                    SystemFonts.test((java.nio.file.Path) referenceArgument68_0))
            .sorted()
            .forEach(
                (java.util.function.Consumer<java.nio.file.Path>)
                    (path2 -> {
                      if (map.size() < intValue) {
                        String string2 = getString(path2);
                        if (string2 != null) {
                          String text2 = string2.toLowerCase(Locale.ROOT);
                          if (!set.contains(text2) && !map.containsKey(text2)) {
                            map.put(
                                text2,
                                new SystemFontsData(text + ":" + string2, string2, null, path2));
                          }
                        }
                      }
                    }));
      } catch (Exception failure) {
        ClientInitializer.logger.debug("Skipping font folder {}", path, failure);
      }
    }
  }

  public static boolean test(Path path) {
    String text = path.getFileName().toString().toLowerCase(Locale.ROOT);
    return text.endsWith(".ttf") || text.endsWith(".otf");
  }

  public static String getString(Path path) {
    try {
      long size = Files.size(path);
      if (size >= 12L && size <= 40000000L) {
        ByteBuffer byteBuffer =
            ByteBuffer.wrap(Files.readAllBytes(path)).order(ByteOrder.BIG_ENDIAN);
        byteBuffer.position(4);
        int intValue = Short.toUnsignedInt(byteBuffer.getShort());
        int intValue2 = -1;
        byteBuffer.position(12);

        for (int index = 0; index < intValue; index++) {
          if (byteBuffer.remaining() < 16) {
            return null;
          }

          byte[] bytes = new byte[4];
          byteBuffer.get(bytes);
          byteBuffer.getInt();
          int value = byteBuffer.getInt();
          byteBuffer.getInt();
          if (new String(bytes, StandardCharsets.US_ASCII).equals("name")) {
            intValue2 = value;
            break;
          }
        }

        if (intValue2 >= 0 && intValue2 + 6 <= byteBuffer.capacity()) {
          byteBuffer.position(intValue2);
          byteBuffer.getShort();
          int intValue3 = Short.toUnsignedInt(byteBuffer.getShort());
          int intValue4 = intValue2 + Short.toUnsignedInt(byteBuffer.getShort());
          String text = null;

          for (int index2 = 0; index2 < intValue3 && byteBuffer.remaining() >= 12; index2++) {
            int intValue5 = Short.toUnsignedInt(byteBuffer.getShort());
            byteBuffer.getShort();
            byteBuffer.getShort();
            int intValue6 = Short.toUnsignedInt(byteBuffer.getShort());
            int intValue7 = Short.toUnsignedInt(byteBuffer.getShort());
            int intValue8 = Short.toUnsignedInt(byteBuffer.getShort());
            if (intValue6 == 1
                && intValue7 != 0
                && intValue4 + intValue8 + intValue7 <= byteBuffer.capacity()) {
              byte[] bytes2 = new byte[intValue7];
              int intValue9 = byteBuffer.position();
              byteBuffer.position(intValue4 + intValue8);
              byteBuffer.get(bytes2);
              byteBuffer.position(intValue9);
              if (intValue5 == 3) {
                return getString(new String(bytes2, StandardCharsets.UTF_16BE));
              }

              if (intValue5 == 1 && text == null) {
                text = getString(new String(bytes2, StandardCharsets.ISO_8859_1));
              }
            }
          }

          return text;
        } else {
          return null;
        }
      } else {
        return null;
      }
    } catch (IOException | RuntimeException failure) {
      return null;
    }
  }

  public static String getString(String text) {
    String text2 = text.replace('\u0000', ' ').trim();
    return !text2.isEmpty() && text2.length() <= 48 ? text2 : null;
  }
}
