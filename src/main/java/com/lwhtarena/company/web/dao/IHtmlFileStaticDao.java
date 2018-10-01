package com.lwhtarena.company.web.dao;

import com.lwhtarena.company.web.entities.HtmlFileStatic;

/**
 * @Author：liwh
 * @Description:
 * @Date 17:17 2018/8/4
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public interface IHtmlFileStaticDao {
    public void modify(HtmlFileStatic hfs);
    public HtmlFileStatic findByID(long id);
}
