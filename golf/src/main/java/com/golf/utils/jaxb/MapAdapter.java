/*
 * Copyright (C) 2012 The Golf Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 */
package com.golf.utils.jaxb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * <pre>
 * &#064;XmlJavaTypeAdapter(MyHashMapAdapter.class)
 * HashMap hashmap;
 * </pre>
 * 
 * @author Thunder.Hsu 2013-1-30
 */

public class MapAdapter extends XmlAdapter<MapType, Map> {

    /*
     * (non-Javadoc)
     * 
     * @see javax.xml.bind.annotation.adapters.XmlAdapter#marshal(java.lang.Object)
     */
    @Override
    public MapType marshal(Map map) throws Exception {
        MapType mapType = new MapType();
        for (Entry entry : (Set<Entry>) map.entrySet()) {
            MapEntryType mapEntryType = new MapEntryType();
            mapEntryType.key = (Integer) entry.getKey();
            mapEntryType.value = (String) entry.getValue();
            mapType.entries.add(mapEntryType);
        }
        return mapType;
    }

    @Override
    public HashMap unmarshal(MapType arg0) throws Exception {
        HashMap hashMap = new HashMap();
        for (MapEntryType myHashEntryType : (List<MapEntryType>) arg0.entries) {
            hashMap.put(myHashEntryType.key, myHashEntryType.value);
        }
        return hashMap;
    }
}
