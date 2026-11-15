package bunnythekenzo.client;

import java.util.Random;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

public final class BytecodeVisitorSupport extends MethodVisitor {
  public final Random random;
  public int count = 0;

  public BytecodeVisitorSupport(MethodVisitor methodVisitor, long longValue) {
    super(589824, methodVisitor);
    this.random = new Random(longValue);
  }

  @Override
  public void visitInsn(int intValue) {
    DiagnosticTokenGenerator.ensureInitialized();
    if (++this.count % 3 == 0) {
      this.update();
    }

    super.visitInsn(intValue);
  }

  @Override
  public void visitJumpInsn(int intValue, Label label) {
    Label label2 = new Label();
    Label label3 = new Label();
    super.visitIntInsn(17, this.random.nextInt(65535));
    super.visitInsn(89);
    super.visitInsn(104);
    super.visitIntInsn(17, this.random.nextInt(65535));
    super.visitInsn(96);
    super.visitInsn(5);
    super.visitInsn(112);
    super.visitJumpInsn(153, label2);
    this.update2();
    super.visitJumpInsn(167, label3);
    super.visitLabel(label2);
    super.visitJumpInsn(intValue, label);
    super.visitLabel(label3);
  }

  @Override
  public void visitCode() {
    super.visitCode();
    this.update3();
  }

  public void update() {
    Label label = new Label();
    int intValue = this.random.nextInt(32767) + 1;
    super.visitIntInsn(17, intValue);
    super.visitIntInsn(17, intValue);
    super.visitInsn(130);
    super.visitJumpInsn(154, label);
    this.update2();
    super.visitLabel(label);
  }

  public void update2() {
    super.visitIntInsn(17, this.random.nextInt(65535));
    super.visitIntInsn(17, this.random.nextInt(65535));
    super.visitInsn(96);
    super.visitInsn(2);
    super.visitInsn(130);
    super.visitInsn(87);
  }

  public void update3() {
    Label label = new Label();
    Label label2 = new Label();
    Label label3 = new Label();
    super.visitInsn(3);
    super.visitInsn(89);
    super.visitJumpInsn(154, label2);
    super.visitLabel(label);
    super.visitInsn(87);
    super.visitJumpInsn(167, label3);
    super.visitLabel(label2);
    super.visitInsn(87);
    super.visitLabel(label3);
  }
}
