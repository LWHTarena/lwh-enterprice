package com.lwhtarena.company.web.portal.obj;


import com.lwhtarena.company.sys.obj.DatetimeSpanSuffixTxt;

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
public class TempletCommentFmtRequisite {

    private String lf;
    private String funHtmlPass;
    private String imgHtmlTemplet;
    private String replyAdd;
    private String funHtmlDel;
    private String anonymity;
    private String headDef;
    private String avatarNull;
    private boolean master;
    private DatetimeSpanSuffixTxt dsst;

    public String getLf() {
        return lf;
    }

    public void setLf(String lf) {
        this.lf = lf;
    }

    public String getFunHtmlPass() {
        return funHtmlPass;
    }

    public void setFunHtmlPass(String funHtmlPass) {
        this.funHtmlPass = funHtmlPass;
    }

    public String getImgHtmlTemplet() {
        return imgHtmlTemplet;
    }

    public void setImgHtmlTemplet(String imgHtmlTemplet) {
        this.imgHtmlTemplet = imgHtmlTemplet;
    }

    public String getAvatarNull() {
        return avatarNull;
    }

    public void setAvatarNull(String avatarNull) {
        this.avatarNull = avatarNull;
    }

    public String getReplyAdd() {
        return replyAdd;
    }

    public void setReplyAdd(String replyAdd) {
        this.replyAdd = replyAdd;
    }

    public String getFunHtmlDel() {
        return funHtmlDel;
    }

    public void setFunHtmlDel(String funHtmlDel) {
        this.funHtmlDel = funHtmlDel;
    }

    public String getHeadDef() {
        return headDef;
    }

    public void setHeadDef(String headDef) {
        this.headDef = headDef;
    }

    public String getAnonymity() {
        return anonymity;
    }

    public void setAnonymity(String anonymity) {
        this.anonymity = anonymity;
    }

    public boolean isMaster() {
        return master;
    }

    public void setMaster(boolean master) {
        this.master = master;
    }

    public DatetimeSpanSuffixTxt getDsst() {
        return dsst;
    }

    public void setDsst(DatetimeSpanSuffixTxt dsst) {
        this.dsst = dsst;
    }
}
