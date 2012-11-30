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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import com.google.common.io.InputSupplier;

/**
 * @author Thunder.Hsu
 * 
 */
public class FileStream extends OutputStream {

    private final int fileThreshold;
    private final boolean resetOnFinalize;
    private final InputSupplier<InputStream> supplier;

    private OutputStream out;
    private MemoryOutput memory;
    private File file;

    /** ByteArrayOutputStream that exposes its internals. */
    private static class MemoryOutput extends ByteArrayOutputStream {
        byte[] getBuffer() {
            return buf;
        }

        int getCount() {
            return count;
        }
    }

    /** Returns the file holding the data (possibly null). */
    synchronized File getFile() {
        return file;
    }

    public FileStream(int fileThreshold) {
        this(fileThreshold, false);
    }

    public FileStream(int fileThreshold, boolean resetOnFinalize) {
        this.fileThreshold = fileThreshold;
        this.resetOnFinalize = resetOnFinalize;
        memory = new MemoryOutput();
        out = memory;

        if (resetOnFinalize) {
            supplier = new InputSupplier<InputStream>() {
                @Override
                public InputStream getInput() throws IOException {
                    return openStream();
                }

                @Override
                protected void finalize() {
                    try {
                        reset();
                    } catch (Throwable t) {
                        t.printStackTrace(System.err);
                    }
                }
            };
        } else {
            supplier = new InputSupplier<InputStream>() {
                @Override
                public InputStream getInput() throws IOException {
                    return openStream();
                }
            };
        }
    }

    public InputSupplier<InputStream> getSupplier() {
        return supplier;
    }

    private synchronized InputStream openStream() throws IOException {
        if (file != null) {
            return new FileInputStream(file);
        } else {
            return new ByteArrayInputStream(memory.getBuffer(), 0, memory.getCount());
        }
    }

    public synchronized void reset() throws IOException {
        try {
            close();
        } finally {
            if (memory == null) {
                memory = new MemoryOutput();
            } else {
                memory.reset();
            }
            out = memory;
            if (file != null) {
                File deleteMe = file;
                file = null;
                if (!deleteMe.delete()) {
                    throw new IOException("Could not delete: " + deleteMe);
                }
            }
        }
    }

    @Override
    public synchronized void write(int b) throws IOException {
        update(1);
        out.write(b);
    }

    @Override
    public synchronized void write(byte[] b) throws IOException {
        write(b, 0, b.length);
    }

    @Override
    public synchronized void write(byte[] b, int off, int len) throws IOException {
        update(len);
        out.write(b, off, len);
    }

    @Override
    public synchronized void close() throws IOException {
        out.close();
    }

    @Override
    public synchronized void flush() throws IOException {
        out.flush();
    }

    private void update(int len) throws IOException {
        if (file == null && (memory.getCount() + len > fileThreshold)) {
            File temp = File.createTempFile(UUID.randomUUID().toString(), null);
            if (resetOnFinalize) {
                temp.deleteOnExit();
            }
            FileOutputStream transfer = new FileOutputStream(temp);
            transfer.write(memory.getBuffer(), 0, memory.getCount());
            transfer.flush();

            out = transfer;
            file = temp;
            memory = null;
        }
    }
}
