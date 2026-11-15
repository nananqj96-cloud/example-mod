package bunnythekenzo.client;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import bunnythekenzo.client.module.SusChunkFinderModule;

public final class LookupSupport3 {
  public static final float amount = 0.3F;
  public static final float amount2 = 0.45F;
  public static final float amount3 = 24.0F;
  public static final Map map = new HashMap();
  public static final Map map2 = new HashMap();
  public static long timestamp;

  public static void update() {
    map.clear();
    map2.clear();
  }

  public static String getString() {
    StringBuilder builder = new StringBuilder();

    for (Entry entry : (java.lang.Iterable<Entry>) (java.lang.Iterable<?>) (map.entrySet())) {
      LookupSupport lookupSupport = (LookupSupport) entry.getValue();
      builder
          .append(new ChunkPos((Long) entry.getKey()))
          .append("{a=")
          .append(lookupSupport.amount)
          .append(",t=")
          .append(lookupSupport.amount2)
          .append(",f=")
          .append(lookupSupport.enabled)
          .append("} ");
    }

    for (Entry entry2 : (java.lang.Iterable<Entry>) (java.lang.Iterable<?>) (map2.entrySet())) {
      LookupSupport2 lookupSupport2 = (LookupSupport2) entry2.getValue();
      builder
          .append("zone")
          .append(new ChunkPos((Long) entry2.getKey()))
          .append("{a=")
          .append(lookupSupport2.amount3)
          .append(",s=")
          .append(lookupSupport2.amount)
          .append(",f=")
          .append(lookupSupport2.enabled)
          .append("} ");
    }

    return builder.toString();
  }

  public static void apply(
      Immediate immediate,
      MatrixStack matrixStack,
      Vec3d vec3d,
      SusChunkFinderModule susChunkFinderModule) {
    SuspiciousChunkScanner suspiciousChunkScanner = susChunkFinderModule.suspiciousChunkScanner;
    double doubleValue = (Double) susChunkFinderModule.renderYSetting.getValue();
    int count = ClientInitializer.getThemeManager().getColorPalette().getCount();
    float floatValue = susChunkFinderModule.fillOpacitySetting.floatValue() / 255.0F;
    boolean flag = (Boolean) susChunkFinderModule.smartModeSetting.getValue();
    int value = suspiciousChunkScanner.computeInt();
    updateTargets(suspiciousChunkScanner, flag, value);
    float floatValue2 = computeFloat();
    float floatValue3 = 1.0F - (float) Math.exp(-floatValue2 * 24.0F);
    Iterator iterator = map.entrySet().iterator();

    while (iterator.hasNext()) {
      Entry entry = (Entry) iterator.next();
      LookupSupport lookupSupport = (LookupSupport) entry.getValue();
      lookupSupport.amount =
          lookupSupport.amount
              + (lookupSupport.enabled ? floatValue2 / 0.3F : -floatValue2 / 0.45F);
      lookupSupport.amount = Math.clamp(lookupSupport.amount, 0.0F, 1.0F);
      if (!lookupSupport.enabled && lookupSupport.amount <= 0.0F) {
        iterator.remove();
      } else {
        double doubleValue2 = ChunkPos.getPackedX((Long) entry.getKey()) * 16.0;
        double doubleValue3 = ChunkPos.getPackedZ((Long) entry.getKey()) * 16.0;
        double value2 =
            VertexConsumerSupport.computeDouble(
                doubleValue, doubleValue2 + 8.0, doubleValue3 + 8.0);
        render(
            immediate,
            matrixStack,
            vec3d,
            doubleValue2,
            doubleValue3,
            doubleValue2 + 16.0,
            doubleValue3 + 16.0,
            value2,
            count,
            floatValue * lookupSupport.amount2 * lookupSupport.amount);
        if ((Boolean) susChunkFinderModule.centroidMarkerSetting.getValue()) {
          VertexConsumerSupport.render2(
              immediate,
              matrixStack,
              vec3d,
              doubleValue2 + 8.0,
              doubleValue3 + 8.0,
              value2 + 0.05,
              1.5,
              ArgbColors.withOpacity(count, 0.95F * lookupSupport.amount));
        }
      }
    }

    Iterator iterator2 = map2.entrySet().iterator();

    while (iterator2.hasNext()) {
      LookupSupport2 lookupSupport2 = (LookupSupport2) ((Entry) iterator2.next()).getValue();
      lookupSupport2.amount3 =
          lookupSupport2.amount3
              + (lookupSupport2.enabled ? floatValue2 / 0.3F : -floatValue2 / 0.45F);
      lookupSupport2.amount3 = Math.clamp(lookupSupport2.amount3, 0.0F, 1.0F);
      if (!lookupSupport2.enabled && lookupSupport2.amount3 <= 0.0F) {
        iterator2.remove();
      } else {
        lookupSupport2.value =
            lookupSupport2.value + (lookupSupport2.value3 - lookupSupport2.value) * floatValue3;
        lookupSupport2.value2 =
            lookupSupport2.value2 + (lookupSupport2.value4 - lookupSupport2.value2) * floatValue3;
        lookupSupport2.amount =
            lookupSupport2.amount + (lookupSupport2.amount2 - lookupSupport2.amount) * floatValue3;
        double doubleValue4 = lookupSupport2.amount / 2.0;
        double value3 =
            VertexConsumerSupport.computeDouble(
                doubleValue, lookupSupport2.value, lookupSupport2.value2);
        render(
            immediate,
            matrixStack,
            vec3d,
            lookupSupport2.value - doubleValue4,
            lookupSupport2.value2 - doubleValue4,
            lookupSupport2.value + doubleValue4,
            lookupSupport2.value2 + doubleValue4,
            value3,
            count,
            floatValue * lookupSupport2.amount4 * lookupSupport2.amount3);
        if ((Boolean) susChunkFinderModule.centroidMarkerSetting.getValue()) {
          VertexConsumerSupport.render2(
              immediate,
              matrixStack,
              vec3d,
              lookupSupport2.value,
              lookupSupport2.value2,
              value3 + 0.05,
              2.0,
              ArgbColors.withOpacity(count, 0.95F * lookupSupport2.amount3));
        }
      }
    }

    VertexConsumerSupport.apply(immediate);
  }

  public static void render(
      Immediate immediate,
      MatrixStack matrixStack,
      Vec3d vec3d,
      double doubleValue,
      double doubleValue2,
      double doubleValue3,
      double doubleValue4,
      double doubleValue5,
      int intValue,
      float floatValue) {
    VertexConsumerSupport.render(
        immediate,
        matrixStack,
        vec3d,
        doubleValue,
        doubleValue2,
        doubleValue3,
        doubleValue4,
        doubleValue5,
        ArgbColors.withOpacity(intValue, floatValue));
  }

  public static void updateTargets(
      SuspiciousChunkScanner suspiciousChunkScanner, boolean flag, int intValue) {
    for (LookupSupport lookupSupport :
        (java.lang.Iterable<LookupSupport>) (java.lang.Iterable<?>) (map.values())) {
      lookupSupport.enabled = false;
    }

    for (LookupSupport2 lookupSupport2 :
        (java.lang.Iterable<LookupSupport2>) (java.lang.Iterable<?>) (map2.values())) {
      lookupSupport2.enabled = false;
    }

    if (flag) {
      for (WorldSignalTypeData worldSignalTypeData :
          (java.lang.Iterable<WorldSignalTypeData>)
              (java.lang.Iterable<?>) (suspiciousChunkScanner.getList3())) {
        if (worldSignalTypeData.set().size() == 1) {
          long longValue = (Long) worldSignalTypeData.set().iterator().next();
          LookupSupport lookupSupport3 =
              (bunnythekenzo.client.LookupSupport)
                  (map.computeIfAbsent(
                      longValue,
                      value -> {
                        return new LookupSupport();
                      }));
          lookupSupport3.enabled = true;
          lookupSupport3.amount2 = computeFloat(worldSignalTypeData.value4(), intValue);
        } else {
          long MAX_VALUE = Long.MAX_VALUE;

          for (long longValue2 :
              (java.lang.Iterable<Long>) (java.lang.Iterable<?>) (worldSignalTypeData.set())) {
            MAX_VALUE = Math.min(MAX_VALUE, longValue2);
          }

          LookupSupport2 lookupSupport22 = (LookupSupport2) map2.get(MAX_VALUE);
          if (lookupSupport22 == null) {
            lookupSupport22 =
                new LookupSupport2(worldSignalTypeData.value(), worldSignalTypeData.value2());
            map2.put(MAX_VALUE, lookupSupport22);
          }

          lookupSupport22.enabled = true;
          lookupSupport22.value3 = worldSignalTypeData.value();
          lookupSupport22.value4 = worldSignalTypeData.value2();
          lookupSupport22.amount2 =
              Math.min(48.0F, 16.0F + (worldSignalTypeData.set().size() - 1) * 8.0F);
          lookupSupport22.amount4 = computeFloat(worldSignalTypeData.value4(), intValue);
        }
      }
    } else {
      for (WorldSignalTypeData3 worldSignalTypeData3 :
          (java.lang.Iterable<WorldSignalTypeData3>)
              (java.lang.Iterable<?>) (suspiciousChunkScanner.getList())) {
        LookupSupport chatMessageBuilder =
            (bunnythekenzo.client.LookupSupport)
                (map.computeIfAbsent(
                    worldSignalTypeData3.timestamp(),
                    value2 -> {
                      return new LookupSupport();
                    }));
        chatMessageBuilder.enabled = true;
        chatMessageBuilder.amount2 = 1.0F;
      }
    }
  }

  public static float computeFloat(double doubleValue, int intValue) {
    if (intValue <= 0) {
      return 1.0F;
    }

    float floatValue = (float) ((doubleValue - intValue) / (intValue * 2.0));
    return 0.55F + 0.45F * Math.clamp(floatValue, 0.0F, 1.0F);
  }

  public static float computeFloat() {
    long timestamp2 = System.nanoTime();
    float floatValue = timestamp == 0L ? 0.016F : (float) (timestamp2 - timestamp) / 1.0E9F;
    timestamp = timestamp2;
    return Math.min(floatValue, 0.1F);
  }
}
