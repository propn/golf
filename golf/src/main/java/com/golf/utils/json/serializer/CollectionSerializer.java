package com.golf.utils.json.serializer;

import static com.golf.utils.json.JsonStringSymbol.ARRAY_PRE;
import static com.golf.utils.json.JsonStringSymbol.ARRAY_SUF;
import static com.golf.utils.json.JsonStringSymbol.EMPTY_ARRAY;
import static com.golf.utils.json.JsonStringSymbol.SEPARATOR;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import com.golf.utils.json.Serializer;
import com.golf.utils.json.support.JsonStringWriter;

public class CollectionSerializer implements Serializer {

    @Override
    public void convertTo(JsonStringWriter writer, Object obj, String datePattern) throws IOException {
        Collection<?> collection = (Collection<?>) obj;
        if (collection.size() == 0) {
            writer.write(EMPTY_ARRAY);
            return;
        }

        writer.append(ARRAY_PRE);
        for (Iterator<?> it = collection.iterator();;) {
            SerialStateMachine.toJson(it.next(), writer, datePattern);
            if (!it.hasNext()) {
                writer.append(ARRAY_SUF);
                return;
            }
            writer.append(SEPARATOR);
        }

    }

}
