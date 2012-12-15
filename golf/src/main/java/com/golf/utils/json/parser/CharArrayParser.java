package com.golf.utils.json.parser;

import com.golf.utils.json.Parser;
import com.golf.utils.json.support.JsonStringReader;

public class CharArrayParser implements Parser {

    @Override
    public Object convertTo(JsonStringReader reader, Class<?> clazz,String datePattern) {
        String ret = reader.readString();
        return ret != null ? ret.toCharArray() : null;
    }

}
