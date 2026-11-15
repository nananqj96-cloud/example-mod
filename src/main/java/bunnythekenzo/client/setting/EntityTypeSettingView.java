package bunnythekenzo.client.setting;

public final class EntityTypeSettingView extends SettingView {
  public final EntityTypeSetting entityTypeSetting;

  public EntityTypeSettingView(EntityTypeSetting entityTypeSetting2) {
    super(entityTypeSetting2.getName(), entityTypeSetting2.getDescription());
    this.entityTypeSetting = entityTypeSetting2;
  }

  @Override
  public boolean isBound() {
    return true;
  }

  public EntityTypeSetting getEntityTypeSetting() {
    return this.entityTypeSetting;
  }

  public String getString3() {
    long longValue = this.entityTypeSetting.enabledCount();
    int value = this.entityTypeSetting.computeInt();
    return value == 0 ? "Nothing to choose" : longValue + " of " + value + " on";
  }
}
