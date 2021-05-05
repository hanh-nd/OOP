package items;

import game.Handler;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Iterator;

public class ItemManager {

    private Handler handler;
    private ArrayList<Item> items;


    public ItemManager(Handler handler){
        this.handler = handler;
        items = new ArrayList<Item>();
    }

    public void tick(){
        Iterator<Item> it = items.iterator();
        while(it.hasNext()){
            Item i = it.next();
            i.tick();
            if(i.isPickedUp()){
                it.remove();
            }
        }
    }

    public void render(GraphicsContext gc){
        for(Item i : items){
            i.render(gc);
        }
    }

    public void addItem(Item i){
        i.setHandler(handler);
        items.add(i);
    }

    //getters and setters

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}
