package bunnythekenzo.client.setting;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class ChoiceSettingView extends SettingView {
  public final Supplier valueSupplier;
  public final Consumer valueConsumer;
  public final List choices;
  public int selectedIndex;

  public ChoiceSettingView(String text, String text2, int intValue, String[] textArray) {
    this(text, text2, intValue, List.of(textArray));
  }

  public ChoiceSettingView(String text, String text2, int intValue, List list2) {
    this(text, text2, intValue, list2, null, null);
  }

  public ChoiceSettingView(
      String text, String text2, int intValue, List list2, Supplier supplier2, Consumer consumer2) {
    super(text, text2);
    this.choices = list2.isEmpty() ? List.of("Default") : List.copyOf(list2);
    this.selectedIndex = Math.clamp(intValue, 0, this.choices.size() - 1);
    this.valueSupplier = supplier2;
    this.valueConsumer = consumer2;
  }

  @Override
  public boolean isBound() {
    return this.valueSupplier != null;
  }

  public List getChoices() {
    return this.choices;
  }

  public int getSelectedIndex() {
    if (this.valueSupplier == null) {
      return this.selectedIndex;
    }

    int intValue = this.choices.indexOf(this.valueSupplier.get());
    return intValue < 0 ? 0 : intValue;
  }

  public String getSelectedValue() {
    return (String) this.choices.get(this.getSelectedIndex());
  }

  public void selectIndex(int intValue) {
    int count2 = Math.clamp(intValue, 0, this.choices.size() - 1);
    if (this.valueConsumer != null) {
      this.valueConsumer.accept((String) this.choices.get(count2));
    } else {
      this.selectedIndex = count2;
    }
  }
}
