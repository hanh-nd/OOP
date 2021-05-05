package gfx;

import javafx.scene.text.Font;

import java.io.FileInputStream;

public class FontLoader {

    public static Font loadFont(String path, float size){
        try {

            return Font.loadFont(new FileInputStream(path), size);

        }
        catch(Exception e){
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("ha");
        return null;
    }
}
