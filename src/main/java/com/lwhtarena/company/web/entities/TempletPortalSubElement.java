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
public class TempletPortalSubElement implements Serializable {

    private long id;
    private String borderCode;
    private String cssCode;
    private String mainCode;
    private String topCode;
    private String footerCode;
    private String targetStr;
    private String titleFormat;
    private String majorLoopCodeInLump; 			// 主循环体
    private String minorLoopCodeInLump;				// 副循环体
    private String searchAreaCode;
    private String functionAreaCode;
    private String userPanelCodeLogin;
    private String userPanelCodeLogout;
    private String exclusiveCode1;
    private String exclusiveCode2;
    private String exclusiveCode3;
    private String exclusiveCode4;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBorderCode() {
        return borderCode;
    }

    public void setBorderCode(String borderCode) {
        this.borderCode = borderCode;
    }

    public String getCssCode() {
        return cssCode;
    }

    public void setCssCode(String cssCode) {
        this.cssCode = cssCode;
    }

    public String getMainCode() {
        return mainCode;
    }

    public void setMainCode(String mainCode) {
        this.mainCode = mainCode;
    }

    public String getTopCode() {
        return topCode;
    }

    public void setTopCode(String topCode) {
        this.topCode = topCode;
    }

    public String getFooterCode() {
        return footerCode;
    }

    public void setFooterCode(String footerCode) {
        this.footerCode = footerCode;
    }

    public String getTargetStr() {
        return targetStr;
    }

    public void setTargetStr(String targetStr) {
        this.targetStr = targetStr;
    }

    public String getTitleFormat() {
        return titleFormat;
    }

    public void setTitleFormat(String titleFormat) {
        this.titleFormat = titleFormat;
    }

    public String getMajorLoopCodeInLump() {
        return majorLoopCodeInLump;
    }

    public void setMajorLoopCodeInLump(String majorLoopCodeInLump) {
        this.majorLoopCodeInLump = majorLoopCodeInLump;
    }

    public String getMinorLoopCodeInLump() {
        return minorLoopCodeInLump;
    }

    public void setMinorLoopCodeInLump(String minorLoopCodeInLump) {
        this.minorLoopCodeInLump = minorLoopCodeInLump;
    }

    public String getSearchAreaCode() {
        return searchAreaCode;
    }

    public void setSearchAreaCode(String searchAreaCode) {
        this.searchAreaCode = searchAreaCode;
    }

    public String getFunctionAreaCode() {
        return functionAreaCode;
    }

    public void setFunctionAreaCode(String functionAreaCode) {
        this.functionAreaCode = functionAreaCode;
    }

    public String getUserPanelCodeLogin() {
        return userPanelCodeLogin;
    }

    public void setUserPanelCodeLogin(String userPanelCodeLogin) {
        this.userPanelCodeLogin = userPanelCodeLogin;
    }

    public String getUserPanelCodeLogout() {
        return userPanelCodeLogout;
    }

    public void setUserPanelCodeLogout(String userPanelCodeLogout) {
        this.userPanelCodeLogout = userPanelCodeLogout;
    }

    public String getExclusiveCode1() {
        return exclusiveCode1;
    }

    public void setExclusiveCode1(String exclusiveCode1) {
        this.exclusiveCode1 = exclusiveCode1;
    }

    public String getExclusiveCode2() {
        return exclusiveCode2;
    }

    public void setExclusiveCode2(String exclusiveCode2) {
        this.exclusiveCode2 = exclusiveCode2;
    }

    public String getExclusiveCode3() {
        return exclusiveCode3;
    }

    public void setExclusiveCode3(String exclusiveCode3) {
        this.exclusiveCode3 = exclusiveCode3;
    }

    public String getExclusiveCode4() {
        return exclusiveCode4;
    }

    public void setExclusiveCode4(String exclusiveCode4) {
        this.exclusiveCode4 = exclusiveCode4;
    }
}
