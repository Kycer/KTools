package site.kycer.project.ktool.basic.core;

import java.util.UUID;

/**
 * uuid 工具类
 *
 * @author Kycer
 * @version 1.0
 * @date 2019-09-20
 */
public class IDUtils {

    private IDUtils() {
    }


    /**
     * 生成UUID
     *
     * @return UUID
     */
    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 生成简单 UUID
     *
     * @return UUID
     */
    public static String simpleUUID() {
        return randomUUID().replace("-", "");
    }
}
