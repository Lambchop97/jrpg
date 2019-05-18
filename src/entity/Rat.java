package entity;

import graphics.Animation;
import input.JKeyboard;

import java.util.ArrayList;
import java.util.List;

public class Rat extends Entity {

    private static List<Animation> staticAnimations;

    private int[] move = {0,0};
    private int[][] moveFrames;
    private int frame = 0;
    private int framesLeft = 0;
    private int spot = 0;

    public Rat(int x, int y){
        super(x, y);

        animations.clear();

        for(Animation a: staticAnimations){
            animations.add(a.getCopy());
        }
        currentAnimation = animations.get(0);
    }

    public void update(JKeyboard input){
        super.update(input);

        if(rand.nextInt(120) == 45){
            addScurryAnimation();
            //makeMove();
        }
        if(animationQueue.size()==-1){
            int nextSpot = rand.nextInt(3);
            if(spot != nextSpot){
                int k = rand.nextInt(7);
                if(spot == 0){
                    if(nextSpot == 1){
                        animationQueue.add(4);
                        for(int i = 0; i < k; i++){
                            animationQueue.add(1);
                        }
                    } else {
                        animationQueue.add(5);
                        for(int i = 0; i < k; i++){
                            animationQueue.add(2);
                        }
                    }
                } else if(spot == 1){
                    if(nextSpot == 0){
                        animationQueue.add(6);
                        for(int i = 0; i < k; i++){
                            animationQueue.add(0);
                        }
                    } else {
                        animationQueue.add(7);
                        for(int i = 0; i < k; i++){
                            animationQueue.add(2);
                        }
                    }
                } else {
                    if(nextSpot == 0){
                        animationQueue.add(8);
                        for(int i = 0; i < k; i++){
                            animationQueue.add(0);
                        }
                    } else {
                        animationQueue.add(9);
                        for(int i = 0; i < k; i++){
                            animationQueue.add(1);
                        }
                    }
                }
                spot = nextSpot;
            }
        }

        if((currentAnimation.getName().matches("move_up0")) && framesLeft != 0){
            xPos += moveFrames[frame][0];
            yPos += moveFrames[frame][1];
            frame++;
            framesLeft--;
        }
    }

    public void makeMove(){
        if(framesLeft != 0) return;
        animationQueue.add(10);
        animationQueue.add(0);

        moveFrames = new int[animations.get(10).getNumFrames()][2];

        int[] num = {0,-1};

        int k = 0;
        for(int i = 0; i < 16; i++){
            if(i == 1 || i == 4 || i == 7 || i == 10 || i == 13) k++;
            moveFrames[10+i+k] = num;
        }
        framesLeft = animations.get(5).getNumFrames();
        frame = 0;

    }

    private void addScurryAnimation(){
        if(spot == 0) {
            animationQueue.add(11);
        } else if(spot == 1){
            animationQueue.add(6);
            animationQueue.add(11);
        } else {
            animationQueue.add(8);
            animationQueue.add(11);


        }
        animationQueue.add(0);

        int returnSpot = rand.nextInt(2) + 1;
        if(returnSpot == 1){
            animationQueue.add(4);
            animationQueue.add(1);
        } else {
            animationQueue.add(5);
            animationQueue.add(2);
        }
        spot = returnSpot;
    }

    public static void init(){
        staticAnimations = loadAnimations("/animations/rat");
    }
}
