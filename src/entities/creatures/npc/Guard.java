package entities.creatures.npc;

import dialogue.Dialogue;
import game.Handler;
import gfx.Assets;

public class Guard extends NPC{

    String text;
    public Guard(Handler handler, double x, double y) {
        super(handler, Assets.male_npcs, x, y, Dialogue.Guard, 264, 0, 196, 0, 66, 132);
    }

    public Guard(Handler handler, String text, double x, double y) {
        super(handler, Assets.male_npcs, x, y, text, 264, 0, 196, 0, 66, 132);
        this.text = text;
    }

    @Override
    public void tick() {
        playDialogue();
    }
}
