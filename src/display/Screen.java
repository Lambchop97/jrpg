package display;

import graphics.Sprite;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Screen {

    private int width, height;

    public BufferedImage image;

    private int[] pixels;

    public Screen(int widht, int height){
        this.width = widht;
        this.height = height;
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    }

    public void clear(){
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



        for(int i = yStart; i < yEnd; i++){
            for(int j = xStart; j < xEnd; j++){
                if(item.getPixels()[(i - yStart + yDiff) * item.getWidth() + (j - xStart + xDiff)] >> 24 == 0){
                    continue;
                }
                pixels[i * width + j] = item.getPixels()[(i - yStart + yDiff) * item.getWidth() + (j - xStart + xDiff)];
            }
        }
    }
}
