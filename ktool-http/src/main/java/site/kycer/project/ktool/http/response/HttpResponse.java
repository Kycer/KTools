package site.kycer.project.ktool.http.response;

import okhttp3.Headers;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

/**
 * 分装响应
 *
 * @author Kycer
 * @version 1.0
 * @date 2019-09-18
 */
public class HttpResponse {

    /**
     * 请求状态码
     */
    private Integer code;

    /**
     * 请求消息
     */
    private String message;

    /**
     * 所有header头
     */
    private Headers headers;

    /**
     * body
     */
    private String body;


    public HttpResponse(Response response) throws IOException {
        this.code = response.code();
        this.message = response.message();
        this.headers = response.headers();
        ResponseBody body = response.body();
        this.body = Objects.isNull(body) ? null : body.string();
    }

    public String bodyOrThrow() {
        return Optional.ofNullable(this.body).orElseThrow(NullPointerException::new);
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Headers getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }
}
