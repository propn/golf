package com.golf.utils.json.serializer;

import static com.golf.utils.json.JsonStringSymbol.EMPTY_ARRAY;
import static com.golf.utils.json.JsonStringSymbol.OBJ_PRE;
import static com.golf.utils.json.JsonStringSymbol.OBJ_SUF;
import static com.golf.utils.json.JsonStringSymbol.SEPARATOR;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.golf.utils.json.Serializer;
import com.golf.utils.json.support.JsonStringWriter;

public class MapSerializer implements Serializer {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void convertTo(JsonStringWriter writer, Object obj) throws IOException {
        Map map = (Map) obj;
        Set<Entry<?, ?>> entrySet = map.entrySet();
        if (entrySet.size() == 0) {
            writer.write(EMPTY_ARRAY);
            return;
        }

        writer.append(OBJ_PRE);
        for (Iterator<Entry<?, ?>> it = entrySet.iterator();;) {
            Entry<?, ?> entry = it.next();
            writer.write("\"" + entry.getKey() + "\":");
            SerialStateMachine.toJson(entry.getValue(), writer);
            if (!it.hasNext()) {
                writer.append(OBJ_SUF);
                return;
            }
            writer.append(SEPARATOR);
        }

    }

}
