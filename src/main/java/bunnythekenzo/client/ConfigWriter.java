package bunnythekenzo.client;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public final class ConfigWriter {
  public static final String string = "bunnythekenzo-config-writer";

  public static Object executor() {
    return Executors.newSingleThreadExecutor(
        runnable -> {
          Thread thread = new Thread(runnable, string);
          thread.setDaemon(true);
          return thread;
        });
  }

  public static Object getObject(Runnable runnable, Object value) {
    return CompletableFuture.runAsync(runnable, (Executor) value);
  }

  public static void apply(Object value) {
    ((CompletableFuture) value).join();
  }
}
