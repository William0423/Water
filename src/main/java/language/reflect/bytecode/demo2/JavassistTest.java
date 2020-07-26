package language.reflect.bytecode.demo2;

import javassist.*;
import language.reflect.bytecode.Base;

import java.io.IOException;

public class JavassistTest {

    public static void main(String[] args) throws NotFoundException, CannotCompileException, IllegalAccessException, InstantiationException, IOException {
//        Base b = new Base(); // JVM是不允许在运行时动态重载一个类的。
        ClassPool cp = ClassPool.getDefault();
        CtClass cc = cp.get("language.reflect.bytecode.Base");
        CtMethod m = cc.getDeclaredMethod("process");
        m.insertBefore("{ System.out.println(\"start\"); }");
        m.insertAfter("{ System.out.println(\"end\"); }");
        Class c = cc.toClass();
        cc.writeFile("/Users/admin/Desktop/personal/workspace/intellij/Water/target/classes/language/reflect/bytecode/demo2");
        Base h = (Base)c.newInstance();
        h.process();
    }

}
