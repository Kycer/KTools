package site.kycer.project.ktool.cache.store.impl;

import site.kycer.project.ktool.cache.store.CacheStore;
import site.kycer.project.ktool.cache.store.Element;
import site.kycer.project.ktool.cache.store.ElementEvent;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
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
public abstract class AbstractStore<K, V> implements CacheStore<K, V>, ElementEvent<K, V> {

    protected Map<K, Element<K, V>> storageMap;

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    protected final Lock readLock = readWriteLock.readLock();
    protected final Lock writeLock = readWriteLock.writeLock();

    /**
     * 容器初始大小
     */
    protected final Integer SIZE;

    /**
     * 集合默认大小
     */
    private static final Integer DEFAULT_SIZE = 16;

    AbstractStore(Integer size) {
        SIZE = Objects.nonNull(size) ? size : DEFAULT_SIZE;
    }

    @Override
    public Element<K, V> put(Element<K, V> e) {
        writeLock.lock();
        try {
            // 手动清除所有过期数据
            clearExpired();
            // 淘汰对象
            if (isFull()) {
                eliminate();
            }
            storageMap.put(e.getKey(), e);
            return e;
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public Element<K, V> get(K key) {
        if (!containsKey(key)) {
            return null;
        }
        readLock.lock();
        try {
            Element<K, V> element = storageMap.get(key);
            // 过期就删除
            if (element.isExpired()) {
                remove(key);
                return null;
            }
            return updateElement(element);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Element<K, V> getFirst() {
        Map.Entry<K, Element<K, V>> next = storageMap.entrySet().iterator().next();
        return next.getValue();
    }

    @Override
    public Collection<Element<K, V>> getAll() {
        if (isEmpty()) {
            return null;
        }
        return storageMap.values();
    }

    @Override
    public Set<K> getKeys() {
        return storageMap.keySet();
    }

    @Override
    public void clear() {
        storageMap.clear();
    }

    @Override
    public void clearExpired() {
        if (storageMap.isEmpty()) {
            return;
        }
        List<K> keys = storageMap.values().stream().filter(Element::isExpired)
                .map(Element::getKey).collect(Collectors.toList());
        this.removeAll(keys);
    }

    @Override
    public Element<K, V> remove(K key) {
        return storageMap.remove(key);
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
        return storageMap.size();
    }

    @Override
    public boolean isFull() {
        return storageMap.size() == SIZE;
    }

    @Override
    public boolean isEmpty() {
        return storageMap.isEmpty();
    }

    @Override
    public boolean containsKey(K key) {
        return storageMap.containsKey(key);
    }
}
