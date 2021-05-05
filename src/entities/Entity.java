package entities;

import game.Handler;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import configs.Configs;

public abstract class Entity {

    protected double x, y;
    protected int width, height;
    protected Handler handler;
    protected Image image;
    protected ImageView imageView;
    protected SnapshotParameters params;
    protected int health, maxHealth;

    protected boolean active = true;

    public Rectangle bounds;

    public Entity(Handler handler, Image image, double x, double y, int width, int height){
        this.handler = handler;
        this.image = image;

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        health = Configs.DEFAULT_HEALTH + 100*handler.getDifficulty();// + 100*(handler.getWorld().getCountWorld()-1);
        maxHealth = health;

        params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);

        bounds = new Rectangle(0, 0, width, height);

    }

    public abstract void tick();

    public abstract void render(GraphicsContext g);

    public abstract void die();

    public void takeDamage(int amount){
        health -= amount;
        if(health <= 0){
            active = false;
            die();
        }
    }

    public boolean checkEntityCollision(double xOffset, double yOffset){
        for(Entity e : handler.getWorld().getEntityManager().getEntities()){
            if(e.equals(this))
                continue;
            if(e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset).getBoundsInLocal())){
                return true;
            }
        }
        return false;
    }


    public Rectangle getCollisionBounds(double xOffset, double yOffset){
        return new Rectangle((int) (x + bounds.getX() + xOffset),
                        (int) (y + bounds.getY() + yOffset), bounds.getWidth(), bounds.getHeight());
    }

    //Getters & Setters

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth(){
        return maxHealth;
    }

    public void setHealth(int health) {
        this.health = health;
        this.maxHealth = health;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
