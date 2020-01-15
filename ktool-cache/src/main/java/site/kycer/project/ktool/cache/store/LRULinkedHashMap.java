package site.kycer.project.ktool.cache.store;

import java.util.LinkedHashMap;

/**
 * 继承 LinkedHashMap, 固定大小超过删除
 *
 * @author Kycer
 * @version 1.0.0
 * @date 2020-01-15
 */
public class LRULinkedHashMap<K, V> extends LinkedHashMap<K, V> {
    /**
     * 容量，超过此容量自动删除末尾元素
     */
    private int capacity;


    public LRULinkedHashMap(int capacity) {
        super(capacity + 1, 1.0f, true);
        this.capacity = capacity;
    }

    /**
     * 获取容量
     *
     * @return 容量
     */
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * 设置容量
     *
     * @param capacity 容量
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
        //当链表元素大于容量时，移除最老（最久未被使用）的元素
        return size() > this.capacity;
    }
}
