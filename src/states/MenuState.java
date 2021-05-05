package states;

import game.Handler;
import gfx.Assets;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import configs.Configs;

import sounds.Sound;
import ui.UIImageButton;
import ui.UIManager;

public class MenuState extends State{

    private UIManager uiManager;

    public MenuState(Handler handler){
        super(handler);
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUiManager(uiManager);

        stateSound = Sound.uchiha;
        handler.getSoundManager().addSound(stateSound);
        if(!Configs.IS_MUTE)
            stateSound.play();
        uiManager.addObject(new UIImageButton(50, 350,200, 100, Assets.credits,
                () -> {
                    handler.getMouseManager().setUiManager(null);
                    State.setState(new CreditsState(handler));
                }));

        uiManager.addObject(new UIImageButton(300, 350,200, 100, Assets.start,
                () -> {
                    handler.getMouseManager().setUiManager(null);
                    State.setState(new DifficultyState(handler));
                }));

        uiManager.addObject(new UIImageButton(550, 350,200, 100, Assets.exit, Platform::exit));

        uiManager.addObject(new UIImageButton(300, 480,180, 120, Assets.mute_unmute,
                () -> {
                    if(!Configs.IS_MUTE) {
                        handler.getSoundManager().muteAll();
                        Configs.IS_MUTE = true;
                    } else {
                        handler.getSoundManager().unMuteAll();
                        Configs.IS_MUTE = false;
                    }
                }));

    }

    @Override
    public void tick() {
        uiManager.tick();
    }

    @Override
    public void render(GraphicsContext g) {
        g.drawImage(Assets.background, 0, 0, Configs.STAGE_WIDTH, Configs.STAGE_HEIGHT);
        uiManager.render(g);
    }
}