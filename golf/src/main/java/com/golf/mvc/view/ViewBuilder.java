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
package com.golf.mvc.view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Thunder.Hsu
 * 
 */
public interface ViewBuilder {

    /**
     * 
     * @param request
     * @param response
     * @param mediaType
     * @param rst
     * @throws IOException
     * @throws ServletException
     */
    public void build(HttpServletRequest request, HttpServletResponse response, String mediaType, Object rst)
            throws IOException, ServletException;
}
