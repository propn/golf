package com.golf.utils.json.serializer;

import static com.golf.utils.json.JsonStringSymbol.ARRAY_PRE;
import static com.golf.utils.json.JsonStringSymbol.ARRAY_SUF;
import static com.golf.utils.json.JsonStringSymbol.EMPTY_ARRAY;
import static com.golf.utils.json.JsonStringSymbol.SEPARATOR;

import java.io.IOException;

import com.golf.utils.json.Serializer;
import com.golf.utils.json.support.JsonStringWriter;

public class ArraySerializer implements Serializer {

    @Override
    public void convertTo(JsonStringWriter writer, Object obj, String datePattern) throws IOException {
        Object[] objArray = (Object[]) obj;
        int iMax = objArray.length - 1;
        if (iMax == -1) {
            writer.write(EMPTY_ARRAY);
            return;
        }

        writer.append(ARRAY_PRE);
        for (int i = 0;; i++) {
            SerialStateMachine.toJson(objArray[i], writer, datePattern);
            if (i == iMax) {
                writer.append(ARRAY_SUF);
                return;
            }
            writer.append(SEPARATOR);
        }
    }

}
