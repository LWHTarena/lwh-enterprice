package com.lwhtarena.company.sys.obj;

/**
 * @Author：liwh
 * @Description:
 * @Date 18:18 2018/8/5
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class LoginSessionTest {

    private boolean interrup;
    private LoginMark lsr;
    private String lsrStr;
    private String location;

    public boolean isInterrup() {
        return interrup;
    }

    public void setInterrup(boolean interrup) {
        this.interrup = interrup;
    }

    public LoginMark getLsr() {
        return lsr;
    }

    public void setLsr(LoginMark lsr) {
        this.lsr = lsr;
    }

    public String getLsrStr() {
        return lsrStr;
    }

    public void setLsrStr(String lsrStr) {
        this.lsrStr = lsrStr;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
