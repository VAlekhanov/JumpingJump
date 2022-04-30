package org.example;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.IOException;

public class Game extends Canvas implements Runnable {

    private boolean running = false;
    private String title = "JUMPINGJUMP";
    private int width = 640;
    private int height = width / 12 * 9;
    private Thread thread;
    private int countOfBuffers = 3;
    private long sleepTime = 15l;
    private Handler handler;

    public Game() throws IOException {
        new MainFrame(width, height, title, this);
        start();

        handler = new Handler();
    }

    private void tick() {
    }

    private void render() throws InterruptedException {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(countOfBuffers);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        Graphics2D g2 = (Graphics2D) g;

        g.setColor(Color.gray);
        g.fillRect(0,0,width,height);

        g2.translate(width,height);

        handler.render(g);

        g2.translate(width,height);
        Thread.sleep(sleepTime);

        g.dispose();
        bs.show();
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

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
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
            if (running) {
                try {
                    render();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }
}
