package jvm;

import java.util.Vector;

/**
 * 测试的两个参数：
 * -Xmx5M -Xmx11M
 */
public class JavaHeapTest1 {

    public static void main(String[] args) {
        Vector x = new Vector();
        for (int i =0;i<10;i++) {
//            Byte[] b = new Byte[1024*1024];
//            x.add(b);

            System.out.println(i);
        }

        System.out.println("可用的最大堆" + Runtime.getRuntime().maxMemory()/1024/1024+"M");

    }


}
