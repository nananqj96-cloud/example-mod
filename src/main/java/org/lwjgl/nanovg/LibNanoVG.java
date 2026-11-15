package org.lwjgl.nanovg;

import org.lwjgl.system.Configuration;
import org.lwjgl.system.Library;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.MemoryUtil.MemoryAllocator;
import org.lwjgl.system.Platform;

final class LibNanoVG {
  private LibNanoVG() {}

  static void initialize() {}

  private static native void setupMalloc(
      long longValue,
      long longValue2,
      long longValue3,
      long longValue4,
      long longValue5,
      long longValue6);

  static {
    String libName = Platform.mapLibraryNameBundled("lwjgl_nanovg");
    Library.loadSystem(
        System::load, System::loadLibrary, LibNanoVG.class, "org.lwjgl.nanovg", libName);
    MemoryAllocator allocator =
        MemoryUtil.getAllocator((Boolean) Configuration.DEBUG_MEMORY_ALLOCATOR_INTERNAL.get(true));
    setupMalloc(
        allocator.getMalloc(),
        allocator.getCalloc(),
        allocator.getRealloc(),
        allocator.getFree(),
        allocator.getAlignedAlloc(),
        allocator.getAlignedFree());
  }
}
