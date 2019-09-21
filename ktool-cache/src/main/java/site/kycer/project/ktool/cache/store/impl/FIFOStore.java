package site.kycer.project.ktool.cache.store.impl;

import site.kycer.project.ktool.cache.store.Element;

import java.util.Comparator;
import java.util.Optional;

/**
 * 先进先出
 *
 * @author Kycer
 * @version 1.0
 * @date 2019-09-20
 */
public class FIFOStore<K, V> extends AbstractStore<K, V> {

    public FIFOStore(Integer size) {
        super(size);
    }

    @Override
    public Element put(Element<K, V> e) {
        writeLock.lock();
        try {
            // 手动清除所有过期数据
            clearExpired();
            if (isFull()) {
                Optional<K> optional = getAll().stream().min(Comparator.comparing(Element::getCreationTime)).map(Element::getKey);
                optional.ifPresent(this::remove);
            }
            STORAGE_MAP.put(e.getKey(), e);
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
            Element<K, V> element = STORAGE_MAP.get(key);
            // 过期就删除
            if (element.isExpired()) {
                remove(key);
                return null;
            }
            return element;
        } finally {
            readLock.unlock();
        }
    }


}
