package com.golf.utils.json.compiler;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import com.golf.utils.RefUtils;
import com.golf.utils.json.anno.Transient;
import com.golf.utils.json.exception.JsonException;
import com.golf.utils.json.parser.CollectionParser;
import com.golf.utils.json.parser.ComplexTypeParser;
import com.golf.utils.json.parser.MapParser;
import com.golf.utils.json.parser.ParserStateMachine;
import com.golf.utils.json.support.FieldInvoke;
import com.golf.utils.json.support.ParserMetaInfo;

public class DecodeCompiler {
    private static final ParserMetaInfo[] EMPTY_ARRAY = new ParserMetaInfo[0];

    public static ParserMetaInfo[] compile(Class<?> clazz) {
        ParserMetaInfo[] parserMetaInfos = null;
        Set<ParserMetaInfo> fieldSet = new TreeSet<ParserMetaInfo>();
        Set<Entry<String, Field>> fields = RefUtils.getFields(clazz).entrySet();
        for (Entry<String, Field> entry : fields) {
            Field field = entry.getValue();
            if (Modifier.isTransient(field.getModifiers()) || field.isAnnotationPresent(Transient.class)) {
                continue;
            }
            ParserMetaInfo parserMetaInfo = new ParserMetaInfo();
            parserMetaInfo.setPropertyNameString(field.getName());
            parserMetaInfo.setPropertyInvoke(new FieldInvoke(field));

            Class<?> type = field.getType();
            if (Collection.class.isAssignableFrom(type)) {
                Type fieldType = field.getGenericType();
                if (!(fieldType instanceof ParameterizedType))
                    throw new JsonException("not support the " + field);

                ParameterizedType paramType = (ParameterizedType) fieldType;
                Type[] types2 = paramType.getActualTypeArguments();
                if (types2.length != 1)
                    throw new JsonException("not support the " + field);

                Type elementType = types2[0];
                parserMetaInfo.setType(ComplexTypeParser.getImplClass(type));
                parserMetaInfo.setParser(new CollectionParser(elementType));
            } else if (Map.class.isAssignableFrom(type)) { // Map元信息构造
                Type fieldType = field.getGenericType();
                if (!(fieldType instanceof ParameterizedType))
                    throw new JsonException("not support the " + field);

                ParameterizedType paramType = (ParameterizedType) fieldType;
                Type[] types2 = paramType.getActualTypeArguments();
                if (types2.length != 2) {
                    throw new JsonException("not support the " + field);
                }
                Type key = types2[0];
                if (!((key instanceof Class) && key == String.class)) {
                    throw new JsonException("not support the " + field);
                }
                Type elementType = types2[1];
                parserMetaInfo.setType(ComplexTypeParser.getImplClass(type));
                parserMetaInfo.setParser(new MapParser(elementType));
            } else { // 获取对象、枚举或者数组Parser
                parserMetaInfo.setType(type);
                parserMetaInfo.setParser(ParserStateMachine.getParser(type));
            }
            fieldSet.add(parserMetaInfo);
        }
        parserMetaInfos = fieldSet.toArray(EMPTY_ARRAY);
        if (parserMetaInfos.length <= 0)
            throw new JsonException("not support the " + clazz.getName());
        return parserMetaInfos;
    }
}
