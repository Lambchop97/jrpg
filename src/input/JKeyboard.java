package input;

import display.Game;

import java.util.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class JKeyboard implements KeyListener{

    private volatile List<Integer> currentPressedKeys;

    public JKeyboard(Game game){
            game.addKeyListener(this);
            currentPressedKeys = new ArrayList<>();
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        if(!isKeyPressed(e.getKeyCode())){
            currentPressedKeys.add(e.getKeyCode());
        }
    }

    public void keyReleased(KeyEvent e) {
        if(isKeyPressed(e.getKeyCode())){
            currentPressedKeys.remove(new Integer(e.getKeyCode()));
        }
    }

    public synchronized boolean isKeyPressed(int keyCode){
        return currentPressedKeys.contains(keyCode);
    }
}
