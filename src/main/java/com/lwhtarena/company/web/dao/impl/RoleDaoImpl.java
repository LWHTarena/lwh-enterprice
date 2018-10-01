package com.lwhtarena.company.web.dao.impl;

import com.lwhtarena.company.web.dao.IRoleDao;
import com.lwhtarena.company.web.entities.Role;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author：liwh
 * @Description:
 * @Date 17:38 2018/8/4
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class RoleDaoImpl extends HibernateDaoSupport implements IRoleDao {


    @Override
    public Role add(Role role) {
        this.getHibernateTemplate().save(role);
        return role;
    }

    @Override
    public Role findByID(long id) {
        return this.getHibernateTemplate().get(Role.class, id);
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
    public void modify(Role role) {
        if (role.isDef()) {
            String hql="";
            hql="update Role r set r.def=false";
            this.getHibernateTemplate().bulkUpdate(hql);
        }

        this.getHibernateTemplate().saveOrUpdate(role);

    }


    @Override
    public int countUserByRoleID(long id) {
        String hql="select count(*) from User u where u.role.id=?0";
        int count = (Integer) this.getHibernateTemplate().find(hql,id).get(0);
        return count;
    }

    @Override
    public List<Role> queryAll() {
        List<Role> list = this.getHibernateTemplate().loadAll(Role.class);
        // TODO Auto-generated method stub
        return list;
    }

    @Override
    public List<Role> queryAllAndCounts() {
        String hql="from Role r order by r.id desc";
        @SuppressWarnings("unchecked")
        List<Role> list = (List<Role>) this.getHibernateTemplate().find(hql);
        List<Role> listnew = new ArrayList<Role>();
        long count;
        for (Role r : list) {
            hql="select count(*) from User u where u.role.id="+r.getId();
            count =  (Long) this.getHibernateTemplate().find(hql).get(0);
            r.setCounts(count);
            listnew.add(r);
        }
        return listnew;
    }

    @Override
    public void setDef(long id) {
        String hql="";
        hql="update Role r set r.def=false";
        this.getHibernateTemplate().bulkUpdate(hql);
        hql="update Role r set r.def=true where r.id="+id;
        this.getHibernateTemplate().bulkUpdate(hql);
    }

    @Override
    public Role findDef() {
        String hql="from Role r where r.def is true";
        @SuppressWarnings("unchecked")
        List<Role> list = (List<Role>) this.getHibernateTemplate().find(hql);
        if (list.size()>0){
            return list.get(0);
        }else{
            return null;
        }

    }

    @Override
    public List<Role> findByName(String name) {
        String hql = "from Role r where r.name=?0";
        @SuppressWarnings("unchecked")
        List<Role> lu=(List<Role>) this.getHibernateTemplate().find(hql,name);
        if (lu.size() > 0) { // 如果有子对象，则拒绝删除
            return lu;
        }else{
            return null;
        }
    }

    @Override
    public List<Role> findByNameOutID(String name, long id) {
        String hql = "from Role r where r.name=?0 and r.id<>"+id;
        @SuppressWarnings("unchecked")
        List<Role> lu=(List<Role>) this.getHibernateTemplate().find(hql,name);
        if (lu.size() > 0) { // 如果有子对象，则拒绝删除
            return lu;
        }else{
            return null;
        }
    }
}
