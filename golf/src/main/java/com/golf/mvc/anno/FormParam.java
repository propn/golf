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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Thunder.Hsu
 * 2012-12-6
 */
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FormParam {
    /**
     * Defines the name of the form parameter whose value will be used
     * to initialize the value of the annotated method argument. The name is 
     * specified in decoded form, any percent encoded literals within the value
     * will not be decoded and will instead be treated as literal text. E.g. if
     * the parameter name is "a b" then the value of the annotation is "a b", 
     * <i>not</i> "a+b" or "a%20b".
     */
    String value();
}
