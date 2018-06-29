package display;

import camera.Camera;
import graphics.Sprite;
import graphics.TileSprites;
import input.JKeyboard;
import world.World;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Game extends Canvas implements Runnable{

    private static Game game;

    public static final Dimension DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();

    private static JFrame frame;

    private boolean running = false;

    public static final int updatedPerSecond = 60;

    private int frames = 0, updates = 0;

    private Screen screen;
    private JKeyboard input;
    private Camera camera;
    private World world;

    private float angle = 0;

    private float scale = 0;
    private boolean up = true;

    public static boolean intersected = false;

    public Game(){
        TileSprites.init();
        frame = new JFrame("Dank display");
        frame.setMinimumSize(DIMENSION);
        frame.setMaximumSize(DIMENSION);
        frame.setPreferredSize(DIMENSION);
        frame.add(this);
        input = new JKeyboard(this);
        frame.pack();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        this.requestFocus();

        screen = new Screen(DIMENSION.width / 5, DIMENSION.height / 5);
        camera = new Camera(0 ,0);
        world = new World();

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
        double nsPerUpdate = 1000000000d / updatedPerSecond;
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
    }

    private void render(){
        BufferStrategy bs = getBufferStrategy();
        if(bs == null){
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        if(angle < 2*Math.PI - Math.PI/15 && intersected){
            angle += Math.PI/15;
        }

//        if(angle >= 2* Math.PI){
//            angle -= (2 * Math.PI);
//        }


        if(up && intersected){
            scale+=.2;
        } else {
            //scale-=.2;
        }
        if(scale >= 5){
            up = false;
        }
        if(scale <= 0){
            up = true;
        }


        world.render(screen, input);

        try{
            BufferedImage image = ImageIO.read(Game.class.getResourceAsStream("/ItemsWorld.png"));

            int w = image.getWidth();
            int h = image.getHeight();
            int[] pixels = new int[w * h];
            image.getRGB(0, 0, w, h, pixels, 0, w);

            Sprite sprite = new Sprite(pixels, 0, 0, 16, 16);
            screen.render(sprite, 100, 100);

        } catch (IOException e){
            e.printStackTrace();
        }
//        screen.render(TileSprites.getGrassSnow().getTile("tl"), 164, 100);
//        screen.render(TileSprites.getGrassSnow().getTile("t"), 180, 100);
//        screen.render(TileSprites.getGrassSnow().getTile("tr"), 196, 100);
//        screen.render(TileSprites.getGrassSnow().getTile("e2"), 164, 116);
//        screen.render(TileSprites.getGrassSnow().getTile("m"), 180, 116);
//        screen.render(TileSprites.getGrassSnow().getTile("mr"), 196, 116);
//        screen.render(TileSprites.getGrassSnow().getTile("bl"), 164, 132);
//        screen.render(TileSprites.getGrassSnow().getTile("b"), 180, 132);
//        screen.render(TileSprites.getGrassSnow().getTile("br"), 196, 132);
//        screen.render(TileSprites.getGrassSnow().getTile("e1"), 148, 100);
//        screen.render(TileSprites.getGrassSnow().getTile("e2"), 148, 116);
//
//        screen.render(TileSprites.getGrassWater().getTile("blue"), 248, 216);

        BufferedImage i = screen.image.getSubimage(16, 16, 160, 160);
        AffineTransform at = new AffineTransform();

        at.translate(getWidth() / 2, getHeight() / 2);

        at.rotate(angle);

        at.scale(scale, scale);

        at.translate(-i.getWidth()/2, -i.getHeight()/2);

        g.drawImage(screen.image, 0, 0, getWidth(), getHeight(), null);
        ((Graphics2D) g).drawImage(i, at, null);

        screen.clear();
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
}
