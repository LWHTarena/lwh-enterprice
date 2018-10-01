package com.lwhtarena.company.Interface;

import org.springframework.orm.hibernate5.HibernateTemplate;

/**
 * @Author：liwh
 * @Description:
 * @Date 21:08 2018/8/6
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public interface IHibernateDao {
    public abstract HibernateTemplate getTemplate();
}
