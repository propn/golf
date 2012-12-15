package com.golf.utils.json.parser;

import java.util.HashMap;
import java.util.Map;

import com.golf.utils.json.Parser;
import com.golf.utils.json.support.JsonStringReader;
import com.golf.utils.json.support.SafeSimpleDateFormat;

public class DateParser implements Parser {

    private static final Map<String, SafeSimpleDateFormat> SDF_MAP = new HashMap<String, SafeSimpleDateFormat>();

    @Override
    public Object convertTo(JsonStringReader reader, Class<?> clazz, String datePattern) {
        SafeSimpleDateFormat sdf = SDF_MAP.get(datePattern);
        if (null == sdf) {
            sdf = new SafeSimpleDateFormat(datePattern);
            SDF_MAP.put(datePattern, sdf);
        }
        return sdf.parse(reader.readString());
    }

}
