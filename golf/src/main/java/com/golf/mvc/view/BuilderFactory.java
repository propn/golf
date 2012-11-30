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

import java.io.File;

import javax.servlet.ServletException;
import javax.ws.rs.core.MediaType;

/**
 * @author Thunder.Hsu
 * 
 */
public class BuilderFactory {

    /**
     * 
     * @param mediaType
     * @param rst
     * @return
     * @throws Exception
     */
    public static Builder getBuilder(String mediaType, Object rst) throws ServletException {

        if (rst instanceof View) {
            View v = (View) rst;
            Vkind vkind = v.getKind();
            switch (vkind) {
            case forward:
                return new ForwardViewBuilder();
            case redirect:
                return new RedirectViewBuilder();
            case freeMarker:
                return new FreeMarkerViewBuilder();
            default:
                throw new ServletException("系统不支持视图 vkind:" + vkind);
            }
        }

        if (rst instanceof Throwable) {
            return new ErrorViewBuilder();
        }

        if (rst instanceof File) {
            return new FileViewBuilder();
        }

        // application/json
        if (mediaType.equals(MediaType.APPLICATION_JSON)) {
            return new JsonViewBuilder();
        }

        // application/xml
        if (mediaType.equals(MediaType.APPLICATION_XML)) {
            return new XmlViewBuilder();
        }

        throw new ServletException("系统不支持视图 [mediaType=" + mediaType + "vkind" + rst.getClass());
    }
}
