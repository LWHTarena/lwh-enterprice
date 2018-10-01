package com.lwhtarena.company.web.dao;

import com.lwhtarena.company.hql.entities.Rs;
import com.lwhtarena.company.web.entities.CommentThread;

/**
 * @Author：liwh
 * @Description:
 * @Date 17:16 2018/8/4
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public interface ICommentThreadDao {
    public CommentThread add(CommentThread ct);
    public CommentThread findByID(long id);
    public boolean delByID(long id);
    public void modify(CommentThread ct);
    public Rs queryByBid(long bid, int page, int pagesize, boolean asc, int status, boolean deletedShow);
    public long countByUid(long uid);
}
