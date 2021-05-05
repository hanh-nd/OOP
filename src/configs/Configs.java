package configs;

public class Configs {
    public static final int STAGE_WIDTH = 800, STAGE_HEIGHT = 600;
    public static final int DEFAULT_CREATURE_WIDTH = 64, DEFAULT_CREATURE_HEIGHT = 64;
    public static final int TILE_WIDTH = 64, TILE_HEIGHT = 64;

    //enemies
    public static final int DEFAULT_HEALTH = 100;
    public static final double DEFAULT_SPEED = 1.0;
    public static final int DISTANCE_PLAYER = 200*200;
    public static final int ATTACK_ZONE = 35*35;
    public static final int ENEMY_RESPAWN_TIME = 40000;
    public static final int ENEMY_ATTACK_COOL_DOWN = 1000;
    public static final int ENEMY_RECOVER_COOL_DOWN = 100;

    //player
    public static final double PLAYER_SPEED = 7.0;
    public static final int PLAYER_HEALTH = 1000;
    public static final int PLAYER_BULLET_DAMAGE = 60;
    public static final int PLAYER_SWORD_DAMAGE = 25;
    public static final int PLAYER_SPELL_COOL_DOWN = 3000;
    public static final int PLAYER_SWORD_COOL_DOWN = 500;
    public static boolean IS_MUTE = false;
    public static int SCORES = 0;
}
