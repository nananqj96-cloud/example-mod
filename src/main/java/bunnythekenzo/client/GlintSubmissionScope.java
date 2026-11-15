package bunnythekenzo.client;

public final class GlintSubmissionScope {
  public static final ThreadLocal active =
      ThreadLocal.withInitial(
          () -> {
            return false;
          });

  public static boolean isActive() {
    return (Boolean) active.get();
  }

  public static boolean setActive(boolean flag) {
    boolean flag2 = (Boolean) active.get();
    active.set(flag);
    return flag2;
  }
}
