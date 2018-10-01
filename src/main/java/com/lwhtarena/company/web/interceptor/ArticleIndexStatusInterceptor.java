package com.lwhtarena.company.web.interceptor;

import com.lwhtarena.company.web.entities.Article;
//import org.hibernate.search.indexes.interceptor.EntityIndexingInterceptor;
//import org.hibernate.search.indexes.interceptor.IndexingOverride;

/**
 * @Author：liwh
 * @Description:
 * @Date 11:25 2018/8/5
 * @Modified By:
 * <h1>配置一个拦截器</h1>
 * <ol>当文章的审核状态为true时，加入结果列表，否则剔除</ol>
 */
public class ArticleIndexStatusInterceptor {
//        implements EntityIndexingInterceptor<Article> {

//    @Override
//    public IndexingOverride onAdd(Article article) {
//        if(article.isStatus()){
//            return IndexingOverride.APPLY_DEFAULT;
//        }
//        return IndexingOverride.SKIP;
//    }
//
//    @Override
//    public IndexingOverride onUpdate(Article article) {
//        if(article.isStatus()){
//            return IndexingOverride.UPDATE;
//        }else {
//            return IndexingOverride.REMOVE;
//        }
//    }
//
//    @Override
//    public IndexingOverride onDelete(Article article) {
//        return IndexingOverride.APPLY_DEFAULT;
//    }
//
//    @Override
//    public IndexingOverride onCollectionUpdate(Article article) {
//        return onUpdate(article);
//    }
}
