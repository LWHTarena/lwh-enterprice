package com.lwhtarena.company.web.dao.impl;

import com.lwhtarena.company.web.dao.IPollDao;
import com.lwhtarena.company.web.entities.Poll;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

/**
 * @Author：liwh
 * @Description:
 * @Date 17:32 2018/8/4
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class PollDaoImpl extends HibernateDaoSupport implements IPollDao {

    @Override
    public Poll poll(Poll poll, int status) {
        switch (status) {
            case -1:
                poll.setAntis(poll.getAntis()+1);
                break;
            case 1:
                poll.setAgrees(poll.getAgrees()+1);
                break;
            default:
                poll.setPassbys(poll.getPassbys()+1);
        }
        this.getHibernateTemplate().update(poll);
        return poll;
    }

    @Override
    public Poll findByID(long id) {
        return this.getHibernateTemplate().get(Poll.class, id);
    }
}
