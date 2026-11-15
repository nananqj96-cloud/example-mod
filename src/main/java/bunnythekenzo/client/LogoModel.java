package bunnythekenzo.client;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public final class LogoModel {
  public static final float[][] amountArray = getAmountArray();

  public static float[][] getAmountArray() {
    InputStream resourceAsStream =
        LogoModel.class
            .getClassLoader()
            .getResourceAsStream("assets/bunnythekenzo/models/ui/logo_bunnythekenzo.json");
    if (resourceAsStream == null) {
      throw new IllegalStateException(DiagnosticTokenGenerator.tokenFor(1604));
    }

    try (InputStreamReader inputStreamReader =
        new InputStreamReader(resourceAsStream, StandardCharsets.UTF_8)) {
      LogoData2 logoData2 = new Gson().fromJson(inputStreamReader, LogoData2.class);
      if (logoData2 == null
          || logoData2.amountArray() == null
          || logoData2.amountArray().length == 0) {
        throw new IllegalStateException(DiagnosticTokenGenerator.tokenFor(1605));
      } else {
        return logoData2.amountArray();
      }
    } catch (IOException failure) {
      throw new IllegalStateException(DiagnosticTokenGenerator.tokenFor(1606), failure);
    }
  }
}
