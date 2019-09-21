package site.kycer.project.ktool.cache.store.impl;

import site.kycer.project.ktool.cache.store.Element;
import site.kycer.project.ktool.cache.store.CacheStore;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

/**
 * 存储基类
 *
 * @author Kycer
 * @version 1.0
 * @date 2019-09-20
 */
@SuppressWarnings("WeakerAccess")
public abstract class AbstractStore<K, V> implements CacheStore<K, V> {

    protected final Map<K, Element<K, V>> STORAGE_MAP;

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    protected final Lock readLock = readWriteLock.readLock();
    protected final Lock writeLock = readWriteLock.writeLock();

    /**
     * 容器初始大小
     */
    private final Integer SIZE;

    AbstractStore(Integer size) {
        SIZE = size;
        this.STORAGE_MAP = new ConcurrentHashMap<>(size);
    }

    @Override
    public Collection<Element<K, V>> getAll() {
        if (isEmpty()) {
            return null;
        }
        return STORAGE_MAP.values();
    }

    @Override
    public Set<K> getKeys() {
        return STORAGE_MAP.keySet();
    }

    @Override
    public void clear() {
        STORAGE_MAP.clear();
    }

    @Override
    public void clearExpired() {
        if (STORAGE_MAP.isEmpty()) {
            return;
        }
        List<K> keys = STORAGE_MAP.values().stream().filter(Element::isExpired)
                .map(Element::getKey).collect(Collectors.toList());
        this.removeAll(keys);
    }

    @Override
    public Element<K, V> remove(K key) {
        return STORAGE_MAP.remove(key);
    }

    @Override
    public void removeAll(Collection<K> keys) {
        writeLock.lock();
        try {
            keys.forEach(this::remove);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public Integer size() {
        return STORAGE_MAP.size();
    }

    @Override
    public boolean isFull() {
        return STORAGE_MAP.size() == SIZE;
    }

    @Override
    public boolean isEmpty() {
        return STORAGE_MAP.isEmpty();
    }

    @Override
    public boolean containsKey(K key) {
        return STORAGE_MAP.containsKey(key);
    }
}
