package states;

import game.Handler;
import gfx.Assets;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;

import configs.Configs;


import sounds.Sound;
import ui.UIImageButton;
import ui.UIManager;


public class VictoryState extends State{

    private UIManager uiManager;

    public VictoryState (Handler handler){
        super(handler);
        handler.setInGame(false);

        uiManager = new UIManager(handler);
        handler.getMouseManager().setUiManager(uiManager);

        stateSound = Sound.victory;
        handler.getSoundManager().addSound(stateSound);
        if(!Configs.IS_MUTE)
            stateSound.play();


        uiManager.addObject(new UIImageButton(150, 430,200, 100, Assets.restart,
                () -> {
                    handler.getMouseManager().setUiManager(null);
                    handler.setTele(true);
                    handler.getGame().gameState = new GameState(handler);
                    State.setState(handler.getGame().gameState);
                    stateSound.stop();
                    GameState.scores = 0;
                }));

        uiManager.addObject(new UIImageButton(450, 430,200, 100, Assets.exit, Platform::exit));
    }

    @Override
    public void tick() {
        uiManager.tick();
    }

    @Override
    public void render(GraphicsContext g) {
        g.drawImage(Assets.victory, 0, 0, Configs.STAGE_WIDTH, Configs.STAGE_HEIGHT);
        uiManager.render(g);
    }
}
