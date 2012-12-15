package com.golf.utils.json.serializer;

import static com.golf.utils.json.JsonStringSymbol.QUOTE;

import com.golf.utils.json.Serializer;
import com.golf.utils.json.support.JsonStringWriter;

public class CharacterSerializer implements Serializer {

    @Override
    public void convertTo(JsonStringWriter writer, Object obj, String datePattern) {
        writer.append(QUOTE).append((Character) obj).append(QUOTE);
    }

}
