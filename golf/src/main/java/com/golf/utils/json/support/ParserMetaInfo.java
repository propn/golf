package com.golf.utils.json.support;

import java.util.Arrays;

import com.golf.utils.json.Parser;

public class ParserMetaInfo extends MetaInfo {

    private Class<?> type;
    private Parser parser;

    public void invoke(Object obj, JsonStringReader reader, String datePattern) {
        try {
            propertyInvoke.set(obj, getValue(reader, datePattern));
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void setPropertyNameString(String propertyNameString) {
        this.propertyNameString = propertyNameString;
        propertyName = propertyNameString.toCharArray();
    }

    public Object getValue(JsonStringReader reader, String datePattern) {
        return parser.convertTo(reader, type, datePattern);
    }

    public boolean equals(char[] field) {
        return Arrays.equals(propertyName, field);
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public Parser getParser() {
        return parser;
    }

    public void setParser(Parser parser) {
        this.parser = parser;
    }
}
