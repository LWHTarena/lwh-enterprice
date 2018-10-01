package com.lwhtarena.company.web.dao;

import com.lwhtarena.company.web.entities.ArticleGroup;
import org.springframework.orm.hibernate5.HibernateTemplate;

import java.util.List;

/**
 * @Author：liwh
 * @Description:
 * @Date 14:26 2018/8/4
 * @Modified By:
 * <h1>
 * <ol></ol>
 * </h1>
 */
public interface IGroupDao {

    public long add (ArticleGroup g);
    public void modify (ArticleGroup g);
    public ArticleGroup findByID(long id);
    public boolean delByID(long id);
    public List<ArticleGroup> queryByParentID(long parentID,boolean showPosterity,boolean gather,int status);
    public List<ArticleGroup> queryGatherByParentID(long parentID);
    public List<ArticleGroup> queryParentBySubID(long subID,int status);
    public List<ArticleGroup> queryChanged();
    public HibernateTemplate ht();
    public void move(ArticleGroup g,int offset);
    public void changed(ArticleGroup g);

}
