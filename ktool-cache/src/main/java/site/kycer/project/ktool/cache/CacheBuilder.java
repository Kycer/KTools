package site.kycer.project.ktool.cache;

import site.kycer.project.ktool.basic.core.UUIDUtils;
import site.kycer.project.ktool.cache.cache.CacheManager;
import site.kycer.project.ktool.cache.cache.Cache;
import site.kycer.project.ktool.cache.config.CacheConfig;
import site.kycer.project.ktool.cache.enums.ExpirationType;

import java.util.Objects;

/**
 * @author Kycer
 * @version 1.0
 * @date 2019-09-20
 */
@SuppressWarnings("unchecked")
public class CacheBuilder<K, V> {

    public static CacheBuilder<Object, Object> newBuilder() {
        return new CacheBuilder<>();
    }


    private CacheConfig cacheConfig;

    CacheBuilder() {
        this.cacheConfig = new CacheConfig(UUIDUtils.generator());
    }

    /**
     * 设置缓存失效策略
     *
     * @param expiration {@link ExpirationType} 缓存策略
     * @return {@link CacheBuilder}
     */
    public <K1 extends K, V1 extends V> CacheBuilder<K1, V1> expiration(ExpirationType expiration) {
        if (Objects.isNull(expiration)) {
            return (CacheBuilder<K1, V1>) this;
        }
        this.cacheConfig.setExpiration(expiration);
        return (CacheBuilder<K1, V1>) this;
    }

    /**
     * 设置缓存大小
     *
     * @param size 大小
     * @return {@link CacheBuilder}
     */
    public <K1 extends K, V1 extends V> CacheBuilder<K1, V1> size(Integer size) {
        if (Objects.isNull(size) || size < 0) {
            return (CacheBuilder<K1, V1>) this;
        }
        this.cacheConfig.setSize(size);
        return (CacheBuilder<K1, V1>) this;
    }

    /**
     * 缓存失效时间
     *
     * @param expires 失效时间
     * @return {@link CacheBuilder}
     */
    public <K1 extends K, V1 extends V> CacheBuilder<K1, V1> expires(Long expires) {
        if (Objects.isNull(expires) || expires < 0) {
            return (CacheBuilder<K1, V1>) this;
        }
        this.cacheConfig.setExpires(expires);
        return (CacheBuilder<K1, V1>) this;
    }

    /**
     * 设置缓存失效扫描时间
     *
     * @param scanSeconds 缓存失效扫描时间
     * @return {@link CacheBuilder}
     */
    public <K1 extends K, V1 extends V> CacheBuilder<K1, V1> scanSeconds(Long scanSeconds) {
        if (Objects.isNull(scanSeconds) || scanSeconds < 0) {
            return (CacheBuilder<K1, V1>) this;
        }
        this.cacheConfig.setScanSeconds(scanSeconds);
        return (CacheBuilder<K1, V1>) this;
    }

    public <K1 extends K, V1 extends V> Cache<K1, V1> build() {
        return new CacheManager<>((CacheBuilder<K1, V1>) this);
    }

    public CacheConfig cacheConfig() {
        return cacheConfig;
    }
}
