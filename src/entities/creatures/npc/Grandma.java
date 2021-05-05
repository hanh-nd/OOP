package entities.creatures.npc;

import dialogue.Dialogue;
import game.Handler;
import gfx.Assets;

public class Grandma extends NPC{

    public Grandma(Handler handler, double x, double y) {
        super(handler, Assets.female_npcs, x, y, Dialogue.Grandma, 132, 0, 196, 0, 66, 132);
    }
}
