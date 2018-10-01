package com.lwhtarena.company.web.dao.impl;

import com.lwhtarena.company.hql.entities.Rs;
import com.lwhtarena.company.hql.util.HibernateCallbackUtil;
import com.lwhtarena.company.hql.util.RsUtil;
import com.lwhtarena.company.web.common.ArticleUtil;
import com.lwhtarena.company.web.dao.IArticleDao;
import com.lwhtarena.company.web.entities.*;
import com.lwhtarena.company.web.portal.obj.PortalStatInfo;
//import org.hibernate.search.FullTextQuery;
//import org.hibernate.search.FullTextSession;
//import org.hibernate.search.Search;
//import org.hibernate.search.exception.EmptyQueryException;
//import org.hibernate.search.query.dsl.QueryBuilder;
import org.apache.lucene.search.Query;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author：liwh
 * @Description:
 * @Date 17:28 2018/8/4
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class ArticleDaoImpl extends HibernateDaoSupport implements IArticleDao {

    @Override
    public Article add(Article art) {
        VisitorsBook vbook = art.getVbook();
        Poll poll=art.getPoll();
        CommentBridge cb=art.getCb();
        HtmlFileStatic hfs=art.getHfs();
        this.getHibernateTemplate().save(vbook);
        this.getHibernateTemplate().save(cb);
        this.getHibernateTemplate().save(poll);
        this.getHibernateTemplate().save(hfs);
        this.getHibernateTemplate().save(art);
        return art;
    }

    @Override
    public void modify(Article art) {
        VisitorsBook vbook = art.getVbook();
        vbook.setObjTitle(art.getSubject());
        Poll poll = art.getPoll();
        CommentBridge cb=art.getCb();
        HtmlFileStatic hfs=art.getHfs();
        if (hfs==null) {

            hfs=new HtmlFileStatic();
            this.getHibernateTemplate().save(hfs);
            art.setHfs(hfs);
        }
        this.getHibernateTemplate().update(cb);
        this.getHibernateTemplate().update(poll);
        this.getHibernateTemplate().update(vbook);
        this.getHibernateTemplate().update(hfs);
        this.getHibernateTemplate().update(art);
        this.getHibernateTemplate().flush();

    }

    @Override
    public Article findByID(long id) {
        return this.getHibernateTemplate().get(Article.class, id);
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
    public Rs queryByGid(long gid,int firstResult, int page, int pagesize,boolean asc,int status,int img) {
        String ascCmd;
        if (asc) {
            ascCmd=" asc ";
        }else {
            ascCmd=" desc ";
        }
        String gCmd="";
        if (gid>0L) {
            ArticleGroup g = (ArticleGroup) this.getHibernateTemplate().get(
                    ArticleGroup.class, gid);
            if (g!=null){
                gCmd = " where a.agroup.footLeft >= " + g.getFootLeft() + " and a.agroup.footRight <= " +g.getFootRight();
            }

        }

        if (gCmd.equals("") && gid!=0){
            //gCmd=" where 1<>1 ";
            return RsUtil.init(1, 1, 0);
        }

        String statusSql;
        switch (status) {
            case -1:
                statusSql=" a.status is false ";
                break;
            case 0:
                statusSql="";
                break;
            default:
                statusSql=" a.status is true ";
        }
        if (!statusSql.trim().equals("")) {
            if (gCmd.trim().equals("")){
                gCmd = " where " + statusSql;
            }else {
                gCmd = gCmd+ " and " + statusSql;
            }
        }

        if (img==1) {
            if (!gCmd.trim().equals("")){
                gCmd+=" and ((a.thumbnail is not null and trim(a.thumbnail)<>'') or (a.titleImg is not null and trim(a.titleImg)<>'')) ";
            }else{
                gCmd+=" where ((a.thumbnail is not null and trim(a.thumbnail)<>'') or (a.titleImg is not null and trim(a.titleImg)<>'')) ";
            }
        }

        String hql = "from Article a  " + gCmd + " order by a.topOne desc, a.creationTime "+ascCmd+",a.id " + ascCmd;
        return HibernateCallbackUtil.getRs(this.getHibernateTemplate(), hql, firstResult, page, pagesize);
    }

    @Override
    public Rs queryByUid(long uid, long gid, int firstResult, int page, int pagesize, boolean asc, int status) {
        String ascCmd;
        if (asc) {
            ascCmd=" asc ";
        }else {
            ascCmd=" desc ";
        }
        String gCmd="";


        if (uid==0){

            return RsUtil.init(1, 1, 0);
        }

        gCmd = " where a.user.id = " + uid;


        if (gid>0L) {
            ArticleGroup g = (ArticleGroup) this.getHibernateTemplate().get(
                    ArticleGroup.class, gid);
            if (g!=null){
                gCmd = gCmd + " and a.agroup.footLeft >= " + g.getFootLeft() + " and a.agroup.footRight <= " +g.getFootRight();
            }

        }

        String statusSql;
        switch (status) {
            case -1:
                statusSql=" a.status is false ";
                break;
            case 0:
                statusSql="";
                break;
            default:
                statusSql=" a.status is true ";
        }
        if (!statusSql.trim().equals("")) {
            if (gCmd.trim().equals("")){
                gCmd = " where " + statusSql;
            }else {
                gCmd = gCmd+ " and " + statusSql;
            }
        }

        String hql = "from Article a  " + gCmd + " order by a.creationTime "+ascCmd+",a.id " + ascCmd;
        return HibernateCallbackUtil.getRs(this.getHibernateTemplate(), hql, firstResult, page, pagesize);
    }

    @Override
    public Rs hotByGid(long gid,int firstResult, int page, int pagesize) {
        String gCmd="";
        if (gid>0L) {
            ArticleGroup g = (ArticleGroup) this.getHibernateTemplate().get(
                    ArticleGroup.class, gid);
            if (g!=null){
                gCmd = " and a.agroup.footLeft >= " + g.getFootLeft() + " and a.agroup.footRight <= " +g.getFootRight();
            }

        }
        String hql = "from Article a  where a.status is true "+gCmd+" order by (a.poll.agrees/( TO_DAYS(NOW()) - (TO_DAYS(a.creationTime)-1) )) desc,(a.vbook.ipOwn/( TO_DAYS(NOW()) - (TO_DAYS(a.creationTime)-1) )) desc, a.id desc";
        return HibernateCallbackUtil.getRs(this.getHibernateTemplate(), hql, firstResult, page, pagesize);
    }

    //点击量
    @Override
    public Rs clicksByGid(long gid,int firstResult, int page, int pagesize) {
        String gCmd="";
        if (gid>0L) {
            ArticleGroup g = (ArticleGroup) this.getHibernateTemplate().get(
                    ArticleGroup.class, gid);
            if (g!=null){
                gCmd = " and a.agroup.footLeft >= " + g.getFootLeft() + " and a.agroup.footRight <= " +g.getFootRight();
            }

        }
        String hql = "from Article a  where a.status is true "+gCmd+" order by a.vbook.ipOwn desc";
        return HibernateCallbackUtil.getRs(this.getHibernateTemplate(), hql, firstResult, page, pagesize);
    }

    @Override
    public Rs randByGid(long gid, int page, int pagesize) {
        String gCmd="";
        if (gid>0L) {
            ArticleGroup g = (ArticleGroup) this.getHibernateTemplate().get(
                    ArticleGroup.class, gid);
            if (g!=null){
                gCmd = " and a.agroup.footLeft >= " + g.getFootLeft() + " and a.agroup.footRight <= " +g.getFootRight();
            }

        }
        String hql = "from Article a  where a.status is true "+gCmd+" order by rand()";
        return HibernateCallbackUtil.getRs(this.getHibernateTemplate(), hql, 0, page, pagesize);
    }

    @Override
    public Rs hotByUid(long uid,long gid,int firstResult, int page, int pagesize) {
        String gCmd="";
        if (gid>0L) {
            ArticleGroup g = (ArticleGroup) this.getHibernateTemplate().get(
                    ArticleGroup.class, gid);
            if (g!=null){
                gCmd = " and a.agroup.footLeft >= " + g.getFootLeft() + " and a.agroup.footRight <= " +g.getFootRight();
            }

        }
        String hql = "from Article a  where a.status is true and a.user.id="+uid+gCmd+" order by (a.poll.agrees/( TO_DAYS(NOW()) - (TO_DAYS(a.creationTime)-1) )) desc,(a.vbook.ipOwn/( TO_DAYS(NOW()) - (TO_DAYS(a.creationTime)-1) )) desc, a.id desc";
        return HibernateCallbackUtil.getRs(this.getHibernateTemplate(), hql, firstResult, page, pagesize);
    }


    @Override
    public Rs clicksByUid(long uid,long gid,int firstResult, int page, int pagesize) {
        String gCmd="";
        if (gid>0L) {
            ArticleGroup g = (ArticleGroup) this.getHibernateTemplate().get(
                    ArticleGroup.class, gid);
            if (g!=null){
                gCmd = " and a.agroup.footLeft >= " + g.getFootLeft() + " and a.agroup.footRight <= " +g.getFootRight();
            }

        }
        String hql = "from Article a  where a.status is true and a.user.id="+uid+gCmd+" order by a.vbook.ipOwn desc";
        return HibernateCallbackUtil.getRs(this.getHibernateTemplate(), hql, firstResult, page, pagesize);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Rs search(long gid,String key,boolean fulltext,int firstResult, int page, int pagesize) {

//        FullTextSession fts = Search.getFullTextSession(this.getSessionFactory().getCurrentSession());
//        QueryBuilder qb = fts.getSearchFactory().buildQueryBuilder().forEntity(Article.class).get();
//
//        Query luceneQuery;
//        List<Article> data;
//        Rs rs=null;
//        try {
//            luceneQuery = qb.keyword().onFields("content","subject","titleImgTxt","author","authorDept").matching(key).createQuery();
//
//            FullTextQuery query = fts.createFullTextQuery(luceneQuery, Article.class);
//            query.addQueryHint("status is true");
//            query.setFirstResult((page - 1) * pagesize);
//            query.setMaxResults(pagesize);
//            data = query.list();
//            rs=RsUtil.init(page, pagesize, query.getResultSize());
//            List<Article> la=ArticleUtil.hightLight(luceneQuery, data, "content","subject","titleImgTxt","author","authorDept");
//            rs.setList(la);
//        } catch (EmptyQueryException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        if (rs!=null) {
//			/*for (Object art:rs.getList()) {
//		    	Article a=(Article) art;
//		    	System.out.println("a.getSubject():"+a.getSubject());
//		    }*/
//        }else {
//            rs=new Rs();
//            rs.setList(new ArrayList<Article>());
//        }



//        return rs;
        return null;
    }

    @Override
    //本函数未用
    public Rs statByUser(long gid, int page, int pagesize) {
        String hql="select u from User u,Article a where a.user.id=u.id and a.status is true order by count(a) desc";
        return HibernateCallbackUtil.getRs(this.getHibernateTemplate(), hql, 0, page, pagesize);
    }

    @SuppressWarnings("unchecked")
    @Override
    public PortalStatInfo stat(PortalStatInfo psi) {
        String hql="select count(*) from Article";
        List<Long> list = (List<Long>) this.getHibernateTemplate().find(hql);
        psi.setArtsTotal(list.get(0));

        hql="select count(*) from Article a where a.status is true ";
        list = (List<Long>) this.getHibernateTemplate().find(hql);
        psi.setArtsPassed(list.get(0));

        return psi;
    }

    @Override
    public long countByUid(long uid) {
        String hql="select count(*) from Article a where a.user.id=?0";
        @SuppressWarnings("unchecked")
        List<Long> list = (List<Long>) this.getHibernateTemplate().find(hql,uid);
        return list.get(0);
    }


    @Override
    public void topOne(long id,boolean state) {
        this.getHibernateTemplate().bulkUpdate("update Article a set a.topOne=false where a.topOne=true");
        if (state){
            this.getHibernateTemplate().bulkUpdate("update Article a set a.topOne=true where a.id="+id);
        }
    }
}
