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
public class TempletPortalMain implements Serializable {

    private long id;
    private String name;
    private boolean state;
    private boolean def;
    private boolean freeOnClouds;
    private String author;
    private String description;
    private String uuid;
    private String ownerToken;
    private String resFolder;
    private int orderNum;
    private int downs;
    private long designTime;
    private long modifyTime;
    private String sundriesTag;
    private String publicCode1;
    private String publicCode2;
    private String publicCode3;
    private String publicCode4;
    private String customFormatCode1;
    private String customFormatCode2;
    private String customFormatCode3;
    private String customFormatCode4;
    private String customFormatCode5;
    private String customFormatCode6;
    private String customFormatCode7;
    private String customFormatCode8;

    private TempletPortalSubElement elPublic;
    private TempletPortalSubElement elIndex;
    private TempletPortalSubElement elNav;
    private TempletPortalSubElement elArt;
    private TempletPortalSubElement elCorpus;
    //	private TempletPortalSubElement elSearch;
    private TempletPortalSubElement elEdit;
    private TempletPortalSubElement elExtend;

    //评论
    private TempletComment elComment;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public boolean isDef() {
        return def;
    }

    public void setDef(boolean def) {
        this.def = def;
    }

    public boolean isFreeOnClouds() {
        return freeOnClouds;
    }

    public void setFreeOnClouds(boolean freeOnClouds) {
        this.freeOnClouds = freeOnClouds;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOwnerToken() {
        return ownerToken;
    }

    public void setOwnerToken(String ownerToken) {
        this.ownerToken = ownerToken;
    }

    public String getResFolder() {
        return resFolder;
    }

    public void setResFolder(String resFolder) {
        this.resFolder = resFolder;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public int getDowns() {
        return downs;
    }

    public void setDowns(int downs) {
        this.downs = downs;
    }

    public long getDesignTime() {
        return designTime;
    }

    public void setDesignTime(long designTime) {
        this.designTime = designTime;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getSundriesTag() {
        return sundriesTag;
    }

    public void setSundriesTag(String sundriesTag) {
        this.sundriesTag = sundriesTag;
    }

    public String getPublicCode1() {
        return publicCode1;
    }

    public void setPublicCode1(String publicCode1) {
        this.publicCode1 = publicCode1;
    }

    public String getPublicCode2() {
        return publicCode2;
    }

    public void setPublicCode2(String publicCode2) {
        this.publicCode2 = publicCode2;
    }

    public String getPublicCode3() {
        return publicCode3;
    }

    public void setPublicCode3(String publicCode3) {
        this.publicCode3 = publicCode3;
    }

    public String getPublicCode4() {
        return publicCode4;
    }

    public void setPublicCode4(String publicCode4) {
        this.publicCode4 = publicCode4;
    }

    public String getCustomFormatCode1() {
        return customFormatCode1;
    }

    public void setCustomFormatCode1(String customFormatCode1) {
        this.customFormatCode1 = customFormatCode1;
    }

    public String getCustomFormatCode2() {
        return customFormatCode2;
    }

    public void setCustomFormatCode2(String customFormatCode2) {
        this.customFormatCode2 = customFormatCode2;
    }

    public String getCustomFormatCode3() {
        return customFormatCode3;
    }

    public void setCustomFormatCode3(String customFormatCode3) {
        this.customFormatCode3 = customFormatCode3;
    }

    public String getCustomFormatCode4() {
        return customFormatCode4;
    }

    public void setCustomFormatCode4(String customFormatCode4) {
        this.customFormatCode4 = customFormatCode4;
    }

    public String getCustomFormatCode5() {
        return customFormatCode5;
    }

    public void setCustomFormatCode5(String customFormatCode5) {
        this.customFormatCode5 = customFormatCode5;
    }

    public String getCustomFormatCode6() {
        return customFormatCode6;
    }

    public void setCustomFormatCode6(String customFormatCode6) {
        this.customFormatCode6 = customFormatCode6;
    }

    public String getCustomFormatCode7() {
        return customFormatCode7;
    }

    public void setCustomFormatCode7(String customFormatCode7) {
        this.customFormatCode7 = customFormatCode7;
    }

    public String getCustomFormatCode8() {
        return customFormatCode8;
    }

    public void setCustomFormatCode8(String customFormatCode8) {
        this.customFormatCode8 = customFormatCode8;
    }

    public TempletPortalSubElement getElPublic() {
        return elPublic;
    }

    public void setElPublic(TempletPortalSubElement elPublic) {
        this.elPublic = elPublic;
    }

    public TempletPortalSubElement getElIndex() {
        return elIndex;
    }

    public void setElIndex(TempletPortalSubElement elIndex) {
        this.elIndex = elIndex;
    }

    public TempletPortalSubElement getElNav() {
        return elNav;
    }

    public void setElNav(TempletPortalSubElement elNav) {
        this.elNav = elNav;
    }

    public TempletPortalSubElement getElArt() {
        return elArt;
    }

    public void setElArt(TempletPortalSubElement elArt) {
        this.elArt = elArt;
    }

    public TempletPortalSubElement getElCorpus() {
        return elCorpus;
    }

    public void setElCorpus(TempletPortalSubElement elCorpus) {
        this.elCorpus = elCorpus;
    }

    public TempletPortalSubElement getElEdit() {
        return elEdit;
    }

    public void setElEdit(TempletPortalSubElement elEdit) {
        this.elEdit = elEdit;
    }

    public TempletPortalSubElement getElExtend() {
        return elExtend;
    }

    public void setElExtend(TempletPortalSubElement elExtend) {
        this.elExtend = elExtend;
    }

    public TempletComment getElComment() {
        return elComment;
    }

    public void setElComment(TempletComment elComment) {
        this.elComment = elComment;
    }
}
