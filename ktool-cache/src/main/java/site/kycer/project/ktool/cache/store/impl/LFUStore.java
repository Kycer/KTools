package site.kycer.project.ktool.cache.store.impl;

import site.kycer.project.ktool.cache.store.Element;

import java.util.ArrayList;

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
    }

    @Override
    public Element put(Element<K, V> e) {
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
        writeLock.lock();
        try {
            Element<K, V> element = STORAGE_MAP.get(key);
            // 过期就删除
            if (element.isExpired()) {
                remove(key);
                return null;
            }
            // 更新最后一次使用时间
            element.setHitCount(element.getHitCount() + 1);
            return element;
        } finally {
            writeLock.unlock();
        }
    }

}
