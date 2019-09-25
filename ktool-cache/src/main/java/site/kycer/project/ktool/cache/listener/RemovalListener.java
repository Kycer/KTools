package site.kycer.project.ktool.cache.listener;

import site.kycer.project.ktool.cache.cache.CacheManager;
import site.kycer.project.ktool.cache.scheduled.ScheduledExecutor;
import site.kycer.project.ktool.cache.store.Element;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;

/**
 * 剔除监听器
 *
 * @author Kycer
 * @version 1.0
 * @date 2019-09-20
 */
public class RemovalListener<K, V> {


    private final ConcurrentLinkedQueue<Element<K, V>> LINKED_QUEUE = new ConcurrentLinkedQueue<>();

    /**
     * 缓存管理
     */
    private final CacheManager<K, V> cacheManager;

    /**
     * 扫描时间
     */
    private final Long scanSeconds;


    /**
     * {@link ScheduledFuture}
     */
    private volatile ScheduledFuture<?> schedule;

    public RemovalListener(CacheManager<K, V> cacheManager, Long scanSeconds) {
        this.cacheManager = cacheManager;
        this.scanSeconds = scanSeconds;
    }

    public void addSource(Element<K, V> element) {
        LINKED_QUEUE.add(element);
        run();
    }

    private void run() {
        if (Objects.isNull(schedule)) {
            synchronized (this) {
                if (Objects.isNull(schedule)) {
                    this.schedule = ScheduledExecutor.getInstance().schedule(() -> {
                        List<K> keys = LINKED_QUEUE.stream().filter(Element::isExpired).map(Element::getKey).collect(Collectors.toList());
                        cacheManager.removeAll(keys);
                    }, this.scanSeconds);
                }
            }
        }
    }
}
