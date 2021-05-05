package gfx;

import javafx.scene.image.Image;
import javafx.scene.text.Font;

public class Assets {

    public static Font font28;
    public static Font font20;

    private static final int width = 32, height = 32;
    public static Image icon, dirt, grass, stone, skeleton, player, witch;
    public static Image checkpoint, clear, easy, medium, hard, difficultState, creditsState;
    public static Image[] tree = new Image[13];
    public static Image[][] map1 = new Image[30][30];
    public static Image[][] map2 = new Image[30][30];
    public static Image[][] map3 = new Image[30][30];
    public static Image[][] map4 = new Image[30][30];
    public static Image background, gameover, pause, victory;
    public static Image start, exit, mute_unmute, restart, main_menu, back, credits, resume;


    public static Image[] slime_up, slime_down, slime_left, slime_right;
    public static Image talk;

    //boss
    public static Image bossblue;
    public static Image firebreath;
    public static Image energyBall;
    public static Image energyBall1;
    public static Image energyBall2;
    public static Image energyBall3;
    public static Image energyBall4;

    //item
    public static Image lotionMana, lotionHP, lotionDamage;

    //inventory
    public static Image inventoryScreen;

    public static Image player_bullet;

    //chuong
    public static Image player_ball1,player_ball2,player_ball3,player_ball4;
    public static Image player_sword1,player_sword2,player_sword3,player_sword4;

    //npc
    public static Image male_npcs, female_npcs, children_npcs;

    public static void init(){
        //boss
        bossblue = ImageLoader.loadImage("res/textures/bossblue.png");
        firebreath = ImageLoader.loadImage("res/textures/boss/fireball3.png");
        energyBall = ImageLoader.loadImage("res/textures/boss/energyBall.png");
        SpriteSheet e = new SpriteSheet(energyBall);
        energyBall1 = e.crop(0,0,182,206);
        energyBall2 = e.crop(182 * 1,0,182,206);
        energyBall3 = e.crop(182 * 2,0,182,206);
        energyBall4 = e.crop(182 * 3,0,182,206);

        inventoryScreen = ImageLoader.loadImage("res/textures/inventoryScreen.png");

        font28 = FontLoader.loadFont("res/fonts/slkscr.ttf",28);
        font20 = FontLoader.loadFont("res/fonts/slkscr.ttf",20);

        icon = ImageLoader.loadImage("res/textures/icon.png");

        talk = ImageLoader.loadImage("res/textures/talk.png", 42, 42);

        SpriteSheet crystal_clear = new SpriteSheet(ImageLoader.loadImage("res/textures/BladeY.png"));

        easy = ImageLoader.loadImage("res/textures/Easy.png");
        medium = ImageLoader.loadImage("res/textures/Medium.png");
        hard = ImageLoader.loadImage("res/textures/Hard.png");
        difficultState = ImageLoader.loadImage("res/textures/difficulty_background.png");
        creditsState = ImageLoader.loadImage("res/textures/credit.png");

        SpriteSheet teleport = new SpriteSheet(ImageLoader.loadImage("res/textures/Teleport.png"));
        SpriteSheet map_1 = new SpriteSheet(ImageLoader.loadImage("res/textures/Map1.png"));
        SpriteSheet map_2 = new SpriteSheet(ImageLoader.loadImage("res/textures/Map2.png"));
        SpriteSheet map_3 = new SpriteSheet(ImageLoader.loadImage("res/textures/Map3.png"));
        SpriteSheet map_4 = new SpriteSheet(ImageLoader.loadImage("res/textures/Map4.png"));

        for(int i = 0; i<20; i++){
            for(int j = 0; j<25; j++){
                map1[j][i] = map_1.crop(width*j, height*i, width, height);
                map2[j][i] = map_2.crop(width*j, height*i, width, height);
                map3[j][i] = map_3.crop(width*j, height*i, width, height);
                map4[j][i] = map_4.crop(width*j, height*i, width, height);
            }
        }
        clear = crystal_clear.crop(0, 0, width, height);
        checkpoint = teleport.crop(40, 180, 430, 430);

        skeleton = ImageLoader.loadImage("res/textures/skeleton.png");
        player = ImageLoader.loadImage("res/textures/player.png");
        witch = ImageLoader.loadImage("res/textures/witch.png");

        //bullet
        player_bullet = ImageLoader.loadImage("res/textures/player_bullet.png");
        //chuong
        SpriteSheet energyBall = new SpriteSheet(ImageLoader.loadImage("res/textures/energy_ball.png"));
        player_ball1 = energyBall.crop(0, height,width , height);
        player_ball2 = energyBall.crop(0, 0,width, height);
        player_ball3 = energyBall.crop(width , 0,width , height );
        player_ball4 = energyBall.crop(width , height ,width  , height);

        SpriteSheet sheet3 = new SpriteSheet(ImageLoader.loadImage("res/textures/player_sword.png"));
        player_sword1 = sheet3.crop(width/3, 0,width/3 , height+13);
        player_sword2 = sheet3.crop(0, 0,width/3 , height+13);
        player_sword3 = sheet3.crop(0, height+24,width+13 , height/3);
        player_sword4 = sheet3.crop(0, height+13,width+13 , height/3);

        start = ImageLoader.loadImage("res/textures/start_button.png");
        exit = ImageLoader.loadImage("res/textures/exit_button.png");
        mute_unmute = ImageLoader.loadImage("res/textures/mute_unmute.png");
        restart = ImageLoader.loadImage("res/textures/restart_button.png");
        main_menu = ImageLoader.loadImage("res/textures/menu_button.png");
        back = ImageLoader.loadImage("res/textures/back_button.png");
        credits = ImageLoader.loadImage("res/textures/credits_button.png");
        resume = ImageLoader.loadImage("res/textures/resume_button.png");

        background = ImageLoader.loadImage("res/textures/background.png");
        gameover = ImageLoader.loadImage("res/textures/gameover.jpg");
        pause = ImageLoader.loadImage("res/textures/pause.jpg");
        victory = ImageLoader.loadImage("res/textures/victory.jpg");

        slime_up = new Image[4];
        for(int i = 0; i < 4; i++)
            slime_up[i] = ImageLoader.loadImage("res/textures/slime/SlimeUp_" + i + ".png");

        slime_down = new Image[4];
        for(int i = 0; i < 4; i++)
            slime_down[i] = ImageLoader.loadImage("res/textures/slime/SlimeDown_" + i + ".png");

        slime_left = new Image[4];
        for(int i = 0; i < 4; i++)
            slime_left[i] = ImageLoader.loadImage("res/textures/slime/SlimeLeft_" + i + ".png");
        slime_right = new Image[4];
        for(int i = 0; i < 4; i++)
            slime_right[i] = ImageLoader.loadImage("res/textures/slime/SlimeRight_" + i + ".png");

        //item
        SpriteSheet sheet_item = new SpriteSheet(ImageLoader.loadImage("res/textures/items4x.png"));
        lotionMana = sheet_item.crop(64 * 3, 0, 64, 64);
        lotionHP = sheet_item.crop(64 * 4, 0, 64, 64);
        lotionDamage = sheet_item.crop(64 * 5, 0, 64, 64);

        //NPCs
        male_npcs = ImageLoader.loadImage("res/textures/male_npcs.png", 528, 528);
        female_npcs = ImageLoader.loadImage("res/textures/female_npcs.png", 528, 528);
        children_npcs = ImageLoader.loadImage("res/textures/children_npcs.png", 528, 528);

    }
}
