package designpatterns.structure.bridge.demo1;

public class BridgePattern {

    public static void main(String[] args)
    {
        Shape[] shapes = new Shape[2];
        shapes[0] = new ShapeCircle(1, 2, 3, new DrawingAPI1());
        shapes[1] = new ShapeCircle(5, 7, 11, new DrawingAPI2());

        for (Shape shape : shapes)
        {
            shape.resizeByPercentage(2.5);
            shape.draw();
        }
    }

}
