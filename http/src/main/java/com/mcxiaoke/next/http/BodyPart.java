package com.mcxiaoke.next.http;

import com.mcxiaoke.next.utils.AssertUtils;
import com.mcxiaoke.next.utils.MimeUtils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import java.io.File;
import java.io.IOException;

/**
 * User: mcxiaoke
 * Date: 15/7/2
 * Time: 13:15
 */
class BodyPart {


    public final String name;
    public final String contentType;
    public final File file;
    public final byte[] bytes;
    public final String fileName;
    public final long length;
    private RequestBody body;

    private BodyPart(String name, File file, String mimeType, String fileName) {
        AssertUtils.notNull(name, "name can not be null.");
        AssertUtils.notNull(file, "file can not be null.");
        AssertUtils.notNull(mimeType, "mimeType can not be null.");
        this.name = name;
        this.contentType = mimeType;
        this.file = file;
        this.bytes = null;
        this.fileName = fileName;
        this.length = file.length();
        this.body = RequestBody.create(MediaType.parse(contentType), file);
    }

    private BodyPart(String name, byte[] bytes, String mimeType) {
        AssertUtils.notNull(name, "name can not be null.");
        AssertUtils.notNull(bytes, "bytes can not be null.");
        AssertUtils.notNull(mimeType, "mimeType can not be null.");
        this.name = name;
        this.contentType = mimeType;
        this.file = null;
        this.bytes = bytes;
        this.length = bytes.length;
        this.fileName = HttpConsts.DEFAULT_NAME;
        RequestBody.create(MediaType.parse(contentType), bytes);
    }

    public String getName() {
        return name;
    }

    public String getFileName() {
        return fileName;
    }

    public RequestBody getBody() throws IOException {
        return body;
    }

    public static BodyPart create(String name, File file) {
        final String mimeType = MimeUtils.getMimeTypeFromPath(file.getPath());
        return create(name, file, mimeType, file.getName());
    }

    public static BodyPart create(String name, File file, String mimeType) {
        return create(name, file, mimeType, file.getName());
    }

    public static BodyPart create(String name, File file, String mimeType, String fileName) {
        return new BodyPart(name, file, mimeType, fileName);
    }

    public static BodyPart create(String name, byte[] bytes) {
        return create(name, bytes, HttpConsts.APPLICATION_OCTET_STREAM);
    }

    public static BodyPart create(String name, byte[] bytes, String mimeType) {
        return new BodyPart(name, bytes, mimeType);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StreamPart{");
        sb.append("name='").append(name).append('\'');
        sb.append(", contentType=").append(contentType);
        sb.append(", length=").append(length);
        sb.append(", file=").append(file);
        sb.append('}');
        return sb.toString();
    }
}