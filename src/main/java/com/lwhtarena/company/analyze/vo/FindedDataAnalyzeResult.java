package com.lwhtarena.company.analyze.vo;

/**
 * @Author：liwh
 * @Description:
 * @Date 20:31 2018/8/6
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class FindedDataAnalyzeResult {
    private String wholeTag;
    private DataShowParams dsp;

    public FindedDataAnalyzeResult() {
    }

    public String getWholeTag() {
        return this.wholeTag;
    }

    public void setWholeTag(String wholeTag) {
        this.wholeTag = wholeTag;
    }

    public DataShowParams getDsp() {
        return this.dsp;
    }

    public void setDsp(DataShowParams dsp) {
        this.dsp = dsp;
    }
}
