package com.lwhtarena.company.web.dao.impl;

import com.lwhtarena.company.web.dao.IVisitorCountInPeriodDao;
import com.lwhtarena.company.web.entities.VisitorCountInPeriod;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.List;

/**
 * @Author：liwh
 * @Description:
 * @Date 17:42 2018/8/4
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class VisitorCountInPeriodDaoImpl extends HibernateDaoSupport implements IVisitorCountInPeriodDao {

    @Override
    public VisitorCountInPeriod add(VisitorCountInPeriod vcip) {
        VisitorCountInPeriod vcipfind=findByPeriod(vcip.getVa().getId(),vcip.getHour());
        if (vcipfind==null) {
            this.getHibernateTemplate().save(vcip);
            return vcip;
        }else {
            return vcipfind;
        }
    }

    @Override
    public void modify(VisitorCountInPeriod vcip) {
        this.getHibernateTemplate().update(vcip);

    }

    @Override
    public VisitorCountInPeriod findByID(long id) {
        return this.getHibernateTemplate().get(VisitorCountInPeriod.class, id);
    }

    @Override
    public VisitorCountInPeriod findByPeriod(long vid, int hour) {
        String hql="from VisitorCountInPeriod v where v.va.id="+vid+" and v.hour=?0";
        @SuppressWarnings("unchecked")
        List<VisitorCountInPeriod> list= (List<VisitorCountInPeriod>) this.getHibernateTemplate().find(hql,hour);
        if (list.size()>0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public long count(long vid,int hour) {
        String hql="select count(*) from VisitorCountInPeriod v where v.va.vbook.id="+vid+" and v.hour=?0";
        @SuppressWarnings("unchecked")
        List<Long> list = (List<Long>) this.getHibernateTemplate().find(hql,hour);
        if (list.size()>0) {
            return list.get(0);
        }
        return 0;
    }

    @Override
    public long sum(long vid,int hour,int mode) {
        String col;
        if (mode==0) {
            col="totalView";
        }else {
            col="totalIP";
        }
        String hql="select sum(v."+col+") from VisitorCountInPeriod v where v.va.vbook.id="+vid+" and v.hour=" +hour;
//		System.out.println("hql:"+hql);
        if (this.getHibernateTemplate().find(hql)!=null && this.getHibernateTemplate().find(hql).get(0)!=null){
            long sum = (Long) this.getHibernateTemplate().find(hql).get(0);
            return sum;
        }
        return 0L;

    }
}
