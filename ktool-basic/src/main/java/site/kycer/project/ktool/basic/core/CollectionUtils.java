package site.kycer.project.ktool.basic.core;

import java.util.Collection;
import java.util.Map;

/**
 * @author Kycer
 * @version 1.0
 * @date 2019-09-18
 */
public class CollectionUtils {
    private CollectionUtils() {
    }
    /**
     * 判断集合是否为空
     *
     * @param collection 集合
     * @return {@code true} 为空
     */
    public static boolean isEmpty(Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }

    /**
     * 判断集合不为空
     *
     * @param collection 集合
     * @return {@code true} 不为空
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * 判断map是否为空
     *
     * @param map Map
     * @return {@code true} 为空
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return (map == null || map.isEmpty());
    }

    /**
     * 判断map不为空
     *
     * @param map Map
     * @return {@code true} 不为空
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }
}
