package com.golf.utils.json.parser;

import java.lang.reflect.Type;
import java.util.Collection;

import com.golf.utils.json.exception.JsonException;
import com.golf.utils.json.support.JsonStringReader;

public class CollectionParser extends ComplexTypeParser {

    public CollectionParser(Type elementType) {
        super(elementType);
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Object convertTo(JsonStringReader reader, Class<?> clazz, String datePattern) {
        reader.mark();
        if (reader.isNull())
            return null;
        else
            reader.reset();

        if (!reader.isArray())
            throw new JsonException("json string is not array format");

        Collection obj = null;
        try {
            obj = (Collection) clazz.newInstance();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        // 判断空数组
        reader.mark();
        char c0 = reader.readAndSkipBlank();
        if (c0 == ']')
            return obj;
        else
            reader.reset();

        for (;;) {
            obj.add(elementMetaInfo.getValue(reader, datePattern));

            char ch = reader.readAndSkipBlank();
            if (ch == ']')
                return obj;

            if (ch != ',')
                throw new JsonException("missing ','");
        }
    }

}
