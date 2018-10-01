package com.lwhtarena.company.web.dao.impl;

import com.lwhtarena.company.web.dao.IHtmlFileStaticDao;
import com.lwhtarena.company.web.entities.HtmlFileStatic;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

/**
 * @Author：liwh
 * @Description:
 * @Date 17:32 2018/8/4
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class HtmlFileStaticDaoImpl extends HibernateDaoSupport implements IHtmlFileStaticDao {

    @Override
    public void modify(HtmlFileStatic hfs) {
        this.getHibernateTemplate().update(hfs);

    }

    @Override
    public HtmlFileStatic findByID(long id) {
        return this.getHibernateTemplate().get(HtmlFileStatic.class, id);
    }
}
