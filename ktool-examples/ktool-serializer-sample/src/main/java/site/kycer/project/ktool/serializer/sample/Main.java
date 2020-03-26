package site.kycer.project.ktool.serializer.sample;

import site.kycer.project.ktool.serializer.SerializerHelper;
import site.kycer.project.ktool.serializer.enums.SerializerProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Kycer
 * @version 1.0.0
 * @date 2020-03-24
 */
public class Main {

    public static void main(String[] args) {
        Person person = new Person();
        person.setName("Kycer");
        person.setAge(25);
        person.setSex("男");
        List<String> list = new ArrayList<>();
        list.add("唱");
        list.add("跳");
        list.add("Rap");
        person.setLike(list);

        byte[] serialize = SerializerHelper.serialize(person, SerializerProvider.KRYO);
        System.out.println(Arrays.toString(serialize));
        Person deserialize = SerializerHelper.deserialize(serialize, Person.class, SerializerProvider.KRYO);
        System.out.println(deserialize);
    }
}
