package com.golf.utils.json.serializer;

import com.golf.utils.json.Serializer;
import com.golf.utils.json.support.JsonStringWriter;

public class BoolSerializer implements Serializer {

    @Override
    public void convertTo(JsonStringWriter writer, Object obj) {
        writer.writeBoolean((Boolean) obj);
    }

}
