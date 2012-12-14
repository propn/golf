package com.golf.utils.json;

import java.io.IOException;

import com.golf.utils.json.support.JsonStringWriter;

public interface Serializer {
	void convertTo(JsonStringWriter writer, Object obj) throws IOException;
}
