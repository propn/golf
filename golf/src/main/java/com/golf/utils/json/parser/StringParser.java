package com.golf.utils.json.parser;

import com.golf.utils.json.Parser;
import com.golf.utils.json.support.JsonStringReader;

public class StringParser implements Parser {

    @Override
    public Object convertTo(JsonStringReader reader, Class<?> clazz) {
        return reader.readString();
    }

}
