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
public class PollIPRecord implements Serializable {
    private long id;
    private String ip;
    private int result;
    private String ipfrom;
    private String reffer;
    private String visitUrl;
    private long pollDatetime;
    private Poll poll;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getIpfrom() {
        return ipfrom;
    }

    public void setIpfrom(String ipfrom) {
        this.ipfrom = ipfrom;
    }

    public String getReffer() {
        return reffer;
    }

    public void setReffer(String reffer) {
        this.reffer = reffer;
    }

    public String getVisitUrl() {
        return visitUrl;
    }

    public void setVisitUrl(String visitUrl) {
        this.visitUrl = visitUrl;
    }

    public long getPollDatetime() {
        return pollDatetime;
    }

    public void setPollDatetime(long pollDatetime) {
        this.pollDatetime = pollDatetime;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }
}
