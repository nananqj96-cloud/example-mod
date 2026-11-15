package bunnythekenzo.client.module;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import bunnythekenzo.client.FakeLootDropModule;
import bunnythekenzo.client.setting.BooleanSetting;
import bunnythekenzo.client.setting.NumberSetting;

public class RegionMapModule extends Module {

  public static final double value = 50000.0;
  public static final double value2 = 225000.0;
  public static final String[] stringArray =
      new String[] {"EU-C", "EU-W", "NA-E", "NA-W", "Asia", "Oce"};
  public static final int[] countArray;
  public static final int[][] countArray2 =
      new int[][] {
        {82, 5},
        {100, 3},
        {101, 3},
        {102, 3},
        {103, 2},
        {104, 2},
        {105, 2},
        {106, 2},
        {91, 2},
        {83, 5},
        {44, 3},
        {75, 3},
        {42, 3},
        {41, 2},
        {40, 2},
        {39, 2},
        {38, 2},
        {92, 2},
        {84, 5},
        {45, 3},
        {14, 3},
        {13, 3},
        {12, 2},
        {11, 2},
        {10, 2},
        {37, 2},
        {93, 2},
        {85, 5},
        {46, 5},
        {74, 5},
        {3, 3},
        {2, 2},
        {1, 2},
        {25, 2},
        {36, 2},
        {94, 2},
        {86, 4},
        {47, 4},
        {72, 4},
        {71, 4},
        {5, 2},
        {4, 2},
        {24, 2},
        {35, 2},
        {95, 2},
        {87, 4},
        {51, 1},
        {17, 1},
        {9, 0},
        {8, 0},
        {7, 0},
        {23, 0},
        {34, 0},
        {96, 2},
        {88, 4},
        {54, 1},
        {18, 1},
        {61, 0},
        {62, 0},
        {21, 0},
        {22, 0},
        {33, 0},
        {97, 0},
        {89, 0},
        {26, 1},
        {27, 0},
        {28, 0},
        {29, 0},
        {30, 0},
        {59, 0},
        {32, 0},
        {98, 0},
        {90, 0},
        {107, 1},
        {108, 1},
        {109, 1},
        {110, 1},
        {111, 1},
        {112, 1},
        {113, 1},
        {99, 0}
      };
  public static final int[] countArray3 = new int[81];
  public static final int[] countArray4 = new int[81];
  public final NumberSetting opacitySetting =
      (NumberSetting)
          this.registerSetting(
              new NumberSetting(
                  "Opacity", "Region cell fill opacity.", 90.0, 10.0, 100.0, 5.0, "%"));
  public final BooleanSetting gridLinesSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Grid Lines", "Draw the accent lines between region cells.", true));
  public final BooleanSetting cellNumbersSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Cell Numbers", "Show each region's ID number in its cell.", true));
  public final BooleanSetting legendSetting =
      (BooleanSetting)
          this.registerSetting(
              new BooleanSetting(
                  "Legend", "Show the region-type colour legend below the grid.", true));

  public RegionMapModule() {
    super("RegionMap", "DonutSMP server region map on the HUD.", ModuleCategory.RENDER);
  }

  public static int computeInt(int intValue, int intValue2, int intValue3) {
    return intValue << 16 | intValue2 << 8 | intValue3;
  }

  public boolean test() {
    return true;
  }

  public int computeInt() {
    return 9;
  }

  public int computeInt2() {
    return stringArray.length;
  }

  public String getString(int index) {
    FakeLootDropModule.ensureInitialized();
    return index >= 0 && index < stringArray.length ? stringArray[index] : "";
  }

  public int computeInt(int index) {
    FakeLootDropModule.ensureInitialized();
    return index >= 0 && index < countArray.length ? countArray[index] : 16777215;
  }

  public int computeInt2(int index) {
    FakeLootDropModule.ensureInitialized();
    return index >= 0 && index < countArray4.length ? countArray4[index] : -1;
  }

  public int computeInt3(int index) {
    FakeLootDropModule.ensureInitialized();
    return index >= 0 && index < countArray3.length ? countArray3[index] : -1;
  }

  public int[] getCountArray(double doubleValue, double doubleValue2) {
    int intValue = (int) Math.floor((doubleValue + 225000.0) / 50000.0);
    int intValue2 = (int) Math.floor((doubleValue2 + 225000.0) / 50000.0);
    return new int[] {intValue, intValue2};
  }

  public double[] getValueArray(double doubleValue, double doubleValue2) {
    FakeLootDropModule.ensureInitialized();

    double index = (doubleValue + 225000.0) % 50000.0 / 50000.0;
    double index2 = (doubleValue2 + 225000.0) % 50000.0 / 50000.0;
    if (index < 0.0) {
      index++;
    }

    if (index2 < 0.0) {
      index2++;
    }

    return new double[] {Math.clamp(index, 0.0, 1.0), Math.clamp(index2, 0.0, 1.0)};
  }

  public int computeInt(double doubleValue, double doubleValue2) {
    FakeLootDropModule.ensureInitialized();

    int[] countArray5 = this.getCountArray(doubleValue, doubleValue2);
    return countArray5[0] >= 0 && countArray5[0] < 9 && countArray5[1] >= 0 && countArray5[1] < 9
        ? this.computeInt3(countArray5[1] * 9 + countArray5[0])
        : -1;
  }

  public int computeInt2(double doubleValue, double doubleValue2) {
    FakeLootDropModule.ensureInitialized();

    int[] countArray5 = this.getCountArray(doubleValue, doubleValue2);
    return countArray5[0] >= 0 && countArray5[0] < 9 && countArray5[1] >= 0 && countArray5[1] < 9
        ? this.computeInt2(countArray5[1] * 9 + countArray5[0])
        : -1;
  }

  public int computeInt3() {
    ClientPlayerEntity player = MinecraftClient.getInstance().player;
    return player == null ? -1 : this.computeInt(player.getX(), player.getZ());
  }

  static {
    int[] intValues;
    int[] countArray5 = intValues = new int[6];
    intValues[0] = computeInt(159, 206, 99);
    countArray5[1] = computeInt(0, 166, 99);
    countArray5[2] = computeInt(79, 173, 234);
    countArray5[3] = computeInt(47, 110, 186);
    countArray5[4] = computeInt(245, 194, 66);
    countArray5[5] = computeInt(252, 136, 3);
    countArray = countArray5;

    for (int index = 0; index < countArray2.length && index < countArray3.length; index++) {
      if (countArray2[index].length >= 2) {
        countArray3[index] = countArray2[index][0];
        countArray4[index] = Math.min(countArray2[index][1], stringArray.length - 1);
      } else {
        countArray4[index] = -1;
      }
    }
  }
}
