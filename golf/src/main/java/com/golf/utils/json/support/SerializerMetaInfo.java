package com.golf.utils.json.support;

import static com.golf.utils.json.JsonStringSymbol.OBJ_SEPARATOR;
import static com.golf.utils.json.JsonStringSymbol.QUOTE;

import java.io.IOException;

import com.golf.utils.json.Serializer;

public class SerializerMetaInfo extends MetaInfo {

    private Serializer serializer;

    public void setPropertyName(String propertyName, boolean first) {
        propertyNameString = propertyName;
        this.propertyName = ((first ? "" : ",") + QUOTE + propertyName + QUOTE + OBJ_SEPARATOR).toCharArray();
    }

    public void setSerializer(Serializer serializer) {
        this.serializer = serializer;
    }

    public Serializer getSerializer() {
        return serializer;
    }

    public void toJson(Object obj, JsonStringWriter writer) throws IOException {
        Object ret = propertyInvoke.get(obj);
        if (ret == null) {
            writer.writeNull();
            return;
        }
        serializer.convertTo(writer, ret);
    }

}
