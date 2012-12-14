package com.golf.utils.json.serializer;

import com.golf.utils.json.Serializer;

public abstract class SimpleArraySerializer implements Serializer {

	protected boolean primitive;

	public SimpleArraySerializer(boolean primitive) {
		this.primitive = primitive;
	}

}
