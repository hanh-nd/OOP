package entities.creatures;

import entities.creatures.weapons.Spell;
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
import sounds.SoundPlayer;
import states.GameState;

public class Witch extends Enemy{

    int count = 3;
    int columns = 3;
    int offsetX = 0;
    int offsetY = 0;
    int width = 64;
    int height = 64;

    SpriteAnimation animation;
    Image enemy;

    public  long lastSpellTimer, spellCoolDown = 3000, spellTimer = spellCoolDown;

    public Witch(Handler handler, double x, double y, int worldCount){
        super(handler, Assets.witch, x, y);

        this.worldCount = worldCount;
        setWidth(64);
        setHeight(64);

        imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setViewport(new Rectangle2D(offsetX,offsetY,width,height));
        animation = new SpriteAnimation(imageView, Duration.millis(1000),count,columns,offsetX,offsetY,width,height);

        bounds.setX(16);
        bounds.setY(26);
        bounds.setWidth(24);
        bounds.setHeight(34);

    }

    @Override
    public void tick() {
        super.tick();
        setAnimation();
        checkSpells();
    }

    private void setAnimation(){
        if(direction == 1){ //up
            animation.setOffsetY(64);
        }
        if(direction == 2){ //down
            animation.setOffsetY(0);
        }
        if(direction == 3){ //left
            animation.setOffsetY(192);
        }
        if(direction == 4){ //right
            animation.setOffsetY(128);
        }
    }

    private void checkSpells(){

        spellTimer += System.currentTimeMillis() - lastSpellTimer;
        lastSpellTimer = System.currentTimeMillis();
        if(spellTimer < spellCoolDown){
            return;
        }

        if(distance < 300*300){
            handler.getWorld().getEntityManager().addBullet(new Spell(handler, Assets.player_bullet,
                    x+20, y+30, 40, direction));
            SoundPlayer.PlaySound(Sound.player_fired);
        } else {
            return;
        }

        spellTimer = 0;

    }

    @Override
    protected boolean checkPlayerZone() {
        enemyX = getCollisionBounds(0,0).getX();
        enemyY = getCollisionBounds(0,0).getY();
        playerX = handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0,0).getX();
        playerY = handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0,0).getY();
        distance = (enemyX - playerX)*(enemyX - playerX) + (enemyY - playerY)*(enemyY - playerY);

        return 200*200 < distance && distance < 300*300;
    }

    @Override
    public void render(GraphicsContext g) {
        if (distance < 200*200 - 1)
            animation.stop();
        else if (xMove != 0 || yMove != 0)
            animation.play();
        else animation.stop();

        enemy = imageView.snapshot(params, null);
        g.drawImage(enemy, (int) (x - handler.getGameCamera().getxOffset()),
                (int) (y - handler.getGameCamera().getyOffset()));

        //draw health bar
        g.setFill(Color.BLACK);
        g.strokeRoundRect((int) (x - handler.getGameCamera().getxOffset()) + 11,
                (int) (y - handler.getGameCamera().getyOffset()), 40, 5, 10, 10);
        g.setFill(Color.GREEN);
        g.fillRoundRect((int) (x - handler.getGameCamera().getxOffset()) + 11,
                (int) (y - handler.getGameCamera().getyOffset()), 40 * ((double) (health) / (double) maxHealth), 4, 10, 10);

    }

    @Override
    public void die(){
        super.die();
        Thread enemySpawner = new Thread(() -> {
            try {
                Thread.sleep(Configs.ENEMY_RESPAWN_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            GameState.world[worldCount].getEntityManager().addEntity(new Witch(handler, homeX, homeY, worldCount));
        });
        enemySpawner.start();
    }
}
