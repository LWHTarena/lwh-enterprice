package com.lwhtarena.company.web.dao;

import com.lwhtarena.company.web.entities.VisitorCountInPeriod;

/**
 * @Author：liwh
 * @Description:
 * @Date 17:23 2018/8/4
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public interface IVisitorCountInPeriodDao {


    public VisitorCountInPeriod add(VisitorCountInPeriod vcip);
    public void modify(VisitorCountInPeriod vcip);
    public VisitorCountInPeriod findByID(long id);
    public VisitorCountInPeriod findByPeriod(long vid,int hour);
    public long count(long vid,int hour);
    public long sum(long vid,int hour,int mode);
}
