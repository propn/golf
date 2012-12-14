package com.golf.utils.json.serializer;

import java.io.IOException;

import com.golf.utils.json.support.JsonStringWriter;

public class BooleanArraySerializer extends SimpleArraySerializer {

    public BooleanArraySerializer(boolean primitive) {
        super(primitive);
    }

    @Override
    public void convertTo(JsonStringWriter writer, Object obj) throws IOException {
        if (primitive) {
            writer.writeBooleanArray((boolean[]) obj);
        } else {
            writer.writeBooleanArray((Boolean[]) obj);
        }
    }

}
