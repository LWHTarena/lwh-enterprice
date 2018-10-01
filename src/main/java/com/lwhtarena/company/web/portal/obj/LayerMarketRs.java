package com.lwhtarena.company.web.portal.obj;

import java.util.List;

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
public class LayerMarketRs {

    private int code;
    private String msg;
    private long count;
    private List<TempletPortalMainSimple> data;

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public long getCount() {
        return count;
    }
    public void setCount(long count) {
        this.count = count;
    }
    public List<TempletPortalMainSimple> getData() {
        return data;
    }
    public void setData(List<TempletPortalMainSimple> data) {
        this.data = data;
    }
}
