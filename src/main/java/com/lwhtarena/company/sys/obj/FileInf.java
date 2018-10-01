package com.lwhtarena.company.sys.obj;

/**
 * @Author：liwh
 * @Description:
 * @Date 22:56 2018/8/6
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class FileInf {

    private String url;
    private String fullPath;
    private String realPath;
    private String nameNoExt;
    private String ext;
    private long size;
    private long addtime;
    private String md5;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public String getRealPath() {
        return realPath;
    }

    public void setRealPath(String realPath) {
        this.realPath = realPath;
    }

    public String getNameNoExt() {
        return nameNoExt;
    }

    public void setNameNoExt(String nameNoExt) {
        this.nameNoExt = nameNoExt;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getAddtime() {
        return addtime;
    }

    public void setAddtime(long addtime) {
        this.addtime = addtime;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}
