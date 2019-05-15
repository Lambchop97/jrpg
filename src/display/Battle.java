package display;

import graphics.SGPosition;
import graphics.Sprite;
import graphics.SpriteGrouping;
import util.DisplayInfo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

class Battle {

    private static boolean initiated = false;

    private static SpriteGrouping playerParty;
    private static SpriteGrouping enemyParty;
    private static SpriteGrouping battleInterfaceSG;

    static void render(Screen screen){
        if(!checkInit()){
            return;
        }

        playerParty.render(screen);
        enemyParty.render(screen);
        battleInterfaceSG.render(screen);
    }


    static void init(){
        if(checkInit()) return; //Do not run if already initialized.


        try{
            BufferedImage image = ImageIO.read(Game.class.getResourceAsStream("/images/BattleCircle.png"));

            int w = image.getWidth();
            int h = image.getHeight();
            int[] pixels = new int[w * h];
            image.getRGB(0, 0, w, h, pixels, 0, w);

            Sprite battleCircle = new Sprite(pixels, 0, 0, 26, 22, 26);

            playerParty = new SpriteGrouping(0,0);
            playerParty.setPosFromTop(false);

            enemyParty = new SpriteGrouping(0,0);
            enemyParty.setPosFromLeft(false);

            battleInterfaceSG = new SpriteGrouping(0,0);
            battleInterfaceSG.setPosFromLeft(false);
            battleInterfaceSG.setPosFromTop(false);

            SGPosition battleCircleSG = new SGPosition(battleCircle);
            battleCircleSG.addPosition(10, 100);
            battleCircleSG.addPosition(90, 60);
            battleCircleSG.addPosition(170, 20);
            battleCircleSG.addPosition(40, 50);
            battleCircleSG.addPosition(100, 10);

            playerParty.addSGPos(battleCircleSG);

            enemyParty.addSGPos(battleCircleSG);


            image = ImageIO.read(Game.class.getResourceAsStream("/images/BattleInterface.png"));

            w = image.getWidth();
            h = image.getHeight();
            pixels = new int[w * h];
            image.getRGB(0, 0, w, h, pixels, 0, w);

            Sprite battleInterface = new Sprite(pixels, 0, 0, w, h, w);

            SGPosition battleInterfaceSGP = new SGPosition(battleInterface);
            battleInterfaceSGP.addPosition(3,3);

            battleInterfaceSG.addSGPos(battleInterfaceSGP);

        } catch (IOException e){
            e.printStackTrace();
        }
        initiated = true;
    }

    private static boolean checkInit(){

        return initiated;
    }
}
