package com.lwhtarena.company.web.entities;

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
public class User extends com.lwhtarena.company.user.entities.User {

    private Role role;
    private String regCodeSendTarget;
    private long artsPassed;
    private long artsTotal;

    @Override
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getRegCodeSendTarget() {
        return regCodeSendTarget;
    }

    public void setRegCodeSendTarget(String regCodeSendTarget) {
        this.regCodeSendTarget = regCodeSendTarget;
    }

    public long getArtsPassed() {
        return artsPassed;
    }

    public void setArtsPassed(long artsPassed) {
        this.artsPassed = artsPassed;
    }

    public long getArtsTotal() {
        return artsTotal;
    }

    public void setArtsTotal(long artsTotal) {
        this.artsTotal = artsTotal;
    }
}
