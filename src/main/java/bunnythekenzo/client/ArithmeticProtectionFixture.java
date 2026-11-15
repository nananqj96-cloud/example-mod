package bunnythekenzo.client;

public class ArithmeticProtectionFixture {
  public static int computeInt(int left, int right) {
    return left + right;
  }

  public static int computeInt2(int left, int right) {
    DiagnosticTokenGenerator.ensureInitialized();

    return left + right;
  }
}
