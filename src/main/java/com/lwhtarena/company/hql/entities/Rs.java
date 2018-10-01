package com.lwhtarena.company.hql.entities;

import java.util.List;

/**
 * @Author：liwh
 * @Description:
 * @Date 16:27 2018/8/4
 * @Modified By:
 * <h1>分页插件</h1>
 * <ol></ol>
 */
public class Rs {
    long count;
    List<?> list;
    int page;
    int pageSize;
    int pageCount;

    public Rs() {
    }

    public long getCount() {
        return this.count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<?> getList() {
        return this.list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageCount() {
        return this.pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
}
