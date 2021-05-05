package maps;

import gfx.Assets;

public class CheckPoint extends Tile{

    public CheckPoint(int id) {
        super(Assets.checkpoint, id);
    }

    public boolean isCheckPoint(){
        return true;
    }
}
