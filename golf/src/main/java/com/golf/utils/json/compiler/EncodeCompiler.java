package com.golf.utils.json.compiler;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import com.golf.utils.RefUtils;
import com.golf.utils.json.anno.Transient;
import com.golf.utils.json.serializer.SerialStateMachine;
import com.golf.utils.json.support.FieldInvoke;
import com.golf.utils.json.support.SerializerMetaInfo;

public class EncodeCompiler {

    private static final SerializerMetaInfo[] EMPTY_ARRAY = new SerializerMetaInfo[0];

    public static SerializerMetaInfo[] compile(Class<?> clazz, String datePattern) {
        SerializerMetaInfo[] serializerMetaInfos = null;
        Set<SerializerMetaInfo> fieldSet = new TreeSet<SerializerMetaInfo>();
        Set<Entry<String, Field>> fields = RefUtils.getFields(clazz).entrySet();
        for (Entry<String, Field> entry : fields) {
            Field field = entry.getValue();
            if (Modifier.isTransient(field.getModifiers()) || field.isAnnotationPresent(Transient.class)) {
                continue;
            }
            String fieldName = entry.getKey();
            SerializerMetaInfo fieldMetaInfo = new SerializerMetaInfo();
            fieldMetaInfo.setPropertyName(fieldName, false);
            fieldMetaInfo.setPropertyInvoke(new FieldInvoke(field));
            fieldMetaInfo.setSerializer(SerialStateMachine.getSerializerInCompiling(field.getType(), datePattern));
            fieldSet.add(fieldMetaInfo);
        }
        serializerMetaInfos = fieldSet.toArray(EMPTY_ARRAY);
        if (serializerMetaInfos.length > 0) {
            serializerMetaInfos[0].setPropertyName(serializerMetaInfos[0].getPropertyNameString(), true);
        }
        return serializerMetaInfos;
    }

}
