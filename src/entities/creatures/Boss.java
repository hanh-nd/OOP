package entities.creatures;

import entities.creatures.weapons.EnergyBall;
import game.Handler;
import gfx.Assets;
import gfx.SpriteAnimation;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import configs.Configs;
import sounds.Sound;

public class Boss extends Enemy{

    int count = 3;
    int columns = 3;
    int offsetX = 0;
    int offsetY = 0;
    int width = 144;
    int height = 128;

    private boolean isAttack = false;

    // fire breath
    private static long fireTimer, lastFireTimer = 3000, fireCoolDown = 3000;

    //normal attack
    private long lastAttackTimer, attackCoolDown = 1000, attackTimer = attackCoolDown;
    private long distanceA;

    SpriteAnimation animation;
    Image enemy;

    public long getDistanceA() {
        return distanceA;
    }

    public void setDistanceA(long distanceA) {
        this.distanceA = distanceA;
    }

    public Boss(Handler handler, double x, double y) {
        super(handler, Assets.bossblue, x, y);
        setWidth(144);
        setHeight(128);

        imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setViewport(new Rectangle2D(offsetX,offsetY,width,height));
        animation = new SpriteAnimation(imageView, Duration.millis(1000),count,columns,offsetX,offsetY,width,height);

        bounds.setX(42 * 3);
        bounds.setY(45 * 3);
        bounds.setWidth(54 *3);
        bounds.setHeight(54 *3);

        //health
        health = 1500;
        maxHealth = 1500;

        //range normal attack
        distanceA = 60 * 60 * 3 * 3;
        setDamage(50);
    }

    @Override
    public void tick() {
        if(!isAttack) {
            //Movements
            chasePlayerMove();
            backHomeMove();
            move();

            //Recover
            selfRecovery();

        }
        //Attack
        checkAttacks();
        setAnimation();
        checkFire();
    }

    @Override
    public void chasePlayerMove(){
        xMove = 0;
        yMove = 0;

        if(checkPlayerZone()){
            if(bounds.getY() + y +bounds.getHeight()/2> handler.getWorld().getEntityManager().getPlayer().getY() + 1){ //up
                direction = 1;
                yMove = -speed;
            }
            if(bounds.getY() + y +bounds.getHeight()/2<  handler.getWorld().getEntityManager().getPlayer().getY() - 1){ //down
                direction = 2;
                yMove = speed;
            }
            if(bounds.getX() + x + bounds.getWidth()/2< handler.getWorld().getEntityManager().getPlayer().getX() - 1){ //right
                direction = 3;
                xMove = speed;
            }
            if(bounds.getX() + x + bounds.getHeight()/2> handler.getWorld().getEntityManager().getPlayer().getX() + 1){ //left
                direction = 4;
                xMove = -speed;
            }
        }
    }

    @Override
    protected boolean checkPlayerZone() {
        enemyX = getCollisionBounds(0,0).getX();
        enemyY = getCollisionBounds(0,0).getY();
        playerX = handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0,0).getX();
        playerY = handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0,0).getY();
        distance = (enemyX - playerX)*(enemyX - playerX) + (enemyY - playerY)*(enemyY - playerY);

        return distance < 500 * 500;
    }

    private void setAnimation(){
        if(direction == 1){ //up
            animation.setOffsetY(0);
        }
        if(direction == 2){ //down
            animation.setOffsetY(128*2);
        }
        if(direction == 3){ //right
            animation.setOffsetY(128);
        }
        if(direction == 4){ //left
            animation.setOffsetY(128 * 3);
        }
    }

    @Override
    public void render(GraphicsContext g) {
        if (xMove != 0 || yMove != 0)
            animation.play();
        else animation.stop();

        enemy = imageView.snapshot(params, null);
        g.drawImage(enemy, (int) (x - handler.getGameCamera().getxOffset()),
                (int) (y - handler.getGameCamera().getyOffset()), width * 3, height * 3);

        //draw health bar
        g.setFill(Color.BLACK);
        g.strokeRoundRect((int) (x - handler.getGameCamera().getxOffset()) + 11,
                (int) (y - handler.getGameCamera().getyOffset()), 144 * 3, 20, 10, 10);
        g.setFill(Color.GREEN);
        g.fillRoundRect((int) (x - handler.getGameCamera().getxOffset()) + 11,
                (int) (y - handler.getGameCamera().getyOffset()), 144 * 3 * ((double) (health) / (double) maxHealth), 19, 10, 10);

    }

    @Override
    protected boolean collisionWithTile(int x, int y){
//        for(int z = 0; z<handler.getWorld().getLayer(); z++) {
//            if(handler.getWorld().getTile(x, y, z).isSolid()){
//                return true;
//            }
//        }
        return false;
    }

    private void checkFire() {
        fireTimer += System.currentTimeMillis() - lastFireTimer;
        lastFireTimer = System.currentTimeMillis();
        if(fireTimer < fireCoolDown){
            return;
        }
        if(checkPlayerZone()){
            handler.getWorld().getEntityManager().addEnergyBall(new EnergyBall(handler, Assets.energyBall1,x, y, direction,
                    handler.getWorld().getEntityManager().getPlayer().getX(),
                    handler.getWorld().getEntityManager().getPlayer().getY()));
//            isAttack = true;
            Sound.playSound(Sound.dragon_fired);

        }else{
            return;
        }


        fireTimer = 0;
    }


    protected boolean checkAttackZone() {
        enemyX = getCollisionBounds(0,0).getX();
        enemyY = getCollisionBounds(0,0).getY();
        playerX = handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0,0).getX();
        playerY = handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0,0).getY();
        distance = (enemyX + 81  - playerX)*(enemyX + 81 -playerX) + (enemyY +81- playerY)*(enemyY + 81- playerY);

        return distance < distanceA;
    }

    private void checkAttacks(){
        attackTimer += System.currentTimeMillis() - lastAttackTimer;
        lastAttackTimer = System.currentTimeMillis();
        if(attackTimer < attackCoolDown){
            return;
        }

        attackTimer = 0;

        if(checkAttackZone()){
            handler.getWorld().getEntityManager().getPlayer().takeDamage(damage);
            Sound.playSound(Sound.hurt);
        }
    }

    @Override
    public void die() {
        Configs.SCORES++;
        handler.getWorld().setEnemyOnBoard(handler.getWorld().getEnemyOnBoard() - 1);
        System.out.println(Configs.SCORES);

        handler.setWin(true);

    }
}
