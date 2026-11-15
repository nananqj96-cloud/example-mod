package bunnythekenzo.client;

import com.google.gson.annotations.SerializedName;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public final class ProfileDefaults {

  @SerializedName("username")
  public String string2 = "";

  @SerializedName("welcomed")
  public boolean enabled;

  @SerializedName("welcomeSound")
  public boolean enabled2 = true;

  @SerializedName("autoRotate")
  public boolean enabled3 = true;

  @SerializedName("crystal")
  public boolean enabled4;

  @SerializedName(value = "bunnythekenzoLogo", alternate = {"logo67"})
  public boolean enabled5;

  @SerializedName("created")
  public String string3 = LocalDate.now().toString();

  @SerializedName("playMillis")
  public long timestamp;

  @SerializedName("sessions")
  public int count;

  @SerializedName("daily")
  public TreeMap treeMap = new TreeMap();

  public static String string4;

  public static boolean test(String text) {
    return text != null && text.matches("[A-Za-z0-9_-]{3,24}");
  }

  public void normalize() {
    if (!test(this.string2)) {
      this.string2 = "";
    }

    try {
      LocalDate.parse(this.string3);
    } catch (Exception failure) {
      this.string3 = LocalDate.now().toString();
    }

    this.timestamp = Math.max(0L, this.timestamp);
    this.count = Math.max(0, this.count);
    if (this.treeMap == null) {
      this.treeMap = new TreeMap();
    }

    this.treeMap
        .entrySet()
        .removeIf(
            value2 -> {
              try {
                LocalDate localDate =
                    LocalDate.parse((CharSequence) ((java.util.Map.Entry) (value2)).getKey());
                return ((java.util.Map.Entry) (value2)).getValue() == null
                    || (Long) ((java.util.Map.Entry) (value2)).getValue() < 0L
                    || localDate.isAfter(LocalDate.now());
              } catch (Exception failure2) {
                return true;
              }
            });
    this.apply(LocalDate.now());
    ClientInitializer.ensureInitialized();
  }

  public void apply(long longValue, LocalDate localDate) {
    if (longValue > 0L) {
      this.timestamp += longValue;
      this.treeMap.merge(
          localDate.toString(),
          longValue,
          (referenceArgument8_0, referenceArgument8_1) ->
              Long.sum(
                  (java.lang.Long) referenceArgument8_0, (java.lang.Long) referenceArgument8_1));
      this.apply(localDate);
    }
  }

  public void apply(LocalDate localDate) {
    this.treeMap.headMap(localDate.minusDays(30L).toString()).clear();
  }

  public List getList(LocalDate localDate) {

    ArrayList arrayList = new ArrayList(7);

    for (int index = 6; index >= 0; index--) {
      arrayList.add(this.treeMap.getOrDefault(localDate.minusDays(index).toString(), 0L));
    }

    return arrayList;
  }

  public static String getString(long longValue) {
    if (longValue < 60000L) {
      return longValue <= 0L ? "0m" : "< 1m";
    }

    long longValue2 = longValue / 60000L;
    return longValue2 < 60L ? longValue2 + "m" : longValue2 / 60L + "h " + longValue2 % 60L + "m";
  }

  public static void setString4(String string42) {
    string4 = string42;
  }

  public static String getString4() {
    return string4;
  }

  static {
    setString4("GZcjyb");
  }

  public static void ensureInitialized() {}
}
