package sounds;

import javafx.scene.media.Media;

import java.io.File;

public class SoundLoader {

    public static Media loadSound(String path){
        return new Media(new File(path).toURI().toString());
    }

}
