package entities.creatures.weapons;

import game.Handler;
import javafx.scene.image.Image;
import sounds.Sound;

public class Sword extends Weapon {

    public Sword(Handler handler, Image image, double x, double y, int damage,int direction) {
        super(handler, image, x, y, 10,11, damage, 30, 30, Sound.cut);
        this.direction = direction;

        setSpeed(8);
        bounds.setX(4);
        bounds.setY(4);
        bounds.setHeight(40);
        bounds.setWidth(40);

    }
}