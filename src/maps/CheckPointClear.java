package maps;

import gfx.Assets;

public class CheckPointClear extends Tile{
    public CheckPointClear(int id) {
        super(Assets.clear, id);
    }

    @Override
    public boolean isCheckPoint() {
        return true;
    }
}
