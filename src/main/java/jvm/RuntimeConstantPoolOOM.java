package jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * 运行时常量池溢出：
 * 参数：
 * 使用-XX:PermSize=10m -XX:MaxPermSize=10m
 */
public class RuntimeConstantPoolOOM{

    public static void main(String[] args){
        List<String> list = new ArrayList<String>();
        int i = 0;
        while(true){
            list.add(String.valueOf(i++).intern());
            System.out.println(i);
        }
    }
}
