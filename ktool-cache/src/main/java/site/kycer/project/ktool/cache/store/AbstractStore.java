package site.kycer.project.ktool.cache.store;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 存储基类
 *
 * @author Kycer
 * @version 1.0
 * @date 2019-09-20
 */
public abstract class AbstractStore<K, V> implements ICacheStore<K, V> {

    private final Map<K, Element<K, V>> STORAGE_MAP;

    /**
     * 容器初始大小
     */
    private final Integer SIZE;

    AbstractStore(Integer size) {
        SIZE = size;
        this.STORAGE_MAP = new ConcurrentHashMap<>(size);
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
        STORAGE_MAP.values().stream().filter(Element::isExpired)
                .map(Element::getKey).forEach(this::remove);
    }

    @Override
    public Element<K, V> remove(K key) {
        return STORAGE_MAP.remove(key);
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
