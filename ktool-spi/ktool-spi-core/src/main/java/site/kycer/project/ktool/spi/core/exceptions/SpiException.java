package site.kycer.project.ktool.spi.core.exceptions;

/**
 * SpiException
 *
 * @author Kycer
 * @version 1.0.0
 * @date 2020-03-26
 */
public class SpiException extends RuntimeException {
    public SpiException() {
        super();
    }

    public SpiException(String message) {
        super(message);
    }

    public SpiException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpiException(Throwable cause) {
        super(cause);
    }
}
