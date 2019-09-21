package site.kycer.project.ktool.cache.store.factory;

import site.kycer.project.ktool.cache.enums.ExpirationType;
import site.kycer.project.ktool.cache.store.*;
import site.kycer.project.ktool.cache.store.impl.FIFOStore;
import site.kycer.project.ktool.cache.store.impl.LFUStore;
import site.kycer.project.ktool.cache.store.impl.LRUStore;
import site.kycer.project.ktool.cache.store.impl.ProtractedStore;

/**
 * 存储实例创建工厂
 *
 * @author Kycer
 * @version 1.0
 * @date 2019-09-20
 */
public class CacheStoreFactory {

    public static <K, V> CacheStore<K, V> create(ExpirationType expiration, Integer size) {
        switch (expiration) {
            case LFU:
                return new LFUStore<>(size);
            case FIFO:
                return new FIFOStore<>(size);
            case PROTRACTED:
                return new ProtractedStore<>(size);
            default:
                return new LRUStore<>(size);
        }
    }
}
