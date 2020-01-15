package site.kycer.project.ktool.cache.store.impl;

import site.kycer.project.ktool.cache.store.Element;
import site.kycer.project.ktool.cache.store.LRULinkedHashMap;

import java.util.Map;

/**
 * 最近最少使用
 *
 * @author Kycer
 * @version 1.0
 * @date 2019-09-20
 */
public class LRUStore<K, V> extends AbstractStore<K, V> {


    public LRUStore(Integer size) {
        super(size);
        this.storageMap = new LRULinkedHashMap<>(SIZE);
    }

    @Override
    public Element<K, V> getFirst() {
        Map.Entry<K, Element<K, V>> next = storageMap.entrySet().iterator().next();
        return next.getValue();
    }

}
