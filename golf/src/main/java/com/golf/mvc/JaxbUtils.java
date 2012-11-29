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
package com.golf.mvc;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.golf.Golf;
import com.golf.tools.ClassUtils;

/**
 * @author Thunder.Hsu
 * 
 */
public class JaxbUtils {
    private static final Logger log = LoggerFactory.getLogger(JaxbUtils.class);
    private static final String ENCODING = "UTF-8";
    private static JAXBContext context = null;
    private static Marshaller marshaller = null;
    private static Unmarshaller unmarshaller = null;

    public static void regist(Set<Class<?>> clzs) throws JAXBException, IOException, ClassNotFoundException {
        long start = System.currentTimeMillis();
        List<Class<?>> rst = new ArrayList<Class<?>>();
        for (Class clz : clzs) {
            if (clz.isAnnotationPresent(XmlRootElement.class)) {
                rst.add(clz);
                log.debug("register jaxb class " + clz.getName());
            }
        }
        Class[] clz = new Class[rst.size()];
        int i = 0;
        for (Class<?> c : rst) {
            clz[i] = c;
            i++;
        }
        context = JAXBContext.newInstance(clz);
        marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_ENCODING, ENCODING);
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);
        unmarshaller = context.createUnmarshaller();
        log.debug("init JAXBContext cost time(millis):" + String.valueOf(System.currentTimeMillis() - start));
    }

    synchronized private static void registJaxb() throws JAXBException, IOException, ClassNotFoundException {
        long start = System.currentTimeMillis();
        context = JAXBContext.newInstance(loadJaxbClass(Golf.getPkgs()));
        marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_ENCODING, ENCODING);
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);
        unmarshaller = context.createUnmarshaller();
        log.debug("init JAXBContext cost time(millis):" + String.valueOf(System.currentTimeMillis() - start));
    }

    public static String toXml(Object obj) throws JAXBException, IOException, ClassNotFoundException {
        if (null == context) {
            registJaxb();
        }
        StringWriter sw = new StringWriter();
        marshaller.marshal(obj, sw);
        return sw.toString();
    }

    public static <T> T fromXml(Class<?> stream, String xml) throws JAXBException, IOException, ClassNotFoundException {
        if (null == context) {
            registJaxb();
        }
        JAXBElement<T> element = (JAXBElement<T>) unmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes()));
        return element.getValue();
    }

    private static Class[] loadJaxbClass(String[] packages) throws IOException, ClassNotFoundException {
        long start = System.currentTimeMillis();
        Set<Class<?>> calssList = new LinkedHashSet<Class<?>>();
        List<String> classFilters = new ArrayList<String>();
        ClassUtils handler = new ClassUtils(true, true, classFilters);
        for (String pkg : packages) {
            calssList.addAll(handler.getPackageAllClasses(pkg, true));
        }
        List<Class<?>> rst = new ArrayList<Class<?>>();
        for (Class clz : calssList) {
            if (clz.isAnnotationPresent(XmlRootElement.class)) {
                rst.add(clz);
                log.debug("register jaxb class " + clz.getName());
            }
        }
        Class[] clz = new Class[rst.size()];
        int i = 0;
        for (Class<?> c : rst) {
            clz[i] = c;
            i++;
        }
        log.debug("scan JAXBContext Class cost time(millis):" + String.valueOf(System.currentTimeMillis() - start));
        return clz;
    }

}
