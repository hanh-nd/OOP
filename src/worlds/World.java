package worlds;

import entities.EntityManager;
import entities.creatures.Player;
import game.Handler;
import gfx.Assets;
import items.ItemManager;
import javafx.scene.canvas.GraphicsContext;
import configs.Configs;
import javafx.scene.image.Image;
import maps.Tile;
import utils.Utils;


public class World {

    private int width, height;
    private int spawnXNext;
    private int spawnYNext;
    private int spawnXPre;
    private int spawnYPre;
    private int spawnX;
    private int spawnY;
    private int layer = 3;


    private int countWorld;
    private int[][][] tiles;


    //Entities
    private EntityManager entityManager;
    private Handler handler;
    private int enemyOnBoard = 0;

    //Items
    private ItemManager itemManager;

    //Player
    private Image player;
    public World(Handler handler, String path){
        this.handler = handler;

        loadWorld(path);
        if(handler.isTele()){
            spawnX = spawnXNext;
            spawnY = spawnYNext;
        }else{
            spawnX = spawnXPre;
            spawnY = spawnYPre;
        }

        entityManager = new EntityManager(handler, new Player(handler, spawnX, spawnY, Configs.PLAYER_SWORD_DAMAGE));
        itemManager = new ItemManager(handler);

    }


    public void tick() {
        itemManager.tick();
        entityManager.tick();
    }


        public void render(GraphicsContext g){

        int xStart = (int) (Math.max(0, handler.getGameCamera().getxOffset() / Configs.TILE_WIDTH));
        int xEnd = (int) (Math.min(width, (handler.getGameCamera().getxOffset() + handler.getWidth()) / Configs.TILE_WIDTH + 1));
        int yStart = (int) (Math.max(0, handler.getGameCamera().getyOffset() / Configs.TILE_HEIGHT));
        int yEnd = (int) (Math.min(height, (handler.getGameCamera().getyOffset() + handler.getHeight()) / Configs.TILE_HEIGHT + 1));

        for(int z = 0; z< layer; z++) {
            for (int y = yStart; y < yEnd; y++) {
                for (int x = xStart; x < xEnd; x++) {

                    getTile(x, y, z).render(g, (int) (x * Configs.TILE_WIDTH - handler.getGameCamera().getxOffset()),
                            (int) (y * Configs.TILE_HEIGHT - handler.getGameCamera().getyOffset()));

                }
            }
        }

        itemManager.render(g);
        entityManager.render(g);
    }

    public Tile getTile(int x, int y, int z){
        if(x < 0 || y < 0 || x >= width || y >= height){
            return Tile.rockTile;
        }
        Tile t = Tile.tiles[tiles[x][y][z]];
        if(t == null)
            return Tile.clear;
        return t;
    }


    public void loadWorld(String path){
        String file = Utils.loadFileAsString(path);
        String[] tokens = file.split("\\s+");
        width = Utils.parseInt(tokens[0]);
        height = Utils.parseInt(tokens[1]);
        spawnXNext = Utils.parseInt(tokens[2]);
        spawnYNext = Utils.parseInt(tokens[3]);
        spawnXPre = Utils.parseInt(tokens[4]);
        spawnYPre = Utils.parseInt(tokens[5]);
        countWorld = Utils.parseInt(tokens[6]);
        layer = Utils.parseInt(tokens[7]);

        tiles = new int[width][height][layer];

        for(int z = 0; z < layer; z++) {
            for (int j = 0; j < height; j++) {
                for (int i = 0; i < width; i++) {
                    tiles[i][j][z] = Utils.parseInt(tokens[width*height*z + i + j * width + 8]);
                }
            }
        }


    }
    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getCountWorld() {
        return countWorld;
    }

    public void setCountWorld(int countWorld) {
        this.countWorld = countWorld;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public int getEnemyOnBoard() {
        return enemyOnBoard;
    }

    public void setEnemyOnBoard(int enemyOnBoard) {
        this.enemyOnBoard = enemyOnBoard;
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public ItemManager getItemManager() {
        return itemManager;
    }

    public void setItemManager(ItemManager itemManager) {
        this.itemManager = itemManager;
    }
}
