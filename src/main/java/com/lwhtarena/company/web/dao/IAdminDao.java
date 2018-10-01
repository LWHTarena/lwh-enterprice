package com.lwhtarena.company.web.dao;

import com.lwhtarena.company.hql.entities.Rs;
import com.lwhtarena.company.web.entities.Admin;

/**
 * @Author：liwh
 * @Description:
 * @Date 17:14 2018/8/4
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public interface IAdminDao {

    public Admin add(Admin admin);

    public void modify(Admin admin);

    public void modifySafely(Admin admin);

    public Admin findByID(long id);

    public Admin findByUsername(String username);

    public boolean delByID(long id);

    public Rs find(int page, int pagesize);

    public Admin login(Admin admin);

    public void pwchange(Admin admin, String newpw) ;
}
