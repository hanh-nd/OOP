package inventory;

import game.Handler;
import gfx.Assets;
import gfx.TextItem;
import items.Item;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Inventory {
    private int invX = 144, invY = 108, invWidth = 512, invHeight = 384;

    private int invListCenterX = invX + 20;
    private int invListCenterY = invY + 205;
    private int invListSpacing = 30;

    private int invImageX = invX + 385, invImageY = invY + 35,
                invImageWidth = 64, invImageHeight = 64;

    private int invCountX = invX + 400, invCountY = invY + 132;

    private int selectedItem = 0;

    private Handler handler;
    private boolean active = false;
    private ArrayList<Item> inventoryItems;

    public Inventory(Handler handler){
        this.handler = handler;
        inventoryItems = new ArrayList<Item>();

        addItem(Item.lotionHP.createNew(1));
        addItem(Item.lotionAttack.createNew(1));
        addItem(Item.lotionMana.createNew(1));

    }

    public void tick(){
        if(!active)
            return;
    }

    public void scrollDown() {
        if (selectedItem + 1 < inventoryItems.size()){
            selectedItem++;
        }else{
            selectedItem = 0;
        }
    }

    public void scrollUp(){
        if(selectedItem - 1 >= 0) {
            selectedItem--;
        }
        else{
            selectedItem = inventoryItems.size() - 1;
        }
    }

    public void render(GraphicsContext g){
        if(!active){
            return;
        }

        g.drawImage(Assets.inventoryScreen, invX, invY, invWidth, invHeight);

        int len = inventoryItems.size();
        if(len == 0){
            return;
        }

        //draw name of item
        for(int i = -5; i< 6; i++){
            if(selectedItem + i < 0|| selectedItem + i >= len){
                continue;
            }

            if(i ==0){
                TextItem.drawString(g, "> "+inventoryItems.get(selectedItem + i).getName() +" <",
                        invListCenterX, invListCenterY + invListSpacing * i, Color.YELLOW, Assets.font28);
            }else {

                TextItem.drawString(g, inventoryItems.get(selectedItem + i).getName(),
                        invListCenterX, invListCenterY + invListSpacing * i, Color.WHITE, Assets.font28);
            }
        }

        //draw item image
        Item item = inventoryItems.get(selectedItem);
        g.drawImage(item.getTexture(), invImageX, invImageY, invImageWidth,invImageHeight);
        //draw number of chosen item
        TextItem.drawString(g, Integer.toString(item.getCount()),
                    invCountX,invCountY, Color.WHITE, Assets.font28);

        //draw detail of chosen item
        TextItem.drawString(g, item.getDetail(), 352 + invX, 172 + invY, Color.WHITE, Assets.font28);
        item.printDetail(g, invX, invY);

    }

    //inventory methods

    public void addItem(Item item){
        for(Item i : inventoryItems){
            if(i.getId() == item.getId()){
                i.setCount(i.getCount() + item.getCount());
                return;
            }
        }
        inventoryItems.add(item);
    }

    public void useItem(){
        if(inventoryItems.size() != 0) {
            int countI = inventoryItems.get(selectedItem).getCount();
            inventoryItems.get(selectedItem).setCount(countI -1);
            inventoryItems.get(selectedItem).use(handler.getWorld().getEntityManager().getPlayer());
            if(countI - 1 == 0){
                inventoryItems.remove(inventoryItems.get(selectedItem));
                if(selectedItem == 0){
                    selectedItem =0;
                }
                else{
                    selectedItem --;
                }
            }

        }
    }

    //handler
    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public boolean isActive() {
        return active;
    }

    public void changeActive() {
        this.active = !this.active;
    }
}
