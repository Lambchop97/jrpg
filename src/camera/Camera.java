package camera;

import display.Game;
import input.JKeyboard;
import world.World;

import java.awt.event.KeyEvent;

public class Camera {

    private int xOffset;
    private int yOffset;

    private int targetX;
    private int targetY;

    private final int VELOCITY = 4;

    private int currentVelX;
    private int currentVelY;

    private int updates;

    public Camera(int x, int y){
        xOffset = x;
        yOffset = y;
        targetX = x;
        targetY = y;
        currentVelX = 0;
        currentVelY = 0;
        updates = 0;
    }

    public void move(int deltaX, int deltaY){
        xOffset += deltaX;
        yOffset += deltaY;
        if(xOffset < 0) xOffset = 0;
        if(yOffset < 0) yOffset = 0;
        if(xOffset + Game.DIMENSION.width / 5 > World.SIZE * 16) xOffset = World.SIZE * 16 - Game.DIMENSION.width / 5;
        if(yOffset + Game.DIMENSION.height / 5 > World.SIZE * 16) yOffset = World.SIZE * 16 - Game.DIMENSION.height / 5;
    }

    public void setTarget(int x, int y, int updates){
        targetX = xOffset + x;
        targetY = yOffset + y;

        currentVelX = (targetX - xOffset) / updates;
        currentVelY = (targetY - yOffset) / updates;
        this.updates = updates;
    }

    public int getxOffset(){
        return xOffset;
    }

    public int getyOffset(){
        return yOffset;
    }

    public void update(JKeyboard input){
        if(input.isKeyPressed(KeyEvent.VK_W)){
            move(0, -4);
        }
        if(input.isKeyPressed(KeyEvent.VK_A)){
            move(-4, 0);
        }
        if(input.isKeyPressed(KeyEvent.VK_S)){
            move(0, 4);
        }
        if(input.isKeyPressed(KeyEvent.VK_D)){
            move(4, 0);
        }
        if(updates != 0){
            xOffset += currentVelX;
            yOffset += currentVelY;
            if(xOffset < 0) xOffset = 0;
            if(yOffset < 0) yOffset = 0;
            if(xOffset + Game.DIMENSION.width / 5 > World.SIZE * 16) xOffset = World.SIZE * 16 - Game.DIMENSION.width / 5;
            if(yOffset + Game.DIMENSION.height / 5 > World.SIZE * 16) yOffset = World.SIZE * 16 - Game.DIMENSION.height / 5;
            updates--;
        }
    }
}
