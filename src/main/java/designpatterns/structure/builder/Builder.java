package designpatterns.structure.builder;

public abstract class Builder {

    protected Product product;

    public void createProductInstance() {
        product = new Product();
    }

    /**
     * 抽象类中返回结果，实现类中构建复杂产品
     * @return
     */
    public Product getProduct() {
        return product;
    }

    public abstract void buildPartA();

    public abstract void buildPartB();

    public abstract void buildPartC();


}
