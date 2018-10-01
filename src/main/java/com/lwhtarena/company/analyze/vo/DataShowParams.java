package com.lwhtarena.company.analyze.vo;

/**
 * @Author：liwh
 * @Description:
 * @Date 20:30 2018/8/6
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class DataShowParams {

    private String dataSource;
    private String formatSource;
    private int fid;
    private String loopFormatStr;
    private String subFormatSource;
    private int subFid;
    private String subLoopFormatStr;
    private long gid;
    private int personal;
    private int single;
    private long singleID;
    private int curpage;
    private int pagesize;
    private int order;
    private int firstResult;
    private int titleLen;
    private int hrefLen;
    private int permit;
    private int top;
    private int soul;
    private int img;
    private int spare1;
    private int spare2;

    public DataShowParams() {
    }

    public String getDataSource() {
        return this.dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getFormatSource() {
        return this.formatSource;
    }

    public void setFormatSource(String formatSource) {
        this.formatSource = formatSource;
    }

    public int getFid() {
        return this.fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getLoopFormatStr() {
        return this.loopFormatStr;
    }

    public void setLoopFormatStr(String loopFormatStr) {
        this.loopFormatStr = loopFormatStr;
    }

    public String getSubFormatSource() {
        return this.subFormatSource;
    }

    public void setSubFormatSource(String subFormatSource) {
        this.subFormatSource = subFormatSource;
    }

    public int getSubFid() {
        return this.subFid;
    }

    public void setSubFid(int subFid) {
        this.subFid = subFid;
    }

    public String getSubLoopFormatStr() {
        return this.subLoopFormatStr;
    }

    public void setSubLoopFormatStr(String subLoopFormatStr) {
        this.subLoopFormatStr = subLoopFormatStr;
    }

    public long getGid() {
        return this.gid;
    }

    public void setGid(long gid) {
        this.gid = gid;
    }

    public int getPersonal() {
        return this.personal;
    }

    public void setPersonal(int personal) {
        this.personal = personal;
    }

    public int getSingle() {
        return this.single;
    }

    public void setSingle(int single) {
        this.single = single;
    }

    public long getSingleID() {
        return this.singleID;
    }

    public void setSingleID(long singleID) {
        this.singleID = singleID;
    }

    public int getCurpage() {
        return this.curpage;
    }

    public void setCurpage(int curpage) {
        this.curpage = curpage;
    }

    public int getPagesize() {
        return this.pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getFirstResult() {
        return this.firstResult;
    }

    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }

    public int getTitleLen() {
        return this.titleLen;
    }

    public void setTitleLen(int titleLen) {
        this.titleLen = titleLen;
    }

    public int getHrefLen() {
        return this.hrefLen;
    }

    public void setHrefLen(int hrefLen) {
        this.hrefLen = hrefLen;
    }

    public int getPermit() {
        return this.permit;
    }

    public void setPermit(int permit) {
        this.permit = permit;
    }

    public int getTop() {
        return this.top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getSoul() {
        return this.soul;
    }

    public void setSoul(int soul) {
        this.soul = soul;
    }

    public int getImg() {
        return this.img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getSpare1() {
        return this.spare1;
    }

    public void setSpare1(int spare1) {
        this.spare1 = spare1;
    }

    public int getSpare2() {
        return this.spare2;
    }

    public void setSpare2(int spare2) {
        this.spare2 = spare2;
    }

    public String toString() {
        return "DataShowParams [dataSource=" + this.dataSource + ", formatSource=" + this.formatSource + ", fid=" + this.fid + ", loopFormatStr=" + this.loopFormatStr + ", gid=" + this.gid + ", curpage=" + this.curpage + ", pagesize=" + this.pagesize + ", order=" + this.order + ", firstResult=" + this.firstResult + ", titleLen=" + this.titleLen + ", hrefLen=" + this.hrefLen + "]";
    }
}
