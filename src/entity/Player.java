package entity;

import display.Game;
import graphics.Sprite;
import input.JKeyboard;
import world.World;

import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{

    private int lastMove;
    private boolean diagJumped = false;

    private boolean moved;

    public Player(int x, int y){
        super(x, y);
        lastMove = 1;
        moved = false;
        try{
            BufferedImage image = ImageIO.read(Game.class.getResourceAsStream("/Characters.png"));

            int w = image.getWidth();
            int h = image.getHeight();
            int[] pixels = new int[w * h];
            image.getRGB(0, 0, w, h, pixels, 0, w);

            sprite = new Sprite(pixels, 0, 0, 16, 16);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void update(JKeyboard input) {
        moved = false;
        if(input.isKeyPressed(KeyEvent.VK_DOWN) && !(lastMove % KeyEvent.VK_DOWN == 0)){
            if(checkMovement(xPos / 16, (yPos + 16) /16)){
                move(0, 16);
            }
            lastMove *= KeyEvent.VK_DOWN;
        }
        if(input.isKeyPressed(KeyEvent.VK_UP) && !(lastMove % KeyEvent.VK_UP == 0)){
            if(checkMovement(xPos / 16, (yPos - 16) /16)) {
                move(0,-16);
            }
            lastMove *= KeyEvent.VK_UP;
        }
        if(input.isKeyPressed(KeyEvent.VK_RIGHT) && !(lastMove % KeyEvent.VK_RIGHT == 0)){
            if(checkMovement((xPos + 16) / 16, yPos /16)) {
                move(16,0);
            }
            lastMove *= KeyEvent.VK_RIGHT;
        }
        if(input.isKeyPressed(KeyEvent.VK_LEFT) && !(lastMove % KeyEvent.VK_LEFT == 0)){
            if(checkMovement((xPos - 1) / 16, yPos /16)) {
                move(-16, 0);
            }
            lastMove *= KeyEvent.VK_LEFT;
        }

        // ===== CHECK FOR DIAGONAL JUMP =====

        if(!diagJumped && lastMove % KeyEvent.VK_LEFT == 0 && lastMove % KeyEvent.VK_DOWN == 0){
            if(!checkMovement((xPos - 16) / 16, yPos / 16) && !checkMovement(xPos/ 16, (yPos + 16) / 16)){
                if(checkMovement((xPos - 16) / 16, (yPos + 16) / 16)){
                    move(-16, 16);
                }
            }
        }

        if(!diagJumped && lastMove % KeyEvent.VK_LEFT == 0 && lastMove % KeyEvent.VK_UP == 0){
            if(!checkMovement((xPos - 16) / 16, yPos / 16) && !checkMovement(xPos/ 16, (yPos - 16) / 16)){
                if(checkMovement((xPos - 16) / 16, (yPos - 16) / 16)){
                    move(-16, -16);
                }
            }
        }

        if(!diagJumped && lastMove % KeyEvent.VK_RIGHT == 0 && lastMove % KeyEvent.VK_DOWN == 0){
            if(!checkMovement((xPos + 16) / 16, yPos / 16) && !checkMovement(xPos/ 16, (yPos + 16) / 16)){
                if(checkMovement((xPos + 16) / 16, (yPos + 16) / 16)){
                   move(16,16);
                }
            }
        }

        if(!diagJumped && lastMove % KeyEvent.VK_RIGHT == 0 && lastMove % KeyEvent.VK_UP == 0){
            if(!checkMovement((xPos + 16) / 16, yPos / 16) && !checkMovement(xPos/ 16, (yPos - 16) / 16)){
                if(checkMovement((xPos + 16) / 16, (yPos - 16) / 16)){
                   move(16, -16);
                }
            }
        }

        // ===== RESET LAST MOVE IF KEY RELEASED =====

        if(!input.isKeyPressed(KeyEvent.VK_DOWN) && (lastMove % KeyEvent.VK_DOWN == 0)){
            lastMove /= KeyEvent.VK_DOWN;
        }
        if(!input.isKeyPressed(KeyEvent.VK_UP) && (lastMove % KeyEvent.VK_UP == 0)){
            lastMove /= KeyEvent.VK_UP;
        }
        if(!input.isKeyPressed(KeyEvent.VK_LEFT) && (lastMove % KeyEvent.VK_LEFT == 0)){
            lastMove /= KeyEvent.VK_LEFT;
        }
        if(!input.isKeyPressed(KeyEvent.VK_RIGHT) && (lastMove % KeyEvent.VK_RIGHT == 0)){
            lastMove /= KeyEvent.VK_RIGHT;
        }

        if(lastMove == 1 && diagJumped){
            diagJumped = false;
        }
    }

    private void move(int dx, int dy){
        xPos += dx;
        if (xPos > 16 * World.SIZE) xPos = 16 * World.SIZE - 16;
        if (xPos + 16 - Game.DIMENSION.width / 5 >= Game.getInstance().getCamera().getxOffset()) {
            int dist = Math.max(80,70 + (xPos + 16 - (Game.getInstance().getCamera().getxOffset() + Game.DIMENSION.width / 5)));
            //Game.getInstance().getCamera().move(80, 0);
            Game.getInstance().getCamera().setTarget(dist, 0, 30);
        }
        yPos += dy;
        if(yPos > 16 * World.SIZE) yPos = 16 * World.SIZE - 16;
        if(yPos + 16 - Game.DIMENSION.height / 5 >= Game.getInstance().getCamera().getyOffset()){
            int dist = Math.max(80,70 + (yPos + 16 - (Game.getInstance().getCamera().getyOffset() + Game.DIMENSION.height / 5)));
            //Game.getInstance().getCamera().move(0, 80);
            Game.getInstance().getCamera().setTarget(0, dist, 30);
        }
        diagJumped = true;
        moved = true;
    }

    public boolean getMoved(){
        return moved;
    }
}
