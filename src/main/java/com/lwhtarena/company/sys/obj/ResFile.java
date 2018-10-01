package com.lwhtarena.company.sys.obj;

/**
 * @Author：liwh
 * @Description:
 * @Date 23:16 2018/8/6
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class ResFile {

    private String filename;
    private String realpath;
    private long size;
    private long lastModified;

    public String getFilename() {
        return this.filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getRealpath() {
        return this.realpath;
    }

    public void setRealpath(String realpath) {
        this.realpath = realpath;
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getLastModified() {
        return this.lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }
}
