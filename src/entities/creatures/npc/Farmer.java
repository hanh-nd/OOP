package entities.creatures.npc;

import dialogue.Dialogue;
import game.Handler;
import gfx.Assets;

public class Farmer extends NPC{

    public Farmer(Handler handler, double x, double y) {
        super(handler, Assets.male_npcs, x, y, Dialogue.Farmer, 132, 0, 196, 0, 66, 132);
    }
}
