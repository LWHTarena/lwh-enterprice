package com.lwhtarena.company.web.dao;

import com.lwhtarena.company.web.entities.Poll;

/**
 * @Author：liwh
 * @Description:
 * @Date 17:18 2018/8/4
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public interface IPollDao {

    public Poll poll(Poll poll, int status);
    public Poll findByID(long id);
}
