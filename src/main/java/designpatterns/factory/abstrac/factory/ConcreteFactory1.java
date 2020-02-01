package designpatterns.factory.abstrac.factory;

import designpatterns.factory.abstrac.product.AbstractProductA;
import designpatterns.factory.abstrac.product.AbstractProductB;
import designpatterns.factory.abstrac.product.ProductA1;
import designpatterns.factory.abstrac.product.ProductB1;

public class ConcreteFactory1 extends AbstractFactory {
    @Override
    public AbstractProductA createAbstractProductA() {
        return new ProductA1();
    }

    @Override
    public AbstractProductB createAbstractProductB() {
        return new ProductB1();
    }
}
