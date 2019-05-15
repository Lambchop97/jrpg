package entity;

import display.Game;
import display.Screen;
import graphics.Animation;
import graphics.Sprite;
import input.JKeyboard;
import util.GraphicsUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Entity {

    int xPos, yPos;
    List<Integer> animationQueue;
    List<Animation> animations;
    Animation currentAnimation;
    Random rand;

    Entity(int x, int y){
        xPos = x;
        yPos = y;
        rand = new Random();

        animationQueue = new ArrayList<>();
        animations = new ArrayList<>();

        loadAnimations("/animations/slime");
//        try{
//            BufferedImage image = ImageIO.read(Game.class.getResourceAsStream(path));
//
//            int w = image.getWidth();
//            int h = image.getHeight();
//            sheet = new int[w * h];
//            image.getRGB(0, 0, w, h, sheet, 0, w);
//
//            Animation tempAni = Animation.createAnimation(sheet, "0,0", "idle", 300);
//            animations.add(tempAni);
//
//            currentAnimation = tempAni;
//        } catch (IOException e){
//            e.printStackTrace();
//        }
    }

    public void update(JKeyboard input){
        currentAnimation.update();
        if(animationQueue.size() > 0 && currentAnimation.isFinished()){
            currentAnimation = animations.get(animationQueue.get(0));
            currentAnimation.updateTime();
            animationQueue.remove(0);
        }
    }

    public void render(Screen screen){
        currentAnimation.render(screen, xPos, yPos);
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

    boolean checkMovement(int x, int y){
        System.out.println("Checking (" + x + ", " + y + ")");
        return !Game.getInstance().getWorld().isTileType(x, y, 9);
    }

    public int getxPos(){
        return xPos;
    }

    public int getyPos(){
        return yPos;
    }


    void loadAnimations(String path){
        // Clear existing animations (done for subclasses as they need to erase the default animations)
        animations.clear();

        // Get the folder of animations from path
        URL folderurl = Game.class.getResource(path + "/idle_hop_r.ani");

        File folder = new File(folderurl.getPath());

        // Load an animation for each file in the folder
        for(File f: folder.getParentFile().listFiles()){
            try{
                // Set up data
                BufferedReader reader = new BufferedReader(new FileReader(f));

                String name = "";
                String sheet = "";
                String animation = "";
                int[] frames = {};

                List<Integer> framel = new ArrayList<>();

                String line;
                int i = 1;
                while((line = reader.readLine()) != null){
                    if(i == 1){
                        // Line 1 is always name
                        name = line;
                    } else if (i == 2){
                        // Line 2 is always path
                        sheet = line;
                    } else {
                        // The rest of the lines are frame data

                        // Split frame data up
                       String[] sub = line.split(",");

                       // Data is incompatible if there aren't 3 pieces
                       if(sub.length != 3) continue;

                       // Add frame location
                       if(animation.matches("")) {
                           //TODO: change string cat to StringBuilders
                           animation = animation + sub[0] + "," + sub[1];
                       } else {
                           animation = animation + ">" + sub[0] + "," + sub[1];
                       }

                       // Add frame length
                       framel.add(Integer.parseInt(sub[2]));
                    }
                    i++;
                }

                // Transfer frame data over
                frames = new int[framel.size()];
                for(i = 0; i < framel.size(); i++){
                    frames[i] = framel.get(i);
                }

                // Get sprite sheet
                int[] sSheet = GraphicsUtil.loadSpriteSheet(sheet);

                // Create and add animation
                animations.add(Animation.createAnimation(sSheet, animation, name, frames));
                System.out.println("loaded " + name);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        currentAnimation = animations.get(0);
    }
}
