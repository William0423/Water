package designpatterns.factory.abstrac;

import designpatterns.factory.abstrac.factory.AbstractFactory;
import designpatterns.factory.abstrac.factory.ConcreteFactory1;
import designpatterns.factory.abstrac.factory.ConcreteFactory2;
import designpatterns.factory.abstrac.product.AbstractProductA;
import designpatterns.factory.abstrac.product.AbstractProductB;

public class Client {

    public static void main(String[] args) {
        AbstractFactory factory1 = new ConcreteFactory1();
        AbstractProductA productA1 = factory1.createAbstractProductA();
        productA1.use();

        AbstractProductB productB1 = factory1.createAbstractProductB();
        productB1.eat();


        AbstractFactory factory2 = new ConcreteFactory2();
        AbstractProductA productA2 = factory2.createAbstractProductA();
        productA2.use();


        AbstractProductB productB2 = factory2.createAbstractProductB();
        productB2.eat();



    }

}
