package com.lwhtarena.company.sys.obj;

import com.lwhtarena.company.Interface.IHibernateDao;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

/**
 * @Author：liwh
 * @Description:
 * @Date 23:08 2018/8/6
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class HibernateTemplateObj extends HibernateDaoSupport implements IHibernateDao {

    public HibernateTemplateObj() {
    }

    @Override
    public HibernateTemplate getTemplate() {
        return this.getHibernateTemplate();
    }
}
