package com.lwhtarena.company.sys.obj;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author：liwh
 * @Description:
 * @Date 23:17 2018/8/6
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class SecFilePosition {

    private HttpServletRequest request;
    private String secFileName;

    public HttpServletRequest getRequest() {
        return this.request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public String getSecFileName() {
        return this.secFileName;
    }

    public void setSecFileName(String secFileName) {
        this.secFileName = secFileName;
    }
}
