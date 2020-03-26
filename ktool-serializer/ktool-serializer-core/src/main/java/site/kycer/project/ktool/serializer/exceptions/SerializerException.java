package site.kycer.project.ktool.serializer.exceptions;

/**
 * SerializerException
 *
 * @author Kycer
 * @version 1.0.0
 * @date 2020-03-24
 */
public class SerializerException extends RuntimeException {

    public SerializerException() {
        super();
    }

    public SerializerException(String message) {
        super(message);
    }

    public SerializerException(String message, Throwable cause) {
        super(message, cause);
    }

    public SerializerException(Throwable cause) {
        super(cause);
    }
}
