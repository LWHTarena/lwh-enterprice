package com.lwhtarena.company.web.dao;

import com.lwhtarena.company.web.entities.CommentBridge;

/**
 * @Author：liwh
 * @Description:
 * @Date 17:16 2018/8/4
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public interface ICommentBridgeDao {

    public void modify(CommentBridge cb);

    public CommentBridge findByID(long id);

    public long countByUid(long uid);

}
