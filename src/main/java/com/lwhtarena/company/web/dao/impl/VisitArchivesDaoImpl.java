package com.lwhtarena.company.web.dao.impl;

import com.lwhtarena.company.web.dao.IVisitArchivesDao;
import com.lwhtarena.company.web.entities.VisitArchives;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.List;

/**
 * @Author：liwh
 * @Description:
 * @Date 17:41 2018/8/4
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class VisitArchivesDaoImpl extends HibernateDaoSupport implements IVisitArchivesDao {

    @Override
    public VisitArchives add(VisitArchives va) {
        VisitArchives vafind=findByDayKey(va.getVbook().getId(),va.getDayKey());
        if (vafind==null) {
            this.getHibernateTemplate().save(va);
            return va;
        }else {
            return vafind;
        }
    }


    @Override
    public void modify(VisitArchives va) {
        this.getHibernateTemplate().update(va);

    }

    @Override
    public VisitArchives findByID(long id) {
        return this.getHibernateTemplate().get(VisitArchives.class, id);
    }

    @Override
    public VisitArchives findByDayKey(long vid,int dayKey) {
        String hql="from VisitArchives v where v.vbook.id="+vid+" and v.dayKey=?0";
        @SuppressWarnings("unchecked")
        List<VisitArchives> list= (List<VisitArchives>) this.getHibernateTemplate().find(hql,dayKey);
        if (list.size()>0) {
            return list.get(0);
        }
        return null;
    }
}
