package bunnythekenzo.client.runtime;

public final class ExceptionPropagation {
  private ExceptionPropagation() {}

  @SuppressWarnings("unchecked")
  public static <Failure extends Throwable> RuntimeException rethrow(Throwable failure)
      throws Failure {
    throw (Failure) failure;
  }
}
