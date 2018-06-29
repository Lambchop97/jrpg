package entity;

import display.Game;
import display.Screen;
import graphics.Sprite;
import input.JKeyboard;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Entity {

    protected int xPos, yPos;
    protected Sprite sprite;
    private Random rand;

    public Entity(int x, int y){
        xPos = x;
        yPos = y;
        rand = new Random();
        try{
            BufferedImage image = ImageIO.read(Game.class.getResourceAsStream("/Enemy.png"));

            int w = image.getWidth();
            int h = image.getHeight();
            int[] pixels = new int[w * h];
            image.getRGB(0, 0, w, h, pixels, 0, w);

            sprite = new Sprite(pixels, 0, 0, 16, 16);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void update(JKeyboard input){

    }

    public void render(Screen screen){
        screen.render(sprite, xPos, yPos);
    }

    public void makeMove(){
        List<Integer[]> tiles = new ArrayList<>();
        if(checkMovement(xPos / 16 - 1, yPos / 16 - 1)){
            Integer[] move = {-16, -16};
            tiles.add(move);
        }
        if(checkMovement(xPos / 16 - 1, yPos / 16)){
            Integer[] move = {-16, 0};
            tiles.add(move);
        }
        if(checkMovement(xPos / 16 - 1, yPos / 16 + 1)){
            Integer[] move = {-16, 16};
            tiles.add(move);
        }
        if(checkMovement(xPos / 16, yPos / 16 - 1)){
            Integer[] move = {0 , -16};
            tiles.add(move);
        }
        if(checkMovement(xPos / 16, yPos / 16 + 1)){
            Integer[] move = {0, 16};
            tiles.add(move);
        }
        if(checkMovement(xPos / 16 + 1, yPos / 16 - 1)){
            Integer[] move = {16, -16};
            tiles.add(move);
        }
        if(checkMovement(xPos / 16 + 1, yPos / 16)){
            Integer[] move = {16, 0};
            tiles.add(move);
        }
        if(checkMovement(xPos / 16 + 1, yPos / 16 + 1)){
            Integer[] move = {16, 16};
            tiles.add(move);
        }

        if(tiles.size() != 0){
            int move = rand.nextInt(tiles.size());
            System.out.println(tiles.size() + ", " + move);
            xPos += tiles.get(move)[0];
            yPos += tiles.get(move)[1];
        }
    }

    public boolean checkMovement(int x, int y){
        System.out.println("Checking (" + x + ", " + y + ")");
        return !Game.getInstance().getWorld().isTileType(x, y, 9);
    }

    public int getxPos(){
        return xPos;
    }

    public int getyPos(){
        return yPos;
    }
}
