package site.kycer.project.ktool.serializer.enums;

/**
 * SerializerProvider
 *
 * @author Kycer
 * @version 1.0.0
 * @date 2020-03-26
 */
public enum SerializerProvider {
    /**
     * Java Serializer
     */
    JDK("jdk"),
    /**
     * kryo Serializer
     */
    KRYO("kryo"),
    /**
     * protostuff Serializer
     */
    PROTOSTUFF("protostuff"),
    ;

    private String value;

    SerializerProvider(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
