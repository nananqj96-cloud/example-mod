package bunnythekenzo.client;

import java.util.UUID;

public final class StaffWatchNotification {
  public static void apply(UUID uuid, String text, int intValue) {
    ChatMessageBuilder.getCountArray();
    if (ClientInitializer.getNotificationsHud() != null) {
      ClientInitializer.getNotificationsHud()
          .apply((text != null && !text.isBlank() ? text : "Staff") + " may be watching you");
    }
  }
}
