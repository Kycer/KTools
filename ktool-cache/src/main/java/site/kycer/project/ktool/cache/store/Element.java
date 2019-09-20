package site.kycer.project.ktool.cache.store;

import site.kycer.project.ktool.basic.core.DateUtils;

import java.io.Serializable;

/**
 * 缓存载体
 *
 * @author Kycer
 * @version 1.0
 * @date 2019-09-20
 */
public class Element<K, V> implements Serializable {

    /**
     * 缓存Key
     */
    private K key;

    /**
     * 缓存
     */
    private V value;

    /**
     * 存活时间 {@code 0} 表示永久
     */
    private Long expires;

    /**
     * 使用次数
     */
    private Long hitCount;

    /**
     * 创建时间
     */
    private Long creationTime;

    /**
     * 失效时间
     */
    private Long failureTime;

    /**
     * 最后更新时间
     */
    private Long lastAccessTime;


    public Element(K key, V value, Long expires) {
        this.key = key;
        this.value = value;
        this.expires = expires;
        this.hitCount = 0L;
        this.creationTime = DateUtils.getMillis();
        this.lastAccessTime = DateUtils.getMillis();
        this.failureTime = this.expires == 0 ? 0 : (this.creationTime + this.expires);
    }

    /**
     * 是否过期
     *
     * @return {@code true} 是
     */
    public boolean isExpired() {
        if (this.failureTime == 0) {
            return false;
        }
        return this.failureTime > 0 && this.failureTime < DateUtils.getMillis();
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public Long getExpires() {
        return expires;
    }

    public void setExpires(Long expires) {
        this.expires = expires;
    }

    public Long getHitCount() {
        return hitCount;
    }

    public void setHitCount(Long hitCount) {
        this.hitCount = hitCount;
    }

    public Long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Long creationTime) {
        this.creationTime = creationTime;
    }

    public Long getFailureTime() {
        return failureTime;
    }

    public void setFailureTime(Long failureTime) {
        this.failureTime = failureTime;
    }

    public Long getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Long lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    @Override
    public String toString() {
        return "Element{" +
                "key=" + key +
                ", value=" + value +
                ", expires=" + expires +
                ", hitCount=" + hitCount +
                ", creationTime=" + creationTime +
                ", failureTime=" + failureTime +
                ", lastAccessTime=" + lastAccessTime +
                '}';
    }
}
