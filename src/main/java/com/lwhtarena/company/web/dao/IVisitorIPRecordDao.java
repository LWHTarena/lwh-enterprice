package com.lwhtarena.company.web.dao;

import com.lwhtarena.company.hql.entities.Rs;
import com.lwhtarena.company.web.entities.VisitorIPRecord;

import java.util.List;

/**
 * @Author：liwh
 * @Description:
 * @Date 17:24 2018/8/4
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public interface IVisitorIPRecordDao {
    public VisitorIPRecord add(VisitorIPRecord vipr);
    public void modify(VisitorIPRecord vipr);
    public VisitorIPRecord findLast(long bid);
    public List<VisitorIPRecord> findSameIPAfterDatetime(long bid, long datetime, String ip);
    public Rs findSameIPAfterDatetime(long bid, long datetime, String ip, int page, int pagesize);
    public Rs find(long bid, int page, int pagesize);
    public long currPeriod(long bid,int stamp);
}
