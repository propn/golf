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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.golf.Golf;

/**
 * @author Administrator
 * 
 */
public class DownloadViewBuilder implements Builder {

    /*
     * (non-Javadoc)
     * 
     * @see com.golf.mvc.view.Builder#build(javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse, java.lang.String, java.lang.Object)
     */
    @Override
    public void build(HttpServletRequest request, HttpServletResponse response, String mediaType, Object rst)
            throws IOException {
        File file = (File) rst;
        response.setContentType("application/x-msdownload");
        String filename = java.net.URLEncoder.encode(file.getName(), Golf.charsetName);
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + filename);
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream buff = new BufferedInputStream(fis);
        byte[] b = new byte[1024];
        long i = 0;
        OutputStream outs = response.getOutputStream();
        while (i < file.length()) {
            int j = buff.read(b, 0, 1024);
            i += j;
            outs.write(b, 0, j);
        }
        outs.flush();
        return;

    }

}
