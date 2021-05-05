package maps;

import gfx.Assets;

public class ArchitectureMap2 extends Tile{
    public ArchitectureMap2(int id) {
        super(Assets.map2[(id-501)%25][(id-501-((id-501)%25))/25], id);
    }

    @Override
    public boolean isSolid() {
        return false;
    }
}
