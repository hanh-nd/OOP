package entities.creatures.npc;

import dialogue.Dialogue;
import game.Handler;
import gfx.Assets;

public class Jack extends NPC{

    public Jack(Handler handler, double x, double y) {
        super(handler, Assets.male_npcs, x, y, Dialogue.Jack, 0, 0, 196, 0, 66, 132);
    }
}
