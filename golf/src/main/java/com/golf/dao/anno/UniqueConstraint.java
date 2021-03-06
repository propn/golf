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
package com.golf.dao.anno;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author Thunder.Hsu
 * 2012-12-6
 */
@Target({}) 
@Retention(RUNTIME)

public @interface UniqueConstraint {

    /** (Required) An array of the column names that make up the constraint. */
    String[] columnNames();
}
