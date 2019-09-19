package site.kycer.project.ktool.http.response;

import okhttp3.Headers;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;

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
    private ResponseBody body;

    /**
     * 主体字符串内容
     */
    private String bodyString;


    public HttpResponse(Response response) throws IOException {
        this.code = response.code();
        this.message = response.message();
        this.headers = response.headers();
        this.body = response.body();
        this.bodyString = this.body != null ? this.body.string() : null;
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

    public ResponseBody getBody() {
        return body;
    }

    public String getBodyString() {
        return bodyString;
    }
}
