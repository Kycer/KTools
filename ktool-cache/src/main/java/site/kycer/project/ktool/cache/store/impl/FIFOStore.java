package site.kycer.project.ktool.cache.store.impl;

import java.util.LinkedHashMap;

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
        this.storageMap = new LinkedHashMap<>(SIZE + 1, 1.0f, false);
    }

    @Override
    public void eliminate() {
        storageMap.remove(this.getFirst().getKey());
    }

}
