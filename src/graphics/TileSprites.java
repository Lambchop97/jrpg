package graphics;

import display.Game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class TileSprites {

    private static TileSet grassWater;
    private static TileSet grassPath;
    private static TileSet grassSnow;

    public static final int SPRITE_WIDTH = 16;
    public static final int SPRITE_HEIGHT = 16;

    private static BufferedImage image;
    private static String path = "/Tiles.png";

    public static void init(){
        try{
            image = ImageIO.read(Game.class.getResourceAsStream(path));

            int w = image.getWidth();
            int h = image.getHeight();
            int[] pixels = new int[w * h];
            image.getRGB(0, 0, w, h, pixels, 0, w);

            String grassWater = "0,0;1,0;2,0;0,1;1,1;2,1;0,2;1,2;2,2;0,3;2,3;1,3;1,4;0,5;2,5;1,6";
            String grassPath = "3,0;4,0;5,0;3,1;4,1;5,1;3,2;4,2;5,2;X;3,3;4,3";
            String grassSnow = "6,0;7,0;8,0;6,1;X;8,1;6,2;7,2;8,2;7,1";

            TileSprites.grassWater = new TileSet(pixels, grassWater, "grassWater");
            TileSprites.grassWater.setDefaultSprite(9);
            TileSprites.grassPath = new TileSet(pixels, grassPath, "grassPath");
            TileSprites.grassPath.setDefaultSprite(4);
            TileSprites.grassSnow = new TileSet(pixels, grassSnow, "grassSnow");
            TileSprites.grassSnow.setDefaultSprite(9);

        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public static TileSet getGrassWater(){
        return grassWater;
    }

    public static TileSet getGrassPath(){
        return grassPath;
    }

    public static TileSet getGrassSnow(){
        return grassSnow;
    }
}
