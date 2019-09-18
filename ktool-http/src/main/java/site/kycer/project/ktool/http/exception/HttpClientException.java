package site.kycer.project.ktool.http.exception;

/**
 * 客户端异常
 *
 * @author Kycer
 * @version 1.0
 * @date 2019-09-18
 */
public class HttpClientException extends RuntimeException {
    public HttpClientException() {
        super();
    }

    public HttpClientException(String s) {
        super(s);
    }

    public HttpClientException(String s, Throwable throwable) {
        super(s, throwable);
    }

    protected HttpClientException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
