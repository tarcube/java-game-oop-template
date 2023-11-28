import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import java.util.*;
import javax.sound.sampled.*;
import java.io.*;

// -

public class Demo extends Canvas implements Runnable {
    public static int WIDTH = 800, HEIGHT = 600;
    private Thread thread;
    private boolean running = false;
    private Handler handler;

    public static int red = (int) (Math.random()*128);
    public static int green = (int) (Math.random()*128);
    public static int blue = (int) (Math.random()*128);
    public static Color randomColor = new Color(red, green, blue);
    public static Color randomColorBy2 = new Color(red/2, green/2, blue/2);

    public Demo() {
        handler = new Handler();
        this.addKeyListener(new KeyInput(handler));
        new Window(WIDTH, HEIGHT, "Demo", this);
        handler.addObject(new Background(100, 100, ID.Background, 1, 1));
    }

    private void tick(int ticks) {
        handler.tick(ticks);
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {}
    }

    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 100.0;
        double ns = 1_000_000_000.0 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        int ticks = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime)/ns;
            lastTime = now;
            while (delta >= 1) {
                tick(ticks);
                delta--;
                ticks++;
                if (running) {
                    Toolkit.getDefaultToolkit().sync();
                    render();
                    frames++;
                }
            }
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frames = 0;
                ticks = 0;
            }
        }
        stop();
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(randomColor);
        g.fillRect(0,0,WIDTH,HEIGHT);
        handler.render(g);
        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        if (args.length != 0) {
            int w = Integer.parseInt(args[0]);
            int h = Integer.parseInt(args[1]);
        }
        new Demo();
    }
}

// -

class KeyInput extends KeyAdapter {
    private Handler handler;

    public KeyInput(Handler handler) {
        this.handler = handler;
    }

    public static HashSet<Integer> keysPressed = new HashSet<>();

    public static void clickSFX() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(Demo.class.getResource("colon_3.wav"));
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
            Demo.red = (int) (Math.random()*128);
            Demo.green = (int) (Math.random()*128);
            Demo.blue = (int) (Math.random()*128);
            Demo.randomColor = new Color(Demo.red, Demo.green, Demo.blue);
            Demo.randomColorBy2 = new Color(Demo.red/2, Demo.green/2, Demo.blue/2);
            clickSFX();
        }
    }

    @Override

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        keysPressed.remove(key);
    }
}

// -

class Handler {
    LinkedList<GameObject> object = new LinkedList<GameObject>();
    
    public void tick(int ticks) {
        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);
            tempObject.tick(ticks);
        }
    }

    public void render(Graphics g) {
        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);
            tempObject.render(g);
        }
    }

    public void addObject(GameObject object) {this.object.add(object);}
    public void removeObject(GameObject object) {this.object.remove(object);}
}

// -

class Window extends Canvas {
    Window (int width, int height, String title, Demo Demo) {
        JFrame frame = new JFrame(title);
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(Demo);
        frame.setVisible(true);
        Demo.start();
    }
}

// -

abstract class GameObject {
    protected int x, y;
    protected ID id;
    protected int velX, velY;

    public GameObject(int x, int y, ID id, int velX, int velY) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.velX = velX;
        this.velY = velY;
    }

    public abstract void tick(int ticks);
    public abstract void render(Graphics g);

    public void setX(int x) {this.x = x;}
    public void setY(int y) {this.y = y;}
    public int getX() {return x;}
    public int getY() {return y;}
    public void setId(ID id) {this.id = id;}
    public ID getId() {return id;}
    public void setVelX(int velX) {this.velX = velX;}
    public void setVelY(int velY) {this.velY = velY;}
    public int getVelX() {return velX;}
    public int getVelY() {return velY;}
}

// -

class Background extends GameObject {
    static int w = Demo.WIDTH/4;
    static int h = Demo.HEIGHT/4;

    public Background(int x, int y, ID id, int velX, int velY) {
        super(x, y, id, velX, velY);
    }

    public void tick(int ticks) {
        if (id == ID.Background) {x += velX; y += velY;}
        if (x < 0 || x > Demo.WIDTH-w) {velX *= -1;}
        if (y < 0 || y > Demo.HEIGHT-h) {velY *= -1;}
    }

    public void render(Graphics g) {
        if (id == ID.Background) {
            g.setColor(Demo.randomColorBy2);
            g.fillRect(x, y, w, h);
        }
    }
}

// -

enum ID {
    Background();
}