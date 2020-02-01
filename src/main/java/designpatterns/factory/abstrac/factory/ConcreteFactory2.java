package designpatterns.factory.abstrac.factory;

import designpatterns.factory.abstrac.product.AbstractProductA;
import designpatterns.factory.abstrac.product.AbstractProductB;
import designpatterns.factory.abstrac.product.ProductA2;
import designpatterns.factory.abstrac.product.ProductB2;

/**
 * 一个facatory对应多个产品， 如此处对应产品 A2 B2
 */
public class ConcreteFactory2 extends AbstractFactory {
    @Override
    public AbstractProductA createAbstractProductA() {
        return new ProductA2();
    }

    @Override
    public AbstractProductB createAbstractProductB() {
        return new ProductB2();
    }
}
