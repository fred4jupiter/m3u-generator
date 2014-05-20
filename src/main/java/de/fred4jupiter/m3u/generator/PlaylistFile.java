package de.fred4jupiter.m3u.generator;

public enum PlaylistFile {

    M3U(".m3u", "ISO-8859-1"),

    M3U8(".m3u8", "UTF-8");

    private String fileExtension;

    private String encoding;

    private PlaylistFile(String fileExtension, String encoding) {
        this.fileExtension = fileExtension;
        this.encoding = encoding;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public String getEncoding() {
        return encoding;
    }
}
