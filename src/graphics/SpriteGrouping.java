package graphics;

import display.Screen;

import java.util.ArrayList;

public class SpriteGrouping {

    private int xPos;
    private int yPos;

    private boolean posFromTop = true;
    private boolean posFromLeft = true;

    private ArrayList<SGPosition> sprites;


    public SpriteGrouping(int xPos, int yPos){
        this.xPos = xPos;
        this.yPos = yPos;

        sprites = new ArrayList<>();
    }

    public SpriteGrouping(ArrayList<SGPosition> sprites, int xPos, int yPos){
        this.xPos = xPos;
        this.yPos = yPos;

        this.sprites = sprites;
    }

    public void setPosFromTop(boolean fromTop){
        posFromTop = fromTop;
    }

    public void setPosFromLeft(boolean fromLeft){
        posFromLeft = fromLeft;
    }

    public void addSGPos(SGPosition sprite){
        sprites.add(sprite);
    }

    public void render(Screen screen){
        for(SGPosition sprite: sprites){
            sprite.render(screen, posFromTop, posFromLeft);
        }
    }
}
