package com.lwhtarena.company.ip;

import java.io.File;
import java.util.Observable;

/**
 * <p>
 * <h2>简述：</h2>
 * <ol></ol>
 * <h2>功能描述：</h2>
 * <ol></ol>
 * </p>
 *
 * @Author: liwh
 * @Date :
 * @Version: 版本
 */
public class SimpleFileWatchService  extends Observable implements Runnable{

    private File file;
    private Thread thread;
    private long lastModified = 0L;
    private int intervalSeconds;
    private volatile boolean flag;

    SimpleFileWatchService(String filePath, int intervalSeconds) {
        this.file = new File(filePath);
        this.intervalSeconds = intervalSeconds;
        this.lastModified = this.file.lastModified();
        this.thread = new Thread(this);
        this.flag = true;
    }

    private void monitorFile() {
        try {
            Thread.sleep(300000L);
        } catch (InterruptedException var5) {
            ;
        }

        while(this.flag) {
            long l = this.file.lastModified();
            if (l != this.lastModified) {
                this.lastModified = l;
                this.setChanged();
                this.notifyObservers();
            }

            try {
                Thread.sleep((long)(this.intervalSeconds * 1000));
            } catch (InterruptedException var4) {
                ;
            }
        }

    }

    void excute() {
        this.thread.start();
    }

    @Override
    public void run() {
        this.monitorFile();
    }

    public void shutdown() {
        this.flag = false;
    }
}
