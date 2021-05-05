package entities.creatures;

import entities.Entity;
import game.Handler;
import javafx.scene.image.Image;
import configs.Configs;


public abstract class Creature extends Entity {

    protected double speed;
    protected double xMove, yMove;
    protected int direction = 2; //1-up, 2-down, 3-left, 4-right
    protected int damage;

    public Creature(Handler handler, Image image, double x, double y, int width, int height, int damage){
        super(handler, image, x, y, width, height);
        this.damage = damage;
        speed = Configs.DEFAULT_SPEED + handler.getDifficulty()+0.5;
        xMove = 0;
        yMove = 0;
    }

    public void move(){
        if(!checkEntityCollision(xMove, 0f))
            moveX();
        if(!checkEntityCollision(0f, yMove))
            moveY();
    }

    public void moveX(){
        if(xMove > 0){ //Moving right
            int tx = (int) (x + xMove + bounds.getX() + bounds.getWidth()) / Configs.TILE_WIDTH;

            if(!collisionWithTile(tx, (int) (y + bounds.getY()) / Configs.TILE_HEIGHT) &&
                    !collisionWithTile(tx, (int) (y + bounds.getY() + bounds.getHeight()) / Configs.TILE_HEIGHT)){
                x += xMove/2;
            } else {
                x = (int) (tx * Configs.TILE_WIDTH - bounds.getX() - bounds.getWidth() - 1);
            }
        } else if (xMove < 0){ //Moving left
            int tx = (int) (x + xMove + bounds.getX()) / Configs.TILE_WIDTH;

            if (!collisionWithTile(tx, (int) (y + bounds.getY()) / Configs.TILE_HEIGHT) &&
                    !collisionWithTile(tx, (int) (y + bounds.getY() + bounds.getHeight()) / Configs.TILE_HEIGHT)) {
                x += xMove/2;
            } else {
                x = (int) (tx* Configs.TILE_WIDTH + Configs.TILE_WIDTH - bounds.getX());
            }
        }
    }

    public void moveY(){
        if(yMove < 0){ //Moving up
            int ty = (int) (y + yMove + bounds.getY()) / Configs.TILE_HEIGHT;

            if(!collisionWithTile((int) (x + bounds.getX()) / Configs.TILE_WIDTH, ty) &&
                    !collisionWithTile((int) (x + bounds.getX() + bounds.getWidth()) / Configs.TILE_WIDTH, ty)){
                y += yMove/2;
            } else {
                y = (int) (ty * Configs.TILE_HEIGHT + Configs.TILE_HEIGHT - bounds.getY());
            }
        } else if (yMove > 0){ //Moving down
            int ty = (int) (y + yMove + bounds.getY() + bounds.getHeight()) / Configs.TILE_HEIGHT;

            if(!collisionWithTile((int) (x + bounds.getX()) / Configs.TILE_WIDTH, ty) &&
                    !collisionWithTile((int) (x + bounds.getX() + bounds.getWidth()) / Configs.TILE_WIDTH, ty)){
                y += yMove/2;
            } else {
                y= (int) (ty * Configs.TILE_HEIGHT - bounds.getY() - bounds.getHeight() - 1);
            }
        }
    }

    protected boolean collisionWithTile(int x, int y){
            for(int z = 0; z<handler.getWorld().getLayer(); z++) {
                if(handler.getWorld().getTile(x, y, z).isSolid()){
                    return true;
                }
            }
            return false;
    }





    //Getters & Setters

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getxMove() {
        return xMove;
    }

    public void setxMove(double xMove) {
        this.xMove = xMove;
    }

    public double getyMove() {
        return yMove;
    }

    public void setyMove(double yMove) {
        this.yMove = yMove;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
