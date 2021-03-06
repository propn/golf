package com.golf.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import com.golf.cache.imp.PartitionCache;

/**
 * 反射工具类
 * 
 * @author Thunder.Hsu
 * 
 */
public class RefUtils {

    private static PartitionCache<Field> fieldsCache = new PartitionCache<Field>();
    private static PartitionCache<Method> methodsCache = new PartitionCache<Method>();

    private RefUtils() {
    }

    public static Object getFieldValue(Object obj, String fieldName) throws Exception {
        return getFieldValue(obj, getField(obj.getClass(), fieldName));
    }

    public static void setFieldValue(Object obj, String fieldName, Object value) throws Exception {
        setFieldValue(obj, getField(obj.getClass(), fieldName), value);
    }

    public static Map<String, Field> getFields(Class<? extends Object> clz) {
        String className = clz.getName();
        if (fieldsCache.get(className).isEmpty()) {
            cacheFields(clz);
        }
        return fieldsCache.get(className);
    }

    public static Map<String, Method> getMethods(Class<?> clz) {
        String className = clz.getName();
        if (methodsCache.get(className).isEmpty()) {
            cacheMethods(clz);
        }
        return methodsCache.get(className);
    }

    public static Field getField(Class<? extends Object> clz, String fieldName) throws Exception {
        Map<String, Field> fields = getFields(clz);
        Field o = fields.get(fieldName);
        if (null == o) {
            throw new Exception(clz.getName() + "不存在属性:" + fieldName);
        }
        return o;
    }

    public static Method getMethod(Class<?> clz, String methodName) throws Exception {
        Map<String, Method> methods = getMethods(clz);
        Method o = methods.get(methodName);
        if (null == o) {
            throw new Exception(clz.getName() + "不存在方法:" + methodName);
        }
        return o;
    }

    private static void cacheFields(Class<? extends Object> clz) {
        Class<?> superClass = clz.getSuperclass();
        while (true) {
            if (superClass != null) {
                Field[] superFields = superClass.getDeclaredFields();
                if (superFields != null && superFields.length > 0) {
                    for (Field field : superFields) {
                        field.setAccessible(true);
                        fieldsCache.put(clz.getName(), field.getName(), field);
                    }
                }
                superClass = superClass.getSuperclass();
            } else {
                break;
            }
        }
        Field[] objFields = clz.getDeclaredFields();
        if (objFields != null && objFields.length > 0) {
            for (Field field : objFields) {
                field.setAccessible(true);
                fieldsCache.put(clz.getName(), field.getName(), field);
            }
        }
    }

    private static void cacheMethods(Class<?> clz) {
        Class<?> superClass = clz.getSuperclass();
        while (true) {
            if (superClass != null && !superClass.equals(Object.class)) {
                Method[] superMethods = superClass.getDeclaredMethods();
                if (superMethods != null && superMethods.length > 0) {
                    for (Method method : superMethods) {
                        method.setAccessible(true);
                        methodsCache.put(clz.getName(), method.getName(), method);
                    }
                }
                superClass = superClass.getSuperclass();
            } else {
                break;
            }
        }
        Method[] objMethods = clz.getDeclaredMethods();
        if (objMethods != null && objMethods.length > 0) {
            for (Method method : objMethods) {
                method.setAccessible(true);
                methodsCache.put(clz.getName(), method.getName(), method);
            }
        }
    }

    private static Object getFieldValue(Object obj, Field field) throws Exception {
        return field.get(obj);
    }

    /**
     * 类型转换
     * 
     * @param obj
     * @param field
     * @param value
     * @throws Exception
     */
    private static void setFieldValue(Object obj, Field field, Object value) throws Exception {
        field.set(obj, ConvertUtils.convert(value, field.getType()));
    }

}
