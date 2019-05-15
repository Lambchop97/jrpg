package display;

import graphics.Sprite;
import util.GraphicsUtil;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Screen {

    private int width, height;

    BufferedImage image;

    private int[] pixels;

    Screen(int width, int height){
        this.width = width;
        this.height = height;
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    }

    void clear(){
        for(int i = 0; i < pixels.length; i++){
            pixels[i] = 0xff123456;
        }
    }

    public void render(Sprite item, int x, int y){
        x -= Game.getInstance().getCamera().getxOffset();
        y -= Game.getInstance().getCamera().getyOffset();
        int xStart = x;
        int xEnd = x + item.getWidth();
        if(xStart < 0) xStart = 0;
        if(xEnd > this.width) xEnd = this.width;
        int xDiff = (xStart - x);

        int yStart = y;
        int yEnd = y + item.getHeight();
        if(yStart < 0) yStart = 0;
        if(yEnd > this.height) yEnd = this.height;
        int yDiff = (yStart - y);

        int[] iPix = item.getPixels();

        for(int i = yStart; i < yEnd; i++){
            for(int j = xStart; j < xEnd; j++){
                // If the pixel in the sprite is completely transparent skip it
                if(iPix[(i - yStart + yDiff) * item.getWidth() + (j - xStart + xDiff)] >>> 24 == 0){
                    continue;
                }

                // If the pixel in the sprite has no transparency just replace the color
                // Otherwise apply an alpha blend
                if((iPix[(i - yStart + yDiff) * item.getWidth() + (j - xStart + xDiff)] >>> 24) == 0xff){
                    pixels[i * width + j] = item.getPixels()[(i - yStart + yDiff) * item.getWidth() + (j - xStart + xDiff)];
                } else {
                    int colorApply = iPix[(i - yStart + yDiff) * item.getWidth() + (j - xStart + xDiff)];
                    int colorBase = pixels[i * width + j];
                    pixels[i * width + j] = GraphicsUtil.alphaBlend(colorApply, colorBase);
                }
            }
        }
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }
}
