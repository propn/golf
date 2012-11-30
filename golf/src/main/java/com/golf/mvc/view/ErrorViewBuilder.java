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
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import com.golf.Golf;

/**
 * @author Administrator
 * 
 */
public class ErrorViewBuilder implements Builder {

    /*
     * (non-Javadoc)
     * 
     * @see com.golf.mvc.view.Builder#build(javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse, java.lang.String, java.lang.Object)
     */
    @Override
    public void build(HttpServletRequest request, HttpServletResponse response, String mediaType, Object rst)
            throws IOException {
        response.setCharacterEncoding(Golf.charsetName);
        response.setContentType(MediaType.TEXT_PLAIN);
        Throwable e = (Throwable) rst;
        PrintWriter out = response.getWriter();
        response.setStatus(500);
        out.print("Message:" + e.getMessage());
        out.println("");
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        out.print("StackTrace:" + stringWriter.toString());
        out.flush();
        out.close();
    }

}
