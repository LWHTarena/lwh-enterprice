package com.lwhtarena.company.web.dao.impl;

import com.lwhtarena.company.web.dao.IPortalDao;
import com.lwhtarena.company.web.entities.Portal;
import com.lwhtarena.company.web.entities.VisitorsBook;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

/**
 * @Author：liwh
 * @Description:
 * @Date 17:33 2018/8/4
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class PortalDaoImpl extends HibernateDaoSupport implements IPortalDao {

    @Override
    public Portal add(Portal portal) {
        portal.setId(1L);
        portal.setStatus(0);
        portal.setComm(true);
        portal.setPoll(true);
        portal.setUserRegAllow(true);
        portal.setCommPassAuto(true);
        VisitorsBook vbook = new VisitorsBook();
        vbook.setObjTitle(portal.getName());
        vbook.setObjType(0);
        this.getHibernateTemplate().save(vbook);
        portal.setVbook(vbook);
        this.getHibernateTemplate().save(portal);
        return portal;
    }

    @Override
    public Portal findByID(long id) {
        return this.getHibernateTemplate().get(Portal.class, id);
    }

    @Override
    public void modify(Portal portal) {
        this.getHibernateTemplate().update(portal);

    }

    @Override
    public Portal query() {
        Portal portal=this.getHibernateTemplate().get(Portal.class, 1L);
        if (portal==null){
            portal = new Portal();
            portal.setName("");
            portal.setStatus(0);
            portal.setComm(true);
            portal.setPoll(true);
            portal.setUserRegAllow(true);
            portal.setCommPassAuto(true);
			/*VisitorsBook vbook = new VisitorsBook();
			portal.setVbook(vbook);*/
            portal=add(portal);
        }
        return portal;

    }
}
