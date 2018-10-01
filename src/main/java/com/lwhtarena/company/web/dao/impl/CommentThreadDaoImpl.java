package com.lwhtarena.company.web.dao.impl;

import com.lwhtarena.company.hql.entities.Rs;
import com.lwhtarena.company.hql.util.HibernateCallbackUtil;
import com.lwhtarena.company.sys.util.StringUtil;
import com.lwhtarena.company.web.dao.ICommentThreadDao;
import com.lwhtarena.company.web.entities.CommentThread;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.List;

/**
 * @Author：liwh
 * @Description:
 * @Date 17:29 2018/8/4
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class CommentThreadDaoImpl extends HibernateDaoSupport implements ICommentThreadDao {

    @Override
    public CommentThread add(CommentThread ct) {
        ct.setContent(StringUtil.htmlFilter(ct.getContent()));
        this.getHibernateTemplate().save(ct.getPoll());
        this.getHibernateTemplate().save(ct);
        return ct;
    }

    @Override
    public CommentThread findByID(long id) {
        return this.getHibernateTemplate().get(CommentThread.class, id);
    }

    @Override
    public boolean delByID(long id) {
        this.getHibernateTemplate().delete(findByID(id));

        if (findByID(id) == null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void modify(CommentThread ct) {
        this.getHibernateTemplate().update(ct);

    }

    @Override
    public Rs queryByBid(long bid, int page, int pagesize, boolean asc, int status, boolean deletedShow) {
        String statusSql;
        switch (status) {
            case -1:
                statusSql=" and c.status is false ";
                break;
            case 0:
                statusSql="";
                break;
            default:
                statusSql=" and c.status is true ";
        }
        String deletedSql="";
        if (!deletedShow) {
            deletedSql=" c.deleted=false and ";
        }
        String hql = "from CommentThread c where "+deletedSql+" c.cb="+bid+statusSql+" order by c.id desc";
        int firstPlace = 0;
        return HibernateCallbackUtil.getRs(this.getHibernateTemplate(), hql, firstPlace, page, pagesize);
    }

    @Override
    public long countByUid(long uid) {
        String hql="select count(*) from CommentThread c where c.user.id=?0";
        @SuppressWarnings("unchecked")
        List<Long> list = (List<Long>) this.getHibernateTemplate().find(hql,uid);
        return list.get(0);
    }
}
