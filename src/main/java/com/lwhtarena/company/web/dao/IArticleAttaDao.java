package com.lwhtarena.company.web.dao;

import com.lwhtarena.company.web.entities.ArticleAtta;
import java.util.List;

/**
 * @Author：liwh
 * @Description:
 * @Date 17:15 2018/8/4
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public interface IArticleAttaDao {


    public ArticleAtta add(ArticleAtta aa);

    public ArticleAtta findByID(long id);

    public boolean delByID(long id);

    public List<ArticleAtta> findByArticleID(long aid);

    public List<ArticleAtta> findByArticleAndFileID(long aid,long uid);
}
