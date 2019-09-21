package site.kycer.project.ktool.cache.store;

import java.util.Collection;
import java.util.Set;

/**
 * 缓存存储接口
 *
 * @author Kycer
 * @version 1.0
 * @date 2019-09-20
 */
public interface CacheStore<K, V> {

    /**
     * 获取所有缓存名字
     *
     * @return 所有缓存名字
     */
    Set<K> getKeys();

    /**
     * 添加一个元素
     *
     * @param e {@linkplain Element} 元素
     * @return 当前添加的 {@linkplain Element} 元素
     */
    Element put(Element<K, V> e);

    /**
     * 根据 {@code key} 获取一个元素 {@linkplain Element}
     *
     * @param key 缓存key值
     * @return 缓存元素 {@linkplain Element}
     */
    Element<K, V> get(K key);

    /**
     * 获取所有缓存
     *
     * @return 索取所有缓存元素 {@linkplain Element}
     */
    Collection<Element<K, V>> getAll();

    /**
     * 清除所有元素
     */
    void clear();

    /**
     * 清楚所有过期数据
     */
    void clearExpired();

    /**
     * 删除一个缓存
     *
     * @param key 缓存key值
     * @return @{code true} 删除成功
     */
    Element<K, V> remove(K key);

    /**
     * 移除所有
     *
     * @param keys 集合K
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
