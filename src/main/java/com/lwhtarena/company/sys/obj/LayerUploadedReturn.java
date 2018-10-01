package com.lwhtarena.company.sys.obj;

/**
 * @Author：liwh
 * @Description:
 * @Date 23:13 2018/8/6
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class LayerUploadedReturn {

    private int code;
    private String msg;
    private LayerUploadReturnData data;

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

    public LayerUploadReturnData getData() {
        return data;
    }

    public void setData(LayerUploadReturnData data) {
        this.data = data;
    }
}
