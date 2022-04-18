package io.starlight.http;

/**
 *
 * @author denny
 */
public enum ResponseType {
    
    TEXT    ("text/plain"),
    HTML    ("text/html"),
    JSON    ("application/json"),
    RAW     ("application/octet-stream");
    
    private final String mimeType;

    private ResponseType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getMimeType() {
        return mimeType;
    }
}
