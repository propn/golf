package com.golf.utils.json;

import com.golf.utils.json.support.JsonStringReader;

public interface Parser {
    Object convertTo(JsonStringReader reader, Class<?> clazz, String datePattern);
}
