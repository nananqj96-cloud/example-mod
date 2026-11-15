package bunnythekenzo.client;

public record ProfileArtworkData2(
    ProfileArtworkData[] profileArtworkDataArray,
    float amount,
    float amount2,
    float amount3,
    float amount4,
    int count,
    int count2) {

  public float computeFloat() {
    float floatValue = 0.0F;

    for (ProfileArtworkData profileArtworkData : this.profileArtworkDataArray) {
      floatValue += profileArtworkData.amount3();
    }

    return floatValue / this.profileArtworkDataArray.length;
  }
}
