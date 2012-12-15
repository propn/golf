package com.golf.utils.json;

import java.io.IOException;

import com.golf.utils.json.parser.ParserStateMachine;
import com.golf.utils.json.serializer.SerialStateMachine;
import com.golf.utils.json.support.JsonStringReader;
import com.golf.utils.json.support.JsonStringWriter;

public abstract class Json {

    private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String toJson(Object obj) {
        String ret = null;
        JsonStringWriter writer = new JsonStringWriter();
        try {
            SerialStateMachine.toJson(obj, writer, DEFAULT_DATE_PATTERN);
            ret = writer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }
        return ret;
    }

    /**
     * 
     * @param obj
     * @param datePattern
     * @return
     */
    public static String toJson(Object obj, String datePattern) {
        String ret = null;
        JsonStringWriter writer = new JsonStringWriter();
        try {
            SerialStateMachine.toJson(obj, writer, datePattern);
            ret = writer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    public static <T> T toObject(String json, Class<T> clazz) {
        JsonStringReader reader = new JsonStringReader(json);
        return (T) ParserStateMachine.toObject(reader, clazz, DEFAULT_DATE_PATTERN);
    }

    @SuppressWarnings("unchecked")
    public static <T> T toObject(String json, Class<T> clazz, String datePattern) {
        JsonStringReader reader = new JsonStringReader(json);
        return (T) ParserStateMachine.toObject(reader, clazz, datePattern);
    }
}
