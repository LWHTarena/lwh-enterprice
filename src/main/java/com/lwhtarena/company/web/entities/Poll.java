package com.lwhtarena.company.web.entities;

import java.io.Serializable;

/**
 * @Author：liwh
 * @Description:
 * @Date 11:03 2018/8/5
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class Poll implements Serializable {
    private long id;
    private int objType;
    private long objID;
    private String objTitle;
    private boolean status;
    private long agrees; //支持者数
    private long antis; //反对者数
    private long passbys;//中立者数

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getObjType() {
        return objType;
    }

    public void setObjType(int objType) {
        this.objType = objType;
    }

    public long getObjID() {
        return objID;
    }

    public void setObjID(long objID) {
        this.objID = objID;
    }

    public String getObjTitle() {
        return objTitle;
    }

    public void setObjTitle(String objTitle) {
        this.objTitle = objTitle;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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
