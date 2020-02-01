package designpatterns.structure.decorator.demo1;

public class VerticalScrollBarDecorator extends WindowDecorator
{
    public VerticalScrollBarDecorator(Window windowToBeDecorated) {
        super(windowToBeDecorated);
    }


    @Override
    public void draw() {
        super.draw();
        drawVerticalScrollBar();
    }

    // 此处为修饰的具体内容
    private void drawVerticalScrollBar() {
        // Draw the vertical scrollbar
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", including vertical scrollbars";
    }

}
