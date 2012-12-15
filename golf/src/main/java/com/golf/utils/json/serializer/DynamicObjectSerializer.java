package com.golf.utils.json.serializer;

import java.io.IOException;

import com.golf.utils.json.Serializer;
import com.golf.utils.json.support.JsonStringWriter;

public class DynamicObjectSerializer implements Serializer {

    @Override
    public void convertTo(JsonStringWriter writer, Object obj, String datePattern) throws IOException {
        SerialStateMachine.toJson(obj, writer, datePattern);
    }

}
