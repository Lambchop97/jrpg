package graphics;

public class Sprite {

    public static final int sheetWidth = 160;
    private int[] pixels;
    private int xPos, yPos, width, height;

    public Sprite(int[] sheet, int xPos, int yPos, int width, int height){
        this.xPos = xPos * TileSprites.SPRITE_WIDTH;
        this.yPos = yPos * TileSprites.SPRITE_HEIGHT;
        this.width = width;
        this.height = height;
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
}
