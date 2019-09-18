package site.kycer.project.ktool.http.request;

import okhttp3.RequestBody;

/**
 * 文件参数包装
 *
 * @author Kycer
 * @version 1.0
 * @date 2019-09-18
 */
public class FileWrapper {

    /**
     * 文件key
     */
    private String key;
    /**
     * 生成 RequestBody
     */
    private RequestBody requestBody;
    /**
     * 文件名
     */
    private String filename;

    public FileWrapper(String key, RequestBody requestBody, String filename) {
        this.key = key;
        this.requestBody = requestBody;
        this.filename = filename;
    }

    public String getKey() {
        return key;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public String getFilename() {
        return filename;
    }

}
