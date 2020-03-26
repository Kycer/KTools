package site.kycer.project.ktool.serializer.spi;

import site.kycer.project.ktool.serializer.exceptions.SerializerException;
import site.kycer.project.ktool.spi.annotations.SPI;
import site.kycer.project.ktool.spi.annotations.SPIValue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * JavaSerializer
 *
 * @author Kycer
 * @version 1.0.0
 * @date 2020-03-24
 */
@SPI(ObjectSerializer.class)
@SPIValue("jdk")
public class JdkSerializer implements ObjectSerializer {

    @Override
    public byte[] serialize(final Object obj) throws SerializerException {
        try (ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream outputStream = new ObjectOutputStream(arrayOutputStream)) {
            outputStream.writeObject(obj);
            outputStream.flush();
            return arrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new SerializerException("java serialize error " + e.getMessage());
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T deserialize(final byte[] param, final Class<T> clazz) throws SerializerException {
        try (ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(param);
             ObjectInputStream input = new ObjectInputStream(arrayInputStream)) {
            return (T) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new SerializerException("java deSerialize error " + e.getMessage());
        }
    }
}
