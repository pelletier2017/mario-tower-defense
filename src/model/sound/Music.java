package model.sound;

import javafx.util.Duration;

public class Music extends Sound {

    private static final double VOLUME = 0.2;
    private static final String PATH = "src/resources/sounds/music/";
    private Thread fadeInThread = new Thread();
    private Thread fadeOutThread = new Thread();
    
    public Music(String fileName) {
        super(PATH + fileName, VOLUME);
        fadeIn();
        
        // song will repeat when it ends
        player.setOnEndOfMedia( () -> player.seek(Duration.ZERO));
    }
    
    public void fadeIn() {
        double secondsToFade = 3;
        
        Thread fadeInThread = new Thread( () -> {
            for (int i = 1; i <= 100; i++) {
                try {
                    player.setVolume(VOLUME * (i / 100.0));
                    try {
                        Thread.sleep((long) (secondsToFade * 1000 / 100));
                    } catch (InterruptedException e) {
                        System.out.println("Fade-in interrupted");
                    }
                } catch (NullPointerException e) {
                    return;
                }
                
            }
        });
        fadeInThread.setDaemon(true);
        fadeInThread.start();
    }
    
    public void pause() {
        player.pause();
    }
    
    public void resume() {
        player.play();
    }
    
    public void interrupt() {
        System.out.println("interrupting music");
        player.stop();
        fadeInThread.interrupt();
        fadeOutThread.interrupt();
    }
    
}
