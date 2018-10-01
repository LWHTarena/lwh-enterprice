package com.lwhtarena.company.ip;

import com.lwhtarena.company.ip.entities.LocationInfo;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

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
public class AutoReloadLocator implements ILocator, Observer {

    private final SimpleFileWatchService watchService;
    private final String filePath;
    private Locator locator;

    public AutoReloadLocator(String filePath, int intervalSeconds) throws IOException {
        this.filePath = filePath;
        this.locator = Locator.loadFromLocal(filePath);
        this.watchService = new SimpleFileWatchService(filePath, intervalSeconds);
        this.watchService.addObserver(this);
        this.watchService.excute();
    }

    public static void main(String[] args) {
        String filePath = "17monipdb.dat";
        if (args != null && args.length > 0) {
            filePath = args[0];
        }

        try {
            AutoReloadLocator l = new AutoReloadLocator(filePath, 10);
            System.out.println(l.find("8.8.8.8"));
        } catch (IOException var5) {
            var5.printStackTrace();
        }

        try {
            Thread.sleep(3600000L);
        } catch (InterruptedException var4) {
            var4.printStackTrace();
        }

    }

    @Override
    public LocationInfo find(String ip) {
        return this.locator.find(ip);
    }

    @Override
    public LocationInfo find(byte[] ipBin) {
        return this.locator.find(ipBin);
    }

    @Override
    public LocationInfo find(int address) {
        return this.locator.find(address);
    }

    @Override
    public void update(Observable o, Object arg) {
        try {
            this.locator = Locator.loadFromLocal(this.filePath);
        } catch (IOException var4) {
            var4.printStackTrace();
        }

    }

    public void shutdown() {
        this.watchService.deleteObserver(this);
        this.watchService.shutdown();
    }
}
