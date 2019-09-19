package site.kycer.project.ktool.basic.core;

import java.util.Arrays;

/**
 * 数组工具类
 *
 * @author Kycer
 * @version 1.0
 * @date 2019-09-19
 */
public final class ArrayUtils {
    private ArrayUtils() {
    }

    /**
     * 数组转化为string输出
     *
     * @param array Array
     * @return 输出后字符串
     */
    public static <T> String toString(T[] array) {
        return Arrays.toString(array);
    }

    /**
     * 判断数组不为空
     *
     * @param array 数组
     * @param <T>   泛型
     * @return {@code true} 数组不为空
     */
    public static <T> boolean isNotEmpty(T[] array) {
        return (array != null && array.length != 0);
    }

    /**
     * 判断数组为空
     *
     * @param array 数组
     * @param <T>   泛型
     * @return {@code true} 数组为空
     */
    public static <T> boolean isEmpty(T[] array) {
        return (array == null || array.length == 0);
    }


    /**
     * 判断char数组为空
     *
     * @param array char数组
     * @return {@code true} 数组为空
     */
    public static boolean isEmpty(char[] array) {
        return (array == null || array.length == 0);
    }

    /**
     * 判断long数组为空
     *
     * @param array long数组
     * @return {@code true} 数组为空
     */
    public static boolean isEmpty(long[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 判断int数组为空
     *
     * @param array int数组
     * @return {@code true} 数组为空
     */
    public static boolean isEmpty(int[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 判断short数组为空
     *
     * @param array short数组
     * @return {@code true} 数组为空
     */
    public static boolean isEmpty(short[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 判断byte数组为空
     *
     * @param array byte数组
     * @return {@code true} 数组为空
     */
    public static boolean isEmpty(byte[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 判断double数组为空
     *
     * @param array double数组
     * @return {@code true} 数组为空
     */
    public static boolean isEmpty(double[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 判断float数组为空
     *
     * @param array float数组
     * @return {@code true} 数组为空
     */
    public static boolean isEmpty(float[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 判断boolean数组为空
     *
     * @param array boolean数组
     * @return {@code true} 数组为空
     */
    public static boolean isEmpty(boolean[] array) {
        return array == null || array.length == 0;
    }

}
