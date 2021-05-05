package ui;

import game.Handler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class UIManager {

    private Handler handler;
    private ArrayList<UIObject> objects;

    public UIManager(Handler handler){
        this.handler = handler;
        objects = new ArrayList<>();
    }

    public void tick(){
        for(UIObject o : objects){
            o.tick();
        }
    }

    public void render(GraphicsContext g){
        for(UIObject o : objects){
            o.render(g);
        }
    }

    public void onMouseMove(MouseEvent e){
        for(UIObject o : objects){
            o.onMouseMove(e);
        }
    }

    public void onMouseRelease(MouseEvent e){
        for(UIObject o : objects){
            o.onMouseRelease(e);
        }
    }

    public void addObject(UIObject o){
        objects.add(o);
    }

    public void removeObject(UIObject o){
        objects.remove(o);
    }

    //Getters & Setters

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public ArrayList<UIObject> getObjects() {
        return objects;
    }

    public void setObjects(ArrayList<UIObject> objects) {
        this.objects = objects;
    }
}
