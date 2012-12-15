package com.golf.utils.json.parser;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import com.golf.utils.json.Parser;
import com.golf.utils.json.exception.JsonException;
import com.golf.utils.json.support.JsonStringReader;
import com.golf.utils.json.support.ParserMetaInfo;

public class ArrayParser implements Parser {

    private ParserMetaInfo elementMetaInfo;

    public ArrayParser(Class<?> clazz) {
        elementMetaInfo = new ParserMetaInfo();
        elementMetaInfo.setType(clazz);
        elementMetaInfo.setParser(ParserStateMachine.getParser(clazz));
    }

    @Override
    public Object convertTo(JsonStringReader reader, Class<?> clazz,String datePattern) {
        reader.mark();
        if (reader.isNull())
            return null;
        else
            reader.reset();

        if (!reader.isArray())
            throw new JsonException("json string is not array format");

        // 判断空数组
        reader.mark();
        char c0 = reader.readAndSkipBlank();
        if (c0 == ']')
            return Array.newInstance(clazz, 0);
        else
            reader.reset();

        List<Object> obj = new ArrayList<Object>();

        for (;;) {
            obj.add(elementMetaInfo.getValue(reader,datePattern));

            char ch = reader.readAndSkipBlank();
            if (ch == ']')
                return copyOf(obj);

            if (ch != ',')
                throw new JsonException("missing ','");
        }
    }

    public Object copyOf(List<Object> list) {
        Object ret = Array.newInstance(elementMetaInfo.getType(), list.size());
        for (int i = 0; i < list.size(); i++) {
            Array.set(ret, i, list.get(i));
        }
        return ret;
    }

}
