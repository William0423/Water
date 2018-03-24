package jvm;

import java.util.Vector;

public class JavaHeapTest2 {

    public static void main(String[] args) {

        Vector v = new Vector();

        for(int i=0;i<10;i++) {
            Byte[] b = new Byte[1024*1024];
            v.add(b);


            if (v.size() == 3) {
                System.out.println(i);
                v.clear();
            }
        }

    }

}
