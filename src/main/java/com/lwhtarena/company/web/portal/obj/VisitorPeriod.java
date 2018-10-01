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
public class VisitorPeriod {

    private int hour;
    private long countViews;
    private long countIps;

    public int getHour() {
        return hour;
    }
    public void setHour(int hour) {
        this.hour = hour;
    }
    public long getCountViews() {
        return countViews;
    }
    public void setCountViews(long countViews) {
        this.countViews = countViews;
    }
    public long getCountIps() {
        return countIps;
    }
    public void setCountIps(long countIps) {
        this.countIps = countIps;
    }
}
