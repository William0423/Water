package designpatterns.structure.builder;

public class ConcreteBuilder extends Builder {


    @Override
    public void buildPartA() {
        product.setM_a("part a");
    }

    @Override
    public void buildPartB() {
        product.setM_b("part b");
    }

    @Override
    public void buildPartC() {
        product.setM_c("part c");
    }
}
