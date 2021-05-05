package entities.creatures;

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
import states.GameState;


public class Skeleton extends Enemy{

    int count = 9;
    int columns = 9;
    int offsetX = 0;
    int offsetY = 128;
    int width = 64;
    int height = 64;

    SpriteAnimation animation;
    Image enemy;

    public Skeleton(Handler handler, double x, double y, int worldCount){
        super(handler, Assets.skeleton, x, y);

        setWidth(64);
        setWidth(64);

        this.worldCount = worldCount;

        imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setViewport(new Rectangle2D(offsetX,offsetY,width,height));
        animation = new SpriteAnimation(imageView, Duration.millis(1000),count,columns,offsetX,offsetY,width,height);

        bounds.setX(24);
        bounds.setY(38);
        bounds.setWidth(16);
        bounds.setHeight(24);

    }

    @Override
    public void tick() {
       super.tick();
       setAnimation();
    }

    private void setAnimation(){
        if(direction == 1){ //up
            animation.setOffsetY(0);
        }
        if(direction == 2){ //down
            animation.setOffsetY(128);
        }
        if(direction == 3){ //left
            animation.setOffsetY(64);
        }
        if(direction == 4){ //right
            animation.setOffsetY(192);
        }
    }


    @Override
    public void render(GraphicsContext g) {
        if (xMove != 0 || yMove != 0)
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
            GameState.world[worldCount].getEntityManager().addEntity(new Skeleton(handler, homeX, homeY, worldCount));
        });
        enemySpawner.start();
    }
}
