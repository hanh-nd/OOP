package sounds;
import javafx.scene.media.MediaPlayer;
import configs.Configs;


public class Sound {

    public static MediaPlayer main = new MediaPlayer(SoundLoader.loadSound("res/sounds/main_theme.wav"));
    public static MediaPlayer footstep = new MediaPlayer(SoundLoader.loadSound("res/sounds/foot_step.wav"));
    public static MediaPlayer hurt = new MediaPlayer(SoundLoader.loadSound("res/sounds/ouch.wav"));
    public static MediaPlayer uchiha = new MediaPlayer(SoundLoader.loadSound("res/sounds/uchiha_theme.wav"));
    public static MediaPlayer gameover = new MediaPlayer(SoundLoader.loadSound("res/sounds/gameover.wav"));
    public static MediaPlayer victory = new MediaPlayer(SoundLoader.loadSound("res/sounds/victory.wav"));
    public static MediaPlayer punch = new MediaPlayer(SoundLoader.loadSound("res/sounds/punch.wav"));
    public static MediaPlayer player_fired = new MediaPlayer(SoundLoader.loadSound("res/sounds/player_fired.wav"));
    public static MediaPlayer player_sword = new MediaPlayer(SoundLoader.loadSound("res/sounds/sword-arm-2a.wav"));
    public static MediaPlayer cut = new MediaPlayer(SoundLoader.loadSound("res/sounds/sword-1a.wav"));
    public static MediaPlayer boom = new MediaPlayer(SoundLoader.loadSound("res/sounds/boom.wav"));
    public static MediaPlayer dragon_fired = new MediaPlayer(SoundLoader.loadSound("res/sounds/dragon_fired.wav"));

    public static void playSound(MediaPlayer mediaPlayer){
        if (!Configs.IS_MUTE) {
            if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING)
                mediaPlayer.stop();
            mediaPlayer.play();
        }
    }
}

