package site.kycer.project.ktool.basic.core;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author Kycer
 * @version 1.0
 * @date 2019-09-20
 */
public class DateUtils {

    /**
     * Date格式化字符串
     */
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    /**
     * DateTime格式化字符串
     */
    public static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    /**
     * Time格式化字符串
     */
    public static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    private DateUtils() {
    }

    /**
     * 设置 ZoneId
     *
     * @return {@link ZoneId}
     */
    private static ZoneId getZoneId() {
        return ZoneId.systemDefault();
    }

    /**
     * 获取当前clock
     *
     * @return {@link Clock}
     */
    public static Clock getClock() {
        return Clock.system(getZoneId());
    }

    /**
     * 获取当前clock
     *
     * @param zoneId {@link ZoneId}
     * @return {@link Clock}
     */
    public static Clock getClock(ZoneId zoneId) {
        return Clock.system(getZoneId());
    }


    /**
     * 获取当前LocalDate
     *
     * @return {@link LocalDate}
     */
    public static LocalDate getNowDate() {
        return LocalDate.now(getZoneId());
    }

    /**
     * 获取当前LocalDate
     *
     * @param zoneId {@link ZoneId}
     * @return {@link LocalDate}
     */
    public static LocalDate getNowDate(ZoneId zoneId) {
        return LocalDate.now(zoneId);
    }

    /**
     * 获取当前LocalDateTime
     *
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime getNowDateTime() {
        return LocalDateTime.now(getZoneId());
    }

    /**
     * 获取当前LocalDateTime
     *
     * @param zoneId {@link ZoneId}
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime getNowDateTime(ZoneId zoneId) {
        return LocalDateTime.now(zoneId);
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
     * 获取当前时间戳 毫秒
     *
     * @param zoneId {@link ZoneId}
     * @return 时间戳
     */
    public static long getMillis(ZoneId zoneId) {
        return getClock(zoneId).millis();
    }

    /**
     * 获取当前时间搓 秒
     *
     * @return 时间戳
     */
    public static long getMillisSecond() {
        return getClock().millis() / 1000;
    }

    /**
     * 指定时间的一天开始时间
     *
     * @param localDate {@link LocalDate}
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime beginOfDay(LocalDate localDate) {
        return LocalDateTime.of(localDate, LocalTime.MIN);
    }

    /**
     * 指定时间的一天结束时间
     *
     * @param localDate {@link LocalDate}
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime endOfDay(LocalDate localDate) {
        return LocalDateTime.of(localDate, LocalTime.MAX);
    }

    /**
     * 获取某月第一天
     *
     * @param dateTime {@link LocalDateTime}
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime beginOfMonth(LocalDateTime dateTime) {
        return dateTime.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
    }

    /**
     * 获取某月最后一天
     *
     * @param dateTime {@link LocalDateTime}
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime endOfMonth(LocalDateTime dateTime) {
        return dateTime.with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);
    }

    /**
     * 格式化时间
     *
     * @param localDateTime {@link LocalDateTime}
     * @param formatter     {@link DateTimeFormatter}
     * @return 格式化后字符串
     */
    public static String formatDate(LocalDateTime localDateTime, DateTimeFormatter formatter) {
        return formatter.format(localDateTime);
    }

    /**
     * 解析时间
     *
     * @param localDateTime {@link LocalDateTime}
     * @param formatter     {@link DateTimeFormatter}
     * @return 格式化后字符串
     */
    public static LocalDateTime parseDate(String localDateTime, DateTimeFormatter formatter) {
        return LocalDateTime.parse(localDateTime, formatter);
    }

    /**
     * 将 Date 转换成LocalDate
     * atZone()方法返回在指定时区从此Instant生成的ZonedDateTime。
     *
     * @param date
     * @return
     */
    public static LocalDate dateToLocalDate(Date date) {
        return date.toInstant().atZone(getZoneId()).toLocalDate();
    }

    /**
     * Date转换为LocalDateTime
     *
     * @param date {@link Date}
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), getZoneId());
    }

    /**
     * 将LocalDate 转换成 Date
     *
     * @param localDate {@link LocalDate}
     * @return {@link Date}
     */
    public static Date localDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(getZoneId()).toInstant());
    }

    /**
     * LocalDateTime转换为Date
     *
     * @param time {@link LocalDateTime}
     * @return {@link Date}
     */
    public static Date localDateTimeToDate(LocalDateTime time) {
        return Date.from(time.atZone(getZoneId()).toInstant());
    }

    /**
     * 时间1在时间2之后
     *
     * @param localDateTime1 {@link LocalDateTime}
     * @param localDateTime2 {@link LocalDateTime}
     * @return {@code true} 是
     */
    public static boolean isAfter(LocalDateTime localDateTime1, LocalDateTime localDateTime2) {
        return localDateTime1.isAfter(localDateTime2);
    }


    /**
     * 时间1在时间2之后
     *
     * @param localDate1 {@link LocalDate}
     * @param localDate2 {@link LocalDate}
     * @return {@code true} 是
     */
    public static boolean isAfter(LocalDate localDate1, LocalDate localDate2) {
        return localDate1.isAfter(localDate2);
    }

    /**
     * 时间1在时间2之前
     *
     * @param localDateTime1 {@link LocalDateTime}
     * @param localDateTime2 {@link LocalDateTime}
     * @return {@code true} 是
     */
    public static boolean isBefore(LocalDateTime localDateTime1, LocalDateTime localDateTime2) {
        return localDateTime1.isBefore(localDateTime2);
    }


    /**
     * 时间1在时间2之前
     *
     * @param localDate1 {@link LocalDate}
     * @param localDate2 {@link LocalDate}
     * @return {@code true} 是
     */
    public static boolean isBefore(LocalDate localDate1, LocalDate localDate2) {
        return localDate1.isBefore(localDate2);
    }

    /**
     * 计算两个时间之间的毫秒数
     *
     * @param time1 {@link Temporal} 子类
     * @param time2 {@link Temporal} 子类
     * @return 毫秒数
     */
    public static <T extends Temporal> Long betweenMinus(T time1, T time2) {
        return Duration.between(time1, time2).toMillis();
    }

}
