package com.golf.utils.json.serializer;

import java.io.IOException;

import com.golf.utils.Base64;
import com.golf.utils.json.Serializer;
import com.golf.utils.json.support.JsonStringWriter;

public class ByteArraySerializer implements Serializer {

    @Override
    public void convertTo(JsonStringWriter writer, Object obj, String datePattern) throws IOException {
        writer.writeStringWithQuote(Base64.encodeToString((byte[]) obj, false));
    }

}
