package com.golf.utils.json.parser;

import com.golf.utils.json.Parser;
import com.golf.utils.json.support.JsonStringReader;

public class IntParser implements Parser {

    @Override
    public Object convertTo(JsonStringReader reader, Class<?> clazz,String datePattern) {
        return reader.readInt();
    }

}
