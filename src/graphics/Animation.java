package graphics;

import display.Screen;

public class Animation {

    private Sprite[] sprites;
    private int frame;
    private int[] frameLengths;
    private long frameTimer;
    private String name;
    private boolean finished;
    private int numFrames;

    private Animation(Sprite[] sprites, String name){
        this.sprites = sprites;
        this.name = name;
        frame = 0;
        frameLengths = new int[sprites.length];
        for(int i = 0; i < frameLengths.length; i++){
            frameLengths[i] = 500;
        }
        frameTimer = System.currentTimeMillis();
        finished = false;
        numFrames = 0;
    }

    public void update(){
        finished = false;
        if(System.currentTimeMillis() - frameTimer >= frameLengths[frame]){
            frameTimer += frameLengths[frame];
            frame++;
            if(frame >= sprites.length){
                frame = 0;
                finished = true;
            }
        }

    }

    public void updateTime(){
        frameTimer = System.currentTimeMillis();
    }

    public void render(Screen screen, int x, int y){
        screen.render(sprites[frame], x, y);
    }

    public String getName(){
        return name;
    }

    public boolean isFinished(){
        return finished;
    }

    public void reset(){
        frame = 0;
    }

    public void resetTimer(){
        frameTimer = System.currentTimeMillis();
    }

    public static Animation createAnimation(int[] sheet, String ani, String name){
        return createAnimation(sheet, ani, name, 500);
    }

    public static Animation createAnimation(int[] sheet, String ani, String name, int frameLength){
        String[] frames = ani.split(">");
        Sprite[] sprites = new Sprite[frames.length];
        int i = 0;
        for(String frame: frames){
            String[] coords = frame.split(",");
            int xCoord = Integer.parseInt(coords[0]);
            int yCoord = Integer.parseInt(coords[1]);
            Sprite sprite = new Sprite(sheet, xCoord, yCoord, 16, 16);
            sprites[i++] = sprite;
        }
        Animation anim = new Animation(sprites, name);
        int totalMS = 0;
        for(i = 0; i < anim.frameLengths.length; i++){
            anim.frameLengths[i] = frameLength;
            totalMS += frameLength;
        }
        anim.numFrames = (int) (totalMS / 16.67f) + 1;
        return anim;
    }

    public static Animation createAnimation(int[] sheet, String ani, String name, int[] frameLengths){
        String[] frames = ani.split(">");
        if(frameLengths.length != frames.length){
            if(frameLengths.length == 0) return createAnimation(sheet, ani, name, 500);
            return createAnimation(sheet, ani, name, frameLengths[0]);
        }
        Sprite[] sprites = new Sprite[frames.length];
        int i = 0;
        for(String frame: frames){
            String[] coords = frame.split(",");
            int xCoord = Integer.parseInt(coords[0]);
            int yCoord = Integer.parseInt(coords[1]);
            Sprite sprite = new Sprite(sheet, xCoord, yCoord, 16, 16);
            sprites[i++] = sprite;
        }
        Animation anim = new Animation(sprites, name);
        anim.frameLengths = frameLengths;
        int totalMS = 0;
        for(int ms: anim.frameLengths){
            totalMS+= ms;
        }
        anim.numFrames = (int) (totalMS / 16.67f) + 1;
        return anim;
    }

    public boolean equals(Object o){
        if(o instanceof Animation){
            return this.name.matches(((Animation) o).name);
        }
        return false;
    }

    public Sprite getFrame(){
        return sprites[frame];
    }

    public int getNumFrames(){
        return numFrames;
    }
}
