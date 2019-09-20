package site.kycer.project.ktool.cache.cache;

import site.kycer.project.ktool.basic.core.CollectionUtils;
import site.kycer.project.ktool.cache.config.CacheConfig;
import site.kycer.project.ktool.cache.store.Element;
import site.kycer.project.ktool.cache.store.ICacheStore;
import site.kycer.project.ktool.cache.store.factory.CacheStoreFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 具体执行缓存操作
 *
 * @author Kycer
 * @version 1.0
 * @date 2019-09-20
 */
public class CacheManager<K, V> implements ICache<K, V> {

    /**
     * 缓存配置文件
     */
    private final CacheConfig configure;

    /**
     * 缓存存储
     */
    private final ICacheStore<K, V> cacheStore;

    public CacheManager(CacheConfig configure) {
        this.configure = configure;
        this.cacheStore = CacheStoreFactory.create(configure.getExpiration(), configure.getSize());
    }

    @Override
    public Set<K> getKeys() {
        return cacheStore.getKeys();
    }

    @Override
    public void put(K key, V value) {
        this.put(key, value, null);
    }

    @Override
    public void put(K key, V value, Long millis) {
        cacheStore.put(generateElement(key, value, millis));
    }

    @Override
    public Optional<V> get(K key) {
        Element<K, V> element = cacheStore.get(key);
        return Optional.ofNullable(element).map(Element::getValue);
    }

    @Override
    public List<Map<K, V>> getAll() {
        List<Element<K, V>> all = cacheStore.getAll();
        if (CollectionUtils.isNotEmpty(all)) {
            return all.stream()
                    .map(a -> {
                        Map<K, V> map = new HashMap<>(1);
                        map.put(a.getKey(), a.getValue());
                        return map;
                    }).collect(Collectors.toList());
        }
        return new ArrayList<>(0);
    }

    @Override
    public void clear() {
        cacheStore.clear();
    }

    @Override
    public boolean remove(K key) {
        Element<K, V> element = cacheStore.remove(key);
        return Objects.nonNull(element);
    }

    @Override
    public void removeAll(Collection<K> keys) {

    }

    @Override
    public Integer size() {
        return cacheStore.size();
    }

    @Override
    public boolean isFull() {
        return cacheStore.isFull();
    }

    @Override
    public boolean isEmpty() {
        return cacheStore.isEmpty();
    }

    @Override
    public boolean containsKey(K key) {
        return cacheStore.containsKey(key);
    }

    /**
     * 生成 {@linkplain Element} 对象
     *
     * @param key   缓存key
     * @param value 缓存value
     * @return {@linkplain Element}
     */
    private Element<K, V> generateElement(K key, V value, Long expires) {
        Long millis = Optional.ofNullable(expires).orElse(configure.getExpires());
        return new Element<>(key, value, millis);
    }
}
