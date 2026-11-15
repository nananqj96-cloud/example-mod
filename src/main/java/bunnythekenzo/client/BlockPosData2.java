package bunnythekenzo.client;

public record BlockPosData2(BlockPosData blockPosData, int count, long timestamp) {

  public int computeInt() {
    return this.blockPosData.count();
  }

  public int computeInt2() {
    return this.blockPosData.count2();
  }

  public int computeInt3() {
    return this.blockPosData.count3();
  }

  public int computeInt4() {
    return this.blockPosData.count4();
  }

  public int computeInt5() {
    return this.blockPosData.count5();
  }

  public int F_() {
    return this.computeInt2() - this.count + 1;
  }
}
