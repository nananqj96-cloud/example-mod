package bunnythekenzo.client;

import com.google.gson.Gson;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import net.minecraft.client.render.entity.model.BipedEntityModel;

public final class OrangeJusticeAnimation {
  public static final long timestamp = 29433L;
  public static final float amount = 121.6F;

  public static void update() {
    Objects.requireNonNull(OrangeJusticeAnimationSupport.orangeJusticeAnimationData);
  }

  public static void render(
      BipedEntityModel bipedEntityModel, float floatValue, float floatValue2) {
    OrangeJusticeAnimationData orangeJusticeAnimationData =
        OrangeJusticeAnimationSupport.orangeJusticeAnimationData;
    float floatValue3 =
        Math.clamp(
            floatValue * orangeJusticeAnimationData.count2(),
            0.0F,
            orangeJusticeAnimationData.amountArray().length - 1.0F);
    int index = (int) floatValue3;
    ((EmoteModelAccess) (Object) (bipedEntityModel))
        .getEmoteModelGeometry()
        .render(
            orangeJusticeAnimationData.amountArray()[index],
            orangeJusticeAnimationData
                .amountArray()[
                Math.min(index + 1, orangeJusticeAnimationData.amountArray().length - 1)],
            floatValue3 - index,
            floatValue2);
  }

  public static OrangeJusticeAnimationData getOrangeJusticeAnimationData() {
    try (InputStream inputStream =
            Objects.requireNonNull(
                OrangeJusticeAnimation.class.getResourceAsStream(
                    "/assets/bunnythekenzo/emotes/orange_justice.json"));
        InputStreamReader inputStreamReader =
            new InputStreamReader(inputStream, StandardCharsets.UTF_8); ) {
      OrangeJusticeAnimationData orangeJusticeAnimationData =
          new Gson().fromJson(inputStreamReader, OrangeJusticeAnimationData.class);
      if (orangeJusticeAnimationData.count() != 1
          || orangeJusticeAnimationData.count2() != 30
          || orangeJusticeAnimationData.amountArray().length != 883) {
        throw new IllegalStateException(DiagnosticTokenGenerator.tokenFor(1108));
      }

      for (float[] floatValues : orangeJusticeAnimationData.amountArray()) {
        if (floatValues.length != 43) {
          throw new IllegalStateException(DiagnosticTokenGenerator.tokenFor(1109));
        }

        for (float floatValue : floatValues) {
          if (!Float.isFinite(floatValue)) {
            throw new IllegalStateException(DiagnosticTokenGenerator.tokenFor(1110));
          }
        }
      }

      return orangeJusticeAnimationData;
    } catch (Exception failure) {
      throw new IllegalStateException(DiagnosticTokenGenerator.tokenFor(1111), failure);
    }
  }
}
