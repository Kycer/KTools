package site.kycer.project.ktool.cache.enums;

/**
 * 缓存过期策略
 *
 * @author Kycer
 * @version 1.0
 * @date 2019-09-20
 */
public enum ExpirationType {
    /**
     * 最近最少使用
     */
    LRU,

    /**
     * 最不经常使用
     */
    LFU,

    /**
     * 先进先出
     */
    FIFO,

    /**
     * 持久
     */
    PROTRACTED
}
