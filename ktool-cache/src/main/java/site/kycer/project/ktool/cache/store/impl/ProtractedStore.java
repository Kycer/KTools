package site.kycer.project.ktool.cache.store.impl;

import site.kycer.project.ktool.cache.store.Element;

/**
 * 持久缓存
 *
 * @author Kycer
 * @version 1.0
 * @date 2019-09-20
 */
public class ProtractedStore<K, V> extends AbstractStore<K, V> {

    public ProtractedStore(Integer size) {
        super(size);
    }

    @Override
    public Element<K, V> getFirst() {
        return null;
    }

    @Override
    public Element<K, V> put(Element<K, V> e) {
        return storageMap.put(e.getKey(), e);
    }

    @Override
    public Element<K, V> get(K key) {
        return storageMap.get(key);
    }

}
