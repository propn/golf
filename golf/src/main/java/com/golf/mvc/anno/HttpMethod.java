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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Thunder.Hsu
 * 2012-12-6
 */
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HttpMethod {
    
    /**
     * HTTP GET method
     */
    public static final String GET="GET";
    /**
     * HTTP POST method
     */
    public static final String POST="POST"; 
    /**
     * HTTP PUT method
     */
    public static final String PUT="PUT";
    /**
     * HTTP DELETE method
     */
    public static final String DELETE="DELETE";
    /**
     * HTTP HEAD method
     */
    public static final String HEAD="HEAD";
    /**
     * HTTP OPTIONS method
     */
    public static final String OPTIONS="OPTIONS";
    
    /**
     * Specifies the name of a HTTP method. E.g. "GET".
     */
    String value();        
}
