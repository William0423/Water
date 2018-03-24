package jvm;

/**
 * 单线程环境下 虚拟机栈深度溢出测试
 * 可以不需要设置参数也会
 * Exception in thread "main" java.lang.StackOverflowError
 *
 */
public class JavaVMStackOF {

    private int stackLength = 1;

    public void stackLeak() {
        stackLength++;
        stackLeak();
    }

    public static void main(String args[]) {
        JavaVMStackOF oom = new JavaVMStackOF();
        oom.stackLeak();
    }


}
