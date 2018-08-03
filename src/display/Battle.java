package display;

import graphics.Sprite;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Battle {

    private static boolean initiated = false;

    public static Sprite battleCircle = null;

    public static void render(Screen screen){
        if(!checkInit()){
            return;
        }

        screen.render(battleCircle, 5, 90);
        screen.render(battleCircle, 41, 111);
        screen.render(battleCircle, 77, 132);
        screen.render(battleCircle, 10, 121);
        screen.render(battleCircle, 45, 139);
    }


    public static void init(){
        if(checkInit()) return;
        try{
            BufferedImage image = ImageIO.read(Game.class.getResourceAsStream("/BattleCircle.png"));

            int w = image.getWidth();
            int h = image.getHeight();
            int[] pixels = new int[w * h];
            image.getRGB(0, 0, w, h, pixels, 0, w);

            battleCircle = new Sprite(pixels, 0, 0, 26, 22, 26);

        } catch (IOException e){
            e.printStackTrace();
        }
        initiated = true;
    }

    public static boolean checkInit(){

        return initiated;
    }
}
