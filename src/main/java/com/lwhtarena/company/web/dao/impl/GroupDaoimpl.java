package com.lwhtarena.company.web.dao.impl;

import com.lwhtarena.company.rank.util.RankUnitUtil;
import com.lwhtarena.company.web.dao.IGroupDao;
import com.lwhtarena.company.web.entities.ArticleGroup;
import com.lwhtarena.company.web.entities.VisitorsBook;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.List;

/**
 * @Author：liwh
 * @Description:
 * @Date 17:31 2018/8/4
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class GroupDaoimpl extends HibernateDaoSupport implements IGroupDao {

    @Override
    public long add(ArticleGroup g) {
        VisitorsBook vbook = new VisitorsBook();
        vbook.setObjType(1);
        vbook.setObjTitle(g.getName());
        this.getHibernateTemplate().save(vbook);
        g.setVbook(vbook);
        g = (ArticleGroup) RankUnitUtil.add(this.getHibernateTemplate(), g);
        changed(g);
        return g.getId();
    }

    @Override
    public void modify(ArticleGroup g) {

        RankUnitUtil.modify(this.getHibernateTemplate(), g);

        ArticleGroup changed=findByID(g.getId());
        g.setFootLeft(changed.getFootLeft());
        g.setFootRight(changed.getFootRight());
        g.setParent(changed.getParent());
        g.setOrderStr(changed.getOrderStr());
        g.setVbook(changed.getVbook());


        this.getHibernateTemplate().clear();
        this.getHibernateTemplate().update(g);
        this.getHibernateTemplate().flush();

        VisitorsBook vbook = g.getVbook();
        if (g!=null && vbook==null) {
            vbook = new VisitorsBook();
            vbook.setObjType(1);
            vbook.setObjTitle(g.getName());
            this.getHibernateTemplate().save(vbook);
            g.setVbook(vbook);
        }
        vbook.setObjTitle(g.getName());

        this.getHibernateTemplate().update(vbook);
        changed(changed);

    }

    @Override
    public ArticleGroup findByID(long id) {
        return this.getHibernateTemplate().get(ArticleGroup.class, id);
    }

    @Override
    public boolean delByID(long id) {
        ArticleGroup g = findByID(id);
        if (g==null) {
            return false;
        }
        VisitorsBook vbook=g.getVbook();
        changed(g);
        int r=RankUnitUtil.del(this.getHibernateTemplate(), g);
        if (r<0) {
            return false;
        }else {
            this.getHibernateTemplate().delete(vbook);
            return true;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ArticleGroup> queryByParentID(long parentID,boolean showPosterity,boolean gather,int status) {
        List<ArticleGroup> list;
        ArticleGroup parent=findByID(parentID);
        String hql;

        String statusSql;
        switch (status) {
            case -1:
                statusSql=" g.status is false ";
                break;
            case 0:
                statusSql="";
                break;
            default:
                statusSql=" g.status is true ";
        }

        if (gather) {
            if (statusSql!=null && !statusSql.trim().equals("")){
                statusSql = statusSql + " and g.gather is true ";
            }else {
                statusSql =  " g.gather is true ";
            }
        }
        if (parentID > 0L && findByID(parentID)!=null) {
            if (statusSql!=null && !statusSql.trim().equals("")){
                statusSql = statusSql + " and ";
            }
            if (showPosterity) {
                hql="from ArticleGroup g where " + statusSql + " g.footLeft > "+parent.getFootLeft() + " and g.footRight < " + parent.getFootRight()+" order by g.orderStr asc";
            }else {

                hql="from ArticleGroup g where " + statusSql + " g.parent.id = " + parentID + " order by g.orderStr asc";
            }

        }else {

            if (showPosterity) {
                if (statusSql!=null && !statusSql.trim().equals("")){
                    statusSql = " where " + statusSql;
                }
                hql="from ArticleGroup g " + statusSql + " order by g.orderStr asc";
            }else {
                if (statusSql!=null && !statusSql.trim().equals("")){
                    statusSql = " and " + statusSql;
                }
                hql="from ArticleGroup g  where (g.parent is null) " + statusSql + " order by g.orderStr asc";
            }
        }
        this.getHibernateTemplate().clear();
        list = (List<ArticleGroup>) this.getHibernateTemplate().find(hql);

        return list;

    }


    @SuppressWarnings("unchecked")
    @Override
    public List<ArticleGroup> queryGatherByParentID(long parentID) {
        List<ArticleGroup> list;
        String hql;

        if (parentID > 0L && findByID(parentID)!=null) {
            hql="from ArticleGroup g where g.status is true and g.gather is true  and g.parent.id = " + parentID + " order by g.orderStr asc";

        }else {
            hql="from ArticleGroup g  where (g.parent is null) and g.status is true and g.gather is true order by g.orderStr asc";
        }
        this.getHibernateTemplate().clear();
        list = (List<ArticleGroup>) this.getHibernateTemplate().find(hql);

        return list;
    }

    @Override
    public HibernateTemplate ht() {
        return this.getHibernateTemplate();
    }

    @Override
    public void move(ArticleGroup g, int offset) {
        changed(g);
        RankUnitUtil.move(this.getHibernateTemplate(), g, null, offset);

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ArticleGroup> queryParentBySubID(long subID, int status) {
        ArticleGroup currGroup=findByID(subID);
        String statusSql;
        switch (status) {
            case -1:
                statusSql=" g.status is false ";
                break;
            case 0:
                statusSql="";
                break;
            default:
                statusSql=" g.status is true ";
        }

        if (statusSql!=null && !statusSql.trim().equals("")){
            statusSql = statusSql + " and ";
        }

        String hql="from ArticleGroup g where " + statusSql + " g.footLeft < "+currGroup.getFootLeft() + " and g.footRight > " + currGroup.getFootRight()+" order by g.orderStr asc";
        List<ArticleGroup> list;
        list = (List<ArticleGroup>) this.getHibernateTemplate().find(hql);

        return list;
    }

    @Override
    public void changed(ArticleGroup g) {
        String hql="update ArticleGroup g set g.changed=true where g.footLeft <= "+g.getFootLeft() + " and g.footRight >= " + g.getFootRight();
        this.getHibernateTemplate().bulkUpdate(hql);

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ArticleGroup> queryChanged() {
        String hql="from ArticleGroup g where g.status is true and g.open is true and staticPage is true and g.changed is true";
        List<ArticleGroup> list;
        list = (List<ArticleGroup>) this.getHibernateTemplate().find(hql);
        return list;
    }
}
