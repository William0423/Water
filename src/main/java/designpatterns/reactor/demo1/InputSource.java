package designpatterns.reactor.demo1;

public class InputSource {

    private Object data;
    private long id;


    public InputSource (Object data, long id) {
        this.data = data;
        this.id = id;
    }

    @Override
    public String toString() {
        return "InputSource{" +
                "data=" + data +
                ", id=" + id +
                '}';
    }

}
