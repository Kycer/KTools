package site.kycer.project.ktool.http.enums;

import okhttp3.MediaType;

/**
 * MediaType 枚举
 *
 * @author Kycer
 * @version 1.0
 * @date 2019-09-18
 */
public enum MediaTypeEnum {

    /**
     * JSON
     */
    JSON(MediaType.parse("application/json;charset=utf-8")),

    /**
     * XML
     */
    XML(MediaType.parse("text/xml;charset=utf-8")),

    /**
     * STREAM
     */
    STREAM(MediaType.parse("application/octet-stream")),

    /**
     * HTML
     */
    HTML(MediaType.parse("application/html;charset=utf-8"));

    private MediaType type;

    MediaTypeEnum(MediaType type) {
        this.type = type;
    }

    public MediaType getType() {
        return type;
    }
}
