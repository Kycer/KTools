package site.kycer.project.ktool.http.request;


import okhttp3.*;
import site.kycer.project.ktool.basic.core.CollectionUtils;
import site.kycer.project.ktool.basic.core.StringUtils;
import site.kycer.project.ktool.basic.io.IOUtils;
import site.kycer.project.ktool.http.enums.MediaTypeEnum;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * post请求创造器
 *
 * @author Kycer
 * @version 1.0
 * @date 2019-09-18
 */
@SuppressWarnings({"WeakerAccess"})
public class PostRequest extends AbstractRequest<PostRequest> {

    private boolean isMultipart;
    private Map<String, List<String>> formParams;
    private Map<String, List<FileWrapper>> fileParams;

    public PostRequest(OkHttpClient.Builder okHttpClient, String url) {
        super(okHttpClient, url);
        this.formParams = new LinkedHashMap<>();
        this.fileParams = new LinkedHashMap<>();
    }

    /**
     * 设置是否带有文件
     * true: 文件参数类型提交
     *
     * @param isMultipart 参数是否带有文件
     * @return {@linkplain PostRequest}
     */
    public PostRequest isMultipart(boolean isMultipart) {
        this.isMultipart = isMultipart;
        return this;
    }

    /**
     * 添加参数 不替换重复key
     *
     * @param key   字段名
     * @param value 字段值
     * @return {@linkplain PostRequest}
     */
    public PostRequest param(String key, String value) {
        return this.param(key, value, false);
    }

    /**
     * 添加参数 可指定是否替换重复key
     *
     * @param key     字段名
     * @param value   字段值
     * @param replace 是否替换
     * @return {@linkplain PostRequest}
     */
    public PostRequest param(String key, String value, boolean replace) {
        if (StringUtils.isEmpty(key)) {
            return this;
        }
        if (!replace && value == null) {
            return this;
        }
        List<String> values = this.formParams.computeIfAbsent(key, k -> new LinkedList<>());
        if (replace) {
            values.clear();
        }
        values.add(value);
        return this;
    }

    /**
     * 添加文件参数
     *
     * @param key      字段名
     * @param file     文件
     * @param filename 文件名
     * @return {@linkplain PostRequest}
     */
    public PostRequest param(String key, File file, String filename) {
        if (StringUtils.isEmpty(key) || Objects.isNull(file) || StringUtils.isEmpty(filename)) {
            return this;
        }
        List<FileWrapper> fileWrapperList = this.fileParams.computeIfAbsent(key, k -> new LinkedList<>());
        fileWrapperList.add(new FileWrapper(key, RequestBody.create(file, MediaTypeEnum.STREAM.getType()), filename));
        return this;
    }


    /**
     * 添加文件参数
     *
     * @param key         字段名
     * @param inputStream 文件流
     * @param streamName  文件名
     * @return {@linkplain PostRequest}
     * @throws IOException io异常
     */
    public PostRequest param(String key, InputStream inputStream, String streamName) throws IOException {
        if (StringUtils.isEmpty(key) || Objects.isNull(inputStream) || StringUtils.isEmpty(streamName)) {
            return this;
        }
        List<FileWrapper> fileWrapperList = this.fileParams.computeIfAbsent(key, k -> new LinkedList<>());
        RequestBody requestBody = RequestBody.create(IOUtils.toByteArray(inputStream), MediaTypeEnum.STREAM.getType());
        fileWrapperList.add(new FileWrapper(key, requestBody, streamName));
        return this;
    }


    @Override
    Request.Builder getRequestBuilder() {
        if (Objects.nonNull(this.fileParams) && !this.fileParams.isEmpty()) {
            this.isMultipart(true);
        }
        RequestBody body = this.generateRequestBody();
        return new Request.Builder()
                .url(this.getUrl())
                .post(body);
    }

    /**
     * 创建 RequestBody
     *
     * @return {@linkplain RequestBody}
     */
    private RequestBody generateRequestBody() {
        if (this.isMultipart) {
            // 文件参数部分
            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            if (CollectionUtils.isNotEmpty(this.fileParams)) {
                this.fileParams.forEach((key, values) -> {
                    if (CollectionUtils.isNotEmpty(values)) {
                        for (FileWrapper wrapper : values) {
                            builder.addFormDataPart(key, wrapper.getFilename(), wrapper.getRequestBody());
                        }
                    }
                });
            }
            // 普通参数部分
            if (CollectionUtils.isNotEmpty(this.formParams)) {
                this.formParams.forEach((key, values) -> {
                    if (CollectionUtils.isNotEmpty(values)) {
                        for (String value : values) {
                            builder.addFormDataPart(key, value);
                        }
                    }
                });
            }
            return builder.build();
        } else {
            FormBody.Builder builder = new FormBody.Builder();
            if (CollectionUtils.isNotEmpty(this.formParams)) {
                this.formParams.forEach((key, values) -> {
                    if (CollectionUtils.isNotEmpty(values)) {
                        for (String value : values) {
                            builder.add(key, value);
                        }
                    }
                });
            }
            return builder.build();
        }
    }

}
