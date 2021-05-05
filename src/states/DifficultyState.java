package states;

import game.Handler;
import gfx.Assets;
import javafx.scene.canvas.GraphicsContext;
import configs.Configs;
import sounds.Sound;
import ui.UIImageButton;
import ui.UIManager;

public class DifficultyState extends State{
    private UIManager uiManager;
    public DifficultyState(Handler handler) {
        super(handler);
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUiManager(uiManager);

        stateSound = Sound.uchiha;
        handler.getSoundManager().addSound(stateSound);
        if(!Configs.IS_MUTE)
            stateSound.play();

        uiManager.addObject(new UIImageButton(60, 250,160, 160, Assets.easy,
                () -> {
                    handler.getMouseManager().setUiManager(null);
                    handler.setTele(true);
                    handler.setDifficulty(0);
                    handler.getGame().gameState = new GameState(handler);
                    State.setState(handler.getGame().gameState);
                    stateSound.stop();
                }));
        uiManager.addObject(new UIImageButton(320, 250,160, 160, Assets.medium,
                () -> {
                    handler.getMouseManager().setUiManager(null);
                    handler.setTele(true);
                    handler.setDifficulty(1);
                    handler.getGame().gameState = new GameState(handler);
                    State.setState(handler.getGame().gameState);
                    stateSound.stop();
                }));
        uiManager.addObject(new UIImageButton(580, 250,160, 160, Assets.hard,
                () -> {
                    handler.getMouseManager().setUiManager(null);
                    handler.setTele(true);
                    handler.setDifficulty(2);
                    handler.getGame().gameState = new GameState(handler);
                    State.setState(handler.getGame().gameState);
                    stateSound.stop();
                }));

        uiManager.addObject(new UIImageButton(30, 500,100, 75, Assets.back,
                () -> {
                    handler.getMouseManager().setUiManager(null);
                    handler.setTele(true);
                    handler.setDifficulty(0);
                    handler.getGame().menuState = new MenuState(handler);
                    State.setState(handler.getGame().menuState);
                }));
    }

    @Override
    public void tick() {
        uiManager.tick();
    }

    @Override
    public void render(GraphicsContext g) {
        g.drawImage(Assets.difficultState, 0, 0, Configs.STAGE_WIDTH, Configs.STAGE_HEIGHT);
        uiManager.render(g);
    }
}
