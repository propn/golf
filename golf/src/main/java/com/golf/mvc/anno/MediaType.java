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
package com.golf.mvc.anno;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Thunder.Hsu 2012-12-6
 */
public class MediaType {

    private String type;
    private String subtype;
    private Map<String, String> parameters;

    /**
     * Empty immutable map used for all instances without parameters
     */
    private static final Map<String, String> emptyMap = Collections.emptyMap();

    /** The value of a type or subtype wildcard: "*" */
    public static final String MEDIA_TYPE_WILDCARD = "*";

    // Common media type constants
    /** "*&#47;*" */
    public final static String WILDCARD = "*/*";
    /** "*&#47;*" */
    public final static MediaType WILDCARD_TYPE = new MediaType();

    /** "application/xml" */
    public final static String APPLICATION_XML = "application/xml";
    /** "application/xml" */
    public final static MediaType APPLICATION_XML_TYPE = new MediaType("application", "xml");

    /** "application/atom+xml" */
    public final static String APPLICATION_ATOM_XML = "application/atom+xml";
    /** "application/atom+xml" */
    public final static MediaType APPLICATION_ATOM_XML_TYPE = new MediaType("application", "atom+xml");

    /** "application/xhtml+xml" */
    public final static String APPLICATION_XHTML_XML = "application/xhtml+xml";
    /** "application/xhtml+xml" */
    public final static MediaType APPLICATION_XHTML_XML_TYPE = new MediaType("application", "xhtml+xml");

    /** "application/svg+xml" */
    public final static String APPLICATION_SVG_XML = "application/svg+xml";
    /** "application/svg+xml" */
    public final static MediaType APPLICATION_SVG_XML_TYPE = new MediaType("application", "svg+xml");

    /** "application/json" */
    public final static String APPLICATION_JSON = "application/json";
    /** "application/json" */
    public final static MediaType APPLICATION_JSON_TYPE = new MediaType("application", "json");

    /** "application/x-www-form-urlencoded" */
    public final static String APPLICATION_FORM_URLENCODED = "application/x-www-form-urlencoded";
    /** "application/x-www-form-urlencoded" */
    public final static MediaType APPLICATION_FORM_URLENCODED_TYPE = new MediaType("application",
            "x-www-form-urlencoded");

    /** "multipart/form-data" */
    public final static String MULTIPART_FORM_DATA = "multipart/form-data";
    /** "multipart/form-data" */
    public final static MediaType MULTIPART_FORM_DATA_TYPE = new MediaType("multipart", "form-data");

    /** "application/octet-stream" */
    public final static String APPLICATION_OCTET_STREAM = "application/octet-stream";
    /** "application/octet-stream" */
    public final static MediaType APPLICATION_OCTET_STREAM_TYPE = new MediaType("application", "octet-stream");

    /** "text/plain" */
    public final static String TEXT_PLAIN = "text/plain";
    /** "text/plain" */
    public final static MediaType TEXT_PLAIN_TYPE = new MediaType("text", "plain");

    /** "text/xml" */
    public final static String TEXT_XML = "text/xml";
    /** "text/xml" */
    public final static MediaType TEXT_XML_TYPE = new MediaType("text", "xml");

    /** "text/html" */
    public final static String TEXT_HTML = "text/html";
    /** "text/html" */
    public final static MediaType TEXT_HTML_TYPE = new MediaType("text", "html");

    /**
     * Creates a new instance of MediaType with the supplied type, subtype and parameters.
     * 
     * @param type the primary type, null is equivalent to {@link #MEDIA_TYPE_WILDCARD}.
     * @param subtype the subtype, null is equivalent to {@link #MEDIA_TYPE_WILDCARD}.
     * @param parameters a map of media type parameters, null is the same as an empty map.
     */
    public MediaType(String type, String subtype, Map<String, String> parameters) {
        this.type = type == null ? MEDIA_TYPE_WILDCARD : type;
        this.subtype = subtype == null ? MEDIA_TYPE_WILDCARD : subtype;
        if (parameters == null) {
            this.parameters = emptyMap;
        } else {
            Map<String, String> map = new TreeMap<String, String>(new Comparator<String>() {
                public int compare(String o1, String o2) {
                    return o1.compareToIgnoreCase(o2);
                }
            });
            for (Map.Entry<String, String> e : parameters.entrySet()) {
                map.put(e.getKey().toLowerCase(), e.getValue());
            }
            this.parameters = Collections.unmodifiableMap(map);
        }
    }

    /**
     * Creates a new instance of MediaType with the supplied type and subtype.
     * 
     * @param type the primary type, null is equivalent to {@link #MEDIA_TYPE_WILDCARD}
     * @param subtype the subtype, null is equivalent to {@link #MEDIA_TYPE_WILDCARD}
     */
    public MediaType(String type, String subtype) {
        this(type, subtype, emptyMap);
    }

    /**
     * Creates a new instance of MediaType, both type and subtype are wildcards. Consider using the constant
     * {@link #WILDCARD_TYPE} instead.
     */
    public MediaType() {
        this(MEDIA_TYPE_WILDCARD, MEDIA_TYPE_WILDCARD);
    }

    /**
     * Getter for primary type.
     * 
     * @return value of primary type.
     */
    public String getType() {
        return this.type;
    }

}