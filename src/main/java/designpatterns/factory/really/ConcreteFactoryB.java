package designpatterns.factory.really;

public class ConcreteFactoryB implements ReallyFactory {
    @Override
    public Product factoryMethod() {
        return new ConcreteProductB();
    }
}
