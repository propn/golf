package com.golf.utils;

abstract public class VerifyUtils {

    public static boolean isNotEmpty(Long o) {
        return o != null && StringUtils.hasText(o.toString());
    }

    public static boolean isNotEmpty(Integer o) {
        return o != null && StringUtils.hasText(o.toString());
    }

    public static boolean isNotEmpty(String o) {
        return StringUtils.hasText(o);
    }

    public static boolean isEmpty(Long o) {
        return !isNotEmpty(o);
    }

    public static boolean isEmpty(Integer o) {
        return !isNotEmpty(o);
    }

    public static boolean isEmpty(String o) {
        return !isNotEmpty(o);
    }
}
