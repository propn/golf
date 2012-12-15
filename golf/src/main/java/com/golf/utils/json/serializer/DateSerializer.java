package com.golf.utils.json.serializer;

import static com.golf.utils.json.JsonStringSymbol.QUOTE;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.golf.utils.json.Serializer;
import com.golf.utils.json.support.JsonStringWriter;
import com.golf.utils.json.support.SafeSimpleDateFormat;

public class DateSerializer implements Serializer {

    private static final Map<String, SafeSimpleDateFormat> SDF_MAP = new HashMap<String, SafeSimpleDateFormat>();

    @Override
    public void convertTo(JsonStringWriter writer, Object obj, String datePattern) {
        SafeSimpleDateFormat sdf = SDF_MAP.get(datePattern);
        if (null == sdf) {
            sdf = new SafeSimpleDateFormat(datePattern);
            SDF_MAP.put(datePattern, sdf);
        }
        writer.write(QUOTE + sdf.format((Date) obj) + QUOTE);
    }

}
