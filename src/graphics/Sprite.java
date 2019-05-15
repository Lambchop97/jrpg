package graphics;

import java.util.ArrayList;

public class Sprite {

    private int sheetWidth = 160;
    private int[] pixels;
    private int xPos, yPos, width, height;


    public Sprite(int[] sheet, int xPos, int yPos, int width, int height){
        this.xPos = xPos * TileSprites.SPRITE_WIDTH;
        this.yPos = yPos * TileSprites.SPRITE_HEIGHT;
        this.width = width;
        this.height = height;
        loadPixels(sheet);
    }

    public Sprite(int[] sheet, int xPos, int yPos, int width, int height, int sheetWidth){
        this.xPos = xPos * TileSprites.SPRITE_WIDTH;
        this.yPos = yPos * TileSprites.SPRITE_HEIGHT;
        this.width = width;
        this.height = height;
        this.sheetWidth = sheetWidth;
        loadPixels(sheet);
    }

    public Sprite(int[] sprite, int width , int height){
        pixels = sprite;
        xPos = 0;
        yPos = 0;
        this.width = width;
        this.height = height;
    }

    private void loadPixels(int[] sheet){
        pixels = new int[width * height];
        for(int i = yPos; i < yPos + height; i++){
            for(int k = xPos; k < xPos + width; k++){
                if(i * sheetWidth + k == 40960){
                    continue;
                }
                pixels[(i - yPos) * width + (k - xPos)] = sheet[i * sheetWidth + k];
            }
        }
    }

    public int[] getPixels(){
        return pixels;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    @Override
    public boolean equals(Object obj) {
        //obj isn't a sprite
        if(!(obj instanceof Sprite)) return false;

        //cast obj to Sprite, check for same height and width
        Sprite sprite = (Sprite) obj;
        if(height != sprite.height || width != sprite.width) return false;

        //compare each pixel
        for(int i = 0; i < height; i++){
            for(int k = 0; k < width; k++){
                if(pixels[i * width + k] != sprite.pixels[i * width + k]) return false;
            }
        }

        //sprites are the same
        return true;
    }
}
