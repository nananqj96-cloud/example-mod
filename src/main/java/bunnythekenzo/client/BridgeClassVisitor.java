package bunnythekenzo.client;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

public final class BridgeClassVisitor extends ClassVisitor {

  public BridgeClassVisitor(ClassVisitor classVisitor) {
    super(589824, classVisitor);
  }

  @Override
  public MethodVisitor visitMethod(
      int intValue, String text, String text2, String text3, String[] textArray) {
    MethodVisitor methodVisitor = super.visitMethod(intValue, text, text2, text3, textArray);
    return new BridgeClassVisitorSupport(methodVisitor, intValue, text, text2);
  }

  @Override
  public void visitEnd() {
    this.update();
    super.visitEnd();
  }

  public void update() {
    MethodVisitor methodVisitor =
        super.visitMethod(
            4161,
            "$bridge$0",
            "(Ljava/lang/Object;)Ljava/lang/Object;",
            "<T::Ljava/lang/Comparable<TT;>;>(TT;)TT;",
            null);
    methodVisitor.visitCode();
    methodVisitor.visitVarInsn(25, 1);
    methodVisitor.visitInsn(176);
    methodVisitor.visitMaxs(1, 2);
    methodVisitor.visitEnd();
    MethodVisitor methodVisitor2 =
        super.visitMethod(4164, "$bridge$1", "()Ljava/lang/Object;", null, null);
    methodVisitor2.visitCode();
    methodVisitor2.visitInsn(1);
    methodVisitor2.visitInsn(176);
    methodVisitor2.visitMaxs(1, 1);
    methodVisitor2.visitEnd();
    MethodVisitor methodVisitor3 =
        super.visitMethod(
            4098, "access$000", "(Ljava/lang/Object;Ljava/lang/Object;)I", null, null);
    methodVisitor3.visitCode();
    methodVisitor3.visitInsn(3);
    methodVisitor3.visitInsn(172);
    methodVisitor3.visitMaxs(1, 3);
    methodVisitor3.visitEnd();
  }
}
