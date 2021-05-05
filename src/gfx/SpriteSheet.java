package gfx;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public class SpriteSheet {
    private Image image;
    private PixelReader reader;
    public SpriteSheet(Image image){
        this.image = image;
        this.reader = image.getPixelReader();
    }

    public Image crop(int x, int y, int width, int height){
        WritableImage writableImage = new WritableImage(reader, x, y, width, height);
        return writableImage;
    }
}
