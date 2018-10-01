package com.lwhtarena.company.web.entities;

import java.io.Serializable;

/**
 * @Author：liwh
 * @Description:
 * @Date 11:09 2018/8/5
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class HtmlFileStatic implements Serializable {
    private long id;
    private boolean status;
    private String realPath;
    private String url;
    private String filename;
    private long buildDatetime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getRealPath() {
        return realPath;
    }

    public void setRealPath(String realPath) {
        this.realPath = realPath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public long getBuildDatetime() {
        return buildDatetime;
    }

    public void setBuildDatetime(long buildDatetime) {
        this.buildDatetime = buildDatetime;
    }
}
