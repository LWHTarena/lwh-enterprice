package com.lwhtarena.company.web.dao;

import com.lwhtarena.company.hql.entities.Rs;
import com.lwhtarena.company.web.entities.User;
import com.lwhtarena.company.web.portal.obj.PortalStatInfo;

/**
 * @Author：liwh
 * @Description:
 * @Date 17:14 2018/8/4
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public interface IUserDao {

    public User add(User user);
    public void modify(User user);
    public void modifySafely(User user);
    public User findByKeywords(String keywords);
    public User findByID(long id);
    public User findByUsername(String username);
    public User findByMobile(String mobile,long excludeID);
    public User findByEmail(String email,long excludeID);
    public boolean delByID(long id);
    public Rs find(int page, int pagesize);
    public User login(User user);
    public void pwchange(User user, String newpw) ;
    public PortalStatInfo stat(PortalStatInfo psi);
    public Rs artsPassedRank(int page, int pagesize);
}
