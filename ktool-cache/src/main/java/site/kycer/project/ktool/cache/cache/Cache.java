package site.kycer.project.ktool.cache.cache;

import java.util.*;

/**
 * 缓存操作接口
 *
 * @author Kycer
 * @version 1.0
 * @date 2019-09-20
 */
public interface Cache<K, V> {

    /**
     * 获取所有缓存名字
     *
     * @return 所有缓存名字
     */
    Set<K> getKeys();

    /**
     * 添加一个元素
     *
     * @param key   缓存key
     * @param value 缓存value
     */
    void put(K key, V value);

    /**
     * 添加一个元素 带过期时间
     *
     * @param key    缓存key
     * @param value  缓存value
     * @param millis 缓存时长（毫秒）
     */
    void put(K key, V value, Long millis);

    /**
     * 根据 {@code key} 获取一个元素
     *
     * @param key 缓存key值
     * @return 缓存元素值
     */
    Optional<V> get(K key);

    /**
     * 获取所有缓存
     *
     * @return 索取所有缓存元素
     */
    List<Map<K, V>> getAll();

    /**
     * 清除所有元素
     */
    void clear();

    /**
     * 删除一个缓存
     *
     * @param key 缓存key值
     * @return @{code true} 删除成功
     */
    boolean remove(K key);

    /**
     * 删除一组元素
     *
     * @param keys 缓存key集合
     */
    void removeAll(Collection<K> keys);

    /**
     * 获取缓存大小
     *
     * @return 缓存大小
     */
    Integer size();

    /**
     * 缓存是否已满
     *
     * @return {@code true} 已满
     */
    boolean isFull();

    /**
     * 缓存是否为空
     *
     * @return {@code true} 为空
     */
    boolean isEmpty();

    /**
     * 是否包含key
     *
     * @param key key
     * @return {@code true} 包含
     */
    boolean containsKey(K key);
}
