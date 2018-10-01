package com.lwhtarena.company.web.dao;

import com.lwhtarena.company.web.entities.VisitorsBook;

/**
 * @Author：liwh
 * @Description:
 * @Date 17:24 2018/8/4
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public interface IVisitorsBookDao {

    public void modify(VisitorsBook vbook);
    public VisitorsBook findByID(long id);
}
