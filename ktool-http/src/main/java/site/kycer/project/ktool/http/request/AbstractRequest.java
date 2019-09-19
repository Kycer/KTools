package site.kycer.project.ktool.http.request;


import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import site.kycer.project.ktool.basic.core.CollectionUtils;
import site.kycer.project.ktool.basic.core.StringUtils;
import site.kycer.project.ktool.http.exception.HttpClientException;
import site.kycer.project.ktool.http.response.HttpResponse;

import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author Kycer
 * @version 1.0
 * @date 2019-09-18
 */
@SuppressWarnings("unchecked")
public abstract class AbstractRequest<T extends AbstractRequest> {

    /**
     * 请求URL
     */
    private String url;

    /**
     * okHttpClient
     */
    private OkHttpClient okHttpClient;

    /**
     * url后面参数
     */
    private Map<String, List<String>> queryParams;

    /**
     * 自定义头
     */
    private Map<String, String> headers;


    public AbstractRequest(OkHttpClient.Builder okHttpClientBuilder, String url) {
        this.okHttpClient = okHttpClientBuilder.build();
        this.url = url;
        this.queryParams = new LinkedHashMap<>();
    }

    /**
     * 添加查询字段 参数将直接拼接在url后面
     *
     * @param key   字段名
     * @param value 字段值
     * @return 返回当前 {@linkplain T} 对象
     */
    public T queryString(String key, String value) {
        this.queryString(key, value, false);
        return (T) this;
    }

    /**
     * 添加查询字段 参数将直接拼接在url后面
     *
     * @param key     字段名
     * @param value   字段值
     * @param replace 是否替换相同key值的value
     * @return 返回当前 {@linkplain T} 对象
     */
    public T queryString(String key, String value, boolean replace) {
        if (StringUtils.isEmpty(key)) {
            return (T) this;
        }
        if (!replace && value == null) {
            return (T) this;
        }
        List<String> values = this.queryParams.computeIfAbsent(key, k -> new LinkedList<>());
        if (replace) {
            values.clear();
        }
        values.add(value);
        return (T) this;
    }

    /**
     * 获取请求URL
     *
     * @return url
     */
    public String getUrl() {
        if (CollectionUtils.isNotEmpty(this.queryParams)) {
            String query = this.queryParams.keySet().stream().map(k -> {
                List<String> values = this.queryParams.get(k);
                if (CollectionUtils.isNotEmpty(values)) {
                    return values.stream().map(v -> k + "=" + v).collect(Collectors.joining("&"));
                }
                return k + "=" + "";
            }).collect(Collectors.joining("&"));

            int index = this.url.indexOf("?");
            this.url += (index != -1) ? "&" : "?";
            this.url += query;
        }
        return this.url;
    }


    /**
     * 添加Header头
     *
     * @param name  头名字
     * @param value 头值
     * @return 返回当前 {@linkplain T} 对象
     */
    public T header(String name, String value) {
        if (StringUtils.isNotEmpty(name) && null != value) {
            if (Objects.isNull(this.headers)) {
                this.headers = new LinkedHashMap<>();
            }
            this.headers.put(name, value);
        }
        return (T) this;
    }

    /**
     * 获取请求 Request
     *
     * @return Request
     */
    abstract Request.Builder getRequestBuilder();


    /**
     * 获取 Request
     *
     * @return {@linkplain Request}
     */
    private Request getRequest() {
        Request.Builder builder = this.getRequestBuilder();
        if (CollectionUtils.isNotEmpty(headers)) {
            headers.forEach(builder::addHeader);
        }
        return builder.build();
    }

    /**
     * 同步执行请求
     *
     * @return {@linkplain HttpResponse}
     */
    public HttpResponse execute() {
        try {

            Response response = this.okHttpClient.newCall(this.getRequest()).execute();
            return new HttpResponse(response);
        } catch (IOException e) {
            throw new HttpClientException(e);
        }
    }

    /**
     * 同步执行并返回String
     *
     * @return 结果字符串
     */
    public String string() {
        try {
            ResponseBody body = this.okHttpClient.newCall(this.getRequest()).execute().body();
            return body != null ? body.string() : null;
        } catch (IOException e) {
            throw new HttpClientException(e);
        }
    }


    /**
     * 异步执行请求
     *
     * @param successCallback 成功回调
     * @param failCallback    失败回调
     */
    public void asyncExecute(Consumer<HttpResponse> successCallback, Consumer<Call> failCallback) {
        this.okHttpClient.newCall(this.getRequest()).enqueue(new Callback() {

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                ResponseBody body = response.body();
                successCallback.accept(new HttpResponse(response));
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                if (!call.isCanceled()) {
                    call.cancel();
                }
                failCallback.accept(call);
            }
        });
    }
}
