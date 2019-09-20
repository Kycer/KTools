package site.kycer.project.ktool.cache.store;

import java.util.List;

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
        return null;
    }

    @Override
    public Element<K, V> get(K key) {
        return null;
    }

    @Override
    public List<Element<K, V>> getAll() {
        return null;
    }
}
