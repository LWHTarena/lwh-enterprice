package com.lwhtarena.company.web.dao;

import com.lwhtarena.company.hql.entities.Rs;
import com.lwhtarena.company.web.entities.UploadedFile;

/**
 * @Author：liwh
 * @Description:
 * @Date 17:20 2018/8/4
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public interface IUploadedFileDao {

    public long add(UploadedFile uf);
    public boolean delByID(long id);
    public boolean delByURL(String url);
    public void modify(UploadedFile uf);
    public UploadedFile findByID(long id);
    public UploadedFile findByMD5(String md5,long excludedID);
    public UploadedFile findByURL(String url);
    public Rs query(int page, int pagesize);
    public long countByUid(long uid);
}
