/**
 * 
 */
package com.golf.tools;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.golf.mvc.GolfFilter;

/**
 * @author Thunder.Hsu
 * 
 */
public class ConvertUtils {

    private static final Logger log = LoggerFactory.getLogger(GolfFilter.class);

    public static Object convert(Object obj, Class dist) throws Exception {
        if (null == obj) {
            return null;
        }
        if (obj.getClass().equals(dist)) {
            return obj;
        }
        log.debug("Convert Object Type: " + obj.getClass().getName() + " " + dist.getName());
        return convert(obj.getClass(), obj, dist);
    }

    private static Object convert(Class<? extends Object> src, Object obj, Class dist) throws Exception {
        if (obj instanceof String) {
            return convert(dist, (String) obj);
        }

        if (obj instanceof BigDecimal) {
            return convert(dist, (BigDecimal) obj);
        }
        if (obj instanceof Vector) {
            return convert(dist, (Vector) obj);
        }
        if (obj instanceof Integer) {
            return convert(dist, (Integer) obj);
        }
        throw new Exception(obj.getClass() + " 转 " + dist + "未实现!");
    }

    // String
    private static Object convert(Class distClass, String obj) throws Exception {

        if (distClass.equals(long.class)) {
            if (null == obj || "".equals(obj)) {
                return 0;
            }
            return Long.valueOf(obj).longValue();
        }

        if (distClass.equals(int.class)) {
            if (null == obj || "".equals(obj)) {
                return 0;
            }
            return Integer.valueOf(obj).intValue();
        }

        if (distClass.equals(double.class)) {
            if (null == obj || "".equals(obj)) {
                return 0;
            }
            return Double.valueOf(obj).doubleValue();
        }
        throw new Exception(obj.getClass() + " 转 " + distClass + "未实现!");
    }

    // Integer
    private static Object convert(Class distClass, Integer obj) throws Exception {

        if (distClass.equals(int.class)) {
            return obj.intValue();
        }
        if (distClass == String.class) {
            return String.valueOf(obj);
        }
        throw new Exception(obj.getClass() + " 转 " + distClass + "未实现!");
    }

    // BigDecimal
    private static Object convert(Class distClass, BigDecimal obj) throws Exception {
        if (distClass.equals(int.class)) {
            return obj.intValue();
        }

        if (distClass.equals(long.class)) {
            return obj.longValue();
        }

        if (distClass == String.class) {
            return String.valueOf(obj);
        }
        throw new Exception(obj.getClass() + " 转 " + distClass + "未实现!");
    }

    // Vector
    private static Object convert(Class distClass, Vector v) throws Exception {

        if (distClass == String.class) {
            StringBuffer sb = new StringBuffer();
            for (Iterator it = v.iterator(); it.hasNext();) {
                sb.append(convert(it.next(), String.class)).append(",");
            }
            return sb.substring(0, sb.length() - 1).toString();
        }

        if (distClass == int.class) {
            if (v.size() == 1) {
                return Integer.valueOf((String) v.firstElement());
            }
        }
        throw new Exception(v.getClass() + " 转 " + distClass + "未实现!");
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

    }
}
