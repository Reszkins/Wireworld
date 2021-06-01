package GUI.utils;

import GUI.WorldCanvas;
import Wireworld.World;
import javafx.application.Platform;

public class PausableTask implements Runnable {

    Object o = new Object();
    private volatile boolean isPaused = false;
    private volatile boolean shutDown = false;

    private World world;
    private int iterations;
    private WorldCanvas canvas;
    private int maxFps;
    PropertiesManager properties;

    public PausableTask(WorldCanvas canvas, int iterations) {
        this.world = canvas.getWorld();
        this.canvas = canvas;
        this.iterations = iterations;
        properties = new PropertiesManager();
        this.maxFps = Integer.parseInt(properties.getProperty("maxFPS"));
    }

    public void pause(){
        isPaused = true;
    }

    public void resume(){
        isPaused = false;
        synchronized (o) {
            o.notifyAll();
        }
    }

    public void shutDown() {
        shutDown = true;
    }

    @Override
    public void run() {

        while(!Thread.currentThread().isInterrupted()){
            if(!isPaused) {
                for (int i = world.generation; i < iterations; i++) {
                    if(isPaused || shutDown) break;
                    world.NextIteration();
                    //canvas.drawWorld();
                    canvas.drawNextGen();
                    Platform.runLater(() -> canvas.printGen());
                    try {
                        Thread.sleep(1000 / maxFps);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(world.generation >= iterations || shutDown) break;
            }
            else{
                try {
                    while(isPaused){
                        synchronized(o){
                            o.wait();
                        }
                    }
                }
                catch (InterruptedException e) {
                }
            }
        }
    }

}

