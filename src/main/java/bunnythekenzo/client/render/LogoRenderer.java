package bunnythekenzo.client.render;

import java.nio.ByteBuffer;
import java.util.HashMap;
import org.joml.Matrix3f;
import org.joml.Vector3f;
import org.lwjgl.nanovg.NanoVGGL3;
import org.lwjgl.opengl.GL33C;
import bunnythekenzo.client.ArgbColors;
import bunnythekenzo.client.ColorPalette;
import bunnythekenzo.client.DiagnosticTokenGenerator;
import bunnythekenzo.client.LogoData;
import bunnythekenzo.client.LogoModel;
import bunnythekenzo.client.OpenGlStateSnapshot;

public final class LogoRenderer implements AutoCloseable {
  public static final String string =
      "#version 150\n"
          + "in vec3 position;\n"
          + "in vec3 normal;\n"
          + "in float material;\n"
          + "uniform mat3 rotation;\n"
          + "out vec3 worldPosition;\n"
          + "out vec3 worldNormal;\n"
          + "flat out float surface;\n"
          + "void main() {\n"
          + "    worldPosition = rotation * (position * vec3(1., -1., 1.));\n"
          + "    worldNormal = rotation * (normal * vec3(1., -1., 1.));\n"
          + "    surface = material;\n"
          + "    vec3 p = worldPosition;\n"
          + "    gl_Position = vec4(p.x / 19., p.y / 14.25, -p.z / 50., 1. - p.z / 90.);\n"
          + "}\n";
  public static final String string2 =
      "#version 150\n"
          + "in vec3 worldPosition;\n"
          + "in vec3 worldNormal;\n"
          + "flat in float surface;\n"
          + "uniform vec3 accent;\n"
          + "uniform float sweep;\n"
          + "uniform float exposure;\n"
          + "out vec4 fragColor;\n"
          + "void main() {\n"
          + "    vec3 n = normalize(worldNormal);\n"
          + "    vec3 v = normalize(vec3(0., 0., 90.) - worldPosition);\n"
          + "    vec3 key = normalize(vec3(-.45, .8, 1.2));\n"
          + "    vec3 fill = normalize(vec3(.9, -.15, .5));\n"
          + "    float diffuse = max(dot(n, key), 0.);\n"
          + "    float fillLight = max(dot(n, fill), 0.);\n"
          + "    vec3 reflected = reflect(-v, n);\n"
          + "    float softbox = pow(max(dot(reflected, normalize(vec3(-.4, .65, 1.))), 0.),"
          + " 14.);\n"
          + "    float edgebox = pow(max(dot(reflected, normalize(vec3(.9, -.1, .5))), 0.), 24.);\n"
          + "    float fresnel = pow(1. - max(dot(n, v), 0.), 3.);\n"
          + "    float front = 1. - step(.5, surface);\n"
          + "    float bevel = step(.5, surface) * (1. - step(1.5, surface));\n"
          + "    float vertical = clamp((worldPosition.y + 8.) / 16., 0., 1.);\n"
          + "    vec3 silver = mix(vec3(.25, .29, .35), vec3(.82, .87, .93), vertical);\n"
          + "    vec3 base = mix(vec3(.105, .12, .15), silver, front);\n"
          + "    base = mix(base, vec3(.62, .68, .75), bevel);\n"
          + "    vec3 color = base * (.42 + diffuse * .60 + fillLight * .14);\n"
          + "    color += vec3(.93, .96, 1.) * softbox * (.23 + bevel * .38);\n"
          + "    color += vec3(.80, .88, 1.) * edgebox * .30;\n"
          + "    color += accent * fresnel * .16;\n"
          + "    float strip = exp(-pow((worldPosition.y - 3.5 + worldPosition.x * .16) / 2.8,"
          + " 2.));\n"
          + "    color += vec3(.90, .95, 1.) * strip * front * .10;\n"
          + "    float band = exp(-pow((worldPosition.x + worldPosition.y * .55 - sweep) / 3.8,"
          + " 2.));\n"
          + "    color += vec3(.82, .9, 1.) * band * .22 * max(n.z, 0.);\n"
          + "    fragColor = vec4(clamp(color * exposure, 0., 1.), 1.);\n"
          + "}\n";
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
  public FontManager fontManager;
  public float amount = Float.NaN;
  public float amount2;
  public float amount3;
  public float amount4;
  public int count14;

  public void render(
      FontManager fontManager2,
      float floatValue,
      float amount5,
      float amount22,
      float amount32,
      float amount42) {
    int intValue =
        Math.max(512, Math.min(2048, ((int) Math.ceil(floatValue * 1.5F) + 63) / 64 * 64));
    int count15 = ColorPalette.getInstance().getCount();
    if (this.count7 <= 0
        || intValue > this.count8
        || amount5 != this.amount
        || amount22 != this.amount2
        || amount32 != this.amount3
        || amount42 != this.amount4
        || count15 != this.count14) {
      int intValue2 = GL33C.glGetInteger(36006);
      int intValue3 = GL33C.glGetInteger(36010);
      int intValue4 = GL33C.glGetInteger(36007);
      float[] floatValues = new float[4];
      GL33C.glGetFloatv(3106, floatValues);
      double doubleValue = GL33C.glGetDouble(2931);
      boolean flag = GL33C.glIsEnabled(36281);
      OpenGlStateSnapshot openGlStateSnapshot = OpenGlStateSnapshot.getOpenGlStateSnapshot();

      try {
        if (this.count == 0) {
          this.update();
        }

        if (this.count4 == 0 || intValue > this.count8) {
          this.apply(fontManager2, intValue);
        }

        GL33C.glBindFramebuffer(36160, this.count5);
        GL33C.glViewport(0, 0, this.count8, this.count8 * 3 / 4);
        GL33C.glDisable(3089);
        GL33C.glDisable(2960);
        GL33C.glDisable(3042);
        GL33C.glDisable(2884);
        GL33C.glDisable(36281);
        GL33C.glEnable(2929);
        GL33C.glDepthFunc(513);
        GL33C.glDepthMask(true);
        GL33C.glColorMask(true, true, true, true);
        GL33C.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
        GL33C.glClearDepth(1.0);
        GL33C.glClear(16640);
        GL33C.glUseProgram(this.count);
        GL33C.glBindVertexArray(this.count2);
        float[] floatValues2 = new float[9];
        new Matrix3f().rotateX(amount22).rotateY(amount5).get(floatValues2);
        GL33C.glUniformMatrix3fv(this.count10, false, floatValues2);
        GL33C.glUniform3f(
            this.count11,
            ArgbColors.red(count15) / 255.0F,
            ArgbColors.green(count15) / 255.0F,
            ArgbColors.blue(count15) / 255.0F);
        GL33C.glUniform1f(this.count12, amount32);
        GL33C.glUniform1f(this.count13, amount42);
        GL33C.glDrawArrays(4, 0, this.count9);
        GL33C.glActiveTexture(33984);
        GL33C.glBindTexture(3553, this.count4);
        GL33C.glGenerateMipmap(3553);
        this.amount = amount5;
        this.amount2 = amount22;
        this.amount3 = amount32;
        this.amount4 = amount42;
        this.count14 = count15;
      } finally {
        GL33C.glBindFramebuffer(36009, intValue2);
        GL33C.glBindFramebuffer(36008, intValue3);
        GL33C.glBindRenderbuffer(36161, intValue4);
        GL33C.glClearColor(floatValues[0], floatValues[1], floatValues[2], floatValues[3]);
        GL33C.glClearDepth(doubleValue);
        if (flag) {
          GL33C.glEnable(36281);
        } else {
          GL33C.glDisable(36281);
        }

        openGlStateSnapshot.update();
      }
    }
  }

  public void render(
      FontManager fontManager2, float floatValue, float floatValue2, float floatValue3) {
    fontManager2.render(
        this.count7,
        floatValue - floatValue3 / 2.0F,
        floatValue2 - floatValue3 * 0.375F,
        floatValue3,
        floatValue3 * 0.75F,
        -1);
  }

  public void update() {
    int intValue =
        computeInt(
            35633,
            "#version 150\n"
                + "in vec3 position;\n"
                + "in vec3 normal;\n"
                + "in float material;\n"
                + "uniform mat3 rotation;\n"
                + "out vec3 worldPosition;\n"
                + "out vec3 worldNormal;\n"
                + "flat out float surface;\n"
                + "void main() {\n"
                + "    worldPosition = rotation * (position * vec3(1., -1., 1.));\n"
                + "    worldNormal = rotation * (normal * vec3(1., -1., 1.));\n"
                + "    surface = material;\n"
                + "    vec3 p = worldPosition;\n"
                + "    gl_Position = vec4(p.x / 19., p.y / 14.25, -p.z / 50., 1. - p.z / 90.);\n"
                + "}\n");
    int intValue2 = 0;

    try {
      intValue2 =
          computeInt(
              35632,
              "#version 150\n"
                  + "in vec3 worldPosition;\n"
                  + "in vec3 worldNormal;\n"
                  + "flat in float surface;\n"
                  + "uniform vec3 accent;\n"
                  + "uniform float sweep;\n"
                  + "uniform float exposure;\n"
                  + "out vec4 fragColor;\n"
                  + "void main() {\n"
                  + "    vec3 n = normalize(worldNormal);\n"
                  + "    vec3 v = normalize(vec3(0., 0., 90.) - worldPosition);\n"
                  + "    vec3 key = normalize(vec3(-.45, .8, 1.2));\n"
                  + "    vec3 fill = normalize(vec3(.9, -.15, .5));\n"
                  + "    float diffuse = max(dot(n, key), 0.);\n"
                  + "    float fillLight = max(dot(n, fill), 0.);\n"
                  + "    vec3 reflected = reflect(-v, n);\n"
                  + "    float softbox = pow(max(dot(reflected, normalize(vec3(-.4, .65, 1.))),"
                  + " 0.), 14.);\n"
                  + "    float edgebox = pow(max(dot(reflected, normalize(vec3(.9, -.1, .5))), 0.),"
                  + " 24.);\n"
                  + "    float fresnel = pow(1. - max(dot(n, v), 0.), 3.);\n"
                  + "    float front = 1. - step(.5, surface);\n"
                  + "    float bevel = step(.5, surface) * (1. - step(1.5, surface));\n"
                  + "    float vertical = clamp((worldPosition.y + 8.) / 16., 0., 1.);\n"
                  + "    vec3 silver = mix(vec3(.25, .29, .35), vec3(.82, .87, .93), vertical);\n"
                  + "    vec3 base = mix(vec3(.105, .12, .15), silver, front);\n"
                  + "    base = mix(base, vec3(.62, .68, .75), bevel);\n"
                  + "    vec3 color = base * (.42 + diffuse * .60 + fillLight * .14);\n"
                  + "    color += vec3(.93, .96, 1.) * softbox * (.23 + bevel * .38);\n"
                  + "    color += vec3(.80, .88, 1.) * edgebox * .30;\n"
                  + "    color += accent * fresnel * .16;\n"
                  + "    float strip = exp(-pow((worldPosition.y - 3.5 + worldPosition.x * .16) /"
                  + " 2.8, 2.));\n"
                  + "    color += vec3(.90, .95, 1.) * strip * front * .10;\n"
                  + "    float band = exp(-pow((worldPosition.x + worldPosition.y * .55 - sweep) /"
                  + " 3.8, 2.));\n"
                  + "    color += vec3(.82, .9, 1.) * band * .22 * max(n.z, 0.);\n"
                  + "    fragColor = vec4(clamp(color * exposure, 0., 1.), 1.);\n"
                  + "}\n");
      this.count = GL33C.glCreateProgram();
      GL33C.glAttachShader(this.count, intValue);
      GL33C.glAttachShader(this.count, intValue2);
      GL33C.glBindAttribLocation(this.count, 0, "position");
      GL33C.glBindAttribLocation(this.count, 1, "normal");
      GL33C.glBindAttribLocation(this.count, 2, "material");
      GL33C.glBindFragDataLocation(this.count, 0, "fragColor");
      GL33C.glLinkProgram(this.count);
      if (GL33C.glGetProgrami(this.count, 35714) == 0) {
        throw new IllegalStateException(DiagnosticTokenGenerator.tokenFor(1607));
      }

      this.count10 = GL33C.glGetUniformLocation(this.count, "rotation");
      this.count11 = GL33C.glGetUniformLocation(this.count, "accent");
      this.count12 = GL33C.glGetUniformLocation(this.count, "sweep");
      this.count13 = GL33C.glGetUniformLocation(this.count, "exposure");
      float[] floatValues = vertices();
      this.count9 = floatValues.length / 7;
      this.count2 = GL33C.glGenVertexArrays();
      GL33C.glBindVertexArray(this.count2);
      this.count3 = GL33C.glGenBuffers();
      GL33C.glBindBuffer(34962, this.count3);
      GL33C.glBufferData(34962, floatValues, 35044);
      GL33C.glEnableVertexAttribArray(0);
      GL33C.glVertexAttribPointer(0, 3, 5126, false, 28, 0L);
      GL33C.glEnableVertexAttribArray(1);
      GL33C.glVertexAttribPointer(1, 3, 5126, false, 28, 12L);
      GL33C.glEnableVertexAttribArray(2);
      GL33C.glVertexAttribPointer(2, 1, 5126, false, 28, 24L);
    } catch (RuntimeException failure) {
      this.close();
      throw failure;
    } finally {
      GL33C.glDeleteShader(intValue);
      if (intValue2 != 0) {
        GL33C.glDeleteShader(intValue2);
      }
    }
  }

  public static int computeInt(int intValue, String text) {
    int intValue2 = GL33C.glCreateShader(intValue);
    GL33C.glShaderSource(intValue2, text);
    GL33C.glCompileShader(intValue2);
    if (GL33C.glGetShaderi(intValue2, 35713) == 0) {
      GL33C.glGetShaderInfoLog(intValue2);
      GL33C.glDeleteShader(intValue2);
      throw new IllegalStateException(DiagnosticTokenGenerator.tokenFor(1608));
    } else {
      return intValue2;
    }
  }

  public static LogoData getLogoData(float[] floatValues, int index) {
    return new LogoData(
        floatValues[index], floatValues[index + 1], floatValues[index + 2], (int) floatValues[9]);
  }

  public static float[] vertices() {
    HashMap hashMap = new HashMap();

    for (float[] floatValues : LogoModel.amountArray) {
      Vector3f vector3f =
          new Vector3f(
                  floatValues[3] - floatValues[0],
                  floatValues[4] - floatValues[1],
                  floatValues[5] - floatValues[2])
              .cross(
                  floatValues[6] - floatValues[0],
                  floatValues[7] - floatValues[1],
                  floatValues[8] - floatValues[2]);

      for (byte byteValue = 0; byteValue < 9; byteValue += 3) {
        ((org.joml.Vector3f)
                (hashMap.computeIfAbsent(
                    getLogoData(floatValues, byteValue),
                    value -> {
                      return new Vector3f();
                    })))
            .add(vector3f);
      }
    }

    hashMap
        .values()
        .forEach(
            (referenceArgument66_0) -> ((org.joml.Vector3f) referenceArgument66_0).normalize());
    float[] floatValues2 = new float[LogoModel.amountArray.length * 21];
    int index = 0;

    for (float[] floatValues3 : LogoModel.amountArray) {
      for (byte index2 = 0; index2 < 9; index2 += 3) {
        Vector3f vector3f2 = (Vector3f) hashMap.get(getLogoData(floatValues3, index2));
        floatValues2[index++] = floatValues3[index2];
        floatValues2[index++] = floatValues3[index2 + 1];
        floatValues2[index++] = floatValues3[index2 + 2];
        floatValues2[index++] = vector3f2.x;
        floatValues2[index++] = vector3f2.y;
        floatValues2[index++] = vector3f2.z;
        floatValues2[index++] = floatValues3[9];
      }
    }

    return floatValues2;
  }

  public void apply(FontManager fontManager2, int intValue) {
    this.update2();
    this.fontManager = fontManager2;
    this.count8 = Math.min(intValue, GL33C.glGetInteger(3379));
    int h = this.count8 * 3 / 4;
    this.count4 = GL33C.glGenTextures();
    GL33C.glActiveTexture(33984);
    GL33C.glBindTexture(3553, this.count4);
    GL33C.glTexImage2D(3553, 0, 32856, this.count8, h, 0, 6408, 5121, (ByteBuffer) null);
    GL33C.glTexParameteri(3553, 10241, 9987);
    GL33C.glTexParameteri(3553, 10240, 9729);
    GL33C.glTexParameteri(3553, 10242, 33071);
    GL33C.glTexParameteri(3553, 10243, 33071);
    this.count5 = GL33C.glGenFramebuffers();
    GL33C.glBindFramebuffer(36160, this.count5);
    GL33C.glFramebufferTexture2D(36160, 36064, 3553, this.count4, 0);
    this.count6 = GL33C.glGenRenderbuffers();
    GL33C.glBindRenderbuffer(36161, this.count6);
    GL33C.glRenderbufferStorage(36161, 33190, this.count8, h);
    GL33C.glFramebufferRenderbuffer(36160, 36096, 36161, this.count6);
    if (GL33C.glCheckFramebufferStatus(36160) != 36053) {
      this.update2();
      throw new IllegalStateException(DiagnosticTokenGenerator.tokenFor(1609));
    }

    this.count7 =
        NanoVGGL3.nvglCreateImageFromHandle(
            fontManager2.getTimestamp(), this.count4, this.count8, h, 65561);
    if (this.count7 <= 0) {
      this.update2();
      throw new IllegalStateException(DiagnosticTokenGenerator.tokenFor(1610));
    }
  }

  public void update2() {
    if (this.count7 > 0 && this.fontManager != null) {
      this.fontManager.apply(this.count7);
    }

    if (this.count6 != 0) {
      GL33C.glDeleteRenderbuffers(this.count6);
    }

    if (this.count5 != 0) {
      GL33C.glDeleteFramebuffers(this.count5);
    }

    if (this.count4 != 0) {
      GL33C.glDeleteTextures(this.count4);
    }

    this.count7 = this.count6 = this.count5 = this.count4 = this.count8 = 0;
  }

  @Override
  public void close() {
    this.update2();
    if (this.count3 != 0) {
      GL33C.glDeleteBuffers(this.count3);
    }

    if (this.count2 != 0) {
      GL33C.glDeleteVertexArrays(this.count2);
    }

    if (this.count != 0) {
      GL33C.glDeleteProgram(this.count);
    }

    this.count3 = this.count2 = this.count = 0;
    this.amount = Float.NaN;
  }
}
