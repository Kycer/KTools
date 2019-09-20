package site.kycer.project.ktool.cache.config;

import site.kycer.project.ktool.cache.enums.ExpirationType;

/**
 * Cache的一些配置
 *
 * @author Kycer
 * @version 1.0
 * @date 2019-09-20
 */
public class CacheConfig {

    /**
     * 缓存名称
     */
    private String name;

    /**
     * 缓存丢弃策略
     */
    private ExpirationType expiration;

    /**
     * 缓存大小
     */
    private Integer size;

    /**
     * 存活时间(毫秒) 0: 表示永久存活
     */
    private Long expires;

    /**
     * 缓存失效扫描间隔(毫秒)
     */
    private Long scanSeconds;


    public CacheConfig(String name) {
        this.name = name;
        this.expiration = ExpirationType.LRU;
        this.size = 10;
        this.expires = 0L;
        this.scanSeconds = 3000L;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ExpirationType getExpiration() {
        return expiration;
    }

    public void setExpiration(ExpirationType expiration) {
        this.expiration = expiration;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Long getExpires() {
        return expires;
    }

    public void setExpires(Long expires) {
        this.expires = expires;
    }

    public Long getScanSeconds() {
        return scanSeconds;
    }

    public void setScanSeconds(Long scanSeconds) {
        this.scanSeconds = scanSeconds;
    }

}
