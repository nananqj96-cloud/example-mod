package bunnythekenzo.client.setting;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import bunnythekenzo.client.AnimationIntensitySupport;
import bunnythekenzo.client.ArgbColors;
import bunnythekenzo.client.BlockGalleryModel;
import bunnythekenzo.client.ClientAudio;
import bunnythekenzo.client.ColorPalette;
import bunnythekenzo.client.DiagnosticTokenGenerator;
import bunnythekenzo.client.EntityGalleryModel;
import bunnythekenzo.client.GuiDialogManager;
import bunnythekenzo.client.render.FontManager;
import bunnythekenzo.client.ui.GalleryModel;
import bunnythekenzo.client.ui.GlintGallery;
import bunnythekenzo.client.ui.SelectionGallery;

public final class SettingWidgetSupport extends SettingWidget {
  public static final float amount = 64.0F;
  public static final float amount2 = 22.0F;
  public static final float amount3 = 8.0F;
  public static final float amount4 = 3.0F;
  public static final int count = 10;
  public final SettingEntry settingEntry;
  public final AnimationIntensitySupport animationIntensitySupport =
      new AnimationIntensitySupport();

  public SettingWidgetSupport(BlockListSettingView settingEntry3) {
    super(settingEntry3);
    this.settingEntry = settingEntry3;
  }

  public SettingWidgetSupport(EntityTypeSettingView settingEntry3) {
    super(settingEntry3);
    this.settingEntry = settingEntry3;
  }

  public SettingWidgetSupport(ItemSettingView settingEntry3) {
    super(settingEntry3);
    this.settingEntry = settingEntry3;
  }

  public SettingWidgetSupport(GlintStyleSettingView settingEntry3) {
    super(settingEntry3);
    this.settingEntry = settingEntry3;
  }

  public SettingWidgetSupport(ItemReplacementSettingView settingEntry3) {
    super(settingEntry3);
    this.settingEntry = settingEntry3;
  }

  @Override
  public float computeFloat() {
    return !(this.settingEntry instanceof BlockListSettingView)
            && !(this.settingEntry instanceof EntityTypeSettingView)
        ? 52.0F
        : 64.0F;
  }

  @Override
  public float computeFloat2() {
    return this.amount3 + 17.0F;
  }

  public float computeFloat3() {
    return Math.max(0.0F, this.amount4 - 24.0F - 64.0F - 12.0F);
  }

  @Override
  public void render(FontManager fontManager, ColorPalette colorPalette, float floatValue) {
    fontManager.computeFloat(
        this.settingEntry.getName(),
        this.amount2 + 12.0F,
        this.computeFloat2(),
        12.5F,
        ArgbColors.lerp(colorPalette.computeInt17(), colorPalette.computeInt16(), floatValue),
        this.computeFloat3());
  }

  @Override
  public void render(
      FontManager fontManager,
      ColorPalette colorPalette,
      float floatValue,
      float floatValue2,
      float floatValue3) {
    fontManager.computeFloat(
        this.getString(),
        this.amount2 + 12.0F,
        this.amount3 + 34.0F,
        9.5F,
        colorPalette.computeInt18(),
        this.computeFloat3());
    this.apply(fontManager, colorPalette);
    this.render(fontManager, colorPalette, floatValue2, floatValue3);
  }

  public void apply(FontManager fontManager, ColorPalette colorPalette) {
    List list = this.getList();
    if (!list.isEmpty()) {
      float floatValue = this.amount2 + 12.0F;
      float floatValue2 = this.amount3 + 50.0F;

      int index;
      for (index = Math.min(list.size(), 10); index > 0; index--) {
        float floatValue3 = index * 11.0F - 3.0F;
        float floatValue4 =
            list.size() > index
                ? 4.0F + fontManager.computeFloat("+" + (list.size() - index), 9.5F)
                : 0.0F;
        if (floatValue3 + floatValue4 <= this.computeFloat3()) {
          break;
        }
      }

      for (int index2 = 0; index2 < index; index2++) {
        int[] intValues = (int[]) list.get(index2);
        float floatValue5 = floatValue + index2 * 11.0F;
        fontManager.render(
            floatValue5,
            floatValue2 - 4.0F,
            8.0F,
            8.0F,
            2.0F,
            ArgbColors.withOpacity(intValues[0], intValues[1] == 1 ? 1.0F : 0.3F));
      }

      if (list.size() > index) {
        float floatValue6 = index == 0 ? 0.0F : index * 11.0F - 3.0F + 4.0F;
        fontManager.computeFloat(
            "+" + (list.size() - index),
            floatValue + floatValue6,
            floatValue2,
            9.5F,
            colorPalette.computeInt19(),
            this.computeFloat3() - floatValue6);
      }
    }
  }

  public void render(
      FontManager fontManager, ColorPalette colorPalette, float floatValue, float floatValue2) {
    float floatValue3 = this.amount2 + this.amount4 - 12.0F - 64.0F;
    float floatValue4 = this.amount3 + (this.amount5 - 22.0F) / 2.0F;
    float value = this.animationIntensitySupport.computeFloat(this.test(floatValue, floatValue2));
    fontManager.render(
        floatValue3,
        floatValue4,
        64.0F,
        22.0F,
        7.0F,
        ArgbColors.lerp(colorPalette.computeInt10(), colorPalette.computeInt12(), value));
    fontManager.render(
        floatValue3,
        floatValue4,
        64.0F,
        22.0F,
        7.0F,
        1.0F,
        ArgbColors.lerp(colorPalette.computeInt14(), colorPalette.computeInt15(), value));
    fontManager.computeFloat2(
        this.settingEntry instanceof ItemReplacementSettingView ? "Manage" : "Choose",
        floatValue3 + 32.0F,
        floatValue4 + 11.0F,
        11.0F,
        ArgbColors.lerp(colorPalette.computeInt17(), colorPalette.computeInt(), value));
  }

  public boolean test(float floatValue, float floatValue2) {
    float floatValue3 = this.amount2 + this.amount4 - 12.0F - 64.0F;
    float floatValue4 = this.amount3 + (this.amount5 - 22.0F) / 2.0F;
    return floatValue >= floatValue3 - 3.0F
        && floatValue <= floatValue3 + 64.0F + 3.0F
        && floatValue2 >= floatValue4 - 4.0F
        && floatValue2 <= floatValue4 + 22.0F + 4.0F;
  }

  @Override
  public boolean test(float floatValue, float floatValue2, int intValue) {
    if (intValue == 0 && this.test4(floatValue, floatValue2)) {
      this.animationIntensitySupport.apply(true);
      ClientAudio.update5();
      if (this.settingEntry instanceof ItemReplacementSettingView toggleSettingViewSupport5) {
        GuiDialogManager.open(toggleSettingViewSupport5.getItemReplacementSetting());
      } else {
        GuiDialogManager.open(this.getGalleryModel());
      }

      return true;
    } else {
      return false;
    }
  }

  public String getString() {
    SettingEntry settingEntry = this.settingEntry;
    Objects.requireNonNull(settingEntry);
    SettingEntry settingEntry2 = settingEntry;
    return switch (bunnythekenzo.client.runtime.TypeDispatch.select(
        new Class<?>[] {
          BlockListSettingView.class,
          EntityTypeSettingView.class,
          ItemSettingView.class,
          GlintStyleSettingView.class,
          ItemReplacementSettingView.class
        },
        (Object) settingEntry2,
        0)) {
      case 0 -> {
        BlockListSettingView toggleSettingViewSupport82 = (BlockListSettingView) settingEntry2;
        yield toggleSettingViewSupport82.getString3();
      }
      case 1 -> {
        EntityTypeSettingView toggleSettingViewSupport32 = (EntityTypeSettingView) settingEntry2;
        yield toggleSettingViewSupport32.getString3();
      }
      case 2 -> {
        ItemSettingView toggleSettingViewSupport6 = (ItemSettingView) settingEntry2;
        yield toggleSettingViewSupport6.getString3();
      }
      case 3 -> {
        GlintStyleSettingView toggleSettingViewSupport7 = (GlintStyleSettingView) settingEntry2;
        yield toggleSettingViewSupport7.getString3();
      }
      case 4 -> {
        ItemReplacementSettingView toggleSettingViewSupport5 =
            (ItemReplacementSettingView) settingEntry2;
        yield toggleSettingViewSupport5.getString3();
      }
      default -> "";
    };
  }

  public List getList() {
    ArrayList<int[]> arrayList = new ArrayList<int[]>();
    SettingEntry settingEntry = this.settingEntry;
    Objects.requireNonNull(settingEntry);
    SettingEntry settingEntry2 = settingEntry;
    switch (bunnythekenzo.client.runtime.TypeDispatch.select(
        new Class<?>[] {
          BlockListSettingView.class, EntityTypeSettingView.class, ItemSettingView.class
        },
        (Object) settingEntry2,
        0)) {
      case 0:
        {
          BlockListSettingView toggleSettingViewSupport8 = (BlockListSettingView) settingEntry2;
          for (BlockFilterEntry choiceSettingSupport :
              (java.lang.Iterable<BlockFilterEntry>)
                  (java.lang.Iterable<?>)
                      (toggleSettingViewSupport8.getBlockListSetting().getList())) {
            arrayList.add(
                new int[] {
                  choiceSettingSupport.colorSetting.getInteger(),
                  (Boolean) choiceSettingSupport.enabledSetting.getValue() != false ? 1 : 0
                });
          }
          break;
        }
      case 1:
        {
          EntityTypeSettingView toggleSettingViewSupport3 = (EntityTypeSettingView) settingEntry2;
          for (EntityFilterEntry choiceSettingSupport2 :
              (java.lang.Iterable<EntityFilterEntry>)
                  (java.lang.Iterable<?>)
                      (toggleSettingViewSupport3.getEntityTypeSetting().getList())) {
            if (!((Boolean) choiceSettingSupport2.enabledSetting.getValue()).booleanValue())
              continue;
            arrayList.add(new int[] {choiceSettingSupport2.colorSetting.getInteger(), 1});
          }
          break;
        }
      case 2:
        {
          break;
        }
    }
    return arrayList;
  }

  public GalleryModel getGalleryModel() {
    SettingEntry settingEntry = this.settingEntry;
    Objects.requireNonNull(settingEntry);
    SettingEntry settingEntry2 = settingEntry;
    return switch (bunnythekenzo.client.runtime.TypeDispatch.select(
        new Class<?>[] {
          BlockListSettingView.class,
          EntityTypeSettingView.class,
          ItemSettingView.class,
          GlintStyleSettingView.class
        },
        (Object) settingEntry2,
        0)) {
      case 0 -> {
        BlockListSettingView toggleSettingViewSupport82 = (BlockListSettingView) settingEntry2;
        yield new BlockGalleryModel(
            toggleSettingViewSupport82.getBlockListSetting(),
            toggleSettingViewSupport82.getIntSupplier(),
            toggleSettingViewSupport82.getName());
      }
      case 1 -> {
        EntityTypeSettingView toggleSettingViewSupport32 = (EntityTypeSettingView) settingEntry2;
        yield new EntityGalleryModel(toggleSettingViewSupport32.getEntityTypeSetting());
      }
      case 2 -> {
        ItemSettingView toggleSettingViewSupport6 = (ItemSettingView) settingEntry2;
        yield new SelectionGallery(toggleSettingViewSupport6.getItemSetting());
      }
      case 3 -> {
        GlintStyleSettingView toggleSettingViewSupport7 = (GlintStyleSettingView) settingEntry2;
        yield new GlintGallery(toggleSettingViewSupport7.getGlintStyleSetting());
      }
      default -> throw new IllegalStateException(DiagnosticTokenGenerator.tokenFor(1612));
    };
  }
}
