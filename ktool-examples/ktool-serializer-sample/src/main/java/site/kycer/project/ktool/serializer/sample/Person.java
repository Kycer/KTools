package site.kycer.project.ktool.serializer.sample;

import java.io.Serializable;
import java.util.List;

/**
 * @author Kycer
 * @version 1.0.0
 * @date 2020-03-24
 */
public class Person implements Serializable {

    private int age;
    private String name;
    private String sex;

    private List<String> like;

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = -5809782578272943999L;

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public List<String> getLike() {
        return like;
    }

    public void setLike(List<String> like) {
        this.like = like;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", like=" + like +
                '}';
    }
}
