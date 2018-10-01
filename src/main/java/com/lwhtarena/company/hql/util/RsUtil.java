package com.lwhtarena.company.hql.util;

import com.lwhtarena.company.hql.entities.Rs;

import java.util.ArrayList;

/**
 * @Author：liwh
 * @Description:
 * @Date 16:34 2018/8/4
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class RsUtil {
    public RsUtil() {
    }

    public static Rs init(int page, int pageSize, long count) {
        Rs rs = new Rs();
        if (pageSize <= 0) {
            pageSize = 10;
        }

        int pageCount;
        if (count % (long)pageSize > 0L) {
            pageCount = Integer.parseInt(String.valueOf(count / (long)pageSize)) + 1;
        } else {
            pageCount = Integer.parseInt(String.valueOf(count / (long)pageSize));
        }

        if (page > pageCount) {
            page = pageCount;
        }

        if (page < 1) {
            page = 1;
        }

        rs.setCount(count);
        rs.setPageCount(pageCount);
        rs.setPage(page);
        rs.setPageSize(pageSize);
        if (count == 0L) {
            rs.setList(new ArrayList());
        }

        return rs;
    }
}
