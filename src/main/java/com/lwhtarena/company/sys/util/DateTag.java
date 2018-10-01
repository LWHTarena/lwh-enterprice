package com.lwhtarena.company.sys.util;


import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * @Author：liwh
 * @Description:
 * @Date 11:13 2018/8/4
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class DateTag extends TagSupport {
    private String value;
    private String fmtstr;

    public DateTag() {
    }

    @Override
    public int doStartTag() throws JspException {
        long time = Long.valueOf(value).longValue();
        if (time < 1000000000000L) {
            time *= 1000L;
        }

        String s = TimeUtil.coverLongToStr(Long.valueOf(time), fmtstr);
        try {
            pageContext.getOut().write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.doStartTag();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getFmtstr() {
        return fmtstr;
    }

    public void setFmtstr(String fmtstr) {
        this.fmtstr = fmtstr;
    }
}
