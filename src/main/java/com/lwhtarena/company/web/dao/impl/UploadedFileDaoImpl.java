package com.lwhtarena.company.web.dao.impl;

import com.lwhtarena.company.hql.entities.Rs;
import com.lwhtarena.company.hql.util.HibernateCallbackUtil;
import com.lwhtarena.company.web.dao.IUploadedFileDao;
import com.lwhtarena.company.web.entities.UploadedFile;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.List;

/**
 * @Author：liwh
 * @Description:
 * @Date 17:40 2018/8/4
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class UploadedFileDaoImpl extends HibernateDaoSupport implements IUploadedFileDao {

    @Override
    public long add(UploadedFile uf) {
        this.getHibernateTemplate().save(uf);
        return uf.getId();
    }

    @Override
    public boolean delByID(long id) {
        this.getHibernateTemplate().delete(findByID(id));

        if (findByID(id)==null){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void modify(UploadedFile uf) {
        this.getHibernateTemplate().update(uf);

    }

    @Override
    public UploadedFile findByID(long id) {
        return this.getHibernateTemplate().get(UploadedFile.class, id);
    }


    /*
     * 分页查询
     */

    @Override
    public Rs query(int page, int pagesize) {
        String hql="from UploadedFile u order by u.id desc";
        int firstPlace=0;
        return HibernateCallbackUtil.getRs(this.getHibernateTemplate(), hql,firstPlace, page, pagesize);
    }

    @Override
    public boolean delByURL(String url) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public UploadedFile findByURL(String url) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UploadedFile findByMD5(String md5,long excludedID) {
        String hql="from UploadedFile u where u.id<>"+excludedID+" and  u.md5='"+md5+"' order by u.id asc";
        int firstPlace=0;
        Rs rs = HibernateCallbackUtil.getRs(this.getHibernateTemplate(), hql,firstPlace, 0, 1);
        @SuppressWarnings("unchecked")
        List<UploadedFile> lu=(List<UploadedFile>) rs.getList();
        if (lu!=null && lu.size()>0){
            return lu.get(0);
        }
        return null;
    }

    @Override
    public long countByUid(long uid) {
        String hql="select count(*) from UploadedFile u where u.user.id=?0";
        @SuppressWarnings("unchecked")
        List<Long> list = (List<Long>) this.getHibernateTemplate().find(hql,uid);
        return list.get(0);
    }
}
