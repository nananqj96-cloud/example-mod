package bunnythekenzo.client.runtime;

public final class TypeDispatch {
  private TypeDispatch() {}

  public static int select(Class<?>[] labels, Object value, int restart) {
    if (value == null) return -1;
    if (restart < 0 || restart > labels.length) throw new IndexOutOfBoundsException(restart);
    for (int index = restart; index < labels.length; index++) {
      if (labels[index].isInstance(value)) return index;
    }
    return labels.length;
  }
}
