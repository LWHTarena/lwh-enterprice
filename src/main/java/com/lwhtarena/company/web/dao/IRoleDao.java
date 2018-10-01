package com.lwhtarena.company.web.dao;

import com.lwhtarena.company.web.entities.Role;

import java.util.List;

/**
 * @Author：liwh
 * @Description:
 * @Date 17:19 2018/8/4
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public interface IRoleDao {

    public Role add(Role role);
    public Role findByID(long id);
    public List<Role> findByName(String name);
    public List<Role> findByNameOutID(String name,long id);
    public boolean delByID(long id);
    public void modify(Role role);
    public int countUserByRoleID(long id);
    public List<Role> queryAll();
    public List<Role> queryAllAndCounts();
    public void setDef(long id);
    public Role findDef();
}
