package entities.creatures.npc;

import entities.creatures.Creature;
import game.Handler;
import gfx.Assets;
import gfx.SpriteAnimation;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import configs.Configs;

import java.util.Random;

public abstract class NPC extends Creature {

    int count = 3;
    int columns = 3;
    int offsetX;
    int offsetY;
    int width = 44;
    int height = 66;
    boolean isSpoken = false, flag = false, isRandomMove = false;
    SpriteAnimation animation;
    Image npc;
    int offsetUp, offsetDown, offsetLeft, offsetRight;
    protected double npcX, npcY, playerX, playerY, distance;
    String dialogue;


    public NPC(Handler handler, Image image, double x, double y, String dialogue, int offsetX, int offsetY,
               int offsetUp, int offsetDown, int offsetLeft, int offsetRight) {
        super(handler, image, x, y, 64, 96, 0);

        setHealth(9999999);
        this.offsetUp = offsetUp;
        this.offsetDown = offsetDown;
        this.offsetLeft = offsetLeft;
        this.offsetRight = offsetRight;

        this.dialogue = dialogue;
        this.offsetX = offsetX;
        this.offsetY = offsetY;

        setSpeed(Configs.PLAYER_SPEED);
        imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setViewport(new Rectangle2D(offsetX,offsetY,width,height));
        animation = new SpriteAnimation(imageView, Duration.millis(1000),count,columns,offsetX,offsetY,width,height);

        bounds.setX(16);
        bounds.setY(36);
        bounds.setWidth(16);
        bounds.setHeight(28);
    }

    @Override
    public void tick() {
        setMovement();
        setAnimation();
        move();
        playDialogue();
    }

    public void setAnimation(){
        if(direction == 1){ //up
            animation.setOffsetY(offsetUp);
        }
        if(direction == 2){ //down
            animation.setOffsetY(offsetDown);
        }
        if(direction == 3){ //left
            animation.setOffsetY(offsetLeft);
        }
        if(direction == 4){ //right
            animation.setOffsetY(offsetRight);
        }
    }

    public void setMovement(){
        if (checkPlayerZone()){
            xMove = 0;
            yMove = 0;
            if(y > handler.getWorld().getEntityManager().getPlayer().getY() + 25){ //up
                direction = 1;
            }
            if(y < handler.getWorld().getEntityManager().getPlayer().getY() - 25){ //down
                direction = 2;
            }
            if(x < handler.getWorld().getEntityManager().getPlayer().getX() - 25){ //right
                direction = 4;
            }
            if(x > handler.getWorld().getEntityManager().getPlayer().getX() + 25){ //left
                direction = 3;
            }
        }

        else if (!isRandomMove) {
            Random random = new Random();
            int rand = random.nextInt(4);
            Thread randomMove = new Thread(() -> {
                try {
                    isRandomMove = true;
                    if (rand == 0) {
                        yMove = 0;
                        xMove = 3;
                        direction = 4;
                    } else if (rand == 1) {
                        yMove = 0;
                        xMove = -3;
                        direction = 3;
                    } else if (rand == 2) {
                        xMove = 0;
                        yMove = 3;
                        direction = 2;
                    } else if (rand == 3) {
                        xMove = 0;
                        yMove = -3;
                        direction = 1;
                    }
                    Thread.sleep(5000);
                    isRandomMove = false;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            randomMove.start();
        }
    }

    public void playDialogue(){
        if((checkPlayerZone() && handler.getKeyManager().isEnter())){
            if (!flag) {
                isSpoken = true;
                flag = true;
                Thread waitThread = new Thread(() -> {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    isSpoken = false;
                });
                waitThread.start();
            }
        } else {
            flag = false;
        }
    }

    protected boolean checkPlayerZone() {
        npcX = getCollisionBounds(0,0).getX();
        npcY = getCollisionBounds(0,0).getY();
        playerX = handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0,0).getX();
        playerY = handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0,0).getY();
        distance = (npcX - playerX)*(npcX - playerX) + (npcY - playerY)*(npcY - playerY);
        return distance < 45*45;
    }



    @Override
    public void render(GraphicsContext g) {
        animation.play();
        if (isSpoken){
            g.setFill(Color.WHITE);
            g.fillRoundRect((int) (x - handler.getGameCamera().getxOffset() - 10),
                    (int) (y - handler.getGameCamera().getyOffset()) - 40, 100, 50, 10, 10);
            g.setFill(Color.BLACK);
            g.setFont(Font.font(13));
            g.fillText(dialogue, (int) (x - handler.getGameCamera().getxOffset()) - 5,
                    (int) (y - handler.getGameCamera().getyOffset()) - 26);
        }
        npc = imageView.snapshot(params, null);
        g.drawImage(npc, (int) (x - handler.getGameCamera().getxOffset()),
                (int) (y - handler.getGameCamera().getyOffset()));

        if (checkPlayerZone()) {
            g.setFill(Color.BLACK);
            g.strokeOval(740, 520,40,40);
            g.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
            g.setFill(Color.GREEN);
            g.fillOval(740, 520, 40, 40);
            g.drawImage(Assets.talk, 739, 518);

            g.setFont(Font.font("Verdana", FontWeight.BOLD, 7));
            g.setFill(Color.web("#e2fbff"));
            g.fillRoundRect(748, 555, 30,10, 10,10);
            g.setFill(Color.BLACK);
            g.fillText("Enter", 751,562);
        }
    }

    @Override
    public void die() {
    }
}
