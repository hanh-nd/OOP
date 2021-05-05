package gfx;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ImageLoader {

    public static Image loadImage(String path){
        try {
            return new Image(new FileInputStream(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Image loadImage(String path, int width, int height){
        try {
            return new Image(new FileInputStream(path), width, height, true, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


}
