package com.golf.mvc.multipart;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletInputStream;

public class FilePart extends Part {

    /** "file system" name of the file */
    private String fileName;

    /** path of the file as sent in the request, if given */
    private String filePath;

    /** content type of the file */
    private String contentType;

    /** input stream containing file data */
    private PartInputStream partInput;

    FilePart(String name, ServletInputStream in, String boundary, String contentType, String fileName, String filePath)
            throws IOException {
        super(name);
        this.fileName = fileName;
        this.filePath = filePath;
        this.contentType = contentType;
        partInput = new PartInputStream(in, boundary);
    }

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getContentType() {
        return contentType;
    }

    public InputStream getInputStream() {
        return partInput;
    }

    public long writeTo(OutputStream out) throws IOException {
        long size = 0;
        // Only do something if this part contains a file
        if (fileName != null) {
            // Write it out
            size = write(out);
        }
        return size;
    }

    long write(OutputStream out) throws IOException {
        // decode macbinary if this was sent
        if (contentType.equals("application/x-macbinary")) {
            out = new MacBinaryDecoderOutputStream(out);
        }
        long size = 0;
        int read;
        byte[] buf = new byte[8 * 1024];
        while ((read = partInput.read(buf)) != -1) {
            out.write(buf, 0, read);
            size += read;
        }
        return size;
    }

    /**
     * Returns <code>true</code> to indicate this part is a file.
     * 
     * @return true.
     */
    public boolean isFile() {
        return true;
    }
}
