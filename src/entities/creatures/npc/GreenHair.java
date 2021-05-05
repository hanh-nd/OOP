package entities.creatures.npc;

import dialogue.Dialogue;
import game.Handler;
import gfx.Assets;

public class GreenHair extends NPC{

    public GreenHair(Handler handler, double x, double y) {
        super(handler, Assets.children_npcs, x, y, Dialogue.GreenHair, 0, 0, 196, 0, 66, 132);
    }
}
