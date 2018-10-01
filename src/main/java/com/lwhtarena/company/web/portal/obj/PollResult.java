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
public class PollResult {

    private int status;
    private long pid;
    private long agrees;			//支持者数
    private long antis;				//反对者数
    private long passbys;			//中立者数

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public long getPid() {
        return pid;
    }
    public void setPid(long pid) {
        this.pid = pid;
    }
    public long getAgrees() {
        return agrees;
    }
    public void setAgrees(long agrees) {
        this.agrees = agrees;
    }
    public long getAntis() {
        return antis;
    }
    public void setAntis(long antis) {
        this.antis = antis;
    }
    public long getPassbys() {
        return passbys;
    }
    public void setPassbys(long passbys) {
        this.passbys = passbys;
    }
}
