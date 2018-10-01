package com.lwhtarena.company.aop.args;

/**
 * @Author：liwh
 * @Description:
 * @Date 12:28 2018/8/5
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class CutTags {

    private boolean skip;
    private String resultPage;

    public CutTags() {
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public String getResultPage() {
        return resultPage;
    }

    public void setResultPage(String resultPage) {
        this.resultPage = resultPage;
    }
}
