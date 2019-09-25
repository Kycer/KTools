package site.kycer.project.ktool.cache.cache;

import site.kycer.project.ktool.basic.core.CollectionUtils;
import site.kycer.project.ktool.cache.CacheBuilder;
import site.kycer.project.ktool.cache.config.CacheConfig;
import site.kycer.project.ktool.cache.enums.ExpirationType;
import site.kycer.project.ktool.cache.listener.RemovalListener;
import site.kycer.project.ktool.cache.store.CacheStore;
import site.kycer.project.ktool.cache.store.Element;
import site.kycer.project.ktool.cache.store.factory.CacheStoreFactory;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

/**
 * 具体执行缓存操作  TODO 参数判断
 *
 * @author Kycer
 * @version 1.0
 * @date 2019-09-20
 */
public class CacheManager<K, V> implements Cache<K, V> {

    /**
     * 缓存配置文件
     */
    private final CacheConfig configure;

    /**
     * 具体失效策略
     */
    private final ExpirationType expiration;

    /**
     * 缓存存储
     */
    private final CacheStore<K, V> cacheStore;

    private RemovalListener<K, V> removalListener;

    public CacheManager(CacheBuilder<K, V> cacheBuilder) {
        this.configure = cacheBuilder.cacheConfig();
        this.expiration = configure.getExpiration();
        this.cacheStore = CacheStoreFactory.create(expiration, configure.getSize());
        if (!Objects.equals(expiration, ExpirationType.PROTRACTED)) {
            this.removalListener = new RemovalListener<>(this, configure.getScanSeconds());
        }
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
        requireNonNull(key);
        requireNonNull(value);
        Element<K, V> element = generateElement(key, value, millis);
        cacheStore.put(element);
        if (element.getExpires() != 0) {
            this.removalListener.addSource(element);
        }
    }

    @Override
    public Optional<V> get(K key) {
        if (Objects.isNull(key)) {
            return Optional.empty();
        }
        Element<K, V> element = cacheStore.get(key);
        System.out.println(element);
        return Optional.ofNullable(element).map(Element::getValue);
    }

    @Override
    public List<Map<K, V>> getAll() {
        Collection<Element<K, V>> all = cacheStore.getAll();
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
        if (Objects.isNull(key)) {
            return false;
        }
        Element<K, V> element = cacheStore.remove(key);
        return Objects.nonNull(element);
    }

    @Override
    public void removeAll(Collection<K> keys) {
        if (CollectionUtils.isNotEmpty(keys)) {
            cacheStore.removeAll(keys);
        }
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
        if (Objects.isNull(key)) {
            return false;
        }
        return cacheStore.containsKey(key);
    }

    /**
     * 生成 {@link Element} 对象
     *
     * @param key   缓存key
     * @param value 缓存value
     * @return {@link Element}
     */
    private Element<K, V> generateElement(K key, V value, Long expires) {
        Long millis = Objects.equals(expiration, ExpirationType.PROTRACTED) ? 0L : Optional.ofNullable(expires).orElse(configure.getExpires());
        return new Element<>(key, value, millis);
    }
}
