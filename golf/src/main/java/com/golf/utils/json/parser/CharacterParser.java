package com.golf.utils.json.parser;

import com.golf.utils.json.Parser;
import com.golf.utils.json.support.JsonStringReader;

public class CharacterParser implements Parser {

	@Override
	public Object convertTo(JsonStringReader reader, Class<?> clazz) {
		char ret = 0;
		String s = reader.readString();
		
		if(s.length() > 0)
			ret = s.charAt(0);
		return ret;
	}

}
