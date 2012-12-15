package com.golf.utils.json.serializer;

import java.io.IOException;

import com.golf.utils.json.support.JsonStringWriter;

public class ShortArraySerializer extends SimpleArraySerializer {

    public ShortArraySerializer(boolean primitive) {
        super(primitive);
    }

    @Override
    public void convertTo(JsonStringWriter writer, Object obj, String datePattern) throws IOException {
        if (primitive) {
            writer.writeShortArray((short[]) obj);
        } else {
            writer.writeShortArray((Short[]) obj);
        }

    }

}
