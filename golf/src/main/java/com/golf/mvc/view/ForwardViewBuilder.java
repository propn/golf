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
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import com.golf.Golf;
import com.golf.tools.MultMap;

/**
 * @author Administrator
 * 
 */
public class ForwardViewBuilder implements Builder {
    @Override
    public void build(HttpServletRequest request, HttpServletResponse response, String mediaType, Object rst)
            throws IOException, ServletException {
        View v = (View) rst;
        MultMap<String, Object> model = v.getModel();
        for (Entry<String, List<Object>> entry : model.entrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue());
        }
        response.setCharacterEncoding(Golf.charsetName);
        RequestDispatcher dispatcher = request.getRequestDispatcher(request.getContextPath() + v.getPath());
        response.setContentType(MediaType.TEXT_HTML);
        // dispatcher.include(request, response);
        dispatcher.forward(request, response);
    }

}
