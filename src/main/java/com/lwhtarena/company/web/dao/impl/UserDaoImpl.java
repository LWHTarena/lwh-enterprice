package com.lwhtarena.company.web.dao.impl;

import com.lwhtarena.company.hql.entities.Rs;
import com.lwhtarena.company.hql.util.HibernateCallbackUtil;
import com.lwhtarena.company.login.util.LoginUtil;
import com.lwhtarena.company.web.dao.IUserDao;
import com.lwhtarena.company.web.entities.User;
import com.lwhtarena.company.web.portal.obj.PortalStatInfo;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.List;

/**
 * @Author：liwh
 * @Description:
 * @Date 17:41 2018/8/4
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class UserDaoImpl extends HibernateDaoSupport implements IUserDao {
    @Override
    public User add(User user) {
        if (findByUsername(user.getUsername())!=null) {
            return null;
        }
        user = (User) LoginUtil.create(user, true);
        this.getHibernateTemplate().save(user);
        return user;
    }

    @Override
    public void modify(User user) {
        this.getHibernateTemplate().update(user);
    }

    @Override
    public void modifySafely(User user) {
        LoginUtil.safeSave(this.getHibernateTemplate(), user);

    }

    //通过用户名、手机号、邮箱来获取用户
    @Override
    public User findByKeywords(String keywords) {
        // TODO Auto-generated method stub
        User user=new User();
        user.setUsername(keywords);
        return (User) LoginUtil.find(this.getHibernateTemplate(), user);
    }


    @Override
    public User findByID(long id) {
        return this.getHibernateTemplate().get(User.class, id);
    }

    @Override
    public User findByUsername(String username) {
        String hql = "from User a where a.username=?0";
        @SuppressWarnings("unchecked")
        List<User> lu=(List<User>) this.getHibernateTemplate().find(hql,username);
        if (lu.size() > 0) { // 如果有子对象，则拒绝删除
            return lu.get(0);
        }else{
            return null;
        }
    }

    @Override
    public User findByMobile(String mobile,long excludeID) {
        String hql = "from User a where a.mobile=?0";
        if (excludeID>0L) {
            hql+= " and a.id<>"+excludeID;
        }
        @SuppressWarnings("unchecked")
        List<User> lu=(List<User>) this.getHibernateTemplate().find(hql,mobile);
        if (lu.size() > 0) { // 如果有子对象，则拒绝删除
            return lu.get(0);
        }else{
            return null;
        }
    }

    @Override
    public User findByEmail(String email,long excludeID) {
        String hql = "from User a where a.email=?0";
        if (excludeID>0L) {
            hql+= " and a.id<>"+excludeID;
        }
        @SuppressWarnings("unchecked")
        List<User> lu=(List<User>) this.getHibernateTemplate().find(hql,email);
        if (lu.size() > 0) { // 如果有子对象，则拒绝删除
            return lu.get(0);
        }else{
            return null;
        }
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
    public Rs find(int page, int pagesize) {
        String hql = "from User u order by u.id desc";
        int firstPlace = 0;
        return HibernateCallbackUtil.getRs(this.getHibernateTemplate(), hql, firstPlace, page, pagesize);
    }

    @Override
    public User login(User user) {
		/*if (findByUsername(user.getUsername())==null){
			return null;
		}*/
        user = (User) LoginUtil.login(this.getHibernateTemplate(), user);
        return user;
    }

    @Override
    public void pwchange(User user, String newpw) {
        user.setSalt(null);
        user.setPassword(newpw);
        user = (User) LoginUtil.passwordMD5Create(user);
        this.getHibernateTemplate().update(user);

    }

    @SuppressWarnings("unchecked")
    @Override
    public PortalStatInfo stat(PortalStatInfo psi) {
        String hql="select count(*) from User";
        List<Long> list = (List<Long>) this.getHibernateTemplate().find(hql);
        psi.setUserTotal(list.get(0));

        hql="select count(*) from User u where u.state is true ";
        list = (List<Long>) this.getHibernateTemplate().find(hql);
        psi.setUserPassed(list.get(0));

        return psi;
    }

    @Override
    public Rs artsPassedRank(int page, int pagesize) {
        String hql="from User u where u.state is true order by u.artsPassed desc";
        int firstPlace = 0;
        return HibernateCallbackUtil.getRs(this.getHibernateTemplate(), hql, firstPlace, page, pagesize);
    }

}
