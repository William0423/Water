package designpatterns.factory.really;

public class Client {
    public static void main(String[] args) {

        /**
         * 利用多态
         */
        ReallyFactory concreteFactoryA = new ConcreteFactoryA();
        Product product = concreteFactoryA.factoryMethod();
        product.use();

        ReallyFactory conB = new ConcreteFactoryB();
        Product productB = conB.factoryMethod();
        productB.use();

    }
}
