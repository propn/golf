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
package com.golf.mvc.multipart;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Thunder.Hsu
 * 
 */
public class UpFile {

    private String fileName;
    private String contentType;
    private FileStream fileStream;

    public UpFile(FilePart filePart) throws IOException {
        fileName = filePart.getFileName();
        contentType = filePart.getContentType();
        fileStream = new FileStream(50 * 1000 * 1000);
        filePart.writeTo(fileStream);
    }

    public String getFileName() {
        return fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public File writeTo(String path) throws IOException {
        File file = null;
        InputStream inputStream = null;
        BufferedOutputStream fileOut = null;
        try {
            file = new File(path);
            inputStream = fileStream.getSupplier().getInput();
            fileOut = new BufferedOutputStream(new FileOutputStream(file));
            write(inputStream, fileOut);
        } finally {
            fileStream.reset();
            if (fileOut != null) {
                fileOut.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return file;
    }

    long write(InputStream inputStream, OutputStream out) throws IOException {
        long size = 0;
        int read;
        byte[] buf = new byte[8 * 1024];
        while ((read = inputStream.read(buf)) != -1) {
            out.write(buf, 0, read);
            size += read;
        }
        return size;
    }

}
