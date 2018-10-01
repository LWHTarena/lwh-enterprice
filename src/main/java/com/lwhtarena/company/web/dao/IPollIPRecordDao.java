package com.lwhtarena.company.web.dao;

import com.lwhtarena.company.hql.entities.Rs;
import com.lwhtarena.company.web.entities.PollIPRecord;

import java.util.List;

/**
 * @Author：liwh
 * @Description:
 * @Date 17:18 2018/8/4
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public interface IPollIPRecordDao {

    public PollIPRecord add(PollIPRecord pr);
    public PollIPRecord findLast(long pid);
    public List<PollIPRecord> findSameIPAfterDatetime(long pid, long datetime, String ip);
    public Rs findSameIPAfterDatetime(long pid, long datetime, String ip, int page, int pagesize);
    public Rs find(long pid, int page, int pagesize);

}
