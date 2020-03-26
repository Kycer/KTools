package site.kycer.project.ktool.spi.core.context;

import site.kycer.project.ktool.spi.core.exceptions.SpiException;
import site.kycer.project.ktool.spi.core.loader.ExtensionLoader;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * CacheManager
 *
 * @author Kycer
 * @version 1.0.0
 * @date 2020-03-26
 */
public class SpiCacheManager<T> {

    /**
     * 缓存
     */
    private final Map<Class<T>, Map<String, T>> SPI_CACHE_MAP = new ConcurrentHashMap<>();

    /**
     * 获取说有实现类
     *
     * @param clazz Class<T>
     * @return 实现类Map
     */
    public Map<String, T> getAllType(Class<T> clazz) {
        if (!SPI_CACHE_MAP.containsKey(clazz)) {
            Map<String, T> map = ExtensionLoader.load(clazz);
            if (map.isEmpty()) {
                throw new SpiException("not found spi service");
            }
            SPI_CACHE_MAP.put(clazz, map);
            return map;
        }
        return SPI_CACHE_MAP.get(clazz);
    }

    /**
     * 获取说有实现类
     *
     * @param clazz  Class<T>
     * @param tClazz Class<? extends T>
     * @return 实现类Map
     */
    public T getType(Class<T> clazz, Class<? extends T> tClazz) {
        Map<String, T> map = this.getAllType(clazz);
        if (map == null) {
            return null;
        }
        return map.get(tClazz.getSimpleName());
    }

    /**
     * 获取说有实现类
     *
     * @param clazz    Class<T>
     * @param typeName typeName
     * @return 实现类Map
     */
    public T getType(Class<T> clazz, String typeName) {
        Map<String, T> map = this.getAllType(clazz);
        if (map == null) {
            return null;
        }
        return map.get(typeName);
    }
}
