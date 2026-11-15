package bunnythekenzo.client;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public final class ItemWorthService {
  public static final URI uRI = URI.create("https://donutsmpstats.com/api/worth");
  public static final long timestamp = TimeUnit.MINUTES.toMillis(30L);
  public static final long timestamp2 = TimeUnit.MINUTES.toMillis(1L);
  public static final HttpClient httpClient =
      HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5L)).build();
  public static final AtomicBoolean atomicBoolean = new AtomicBoolean();
  public static volatile Map map = Map.of();
  public static volatile long timestamp3;
  public static ClientInitializer[] clientInitializerArray;
  public static final long timestamp4 = 4245165024194068580L;

  public static void refreshIfStale() {
    getClientInitializerArray();
    long timestamp32 = System.currentTimeMillis();
    long longValue = map.isEmpty() ? timestamp2 : timestamp;
    if (timestamp32 - timestamp3 >= longValue && atomicBoolean.compareAndSet(false, true)) {
      timestamp3 = timestamp32;
      HttpRequest httpRequest =
          HttpRequest.newBuilder(uRI)
              .timeout(Duration.ofSeconds(8L))
              .header("Accept", "application/json")
              .header("User-Agent", "bunnythekenzo/2.0 Fake-Loot-Drop")
              .GET()
              .build();
      httpClient
          .sendAsync(httpRequest, BodyHandlers.ofString())
          .orTimeout(10L, TimeUnit.SECONDS)
          .whenComplete(
              (httpResponse, throwable) -> {
                getClientInitializerArray();

                try {
                  if (throwable == null
                      && httpResponse != null
                      && httpResponse.statusCode() / 100 == 2) {
                    Map map2 = getMap(httpResponse.body());
                    if (!map2.isEmpty()) {
                      map = Map.copyOf(map2);
                    }

                    return;
                  }

                  ClientInitializer.logger.debug("Donut worth refresh unavailable", throwable);
                } catch (RuntimeException failure) {
                  ClientInitializer.logger.debug("Could not parse Donut worth response", failure);
                  return;
                } finally {
                  atomicBoolean.set(false);
                }
              });
    }
  }

  public static Map getMap(String text) {
    getClientInitializerArray();
    HashMap hashMap = new HashMap();
    JsonObject asJsonObject = JsonParser.parseString(text).getAsJsonObject();
    if (asJsonObject.has("items") && asJsonObject.get("items").isJsonArray()) {
      for (JsonElement jsonElement : asJsonObject.getAsJsonArray("items")) {
        if (jsonElement.isJsonObject()) {
          JsonObject asJsonObject2 = jsonElement.getAsJsonObject();
          if (asJsonObject2.has("id") && asJsonObject2.has("worth")) {
            double asDouble = asJsonObject2.get("worth").getAsDouble();
            if (Double.isFinite(asDouble) && asDouble >= 0.0) {
              hashMap.put(asJsonObject2.get("id").getAsString(), asDouble);
            }
          }
        }
      }

      ClientInitializer.ensureInitialized();
      return hashMap;
    } else {
      return hashMap;
    }
  }

  public static String getString(Item item, int intValue) {
    getClientInitializerArray();
    Identifier id = Registries.ITEM.getId(item);
    if (id == null) {
      return null;
    }

    Double doubleValue = (Double) map.get(id.getPath());
    return doubleValue == null
        ? null
        : "$" + CompactNumberFormat.getString(doubleValue * Math.max(1, intValue));
  }

  static {
    ClientInitializer[] clientInitializerArray2 = new ClientInitializer[1];

    setClientInitializerArray(clientInitializerArray2);
  }

  public static void setClientInitializerArray(ClientInitializer[] clientInitializerArray2) {
    clientInitializerArray = clientInitializerArray2;
  }

  public static ClientInitializer[] getClientInitializerArray() {
    return clientInitializerArray;
  }
}
