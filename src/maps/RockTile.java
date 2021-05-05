package maps;


import gfx.Assets;

public class RockTile extends Tile{

    public RockTile(int id) {
        super(Assets.clear, id);
    }

    @Override
    public boolean isSolid() {
        return true;
    }

}
