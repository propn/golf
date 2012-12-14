package com.golf.utils.json.serializer;

import java.io.IOException;

import com.golf.utils.json.support.JsonStringWriter;

public class IntegerArraySerializer extends SimpleArraySerializer {

    public IntegerArraySerializer(boolean primitive) {
        super(primitive);
    }

    @Override
    public void convertTo(JsonStringWriter writer, Object obj) throws IOException {
        if (primitive) {
            writer.writeIntArray((int[]) obj);
        } else {
            writer.writeIntArray((Integer[]) obj);
        }
    }

}
