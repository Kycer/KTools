package site.kycer.project.ktool.basic.core;

import java.util.UUID;

/**
 * uuid 工具类
 *
 * @author Kycer
 * @version 1.0
 * @date 2019-09-20
 */
public class UUIDUtils {

    private UUIDUtils() {
    }


    /**
     * 生成UUID
     *
     * @return UUID
     */
    public static String generator() {
        return UUID.randomUUID().toString();
    }

    /**
     * 生成没有连接线的uuid
     *
     * @return UUID
     */
    public static String generatorUnLine() {
        return generator().replace("-", "");
    }
}
