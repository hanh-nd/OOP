package entities.creatures;

import game.Handler;
import items.Item;
import javafx.scene.image.Image;


import javafx.scene.media.MediaPlayer;
import configs.Configs;
import sounds.Sound;
import states.GameState;


public abstract class Enemy extends Creature{

    //Music

    protected int worldCount;
    //Attack Timer
    private long lastAttackTimer, attackCoolDown = Configs.ENEMY_ATTACK_COOL_DOWN, attackTimer = attackCoolDown;
    private long lastHealthRecover, recoverCoolDown = Configs.ENEMY_RECOVER_COOL_DOWN, recoverTimer = recoverCoolDown;

    //Zone
    double enemyX, enemyY, playerX, playerY, distance;
    double homeX, homeY;
    public Enemy(Handler handler,  Image image, double x, double y){
        super(handler, image, x, y, Configs.DEFAULT_CREATURE_WIDTH, Configs.DEFAULT_CREATURE_HEIGHT, 25);
        homeX = x;
        homeY = y;
    }

    @Override
    public void tick() {
        //Movements
        chasePlayerMove();
        backHomeMove();
        move();

        //Recover
        selfRecovery();

        //Attack
        checkAttacks();
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
            if(!Configs.IS_MUTE){
                if(Sound.hurt.getStatus() == MediaPlayer.Status.PLAYING)
                    Sound.hurt.stop();
                Sound.hurt.play();
            }
        }
    }

    protected boolean checkPlayerZone() {
        enemyX = getCollisionBounds(0,0).getX();
        enemyY = getCollisionBounds(0,0).getY();
        playerX = handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0,0).getX();
        playerY = handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0,0).getY();
        distance = (enemyX - playerX)*(enemyX - playerX) + (enemyY - playerY)*(enemyY - playerY);

        return distance < Configs.DISTANCE_PLAYER;
    }

    protected boolean checkAttackZone() {
        enemyX = getCollisionBounds(0,0).getX();
        enemyY = getCollisionBounds(0,0).getY();
        playerX = handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0,0).getX();
        playerY = handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0,0).getY();
        distance = (enemyX - playerX)*(enemyX - playerX) + (enemyY - playerY)*(enemyY - playerY);

        return distance < Configs.ATTACK_ZONE;
    }

    public void backHomeMove(){
        if(!checkPlayerZone()){
            if(y > homeY + 1){ //up
                direction = 1;
                yMove = -speed;
            }

            if(y < homeY - 1){ //down
                direction = 2;
                yMove = speed;
            }

            if(x < homeX - 1){ //right
                direction = 4;
                xMove = speed;
            }

            if(x > homeX + 1){ //left
                direction = 3;
                xMove = -speed;
            }

        }
    }

    public void chasePlayerMove(){
        xMove = 0;
        yMove = 0;

        if(checkPlayerZone()){
            if(y > handler.getWorld().getEntityManager().getPlayer().getY() + 1){ //up
                direction = 1;
                yMove = -speed;
            }
            if(y < handler.getWorld().getEntityManager().getPlayer().getY() - 1){ //down
                direction = 2;
                yMove = speed;
            }
            if(x < handler.getWorld().getEntityManager().getPlayer().getX() - 1){ //right
                direction = 4;
                xMove = speed;
            }
            if(x > handler.getWorld().getEntityManager().getPlayer().getX() + 1){ //left
                direction = 3;
                xMove = -speed;
            }
        }
    }

    public void selfRecovery(){
        recoverTimer += System.currentTimeMillis() - lastHealthRecover;
        lastHealthRecover = System.currentTimeMillis();
        if(recoverTimer < recoverCoolDown){
            return;
        }

        recoverTimer = 0;

        if(x == homeX + 1 || x == homeX - 1 || y == homeY + 1 || y == homeY - 1){
            if(health < maxHealth){
                health ++;
            }
        }
    }

    @Override
    public void die() {
        GameState.scores++;
        handler.getWorld().setEnemyOnBoard(handler.getWorld().getEnemyOnBoard() - 1);
        System.out.println(GameState.scores);
       // System.out.println("xin lũiiii mà :(");

        int rand = (int) (Math.random() * 5);
        if(rand == 1){
            handler.getWorld().getItemManager().addItem(Item.lotionHP.createNew((int) x, (int) y));
        }else if(rand == 2){
            handler.getWorld().getItemManager().addItem(Item.lotionMana.createNew((int) x, (int) y));
        }else if(rand == 3) {
            handler.getWorld().getItemManager().addItem(Item.lotionAttack.createNew((int) x, (int) y));
        }

        active = false;
    }

}
