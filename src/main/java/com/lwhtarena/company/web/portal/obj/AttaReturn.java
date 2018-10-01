package com.lwhtarena.company.web.portal.obj;

/**
 * <p>
 * <h2>简述：</h2>
 * <ol></ol>
 * <h2>功能描述：</h2>
 * <ol></ol>
 * </p>
 *
 * @Author: liwh
 * @Date :
 * @Version: 版本
 */
public class AttaReturn {

    private long aid;
    private long oid;
    private long fid;
    private String url;
    private String title;
    private long uploadDatetime;

    public long getAid() {
        return aid;
    }
    public void setAid(long aid) {
        this.aid = aid;
    }
    public long getOid() {
        return oid;
    }
    public void setOid(long oid) {
        this.oid = oid;
    }
    public long getFid() {
        return fid;
    }
    public void setFid(long fid) {
        this.fid = fid;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public long getUploadDatetime() {
        return uploadDatetime;
    }
    public void setUploadDatetime(long uploadDatetime) {
        this.uploadDatetime = uploadDatetime;
    }
}
