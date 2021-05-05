package entities.creatures;

import entities.Entity;
import entities.creatures.weapons.Bullet;
import entities.creatures.weapons.Sword;
import game.Handler;

import gfx.Assets;
import gfx.SpriteAnimation;
import inventory.Inventory;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import configs.Configs;
import sounds.Sound;
import sounds.SoundPlayer;
import states.GameOverState;
import states.GameState;
import states.State;


public class Player extends Creature{

    protected int count = 9;
    protected int columns = 9;
    protected int offsetX = 0;
    protected int offsetY = 640;
    protected int width = 64;
    protected int height = 64;
    protected boolean tele;
    protected SpriteAnimation animation;
    protected Image player;


    //Attack Timer
    protected long lastAttackTimer, attackCoolDown = Configs.PLAYER_SWORD_COOL_DOWN, attackTimer = attackCoolDown;
    public static long lastSpellTimer, spellCoolDown = Configs.PLAYER_SPELL_COOL_DOWN, spellTimer = spellCoolDown;
    public static long lastCutTimer, cutCoolDown = Configs.PLAYER_SWORD_COOL_DOWN, cutTimer = cutCoolDown;

    protected MediaPlayer footstep;

    public Rectangle abound;
    //inventory
    private Inventory inventory;

    public Player(Handler handler, double x, double y, int damage){
        super(handler, Assets.player, x, y, Configs.DEFAULT_CREATURE_WIDTH, Configs.DEFAULT_CREATURE_HEIGHT, damage);

        setSpeed(Configs.PLAYER_SPEED);
        imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setViewport(new Rectangle2D(offsetX,offsetY,width,height));
        animation = new SpriteAnimation(imageView, Duration.millis(1000),count,columns,offsetX,offsetY,width,height);

        bounds.setX(24);
        bounds.setY(38);
        bounds.setWidth(16);
        bounds.setHeight(24);

        //attack bounds
        abound =new Rectangle();
        abound.setX(16);
        abound.setY(16);
        abound.setWidth(32);
        abound.setHeight(48);

        footstep = Sound.footstep;
        handler.getSoundManager().addSound(footstep);

        inventory = new Inventory(handler);

        maxHealth = Configs.PLAYER_HEALTH;
        health = 1000;

        speed = 10;
    }

    @Override
    public void tick() {
        //Movement
        getInput();
        move();
        checkPointMove();
        //stepSound();
        handler.getGameCamera().centerOnEntity(this);

        //Attack
        checkAttacks();
        checkSpells();
        checkCut();

        //inventory
        inventory.tick();
    }

    //CHECKPOINT
    //check if Player reach CheckPoint
    private boolean checkPointTile(int x, int y){
        for(int z = 0; z<handler.getWorld().getLayer(); z++) {
            if(handler.getWorld().getTile(x,y, z).isCheckPoint()){
                return true;
            }
        }
        return false;
        //return handler.getWorld().getTile(x,y).isCheckPoint();
    }

//    private boolean isStrongEnough() {
//        if (handler.getWorld().getCountWorld() == 1) {
//            return true;
//        }
//
//        if (handler.getWorld().getCountWorld() == 2) {
//            return GameState.scores > 15;
//        }
//
//        if (handler.getWorld().getCountWorld() == 3) {
//            return GameState.scores > 30;
//        }
//
//        if (handler.getWorld().getCountWorld() == 4) {
//            return true;
//        }
//
//        return false;
//    }

    //set world
    private void setNewWorld(){
         {
            if (handler.getWorld().getWidth() * 64 * 2 / 3 <= x + xMove) {
                GameState.world[0] = GameState.world[handler.getWorld().getCountWorld() + 1];
                GameState.entityManager = GameState.world[handler.getWorld().getCountWorld() + 1].getEntityManager();
                tele = true;
            } else {
                GameState.world[0] = GameState.world[handler.getWorld().getCountWorld() - 1];
                GameState.entityManager = GameState.world[handler.getWorld().getCountWorld() - 1].getEntityManager();
                tele = false;
            }
            GameState.playerCurrentHealth = handler.getWorld().getEntityManager().getPlayer().getHealth();
            GameState.playerCurrentSpeed = handler.getWorld().getEntityManager().getPlayer().getSpeed();

            handler.setWorld(GameState.world[0], tele);

            GameState.entityManager.getPlayer().setHealth(GameState.playerCurrentHealth);
            GameState.entityManager.getPlayer().setSpeed(GameState.playerCurrentSpeed);
        }

    }

    //get Move
    private void checkPointMove(){
        if(xMove > 0){
            int tx = (int) (x + xMove + bounds.getX() + bounds.getWidth()) / Configs.TILE_WIDTH;

            if(checkPointTile(tx, ((int) (y + bounds.getY()) / Configs.TILE_HEIGHT)) &&
                    checkPointTile(tx, (int) (y + bounds.getY() + bounds.getHeight()) / Configs.TILE_HEIGHT)){
                setNewWorld();
            }
        } else if(xMove < 0){
            int tx = (int) (x + xMove + bounds.getX()) / Configs.TILE_WIDTH;

            if(checkPointTile(tx, ((int) (y + bounds.getY()) / Configs.TILE_HEIGHT)) &&
                    checkPointTile(tx, (int) (y + bounds.getY() + bounds.getHeight()) / Configs.TILE_HEIGHT)){
                setNewWorld();
            }
        } else if(yMove < 0){
            int ty = (int) (y + yMove + bounds.getY()) / Configs.TILE_HEIGHT;

            if(checkPointTile((int) (x + bounds.getX()) / Configs.TILE_WIDTH, ty) &&
                    checkPointTile((int) (x + bounds.getX() + bounds.getWidth()) / Configs.TILE_WIDTH, ty)){
                setNewWorld();
            }
        } else {
            int ty = (int) (y + yMove + bounds.getY() + bounds.getHeight()) / Configs.TILE_HEIGHT;

            if(checkPointTile((int) (x + bounds.getX()) / Configs.TILE_WIDTH, ty) &&
                    checkPointTile((int) (x + bounds.getX() + bounds.getWidth()) / Configs.TILE_WIDTH, ty)){
                setNewWorld();
            }
        }
    }


    private void checkSpells(){
        if(inventory.isActive()){
            return;
        }
        spellTimer += System.currentTimeMillis() - lastSpellTimer;
        lastSpellTimer = System.currentTimeMillis();
        if(spellTimer < spellCoolDown){
            return;
        }

        if(handler.getKeyManager().isDestroyThemAll()){
            for(Entity e : handler.getWorld().getEntityManager().getEntities()){
                if (e instanceof Enemy){
                    e.takeDamage(1000);
                }
            }
        }

        if(handler.getKeyManager().isSpell()){
            if(direction ==1) {
                handler.getWorld().getEntityManager().addBullet(new Bullet(handler, Assets.player_ball1,
                        x+20 , y+30 , Configs.PLAYER_BULLET_DAMAGE, direction));}
            if(direction ==2) {
                handler.getWorld().getEntityManager().addBullet(new Bullet(handler, Assets.player_ball2,
                        x+20, y+35 , Configs.PLAYER_BULLET_DAMAGE, direction));}
            if(direction ==3) {
                handler.getWorld().getEntityManager().addBullet(new Bullet(handler, Assets.player_ball3,
                        x+22, y+30 , Configs.PLAYER_BULLET_DAMAGE, direction));}
            if(direction ==4) {
                handler.getWorld().getEntityManager().addBullet(new Bullet(handler, Assets.player_ball4,
                        x+35, y+30 , Configs.PLAYER_BULLET_DAMAGE, direction));}

            SoundPlayer.PlaySound(Sound.player_fired);
        } else {
            return;
        }

        spellTimer = 0;

    }

    private void checkCut() {
        cutTimer += System.currentTimeMillis() - lastCutTimer;
        lastCutTimer = System.currentTimeMillis();
        if(cutTimer < cutCoolDown){
            return;

        }
        if(handler.getKeyManager().isSpace()){
            if(direction ==1) {
                handler.getWorld().getEntityManager().addSword(new Sword(handler, Assets.player_sword1,
                        x+33 , y+25 , Configs.PLAYER_SWORD_DAMAGE, direction));}
            if(direction ==2) {
                handler.getWorld().getEntityManager().addSword(new Sword(handler, Assets.player_sword2,
                        x+34, y+25 , Configs.PLAYER_SWORD_DAMAGE, direction));}
            if(direction ==3) {
                handler.getWorld().getEntityManager().addSword(new Sword(handler, Assets.player_sword3,
                        x+15, y+37 , Configs.PLAYER_SWORD_DAMAGE, direction));}
            if(direction ==4) {
                handler.getWorld().getEntityManager().addSword(new Sword(handler, Assets.player_sword4,
                        x+15, y+37 , Configs.PLAYER_SWORD_DAMAGE, direction));}

            SoundPlayer.PlaySound(Sound.player_sword);
        } else {
            return;
        }

        cutTimer = 0;
    }

    private void checkAttacks(){
        if(inventory.isActive())
            return;

        attackTimer += System.currentTimeMillis() - lastAttackTimer;
        lastAttackTimer = System.currentTimeMillis();
        if(attackTimer < attackCoolDown){
            return;
        }

        Rectangle cb = getCollisionBounds(0, 0);
        Rectangle ar = new Rectangle();

        int arSize = 30;
        ar.setWidth(arSize);
        ar.setHeight(arSize);

        if(handler.getMouseManager().isLeftPressed() && direction == 1){
            ar.setX(cb.getX() + cb.getWidth()/2 - arSize/2);
            ar.setY(cb.getY() - arSize);


        } else if(handler.getMouseManager().isLeftPressed() && direction == 2){
            ar.setX(cb.getX() + cb.getWidth()/2 - arSize/2);
            ar.setY(cb.getY() + cb.getHeight());


        } else if(handler.getMouseManager().isLeftPressed() && direction == 3){
            ar.setX(cb.getX() - arSize);
            ar.setY(cb.getY() + cb.getHeight()/2 - arSize/2);


        } else if(handler.getMouseManager().isLeftPressed() && direction == 4){
            ar.setX(cb.getX() + cb.getWidth());
            ar.setY(cb.getY() + cb.getHeight()/2 - arSize/2);

        } else {

            return;
        }

        handler.getGraphicsContext().fillRect(ar.getX(), ar.getY(), arSize, arSize);

        attackTimer = 0;

        for(Entity e : handler.getWorld().getEntityManager().getEntities()){
            if(e.equals(this))
                continue;
            if(e.getCollisionBounds(0, 0).intersects(ar.getBoundsInLocal())){
                e.takeDamage(damage);
                if(!Configs.IS_MUTE){
                    if(Sound.punch.getStatus() == MediaPlayer.Status.PLAYING)
                        Sound.punch.stop();
                    //Sound.punch.play();
                }
            }
        }
    }

    public void getInput(){
        xMove = 0;
        yMove = 0;

        if(inventory.isActive())
            return;

        if(handler.getKeyManager().isMoveUp()){
            direction = 1;
            yMove = -speed;
            animation.setOffsetY(512);
        }

        if(handler.getKeyManager().isMoveDown()){
            direction = 2;
            yMove = speed;
            animation.setOffsetY(640);
        }

        if(handler.getKeyManager().isMoveLeft()){
            direction = 3;
            xMove = -speed;
            animation.setOffsetY(576);
        }

        if(handler.getKeyManager().isMoveRight()){
            direction = 4;
            xMove = speed;
            animation.setOffsetY(704);
        }
    }

    public void stepSound(){
        if(active){
            if(handler.getKeyManager().isMoveUp() || handler.getKeyManager().isMoveDown()
                    || handler.getKeyManager().isMoveLeft() || handler.getKeyManager().isMoveRight()){
                footstep.setCycleCount(MediaPlayer.INDEFINITE);
                //footstep.play();
            } else {
                //footstep.stop();
            }
        }
    }

    @Override
    public void die() {
       // System.out.println("Chết dồi :D");

        //Set active
        active = false;

        //Reset Scores
        Configs.SCORES = 0;

        //Sound off
        handler.getSoundManager().soundOff();

        //New game
        State.setState(new GameOverState(handler));
    }

    @Override
    public void render(GraphicsContext g) {
        //draw player
        if(xMove != 0 || yMove != 0)
            animation.play();
        else animation.stop();

        player = imageView.snapshot(params, null);
        g.drawImage(player, (int)(x - handler.getGameCamera().getxOffset()),
                (int) (y - handler.getGameCamera().getyOffset()));


    }

    public void postRender(GraphicsContext g){
        inventory.render(g);
    }

    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public void setHealth(int health){
        this.health = health;
    }

    public Rectangle getAttackBounds(double xOffset, double yOffset){
        return new Rectangle((int) (x + abound.getX() + xOffset),
                (int) (y + abound.getY() + yOffset), abound.getWidth(), abound.getHeight());
    }
}
