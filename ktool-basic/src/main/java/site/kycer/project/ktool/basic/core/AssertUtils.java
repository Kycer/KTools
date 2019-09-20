package site.kycer.project.ktool.basic.core;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * 断言工具类
 *
 * @author Kycer
 * @version 1.0
 * @date 2019-08-12
 */
public final class AssertUtils {

    /**
     * 断言为 True
     *
     * @param expression boolean对象
     * @param message    报错提示
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }


    /**
     * 断言对象为 null
     *
     * @param object  对象
     * @param message 报错提示
     */
    public static void isNull(Object object, String message) {
        if (object != null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言对象不为 null
     *
     * @param object  对象
     * @param message 报错提示
     */
    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言文本为空
     *
     * @param text    文本内容
     * @param message 报错提示
     */
    public static void notBlank(String text, String message) {
        if (StringUtils.isBlank(text)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言集合为空
     *
     * @param collection 集合
     * @param message    报错提示
     */
    public static void notEmpty(Collection<?> collection, String message) {
        if (Objects.isNull(collection) || collection.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言Map为空
     *
     * @param map     map
     * @param message 错误提示
     */
    public static void notEmpty(Map<?, ?> map, String message) {
        if (Objects.isNull(map) || map.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }
}
