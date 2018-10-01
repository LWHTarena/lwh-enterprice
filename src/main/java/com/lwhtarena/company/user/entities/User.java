package com.lwhtarena.company.user.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @Author：liwh
 * @Description:
 * @Date 10:43 2018/8/4
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class User {

    private long id;
    @NotEmpty
    @Size(max = 10,min = 3)
    private String username;
    private String uuid;
    private String avatarUrl;
    private String truename;
    private String password;
    private String pwdAtCreate;
    private String salt;
    private String email;
    private String mobile;
    private long pwdChangeTime;
    private long lastLoginTime;
    private String lastLoginIP;
    private long createTime;
    private String createIP;
    private boolean state;
    private String entitesName;
    private Role role;

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPwdAtCreate() {
        return pwdAtCreate;
    }

    public void setPwdAtCreate(String pwdAtCreate) {
        this.pwdAtCreate = pwdAtCreate;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public long getPwdChangeTime() {
        return pwdChangeTime;
    }

    public void setPwdChangeTime(long pwdChangeTime) {
        this.pwdChangeTime = pwdChangeTime;
    }

    public long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIP() {
        return lastLoginIP;
    }

    public void setLastLoginIP(String lastLoginIP) {
        this.lastLoginIP = lastLoginIP;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getCreateIP() {
        return createIP;
    }

    public void setCreateIP(String createIP) {
        this.createIP = createIP;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getEntitesName() {
        return entitesName;
    }

    public void setEntitesName(String entitesName) {
        this.entitesName = entitesName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
