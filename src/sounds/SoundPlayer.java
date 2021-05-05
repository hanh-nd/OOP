package sounds;

import javafx.scene.media.MediaPlayer;
import configs.Configs;

public class SoundPlayer {
    public static void PlaySound(MediaPlayer mp){
        if(!Configs.IS_MUTE){
            if(mp.getStatus() == MediaPlayer.Status.PLAYING)
                mp.stop();
            mp.play();
        }
    }

}
