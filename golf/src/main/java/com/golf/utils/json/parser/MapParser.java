package com.golf.utils.json.parser;

import java.lang.reflect.Type;
import java.util.Map;

import com.golf.utils.json.exception.JsonException;
import com.golf.utils.json.support.JsonStringReader;

public class MapParser extends ComplexTypeParser {
	
	public MapParser(Type elementType) {
		super(elementType);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object convertTo(JsonStringReader reader, Class<?> clazz) {
		reader.mark();
		if(reader.isNull())
			return null;
		else
			reader.reset();
		
		if(!reader.isObject())
			throw new JsonException("json string is not object format");
		
		Map obj = null;
		try {
			obj = (Map)clazz.newInstance();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		// 判断空对象
		reader.mark();
		char c0 = reader.readAndSkipBlank();
		if(c0 == '}')
			return obj;
		else
			reader.reset();
		
		for(;;) {
			String key = reader.readString();
			if(!reader.isColon())
				throw new JsonException("missing ':'");
			
			obj.put(key, elementMetaInfo.getValue(reader));
			
			char ch = reader.readAndSkipBlank();
			if(ch == '}')
				return obj;

			if(ch != ',')
				throw new JsonException("missing ','");
		}
	}

}
