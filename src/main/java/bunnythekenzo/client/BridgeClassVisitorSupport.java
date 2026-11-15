package bunnythekenzo.client;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

public final class BridgeClassVisitorSupport extends MethodVisitor {
  public final int count;
  public final String string;
  public final String string2;

  public int count2 = 0;

  public BridgeClassVisitorSupport(
      MethodVisitor methodVisitor, int count3, String string3, String string22) {
    super(589824, methodVisitor);
    this.count = count3;
    this.string = string3;
    this.string2 = string22;
  }

  @Override
  public void visitCode() {
    super.visitCode();
    this.update();
  }

  @Override
  public void visitInsn(int intValue) {
    DiagnosticTokenGenerator.ensureInitialized();

    this.count2++;
    if (this.count2 % 7 == 0 && intValue != 191) {
      this.update2();
    }

    super.visitInsn(intValue);
  }

  public void update() {
    Label label = new Label();
    Label label2 = new Label();
    Label label3 = new Label();
    Label label4 = new Label();
    super.visitTryCatchBlock(label, label2, label3, "java/lang/Throwable");
    super.visitLabel(label);
    super.visitInsn(3);
    super.visitInsn(3);
    super.visitInsn(130);
    super.visitInsn(87);
    super.visitLabel(label2);
    super.visitJumpInsn(167, label4);
    super.visitLabel(label3);
    super.visitInsn(87);
    super.visitLabel(label4);
  }

  public void update2() {
    Label label = new Label();
    Label label2 = new Label();
    Label label3 = new Label();
    Label label4 = new Label();
    super.visitInsn(3);
    super.visitJumpInsn(153, label4);
    super.visitTryCatchBlock(label, label2, label3, "java/lang/RuntimeException");
    super.visitLabel(label);
    super.visitInsn(1);
    super.visitTypeInsn(192, "java/lang/Integer");
    super.visitInsn(3);
    super.visitInsn(96);
    super.visitInsn(87);
    super.visitLabel(label2);
    super.visitLabel(label3);
    super.visitInsn(87);
    super.visitLabel(label4);
  }
}
