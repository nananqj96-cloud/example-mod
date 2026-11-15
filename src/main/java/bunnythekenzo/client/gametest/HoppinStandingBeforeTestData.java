package bunnythekenzo.client.gametest;

public record HoppinStandingBeforeTestData(
    float amount, float amount2, float amount3, float amount4, float amount5) {

  public boolean test(HoppinStandingBeforeTestData hoppinStandingBeforeTestData) {
    ProtectionPerformanceTest.ensureInitialized();
    return Math.abs(this.amount - hoppinStandingBeforeTestData.amount) < 1.0E-5F
        && Math.abs(this.amount2 - hoppinStandingBeforeTestData.amount2) < 1.0E-5F
        && Math.abs(this.amount3 - hoppinStandingBeforeTestData.amount3) < 1.0E-5F
        && Math.abs(this.amount4 - hoppinStandingBeforeTestData.amount4) < 1.0E-5F
        && Math.abs(this.amount5 - hoppinStandingBeforeTestData.amount5) < 1.0E-5F;
  }
}
