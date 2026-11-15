package bunnythekenzo.client;

public final class ConfigRepositorySupport {
  public final int count;
  public String string;
  public boolean enabled;
  public long timestamp;

  public ConfigRepositorySupport(int count2) {
    this.count = count2;
    this.string = ConfigRepository.getString(count2);
  }

  public int getCount() {
    return this.count;
  }

  public String getString() {
    return this.string;
  }

  public boolean isEnabled() {
    return this.enabled;
  }

  public long getTimestamp() {
    return this.timestamp;
  }
}
