package site.kycer.project.ktool.serializer.spi;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * SchemaCache
 *
 * @author Kycer
 * @version 1.0.0
 * @date 2020-03-24
 */
public class SchemaCache {
    private Cache<Class<?>, Schema<?>> cache = CacheBuilder.newBuilder()
            .maximumSize(1024).expireAfterWrite(1, TimeUnit.HOURS).build();

    /**
     * Gets instance.
     *
     * @return the instance
     */
    protected static SchemaCache getInstance() {
        return SchemaCacheHolder.cache;
    }

    private Schema<?> get(final Class<?> cls, final Cache<Class<?>, Schema<?>> cache) {
        try {
            return cache.get(cls, () -> RuntimeSchema.createFrom(cls));
        } catch (ExecutionException e) {
            return null;
        }
    }

    /**
     * acquire Schema with class.
     *
     * @param clazz Class
     * @return Schema schema
     */
    public Schema<?> get(final Class<?> clazz) {
        return get(clazz, cache);
    }

    private static class SchemaCacheHolder {
        private static SchemaCache cache = new SchemaCache();
    }
}
