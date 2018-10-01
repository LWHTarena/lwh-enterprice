package com.lwhtarena.company.hql.entities;

import org.springframework.orm.hibernate5.HibernateTemplate;

/**
 * @Author：liwh
 * @Description:
 * @Date 16:30 2018/8/4
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class HibernateCallbackUtilVo {

    private int page;
    private int pageSize;
    private int firstPlace;

    private String hql;
    private long originalCount;
    HibernateTemplate hibernateTemplate;

    public HibernateCallbackUtilVo() {
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getFirstPlace() {
        return firstPlace;
    }

    public void setFirstPlace(int firstPlace) {
        this.firstPlace = firstPlace;
    }

    public String getHql() {
        return hql;
    }

    public void setHql(String hql) {
        this.hql = hql;
    }

    public long getOriginalCount() {
        return originalCount;
    }

    public void setOriginalCount(long originalCount) {
        this.originalCount = originalCount;
    }

    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }

    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }
}
