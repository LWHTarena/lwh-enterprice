package com.lwhtarena.company.user.entities;

/**
 * @Author：liwh
 * @Description:
 * @Date 10:44 2018/8/4
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class Role {

    /**标识符**/
    private long id;

    /**角色名称**/
    private String name;

    /**授权码 a1,a2, ... **/
    private String mask;

    /**状态 0：禁用;1：正常**/
    private boolean status;

    /**默认角色 0：关 1：开 **/
    private boolean def;

    /**用户数量**/
    private long counts;

    public Role() {
    }

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

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isDef() {
        return def;
    }

    public void setDef(boolean def) {
        this.def = def;
    }

    public long getCounts() {
        return counts;
    }

    public void setCounts(long counts) {
        this.counts = counts;
    }
}
