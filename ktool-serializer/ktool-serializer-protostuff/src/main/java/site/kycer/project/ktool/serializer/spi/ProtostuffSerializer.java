package site.kycer.project.ktool.serializer.spi;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import site.kycer.project.ktool.serializer.exceptions.SerializerException;
import site.kycer.project.ktool.spi.annotations.SPI;
import site.kycer.project.ktool.spi.annotations.SPIValue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * ProtostuffSerializer
 *
 * @author Kycer
 * @version 1.0.0
 * @date 2020-03-24
 */
@SuppressWarnings("unchecked")
@SPI(ObjectSerializer.class)
@SPIValue("protostuff")
public class ProtostuffSerializer implements ObjectSerializer {

    // private static final SchemaCache CACHED_SCHEMA = SchemaCache.getInstance();


    private static final Cache<Class<?>, Schema<?>> CACHE = CacheBuilder.newBuilder()
            .maximumSize(1024).expireAfterWrite(1, TimeUnit.HOURS).build();

    private <T> Schema<T> getSchema(Class<T> cls) throws ExecutionException {
        return (Schema<T>) CACHE.get(cls, () -> RuntimeSchema.createFrom(cls));
    }

    @Override
    public <T> byte[] serialize(T obj) throws SerializerException {
        Class<T> cls = (Class<T>) obj.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Schema<T> schema = getSchema(cls);
            ProtostuffIOUtil.writeTo(outputStream, obj, schema, buffer);
            return outputStream.toByteArray();
        } catch (IOException | ExecutionException e) {
            throw new SerializerException(e.getMessage(), e);
        } finally {
            buffer.clear();
        }
    }

    @Override
    public <T> T deserialize(final byte[] param, final Class<T> clazz) throws SerializerException {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(param)) {
            T object = clazz.newInstance();
            Schema<T> schema = getSchema(clazz);
            ProtostuffIOUtil.mergeFrom(inputStream, object, schema);
            return object;
        } catch (IOException | IllegalAccessException | InstantiationException | ExecutionException e) {
            throw new SerializerException(e.getMessage(), e);
        }
    }
}