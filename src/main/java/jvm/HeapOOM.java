package jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * java堆溢出 错误测试：
 * java.lang.OutOfMemoryError: Java heap space
 * jvm参数设置：
 * -Xms10m -Xmx10m -XX:+HeapDumpOnOutOfMemoryError
 */
public class HeapOOM{

    static class OOMObject{

    }

    public static void main(String[] args){
        List<OOMObject> list = new ArrayList<OOMObject>();

        while(true){
            list.add(new OOMObject());

        }
    }
}

//public class HeapOOM {
//
//    static class OOMObject {
//
//        public static void main(String[] args) {
//
//            List<OOMObject> list = new ArrayList<OOMObject>();
//
//            while (true) {
//
//                list.add(new OOMObject());
//            }
//        }
//    }
//}