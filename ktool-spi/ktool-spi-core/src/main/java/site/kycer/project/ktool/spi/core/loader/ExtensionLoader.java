package site.kycer.project.ktool.spi.core.loader;

import site.kycer.project.ktool.spi.annotations.SPIValue;
import site.kycer.project.ktool.spi.core.exceptions.SpiException;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * Spi加载
 *
 * @author Kycer
 * @version 1.0.0
 * @date 2020-03-26
 */
public class ExtensionLoader {

    /**
     * load Class
     *
     * @param type Class
     * @return Map
     */
    public static <T> Map<String, T> load(Class<T> type) {
        if (type == null) {
            throw new SpiException("type == null");
        }
        if (!type.isInterface()) {
            throw new SpiException(type + " not interface!");
        }
        ServiceLoader<T> loader = ServiceLoader.load(type);
        Map<String, T> map = new HashMap<>();
        for (T next : loader) {
            SPIValue annotation = next.getClass().getAnnotation(SPIValue.class);
            String key = next.getClass().getSimpleName();
            if (annotation != null && annotation.value().length() > 0) {
                key = annotation.value();
            }
            map.put(key, next);
        }
        return map;
    }

}
