package com.jack.configuration;

import java.nio.file.*;
import java.util.List;

/**
 * Created by maxim on 21/07/2016.
 */
public class WatchThread implements Runnable {
    private Path dir;
    private WatchService watcher;
    private boolean state;
    protected volatile boolean running = true;

    public WatchThread(String path){
        state = false;
        try {
            dir = Paths.get(path);
            watcher = dir.getFileSystem().newWatchService();
            dir.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);
        }
        catch (Exception e){
            System.out.println("Error when initializing Watch service:" + e.getMessage());
        }
    }

    public boolean isState() {
        return state;
    }

    public void terminate(){
        running = false;
    }

    public void run(){
        while(running){
            try{
                WatchKey watchKey = watcher.take();
                List<WatchEvent<?>> events = watchKey.pollEvents();
                for(WatchEvent<?> event : events){
                    if(event.kind() == StandardWatchEventKinds.ENTRY_MODIFY){
                        System.out.println("------------WATCHER THREAD INFORMATION------------");
                        System.out.println("Modified: " + event.context().toString());
                        state = true;
                    }
                }
                watchKey.reset();
                state = false;
            }
            catch (InterruptedException e){
                System.out.println("Error when watching: " + e.getMessage());
                running = false;
            }

        }
    }
}
