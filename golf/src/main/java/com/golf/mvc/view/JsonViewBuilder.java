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
import com.golf.mvc.anno.MediaType;
import com.golf.utils.json.Json;

/**
 * 
 * @author Thunder.Hsu 2012-12-8
 */
public class JsonViewBuilder implements ViewRender {

    @Override
    public void build(HttpServletRequest request, HttpServletResponse response, String mediaType, Object rst)
            throws IOException {
        response.setCharacterEncoding(Golf.charsetName);
        response.setContentType(MediaType.APPLICATION_JSON);
        PrintWriter out = response.getWriter();
        out.append(Json.toJson(rst));
        out.flush();
        out.close();
    }

}
