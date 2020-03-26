package site.kycer.project.ktool.serializer.spi;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.BeanSerializer;
import site.kycer.project.ktool.serializer.exceptions.SerializerException;
import site.kycer.project.ktool.spi.annotations.SPI;
import site.kycer.project.ktool.spi.annotations.SPIValue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * KryoSerializer
 *
 * @author Kycer
 * @version 1.0.0
 * @date 2020-03-24
 */
@SPI(ObjectSerializer.class)
@SPIValue("kryo")
public class KryoSerializer implements ObjectSerializer {


    /**
     * Kryo obj
     */
    final ThreadLocal<Kryo> kryoLocal = ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();
        kryo.setReferences(false);
        kryo.setRegistrationRequired(false);
        return kryo;
    });

    @Override
    public byte[] serialize(final Object obj) throws SerializerException {
        byte[] bytes;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); Output output = new Output(outputStream)) {
            Kryo kryo = kryoLocal.get();
            Class<?> type = obj.getClass();
            kryo.register(type, new BeanSerializer<>(kryo, type));
            kryo.writeObject(output, obj);
            bytes = output.toBytes();
            output.flush();
        } catch (IOException ex) {
            throw new SerializerException("kryo serialize error" + ex.getMessage());
        }
        return bytes;
    }

    @Override
    public <T> T deserialize(final byte[] param, final Class<T> clazz) throws SerializerException {
        T object;
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(param)) {
            Kryo kryo = kryoLocal.get();
            kryo.register(clazz, new BeanSerializer<>(kryo, clazz));
            Input input = new Input(inputStream);
            object = kryo.readObject(input, clazz);
            input.close();
        } catch (IOException e) {
            throw new SerializerException("kryo deSerialize error" + e.getMessage());
        }
        return object;
    }
}
