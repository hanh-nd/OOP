package ui;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class UIImageButton extends UIObject{

    private Image image;
    private ClickListener clicker;

    public UIImageButton(double x, double y, int width, int height, Image image, ClickListener clicker) {
        super(x, y, width, height);
        this.image = image;
        this.clicker = clicker;
    }

    @Override
    public void tick() {
    }

    @Override
    public void render(GraphicsContext g) {
        g.drawImage(image, (int) x, (int) y, width, height);
    }

    @Override
    public void onClick() {
        clicker.onClick();
    }
}
