package entities.creatures.npc;

import dialogue.Dialogue;
import game.Handler;
import gfx.Assets;

public class FemaleGuard extends NPC{

    public FemaleGuard(Handler handler, double x, double y) {
        super(handler, Assets.female_npcs, x, y, Dialogue.FemaleGuard, 264, 0, 196, 0, 66, 132);
    }


    @Override
    public void tick() {
        playDialogue();
    }
}
