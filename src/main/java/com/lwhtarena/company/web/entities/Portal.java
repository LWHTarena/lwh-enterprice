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

    /**网站名称**/
    private String name;

    /**网站全称(全称)**/
    private String fullName;

    /**网址**/
    private String url;

    /**状态**/
    private int status;

    /**网站关键字**/
    private String keyWords;

    /**网站描述**/
    private String description;

    /**网站主机名或域名**/
    private String host;

    /**网站关闭通知**/
    private String closeAnnounce;

    /**网站欢迎词**/
    private String welcomeStr;

    /**限定可注册IP或范围,如：192.168.222.12-192.168.222.254**/
    private String ipRegScope;

    /**允许用户注册：0：关闭 1：开启**/
    private boolean userRegAllow;

    /**允许投票 0：关闭 1：开启**/
    private boolean poll;

    /**允许评论 0：关闭 1：开启**/
    private boolean comm;

    /**匿名评论 0：关闭 1：开启**/
    private boolean freeComm;

    /**评论自动通过 0：关闭 1：开启**/
    private boolean commPassAuto;

    /**文章自动审核 0：关闭 1：开启**/
    private boolean artPassAuto;

    /**验证码发送方式**/
    private int codeSendMode;

    /**限定可访问的IP**/
    private String ipVisitAllow;


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
