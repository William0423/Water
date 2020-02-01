package designpatterns.structure.bridge.demo1;

/** "Implementor" */
// 定义具体行为、具体特征的应用接口
public interface DrawingAPI {

    public void drawCircle(double x, double y, double radius);
}
