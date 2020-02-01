package designpatterns.structure.adapter;

public class Adaptee {

    public void specificRequest() {
        System.out.println(Adaptee.class.getName() + " request");
    }

}
