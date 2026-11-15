package bunnythekenzo.client.runtime;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;

public final class MappedReflection {
  private MappedReflection() {}

  public static Field requireField(
      Class<?> owner, String readableName, String intermediaryName, String intermediaryDescriptor) {
    try {
      return field(owner, readableName, intermediaryName, intermediaryDescriptor);
    } catch (ReflectiveOperationException failure) {
      throw new ExceptionInInitializerError(failure);
    }
  }

  public static Method requireMethod(
      Class<?> owner,
      String readableName,
      String intermediaryName,
      String intermediaryDescriptor,
      Class<?>... parameterTypes) {
    try {
      return method(owner, readableName, intermediaryName, intermediaryDescriptor, parameterTypes);
    } catch (ReflectiveOperationException failure) {
      throw new ExceptionInInitializerError(failure);
    }
  }

  public static Field field(
      Class<?> owner, String readableName, String intermediaryName, String intermediaryDescriptor)
      throws ReflectiveOperationException {
    MappingResolver mappings = FabricLoader.getInstance().getMappingResolver();
    String name = readableName;
    if (!mappings.getCurrentRuntimeNamespace().equals("named")) {
      String intermediaryOwner = mappings.unmapClassName("intermediary", owner.getName());
      name =
          mappings.mapFieldName(
              "intermediary", intermediaryOwner, intermediaryName, intermediaryDescriptor);
    }
    Field field = owner.getDeclaredField(name);
    field.setAccessible(true);
    return field;
  }

  public static Method method(
      Class<?> owner,
      String readableName,
      String intermediaryName,
      String intermediaryDescriptor,
      Class<?>... parameterTypes)
      throws ReflectiveOperationException {
    MappingResolver mappings = FabricLoader.getInstance().getMappingResolver();
    String name = readableName;
    if (!mappings.getCurrentRuntimeNamespace().equals("named")) {
      String intermediaryOwner = mappings.unmapClassName("intermediary", owner.getName());
      name =
          mappings.mapMethodName(
              "intermediary", intermediaryOwner, intermediaryName, intermediaryDescriptor);
    }
    Method method = owner.getDeclaredMethod(name, parameterTypes);
    method.setAccessible(true);
    return method;
  }
}
