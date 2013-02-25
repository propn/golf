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
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.golf.Golf;
import com.golf.mvc.anno.MediaType;

/**
 * @author Thunder.Hsu
 * 
 */
public class ForwardViewBuilder implements ViewRender {
    @Override
    public void build(HttpServletRequest request, HttpServletResponse response, String mediaType, Object rst)
            throws IOException, ServletException {
        response.setCharacterEncoding(Golf.charsetName);
        response.setContentType(MediaType.TEXT_HTML);

        View v = (View) rst;
        Map<String, Object> model = v.getModel();
        for (Entry<String, Object> entry : model.entrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue());
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(request.getContextPath() + v.getPath());
        // dispatcher.include(request, response);
        dispatcher.forward(request, response);
    }

}
