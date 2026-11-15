package bunnythekenzo.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public final class ClickRippleEffects {
  public static final float amount = 520.0F;
  public final List list = new ArrayList(2);

  public void render(float amount2, float amount22, float amount3) {
    this.list.add(new CollectionData4(amount2, amount22, amount3, System.nanoTime()));
    if (this.list.size() > 4) {
      this.list.removeFirst();
    }
  }

  public boolean test() {
    return this.list.isEmpty();
  }

  public void apply(Consumer consumer) {
    Iterator iterator = this.list.iterator();

    while (iterator.hasNext()) {
      CollectionData4 collectionData4 = (CollectionData4) iterator.next();
      float floatValue =
          (float) (System.nanoTime() - collectionData4.timestamp()) / 1000000.0F / 520.0F;
      if (floatValue >= 1.0F) {
        iterator.remove();
      } else {
        float amount3 = Easing.OUT_QUART.computeFloat(floatValue) * collectionData4.amount3();
        float amount4 = 1.0F - Easing.IN_QUAD.computeFloat(floatValue);
        consumer.accept(
            new CollectionData5(
                collectionData4.amount(), collectionData4.amount2(), amount3, amount4));
      }
    }
  }
}
