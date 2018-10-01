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
public class TempletDownCode implements Serializable {
    private long id;
    private String code;
    private long templetID;
    private int typeNum;
    private long createDate;
    private long downDate;
    private String createIP;
    private String createHost;
    private String downIP;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getTempletID() {
        return templetID;
    }

    public void setTempletID(long templetID) {
        this.templetID = templetID;
    }

    public int getTypeNum() {
        return typeNum;
    }

    public void setTypeNum(int typeNum) {
        this.typeNum = typeNum;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public long getDownDate() {
        return downDate;
    }

    public void setDownDate(long downDate) {
        this.downDate = downDate;
    }

    public String getCreateIP() {
        return createIP;
    }

    public void setCreateIP(String createIP) {
        this.createIP = createIP;
    }

    public String getCreateHost() {
        return createHost;
    }

    public void setCreateHost(String createHost) {
        this.createHost = createHost;
    }

    public String getDownIP() {
        return downIP;
    }

    public void setDownIP(String downIP) {
        this.downIP = downIP;
    }
}
