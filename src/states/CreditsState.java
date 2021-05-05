package states;

import game.Handler;
import gfx.Assets;
import javafx.scene.canvas.GraphicsContext;
import configs.Configs;
import sounds.Sound;
import ui.UIImageButton;
import ui.UIManager;

public class CreditsState extends State{
    private UIManager uiManager;
    public CreditsState(Handler handler) {
        super(handler);
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUiManager(uiManager);

        stateSound = Sound.uchiha;
        handler.getSoundManager().addSound(stateSound);
        if(!Configs.IS_MUTE)
            stateSound.play();

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
        g.drawImage(Assets.creditsState, 0, 0, Configs.STAGE_WIDTH, Configs.STAGE_HEIGHT);
        uiManager.render(g);
    }
}
