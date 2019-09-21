package site.kycer.project.ktool.cache.store.impl;

import site.kycer.project.ktool.cache.store.Element;

/**
 * @author Kycer
 * @version 1.0
 * @date 2019-09-20
 */
public class ProtractedStore<K, V> extends AbstractStore<K, V> {

    public ProtractedStore(Integer size) {
        super(size);
    }

    @Override
    public Element put(Element<K, V> e) {
        return STORAGE_MAP.put(e.getKey(), e);
    }

    @Override
    public Element<K, V> get(K key) {
        return STORAGE_MAP.get(key);
    }

}
