package maps;

import gfx.Assets;

public class ArchitectureMap3 extends Tile{
    public ArchitectureMap3(int id) {
        super(Assets.map3[(id-1001)%25][(id-1001-((id-1001)%25))/25], id);
    }

    @Override
    public boolean isSolid() {
        return false;
    }
}
