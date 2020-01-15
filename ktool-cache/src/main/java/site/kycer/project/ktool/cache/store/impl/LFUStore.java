package site.kycer.project.ktool.cache.store.impl;

import site.kycer.project.ktool.cache.store.Element;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 最不经常使用算法
 *
 * @author Kycer
 * @version 1.0
 * @date 2019-09-20
 */
public class LFUStore<K, V> extends AbstractStore<K, V> {

    public LFUStore(Integer size) {
        super(size);
        storageMap = new ConcurrentHashMap<>(SIZE);
    }

    @Override
    public Element<K, V> updateElement(Element<K, V> element) {
        // 更新使用次数
        element.setHitCount(element.getHitCount() + 1);
        return element;
    }

    @Override
    public Element<K, V> put(Element<K, V> e) {
        writeLock.lock();
        try {
            // 手动清除所有过期数据
            clearExpired();
            if (isFull()) {
                ArrayList<Element<K, V>> list = new ArrayList<>(getAll());
                list.sort((o1, o2) -> {
                    int hComp = o2.getHitCount().compareTo(o1.getHitCount());
                    if (hComp != 0) {
                        return hComp;
                    } else {
                        return o2.getCreationTime().compareTo(o1.getCreationTime());
                    }
                });
                K key = list.get(list.size() - 1).getKey();
                this.remove(key);
            }
            e.setHitCount(1L);
            storageMap.put(e.getKey(), e);
            return e;
        } finally {
            writeLock.unlock();
        }
    }
}
