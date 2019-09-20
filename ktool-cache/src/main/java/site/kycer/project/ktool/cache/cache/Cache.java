package site.kycer.project.ktool.cache.cache;

import site.kycer.project.ktool.basic.core.UUIDUtils;
import site.kycer.project.ktool.cache.enums.ExpirationType;
import site.kycer.project.ktool.cache.config.CacheConfig;

import java.util.*;

/**
 * 具体缓存缓存
 *
 * @author Kycer
 * @version 1.0
 * @date 2019-09-20
 */
public class Cache<K, V> implements ICache<K, V> {

    private CacheManager<K, V> cacheManager;

    private Cache(CacheConfig cacheConfig) {
        this.cacheManager = new CacheManager<>(cacheConfig);
    }


    @Override
    public Set<K> getKeys() {
        return this.cacheManager.getKeys();
    }

    @Override
    public void put(K key, V value) {
        this.cacheManager.put(key, value);
    }

    @Override
    public void put(K key, V value, Long millis) {
        this.cacheManager.put(key, value, millis);
    }

    @Override
    public Optional<V> get(K key) {
        return this.cacheManager.get(key);
    }

    @Override
    public List<Map<K, V>> getAll() {
        return this.cacheManager.getAll();
    }

    @Override
    public void clear() {
        this.cacheManager.clear();
    }

    @Override
    public boolean remove(K key) {
        return this.cacheManager.remove(key);
    }

    @Override
    public void removeAll(Collection<K> keys) {
        this.cacheManager.removeAll(keys);
    }

    @Override
    public Integer size() {
        return this.cacheManager.size();
    }

    @Override
    public boolean isFull() {
        return this.cacheManager.isFull();
    }

    @Override
    public boolean isEmpty() {
        return this.cacheManager.isEmpty();
    }

    @Override
    public boolean containsKey(K key) {
        return this.cacheManager.containsKey(key);
    }

    /**
     * builder形式创建 Cache
     *
     * @return {@linkplain Cache.Builder}
     */
    public static Cache.Builder newBuilder() {
        return new Builder();
    }

    public static class Builder<K, V> {
        private CacheConfig cacheConfig;

        Builder() {
            this.cacheConfig = new CacheConfig(UUIDUtils.generator());
        }

        /**
         * 设置缓存失效策略
         *
         * @param expiration {@linkplain ExpirationType} 缓存策略
         * @return {@linkplain Builder}
         */
        public Builder expiration(ExpirationType expiration) {
            if (Objects.isNull(expiration)) {
                return this;
            }
            this.cacheConfig.setExpiration(expiration);
            return this;
        }

        /**
         * 设置缓存大小
         *
         * @param size 大小
         * @return {@linkplain Builder}
         */
        public Builder size(Integer size) {
            if (Objects.isNull(size) || size < 0) {
                return this;
            }
            this.cacheConfig.setSize(size);
            return this;
        }

        /**
         * 缓存失效时间
         *
         * @param expires 失效时间
         * @return {@linkplain Builder}
         */
        public Builder expires(Long expires) {
            if (Objects.isNull(expires) || expires < 0) {
                return this;
            }
            this.cacheConfig.setExpires(expires);
            return this;
        }

        /**
         * 设置缓存失效扫描时间
         *
         * @param scanSeconds 缓存失效扫描时间
         * @return {@linkplain Builder}
         */
        public Builder scanSeconds(Long scanSeconds) {
            if (Objects.isNull(scanSeconds) || scanSeconds < 0) {
                return this;
            }
            this.cacheConfig.setScanSeconds(scanSeconds);
            return this;
        }

        public Cache<K, V> build() {
            return new Cache<>(this.cacheConfig);
        }

    }
}
