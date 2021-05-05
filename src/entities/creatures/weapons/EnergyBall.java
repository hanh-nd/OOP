package entities.creatures.weapons;

import entities.creatures.Creature;
import game.Handler;
import gfx.Assets;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sounds.Sound;


public class EnergyBall extends Creature {
    private int direction;
    private double xP;
    private double yP;

    // vi tri dau boss
    private double xHeadBoss;
    private double yHeadBoss;

    //move
    private double moveX;
    private double moveY;

    private long speed;


    // distance
    private double distance;

    //timer
    private long timer;
    private long lastTime;

    private long startTime;
    private long attackTimer;
    private long countDown;



    private boolean isHit= false;

    private double angle;


    public EnergyBall(Handler handler, Image image, double x, double y, int direction, double xP, double yP) {
        super(handler, image, x, y, 182, 206, 50);

        this.direction = direction;
        this.xP = xP + 64/2;
        this.yP = yP + 64/2;

        if(direction == 1){ //up
            xHeadBoss = 72*3 + x;
            yHeadBoss = 28*3 + y;
        }
        else if(direction == 2){ //down
            xHeadBoss = 73*3 + x;
            yHeadBoss = 100*3 + y;
        }else if(direction == 4){ //right
            xHeadBoss = 19*3 + x;
            yHeadBoss = 75*3 + y;
        }else if(direction == 3){ //left
            xHeadBoss = 124*3 + x;
            yHeadBoss = 70*3 + y;
        }

        x = xHeadBoss;
        y = yHeadBoss;

        startTime = System.currentTimeMillis();

        countDown = 1000;
        distance = Math.sqrt(Math.pow(xP - xHeadBoss, 2) + Math.pow(yP-yHeadBoss,2));

        if(distance != 0) {
            moveX = (xP-xHeadBoss)/distance;
            moveY = (yP-yHeadBoss)/distance;

        }else{
            moveX = 0;
            moveY = 0;
        }

        speed = countDown/10;
        timer = 0;
        lastTime= System.currentTimeMillis();

        bounds.setX(-50);
        bounds.setY(-50);
        bounds.setWidth(96);
        bounds.setHeight(106);

    }

    @Override
    public void tick() {
        long temp= System.currentTimeMillis() - startTime ;
        if(temp > countDown){
            die();
        }else if(temp > (countDown / 4) * 3){
            image = Assets.energyBall4;
        }else if(temp > (countDown /4) * 2){
            image = Assets.energyBall3;
        }else if(temp > (countDown/4)){
            image =Assets.energyBall2;
        }

        xHeadBoss += moveX * distance/30;
        yHeadBoss += moveY * distance/30;
        x = xHeadBoss;
        y = yHeadBoss;

    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(image, (int)(xHeadBoss - handler.getGameCamera().getxOffset() - 182/2),
                (int) (yHeadBoss - handler.getGameCamera().getyOffset() - 206/2) );
        if(checkHit()){
            die();
        }


    }

    @Override
    public void die() {
        active = false;

    }

    public boolean checkHit(){

        if(handler.getWorld().getEntityManager()
                .getPlayer()
                .getAttackBounds(0,0).intersects(getCollisionBounds(0,0).getBoundsInLocal())){
            handler.getWorld().getEntityManager().getPlayer().takeDamage(damage);
            Sound.playSound(Sound.hurt);
            return true;
        }

        return false;
    }
}
