package com.golf.utils.json.parser;

import com.golf.utils.Base64;
import com.golf.utils.json.Parser;
import com.golf.utils.json.support.JsonStringReader;

public class ByteArrayParser implements Parser {

    @Override
    public Object convertTo(JsonStringReader reader, Class<?> clazz) {
        return Base64.decodeFast(reader.readChars());
    }

}
