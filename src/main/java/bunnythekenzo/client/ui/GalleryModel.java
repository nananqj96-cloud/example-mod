package bunnythekenzo.client.ui;

import java.util.List;

public interface GalleryModel {
  default float computeFloat() {
    return 44.0F;
  }

  default float computeFloat2() {
    return 44.0F;
  }

  default boolean test() {
    return false;
  }

  default void update() {}

  String getString();

  long computeLong();

  default String getString2() {
    return this.computeLong() + " on";
  }

  default String getString3() {
    return "Selected only";
  }

  default String getString4() {
    return "Right-click for colour";
  }

  List getList2();
}
