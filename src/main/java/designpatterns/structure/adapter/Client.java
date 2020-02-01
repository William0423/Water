package designpatterns.structure.adapter;

public class Client {

    public static void main(String[] args) {
        // 请求者
        Adaptee adaptee = new Adaptee();
        // 根据请求者适配对应的能够访问Target的适配器，
        Target target = new Adapter(adaptee);

        target.request();

    }
}
