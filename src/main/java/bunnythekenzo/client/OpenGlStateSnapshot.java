package bunnythekenzo.client;

import org.lwjgl.opengl.GL33C;

public final class OpenGlStateSnapshot {
  public int count;
  public int count2;
  public int count3;
  public int count4;
  public int count5;
  public int count6;
  public int count7;
  public int count8;
  public int count9;
  public int count10;
  public int count11;
  public int count12;
  public int count13;
  public int count14;
  public int count15;
  public int count16;
  public int count17;
  public int count18;
  public int count19;
  public int count20;
  public int count21;
  public int count22;
  public int count23;
  public int count24;
  public boolean enabled;
  public boolean enabled2;
  public boolean enabled3;
  public boolean enabled4;
  public boolean enabled5;
  public boolean enabled6;
  public final int[] countArray = new int[4];
  public final int[] countArray2 = new int[4];
  public final boolean[] enabledArray = new boolean[4];
  public int count25;
  public int count26;
  public int count27;
  public int count28;

  public static OpenGlStateSnapshot getOpenGlStateSnapshot() {
    OpenGlStateSnapshot openGlStateSnapshot = new OpenGlStateSnapshot();
    openGlStateSnapshot.count = GL33C.glGetInteger(35725);
    openGlStateSnapshot.count2 = GL33C.glGetInteger(34229);
    openGlStateSnapshot.count3 = GL33C.glGetInteger(34964);
    openGlStateSnapshot.count4 = GL33C.glGetInteger(35368);
    openGlStateSnapshot.count5 = GL33C.glGetIntegeri(35368, 0);
    openGlStateSnapshot.count6 = GL33C.glGetInteger(34016);
    GL33C.glActiveTexture(33984);
    openGlStateSnapshot.count7 = GL33C.glGetInteger(32873);
    openGlStateSnapshot.count8 = GL33C.glGetInteger(35097);
    GL33C.glBindSampler(0, 0);
    GL33C.glActiveTexture(openGlStateSnapshot.count6);
    openGlStateSnapshot.enabled = GL33C.glIsEnabled(3042);
    openGlStateSnapshot.count9 = GL33C.glGetInteger(32969);
    openGlStateSnapshot.count10 = GL33C.glGetInteger(32968);
    openGlStateSnapshot.count11 = GL33C.glGetInteger(32971);
    openGlStateSnapshot.count12 = GL33C.glGetInteger(32970);
    openGlStateSnapshot.count13 = GL33C.glGetInteger(32777);
    openGlStateSnapshot.count14 = GL33C.glGetInteger(34877);
    openGlStateSnapshot.enabled2 = GL33C.glIsEnabled(2884);
    openGlStateSnapshot.count15 = GL33C.glGetInteger(2885);
    openGlStateSnapshot.count16 = GL33C.glGetInteger(2886);
    openGlStateSnapshot.enabled3 = GL33C.glIsEnabled(2929);
    openGlStateSnapshot.count17 = GL33C.glGetInteger(2932);
    openGlStateSnapshot.enabled6 = GL33C.glGetBoolean(2930);
    openGlStateSnapshot.enabled4 = GL33C.glIsEnabled(3089);
    openGlStateSnapshot.enabled5 = GL33C.glIsEnabled(2960);
    openGlStateSnapshot.count18 = GL33C.glGetInteger(2962);
    openGlStateSnapshot.count19 = GL33C.glGetInteger(2967);
    openGlStateSnapshot.count20 = GL33C.glGetInteger(2963);
    openGlStateSnapshot.count21 = GL33C.glGetInteger(2968);
    openGlStateSnapshot.count22 = GL33C.glGetInteger(2964);
    openGlStateSnapshot.count23 = GL33C.glGetInteger(2965);
    openGlStateSnapshot.count24 = GL33C.glGetInteger(2966);
    GL33C.glGetIntegerv(2978, openGlStateSnapshot.countArray);
    GL33C.glGetIntegerv(3088, openGlStateSnapshot.countArray2);
    int[] intValues = new int[4];
    GL33C.glGetIntegerv(3107, intValues);

    for (int index = 0; index < 4; index++) {
      openGlStateSnapshot.enabledArray[index] = intValues[index] != 0;
    }

    openGlStateSnapshot.count25 = GL33C.glGetInteger(3317);
    openGlStateSnapshot.count26 = GL33C.glGetInteger(3314);
    openGlStateSnapshot.count27 = GL33C.glGetInteger(3316);
    openGlStateSnapshot.count28 = GL33C.glGetInteger(3315);
    return openGlStateSnapshot;
  }

  public void update() {
    GL33C.glUseProgram(this.count);
    GL33C.glBindVertexArray(this.count2);
    GL33C.glBindBuffer(34962, this.count3);
    GL33C.glBindBufferBase(35345, 0, this.count5);
    GL33C.glBindBuffer(35345, this.count4);
    GL33C.glActiveTexture(33984);
    GL33C.glBindTexture(3553, this.count7);
    GL33C.glBindSampler(0, this.count8);
    GL33C.glActiveTexture(this.count6);
    apply(3042, this.enabled);
    GL33C.glBlendFuncSeparate(this.count9, this.count10, this.count11, this.count12);
    GL33C.glBlendEquationSeparate(this.count13, this.count14);
    apply(2884, this.enabled2);
    GL33C.glCullFace(this.count15);
    GL33C.glFrontFace(this.count16);
    apply(2929, this.enabled3);
    GL33C.glDepthFunc(this.count17);
    GL33C.glDepthMask(this.enabled6);
    apply(3089, this.enabled4);
    apply(2960, this.enabled5);
    GL33C.glStencilFunc(this.count18, this.count19, this.count20);
    GL33C.glStencilMask(this.count21);
    GL33C.glStencilOp(this.count22, this.count23, this.count24);
    GL33C.glViewport(
        this.countArray[0], this.countArray[1], this.countArray[2], this.countArray[3]);
    GL33C.glScissor(
        this.countArray2[0], this.countArray2[1], this.countArray2[2], this.countArray2[3]);
    GL33C.glColorMask(
        this.enabledArray[0], this.enabledArray[1], this.enabledArray[2], this.enabledArray[3]);
    GL33C.glPixelStorei(3317, this.count25);
    GL33C.glPixelStorei(3314, this.count26);
    GL33C.glPixelStorei(3316, this.count27);
    GL33C.glPixelStorei(3315, this.count28);
  }

  public static void apply(int intValue, boolean flag) {
    if (flag) {
      GL33C.glEnable(intValue);
    } else {
      GL33C.glDisable(intValue);
    }
  }
}
