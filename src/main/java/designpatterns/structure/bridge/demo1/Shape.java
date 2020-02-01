package designpatterns.structure.bridge.demo1;

/** "Abstraction" */
interface Shape
{
    public void draw();                                            // low-level
    public void resizeByPercentage(double pct);     // high-level
}

