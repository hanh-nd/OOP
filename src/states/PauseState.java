package states;

import game.Handler;
import gfx.Assets;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;

import configs.Configs;

import ui.UIImageButton;
import ui.UIManager;

public class PauseState extends State{

    private UIManager uiManager;

    public PauseState (Handler handler){
        super(handler);
        handler.setInGame(false);

        uiManager = new UIManager(handler);
        handler.getMouseManager().setUiManager(uiManager);

        uiManager.addObject(new UIImageButton(320, 120,180, 90, Assets.resume,
                () -> {
                    handler.getMouseManager().setUiManager(null);
                    handler.setInGame(true);
                    State.setState(handler.getGame().gameState);
                }));

        uiManager.addObject(new UIImageButton(320, 220, 180, 90, Assets.main_menu,
                () -> {
                    handler.getMouseManager().setUiManager(null);
                    handler.getGame().menuState = new MenuState(handler);
                    State.setState(handler.getGame().menuState);
                }));


        uiManager.addObject(new UIImageButton(320, 320,180, 90, Assets.exit, Platform::exit));

        uiManager.addObject(new UIImageButton(320, 400,180, 120, Assets.mute_unmute,
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
        g.drawImage(Assets.pause, 0, 0, Configs.STAGE_WIDTH, Configs.STAGE_HEIGHT);
        uiManager.render(g);
    }
}
