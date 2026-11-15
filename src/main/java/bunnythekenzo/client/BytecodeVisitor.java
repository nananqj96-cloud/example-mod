package bunnythekenzo.client;

import java.util.Random;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

public final class BytecodeVisitor extends ClassVisitor {
  public final long timestamp;
  public final Random random;

  public BytecodeVisitor(ClassVisitor classVisitor, long timestamp2) {
    super(589824, classVisitor);
    this.timestamp = timestamp2;
    this.random = new Random(timestamp2);
  }

  @Override
  public MethodVisitor visitMethod(
      int intValue, String text, String text2, String text3, String[] textArray) {
    DiagnosticTokenGenerator.ensureInitialized();

    MethodVisitor methodVisitor = super.visitMethod(intValue, text, text2, text3, textArray);
    return (intValue & 1024) == 0 && (intValue & 256) == 0
        ? new BytecodeVisitorSupport(methodVisitor, this.random.nextLong())
        : methodVisitor;
  }
}
