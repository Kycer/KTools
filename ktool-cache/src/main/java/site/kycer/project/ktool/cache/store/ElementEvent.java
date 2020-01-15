package site.kycer.project.ktool.cache.store;

/**
 * 元素节点事件
 *
 * @author Kycer
 * @version 1.0.0
 * @date 2020-01-15
 */
public interface ElementEvent<K, V> {

    /**
     * 淘汰对象
     */
    default void eliminate() {

    }

    /**
     * 更新对象信息
     *
     * @param element {@link Element}
     * @return {@link Element}
     */
    default Element<K, V> updateElement(Element<K, V> element) {
        return element;
    }
}
