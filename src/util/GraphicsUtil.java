package util;

import display.Game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GraphicsUtil {

    //TODO: add sprite info here

    public static int alphaBlend(int c1, int c2) {
        int a1 = (c1) >>> 24;
        //int a2 = (c2 & 0xff000000) >>> 24; // Do not need for traditional alpha blending

        int r1 = (c1 & 0x00ff0000) >> 16;
        int r2 = (c2 & 0x00ff0000) >> 16;

        int g1 = (c1 & 0x0000ff00) >> 8;
        int g2 = (c2 & 0x0000ff00) >> 8;

        int b1 = (c1 & 0x000000ff);
        int b2 = (c2 & 0x000000ff);

        float src_alpha = ((float)a1) / 255.0f;

        int red   = (int) ((r1 * src_alpha) + (r2 * (1.0f - src_alpha)));
        int green = (int) ((g1 * src_alpha) + (g2 * (1.0f - src_alpha)));
        int blue  = (int) ((b1 * src_alpha) + (b2 * (1.0f - src_alpha)));

        return (0xff << 24) | (red << 16) | (green << 8) | blue;
    }

    public static int[] loadSpriteSheet(String path){
        try {
            BufferedImage image = ImageIO.read(Game.class.getResourceAsStream(path));

            int w = image.getWidth();
            int h = image.getHeight();
            int[] sheet = new int[w * h];
            image.getRGB(0, 0, w, h, sheet, 0, w);

            return sheet;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
