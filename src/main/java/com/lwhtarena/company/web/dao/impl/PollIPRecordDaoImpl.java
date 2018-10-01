package com.lwhtarena.company.web.dao.impl;

import com.lwhtarena.company.hql.entities.Rs;
import com.lwhtarena.company.hql.util.HibernateCallbackUtil;
import com.lwhtarena.company.web.dao.IPollIPRecordDao;
import com.lwhtarena.company.web.entities.PollIPRecord;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.List;

/**
 * @Author：liwh
 * @Description:
 * @Date 17:37 2018/8/4
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class PollIPRecordDaoImpl extends HibernateDaoSupport implements IPollIPRecordDao {

    @Override
    public PollIPRecord add(PollIPRecord pir) {
        this.getHibernateTemplate().save(pir);
        return pir;
    }

    @Override
    public PollIPRecord findLast(long pid) {
        String hql="select max(p.id) from PollIPRecord p where p.poll.id=?0";
        @SuppressWarnings("unchecked")
        List<Long> list= (List<Long>) this.getHibernateTemplate().find(hql,pid);
        if (list.size()>0) {
            long id=list.get(0);
            return this.getHibernateTemplate().get(PollIPRecord.class, id);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PollIPRecord> findSameIPAfterDatetime(long pid, long datetime,String ip) {
        String hql="from PollIPRecord p where p.poll.id="+pid+" and p.pollDatetime>"+datetime+" and p.ip=?0";
        return (List<PollIPRecord>) this.getHibernateTemplate().find(hql,ip);
    }

    @Override
    public Rs findSameIPAfterDatetime(long pid, long datetime, String ip, int page, int pagesize) {
        String hql="from PollIPRecord p where p.poll.id="+pid+" and p.pollDatetime>"+datetime+" and p.ip='"+ip+ "' order by p.id desc";
        int firstPlace = 0;
        return HibernateCallbackUtil.getRs(this.getHibernateTemplate(), hql, firstPlace, page, pagesize);
    }

    @Override
    public Rs find(long pid, int page, int pagesize) {
        String hql="from PollIPRecord p where p.poll.id="+pid + " order by p.id desc";
        int firstPlace = 0;
        return HibernateCallbackUtil.getRs(this.getHibernateTemplate(), hql, firstPlace, page, pagesize);
    }

}
