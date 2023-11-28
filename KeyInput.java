import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import java.util.*;
import javax.sound.sampled.*;
import java.io.*;

class KeyInput extends KeyAdapter {
    private Handler handler;

    public KeyInput(Handler handler) {
        this.handler = handler;
    }

    public static HashSet<Integer> keysPressed = new HashSet<>();

    public static void clickSFX() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(Pong.class.getResource("colon_3.wav"));
            Clip sound = AudioSystem.getClip();
            sound.open(audioInputStream);
            sound.start();
        } catch (Exception err) {
            System.out.println(err);
        }
    }

    @Override

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        keysPressed.add(key);
        if ((char)key == ' ') {
            Pong.red = (int) (Math.random()*255);
            Pong.green = (int) (Math.random()*255);
            Pong.blue = (int) (Math.random()*255);
            Pong.randomColor = new Color(Pong.red, Pong.green, Pong.blue);
            Pong.randomColorBy2 = new Color(Pong.red/2, Pong.green/2, Pong.blue/2);
            clickSFX();
        }
    }

    @Override

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        keysPressed.remove(key);
    }
}