package bunnythekenzo.client.ui;

import java.util.Locale;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.lwjgl.system.MemoryUtil;
import bunnythekenzo.client.ClientData2;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.GlintColorSampler;
import bunnythekenzo.client.GlintStyle;
import bunnythekenzo.client.module.CustomGlintModule;
import bunnythekenzo.client.render.FontManager;
import bunnythekenzo.client.setting.ColorSetting;

public final class GlintGallerySupport implements GalleryItem {
  public final GlintStyle glintStyle;
  public int count;
  public GlintGalleryData glintGalleryData;
  public long timestamp;
  public final GlintGallery glintGallery;

  public GlintGallerySupport(GlintGallery glintGallery2, GlintStyle glintStyle2) {
    this.glintGallery = glintGallery2;
    this.glintStyle = glintStyle2;
  }

  @Override
  public ItemStack getItemStack() {
    return ItemStack.EMPTY;
  }

  @Override
  public Identifier getIdentifier() {
    return null;
  }

  @Override
  public String getString2() {
    return this.glintStyle.label;
  }

  @Override
  public String getString() {
    return this.glintStyle.description;
  }

  @Override
  public boolean test(String text) {
    return (this.glintStyle.label + " " + this.glintStyle.description)
        .toLowerCase(Locale.ROOT)
        .contains(text);
  }

  @Override
  public boolean test() {
    return this.test3();
  }

  @Override
  public boolean test2() {
    return this.test3();
  }

  @Override
  public boolean test3() {
    return this.glintGallery.glintStyleSetting.isSelected(this.glintStyle.label);
  }

  @Override
  public int computeInt() {
    return this.glintStyle.bright;
  }

  @Override
  public void update() {
    this.glintGallery.glintStyleSetting.apply(this.glintStyle.label);
  }

  @Override
  public ColorSetting getColorSetting() {
    return null;
  }

  @Override
  public boolean test(
      FontManager fontManager,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4) {
    this.glintGallery.fontManager = fontManager;
    CustomGlintModule customGlintModule = ClientInitializer.getModuleManager().customGlintModule;
    double value = customGlintModule.computeDouble();
    ClientData2 clientData2 = customGlintModule.getClientData2(this.glintStyle);
    int int2 = customGlintModule.computeInt2();
    GlintGalleryData glintGalleryData2 =
        new GlintGalleryData(
            (long) Math.floor(value * 20.0), clientData2, int2, customGlintModule.computeFloat2());
    if (this.count == 0
        || !glintGalleryData2.equals(this.glintGalleryData)
            && System.nanoTime() >= this.timestamp) {
      if (this.glintGallery.byteBuffer == null) {
        this.glintGallery.byteBuffer = MemoryUtil.memAlloc(46080);
      }

      this.glintGallery.byteBuffer.clear();

      for (int index = 0; index < 80; index++) {
        for (int index2 = 0; index2 < 144; index2++) {
          int value2 =
              GlintColorSampler.computeInt(
                  this.glintStyle,
                  (index2 + 0.5F) / 144.0F / clientData2.amount(),
                  (index + 0.5F) / 80.0F / clientData2.amount(),
                  value,
                  customGlintModule.computeFloat2(),
                  clientData2);
          if (this.glintStyle == GlintStyle.DEFAULT) {
            int intValue = value2 & 0xFF;
            value2 =
                0xFF000000
                    | (int2 >> 16 & 0xFF) * intValue / 255 << 16
                    | (int2 >> 8 & 0xFF) * intValue / 255 << 8
                    | (int2 & 0xFF) * intValue / 255;
          }

          this.glintGallery
              .byteBuffer
              .put((byte) (value2 >> 16))
              .put((byte) (value2 >> 8))
              .put((byte) value2)
              .put((byte) -1);
        }
      }

      this.glintGallery.byteBuffer.flip();
      if (this.count == 0) {
        this.count = fontManager.computeInt(144, 80, this.glintGallery.byteBuffer);
      } else {
        fontManager.apply(this.count, this.glintGallery.byteBuffer);
      }

      this.glintGalleryData = glintGalleryData2;
      this.timestamp = System.nanoTime() + 50000000L;
    }

    fontManager.render(this.count, floatValue, floatValue2, floatValue3, floatValue4, 1.0F, 5.0F);
    return true;
  }
}
