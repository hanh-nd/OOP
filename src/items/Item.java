package items;

import entities.creatures.Player;
import game.Handler;
import gfx.Assets;
import gfx.TextItem;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Item {

    public static Item[] items = new Item[256];
    public static Item lotionMana = new Item(Assets.lotionMana, "lotion mana", 0);
    public static Item lotionHP = new Item(Assets.lotionHP, "lotion hp", 1);
    public static Item lotionAttack = new Item(Assets.lotionDamage, "lotion damage", 2);
    //class

    public static final int ITEMWIDTH = 32, ITEMHEIGHT = 32;

    protected Handler handler;
    protected String name;
    protected Image texture;
    protected final int id;

    //chi dan
    protected String detail;

    protected int x, y ,count;
    protected boolean pickedUp = false;

    protected Rectangle bounds;

    public Item(Image texture, String name, int id){
        this.texture = texture;
        this.name = name;
        this.id = id;
        count = 1;

        bounds = new Rectangle(x, y , ITEMHEIGHT, ITEMWIDTH);

        items[id] = this;
    }

    public void tick(){
        if(handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0,0)
                .intersects(bounds.getBoundsInLocal())){
            pickedUp = true;
            handler.getWorld().getEntityManager().getPlayer().getInventory().addItem(this);
        }
    }

    public void render(GraphicsContext gc){
        if(handler == null){
            return;
        }
        render(gc, (int)(x - handler.getGameCamera().getxOffset()),
                (int)(y - handler.getGameCamera().getyOffset()));
    }

    public void render(GraphicsContext gc, int x, int y){
        gc.drawImage(texture, x , y, ITEMWIDTH, ITEMHEIGHT);
    }

    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
        bounds.setX(x);
        bounds.setY(y);
    }

    public Item createNew(int count){
        Item i = new Item(texture, name, id);
        i.setPickedUp(true);
        i.setCount(count);
        return i;
    }

    public Item createNew(int x, int y){
        Item i = new Item(texture, name, id);
        i.setPosition(x, y);
        return i;
    }

    public void use(Player player){
        switch (id){
            case 0:
                player.setSpeed(player.getSpeed() + 1);
                break;
            case 1:
                int h = player.getHealth() + 200;
                if (h <= player.getMaxHealth()) {
                    player.setHealth(h);
                } else {
                    player.setHealth(player.getMaxHealth());
                }
                player.setDamage(player.getDamage() + 5);
                break;
            case 2:
                player.setDamage(player.getDamage() + 10);
                break;
        }

    };

    public void printDetail(GraphicsContext g,int invX, int invY){
        switch (id){
            case 0:
                TextItem.drawString(g, "Speed: +1", 352 + invX, 172 + invY, Color.WHITE, Assets.font20);
                break;
            case 1:
                TextItem.drawString(g, "HP: +100", 352 + invX, 172 + invY, Color.WHITE, Assets.font20);
                TextItem.drawString(g, "Damage: +2", 352 + invX, 172 + invY + 30, Color.WHITE, Assets.font20);
                break;
            case 2:
                TextItem.drawString(g, "Damage: +10", 352 + invX, 172 + invY, Color.WHITE, Assets.font20);
                break;
        }

    }


    //getter and setter

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getTexture() {
        return texture;
    }

    public void setTexture(Image texture) {
        this.texture = texture;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isPickedUp() {
        return pickedUp;
    }

    public void setPickedUp(boolean pickedUp) {
        this.pickedUp = pickedUp;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
