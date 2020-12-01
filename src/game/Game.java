package src.game;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.Color;

public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = -4716793320140119407L;
    public static final String TITLE = "Yu-Gi-Oh! VANVITELLI EDITION";
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public static STATE gameState = STATE.MainMenu;
    private Thread thread;

    private MainMenu mainMenu;
    private Table table;

    private boolean running = false;

    public Game() {
        mainMenu = new MainMenu();
        table = new Table();
	this.addKeyListener(new KeyInput());   //travel è stato qui
        this.addMouseListener(new Mouse(table));
        new Window(WIDTH, HEIGHT, TITLE, this);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1_000_000_000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            if (running) render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000) {
                timer+=1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }
    
    private void tick() {
        if (gameState == STATE.MainMenu) {
            mainMenu.tick();
        }
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        
        g.setColor(Color.white);
        g.fillRect(0,0, WIDTH, HEIGHT);


        if (gameState == STATE.MainMenu) 
            mainMenu.render(g);
        
        if (gameState == STATE.Match) 
            table.render(g);
        
            g.dispose();
        bs.show();
    }
    public static void main(String[] args) {
        new Game();
    }
}