package site.kycer.project.ktool.cache.scheduled;

import site.kycer.project.ktool.cache.store.ICacheStore;

import java.util.concurrent.TimeUnit;

/**
 * @author Kycer
 * @version 1.0
 * @date 2019-09-20
 */
public class ClearTask implements Runnable {

    /**
     * 多久执行一次
     */
    private Long scanSeconds;

    private ICacheStore cacheStore;

    public ClearTask(Long scanSeconds, ICacheStore cacheStore) {
        this.scanSeconds = scanSeconds;
        this.cacheStore = cacheStore;
    }

    @Override
    public void run() {
        while (true) {
            cacheStore.clearExpired();
            try {
                TimeUnit.SECONDS.sleep(scanSeconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
