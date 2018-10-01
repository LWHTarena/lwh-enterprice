package com.lwhtarena.company.web.dao;

import com.lwhtarena.company.web.entities.Portal;

/**
 * @Author：liwh
 * @Description:
 * @Date 17:19 2018/8/4
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public interface IPortalDao {
    public Portal add(Portal portal);
    public Portal findByID(long id);
    public void modify(Portal portal);
    public Portal query();
}
