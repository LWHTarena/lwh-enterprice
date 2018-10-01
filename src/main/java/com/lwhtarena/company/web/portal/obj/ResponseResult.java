package com.lwhtarena.company.web.portal.obj;

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
public class ResponseResult {

    private int result;
    private String msg;

    private String valueS1;
    private String valueS2;
    private long valueL;
    private boolean valueB;
    private int valueI;

    public int getResult() {
        return result;
    }
    public void setResult(int result) {
        this.result = result;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getValueS1() {
        return valueS1;
    }
    public void setValueS1(String valueS1) {
        this.valueS1 = valueS1;
    }
    public String getValueS2() {
        return valueS2;
    }
    public void setValueS2(String valueS2) {
        this.valueS2 = valueS2;
    }
    public long getValueL() {
        return valueL;
    }
    public void setValueL(long valueL) {
        this.valueL = valueL;
    }
    public boolean isValueB() {
        return valueB;
    }
    public void setValueB(boolean valueB) {
        this.valueB = valueB;
    }
    public int getValueI() {
        return valueI;
    }
    public void setValueI(int valueI) {
        this.valueI = valueI;
    }
}
