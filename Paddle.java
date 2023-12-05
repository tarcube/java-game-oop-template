import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import java.util.*;
import javax.sound.sampled.*;
import java.io.*;

class Paddle extends GameObject {
    public Paddle(int x, int y, int w, int h, ID id, int velX, int velY, int velW, int velH) {
        super(x, y, w, h, id, velX, velY, velW, velH);
    }

    public void tick(int ticks) {
        if (id == ID.Paddle1) {
            if (KeyInput.keysPressed.contains(87)) {
                y -= velY;
            }
            if (KeyInput.keysPressed.contains(83)) {
                y += velY;
            }
        }
    }

    public void render(Graphics g) {
        if (id == ID.Paddle1) {
            g.setColor(Pong.randomColor);
            g.fillRect(x, y, w, h);
        }
    }
}