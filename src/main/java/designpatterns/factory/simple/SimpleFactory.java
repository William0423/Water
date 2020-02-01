package designpatterns.factory.simple;

/**
 * https://design-patterns.readthedocs.io/zh_CN/latest/creational_patterns/simple_factory.html
 * 设计模式的组成； 遵循的设计原则； 优点； 缺点； 适用的场景
 */
public class SimpleFactory {

    public static Product createProduct(String productName) {
        if (productName == "A") {
            return new ConcreteProductA();
        } else if (productName == "B") {
            return new ConcreteProductB();
        }
        return null;
    }

    public static void main(String[] args) {
        SimpleFactory.createProduct("A");
    }

}
