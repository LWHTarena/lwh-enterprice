package com.lwhtarena.company.web.dao.impl;

import com.lwhtarena.company.web.dao.ICommentBridgeDao;
import com.lwhtarena.company.web.entities.CommentBridge;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author：liwh
 * @Description:
 * @Date 17:28 2018/8/4
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class CommentBridgeDaoImpl extends HibernateDaoSupport implements ICommentBridgeDao {

    @Override
    public void modify(CommentBridge cb) {
        this.getHibernateTemplate().update(cb);

    }

    @Override
    public CommentBridge findByID(long id) {
        return this.getHibernateTemplate().get(CommentBridge.class, id);
    }

    @Override
    public long countByUid(long uid) {
        String hql="select count(*) from CommentBridge c where c.user.id=?0";
        @SuppressWarnings("unchecked")
        List<Long> list = (List<Long>) this.getHibernateTemplate().find(hql,uid);
        return list.get(0);
    }

}
