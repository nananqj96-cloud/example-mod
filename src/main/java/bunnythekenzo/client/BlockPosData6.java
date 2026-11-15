package bunnythekenzo.client;

public record BlockPosData6(int _i, int _T, int _X, int _u) {

  public boolean test(int intValue, int intValue2) {
    return intValue >= this._i
        && intValue < this._i + this._X
        && intValue2 >= this._T
        && intValue2 < this._T + this._u;
  }
}
