package bunnythekenzo.client;

import java.nio.ByteBuffer;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.nanovg.NanoVGGL3;
import org.lwjgl.opengl.GL33C;
import bunnythekenzo.client.render.FontManager;

public final class ModuleSearchSupport8 implements AutoCloseable {
  public final int count;
  public final int count2;
  public int count3;
  public int count4;
  public int count5;
  public int count6;
  public FontManager fontManager;

  public ModuleSearchSupport8(int count7, int count22) {
    this.count = count7;
    this.count2 = count22;
  }

  public int getCount6() {
    return this.count6;
  }

  public void apply(FontManager fontManager2, Runnable runnable) {
    int intValue = GL33C.glGetInteger(36006);
    int intValue2 = GL33C.glGetInteger(36010);
    int intValue3 = GL33C.glGetInteger(36007);
    float[] floatValues = new float[4];
    GL33C.glGetFloatv(3106, floatValues);
    int intValue4 = GL33C.glGetInteger(2961);
    OpenGlStateSnapshot openGlStateSnapshot = OpenGlStateSnapshot.getOpenGlStateSnapshot();
    boolean flag = false;
    boolean flag2 = false  ;

    try {
      flag2 = true;
      if (this.count3 == 0) {
        this.apply(fontManager2);
      }

      GL33C.glBindFramebuffer(36160, this.count4);
      GL33C.glViewport(0, 0, this.count * 2, this.count2 * 2);
      GL33C.glDisable(3089);
      GL33C.glColorMask(true, true, true, true);
      GL33C.glStencilMask(255);
      GL33C.glClearStencil(0);
      GL33C.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
      GL33C.glClear(17408);
      fontManager2.render(this.count, this.count2, 2.0F);
      flag = true;
      runnable.run();
      fontManager2.update();
      flag = false;
      flag2 = false;
    } finally {
      if (flag2) {
        if (flag) {
          NanoVG.nvgCancelFrame(fontManager2.getTimestamp());
        }

        GL33C.glBindFramebuffer(36009, intValue);
        GL33C.glBindFramebuffer(36008, intValue2);
        GL33C.glBindRenderbuffer(36161, intValue3);
        GL33C.glClearColor(floatValues[0], floatValues[1], floatValues[2], floatValues[3]);
        GL33C.glClearStencil(intValue4);
        openGlStateSnapshot.update();
      }
    }

    GL33C.glBindFramebuffer(36009, intValue);
    GL33C.glBindFramebuffer(36008, intValue2);
    GL33C.glBindRenderbuffer(36161, intValue3);
    GL33C.glClearColor(floatValues[0], floatValues[1], floatValues[2], floatValues[3]);
    GL33C.glClearStencil(intValue4);
    openGlStateSnapshot.update();
  }

  public void apply(FontManager fontManager2) {
    this.fontManager = fontManager2;
    this.count3 = GL33C.glGenTextures();
    GL33C.glActiveTexture(33984);
    GL33C.glBindTexture(3553, this.count3);
    GL33C.glTexImage2D(
        3553, 0, 32856, this.count * 2, this.count2 * 2, 0, 6408, 5121, (ByteBuffer) null);
    GL33C.glTexParameteri(3553, 10241, 9729);
    GL33C.glTexParameteri(3553, 10240, 9729);
    GL33C.glTexParameteri(3553, 10242, 33071);
    GL33C.glTexParameteri(3553, 10243, 33071);
    this.count4 = GL33C.glGenFramebuffers();
    GL33C.glBindFramebuffer(36160, this.count4);
    GL33C.glFramebufferTexture2D(36160, 36064, 3553, this.count3, 0);
    this.count5 = GL33C.glGenRenderbuffers();
    GL33C.glBindRenderbuffer(36161, this.count5);
    GL33C.glRenderbufferStorage(36161, 36168, this.count * 2, this.count2 * 2);
    GL33C.glFramebufferRenderbuffer(36160, 36128, 36161, this.count5);
    if (GL33C.glCheckFramebufferStatus(36160) != 36053) {
      this.close();
      throw new IllegalStateException(DiagnosticTokenGenerator.tokenFor(1602));
    }

    this.count6 =
        NanoVGGL3.nvglCreateImageFromHandle(
            fontManager2.getTimestamp(), this.count3, this.count * 2, this.count2 * 2, 65560);
    if (this.count6 <= 0) {
      this.close();
      throw new IllegalStateException(DiagnosticTokenGenerator.tokenFor(1603));
    }
  }

  @Override
  public void close() {
    if (this.count6 > 0 && this.fontManager != null) {
      this.fontManager.apply(this.count6);
    }

    if (this.count5 != 0) {
      GL33C.glDeleteRenderbuffers(this.count5);
    }

    if (this.count4 != 0) {
      GL33C.glDeleteFramebuffers(this.count4);
    }

    if (this.count3 != 0) {
      GL33C.glDeleteTextures(this.count3);
    }

    this.count6 = this.count4 = this.count3 = this.count5 = 0;
  }
}
