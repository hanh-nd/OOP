package states;

import game.Handler;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.MediaPlayer;

public abstract class State {

    private static State currentState = null;
    public static void setState(State state){
        currentState = state;
    }

    public static State getState(){
        return currentState;
    }
    public MediaPlayer stateSound;

    //Class

    protected Handler handler;

    public State(Handler handler){
        this.handler = handler;
    }

    public abstract void tick();

    public abstract void render(GraphicsContext g);
}
