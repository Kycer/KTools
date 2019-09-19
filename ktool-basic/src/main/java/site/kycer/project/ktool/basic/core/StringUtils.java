package site.kycer.project.ktool.basic.core;

/**
 * 字符串工具类
 *
 * @author Kycer
 * @version 1.0
 * @date 2019-09-18
 */
public class StringUtils {

    private StringUtils() {
    }

    /**
     * 检查字符串是 "" 或 null
     *
     * <pre>
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
     * </pre>
     *
     * @param str 入参字符串
     * @return {@code true} str 是 "" 或 null
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }


    /**
     * 检查字符串不是 "" 且 不为null
     *
     * <pre>
     * StringUtils.isNotEmpty(null)      = false
     * StringUtils.isNotEmpty("")        = false
     * StringUtils.isNotEmpty(" ")       = true
     * StringUtils.isNotEmpty("bob")     = true
     * StringUtils.isNotEmpty("  bob  ") = true
     * </pre>
     *
     * @param str 入参字符串
     * @return {@code true} str 不是"" 且 不为null
     */
    public static boolean isNotEmpty(String str) {
        return !StringUtils.isEmpty(str);
    }


    /**
     * 检查字符串去除空格后是 "" 或 null
     *
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param str 入参字符串
     * @return {@code true} str 是 "" 或 null
     */
    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((!Character.isWhitespace(str.charAt(i)))) {
                return false;
            }
        }
        return true;
    }


    /**
     * 检查字符串去除空格后不是 "" 且 不为null
     *
     * <pre>
     * StringUtils.isNotBlank(null)      = false
     * StringUtils.isNotBlank("")        = false
     * StringUtils.isNotBlank(" ")       = false
     * StringUtils.isNotBlank("bob")     = true
     * StringUtils.isNotBlank("  bob  ") = true
     * </pre>
     *
     * @param str 入参字符串
     * @return {@code true} str 不是"" 且 不为null
     */
    public static boolean isNotBlank(String str) {
        return !StringUtils.isBlank(str);
    }


    /**
     * 比较两个字符串值是否相等
     *
     * @param str1 字符串 1
     * @param str2 字符串 2
     * @return {@code true} 值相等
     */
    public static boolean equals(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equals(str2);
    }

    /**
     * 比较两个字符串值是否相等(忽略大小写)
     *
     * @param str1 字符串 1
     * @param str2 字符串 2
     * @return {@code true} 值相等
     */
    public static boolean equalsIgnoreCase(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equalsIgnoreCase(str2);
    }
}
