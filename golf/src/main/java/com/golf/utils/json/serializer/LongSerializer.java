package com.golf.utils.json.serializer;

import com.golf.utils.json.Serializer;
import com.golf.utils.json.support.JsonStringWriter;

public class LongSerializer implements Serializer {

    @Override
    public void convertTo(JsonStringWriter writer, Object obj, String datePattern) {
        writer.writeLong((Long) obj);
    }

}
