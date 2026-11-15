package bunnythekenzo.client.render;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.nanovg.NanoVGGL3;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import bunnythekenzo.client.ArgbColors;
import bunnythekenzo.client.ClientInitializer;
import bunnythekenzo.client.DiagnosticTokenGenerator;

public final class FontManager {
  public static final String string = "display";
  public static final String string2 = "mono";
  public static final int count = 1;
  public static final int count2 = 2;
  public static final int count3 = 4;
  public static FontManager fontManager;
  public final long timestamp;
  public final boolean enabled;
  public final boolean enabled2;
  public final List list = new ArrayList();
  public final Set set = new HashSet();
  public String string3;
  public float amount;
  public final ArrayDeque arrayDeque = new ArrayDeque();
  public final ArrayDeque arrayDeque2 = new ArrayDeque();
  public float amount2 = 1.0F;
  public boolean enabled3;
  public float amount3 = 1.0F;

  public FontManager() {
    this.timestamp = NanoVGGL3.nvgCreate(1);
    if (this.timestamp == 0L) {
      throw new IllegalStateException(DiagnosticTokenGenerator.tokenFor(1403));
    }

    this.enabled = this.test("display", "assets/bunnythekenzo/fonts/Xuong-Regular.ttf");
    this.enabled2 = this.test("mono", "assets/bunnythekenzo/fonts/Monocraft.ttf");
    if (this.enabled && this.enabled2) {
      NanoVG.nvgAddFallbackFont(this.timestamp, "display", "mono");
    }

    if (this.enabled) {
      this.set.add("display");
    }

    if (this.enabled2) {
      this.set.add("mono");
    }

    this.string3 = this.enabled ? "display" : (this.enabled2 ? "mono" : null);
  }

  public void apply(SystemFontsData systemFontsData) {
    if (systemFontsData != null) {
      if (!this.set.contains(systemFontsData.string())) {
        boolean flag =
            systemFontsData.test()
                ? this.test(systemFontsData.string(), systemFontsData.string3())
                : this.test(systemFontsData.string(), systemFontsData.path());
        if (!flag) {
          return;
        }

        this.set.add(systemFontsData.string());
        if (this.enabled2 && !systemFontsData.string().equals("mono")) {
          NanoVG.nvgAddFallbackFont(this.timestamp, systemFontsData.string(), "mono");
        }
      }

      this.string3 = systemFontsData.string();
    }
  }

  public boolean test(String name, Path path) {
    try {
      byte[] allBytes = Files.readAllBytes(path);
      ByteBuffer data = MemoryUtil.memAlloc(allBytes.length);
      data.put(allBytes).flip();
      int intValue = NanoVG.nvgCreateFontMem(this.timestamp, name, data, false);
      if (intValue == -1) {
        MemoryUtil.memFree(data);
        ClientInitializer.logger.warn("NanoVG rejected the font {}", path);
        return false;
      } else {
        this.list.add(data);
        return true;
      }
    } catch (Exception failure) {
      ClientInitializer.logger.warn("Could not load the font {}", path, failure);
      return false;
    }
  }

  public static FontManager getFontManager() {
    if (fontManager == null) {
      fontManager = new FontManager();
    }

    return fontManager;
  }

  public static FontManager getInstance() {
    return fontManager;
  }

  public long getTimestamp() {
    return this.timestamp;
  }

  public boolean test() {
    return this.string3 != null;
  }

  public String getString() {
    return this.enabled2 ? "mono" : this.string3;
  }

  public boolean test(String name, String text) {
    try (InputStream resourceAsStream =
        FontManager.class.getClassLoader().getResourceAsStream(text)) {
      if (resourceAsStream == null) {
        ClientInitializer.logger.warn("Font resource missing: {}", text);
        return false;
      } else {
        byte[] allBytes = resourceAsStream.readAllBytes();
        ByteBuffer data = MemoryUtil.memAlloc(allBytes.length);
        data.put(allBytes).flip();
        int intValue = NanoVG.nvgCreateFontMem(this.timestamp, name, data, false);
        if (intValue == -1) {
          MemoryUtil.memFree(data);
          ClientInitializer.logger.warn("NanoVG rejected font {}", text);
          return false;
        } else {
          this.list.add(data);
          return true;
        }
      }
    } catch (IOException failure) {
      ClientInitializer.logger.error("Failed to load font {}", text, failure);
      return false;
    }
  }

  public void render(float windowWidth, float windowHeight, float devicePixelRatio) {
    this.arrayDeque.clear();
    this.arrayDeque2.clear();
    this.amount2 = 1.0F;
    this.enabled3 = false;
    this.amount3 = Math.max(0.1F, devicePixelRatio);
    NanoVG.nvgBeginFrame(this.timestamp, windowWidth, windowHeight, devicePixelRatio);
  }

  public void update() {
    NanoVG.nvgEndFrame(this.timestamp);
  }

  public void update2() {
    this.arrayDeque.push(this.amount2);
    this.arrayDeque2.push(this.enabled3);
    NanoVG.nvgSave(this.timestamp);
  }

  public void update3() {
    if (!this.arrayDeque2.isEmpty()) {
      this.enabled3 = (Boolean) this.arrayDeque2.pop();
    }

    if (!this.arrayDeque.isEmpty()) {
      this.amount2 = (Float) this.arrayDeque.pop();
      NanoVG.nvgGlobalAlpha(this.timestamp, this.amount2);
    }

    NanoVG.nvgRestore(this.timestamp);
  }

  public void apply(float x, float y) {
    NanoVG.nvgTranslate(this.timestamp, x, y);
  }

  public void apply(float x) {
    NanoVG.nvgScale(this.timestamp, x, x);
  }

  public void apply2(float x, float y) {
    NanoVG.nvgScale(this.timestamp, x, y);
  }

  public void apply2(float angle) {
    NanoVG.nvgRotate(this.timestamp, angle);
  }

  public void render2(float x, float y, float x2) {
    NanoVG.nvgTranslate(this.timestamp, x, y);
    NanoVG.nvgScale(this.timestamp, x2, x2);
    NanoVG.nvgTranslate(this.timestamp, -x, -y);
  }

  public void apply3(float floatValue) {
    this.amount2 = this.amount2 * Math.clamp(floatValue, 0.0F, 1.0F);
    NanoVG.nvgGlobalAlpha(this.timestamp, this.amount2);
  }

  public void setEnabled3(boolean enabled32) {
    this.enabled3 = enabled32;
  }

  public float getAmount2() {
    return this.amount2;
  }

  public void render(float x, float y, float w, float h) {
    NanoVG.nvgIntersectScissor(this.timestamp, x, y, w, h);
  }

  public void render(float x, float y, float w, float h, float r, int intValue) {
    try (MemoryStack memoryStack = MemoryStack.stackPush()) {
      NanoVG.nvgBeginPath(this.timestamp);
      NanoVG.nvgRoundedRect(this.timestamp, x, y, w, h, r);
      NanoVG.nvgFillColor(this.timestamp, getNVGColor(memoryStack, intValue));
      NanoVG.nvgFill(this.timestamp);
    }
  }

  public void render(
      float sx, float sy, float w, float h, float r, int intValue, int intValue2, boolean flag) {
    try (MemoryStack stack = MemoryStack.stackPush()) {
      NVGPaint __result = NVGPaint.malloc(stack);
      float ex = flag ? sx : sx + w;
      float ey = flag ? sy + h : sy;
      NanoVG.nvgLinearGradient(
          this.timestamp,
          sx,
          sy,
          ex,
          ey,
          getNVGColor(stack, intValue),
          getNVGColor(stack, intValue2),
          __result);
      NanoVG.nvgBeginPath(this.timestamp);
      NanoVG.nvgRoundedRect(this.timestamp, sx, sy, w, h, r);
      NanoVG.nvgFillPaint(this.timestamp, __result);
      NanoVG.nvgFill(this.timestamp);
    }
  }

  public void render(
      float x, float y, float w, float h, float r, int intValue, int intValue2, float floatValue) {
    float floatValue2 = x + w / 2.0F;
    float floatValue3 = y + h / 2.0F;
    float floatValue4 =
        (Math.abs((float) Math.cos(floatValue)) * w + Math.abs((float) Math.sin(floatValue)) * h)
            / 2.0F;
    float floatValue5 = (float) Math.cos(floatValue) * floatValue4;
    float floatValue6 = (float) Math.sin(floatValue) * floatValue4;

    try (MemoryStack stack = MemoryStack.stackPush()) {
      NVGPaint __result = NVGPaint.malloc(stack);
      NanoVG.nvgLinearGradient(
          this.timestamp,
          floatValue2 - floatValue5,
          floatValue3 - floatValue6,
          floatValue2 + floatValue5,
          floatValue3 + floatValue6,
          getNVGColor(stack, intValue),
          getNVGColor(stack, intValue2),
          __result);
      NanoVG.nvgBeginPath(this.timestamp);
      NanoVG.nvgRoundedRect(this.timestamp, x, y, w, h, r);
      NanoVG.nvgFillPaint(this.timestamp, __result);
      NanoVG.nvgFill(this.timestamp);
    }
  }

  public void render(
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      float floatValue7,
      float floatValue8,
      int intValue,
      int intValue2) {
    this.render(
        floatValue,
        floatValue2,
        floatValue3,
        floatValue4,
        floatValue5,
        floatValue6,
        floatValue7,
        floatValue8,
        intValue,
        intValue2,
        true);
  }

  public void render(
      float sx,
      float sy,
      float w,
      float h,
      float radTopLeft,
      float radTopRight,
      float radBottomRight,
      float radBottomLeft,
      int intValue,
      int intValue2,
      boolean flag) {
    try (MemoryStack stack = MemoryStack.stackPush()) {
      NVGPaint __result = NVGPaint.malloc(stack);
      float ex = flag ? sx : sx + w;
      float ey = flag ? sy + h : sy;
      NanoVG.nvgLinearGradient(
          this.timestamp,
          sx,
          sy,
          ex,
          ey,
          getNVGColor(stack, intValue),
          getNVGColor(stack, intValue2),
          __result);
      NanoVG.nvgBeginPath(this.timestamp);
      NanoVG.nvgRoundedRectVarying(
          this.timestamp, sx, sy, w, h, radTopLeft, radTopRight, radBottomRight, radBottomLeft);
      NanoVG.nvgFillPaint(this.timestamp, __result);
      NanoVG.nvgFill(this.timestamp);
    }
  }

  public void render(
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float r,
      float size,
      int intValue) {
    try (MemoryStack memoryStack = MemoryStack.stackPush()) {
      NanoVG.nvgBeginPath(this.timestamp);
      NanoVG.nvgRoundedRect(
          this.timestamp,
          floatValue + size / 2.0F,
          floatValue2 + size / 2.0F,
          floatValue3 - size,
          floatValue4 - size,
          r);
      NanoVG.nvgStrokeColor(this.timestamp, getNVGColor(memoryStack, intValue));
      NanoVG.nvgStrokeWidth(this.timestamp, size);
      NanoVG.nvgStroke(this.timestamp);
    }
  }

  public void render(
      float sx,
      float sy,
      float floatValue,
      float floatValue2,
      float r,
      float size,
      int intValue,
      int intValue2,
      boolean flag) {
    try (MemoryStack stack = MemoryStack.stackPush()) {
      NVGPaint __result = NVGPaint.malloc(stack);
      float ex = flag ? sx : sx + floatValue;
      float ey = flag ? sy + floatValue2 : sy;
      NanoVG.nvgLinearGradient(
          this.timestamp,
          sx,
          sy,
          ex,
          ey,
          getNVGColor(stack, intValue),
          getNVGColor(stack, intValue2),
          __result);
      NanoVG.nvgBeginPath(this.timestamp);
      NanoVG.nvgRoundedRect(
          this.timestamp,
          sx + size / 2.0F,
          sy + size / 2.0F,
          floatValue - size,
          floatValue2 - size,
          r);
      NanoVG.nvgStrokePaint(this.timestamp, __result);
      NanoVG.nvgStrokeWidth(this.timestamp, size);
      NanoVG.nvgStroke(this.timestamp);
    }
  }

  public void render2(float x, float y, float w, float h, float r, float floatValue, int intValue) {
    try (MemoryStack stack = MemoryStack.stackPush()) {
      NVGPaint __result = NVGPaint.malloc(stack);
      NanoVG.nvgBoxGradient(
          this.timestamp,
          x,
          y,
          w,
          h,
          r,
          floatValue * 2.0F,
          getNVGColor(stack, intValue),
          getNVGColor(stack, ArgbColors.withAlpha(intValue, 0)),
          __result);
      NanoVG.nvgBeginPath(this.timestamp);
      NanoVG.nvgRoundedRect(
          this.timestamp,
          x - floatValue,
          y - floatValue,
          w + floatValue * 2.0F,
          h + floatValue * 2.0F,
          r + floatValue);
      NanoVG.nvgFillPaint(this.timestamp, __result);
      NanoVG.nvgFill(this.timestamp);
    }
  }

  public void render(
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      float floatValue7,
      int intValue) {
    this.render2(
        floatValue,
        floatValue2 + floatValue7,
        floatValue3,
        floatValue4,
        floatValue5,
        floatValue6,
        intValue);
  }

  public void render3(float x, float y, float x2, float y2, float x3, float y3, int intValue) {
    try (MemoryStack memoryStack = MemoryStack.stackPush()) {
      NanoVG.nvgBeginPath(this.timestamp);
      NanoVG.nvgMoveTo(this.timestamp, x, y);
      NanoVG.nvgLineTo(this.timestamp, x2, y2);
      NanoVG.nvgLineTo(this.timestamp, x3, y3);
      NanoVG.nvgClosePath(this.timestamp);
      NanoVG.nvgFillColor(this.timestamp, getNVGColor(memoryStack, intValue));
      NanoVG.nvgFill(this.timestamp);
    }
  }

  public void render(float x, float y, float floatValue, int intValue) {
    try (MemoryStack memoryStack = MemoryStack.stackPush()) {
      NanoVG.nvgBeginPath(this.timestamp);
      NanoVG.nvgMoveTo(this.timestamp, x, y - floatValue);
      NanoVG.nvgLineTo(this.timestamp, x + floatValue, y);
      NanoVG.nvgLineTo(this.timestamp, x, y + floatValue);
      NanoVG.nvgLineTo(this.timestamp, x - floatValue, y);
      NanoVG.nvgClosePath(this.timestamp);
      NanoVG.nvgFillColor(this.timestamp, getNVGColor(memoryStack, intValue));
      NanoVG.nvgFill(this.timestamp);
    }
  }

  public void render(float x, float y, float floatValue, float size, int intValue) {
    try (MemoryStack memoryStack = MemoryStack.stackPush()) {
      NanoVG.nvgBeginPath(this.timestamp);
      NanoVG.nvgMoveTo(this.timestamp, x, y - floatValue);
      NanoVG.nvgLineTo(this.timestamp, x + floatValue, y);
      NanoVG.nvgLineTo(this.timestamp, x, y + floatValue);
      NanoVG.nvgLineTo(this.timestamp, x - floatValue, y);
      NanoVG.nvgClosePath(this.timestamp);
      NanoVG.nvgStrokeColor(this.timestamp, getNVGColor(memoryStack, intValue));
      NanoVG.nvgStrokeWidth(this.timestamp, size);
      NanoVG.nvgLineJoin(this.timestamp, 1);
      NanoVG.nvgStroke(this.timestamp);
    }
  }

  public void render(
      float cx,
      float cy,
      float outr,
      float floatValue,
      float floatValue2,
      int intValue,
      int intValue2) {
    float floatValue3 = Math.min(floatValue2, 3.0787609F) / 2.0F;

    try (MemoryStack stack = MemoryStack.stackPush()) {
      NVGPaint __result = NVGPaint.malloc(stack);
      NanoVG.nvgRadialGradient(
          this.timestamp,
          cx,
          cy,
          0.0F,
          outr,
          getNVGColor(stack, intValue),
          getNVGColor(stack, intValue2),
          __result);
      NanoVG.nvgBeginPath(this.timestamp);
      NanoVG.nvgMoveTo(this.timestamp, cx, cy);
      NanoVG.nvgArc(
          this.timestamp, cx, cy, outr, floatValue - floatValue3, floatValue + floatValue3, 2);
      NanoVG.nvgClosePath(this.timestamp);
      NanoVG.nvgFillPaint(this.timestamp, __result);
      NanoVG.nvgFill(this.timestamp);
    }
  }

  public void render(
      float[] floatValues, float[] floatValues2, int intValue, float size, int intValue2) {
    if (intValue >= 2) {
      try (MemoryStack memoryStack = MemoryStack.stackPush()) {
        NanoVG.nvgBeginPath(this.timestamp);
        NanoVG.nvgMoveTo(this.timestamp, floatValues[0], floatValues2[0]);

        for (int index = 1; index < intValue; index++) {
          NanoVG.nvgLineTo(this.timestamp, floatValues[index], floatValues2[index]);
        }

        NanoVG.nvgStrokeColor(this.timestamp, getNVGColor(memoryStack, intValue2));
        NanoVG.nvgStrokeWidth(this.timestamp, size);
        NanoVG.nvgLineCap(this.timestamp, 1);
        NanoVG.nvgLineJoin(this.timestamp, 1);
        NanoVG.nvgStroke(this.timestamp);
      }
    }
  }

  public void render4(float cx, float cy, float r, float a0, float a1, float size, int intValue) {
    try (MemoryStack memoryStack = MemoryStack.stackPush()) {
      NanoVG.nvgBeginPath(this.timestamp);
      NanoVG.nvgArc(this.timestamp, cx, cy, r, a0, a1, 2);
      NanoVG.nvgStrokeColor(this.timestamp, getNVGColor(memoryStack, intValue));
      NanoVG.nvgStrokeWidth(this.timestamp, size);
      NanoVG.nvgLineCap(this.timestamp, 1);
      NanoVG.nvgStroke(this.timestamp);
    }
  }

  public void render2(float cx, float cy, float r, int intValue) {
    try (MemoryStack memoryStack = MemoryStack.stackPush()) {
      NanoVG.nvgBeginPath(this.timestamp);
      NanoVG.nvgCircle(this.timestamp, cx, cy, r);
      NanoVG.nvgFillColor(this.timestamp, getNVGColor(memoryStack, intValue));
      NanoVG.nvgFill(this.timestamp);
    }
  }

  public void render(
      int image,
      float ox,
      float oy,
      float ex,
      float ey,
      float x,
      float y,
      float w,
      float h,
      float alpha) {
    if (image > 0) {
      try (MemoryStack stack = MemoryStack.stackPush()) {
        NVGPaint __result = NVGPaint.malloc(stack);
        NanoVG.nvgImagePattern(this.timestamp, ox, oy, ex, ey, 0.0F, image, alpha, __result);
        NanoVG.nvgBeginPath(this.timestamp);
        NanoVG.nvgRect(this.timestamp, x, y, w, h);
        NanoVG.nvgFillPaint(this.timestamp, __result);
        NanoVG.nvgFill(this.timestamp);
      }
    }
  }

  public void render2(float cx, float cy, float r, float size, int intValue) {
    try (MemoryStack memoryStack = MemoryStack.stackPush()) {
      NanoVG.nvgBeginPath(this.timestamp);
      NanoVG.nvgCircle(this.timestamp, cx, cy, r);
      NanoVG.nvgStrokeColor(this.timestamp, getNVGColor(memoryStack, intValue));
      NanoVG.nvgStrokeWidth(this.timestamp, size);
      NanoVG.nvgStroke(this.timestamp);
    }
  }

  public void render3(float cx, float cy, float floatValue, float floatValue2, int intValue) {
    try (MemoryStack stack = MemoryStack.stackPush()) {
      NVGPaint __result = NVGPaint.malloc(stack);
      NanoVG.nvgRadialGradient(
          this.timestamp,
          cx,
          cy,
          floatValue * 0.25F,
          floatValue + floatValue2,
          getNVGColor(stack, intValue),
          getNVGColor(stack, ArgbColors.withAlpha(intValue, 0)),
          __result);
      NanoVG.nvgBeginPath(this.timestamp);
      NanoVG.nvgCircle(this.timestamp, cx, cy, floatValue + floatValue2);
      NanoVG.nvgFillPaint(this.timestamp, __result);
      NanoVG.nvgFill(this.timestamp);
    }
  }

  public void render2(float x, float y, float x2, float y2, float size, int intValue) {
    try (MemoryStack memoryStack = MemoryStack.stackPush()) {
      NanoVG.nvgBeginPath(this.timestamp);
      NanoVG.nvgMoveTo(this.timestamp, x, y);
      NanoVG.nvgLineTo(this.timestamp, x2, y2);
      NanoVG.nvgStrokeColor(this.timestamp, getNVGColor(memoryStack, intValue));
      NanoVG.nvgStrokeWidth(this.timestamp, size);
      NanoVG.nvgLineCap(this.timestamp, 1);
      NanoVG.nvgStroke(this.timestamp);
    }
  }

  public void render(float x, float y, float floatValue, float size, int intValue, boolean flag) {
    try (MemoryStack memoryStack = MemoryStack.stackPush()) {
      NanoVG.nvgBeginPath(this.timestamp);
      if (flag) {
        NanoVG.nvgMoveTo(this.timestamp, x - floatValue, y - floatValue / 2.0F);
        NanoVG.nvgLineTo(this.timestamp, x, y + floatValue / 2.0F);
        NanoVG.nvgLineTo(this.timestamp, x + floatValue, y - floatValue / 2.0F);
      } else {
        NanoVG.nvgMoveTo(this.timestamp, x - floatValue / 2.0F, y - floatValue);
        NanoVG.nvgLineTo(this.timestamp, x + floatValue / 2.0F, y);
        NanoVG.nvgLineTo(this.timestamp, x - floatValue / 2.0F, y + floatValue);
      }

      NanoVG.nvgStrokeColor(this.timestamp, getNVGColor(memoryStack, intValue));
      NanoVG.nvgStrokeWidth(this.timestamp, size);
      NanoVG.nvgLineCap(this.timestamp, 1);
      NanoVG.nvgLineJoin(this.timestamp, 1);
      NanoVG.nvgStroke(this.timestamp);
    }
  }

  public void render4(
      float floatValue, float floatValue2, float floatValue3, float floatValue4, int intValue) {
    this.render(floatValue, floatValue2, floatValue3, floatValue4, intValue, 1.0F);
  }

  public void render(
      float floatValue,
      float floatValue2,
      float floatValue3,
      float size,
      int intValue,
      float floatValue4) {
    float floatValue5 = Math.clamp(floatValue4, 0.0F, 1.0F);
    if (!(floatValue5 <= 0.001F)) {
      float x = floatValue + floatValue3 * 0.24F;
      float y = floatValue2 + floatValue3 * 0.53F;
      float x2 = floatValue + floatValue3 * 0.43F;
      float y2 = floatValue2 + floatValue3 * 0.72F;
      float floatValue6 = floatValue + floatValue3 * 0.77F;
      float floatValue7 = floatValue2 + floatValue3 * 0.31F;

      try (MemoryStack memoryStack = MemoryStack.stackPush()) {
        NanoVG.nvgBeginPath(this.timestamp);
        NanoVG.nvgMoveTo(this.timestamp, x, y);
        if (floatValue5 <= 0.35F) {
          float floatValue8 = floatValue5 / 0.35F;
          NanoVG.nvgLineTo(this.timestamp, x + (x2 - x) * floatValue8, y + (y2 - y) * floatValue8);
        } else {
          float floatValue9 = (floatValue5 - 0.35F) / 0.65F;
          NanoVG.nvgLineTo(this.timestamp, x2, y2);
          NanoVG.nvgLineTo(
              this.timestamp,
              x2 + (floatValue6 - x2) * floatValue9,
              y2 + (floatValue7 - y2) * floatValue9);
        }

        NanoVG.nvgStrokeColor(this.timestamp, getNVGColor(memoryStack, intValue));
        NanoVG.nvgStrokeWidth(this.timestamp, size);
        NanoVG.nvgLineCap(this.timestamp, 1);
        NanoVG.nvgLineJoin(this.timestamp, 1);
        NanoVG.nvgStroke(this.timestamp);
      }
    }
  }

  public void render2(
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      int intValue,
      float floatValue5) {
    float floatValue6 = Math.clamp(floatValue5, 0.0F, 1.0F);
    float floatValue7 = floatValue3 * 0.74F;
    float floatValue8 = floatValue3 * 0.56F;
    float floatValue9 = floatValue7 * 0.32F;
    float floatValue10 = floatValue2 + floatValue9 * 0.58F - floatValue8 / 2.0F;
    float floatValue11 = floatValue10 - floatValue9 * 0.16F - floatValue6 * floatValue3 * 0.11F;
    float floatValue12 = floatValue6 * 0.42F;
    float floatValue13 = floatValue + floatValue6 * floatValue3 * 0.075F;
    this.update2();
    this.apply(floatValue13, floatValue11);
    this.apply2(floatValue12);
    this.render(
        -floatValue9 - floatValue4,
        -floatValue9 - floatValue4,
        (floatValue9 + floatValue4) * 2.0F,
        floatValue9 + floatValue4);
    this.render2(0.0F, 0.0F, floatValue9, floatValue4, intValue);
    this.update3();
    float floatValue14 = Math.max(0.0F, floatValue10 + floatValue4 / 2.0F - floatValue11);
    if (floatValue14 > 0.4F) {
      this.update2();
      this.apply(floatValue13, floatValue11);
      this.apply2(floatValue12);
      this.render(
          -floatValue9 - floatValue4 / 2.0F,
          0.0F,
          floatValue4,
          floatValue14,
          floatValue4 / 2.0F,
          intValue);
      this.render(
          floatValue9 - floatValue4 / 2.0F,
          0.0F,
          floatValue4,
          floatValue14 * (1.0F - floatValue6),
          floatValue4 / 2.0F,
          intValue);
      this.update3();
    }

    this.render(
        floatValue - floatValue7 / 2.0F,
        floatValue10,
        floatValue7,
        floatValue8,
        floatValue3 * 0.11F,
        floatValue4,
        intValue);
    if (floatValue3 >= 16.0F) {
      this.render2(floatValue, floatValue10 + floatValue8 * 0.4F, floatValue4 * 1.15F, intValue);
      this.render(
          floatValue - floatValue4 / 2.0F,
          floatValue10 + floatValue8 * 0.4F,
          floatValue4,
          floatValue8 * 0.3F,
          floatValue4 / 2.0F,
          intValue);
    }
  }

  public void render5(
      float floatValue, float floatValue2, float floatValue3, float floatValue4, int intValue) {
    float floatValue5 = floatValue3 * 0.3F;
    this.render2(
        floatValue + floatValue5,
        floatValue2 + floatValue5,
        floatValue + floatValue3 - floatValue5,
        floatValue2 + floatValue3 - floatValue5,
        floatValue4,
        intValue);
    this.render2(
        floatValue + floatValue3 - floatValue5,
        floatValue2 + floatValue5,
        floatValue + floatValue5,
        floatValue2 + floatValue3 - floatValue5,
        floatValue4,
        intValue);
  }

  public float computeFloat(
      String text, float floatValue, float floatValue2, float floatValue3, int intValue) {
    return this.computeFloat(text, floatValue, floatValue2, floatValue3, intValue, this.string3, 1);
  }

  public float computeFloat2(
      String text, float floatValue, float floatValue2, float floatValue3, int intValue) {
    return this.computeFloat(text, floatValue, floatValue2, floatValue3, intValue, this.string3, 2);
  }

  public float computeFloat3(
      String text, float floatValue, float floatValue2, float floatValue3, int intValue) {
    return this.computeFloat(text, floatValue, floatValue2, floatValue3, intValue, this.string3, 4);
  }

  public float computeFloat(
      String text,
      float floatValue,
      float floatValue2,
      float floatValue3,
      int intValue,
      int intValue2) {
    return this.computeFloat(
        text, floatValue, floatValue2, floatValue3, intValue, this.getString(), intValue2);
  }

  public float computeFloat4(
      String text, float floatValue, float floatValue2, float floatValue3, int intValue) {
    return this.computeFloat(text, floatValue, floatValue2, floatValue3, intValue, 1);
  }

  public float computeFloat2(
      String string4, float sx, float y, float size, int intValue, int intValue2) {
    if (this.string3 == null) {
      return 0.0F;
    }

    try (MemoryStack stack = MemoryStack.stackPush()) {
      NVGPaint __result = NVGPaint.malloc(stack);
      NanoVG.nvgLinearGradient(
          this.timestamp,
          sx,
          y - size / 2.0F,
          sx,
          y + size / 2.0F,
          getNVGColor(stack, intValue),
          getNVGColor(stack, intValue2),
          __result);
      NanoVG.nvgFontFace(this.timestamp, this.string3);
      NanoVG.nvgFontSize(this.timestamp, size);
      NanoVG.nvgTextAlign(this.timestamp, 17);
      NanoVG.nvgFillPaint(this.timestamp, __result);
      return NanoVG.nvgText(this.timestamp, sx, y, string4) - sx;
    }
  }

  public void render(String string4, float x, float y, float size, int intValue) {
    if (this.string3 != null) {
      try (MemoryStack memoryStack = MemoryStack.stackPush()) {
        NanoVG.nvgFontFace(this.timestamp, this.string3);
        NanoVG.nvgFontSize(this.timestamp, size);
        NanoVG.nvgTextAlign(this.timestamp, 17);
        NanoVG.nvgFontBlur(this.timestamp, 4.0F);
        NanoVG.nvgFillColor(this.timestamp, getNVGColor(memoryStack, intValue));
        NanoVG.nvgText(this.timestamp, x, y, string4);
        NanoVG.nvgFontBlur(this.timestamp, 0.0F);
      }
    }
  }

  public float computeFloat(
      String string4, float x, float y, float size, int intValue, String font, int intValue2) {
    if (font == null) {
      return 0.0F;
    }

    try (MemoryStack memoryStack = MemoryStack.stackPush()) {
      if (this.enabled3) {
        FloatBuffer xform = memoryStack.mallocFloat(6);
        NanoVG.nvgCurrentTransform(this.timestamp, xform);
        float floatValue = xform.get(0);
        if (floatValue > 0.0F
            && Math.abs(xform.get(1)) < 1.0E-4F
            && Math.abs(xform.get(2)) < 1.0E-4F
            && Math.abs(xform.get(3) - floatValue) < 1.0E-4F) {
          float x2 = Math.round((x * floatValue + xform.get(4)) * this.amount3) / this.amount3;
          float y2 = Math.round((y * floatValue + xform.get(5)) * this.amount3) / this.amount3;
          NanoVG.nvgSave(this.timestamp);

          try {
            NanoVG.nvgResetTransform(this.timestamp);
            NanoVG.nvgFontFace(this.timestamp, font);
            NanoVG.nvgFontSize(this.timestamp, size * floatValue);
            NanoVG.nvgFontBlur(this.timestamp, 0.0F);
            NanoVG.nvgTextAlign(this.timestamp, intValue2 | 16);
            NanoVG.nvgTextLetterSpacing(this.timestamp, this.amount * floatValue);
            NanoVG.nvgFillColor(this.timestamp, getNVGColor(memoryStack, intValue));
            return (NanoVG.nvgText(this.timestamp, x2, y2, string4) - x2) / floatValue;
          } finally {
            NanoVG.nvgRestore(this.timestamp);
          }
        }
      }

      NanoVG.nvgFontFace(this.timestamp, font);
      NanoVG.nvgFontSize(this.timestamp, size);
      NanoVG.nvgTextAlign(this.timestamp, intValue2 | 16);
      NanoVG.nvgTextLetterSpacing(this.timestamp, this.amount);
      NanoVG.nvgFillColor(this.timestamp, getNVGColor(memoryStack, intValue));
      float floatValue2 = NanoVG.nvgText(this.timestamp, x, y, string4);
      NanoVG.nvgTextLetterSpacing(this.timestamp, 0.0F);
      return floatValue2 - x;
    }
  }

  public void setAmount(float amount4) {
    this.amount = amount4;
  }

  public float computeFloat(
      String text,
      float floatValue,
      float floatValue2,
      float floatValue3,
      int intValue,
      float floatValue4) {
    return this.computeFloat(text, floatValue, floatValue2, floatValue3, intValue, floatValue4, 1);
  }

  public float computeFloat(
      String text,
      float floatValue,
      float floatValue2,
      float floatValue3,
      int intValue,
      float floatValue4,
      int intValue2) {
    if (this.computeFloat(text, floatValue3) <= floatValue4) {
      return this.computeFloat(
          text, floatValue, floatValue2, floatValue3, intValue, this.string3, intValue2);
    }

    String text2 = text;

    while (text2.length() > 1 && this.computeFloat(text2 + "…", floatValue3) > floatValue4) {
      text2 = text2.substring(0, text2.length() - 1);
    }

    return this.computeFloat(
        text2 + "…", floatValue, floatValue2, floatValue3, intValue, this.string3, intValue2);
  }

  public float computeFloat(String text, float floatValue) {
    return this.computeFloat(text, floatValue, this.string3);
  }

  public float computeFloat2(String text, float floatValue) {
    return this.computeFloat(text, floatValue, this.getString());
  }

  public float computeFloat(String string4, float size, String font) {
    if (font == null) {
      return 0.0F;
    }

    NanoVG.nvgFontFace(this.timestamp, font);
    NanoVG.nvgFontSize(this.timestamp, size);
    NanoVG.nvgTextAlign(this.timestamp, 17);
    NanoVG.nvgTextLetterSpacing(this.timestamp, this.amount);
    float floatValue =
        NanoVG.nvgTextBounds(this.timestamp, 0.0F, 0.0F, string4, (FloatBuffer) null);
    NanoVG.nvgTextLetterSpacing(this.timestamp, 0.0F);
    return floatValue;
  }

  public void render(
      int image,
      float ex,
      float ey,
      float x,
      float y,
      float x2,
      float y2,
      float x3,
      float y3,
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6) {
    float floatValue7 = x2 - x;
    float floatValue8 = y2 - y;
    float floatValue9 = x3 - x;
    float floatValue10 = y3 - y;
    float floatValue11 = floatValue7 * floatValue10 - floatValue9 * floatValue8;
    if (image > 0 && !(Math.abs(floatValue11) < 1.0E-5F)) {
      float a =
          ((floatValue3 - floatValue) * floatValue10 - (floatValue5 - floatValue) * floatValue8)
              / floatValue11;
      float b =
          ((floatValue4 - floatValue2) * floatValue10 - (floatValue6 - floatValue2) * floatValue8)
              / floatValue11;
      float c =
          (floatValue7 * (floatValue5 - floatValue) - floatValue9 * (floatValue3 - floatValue))
              / floatValue11;
      float d =
          (floatValue7 * (floatValue6 - floatValue2) - floatValue9 * (floatValue4 - floatValue2))
              / floatValue11;
      if (!(Math.abs(a * d - b * c) < 1.0E-5F)) {
        NanoVG.nvgSave(this.timestamp);

        try (MemoryStack stack = MemoryStack.stackPush()) {
          NanoVG.nvgShapeAntiAlias(this.timestamp, false);
          NanoVG.nvgTransform(
              this.timestamp, a, b, c, d, floatValue - a * x - c * y, floatValue2 - b * x - d * y);
          NanoVG.nvgBeginPath(this.timestamp);
          NanoVG.nvgMoveTo(this.timestamp, x, y);
          NanoVG.nvgLineTo(this.timestamp, x2, y2);
          NanoVG.nvgLineTo(this.timestamp, x3, y3);
          NanoVG.nvgClosePath(this.timestamp);
          NVGPaint __result = NVGPaint.calloc(stack);
          NanoVG.nvgImagePattern(this.timestamp, 0.0F, 0.0F, ex, ey, 0.0F, image, 1.0F, __result);
          NanoVG.nvgFillPaint(this.timestamp, __result);
          NanoVG.nvgFill(this.timestamp);
        } finally {
          NanoVG.nvgRestore(this.timestamp);
        }
      }
    }
  }

  public void render(int image, float ox, float oy, float ex, float ey, int intValue) {
    if (image > 0) {
      try (MemoryStack stack = MemoryStack.stackPush()) {
        NVGPaint __result = NVGPaint.malloc(stack);
        NanoVG.nvgImagePattern(this.timestamp, ox, oy, ex, ey, 0.0F, image, 1.0F, __result);
        __result.innerColor(getNVGColor(stack, intValue));
        NanoVG.nvgBeginPath(this.timestamp);
        NanoVG.nvgRect(this.timestamp, ox, oy, ex, ey);
        NanoVG.nvgFillPaint(this.timestamp, __result);
        NanoVG.nvgFill(this.timestamp);
      }
    }
  }

  public void render2(
      float floatValue,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      float floatValue7,
      float floatValue8,
      int intValue,
      int intValue2) {
    this.render(
        floatValue,
        floatValue2,
        floatValue3,
        floatValue4,
        0.0F,
        floatValue5,
        floatValue6,
        floatValue7,
        floatValue8,
        intValue,
        intValue2);
  }

  public void render(
      float x,
      float y,
      float w,
      float h,
      float r,
      float cx,
      float cy,
      float inr,
      float outr,
      int intValue,
      int intValue2) {
    try (MemoryStack stack = MemoryStack.stackPush()) {
      NVGPaint __result = NVGPaint.malloc(stack);
      NanoVG.nvgRadialGradient(
          this.timestamp,
          cx,
          cy,
          inr,
          outr,
          getNVGColor(stack, intValue),
          getNVGColor(stack, intValue2),
          __result);
      NanoVG.nvgBeginPath(this.timestamp);
      if (r > 0.0F) {
        NanoVG.nvgRoundedRect(this.timestamp, x, y, w, h, r);
      } else {
        NanoVG.nvgRect(this.timestamp, x, y, w, h);
      }

      NanoVG.nvgFillPaint(this.timestamp, __result);
      NanoVG.nvgFill(this.timestamp);
    }
  }

  public int computeInt(int w, int h, ByteBuffer data) {
    return NanoVG.nvgCreateImageRGBA(this.timestamp, w, h, 6, data);
  }

  public void apply(int image, ByteBuffer data) {
    NanoVG.nvgUpdateImage(this.timestamp, image, data);
  }

  public void apply(int image) {
    if (image > 0) {
      NanoVG.nvgDeleteImage(this.timestamp, image);
    }
  }

  public void render(
      int image,
      float x,
      float y,
      float w,
      float h,
      float ex,
      float floatValue,
      float floatValue2,
      float alpha) {
    if (image > 0) {
      try (MemoryStack stack = MemoryStack.stackPush()) {
        NVGPaint __result = NVGPaint.malloc(stack);
        NanoVG.nvgImagePattern(
            this.timestamp, x + floatValue, y + floatValue2, ex, ex, 0.0F, image, alpha, __result);
        NanoVG.nvgBeginPath(this.timestamp);
        NanoVG.nvgRect(this.timestamp, x, y, w, h);
        NanoVG.nvgFillPaint(this.timestamp, __result);
        NanoVG.nvgFill(this.timestamp);
      }
    }
  }

  public void render(int image, float ox, float oy, float ex, float ey, float alpha, float r) {
    if (image > 0) {
      try (MemoryStack stack = MemoryStack.stackPush()) {
        NVGPaint __result = NVGPaint.malloc(stack);
        NanoVG.nvgImagePattern(this.timestamp, ox, oy, ex, ey, 0.0F, image, alpha, __result);
        NanoVG.nvgBeginPath(this.timestamp);
        NanoVG.nvgRoundedRect(this.timestamp, ox, oy, ex, ey, r);
        NanoVG.nvgFillPaint(this.timestamp, __result);
        NanoVG.nvgFill(this.timestamp);
      }
    }
  }

  public static NVGColor getNVGColor(MemoryStack stack, int intValue) {
    return NanoVG.nvgRGBA(
        (byte) ArgbColors.red(intValue),
        (byte) ArgbColors.green(intValue),
        (byte) ArgbColors.blue(intValue),
        (byte) ArgbColors.alpha(intValue),
        NVGColor.malloc(stack));
  }
}
