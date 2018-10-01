package com.lwhtarena.company.sys.obj;


/**
 * @Author：liwh
 * @Description:
 * @Date 23:13 2018/8/6
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class LayuiUploadedResult {

    private int code;
    private String msg;
    private Data data;

    public LayuiUploadedResult() {
        Data d = new Data();
        setData(d);
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Data getData() {
        return this.data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}