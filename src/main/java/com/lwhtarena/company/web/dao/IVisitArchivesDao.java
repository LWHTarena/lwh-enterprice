package com.lwhtarena.company.web.dao;

import com.lwhtarena.company.web.entities.VisitArchives;

/**
 * @Author：liwh
 * @Description:
 * @Date 17:22 2018/8/4
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public interface IVisitArchivesDao {

    public VisitArchives add(VisitArchives va);
    public void modify(VisitArchives va);
    public VisitArchives findByID(long id);
    public VisitArchives findByDayKey(long vid,int dayKey);

}
