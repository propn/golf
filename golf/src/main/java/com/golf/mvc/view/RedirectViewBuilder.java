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
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import com.golf.Golf;

/**
 * @author Thunder.Hsu
 * 
 */
public class RedirectViewBuilder implements Builder {

    @Override
    public void build(HttpServletRequest request, HttpServletResponse response, String mediaType, Object rst)
            throws IOException, ServletException {
        response.setCharacterEncoding(Golf.charsetName);
        response.setContentType(MediaType.TEXT_HTML);

        View v = (View) rst;
        String path = v.getPath();
        Map<String, Object> model = v.getModel();
        String ctx = request.getContextPath();
        StringBuffer sb = new StringBuffer(ctx);
        sb.append(path);
        sb.append("?");
        for (Entry<String, ?> p : model.entrySet()) {
            sb.append(p.getKey()).append("=").append(p.getValue());
        }
        response.sendRedirect(sb.toString());
    }
}
