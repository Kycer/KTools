package site.kycer.project.ktool.serializer;

import site.kycer.project.ktool.serializer.enums.SerializerProvider;
import site.kycer.project.ktool.serializer.exceptions.SerializerException;
import site.kycer.project.ktool.serializer.spi.ObjectSerializer;
import site.kycer.project.ktool.spi.core.context.SpiServiceContext;

import java.util.Map;

/**
 * SerializerHelper
 *
 * @author Kycer
 * @version 1.0.0
 * @date 2020-03-26
 */
public class SerializerHelper {

    private SerializerHelper() {

    }

    public static byte[] serialize(Object obj) throws SerializerException {
        Map<String, ObjectSerializer> map = getAllType();
        SerializerProvider provider = SerializerProvider.JDK;
        if (map.containsKey(SerializerProvider.KRYO.getValue())) {
            provider = SerializerProvider.KRYO;
        }
        return serialize(obj, provider);
    }


    public static <T> T deserialize(byte[] param, Class<T> clazz) throws SerializerException {
        Map<String, ObjectSerializer> map = getAllType();
        SerializerProvider provider = SerializerProvider.JDK;
        if (map.containsKey(SerializerProvider.KRYO.getValue())) {
            provider = SerializerProvider.KRYO;
        }
        return deserialize(param, clazz, provider);
    }

    public static byte[] serialize(Object obj, SerializerProvider provider) throws SerializerException {
        ObjectSerializer serializer = getType(provider);
        return serializer.serialize(obj);
    }

    public static <T> T deserialize(byte[] param, Class<T> clazz, SerializerProvider provider) throws SerializerException {
        ObjectSerializer serializer = getType(provider);
        return serializer.deserialize(param, clazz);
    }

    private static ObjectSerializer getType(SerializerProvider provider) {
        ObjectSerializer serializer = SpiServiceContext.getInstance().getType(ObjectSerializer.class, provider.getValue());
        if (serializer == null) {
            throw new SerializerException("not found serializer provider: " + provider.getValue());
        }
        return serializer;
    }


    private static Map<String, ObjectSerializer> getAllType() {
        Map<String, ObjectSerializer> allType = SpiServiceContext.getInstance().getAllType(ObjectSerializer.class);
        if (allType == null || allType.isEmpty()) {
            throw new SerializerException("not found serializer providers");
        }
        return allType;
    }
}
