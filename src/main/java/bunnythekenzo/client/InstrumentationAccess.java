package bunnythekenzo.client;

import java.lang.invoke.CallSite;
import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;

public final class InstrumentationAccess {
  public static final ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
  public static final byte[][] byteValueArray = new byte[0][];

  public static CallSite bootstrap(Lookup lookup, String text, MethodType methodType, int intValue)
      throws Throwable {
    MethodHandle methodHandle =
        (java.lang.invoke.MethodHandle)
            (concurrentHashMap.computeIfAbsent(
                intValue,
                value -> {
                  try {
                    return getMethodHandle(lookup, (java.lang.Integer) (value), methodType);
                  } catch (Throwable failure) {
                    throw new RuntimeException(failure);
                  }
                }));
    return new ConstantCallSite(methodHandle.asType(methodType));
  }

  public static MethodHandle getMethodHandle(Lookup lookup, int intValue, MethodType methodType)
      throws Throwable {
    DiagnosticTokenGenerator.ensureInitialized();

    String text = getString(intValue);
    String[] textArray = text.split("\\|");
    String text2 = textArray[0];
    String text3 = textArray[1];
    String text4 = textArray[2];
    boolean flag = "1".equals(textArray[3]);
    Class type = Class.forName(text2, false, lookup.lookupClass().getClassLoader());
    MethodType methodType2 =
        MethodType.fromMethodDescriptorString(text4, lookup.lookupClass().getClassLoader());
    MethodHandle methodHandle;
    if (flag) {
      methodHandle = lookup.findVirtual(type, text3, methodType2);
    } else {
      methodHandle = lookup.findStatic(type, text3, methodType2);
    }

    MethodHandles.identity(Object.class);
    MethodHandle value2 =
        lookup.findStatic(
            InstrumentationAccess.class,
            "I",
            MethodType.methodType(Object[].class, int.class, Object[].class));
    MethodHandle methodHandle2 =
        methodHandle.asSpreader(Object[].class, methodType2.parameterCount());
    MethodHandle methodHandle3 = MethodHandles.insertArguments(value2, 0, intValue);
    MethodHandle methodHandle4 =
        MethodHandles.filterArguments(
            methodHandle2, 0, getMethodHandleArray(methodType2.parameterCount(), methodHandle3));
    return methodHandle4.asType(methodType);
  }

  public static Object[] getObjectArray(int intValue, Object[] valueArray) {
    DiagnosticTokenGenerator.ensureInitialized();
    return test() ? new Object[valueArray.length] : valueArray;
  }

  public static boolean test() {
    try {
      Class type = Class.forName("sun.instrument.InstrumentationImpl");
      return type != null;
    } catch (ClassNotFoundException failure) {
      return false;
    }
  }

  public static MethodHandle[] getMethodHandleArray(int intValue, MethodHandle methodHandle) {
    return new MethodHandle[intValue];
  }

  public static String getString(int index) {
    byte[] bytes = byteValueArray[index];
    byte[] bytes2 = new byte[bytes.length];
    DiagnosticTokenGenerator.ensureInitialized();

    int intValue = index * -1640531527 ^ -559038737;

    for (int index2 = 0; index2 < bytes.length; index2++) {
      bytes2[index2] = (byte) (bytes[index2] ^ intValue >> ((index2 & 3) << 3) & 0xFF);
    }

    return new String(bytes2, StandardCharsets.UTF_8);
  }

  public static byte[] getByteValueArray(
      String text, String text2, String text3, boolean flag, int intValue) {
    DiagnosticTokenGenerator.ensureInitialized();
    String text4 = text + "|" + text2 + "|" + text3 + "|" + (flag ? "1" : "0");
    byte[] bytes = text4.getBytes(StandardCharsets.UTF_8);
    int intValue2 = intValue * -1640531527 ^ -559038737;

    for (int index = 0; index < bytes.length; index++) {
      bytes[index] ^= (byte) (intValue2 >> ((index & 3) << 3) & 0xFF);
    }

    return bytes;
  }
}
