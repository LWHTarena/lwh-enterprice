package com.lwhtarena.company.web.dao;

import com.lwhtarena.company.hql.entities.Rs;
import com.lwhtarena.company.web.entities.TempletPortalMain;

import java.util.List;

/**
 * @Author：liwh
 * @Description:
 * @Date 17:20 2018/8/4
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public interface ITemplatePortalMainDao {


    public long add(TempletPortalMain templet);
    public boolean delByID(long id);
    public void modify(TempletPortalMain templet);
    public TempletPortalMain findByID(long id);
    public TempletPortalMain findByUuid(String uuid);
    public List<TempletPortalMain> findByTitle(String title);
    public Rs query(int page, int pagesize);
    public Rs queryByAllow(int page, int pagesize);
    public List<TempletPortalMain> queryAll();
    public void setDef(long id);
    public TempletPortalMain findDef();
}
