package display;

import camera.Camera;
import graphics.Sprite;
import graphics.TileSprites;
import input.JKeyboard;
import util.DisplayInfo;
import world.World;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Game extends Canvas implements Runnable{

    private static Game game;

    private static JFrame frame;

    private boolean running = false;

    private int frames = 0, updates = 0;

    private Screen mainScreen;
    private Screen battleScreen;
    private JKeyboard input;
    private Camera camera;
    private World world;

    private float angle = 0;

    private float scale = 0;
    private boolean up = true;

    private AffineTransform transform;


    private static boolean intersected = false;
    private static boolean battling = false;

    public Game(){
        TileSprites.init();
        frame = new JFrame("Dank display");
        frame.setMinimumSize(DisplayInfo.DIM);
        frame.setMaximumSize(DisplayInfo.DIM);
        frame.setPreferredSize(DisplayInfo.DIM);
        frame.add(this);
        input = new JKeyboard(this);
        frame.pack();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        this.requestFocus();

        mainScreen = new Screen(DisplayInfo.GAME_DIM.width, DisplayInfo.GAME_DIM.height);
        battleScreen = new Screen(DisplayInfo.GAME_DIM.width, DisplayInfo.GAME_DIM.height);
        Battle.init();

        camera = new Camera(0 ,0);
        world = new World();

        transform = new AffineTransform();

    }

    public static void main(String[] args){
         game = new Game();
         game.start();
    }


    private void start(){
        running = true;
        run();
    }

    public void run() {
        long fpsTimer = System.currentTimeMillis();
        double nsPerUpdate = 1000000000d / DisplayInfo.updatesPerSecond;
        long then = System.nanoTime();
        double delta = 0;
        while(running){
            long now = System.nanoTime();
            delta += (now - then) / nsPerUpdate;
            then = now;
            boolean canRender = false;
            while(delta > 1){
                update();
                updates++;
                delta -= 1;
                canRender = true;
            }

            if(canRender){
                render();
                frames++;
            }

            if(System.currentTimeMillis() - fpsTimer > 1000){
                System.out.printf("%d frames, %d updates\n", frames, updates);
                frames = 0;
                updates = 0;
                fpsTimer += 1000;
            }
        }
    }

    private void update(){
        camera.update(input);
        world.update(input);
        if(input.isKeyPressed(KeyEvent.VK_G)){
            intersected = true;
        }
        if(input.isKeyPressed(KeyEvent.VK_H)){
            intersected = false;
            battling = false;
            scale = 1;
            angle = 0;
            up = true;
        }
    }

    private void render(){
        BufferStrategy bs = getBufferStrategy();
        if(bs == null){
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        if(intersected){
            setUpTransform();
        }


//  try{
//            BufferedImage image = ImageIO.read(Game.class.getResourceAsStream("/ItemsWorld.png"));
//
//            int w = image.getWidth();
//            int h = image.getHeight();
//            int[] pixels = new int[w * h];
//            image.getRGB(0, 0, w, h, pixels, 0, w);
//
//            Sprite sprite = new Sprite(pixels, 0, 0, 16, 16);
//            screen.render(sprite, 100, 100);
//
//        } catch (IOException e){
//            e.printStackTrace();
//        }

        if(!battling){
            world.render(mainScreen, input);

            g.drawImage(mainScreen.image, 0, 0, getWidth(), getHeight(), null);
        }

        if(intersected || battling){

            Battle.render(battleScreen);
            ((Graphics2D) g).drawImage(battleScreen.image, transform, null);
        }


        mainScreen.clear();
        battleScreen.clear();
        g.dispose();
        bs.show();
    }

    public static Game getInstance(){
        return game;
    }

    public int getUpdates(){
        return updates;
    }

    public Camera getCamera(){
        return  camera;
    }

    public World getWorld(){
        return world;
    }

    private void setUpTransform(){
        if(angle < 2*Math.PI - Math.PI/15){
            angle += Math.PI/15;

        } else {
            battling = true;
            intersected = false;
        }



        if(scale < 4.8){
            scale+=.2;
        }



        transform = new AffineTransform();

//        transform.translate(getWidth() / 2, getHeight() / 2);
//
//        transform.rotate(angle);

        transform.setToScale(scale-.015625, scale-.13425);

//        transform.translate(-battleScreen.getWidth()/2, -battleScreen.getHeight()/2);
    }
}
