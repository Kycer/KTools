package site.kycer.project.ktool.basic.core;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * 时间工具类
 *
 * @author Kycer
 * @version 1.0
 * @date 2019-09-20
 */
public class DateUtils {

    private DateUtils() {
    }

    /**
     * 设置 ZoneId
     *
     * @return {@link ZoneId}
     */
    private static ZoneId setZoneId() {
        return ZoneId.systemDefault();
    }

    /**
     * 获取当前clock
     *
     * @return {@link Clock}
     */
    public static Clock getClock() {
        return Clock.system(setZoneId());
    }


    /**
     * 获取当前LocalDate
     *
     * @return {@link LocalDate}
     */
    public static LocalDate getNowDate() {
        return LocalDate.now(getClock());
    }

    /**
     * 获取当前LocalDateTime
     *
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime getNowDateTime() {
        return LocalDateTime.now(getClock());
    }

    /**
     * 获取当前时间戳 毫秒
     *
     * @return 时间戳
     */
    public static long getMillis() {
        return getClock().millis();
    }

    /**
     * 获取当前时间搓 秒
     *
     * @return 时间戳
     */
    public static long getMillisSecond() {
        return getClock().millis() / 1000;
    }

}
