package bunnythekenzo.client;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntConsumer;
import bunnythekenzo.client.render.FontManager;
import bunnythekenzo.client.render.IconCache;

public final class ModuleSearchSupport {
  public static final float amount = 12.0F;
  public static final float amount2 = 17.0F;
  public static final float amount3 = 14.0F;
  public static final float amount4 = 11.0F;
  public static final float amount5 = 6.0F;
  public final List list;
  public final List list2 = new ArrayList();
  public final List list3 = new ArrayList();
  public final List list4 = new ArrayList();
  public final StaggeredTransition staggeredTransition = new StaggeredTransition(30.0F, 380.0F);
  public final DampedSpring dampedSpring = new DampedSpring(240.0F, 22.0F, 0.0F);
  public final DampedSpring dampedSpring2 = DampedSpring.createQuick();
  public final float[] amountArray;
  public int count;
  public float amount6;
  public float amount7;
  public float amount8;
  public float amount9;

  public ModuleSearchSupport(List list5) {
    this.list = list5;
    this.amountArray = new float[list5.size()];

    for (int index = 0; index < list5.size(); index++) {
      this.list2.add(new AnimationIntensitySupport());
      this.list3.add(new ClickRippleEffects());
      this.list4.add(new ModuleSearchSupport6());
    }
  }

  public void update() {
    this.staggeredTransition.apply(this.list.size());
  }

  public void update2() {
    this.staggeredTransition.apply2(this.list.size());
  }

  public int getCount() {
    return this.count;
  }

  public CollectionData3 getCollectionData3() {
    return (CollectionData3) this.list.get(this.count);
  }

  public void apply(int intValue) {
    int count2 = Math.clamp(intValue, 0, this.list.size() - 1);
    if (count2 != this.count) {
      this.count = count2;
      this.dampedSpring.setTarget(count2);
    }
  }

  public void apply(int intValue, IntConsumer intConsumer) {
    int count2 = Math.clamp(intValue, 0, this.list.size() - 1);
    if (count2 != this.count) {
      ClientAudio.apply(Integer.signum(count2 - this.count));
      this.count = count2;
      this.dampedSpring.setTarget(count2);
      intConsumer.accept(count2);
    }
  }

  public void render(
      FontManager fontManager,
      float amount62,
      float amount72,
      float amount82,
      float amount92,
      float floatValue,
      float floatValue2) {
    this.amount6 = amount62;
    this.amount7 = amount72;
    this.amount8 = amount82;
    this.amount9 = amount92;
    ColorPalette instance = ColorPalette.getInstance();
    fontManager.render(amount62, amount72, amount82, amount92, 0.0F, instance.computeInt9());
    fontManager.render(
        amount62 + amount82 - 1.0F, amount72, 1.0F, amount92, 0.0F, instance.computeInt13());
    float floatValue3 = amount72 + 14.0F;

    for (int index = 0; index < this.list.size(); index++) {
      this.amountArray[index] = floatValue3 + index * 46.0F;
    }

    this.apply(fontManager, instance);

    for (int index2 = 0; index2 < this.list.size(); index2++) {
      this.renderItem(fontManager, instance, index2, floatValue, floatValue2);
    }
  }

  public void apply(FontManager fontManager, ColorPalette colorPalette) {
    float value = this.dampedSpring.advance();
    float floatValue = this.amountArray[0] + value * 46.0F;
    float floatValue2 = this.amount6 + 12.0F;
    float floatValue3 = this.amount8 - 24.0F;
    float value2 =
        this.staggeredTransition.computeFloat(
            Math.round(Math.clamp(value, 0.0F, this.list.size() - 1)));
    if (!(value2 <= 0.01F)) {
      int value3 = colorPalette.computeInt();
      float floatValue4 = Math.clamp(Math.abs(this.dampedSpring.getVelocity()) * 1.6F, 0.0F, 7.0F);
      float floatValue5 = floatValue - floatValue4 / 2.0F;
      float floatValue6 = 36.0F + floatValue4;
      fontManager.update2();
      fontManager.apply3(value2);
      fontManager.render(
          floatValue2,
          floatValue5,
          floatValue3,
          floatValue6,
          9.0F,
          7.0F,
          2.0F,
          ArgbColors.withOpacity(-16777216, 0.28F));
      fontManager.render(
          floatValue2,
          floatValue5,
          floatValue3,
          floatValue6,
          9.0F,
          colorPalette.computeInt12(),
          ArgbColors.withOpacity(-14936538, 0.92F),
          0.6F);
      fontManager.render(
          floatValue2,
          floatValue5,
          floatValue3,
          floatValue6,
          9.0F,
          1.0F,
          ArgbColors.withOpacity(value3, 0.34F));
      fontManager.render(floatValue2 + 1.5F, floatValue + 10.0F, 2.5F, 16.0F, 1.25F, value3);
      fontManager.update3();
    }
  }

  public void renderItem(
      FontManager fontManager,
      ColorPalette colorPalette,
      int index,
      float floatValue,
      float floatValue2) {
    float value = this.staggeredTransition.computeFloat(index);
    if (value <= 0.01F) {
      ((ModuleSearchSupport6) this.list4.get(index)).update();
    } else {
      float floatValue3 = this.amount6 + 12.0F;
      float floatValue4 = this.amountArray[index];
      float floatValue5 = this.amount8 - 24.0F;
      AnimationIntensitySupport animationIntensitySupport =
          (AnimationIntensitySupport) this.list2.get(index);
      float value2 =
          animationIntensitySupport.computeFloat(
              this.test(index, floatValue, floatValue2)
                  || animationIntensitySupport.computeFloat() > 0.05F
                      && ((ModuleSearchSupport6) this.list4.get(index))
                          .test(floatValue, floatValue2));
      boolean flag = index == this.count;
      CollectionData3 collectionData3 = (CollectionData3) this.list.get(index);
      int value3 = colorPalette.computeInt();
      fontManager.update2();
      fontManager.apply((1.0F - value) * -26.0F, 0.0F);
      fontManager.apply3(value);
      float floatValue6 =
          AnimationPreferences.isEnabled()
              ? value2 * 14.0F - animationIntensitySupport.computeFloat2() * 5.0F
              : 0.0F;
      ModuleSearchSupport7.render(
          fontManager,
          (ModuleSearchSupport6) this.list4.get(index),
          floatValue3,
          floatValue4,
          floatValue5,
          36.0F,
          2.0F + floatValue6 + (flag ? 1 : 0),
          0.0F,
          -0.012F,
          9.0F,
          3.5F,
          value2,
          () -> {
            fontManager.render(
                floatValue3,
                floatValue4,
                floatValue5,
                36.0F,
                9.0F,
                flag
                    ? colorPalette.computeInt12()
                    : ArgbColors.lerp(
                        colorPalette.computeInt9(), colorPalette.computeInt11(), value2 * 0.65F),
                colorPalette.computeInt6(),
                true);
            fontManager.render(
                floatValue3 + 0.5F,
                floatValue4 + 0.5F,
                floatValue5 - 1.0F,
                36.0F - 1.0F,
                8.5F,
                0.8F,
                flag
                    ? colorPalette.computeInt(0.5F)
                    : ArgbColors.withOpacity(-1, 0.09F + value2 * 0.17F),
                167772159,
                true);
            if (flag) {
              fontManager.render(
                  floatValue3 + 1.5F, floatValue4 + 10.0F, 2.0F, 36.0F - 20.0F, 1.0F, value3);
            }

            ClickRippleEffects clickRippleEffects = (ClickRippleEffects) this.list3.get(index);
            if (!clickRippleEffects.test()) {
              fontManager.update2();
              fontManager.render(floatValue3, floatValue4, floatValue5, 36.0F);
              clickRippleEffects.apply(
                  value4 -> {
                    fontManager.render2(
                        ((bunnythekenzo.client.CollectionData5) (value4)).amount(),
                        ((bunnythekenzo.client.CollectionData5) (value4)).amount2(),
                        ((bunnythekenzo.client.CollectionData5) (value4)).amount3(),
                        ArgbColors.withOpacity(
                            value3,
                            0.16F * ((bunnythekenzo.client.CollectionData5) (value4)).amount4()));
                  });
              fontManager.update3();
            }

            float floatValue7 = Math.max(value2 * 0.55F, flag ? 1.0F : 0.0F);
            int value5 = ArgbColors.lerp(colorPalette.computeInt18(), value3, floatValue7);
            float floatValue8 = 1.0F + floatValue7 * 0.1F;
            float floatValue9 = floatValue3 + 16.0F + 8.5F;
            float floatValue10 = floatValue4 + 36.0F / 2.0F;
            int intValue = IconCache.get(collectionData3.string2());
            if (intValue > 0) {
              fontManager.update2();
              fontManager.render2(floatValue9, floatValue10, floatValue8);
              fontManager.render(
                  intValue, floatValue9 - 8.5F, floatValue10 - 8.5F, 17.0F, 17.0F, value5);
              fontManager.update3();
            }

            int value6 =
                ArgbColors.lerp(
                    colorPalette.computeInt17(), colorPalette.computeInt16(), floatValue7);
            float floatValue11 = floatValue3 + 44.0F;
            float floatValue12 = floatValue3 + floatValue5 - 15.0F;
            float floatValue13 =
                collectionData3.enabled()
                    ? floatValue12 - 5.5F - 6.0F
                    : floatValue3 + floatValue5 - 12.0F;
            float floatValue14 = floatValue13 - floatValue11;
            float floatValue15 = 13.5F;
            float value7 = fontManager.computeFloat(collectionData3.string(), 13.5F);
            if (value7 > floatValue14) {
              floatValue15 = Math.max(11.0F, 13.5F * floatValue14 / value7);
            }

            while (floatValue15 > 11.0F
                && fontManager.computeFloat(collectionData3.string(), floatValue15)
                    > floatValue14) {
              floatValue15 = Math.max(11.0F, floatValue15 - 0.25F);
            }

            fontManager.computeFloat(
                collectionData3.string(),
                floatValue11,
                floatValue10 + 0.5F,
                floatValue15,
                value6,
                floatValue14);
            if (collectionData3.enabled()) {
              this.render(
                  fontManager, colorPalette, floatValue12, floatValue10, value3, floatValue7);
            }
          });
      fontManager.update3();
    }
  }

  public void render(
      FontManager fontManager,
      ColorPalette colorPalette,
      float floatValue,
      float floatValue2,
      int intValue,
      float floatValue3) {
    float floatValue4 = Math.clamp(this.dampedSpring2.advance(), 0.0F, 1.0F);
    int value =
        ArgbColors.lerp(
            ArgbColors.lerp(colorPalette.computeInt18(), intValue, floatValue3),
            colorPalette.computeInt23(),
            floatValue4);
    fontManager.render2(floatValue, floatValue2, 11.0F, 1.2F, value, floatValue4);
  }

  public void apply(boolean flag) {
    this.dampedSpring2.setTarget(flag ? 1.0F : 0.0F);
  }

  public boolean test(int index, float floatValue, float floatValue2) {
    if (this.staggeredTransition.computeFloat(index) < 0.98F) {
      return false;
    }

    ClientData3 clientData3 =
        ((ModuleSearchSupport6) this.list4.get(index)).getClientData3(floatValue, floatValue2);
    floatValue = clientData3.amount();
    floatValue2 = clientData3.amount2();
    float floatValue3 = this.amount6 + 12.0F;
    float floatValue4 = this.amount8 - 24.0F;
    float floatValue5 = this.amountArray[index];
    return floatValue >= floatValue3
        && floatValue <= floatValue3 + floatValue4
        && floatValue2 >= floatValue5
        && floatValue2 <= floatValue5 + 36.0F;
  }

  public boolean test(float floatValue, float floatValue2) {
    return floatValue >= this.amount6
        && floatValue <= this.amount6 + this.amount8
        && floatValue2 >= this.amount7
        && floatValue2 <= this.amount7 + this.amount9;
  }

  public boolean test(float floatValue, float floatValue2, IntConsumer intConsumer) {
    for (int index = 0; index < this.list.size(); index++) {
      if (this.test(index, floatValue, floatValue2)) {
        ((AnimationIntensitySupport) this.list2.get(index)).apply(true);
        ClientData3 clientData3 =
            ((ModuleSearchSupport6) this.list4.get(index)).getClientData3(floatValue, floatValue2);
        ((ClickRippleEffects) this.list3.get(index))
            .render(clientData3.amount(), clientData3.amount2(), this.amount8);
        if (index != this.count) {
          ClientAudio.apply(Integer.signum(index - this.count));
          this.count = index;
          this.dampedSpring.setTarget(index);
          intConsumer.accept(index);
        } else {
          ClientAudio.update5();
        }

        return true;
      }
    }

    return false;
  }

  public void update3() {
    for (AnimationIntensitySupport animationIntensitySupport :
        (java.lang.Iterable<AnimationIntensitySupport>) (java.lang.Iterable<?>) (this.list2)) {
      animationIntensitySupport.apply(false);
    }
  }

  public void apply2(int intValue, IntConsumer intConsumer) {
    int intValue2 = Math.floorMod(this.count + intValue, this.list.size());
    this.apply(intValue2, intConsumer);
  }
}
