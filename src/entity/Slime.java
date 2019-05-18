package entity;

import display.Game;
import display.Screen;
import graphics.Animation;
import graphics.Sprite;
import input.JKeyboard;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Slime extends Entity{

    private int[] move = {0,0};
    private int[][] moveFrames;
    private int framesLeft = 0;
    private int frame = 0;
    private boolean left = true;
    private static List<Animation> staticAnimations;


    public Slime(int x, int y){
        super(x,y);

        animations.clear();

        for(Animation a: staticAnimations){
            animations.add(a.getCopy());
        }
        currentAnimation = animations.get(3);
    }

    public void update(JKeyboard input){
        super.update(input);
        if((!currentAnimation.getName().matches("idle_hop_l") || !currentAnimation.getName().matches("idle_hop_r")) && rand.nextInt()%400==40){
            addJumpAnimation();
        }
        if((!currentAnimation.getName().matches("turn_l") || !currentAnimation.getName().matches("turn_r")) && rand.nextInt()%100==40){
            turn();
        }
        if(framesLeft == 0 && !(animationQueue.contains(5) || animationQueue.contains(6)) && rand.nextInt() % 200 == 40){
            makeMove();
        }
        if((currentAnimation.getName().matches("roll_l") || currentAnimation.getName().matches("roll_r")) && framesLeft != 0){
            xPos += moveFrames[frame][0];
            yPos += moveFrames[frame][1];
            frame++;
            framesLeft--;
        }
    }

    private void addJumpAnimation(){
        if(left){
            animationQueue.add(1);
            animationQueue.add(3);
        } else {
            animationQueue.add(2);
            animationQueue.add(4);
        }
    }

    public void makeMove(){
        if(framesLeft != 0) return;
        List<Integer[]> tiles = new ArrayList<>();
        int numturnleft = 0;
        if(checkMovement(xPos / 16 - 1, yPos / 16 - 1)){
            Integer[] move = {-16, -16};
            tiles.add(move);
            numturnleft++;
        }
        if(checkMovement(xPos / 16 - 1, yPos / 16)){
            Integer[] move = {-16, 0};
            tiles.add(move);
            numturnleft++;
        }
        if(checkMovement(xPos / 16 - 1, yPos / 16 + 1)){
            Integer[] move = {-16, 16};
            tiles.add(move);
            numturnleft++;
        }
        if(checkMovement(xPos / 16, yPos / 16 - 1)){
            Integer[] move = {0 , -16};
            tiles.add(move);
            numturnleft++;
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
            int temp= rand.nextInt(tiles.size());
            if(temp < numturnleft){
                if(!left) turn();
            } else {
                if(left) turn();
            }
            move[0] = tiles.get(temp)[0];
            move[1] = tiles.get(temp)[1];
            if(left){
                animationQueue.add(5);
                animationQueue.add(3);
            } else {
                animationQueue.add(6);
                animationQueue.add(4);
            }

            moveFrames = new int[animations.get(5).getNumFrames()][2];

            int[] num = new int[2];
            if(move[0] != 0){
                num[0] = Math.abs(move[0]) / move[0];
            }

            if(move[1] != 0){
                num[1] = Math.abs(move[1]) / move[1];
            }

            int k = 0;
            for(int i = 0; i < 16; i++){
                if(i == 1 || i == 3 || i == 5 || i == 10 || i == 13) k++;
                moveFrames[19+i+k] = num;
            }
            framesLeft += animations.get(5).getNumFrames();
            frame = 0;
        }
    }

    private void turn(){
        if(left){
            animationQueue.add(8);
            animationQueue.add(4);
        } else {
            animationQueue.add(7);
            animationQueue.add(3);
        }
        left = !left;
    }


    public static void init(){
        staticAnimations = loadAnimations("/animations/slime");
    }
}
