package entities.creatures.weapons;

import entities.Entity;
import game.Handler;
import javafx.scene.image.Image;
import sounds.SoundPlayer;

public class Spell extends Bullet{

    public Spell(Handler handler, Image image, double x, double y, int damage, int direction) {
        super(handler, image, x, y, damage, direction);
        setSpeed(6);
    }

    @Override
    public void checkHit(){
        //Enemy hit
        for(Entity e : handler.getWorld().getEntityManager().getEntities()){
            if(!e.equals(handler.getWorld().getEntityManager().getPlayer()))
                continue;
            if(e.getCollisionBounds(0, 0).intersects(getCollisionBounds(0,0).getBoundsInLocal())){
                e.takeDamage(damage);
                SoundPlayer.PlaySound(mediaPlayer);
                die();
            }
        }

        //Bullet move
        if(Math.abs(xLong) > 100 || Math.abs(yLong) > 100)
            die();
    }

    @Override
    public void cut(){
        xMove = 0;
        yMove = 0;

        if(y > handler.getWorld().getEntityManager().getPlayer().getY() + 25){ //up
            direction = 1;
            yMove = -speed;
        }
        if(y < handler.getWorld().getEntityManager().getPlayer().getY() + 25){ //down
            direction = 2;
            yMove = speed;
        }
        if(x < handler.getWorld().getEntityManager().getPlayer().getX() + 25){ //right
            direction = 4;
            xMove = speed;
        }
        if(x > handler.getWorld().getEntityManager().getPlayer().getX() + 25){ //left
            direction = 3;
            xMove = -speed;
        }
    }
}
