package com.golf.utils.json.parser;

import com.golf.utils.json.Parser;
import com.golf.utils.json.support.JsonStringReader;
import com.golf.utils.json.support.SafeSimpleDateFormat;

public class DateParser implements Parser {

    @Override
    public Object convertTo(JsonStringReader reader, Class<?> clazz) {
        return SafeSimpleDateFormat.defaultDateFormat.parse(reader.readString());
    }

}
