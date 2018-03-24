package jvm;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class DirectMemoryOOM {

        private static final int _1MB = 1024* 1024;

        public static void main(String[] args) throws Exception {
            Field unsafeField = Unsafe.class.getDeclaredFields()[0];
            unsafeField.setAccessible(true);
            Unsafe unsafe = (Unsafe) unsafeField.get(null);
            while(true){
                System.out.println("##################");
                //unsafe直接想操作系统申请内存
                unsafe.allocateMemory(_1MB);
            }
    }
}
