import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import java.util.*;
import javax.sound.sampled.*;
import java.io.*;

abstract class GameObject {
    protected int x, y;
    protected int w, h;
    protected ID id;
    protected int velX, velY;
    protected int velW, velH;

    public GameObject(int x, int y, int w, int h, ID id, int velX, int velY, int velW, int velH) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.id = id;
        this.velX = velX;
        this.velY = velY;
        this.velW = velW;
        this.velH = velH;
    }

    public abstract void tick(int ticks);
    public abstract void render(Graphics g);

    public void setX(int x) {this.x = x;}
    public void setY(int y) {this.y = y;}
    public int getX() {return x;}
    public int getY() {return y;}
    public void setW(int w) {this.w = w;}
    public void setH(int h) {this.h = h;}
    public int getW() {return w;}
    public int getH() {return h;}
    public void setId(ID id) {this.id = id;}
    public ID getId() {return id;}
    public void setVelX(int velX) {this.velX = velX;}
    public void setVelY(int velY) {this.velY = velY;}
    public int getVelX() {return velX;}
    public int getVelY() {return velY;}
    public void setVelW(int velW) {this.velW = velW;}
    public void setVelH(int velH) {this.velH = velH;}
    public int getVelW() {return velW;}
    public int getVelH() {return velH;}
}