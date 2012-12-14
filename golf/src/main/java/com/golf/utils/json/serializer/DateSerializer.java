package com.golf.utils.json.serializer;

import static com.golf.utils.json.JsonStringSymbol.QUOTE;

import java.util.Date;

import com.golf.utils.json.Serializer;
import com.golf.utils.json.support.JsonStringWriter;
import com.golf.utils.time.SafeSimpleDateFormat;

public class DateSerializer implements Serializer {

    @Override
    public void convertTo(JsonStringWriter writer, Object obj) {
        writer.write(QUOTE + SafeSimpleDateFormat.defaultDateFormat.format((Date) obj) + QUOTE);
    }

}
