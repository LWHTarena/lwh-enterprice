package com.lwhtarena.company.web.dao;

import com.lwhtarena.company.hql.entities.Rs;
import com.lwhtarena.company.web.entities.Article;
import com.lwhtarena.company.web.portal.obj.PortalStatInfo;

/**
 * @Author：liwh
 * @Description:
 * @Date 17:16 2018/8/4
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public interface IArticleDao {

    public Article add (Article art);

    public void modify (Article g);

    public Article findByID(long id);

    public boolean delByID(long id);

    public Rs queryByGid(long gid, int firstResult, int page, int pagesize, boolean asc, int status, int img);

    public Rs queryByUid(long uid, long gid, int firstResult, int page, int pagesize, boolean asc, int status);

    public Rs hotByGid(long gid,int firstResult,int page,int pagesize);

    public Rs clicksByGid(long gid,int firstResult,int page,int pagesize);

    public Rs randByGid(long gid,int page,int pagesize);

    public Rs hotByUid(long uid,long gid,int firstResult,int page,int pagesize);

    public Rs clicksByUid(long uid,long gid,int firstResult,int page,int pagesize);

    public Rs search(long gid,String key,boolean fulltext,int firstResult,int page,int pagesize);

    public Rs statByUser(long gid,int page,int pagesize);

    public PortalStatInfo stat(PortalStatInfo psi);

    public long countByUid(long uid);

    public void topOne(long id,boolean state);
}
