package site.kycer.project.ktool.http.request;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import site.kycer.project.ktool.http.enums.MediaTypeEnum;

/**
 * 文本类型请求
 *
 * @author Kycer
 * @version 1.0
 * @date 2019-09-18
 */
public class TextRequest extends AbstractRequest<TextRequest> {

    public TextRequest(OkHttpClient.Builder okHttpClient, String url) {
        super(okHttpClient, url);
    }

    /**
     * mediaType
     */
    private MediaType mediaType;

    /**
     * 请求内容
     */
    private String content;

    @Override
    Request.Builder getRequestBuilder() {
        RequestBody body = RequestBody.create(content, mediaType);
        return new Request.Builder()
                .url(this.getUrl())
                .post(body);
    }

    /**
     * post 提交 json
     *
     * @param json json字符串
     * @return {@link TextRequest}
     */
    public TextRequest json(String json) {
        this.mediaType = MediaTypeEnum.JSON.getType();
        this.content = json;
        return this;
    }

    /**
     * post 提交 xml
     *
     * @param xml xml字符串
     * @return {@link TextRequest}
     */
    public TextRequest xml(String xml) {
        this.mediaType = MediaTypeEnum.XML.getType();
        this.content = xml;
        return this;
    }

    /**
     * post 提交 html
     *
     * @param html html字符串
     * @return {@link TextRequest}
     */
    public TextRequest html(String html) {
        this.mediaType = MediaTypeEnum.HTML.getType();
        this.content = html;
        return this;
    }
}
