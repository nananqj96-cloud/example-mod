package bunnythekenzo.client;

public enum WorldSignalType {
  AMETHYST(6.0, 16),
  KELP(2.0, 6),
  BAMBOO(2.0, 6),
  BERRIES(2.0, 5),
  VINES(2.0, 6),
  DRIPSTONE(2.0, 5);

  public final double weight;
  public final int count;

  WorldSignalType(double weight2, int count2) {
    this.weight = weight2;
    this.count = count2;
  }
}
