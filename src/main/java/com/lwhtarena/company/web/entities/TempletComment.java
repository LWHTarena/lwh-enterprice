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
public class TempletComment implements Serializable {
    private long id;
    private String formCode;
    private String itemLoopCode;
    private String actPassCode;
    private String actDelCode;
    private String actReplyCode;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFormCode() {
        return formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getItemLoopCode() {
        return itemLoopCode;
    }

    public void setItemLoopCode(String itemLoopCode) {
        this.itemLoopCode = itemLoopCode;
    }

    public String getActPassCode() {
        return actPassCode;
    }

    public void setActPassCode(String actPassCode) {
        this.actPassCode = actPassCode;
    }

    public String getActDelCode() {
        return actDelCode;
    }

    public void setActDelCode(String actDelCode) {
        this.actDelCode = actDelCode;
    }

    public String getActReplyCode() {
        return actReplyCode;
    }

    public void setActReplyCode(String actReplyCode) {
        this.actReplyCode = actReplyCode;
    }
}
