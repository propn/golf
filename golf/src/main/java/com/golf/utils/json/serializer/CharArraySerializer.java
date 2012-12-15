package com.golf.utils.json.serializer;

import java.io.IOException;

import com.golf.utils.json.Serializer;
import com.golf.utils.json.support.JsonStringWriter;

public class CharArraySerializer implements Serializer {

    @Override
    public void convertTo(JsonStringWriter writer, Object obj, String datePattern) throws IOException {
        writer.writeStringWithQuote(new String((char[]) obj));
    }

}
