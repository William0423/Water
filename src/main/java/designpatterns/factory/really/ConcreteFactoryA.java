package designpatterns.factory.really;

public class ConcreteFactoryA implements ReallyFactory {
    @Override
    public Product factoryMethod() {
        return new ConcreteProductA();
    }
}
