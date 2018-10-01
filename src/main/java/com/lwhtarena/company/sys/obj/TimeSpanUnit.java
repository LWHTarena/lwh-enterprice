package com.lwhtarena.company.sys.obj;

/**
 * @Author：liwh
 * @Description:
 * @Date 23:17 2018/8/6
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class TimeSpanUnit {

    private long seconds;
    private int spanSize;
    private String spanTitle;

    public long getSeconds() {
        return this.seconds;
    }

    public void setSeconds(long seconds) {
        this.seconds = seconds;
    }

    public int getSpanSize() {
        return this.spanSize;
    }

    public void setSpanSize(int spanSize) {
        this.spanSize = spanSize;
    }

    public String getSpanTitle() {
        return this.spanTitle;
    }

    public void setSpanTitle(String spanTitle) {
        this.spanTitle = spanTitle;
    }
}
