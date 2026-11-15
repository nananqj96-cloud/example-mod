package bunnythekenzo.client.ui;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import org.lwjgl.system.MemoryUtil;
import bunnythekenzo.client.GlintStyle;
import bunnythekenzo.client.render.FontManager;
import bunnythekenzo.client.setting.GlintStyleSetting;

public final class GlintGallery implements GalleryModel {
  public final GlintStyleSetting glintStyleSetting;
  public final List list;
  public static final int count = 144;
  public static final int count2 = 80;
  public ByteBuffer byteBuffer;
  public FontManager fontManager;

  public GlintGallery(GlintStyleSetting glintStyleSetting2) {
    this.glintStyleSetting = glintStyleSetting2;
    this.list =
        Arrays.stream(GlintStyle.values())
            .map(
                glintStyle -> {
                  return new GlintGallerySupport(this, glintStyle);
                })
            .toList();
  }

  @Override
  public String getString() {
    return "Custom Glint";
  }

  @Override
  public long computeLong() {
    return 1L;
  }

  @Override
  public String getString2() {
    return (String) this.glintStyleSetting.getValue();
  }

  @Override
  public String getString3() {
    return "Current only";
  }

  @Override
  public String getString4() {
    return "Click to apply";
  }

  @Override
  public float computeFloat() {
    return 147.0F;
  }

  @Override
  public float computeFloat2() {
    return 92.0F;
  }

  @Override
  public boolean test() {
    return true;
  }

  @Override
  public List getList2() {
    return this.list;
  }

  @Override
  public void update() {
    for (GalleryItem galleryItem :
        (java.lang.Iterable<GalleryItem>) (java.lang.Iterable<?>) (this.list)) {
      GlintGallerySupport glintGallerySupport = (GlintGallerySupport) galleryItem;
      if (this.fontManager != null) {
        this.fontManager.apply(glintGallerySupport.count);
      }

      glintGallerySupport.count = 0;
    }

    if (this.byteBuffer != null) {
      MemoryUtil.memFree(this.byteBuffer);
    }

    this.byteBuffer = null;
    this.fontManager = null;
  }
}
