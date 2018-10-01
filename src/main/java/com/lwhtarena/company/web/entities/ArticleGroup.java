package com.lwhtarena.company.web.entities;


import com.lwhtarena.company.rank.entities.RankUnit;

/**
 * @Author：liwh
 * @Description:
 * @Date 14:30 2018/8/4
 * @Modified By:
 * <h1>
 * <ol></ol>
 * </h1>
 */
public class ArticleGroup extends RankUnit {
    private String name;
    private String title;
    private String icoUrl;
    private String jumpToUrl;
    private String folder;
    private String url;
    private String ipVisitAllow; //限定可访问的IP
    private String htmlOwn;
    private boolean status;
    private boolean changed;
    private boolean staticPage;
    private boolean open;
    private boolean clogging;
    private boolean poll;
    private boolean comm;
    private boolean gather;
    private int tmp;
    private int cw;
    private int ch;
    private VisitorsBook vbook;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getIcoUrl() {
        return icoUrl;
    }
    public void setIcoUrl(String icoUrl) {
        this.icoUrl = icoUrl;
    }
    public String getJumpToUrl() {
        return jumpToUrl;
    }
    public void setJumpToUrl(String jumpToUrl) {
        this.jumpToUrl = jumpToUrl;
    }
    public String getFolder() {
        return folder;
    }
    public void setFolder(String folder) {
        this.folder = folder;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public boolean isStaticPage() {
        return staticPage;
    }
    public void setStaticPage(boolean staticPage) {
        this.staticPage = staticPage;
    }
    public boolean isOpen() {
        return open;
    }
    public void setOpen(boolean open) {
        this.open = open;
    }
    public boolean isChanged() {
        return changed;
    }
    public void setChanged(boolean changed) {
        this.changed = changed;
    }
    public boolean isClogging() {
        return clogging;
    }
    public void setClogging(boolean clogging) {
        this.clogging = clogging;
    }
    public boolean isPoll() {
        return poll;
    }
    public void setPoll(boolean poll) {
        this.poll = poll;
    }
    public boolean isComm() {
        return comm;
    }
    public void setComm(boolean comm) {
        this.comm = comm;
    }
    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    public boolean isGather() {
        return gather;
    }
    public void setGather(boolean gather) {
        this.gather = gather;
    }
    public String getIpVisitAllow() {
        return ipVisitAllow;
    }
    public void setIpVisitAllow(String ipVisitAllow) {
        this.ipVisitAllow = ipVisitAllow;
    }
    public String getHtmlOwn() {
        return htmlOwn;
    }
    public void setHtmlOwn(String htmlOwn) {
        this.htmlOwn = htmlOwn;
    }
    public int getTmp() {
        return tmp;
    }
    public int getCw() {
        return cw;
    }
    public void setCw(int cw) {
        this.cw = cw;
    }
    public int getCh() {
        return ch;
    }
    public void setCh(int ch) {
        this.ch = ch;
    }
    public void setTmp(int tmp) {
        this.tmp = tmp;
    }
    public VisitorsBook getVbook() {
        return vbook;
    }
    public void setVbook(VisitorsBook vbook) {
        this.vbook = vbook;
    }
}
