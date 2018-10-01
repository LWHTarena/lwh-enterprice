package com.lwhtarena.company.web.entities;


import java.io.Serializable;

/**
 * @Author：liwh
 * @Description:
 * @Date 11:07 2018/8/5
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class CommentBridge implements Serializable {
    private long id;
    private long total;
    private int objType;
    private long objID;
    private User user;
    private String objTitle;
    private boolean status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
}
