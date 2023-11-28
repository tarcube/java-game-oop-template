import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import java.util.*;
import javax.sound.sampled.*;
import java.io.*;

class Ball extends GameObject {
    public Ball(int x, int y, int w, int h, ID id, int velX, int velY, int velW, int velH) {
        super(x, y, w, h, id, velX, velY, velW, velH);
    }

    public void tick(int ticks) {
        if (id == ID.Ball) {
            x += velX;
            y += velY;
            w += velW;
            h += velH;
        }
        if (x < 0 || x > Pong.WIDTH-w) {velX *= -1;}
        if (y < 0 || y > Pong.HEIGHT-h) {velY *= -1;}
    }

    public void render(Graphics g) {
        if (id == ID.Ball) {
            g.setColor(Pong.randomColor);
            g.fillRect(x, y, w, h);
        }
    }
}