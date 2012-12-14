package com.golf.utils.json.serializer;

import java.io.IOException;

import com.golf.utils.json.support.JsonStringWriter;

public class LongArraySerializer extends SimpleArraySerializer {

    public LongArraySerializer(boolean primitive) {
        super(primitive);
    }

    @Override
    public void convertTo(JsonStringWriter writer, Object obj) throws IOException {
        if (primitive) {
            writer.writeLongArray((long[]) obj);
        } else {
            writer.writeLongArray((Long[]) obj);
        }
    }

}
