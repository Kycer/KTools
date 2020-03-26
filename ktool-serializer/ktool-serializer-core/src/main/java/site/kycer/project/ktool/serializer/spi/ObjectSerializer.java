package site.kycer.project.ktool.serializer.spi;

import site.kycer.project.ktool.serializer.exceptions.SerializerException;

/**
 * ObjectSerializer
 *
 * @author Kycer
 * @version 1.0.0
 * @date 2020-03-24
 */
public interface ObjectSerializer {

    /**
     * Serialize byte [ ].
     *
     * @param obj the obj
     * @return the byte [ ]
     * @throws SerializerException the serializer exception
     */
    <T> byte[] serialize(T obj) throws SerializerException;


    /**
     * De serialize t.
     *
     * @param <T>   the type parameter
     * @param param the param
     * @param clazz the clazz
     * @return the t
     * @throws SerializerException the serializer exception
     */
    <T> T deserialize(byte[] param, Class<T> clazz) throws SerializerException;
}
