package com.lwhtarena.company.sys.obj;

/**
 * @Author：liwh
 * @Description:
 * @Date 23:15 2018/8/6
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class LogWriteRequestArgs {

    private String remoteHost;
    private String pathInfo;
    private String requestURI;
    private String referer;
    private String ip;

    public String getRemoteHost() {
        return remoteHost;
    }

    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }

    public String getPathInfo() {
        return pathInfo;
    }

    public void setPathInfo(String pathInfo) {
        this.pathInfo = pathInfo;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
