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
public class UserCrucialInf {

    private long uid;
    private String firstMail;
    private String regDatetime;
    private String regIP;
    private String lastLoginDatetime;
    private String lastLoginIP;

    public long getUid() {
        return uid;
    }
    public void setUid(long uid) {
        this.uid = uid;
    }
    public String getFirstMail() {
        return firstMail;
    }
    public void setFirstMail(String firstMail) {
        this.firstMail = firstMail;
    }
    public String getRegIP() {
        return regIP;
    }
    public void setRegIP(String regIP) {
        this.regIP = regIP;
    }
    public String getLastLoginIP() {
        return lastLoginIP;
    }
    public void setLastLoginIP(String lastLoginIP) {
        this.lastLoginIP = lastLoginIP;
    }
    public String getRegDatetime() {
        return regDatetime;
    }
    public void setRegDatetime(String regDatetime) {
        this.regDatetime = regDatetime;
    }
    public String getLastLoginDatetime() {
        return lastLoginDatetime;
    }
    public void setLastLoginDatetime(String lastLoginDatetime) {
        this.lastLoginDatetime = lastLoginDatetime;
    }

}
