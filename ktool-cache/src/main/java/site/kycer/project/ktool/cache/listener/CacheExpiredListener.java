package site.kycer.project.ktool.cache.listener;

import site.kycer.project.ktool.cache.cache.CacheManager;
import site.kycer.project.ktool.cache.scheduled.ScheduledClean;
import site.kycer.project.ktool.cache.store.Element;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 缓存失效监听器
 *
 * @author Kycer
 * @version 1.0
 * @date 2019-09-20
 */
public class CacheExpiredListener<K, V> {


    private final static ConcurrentLinkedQueue<Element> QUEUE = new ConcurrentLinkedQueue<>();

    /**
     * 缓存管理
     */
    private final CacheManager<K, V> cacheManager;

    /**
     * 扫描时间
     */
    private final Long scanSeconds;


    public CacheExpiredListener(CacheManager<K, V> cacheManager, Long scanSeconds) {
        this.cacheManager = cacheManager;
        this.scanSeconds = scanSeconds;
    }

    public void addSource(Element<K, V> element) {
        QUEUE.add(element);
    }

    public void run() {
        ScheduledClean.INSTANCE.schedule(() -> {

        }, this.scanSeconds);

    }
}
