package language.reflect.bytecode;

import java.lang.management.ManagementFactory;

public class Base {

    /**
     * https://mp.weixin.qq.com/s?__biz=MjM5NjQ5MTI5OA==&mid=2651750626&idx=1&sn=3e1ac6c41d6e1803abb32285daf0244a&chksm=bd1259af8a65d0b97809a6a8ff5afaff1be4a4232bd8527ef9d95bb7a2e768bd7d9fdc768211&scene=21#wechat_redirect
     */
    public void process(){
        System.out.println("process");
    }

    public static void main(String[] args) {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        System.out.println(name);
        String s = name.split("@")[0];
        //打印当前Pid
        System.out.println("pid:"+s);
        Base base = new Base();
        while (true) {
            try {
                Thread.sleep(5000L);
            } catch (Exception e) {
                break;
            }
            base.process();
        }
    }

}
