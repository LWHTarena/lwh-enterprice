package com.lwhtarena.company.web.entities;

import java.io.Serializable;

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
public class Portal implements Serializable {

    private long id;
    private String name;
    private String fullName; //网站全称
    private String url;
    private int status;
    private String keyWords; //网站关键字
    private String description; //网站描述
    private String host; //网站主机名或域名
    private String closeAnnounce; //网站关闭通知
    private String welcomeStr; //网站欢迎词
    private String ipRegScope;
    private boolean userRegAllow;
    private boolean poll;
    private boolean comm;
    private boolean freeComm;
    private boolean commPassAuto;
    private boolean artPassAuto;
    private int codeSendMode;
    private String ipVisitAllow; //限定可访问的IP
    private VisitorsBook vbook;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getCloseAnnounce() {
        return closeAnnounce;
    }

    public void setCloseAnnounce(String closeAnnounce) {
        this.closeAnnounce = closeAnnounce;
    }

    public String getWelcomeStr() {
        return welcomeStr;
    }

    public void setWelcomeStr(String welcomeStr) {
        this.welcomeStr = welcomeStr;
    }

    public String getIpRegScope() {
        return ipRegScope;
    }

    public void setIpRegScope(String ipRegScope) {
        this.ipRegScope = ipRegScope;
    }

    public boolean isUserRegAllow() {
        return userRegAllow;
    }

    public void setUserRegAllow(boolean userRegAllow) {
        this.userRegAllow = userRegAllow;
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

    public boolean isFreeComm() {
        return freeComm;
    }

    public void setFreeComm(boolean freeComm) {
        this.freeComm = freeComm;
    }

    public boolean isCommPassAuto() {
        return commPassAuto;
    }

    public void setCommPassAuto(boolean commPassAuto) {
        this.commPassAuto = commPassAuto;
    }

    public boolean isArtPassAuto() {
        return artPassAuto;
    }

    public void setArtPassAuto(boolean artPassAuto) {
        this.artPassAuto = artPassAuto;
    }

    public int getCodeSendMode() {
        return codeSendMode;
    }

    public void setCodeSendMode(int codeSendMode) {
        this.codeSendMode = codeSendMode;
    }

    public String getIpVisitAllow() {
        return ipVisitAllow;
    }

    public void setIpVisitAllow(String ipVisitAllow) {
        this.ipVisitAllow = ipVisitAllow;
    }

    public VisitorsBook getVbook() {
        return vbook;
    }

    public void setVbook(VisitorsBook vbook) {
        this.vbook = vbook;
    }
}
