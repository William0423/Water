package designpatterns.structure.builder;

public class Client {

    public static void main(String[] args) {
        ConcreteBuilder concreteBuilder = new ConcreteBuilder();
        Director director = new Director();
        director.setBuilder(concreteBuilder);
        Product pd = director.constructProduct();
        pd.show();

    }

}
