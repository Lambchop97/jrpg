package graphics;

import display.Screen;
import util.DisplayInfo;

import java.util.ArrayList;

public class SGPosition {

    private Sprite sprite;
    private ArrayList<Integer> positions;

    public SGPosition(Sprite sprite){
        this.sprite = sprite;
        positions = new ArrayList<>();
    }

    public void addPosition(int pos){
        positions.add(pos);
    }

    public void addPosition(int xPos, int yPos){
        positions.add(yPos * DisplayInfo.GAME_DIM.width + xPos);
    }

    public void removePosition(int pos){
        positions.remove(Integer.valueOf(pos));
    }

    public void removePosition(int xPos, int yPos){
        positions.remove(Integer.valueOf(yPos * DisplayInfo.GAME_DIM.width + xPos));
    }

    void render(Screen screen, boolean fromTop, boolean fromLeft){

        for(int i: positions){
            int xPos;

            if(fromLeft) xPos = i % DisplayInfo.GAME_DIM.width;
            else xPos = DisplayInfo.GAME_DIM.width - i % DisplayInfo.GAME_DIM.width - sprite.getWidth();

            int yPos;
            if(fromTop) yPos = i / DisplayInfo.GAME_DIM.width;
            else yPos = DisplayInfo.GAME_DIM.height - i / DisplayInfo.GAME_DIM.width - sprite.getHeight();
            screen.render(sprite, xPos, yPos);
        }
    }
}
