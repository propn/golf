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
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.golf.Golf;

/**
 * @author Thunder.Hsu
 * 
 */
public class TextViewBuilder implements ViewRender {
    /*
     * (non-Javadoc)
     * 
     * @see com.golf.mvc.view.Builder#build(javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse, java.lang.String, java.lang.Object)
     */
    @Override
    public void build(HttpServletRequest request, HttpServletResponse response, String mediaType, Object rst)
            throws IOException {
        rst = (String) rst;
        
        response.setCharacterEncoding(Golf.charsetName);
        response.setContentType(mediaType);
        
        PrintWriter out = response.getWriter();
        response.setStatus(200);
        out.print(rst);
        out.flush();
        out.close();
    }
}
