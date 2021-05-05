package sounds;

import game.Handler;
import javafx.scene.media.MediaPlayer;

import java.util.ArrayList;

public class SoundManager {

    private Handler handler;
    private ArrayList<MediaPlayer> sounds;

    public SoundManager(Handler handler){
        this.handler = handler;
        sounds = new ArrayList<>();
    }

    public void addSound(MediaPlayer mediaPlayer){
        sounds.add(mediaPlayer);
    }

    public MediaPlayer getSound(MediaPlayer mediaPlayer){
        for(MediaPlayer mediaP : sounds){
            if(mediaP.equals(mediaPlayer))
                return mediaP;
        }
        return null;
    }

    public void soundOff(){
        for(MediaPlayer mediaP : sounds)
            mediaP.stop();
    }

    public void muteAll(){
        for (MediaPlayer mediaP : sounds)
            mediaP.setMute(true);
    }

    public void unMuteAll(){
        for (MediaPlayer mediaP : sounds)
            mediaP.setMute(false);
    }

    public void setVolume(int volume){
        for(MediaPlayer mediaP : sounds)
            mediaP.setVolume(volume);
    }


    public ArrayList<MediaPlayer> getSoundList(){
        return sounds;
    }
}
