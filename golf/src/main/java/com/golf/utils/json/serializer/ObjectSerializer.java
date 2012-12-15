package com.golf.utils.json.serializer;

import static com.golf.utils.json.JsonStringSymbol.OBJ_PRE;
import static com.golf.utils.json.JsonStringSymbol.OBJ_SUF;

import java.io.IOException;

import com.golf.utils.json.Serializer;
import com.golf.utils.json.compiler.EncodeCompiler;
import com.golf.utils.json.support.JsonStringWriter;
import com.golf.utils.json.support.SerializerMetaInfo;

public class ObjectSerializer implements Serializer {

    private SerializerMetaInfo[] serializerMetaInfos;

    public ObjectSerializer(Class<?> clazz, String datePattern) {
        serializerMetaInfos = EncodeCompiler.compile(clazz, datePattern);
    }

    @Override
    public void convertTo(JsonStringWriter writer, Object obj, String datePattern) throws IOException {
        if (writer.existRef(obj)) { // 防止循环引用，此处会影响一些性能
            writer.writeNull();
            return;
        }

        writer.pushRef(obj);
        writer.append(OBJ_PRE);
        for (SerializerMetaInfo metaInfo : serializerMetaInfos) {
            writer.write(metaInfo.getPropertyName());
            metaInfo.toJson(obj, writer, datePattern);
        }
        writer.append(OBJ_SUF);
        writer.popRef();
    }

}
