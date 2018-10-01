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
public class PortalStatInfo {

    private long artsTotal;
    private long artsPassed;
    private long userTotal;
    private long userPassed;
    private long views;
    private long ips;

    public long getArtsTotal() {
        return artsTotal;
    }
    public void setArtsTotal(long artsTotal) {
        this.artsTotal = artsTotal;
    }
    public long getArtsPassed() {
        return artsPassed;
    }
    public void setArtsPassed(long artsPassed) {
        this.artsPassed = artsPassed;
    }
    public long getUserTotal() {
        return userTotal;
    }
    public void setUserTotal(long userTotal) {
        this.userTotal = userTotal;
    }
    public long getUserPassed() {
        return userPassed;
    }
    public void setUserPassed(long userPassed) {
        this.userPassed = userPassed;
    }

    public long getViews() {
        return views;
    }
    public void setViews(long views) {
        this.views = views;
    }
    public long getIps() {
        return ips;
    }
    public void setIps(long ips) {
        this.ips = ips;
    }
    @Override
    public String toString() {
        return "PortalStatInfo [artsTotal=" + artsTotal + ", artsPassed=" + artsPassed + ", userTotal=" + userTotal
                + ", userPassed=" + userPassed + "]";
    }
}
