package entities.creatures.npc;

import dialogue.Dialogue;
import game.Handler;
import gfx.Assets;

public class BlueHat extends NPC{

    public BlueHat(Handler handler, double x, double y) {
        super(handler, Assets.children_npcs, x, y, Dialogue.BlueHat, 264, 0, 196, 0, 66, 132);
    }
}
