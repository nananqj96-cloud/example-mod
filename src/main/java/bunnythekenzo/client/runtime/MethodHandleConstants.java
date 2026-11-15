package bunnythekenzo.client.runtime;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public final class MethodHandleConstants {
  private MethodHandleConstants() {}

  public static MethodHandle constant(
      MethodHandles.Lookup caller, int kind, Class<?> owner, String name, String descriptor) {
    try {
      ClassLoader loader = caller.lookupClass().getClassLoader();
      if (kind <= 4) {
        Class<?> type =
            MethodType.fromMethodDescriptorString("()" + descriptor, loader).returnType();
        return switch (kind) {
          case 1 -> caller.findGetter(owner, name, type);
          case 2 -> caller.findStaticGetter(owner, name, type);
          case 3 -> caller.findSetter(owner, name, type);
          case 4 -> caller.findStaticSetter(owner, name, type);
          default -> throw new IllegalArgumentException("Method handle kind " + kind);
        };
      }
      MethodType type = MethodType.fromMethodDescriptorString(descriptor, loader);
      return switch (kind) {
        case 5, 9 -> caller.findVirtual(owner, name, type);
        case 6 -> caller.findStatic(owner, name, type);
        case 7 -> caller.findSpecial(owner, name, type, caller.lookupClass());
        case 8 -> caller.findConstructor(owner, type);
        default -> throw new IllegalArgumentException("Method handle kind " + kind);
      };
    } catch (ReflectiveOperationException failure) {
      throw new BootstrapMethodError(failure);
    }
  }
}
