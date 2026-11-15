package org.lwjgl.nanovg;

import java.nio.ByteBuffer;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.Checks;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class OUI {
  public static final int UI_USERMASK = -16777216;
  public static final int UI_ANY = -1;
  public static final int UI_COLD = 0;
  public static final int UI_HOT = 1;
  public static final int UI_ACTIVE = 2;
  public static final int UI_FROZEN = 3;
  public static final int UI_ROW = 2;
  public static final int UI_COLUMN = 3;
  public static final int UI_LAYOUT = 0;
  public static final int UI_FLEX = 2;
  public static final int UI_NOWRAP = 0;
  public static final int UI_WRAP = 4;
  public static final int UI_START = 8;
  public static final int UI_MIDDLE = 0;
  public static final int UI_END = 16;
  public static final int UI_JUSTIFY = 24;
  public static final int UI_LEFT = 32;
  public static final int UI_TOP = 64;
  public static final int UI_RIGHT = 128;
  public static final int UI_DOWN = 256;
  public static final int UI_HFILL = 160;
  public static final int UI_VFILL = 320;
  public static final int UI_HCENTER = 0;
  public static final int UI_VCENTER = 0;
  public static final int UI_CENTER = 0;
  public static final int UI_FILL = 480;
  public static final int UI_BREAK = 512;
  public static final int UI_BUTTON0_DOWN = 1024;
  public static final int UI_BUTTON0_UP = 2048;
  public static final int UI_BUTTON0_HOT_UP = 4096;
  public static final int UI_BUTTON0_CAPTURE = 8192;
  public static final int UI_BUTTON2_DOWN = 16384;
  public static final int UI_SCROLL = 32768;
  public static final int UI_KEY_DOWN = 65536;
  public static final int UI_KEY_UP = 131072;
  public static final int UI_CHAR = 262144;

  protected OUI() {
    throw new UnsupportedOperationException();
  }

  @NativeType("UIcontext *")
  public static native long uiCreateContext(
      @NativeType("unsigned int") int intValue, @NativeType("unsigned int") int intValue2);

  public static native void uiMakeCurrent(@NativeType("UIcontext *") long longValue);

  public static native void nuiDestroyContext(long longValue);

  public static void uiDestroyContext(@NativeType("UIcontext *") long ctx) {
    if (Checks.CHECKS) {
      Checks.check(ctx);
    }

    nuiDestroyContext(ctx);
  }

  @NativeType("UIcontext *")
  public static native long uiGetContext();

  public static native void uiSetCursor(int intValue, int intValue2);

  public static native void nuiGetCursor(long longValue);

  @NativeType("UIvec2")
  public static UIVec2 uiGetCursor(@NativeType("UIvec2") UIVec2 __result) {
    nuiGetCursor(__result.address());
    return __result;
  }

  public static native void nuiGetCursorDelta(long longValue);

  @NativeType("UIvec2")
  public static UIVec2 uiGetCursorDelta(@NativeType("UIvec2") UIVec2 __result) {
    nuiGetCursorDelta(__result.address());
    return __result;
  }

  public static native void nuiGetCursorStart(long longValue);

  @NativeType("UIvec2")
  public static UIVec2 uiGetCursorStart(@NativeType("UIvec2") UIVec2 __result) {
    nuiGetCursorStart(__result.address());
    return __result;
  }

  public static native void nuiGetCursorStartDelta(long longValue);

  @NativeType("UIvec2")
  public static UIVec2 uiGetCursorStartDelta(@NativeType("UIvec2") UIVec2 __result) {
    nuiGetCursorStartDelta(__result.address());
    return __result;
  }

  public static native void nuiSetButton(int intValue, int intValue2, int intValue3);

  public static void uiSetButton(
      @NativeType("unsigned int") int button,
      @NativeType("unsigned int") int mod,
      @NativeType("int") boolean enabled) {
    nuiSetButton(button, mod, enabled ? 1 : 0);
  }

  public static native int nuiGetButton(int intValue);

  @NativeType("int")
  public static boolean uiGetButton(@NativeType("unsigned int") int button) {
    return nuiGetButton(button) != 0;
  }

  public static native int uiGetClicks();

  public static native void nuiSetKey(int intValue, int intValue2, int intValue3);

  public static void uiSetKey(
      @NativeType("unsigned int") int key,
      @NativeType("unsigned int") int mod,
      @NativeType("int") boolean enabled) {
    nuiSetKey(key, mod, enabled ? 1 : 0);
  }

  public static native void uiSetChar(@NativeType("unsigned int") int intValue);

  public static native void uiSetScroll(int intValue, int intValue2);

  public static native void nuiGetScroll(long longValue);

  @NativeType("UIvec2")
  public static UIVec2 uiGetScroll(@NativeType("UIvec2") UIVec2 __result) {
    nuiGetScroll(__result.address());
    return __result;
  }

  public static native void uiBeginLayout();

  public static native void uiEndLayout();

  public static native void uiUpdateHotItem();

  public static native void uiProcess(int intValue);

  public static native void uiClearState();

  public static native int uiItem();

  public static native void nuiSetFrozen(int intValue, int intValue2);

  public static void uiSetFrozen(int item, @NativeType("int") boolean enable) {
    nuiSetFrozen(item, enable ? 1 : 0);
  }

  public static native void uiSetHandle(int intValue, @NativeType("void *") long longValue);

  public static native long nuiAllocHandle(int intValue, int intValue2);

  @Nullable
  @NativeType("void *")
  public static ByteBuffer uiAllocHandle(int item, @NativeType("unsigned int") int size) {
    long __result = nuiAllocHandle(item, size);
    return MemoryUtil.memByteBufferSafe(__result, size);
  }

  public static native void nuiSetHandler(long longValue);

  public static void uiSetHandler(@NativeType("UIhandler") UIHandlerI handler) {
    nuiSetHandler(handler.address());
  }

  public static native void uiSetEvents(int intValue, @NativeType("unsigned int") int intValue2);

  public static native void uiSetFlags(int intValue, @NativeType("unsigned int") int intValue2);

  public static native int uiInsert(int intValue, int intValue2);

  public static native int uiAppend(int intValue, int intValue2);

  public static native int uiInsertBack(int intValue, int intValue2);

  public static native int uiInsertFront(int intValue, int intValue2);

  public static native void uiSetSize(int intValue, int intValue2, int intValue3);

  public static native void uiSetLayout(int intValue, @NativeType("unsigned int") int intValue2);

  public static native void uiSetBox(int intValue, @NativeType("unsigned int") int intValue2);

  public static native void nuiSetMargins(
      int intValue, short shortValue, short shortValue2, short shortValue3, short shortValue4);

  public static void uiSetMargins(
      int item,
      @NativeType("short") int l,
      @NativeType("short") int t,
      @NativeType("short") int r,
      @NativeType("short") int b) {
    nuiSetMargins(item, (short) l, (short) t, (short) r, (short) b);
  }

  public static native void uiFocus(int intValue);

  public static native int uiFirstChild(int intValue);

  public static native int uiNextSibling(int intValue);

  public static native int uiGetItemCount();

  @NativeType("unsigned int")
  public static native int uiGetAllocSize();

  @NativeType("UIitemState")
  public static native int uiGetState(int intValue);

  @NativeType("void *")
  public static native long uiGetHandle(int intValue);

  public static native int uiGetHotItem();

  public static native int uiGetFocusedItem();

  public static native int uiFindItem(
      int intValue,
      int intValue2,
      int intValue3,
      @NativeType("unsigned int") int intValue4,
      @NativeType("unsigned int") int intValue5);

  public static native long nuiGetHandler();

  @Nullable
  @NativeType("UIhandler")
  public static UIHandler uiGetHandler() {
    return UIHandler.createSafe(nuiGetHandler());
  }

  @NativeType("unsigned int")
  public static native int uiGetEvents(int intValue);

  @NativeType("unsigned int")
  public static native int uiGetFlags(int intValue);

  @NativeType("unsigned int")
  public static native int uiGetKey();

  @NativeType("unsigned int")
  public static native int uiGetModifier();

  public static native void nuiGetRect(int intValue, long longValue);

  @NativeType("UIrect")
  public static UIRect uiGetRect(int item, @NativeType("UIrect") UIRect __result) {
    nuiGetRect(item, __result.address());
    return __result;
  }

  public static native int nuiContains(int intValue, int intValue2, int intValue3);

  @NativeType("int")
  public static boolean uiContains(int item, int x, int y) {
    return nuiContains(item, x, y) != 0;
  }

  public static native int uiGetWidth(int intValue);

  public static native int uiGetHeight(int intValue);

  @NativeType("unsigned int")
  public static native int uiGetLayout(int intValue);

  @NativeType("unsigned int")
  public static native int uiGetBox(int intValue);

  public static native short uiGetMarginLeft(int intValue);

  public static native short uiGetMarginTop(int intValue);

  public static native short uiGetMarginRight(int intValue);

  public static native short uiGetMarginDown(int intValue);

  public static native int uiRecoverItem(int intValue);

  public static native void uiRemapItem(int intValue, int intValue2);

  public static native int uiGetLastItemCount();

  static {
    LibNanoVG.initialize();
  }
}
