package maps;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import configs.Configs;


public class Tile {

    public static Tile[] tiles = new Tile[10000];
//    public static Tile grassTile = new GrassTile(0);
//    public static Tile dirtTile = new DirtTile(1);
    public static Tile rockTile ;
    public static Tile checkPoint , checkPointClear;
    public static Tile clear;
    public static Tile[] Architecture1 = new Tile[501];
    public static Tile[] Architecture2 = new Tile[501];
    public static Tile[] Architecture3 = new Tile[501];
    public static Tile[] Architecture4 = new Tile[501];


    protected Image image;
    protected final int id;

    public Tile(Image image, int id){
        this.image = image;
        this.id = id;
        tiles[id] = this;
    }

    public static void init(){
        clear = new Clear(0);
        checkPoint = new CheckPoint(9999);
        checkPoint = new CheckPointClear(9997);
        rockTile = new RockTile(9998);
        for(int i=1; i<501; i++ )
            Architecture1[i] = new ArchitectureMap1(i);
        for(int i=501; i<1001; i++ )
            Architecture2[i-501] = new ArchitectureMap2(i);
        for(int i=1001; i<1501; i++ )
            Architecture3[i-1001] = new ArchitectureMap3(i);
        for(int i=1501; i<2001; i++ )
            Architecture4[i-1501] = new ArchitectureMap4(i);

    }

    public void tick(){

    }

    public void render(GraphicsContext g, int x, int y){
        g.drawImage(image, x, y, Configs.TILE_WIDTH, Configs.TILE_HEIGHT);
    }

    public boolean isSolid(){
        return false;
    }

    public boolean isCheckPoint(){
        return false;
    }

    public int getId(){
        return id;
    }

}
