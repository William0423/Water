package designpatterns.structure.adapter;

public class Adapter extends Target
{

    private Adaptee adaptee;

    public Adapter(Adaptee adaptee) {

        this.adaptee = adaptee;

    }


    @Override
    public void request() {
        adaptee.specificRequest();
    }
}
