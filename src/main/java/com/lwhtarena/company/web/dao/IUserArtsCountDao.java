package com.lwhtarena.company.web.dao;

import com.lwhtarena.company.hql.entities.Rs;
import com.lwhtarena.company.web.entities.User;
import com.lwhtarena.company.web.entities.UserArtsCount;

/**
 * @Author：liwh
 * @Description:
 * @Date 17:21 2018/8/4
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public interface IUserArtsCountDao {

    public boolean modify(UserArtsCount uac);
    public UserArtsCount findByUK(User user, int timeKey);
    public Rs findTopByUKAndGroup(int groupId, int timeKey, int mod, int page,
                                  int pageSize);
    public Rs findTopByUKAndGroup(String gidStr,int timeKey,int mod,int page,
                                  int pageSize);
    public long countByUid(long uid);
}
