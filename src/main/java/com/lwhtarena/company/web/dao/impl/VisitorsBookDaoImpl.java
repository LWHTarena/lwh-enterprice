package com.lwhtarena.company.web.dao.impl;

import com.lwhtarena.company.web.dao.IVisitorsBookDao;
import com.lwhtarena.company.web.entities.VisitorsBook;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

/**
 * @Author：liwh
 * @Description:
 * @Date 17:43 2018/8/4
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class VisitorsBookDaoImpl extends HibernateDaoSupport implements IVisitorsBookDao {

    @Override
    public void modify(VisitorsBook vbook) {
        this.getHibernateTemplate().update(vbook);

    }

    @Override
    public VisitorsBook findByID(long id) {
        return this.getHibernateTemplate().get(VisitorsBook.class, id);
    }
}
