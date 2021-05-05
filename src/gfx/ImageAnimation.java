package gfx;

import javafx.scene.image.Image;

public class ImageAnimation {

    private int speed, index;
    private long lastTime, timer;
    private Image[] frames;

    public ImageAnimation(int speed, Image[] frames) {
        this.speed = speed;
        this.frames = frames;
        index = 0;
        timer = 0;
        lastTime = System.currentTimeMillis();
    }

    public void tick() {
        timer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();

        if (timer > speed) {
            index++;
            timer = 0;
            if (index >= frames.length) {
                index = 0;
            }
        }
    }

    public Image getCurrentFrame() {
        return frames[index];
    }
}
