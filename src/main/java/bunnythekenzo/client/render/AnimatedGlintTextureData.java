package bunnythekenzo.client.render;

import bunnythekenzo.client.ClientData2;
import bunnythekenzo.client.GlintStyle;

public record AnimatedGlintTextureData(
    GlintStyle glintStyle, ClientData2 clientData2, long o_, int count) {

  public boolean test(AnimatedGlintTextureData animatedGlintTextureData) {
    return animatedGlintTextureData != null
        && this.glintStyle == animatedGlintTextureData.glintStyle
        && this.clientData2.equals(animatedGlintTextureData.clientData2)
        && this.count == animatedGlintTextureData.count;
  }
}
