package designpatterns.factory.abstrac.factory;

import designpatterns.factory.abstrac.product.AbstractProductA;
import designpatterns.factory.abstrac.product.AbstractProductB;

public abstract class AbstractFactory {

    public abstract AbstractProductA createAbstractProductA();

    public abstract AbstractProductB createAbstractProductB();


}
