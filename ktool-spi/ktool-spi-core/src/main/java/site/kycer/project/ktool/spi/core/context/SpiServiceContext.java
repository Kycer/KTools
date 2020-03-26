package site.kycer.project.ktool.spi.core.context;

import java.util.Map;

/**
 * ServiceContext
 *
 * @author Kycer
 * @version 1.0.0
 * @date 2020-03-26
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class SpiServiceContext {

    private static SpiCacheManager spiCacheManager;

    private SpiServiceContext() {
        spiCacheManager = new SpiCacheManager();
    }

    /**
     * getInstance
     */
    public static SpiServiceContext getInstance() {
        return ServiceContextHolder.INSTANCE;
    }

    private static class ServiceContextHolder {
        private static final SpiServiceContext INSTANCE = new SpiServiceContext();
    }

    /**
     * 获取说有实现类
     *
     * @param clazz Class<T>
     * @return 实现类Map
     */
    public <T> Map<String, T> getAllType(Class<T> clazz) {
        return spiCacheManager.getAllType(clazz);
    }

    /**
     * 获取说有实现类
     *
     * @param clazz  Class<T>
     * @param tClazz Class<? extends T>
     * @return 实现类Map
     */
    public <T> T getType(Class<T> clazz, Class<? extends T> tClazz) {
        return (T) spiCacheManager.getType(clazz, tClazz);
    }

    /**
     * 获取说有实现类
     *
     * @param clazz    Class<T>
     * @param typeName typeName
     * @return 实现类Map
     */
    public <T> T getType(Class<T> clazz, String typeName) {
        return (T) spiCacheManager.getType(clazz, typeName);
    }

}
