package com.lwhtarena.company.web.dao.impl;

import com.lwhtarena.company.web.dao.IArticleAttaDao;
import com.lwhtarena.company.web.entities.ArticleAtta;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.List;

/**
 * @Author：liwh
 * @Description:
 * @Date 17:27 2018/8/4
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class ArticleAttrDaoImpl extends HibernateDaoSupport implements IArticleAttaDao {

    @Override
    public ArticleAtta add(ArticleAtta aa) {
        List<ArticleAtta> list=findByArticleAndFileID(aa.getArticle().getId(),aa.getUf().getId());
        if (list.isEmpty() || list.size()==0) {
            this.getHibernateTemplate().save(aa);
            return aa;
        }else {
            return null;
        }

    }

    @Override
    public ArticleAtta findByID(long id) {
        return this.getHibernateTemplate().get(ArticleAtta.class, id);
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
    public List<ArticleAtta> findByArticleID(long aid) {
        String hql="from ArticleAtta a where a.article.id="+aid;
        @SuppressWarnings("unchecked")
        List<ArticleAtta> list = (List<ArticleAtta>) this.getHibernateTemplate().find(hql);
        return list;
    }

    @Override
    public List<ArticleAtta> findByArticleAndFileID(long aid, long uid) {
        String hql="from ArticleAtta a where a.article.id="+aid + " and a.uf.id="+uid;
        @SuppressWarnings("unchecked")
        List<ArticleAtta> list = (List<ArticleAtta>) this.getHibernateTemplate().find(hql);
        return list;
    }

}
